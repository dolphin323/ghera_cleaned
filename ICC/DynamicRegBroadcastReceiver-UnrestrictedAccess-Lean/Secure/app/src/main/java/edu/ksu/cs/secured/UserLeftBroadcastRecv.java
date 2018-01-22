package edu.ksu.cs.secured;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Joy on 3/1/17.
 */

public class UserLeftBroadcastRecv extends BroadcastReceiver {

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
