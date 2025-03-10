package edu.ksu.cs.benign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        if (getIntent().hasExtra("status_msg")) {
            String status = getIntent().getStringExtra("status_msg");
            if (status.equals("FAILURE")) {
                TextView header = (TextView) findViewById(R.id.headingText);
                header.setText("SSL Handshake failed");
            }
        }
    }
}