# Summary
Apps that write files into internal storage are vulnerable to directory traversal attack if sanitization checks for file paths are absent.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
Every Android app has its own internal filesystem. Only the app and no one else has read, write, execute permissions over files in this filesystem.

*Issue:* If an app accepts a file path as input from an external source like the user, or another app, or the web, and does not sanitize the input then it is possible to craft a malicious input to obtain access to the app's internal file-system and read, write, execute files in it.

*Example:* We demonstrate this benchmark with the help of two apps *Benign* and *Malicious*. *Benign* has an exported activity *MainActivity*. *MainActivity* accepts file path as input from external sources, writes the file in a folder called *custom* that resides in *Benign's* internal filesystem and returns a failure or success result code depending on whether the file was written successfully. *Malicious* crafts a malicious input like *../../cache/Malicious.txt* and calls *Benign/MainActivity*. *Benign/MainActivity* does not sanitize the input and ends up writing the file in the *cache* folder of its internal memory. This example just shows how easily files can be injected into the internal filesystem of an app if proper sanitization checks are not in place. Such injection can have widespread consequences. For instance, in some situations Android places certain dex files in *app_internal_memory/code_cache*. In the absence of appropriate sanitization checks, the dex files in *app_internal_memory/code_cache* can be overwritten with malicious dex files. When the *Benign* executes these dex files, it will end up executing malicious code.

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

7. Launch *Malicious*. Click the back button.

   Benign app will be launched.

8. Click back button.

   You will see a textview displaying *Data injection succeeded*

9. Check if *malicious.txt* was written in *cache*.

    `$ adb shell run-as edu.ksu.cs.benign`

    This will give you a shell into *Benign's* internal filesystem

    `$ cd cache`

    `$ ls -l`

    You should be able to see *malicious.txt*.

# References

1.  [Android Official Documentation](https://developer.android.com/guide/topics/data/data-storage.html)

2.  [Common Vulnerabilities and Exposures  (CVE-2014-5319)](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2014-5319)

3.  [Mozilla Firefox Directory Traversal Vulnerability](https://bug944374.bmoattachments.org/attachment.cgi?id=8339914)

4.  Related to [Issue #38](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/38/explore-cve-2014-5319-to-create-new-lean)

5.  Related to [Issue #54](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/54/explore-cve-2014-1506-to-add-new-lean-and)
