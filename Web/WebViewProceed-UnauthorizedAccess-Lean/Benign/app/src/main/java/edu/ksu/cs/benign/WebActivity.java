package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

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
        MyWebViewClient myWebViewClient = getWebViewClient(getIntent());
        webView.setWebViewClient(myWebViewClient);
        doWebSettings(webView);
        webView.loadUrl("http://10.0.2.2:5000/ssn/" + getIntent().getStringExtra("ssn"));
    }

    private MyWebViewClient getWebViewClient(Intent intent) {
        final MyWebViewClient myWebViewClient = new MyWebViewClient();
        myWebViewClient.username = intent.getStringExtra("username");
        myWebViewClient.password = intent.getStringExtra("password");
        return myWebViewClient;
    }

    private void doWebSettings(WebView webView) {
        final WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }
}
