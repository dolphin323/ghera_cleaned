package edu.ksu.cs.logutil;

import android.util.Log;

/**
 * Created by Joy on 5/16/18.
 */

public class LogDebug {

    private static String TAG = "LogUtilLib";

    public static void d(String message){
        Log.d(TAG, message);
    }
}
