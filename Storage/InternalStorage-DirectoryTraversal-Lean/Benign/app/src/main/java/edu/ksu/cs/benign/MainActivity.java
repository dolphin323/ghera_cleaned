package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String fileNm = "demo.txt";
        String fileContent = "demo file";
        Intent i = getIntent();
        if (i != null && i.getStringExtra("fileName") != null && !i.getStringExtra("fileName").equals("")
                && i.getStringExtra("fileContent") != null && !i.getStringExtra("fileContent").equals("")) {
            fileNm = i.getStringExtra("fileName");
            fileContent = i.getStringExtra("fileContent");
        }
        Log.d("BenignMainActivity", fileNm);
        File dir = new File(getFilesDir(), "custom");
        if (!dir.exists()) {
            dir.mkdir();
        }
        Log.d("BenignMainActivity", dir.getAbsolutePath());
        File file = new File(dir, fileNm);
        Log.d("BenignMainActivity", file.getAbsolutePath());
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.append(fileContent);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred!");
        }
        Log.d("BenignMainActivity", "done!");
        setResult(RESULT_OK);
    }
}
