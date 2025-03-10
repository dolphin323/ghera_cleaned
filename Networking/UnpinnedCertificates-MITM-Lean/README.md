# Summary
Apps that do not pin the certificates of servers they trust are
vulnerable to MITM attacks.   

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android apps can use SSL/TLS to securely communicate with a web server. All apps running on that device inherit
the trusted list of Certificate Authorities. If an app knows the certificate of server X, then app can store the corresponding certificate in a keystore for the future reference. When app tries to connect to any server, it will check against stored certificates. This is called *certificate pinning*.

*Issue:* Apps can be vulnerable if they do not implement a certificate pinning. If a certificate authority mistakenly trusts the Malicious server, then the app which does not implement the pinning, will end up trusting the Malicious server.

*Example:* The vulnerability is demonstrated by *Benign*. The app connects to a server over SSL/TLS. The local server in *Misc/LocalServer* is man-in-the-middle that masquerades as the server and is signed with a self signed certificate. For the purpose of demonstration, we will assume that the server X has a certificate which was signed by one of the trusted CAs. Benign app trusts all the servers having certificate signed by trusted CAs. Since, X has a certificate signed by trusted CA, Benign will connect to the X.

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

8. TextView will display response from local web server you setup:

    The web content displayed in the console demonstrates that *Benign* does not verify the Certificate Authority.

# References

1.  [Official Android Documentation](https://developer.android.com/training/articles/security-ssl#java)

2.  [To Pin or Not to Pin - Marten Oltrogge, Yasemin Acar, Sergej Dechand, Matthew Smith, Sascha Fahl](https://saschafahl.de/papers/pinnotpin2015.pdf)
