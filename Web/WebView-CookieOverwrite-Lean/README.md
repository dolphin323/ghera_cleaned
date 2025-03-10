# Summary
Apps that allow websites viewed through WebView may enable *cookie overwrite* attacks.   

# Android versions affected
Tested on Android 5.1.1 - Android 8.1

# Description of vulnerability and corresponding exploit
Android apps can display websites via WebView. Websites store state information on the browser/client sides using cookies. *Cookies* can be set either by *Set-Cookie* header or client-side *javascript* by using *document.write*.

A cookie consists of name, value, and zero or more attributes as name/value pairs. The *domain* and *path* are two attributes that define the scope of a cookie.

If a cookie shares the domain scope with a related domain, it can be directly overwritten by that domain using another cookie with the exactly same name/domain/path.

*Issue:* An Android that uses WebView can allow websites to store cookies. A malicious web page *MW* from the same domain as benign web page *BW* can overwrite cookies set by *BW* by specifying the same name/domain/path.

*Example:*
This vulnerability is demonstrated by Benign* app, *Benign Website BW* available at *localhost/BenignCookie*, and a *Malicious Website MW* available at *localhost/MalCookie*. *BW* sets a cookie in a WebView with default domain and path. *MW* sets cookie with *domain = 10.0.2.2* (IP of the local server) and *path = /BenignCookie* (the path/route of *BW* on the local server). When *BW* tries to read the saved cookie, it will read the cookie which is overwritten by Malicious.

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

7. Click on *BENIGN WEB PAGE*.

8. Click on *Put!* and then click on *getCookie!*.

   You will see *username=John Doe*

9. Come back to home page and click on *MALICIOUS WEB PAGE* and then click on *Put!*.

10. Come back to home page and then click on *BENIGN WEB PAGE* and then click on *getCookie!*

   You will see *username=Jhontu*

  *BW* stores the cookie with key-value pair username=John Doe. *MW* successfully overwrites the key-value pair to username=Jhontu.

# References

1.  [Official Android Documentation](https://developer.android.com/reference/android/webkit/CookieManager#setAcceptCookie(boolean))

2.  [USENIX Paper](https://www.usenix.org/system/files/conference/usenixsecurity15/sec15-paper-zheng-updated.pdf)

3.  [Mozilla Documentation on Cookies](https://developer.mozilla.org/en-US/docs/Web/HTTP/Cookies)
