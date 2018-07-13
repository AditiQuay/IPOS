package quay.com.ipos.compliance.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;



import java.util.List;

import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;

/**
 * Repository handling the work with products and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<BusinessPlaceEntity>> mObservableProducts;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
        mObservableProducts = new MediatorLiveData<>();

     /*   mObservableProducts.addSource(mDatabase.placeDao().loadPlaces(), new Observer<List<BusinessPlaceEntity>>() {
            @Override
            public void onChanged(@Nullable List<BusinessPlaceEntity> businessPlaceEntities) {
                {
                    Log.i("ddd", new Gson().toJson(businessPlaceEntities));
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableProducts.postValue(businessPlaceEntities);
                    }
                }
            }
        });*/
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
    public LiveData<List<BusinessPlaceEntity>> getPlaces() {
        return mObservableProducts;
    }

    public LiveData<BusinessPlaceEntity> loadProduct(final int productId) {
        return mDatabase.placeDao().getPlaces(productId);
    }
    public void saveProduct(BusinessPlaceEntity businessPlaceEntity) {
         mDatabase.placeDao().savePlace(businessPlaceEntity);
    }

  /*  public LiveData<List<TaskSchedulerEntity>> loadComments(final int productId) {
        return mDatabase.taskSchedulerDao().getAllTaskSchedulers(productId);
    }*/

}
