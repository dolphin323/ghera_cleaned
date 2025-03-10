package edu.ksu.cs.benign;

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
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "Benign/ResetPassword";
    static final String PROVIDED_TOKEN_KEY = "generatedToken";
    static final String INIT_VECTOR_KEY = "initVector";
    static final byte[] SECRET_KEY = new byte[16];

    static {
        new SecureRandom().nextBytes(SECRET_KEY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Intent intent = new Intent(getApplicationContext(), NewPasswordActivity.class);

        findViewById(R.id.getToken).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailAddrText = (EditText) findViewById(R.id.emailEdit);
                String emailAddr = emailAddrText.getText().toString();
                if (emailAddr == null || emailAddr.equals("")) {
                    Toast.makeText(getApplicationContext(), "Invalid email",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Cipher cipher;
                try {
                    cipher = Cipher.getInstance("AES/GCM/NoPadding");
                } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                    Log.d(TAG, "Exception while creating cipher instance", e);
                    Toast.makeText(getApplicationContext(), "Cipher error",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET_KEY, "AES");
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
                    Log.d(TAG, "Token = " + token);
                    Toast.makeText(getApplicationContext(), "Token emailed!",
                            Toast.LENGTH_LONG).show();
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
            }
        });

        findViewById(R.id.resetPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra(PROVIDED_TOKEN_KEY,
                        ((EditText) findViewById(R.id.tokenEdit)).getText().toString());
                startActivity(intent);
            }
        });
    }
}
