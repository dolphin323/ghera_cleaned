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
//        String temp = getIntent().getStringExtra("result");
        TextView res = (TextView) findViewById(R.id.result);
//        if (temp.equals("1")) {
            res.setText("Written to external storage");
//        } else {
//            res.setText("Unable to write to external storage");
//        }
    }
}