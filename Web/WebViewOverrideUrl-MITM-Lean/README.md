# Summary
Apps that do not validate page requests made from within a WebView, are vulnerable to loading malicious content in it.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android allows apps to display web content in a WebView, and control navigation across webpages via the *shouldOverrideUrlLoading()* method in *WebViewClient*.
The *shouldOverrideUrlLoading()* method takes an instance of WebView and the page request as input and returns a boolean. If true is returned then the host application handles the request else if false is returned then
the current WebView handles the request.

*Issue:* The app does not validate the page request in *shouldOverridUrlLoading()* before loading it in the WebView. Consequently, any web page provided by the server will be loaded into WebView.

*Example:* The vulnerability is demonstrated by *Benign*. The *MainActivity* of this app has a WebView which loads a *http* URL. The app contains *MyWebViewClient* which extends the default *WebClient* and
overrides the *shouldOverrideUrlLoading()* method. The method always returns false which means that any URL including malicious URLs can be loaded into the WebView. In this example, *Misc/LocalServer* is a man-in-the-middle that injects a malicious url in page that gets loaded into the WebView. Since, the *MyWebViewClient.shouldOverrideUrlLoading()* does not validate page requests, the malicious content will be successfully loaded into the WebView.

Note that the default *shouldOverrideUrlLoading()* returns false all the time.  


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

7. Click on the link *Google.com*. 

    Some JavaScript code will be loaded instead thereby, demonstrating that a man-in-the-middle can inject malicious content into the WebView.

# References

1.  [Official Android Documentation](https://developer.android.com/reference/android/webkit/WebViewClient.html#shouldOverrideUrlLoading(android.webkit.WebView, android.webkit.WebResourceRequest))

2.  [Bifocals:Analyzing WebView Vulnerabilities in Android Applications - Erika Chin](https://people.eecs.berkeley.edu/~daw/papers/bifocals-wisa13.pdf)
