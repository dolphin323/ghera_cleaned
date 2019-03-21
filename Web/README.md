1. **HttpConnection-MITM-Lean**
Apps that connect to a server via the *HTTP* protocol are vulnerable to Man-in-the-Middle (MITM) attacks.

2. **JavaScriptExecution-CodeInjection-Lean** Apps that allow Javascript code to execute in a WebView may permit malicious code execution.    

3. **UnsafeIntentURLImpl-InformationExposure-Lean** Apps that do not safely handle an incoming intent embedded inside a URI are vulnerable to information exposure via intent hijacking.

4. **WebView-CookieOverwrite-Lean** Apps that allow websites viewed through WebView may enable cookie overwrite attacks.

5. **WebView-NoUserPermission-InformationExposure** Apps that disclose sensitive information without explicitly requesting the user for permission are vulnerable to unintended information exposure.

6. **WebViewAllowContentAccess-UnauthorizedFileAccess-Lean** Apps that allow Javascript code to execute in a WebView without verifying where the JavaScript is coming from, can expose the app's resources.

7. **WebViewAllowFileAccess-UnauthorizedFileAccess-Lean** Apps that allow Javascript code to execute in a WebView without verifying where the JavaScript is coming from, can expose the app's resources

8. **WebViewIgnoreSSLWarning-MITM-Lean** Apps that ignore SSL errors when loading content in a WebView are vulnerable to MitM attacks

9. **WebViewInterceptRequest-MITM-Lean** Apps that do not validate resource request before loading them into a WebView are vulnerable to MitM attacks.

10. **WebViewLoadDataWithBaseUrl-UnauthorizedFileAccess-Lean** Apps that use loadDataWithBaseUrl() with a file scheme based baseURL (e.g., file://www.google.com) and allow the execution of JavaScript sourced from unverified source may expose the app's resources.

11. **WebViewProceed-UnauthorizedAccess-Lean** Apps that use HttpAuthHandler#proceed(username, password) to instruct the WebView to perform authentication with the given credentials may give unauthorized access to third-party when the apps do not validate credentials, e.g., by sending token of previously validated credentials.

12. **WebViewOverrideUrl-MITM-Lean** Apps that do not validate page requests made from within a WebView, are vulnerable to MitM attcaks.
