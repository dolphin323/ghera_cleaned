# Summary
Apps that rely on dynamic code loading without verifying the integrity and 
authenticity of the loaded code may be vulnerable to code injection.


# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit

Since Android apps are JVM-based, they can use dynamic class loading
features of the JVM (e.g., `Class.forName()`, `Class.getDeclaredField()`) to
dynamically load and execute code that is not packaged with the app.


*Issue:*  Since the apps can load classes from local archives (via
`PathClassLoader`) or remote archives (via `URLClassLoader`), malicious
actors can change such archives and affect the behavior of apps that use
the archive.  Apps can protect themselves from such influences by either
avoiding dynamic code loading (by making such code fragments part of the app)
or using approriate checks to verify both integrity and authenticity of the
archives at loading time.

*Example:*  This benchmark demonstrates this vulnerability via a *Benign* app
and an *UnsignedHelper* library.  *Benign* app dynamically loads
*greetings.jar* from external storage and uses the greetings defined in
`secureiti.Helper` class found in the jar file.  *Benign* app does not check
the integrity or authenticity of *greetings.jar*.  So, placing an unsigned *greetings.jar* (from *UnsignedHelper* library) containing bad greeting
messages in the appropriate location changes the behavior of *Benign* app.


# Steps to build the sample apps and to exploit the vulnerability

1.  list targets:

    `$ avdmanager list targets`

2.  List available Android Virtual Devices:

    `$ avdmanager list avd`

3.  Create an emulator:

    `$ avdmanager create avd -n <name> -k <target>`

    *<target>* is available from the command listed in 1. *<name>* is the name
    you choose to give to the avd.

4.  Start emulator:

    `$ emulator -avd <avd_name>`

    *<avd-name>* is available from the command listed in 2.

5.  Build and install *Benign*:

    `$ cd Benign`

    `$ ./gradlew installApi[VERSION]Debug`

6.  Build and transfer *UnsignedHelper* library to the emulator.

    `$ cd UnsignedHelper`

    Make sure `gradle.properties` exists and it contains an entry
    sdk=/path/to/Android/sdk.

    `$ ./gradle clean dexify`

    `$ adb push build/libs/unsigned-greetings.jar <External
    Storage>/Android/data/edu.ksu.cs.benign/files/greetings.jar`

    Use appropriate *\<External Storage>* depending on the Android API level
    being tested against, e.g., `/storage/sdcard` for API level 19 and
    `/storage/emulated/0` with API level 25.

    Using adb, make sure  *\<External
    Storage>/Android/data/edu.ksu.cs.benign/files/* exists.  If it doesn't
    exist, then launch and close Bengin on the emulator.

7.  Launch *Benign* in the emulator and press "Press Me!!" button.

    The text view should display "Go to Hell".


# References

1.  *Dynamic Code Loading* in section *Lax Input Validation* of [Security Smells in Android](http://scg.unibe.ch/archive/papers/Ghaf17c.pdf) 
2.  Sections *Creation and Loading* and *Linking* of [Java Virtual Machine Specification](https://docs.oracle.com/javase/specs/jvms/se8/jvms8.pdf)
3.  [Dynamic Class Loading in the Java Virtual Machine](https://dl.acm.org/citation.cfm?doid=286936.286945)
