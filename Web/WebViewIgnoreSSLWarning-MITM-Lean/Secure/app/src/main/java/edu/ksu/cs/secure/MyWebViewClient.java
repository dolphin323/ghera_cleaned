package edu.ksu.cs.secure;

import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Joy on 4/5/17.
 */

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = "WebViewSSLErr";
    /*
    ignores SSL errors
     */
    @Override
    public void onReceivedSslError(WebView view , SslErrorHandler handler ,
                                   SslError error){
        Log.d(TAG,"SSL error detected");
        handler.cancel();
    }





}
