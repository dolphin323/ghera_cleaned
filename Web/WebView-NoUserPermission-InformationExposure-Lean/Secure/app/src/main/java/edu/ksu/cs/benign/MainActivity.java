package edu.ksu.cs.benign;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_LOCATION);
            } else {
                final WebView webView = (WebView) findViewById(R.id.page);
                webView.addJavascriptInterface(new GPSTracker(this), "Benign");
            }
        } else {
            final WebView webView = (WebView) findViewById(R.id.page);
            webView.addJavascriptInterface(new GPSTracker(this), "Benign");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        final EditText url = (EditText) findViewById(R.id.url);
        final Button load = (Button) findViewById(R.id.load);
        final WebView webView = (WebView) findViewById(R.id.page);
        webView.getSettings().setJavaScriptEnabled(true);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Benign", url.getText().toString());
                webView.loadUrl(url.getText().toString());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Benign", "permission granted");
                final WebView webView = (WebView) findViewById(R.id.page);
                webView.addJavascriptInterface(new GPSTracker(this), "Benign");
            } else {
                Log.d("Benign", "permission denied");
            }
        }
    }
}