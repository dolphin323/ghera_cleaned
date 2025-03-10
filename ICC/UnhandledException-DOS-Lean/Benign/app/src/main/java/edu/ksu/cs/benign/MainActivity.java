package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button = (Button) findViewById(R.id.get);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) findViewById(R.id.concat);
                Intent intent = getIntent();
                String a = intent.getStringExtra("s1");
                String b = intent.getStringExtra("s2");
                textView.setText("total length:" + Integer.toString(a.length() + b.length()));
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        System.exit(0);
    }
}
