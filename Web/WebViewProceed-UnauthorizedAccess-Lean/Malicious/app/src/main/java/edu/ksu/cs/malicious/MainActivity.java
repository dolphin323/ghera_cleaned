package edu.ksu.cs.malicious;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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