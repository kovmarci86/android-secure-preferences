package com.github.kovmarci86.android.secure.preferences.util;

import java.util.Map;
import java.util.Set;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.github.kovmarci86.android.secure.preferences.SecuredEditor;

/**
 * Util classes for {@link com.github.kovmarci86.android.secure.preferences.SecureFactory}.
 * @author NoTiCe
 */
public final class SecureUtils {
    private static final String VERSION_KEY = "SecurePreferences_version";

    /**
     * Hidden util constructor.
     */
    private SecureUtils() {
    }

    /**
     * Copies data from one {@link SharedPreferences} to another.
     * @param from The source.
     * @param to The target.
     * @param version The version code to write into the preferences for future check.
     */
    @SuppressWarnings("unchecked")
    public static void migrateData(SharedPreferences from, SharedPreferences to, int version) {
        Map<String, ?> all = from.getAll();
        Set<String> keySet = all.keySet();
        Editor edit = to.edit();
        for (String key : keySet) {
            Object object = all.get(key);
            if (object == null) {
                // should not reach here
                edit.remove(key);
            } else if (object instanceof String) {
                edit.putString(key, (String) object);
            } else if (object instanceof Integer) {
                edit.putInt(key, (Integer) object);
            } else if (object instanceof Long) {
                edit.putLong(key, (Long) object);
            } else if (object instanceof Float) {
                edit.putFloat(key, (Float) object);
            } else if (object instanceof Boolean) {
                edit.putBoolean(key, (Boolean) object);
            } else if (object instanceof Set<?>) {
                edit.putStringSet(key, (Set<String>) object);
            }
        }
        edit.putInt(VERSION_KEY, version);
        SecuredEditor.compatilitySave(edit);
    }

    /**
     * Gets the version of {@link SharedPreferences} if any.
     * @param preferences The preferences to get the version.
     * @return The version or -1.
     */
    public static int getVersion(SharedPreferences preferences) {
        int currentVersion = preferences.getInt(VERSION_KEY, -1);
        return currentVersion;
    }
}
