package edu.ksu.cs.secured;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

public class FormatOutgoingCallReceiver extends BroadcastReceiver {
    public FormatOutgoingCallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {


            Log.d("OutgoingCall", "Outgoing call...");
            String phNm = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

            setResultData("+1" + phNm);
            Log.d("OutgoingCall", "+1" + phNm);
            Toast.makeText(context, "outgoing call...", Toast.LENGTH_SHORT).show();



//        else {
//            Log.d("OutgoingCall", "Data is already set... Making it null");
//            setResultData(null);
//        }

    }
}