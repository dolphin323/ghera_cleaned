This repository documents vulnerabilities that can occur in native Android apps running on Android 4.4 - Android 7.1. It contains benign apps with vulnerabilities related to *Crypto, ICC, Networking, Storage, System and *Web* APIs. Most of the benign apps are accompanied by malicious apps to exploit the vulnerabilities in the corresponding benign app. We have grouped the vulnerabilities based on APIs they stem from, e.g., ICC, Storage.  In each group, each vulnerability benchmark occurs in folder named *X-Y-Z* where X is the capability that causes a vulnerability, Y is an exploit enabled by the vulnerability, and Z is the variation of the benchmark.


# What's New

- Feb 18, 2018: "Fully automated functional testing support was added.  This should help users to automatically test the validity of benchmarks by executing `functional-test.[sh|ps1]` script."
- Dec 14, 2017: A [new benchmark](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/src/f333c29eee35d2da467ba2c0c84ce49f5ed52a31/ICC/TaskAffinity-LauncherActivity-PhishingAttack-Lean/?at=master) related to task affinity of activities has been added.  More details about the vulnerability/exploit can be found [here](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/wiki/Using%20Task%20Affinity%20to%20launch%20Denial-of-service%20or%20Phishing%20attacks%20in%20Android).
- Nov 08, 2017: Ghera was presented at [PROMISE'17](http://promisedata.org/2017/index.html). Here's the [slide deck](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/downloads/Ghera-Promise17-presentation.pdf).  We got some interesting feedback that we will incorporate shortly.
- Sep 10, 2017: 14 new lean benchmarks were added.
    - 5 new lean benchmarks in 2 new categories: 2 in Network, and 3 in Crypto.
    - 9 new lean benchmarks in existing categories: 2 in ICC, 3 in Storage, 1 in System, and 3 in Web  
- Aug 07, 2017: [Ghera: A Repository of Android App Vulnerability Benchmarks](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/downloads/promise17.pdf) was accepted to be presented at [PROMISE'17](http://promisedata.org/2017/index.html).
- Jun 27, 2017: [Ghera: A Repository of Android App Vulnerability Benchmarks](https://www.researchgate.net/publication/317706386_Ghera_A_Repository_of_Android_App_Vulnerability_Benchmarks) technical report is now available.
- Jun 27, 2017: Repo is public with 25 lean benchmarks :)


# Required Software

1.  Android Studio 3.0.1 or later    
2.  JDK 1.8
3.  Python 2.7 or later
4.  Flask 0.12.2


# Notes about Environments

1. **OS X El Capitan (10.11.6)**

    `export PATH=${PATH}:/path/to/Android/sdk/platform-tools:/path/to/Android/sdk/tools`

2. **Windows**
    To avoid **path too long** errors, you should clone the repo into a top level folder called *ghera*.

    `$ git clone https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks.git ghera`

3. **Android Studio 3.0.1** Beginning Android Studio 2.3.3, the *android* command was deprecated.  If your using Android Studio 2.3.3 or later then use the following commands to set up an emulator instead of the commands listed in the README files under each benchmark.

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

5. **Android Studio 2.3.3** Checkout Ghera @[Android-Studio-2.3.3-Compatible](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/src/e10c0a7d77f62ede23cf83fc3467fd61fa0ab289/?at=Android-Studio-2.3.3-Compatiblea) if you plan run Ghera with Android Studio 2.3.3. However, please be aware that this version does not include the following:    

    - non-vulnerable versions of each benchmark
    - Automatic functional testing support
    - Benchmark/s for *Permission*

4. **Android Versions** All benchmarks have been tested on Android 4.4 (API Level 19) - Android 7.1 (API Level 25)

# Contributions are welcome!!

Please fork the repository and submit pull requests to add/edit new/old benchmarks.  We will review the contributions, discuss any changes (if required) with you, and then approve before merging them into the repository.

Every benchmark should be accompanied by the following artifacts:

1. Source code for a sample *Benign* app with a vulnerability. The sample app should be lean in the sense that it should contain a *minimal* set of features required to exhibit the vulnerability.
2. Source code for a sample *Malicious* app that exploits the vulnerability in the Malicious app.
3. Source code for a sample *Secure* app without the vulnerability in *Benign*.
4. Functional tests to test for presence of vulnerability in *Benign* and absence of vulnerability in *Secure*.
4. A README file summarizing the vulnerability in one line, Android versions affected, a short description of the vulnerability and the exploit along with a description of the example apps in 1 and 2, and finally steps to   
build the sample apps instructions to exploit the vulnerability.
5. A one line summary and the title for benchmark in the top level README under each *benchmark category* e.g. ICC, Storage, System, Web.  If it is a new category, then mention it and provide a brief explanation for the category.
6. Any extra configuration the apps might require should be placed in the *Misc* folder.


# Attribution

Copyright (c) 2017, Kansas State University

Licensed under BSD 3-clause "New" or "Revised" License (https://choosealicense.com/licenses/bsd-3-clause/)

Contributors:

1. Aditya Narkar (Developer + Tester)
2. Joydeep Mitra (Developer + Tester) [Creator]
3. Nasik Nafi (Tester)
4. Venkatesh-Prasad Ranganath (Advisor) [Creator]
