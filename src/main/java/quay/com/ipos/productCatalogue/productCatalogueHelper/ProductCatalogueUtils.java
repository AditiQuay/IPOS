package quay.com.ipos.productCatalogue.productCatalogueHelper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import quay.com.ipos.productCatalogue.productModal.CatalogueModal;
import quay.com.ipos.productCatalogue.productModal.ProductItemModal;
import quay.com.ipos.productCatalogue.productModal.ProductSectionModal;
import quay.com.ipos.utility.Constants;

/**
 * Created by niraj.kumar on 5/25/2018.
 */

public class ProductCatalogueUtils {

    public static void saveProductData(Context context,ArrayList<ProductSectionModal> list) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_PRODUCT_DATA.trim(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(Constants.PREF_KEY_PRODUCT_MAIN.trim(), json);
        editor.apply();     // This line is IMPORTANT !!!
    }
    public static  void saveSearchedProductData(Context context,ArrayList<ProductItemModal> list) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_SEARCHED_PRODUCT_DATA.trim(),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(Constants.PREF_KEY_SEARCHED_ITEM.trim(), json);
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

    public static ArrayList<ProductSectionModal> getProductSectionModals(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_PRODUCT_DATA.trim(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(Constants.PREF_KEY_PRODUCT_MAIN.trim(), null);
        Type type = new TypeToken<ArrayList<ProductSectionModal>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

    public static ArrayList<ProductItemModal> getSearchedItems(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_SEARCHED_PRODUCT_DATA.trim(),Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(Constants.PREF_KEY_SEARCHED_ITEM.trim(), null);
        Type type = new TypeToken<ArrayList<ProductItemModal>>() {
        }.getType();
        return gson.fromJson(json, type);
    }




    public static void saveProductDetail(Context context, ArrayList<CatalogueModal> list) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_PRODUCT_DETAIL_KEY.trim(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(Constants.PREF_KEY_PRODUCT_DETAIL.trim(), json);
        editor.commit();     // This line is IMPORTANT !!!
    }
    public static  void clearProductDetail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_PRODUCT_DETAIL_KEY.trim(),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
    public static ArrayList<CatalogueModal> getProductDetail(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constants.PREF_KEY_PRODUCT_DETAIL_KEY.trim(), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(Constants.PREF_KEY_PRODUCT_DETAIL.trim(), null);
        Type type = new TypeToken<ArrayList<CatalogueModal>>() {
        }.getType();
        return gson.fromJson(json, type);
    }

}
