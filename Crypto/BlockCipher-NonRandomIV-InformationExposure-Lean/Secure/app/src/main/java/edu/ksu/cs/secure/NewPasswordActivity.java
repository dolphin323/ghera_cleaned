package edu.ksu.cs.secure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class NewPasswordActivity extends AppCompatActivity {

    private final static String TAG = "NewPasswordActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_new_password);
    }

    @Override
    protected void onResume(){
        super.onResume();
        try{
            String token = getIntent().getStringExtra("token");
            String IV = getIntent().getStringExtra("IV");
            byte[] decodeToken = Base64.decode(token, Base64.DEFAULT);
            byte[] decodeIV = Base64.decode(IV, Base64.DEFAULT);
            System.out.println("----------display decodeToken bytes --------");
            for(int i=0;i<decodeToken.length;i++){
                System.out.println(decodeToken[i]);
            }
            //byte[] decodeToken = getIntent().getByteArrayExtra("token");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey("key2", null);
            cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(128, decodeIV));
            String decodedToken = new String(cipher.doFinal(decodeToken),"UTF-8");
            System.out.println("decrypted res = " + decodedToken);
            if("anniemaes@gmail.com".equals(decodedToken)){
                setContentView(R.layout.activity_new_password);
            }
            else Toast.makeText(getApplicationContext(),"Sorry we do not have you in our records!",Toast.LENGTH_SHORT).show();
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
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch(KeyStoreException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(CertificateException e){
            e.printStackTrace();
        }
        catch(UnrecoverableKeyException e){
            e.printStackTrace();
        }
        catch(InvalidAlgorithmParameterException e){
            e.printStackTrace();
        }
    }
}
