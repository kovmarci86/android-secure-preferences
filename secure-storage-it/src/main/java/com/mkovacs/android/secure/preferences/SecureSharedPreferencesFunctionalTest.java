package com.mkovacs.android.secure.preferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.gmu.tec.scout.utilities.Encryption;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.test.InstrumentationTestCase;

/**
 * Tests the {@link SecureSharedPreferences} in action.
 * @author NoTiCe
 */
public class SecureSharedPreferencesFunctionalTest extends InstrumentationTestCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecureSharedPreferencesFunctionalTest.class);
    private static final String FOO_PREFERENCES_NAME = "fooPreferencesName";
    private static final String FOO_PASSWORD = "fooPassword";
    private static final String STRING_VALUE = "testString";
    // test values
    private static final String WRONG_VALUE_MESSAGE = "Wrong value";
    private static final String STRING_KEY = "string";
    private static final String LONG_KEY = "long";
    private static final String INT_KEY = "int";
    private static final String FLOAT_KEY = "float";
    private static final String BOOLEAN_KEY = "boolean";
    private static final long LONG_VALUE = 1L;
    private static final int INT_VALUE = 1;
    private static final float FLOAT_VALUE = 1.0f;
    private static final boolean BOOLEAN_VALUE = true;
    private SharedPreferences sut;

    /**
     * Creates the {@link Encryption} and the {@link SecureSharedPreferences}
     * instances for testing.
     * @throws Exception In unexpected cases.
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();

        SharedPreferences preferences = getInstrumentation().getTargetContext().getSharedPreferences(FOO_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Encryption encryption = new Encryption(FOO_PASSWORD);
        sut = new SecureSharedPreferences(preferences, encryption);
    }

    /**
     * Tests if {@link SecureSharedPreferences} can read back it's stored
     * values.
     */
    public void testSecureSharedPreferencesWorks() {
        // Put values in it
        Editor edit = sut.edit();
        edit.putBoolean(BOOLEAN_KEY, BOOLEAN_VALUE);
        edit.putFloat(FLOAT_KEY, FLOAT_VALUE);
        edit.putInt(INT_KEY, INT_VALUE);
        edit.putLong(LONG_KEY, LONG_VALUE);
        edit.putString(STRING_KEY, STRING_VALUE);
        edit.commit();
        // read back the values
        assertEquals(WRONG_VALUE_MESSAGE, BOOLEAN_VALUE, sut.getBoolean(BOOLEAN_KEY, false));
        assertEquals(WRONG_VALUE_MESSAGE, FLOAT_VALUE, sut.getFloat(FLOAT_KEY, 0f));
        assertEquals(WRONG_VALUE_MESSAGE, INT_VALUE, sut.getInt(INT_KEY, 0));
        assertEquals(WRONG_VALUE_MESSAGE, LONG_VALUE, sut.getLong(LONG_KEY, 0L));
        assertEquals(WRONG_VALUE_MESSAGE, STRING_VALUE, sut.getString(STRING_KEY, null));
    }

    /**
     * Writes values to an encoded {@link SecureSharedPreferences}, then tries
     * to read it back using a non encoded {@link SharedPreferences}. These
     * reads must fail and will trigger a {@link ClassCastException}. String
     * reads will return their encoded value.
     */
    public void testSecureSharedPreferencesIsEncrypted() {
        // Put values in it
        Editor edit = sut.edit();
        edit.putBoolean(BOOLEAN_KEY, BOOLEAN_VALUE);
        edit.putFloat(FLOAT_KEY, FLOAT_VALUE);
        edit.putInt(INT_KEY, INT_VALUE);
        edit.putLong(LONG_KEY, LONG_VALUE);
        edit.putString(STRING_KEY, STRING_VALUE);
        edit.commit();
        // read back the values from simple SharedPreferences instance
        SharedPreferences nonSecure = getInstrumentation().getTargetContext().getSharedPreferences(FOO_PREFERENCES_NAME, Context.MODE_PRIVATE);
        try {
            nonSecure.getBoolean(BOOLEAN_KEY, false);
            fail("This command must not work!");
        } catch (Throwable e) {
            LOGGER.info("Read did not work, this is normal.");
        }
        try {
            nonSecure.getFloat(FLOAT_KEY, 0f);
            fail("This command must not work!");
        } catch (Throwable e) {
            LOGGER.info("Read did not work, this is normal.");
        }
        try {
            nonSecure.getInt(INT_KEY, 0);
            fail("This command must not work!");
        } catch (Throwable e) {
            LOGGER.info("Read did not work, this is normal.");
        }
        try {
            nonSecure.getLong(LONG_KEY, 0L);
            fail("This command must not work!");
        } catch (Throwable e) {
            LOGGER.info("Read did not work, this is normal.");
        }
        try {
            String result = nonSecure.getString(STRING_KEY, null);
            assertFalse("The resulting string may not be empty.", STRING_VALUE.equals(result));
        } catch (Throwable e) {
            LOGGER.info("Read did not work, this is normal.");
        }
    }
}
