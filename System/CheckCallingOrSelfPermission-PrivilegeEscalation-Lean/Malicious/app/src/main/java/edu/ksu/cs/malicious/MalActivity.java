package edu.ksu.cs.malicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = new Intent();
        i.setPackage("edu.ksu.cs.benign");
        i.setClassName("edu.ksu.cs.benign", "edu.ksu.cs.benign.MyService");
        try {
            startService(i);
        } catch (SecurityException se) {
            TextView service_status = (TextView) findViewById(R.id.infoText);
            service_status.setText("Permission denied. Unable to start the service.");
        }
    }
}
