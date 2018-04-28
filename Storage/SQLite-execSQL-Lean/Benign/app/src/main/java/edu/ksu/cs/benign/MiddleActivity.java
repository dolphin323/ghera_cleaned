package edu.ksu.cs.benign;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MiddleActivity extends AppCompatActivity {
    private static String TAG = "Benign";
    private String usrname, pass;

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
            usrname = intent.getStringExtra("username");
            pass = intent.getStringExtra("password");
            query();
        }
    }

    private void displayResult(int userNum, String username, String pass) {
        if (userNum == 0) {
            TextView first = (TextView) findViewById(R.id.firstUser);
            first.setText("Username: " + username + " Password: " + pass);
        } else {
            TextView second = (TextView) findViewById(R.id.secondUser);
            second.setText("Username: " + username + "Password: " + pass);
        }
    }

    protected void query() {
        Log.d(TAG, "Query operation");
        SQLiteDatabase db = MyDatabaseHelper.getInstance(getApplicationContext()).getReadableDatabase();
        String query = "UPDATE " + MyDatabase.Table1.TABLE_NAME
                + " SET " + MyDatabase.Table1.COLUMN_NAME_PASS + " = \'" + pass + "\'"
                + " WHERE " + MyDatabase.Table1.COLUMN_NAME_USER + " = \'" + usrname + "\'";
        Log.d("Q", query);
        try {
            db.execSQL(query);
            Log.d(TAG, "Query completed");
        } catch (Exception e) {
            Log.d("E", e.toString());
        } finally {
            currentScreenshotOfTable();
        }
    }

    private void currentScreenshotOfTable() {
        SQLiteDatabase db = MyDatabaseHelper.getInstance(getApplicationContext()).getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM " + MyDatabase.Table1.TABLE_NAME, null);
        cursorInspection(cursor);
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
            displayResult(loopCount, username, password);
            Log.d(TAG, username + " " + password);
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
