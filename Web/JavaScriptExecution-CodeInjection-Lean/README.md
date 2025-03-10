# Summary
Apps that allow Javascript code execution in WebView widget may be susceptible to malicious code execution.

# Android versions affected
Tested on Android 5.1 - Android 8.1

# Description of vulnerability and corresponding exploit
An Android app can display web pages using *WebView* widget. The loaded web content (HTML/JavaScript)
executes in the context of the app with the same priveleges as of the app unless instructed otherwise.
Since WebView is not vetted for security as rigorously as Web Browsers, the execution of JavaScript
code loaded into WebView can be disabled via `WebView.setJavaScriptEnabled()` method.

*Issue:* If arbitrary web content is loaded into WebView without disabling JavaScript code execution,
an adversary could inject malicious code through JavaScript and have it executed by an app to cause harm.

*Example:* The vulnerability is demonstrated by *Benign*. The *MainActivity* of *Benign* app uses an
instance of *WebView* to load HTML file from a server. It allows the execution of JavaScript code in
*WebView* via *WebSetting.setJavaScriptEnabled(true)*.  So, when web content with JavaScript code is loaded,
the embedded JavaScript executes and modifies the HTML page, which is not expected by the *Benign* app.


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


6. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`


7. Launch *Benign*.

    The WebView should display a webpage having a text "This text is set by javascript".

# References

1. [Security Smells in Android](http://scg.unibe.ch/archive/papers/Ghaf17c.pdf)
