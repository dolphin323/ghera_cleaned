package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Malicious", "Mal created");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Malicious", "Mal resumed");
        Intent i = new Intent();
        i.setPackage("edu.ksu.cs.benign");
        i.setClassName("edu.ksu.cs.benign", "edu.ksu.cs.logutil.BackgroundService");
        startService(i);
        Log.d("Malicious", "After service start call");
    }
}
