package edu.ksu.cs.malicious;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
        registerReceiver(new MyReceiver(),new IntentFilter("santos.cs.ksu.edu.benignpireceiver"));
    }
}
