# Summary
Apps that export components with permission to perform privileged operations are vulnerable to privilege escalation.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Every Android app runs in its own sandbox with minimal access to system resources. If an app needs to perform a privileged operation like send an SMS, read location etc. it has to explicitly ask the user or Android for permissions. The operation can only be performed if the permission request is granted. Once the permission is granted, the app will always have access to that resource.


*Issue:* If an app has the permission to perform a particular privileged operation and if that operation is performed via an app component that is exported for public consumption, then a malicious app can invoke the component method that performs the privileged operation and make the app perform the operation on the behalf of the malicious app. This is a privilege escalation attack or a confused deputy attack.

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious* apps. *Benign* is a an app that helps its users send SMS. *Benign* has permission to send SMS and has a broadcast receiver *MyReceiver.java* that sends SMS. *MyReceiver.java* is also exported. *Malicious* uses *MyReceiver.java* to send SMS without having the permission to send SMS. So *Malicious* is able to perform a privileged operation like *sending SMS* without having the required permission.


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

6. Start *Benign* in the emulator where it is installed. Make sure in *Settings* that *Benign* has the permission to send SMS.

7. Start *Malicious* in the emulator where it is installed.

8.  Check the inbox of the default messaging app. You will see a message has been sent with the body "I am malicious".

# References

1. Related to [Issue #35](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/35/explore-cve-2014-8610-to-create-new-lean)

2. [Android SMS vulnerability](http://seclists.org/fulldisclosure/2014/Nov/85)
