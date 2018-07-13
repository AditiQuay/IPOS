package quay.com.ipos.compliance.data.local;

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


/**
 * Created by deepak on 22/05/2018.
 */
@Database(entities = {Task.class, SubTask.class, BusinessPlaceEntity.class, Employee.class, TaskSchedulerEntity.class,
        TransactionEntity.class,
        AttachmentEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "compliance-db";

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
    //new code

  /*   private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }
*/

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
   /* private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                {
                                    // Add a delay to simulate a long-running operation
                                    addDelay();
                                    // Generate the data for pre-population
                                    AppDatabase database = AppDatabase.getInstance(appContext, executors);
                                    List<BusinessPlaceEntity> products = DataGenerator.generateProducts();
                                    List<TaskSchedulerEntity> comments =
                                            DataGenerator.generateCommentsForProducts(products);

                                    insertData(database, products, comments);
                                    // notify that the database was created and it's ready to be used
                                    database.setDatabaseCreated();
                                }
                            }
                        });
                    }
                }).build();
    }

    *//**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     *//*
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    private static void insertData(final AppDatabase database, final List<BusinessPlaceEntity> products,
                                   final List<TaskSchedulerEntity> comments) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                {
                    database.placeDao().savePlace(products);
                   // database.taskSchedulerDao().saveTaskSchedulers(comments);
                }
            }
        });
    }

    private static void addDelay() {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ignored) {
        }
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

*/
}
