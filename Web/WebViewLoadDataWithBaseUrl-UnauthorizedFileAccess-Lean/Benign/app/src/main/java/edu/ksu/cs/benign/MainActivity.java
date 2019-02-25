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
    }

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        String data = null;
        final String mimeType = "text/html";
        final String encoding = "utf-8";

        WebView webView = findViewById(R.id.webview1);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setJavaScriptEnabled(true);

        try {
            data = readFile(getAssets().open("demo.html"));
            Log.d("loadData", data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        webView.loadDataWithBaseURL("file:///www.google.com", data, mimeType, encoding, null);
        /*
        Adding a baseUrl="www.google.com" instead of "file:///www.google.com" changes the baseUrl to
        data scheme URL. This will result in cross-origin file-access if sourced JS tries to access local device files.
         */
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
