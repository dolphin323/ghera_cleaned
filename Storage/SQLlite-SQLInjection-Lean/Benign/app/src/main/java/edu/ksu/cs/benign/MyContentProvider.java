package edu.ksu.cs.benign;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    private static String TAG = "Benign/MyContentProvider";

    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        
        
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        try {
            SQLiteDatabase db = MyDatabaseHelper.getInstance(getContext()).getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MyDatabase.Table1.COLUMN_NAME_USER, "Jhontu");
            contentValues.put(MyDatabase.Table1.COLUMN_NAME_PASS, "Jhontupass");
            db.insert(MyDatabase.Table1.TABLE_NAME, null, contentValues);
            contentValues.put(MyDatabase.Table1.COLUMN_NAME_USER, "janedoe");
            contentValues.put(MyDatabase.Table1.COLUMN_NAME_PASS, "329#DSkdisW");
            db.insert(MyDatabase.Table1.TABLE_NAME, null, contentValues);
            return uri;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean onCreate() {
        
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = MyDatabaseHelper.getInstance(getContext()).getReadableDatabase();
        Cursor cursor = null;
        cursor = db.query(MyDatabase.Table1.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
