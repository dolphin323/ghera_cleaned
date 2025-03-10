package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.writetofilelibrary.WriteActivity;


public class MainActivity extends AppCompatActivity {

    private static int LIBRARY_REQUEST_CODE = 101;
    private static String TAG = "Benign/MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fName, fContent;
        Intent i = getIntent();
        if (i != null && i.hasExtra("fileName") && i.hasExtra("fileContent")) {
            fName = i.getStringExtra("fileName");
            fContent = i.getStringExtra("fileContent");
        } else {
            fName = "demo.txt";
            fContent = "demo file";
        }

        Intent libIntent = new Intent(getApplicationContext(), WriteActivity.class);
        libIntent.putExtra("fileName", fName);
        libIntent.putExtra("fileContent", fContent);
        startActivityForResult(libIntent, LIBRARY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TextView resultText = (TextView) findViewById(R.id.result_text);
        if (requestCode == LIBRARY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.d(TAG, "Successful file creation in Library");
                resultText.setText("Successful file creation.");
                Intent i = getIntent();
                if (i != null && i.hasExtra("fileName") && i.hasExtra("fileContent")) {
                    setResult(RESULT_OK);
                    finish();
                }
            } else {
                resultText.setText("File creation failed!");
                Intent i = getIntent();
                if (i != null && i.hasExtra("fileName") && i.hasExtra("fileContent")) {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            }
        } else {
            resultText.setText("Request failed!");
            Intent i = getIntent();
            if (i != null && i.hasExtra("fileName") && i.hasExtra("fileContent")) {
                setResult(RESULT_CANCELED);
                finish();
            }
        }
    }
}