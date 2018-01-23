package edu.ksu.cs.benign.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        // Implement this to handle requests to delete one or more rows.
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        return null;
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        MatrixCursor cursor = null;
        BufferedReader bufferedReader = null;
        String path;
        File file;
        switch (sUriMatcher.match(uri)) {
            case 1:
                path = getContext().getFilesDir().getPath() + "/mockdata/User.csv";
                //path = "/data/data/edu.ksu.cs.benign/files/mockdata/User.csv";
                file = new File(path);
                try {
                    FileInputStream fis = new FileInputStream(file);
                    bufferedReader = new BufferedReader(new InputStreamReader(fis));
                    String line;
                    cursor = new MatrixCursor(new String[]{"FirstNm", "LastNm", "University"});
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] elems = line.split(",");
                        if (selectionArgs[0].equals(elems[0])) {
                            ArrayList columnValues = new ArrayList();
                            if (elems.length == 4) {
                                columnValues.add(elems[1]);
                                columnValues.add(elems[2]);
                                columnValues.add(elems[3]);
                                cursor.addRow(columnValues);
                            }


                        }
                    }
                } catch (FileNotFoundException fne) {
                    Log.d("UserDetailsCP...", file + " not found");
                    fne.printStackTrace();
                } catch (IOException ioe) {
                    Log.d("UserDetailsCP...", "error while reading " + file);
                    ioe.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException ioe) {
                            Log.d("UserDetailsCP...", "error while closing " + file);
                            ioe.printStackTrace();
                        }

                    }
                }
                break;
            case 2:
                path = getContext().getFilesDir().getPath() + "/mockdata/address/Table1.csv";
                //path = "/data/data/edu.ksu.cs.benign/files/mockdata/address/Table1.csv";
                file = new File(path);
                try {
                    FileInputStream fis = new FileInputStream(file);
                    bufferedReader = new BufferedReader(new InputStreamReader(fis));
                    String line;
                    cursor = new MatrixCursor(new String[]{"ID", "City"});
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] elems = line.split(",");
                        if (selectionArgs[0].equals(elems[0])) {
                            ArrayList columnValues = new ArrayList();
                            if (elems.length == 2) {
                                columnValues.add(elems[0]);
                                columnValues.add(elems[1]);
                                cursor.addRow(columnValues);
                            }


                        }
                    }
                } catch (FileNotFoundException fne) {
                    Log.d("UserDetailsCP...", file + " not found");
                    fne.printStackTrace();
                } catch (IOException ioe) {
                    Log.d("UserDetailsCP...", "error while reading " + file);
                    ioe.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException ioe) {
                            Log.d("UserDetailsCP...", "error while closing " + file);
                            ioe.printStackTrace();
                        }

                    }
                }
                break;

            case 3:
                path = getContext().getFilesDir().getPath() + "/mockdata/ssn/Table1.csv";
                //path = "/data/data/edu.ksu.cs.benign/files/mockdata/ssn/Table1.csv";
                file = new File(path);
                try {
                    FileInputStream fis = new FileInputStream(file);
                    bufferedReader = new BufferedReader(new InputStreamReader(fis));
                    String line;
                    cursor = new MatrixCursor(new String[]{"ID", "SSN"});
                    while ((line = bufferedReader.readLine()) != null) {
                        String[] elems = line.split(",");
                        if (selectionArgs[0].equals(elems[0])) {
                            ArrayList columnValues = new ArrayList();
                            if (elems.length == 2) {
                                columnValues.add(elems[0]);
                                columnValues.add(elems[1]);
                                cursor.addRow(columnValues);
                            }


                        }
                    }
                } catch (FileNotFoundException fne) {
                    Log.d("UserDetailsCP...", file + " not found");
                    fne.printStackTrace();
                } catch (IOException ioe) {
                    Log.d("UserDetailsCP...", "error while reading " + file);
                    ioe.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException ioe) {
                            Log.d("UserDetailsCP...", "error while closing " + file);
                            ioe.printStackTrace();
                        }

                    }
                }
                break;


        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        return 0;
    }
}
