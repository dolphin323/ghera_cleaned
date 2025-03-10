# Summary
Apps that allow Javascript code to execute in a WebView without verifying where the JavaScript is coming from, can expose the app's resources.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
An Android app can display web pages via *WebView* component. The loaded HTML/JavaScript files run in the context of the app. Consequently, they have the same privilege as the app in terms of access to content providers.


*Issue:* An application can inject and execute malicious JavaScript inside the WebView (provided the WebView allows JavaScript execution).
The WebView has permission to access all content providers the app has access to. Therefore, the JavaScript executing in the context of WebView will also have permission to access these content providers.

*Example:* The vulnerability is demonstrated by *Benign*. The
*MainActivity* of this app has a WebView which loads an HTML file from an internal file system.
The WebView uses *WebSettings.setJavaScriptEnable(true)* to enable JavaScript to execute in its context and uses *WebSettings.setAllowUniversalAccessFromFileURLs(true)*
to enable URLs with content scheme to access data provided by app's *content provider*. Note that this is secure because the web page is loaded from a file in the
internal file-system. However, the web page includes a JavaScript source from an Http server which gets loaded into the WebView when the file URL is loaded.
The JavaScript source will execute in the same context as that of the file URL which means that the JavaScript will execute in the context of the WebView
and because of *WebSettings.setJavaScriptEnable(true)* and *WebSettings.setAllowUniversalAccessFromFileURLs(true)* it will be able to access data provided by app's *content provider*. *Misc/LocalServer* acts as man-in-the-middle that injects *Misc/LocalServer/templates/contentAccess.js* into the WebView. When the *uploadFile()* method in *contentAccess.js* executes, it reads sensitive data from the file which can be accessed by *content provider*.

Note: For the WebView to have access of content provider *webSettings.setAllowContentAccess(true)* must need to be true which is true by default.

Linux specific failure: All Tests are passing on Linux except for API Level 27. The emulator for API level 27 cannot be started as root. Hence File2 and demo1.html cannot be pushed into the internal file system.


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

    The WebView should display the contents of *File2* thus demonstrating that *contentAccess.js* has access to the internal file-system of the app.

# References

1.  [Official Android Documentation](https://developer.android.com/reference/android/webkit/WebSettings#setAllowContentAccess(boolean))

2.  [Bifocals:Analyzing WebView Vulnerabilities in Android Applications - Erika Chin](https://people.eecs.berkeley.edu/~daw/papers/bifocals-wisa13.pdf)
