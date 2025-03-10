package edu.ksu.cs.benignpartner;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BenignPartner", "called");
        Bundle bundle = intent.getBundleExtra("bundle");
        PendingIntent pi = bundle.getParcelable("pendingIntent");
        try {
            pi.send();
        } catch (PendingIntent.CanceledException ce) {
            ce.printStackTrace();
        }
    }
}
