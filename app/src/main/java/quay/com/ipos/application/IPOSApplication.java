package quay.com.ipos.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import quay.com.ipos.R;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.utility.Constants;


public class IPOSApplication extends MultiDexApplication {
    private static Context mContext;

    /** The instance. */
    private static IPOSApplication _instance = null;

    public static ArrayList<ProductList.Datum> mProductList= new ArrayList<>();
    public static ArrayList<OrderList.Datum> mOrderList= new ArrayList<>();




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


}


