package edu.ksu.cs.benign;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class UserDetailsActivity extends AppCompatActivity {
    private static int RES_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent recvIntent = getIntent();
        String path = recvIntent.getStringExtra("path");
        Uri uri = Uri.parse("content:
        Intent sendIntent = new Intent();
        sendIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.setData(uri);
        setResult(RES_CODE, sendIntent);
    }
}
