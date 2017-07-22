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
    allows any url to load in the webview.
    The method is not called for requests using a POST method and
    when a url is loaded into a webview for the first time
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, String url){
        Log.d(TAG,"shouldOverrideUrlLoading");
        return false;
    }

    /*
    This incorrect implementation allows any url to load in the webview.
    The method is not called for requests using a POST method and
    when a url is loaded into a webview for the first time
     */
    @Override
    public boolean shouldOverrideUrlLoading(WebView wv, WebResourceRequest req){
        Log.d(TAG,"shouldOverrideUrlLoading");
        Log.d(TAG,req.getUrl().toString());
        return false;
    }

}
