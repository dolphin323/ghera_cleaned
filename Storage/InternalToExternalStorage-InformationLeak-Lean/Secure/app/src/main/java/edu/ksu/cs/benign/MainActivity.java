package edu.ksu.cs.benign;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Secure";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final EditText info = (EditText) findViewById(R.id.editText);
        Button button = (Button) findViewById(R.id.button);
        Button download = (Button) findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sens_info = info.getText().toString();
                String FILENAME = "sensitive_file";
                try (FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE)) {
                    fos.write(sens_info.getBytes());
                } catch (IOException e) {
                    Log.d(TAG, "error while saving ...");
                    e.printStackTrace();
                    throw new RuntimeException("Unable to write, IOException occurred.");
                }
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(getApplicationContext(), DownloadService.class).putExtra("filename", "sensitive_file"));
            }
        });
    }
}
