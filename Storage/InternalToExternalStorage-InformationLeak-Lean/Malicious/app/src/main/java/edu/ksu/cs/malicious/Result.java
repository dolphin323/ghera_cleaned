package edu.ksu.cs.malicious;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Result extends AppCompatActivity {
    private static String TAG = "Mal/Result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        File file = new File(getExternalFilesDir(null), "sensitive_file");
        String absPath = file.getAbsolutePath();
        String path = absPath.replace("edu.ksu.cs.malicious", "edu.ksu.cs.benign");
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader bufferedReader = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            Log.d(TAG, sb.toString());
            TextView res = (TextView) findViewById(R.id.result);
            res.setText(sb.toString());
        } catch (IOException e) {
            Log.d(TAG, "error while saving ...");
            e.printStackTrace();
        }
    }
}
