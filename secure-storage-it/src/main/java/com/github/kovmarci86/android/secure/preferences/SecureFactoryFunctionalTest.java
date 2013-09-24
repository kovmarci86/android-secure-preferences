package com.github.kovmarci86.android.secure.preferences;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.github.kovmarci86.android.secure.preferences.SecureFactory;
import com.github.kovmarci86.android.secure.preferences.SecureSharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.test.InstrumentationTestCase;

/**
 * Functional test for {@link SecureFactory}.
 * @author NoTiCe
 */
public class SecureFactoryFunctionalTest extends InstrumentationTestCase {

    private static final String SHARED_PREFERENCES_NAME = "fooPreferences";
    private static final String STRING_SET_KEY = "stringSet";
    private static final Set<String> STRING_SET_VAULE = new HashSet<String>(Arrays.asList(new String[]{"fooValue1", "fooValue2"}));
    private static final String BOOLEAN_KEY = "boolean";
    private static final boolean BOOLEAN_VALUE = true;
    private static final String STRING_KEY = "string";
    private static final String STRING_VALUE = "fooString";
    private static final String FLOAT_KEY = "float";
    private static final float FLOAT_VALUE = 12344.0f;
    private static final String INT_KEY = "int";
    private static final int INT_VALUE = 34234;
    private static final String LONG_KEY = "long";
    private static final long LONG_VALUE = 3984;
    private static final String PASSWORD = "fooPassword";

    private SharedPreferences unencryptedPreferences;

    public void setUp() throws Exception {
        super.tearDown();
        unencryptedPreferences = getInstrumentation().getContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor edit = unencryptedPreferences.edit();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            edit.putStringSet(STRING_SET_KEY, STRING_SET_VAULE);
        }
        edit.putBoolean(BOOLEAN_KEY, BOOLEAN_VALUE);
        edit.putString(STRING_KEY, STRING_VALUE);
        edit.putFloat(FLOAT_KEY, FLOAT_VALUE);
        edit.putInt(INT_KEY, INT_VALUE);
        edit.putLong(LONG_KEY, LONG_VALUE);
        edit.commit();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        unencryptedPreferences.edit().clear().commit();
    }

    /**
     * Tries to read back values after doing the migration from an unencrypted {@link SharedPreferences}.
     */
    public void testMigrationWorks() {
        SharedPreferences preferences = SecureFactory.getPreferences(unencryptedPreferences, PASSWORD);
        assertEquals(FLOAT_KEY + " not found.", preferences.getFloat(FLOAT_KEY, 0), FLOAT_VALUE);
        assertEquals(BOOLEAN_KEY + " not found.", preferences.getBoolean(BOOLEAN_KEY, false), BOOLEAN_VALUE);
        assertEquals(INT_KEY + " not found.", preferences.getInt(INT_KEY, 0), INT_VALUE);
        assertEquals(STRING_KEY + " not found.", preferences.getString(STRING_KEY, null), STRING_VALUE);
        assertEquals(LONG_KEY + " not found.", preferences.getLong(LONG_KEY, 0), LONG_VALUE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            Arrays.equals(STRING_SET_VAULE.toArray(), preferences.getStringSet(STRING_SET_KEY, new HashSet<String>()).toArray());
        }
    }

    /**
     * Tries to access the encrypted {@link SecureSharedPreferences} with another {@link SharedPreferences}.
     */
    public void testMigrationEncrypts() {
        SharedPreferences preferences = SecureFactory.getPreferences(unencryptedPreferences, PASSWORD);
        try {
            preferences.getFloat(FLOAT_KEY, 0);
            fail("not encrypted.");
        } catch (Throwable e) {
            // success
        }
    }

    /**
     * Tries to access an already encrypted {@link SecureSharedPreferences} again.
     */
    public void testNoDoubleMigration() {
        testMigrationWorks();
        testMigrationWorks();
        testMigrationWorks();
    }
}
