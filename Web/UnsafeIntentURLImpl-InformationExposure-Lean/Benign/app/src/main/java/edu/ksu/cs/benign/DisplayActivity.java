package edu.ksu.cs.benign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        if (getIntent().hasExtra("info")) {
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(getIntent().getStringExtra("info"));
        }
    }
}