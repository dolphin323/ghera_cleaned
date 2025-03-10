# Summary
Provide summary of the vulnerability captured by the benchmark

# Android versions affected
Tested on Android <X> - Android <Y>

# Description of vulnerability and corresponding exploit
Provide the context for the vulnerability captured by the benchmark.  Specifically, describe the features, APIs, and workflow that lead to the creation of the vulnerability.

*Issue:*  In terms of the above featues, describe the vulnerability and how it is created.

*Example:*  Describe how the vulnerability is demonstrated by the apps in the benchmark. 


# Steps to build the sample apps and to exploit the vulnerability

Provide instructions to use the apps to demonstrate the vulnerability

1. list targets:

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

    `$ ./gradlew installAPI[VERSION]`

6. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installAPI[VERSION]`

7. Launch *Malicious* in the emulator where it is installed. 

    An activity should display “Activity started from service.” message.


# References

1.  Provide canonical references to sources that reported the vulnerability.
