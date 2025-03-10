# Summary
Apps that communicate with a remote server over an open port are vulnerable to information leak.

# Versions of Android affected
Tested on Android 5.1.1 - Android 8.1

# Description of the vulnerability and the corresponding exploit
An app can listen on a port expecting a connection from a remote server. When the app is successfully connected with the remote server, it performs an action like uploading code/data from the server or downloading code/data form the server.

*Issue* : If the app does not verify the identity of the host it is connected to before performing an action then the app is susceptible to connecting with a malicious remote server instead of connecting with the intended server.

*Example* : The vulnerability is demonstrated by two apps, *Benign* and *Malicious*. *Benign* has a background service *MyIntentService.java* that runs in the background and opens a port that listens for incoming connection requests from a remote server. When the connection is successful, it reads *myInfo.txt*, which stores sensitive information, and writes the information onto the output stream of the socket. *Malicious* also has a background server that scans open ports on the device. Once it finds an open port it connects to it and reads information from its input stream. In this case, *Malicious* will be able to read the contents of *Benign's* *myInfo.txt* in spite of not having access to *Benign's* internal file-system.


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

7. Set the *HOST* field in *Malicious/MyIntentService.java* to the ip address of the emulator.

8. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

9.  Launch *Benign* and click on the button labelled *Open Port*.

10.  Launch *Malicious*.

    You will see "Result -- Enter SSN" displayed on screen. This is the string saved in *Benign's myInfo.txt*.

# References

1. [Open Port Usage in Android Apps and Security Implications : Jia et. al.](http://ieeexplore.ieee.org/stamp/stamp.jsp?arnumber=7961980)

2. [Android Official Documentation](https://developer.android.com/reference/java/net/Socket.html)
