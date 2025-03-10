# Summary
Apps that use a block cipher algorithm in CBC mode  with a non random Initialization Vector to encrypt sensitive information are vulnerable to leaking sensitive information.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Android apps can use block cipher algorithms to encrypt sensitive information. The *Cipher* API enables developers to specify which block cipher algorithm to use and in which mode.

*Issue:* If the app uses the block cipher algorithm *AES* in *CBC* mode then it needs to provide an Initialization Vector (IV) which will be *XOR-ed* with the information that needs to be encrypted. If the provided IV is not random then encrypting a particular piece of information with a symmetric key will yield the same result every time encryption is applied to that information with the same symmetric key. An attacker with access to the encrypted information can then infer the actual information by just analyzing and comparing the encrypted results.

*Example:* This vulnerability is demonstrated by the pair of apps *Benign* and *Malicious*. *Benign* has an *exported* content provider *GradesContentProvider* that provides two methods *insert* and *query*. The *insert* method creates a string of the form *name,grade* and encrypts the string before saving it in the database. The *query* method returns a list of encrypted strings (of the form *name,grade*). Let us assume that an attacker knows the names of all students saved in *Benign's* storage and all student have unique names. The attacker's goal is to determine the grade of a particular student X. The attacker uses *Malicious* for this. The attacker guesses a grade for X and from *Malicious* calls *GradesContentProvider.insert(name, grade)*. The attacker then calls *GradesContentProvider.query()* to retrieve the list of encrypted strings of the form *(name,grade)*. Recall that the IV used for encrypting is not random. This means that the result of encrypting the same string twice will be same. An attacker can use this property to look for duplicate entries in the list she retrieved from *GradesContentProvider.query()* and infer if her guess was correct.

*Note: This benchmark also uses a constant key embedded statically in the source code of Benign. This is also a security vulnerability since anyone with access to the source code can extract the secret key from it.*

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

8. Click on *Add Student* to add a student and her grade.

8.  Open *Malicious* in the emulator where you installed the app.

9.  In *Malicious* enter the same *Student Name* and *Grade* you used in 8.

10. Click *Verify*. You will see a message *"Congratulations! you guessed the right grade"*.

11. You can also check the log to see the list of encrypted values. If you find duplicate entries you will know that your guess was right.

    `$ adb logcat -d | grep "Malicious"`

# References

1. [An Empirical study of Crypto API misuse in Android apps](http://dl.acm.org/citation.cfm?id=2516693)

2. [Official Android Documentation](https://developer.android.com/reference/javax/crypto/Cipher.html)
