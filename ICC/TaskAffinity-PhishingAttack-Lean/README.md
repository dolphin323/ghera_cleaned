# Summary
Apps that allow its activities to be started in a new Task are vulnerable to phishing attacks

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
A task is a collection of activities. When an activity is started, Android looks for a task in which to launch the activity in. The activity that is displayed to the user always resides at the top of the task. If the user presses the back button then the activity at the top of the stack is removed. If the user navigates to another activity from the activity on display, then the new activity on display is added to the top of the task. If the user navigates to the home screen of her device, then the task which contained the activity on display is moved to the background. When an activity from this task is called, then the activity at the top of the task is displayed to the user. It must be noted at this point that the default behavior of Android is to group all activities started from an activity in a single task. So if activity A is used to start activity B and activity B is used to start activity C then, they will all be grouped in one single task. However, activities can choose to override the default behavior by requesting Android to group itself with another task or to start itself in a new task altogether. So if activity B requests Android to launch activity B in a new task, then when activity B is started from activity A, a new task will be started that will hold activity B.

Since, Android allows an activity to specify in which task it wants to get launched, an  activity in a malicious app can request to launch/start itself in the same task as that of an activity in a benign app. Let us call the malicious activity M with an affinity for task T. Let us assume a scenario where a benign app has an activity B with an affinity for task T. If malicious activity M manages to appear at the top of task T, then all calls to start activity B will end up resolving into calls to start activity M and the user will end up navigating to activity M instead of activity B. It is not hard to see how this can result in phishing attacks.

*Example:* This vulnerability is demonstrated by the pair of benign and malicious apps in TaskAffinity-PhishingAttack/Benign and TaskAffinity-PhishingAttack/Malicious. The Benign app is as follows:

1.    User navigates to LoginActivity when she starts the app. In this activity the user is asked to enter her credentials.
2.    If the credentials are authorized the user navigates to HomeActivity where she can take a picture.
3.    She can click on the picture to start the ImageEditor Activity where she can edit the image and send it back to HomeActivity.
      Note that the ImageEditor has a task affinity="edu.ksu.santos.benign.editImage" which means that ImageEditor is started/launched in a new task called "edu.ksu.santos.benign.editImage".

The Malicious app has also managed to install itself in the device. Let us assume that the malicious app provides some useful functionality. The malicious app is as follows:

1.    MalActivity which provides useful functionality to the user but hides the malicious behavior.

Also, let us assume that the user while editing the image in the benign app remembered an important task which can be completed by the malicious app. Therefore, the user now navigates to the malicious app.
After a while let us assume that the user comes back to the benign app. The benign app will now display *HomeActivity* with the last edited image.
The user clicks on the image and is navigated to an activity in the malicious app i.e. MalActivity. MalActivity now displays a screen that looks like the Login screen of the
benign image editor app. The user enters her credentials and the credentials are saved by MalActivity. This happened because MalActivity had the same task affinity as the ImageEditor
and when MalActivity was started pushed on top of ImageEditor in the task "edu.ksu.santos.benign.editImage". When the user went back to HomeActivity and clicked on the image to edit
the image, the activity at the top of the task "edu.ksu.santos.benign.editImage" was started i.e. MalActivity.

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

7. Launch *Benign* in the emulator where it was installed.

    Enter username, password and click on the signIn button.

    You will see *HomeActivity*. Click on the *TakePic* button.

    You will see a screen called *CameraActivity*. This is a simulation of a camera. Press back to go back to *HomeActivity*.

    You will see the text *image* appear. Click on the text.

    You will *ImageEditor* with the text *image* appear.

8. Launch *Malicious*.

9. Launch *Benign*.

    You will see *HomeActivity* with the text *image*. Click on the text.

    You will see *Malicious* appear with a login screen that looks like *Benign.LoginActivity*

# References

1.  [Official Android Documentation](https://developer.android.com/guide/topics/manifest/activity-element.html#aff)

2.  [Towards Discovering and Understanding Task Hijacking in Android - Chuangang Ren](https://www.usenix.org/node/190944)
