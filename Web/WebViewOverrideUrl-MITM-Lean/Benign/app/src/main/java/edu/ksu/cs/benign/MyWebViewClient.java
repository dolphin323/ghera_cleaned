package edu.ksu.cs.benign;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = "WebViewOverrideUrl";

    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, String url) {
        Log.d(TAG, url);
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, WebResourceRequest req) {
        Log.d(TAG, req.getUrl().toString());
        return false;
    }
}