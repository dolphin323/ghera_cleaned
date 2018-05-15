package edu.ksu.cs.benign;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private static String uniqueID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(getExternalFilesDir(null), "UNIQUE_ID.txt");
        if (uniqueID == null) {
            if (checkPermission("android.permission.READ_PHONE_STATE",
                    android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
                uniqueID = getId();
            } else {
                if (Build.VERSION.SDK_INT >= 23)
                    requestPermissions(new String[]{"android.permission.READ_PHONE_STATE"}, 100);
            }
            //save to a file for later access
            try {
                OutputStream os = new FileOutputStream(file);
                os.write(("DeviceId :" + uniqueID).getBytes());
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error while saving unique id");
            }
        }
    }

    protected void onResume() {
        super.onResume();
        TextView tv = (TextView) findViewById(R.id.disp_id);
        tv.setText("Device ID = " + uniqueID);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (permissions[0].equalsIgnoreCase("android.permission.READ_PHONE_STATE") &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                uniqueID = getId();
            } else {
                throw new RuntimeException("Permission has not been granted");
            }
        }
    }

    private String getId() {
        TelephonyManager telephonyManager;
        telephonyManager = (TelephonyManager) getSystemService(Context.
                TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
