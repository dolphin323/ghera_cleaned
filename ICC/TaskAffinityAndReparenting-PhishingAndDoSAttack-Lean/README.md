# Summary
Apps are vulnerable to phishing attacks and denial of serivce attacks if the device contains malicious apps that use Task-reparenting.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
An activity A can request to always be at the top of a task. This means that whenever an activity from that task is requested to be brought to the foreground, activity A will be  displayed to the user. This is called Task Re-parenting.

*Issue:* An activity M in a malicious app can use task affinity and task re-parenting to request to start/launch M in the same task as that of an activity in a benign app and as the top of that task, thereby allowing activity M to launch a Denial-Of-Service attack or a Phishing attack.

*Example:* This vulnerability is demonstrated by Benign and Malicious apps. Since taskAffinity is set to "edu.ksu.cs.benign" for NonLauncherActivity in Malicious app, NonLauncherActivity will always be started in the task named edu.ksu.cs.benign. By default, all activities of Benign app will also be started in the task named edu.ksu.cs.benign. Since android:allowTaskReparenting is set true for NonLauncherActivity, starting an activity of the Benign app will display NonLauncherActivity if NonLauncherActivity was started (and not terminated) earlier. As NonLauncherActivity looks exactly like the Benign app's screen (activity), if Benign app is started after starting Malicious app, NonLaunchcerActivity will be displayed to steal user's credentials.


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

7.  Launch *Malicious* in emulator where it is installed and click on the button displayed.

    You will see an empty screen

8.  Launch *Benign* in emulator where it is installed.

    You will always see *Malicious* displaying a login screen that looks exactly  like *Benign*.

# References

1.  [Official Android Documentation](https://developer.android.com/guide/topics/manifest/activity-element.html#reparent)

2.  [Towards Discovering and Understanding Task Hijacking in Android - Chuangang Ren](https://www.usenix.org/node/190944)
