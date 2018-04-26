package quay.com.ipos.utility;

import android.content.Context;
import android.content.SharedPreferences;


//Shared Preference Class for preference file
public final class SharedPrefUtil {


    public static final String PLOTS_PERF = "plots_perference";


    private SharedPrefUtil(){
        throw new Error("Do n" +
                "ot need instantiate!");
    }


    public static void setGcmRegistrationID(String key, String defValue, Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defValue);
        editor.commit();
    }
    /**
     *
     * @param key
     * @param defaultValue
     * @param context
     */
    public static void setRefreshToken(String key, String defaultValue, Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defaultValue);
        editor.commit();
    }
    public static void forgotPasswordToken(String key,String defaultValue,Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defaultValue);
        editor.commit();
    }
    public static void setEmail(String key, String defValue, Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defValue);
        editor.commit();
    }
    public static String getGcmRegistrationID(String key, String defValue, Context context){
        return getSharedPreferences(context).getString(key, defValue);
    }
    /**
     *
     * @param key
     * @param defaultValue
     * @param context
     */
    public static void setOtp(String key, String defaultValue, Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defaultValue);
        editor.commit();
    }

    public static void setHubID(String key, String defValue, Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defValue);
        editor.commit();
    }


    public static String getHubID(String key, String defValue, Context context){
        return getSharedPreferences(context).getString(key, defValue);
    }



    public static void setUserId(String key, String defValue, Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defValue);
        editor.commit();
    }


    public static String getUserId(String key, String defValue, Context context){
        return getSharedPreferences(context).getString(key, defValue);
    }


    public static void setGroupAssigned(String key, Boolean defValue, Context context){
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, defValue);
        editor.commit();
    }

    public static Boolean getGroupAssigned(String key, Boolean defValue, Context context){
        return getSharedPreferences(context).getBoolean(key, defValue);
    }


    /**
     * get Access Token
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static String getAccessToken(String key, String defValue, Context context){
        return getSharedPreferences(context).getString(key, defValue);
    }


    /**
     * set Access Token
     * @param key
     * @param defaultValue
     * @param context
     */
    public static void setAccessToken(String key, String defaultValue, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defaultValue);
        editor.commit();
    }


    /**
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static long getLong(String key, long defValue, Context context) {
        return getSharedPreferences(context).getLong(key, defValue);
    }

    /**
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static String getString(String key, String defValue, Context context) {
        return getSharedPreferences(context).getString(key, defValue);
    }

    /**
     * @param key
     * @param value
     * @param context
     */
    public static void putBoolean(String key, boolean value, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }


    /**
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static boolean getBoolean(String key, boolean defValue, Context context) {
        return getSharedPreferences(context).getBoolean(key, defValue);
    }

    /**
     * @param key
     * @param value
     * @param context
     */
    public static void putFloat(String key, float value, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    /**
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static float getFloat(String key, float defValue, Context context) {
        return getSharedPreferences(context).getFloat(key, defValue);
    }

    /**
     * @param key
     * @param value
     * @param context
     */
    public static void putInt(String key, int value, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }
    /**
     * @param key
     * @param defValue
     * @param context
     * @return
     */
    public static int getInt(String key, int defValue, Context context) {
        return getSharedPreferences(context).getInt(key, defValue);
    }
    /**
     * @param key
     * @param value
     * @param context
     */
    public static void putLong(String key, long value, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    /**
     * @param key
     * @param defaultValue
     * @param context
     */
    public static void putString(String key, String defaultValue, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(key, defaultValue);
        editor.commit();
    }

    /**
     * @param key
     * @param context
     */
    public static void remove(String key, Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * @param context
     * @return
     */
    private static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences pref = context.getSharedPreferences(SharedPrefUtil.PLOTS_PERF, Context.MODE_PRIVATE);
        return pref;
    }
}
