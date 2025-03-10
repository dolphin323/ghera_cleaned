# Summary of the Vulnerability
Apps that contain a broadcast receiver registered to receive system broadcast but does not check for validity of the broadcast message before performing corresponding operations are vulnerable to unintended invocation.

# Versions of Android where the vulnerability is possible
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
In Android, a broadcast receiver can register to receive messages(intents).  Typically, a broadcast receiver expresses its interest in messages by specifying corresponding string identifiers in <intent-filter> tags in its manifest file.  When Android system receives a message for broadcasting, it delivers the message to every broadcast receiver who specified a string identifier (via <intent-filter> tags) that matches the action string of the message.

*Issue:* Since the use of a <intent-filter> tag automatically exports the corresponding broadcast receiver and the message delivery is push-based, the broadcast receiver can be triggered by an appropriate message from any app, including unintended apps.
The vulnerability can be avoided if the broadcast receiver is protected by [permissions](https://developer.android.com/guide/topics/permissions/index.html). However, if a broadcast receiver registers to receive intents from the system about system events then the broadcast receiver cannot protect itself with permissions, because that would prevent the broadcast receiver from receiving the intent.
While it is true that a system intent can only be sent by the system, because a system intent has its action field set to a unique string identifier that can only be used by the system, the broadcast receiver is still exported (due to the intent-filter). This means that the broadcast receiver can be triggered by an app, including unintended apps, via an explicit intent.
An explicit intent does not need to have its action field set to a string identifier. It can set the action field directly to the class name and the package name within which the class resides. Thus, a malicious app can use an explicit intent to directly trigger the broadcast receiver bypassing the need to use a system defined string identifier in its action field.   

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious*. The *AndroidManifest.xml* file in *Benign/app/src/main* has _LowMemoryReceiver_ broadcast receiver in the <receiver> tag. It contains a <intent-filter> with *android.intent.action.DEVICE_STORAGE_LOW*.  This registers _LowMemoryReceiver_ to receive messages from the system when the device is low on memory.
When _LowMemoryReceiver_ is notified it calls a service to delete all files in the internal storage of the app. Due to the presence of the <intent-filter> it also means that it can be invoked by any other component via an explicit intent.
Consequently, a malicious app can send an explicit intent to LowMemoryReceiver and delete all files in the benign app's internal memory. See *MalActivity.java* in *Malicious/app/src/main/java/.../MalActivity.java*.


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

6.  Launch *Benign* in the emulator. Verify if "myfile" was created in the internal file-system of the app:

    `$ <path-to-android-sdk>/platform-tools/adb shell run-as "edu.ksu.cs.benign"`
    `$ ls files`

7.  Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8.  Open *Malicious* in the emulator and click on the button

    You will see a message *success* in *Benign*

# References

1.  [Official Android   Documentation](https://developer.android.com/reference/android/content/BroadcastReceiver.html)
