package quay.com.ipos.compliance.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import java.util.List;

import quay.com.ipos.compliance.data.local.entity.Employee;

@Dao
public interface EmployeeDao {

    @Query("SELECT * FROM 'employee'")
    LiveData<List<Employee>> fetchAllData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveAllEmployees(List<Employee> taskList);



    @Query("SELECT * FROM 'employee' WHERE empCode=:id")
    LiveData<List<Employee>> getTaskByPlace(String id);

    @Query("SELECT * FROM 'employee' WHERE empCode=:id")
    LiveData<Employee> getTaskById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long saveTask(Employee task);
}
