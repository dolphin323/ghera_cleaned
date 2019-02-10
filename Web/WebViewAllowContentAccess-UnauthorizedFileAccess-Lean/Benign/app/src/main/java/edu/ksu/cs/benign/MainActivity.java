package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        WebView webView = findViewById(R.id.webview1);
        WebSettings webSettings = webView.getSettings();
        String url = getResources().getString(R.string.url);
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());
        webSettings.setAllowContentAccess(true);    //It is true by default
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }
}