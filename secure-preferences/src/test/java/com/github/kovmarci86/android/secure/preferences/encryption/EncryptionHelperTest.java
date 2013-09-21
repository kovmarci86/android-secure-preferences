package com.github.kovmarci86.android.secure.preferences.encryption;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.SharedPreferences;
import android.util.Base64;

/**
 * JUnit test for {@link EncryptionHelper} class.
 * @author NoTiCe
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({EncryptionHelper.class, Base64.class})
public class EncryptionHelperTest extends PowerMock {

    @Test
    public void testReadAndDecodeTemplate() throws Exception {
        // GIVEN
        byte[] mockBytaArray = new byte[]{};
        byte[] mockEncryptedBytaArray = new byte[]{};
        EncryptionAlgorithm mockEncryption = createMock(EncryptionAlgorithm.class);
        ByteArrayInputStream mockBais = createMock(ByteArrayInputStream.class);
        ObjectInputStream mockOis = createMock(ObjectInputStream.class);
        mockStatic(Base64.class);
        Object fooResultString = "fooResultString";
        String mockString = "fooString";
        SharedPreferences prefs = createMock(SharedPreferences.class);
        String key = "fooKey";
        String defValue = "defValue";

        // EXPECT
        expect(prefs.getString(key, null)).andReturn(mockString);
        expect(Base64.decode(mockString, Base64.DEFAULT)).andReturn(mockEncryptedBytaArray);
        expect(mockEncryption.decrypt(mockEncryptedBytaArray)).andReturn(mockBytaArray);
        expectNew(ByteArrayInputStream.class, mockBytaArray).andReturn(mockBais);
        expectNew(ObjectInputStream.class, mockBais).andReturn(mockOis);
        expect(mockOis.readObject()).andReturn(fooResultString);
        replayAll();
        // WHEN
        EncryptionHelper helper = new EncryptionHelper(mockEncryption);
        String decoded = helper.readAndDecodeTemplate(prefs, key, defValue);

        // THEN
        assertEquals("Must equal", fooResultString, decoded);
        verifyAll();
    }

    @Test
    public void testReadAndDecodeTemplateGetDefaultValue() throws Exception {
        // GIVEN
        EncryptionAlgorithm mockEncryption = createMock(EncryptionAlgorithm.class);
        mockStatic(Base64.class);
        SharedPreferences prefs = createMock(SharedPreferences.class);
        String key = "fooKey";
        String defValue = "defValue";

        // EXPECT
        expect(prefs.getString(key, null)).andReturn(null);
        replayAll();
        // WHEN
        EncryptionHelper helper = new EncryptionHelper(mockEncryption);
        String decoded = helper.readAndDecodeTemplate(prefs, key, defValue);

        // THEN
        assertEquals("Must equal", defValue, decoded);
        verifyAll();
    }

    @Test
    public void testReadAndDecodeTemplateGetDefaultValueOnIOException() throws Exception {
        // GIVEN
        byte[] mockBytaArray = new byte[]{};
        byte[] mockEncryptedBytaArray = new byte[]{};
        EncryptionAlgorithm mockEncryption = createMock(EncryptionAlgorithm.class);
        ByteArrayInputStream mockBais = createMock(ByteArrayInputStream.class);
        ObjectInputStream mockOis = createMock(ObjectInputStream.class);
        mockStatic(Base64.class);
        String mockString = "fooString";
        SharedPreferences prefs = createMock(SharedPreferences.class);
        String key = "fooKey";
        String defValue = "defValue";
        IOException ioException = new IOException();

        // EXPECT
        expect(prefs.getString(key, null)).andReturn(mockString);
        expect(Base64.decode(mockString, Base64.DEFAULT)).andReturn(mockEncryptedBytaArray);
        expect(mockEncryption.decrypt(mockEncryptedBytaArray)).andReturn(mockBytaArray);
        expectNew(ByteArrayInputStream.class, mockBytaArray).andReturn(mockBais);
        expectNew(ObjectInputStream.class, mockBais).andReturn(mockOis);
        expect(mockOis.readObject()).andThrow(ioException);
        replayAll();
        // WHEN
        EncryptionHelper helper = new EncryptionHelper(mockEncryption);
        String decoded = helper.readAndDecodeTemplate(prefs, key, defValue);

        // THEN
        assertEquals("Must equal", defValue, decoded);
        verifyAll();
    }

    @Test
    public void testReadAndDecodeTemplateGetDefaultValueOnClassNotFoundException() throws Exception {
        // GIVEN
        byte[] mockBytaArray = new byte[]{};
        byte[] mockEncryptedBytaArray = new byte[]{};
        EncryptionAlgorithm mockEncryption = createMock(EncryptionAlgorithm.class);
        ByteArrayInputStream mockBais = createMock(ByteArrayInputStream.class);
        ObjectInputStream mockOis = createMock(ObjectInputStream.class);
        mockStatic(Base64.class);
        String mockString = "fooString";
        SharedPreferences prefs = createMock(SharedPreferences.class);
        String key = "fooKey";
        String defValue = "defValue";
        ClassNotFoundException classNotFoundException = new ClassNotFoundException();

        // EXPECT
        expect(prefs.getString(key, null)).andReturn(mockString);
        expect(Base64.decode(mockString, Base64.DEFAULT)).andReturn(mockEncryptedBytaArray);
        expect(mockEncryption.decrypt(mockEncryptedBytaArray)).andReturn(mockBytaArray);
        expectNew(ByteArrayInputStream.class, mockBytaArray).andReturn(mockBais);
        expectNew(ObjectInputStream.class, mockBais).andReturn(mockOis);
        expect(mockOis.readObject()).andThrow(classNotFoundException);
        replayAll();
        // WHEN
        EncryptionHelper helper = new EncryptionHelper(mockEncryption);
        String decoded = helper.readAndDecodeTemplate(prefs, key, defValue);

        // THEN
        assertEquals("Must equal", defValue, decoded);
        verifyAll();
    }

    @Test
    public void testEncode() throws Exception {
        // GIVEN
        byte[] mockBytaArray = new byte[]{};
        byte[] mockEncryptedBytaArray = new byte[]{};
        String mockString = "fooString";
        Object value = createMock(Object.class);
        EncryptionAlgorithm mockEncryption = createMock(EncryptionAlgorithm.class);
        ByteArrayOutputStream mockBaos = createMock(ByteArrayOutputStream.class);
        ObjectOutputStream mockOos = createMock(ObjectOutputStream.class);
        mockStatic(Base64.class);

        // EXPECT
        expectNew(ByteArrayOutputStream.class).andReturn(mockBaos);
        expectNew(ObjectOutputStream.class, mockBaos).andReturn(mockOos);
        mockOos.writeObject(value);
        expect(mockBaos.toByteArray()).andReturn(mockBytaArray);
        expect(mockEncryption.encrypt(mockBytaArray)).andReturn(mockEncryptedBytaArray);
        expect(Base64.encodeToString(mockEncryptedBytaArray, Base64.DEFAULT)).andReturn(mockString);
        replayAll();
        // WHEN
        EncryptionHelper helper = new EncryptionHelper(mockEncryption);
        String encoded = helper.encode(value);

        // THEN
        assertEquals("Must equal", mockString, encoded);
        verifyAll();
    }

    @Test
    public void testEncodeWontHarmOnEncryptionException() throws Exception {
        // GIVEN
        EncryptionException e = new EncryptionException();
        byte[] mockBytaArray = new byte[]{};
        Object value = createMock(Object.class);
        EncryptionAlgorithm mockEncryption = createMock(EncryptionAlgorithm.class);
        ByteArrayOutputStream mockBaos = createMock(ByteArrayOutputStream.class);
        ObjectOutputStream mockOos = createMock(ObjectOutputStream.class);
        mockStatic(Base64.class);

        // EXPECT
        expectNew(ByteArrayOutputStream.class).andReturn(mockBaos);
        expectNew(ObjectOutputStream.class, mockBaos).andReturn(mockOos);
        mockOos.writeObject(value);
        expect(mockBaos.toByteArray()).andReturn(mockBytaArray);
        expect(mockEncryption.encrypt(mockBytaArray)).andThrow(e);
        replayAll();
        // WHEN
        EncryptionHelper helper = new EncryptionHelper(mockEncryption);
        String encoded = helper.encode(value);

        // THEN
        assertEquals("Must equal null, was an error inside.", null, encoded);
        verifyAll();
    }

    @Test
    public void testEncodeWontHarmOnIOException() throws Exception {
        // GIVEN
        IOException e = new IOException();
        Object value = createMock(Object.class);
        EncryptionAlgorithm mockEncryption = createMock(EncryptionAlgorithm.class);
        ByteArrayOutputStream mockBaos = createMock(ByteArrayOutputStream.class);
        ObjectOutputStream mockOos = createMock(ObjectOutputStream.class);
        mockStatic(Base64.class);

        // EXPECT
        expectNew(ByteArrayOutputStream.class).andReturn(mockBaos);
        expectNew(ObjectOutputStream.class, mockBaos).andReturn(mockOos);
        mockOos.writeObject(value);
        expectLastCall().andThrow(e);
        replayAll();
        // WHEN
        EncryptionHelper helper = new EncryptionHelper(mockEncryption);
        String encoded = helper.encode(value);

        // THEN
        assertEquals("Must equal null, was an error inside.", null, encoded);
        verifyAll();
    }

    @Test
    public void testEncodeNullReturnsNull() throws Exception {
        // GIVEN
        String mockString = null;
        EncryptionAlgorithm mockEncryption = createMock(EncryptionAlgorithm.class);

        // EXPECT
        replayAll();
        // WHEN
        EncryptionHelper helper = new EncryptionHelper(mockEncryption);
        String encoded = helper.encode(mockString);

        // THEN
        assertEquals("Must equal", mockString, encoded);
        verifyAll();
    }

}
