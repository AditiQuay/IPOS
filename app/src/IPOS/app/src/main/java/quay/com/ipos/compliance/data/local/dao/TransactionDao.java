package quay.com.ipos.compliance.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

import quay.com.ipos.compliance.data.local.entity.TransactionEntity;

@Dao
public interface TransactionDao {

    @Query("SELECT * FROM `transaction`")
    LiveData<List<TransactionEntity>> loadTx();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePlaces(List<TransactionEntity> txList);

    @Query("SELECT * FROM 'transaction' WHERE id=:id")
    LiveData<TransactionEntity> getTxs(int id);

}
