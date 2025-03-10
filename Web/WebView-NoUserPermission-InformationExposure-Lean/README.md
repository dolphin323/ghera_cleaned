# Summary
Apps that disclose sensitive information without explicitly requesting the user for permission are vulnerable to unintended information exposure.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android apps that collect sensitive information can inadvertently leak such information thus breaching their users' privacy.

*Issue:* If an app collects sensitive information from a user and releases it to other apps without explicitly requesting the user for permission to do so, then the app is vulnerable to exposing sensitive information to malicious apps.

*Example:* The vulnerability is demonstrated by *Benign*. *Benign* is a simple browser that allows loading web pages and provides support for web pages to determine the device's location via the *GPSTracker* API. *GPSTracker* has *getLatitude()* method that can be called from JavaScript. When this method is called, it returns the latitude of the user's location without explicitly asking the user for permission to do so. A malicious web page can easily retrieve the location of a user without the user knowing about it.

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

6. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7. Create a simple web application that retrieves the location from *Benign* and host it on the local server set up in 1, or use the web application in *LocalServer/templates/dispLoc.html*.

8. Open *Benign* and enter the url of the local server that hosts the web application in 7 and click on *load*.

9. If you have used the web application in *LocalServer/templates/dispLoc.html*, you should be able to see the latitude value of the user's location.

# References

1. [CVE-2014-0806](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2014-0806)

2. [Information disclosure vulnerability in Sleipnir Mobile for Android](http://jvndb.jvn.jp/en/contents/2014/JVNDB-2014-000007.html)

3. [Issue 56](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/56/explore-cve-2014-0806-to-add-new-lean-and)
