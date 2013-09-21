package com.github.kovmarci86.android.secure.preferences.util;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.github.kovmarci86.android.secure.preferences.SecuredEditor;

@RunWith(PowerMockRunner.class)
@PrepareForTest({SecuredEditor.class})
public class SecureUtilsTest extends EasyMockSupport {
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
    private static final String VERSION_KEY = "SecurePreferences_version";

    @Before
    public void setUp() throws Exception {
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testMigrateData() {
        // GIVEN
        PowerMock.mockStatic(SecuredEditor.class);
        SharedPreferences sourcePreferences = createMock(SharedPreferences.class);
        SharedPreferences targetPreference = createMock(SharedPreferences.class);
        Editor targetEditor = createMock(Editor.class);
        int fooVersion = 8549734;
        Map<String, Object> prefs = new LinkedHashMap<String, Object>();
        prefs.put(STRING_SET_KEY, STRING_SET_VAULE);
        prefs.put(BOOLEAN_KEY, BOOLEAN_VALUE);
        prefs.put(STRING_KEY, STRING_VALUE);
        prefs.put(FLOAT_KEY, FLOAT_VALUE);
        prefs.put(INT_KEY, INT_VALUE);
        prefs.put(LONG_KEY, LONG_VALUE);
        // EXPECT
        expect((Map<String, Object>) sourcePreferences.getAll()).andReturn(prefs);
        expect(targetPreference.edit()).andReturn(targetEditor);
        expect(targetEditor.putStringSet(STRING_SET_KEY, STRING_SET_VAULE)).andReturn(targetEditor);
        expect(targetEditor.putBoolean(BOOLEAN_KEY, BOOLEAN_VALUE)).andReturn(targetEditor);
        expect(targetEditor.putString(STRING_KEY, STRING_VALUE)).andReturn(targetEditor);
        expect(targetEditor.putFloat(FLOAT_KEY, FLOAT_VALUE)).andReturn(targetEditor);
        expect(targetEditor.putInt(INT_KEY, INT_VALUE)).andReturn(targetEditor);
        expect(targetEditor.putLong(LONG_KEY, LONG_VALUE)).andReturn(targetEditor);
        expect(targetEditor.putInt(VERSION_KEY, fooVersion)).andReturn(targetEditor);
        SecuredEditor.compatilitySave(targetEditor);
        replayAll();
        PowerMock.replay(SecuredEditor.class);
        // WHEN
        SecureUtils.migrateData(sourcePreferences, targetPreference, fooVersion);
        //THEN
        verifyAll();
        PowerMock.verify(SecuredEditor.class);
    }

    @Test
    public void testGetVersion() {
        // GIVEN
        SharedPreferences preferences = createMock(SharedPreferences.class);
        int fooVersion = 8549734;
        // EXPECT
        expect(preferences.getInt(VERSION_KEY, -1)).andReturn(fooVersion);
        replayAll();
        // WHEN
        int version = SecureUtils.getVersion(preferences);
        //THEN
        verifyAll();
        assertEquals("Wrong version result", fooVersion, version);
    }

}
