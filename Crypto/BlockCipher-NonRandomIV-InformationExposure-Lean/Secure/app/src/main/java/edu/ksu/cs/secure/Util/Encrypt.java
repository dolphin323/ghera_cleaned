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

public class Encrypt {

    public static String encryptGrade(String grade){
        byte[] key = new byte[16];
        byte[] IV = new byte[16];
        Cipher cipher = null;
        new SecureRandom().nextBytes(key);
        new SecureRandom().nextBytes(IV);
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        }
        catch(NoSuchAlgorithmException | NoSuchPaddingException e){
            e.printStackTrace();
            return null;
        }
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(IV);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
        }
        catch(InvalidKeyException | InvalidAlgorithmParameterException e){
            e.printStackTrace();
            return null;
        }
        try {
            return Base64.encodeToString(cipher.doFinal(grade.getBytes()), Base64.DEFAULT);
        }
        catch(IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
            return null;
        }
    }
}
