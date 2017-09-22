package edu.ksu.cs.benign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        startService(new Intent(getApplicationContext(),MyIntentService.class));
        Toast.makeText(getApplicationContext(),"after MyIntentService",Toast.LENGTH_SHORT).show();
    }


}
