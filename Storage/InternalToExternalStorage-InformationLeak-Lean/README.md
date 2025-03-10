# Summary
Apps that allow files in internal storage to be downloaded to external storage are vulnerable to information leak.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit

*Issue* : Consider an app has a component X that accepts requests to copy a file from internal storage to external storage, which is inherently insecure. If such an app does not differentiate between trusted and untrusted sources of requests, then it can be exploited to leak information.

*Example* : We demonstrate how this is possible through two apps, *Benign* and *Malicious*. *DownloadService* in *Benign* accepts a file name and writes the contents of this file into the external storage. *Malicious* sends an intent to *DownloadService* and embeds a file name that it wants to read from internal storage. *DownloadService* takes the file name, copies it into the external storage, and a *Malicious* reads the file from external storage.

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

7.  Launch *Benign*. Edit the text field and click on *save*.

8. Launch *Malicious* and click on *Start Service* button.

    You will see the file contents displayed on screen.

9. Check logcat.

    `$ adb logcat -d | grep "Benign/DownloadService"`

    You should see a message that file was written to external storage successfully.

# References

1.  [Android Official Documentation](https://developer.android.com/guide/topics/data/data-storage.html#filesExternal)

2. [Issue 53](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/53/explore-cve-2014-1566-to-add-new-lean-and)
