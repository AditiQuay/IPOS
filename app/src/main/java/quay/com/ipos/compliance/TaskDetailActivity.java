package quay.com.ipos.compliance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.quayintech.tasklib.RepeatTaskActivity;
import com.quayintech.tasklib.model.Recurrence;

import java.util.Calendar;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.adapter.SubTaskAdapter;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.compliance.interfaces.ComplianceDetailHandler;
import quay.com.ipos.compliance.viewModel.ComplianceViewModel;
import quay.com.ipos.utility.DateAndTimeUtil;
import quay.com.ipos.utility.UiUtils;

public class TaskDetailActivity extends AppCompatActivity implements
        ComplianceDetailHandler {
    private static final String TAG = TaskDetailActivity.class.getSimpleName();
    private static final int REQ_REPEAT_ACTIVITY = 121;
    private int task_id;
    private ComplianceViewModel viewModel;
    private SubTaskAdapter adapter;
    private RecyclerView recyclerView;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity = TaskDetailActivity.this;


        setContentView( R.layout.activity_compliance_detail);
        task_id = getIntent().getIntExtra("task_id", -1);
        if (task_id == -1) {
            Log.e(TAG, "task_id" + task_id);
            Toast.makeText(activity, "Task Id is "+task_id, Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("task_id", task_id + "");
        try {

            viewModel = new ComplianceViewModel(TaskDetailActivity.this);
          //  binding.setViewModel(viewModel);


            IPOSApplication.getDatabase().taskDao().getTaskById(task_id).observe(this, new Observer<Task>() {
                @Override
                public void onChanged(@Nullable Task task) {
                    Gson gson = new Gson();
                    Log.v(TAG, "taskData" + gson.toJson(task));
                    viewModel.setTask(task);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        recyclerView = findViewById(R.id.recyclerView);
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);

        recyclerView.addItemDecoration(new UiUtils.SpacesItemDecoration(spacingInPixels));

        adapter = new SubTaskAdapter(activity);
        recyclerView.setAdapter(adapter);


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

        IPOSApplication.getDatabase().subtaskDao().getAllSubTaskofTask(task_id).observe(TaskDetailActivity.this, new Observer<List<SubTask>>() {
            @Override
            public void onChanged(@Nullable List<SubTask> subTasks) {
                adapter.setData(subTasks);
            }
        });
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
      /*  AlertPickerDialogFragment dialogFragment = AlertPickerDialogFragment.showDialog(getSupportFragmentManager(), viewModel.strAlert);
        dialogFragment.setListener(new AlertSelectionListener() {
            @Override
            public void onAlertSelected(Alert alert) {
                alert.setSelected(true);
                viewModel.setAlert(alert);

            }
        });*/
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
//        TaskStateSelectorDialogFragment dialogFragment = TaskStateSelectorDialogFragment.showDialog(getSupportFragmentManager());
//        dialogFragment.setListener(new TaskStateSelectorDialogFragment.ProgressStateListener() {
//            @Override
//            public void onStateSlected(int mTaskType) {
//                viewModel.setStrProgressState(mTaskType);
//
//            }
//        });
    }

    @Override
    public void onClickAssignTo(final View view) {
       /* AssignTaskDialogFragment dialogFragment = AssignTaskDialogFragment.showDialog(getSupportFragmentManager());
        dialogFragment.setListener(new UserSelectionListener() {
            @Override
            public void onUserSlected(Employee userProfileModel) {
                viewModel.setAssignUser(userProfileModel);
            }


        });
*/
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

}

