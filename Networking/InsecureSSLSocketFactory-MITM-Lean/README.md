# Summary
Apps that use the SSLCertificateSocketFactory.getInsecure() method are vulnerable to MITM attacks.   

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android apps can use SSL/TLS for secure communication with a web server. Such communication involves two checks:

1. Check whether the web server has a certificate signed by a Certificate Authority that is trusted by the client, in this case the app.
2. After establishing the validity of the Certificate Authority, verify whether the certificate presented by the server is correct.  If it is not then the connection should be refused. Verification of the certificate can fail due two reasons:

    1. The certificate presented by the server does not have the correct hostname in its subject line.
    2. The server returns an incorrect certificate to the client due to virtual hosting.

The SSLCertificateSocketFactory has methods to generate an object that can open SSL Sockets.  One of these methods `getInsecure()` allows you to create one that does not check hostname.

*Issue:*
Use of the method getInsecure() opens up a vulnerability to Man in the Middle attacks.

*Example:*
This vulnerability is demonstrated by *Benign* app. *Benign* app uses `HttpsURLConnection` and `SSLCertificateSocketFactory`, APIs that implement the SSL/TLS protocol, to connect to a server over an insecure network. Specifically, the app uses getInsecure() to access a SSLSocketFactory that does not perform any SSL security checks when creating sockets. The local server in Misc/LocalServer acts as a man-in-the-middle and masquerades as the server. The app successfully connects to the man-in-the-middle since it allows connections to any host.

# Steps to build the sample apps and to exploit the vulnerability

1. Setup a local Web Server. We have used Flask here. If you want to use Flask follow the instructions [here](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/src/master/Misc/LocalServer/).

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

8.  You will see the response from the web server displayed on the screen.

# References

1.  [Android Security Report, Mar 2016](https://www.mitre.org/sites/default/files/publications/pr-16-0202-android-security-analysis-final-report.pdf)
