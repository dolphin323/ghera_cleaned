package edu.ksu.cs.benign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        Intent intent = getIntent();
        if (intent.hasExtra("status_msg") &&
                intent.getStringExtra("status_msg").equals("SUCCESS") &&
                intent.hasExtra("response_msg")) {

            TextView header = (TextView) findViewById(R.id.headingText);
            header.setText("Response From Server:");

            String responseMsg = getIntent().getStringExtra("response_msg");
            TextView responseText = (TextView) findViewById(R.id.serverResponseText);
            responseText.setText(responseMsg);
        }
    }
}