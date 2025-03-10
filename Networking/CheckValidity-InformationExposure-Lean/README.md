# Summary
Apps that do not check for the validity of a certificate when communicating with a server are
vulnerable to information exposure attacks.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android apps can use SSL/TLS to securely communicate with a web server. When an app wants to connect to
a web server, it requests the web server for a certificate signed by a certificate authority that the app trusts. Every certificate has a specified start date and end date. If the app tries to connect to the server which has a certificate with an end date earlier than the time when the app is trying to connect, then the connection should be refused. Android provides *checkValidity* API to check if the current date and time are within the validity period given in the certificate. Normally default trustmanager takes care of validating the certificates. If custom trustmanager is implemented, then *checkValidity* API can be used in *checkServerTrusted()* method of *X509TrustManager* to check the validity of all the certificates in the chain. Any improper implementation of *checkServerTrusted()* can lead to vulnerability. 


*Issue:* Apps can be vulnerable if they do not check the expire date of the server certificates. *checkValidity* API is a public method of *X509Certificate* interface. In case of custom trustmanager implementation the *checkServerTrusted()* method is responsible to decide which server to trust and validate the certificate of the server through *checkValidity* API. The partial or complete certificate chain provided by the peer is given to *checkServerTrusted()* as arguments. Every elements in this certificate chain should be checked for validity. If *checkValidity* method is not used correctly in *checkServerTrusted()*, then the app can end up connecting to a server signed by a certificate that already expired.

*Example:* The vulnerability is demonstrated by *Benign*. The app connects to a server over SSL/TLS. The server is signed with a Certificate currently invalid. So *Benign* should refuse the connection. However, the app successfully connects to this server as a result of improper implementation of the *checkValidity* in *checkServerTrusted()*. The vulnerability is mitigated in the secure app by checking validity of every certificates present in the certificate chain inside *checkServerTrusted()* method.

# Steps to build the sample apps and to exploit the vulnerability

1. Setup a local Web Server with an expired certificate. We have used Flask here. If you want to use Flask follow the instructions [here](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/src/76cc87180f064b328c37cc57b5c743dba378a5de/Misc/LocalServer/README.md?at=master&fileviewer=file-view-default).

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

6. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7. The web content displayed in the activity demonstrates that *Benign* does not verify the validity of the Certificate.

# References

1.  [Official Android Documentation](https://developer.android.com/reference/java/security/cert/X509Certificate.html#checkValidity())

2.  [Security Smells in Android](http://scg.unibe.ch/archive/papers/Ghaf17c.pdf)
