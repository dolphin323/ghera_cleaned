# Summary
Apps that store encryption keys in the source code are vulnerable to forgery attacks and information leaks.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Android apps can use block cipher algorithms to encrypt sensitive information using the *Cipher* API.

*Issue:* The *Cipher* API expects a key. If such a key used for encryption/decryption is saved in the source code, then an attacker can get access to it and abuse it.

*Example:* This vulnerability is demonstrated by the pair of apps *Benign* and *Malicious*. *Benign* offers a two-step reset password feature: 1) given an email id, the app emails a token to the email id and 2) the user is allowed to reset the password if she provides the emailed token to the app.  *Benign* uses *AES* cipher in *GCM* mode to generate a token from the email id.  The secret key used with AES to generate the token is stored in the code base of *Benign*.  An attacker obtains this secret key from the code base and creates *Malicious* app that uses the key to generate a token for a given email id and communicates the same to the *Benign* app.  Now, the attacker can use *Malicious* and *Benign* apps to reset the password of the any email id.

Note: The above situation could have been limited by controlling the apps that are serviced by *Benign*.  Even so, publicly accessible key could be used with other parts of the system related to *Benign* app (e.g., web services) to mount similar attacks.


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

8.  Open *Malicious* in the emulator where you installed the app.

9.  In *Malicious*, type in an email address and click *Reset Password* button. You should be able to see the *Reset Password* activity in *Benign* for the given email adress.

# References

1. [An Empirical study of Crypto API misuse in Android apps](http://dl.acm.org/citation.cfm?id=2516693)

2. [Official Android Documentation](https://developer.android.com/reference/javax/crypto/Cipher.html)
