package edu.ksu.cs.benign;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Benign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority("edu.ksu.cs.benign.AUTH_CP");
        uriBuilder.scheme("content");
        getContentResolver().insert(uriBuilder.build(), null);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
