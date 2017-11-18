package edu.cs.ksu.secured;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = new Intent();
        i.setComponent(new ComponentName(getPackageName(), "edu.cs.ksu.secured.MyService"));
        ComponentName c = startService(i);
        Toast.makeText(getApplicationContext(),c.toString(),Toast.LENGTH_LONG).show();

    }
}
