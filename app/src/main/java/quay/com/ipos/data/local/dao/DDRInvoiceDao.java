package quay.com.ipos.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import quay.com.ipos.data.local.entity.DDRInvoiceData;
import quay.com.ipos.data.local.entity.MostUsed;

@Dao
public interface DDRInvoiceDao {

    @Query("SELECT * FROM 'ddr_invoice_data'")
    List<DDRInvoiceData> fetchAllData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllEmployees(List<DDRInvoiceData> taskList);

    @Query("SELECT * FROM 'ddr_invoice_data' WHERE ddrCode=:id")
    LiveData<List<DDRInvoiceData>> getTaskByPlace(String id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveTask(DDRInvoiceData mostUsed);

 /*   @Query(" UPDATE 'ddr_invoice_data' SET count = count + 1 WHERE funName=:funName ")
    int updateFunction(String funName);

*/
}
