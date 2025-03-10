package edu.ksu.cs.benign;

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
        TextView resText = (TextView) findViewById(R.id.result);
        super.onResume();
        Intent i = getIntent();
        String res = i.getStringExtra("Result");
        resText.setText(res);
    }
}
