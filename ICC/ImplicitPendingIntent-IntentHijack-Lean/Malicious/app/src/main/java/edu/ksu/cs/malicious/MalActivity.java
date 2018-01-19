package edu.ksu.cs.malicious;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume(){
        super.onResume();
        String text = getIntent().getStringExtra("pwd");
        TextView tv = (TextView) findViewById(R.id.disp);
        if(text != null) tv.setText(text);
    }
}
