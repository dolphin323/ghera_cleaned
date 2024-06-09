package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Secure";
    static final String ALIAS = "SECRET_KEY";
    static SecretKey secretKey = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView result = (TextView) findViewById(R.id.result_text);

        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            secretKey = keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        KeyStore keyStore = null;
        try {
            keyStore = java.security.KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);

            
            
            KeyStore.ProtectionParameter pp = new KeyStore.PasswordProtection(new char[]{'s', 'e', 'c', 'u', 'r', 'e'});
            keyStore.setEntry(ALIAS, new KeyStore.SecretKeyEntry(secretKey), pp);
            File file = new File(getExternalFilesDir(null), "secretKeyStore");
            FileOutputStream fos = new FileOutputStream(file);
            keyStore.store(fos, null);
            result.setText("Successfully saved key in the keystore");
            Log.d(TAG, "Successfully saved key in the keystore");
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            result.setText("Failed to save key in the keystore");
            Log.d(TAG, "Failed to save key in the keystore");
        }
    }
}
