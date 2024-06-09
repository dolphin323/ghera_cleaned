package edu.ksu.cs.secure;

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

import edu.ksu.cs.secure.Util.Encrypt;

public class GradesContentProvider extends ContentProvider {
    private String TAG = "SecureGradesContentProvider";

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
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (uri.getAuthority().equals("edu.ksu.cs.benign.grades")) {
            Set<String> keys = values.keySet();
            FileOutputStream fos = null;
            for (String key : keys) {
                String grade = (String) values.get(key);
                String encryptedGrade = Encrypt.encryptGrade(key + " , " + grade);
                try {
                    fos = getContext().openFileOutput("scores.txt", Context.MODE_APPEND);
                } catch (FileNotFoundException e) {
                    Log.d(TAG, "scores.txt not found");
                    return null;
                }
                try {
                    fos.write(encryptedGrade.getBytes());
                } catch (IOException e) {
                    Log.d(TAG, "Error while writing to scores.txt");
                    return null;
                } catch (NullPointerException e) {
                    Log.d(TAG, "encryptedGrade is possibly null");
                    return null;
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.d(TAG, "Error while closing fos");
                }
            }
            return uri;

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
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if (uri.getAuthority().equals("edu.ksu.cs.benign.grades")) {
            String[] columnNames = {"stuInfo"};
            try {
                FileInputStream fis = getContext().openFileInput("scores.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                MatrixCursor mCursor = new MatrixCursor(columnNames);
                String line;
                while ((line = reader.readLine()) != null) {
                    mCursor.addRow(new String[]{line});
                }
                return mCursor;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
