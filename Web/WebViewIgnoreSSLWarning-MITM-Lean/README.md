# Summary
Apps that ignore SSL errors when loading content in a WebView are vulnerable to MitM attacks.  

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
An Android app can display web pages by loading HTML/JavaScript files in a *WebView*. A WebView loading
an HTML/JavaScript file from a server using SSL/TLS can throw an SSL exception if an incorrect
certificate is presented by the server or if the app does not trust the Certificate Authority that has
signed the certificate for that server.


*Issue:* Android provides the *WebViewClient* API to manage communication between the app and the server. One of the methods in the API *onReceivedSslError()* allows an app to cancel or proceed with response from the server when an SSL error occurs. If the app chooses to proceed with the response then the app is vulnerable to MITM attacks because a malicious server can create a fake certificate and still communicate with the app.

*Example:* The vulnerability is demonstrated by *Benign*. The app connects to a server over SSL/TLS that produces a certificate that is incorrect. The app ignores this error and proceeds as shown in the *onReceivedSslError()* of the *MyWebViewClient.java* class. The server in *Misc/LocalServer* acts as malicious man-in-the-middle between the app and the server, that masquerades as the server. It is signed by a self-signed certificate and is able to successfully connect to the app.


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

7. Launch the app in the emulator. If you used Flask to setup a local web server like we described in 1, then you will see a webpage with the message "This is a test web page set up using Flask". The local web server is signed with a self-signed certificate which should raise an SSL error when the app connects to the local server. However, *Benign* ignores such errors making it vulnerable to connect to a malicious server.

# References

1.  [Official Android Documentation](https://developer.android.com/training/articles/security-ssl.html)

2.  [Handle SSL Errors in WebView](https://developer.android.com/reference/android/webkit/WebViewClient.html#onReceivedSslError(android.webkit.WebView, android.webkit.SslErrorHandler, android.net.http.SslError))

2.  [Mitigating Android SSL Vulnerabilities - Vasant Sudhakar Tendulkar](https://repository.lib.ncsu.edu/bitstream/handle/1840.16/8840/etd.pdf?sequence=2&isAllowed=y)
