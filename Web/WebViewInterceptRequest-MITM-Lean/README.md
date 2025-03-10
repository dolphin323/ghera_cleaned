# Summary
Apps that do not validate resource requests before loading them into a WebView are vulnerable to MitM attacks.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit

Android allows apps to load resources (e.g, JavaScript, CSS files etc.) in a web page in a WebView, and control the resources being loaded in a webpage via the *shouldInterceptRequest()* method in *WebViewClient*.
The *shouldInterceptRequest()* method takes an instance of WebView and the resource request as input and returns a response object. If the response object returned
is null then the WebView is loaded with the web page containing the resource that was requested. But if a non-null response is returned then the WebView is loaded with the web page
containing the non-null response.

*Issue:* the app does not validate the resource in shouldInterceptRequest method of WebViewClient. Consequently, any resource can be loaded into WebView.

*Example:* The vulnerability is demonstrated by *Benign*. The *MainActivity* of this app has a WebView which loads a *http* URL. The app contains *MyWebViewClient*
which extends the default *WebViewClient* and overrides the *shouldInterceptRequest()* method. The method just returns null. The server in *Misc/LocalServer* acts as malicious man-in-the-middle. It injects *myScripts1.js* into the content that the WebView in *MainActivity* loads. Since *MyWebViewClient.shouldInterceptRequest()* returns null without validating resource requests, *myScripts1.js* is allowed to execute. In this case *myScripts1.js* can read/write information from/to a file in SharedPreferences.  


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

6. Edit the *url* field in *Benign/.../res/values/strings.xml* to reflect the url of the web server where the html file being loaded is stored.

7. Build and install *Benign*:

    `$ cd Benign`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

8. Click on *Display Secret Key*. 

    *API_KEY_SECRET* will be displayed, demonstrating that the malicious JavaScript *myScripts1.js* successfully gains access to the app's internal/private files.  

# References

1.  [Official Android Documentation](https://developer.android.com/reference/android/webkit/WebViewClient.html#shouldInterceptRequest(android.webkit.WebView, android.webkit.WebResourceRequest))
