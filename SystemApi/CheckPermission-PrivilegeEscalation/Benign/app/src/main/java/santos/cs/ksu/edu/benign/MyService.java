package santos.cs.ksu.edu.benign;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d("MyService", "Calling PID = " + Binder.getCallingPid() + " Calling UID  = " + Binder.getCallingUid());
        //if(checkPermission("santos.benign.permission",Binder.getCallingPid(),Binder.getCallingUid())==PackageManager.PERMISSION_GRANTED)
        /*if(checkPermission("santos.benign.permission",Binder.getCallingPid(),Binder.getCallingUid())==PackageManager.PERMISSION_GRANTED){
            Log.d("MyService","do something important");
        }
        else{
            Log.d("MyService","failed to do something important");
        }*/
        try{
            checkPermission("santos.benign.permission",Binder.getCallingPid(),Binder.getCallingUid());
            Log.d("MyService","do something important");
        }
        catch(SecurityException se){
            Log.d("MyService","failed to do something important");
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
