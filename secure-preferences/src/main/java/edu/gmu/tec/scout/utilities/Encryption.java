package edu.gmu.tec.scout.utilities;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.github.kovmarci86.android.secure.preferences.encryption.EncryiptionException;
import com.github.kovmarci86.android.secure.preferences.encryption.EncryptionAlgorithm;

/**
 * This class is from the following site:
 * http://www.java2s.com/Code/Android/Security/AESEncryption.htm with some
 * modification.
 * @author http://www.java2s.com/Code/Android/Security/AESEncryption.htm
 */
public class Encryption implements EncryptionAlgorithm {
    /**
     * Author of the base of this implementation.
     */
    public static final String UTILS_AUTHOR = "http://www.java2s.com/Code/Android/Security/AESEncryption.htm";
    private SecretKeySpec skeySpec;
    private Cipher cipher;

    /**
     * Initializes with raw key.
     * @param keyraw The raw key. Must NOT be null.
     * @throws UnsupportedEncodingException When no UTF-8 support found.
     * @throws NoSuchAlgorithmException When no MD5 support found.
     * @throws NoSuchPaddingException When the AES/ECB/PKCS5Padding is not supported.
     */
    public Encryption(byte[] keyraw) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
        if (keyraw == null) {
            throw new IllegalArgumentException("null key given");
        } else {
            skeySpec = new SecretKeySpec(keyraw, "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        }
    }

    /**
     * Initializes with a password to use for encrypt.
     * @param passphrase The string password.
     * @throws UnsupportedEncodingException When no UTF-8 support found.
     * @throws NoSuchAlgorithmException When no MD5 support found.
     * @throws NoSuchPaddingException When the AES/ECB/PKCS5Padding is not supported.
     */
    public Encryption(String passphrase) throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException {
        byte[] bytesOfMessage = passphrase.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] thedigest = md.digest(bytesOfMessage);
        skeySpec = new SecretKeySpec(thedigest, "AES");
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    }

    /**
     * Encrypts the bytes.
     * @param plaintext The bytes to encrypt.
     * @return The encrypted value.
     * @throws EncryiptionException On encryption exception.
     */
    @Override
    public byte[] encrypt(byte[] plaintext) throws EncryiptionException {
        // returns byte array encrypted with key
        try {
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return cipher.doFinal(plaintext);
        } catch (InvalidKeyException e) {
            throw new EncryiptionException(e);
        } catch (IllegalBlockSizeException e) {
            throw new EncryiptionException(e);
        } catch (BadPaddingException e) {
            throw new EncryiptionException(e);
        }
    }

    /**
     * Decrypes the cypher.
     * @param ciphertext The data to decrypt.
     * @return The decrypted value.
     * @throws EncryiptionException On ecryption exception.
     */
    @Override
    public byte[] decrypt(byte[] ciphertext) throws EncryiptionException {
        // returns byte array decrypted with key
        try {
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            return cipher.doFinal(ciphertext);
        } catch (InvalidKeyException e) {
            throw new EncryiptionException(e);
        } catch (IllegalBlockSizeException e) {
            throw new EncryiptionException(e);
        } catch (BadPaddingException e) {
            throw new EncryiptionException(e);
        }
    }

}
