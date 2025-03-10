package edu.ksu.cs.benignpartner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "BenignPartner";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Intent received by MyReceiver");
        Log.d(TAG, "Action in intent " + intent.getAction());
        String phnNm = intent.getStringExtra("phone");
        Log.d(TAG, "Send SMS to " + phnNm);
        MainActivity.tv.setText("Send SMS to " + phnNm);
        Toast.makeText(context, "Sent SMS to " + phnNm, Toast.LENGTH_SHORT).show();
    }
}
