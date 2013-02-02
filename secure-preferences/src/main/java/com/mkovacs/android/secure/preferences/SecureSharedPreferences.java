package com.mkovacs.android.secure.preferences;

import java.util.Map;

import android.content.SharedPreferences;
import edu.gmu.tec.scout.utilities.Encryption;

/**
 * Decorates SharedPreferences with AES Encryption.
 * 
 * @author NoTiCe
 */
public class SecureSharedPreferences implements SharedPreferences {
	private SharedPreferences prefs;
	private Encryption encryption;
	private EncryptionHelper helper;

	/**
	 * Initializes with a single {@link SharedPreferences} and the
	 * {@link Encryption} to use.
	 * 
	 * @param preferences
	 *            The {@link SharedPreferences} to use.
	 * @param encryption
	 *            The {@link Encryption} to use.
	 */
	public SecureSharedPreferences(SharedPreferences preferences,
			Encryption encryption) {
		this.prefs = preferences;
		this.encryption = encryption;
		helper = new EncryptionHelper(encryption);
	}

	/**
	 * {@inheritDoc SecureSharedPreferences}
	 */
	@Override
	public boolean contains(String key) {
		return prefs.contains(key);
	}

	/**
	 * {@inheritDoc SecureSharedPreferences}
	 */
	@Override
	public Editor edit() {
		return new SecuredEditor(helper, prefs.edit());
	}

	/**
	 * {@inheritDoc SecureSharedPreferences}
	 */
	@Override
	public Map<String, ?> getAll() {
		return prefs.getAll();
	}

	/**
	 * {@inheritDoc SecureSharedPreferences}
	 */
	@Override
	public boolean getBoolean(String key, boolean defValue) {
		return helper.readAndDecodeTemplate(prefs, key, defValue);
	}

	/**
	 * {@inheritDoc SecureSharedPreferences}
	 */
	@Override
	public float getFloat(String key, float defValue) {
		return helper.readAndDecodeTemplate(prefs, key, defValue);
	}

	/**
	 * {@inheritDoc SecureSharedPreferences}
	 */
	@Override
	public int getInt(String key, int defValue) {
		return helper.readAndDecodeTemplate(prefs, key, defValue);
	}

	/**
	 * {@inheritDoc SecureSharedPreferences}
	 */
	@Override
	public long getLong(String key, long defValue) {
		return helper.readAndDecodeTemplate(prefs, key, defValue);
	}

	/**
	 * {@inheritDoc SecureSharedPreferences}
	 */
	@Override
	public String getString(String key, String defValue) {
		return helper.readAndDecodeTemplate(prefs, key, defValue);
	}

	/**
	 * {@inheritDoc SecureSharedPreferences}
	 */
	@Override
	public void registerOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		prefs.registerOnSharedPreferenceChangeListener(listener);
	}

	/**
	 * {@inheritDoc SecureSharedPreferences}
	 */
	@Override
	public void unregisterOnSharedPreferenceChangeListener(
			OnSharedPreferenceChangeListener listener) {
		prefs.unregisterOnSharedPreferenceChangeListener(listener);
	}

	protected Encryption getEncryption() {
		return encryption;
	}

	protected SharedPreferences getPrefs() {
		return prefs;
	}

	protected void setHelper(EncryptionHelper helper) {
		this.helper = helper;
	}
}
