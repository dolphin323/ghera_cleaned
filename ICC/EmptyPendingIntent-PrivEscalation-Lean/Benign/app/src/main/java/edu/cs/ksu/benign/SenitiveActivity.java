package edu.cs.ksu.benign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SenitiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senitive);
    }

    @Override
    protected void onResume(){
        super.onResume();
        TextView textView = (TextView) findViewById(R.id.sensitive_activity_msg);
        String msg = "Sensitive Activity visible";
        textView.setText(msg);
    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        stopService(new Intent(getApplicationContext(),MySensitiveService.class));
    }
}
