package edu.ksu.cs.malicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent("edu.ksu.cs.benign.myrecv");
        intent.putExtra("number","5554");
        intent.putExtra("text","I am malicious");
        sendBroadcast(intent);
    }
}
