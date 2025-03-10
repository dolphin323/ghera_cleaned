# Summary
Apps that export components that are granted permission by Android to perform privileged operations are vulnerable to privilege escalation.

# Android versions affected
Tested on Android 4.4

# Description of vulnerability and corresponding exploit
Every Android app runs in its own sandbox with minimal access to system resources. If an app needs to perform a privileged operation like send an SMS, read location etc. it has to explicitly ask the user or Android for permissions. The operation can only be performed if the permission request is granted. Once the permission is granted, the app will always have access to that resource.


*Issue:* If an app has the permission to perform a particular privileged operation and if that operation is performed via an app component that is exported for public consumption, then a malicious app can invoke the component method that performs the privileged operation and make the app perform the operation on the behalf of the malicious app. This is a privilege escalation attack or a confused deputy attack.

A system app - *com.android.mms* in Android has a broadcast receiver - *SmsReceiver* that is exported.

*Example:* This vulnerability is demonstrated by *Malicious* and a system app *com.android.mms* pre-installed. The *com.android.mms* has a broadcast receiver *SmsReceiver* that sends SMS. *Malicious* writes a message to the draft folder of the device's inbox. It then reads that message and sends it to *SmsReceiver* via an intent. When *SmsReceiver* receives the intent, it sends the message without the user knowing because it already has permission to send messages. This way *Malicious* is able to send messages without the *SEND_SMS* permission.


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


5. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk. Build will fail without this.

    `$ ./gradlew installApi[VERSION]Debug`

6. Create a draft message with the body "another test sms1!" in the emulator's default messaging app.

7. Launch *Malicious* in the emulator where it is installed.

8.  Check the inbox of the default messaging app. You will see a message has been sent with the body "another test sms1!".

# References

1. Related to [Issue #35](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/35/explore-cve-2014-8610-to-create-new-lean)

2. [Android SMS vulnerability](http://seclists.org/fulldisclosure/2014/Nov/85)
