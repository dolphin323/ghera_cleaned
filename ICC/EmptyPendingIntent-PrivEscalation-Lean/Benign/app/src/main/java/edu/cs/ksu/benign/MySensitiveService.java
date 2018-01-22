package edu.cs.ksu.benign;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MySensitiveService extends Service {
    public MySensitiveService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("PendingIntent...", "Do something important");
        startActivity(new Intent(getApplicationContext(), SenitiveActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
