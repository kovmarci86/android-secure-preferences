package com.mkovacs.android.secure.preferences;

import com.mkovacs.android.secure.preferences.encryption.EncryptionHelper;

import android.content.SharedPreferences.Editor;

/**
 * An {@link Editor} decorator using AES encription.
 * @author NoTiCe
 */
public class SecuredEditor implements Editor {
    private Editor editor;
    private EncryptionHelper helper;

    /**
     * Initializes with the {@link EncryptionHelper} an the original
     * {@link Editor}.
     * @param helper
     *            The helper to use.
     * @param edit
     *            The editor to use.
     */
    public SecuredEditor(EncryptionHelper helper, Editor edit) {
        this.helper = helper;
        this.editor = edit;
    }

    @Override
    public Editor putString(String key, String value) {
        return editor.putString(key, helper.encode(value));
    }

    @Override
    public Editor putInt(String key, int value) {
        return editor.putString(key, helper.encode(value));
    }

    @Override
    public Editor putLong(String key, long value) {
        return editor.putString(key, helper.encode(value));
    }

    @Override
    public Editor putFloat(String key, float value) {
        return editor.putString(key, helper.encode(value));
    }

    @Override
    public Editor putBoolean(String key, boolean value) {
        return editor.putString(key, helper.encode(value));
    }

    @Override
    public Editor remove(String key) {
        return editor.remove(key);
    }

    @Override
    public Editor clear() {
        return editor.clear();
    }

    @Override
    public boolean commit() {
        return editor.commit();
    }
}
