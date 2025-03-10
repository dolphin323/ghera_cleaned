# Summary
Apps that allow its activities to be started in a new task are vulnerable to Activity Hijack attack.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
In *TaskAffinity-PhishingAttack* we explained how a malicious activity can poison a task comprised of benign activities. As a result of task poisoning,
malicious apps can rely on the *back button* to hijack benign activities.

*Issue:* If a user visits a malicious app's activity with the same task affinity as that of a benign app then the malicious activity becomes
part of the benign task. If this occurs then there will exist a benign activity A from which the user can navigate to a malicious activity M by
pressing the back button instead of navigating to the intended benign activity C. Therefore, malicious activity M will have hijacked benign
activity C.

*Example:* This vulnerability is demonstrated by the pair of benign and malicious apps in *Benign* and *Malicious*. The Benign app is as follows:

1.    User navigates to LoginActivity when she starts the app. In this activity the user is asked to enter her credentials.
2.    If the credentials are authorized the user navigates to HomeActivity where she can click on *Take Picture* button. Clicking on this button takes the user to *CameraActivity* which is a mock camera. The user can then press back to come back to *HomeActivity*.
3.    In *HomeActivity* the user can click on the text *image* to start the ImageEditor Activity.
      Note that the ImageEditor has a task affinity="edu.ksu.santos.benign.editImage" which means that ImageEditor is started/launched in a new task called "edu.ksu.santos.benign.editImage".

The *Malicious* app has also managed to install itself in the device. Let us assume that the malicious app provides some useful functionality. The malicious app is as follows:

1.    MalActivity which provides useful functionality to the user but hides the malicious behavior.

Let us assume that there exists a moment when the user navigates to *MalActivity*. Let us also say that after navigating to *MalActivity*, the user
navigates to our Benign app and navigates to *ImageEditor*. When the user presses the *back button* from *ImageEditor*, *MalActivity* displayes a
login screen similar to LoginActivity in *Benign*. The user enters her credentials and the credentails are compromised. *MalActivity* takes the user
back to *LoginActivity* making it look like the user's session was lost.

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

7. Launch *Malicious* in the emulator where it was installed.

8. Launch *Benign* in the emulator where it was installed. You will see a Login screen. Login with random strings.

   Click on the button to take a picture.

   Press back to come back to *HomeActivity*. Click on the text *image*. Click on the back button.

   You will see a login screen pop up similar to the first screen, but this screen belongs to *Malicious*.

# References

1.  [Official Android Documentation](https://developer.android.com/guide/topics/manifest/activity-element.html#aff)

2.  [Towards Discovering and Understanding Task Hijacking in Android - Chuangang Ren](https://www.usenix.org/node/190944)
