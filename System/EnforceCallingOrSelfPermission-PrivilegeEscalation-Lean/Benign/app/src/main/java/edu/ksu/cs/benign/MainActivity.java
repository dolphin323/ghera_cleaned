package edu.ksu.cs.benign;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkPermission("santos.benign.permission", android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            Log.d("MyService", "permission to service already granted");
            startService(new Intent(getApplicationContext(), MyService.class));
        } else {
            Log.d("MyService", "component does not have permission to call service.");
            ActivityCompat.requestPermissions(this, new String[]{"santos.benign.permission"}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MyService", "granted");
                    startService(new Intent(getApplicationContext(), MyService.class));
                } else {
                    throw new RuntimeException("Permission denied");
                }
            }
            default: {
                Log.d("MyService", "denied");
            }
        }
    }
}
