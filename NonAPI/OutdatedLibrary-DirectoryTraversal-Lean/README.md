# Summary
Apps using outdated libraries containing vulnerabilities may themselves be vulnerable.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android apps can use external library to incorporate some functionality provided by others or to use same functionaliy across multiple apps. Android supports two types of library - Java Archive (JAR) and Android Archive (ARR). When these archives are added to android project they get DEXed and included in .apk file. Generally library owners provide continuous supports by improving their code and functionality through new releases. Every app should use updated version of any library.

*Issue:* When an app uses an external library, it may use older versions of the library which may contain vulnerabilities. As a result, the app may exhibit vulnerabilities.

*Example:* This vulnerability is demonstrated by *Benign* app. The app uses version 1.0 of a custom library that provides an android activity component named *WriteActivity* to write data to a file. *WriteActivity* may cause directory traversal vulnerability due to not sanitizing the filename correctly. *Malicious* sends an intent with a filename "../../cache/malicious.txt" which contains "../../". If this situation is not handled correctly, the *Malicious* app may able to write in the internal cache of *Benign* app.  


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

5. Build version 1.0 and 1.1 of the library:

    `$ cd Library/version1.0 ; gradle assembleDebug`

    `$ cd Library/version1.1 ; gradle assembleDebug`

6. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk. Build will fail without this.

    `$ ./gradlew installApi[VERSION]Debug`

7. Launch *Malicious* in the emulator where it is installed. Click the button "Click for injection".

    The activity should display �Data injection succeeded.� message.

# References

1.  [Security Smells in Android](http://scg.unibe.ch/archive/papers/Ghaf17c.pdf)
