package edu.ksu.cs.secure;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
        String[] projection = {MyDatabase.Table1.COLUMN_NAME_USER, MyDatabase.Table1.COLUMN_NAME_PASS};
        String selection = MyDatabase.Table1.COLUMN_NAME_USER + " = ? AND " + MyDatabase.Table1.COLUMN_NAME_PASS + " = ?";
        String[] selectionArgs = {"joymitro", "joypass"};
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority("edu.ksu.cs.benign.AUTH_CP");
        uriBuilder.scheme("content");
        Cursor cursor = getContentResolver().query(uriBuilder.build(), projection, selection, selectionArgs, null);
        if (cursor != null) {
            Log.d(TAG, "cursor count = " + cursor.getCount());
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex(MyDatabase.Table1.COLUMN_NAME_USER));
                String password = cursor.getString(cursor.getColumnIndex(MyDatabase.Table1.COLUMN_NAME_PASS));
                Log.d(TAG, "username = " + username);
                Log.d(TAG, "password = " + password);
            }
            cursor.close();
        } else {
            Log.d(TAG, "cursor is null");

        }
    }
}
