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
        /*
        In real-world use case, if url does not need special handling (e.g., trusted), then the url is loaded as it is;
        otherwise, it is loaded after special handling in the else block.
        */
        if ("http://trust.server.edu/expected.js".equals(url))
            return false;
        else {
            wv.loadData("URL not trusted", "text/css", "UTF-8");
            return true;
        }
    }
}