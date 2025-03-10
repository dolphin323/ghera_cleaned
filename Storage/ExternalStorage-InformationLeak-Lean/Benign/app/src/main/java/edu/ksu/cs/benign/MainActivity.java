package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        File file = new File(getExternalFilesDir(null), "ssn_bkup.jpg");
        Log.d("ExternalStorageBenign", file.getAbsolutePath());
        try {
            InputStream is = getResources().openRawResource(R.raw.ssn);
            OutputStream os = new FileOutputStream(file);
            byte[] data = new byte[is.available()];
            is.read(data);
            os.write(data);
            is.close();
            os.close();
        } catch (IOException e) {
            Log.d("ExternalStorageBenign", "Error while writing " + file.getName());
            e.printStackTrace();
            throw new RuntimeException("Error while writing");
        }
    }
}
