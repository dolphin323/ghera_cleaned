# Summary
Apps that use `HttpAuthHandler#proceed(username, password)` to instruct the WebView to proceed with the authentication with the given credentials may give unauthorized access to third-party without validating credentials by sending token of previously validated credentials.

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
An Android app can use `HttpAuthHandler#proceed(username, password)` to proceed with the authentication with the given credentials. For subsequent authentication of credentials WebView will send saved token of previously successful authentication. Therefore, developer relying on `HttpAuthHandler#proceed(username, password)` may give access to third-party without validating their credentials.

*Issue:* An application can request to load an html page using *loadUrl()*. If the loaded web page requires an *HTTP authentication* to access the web page then the server will send `401 Unauthorized` response. An app will then ask user to enter her credentials and an app will send those credentials using `HttpAuthHandler#proceed(username, password)`. Since sending username and password with every request is inconvenient and unsafe, an app creates a token for last provided credentials. For subsequent requests, an app sends the newly created token without verifying credentials for future requests till the validity of the token. Therefore, if an app accepts a *username/password* pair from the third-party and gives them access to the server's resources by relying on `HttpAuthHandler#proceed(username, password)` for authentication then the third-party may get to proceed through the authentication of the server even with incorrect *username/password* pair.

*Example:* The vulnerability is demonstrated by *Benign* and *Malicious*. The *Benign* app has an exported activity which accepts *username/password* pair and *ssn*. *Benign* connects to a server which asks for authentication to proceed to the *ssn* page. *Benign* has a *WebViewClient* which proceeds using provided *username/password* pair using `HttpAuthHandler#proceed(username, password)`. After a valid authentication one or more *username/password* pair, *Malicious* sends a request to the exported activity of *Benign* with a *username/password* pair and *ssn*. Since *Benign* has a valid token from previous authentication, *Benign* sends the saved token and uploads *Malicious's* *ssn* on the server.

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

8. Click on *UPLOAD SSN*:

    The WebView will display `Hello, john! SSN=123`

9. Build and install *Benign*:

    `$ cd Malicious`

    Make sure there is a file called local.properties with an entry sdk=/path/to/Android/sdk.

    `$ ./gradlew installApi[VERSION]Debug`

10. Launch *Benign*.

11. Click on *UPLOAD SSN*:

    The WebView will display `Hello, john! SSN=420`

# References

1.  [Official Android Documentation](https://developer.android.com/reference/android/webkit/HttpAuthHandler.html#proceed(java.lang.String,%20java.lang.String)

2.  [HTTP Authentication](https://developer.mozilla.org/en-US/docs/Web/HTTP/Authentication)
3.  [Authorization Header](https://developer.mozilla.org/en-US/docs/Web/HTTP/Headers/Authorization)
