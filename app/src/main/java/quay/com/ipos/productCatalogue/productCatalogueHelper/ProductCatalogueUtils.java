package quay.com.ipos.productCatalogue.productCatalogueHelper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import quay.com.ipos.productCatalogue.productModal.ProductItemModal;
import quay.com.ipos.productCatalogue.productModal.ProductSectionModal;
import quay.com.ipos.utility.Constants;

/**
 * Created by niraj.kumar on 5/25/2018.
 */

public class ProductCatalogueUtils {

    public static void saveProductData(Context context,ArrayList<ProductSectionModal> list, String key) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_PRODUCT_DATA.trim(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }
    public static  void saveSearchedProductData(Context context,ArrayList<ProductItemModal> list, String key) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_SEARCHED_PRODUCT_DATA.trim(),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }
    public static  void clearProductData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_PRODUCT_DATA.trim(),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }
    public static  void clearSearchedProductData(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_SEARCHED_PRODUCT_DATA.trim(),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static ArrayList<ProductSectionModal> getProductSectionModals(Context context,String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<ProductSectionModal>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static ArrayList<ProductItemModal> getSearchedItems(Context context,String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<ProductItemModal>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}
