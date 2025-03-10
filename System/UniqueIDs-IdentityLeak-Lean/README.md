# Summary
Apps that use device identifiers to uniquely identify app instances are vulnerable to exposing device-specific sensitive information.

# Android versions affected
Tested on Android 4.4 - Android 7.1

# Description of vulnerability and corresponding exploit
An app can collect device identifiers through APIs like TelephonyManager and BluetoothAdapter. 
Such identifiers are often used by app developers to uniquely identify app instances.

*Issue:* Collecting device identifiers could lead to exposing sensitive data related to the 
device which can be used by malicious actors to track the device.

*Example:* This vulnerability is demonstrated by *Benign*  and *Malicious* apps. *Benign* app
obtains the device id and saves it in the device's external storage. *Malicious* reads the 
device id from the file resulting in sensitive data exposure.

*Note:* The *Secure* app uses a random UUID to identify app instances as opposed to a device ID.
With this scheme, if the random UUID is leaked, then only the app may be affected leaving the
device unaffected.


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

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk. Build will fail without this.

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk. Build will fail without this.

    `$ ./gradlew installApi[VERSION]Debug`

7. Launch *Malicious* in the emulator where it is installed.

    An activity should display �DeviceId : <ID>� message.

# References

1.  [Official Android Documentation](https://developer.android.com/reference/java/util/UUID.html#randomUUID())

2.  [How to retrieve an Unique ID to identify Android devices ?](https://medium.com/@ssaurel/how-to-retrieve-an-unique-id-to-identify-android-devices-6f99fd5369eb)

3.  [Security Smells in Android](http://scg.unibe.ch/archive/papers/Ghaf17c.pdf)
