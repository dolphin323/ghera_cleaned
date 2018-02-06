package edu.ksu.cs.malicious;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Button queryBtn = (Button) findViewById(R.id.btnQ);
        final Button insertBtn = (Button) findViewById(R.id.btnI);
        final Button deleteBtn = (Button) findViewById(R.id.btnD);

        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.authority("edu.ksu.cs.benign.myCP");
                uriBuilder.scheme("content");
                getContentResolver().query(uriBuilder.build(), null, null, null, null);
                Toast.makeText(getApplicationContext(), "Check Benign Log", Toast.LENGTH_SHORT).show();
            }
        });

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.authority("edu.ksu.cs.benign.myCP");
                uriBuilder.scheme("content");
                getContentResolver().insert(uriBuilder.build(), new ContentValues());
                Toast.makeText(getApplicationContext(), "Check Benign Log", Toast.LENGTH_SHORT).show();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.authority("edu.ksu.cs.benign.myCP");
                uriBuilder.scheme("content");
                getContentResolver().delete(uriBuilder.build(), null, null);
                Toast.makeText(getApplicationContext(), "Check Benign Log", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
