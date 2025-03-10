# Summary
Apps that do not use *selectionArgs* to construct *select, update, and delete* queries are vulnerable to SQL injection attacks.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Android allows apps to save data in a local database called SQLlite. SQL queries are used to interact with the SQLlite database.

*Issue:* If the app does not parameterize the queries it uses to interact with the database then it is vulnerable to SQL injection attacks.

*Example:* We demonstrate this benchmark with the help of two apps *Benign* and *Malicious*. *Benign/MyContentProvider* uses a non-parameterized query to check if a particular *username/password* is in the database or not. *Benign/MiddleActivity* is exported which uses  *Benign/MyContentProvider's* non-parameterized query. *Malicious* uses a specially crafted query to retrieve all the *username/password* pairs in the table.

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

7.  Launch *Benign*.

8.  Launch *Malicious*. Press *Data injection* button. It will start *Benign's Middle Activity*.

9. Check in log for all the *username/password pairs*

    `$ adb logcat -d | grep "Malicious"`

# References

1.  [Android Official Documentation](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html#compileStatement(java.lang.String))

2.  Related to [Issue #36](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/36/explore-cve-2014-8507-to-create-new-lean)
