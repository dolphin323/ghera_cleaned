# Summary
Apps that use reflection to dynamically load fragments into an activity are vulnerable to fragment injection attacks.

# Versions of Android affected
Tested on Android 4.4 - Android 7.1

# Description of the vulnerability and the corresponding exploit
Fragments can be loaded into an activity dynamically using Java Reflection with just the fragment name.

*Issue:* An activity that accepts fragment names as input from other components, and loads the fragment dynamically into the activity is vulnerable to executing a fragment provided as input by a malicious app on the device.

*Example:* This vulnerability is demonstrated by the pair of apps *Benign* and *Malicious*. *Benign* has an exported activity, *MainActivity* that accepts fragment names from other components and loads the fragments dynamically using reflection. Since *MainActivity* is an exported component, it should be cautious in loading fragments (i.e., not load fragments such as *EmailFragment* in Benign app that performs sensitive operations) but it isn't cautious. So, *Malicious* app can invoke *Benign/MainActivity* to load *EmailFragment* and send email on behalf of *Malicious* app.

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

    *avd-name* is obtained from the command listed in 2.

5. Build and install Benign:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install Malicious:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7.  Open *Malicious* in the emulator where you installed the app

8.  Click *Inject Fragment*

9.  You will see *Benign* show up

10. Click on *Load Fragment*

11. You will see a message "Email sent successfully"

# References

1. [Fragment Injection](https://securityintelligence.com/new-vulnerability-android-framework-fragment-injection/)

2. [Creating and using Fragments](https://github.com/codepath/android_guides/wiki/Creating-and-Using-Fragments)
