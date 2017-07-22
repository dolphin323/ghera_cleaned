package santos.cs.ksu.edu.benign;

import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

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
        return null;
    }

    /*
    Returning null allows any url to load in the webview.
     */
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView wv, String url){
        return null;
    }


}
