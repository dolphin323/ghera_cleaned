# Summary
Apps that have components that accept implicit intents but do not handle the implicit intents properly are vulnerable to unauthorized access.

# Versions of Android where the vulnerability is possible
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Android allows developers to create components that can be accessed by other apps via implicit intent.

*Issue:* If a component accepts implicit intents then it can be accessed by malicious apps. If such components perform sensitive operations then they can be used to perform sensitive operations by malicious apps without the user's knowledge and consent.

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious*. *Benign* has an activity, *SensitiveActivity* that returns sensitive information to its callers. *SensitiveActivity* can be accessed by any component that sends it an implicit intent with the action string set to  *"edu.ksu.cs.benign.SENS_ACTIVITY_ACTION"*. *Malicious* has an activity that crafts an implicit intent with the action string set to *"edu.ksu.cs.benign.SENS_ACTIVITY_ACTION"*. *Malicious* sends this intent to *Benign/SensitiveActivity* when user clicks on the button *Get Sensitive Information from Benign* in *Malicious*. When the user clicks on the back button, *SensitiveActivity* returns sensitive information to *Malicious*.   

# Steps to build the sample apps and to exploit the vulnerability

1. List targets:

    `$ avdmanager list targets`

2. List available Android Virtual Devices:

    `$ avdmanager list avd`

3. Create an emulator:

    `$ avdmanager create avd -n <name> -k <target>`

    *<target>* is obtained from the command listed in 1. *<name>* is the name you choose to give to the avd

4. Start emulator:

    `$ emulator -avd <avd_name>`

    *<avd-name>* is obtained from the command listed in 2

5. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk

    `$ ./gradlew installApi[VERSION]Debug`

7.  Launch *Malicious* and click on the button and then press back

8.  You will see a message - *sensitive information* displayed

# References

1. [Issue #37](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/37/explore-cve-2014-5320-to-create-new-lean)
