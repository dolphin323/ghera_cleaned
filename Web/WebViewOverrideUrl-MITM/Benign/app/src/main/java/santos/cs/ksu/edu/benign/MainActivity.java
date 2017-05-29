package santos.cs.ksu.edu.benign;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveApiKey("API_KEY_SECRET");
    }

    @Override
    protected void onResume(){
        super.onResume();
        WebView webView = (WebView) findViewById(R.id.webview1);
        WebSettings webSettings = webView.getSettings();
        String url = getResources().getString(R.string.url);
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(this,"Android");
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
    }

    private void saveApiKey(String key){
        SharedPreferences mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString("key", key);
        editor.apply();
    }

    @JavascriptInterface
    public String getKey(){
        SharedPreferences mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        return mSettings.getString("key", "missing");
    }

}
