package com.mkovacs.android.secure.preferences;

import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.text.TextUtils;

import com.mkovacs.android.secure.preferences.encryption.EncryptionHelper;

import java.util.Set;

/**
 * An {@link Editor} decorator using AES encription.
 *
 * @author NoTiCe
 */
public class SecuredEditor implements Editor {

    public static final String SET_DELIMITER = "|\u2006|";

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
    public SecuredEditor putString(String key, String value) {
        editor.putString(key, helper.encode(value));
        return this;
    }

	@Override
	public SecuredEditor putStringSet(String key, Set<String> values) {
		String value = TextUtils.join(SET_DELIMITER, values);
		editor.putString(key, helper.encode(value));
        return this;
	}

	@Override
    public SecuredEditor putInt(String key, int value) {
        editor.putString(key, helper.encode(value));
        return this;
    }

    @Override
    public SecuredEditor putLong(String key, long value) {
        editor.putString(key, helper.encode(value));
        return this;
    }

    @Override
    public SecuredEditor putFloat(String key, float value) {
        editor.putString(key, helper.encode(value));
        return this;
    }

    @Override
    public SecuredEditor putBoolean(String key, boolean value) {
        editor.putString(key, helper.encode(value));
        return this;
    }

    @Override
    public SecuredEditor remove(String key) {
        editor.remove(key);
        return this;
    }

    @Override
    public SecuredEditor clear() {
        editor.clear();
        return this;
    }

    @Override
    public boolean commit() {
        return editor.commit();
    }

	/**
	 * Smarter version of original {@link android.content.SharedPreferences.Editor#apply()}
	 * method that simply call {@link android.content.SharedPreferences.Editor#commit()} for pre API 9 Androids.
	 */
    @Override
    public void apply() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
