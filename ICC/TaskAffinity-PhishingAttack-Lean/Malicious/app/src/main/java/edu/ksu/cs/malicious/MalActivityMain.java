package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MalActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal_main);
        startActivity(new Intent(getApplicationContext(), MalActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }
}
