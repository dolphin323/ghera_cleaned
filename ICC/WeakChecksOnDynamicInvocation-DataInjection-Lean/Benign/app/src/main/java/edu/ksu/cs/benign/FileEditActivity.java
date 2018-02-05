package edu.ksu.cs.benign;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FileEditActivity extends AppCompatActivity {

    public static String TAG = "Benign:FileEditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_edit);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tv = (TextView) findViewById(R.id.file);
        EditText et = (EditText) findViewById(R.id.fileData);
        Button save = (Button) findViewById(R.id.save_chng);
        final Intent i = getIntent();
        tv.setText(i.getStringExtra("filename"));
        final String data_in_file = queryContentProvider(i.getStringExtra("filename"));
        et.setText(data_in_file);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.fileData);
                if (isConnectedToInternet()) {
                    //save changes to Database
                    if (insertContentProvider(et.getText().toString()))
                        Log.d(TAG, "changes saved to DB successfully");
                    else Log.d(TAG, "Failed to save changes to DB");
                } else {
                    //save changes to device.
                    if (backup(et.getText().toString(), i))
                        Log.d(TAG, "Data backed up");
                    else Log.d(TAG, "Failed to backup data");
                }
            }
        });

    }

    /*
    Implement this method if you want to query data from a DB through FileContentProvider.
     */
    private String queryContentProvider(String filename) {
        /*
        query Database.
         */
        return "data in " + filename;
    }

    /*
    Implement this method if you want to insert data into a DB through FileContentProvider.
    If insert is successful it returns true else it returns false.
     */
    private boolean insertContentProvider(String data) {
        return false;
    }

    /*
    Implement this method to check for internet connection.
    Return true if connection is successful else return false.
     */
    private Boolean isConnectedToInternet() {
        return false;
    }


    /*
    saves data in the app's internal filesystem
     */
    private boolean backup(String data, Intent i) {
        Uri.Builder ub = new Uri.Builder();
        ub.authority("edu.ksu.cs.benign.filecontentprovider");
        ub.scheme("content");
        Bundle b = new Bundle();
        b.putCharSequence("notes", data);
        getContentResolver().call(ub.build(), "backup", i.getStringExtra("filename"), b);
        return true;
    }
}
