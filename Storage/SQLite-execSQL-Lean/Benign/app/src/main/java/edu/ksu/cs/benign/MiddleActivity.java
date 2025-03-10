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
    private String key, value;

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
            key = intent.getStringExtra("key");
            value = intent.getStringExtra("value");
            query();
        }
    }

    private void displayResult(int keyNum, String key, String value) {
        if (keyNum == 0) {
            TextView first = (TextView) findViewById(R.id.firstKey);
            first.setText("Key: " + key + " value: " + value);
        } else {
            TextView second = (TextView) findViewById(R.id.secondKey);
            second.setText("Key: " + key + " value: " + value);
        }
    }

    protected void query() {
        Log.d(TAG, "Query operation");
        SQLiteDatabase db = MyDatabaseHelper.getInstance(getApplicationContext()).getReadableDatabase();
        String query = "UPDATE " + MyDatabase.Table1.TABLE_NAME
                + " SET " + MyDatabase.Table1.COLUMN_NAME_VALUE + " = \'" + value + "\'"
                + " WHERE " + MyDatabase.Table1.COLUMN_NAME_KEY + " = \'" + key + "\'";
        Log.d("Q", query);
        try {
            db.execSQL(query);
            Log.d(TAG, "Query completed");
        } catch (Exception e) {
            Log.d("E", e.toString());
        } finally {
            currentSnapshotOfTable();
        }
    }

    private void currentSnapshotOfTable() {
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
            cursorData.putExtra("key", list.get(0));
            cursorData.putExtra("value", list.get(1));
            cursor.close();
            setResult(RESULT_OK, cursorData);
        } else {
            Log.d(TAG, "cursor is null");
            setResult(RESULT_CANCELED);
        }
    }

    private List gettingAllTheData(Cursor cursor) {
        String[] keyData = new String[cursor.getCount()];
        String[] valueData = new String[cursor.getCount()];

        int loopCount = 0;
        while (cursor.moveToNext()) {
            String key = cursor.getString(cursor.getColumnIndex("key"));
            String value = cursor.getString(cursor.getColumnIndex("value"));
            displayResult(loopCount, key, value);
            Log.d(TAG, key + " " + value);
            keyData[loopCount] = key;
            valueData[loopCount] = value;
            ++loopCount;
        }

        List<String[]> essentialData = new ArrayList<String[]>();
        essentialData.add(keyData);
        essentialData.add(valueData);
        return essentialData;
    }
}
