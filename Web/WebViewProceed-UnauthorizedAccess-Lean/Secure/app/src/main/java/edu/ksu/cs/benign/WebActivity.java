package edu.ksu.cs.benign;

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
        webView.setWebViewClient(new MyWebViewClient());
        doWebSettings(webView);
        webView.loadUrl("https://10.0.2.2:5000/ssn/" + getIntent().getStringExtra("ssn"), getBasicHead());

        /*
        DISCLAIMER: The way we are securing against this vulnerability is by sending credentials every single time using Authorization header.
        This way the secure app discards the saved credentials. This is one of the ways to secure against this vulnerability.
        The best way to secure against this vulnerability is to send credentials using Authorization header only if the newly acquired credentials
        differ from the credentials used for the previous session.
         */
    }

    private void doWebSettings(WebView webView) {
        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    private String getEncodedString() {
        final String userpass = getIntent().getStringExtra("username") + ":" + getIntent().getStringExtra("password");
        return Base64.encodeToString(userpass.getBytes(),
                Base64.DEFAULT);
    }

    private Map getBasicHead() {
        Map<String, String> additionalHeader = new HashMap();
        additionalHeader.put("Authorization", "Basic " + getEncodedString());
        return additionalHeader;
    }

}
