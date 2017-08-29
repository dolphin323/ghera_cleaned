package santos.cs.ksu.edu.benign.Util;

import android.util.Base64;
import android.util.Log;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Joy on 8/24/17.
 */

public class Encrypt {

    private static final int keyLen = 256;
    private static final int nIter = 1000;
    private static final String algorithm = "PBKDF2WithHmacSHA1";
    private static final String TAG = "Benign/Encrypt";

    public static String encryptGrade(String salt, String grade){
        try{
            SecureRandom secureRandom = new SecureRandom();
            KeySpec keySpec = new PBEKeySpec(Constant.PASS.toCharArray(),salt.getBytes(),nIter,keyLen);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey key = new SecretKeySpec(keyBytes,"AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            byte[] iv = new byte[cipher.getBlockSize()];
            secureRandom.nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE,key,ivParameterSpec);
            Log.d(TAG,Base64.encodeToString(iv,Base64.DEFAULT) + "[" + Base64.encodeToString(cipher.doFinal(grade.getBytes()),Base64.DEFAULT));
            return Base64.encodeToString(iv,Base64.DEFAULT).trim() + "[" + Base64.encodeToString(cipher.doFinal(grade.getBytes()),Base64.DEFAULT);
        }
        catch(NoSuchPaddingException e){
            e.printStackTrace();
            return null;
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
        catch(InvalidKeyException e){
            e.printStackTrace();
            return null;
        }
        catch(InvalidAlgorithmParameterException e){
            e.printStackTrace();
            return null;
        }
        catch(IllegalBlockSizeException e){
            e.printStackTrace();
            return null;
        }
        catch(BadPaddingException e){
            e.printStackTrace();
            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
