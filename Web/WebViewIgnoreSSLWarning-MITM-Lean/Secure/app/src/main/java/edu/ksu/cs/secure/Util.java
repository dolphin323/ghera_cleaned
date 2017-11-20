package edu.ksu.cs.secure;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by Joy on 5/8/17.
 */

public class Util {

    private static String TAG = "WebViewSSLErr";

    @JavascriptInterface
    public String readKey(){
        Log.d(TAG,"sensitive info retrieved");
        return "Sensitive Information";
    }

    @JavascriptInterface
    public void writeKey(String key){
        Log.d(TAG, key  + " written");
    }

    @JavascriptInterface
    public void doPrivilegedOperation(){
        Log.d(TAG,"privileged operation performed");
    }
}
