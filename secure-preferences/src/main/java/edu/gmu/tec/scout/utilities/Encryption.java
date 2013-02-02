package edu.gmu.tec.scout.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.*;
import javax.crypto.spec.*;

/**
 * This class is from the following site:
 * http://www.java2s.com/Code/Android/Security/AESEncryption.htm Modified by
 * adding javadoc.
 * 
 * @author http://www.java2s.com/Code/Android/Security/AESEncryption.htm
 */
public class Encryption {
	public final static String UTILS_AUTHOR = "http://www.java2s.com/Code/Android/Security/AESEncryption.htm";
	private SecretKeySpec skeySpec;
	private Cipher cipher;

	/**
	 * Initializes with raw key.
	 * 
	 * @param keyraw
	 *            The raw key.
	 * @throws UnsupportedEncodingException
	 *             When no UTF-8 support found.
	 * @throws NoSuchAlgorithmException
	 *             When no MD5 support found.
	 * @throws NoSuchPaddingException
	 *             When the AES/ECB/PKCS5Padding is not supported.
	 */
	public Encryption(byte[] keyraw) throws UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException {
		if (keyraw == null) {
			byte[] bytesOfMessage = "".getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytes = md.digest(bytesOfMessage);

			skeySpec = new SecretKeySpec(bytes, "AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		} else {

			skeySpec = new SecretKeySpec(keyraw, "AES");
			cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

		}
	}

	/**
	 * Initializes with a password to use for encrypt.
	 * 
	 * @param passphrase
	 *            The string password.
	 * @throws UnsupportedEncodingException
	 *             When no UTF-8 support found.
	 * @throws NoSuchAlgorithmException
	 *             When no MD5 support found.
	 * @throws NoSuchPaddingException
	 *             When the AES/ECB/PKCS5Padding is not supported.
	 */
	public Encryption(String passphrase) throws UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] bytesOfMessage = passphrase.getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		skeySpec = new SecretKeySpec(thedigest, "AES");

		cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	}

	/**
	 * Initializes without password (the pass will be "").
	 * 
	 * @throws UnsupportedEncodingException
	 *             When no UTF-8 support found.
	 * @throws NoSuchAlgorithmException
	 *             When no MD5 support found.
	 * @throws NoSuchPaddingException
	 *             When the AES/ECB/PKCS5Padding is not supported.
	 */
	public Encryption() throws UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException {
		byte[] bytesOfMessage = "".getBytes("UTF-8");
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		skeySpec = new SecretKeySpec(thedigest, "AES");
		skeySpec = new SecretKeySpec(new byte[16], "AES");
		cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	}

	/**
	 * Encrypts the bytes.
	 * @param plaintext The bytes to encrypt.
	 * @return The encrypted value.
	 * @throws Exception On encrtyipion exception.
	 */
	public byte[] encrypt(byte[] plaintext) throws Exception {
		// returns byte array encrypted with key
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] ciphertext = cipher.doFinal(plaintext);
		return ciphertext;
	}

	/**
	 * Decrypes the cypher.
	 * @param ciphertext The data to decrypt.
	 * @return The decrypted value.
	 * @throws Exception On encrtyipion exception.
	 */
	public byte[] decrypt(byte[] ciphertext) throws Exception {
		// returns byte array decrypted with key
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		byte[] plaintext = cipher.doFinal(ciphertext);
		return plaintext;
	}

}