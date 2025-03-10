# Summary
Apps that connect to a server via the *HTTP* protocol are vulnerable to Man-in-the-Middle (MITM) attacks.  

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android apps can connect to a web server via the *HTTP* protocol using the *HttpURLConnection* API.

*Issue:* A MITM can easily intercept traffic between the app and the server and can even spoof the address of the server to masquerade as the server to the app.

*Example:*
This vulnerability is demonstrated by *Benign* and a MITM depicted by *LocalServer*. *Benign* uses a background service to download data from a web server via the *HTTP* protocol. The MITM masquerades as the web server and connects with the app.

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

6. Edit the *url* field in *Benign/.../HttpIntentService.java* to reflect the url of the web server.

7. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8. Launch *Benign* and click on *Connect*.

  An activity should display the response from MITM. The web content displayed in the activity demonstrates that *Benign* does not verify the identity of the hostname it connects to and accepts connections from any server including malicious ones.

# References

1.  [Official Android Documentation](https://developer.android.com/reference/java/net/HttpURLConnection.html)

2.  [Related to Issue #29](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/29/explore-cve-2015-1595-to-create-new-lean)

3.  [Related to Issue #28](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/18/explore-cve-2016-1520-to-add-new-lean-and)
