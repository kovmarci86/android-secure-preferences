package com.github.kovmarci86.android.secure.preferences;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.Context;
import android.content.SharedPreferences;

import com.github.kovmarci86.android.secure.preferences.encryption.EncryptionAlgorithm;
import com.github.kovmarci86.android.secure.preferences.util.SecureUtils;

import edu.gmu.tec.scout.utilities.Encryption;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecureUtils.class, SecureFactory.class})
public class SecureFactoryTest extends EasyMockSupport {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testGetPreferencesWithSecurePreferencesCurrentVersion() {
        // GIVEN
        int currentVersion = SecureFactory.LATEST_VERSION;
        SharedPreferences original = createMock(SecureSharedPreferences.class);
        EncryptionAlgorithm encryption = createMock(EncryptionAlgorithm.class);
        PowerMock.mockStatic(SecureUtils.class);
        // EXPECT
        expect(SecureUtils.getVersion(original)).andReturn(currentVersion);
        PowerMock.replay(SecureUtils.class);
        replayAll();
        // WHEN
        SecureSharedPreferences preferences = SecureFactory.getPreferences(original, encryption);
        // THEN
        assertEquals("Must not modify already migrated", original, preferences);
        PowerMock.verifyAll();
        verifyAll();
    }

    @Test
    public void testGetPreferencesWithSecurePreferencesZeroVersion() {
        // GIVEN
        int currentVersion = 0;
        SharedPreferences original = createMock(SecureSharedPreferences.class);
        EncryptionAlgorithm encryption = createMock(EncryptionAlgorithm.class);
        PowerMock.mockStatic(SecureUtils.class);
        // EXPECT
        expect(SecureUtils.getVersion(original)).andReturn(currentVersion);
        SecureUtils.migrateData(original, original, SecureFactory.VERSION_1);
        PowerMock.replay(SecureUtils.class);
        replayAll();
        // WHEN
        SecureSharedPreferences preferences = SecureFactory.getPreferences(original, encryption);
        // THEN
        assertEquals("Must not modify already migrated", original, preferences);
        PowerMock.verifyAll();
        verifyAll();
    }

    @Test
    public void testGetPreferencesWithSharedPreferencesZeroVersion() throws Exception {
        // GIVEN
        int currentVersion = 0;
        SharedPreferences original = createMock(SharedPreferences.class);
        EncryptionAlgorithm encryption = createMock(EncryptionAlgorithm.class);
        PowerMock.mockStatic(SecureUtils.class);
        // EXPECT
        expect(SecureUtils.getVersion(anyObject(SecureSharedPreferences.class))).andReturn(currentVersion);
        SecureUtils.migrateData(eq(original), anyObject(SecureSharedPreferences.class), eq(SecureFactory.VERSION_1));
        PowerMock.replay(SecureUtils.class);
        replayAll();
        // WHEN
        SharedPreferences preferences = SecureFactory.getPreferences(original, encryption);
        // THEN
        assertNotSame("Must migrate at first use", original, preferences);
        assertTrue("Must be instanceof SecureSharedPreferences", preferences instanceof SecureSharedPreferences);
        PowerMock.verify(SecureUtils.class);
        verifyAll();
    }

    @Test
    public void testGetPreferencesWithSecurePreferencesAndPasswordCurrentVersion() throws Exception {
        // GIVEN
        String password = "fooPass";
        int currentVersion = SecureFactory.LATEST_VERSION;
        SharedPreferences original = createMock(SecureSharedPreferences.class);
        Encryption encryption = createMock(Encryption.class);
        PowerMock.mockStatic(SecureUtils.class);
        // EXPECT
        PowerMock.expectNew(Encryption.class, password).andReturn(encryption);
        expect(SecureUtils.getVersion(original)).andReturn(currentVersion);
        PowerMock.replay(SecureUtils.class, Encryption.class);
        replayAll();
        // WHEN
        SecureSharedPreferences preferences = SecureFactory.getPreferences(original, password);
        // THEN
        assertEquals("Must not modify already migrated", original, preferences);
        PowerMock.verifyAll();
        verifyAll();
    }

    @Test
    public void testGetPreferencesWithSecurePreferencesAndPrefNameAndPasswordCurrentVersion() throws Exception {
        // GIVEN
        Context context = createMock(Context.class);
        String prefName = "fooPrefName";
        String password = "fooPass";
        int currentVersion = SecureFactory.LATEST_VERSION;
        SharedPreferences original = createMock(SecureSharedPreferences.class);
        Encryption encryption = createMock(Encryption.class);
        PowerMock.mockStatic(SecureUtils.class);
        // EXPECT
        PowerMock.expectNew(Encryption.class, password).andReturn(encryption);
        expect(context.getSharedPreferences(prefName, Context.MODE_PRIVATE)).andReturn(original);
        expect(SecureUtils.getVersion(original)).andReturn(currentVersion);
        PowerMock.replay(SecureUtils.class, Encryption.class);
        replayAll();
        // WHEN
        SecureSharedPreferences preferences = SecureFactory.getPreferences(context, prefName, password);
        // THEN
        assertEquals("Must not modify already migrated", original, preferences);
        PowerMock.verifyAll();
        verifyAll();
    }
}
