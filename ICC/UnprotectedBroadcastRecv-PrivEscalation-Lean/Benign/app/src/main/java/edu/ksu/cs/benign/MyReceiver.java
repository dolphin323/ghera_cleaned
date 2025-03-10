package edu.ksu.cs.benign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction() != null
                && intent.getAction().equals("edu.ksu.cs.benign.myrecv")) {
            String number = intent.getStringExtra("number");
            String text = intent.getStringExtra("text");
            text = "Benign: " + text;
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(number, null, text, null, null);
            Log.d("Benign", "Message sent");
        }
    }
}
