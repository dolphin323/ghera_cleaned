package edu.ksu.cs.malicious;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
        if(checkPermission("android.permission.READ_EXTERNAL_STORAGE",
                android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED){
            showUuid();
        }
        else{
            if(Build.VERSION.SDK_INT >= 23)
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 100) {
            if (permissions[0].equalsIgnoreCase("android.permission.READ_EXTERNAL_STORAGE") &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showUuid();
            } else {
                throw new RuntimeException("Permission has not been granted");
            }
        }
    }

    private void showUuid(){
        String uuid;
        File file = new File(getExternalFilesDir(null), "UNIQUE_ID.txt");
        String path = file.getAbsolutePath().replace("malicious","benign");
        try (Scanner sc = new Scanner(new File(path))) {
            StringBuilder uniqueIDBuf = new StringBuilder();
            while (sc.hasNextLine())
                uniqueIDBuf.append(sc.nextLine());
            uuid = uniqueIDBuf.toString();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found");
        }
        TextView tv = (TextView) findViewById(R.id.id);
        tv.setText(uuid);
    }
}
