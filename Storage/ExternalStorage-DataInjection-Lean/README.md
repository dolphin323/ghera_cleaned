# Summary
Apps that read files stored in External Storage are vulnerable to malicious data injection attacks.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Android allows apps to save data in files. These files can be stored in *Internal Storage* or in *External Storage*. Files stored in *Internal Storage* are private to the app. *External Storage* is divided into two regions -

  1. a shared storage directory, which can be accessed by all apps. However, apps will need to request the user for permission to access *ExternalStorage* before accessing the shared directory.  
  2. a private storage directory for each app. An app does not need any permissions to access its own private storage directory. However, other apps can get access to this directory if they have permission to access *ExternalStorage*.

*Issue* : If an app reads from any file stored in *ExternalStorage*, even if the file is in the private storage directory, then the app has no control over the file it is reading. If a malicious app changes the file the app is reading, the app will unknowingly read malicious content.

*Example* : This vulnerability is demonstrated by two apps, *Benign*: a benign app that creates a file *bible.txt* and writes a message into it, and *Malicious*: a *malicious* app that has write permission to *ExternalStorage* and overwrites *bible.txt* file in *Benign's* private storage folder. *MainActivity* in *Benign* app uses *getExternalFilesDir()* method to get a handle to the root of *Benign's* private storage folder and store *bible.txt* in this folder. Using the same method and modifying the *absolute path* string to its *private storage folder*, *Malicious* accesses and modifies *bible.txt* in *Benign's private storage folder*. Consequently, any subsequent execution of Benign will read the *modified* contents of *bible.txt*.

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

6. Click on a button *Read Bible*

    You will see TextView with string "This is written by Benign"

7. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8.  Launch *Malicious*.

    The app writes *bible.txt* into *Benign's* external private storage directory.

9.  Launch *Benign*.

    *Benign* reads txt files and displays the following text: "To answer before listening — that is folly and shame."

# References

1.  [Android Official Documentation](https://developer.android.com/guide/topics/data/data-storage.html#filesExternal)
