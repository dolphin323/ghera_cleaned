package com.example.writetofilelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteActivity extends AppCompatActivity {

    private static String TAG = "Library/WriteActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String fileNm = null, fileContent = null;
        Intent i = getIntent();
        if (i != null && i.getStringExtra("fileName") != null && !i.getStringExtra("fileName").equals("")
                && i.getStringExtra("fileContent") != null && !i.getStringExtra("fileContent").equals("")) {

            fileNm = i.getStringExtra("fileName");
            fileContent = i.getStringExtra("fileContent");

            Log.d(TAG, fileNm);
            File dir = new File(getFilesDir(), "custom");
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, fileNm);
            Log.d(TAG, file.getAbsolutePath());
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.append(fileContent);
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred!");
            }
            Log.d(TAG, "done!");
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
        Log.d(TAG, "finish!");
    }
}