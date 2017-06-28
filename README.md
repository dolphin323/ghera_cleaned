This repository documents vulnerabilities that can occur in Android apps running on Android 4.4 - Android 7.1. It contains benign apps with vulnerabilities related to *ICC, Storage, Web* and *System* APIs. Most of the benign apps are accompanied by malicious apps to exploit the vulnerabilities in the corresponding benign app. We have grouped the vulnerabilities based on APIs they stem from, e.g., ICC, Storage.  In each group, each vulnerability benchmark occurs in folder named *X_Y* where X is the capability that causes a vulnerability and Y is an exploit enabled by the vulnerability.


# What's New

* [Ghera: A Repository of Android App Vulnerability Benchmarks](https://www.researchgate.net/publication/317706386_Ghera_A_Repository_of_Android_App_Vulnerability_Benchmarks) technical report is available.
* Repo is public :)



# Required Software

1.  Android Studio 2.3.1 or later    
2.  JDK 1.8


# Set up Environment variables

1.  OS X El Capitan (10.11.6):

    `export PATH=${PATH}:/path/to/Android/sdk/platform-tools:/path/to/Android/sdk/tools`
    

# A Note for Android Studio 2.3.3

Beginning Android Studio 2.3.3, the *android* command was deprecated. If your using Android Studio 2.3.3 
or later then use the following commands to set up an emulator instead of the commands listed in the
README files under each benchmark.

1. Set Classpath to Java 8 (OS X):

    `$ export PATH=/Applications/Android Studio.app/Contents/jre/jdk/Contents/Home/bin/:$PATH`

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


# Contributions are welcome!!

Please fork the repository and submit pull requests to add/edit new/old benchmarks.  We will review the contributions, discuss any changes (if required) with you, and then approve before merging them into the repository.

Every benchmark should be accompanied by the following artifacts:

1. Source code for a sample Benign app with a vulnerability. The sample app should be lean in the sense that it should contain a *minimal* set of features required to exhibit the vulnerability.
2. Source code for a sample Malicious app that exploits the vulnerability in the Malicious app.
3. A README file summarizing the vulnerability in one line, Android versions affected, a short description of the vulnerability and the exploit along with a description of the example apps in 1 and 2, and finally steps to   
build the sample apps instructions to exploit the vulnerability.
4. A one line summary and the title for benchmark in the top level README under each *benchmark category* e.g. ICC, Storage, System, Web.  If it is a new category, then mention it and provide a brief explanation for the category.
5. Any extra configuration the apps might require should be placed in the *Misc* folder. 


# Attribution

Copyright (c) 2017, Kansas State University 

Licensed under BSD 3-clause "New" or "Revised" License (https://choosealicense.com/licenses/bsd-3-clause/)

Maintained By:

1. Joydeep Mitra
2. Venkatesh-Prasad Ranganath