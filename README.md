# README #

This repository documents vulnerabilities that can occur in Android apps running on Android 4.2 - Android 7.1. It contains benign apps with vulnerabilities related to *ICC, Storage, Web* and *System* APIs. Most of the benign apps are accompanied by malicious apps to exploit the vulnerabilities in the corresponding benign app. We have grouped the vulnerabilities based on APIs they stem from, e.g., ICC, Storage.  In each group, each vulnerability benchmark occurs in folder named *X_Y* where X is the capability that causes the vulnerability and Y is the enabled exploit.

# Required Software:

1.  Android Studio 2.3.1 or later.   
    
2.  JDK 1.8

# Set up Environment variables:

1.  OS X El Capitan (10.11.6):

    `export PATH=${PATH}:/path/to/Android/sdk/platform-tools:/path/to/Android/sdk/tools`
    
# A note for Android Studio 2.3.3 and later
If your using Android Studio 2.3.3 or later then use the following commands to set up an emulator instead 
of the commands listed in the README files under each benchmark.

1. list targets:

    `$ android list targets`

2. list available Android Virtual Devices:

    `$ android list avd`

3.  create an emulator:

    `$ android create avd -n <name> -t <target>`

    *target* is obtained from the command listed in 1. *name* is the name you choose to give to the avd.

4.  start emulator:

    `$ emulator -avd <avd_name>`

    *avd-name* is obtained from the command listed in 2.


# Attribution

Copyright (c) 2017, Kansas State University 

Licensed under BSD 3-clause "New" or "Revised" License (https://choosealicense.com/licenses/bsd-3-clause/)

Maintained By:

1.	Joydeep Mitra
2.	Venkatesh-Prasad Ranganath