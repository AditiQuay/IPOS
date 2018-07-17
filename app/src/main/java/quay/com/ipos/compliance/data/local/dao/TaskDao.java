package quay.com.ipos.compliance.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

import quay.com.ipos.compliance.data.local.entity.Task;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM 'task'")
    LiveData<List<Task>> getAllTask();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllTask(List<Task> taskList);

    @Query("SELECT * FROM 'task' WHERE store_id=:id")
    List<Task> fetchAllDataFilterByPlace(long id);

    @Query("SELECT * FROM 'task' WHERE store_id=:id")
    LiveData<List<Task>> getTaskByPlace(long id);

    @Query("SELECT * FROM 'task' WHERE task_id=:id")
    LiveData<Task> getTaskById(long id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveTask(Task task);

    @Query("DELETE FROM task")
    void delete();
}
