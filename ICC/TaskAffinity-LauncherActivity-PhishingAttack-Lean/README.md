# Summary
Apps are vulnerable to phishing attacks and denial of service attacks if the device contains malicious apps that use taskAffinity.

# Android versions affected
Tested on Android 5.1.1 - Android 7.1

# Description of vulnerability and corresponding exploit
An activity X can request Android system that it be started in a specific task T. With such a request, when an activity X starts, it will be started in the task T. The name of the default task for an application is the package name set in the <manifest> element of the app manifest.

*Issue:* An activity M, a launcher activity of a *Malicious* app, can use task affinity to start in the same task as that of a benign app. This allows activity M to launch a denial-of-service (DOS) attack or a phishing attack on the benign app.

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious* apps. *Malicious* app has an activity named *MalActivity* with its task affinity set to *cs.ksu.edu.benign* (via *taskAffinity="cs.ksu.edu.benign"* in manifest.xml file). Consequently, *MalActivity* will always be started in task *cs.ksu.edu.benign*. Due to default configuration of *Benign* app, all activities of *Benign* app will be started in task *cs.ksu.edu.benign*. Now, if *MalActivity* is active before any activities from *Benign* app are activated, *MalActivity* will be root of the task *cs.ksu.edu.benign* (i.e., be on top of the task stack) and can deny all activities of *Benign* app from being displayed.

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

7.  Launch *Malicious* in emulator where it is installed.

8.  Launch *Benign* in emulator where it is installed. You will always see *Malicious* instead of Benign.

# References

1.  [Official Android Documentation](https://developer.android.com/guide/topics/manifest/activity-element.html#aff)

2.  [Towards Discovering and Understanding Task Hijacking in Android - Chuangang Ren](https://www.usenix.org/node/190944)
