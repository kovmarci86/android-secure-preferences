package com.mkovacs.android.secure.preferences;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.mkovacs.android.secure.preferences.encryption.EncryptionAlgorithm;
import com.mkovacs.android.secure.preferences.encryption.EncryptionHelper;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Decorates SharedPreferences with AES Encryption.
 * @author NoTiCe
 */
public class SecureSharedPreferences implements SharedPreferences {
    private SharedPreferences prefs;
    private EncryptionAlgorithm encryption;
    private EncryptionHelper helper;

    /**
     * Initializes with a single {@link SharedPreferences}
     * and the {@link edu.gmu.tec.scout.utilities.Encryption} to use.
     *
     * @param preferences
     *            The {@link SharedPreferences} to use.
     * @param encryption
     *            The {@link edu.gmu.tec.scout.utilities.Encryption} to use.
     */
    public SecureSharedPreferences(SharedPreferences preferences, EncryptionAlgorithm encryption) {
        this.prefs = preferences;
        this.encryption = encryption;
        helper = new EncryptionHelper(encryption);
    }

    @Override
    public boolean contains(String key) {
        return prefs.contains(key);
    }

    @Override
    public Editor edit() {
        return new SecuredEditor(helper, prefs.edit());
    }

    @Override
    public Map<String, ?> getAll() {
        return prefs.getAll();
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return helper.readAndDecodeTemplate(prefs, key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return helper.readAndDecodeTemplate(prefs, key, defValue);
    }

    @Override
    public int getInt(String key, int defValue) {
        return helper.readAndDecodeTemplate(prefs, key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return helper.readAndDecodeTemplate(prefs, key, defValue);
    }

    @Override
    public String getString(String key, String defValue) {
        return helper.readAndDecodeTemplate(prefs, key, defValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        String value = helper.readAndDecodeTemplate(prefs, key, "");
        if ("".equals(value)) {
            return defValues;
        }
        String[] sets = TextUtils.split(value, Pattern.quote(SecuredEditor.SET_DELIMITER));
        LinkedHashSet<String> set = new LinkedHashSet<String>(sets.length);
        for (String s : sets) {
            set.add(s);
        }
        return set;
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener);
    }

    protected EncryptionAlgorithm getEncryption() {
        return encryption;
    }

    protected SharedPreferences getPrefs() {
        return prefs;
    }

    protected void setHelper(EncryptionHelper helper) {
        this.helper = helper;
    }
}
