package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Result extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        TextView res = (TextView) findViewById(R.id.result);
        if (i.getStringExtra("Result").length() > 0)
            res.setText(i.getStringExtra("Result"));
    }
}
