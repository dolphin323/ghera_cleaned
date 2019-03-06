package edu.ksu.cs.benign;

import android.net.http.SslError;
import android.util.Log;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewDatabase;

public class MyWebViewClient extends WebViewClient {
    protected String username, password;
    private int count = 0;

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (count == 3) {
            --count;
            handler.cancel();
        } else {
            if (username != null && password != null) {
                Log.d("username", username);
                Log.d("password", password);
            }
            handler.proceed(username, password);
            ++count;
        }
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed();
    }
}