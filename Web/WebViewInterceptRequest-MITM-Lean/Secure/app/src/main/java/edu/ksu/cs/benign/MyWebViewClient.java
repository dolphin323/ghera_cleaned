package edu.ksu.cs.benign;

import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = "WebViewInterceptReq";

    /*
    Returning null allows any url to load in the webview.
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView wv, WebResourceRequest req) {
        Log.d(TAG, req.getUrl().toString());
        return getInterceptRequestResponse(req.getUrl().toString());
    }

    private WebResourceResponse getInterceptRequestResponse(String url) {
        /*
        In real-world use case, if url does not need special handling (e.g., trusted), then the url is loaded as it is;
        otherwise, it is loaded after special handling in the else block.
        */
        if ("http://trust.server.edu/expected.js".equals(url))
            return null;
        else {
            return getWebResourceResponseFromString();
        }
    }

    private WebResourceResponse getWebResourceResponseFromString() {
        return getUtf8EncodedWebResourceResponse(new ByteArrayInputStream("alert('Abort! The webpage contains untrusted content')".getBytes()));
    }

    private WebResourceResponse getUtf8EncodedWebResourceResponse(InputStream data) {
        return new WebResourceResponse("text/css", "UTF-8", data);
    }
}