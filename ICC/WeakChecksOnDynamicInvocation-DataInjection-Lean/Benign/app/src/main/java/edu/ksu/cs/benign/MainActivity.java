package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Button edit1 = (Button) findViewById(R.id.button1);
        final TextView file = (TextView) findViewById(R.id.textView1);
        final TextView file_status = (TextView) findViewById(R.id.file_status);
        final Button getFile_btn = (Button) findViewById(R.id.getFile);
        edit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FileEditActivity.class).putExtra("filename", file.getText().toString()));
            }
        });
        getFile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(getApplicationContext().getFilesDir(), "MalFile.sh");
                if (file.exists()) {
                    file_status.setText("True");
                } else {
                    file_status.setText("False");
                }
            }
        });
    }
}
