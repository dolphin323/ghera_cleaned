package edu.ksu.cs.benign;

import android.annotation.TargetApi;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URISyntaxException;

public class MyWebViewClient extends WebViewClient {

    private static String TAG = "Benign/MyWebViewClient";

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        Log.d(TAG, url);
        if (url.contains("http") || url.contains("https"))
            return false;
        else if (url.contains("intent")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                intent.putExtra("info", "Sensitive information");
                Log.d(TAG, "intent action = " + intent.getAction());
                webView.getContext().startActivity(intent);
            } catch (URISyntaxException e) {
                Log.d(TAG, "URISyntaxException Occured");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return true;
        } else return true;
    }

    @Override
    @TargetApi(21)
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        String url = webResourceRequest.getUrl().toString();
        Log.d(TAG, url);
        if (url.contains("http") || url.contains("https"))
            return false;
        else if (url.contains("intent")) {
            try {
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                intent.putExtra("info", "Sensitive information");
                Log.d(TAG, "intent action = " + intent.getAction());
                webView.getContext().startActivity(intent);
            } catch (URISyntaxException e) {
                Log.d(TAG, "URISyntaxException Occured");
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            return true;
        } else return true;
    }
}