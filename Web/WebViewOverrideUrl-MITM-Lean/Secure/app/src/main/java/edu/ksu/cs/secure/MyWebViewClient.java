package edu.ksu.cs.secure;

import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * Created by Joy on 4/5/17.
 */

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = "WebViewSecure";




    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, String url){
        Log.d(TAG,"shouldOverrideUrlLoading");
        if("http://trust.server.edu/expected.js".equals(Uri.parse(url)))
            return false;
        else return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, WebResourceRequest req){
        Log.d(TAG,"shouldOverrideUrlLoading");
        Log.d(TAG,req.getUrl().toString());
        if("http://trust.server.edu/".equals(req.getUrl().toString()))
            return false;
        else return true;
    }


}
