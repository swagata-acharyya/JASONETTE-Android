package com.jasonette.seed.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class AppUtil {

    private static final String KEY_CONFIG_STORE = "config_store";
    public static final String KEY_DOCS_DB = "docs_db";

    /**
     * Retrieves value for config from {@link SharedPreferences}
     *
     * @param key     key of the value required to be retrieved
     * @param context {@link Context} object
     * @return value if exists, {@code null} otherwise
     */
    public static String getConfigValue(String key, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(KEY_CONFIG_STORE, MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }
}