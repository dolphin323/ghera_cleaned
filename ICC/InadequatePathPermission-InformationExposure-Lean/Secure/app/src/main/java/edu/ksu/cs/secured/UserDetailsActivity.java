package edu.ksu.cs.secured;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class UserDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent recvIntent = getIntent();
        String path = recvIntent.getStringExtra("path");
        Uri uri = Uri.parse("content://edu.ksu.cs.benign.userdetails" + path);
        Intent sendIntent = new Intent();
        sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.setData(uri);
        setResult(100,sendIntent);
    }
}
