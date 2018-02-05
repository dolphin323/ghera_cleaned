package edu.ksu.cs.benign;

import android.annotation.TargetApi;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = "WebViewOverrideUrl";

    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, String url) {
        Log.d(TAG, url);
        return getOverrideUrlSettings(url);
    }

    @Override
    @TargetApi(21)
    public boolean shouldOverrideUrlLoading(WebView wv, WebResourceRequest req) {
        Log.d(TAG, req.getUrl().toString());
        return getOverrideUrlSettings(req.getUrl().toString());
    }

    private boolean getOverrideUrlSettings(String url) {
        if ("http://trust.server.edu/expected.js".equals(url))
            return false;
        else return true;
    }
}