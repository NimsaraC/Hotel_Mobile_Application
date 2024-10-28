package com.android.luxevista;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static final String APP_NAME = "LuxeVista";
    public static String STATUS = "status";
    public static String USER_ID = "userId";

    public SharedPreference() {
        super();
    }

    public void SaveString(Context context, String value, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public void SaveInt(Context context, int value, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void SaveBoolean(Context context, Boolean value, String key) {
        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getString(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, MODE_PRIVATE);
        return preferences.getString(key, null);
    }
    public boolean GetBoolean(Context context, String key) {

        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }
    public int GetInt(Context context, String key)
    {

        SharedPreferences preferences = context.getSharedPreferences(APP_NAME, MODE_PRIVATE);
        return preferences.getInt(key,-1);
    };
    public void clearPref(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(APP_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }

}
