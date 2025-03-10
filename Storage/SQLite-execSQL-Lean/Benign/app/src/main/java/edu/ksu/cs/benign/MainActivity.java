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

    private static String TAG = "Benign";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        final SQLiteDatabase db = MyDatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
        super.onResume();
        Button insert = (Button) findViewById(R.id.insertButton);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Benign", "Insert operation");
                ContentValues contentValues = new ContentValues();
                contentValues.put(MyDatabase.Table1.COLUMN_NAME_KEY, "1");
                contentValues.put(MyDatabase.Table1.COLUMN_NAME_VALUE, "100");
                db.insert(MyDatabase.Table1.TABLE_NAME, null, contentValues);
                contentValues.put(MyDatabase.Table1.COLUMN_NAME_KEY, "2");
                contentValues.put(MyDatabase.Table1.COLUMN_NAME_VALUE, "200");
                db.insert(MyDatabase.Table1.TABLE_NAME, null, contentValues);
                Log.d("Benign", "Insert completed");
                TextView res = (TextView) findViewById(R.id.res);
                res.setText("Successfully inserted!");
            }
        });

        Button truncateTable = (Button) findViewById(R.id.truncateButton);
        truncateTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("DELETE FROM " + MyDatabase.Table1.TABLE_NAME);
                TextView res = (TextView) findViewById(R.id.res);
                res.setText("Successfully truncated!");
            }
        });
    }

}
