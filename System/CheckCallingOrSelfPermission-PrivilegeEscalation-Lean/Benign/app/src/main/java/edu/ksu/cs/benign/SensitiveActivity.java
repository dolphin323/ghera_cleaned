package edu.ksu.cs.benign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SensitiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensitive);

        if (getIntent().hasExtra("status_msg")) {
            String status = getIntent().getStringExtra("status_msg");
            if (status.equals("SUCCESS")) {
                TextView service_status = (TextView) findViewById(R.id.serviceStatusText);
                service_status.setText("Activity Started from Service.");
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(getApplicationContext(), MyService.class));
        super.onDestroy();
    }
}
