package edu.ksu.cs.secure;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Joy on 4/5/17.
 */

public class MyWebViewClient extends WebViewClient {

    @Override
    public void onPageFinished(WebView wv, String url){
        wv.loadUrl("javascript:myFunction()");
    }

}
