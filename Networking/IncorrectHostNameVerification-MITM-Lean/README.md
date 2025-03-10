# Summary
Apps that do not check for the validity of a host name when communicating with a server are
vulnerable to MITM attacks.   

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android apps can use SSL/TLS for secure communication with a web server. Such communication involves two checks:

1. Check whether the web server is signed by a Certificate Authority that is trusted by the client, in this case the app.
2. After establishing the validity of the Certificate Authority, verify whether the certificate presented by the server is correct.  If it is not then the connection should be refused. Verification of the certificate can fail due two reasons:

    1. The certificate presented by the server does not have the correct hostname in its subject line.
    2. The server returns an incorrect certificate to the client due to virtual hosting.

*Issue:*
Android allows apps to provide a custom implementation of HostnameVerifier in which custom checks can be performed on the given hostname in verify method.  If these custom checks are incorrect or weak, then apps can end up connecting to malicious servers.

*Example:*
This vulnerability is demonstrated by *Benign*. *Benign* uses *HttpsURLConnection*, the API that implements the SSL/TLS protocol, to connect to a server over an insecure network. But the app implements a custom HostnameVerifier.verify() method that always returns true (akin to AllowAllHostnameVerifier). The local server in *Misc/LocalServer* acts as a man-in-the-middle and masquerades as the server. The app successfully connects to the man-in-the-middle since it allows connections to all hosts.

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

8.  Check logcat to see response from local web server you setup:

    `$ adb logcat | grep "System.out"`

    The web content displayed in the console demonstrates that *Benign* does not verify the identity of the hostname it connects to and accepts connections from any server including malicious ones.

# References

1.  [Official Android Documentation](https://developer.android.com/training/articles/security-ssl.html)

2.  [Mitigating Android SSL Vulnerabilities - Vasant Sudhakar Tendulkar](https://repository.lib.ncsu.edu/bitstream/handle/1840.16/8840/etd.pdf?sequence=2&isAllowed=y)
