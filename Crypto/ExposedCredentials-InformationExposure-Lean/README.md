# Summary
Apps that store encryption keys without any protection parameter in a keystore accessible by other apps are vulnerable to information exposure.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Android apps can use keystore to store encryption keys. Keystore provides `setEntry` API to store a key entry. Along with the alias and the key, setEntry API takes an instance of ProtectionParameter as argument to protect the contents of the entry.

*Issue:* If an entry is stored in a keystore with `null` as the `ProtectionParameter` argument to `setEntry` API, then any app with access to the keystore and aware of the alias can retrieve the entry. This issue can be mitigated by using a password-based implementation of `ProtectionParameter`.

*Example:* This vulnerability is demonstrated by the pair of *Benign* and *Malicious* apps. *Benign* stores a secret key in a keystore using `setEntry` API with `null` as the `ProtectionParameter` argument. This keystore is stored in the external file system and is accessible by other apps. With the knowledge of the alias of the secret key, *Malicious* app accesses the keystore and retrieves *Benign* app’s secret key.


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

8.  Launch *Benign* in the emulator where you installed the app.

8.  Launch *Malicious* in the emulator.

	You should see the message "Successfully retrieved the key.".

# References

1. [Security Smells in Android](http://scg.unibe.ch/archive/papers/Ghaf17c.pdf)
