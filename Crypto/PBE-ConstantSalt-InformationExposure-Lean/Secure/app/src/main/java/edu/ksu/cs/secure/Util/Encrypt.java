package edu.ksu.cs.secure.Util;

import android.util.Base64;
import android.util.Log;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
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

public class Encrypt {

    private static final int keyLen = 256;
    private static final int nIter = 1000;
    private static final String algorithm = "PBKDF2WithHmacSHA1";
    private static final String TAG = "Benign/Encrypt";

    public static String encryptGrade(String grade) {
        try {
            SecureRandom secureRandom = new SecureRandom();
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            KeySpec keySpec = new PBEKeySpec(Constant.PASS.toCharArray(), salt, nIter, keyLen);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            byte[] keyBytes = secretKeyFactory.generateSecret(keySpec).getEncoded();
            SecretKey key = new SecretKeySpec(keyBytes, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            byte[] iv = new byte[cipher.getBlockSize()];
            secureRandom.nextBytes(iv);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
            Log.d(TAG, Base64.encodeToString(iv, Base64.DEFAULT) + "[" + Base64.encodeToString(cipher.doFinal(grade.getBytes()), Base64.DEFAULT));
            return Base64.encodeToString(iv, Base64.DEFAULT).trim() + "[" + Base64.encodeToString(cipher.doFinal(grade.getBytes()), Base64.DEFAULT);
        } catch (NoSuchPaddingException
                | NoSuchAlgorithmException
                | InvalidKeySpecException
                | InvalidAlgorithmParameterException
                | IllegalBlockSizeException
                | BadPaddingException
                | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }
}
