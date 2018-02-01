package edu.ksu.cs.benign;

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
        TextView res = (TextView) findViewById(R.id.result);
        res.setText("Unable to write to external storage");
    }
}

