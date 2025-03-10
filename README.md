This repository documents vulnerabilities that can occur in Android Java apps running on Android 5.1.1 - Android 8.1. It contains benign apps with vulnerabilities related to *Crypto, ICC, Networking, NonAPI, Permission, Storage, System,* and *Web* APIs. Most of the benign apps are accompanied by malicious apps to exploit the vulnerabilities in the corresponding benign app. We have grouped the vulnerabilities based on APIs they stem from, e.g., ICC, Storage.  In each group, each vulnerability benchmark occurs in folder named *X-Y-Z* where X is the capability that causes a vulnerability, Y is an exploit enabled by the vulnerability, and Z is the variation of the benchmark.


# What's New

- Sep 12, 2019: Ghera's second Android security bug report was acknowledged as medium priority, fixed and released as a patch for Android 10 by the Android Security team. Here are a few related links: [Security Bulletin](https://source.android.com/security/bulletin/android-10), [CVE-2019-9463](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2019-9463), and [Acknowledgements](https://source.android.com/security/overview/release-acknowledgements).
- Mar 20, 2019: A new benchmark was added to the Web category that illustrates an unauthorized acces vulnerability stemming from the lack of authorization logic when interacting with a remote server. With this addition, Ghera now has 60 benchmarks.
- Feb 28, 2019: With the addition of 2 new benchmarks to the Web category, Ghera now captures 59 known vulnerabilities.
- Feb 21, 2019: A new Web benchmark that illustrates an unauthorized resource access to a content provider via JavaScript in WebView was added. This brings the total number of Ghera benchmarks to 57.
- Dec 03, 2018: Ghera's first Android security bug report was acknowledged as high priority and fixed by Android Security team.  Here are few related links: [Security Bulletin](https://source.android.com/security/bulletin/2018-12-01.html), [CVE-2018-9548](https://cve.mitre.org/cgi-bin/cvename.cgi?name=CVE-2018-9548), and [Acknowledgements](https://source.android.com/security/overview/acknowledgements#2018).
- Dec 03, 2018: A new ICC benchmark that illustrates a DoS vulnerability stemming from unhandled exceptions was added.  This brings the total number of Ghera benchmarks to 56.
- Oct 16, 2018: Changed automated testing to happen in headless mode.  This should allow the tests to be executed on a server without graphical terminal.
- Aug 27, 2018: With the addition of 2 new lean benchmarks to Networking category, Ghera now captures 55 known vulnerabilities.
- Aug 10, 2018: New wiki posts about 
    - [how path permissions in Android can enable unauthorized access via ContentProvider](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/wiki/Using%20path%20permission%20to%20protect%20Android's%20Content%20Provider%20leads%20to%20unauthorized%20access)
    - [how an app with READ_EXTERNAL_STORAGE permission can write to private external storage of another app](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/wiki/Android%20allows%20apps%20without%20write%20permission%20to%20write%20to%20external%20storage)
- June 25, 2018: Ghera was used to evaluate the effectiveness of free Android app security analysis tools in detecting known vulnerabilities.  You can find the manuscript [here](https://arxiv.org/abs/1806.09059).
- June 14, 2018: With the addition of 12 new lean benchmarks and one new category, Ghera now captures 53 known vulnerabilities.
    - 2 new lean benchmarks in a new category named *NonAPI*. This category captures app vulnerabilities that cannot be addressed programmatically.
    - 10 new lean benchmarks in existing categories: 1 in Crypto, 1 in ICC, 1 in Permission, 1 in Storage, 3 in System, and 3 in Web.
- May 24, 2018: Added support for Android API levels 26 and 27 and dropped support for Android API levels 19 and 21.  Consequently, _ICC/ImplicitIntent-ServiceHijack-Lean_ benchmark was retired as it was specific to API level 19.  Also, this release includes templates for categories and benchmarks to help with contributions to the repository.
- Feb 18, 2018: Fully automated functional testing support was added.  This should help users to automatically test the validity of benchmarks by executing `Misc/functional-test.[sh|ps1]` script.
- Dec 14, 2017: A [new benchmark](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/src/f333c29eee35d2da467ba2c0c84ce49f5ed52a31/ICC/TaskAffinity-LauncherActivity-PhishingAttack-Lean/?at=master) related to task affinity of activities has been added.  More details about the vulnerability/exploit can be found [here](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/wiki/Using%20Task%20Affinity%20to%20launch%20Denial-of-service%20or%20Phishing%20attacks%20in%20Android).
- Nov 08, 2017: Ghera was presented at [PROMISE'17](http://promisedata.org/2017/index.html). Here's the [slide deck](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/downloads/Ghera-Promise17-presentation.pdf).  We got some interesting feedback that we will incorporate shortly.
- Sep 10, 2017: 14 new lean benchmarks were added.
    - 5 new lean benchmarks in 2 new categories: 2 in Network, and 3 in Crypto.
    - 9 new lean benchmarks in existing categories: 2 in ICC, 3 in Storage, 1 in System, and 3 in Web  
- Aug 07, 2017: [Ghera: A Repository of Android App Vulnerability Benchmarks](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/downloads/promise17.pdf) was accepted to be presented at [PROMISE'17](http://promisedata.org/2017/index.html).
- Jun 27, 2017: [Ghera: A Repository of Android App Vulnerability Benchmarks](https://www.researchgate.net/publication/317706386_Ghera_A_Repository_of_Android_App_Vulnerability_Benchmarks) technical report is now available.
- Jun 27, 2017: Repo is public with 25 lean benchmarks :)


# Required Software

1.  Android Studio 3.1.2 or later
2.  JDK 1.8
3.  Python 2.7 or later
4.  Flask 0.12.2


# Notes about Environments

1. **OS X El Capitan (10.11.6)**

    `export PATH=${PATH}:/path/to/Android/sdk:/path/to/Android/sdk/platform-tools:/path/to/Android/sdk/tools`

2. **Ubuntu 17.04**

    `export PATH=${PATH}:/path/to/Android/sdk:/path/to/Android/sdk/platform-tools:/path/to/Android/sdk/tools`

3. **Windows**

    * To avoid **path too long** errors, you should clone    the repo into a top level folder called *ghera*.

        `$ git clone https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks.git ghera`

    * Ensure the absolute path to Android SDK does not include special characters that require quoting *ANDROID_HOME* and *APK_SIGNER* variables.

4. **Android Studio 3.0.1+** Beginning Android Studio 2.3.3, the *android* command was deprecated.  If you are using Android Studio 2.3.3 or later, then use the following commands to set up an emulator instead of the commands listed in the README files under each benchmark.

    1. Set Environment Varibles:

        `$ export PATH=/Applications/Android Studio.app/Contents/jre/jdk/Contents/Home/bin/:$PATH`       
        `$ export PATH=${PATH}:/path/to/Android/sdk/platform-tools:/path/to/Android/sdk/tools/bin`

    2. List targets:

        `$ avdmanager list targets`

    3. List available Android Virtual Devices:

        `$ avdmanager list avd`

    4. Create an emulator:

        `$ avdmanager create avd -n <name> -k <target>`

        *target* is obtained from the command listed in 1. *name* is the name you choose to give to the avd.

    5. Start emulator:

        `$ emulator -avd <avd_name>`

        *avd-name* is obtained from the command listed in 2.

5. **Android Versions** All benchmarks have been tested on Android 5.1 (API Level 22) - Android 8.1 (API Level 27).

6. **Android Studio 3.0.1** Checkout Ghera @[AndroidStudio-3.0.1-End-of-Support](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/src/AndroidStudio-3.0.1-End-of-Support/) if you plan use Ghera with Android Studio 3.0.1.  This version

    - Includes secure versions of apps in benchmarks.
    - Includes support for automatic functional testing.
    - Includes *Permission* related benchmarks.
    - Was tested on API Level 19 thru API level 25 except API level 20.
    - Contained 42 benchmarks in 7 categories.

7. **Android Studio 2.3.3** Checkout Ghera @[AndroidStudio-2.3.3-End-of-Support](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/src/AndroidStudio-2.3.3-End-of-Support/) if you plan use Ghera with Android Studio 2.3.3. This version

    - Does not include secure versions of apps in benchmarks.
    - Does not include support for automatic functional testing.
    - Does not include *Permission* related benchmarks.
    - Was tested on API Level 19 thru API level 25 except API level 20.
    - Contained 40 benchmarks in 6 categories.


# Contributions are welcome!!

Clone the repository and submit pull requests to add/edit new/old benchmarks.  We will review the PR, discuss any changes (if required) with you, and then approve the PR before merging it into the repository.

Every benchmark should be composed of the following artifacts:

1. Source code for a sample *Benign* app with a vulnerability. The sample app should be lean in the sense that it should contain a *minimal* set of features required to exhibit the vulnerability.
2. Source code for a sample *Malicious* app that exploits the vulnerability in the Malicious app.
3. Source code for a sample *Secure* app without the vulnerability in *Benign*.
4. Functional tests to test for presence of vulnerability in *Benign* app and absence of vulnerability in *Secure* app.
5. A README file summarizing the vulnerability, listing affected versions of Android, and describing the vulnerability, a corresponding exploit, and how the example *Benign* and *Malicious* apps demonstrate the vulnerability.
6. A one line summary and the title for benchmark in the top level README under each *benchmark category* e.g. ICC, Storage, System, Web.  If it is a new category, then mention it and provide a brief explanation for the category.
7. Any extra configuration the apps might require should be placed in the *Misc* folder.

Refer to *TemplateCategory/TemplateBenchmark* for a sample of how a benchmark should be structured.


# Attribution

Copyright (c) 2017, Kansas State University

Licensed under [BSD 3-clause "New" or "Revised" License](https://choosealicense.com/licenses/bsd-3-clause/)

Contributors:

1. Joydeep Mitra (Developer) [Creator]
2. Venkatesh-Prasad Ranganath (Advisor + Developer) [Creator]
3. Aditya Narkar (Developer)
4. Nasik Nafi (Developer)
5. Catherine Mansfield (Developer)
