package edu.ksu.cs.benign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LowMemoryReceiver extends BroadcastReceiver {
    public LowMemoryReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Benign", "Delete files from internal storage");
        /*
        Protect your receiver with the following check to ensure that the receiver is invoked by the system
        if and only if device storage is lows:
        if(intent.getAction() != null && intent.getAction().equals("android.intent.action.DEVICE_STORAGE_LOW")
         */
        context.startService(new Intent(context, DeleteFilesIntentService.class));
    }
}
