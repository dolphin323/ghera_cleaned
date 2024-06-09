package edu.ksu.cs.benign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class EmailBroadcastRecv extends BroadcastReceiver {

    @Override
    public void onReceive(Context c, Intent i) {
        String email = i.getStringExtra("email");
        String text = i.getStringExtra("text");
        Intent intent = new Intent(c, EmailActivity.class);
        intent.putExtra("email", email);
        intent.putExtra("text", text);
        c.startActivity(intent);
    }
}
