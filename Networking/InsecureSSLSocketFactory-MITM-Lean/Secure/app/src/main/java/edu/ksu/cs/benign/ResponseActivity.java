package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);
        Intent i = getIntent();
        if (i.hasExtra("status_msg") && i.getStringExtra("status_msg").equals("FAILURE")) {
            TextView header = (TextView) findViewById(R.id.headingText);
            header.setText("SSL Handshake failed");
        }
    }
}