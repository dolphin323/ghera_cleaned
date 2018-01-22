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
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d("MyService", "Calling PID = " + Binder.getCallingPid() + " Calling UID  = " + Binder.getCallingUid());
        try{
            enforceCallingOrSelfPermission("santos.benign.permission","Not allowed to start MyService");
            Log.d("MyService","Service Started");
            Intent activityIntent = new Intent(this, SensitiveActivity.class);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra("status_msg", "SUCCESS");
            this.startActivity(activityIntent);
        }
        catch(SecurityException se){
            Log.d("MyService","Failed to start service");
        }
        finally{
            return super.onStartCommand(intent,flags,startId);
        }

    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
