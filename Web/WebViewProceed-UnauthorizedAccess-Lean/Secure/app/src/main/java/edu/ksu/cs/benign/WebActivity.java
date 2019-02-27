package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final WebView webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        doWebSettings(webView);
        webView.loadUrl("http://10.0.2.2:5001/"+getIntent().getStringExtra("ssn"), getBasicHead());
    }

    private void doWebSettings(WebView webView) {
        final WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }

    private String getEncodedString() {
        final String userpass = getIntent().getStringExtra("username")+":"+getIntent().getStringExtra("password");
        return Base64.encodeToString(userpass.getBytes(),
                Base64.DEFAULT);
    }

    private Map getBasicHead() {
        Map<String, String> additionalHeader = new HashMap();
        additionalHeader.put("Authorization", "Basic "+ getEncodedString());
        return additionalHeader;
    }

}
