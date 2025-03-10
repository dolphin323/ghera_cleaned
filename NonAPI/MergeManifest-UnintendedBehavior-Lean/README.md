# Summary
Apps that use a vulnerable library may unknowingly inherit the vulnerability.

# Versions of Android where the vulnerability is possible
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
When apps can use libraries that have manifest files, the library manifest files are merged with the app manifest file to form a single manifest file for the app.

*Issue*: If the library exports sensitive components via its manifest, then the app using the library will unknowingly inherit those components and may be vulnerable (e.g., perform unintended actions).

*Example:* This vulnerability is demonstrated by a pair of *Benign* and *Malicious* apps.  *Benign* uses a library *LogUtil* that enables *Benign* to log messages. The *LogUtil* library also has an exported service that keeps the device screen unlocked. When *Benign* uses the *LogUtil* library the exported service becomes part of the application and can be invoked by *Malicious* to start a background service that keeps the device unlocked.

*Note* : There is no way to secure the app against this vulnerability programmatically. It can be done at development time: [Merge manifest files](https://developer.android.com/studio/build/manifest-merge). Consequently, this benchmark does not have a *Secure* app.

# Steps to build the sample apps and to exploit the vulnerability

1. List targets:

    `$ avdmanager list targets`

2. List available Android Virtual Devices:

    `$ avdmanager list avd`

3. Create an emulator:

    `$ avdmanager create avd -n <name> -k <target>`

    *<target>* is obtained from the command listed in 1. *<name>* is the name you choose to give to the avd.

4. Start emulator:

    `$ emulator -avd <avd_name>`

    *<avd-name>* is obtained from the command listed in 2.

5. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7.  Launch *Malicious*

8.  Check Logcat to confirm if wake lock was successful

  `$ adb logcat -d | grep "LogUtilLib"`

  You will see a message "Long running background service"

# References

1.  [Official Android Documentation](https://developer.android.com/guide/topics/manifest/permission-element.html#plevel)

2. [Issue #46](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/46/explore-cve-2014-1977-to-create-new)
