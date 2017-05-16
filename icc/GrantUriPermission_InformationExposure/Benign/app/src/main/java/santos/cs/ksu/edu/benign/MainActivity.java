package santos.cs.ksu.edu.benign;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Benign";
    private static final String GET_DOCS = "santos.cs.ksu.edu.benign.docs";
    private static final String GET_SENS_DOCS = "santos.cs.ksu.edu.benign.sens.docs";
    private static final String GET_CONTACTS = "santos.cs.ksu.edu.benign.contacts";
    private static final String AUTHORITY = "santos.cs.ksu.edu.benign.MyFileProvider";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        String action = getIntent().getAction();
        switch(action){
            case GET_DOCS:
                File docsPath = new File(getFilesDir(), "docs");
                File docsFile = new File(docsPath,"output.txt");
                Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), AUTHORITY, docsFile);
                String pkgName = getIntent().getStringExtra("package");
                grantUriPermission(pkgName,contentUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Intent result = new Intent();
                result.setData(contentUri);
                setResult(100,result);
            case GET_SENS_DOCS:
                File sdocsPath = new File(getFilesDir(), "sensitive_docs");
                File sFile = new File(sdocsPath,"credentials.txt");
                Uri scontentUri = FileProvider.getUriForFile(getApplicationContext(), AUTHORITY, sFile);
                String spkgName = getIntent().getStringExtra("package");
                grantUriPermission(spkgName,scontentUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                result = new Intent();
                result.setData(scontentUri);
                setResult(101,result);
            case GET_CONTACTS:
                File conPath = new File(getFilesDir(), "contacts");
                File file = new File(conPath,"Person1.txt");
                Uri conUri = FileProvider.getUriForFile(getApplicationContext(), AUTHORITY, file);
                String cpkgName = getIntent().getStringExtra("package");
                grantUriPermission(cpkgName,conUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                result = new Intent();
                result.setData(conUri);
                setResult(102,result);
        }
        finish();
    }
}
