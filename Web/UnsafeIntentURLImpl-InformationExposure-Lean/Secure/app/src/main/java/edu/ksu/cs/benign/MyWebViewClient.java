package edu.ksu.cs.benign;

import android.content.Intent;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URISyntaxException;

public class MyWebViewClient extends WebViewClient {

    private static String TAG = "Benign/MyWebViewClient";

    private boolean getUrlOverrideSettings(WebView webView, String url) {
        Log.d(TAG, url);
        if (url.contains("http") || url.contains("https"))
            return false;
        else if (url.contains("intent")) {
            try {
                Log.d(TAG, "intent in url");
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                if (intent != null && intent.getAction().equals("edu.ksu.cs.benign.intent.action.DISP")
                        && intent.getPackage().equals("edu.ksu.cs.benign")) {
                    intent.putExtra("info", "Sensitive information");
                    Log.d(TAG, "intent action = " + intent.getAction());
                    webView.getContext().startActivity(intent);
                } else {
                    intent.putExtra("error_msg", "Malformed intent detected");
                    webView.getContext().startActivity(intent);
                }
            } catch (URISyntaxException | NullPointerException e) {
                e.printStackTrace();
                Intent activityIntent = new Intent(webView.getContext(), DisplayActivity.class);
                activityIntent.putExtra("error_msg", "Malformed intent detected");
                webView.getContext().startActivity(activityIntent);
            }
            return true;
        } else return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return getUrlOverrideSettings(webView, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        return getUrlOverrideSettings(webView, webResourceRequest.getUrl().toString());
    }
}