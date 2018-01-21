package edu.ksu.cs.benign;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Joy on 3/1/17.
 */

public class EmailBroadcastRecv extends BroadcastReceiver {

    @Override
    public void onReceive(Context c, Intent i){
        String action = i.getAction();
        //if(action.equals("edu.ksu.cs.santos.action.USER_LEFT")){
            String email = i.getStringExtra("email");
            String text = i.getStringExtra("text");
            Intent intent = new Intent(c,EmailActivity.class);
            intent.putExtra("email",email);
            intent.putExtra("text",text);
            c.startActivity(intent);
            //Log.d("EmailBroadcastRecv", "An email will be sent to " + email + "with the text : " +  text);
        //}
    }
}
