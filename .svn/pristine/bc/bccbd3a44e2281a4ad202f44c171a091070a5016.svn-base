package quay.com.ipos.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.VisibleForTesting;


import quay.com.ipos.compliance.data.local.dao.AttachmentDao;
import quay.com.ipos.compliance.data.local.dao.BusinessPlaceDao;
import quay.com.ipos.compliance.data.local.dao.EmployeeDao;
import quay.com.ipos.compliance.data.local.dao.SubTaskDao;
import quay.com.ipos.compliance.data.local.dao.TaskDao;
import quay.com.ipos.compliance.data.local.dao.TransactionDao;
import quay.com.ipos.compliance.data.local.entity.AttachmentEntity;
import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.compliance.data.local.entity.TaskSchedulerEntity;
import quay.com.ipos.compliance.data.local.entity.TransactionEntity;
import quay.com.ipos.data.local.dao.DDRInvoiceDao;
import quay.com.ipos.data.local.dao.MostUsedFunDao;
import quay.com.ipos.data.local.entity.DDRInvoiceData;
import quay.com.ipos.data.local.entity.MostUsed;


/**
 * Created by deepak on 22/05/2018.
 */
@Database(entities = {MostUsed.class , DDRInvoiceData.class, Task.class, SubTask.class, BusinessPlaceEntity.class, Employee.class, TaskSchedulerEntity.class,
        TransactionEntity.class,
        AttachmentEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "ipos-db";

    public abstract MostUsedFunDao mostUsedFunDao();

    public abstract DDRInvoiceDao ddrInvoiceDao();

    public abstract BusinessPlaceDao placeDao();

    public abstract TaskDao taskDao();

    public abstract SubTaskDao subtaskDao();

    public abstract EmployeeDao employeeDao();

    public abstract TransactionDao transactionDao();

    public abstract AttachmentDao attachmentDao();


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
