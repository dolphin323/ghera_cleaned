package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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
        webView.setWebViewClient(new WebViewClient());
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptEnabled(true);

        String data = null;
        final String mimeType = "text/html";
        final String encoding = "utf-8";
        try {
            data = readFile(getAssets().open("demo.html"));
            Log.d("loadData", data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        webView.loadDataWithBaseURL("http://10.0.2.2:5000/", data, mimeType, encoding, null);
    }

    private String readFile(final InputStream file) throws IOException {
        final StringBuffer stringBuffer = new StringBuffer();
        int i;
        while((i = file.read()) != -1) {
            stringBuffer.append((char)i);
        }
        return stringBuffer.toString();
    }
}