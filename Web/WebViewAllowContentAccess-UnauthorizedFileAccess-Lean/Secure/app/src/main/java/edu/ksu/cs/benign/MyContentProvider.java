package edu.ksu.cs.benign;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) {
        ParcelFileDescriptor parcel = null;
        Log.d("MyContentProvider","fetching: " + uri);

        String path = getContext().getFilesDir().getAbsolutePath() + "/" + uri.getPath();
        File file = new File(path);

        try {
            parcel = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        } catch (FileNotFoundException e) {
            Log.e("MyContentProvider", "uri " + uri.toString(), e);
        }
        return parcel;
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
        
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
