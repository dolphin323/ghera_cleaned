# Summary
Apps that send sticky broadcasts are vulnerable to leaking sensitive information and data injection attacks

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit

A *sticky broadcast message* is sent to all the registered receivers and is saved by the system after the broadcast is complete so that it can be retrieved and sent to some other broadcast receiver that decides to register for this broadcast in the future.

*Issue:* Since a *sticky broadcast message* is saved in the system, a malicious broadcast receiver can receive the message (intent), change its contents and re-broadcast it. Android will overwrite the old message with the new one and all receivers registered to receive this message will get the updated message.

*Example:* This vulnerability is demonstrated by 3 apps *Benign*, *BenignPartner, and Malicious*. The *MainActivity* in the *Benign* app sends a sticky broadcast message with a phone number in it. The broadcast is meant for *MyReceiver* in *BenignPartner*, which is called when the user interacts with *BenignPartner*. *MyReceiver* retrieves the phone number from the broadcast message and sends an SMS to that number. The *MalActivity* component in the *Malicious* app offers some useful functionality to the user. When the user interacts with it, it retrieves the *sticky broadcast* in the background without the user's knowledge and injects a phone number into it. It then rebroadcasts the message without the user's knowledge. When *MyReceiver* in *BenignPartner* receives this broadcast, it now sends an SMS to the phone number injected by the *Malicious* app instead of sending SMS to the phone number that was in the original *sticky* broadcast message.

*Note:* The secure app in this benchmark is still vulnerable to a malicious app that registers for the broadcast message before the message is sent. However, the secure app does not send sensitive information through a sticky broadcast. Consequently, a malicious app will not be able to steal sensitive information by registering to the broadcast message at a later time. 

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

    *<avd-name>* is obtained from the command listed in 2.

5. Build and install Benign:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install Malicious:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7. Build and install BenignPartner:

    `$ cd BenignPartner`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8. Open *Benign* in the emulator where you installed the app.

9. Open *Malicious* in the emulator where you installed the app.

10. Open *BenignPartner* in the emulator where you installed the app.

     *You will see a message "Sent SMS to 1800-123-0000". This is the phone number injected by the malicious app.*

# References

1.  [Official Android Documentation](https://developer.android.com/reference/android/content/Context.html#sendStickyBroadcast(android.content.Intent))

2.  [Sticky Broadcast Security Vulnerability](https://www.appvigil.co/blog/2015/03/sticky-broadcast-security-vulnerabilities-in-android-apps/)

3.  [Analyzing Inter-Application Communication in Android - Erika Chin](http://dl.acm.org/citation.cfm?id=2000018)
