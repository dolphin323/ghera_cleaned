package edu.ksu.cs.benign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String TAG = "Benign/ResetPwdActivity";
    private static final int PERMISSION_REQ_CODE = 1989;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    protected void onResume(){
        super.onResume();
        final EditText phoneW = (EditText)findViewById(R.id.email);
        final EditText tokenW = (EditText) findViewById(R.id.benign_token);
        final Button getTokenW = (Button) findViewById(R.id.get_token_btn);
        final Button resetPwdW = (Button) findViewById(R.id.reset_pwd_btn);
        getTokenW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = getToken(phoneW.getText().toString());
                Log.d(TAG,"Token = " + token);
                if(token != null) {
                    tokenW.setText(token);
                    sendTokenAsEmail(phoneW.getText().toString(),token);
                }
            }
        });
        resetPwdW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),NewPasswordActivity.class).putExtra("token",tokenW.getText().toString()));
            }
        });
    }

    private String getToken(String phone){
        if(phone != null && !phone.equals("")){
            try{
                Cipher cipher = Cipher.getInstance("AES");
                SecretKeySpec secretKeySpec = new SecretKeySpec(getResources().getString(R.string.secret_key).getBytes(),"AES");
                cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
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

    private void sendTokenAsEmail(String phone, String token){
        Log.d(TAG,"Email the token ... ");
    }
}
