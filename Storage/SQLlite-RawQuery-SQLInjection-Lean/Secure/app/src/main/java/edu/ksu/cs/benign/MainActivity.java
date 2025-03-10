package edu.ksu.cs.benign;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Secure";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Button insert = (Button) findViewById(R.id.insertButton);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Insert operation");
                SQLiteDatabase db = MyDatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyDatabase.Table1.COLUMN_NAME_USER, "Jhontu");
                contentValues.put(MyDatabase.Table1.COLUMN_NAME_PASS, "passOfJhontu");
                db.insert(MyDatabase.Table1.TABLE_NAME, null, contentValues);
                contentValues.put(MyDatabase.Table1.COLUMN_NAME_USER, "janedoe");
                contentValues.put(MyDatabase.Table1.COLUMN_NAME_PASS, "329#DSkdisW");
                db.insert(MyDatabase.Table1.TABLE_NAME, null, contentValues);
                Log.d(TAG, "Insert completed");
                TextView res = (TextView) findViewById(R.id.res);
                res.setText("Successfully inserted!");
            }
        });
    }
}
