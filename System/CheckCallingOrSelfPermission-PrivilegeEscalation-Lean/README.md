# Summary
Apps that use *checkCallingOrSelfPermission* to verify access control are vulnerable
to privilege escalation attacks.  

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Every Android app runs in its own process with a unique Process ID (PID) and a User ID(UID). Components in an Android app can be protected by permissions. 
If a component is protected by a permission then only components that are granted the permission are allowed to call that component. 
A permission can be granted at installation time or at run time or both.
If a permission is granted at compile time then, components in the same app as that of the *component protected by the permission* are granted 
the permission by default. But components outside that app will have to request Android to grant permission to it. 
However, if the permission is granted at run-time then all components have to request Android to grant them permission. 
Android provides numerous API methods to help verify permissions at run-time. One of these methods is *checkCallingOrSelfPermission(String permission)* which 
returns true if permission has been granted to a component and returns false otherwise.


*Issue:* A component that uses run-time permission checking via *checkCallingOrSelfPermission* for access control, grants permission to **all** components if it grants permission even once to a component that is in the same app as that of itself. 
If *checkCallingOrSelfPermission* is used to protect a component that performs a sensitive operation then a component in a malicious app 
can escalate its privilege and access the component.  

*Example:* The vulnerability is demonstrated by *Benign* and *Malicious*. The benign app has a service *MyService* that performs a sensitive operation. 
The app's *MainActivity* requests the user for permission to start *MyService*. 
If the user grants the permission then *MainActivity* starts the service. 
*Malicious* is a malicious app that also attempts to start *Benign/MyService*. 
The attempt will be successful because *Benign/MyService* uses *checkCallingOrSelfPermission* which evaluates to true because 
permission was already granted to *Benign/MainActivity* before. Therefore, the malicious app can successfully perform a privilege escalation attack.


# Steps to build the sample apps and to exploit the vulnerability

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

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7. Launch *Malicious* in the emulator where it is installed. 

    An activity should display “Activity started from service.” message.

# References

1.  [Official Android Documentation](https://developer.android.com/reference/android/content/Context.html#checkCallingOrSelfPermission(java.lang.String))
