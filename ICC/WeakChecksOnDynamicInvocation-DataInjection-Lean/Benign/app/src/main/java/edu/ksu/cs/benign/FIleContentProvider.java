package edu.ksu.cs.benign;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FIleContentProvider extends ContentProvider {
    public FIleContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return uri;
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        return 0;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        if (method != null
                && method.equals("backup")) {
            String data = extras.getCharSequence("notes").toString();
            String fileName = arg;
            File file = new File(getContext().getFilesDir(), fileName);
            try (BufferedOutputStream bf = new BufferedOutputStream(new FileOutputStream(file))) {
                bf.write(data.getBytes());
                Log.d("Backup ...", "Data backed up!");
            } catch (FileNotFoundException fne) {
                Log.d("BackUp ...", fileName + " not found");
                fne.printStackTrace();
            } catch (IOException ioe) {
                Log.d("BackUp ...", "write failed");
                ioe.printStackTrace();
            }
        }
        return null;
    }
}