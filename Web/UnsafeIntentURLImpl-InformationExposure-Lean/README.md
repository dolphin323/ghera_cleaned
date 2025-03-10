# Summary
Apps that do not safely handle an incoming intent embedded inside a URI are vulnerable to information exposure via intent hijacking.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android allows a webpage displayed in an Android app to launch an Android component via an intent. A webpage X needs to encode the intent as a valid URI U and load it into a component C that displays X. When the user interacts with the webpage X and takes an action, the resource pointed to by U is loaded.  

*Issue:* If an app has a component C that displays web pages and that component does not sanitize the URI before acting on it, then a malicious web page could embed an intent to a malicious component installed in the device. When the user interacts with the URI, she ends up interacting with the malicious component.

*Example:* The vulnerability is demonstrated by *Benign* and *Malicious* and a malicious webpage at *Misc/LocalServer/templates/intentSender.html*. *Benign* apps allows users to load a web page from a URI. *MyWebViewClient* component in Benign app checks if an incoming URI contains an embedded intent. If an intent is found, *MyWebViewClient* starts the target/intended component and sends sensitive information to it. *Misc/LocalServer/templates/intentSender.html* is a malicious web page containing a link with an intent to intended for *Malicious*. If a user loads this web page in *Benign* app and clicks on *Click Me* button, then sensitive information will be sent to *Malicious* app.

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

7. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8. Open *Benign* and type the URL "http://serverIP:5000/intent/". Click on the link *Click Me*.

    You will see the app *Malicious* launched and displayed the message *"Sensitive Information"*.


# References

1. [Android Intents with Chrome](https://developer.chrome.com/multidevice/android/intents)

2. [Issue 55](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/55/explore-cve-2014-1506-to-add-new-lean-and)
