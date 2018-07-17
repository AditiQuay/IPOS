package quay.com.ipos.compliance.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

import quay.com.ipos.compliance.data.local.entity.SubTask;

@Dao
public interface SubTaskDao {

    @Query("SELECT * FROM 'sub_task'")
    LiveData<List<SubTask>> fetchAllData();

    @Query("SELECT * FROM 'sub_task' WHERE task_scheduler_id=:taskId")
    LiveData<List<SubTask>> getAllSubTaskofTask(long taskId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllSubTask(List<SubTask> taskList);


    @Query("SELECT * FROM 'sub_task' WHERE sub_task_id=:id")
    LiveData<SubTask> getSubTaskById(int id);

     @Query("SELECT * FROM 'sub_task' WHERE sub_task_id=:id")
     SubTask getSyncSubTaskById(int id);



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveSubTask(SubTask task);

    @Query("SELECT * FROM 'sub_task' WHERE isSync=:forSync")
    List<SubTask> getAllUnSyncSubTask(boolean forSync);

    @Delete
    void deleteSubTask(SubTask subTask);
}
