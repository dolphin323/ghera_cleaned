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

    private static final String TAG = "WebViewSSLErr";

    /*
    Returning null allows any url to load in the webview.
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView wv, WebResourceRequest req){
        Log.d(TAG,"req intercepted");
        Log.d("WebResourceRequest",req.getUrl().toString());
        if("http://trust.server.edu/expected.js".equals(req.getUrl().toString()))
            return null;
        else{
            /*Intent intent= new Intent(Intent.ACTION_VIEW,req.getUrl());
            wv.getContext().startActivity(intent);*/
            return getWebResourceResponseFromString();
        }
    }

    /*
    Returning null allows any url to load in the webview.
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView wv, String url){
        if("http://trust.server.edu/expected.js".equals(Uri.parse(url)))
            return null;
        else{
            /*Intent intent= new Intent(Intent.ACTION_VIEW,req.getUrl());
            wv.getContext().startActivity(intent);*/
            return getWebResourceResponseFromString();
        }
    }

    private WebResourceResponse getWebResourceResponseFromString(){
        return getUtf8EncodedWebResourceResponse(new ByteArrayInputStream("alert('Abort! The webpage contains untrusted content')".getBytes()));
    }
    private WebResourceResponse getUtf8EncodedWebResourceResponse(InputStream data){
        return new WebResourceResponse("text/css", "UTF-8", data);
    }


}
