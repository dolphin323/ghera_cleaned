package edu.ksu.cs.benign;

import android.annotation.TargetApi;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = "WebViewOverrideUrl";

    /*
    Returning false allows any url to load in the webview.
    The method is not called for requests using a POST method.
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, String url) {
        Log.d(TAG, url);
        return false;
    }

    /*
    Returning false allows any url to load in the webview.
    The method is not called for requests using a POST method.
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, WebResourceRequest req) {
        Log.d(TAG, req.getUrl().toString());
        return false;
    }
}