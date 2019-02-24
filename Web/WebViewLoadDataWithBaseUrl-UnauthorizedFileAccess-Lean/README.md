# Summary
Apps that use `loadDataWithBaseUrl()` with a *file scheme* based *baseURL* (e.g., file://www.google.com) and allow the execution of JavaScript sourced from unverified source may expose the app's resources.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
An Android app can display web pages via *WebView* component. The loaded HTML/JavaScript files run in the context of the app. Consequently, they have the same privilege as the app in terms of access to resources.

*Issue:* An application can load saved HTML web page as a string using *loadDataWithBaseUrl()* with *file scheme baseURL*. Since WebView has permission to access all of the app’s resources, JavaScript code executing in the context of WebView will also have same permissions. If the saved web page sources JavaScript code from a malicious server, then these permissions can be abused.

*Example:* The vulnerability is demonstrated by *Benign*. The
*MainActivity* of this app has a WebView which loads an HTML file from an internal file system.
The WebView uses *WebSettings.setJavaScriptEnable(true)* to enable JavaScript to execute in its context and uses *WebSettings.setAllowFileAccessFromFileURLs(true)*
to enable URLs with file scheme to access local device files. Note that this is secure because the web page is loaded from a file in the
internal file-system. However, the web page includes a JavaScript source from an Http server which gets loaded into the WebView.
The JavaScript source will execute in the same context as that of the file URL which means that the JavaScript will execute in the context of the WebView
and because of *WebSettings.setJavaScriptEnable(true)* and *WebSettings.setAllowFileAccessFromFileURLs(true)* it will be able to access local device files. When the *uploadFile()* method in *fileAccess.js* executes, it reads sensitive data from the file is stored in an internal file system of *Benign* app.

Note: Misc/LocalServer/templates/fileAccess.js is shared with Web/WebViewAllowFileAccess-UnauthorizedFileAccess-Lean.

# Steps to build the sample apps and to exploit the vulnerability

1. Setup a local Web Server. We have used Flask here. If you want to use Flask follow the instructions [here](https://bitbucket.org/secure-it-i/android-app-vulnerability-benchmarks/src/76cc87180f064b328c37cc57b5c743dba378a5de/Misc/LocalServer/README.md?at=master&fileviewer=file-view-default).

2. List targets:

    `$ avdmanager list targets`

3. List available Android Virtual Devices:

    `$ avdmanager list avd`

4. Create an emulator:

    `$ avdmanager create avd -n <name> -k <target>`

    *<target>* is obtained from the command listed in 1. *<name>* is the name you choose to give to the avd.

5. Start emulator:

    `$ emulator -avd <avd_name>`

    *<avd-name>* is obtained from the command listed in 3.


6. Make sure *demo1.html* has the correct URL to the JavaScript file if you are not using local web server of your machine.


7. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8. Save *demo1.html* and *File2* in the app's internal file system:

        `$ adb root`

        `$ adb push demo1.html /data/data/edu.ksu.cs.benign/files/demo1.html`

        `$ adb push File2 /data/data/edu.ksu.cs.benign/files/File2`


9. Launch *Benign*.

10. Click on *Click Me!* button.

    The WebView should display the contents of *File2* thus demonstrating that *fileAccess.js* has access to the internal file-system of the app.

# References

1.  [Official Android Documentation](https://developer.android.com/reference/android/webkit/WebSettings#setAllowContentAccess(boolean))

2.  [Bifocals:Analyzing WebView Vulnerabilities in Android Applications - Erika Chin](https://people.eecs.berkeley.edu/~daw/papers/bifocals-wisa13.pdf)
