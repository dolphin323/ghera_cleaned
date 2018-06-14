package edu.ksu.cs.logutil;

import android.util.Log;

/**
 * Created by Joy on 5/16/18.
 * The LogUtil library offers an easy way to log debug messages.
 * LogDebug is a mock of that feature.
 */

public class LogDebug {

    private static String TAG = "LogUtilLib";

    public static void d(String message) {
        Log.d(TAG, message);
    }
}
