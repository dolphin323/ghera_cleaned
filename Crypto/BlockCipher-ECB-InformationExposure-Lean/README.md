# Summary
Apps that use a Block Cipher algorithm in ECB mode for encrypting sensitive information are vulnerable to exposing leaking information.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Android apps can use block cipher algorithms to encrypt sensitive information. The *Cipher* API enables developers to specify which block cipher algorithm to use and in which mode.

*Issue:* If the app uses the block cipher algorithm *AES* in *ECB* mode to encrypt sensitive information then an attacker can break the encryption to get access to the sensitive information. An app can explicitly specify that it wants to use *AES* in *ECB* mode or it can specify that it will just use *AES* without explicitly specifying the mode, in which case Android will use the ECB mode by default.

*Example:* This vulnerability is demonstrated by the pair of apps *Benign* and *Malicious*. *Benign* has a reset password feature which allows registered users to change their password. A registered user needs to provide her email id to reset her password. *Benign* takes this email id, encrypts it using *AES* in *ECB* mode and sends it as a token to the email id. The user can then key in this token. When *Benign* receives this token, it decrypts it and verifies whether the email is a registered one. If it is then the user is allowed to reset her password else she is not allowed to reset password. Let us assume that the attacker wants to reset the password of a certain email id - *anniemaes@gmail.com*. The attacker creates a bogus email id - *2222222222222222anniemaes@gmail.com* and gets a token in her email id. Since *Benign* uses *AES* in *ECB* mode the attacker knows that if a particular block is encrypted twice, the source message will be returned. So if *anniemaes@gmail.com* is encrypted twice, the same result is returned. Therefore, the last 32 bytes of the token for *2222222222222222anniemaes@gmail.com* and *anniemaes@gmail.com* will be the same. *Malicious* has access to the token for *2222222222222222anniemaes@gmail.com*. It extracts the last 32 bytes from this token and passes it onto *NewPasswordActivity/Benign*. *NewPasswordActivity/Benign* takes this input, decrypts it and allows *Malicious* to reset the password for *anniemaes@gmail.com* without knowing the token for *anniemaes@gmail.com*.

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

7.  Open *Benign* in the emulator where you installed the app.

8.  Open *Malicious* in the emulator where you installed the app.

9.  In *Malicious* click *Reset Password* button. You should be able to see the *Reset Password* for *anniemaes@gmail.com* in *Benign*.

# References

1. [An Empirical study of Crypto API misuse in Android apps](http://dl.acm.org/citation.cfm?id=2516693)

2. [Official Android Documentation](https://developer.android.com/reference/javax/crypto/Cipher.html)
