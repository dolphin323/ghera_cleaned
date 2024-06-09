package edu.ksu.cs.benign;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d("MyService", "Calling PID = " + Binder.getCallingPid() + " Calling UID  = " + Binder.getCallingUid());
        if (checkCallingOrSelfPermission("santos.benign.permission") == PackageManager.PERMISSION_GRANTED) {
            Log.d("MyService", "Service Started");
            Intent activityIntent = new Intent(this, SensitiveActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra("status_msg", "SUCCESS");
            this.startActivity(activityIntent);
        } else {
            Log.d("MyService", "Failed to start the service");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
