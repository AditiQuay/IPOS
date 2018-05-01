package quay.com.ipos.utility;

import android.content.Context;
import android.content.SharedPreferences;

import quay.com.ipos.application.IPOSApplication;

/**
 * Created by Gaurav on 27/1/17.
 */

public class Prefs {
    private static final String TAG = "Prefs";
    public static final String PREFS_NAME = "Mudzic";

    private static SharedPreferences getPrefs() {
        Context context1 = IPOSApplication.getContext();
        return context1.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private static SharedPreferences.Editor getEditor() {
        return getPrefs().edit();
    }

    public static void putStringPrefs(String key, String value) {
        getEditor().putString(key, value).commit();
    }

    public static String getStringPrefs(String key) {
        return getPrefs().getString(key, "");
    }

    public static void putIntegerPrefs(String key, int value) {
        getEditor().putInt(key, value).commit();
    }

    public static int getIntegerPrefs(String key) {
        return getPrefs().getInt(key, 0);
    }

    public static void putBooleanPrefs(String key, boolean value) {
        getEditor().putBoolean(key, value).commit();
    }

    public static boolean getBooleanPrefs(String key) {
        return getPrefs().getBoolean(key, false);
    }

    public static void putFloatPrefs(String key, float value) {
        getEditor().putFloat(key, value).commit();
    }

    public static float getFloatPrefs(String key) {
        return getPrefs().getFloat(key, 0.0f);
    }

    public static void putLongPrefs(String key, long value) {
        getEditor().putLong(key, value).commit();
    }

    public static long getLongPrefs(String key) {
        return getPrefs().getLong(key, 000000);
    }

    public static void putDoublePrefs(String key, double value) {
        getEditor().putLong(key, Double.doubleToLongBits(value)).commit();
    }

    public static double getDoublePrefs(String key) {
        return Double.longBitsToDouble(getPrefs().getLong(key, 0));
    }

    public static void clearValue(String key) {
        getEditor().remove(key).commit();
    }

    public static void clearSharedPrefs() {
        getEditor().clear().commit();
    }


}
