package edu.ksu.cs.secure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = new Intent();
        i.setAction("santos.cs.ksu.edu.benign.MyReceiver");
        i.putExtra("phone", "+17857706217");
        sendBroadcast(i);
        Log.d("StickyBroadcast", "broadcast sent");
    }
}
