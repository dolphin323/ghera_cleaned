# Summary
Apps that us constant salt for Password-based Encryption are vulnerable to information exposure via dictionary attacks.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Password-based Encryption is an encryption technique where the key used to encrypt/decrypt data is derived from a user provided password. The password is supplemented by a salt and an integer and is hashed by a secret key. The result is prepended to a salt and an integer and is again hashed. This process can be repeated any number of times to obtain the symmetric key.

*Issue:* If the salt being used is constant then the symmetric key can be re-created by an attacker if the attacker has access to the password. An attacker can precompute a dictionary of symmetric keys for known passwords and use them to decrypt information.

*Example:* This vulnerability is demonstrated by the pair of apps *Benign* and *Malicious*. *Benign* asks its users for a valid passphrase. If the user has the correct passphrase she is allowed to enter student name and grade which are then encrypted and saved. *Benign* uses a *constant salt* to derive a symmetric key from the passphrase. *Malicious* has a list of well-known pass-phrases which it uses along with the constant salt to pre-compute a dictionary of symmetric keys. In this example we assume that a user in *Benign* uses a passphrase - *password* to derive the symmetric key. The passphrase *password* exists in *Malicious's* dictionary. As a result *Malicious* uses the dictionary to derive the same symmetric key. *Malicious* successfully decrypts all of the encrypted information saved by *Benign* for that user.

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

7. Open *Benign* in the emulator where you installed the app.

8. Enter passphrase *password* and click on *Add Student* to add a student and her grade.

9.  Open *Malicious* in the emulator where you installed the app.

10.  In *Malicious* click on *Get Student Info*

11. Check the log to see the list of decrypted values.

    `$ adb logcat -d | grep "Malicious"`

# References

1. [An Empirical study of Crypto API misuse in Android apps](http://dl.acm.org/citation.cfm?id=2516693)

2. [PBE in Android](https://developer.android.com/reference/javax/crypto/spec/PBEKeySpec.html)

3. [Official Android Documentation](https://developer.android.com/reference/javax/crypto/Cipher.html)
