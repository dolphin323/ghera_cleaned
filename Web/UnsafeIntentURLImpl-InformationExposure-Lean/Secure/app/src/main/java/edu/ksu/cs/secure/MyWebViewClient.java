package edu.ksu.cs.secure;

import android.annotation.TargetApi;
import android.content.Intent;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.net.URISyntaxException;

/**
 * Created by Joy on 9/3/17.
 */

public class MyWebViewClient extends WebViewClient {

    private static String TAG = "Benign/MyWebViewClient";
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url){
        Log.d(TAG, url);
        if(url.contains("http") || url.contains("https"))
            return false;
        else if(url.contains("intent")){
            try{
                Log.d(TAG, "intent in url");
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                if(intent != null && intent.getAction().equals("edu.ksu.cs.benign.intent.action.DISP")
                        && intent.getPackage().equals("edu.ksu.cs.secure")
                        && intent.getData().getScheme().equals("benign")
                        && intent.getData().getHost().equals("ghera")){
                    intent.putExtra("info", "Sensitive information");
                    Log.d(TAG, "intent action = " + intent.getAction());
                    webView.getContext().startActivity(intent);
                }
                else{
                    Toast.makeText(webView.getContext(),"malformed intent detected",Toast.LENGTH_SHORT).show();
                }
            }
            catch(URISyntaxException e){
                e.printStackTrace();
            }
            catch(NullPointerException e){
                e.printStackTrace();
                Toast.makeText(webView.getContext(),"malformed intent detected",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        else return true;
    }

    @Override
    @TargetApi(21)
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest){
        String url = webResourceRequest.getUrl().toString();
        Log.d(TAG, url);
        if(url.contains("http") || url.contains("https"))
            return false;
        else if(url.contains("intent")){
            try{
                Log.d(TAG, "intent in url");
                Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                if(intent != null && intent.getAction().equals("edu.ksu.cs.benign.intent.action.DISP")
                        && intent.getPackage().equals("edu.ksu.cs.secure")
                        && intent.getData().getScheme().equals("benign")
                        && intent.getData().getHost().equals("ghera")){
                    intent.putExtra("info", "Sensitive information");
                    Log.d(TAG, "intent action = " + intent.getAction());
                    webView.getContext().startActivity(intent);
                }
                else{
                    Toast.makeText(webView.getContext(),"malformed intent detected",Toast.LENGTH_SHORT).show();
                }
            }
            catch(URISyntaxException e){
                e.printStackTrace();
                Toast.makeText(webView.getContext(),"malformed intent detected",Toast.LENGTH_SHORT).show();
            }
            catch(NullPointerException e){
                e.printStackTrace();
                Toast.makeText(webView.getContext(),"malformed intent detected",Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        else return true;
    }
}
