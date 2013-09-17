package com.github.kovmarci86.android.secure.preferences.encryption;

/**
 * Encryption algorithm.
 * @author NoTiCe
 */
public interface EncryptionAlgorithm {
    /**
     * Encrypts the given bytes.
     * @param bytes
     *            The bytes to encrypt.
     * @return The encrypted bytes.
     * @throws EncryiptionException When the encryiption fails.
     */
    byte[] encrypt(byte[] bytes) throws EncryiptionException;

    /**
     * Decrypts the given bytes.
     * @param bytes
     *            The bytes to decrypt.
     * @return The decrypted bytes.
     * @throws EncryiptionException When the encryiption fails.
     */
    byte[] decrypt(byte[] bytes) throws EncryiptionException;
}
