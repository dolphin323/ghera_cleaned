package edu.ksu.cs.benign;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MiddleActivity extends AppCompatActivity {
    private static String TAG = "Benign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_middle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        int reqCode = intent.getIntExtra("requestCode", 0);
        if (reqCode == 100) {
            String usrname = intent.getStringExtra("username");
            String pass = intent.getStringExtra("password");
            String selection = MyDatabase.Table1.COLUMN_NAME_USER + "=" + usrname + " AND " + MyDatabase.Table1.COLUMN_NAME_PASS + "=" + pass;
            Uri.Builder uriBuilder = new Uri.Builder();
            uriBuilder.authority("edu.ksu.cs.benign.AUTH_CP");
            uriBuilder.scheme("content");
            Cursor cursor = getContentResolver().query(uriBuilder.build(), null, selection, null, null);
            cursorInspection(cursor);
        }
    }

    private void cursorInspection(Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            Log.d(TAG, "cursor count = " + cursor.getCount());
            Intent cursorData = new Intent();
            List<String[]> list = gettingAllTheData(cursor);
            cursorData.putExtra("username", list.get(0));
            cursorData.putExtra("password", list.get(1));
            cursor.close();
            setResult(RESULT_OK, cursorData);
        } else {
            Log.d(TAG, "cursor is null");
            setResult(RESULT_CANCELED);
        }
    }

    private List gettingAllTheData(Cursor cursor) {
        String[] usernameData = new String[cursor.getCount()];
        String[] passwordData = new String[cursor.getCount()];

        int loopCount = 0;
        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            Log.d(TAG, username + password);
            usernameData[loopCount] = username;
            passwordData[loopCount] = password;
            ++loopCount;
        }
        List<String[]> essentialData = new ArrayList<String[]>();
        essentialData.add(usernameData);
        essentialData.add(passwordData);
        return essentialData;
    }
}
