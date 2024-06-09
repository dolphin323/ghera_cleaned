package edu.ksu.cs.benign;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import edu.ksu.cs.benign.Util.Encrypt;

public class GradesContentProvider extends ContentProvider {
    private String TAG = "GradesContentProvider";

    public GradesContentProvider() {
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
        if (uri.getAuthority().equals("edu.ksu.cs.benign.grades")) {
            Set<String> keys = values.keySet();
            FileOutputStream fos = null;
            try {
                for (String key : keys) {
                    String grade = (String) values.get(key);
                    String encryptedGrade = Encrypt.encryptGrade(getContext().getResources().getString(R.string.salt), key + " , " + grade);
                    fos = getContext().openFileOutput("scores.txt", Context.MODE_APPEND);
                    fos.write(encryptedGrade.getBytes());
                }
                if (fos != null) fos.close();
                return uri;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        } else {
            Log.d(TAG, "Wrong authority ... ");
            return null;
        }
    }

    @Override
    public boolean onCreate() {
        
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        FileInputStream fis = null;
        if (uri.getAuthority().equals("edu.ksu.cs.benign.grades")) {
            String[] columnNames = {"stuInfo"};
            try {
                fis = getContext().openFileInput("scores.txt");
            } catch (FileNotFoundException e) {
                Log.d(TAG, "scores.txt not found");
                return null;
            } catch (NullPointerException e) {
                Log.d(TAG, "context not found");
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            MatrixCursor mCursor = new MatrixCursor(columnNames);
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    mCursor.addRow(new String[]{line});
                }
            } catch (IOException e) {
                Log.d(TAG, "IOException while reading file");
                return null;
            }
            return mCursor;
        } else
            return null;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
