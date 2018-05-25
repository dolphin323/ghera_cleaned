package edu.ksu.cs.benignpartner;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        registerReceiver(new MyReceiver(), new IntentFilter("edu.ksu.cs.benignpartner.MyReceiver"));
    }
}
