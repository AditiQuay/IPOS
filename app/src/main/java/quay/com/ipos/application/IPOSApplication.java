package quay.com.ipos.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import quay.com.ipos.R;
import quay.com.ipos.data.local.AppDatabase;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.modal.ProductListResult;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.utility.Constants;


public class IPOSApplication extends MultiDexApplication {
    private static Context mContext;

    /** The instance. */
    private static IPOSApplication _instance = null;
    public static boolean isRefreshed=false;
    public static boolean isClicked=false;
    public static double totalAmount = 0.0;
    public static double totalpointsToRedeem = 0;
    public static double totalpointsToRedeemValue = 0;
    public static ArrayList<ProductSearchResult.Datum> mProductListResult= new ArrayList<>();
//    public static ArrayList<ProductSearchResult.Datum> mProductSearchResult= new ArrayList<>();
    public static ArrayList<OrderList.Datum> mOrderList= new ArrayList<>();

    public static ArrayList<ProductSearchResult.Datum> datumArrayList = new ArrayList<>();
    public static HashMap<String,ArrayList<ProductSearchResult.Datum>> datumSameCode = new HashMap<String,ArrayList<ProductSearchResult.Datum>>();




    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
        mContext = getApplicationContext();
        MultiDex.install(this);
        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name(getString(R.string.app_name))
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        @SuppressWarnings("deprecation")
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheOnDisc(true).cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        @SuppressWarnings("deprecation")
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .discCacheSize(Constants.MAX_DISK_CACHE_SIZE).memoryCacheSize(Constants.MAX_MEMORY_CACHE_SIZE)
                .memoryCache(new WeakMemoryCache()).defaultDisplayImageOptions(defaultOptions).threadPoolSize(5)
                .build();
        ImageLoader.getInstance().init(config);


    }

    /**
     * Gets the app instance.
     *
     * @return the app instance
     */
    public static IPOSApplication getAppInstance() {
        return _instance;
    }

    public static Context getContext() {
        return mContext;
    }

    public static AppDatabase getDatabase() {
        return AppDatabase.getAppDatabase(mContext);
    }


    public static void showToast(String message) {
        Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
    }
}


