package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MalImageEditor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal_image_editor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String info = intent.getStringExtra("image");
        TextView disp = (TextView) findViewById(R.id.disp);
        disp.setText(info);
    }
}
