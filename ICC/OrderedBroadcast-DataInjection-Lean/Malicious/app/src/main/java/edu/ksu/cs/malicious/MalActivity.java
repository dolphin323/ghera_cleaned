package edu.ksu.cs.malicious;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MalActivity extends AppCompatActivity {

    public static int REQ_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.PROCESS_OUTGOING_CALLS"}, REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == REQ_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MalActivity", "Permission granted.");
            } else {
                throw new RuntimeException("Permission denied.");
            }
        }
        else{
            Log.d("MalActivity", "Permission denied.");
        }
    }
}
