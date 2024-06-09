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
        return getOverrideUrlSettings(wv, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, WebResourceRequest req) {
        Log.d(TAG, req.getUrl().toString());
        return getOverrideUrlSettings(wv, req.getUrl().toString());
    }

    private boolean getOverrideUrlSettings(WebView wv, String url) {
        if ("http:
            return false;
        else {
            wv.loadData("URL not trusted", "text/css", "UTF-8");
            return true;
        }
    }
}