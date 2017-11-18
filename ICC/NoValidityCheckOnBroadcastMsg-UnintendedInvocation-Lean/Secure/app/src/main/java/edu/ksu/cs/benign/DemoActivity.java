package edu.ksu.cs.benign;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "5554"));
        intent.putExtra("sms_body", "message");
        startActivity(intent);
    }
}
