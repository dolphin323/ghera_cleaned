# Summary
Apps that communicate with a server over TCP/IP without encryption allow Man-in-the-Middle attackers to spoof server IPs by intercepting client-server data streams.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
An app can connect to a server via the *Socket* API and transmit data to and from it.

*Issue* : A Man-in-the-Middle can intercept traffic between the app and the server and can also redirect the app to a different IP without the app's knowledge.

*Example* : The vulnerability is demonstrated by an app, *Benign* and a malicious Man-in-the-Middle program *LocalServer/java/WriteToSocket.java*. *Benign* has a background service *MyIntentService.java* that runs in the background and connects to a server. Since, the connection is not encrypted *LocalServer/java/WriteToSocket.java* masquerades as the server and communicates with the app.


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

5. Start the server.

  `$ cd Misc/LocalServer/java`

  `$ javac WriteToSocket.java`

  `$ java WriteToSocket`

6. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    Make sure that *Benign/.../MyIntentService* has an attribute *HOST* that is set to the ip address of the machine where *LocalServer/java/WriteToSocket.java* is running.

    `$ ./gradlew installApi[VERSION]Debug`

7.  Launch *Benign* and click on the button labelled *Connect*.

8.  Check log.

    `$ adb logcat -d | grep "Benign"`

    You will see *Result* being displayed indicating that the connection to the Man-in-the-Middle was successful.

# References

1. [Related to Issue #29](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/issues/29/explore-cve-2015-1595-to-create-new-lean).
