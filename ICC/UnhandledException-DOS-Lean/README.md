# Summary
Apps that do not handle *unchecked exceptions* are vulnerable to *Denial of Service* attack.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
If an app has code which might throw exceptions under some circumstances and an app developer does not handle the exception, then the app is vulnerable to *Denial of Service* attack.

*Issue:* If an app has code which might throw exceptions under some circumstances, then an app developer is responsible to handle them explicitly. *Malicious* app may try to execute the part of the app which throws the exception but is not handled by an app developer by passing some arbitrary arguments if needed.

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious* apps. *Benign* app is using string functions to perform some operations on incoming strings. If passed string is null, then app may crash with *NullPointerException*. *Malicious* app passes null as a string, resulting in the crash of *Benign* app.


# Steps to build the sample apps and to exploit the vulnerability

1. List targets:

    `$ avdmanager list targets`

2. List available Android Virtual Devices:

    `$ avdmanager list avd`

3. Create an emulator:

    `$ avdmanager create avd -n <name> -k <target>`

    *<target>* is obtained from the command listed in 1. *<name>* is the name you choose to give to the avd.

4. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk. Build will fail without this.

    `$ ./gradlew installApi[VERSION]Debug`


5. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk. Build will fail without this.

    `$ ./gradlew installApi[VERSION]Debug`

6. Start *Malicious* in the emulator where it is installed and click on *DOS* button.

   You will see a *Benign* app crashing.


# Note

Currently, the automated tests for this benchmark fail to execute due to limitations of Android SDK.  For more information, refer to the [bug report](https://issuetracker.google.com/issues/112532187) or contact the Ghera team.


# References

1. Related to [Issue #35](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/35/explore-cve-2014-8610-to-create-new-lean)

2. [Android SMS vulnerability](http://seclists.org/fulldisclosure/2014/Nov/85)
