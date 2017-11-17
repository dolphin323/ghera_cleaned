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
    private static String IV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
    }

    @Override
    protected void onResume(){
        super.onResume();
        final EditText phoneW = (EditText)findViewById(R.id.editText);
        final EditText tokenW = (EditText) findViewById(R.id.editText2);
        final Button getTokenW = (Button) findViewById(R.id.button);
        final Button resetPwdW = (Button) findViewById(R.id.button2);
        getTokenW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = getToken(phoneW.getText().toString());
                //byte[] deocdedToken = Base64.decode(token,Base64.DEFAULT);
                //System.out.println("Hex String = " + String.format("%040x",new BigInteger(1,deocdedToken)));
                Log.d(TAG,"Token = " + token);
                Log.d(TAG,"IV = " + IV);
                if(token != null) {
                    tokenW.setText(token);
                    sendTokenAsEmail(phoneW.getText().toString(),token);
                }
            }
        });
        resetPwdW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NewPasswordActivity.class);
                intent.putExtra("token",tokenW.getText().toString());
                intent.putExtra("IV", IV);
                startActivity(intent);
            }
        });
    }

    private String getToken(String phone){
        if(phone != null && !phone.equals("")){
            try{
                Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
                SecretKeySpec secretKeySpec = new SecretKeySpec(getResources().getString(R.string.secret_key).getBytes(),"AES");
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

    private void sendTokenAsEmail(String phone, String token){
        Log.d(TAG,"Email the token ... ");
    }
}
