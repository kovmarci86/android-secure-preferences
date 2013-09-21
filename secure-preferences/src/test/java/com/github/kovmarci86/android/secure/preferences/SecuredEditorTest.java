package com.github.kovmarci86.android.secure.preferences;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;

import android.content.SharedPreferences.Editor;
import android.os.Build;

import com.github.kovmarci86.android.secure.preferences.encryption.EncryptionHelper;

/**
 * JUnit test for {@link SecuredEditor}.
 * @author NoTiCe
 */
public class SecuredEditorTest extends EasyMockSupport {
    private static final String FOO_KEY = "fooKey";
    private static final String FOO_ENCODED_STRING = "encodedString";
    private Editor editor;
    private EncryptionHelper helper;

    @Before
    public void setUp() throws Exception {
        editor = createMock(Editor.class);
        helper = createMock(EncryptionHelper.class);
    }

    @Test
    public void testPutString() {
        // GIVEN
        String fooValue = "fooString";
        // EXPECT
        expect(helper.encode(fooValue)).andReturn(FOO_ENCODED_STRING);
        expect(editor.putString(FOO_KEY, FOO_ENCODED_STRING)).andReturn(editor);
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        Editor result = sut.putString(FOO_KEY, fooValue);
        // THEN
        verifyAll();
        assertEquals("Must return self", sut, result);
    }

    @Test
    public void testPutStringSet() {
        // GIVEN
        Set<String> fooValue = new HashSet<String>();
        fooValue.add("fooString");
        // EXPECT
        expect(helper.encode(fooValue)).andReturn(FOO_ENCODED_STRING);
        expect(editor.putString(FOO_KEY, FOO_ENCODED_STRING)).andReturn(editor);
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        Editor result = sut.putStringSet(FOO_KEY, fooValue);
        // THEN
        verifyAll();
        assertEquals("Must return self", sut, result);
    }

    @Test
    public void testPutInt() {
        // GIVEN
        int fooValue = 2394578;
        // EXPECT
        expect(helper.encode(fooValue)).andReturn(FOO_ENCODED_STRING);
        expect(editor.putString(FOO_KEY, FOO_ENCODED_STRING)).andReturn(editor);
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        Editor result = sut.putInt(FOO_KEY, fooValue);
        // THEN
        verifyAll();
        assertEquals("Must return self", sut, result);
    }

    @Test
    public void testPutLong() {
        // GIVEN
        long fooValue = 2394578L;
        // EXPECT
        expect(helper.encode(fooValue)).andReturn(FOO_ENCODED_STRING);
        expect(editor.putString(FOO_KEY, FOO_ENCODED_STRING)).andReturn(editor);
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        Editor result = sut.putLong(FOO_KEY, fooValue);
        // THEN
        verifyAll();
        assertEquals("Must return self", sut, result);
    }

    @Test
    public void testPutFloat() {
        // GIVEN
        float fooValue = 2394578f;
        // EXPECT
        expect(helper.encode(fooValue)).andReturn(FOO_ENCODED_STRING);
        expect(editor.putString(FOO_KEY, FOO_ENCODED_STRING)).andReturn(editor);
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        Editor result = sut.putFloat(FOO_KEY, fooValue);
        // THEN
        verifyAll();
        assertEquals("Must return self", sut, result);
    }

    @Test
    public void testPutBoolean() {
        // GIVEN
        boolean fooValue = true;
        // EXPECT
        expect(helper.encode(fooValue)).andReturn(FOO_ENCODED_STRING);
        expect(editor.putString(FOO_KEY, FOO_ENCODED_STRING)).andReturn(editor);
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        Editor result = sut.putBoolean(FOO_KEY, fooValue);
        // THEN
        verifyAll();
        assertEquals("Must return self", sut, result);
    }

    @Test
    public void testRemove() {
        // GIVEN
        // EXPECT
        expect(editor.remove(FOO_KEY)).andReturn(editor);
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        Editor result = sut.remove(FOO_KEY);
        // THEN
        verifyAll();
        assertEquals("Must return self", sut, result);
    }

    @Test
    public void testClear() {
        // GIVEN
        // EXPECT
        expect(editor.clear()).andReturn(editor);
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        Editor result = sut.clear();
        // THEN
        verifyAll();
        assertEquals("Must return self", sut, result);
    }

    @Test
    public void testCommit() {
        // GIVEN
        Boolean fooResult = true;
        // EXPECT
        expect(editor.commit()).andReturn(fooResult);
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        boolean result = sut.commit();
        // THEN
        verifyAll();
        assertEquals("Must return self", fooResult, result);
    }

    @Test
    public void testApply() {
        // GIVEN
        // EXPECT
        editor.apply();
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        sut.apply();
        // THEN
        verifyAll();
    }

    @Test
    public void testSave() {
        // GIVEN
        // EXPECT - expectation may vary with build environment  
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            editor.apply();
        } else {
            expect(editor.commit()).andReturn(true);
        }
        replayAll();
        // WHEN
        SecuredEditor sut = new SecuredEditor(helper, editor);
        sut.save();
        // THEN
        verifyAll();
    }
}
