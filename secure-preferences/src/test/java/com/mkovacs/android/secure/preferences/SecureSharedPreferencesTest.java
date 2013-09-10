package com.mkovacs.android.secure.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.mkovacs.android.secure.preferences.encryption.EncryptionHelper;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import edu.gmu.tec.scout.utilities.Encryption;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * JUnit test for {@link SecureSharedPreferences}.
 * @author NoTiCe
 */
public class SecureSharedPreferencesTest extends EasyMockSupport {
    private static final float DELTA = 0.1f;
    private SharedPreferences preferences = createMock(SecureSharedPreferences.class);
    private Encryption encryption = createMock(Encryption.class);
    private SecureSharedPreferences sut = new SecureSharedPreferences(preferences, encryption);
    private String key = "fooKey";

    @Before
    public void setUp() throws Exception {
        resetAll();
    }

    @Test
    public void testSecureSharedPreferences() {
        // GIVEN
        // EXPECT
        replayAll();
        // WHEN
        // THEN
        verifyAll();
        assertEquals("Preferences must be the same", preferences, sut.getPrefs());
        assertEquals("Encryption must be the same", encryption, sut.getEncryption());
    }

    @Test
    public void testContains() {
        // GIVEN
        boolean fooContains = true;
        // EXPECT
        expect(preferences.contains(key)).andReturn(fooContains);
        replayAll();
        // WHEN
        boolean contains = sut.contains(key);
        verifyAll();
        assertEquals("Must be true", fooContains, contains);
    }

    @Test
    public void testEdit() {
        // GIVEN
        Editor fooEditor = createMock(SecuredEditor.class);
        // EXPECT
        expect(preferences.edit()).andReturn(fooEditor);
        replayAll();
        // WHEN
        Editor edit = sut.edit();
        // THEN
        verifyAll();
        assertNotNull("Editor must not be null", edit);
        assertEquals("Editor must be SecuredEditor.", SecuredEditor.class, edit.getClass());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Test
    public void testGetAll() {
        // GIVEN
        Map fooAll = createMock(Map.class);
        // EXPECT
        expect(preferences.getAll()).andReturn(fooAll);
        replayAll();
        // WHEN
        Map<String, ?> all = sut.getAll();
        // THEN
        verifyAll();
        assertEquals("Wrong map returned", fooAll, all);
    }

    @Test
    public void testGetBoolean() {
        // GIVEN
        EncryptionHelper helper = createMock(EncryptionHelper.class);
        boolean defValue = false;
        boolean fooResult = true;
        String fooKey = "fooKey";
        // EXPECT
        expect(helper.readAndDecodeTemplate(preferences, key, defValue)).andReturn(fooResult);
        replayAll();
        // WHEN
        sut.setHelper(helper);
        boolean result = sut.getBoolean(fooKey, defValue);
        // THEN
        verifyAll();
        assertEquals("Wrong result", fooResult, result);
    }

    @Test
    public void testGetFloat() {
        // GIVEN
        EncryptionHelper helper = createMock(EncryptionHelper.class);
        float defValue = 1.0f;
        float fooResult = 2.0f;
        String fooKey = "fooKey";
        // EXPECT
        expect(helper.readAndDecodeTemplate(preferences, key, defValue)).andReturn(fooResult);
        replayAll();
        // WHEN
        sut.setHelper(helper);
        float result = sut.getFloat(fooKey, defValue);
        // THEN
        verifyAll();
        assertTrue("Wrong result", Math.abs(fooResult - result) < DELTA);
    }

    @Test
    public void testGetInt() {
        // GIVEN
        EncryptionHelper helper = createMock(EncryptionHelper.class);
        int defValue = 1;
        int fooResult = 2;
        String fooKey = "fooKey";
        // EXPECT
        expect(helper.readAndDecodeTemplate(preferences, key, defValue)).andReturn(fooResult);
        replayAll();
        // WHEN
        sut.setHelper(helper);
        int result = sut.getInt(fooKey, defValue);
        // THEN
        verifyAll();
        assertEquals("Wrong result", fooResult, result);
    }

    @Test
    public void testGetLong() {
        // GIVEN
        EncryptionHelper helper = createMock(EncryptionHelper.class);
        long defValue = 1l;
        long fooResult = 2l;
        String fooKey = "fooKey";
        // EXPECT
        expect(helper.readAndDecodeTemplate(preferences, key, defValue)).andReturn(fooResult);
        replayAll();
        // WHEN
        sut.setHelper(helper);
        long result = sut.getLong(fooKey, defValue);
        // THEN
        verifyAll();
        assertEquals("Wrong result", fooResult, result);
    }

    @Test
    public void testGetString() {
        // GIVEN
        EncryptionHelper helper = createMock(EncryptionHelper.class);
        String defValue = "1f";
        String fooResult = "2f";
        String fooKey = "fooKey";
        // EXPECT
        expect(helper.readAndDecodeTemplate(preferences, key, defValue)).andReturn(fooResult);
        replayAll();
        // WHEN
        sut.setHelper(helper);
        String result = sut.getString(fooKey, defValue);
        // THEN
        verifyAll();
        assertEquals("Wrong result", fooResult, result);
    }

    @Test
    public void testRegisterOnSharedPreferenceChangeListener() {
        // GIVEN
        OnSharedPreferenceChangeListener listener = createMock(OnSharedPreferenceChangeListener.class);
        // EXPECT
        preferences.registerOnSharedPreferenceChangeListener(listener);
        replayAll();
        // WHEN
        sut.registerOnSharedPreferenceChangeListener(listener);
        // THEN
        verifyAll();
    }

    @Test
    public void testUnregisterOnSharedPreferenceChangeListener() {
        // GIVEN
        OnSharedPreferenceChangeListener listener = createMock(OnSharedPreferenceChangeListener.class);
        // EXPECT
        preferences.unregisterOnSharedPreferenceChangeListener(listener);
        replayAll();
        // WHEN
        sut.unregisterOnSharedPreferenceChangeListener(listener);
        // THEN
        verifyAll();
    }

}
