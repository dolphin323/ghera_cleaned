package edu.cs.ksu.malicious;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume(){
        super.onResume();
        String msg = getIntent().getStringExtra("msg");
        TextView tv = (TextView) findViewById(R.id.disp);
        if(msg != null)
            tv.setText(getIntent().getStringExtra("msg"));
    }
}
