package santos.cs.ksu.edu.benign;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;

import santos.cs.ksu.edu.benign.Util.Encrypt;

public class GradesContentProvider extends ContentProvider {
    public GradesContentProvider() {
    }

    private String TAG = "GradesContentProvider";

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uri.getAuthority().equals("santos.cs.ksu.edu.benign.grades")){
            Set<String> keys = values.keySet();
            FileOutputStream fos = null;
            try{
                for (String key: keys) {
                    String grade = (String) values.get(key);
                    String encryptedGrade = Encrypt.encryptGrade(getContext().getResources().getString(R.string.secret),
                            getContext().getResources().getString(R.string.IV),key + " , " + grade);
                    fos = getContext().openFileOutput("scores.txt", Context.MODE_APPEND);
                    fos.write(encryptedGrade.getBytes());
                }
                if(fos != null) fos.close();
                return uri;
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
                return null;
            }
            catch(IOException e){
                e.printStackTrace();
                return null;
            }
            catch(Exception e){
                e.printStackTrace();
                return null;
            }

        }
        else {
            Log.d(TAG,"Wrong authority ... ");
            return null;
        }
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        if(uri.getAuthority().equals("santos.cs.ksu.edu.benign.grades")){
            String[] columnNames = {"stuInfo"};
            try {
                FileInputStream fis = getContext().openFileInput("scores.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                MatrixCursor mCursor = new MatrixCursor(columnNames);
                String line;
                while((line = reader.readLine()) != null){
                    mCursor.addRow(new String[]{line});
                }
                return mCursor;
            }
            catch(FileNotFoundException e){
                e.printStackTrace();
                return null;
            }
            catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
        else return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
