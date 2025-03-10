# Summary
Apps that write sensitive information to files stored in External Storage can potentially leak sensitive information.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Android allows apps to save data in files. These files can be stored in *Internal Storage* or in *External Storage*. Files stored in *Internal Storage* are private to the app. *External Storage* is divided into two regions -

  1. a shared storage directory, which can be accessed by all apps. However, apps will need to request the user for permission to access *ExternalStorage* before accessing the shared directory.  
  2. a private storage directory for each app. An app does not need any permissions to access its own private storage directory. However, other apps can get access to this directory if they have permission to access *ExternalStorage*.

*Issue* : If an app writes sensitive information to any file stored in *ExternalStorage*, even if the file is in the private storage directory, then the app has no control over who can read the file. A malicious app with read
access to *ExternalStorage* can get access to sensitive information stored in the file.

*Example* : We demonstrate how this is possible through two apps, *Benign* - a benign app that saves the image of an SSN card in a file that is stored in the app's private storage directory, and *Malicious* - a malicious app that has write permission to *ExternalStorage*. The benign app's *Benign/.../MainActivity* loads the image of an SSN card from its local resource file and saves it in its private storage directory. *Malicious* reads that file and displays it. *Malicious* can do that because it was granted read permission to *ExternalStorage*.

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

7.  Launch *Benign*. This stores the image of a SSN card.

8.  Launch *Malicious*. You should be able to see the image of the SSN card that *Benign* had access to.

# References

1.  [Android Official Documentation](https://developer.android.com/guide/topics/data/data-storage.html#filesExternal)
