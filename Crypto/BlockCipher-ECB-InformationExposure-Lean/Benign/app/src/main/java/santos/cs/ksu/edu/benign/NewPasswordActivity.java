package santos.cs.ksu.edu.benign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
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
        //setContentView(R.layout.activity_new_password);
    }

    @Override
    protected void onResume(){
        super.onResume();
        try{
            String token = getIntent().getStringExtra("token");
            byte[] decodeToken = Base64.decode(token, Base64.DEFAULT);
            System.out.println("----------display decodeToken bytes --------");
            for(int i=0;i<decodeToken.length;i++){
                System.out.println(decodeToken[i]);
            }
            //byte[] decodeToken = getIntent().getByteArrayExtra("token");
            Cipher cipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec = new SecretKeySpec(getResources().getString(R.string.secret_key).getBytes(), "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            System.out.println("decrypted res = " + new String(cipher.doFinal(decodeToken),"UTF-8"));
            if(new String(cipher.doFinal(decodeToken),"UTF-8").equals("anniemaes@gmail.com")){
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
    }
}
