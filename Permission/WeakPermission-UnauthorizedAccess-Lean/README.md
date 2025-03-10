# Summary
Apps that use weak permissions to protect exported components are akin to apps that do not protect exported components at all.

# Versions of Android where the vulnerability is possible
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Android allows developers create/define a *permission* and use it to protect exported components. A *permission* can have 4 protection levels among other attributes.

  1. *normal*. A permission with protection level *normal* is granted by Android to the requesting application at install time without explicitly asking the user.

  2. *dangerous*. A permission with protection level *dangerous* is granted by Android to the requesting application only if the user has explicitly approved of the request.

  3. *signature*. A permission with protection level *signature* is granted by Android to the requesting application only if the requesting application is signed with the same certificate that the application, where the permission is defined, is signed with.

  4. *signatureOrSystem*. A permission with protection level *signatureOrSystem* is granted by Android to the requesting application only if the requesting application is in the Android system image or if it is signed with the same certificate that the application, where the permission is defined, is signed with.

*Issue*: If an exported component is protected with a normal permission, then any app specifying to use this permission will be granted the permission by Android when the app is installed into the device.

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious*. *Benign* contains an exported content provider *MyContentProvider* that provides an interface to access sensitive information stored in *Benign*. *MyContentProvider* is protected by a *normal* permission. *Malicious* requests for the *normal* permission and is granted permission at install time by Android without asking the user. Once *Malicious* is installed it can query, insert, and delete information into *Benign's* sensitive information store via the *MyContentProvider*.  

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

7.  Launch *Malicious* and click on any button - *Query Benign CP*, *Insert Benign CP*, *Delete Benign CP*

8.  You can verify whether *Malicious* is able to access *Benign/MyContentProvider* by checking the log for *Benign*:

     `$ adb logcat -d | grep "Benign/MyCP"`

    You should be able to see a message depending on the button clicked in *Malicious*.

# References

1.  [Official Android Documentation](https://developer.android.com/guide/topics/manifest/permission-element.html#plevel)

2.  [Issue #46](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/46/explore-cve-2014-1977-to-create-new)
