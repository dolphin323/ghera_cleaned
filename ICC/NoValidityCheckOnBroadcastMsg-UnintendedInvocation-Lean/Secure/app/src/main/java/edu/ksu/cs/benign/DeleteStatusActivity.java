package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class DeleteStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String status = getIntent().getStringExtra("status");
        TextView msg = (TextView) findViewById(R.id.status);
        msg.setText(status);
    }
}
