package edu.ksu.cs.benign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResponseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_response);

        if (getIntent().hasExtra("error_msg")) {
            TextView textView = (TextView) findViewById(R.id.errorText);
            textView.setText(getIntent().getStringExtra("error_msg"));
        }
    }
}