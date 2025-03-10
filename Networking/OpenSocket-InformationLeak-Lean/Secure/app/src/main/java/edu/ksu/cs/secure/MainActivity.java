package edu.ksu.cs.secure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Benign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) findViewById(R.id.TextView);
                try (FileOutputStream fos = openFileOutput("myInfo.txt", MODE_PRIVATE)) {
                    fos.write(tv.getText().toString().getBytes());
                    startService(new Intent(getApplicationContext(), MyIntentService.class));
                } catch (IOException e) {
                    Log.d(TAG, "error while writing to file");
                    e.printStackTrace();
                }
            }
        });
    }
}
