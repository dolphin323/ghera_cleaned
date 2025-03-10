# Summary
Apps that use *checkPermission* to verify access control are vulnerable
to privilege escalation attacks.  

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Every Android app runs in its own process with a unique Process ID (PID) and a unique User ID(UID).
Components in an Android app can be protected by permissions. If a component is protected by a permission then only components that are granted the permission are allowed to call that component.
A permission can be granted at install time of the app or at run time or both.
If a permission is granted at install time then, components in the same app as that of the *component protected by the permission* are granted the permission by default. But components outside that app will have to request Android to grant permission to it.
However, if the permission is granted at run-time then all have to request Android to grant it permission.
Android provides numerous API methods to help verify permissions at run-time.
One of these methods is *checkPermission(String permission, String pid, String uid)* which evaluates to true if permission has been granted to a
component with PID *pid* and UID *uid*. It returns false otherwise.


*Issue:* A component that uses *checkPermission* to verify access control at run-time, will need to need to obtain the *PID* and *UID* of the
calling component before calling *checkPermission*.
The *Binder* API provides methods *getCallingPID()* and *getCallingUID()* to determine the calling component's PID and UID respectively.
However, both these methods do not always return the calling PID and calling UID.
When an application is started, the system creates a thread of execution for the application called *main*.
The system does not create a separate thread for each instance of a component.
All components that run in the same process are instantiated in the *main* thread, and system calls to each component are dispatched from that thread.
If *Binder.getCallingPID()* and *Binder.getCallingPID()* are called from the *main* thread, they do not return the PID and UID of the process in which the calling component is running. Instead, they return the PID and UID of the process in which the *protected component* is running in.
In such a scenario, if the process in which the *protected component* is running in is granted permission,
then *checkPermission* will always evaluate to true. A malicious component can exploit this vulnerability to gain access to the *protected component*.

*Example:* This vulnerability is demonstrated by *Benign* and *Malicious*. The benign app has a service *MyService* that performs a sensitive operation.
The app's *MainActivity* requests the user for permission to start *MyService*.
If the user grants the permission then *MainActivity* starts the service. *Malicious* is a malicious app that also attempts to start *Benign/MyService*.
The attempt will be successful because *Benign/MyService* uses *checkPermission("santos.benign.permission",Binder.getCallingPid(),Binder.getCallingUid())* which evaluates to true because permission was already granted to *Benign/MainActivity* before. Therefore, the malicious app can successfully perform a privilege escalation attack.


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

6. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

7. Launch *Malicious* in the emulator where it is installed.

    An activity should display “Activity started from service.” message.

# References

1.  [Official Android Documentation](https://developer.android.com/reference/android/content/Context.html#checkPermission(java.lang.String,%20int,%20int))

2.  [How to get CallingUid() and CallingPid()](https://developer.android.com/reference/android/os/Binder.html#getCallingPid())

3.  [Processes and Threads in Android](https://developer.android.com/guide/components/processes-and-threads.html)
