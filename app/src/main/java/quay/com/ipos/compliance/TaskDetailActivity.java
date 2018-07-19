package quay.com.ipos.compliance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quayintech.tasklib.RepeatTaskActivity;
import com.quayintech.tasklib.fragment.AlertPickerDialogFragment;
import com.quayintech.tasklib.interfaces.AlertSelectionListener;
import com.quayintech.tasklib.model.Alert;
import com.quayintech.tasklib.model.Recurrence;

import java.util.Calendar;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.adapter.SubTaskAdapter;
import quay.com.ipos.compliance.constants.AnnotationTaskState;
import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.compliance.data.remote.model.ComplianceDetailsResponse;
import quay.com.ipos.compliance.data.remote.model.TaskProgressReq;
import quay.com.ipos.compliance.fragment.AssignTaskDialogFragment;
import quay.com.ipos.compliance.fragment.CommentFragment;
import quay.com.ipos.compliance.fragment.TaskStateSelectorDialogFragment;
import quay.com.ipos.compliance.interfaces.ComplianceDetailHandler;
import quay.com.ipos.compliance.interfaces.UserSelectionListener;
import quay.com.ipos.compliance.viewModel.ComplianceViewModel;
import quay.com.ipos.data.local.AppDatabase;
import quay.com.ipos.data.remote.APIService;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.data.remote.model.PartnerConnectUpdateResponse;
import quay.com.ipos.databinding.ActivityComplianceDetailBinding;
import quay.com.ipos.notifications.model.NotificationBody;
import quay.com.ipos.notifications.model.TaskData;
import quay.com.ipos.utility.DateAndTimeUtil;
import quay.com.ipos.utility.UiUtils;
import quay.com.ipos.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskDetailActivity extends AppCompatActivity implements
        ComplianceDetailHandler {
    private static final String TAG = TaskDetailActivity.class.getSimpleName();
    private static final int REQ_REPEAT_ACTIVITY = 121;
    private int task_id = 0;
    private ComplianceViewModel viewModel;
    private SubTaskAdapter adapter;
    private RecyclerView recyclerView;
    private Activity activity;

    private TextView txtSubCategory, txtTaskName, txtTaskDescription, txtTaskAssignTo;

    private String mNotificationMessage;
    private AppDatabase appDatabase;
    private List<SubTask> mSubTasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            appDatabase = AppDatabase.getAppDatabase(activity);

            this.activity = TaskDetailActivity.this;
            ActivityComplianceDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_compliance_detail);
            viewModel = new ComplianceViewModel(TaskDetailActivity.this);
            binding.setHandler(this);
            binding.setViewModel(viewModel);


            recyclerView = findViewById(R.id.recyclerView);
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
            recyclerView.addItemDecoration(new UiUtils.SpacesItemDecoration(spacingInPixels));
            adapter = new SubTaskAdapter(activity);
            recyclerView.setAdapter(adapter);


            mNotificationMessage = getIntent().getStringExtra("message");
            if (mNotificationMessage != null) {
                NotificationBody notificationBody = new Gson().fromJson(mNotificationMessage, NotificationBody.class);
                TaskData taskData = notificationBody.taskData;
                String messageContextText = taskData.taskName;
                task_id = taskData.taskSchedulerId;

                loadCompliances();
            } else {
                task_id = getIntent().getIntExtra("task_id", 0);


                if (task_id == 0) {
                    Log.e(TAG, "task_id" + task_id);
                    Toast.makeText(activity, "Task Id is " + task_id, Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("task_id", task_id + "");


                IPOSApplication.getDatabase().taskDao().getTaskById(task_id).observe(this, new Observer<Task>() {
                    @Override
                    public void onChanged(@Nullable Task task) {
                        Gson gson = new Gson();
                        Log.v(TAG, "taskData" + gson.toJson(task));
                        viewModel.setTask(task);

                    }
                });


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        Toast.makeText(activity, "On New Intent Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        loadSubTaskList();

    }

    private void loadSubTaskList() {
        try {
            if (task_id != 0) {
                IPOSApplication.getDatabase().subtaskDao().getAllSubTaskofTask(task_id).observe(TaskDetailActivity.this, new Observer<List<SubTask>>() {
                    @Override
                    public void onChanged(@Nullable List<SubTask> subTasks) {
                        if (subTasks != null) {
                            mSubTasks = subTasks;
                            adapter.setData(subTasks);
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onClickUpdate(View view) {
        viewModel.updateTask(task_id);
    }

    @Override
    public void onClickAddSubTask(View view) {
        Intent intent = new Intent(this, SubTaskActivity.class);
        intent.putExtra("task_id", task_id);
        //task_id
        startActivity(intent);

    }

    @Override
    public void onClickClose(View view) {
        finish();
    }

    @Override
    public void onClickAddStartDate(View view) {

    }

    @Override
    public void onClickAddStartTime(View view) {

    }

    @Override
    public void onClickAddDate(View view) {
        final Calendar calendar = viewModel.getCalendar();
        DatePickerDialog datePicker = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                viewModel.setCalendar(calendar);
                viewModel.setStrDueDate(DateAndTimeUtil.toStringReadableDate(calendar));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    @Override
    public void onClickAddTime(View view) {
        final Calendar calendar = viewModel.getCalendar();
        TimePickerDialog timePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                viewModel.setCalendar(calendar);
                viewModel.setStrDueTime(DateAndTimeUtil.toStringReadableTime(calendar, getApplicationContext()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), android.text.format.DateFormat.is24HourFormat(this));
        timePicker.show();
    }

    @Override
    public void onClickAlert(View view) {
        AlertPickerDialogFragment dialogFragment = AlertPickerDialogFragment.showDialog(getSupportFragmentManager(), viewModel.strAlert);
        dialogFragment.setListener(new AlertSelectionListener() {
            @Override
            public void onAlertSelected(Alert alert) {
                alert.setSelected(true);
                viewModel.setAlert(alert);

            }
        });
    }

    @Override
    public void onClickRepeat(View view) {
        Intent intent = new Intent(activity, RepeatTaskActivity.class);
        intent.putExtra("recurrence", viewModel.getRecurrence());
        startActivityForResult(intent, REQ_REPEAT_ACTIVITY);
    }

    @Override
    public void onClickDescription(View view) {

    }

    @Override
    public void onClickProgressState(View view) {
        TaskStateSelectorDialogFragment dialogFragment = TaskStateSelectorDialogFragment.showDialog(getSupportFragmentManager());
        dialogFragment.setListener(new TaskStateSelectorDialogFragment.ProgressStateListener() {
            @Override
            public void onStateSlected(int mTaskType) {
                if (mTaskType == AnnotationTaskState.DONE) {
                    if (validate()) {
                        openRemarkDialog();
                    } else {
                        Util.showToast("Some Sub Task is Pending!");
                    }
                } else {
                    viewModel.setStrProgressState(mTaskType);
                }

            }
        });
    }

    private boolean validate() {

        try {
            if (mSubTasks != null && mSubTasks.size() > 0) {

                for (SubTask subTask : mSubTasks) {
                    if (subTask.getProgress_state() != AnnotationTaskState.DONE) {
                        return false;
                    }
                }
                return true;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public void onClickAssignTo(final View view) {
        AssignTaskDialogFragment dialogFragment = AssignTaskDialogFragment.showDialog(getSupportFragmentManager());
        dialogFragment.setListener(new UserSelectionListener() {
            @Override
            public void onUserSlected(Employee userProfileModel) {
                viewModel.setAssignUser(userProfileModel);
            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_REPEAT_ACTIVITY && resultCode == RESULT_OK) {
            Recurrence recurrence = (Recurrence) data.getSerializableExtra("recurrence");
            Gson gson = new Gson();
            Log.i(TAG, "recurrence:" + gson.toJson(recurrence));
            viewModel.setRecurrence(recurrence);
        }
    }

    private void openRemarkDialog() {
        CommentFragment commentFragment = CommentFragment.newInstance("", "");
        commentFragment.setListener(new CommentFragment.OnClickCommentListener() {
            @Override
            public void onOk(String reason) {
                submitRemarkDialog(reason);
            }

            @Override
            public void onCancel(String reason) {
                submitRemarkDialog(reason);
            }
        });
        commentFragment.show(getSupportFragmentManager(), "CommentFragment");
    }

    private void submitRemarkDialog(String remark) {
        APIService apiService = RestService.getApiServiceSimple();
        TaskProgressReq taskProgressReq = new TaskProgressReq();
        taskProgressReq.taskTrID = task_id + "";
        taskProgressReq.remark = "" + remark;
        taskProgressReq.tasK_TYPE = "task";
        Log.i("data", new Gson().toJson(taskProgressReq));
        Call<PartnerConnectUpdateResponse> call = apiService.COMPLIANCE_TASK_APPROVE(taskProgressReq);
        call.enqueue(new Callback<PartnerConnectUpdateResponse>() {
            @Override
            public void onResponse(Call<PartnerConnectUpdateResponse> call, Response<PartnerConnectUpdateResponse> response) {

                Log.d(TAG, "response.raw().request().url();" + response.raw().request().url());
                try {


                    if (response.body().response) {


                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                Task task = viewModel.getTask();
                                task.progress_state = AnnotationTaskState.DONE;
                                IPOSApplication.getDatabase().taskDao().saveTask(task);
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Void aVoid) {
                                super.onPostExecute(aVoid);
                                finish();
                            }
                        }.execute();

                    } else {
                        Util.showToast("Not Update");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PartnerConnectUpdateResponse> call, Throwable t) {
                Log.i("Rest onFailure", t.toString());
            }
        });


    }


    private void loadCompliances() {

        APIService apiService = RestService.getApiServiceSimple();
        Call<ComplianceDetailsResponse> call = apiService.loadComplianceDetail();
        call.enqueue(new Callback<ComplianceDetailsResponse>() {
            @Override
            public void onResponse(Call<ComplianceDetailsResponse> call, Response<ComplianceDetailsResponse> response) {
                if (response.errorBody() != null) {

                    Log.i("Rest errorBody", "code:" + response.code() + " " + "message:" + response.message());
                }
                if (response.body() != null) {
                    ComplianceDetailsResponse compResp = response.body();
                    Log.i("Rest onResponse", response.toString() + new Gson().toJson(compResp));
                    if (response.code() == 200 && compResp.statusCode != 0) {
                        loadToDatabase(compResp);

                    } else {
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

                Log.i(TAG, "compResp.response.businessPlaceList:" + new Gson().toJson(compResp.response.businessPlaceList));
                if (compResp.response.businessPlaceList == null) {

                    return false;
                }
                List<BusinessPlaceEntity> placeEntityList = compResp.response.businessPlaceList;
                appDatabase.placeDao().savePlace(placeEntityList);


                Log.i(TAG, "compResp.response.taskList:" + new Gson().toJson(compResp.response.taskList));
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
            Log.i("data", aVoid + "");
            if (aVoid) {
                IPOSApplication.getDatabase().taskDao().getTaskById(task_id).observe(TaskDetailActivity.this, new Observer<Task>() {
                    @Override
                    public void onChanged(@Nullable Task task) {
                        Gson gson = new Gson();
                        Log.v(TAG, "taskData" + gson.toJson(task));
                        viewModel.setTask(task);

                    }
                });


                loadSubTaskList();
            } else {
                IPOSApplication.showToast("Error Occurred!");
            }

        }
    }
}

