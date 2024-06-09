package edu.ksu.cs.malicious;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class ConfirmationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Mal", "Receiver hit");
        context.startActivity(new Intent(context, Result.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
