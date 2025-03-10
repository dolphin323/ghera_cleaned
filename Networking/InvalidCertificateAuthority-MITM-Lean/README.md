# Summary
Apps that do not check for the validity of a Certificate Authority when communicating with a server are
vulnerable to MITM attacks.   

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android apps can use SSL/TLS to securely communicate with a web server. When an app wants to connect with
a web server, it requests the web server for a certificate signed by a certificate authority that the app
trusts. How does the app know which Certificate Authority to trust? Every Android device comes with a
list of pre-installed Certificate Authorities that it trusts. All apps running on that device inherit
the trusted list of Certificate Authorities. If the server the app is trying to connect to is signed by
a Certificate Authority not trusted by the Android device in which the app is running then the connection
is refused. This becomes a problem if the server is signed with a certificate that is not well known or
the server is self-signed. Android provides the TrustManager and SSLContext APIs to workaround this problem.
An app can implement its own TrustManager listing the certificates it trusts. It can then set these TrustManagers
in an SSLContext and use that SSLContext when communicating with a server using SSL/TLS.


*Issue:* Apps can be vulnerable if they incorrectly implement a TrustManager. A TrustManager can be
implemented by implementing the *X509TrustManager* interface and overriding the methods *checkClientTrusted()*,
*checkServerTrusted()* and *getAcceptedUsers()*. The method which checks whether a server is signed by
a trusted Certificate Authority is *checkServerTrusted()*. If this method is not implemented correctly then app the app can end up connecting to a malicious
server signed by a fake Certificate Authority.

*Example:* The vulnerability is demonstrated by *Benign*. The app connects to a server over SSL/TLS. The server is signed with a self-signed Certificate Authority not trusted by any Android device and should refuse connection. However, the app works around this by implementing a TrustManager, *MyTrustManager.java* which is a blank implementation of the TrustManager interface, to successfully connect to the server. The local server in *Misc/LocalServer* is man-in-the-middle that masquerades as the server and is signed with a self signed certificate. As a result of the blank implementation of the TrustManager interface, *Misc/LocalServer* will also be able to connect to the app.

# Steps to build the sample apps and to exploit the vulnerability

1. Setup a local Web Server. We have used Flask here. If you want to use Flask follow the instructions [here](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/src/76cc87180f064b328c37cc57b5c743dba378a5de/Misc/LocalServer/README.md?at=master&fileviewer=file-view-default).

2. List targets:

    `$ avdmanager list targets`

3. List available Android Virtual Devices:

    `$ avdmanager list avd`

4. Create an emulator:

    `$ avdmanager create avd -n <name> -k <target>`

    *<target>* is obtained from the command listed in 1. *<name>* is the name you choose to give to the avd.

5. Start emulator:

    `$ emulator -avd <avd_name>`

    *<avd-name>* is obtained from the command listed in 3.

6. Edit the *url* field in *Benign/.../res/values/strings.xml* to reflect the url of the web server where the html file being loaded is stored.

7. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8. Check logcat to see response from local web server you setup:

    `$ adb logcat | grep "System.out"`

    The web content displayed in the console demonstrates that *Benign* does not verify the Certificate Authority.

# References

1.  [Official Android Documentation](https://developer.android.com/training/articles/security-ssl.html)

2.  [Mitigating Android SSL Vulnerabilities - Vasant Sudhakar Tendulkar](https://repository.lib.ncsu.edu/bitstream/handle/1840.16/8840/etd.pdf?sequence=2&isAllowed=y)
