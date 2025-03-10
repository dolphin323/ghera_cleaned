# Summary
Apps that use the *call()* in the Content Provider API are vulnerable to exposing the underlying data store to unauthorized read and write.

# Versions of Android where the vulnerability is possible
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Android allows developers to share data across apps through the Content Provider API. Developers can limit access to their content providers via permissions. Whenever a request for data read/write is made to a content provider method by a component, the android system checks to see if the component making the request has the required permissions to do so.

*Issue:* The Content provider API provides a method *call(String method, String args, Bundle extras)* to call any provider-defined method. Android does no permission checking on this entry into the content provider besides the basic ability for the application to get access to the provider at all. For example, Android has no idea whether *call* will read or write data in the provider, so can't enforce those individual permissions.
Any implementation of this method must do its own permission checks on incoming calls to make sure they are allowed. Failure to do so will allow unauthorized components to interact with the content provider.

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious*. *Benign* has an exported content provider *FileContentProvider* that provides an interface to *query(), insert(), update(), delete(), and call()*. *FileContentProvider* is protected by a read permission. This means that *Benign* can invoke all the methods of *FileContentProvider* but other apps can only invoke *query() and call()* after requesting read permission from the user. However, *call()* writes data to *Benign's* internal files. Since the read permission will allow external apps to invoke *call()*, *Malicious* invokes *call()* to write malicious content into *Benign's* internal files without having *write permission*.

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

7.  Launch *Malicious* and click on "Inject File"

8.  You can verify if the file has been injected by running adb:

     `$ <path-to-android-sdk>/platform-tools/adb shell run-as "edu.ksu.cs.benign"`

     `$ ls files`

    You should be able to see a file called "MalFile.sh"

# References

1.  [Official Android Documentation](https://developer.android.com/reference/android/content/ContentProvider.html#call(java.lang.String, java.lang.String, android.os.Bundle))
