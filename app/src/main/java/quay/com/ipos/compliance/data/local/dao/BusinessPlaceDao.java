package quay.com.ipos.compliance.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;

@Dao
public interface BusinessPlaceDao {

    @Query("SELECT * FROM places")
    LiveData<List<BusinessPlaceEntity>> loadPlaces();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePlace(List<BusinessPlaceEntity> placeEntityList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePlace(BusinessPlaceEntity placeEntity);

    @Query("SELECT * FROM places WHERE id=:id")
    LiveData<BusinessPlaceEntity> getPlaces(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOnlySingleRecord(BusinessPlaceEntity entity);

    @Query("SELECT * FROM places")
    LiveData<List<BusinessPlaceEntity>> fetchAllData();

    @Query("SELECT * FROM places")
    List<BusinessPlaceEntity> fetchAllDataWithoutLive();


    @Delete
    void deleteRecord(BusinessPlaceEntity entity);


    @Query("DELETE FROM places")
    void delete();

}
