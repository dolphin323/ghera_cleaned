package edu.ksu.cs.benign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class FormatOutgoingCallReceiver extends BroadcastReceiver {
    public FormatOutgoingCallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null
                && intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")) {
            Log.d("OutgoingCall", "Outgoing call...");
            String phNm = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

            setResultData("+1" + phNm);
            Log.d("OutgoingCall", "+1" + phNm);
            Toast.makeText(context, "outgoing call...", Toast.LENGTH_SHORT).show();
        }
    }
}