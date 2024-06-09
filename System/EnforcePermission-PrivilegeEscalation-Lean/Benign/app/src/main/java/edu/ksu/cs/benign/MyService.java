package edu.ksu.cs.benign;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyService", "Calling PID = " + Binder.getCallingPid() + " Calling UID  = " + Binder.getCallingUid());
        try {
            enforcePermission("santos.benign.permission", Binder.getCallingPid(), Binder.getCallingUid(), "Not allowed to start MyService");
            Log.d("MyService", "Service Started");
            Intent activityIntent = new Intent(this, SensitiveActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra("status_msg", "SUCCESS");
            this.startActivity(activityIntent);
            return super.onStartCommand(intent, flags, startId);
        } catch (SecurityException se) {
            Log.d("MyService", "Failed to start the service");
            se.printStackTrace();
            throw new RuntimeException(se);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
