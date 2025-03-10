package edu.ksu.cs.benign;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    private static int REQ_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (checkPermission("android.permission.READ_PHONE_STATE",
                android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            saveUuid(getId());
            TextView tv = (TextView) findViewById(R.id.disp_id);
            tv.setText(showUuid());
        } else {
            if (Build.VERSION.SDK_INT >= 23)
                requestPermissions(new String[]{"android.permission.READ_PHONE_STATE"}, REQ_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQ_CODE) {
            if (permissions[0].equalsIgnoreCase("android.permission.READ_PHONE_STATE") &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveUuid(getId());
                TextView tv = (TextView) findViewById(R.id.disp_id);
                tv.setText(showUuid());
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

    private String showUuid() {
        String uuid;
        File file = new File(getExternalFilesDir(null), "UNIQUE_ID.txt");
        try (Scanner sc = new Scanner(new File(file.getAbsolutePath()))) {
            StringBuilder uniqueIDBuf = new StringBuilder();
            while (sc.hasNextLine())
                uniqueIDBuf.append(sc.nextLine());
            uuid = uniqueIDBuf.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        return uuid;
    }

    private void saveUuid(String uuid) {
        try {
            File file = new File(getExternalFilesDir(null), "UNIQUE_ID.txt");
            OutputStream os = new FileOutputStream(file);
            os.write(("DeviceId :" + uuid).getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while saving unique id");
        }
    }
}
