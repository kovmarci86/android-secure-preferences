package com.mkovacs.android.secure.service.settings;

import com.mkovacs.android.secure.preferences.SecureSharedPreferences;

import edu.gmu.tec.scout.utilities.Encryption;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserDataServce {
	private static final String PREFS_NAME = "secure_user_data.prefs";
	private static final String KEY_USERNAME = "username";
	private static final String KEY_PASSWORD = "password";
	private static final String PASS = "turosretes";

	private SharedPreferences prefs;

	public UserDataServce(Context context) throws Exception {
		prefs = new SecureSharedPreferences(context.getSharedPreferences(
				PREFS_NAME, Context.MODE_PRIVATE), new Encryption(PASS));
	}

	public String getUsername() {
		return prefs.getString(KEY_USERNAME, null);
	}

	public void setUsername(String username) {
		Editor edit = prefs.edit();
		edit.putString(KEY_USERNAME, username);
		edit.commit();
	}

	public String getPassword() {
		return prefs.getString(KEY_PASSWORD, null);
	}

	public void setPassword(String password) {
		Editor edit = prefs.edit();
		edit.putString(KEY_PASSWORD, password);
		edit.commit();
	}

	public void reset() {
		prefs.edit().clear().commit();
	}
}
