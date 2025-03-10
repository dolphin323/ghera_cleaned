package edu.ksu.cs.benign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class NewPasswordActivity extends AppCompatActivity {

    private final static String TAG = "Secure/NewPassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/GCM/NoPadding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            Log.d(TAG, "Exception while creating cipher instance", e);
            Toast.makeText(getApplicationContext(),"Cipher error", Toast.LENGTH_LONG).show();
            return;
        }

        Intent intent = getIntent();
        String IV = intent.getStringExtra(ResetPasswordActivity.INIT_VECTOR_KEY);
        byte[] decryptIV = Base64.decode(IV, Base64.DEFAULT);
        try {
            cipher.init(Cipher.DECRYPT_MODE,
                    new SecretKeySpec(ResetPasswordActivity.SECRET_KEY, "AES"),
                    new IvParameterSpec(decryptIV));
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            Log.d(TAG, "Exception while initializing cipher instance", e);
            Toast.makeText(getApplicationContext(),"Cipher error", Toast.LENGTH_LONG).show();
            return;
        }

        String token = intent.getStringExtra(ResetPasswordActivity.PROVIDED_TOKEN_KEY);
        byte[] decodeToken = Base64.decode(token, Base64.DEFAULT);
        setContentView(R.layout.activity_new_password);
        TextView msgView = ((TextView) findViewById(R.id.emailView));
        try {
            String email = new String(cipher.doFinal(decodeToken),"UTF-8");
            msgView.setText("You can change password for " + email);
        } catch (UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            Log.d(TAG, "Exception while decrypting data", e);
            msgView.setText("Invalid Token");
        }
    }
}
