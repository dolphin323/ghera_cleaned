package edu.cs.ksu.malicious;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //Toast.makeText(getApplicationContext(),"Malicious service started",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(),MalActivity.class)
                .putExtra("msg","MalService started")
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
