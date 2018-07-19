package quay.com.ipos.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.List;

import quay.com.ipos.data.local.entity.MostUsed;

@Dao
public interface MostUsedFunDao {

    @Query("SELECT * FROM 'most_used'")
    LiveData<List<MostUsed>> fetchAllData();
//LIMIT 3
    @Query("SELECT * FROM 'most_used' ORDER BY count DESC LIMIT 3 ")
    LiveData<List<MostUsed>> fetchAllDataWITHLIMIT();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllEmployees(List<MostUsed> taskList);

    @Query("SELECT * FROM 'most_used' WHERE funName=:id")
    LiveData<List<MostUsed>> getTaskByPlace(String id);

    @Query("SELECT * FROM 'most_used' WHERE funName=:id")
    LiveData<MostUsed> getTaskById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveTask(MostUsed mostUsed);

   /* @Update(UPDATE "Your tablename"
            SET "counter column name"= "counter column name"+ 1
            WHERE id= "pass your reference id";)
    long*/

  /*  public int getCount(String identifier) {
        String countQuery = "UPDATE" + TABLE_SRESULT + " SET " + KEY_COUNT + "=" + KEY_COUNT + "+1"
                + " WHERE " + KEY_IDENTIFIER + "='" + identifier + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }
        return cursor.getCount();
    }*/

    @Query(" UPDATE 'most_used' SET count = count + 1 WHERE funName=:funName ")
    int updateFunction(String funName);


}
