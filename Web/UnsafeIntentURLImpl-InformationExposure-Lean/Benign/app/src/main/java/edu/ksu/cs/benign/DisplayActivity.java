package edu.ksu.cs.benign;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        String info = getIntent().getStringExtra("info");
        Uri uri = getIntent().getData();
        Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();
    }
}
