package com.mkovacs.android.secure.service.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.mkovacs.android.secure.preferences.SecureFactory;
import com.mkovacs.android.secure.preferences.SecureSharedPreferences;

/**
 * A sample Data Access Object, which is using {@link SecureSharedPreferences}.
 * @author NoTiCe
 */
public class UserDataServce {
    private static final String PREFS_NAME = "secure_user_data.prefs";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String PASS = "turosretes";

    private SharedPreferences prefs;

    /**
     * Initializes the serivice with a {@link Context}.
     * @param context The current {@link Context}
     * @throws Exception If the initialization fails.
     */
    public UserDataServce(Context context) throws Exception {
        // prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs = SecureFactory.getPreferences(context, PREFS_NAME, PASS);
    }

    /**
     * Reads back the username.
     * @return The username.
     */
    public String getUsername() {
        return prefs.getString(KEY_USERNAME, null);
    }

    /**
     * Sets the username.
     * @param username The username.
     */
    public void setUsername(String username) {
        Editor edit = prefs.edit();
        edit.putString(KEY_USERNAME, username);
        edit.commit();
    }

    /**
     * Gets the user's password.
     * @return The user's password.
     */
    public String getPassword() {
        return prefs.getString(KEY_PASSWORD, null);
    }

    /**
     * Sets the user's password.
     * @param password The user's password.
     */
    public void setPassword(String password) {
        Editor edit = prefs.edit();
        edit.putString(KEY_PASSWORD, password);
        edit.commit();
    }

    /**
     * Removes stored data.
     */
    public void reset() {
        prefs.edit().clear().commit();
    }
}
