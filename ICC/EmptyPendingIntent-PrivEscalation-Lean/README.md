# Summary
Apps that use a Pending Intent with base intent empty are vulnerable to leaking privilege.

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


*Issue:* If a malicious app receives a pending intent whose base action is empty then the malicious app
can set an action and execute it in the context of the benign app that sent the pending intent. For example,
if the benign app has permission to write files to some location and it mistakenly sends a pending intent
whose base action is empty, then the malicious app can write files into that location without requesting
permissions from the user.

*Example:* This vulnerability is demonstrated by three apps --  *Benign, BenignPartner, and
Malicious*. *Benign* is a benign app that sends an empty pending intent to *BenignPartner*
which has a broadcast receiver *MyReceiver.java* that takes the pending intent and starts a service *MyService.java*
in *Benign* that performs an operation. *Malicious* app
has a broadcast receiver *MyReceiver.java* that intercepts the pending intent sent from *Benign*
and starts an internal service in *Benign* that performs some sensitive operation.
This service is not exported and is meant for internal use within the benign app. However, because of
the empty pending intent intercepted by the malicious broadcast receiver, *Malicious* app can escalate
its own privilege and can start *MySensitiveService* even though its not supposed to.

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

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install *BenignPartner*:

    `$ cd BenignPartner`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7.  build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8.  First open *BenignPartner* and *Malicious* in no particular order. Then open *Benign* in the emulator and click the button.

9.  You will see a message "Sensitive Activity Visible"

    This means the *MySenitiveService* was called successfully from *Malicious*.

# References

1.  [Android Settings Application privilege leakage vulnerability](http://seclists.org/fulldisclosure/2014/Nov/81)

2. [Official Android Documentation](https://developer.android.com/reference/android/app/PendingIntent.html)
