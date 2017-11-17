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
        getContentResolver().insert(uriBuilder.build(),null);
        /*SQLiteDatabase db = MyDatabaseHelper.getInstance(getApplicationContext()).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDatabase.Table1.COLUMN_NAME_USER,"joymitro");
        contentValues.put(MyDatabase.Table1.COLUMN_NAME_PASS,"joypass");
        db.insert(MyDatabase.Table1.TABLE_NAME,null,contentValues);
        contentValues.put(MyDatabase.Table1.COLUMN_NAME_USER,"janedoe");
        contentValues.put(MyDatabase.Table1.COLUMN_NAME_PASS,"329#DSkdisW");
        db.insert(MyDatabase.Table1.TABLE_NAME,null,contentValues);
        contentValues.put(MyDatabase.Table1.COLUMN_NAME_USER,"jacikortna");
        contentValues.put(MyDatabase.Table1.COLUMN_NAME_PASS,"kakj4737");
        db.insert(MyDatabase.Table1.TABLE_NAME,null,contentValues);
        contentValues.put(MyDatabase.Table1.COLUMN_NAME_USER,"jamilaujka");
        contentValues.put(MyDatabase.Table1.COLUMN_NAME_PASS,"password");
        db.insert(MyDatabase.Table1.TABLE_NAME,null,contentValues);
        contentValues.put(MyDatabase.Table1.COLUMN_NAME_USER,"jamie");
        contentValues.put(MyDatabase.Table1.COLUMN_NAME_PASS,"password1234");
        db.insert(MyDatabase.Table1.TABLE_NAME,null,contentValues);*/
    }

    @Override
    protected void onResume(){
        super.onResume();
        String[] projection = {MyDatabase.Table1.COLUMN_NAME_USER,MyDatabase.Table1.COLUMN_NAME_PASS};
        String selection = MyDatabase.Table1.COLUMN_NAME_USER + " = ? AND " + MyDatabase.Table1.COLUMN_NAME_PASS + " = ?";
        String[] selectionArgs = {"joymitro","joypass"};
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.authority("edu.ksu.cs.benign.AUTH_CP");
        uriBuilder.scheme("content");
        Cursor cursor = getContentResolver().query(uriBuilder.build(),projection,selection,selectionArgs,null);
        if (cursor != null){
            Log.d(TAG,"cursor count = " + cursor.getCount());
            while(cursor.moveToNext()){
                String username = cursor.getString(cursor.getColumnIndex(MyDatabase.Table1.COLUMN_NAME_USER));
                String password = cursor.getString(cursor.getColumnIndex(MyDatabase.Table1.COLUMN_NAME_PASS));
                Log.d(TAG,"username = " + username);
                Log.d(TAG, "password = " + password);
            }
            cursor.close();
        }
        else Log.d(TAG, "cursor is null");
    }
}
