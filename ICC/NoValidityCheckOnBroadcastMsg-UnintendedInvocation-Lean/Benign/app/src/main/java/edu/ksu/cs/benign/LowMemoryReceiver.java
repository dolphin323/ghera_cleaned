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
        context.startService(new Intent(context, DeleteFilesIntentService.class).putExtra("Delete", true));
    }
}
