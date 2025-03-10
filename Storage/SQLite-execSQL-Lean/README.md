# Summary
Apps that use *SQLiteDatabase.execSQL()* method to construct non-parameterized SQL queries are vulnerable to SQL injection attacks.

# Versions of Android affected
Tested on Android 5.1.1 - 8.1

# Description of the vulnerability and the corresponding exploit
Android allows apps to save data in a local database called SQLlite. SQL queries are used to interact with the SQLlite database. *execSQL()* method of SQLlite allows developer to execute queries such as INSERT/DELETE/UPDATE which does not return any result.

*Issue:*  If an app uses inputs (e.g., user input via UI or data from web) to create a SQL statement to be executed via *execSQL()* method, then the app may be vulnerable to *SQL-injection attack*.

*Example:* This benchmark demonstrates this vulnerability by using two apps: *Benign* and *Malicious*. *Benign* app takes an input from another app to update the value of a given key. *MiddleActivity* in *Benign* app uses this input as (without sanitizing it) to construct a SQL statement that is executed via *execSQL()*. *Malicious* app exploits this behavior of *Benign* app by providing the value *"666 where clause 1=1"* to change the value of all keys in the target table to 666.

Note: To make sure our tests are consistent, we introduced *Truncate Table* button and *Insert Table* buttons. Pressing these buttons will clear all entries from the table and add two entries in the table, respectively. So, we can always start with an empty table and then add two entries.

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

6. Launch *Benign* and click *Truncate Table* and then *Insert Data* button.   

7. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8.  Launch *Malicious* and click on *Data Injection* button.

    It will start *Benign's Middle Activity*. You will see updated pairs of *key/value* displayed on the screen.

9. Click back button.

    You will see *Malicious* activity with textview set to *Success*.

10. Check in log for all the *key/value pairs*

    `$ adb logcat -d | grep "Malicious"`

# References

1.  [Android Official Documentation](https://developer.android.com/reference/android/database/sqlite/SQLiteDatabase.html#compileStatement(java.lang.String)

2.  [ISSTA-15](https://researcher.watson.ibm.com/researcher/files/us-otripp/issta15.pdf)
