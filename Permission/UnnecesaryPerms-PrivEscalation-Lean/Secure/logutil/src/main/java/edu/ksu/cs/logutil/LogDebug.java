package edu.ksu.cs.logutil;

import android.util.Log;


public class LogDebug {

    private static String TAG = "LogUtilLib";

    public static void d(String message) {
        Log.d(TAG, message);
    }
}
