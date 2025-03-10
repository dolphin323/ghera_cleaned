# Summary
Apps with low priority activity are vulnerable to Activity Hijack attacks.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
An activity in Android can be started by passing the package name and the class name to an intent or by passing a string identifier. This string identifier is not unique. It can resolve
to more than one activity. When an activity with a string identifier is started and it is the only activity with that string identifier then the activity is started. However, if the string
identifier is used for more than one activity then the user is asked to choose the activity she wants to start from a list of activities with that string identifier. Activities with higher
priority are displayed first.  


*Issue:* If a benign activity uses an *intent-filter* to specify a string identifier then a malicious activity could use the same string identifier in its *intent-filter*. The problem is
exacerbated when the malicious activity has a higher priority than the benign activity. An activity can choose a priority by specifying a positive integer in the *android:priority* field in
the manifest file. If an activity does not set the priority explicitly then the Android system assigns it a priority of 0. When a string a identifier resolves to multiple activities, a list
of activities is displayed to the user for the user to choose. If a malicious activity has a higher priority then it will appear before the benign activity making it highly likely for the
user to choose the malicious activity over the benign activity.

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious*. The manifest.xml file in Benign has *ImageEditor* activity with priority
set to 5. The Malicious app has an activity MalActivity with priority 10. Both activities have the same *action* or string identifier *edu.ksu.cs.benign.imageEditor*. This means
that MalActivity can be started instead of *ImageEditor*. In this case MalActivity hijacks the image sent to ImageEditor. If the image contains sensitive information then it is leaked via
MalActivity. For this to work MalActivity will have to look very similar to ImageEditor and should provide the same functionality so that the user chooses MalActivity over ImageEditor
and does not understand that her images are being leaked.


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

5. Build and install Benign:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install Malicious:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7.  Open *Benign* in the emulator where you installed the app. Click on "Take Picture" button. Then click on the *Done* button and then click on the text *image*.

    You will see that a chooser pops up showing *Malicious* before *Benign*.

9.  If you click on Malicious, *image* will be displayed in Malicious; hence, the image is leaked to Malicious.

# References

1.  [Analyzing Inter-Application Communication in Android - Erika Chin](http://dl.acm.org/citation.cfm?id=2000018)

2. [Official Android Documentation](https://developer.android.com/guide/topics/manifest/intent-filter-element.html#priority)
