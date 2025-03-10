# Summary
Apps that use a Pending Intent with implicit base intent are vulnerable to Intent Hijack attacks.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
A pending intent is used by an application to enable a third party app to perform an action on its behalf
in the future. An example of this is the calendar app which sends a reminder 30 minutes before a meeting
starts. In this case the calendar app will create a pending intent with the reminder and the time the reminder
needs to be sent. The calendar app will send this pending intent to an app that can send notifications, which will perform
the action on behalf of the calendar app, at the time specified in the pending intent. Note that the Android system
will grant the app that can send notifications the same permissions as that of the calendar app while it
performs the prescribed action.


*Issue:* To create a pending intent one has to specify a base intent which is the action that will be
performed in the future. This base intent can be an explicit or implicit intent to an activity, service,
broadcast receiver. If this base intent is an implicit intent then when the pending intent is performed,
a malicious app can intercept the implicit intent. If the implicit intent has sensitive information in it
then it will be leaked.

*Example:* This vulnerability is demonstrated by *Benign,BenignPartner and Malicious*. *Benign* sends a pending intent to *BenignPartner*.
The base intent inside the pending intent is an implicit intent
to *Benign/.../MyService.java* and contains sensitive information like *password*.
*BenignPartner* has a broadcast receiver *MyReceiver.java* that takes the pending intent and starts a service *MyService.java*
in *Benign* that performs an operation. However, When the action in the pending intent
is performed from *BenignPartner*, the malicious service *Malicious/.../MalService* intercepts the
implicit intent and steals the password.


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

6. Build and install *BenignPartner*:

    `$ cd BenignPartner`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7. Open *Benign* in the emulator the app is installed in and click on the     
   Button.

8. Check log:
    `$ adb logcat -d | grep "PendingIntent"`

       You will see a message "Do something trivial"

       This means that *MyService* was called in the Benign app.

9. Build and install *Malicious*:

    $ cd Malicious

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

10. Open *Malicious* and *BenignPartner* in no particular order. Then open *Benign* in the emulator and click the button.

11. You will see *password* displayed in *Malicious*

# References

1. [Official Android Documentation](https://developer.android.com/reference/android/app/PendingIntent.html)
