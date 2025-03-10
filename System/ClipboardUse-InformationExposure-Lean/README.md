# Summary
Apps that allow copying of *sensitive information to clipboard* may leak information.  

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android clipboard is accessible by every app on the device. Widgets like `EditText` by
default allow users to copy their contents onto the clipboard. For example, to *copy*
the data, users can *long-click* on *EditText* and then select *copy* from *dialog-box*.
By doing so, the contents of `EditText` widget will be copied onto *clipboard*. When
contents of the *clipboard* changes, apps that have registered for notifications about
changes to the clipboard (via `addPrimaryClipChangedListener`) will be notified and
they will have access to the contents of the clipboard.

*Issue:* Since every app has access to the content of the clipboard independent of the
source (app) of the content, malicious apps can �steal� information by merely accessing
the clipboard.  

*Example:* This vulnerability is demonstrated by *Benign*  and *Malicious*.  *Benign*
app has text fields to enter username and password.  The app also provides a button which
allows user to copy password onto the clipboard.  This leads to *information expsosure*.
The *Malicious* app registers a listener to be notified of changes to the clipboard and
"steals" information when the contents of the clipboard changes.

*Note:* A *sensitive information* cannot be copied if `setCustomSelectionActionModeCallback()`
return value set to false for `onCreateActionMode()`. We manually tested benchmark on APIs
22 to 27 and it behaves as expected. For instrumentation testing of benchmark, we created a
button to copy text from `EditText` to *clipboard* because `UiAutomator` is not able to detect
dialog box which pops-up after long-clicking the `EditText`.

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

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk. Build will fail without this.

    `$ ./gradlew installApi[VERSION]Debug`

6. Build and install *Malicious*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk. Build will fail without this.

    `$ ./gradlew installApi[VERSION]Debug`

7. Launch *Malicious* in the emulator where it is installed.

8. Launch *Benign* in the emulator where it is installed.
9.  Enter username and password, and then either press *COPY-PASS* button or copy the contents of password by long clicking on password and then selecting *copy* option.
10. Bring *Malicious* to foreground.

    You will see the copied contents displayed on screen.

# Reference

[Security Smells in Android](http://scg.unibe.ch/archive/papers/Ghaf17c.pdf)
