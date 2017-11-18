package santos.cs.ksu.edu.benignpartner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "BenignPartner";
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isInitial = isInitialStickyBroadcast();
        if (isInitial)
        {
            Log.d(TAG, "Intent received by MyReceiver");
            Log.d(TAG, "Action in intent " + intent.getAction());
            String phnNm = intent.getStringExtra("phone");
            Log.d(TAG, "Send SMS to " + phnNm);
            Toast.makeText(context, "Sent SMS to " + phnNm, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Data is not received from original source", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Data is not received from original source");
        }
    }
}
