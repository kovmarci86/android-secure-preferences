package com.mkovacs.android.secure.preferences.encryption;

/**
 * A custom exception for ecoding.
 * @author NoTiCe
 *
 */
public class EncryiptionException extends Exception {

    /**
     * Inherited from {@link Exception}.
     */
    public EncryiptionException() {
        super();
    }

    /**
     * Inherited from {@link Exception}.
     * @param message The message.
     * @param cause The root cause.
     */
    public EncryiptionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Inherited from {@link Exception}.
     * @param message The message.
     */
    public EncryiptionException(String message) {
        super(message);
    }

    /**
     * Inherited from {@link Exception}.
     * @param cause The root cause.
     */
    public EncryiptionException(Throwable cause) {
        super(cause);
    }

}
