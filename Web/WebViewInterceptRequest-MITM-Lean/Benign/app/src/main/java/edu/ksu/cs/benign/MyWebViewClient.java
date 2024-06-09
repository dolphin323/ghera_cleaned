package edu.ksu.cs.benign;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = "WebViewInterceptReq";

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView wv, WebResourceRequest req) {
        Log.d(TAG, req.getUrl().toString());
        return null;
    }
}