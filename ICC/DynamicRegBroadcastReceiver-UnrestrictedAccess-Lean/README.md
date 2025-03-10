# Summary
Apps that register a broadcast receiver dynamically are vulnerable to granting unrestricted access to the broadcast receiver.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Broadcast Receivers can register to receive messages (intents) statically in the manifest file or
they be registered dynamically inside a .java file. Dynamically registering a broadcast receiver also exports the receiver automatically.

*Issue:* Since, dynamically registering a broadcast receiver exports a broadcast receiver, it can be triggered by any app, including unintended apps, via an intent.

*Example:* This vulnerability is demonstrated by the pair of apps *Benign* and *Malicious*. *MainActivity.java* in Benign/app/src/main/java.../MainActivity.java, has a broadcast receiver that is registered when an activity gets created and is unregistered when the activity is destroyed. When the user leaves the particular activity the broadcast receiver registered with this activity is triggered and an email is sent to the app's developers informing them that the user left the app.
Meanwhile, just because the user left the activity, the system does not destroy it and because the activity is not destroyed the broadcast receiver is still registered to receive intents. Also, since the BroadCastReceiver was registered dynamically it got exported by default. A malicious app can invoke this BroadCastReceiver and exploit it to send emails even if the malicious app is not authorized to send emails as shown in *Malicious/app/src/main/java/.../MalActivity.java*. Sending email is not very dangerous but the broadcast receiver exposed accidentally could very well be performing a more dangerous operation.

# Steps to build the sample apps and to exploit the vulnerability

1. List targets:

    `$ avdmanager list targets`

2. List available Android Virtual Devices:

    `$ avdmanager list avd`

3. Create an emulator:

    `$ avdmanager create avd -n <name> -k <target>`

    *target* is obtained from the command listed in 1. *name* is the name you choose to give to the avd.

4. Start emulator:

    `$ emulator -avd <avd_name>`

    *avd-name* is obtained from the command listed in 2.

5. Build and install Benign:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install Malicious:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7.  Open *Benign* in the emulator where you installed the app.

8.  Open *Malicious* in the emulator where you installed the app.

9.  You will see a message "An email will be sent to rookie@malicious.com with the text : I
can send email without any permissions"

    This means the Benign/UserLeftBroadcastRecv was called successfully from *Malicious*.

# References

1. [Analyzing Inter-Application Communication in Android - Erika Chin](http://dl.acm.org/citation.cfm?id=2000018)

2. [Official Android Documentation](https://developer.android.com/reference/android/content/BroadcastReceiver.html)
