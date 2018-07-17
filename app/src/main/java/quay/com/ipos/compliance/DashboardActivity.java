package quay.com.ipos.compliance;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import quay.com.ipos.BuildConfig;
import quay.com.ipos.OnStoreSelectionListener;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.constants.AnnotationComplianceType;
import quay.com.ipos.compliance.constants.AnnotationStoreType;

import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.compliance.data.remote.model.ComplianceDetailsResponse;
import quay.com.ipos.compliance.fragment.ComplianceFragmentHeader;
import quay.com.ipos.compliance.fragment.ComplianceFragmentMain;
import quay.com.ipos.data.local.AppDatabase;
import quay.com.ipos.data.remote.APIService;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.utility.SharedPrefUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity
        implements  ComplianceFragmentMain.ComplianceTypeListener
        , OnStoreSelectionListener,ComplianceFragmentHeader.OnComplianeFilterListener {

    private static final String TAG = DashboardActivity.class.getSimpleName();
    private ComplianceFragmentHeader complianceFragmentHeader;
    private ComplianceFragmentMain complianceFragmentMain;
    private Activity activity;
    private AppDatabase appDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        appDatabase = AppDatabase.getAppDatabase(activity);
        setContentView(R.layout.c_activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

      /*  DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
*/
     /*   NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
*/
        //openComplianceTracking(null);


        loadCompliances();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

  /*  @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (BuildConfig.DEBUG) {
         //   getMenuInflater().inflate(R.menu.dashboard, menu);
        }
        return true;
    }*/

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (BuildConfig.DEBUG) {
            switch (item.getItemId()) {
                case R.id.action_sync:
                    new SyncData().execute();
                    return true;
                case R.id.action_setAlarm:
                    BootReceiver bootReceiver = new BootReceiver();
                    bootReceiver.onReceive(getApplicationContext(), getIntent());
                    return true;
                case R.id.action_CancelAlarm:
                    DatabaseHelper database = DatabaseHelper.getInstance(getApplicationContext());
                    List<TaskData> TaskDataList = database.getNotificationList(ReminderConstant.ACTIVE);
                    Intent alarmIntent = new Intent(getApplicationContext(), AlarmReceiver.class);

                    for (TaskData TaskData : TaskDataList) {
                        Calendar calendar = DateAndTimeUtil.parseDateAndTime(TaskData.getDateAndTime());
                        calendar.set(Calendar.SECOND, 0);
                        AlarmUtil.cancelAlarm(getApplicationContext(), alarmIntent, TaskData.getNotificationId());
                    }
                    return true;
                case R.id.action_CheckActiveAlarm:
                    DatabaseHelper database2 = DatabaseHelper.getInstance(getApplicationContext());
                    List<TaskData> TaskDataList2 = database2.getNotificationList(ReminderConstant.ACTIVE);
                    Intent alarmIntent2 = new Intent(getApplicationContext(), AlarmReceiver.class);
                    for (TaskData TaskData : TaskDataList2) {
                        boolean alarmUp = (PendingIntent.getBroadcast(getApplicationContext(), TaskData.getNotificationId(),
                                alarmIntent2,
                                PendingIntent.FLAG_NO_CREATE) != null);

                        if (alarmUp) {
                            Log.d("myTag"+TaskData.getNotificationId(), "Alarm is already active");
                        }else {
                            Log.d("myTag"+TaskData.getNotificationId(), "Alarm is not active");
                        }
                    }
                    return true;

                default:
            }

        }
        return super.onOptionsItemSelected(item);
    }
*/
   /* @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

            openComplianceTracking(null);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

            openUpdateTask();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }*//* else if (id == R.id.nav_share) {

            printCurrentTime();
        } else if (id == R.id.nav_logout) {
            funLogout();

        }*//*

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
*/
    private void printCurrentTime() {
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        // textView is the TextView view that should display it
        Log.i("current Time in DF", currentDateTimeString);
        long time = new Date().getTime();
        Log.i("current Time in Long ", time + "");
        String getDateTimeString = DateFormat.getDateTimeInstance().format(time);
        Log.i("current Time in convt ", getDateTimeString + "");

        //difference
        long currentTimeLong = System.currentTimeMillis(); // will get you current time in milli
        long convertedLong = System.currentTimeMillis() + 1050000;//1525244396284L;
        long difference = currentTimeLong - convertedLong;
        String strDiff = String.valueOf(difference);

        Log.i("difference ", strDiff + "");
        String diffDF = DateFormat.getDateTimeInstance().format(difference);
        Log.i("diff Time in DF ", diffDF + "");


        String dateStart = "01/14/2012 09:29:58";
        String dateStop = "01/15/2012 10:31:48";

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            //long diff = d2.getTime() - d1.getTime();
            long diff = difference;

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            Log.i(TAG, diffDays + " days, ");
            Log.i(TAG, diffHours + " hours, ");
            Log.i(TAG, diffMinutes + " minutes, ");
            Log.i(TAG, diffSeconds + " seconds.");


         /*   new RushSearch().whereEqual("task_due_date", currentTimeLong)
                    .or().whereGreaterThan("task_due_date", currentTimeLong)
                    .find(TaskData.class, new RushSearchCallback<TaskData>() {
                        @Override
                        public void complete(List<TaskData> list) {
                            for (TaskData taskData : list) {
                                Log.i(TAG, "dueDate" + taskData.getDateAndTime());
                            }
                        }
                    });*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void funLogout() {
        new Thread() {
            @Override
            public void run() {
                //super.run();

              //  RushCore.getInstance().clearDatabase();

                //clear all data in database
                AppDatabase.getAppDatabase(activity).clearAllTables();
                //clear all sharedPreferences
                SharedPrefUtil.clearSharedPreferences(activity);



                new AsyncTask<Void,Void,Void>()
                {
                    @Override
                    protected Void doInBackground(Void... params)
                    {
                        {
                            try
                            {
                                FirebaseInstanceId.getInstance().deleteInstanceId();
                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result)
                    {
                        //call your activity where you want to land after log out
                        finishAffinity();
                       /* Intent intent = new Intent(getApplicationContext(), SplashScreenActivity.class);
                        startActivity(intent);*/
                    }
                }.execute();

            }
        }.start();
    }

    private void openUpdateTask() {
        /*Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
        startActivity(intent);*/


    }


    public void openComplianceTracking(View view) {
        //for title
    /*  Toolbar toolbar = findViewById(R.id.toolbar);
        TextView title = (TextView) toolbar.findViewById(R.id.title_text);
        title.setText("New Title");*/
        getSupportActionBar().setTitle(getResources().getString(R.string.compliance_tracking));

        //for header
        complianceFragmentHeader = ComplianceFragmentHeader.newInstance(getResources().getString(R.string.txtallstore) + "", "");
        getSupportFragmentManager().beginTransaction().replace(R.id.contentPanelHeader, complianceFragmentHeader).commit();

        //for detail
       /* ComplianceFragmentA complianceFragmentA = ComplianceFragmentA.newInstance("", "");
        getSupportFragmentManager().beginTransaction().replace(R.id.contentPanel, complianceFragmentA).commit();
       */

       complianceFragmentMain = ComplianceFragmentMain.newInstance("", AnnotationStoreType.ALL, 0);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentPanel, complianceFragmentMain).commit();

    }



    @Override
    public void onPageSelected(@AnnotationComplianceType.ComplianceType int complianceType) {
        if (complianceFragmentHeader != null) {
            complianceFragmentHeader.updateView(complianceType);
        }
    }



    @Override
    public void onStoreSelected(String strStoreName, String storeId) {
        try {
            if (complianceFragmentMain != null) {
               Intent intent = new Intent(this, StorewiseComplianceActivity.class);
                intent.putExtra("title", strStoreName);
                intent.putExtra("storeid", storeId);
                intent.putExtra("curr_pos", complianceFragmentMain.getCurrentViewPagerPos());
                Log.i(TAG, "strStoreName" + strStoreName + ", storeid" + storeId);
                startActivity(intent);
                Log.i(TAG, "strStoreName" + strStoreName + ", storeid" + storeId);
            } else {
                Log.e(TAG, "complianceFragmentMain is null");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
       }

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        super.onOptionsMenuClosed(menu);
    }

    @Override
    public void onClick(@AnnotationComplianceType.ComplianceType int complianceType) {
        if (complianceFragmentMain != null) {
            complianceFragmentMain.updateFilter(complianceType);
        }
    }


   /* private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, AlarmReceiver.class);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION_ID, delay);
        notificationIntent.putExtra(AlarmReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, delay, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }*/

   /* private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        return builder.build();
    }*/



    private void loadCompliances() {

        APIService apiService = RestService.getApiServiceSimple();
        Call<ComplianceDetailsResponse> call = apiService.loadComplianceDetail();
        call.enqueue(new Callback<ComplianceDetailsResponse>() {
            @Override
            public void onResponse(Call<ComplianceDetailsResponse> call, Response<ComplianceDetailsResponse> response) {
                if (response.errorBody() != null) {
                    if (BuildConfig.DEBUG) {
                        Toast.makeText(activity, "" + "message: " + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                    Log.i("Rest errorBody", "code:" + response.code() + " " + "message:" + response.message());
                }
                if(response.body()!=null) {
                    ComplianceDetailsResponse compResp = response.body();
                    Log.i("Rest onResponse", response.toString() + new Gson().toJson(compResp));
                    if(response.code()==200 && compResp.statusCode!=0) {
                        loadToDatabase(compResp);

                    }else {
                        Log.i("Rest errorBody", "code:" + response.code() + " " + "message:" + response.message());

                    }
                }
            }

            @Override
            public void onFailure(Call<ComplianceDetailsResponse> call, Throwable t) {
                Log.i("Rest onFailure", t.toString());
            }
        });

    }
    ComplianceDetailsResponse compResp;
    private void loadToDatabase(ComplianceDetailsResponse compResp) {
        this.compResp = compResp;
        new DatabaseAsync().execute();
    }

    private class DatabaseAsync extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {

                List<Employee> employeeList = compResp.response.employeeList;
                appDatabase.employeeDao().saveAllEmployees(employeeList);

                Log.i(TAG,"compResp.response.businessPlaceList:"+new Gson().toJson(compResp.response.businessPlaceList));
               if (compResp.response.businessPlaceList == null) {

                    return false;
                }
                List<BusinessPlaceEntity> placeEntityList = compResp.response.businessPlaceList;
                appDatabase.placeDao().savePlace(placeEntityList);


                Log.i(TAG,"compResp.response.taskList:"+new Gson().toJson(compResp.response.taskList));
                if (compResp.response.taskList == null) {
                    Log.i(TAG, "Task List is null");
                    return false;
                }


               /* appDatabase.taskDao().delete();
                appDatabase.subtaskDao().delete();
*/
                List<Task> taskList = compResp.response.taskList;
                appDatabase.taskDao().saveAllTask(taskList);


                List<SubTask> subtaskList = compResp.response.subTaskList;
                if (subtaskList != null)
                    appDatabase.subtaskDao().saveAllSubTask(subtaskList);


                //local data
          /*  String json = LocalJsonReader.getJson(activity);
            ComplianceDetailsResponse storeResponse = new Gson().fromJson(json, ComplianceDetailsResponse.class);
            List<BusinessPlaceEntity> placeEntityList = storeResponse.response.businessPlaceList;
            Log.i("ddd", new Gson().toJson(placeEntityList));
            appDatabase.placeDao().savePlace(placeEntityList);*/

                //List<UserProfileModel> userList = storeResponse.user_list;
           /* List<Task> taskList = new ArrayList<>();
            for (StoreResponseNew.StoreDataNew storeDataNew : storeResponse.storeDataList) {
                taskList.addAll(storeDataNew.compliance_data);
            }*/

                //  appDatabase.taskDao().saveAllEmployees(taskList);

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
           // SharedPrefUtil.putBoolean(KeyConstants.ISLOGGEDIN.trim(), true, getApplicationContext());
           /* Intent intent = new Intent(getApplicationContext(),DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);*/
            Log.i("data", aVoid+"");
            if (aVoid) {
                openComplianceTracking(null);
            }else {
                IPOSApplication.showToast("Error Occurred!");
            }

        }
    }
}
