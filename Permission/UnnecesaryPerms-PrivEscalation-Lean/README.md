# Summary
Apps that use more permissions than they need are vulnerable to privilege escalation attacks.

# Versions of Android where the vulnerability is possible
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
The use of protected features on Android devices requires explicit permissions, and developers occasionally ask for more permissions than necessary.

*Issue*: If an app asks for more permissions than necessary then the permissions can be used by malicious apps that do not have those permissions to invoke protected APIs.

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious*. *Benign* uses a library *LogUtil* that enables *Benign* to log messages. The *LogUtil* library also has an exported service that keeps the device screen unlocked. But the service needs a permission *WAKE_LOCK* to keep the device screen unlocked. However, Benign does not use the service provided by *LogUtil*. But, *Benign* asks for *WAKE_LOCK* permission accidentally. *Malicious* invokes the exported service in the *LogUtil* library and uses *Benign's* unnecessary permission *WAKE-LOCK* to keep the device unlocked thus, draining the device battery. This would not have been possible if *Benign* hadn't accidentally asked for *WAKE_LOCK* permission.

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
