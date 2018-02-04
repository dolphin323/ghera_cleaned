package edu.ksu.cs.benign;

import android.content.Intent;
import android.net.http.SslError;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {

    private static final String TAG = "WebViewSSLError";

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                   SslError error) {
        Log.d(TAG, "SSL error detected");
        handler.cancel();

        Intent activityIntent = new Intent(view.getContext(), ResponseActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activityIntent.putExtra("error_msg", "SSL error detected");
        view.getContext().startActivity(activityIntent);
    }
}