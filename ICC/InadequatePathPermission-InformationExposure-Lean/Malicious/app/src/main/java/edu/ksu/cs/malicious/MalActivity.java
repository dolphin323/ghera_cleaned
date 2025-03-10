package edu.ksu.cs.malicious;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button ssnBtn = (Button) findViewById(R.id.getSsn);
        ssnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) findViewById(R.id.ssnDisp);
                tv.setText(getSSN("1"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 99 && resultCode == 100) {
            Uri uri = data.getData();
            String[] selectionArgs = new String[]{"1"};
            Cursor cursor = getContentResolver().query(uri, null, null, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                TextView tv = (TextView) findViewById(R.id.ssnDisp);
                tv.setText(cursor.getString(1));
                cursor.close();
            }
        }
    }

    private String getSSN(String id) {
        Uri.Builder uribuilder = new Uri.Builder();
        uribuilder.authority("edu.ksu.cs.benign.userdetails");
        uribuilder.appendEncodedPath("/user/ssn");
        uribuilder.scheme("content");
        String[] selectionArgs = new String[]{id};
        try {
            Cursor cursor = getContentResolver().query(uribuilder.build(), null, null, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                String str = cursor.getString(1);
                cursor.close();
                return str;
            }
        }
        catch (SecurityException e) {
            return "SecurityException";
        }
        return null;
    }
}
