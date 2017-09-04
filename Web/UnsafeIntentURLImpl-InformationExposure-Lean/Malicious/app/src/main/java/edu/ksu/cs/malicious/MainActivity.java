package edu.ksu.cs.malicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if(intent != null){
            Toast.makeText(getApplicationContext(),intent.getStringExtra("info"),Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(getApplicationContext(),"Malicious",Toast.LENGTH_SHORT).show();
    }
}
