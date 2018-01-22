package edu.ksu.cs.benign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Joy on 3/1/17.
 */

public class UserLeftBroadcastRecv extends BroadcastReceiver {

    @Override
    public void onReceive(Context c, Intent i) {
        String email = i.getStringExtra("email");
        String text = i.getStringExtra("text");
        Log.d("UserLeftBroadcastRecv", "An email will be sent to " + email + "with the text : " + text);
    }
}
