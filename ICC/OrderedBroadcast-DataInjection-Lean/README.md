# Summary
Apps that contain a broadcast receiver registered to receive ordered broadcasts are vulnerable to receive data from a malicous broadcast receiver with higher priority.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
A Broadcast Receiver can register to receive *ordered* broadcasts. When an ordered broadcast is sent then all broadcast receivers registered to receive that broadcast respond to it in the *order of priority*. The receivers with higher priority (number) respond first and forward it to receivers with lower priority (number), e.g., receiver with priority 1 will receiver the request before a receiver with priority 0.  If multiple receivers have the same priority then the order is arbitrary.

*Issue:* Since higher priority receivers respond first and forward it to lower priority receivers, a malicious receiver with high priority can intercept the message change it and forward it to lower priority receivers.

*Assumption:* The exploited broadcast receiver is the last in the pipeline of ordered broadcast receivers.

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious*. Benign app helps users to dial a phone number without bothering about the international code for that number.  Benign apps registers *FormatOutgoingCallReceiver* to receive an intent from the system whenever an outgoing call is placed.  By default, the priority of *FormatOutgoingCallReceiver* is set to 0 by the system.  This receiver pre-pends +1 to the out-going call if the call belongs to the US.  *MalOutgoingCallReceiver* broadcast receiver in Malicious app is also registered to receive an intent from the system when an outgoing call is placed.  This receiver is registered with a higher priority of 1.  When a call is placed, MalOutoingCallReceiver intercepts the call and replaces the number with a number of its choice. Consequently, any call made from the benign app will results in call to a number chosen by Malicious app. In fact even if the user dials a number from the original phone app of her device, the number chosen by the malicious app will get dialed.

*Disclaimer:* If *MalOutgoingCallReceiver* uses the lowest priority and sets itself up as the last receiver (violating the assumption), then it can modify the data in the broadcast; hence, *there is no way to protect against this exploit.*

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

6.  Go to Settings and make sure that *Benign* has *Phone* permission

7.  Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8.  Launch *Malicious* and grant *Phone* permission when asked

9.  Launch *Benign* and grant *Phone* permission when asked. Click on *call* button.

    You will see that when a call is placed, the call is always routed to "7110006217" which is a phone number hard-coded in the malicious app.

    Note that *Malicious* needs the *Phone* permission to successfully exploit *Benign*. However, it is possible to build functionality into the *Malicious* app that will convince the user to grant *Malicious* the necessary permissions. For Android 5.0 and before this permission will be granted as soon as *Malicious* is installed. So, for these versions of Android, it is even more likely that the permission will be granted.

# References

1.  [Processing Ordered Broadcasts - Bruno Alburqueque](https://android-developers.googleblog.com/2011/01/processing-ordered-broadcasts.html)

2.  [Official Android Documentation](https://developer.android.com/reference/android/content/Context.html#sendOrderedBroadcast(android.content.Intent, java.lang.String))

3.  [Analyzing Inter-Application Communication in Android - Erika Chin](http://dl.acm.org/citation.cfm?id=2000018)
