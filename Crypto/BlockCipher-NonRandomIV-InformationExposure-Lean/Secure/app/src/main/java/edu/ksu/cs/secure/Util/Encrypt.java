package edu.ksu.cs.secure.Util;

import android.util.Base64;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Joy on 8/24/17.
 */

public class Encrypt {

    public static String encryptGrade(String grade){
        byte[] key = new byte[16];
        byte[] IV = new byte[16];
        new SecureRandom().nextBytes(key);
        new SecureRandom().nextBytes(IV);
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
            cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec,ivParameterSpec);
            return Base64.encodeToString(cipher.doFinal(grade.getBytes()),Base64.DEFAULT);
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
