package edu.ksu.cs.benign.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UserDetailsContentProvider extends ContentProvider {
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI("edu.ksu.cs.benign.userdetails", "/user/school", 1);
        sUriMatcher.addURI("edu.ksu.cs.benign.userdetails", "/user/address", 2);
        sUriMatcher.addURI("edu.ksu.cs.benign.userdetails", "/user/ssn", 3);
    }

    public UserDetailsContentProvider() {
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
        
        return null;
    }

    @Override
    public boolean onCreate() {
        
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = null;
        String path;
        File file;
        switch (sUriMatcher.match(uri)) {
            case 1:
                path = getContext().getFilesDir().getPath() + "/mockdata/User.csv";
                file = new File(path);
                try (BufferedReader bufferedReader =
                             new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                    String line;
                    cursor = new MatrixCursor(new String[]{"FirstNm", "LastNm", "University"});
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] elems = line.split(",");
                        if (selectionArgs[0].equals(elems[0])) {
                            ArrayList<String> columnValues = new ArrayList<>();
                            if (elems.length == 4) {
                                columnValues.add(elems[1]);
                                columnValues.add(elems[2]);
                                columnValues.add(elems[3]);
                                cursor.addRow(columnValues);
                            }
                        }
                    }
                } catch (IOException e) {
                    Log.d("UserDetailsCP...", "error while reading " + file);
                    e.printStackTrace();
                    return null;
                }
                break;
            case 2:
                path = getContext().getFilesDir().getPath() + "/mockdata/address/Table1.csv";
                file = new File(path);
                try (BufferedReader bufferedReader =
                             new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                    String line;
                    cursor = new MatrixCursor(new String[]{"ID", "City"});
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] elems = line.split(",");
                        if (selectionArgs[0].equals(elems[0])) {
                            ArrayList<String> columnValues = new ArrayList<>();
                            if (elems.length == 2) {
                                columnValues.add(elems[0]);
                                columnValues.add(elems[1]);
                                cursor.addRow(columnValues);
                            }
                        }
                    }
                } catch (IOException e) {
                    Log.d("UserDetailsCP...", "error while reading " + file);
                    e.printStackTrace();
                    return null;
                }
                break;

            case 3:
                path = getContext().getFilesDir().getPath() + "/mockdata/ssn/Table1.csv";
                file = new File(path);
                try (BufferedReader bufferedReader =
                             new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                    String line;
                    cursor = new MatrixCursor(new String[]{"ID", "SSN"});
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] elems = line.split(",");
                        if (selectionArgs[0].equals(elems[0])) {
                            ArrayList<String> columnValues = new ArrayList<>();
                            if (elems.length == 2) {
                                columnValues.add(elems[0]);
                                columnValues.add(elems[1]);
                                cursor.addRow(columnValues);
                            }
                        }
                    }
                } catch (IOException e) {
                    Log.d("UserDetailsCP...", "error while reading " + file);
                    e.printStackTrace();
                    return null;
                }
                break;
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        
        return 0;
    }
}
