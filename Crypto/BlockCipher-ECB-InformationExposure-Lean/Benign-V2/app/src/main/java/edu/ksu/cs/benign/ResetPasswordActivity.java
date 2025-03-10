package edu.ksu.cs.benign;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "Benign/ResetPwdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final EditText phoneW = (EditText) findViewById(R.id.email);
        final EditText tokenW = (EditText) findViewById(R.id.benign_token);
        final Button getTokenW = (Button) findViewById(R.id.get_token_btn);
        final Button resetPwdW = (Button) findViewById(R.id.reset_pwd_btn);
        getTokenW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = getToken(phoneW.getText().toString());
                Log.d(TAG, "Token = " + token);
                if (token != null) {
                    tokenW.setText(token);
                    sendTokenAsEmail(phoneW.getText().toString(), token);
                }
            }
        });
        resetPwdW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NewPasswordActivity.class)
                        .putExtra("token", tokenW.getText().toString()));
            }
        });
    }

    private String getToken(String phone) {
        Cipher cipher = null;
        if (phone != null && !phone.equals("")) {
            try {
                cipher = Cipher.getInstance("AES");
            } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                Log.d(TAG, "No Such Padding Exception occurred while creating token");
                e.printStackTrace();
                return null;
            }
            SecretKeySpec secretKeySpec = new SecretKeySpec(getResources().getString(R.string.secret_key).getBytes(), "AES");
            try {
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            } catch (InvalidKeyException | NullPointerException e) {
                Log.d(TAG, "Invalid Key or Null Pointer Exception occurred while creating token");
                e.printStackTrace();
                return null;
            }
            try {
                return Base64.encodeToString(cipher.doFinal(phone.getBytes()), Base64.DEFAULT);
            } catch (IllegalBlockSizeException | BadPaddingException | NullPointerException e) {
                Log.d(TAG, "Illegal Block size or Bad Padding exception occurred while creating token");
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private void sendTokenAsEmail(String phone, String token) {
        Toast.makeText(getApplicationContext(), "Token Emailed", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Email the token ... ");
    }
}
