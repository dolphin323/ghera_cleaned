package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private static String uniqueID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File(getExternalFilesDir(null), "UNIQUE_ID.txt");
        if (uniqueID == null) {
            uniqueID = UUID.randomUUID().toString();
            //save to a file for later access
            try {
                OutputStream os = new FileOutputStream(file);
                os.write(("App Id : " + uniqueID).getBytes());
                Log.d("Test", file.getAbsolutePath());
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
}
