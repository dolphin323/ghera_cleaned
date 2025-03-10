package edu.ksu.cs.benign;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "Secure";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup_app();
    }

    @Override
    protected void onResume() {
        final EditText editText = (EditText) findViewById(R.id.id);
        final TextView textView1 = (TextView) findViewById(R.id.name);
        final TextView textView2 = (TextView) findViewById(R.id.school);
        final TextView textView4 = (TextView) findViewById(R.id.ssn);
        Button button1 = (Button) findViewById(R.id.getSchool);
        Button button3 = (Button) findViewById(R.id.getSsn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hmap = getSchool(editText.getText().toString());
                if (hmap != null) {
                    textView1.setText(hmap.get("name"));
                    textView2.setText(hmap.get("school"));
                } else
                    Toast.makeText(getApplicationContext(), "Unable to get data", Toast.LENGTH_SHORT).show();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ssn = getSSN(editText.getText().toString());
                if (ssn != null) {
                    textView4.setText(ssn);
                } else
                    Toast.makeText(getApplicationContext(), "Unable to get data", Toast.LENGTH_SHORT).show();
            }
        });


        super.onResume();
    }

    private HashMap<String, String> getSchool(String id) {
        Uri.Builder uribuilder = new Uri.Builder();
        uribuilder.authority("edu.ksu.cs.benign.userdetails");
        uribuilder.appendEncodedPath("/user/school");
        uribuilder.scheme("content");
        String[] selectionArgs = new String[]{id};
        Cursor cursor = getContentResolver().query(uribuilder.build(), null, null, selectionArgs, null);
        if (cursor != null && cursor.moveToFirst()) {
            HashMap<String, String> hmap = new HashMap<>();
            hmap.put("name", cursor.getString(0) + " " + cursor.getString(1));
            hmap.put("school", cursor.getString(2));
            cursor.close();
            return hmap;
        }
        return null;
    }

    private String getSSN(String id) {
        Uri.Builder uribuilder = new Uri.Builder();
        uribuilder.authority("edu.ksu.cs.benign.userdetails");
        uribuilder.appendEncodedPath("/user/ssn");
        uribuilder.scheme("content");
        String[] selectionArgs = new String[]{id};
        Cursor cursor = getContentResolver().query(uribuilder.build(), null, null, selectionArgs, null);
        if (cursor != null && cursor.moveToFirst()) {
            String str = cursor.getString(1);
            cursor.close();
            return str;
        }
        return null;
    }

    private void setup_app() {
        String user_path = getApplicationContext().getFilesDir().getPath() + "/mockdata/User.csv";
        String addr_path = getApplicationContext().getFilesDir().getPath() + "/mockdata/address/Table1.csv";
        String ssn_path = getApplicationContext().getFilesDir().getPath() + "/mockdata/ssn/Table1.csv";
        String user_data = "Joy,Mitra,KSU";
        String addr_data = "1,Manhattan";
        String ssn_data = "1,123456789";

        File mockdata = new File(getApplicationContext().getFilesDir().getPath() + "/mockdata/");
        if (!mockdata.exists()) mockdata.mkdir();
        File address = new File(getApplicationContext().getFilesDir().getPath() + "/mockdata/address");
        if (!address.exists()) address.mkdir();
        File ssn = new File(getApplicationContext().getFilesDir().getPath() + "/mockdata/ssn");
        if (!ssn.exists()) ssn.mkdir();

        try (FileOutputStream outputStream_user = new FileOutputStream(new File(user_path));
             FileOutputStream outputStream_addr = new FileOutputStream(new File(addr_path));
             FileOutputStream outputStream_ssn = new FileOutputStream(new File(ssn_path))) {
            outputStream_user.write(user_data.getBytes());
            outputStream_addr.write(addr_data.getBytes());
            outputStream_ssn.write(ssn_data.getBytes());
        } catch (IOException e) {
            Log.d(TAG, "setup failed due to IOException");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
