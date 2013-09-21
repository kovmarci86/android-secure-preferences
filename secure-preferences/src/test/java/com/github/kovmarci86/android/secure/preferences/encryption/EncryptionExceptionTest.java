package com.github.kovmarci86.android.secure.preferences.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * JUnit test for {@link EncryptionException}.
 * @author NoTiCe
 *
 */
public class EncryptionExceptionTest {

    @Test
    public void testEncryptionException() {
        // +1 green line in the report
        new EncryptionException();
    }

    @Test
    public void testEncryptionExceptionStringThrowable() {
        String message = "foo";
        Throwable throwable = new Throwable();
        EncryptionException encryptionException = new EncryptionException(message, throwable);
        assertEquals("Must be the same", message, encryptionException.getMessage());
        assertEquals("Must be the same", throwable, encryptionException.getCause());
    }

    @Test
    public void testEncryptionExceptionString() {
        String message = "foo";
        EncryptionException encryptionException = new EncryptionException(message);
        assertEquals("Must be the same", message, encryptionException.getMessage());

    }

    @Test
    public void testEncryptionExceptionThrowable() {
        Throwable throwable = new Throwable();
        EncryptionException encryptionException = new EncryptionException(throwable);
        assertEquals("Must be the same", throwable, encryptionException.getCause());
    }

}
