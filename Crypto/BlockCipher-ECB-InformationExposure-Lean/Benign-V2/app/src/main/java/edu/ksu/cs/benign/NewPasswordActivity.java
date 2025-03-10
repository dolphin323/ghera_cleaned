package edu.ksu.cs.benign;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class NewPasswordActivity extends AppCompatActivity {

    private final static String TAG = "NewPasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Cipher cipher = null;
        String email = "";
        String token = getIntent().getStringExtra("token");
        byte[] decodeToken = Base64.decode(token, Base64.DEFAULT);
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(getResources().getString(R.string.secret_key).getBytes(), "AES");
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        } catch (InvalidKeyException | NullPointerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        try {
            email = new String(cipher.doFinal(decodeToken), "UTF-8");
        } catch (IllegalBlockSizeException | BadPaddingException
                | UnsupportedEncodingException | NullPointerException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        if (email.equals("anniemaes@gmail.com")) {
            setContentView(R.layout.activity_new_password);
            TextView NewPwdEt = (TextView) findViewById(R.id.email_text);
            NewPwdEt.setText(email);
        } else {
            Toast.makeText(getApplicationContext(), "Sorry we do not have you in our records!", Toast.LENGTH_SHORT).show();
        }
    }
}
