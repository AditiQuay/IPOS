package quay.com.ipos.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;


import quay.com.ipos.data.local.dao.MostUsedFunDao;
import quay.com.ipos.data.local.entity.MostUsed;


/**
 * Created by deepak on 22/05/2018.
 */
@Database(entities = {MostUsed.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "ipos-db";

    public abstract MostUsedFunDao mostUsedFunDao();


    //my code
    public static AppDatabase getAppDatabase(Context context) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME).build();
                }
            }

        }
        return sInstance;
    }


}
