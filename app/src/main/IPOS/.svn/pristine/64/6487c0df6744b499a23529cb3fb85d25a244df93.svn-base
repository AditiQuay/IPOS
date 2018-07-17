package quay.com.ipos.compliance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import quay.com.ipos.compliance.data.local.entity.AttachmentEntity;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.compliance.fragment.AssignTaskDialogFragment;
import quay.com.ipos.compliance.fragment.TaskStateSelectorDialogFragment;
import quay.com.ipos.compliance.interfaces.SubTaskHandler;
import quay.com.ipos.compliance.interfaces.UserSelectionListener;
import quay.com.ipos.compliance.viewModel.SubTaskViewModel;
import quay.com.ipos.utility.DateAndTimeUtil;

public class SubTaskActivity extends BaseTaskActivity implements SubTaskHandler {

    private static final int REQ_REPEAT_ACTIVITY = 121;
    private static final int REQ_REM_REPEAT_ACTIVITY = 122;
    private final String TAG = SubTaskActivity.class.getSimpleName();
    private SubTaskViewModel viewModel;
    private int task_id;
    private int id;//auto generated id
    private boolean isAddTask;


     private Task mTask;
     //mTask.task_end_date 2018-05-21T13:07:46.203
    private Calendar mTaskEndDateCalender;


    private Activity activity;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = SubTaskActivity.this;
        try {
            setContentView( R.layout.activity_sub_task);
            task_id = getIntent().getIntExtra("task_id", -1);
            id = getIntent().getIntExtra("id", -1);
            title = findViewById(R.id.title);

            if (task_id == -1) {
                Toast.makeText(activity, "No any Task Found!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (id == -1) {
                isAddTask = true;
                title.setText("Create New Sub Task");
            } else {
                isAddTask = false;
                title.setText("Update Sub Task");
            }


            viewModel = new SubTaskViewModel(task_id, activity);
            //subTaskBinding.setHandler(this);
            //subTaskBinding.setViewModel(viewModel);

            loadData();

            //for base class
            findViewById();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void loadData() {

            IPOSApplication.getDatabase().taskDao().getTaskById(task_id).observe(this, new Observer<Task>() {
                @Override
                public void onChanged(@Nullable Task task) {
                    if (task == null) {
                        Log.e(TAG, "No Task Found from id" + task_id);
                        Toast.makeText(activity, "No Task Found from id" + task_id, Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        mTask = task;
                        if (mTask.task_end_date != null) {
                            Log.i(TAG, "mTask.task_end_date " + mTask.task_end_date);
                            mTaskEndDateCalender = DateAndTimeUtil.parseDateAndTime("2018-06-21T13:07:46.203");
                            loadSubTaskData();
                        } else {
                            Toast.makeText(activity, "No End Date of Task Mention!", Toast.LENGTH_SHORT).show();
                            return;
                        }



                    }
                }
            });





    }

    private void loadSubTaskData() {
        IPOSApplication.getDatabase().subtaskDao().getSubTaskById(id).observe(this, new Observer<SubTask>() {
            @Override
            public void onChanged(@Nullable SubTask subTask) {
                Gson gson = new Gson();
                Log.v(TAG, "subtaskData" + gson.toJson(subTask));
                viewModel.setData(subTask);


            }
        });

        IPOSApplication.getDatabase().attachmentDao().getAttachments(id).observe(this, new Observer<List<AttachmentEntity>>() {
            @Override
            public void onChanged(@Nullable List<AttachmentEntity> attachmentEntityList) {

                Gson gson = new Gson();
                Log.v(TAG, "attachments" + gson.toJson(attachmentEntityList));
                setAttachFileModels(attachmentEntityList);


            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_REPEAT_ACTIVITY && resultCode == RESULT_OK) {
            Recurrence recurrence = (Recurrence) data.getSerializableExtra("recurrence");
            viewModel.setRecurrenceTask(recurrence);
        }
        if (requestCode == REQ_REM_REPEAT_ACTIVITY && resultCode == RESULT_OK) {
            Recurrence recurrence = (Recurrence) data.getSerializableExtra("recurrence");
            viewModel.setRecurrenceReminder(recurrence);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClickCloseTask(View view) {
        finish();
    }

    @Override
    public void onClickAddSubtask(View view) {
        viewModel.saveSubTask();
    }

    @Override
    public void onClickAddDate(View view) {

            // DatePickerFragment.showDatePickerDialog(getSupportFragmentManager(), System.currentTimeMillis(), taskData.task_due_date);
            final Calendar calendar = viewModel.getCalendar();
            DatePickerDialog datePicker = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    viewModel.setStrDueDate(DateAndTimeUtil.toStringReadableDate(calendar));

                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.getDatePicker().setMaxDate(mTaskEndDateCalender.getTime().getTime());
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
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
                viewModel.setStrDueTime(DateAndTimeUtil.toStringReadableTime(calendar, getApplicationContext()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), android.text.format.DateFormat.is24HourFormat(this));
        timePicker.show();
        // TimePickerFragment.showTimePickerDialog(getSupportFragmentManager());

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
                viewModel.setStrProgressState(mTaskType);

            }
        });
    }

    @Override
    public void onClickAssignTo(View view) {
        AssignTaskDialogFragment dialogFragment = AssignTaskDialogFragment.showDialog(getSupportFragmentManager());
        dialogFragment.setListener(new UserSelectionListener() {
            @Override
            public void onUserSlected(Employee userProfileModel) {
                viewModel.setAssignUser(userProfileModel);
            }


        });

    }


    @Override
    public void onClickRepeat(View view) {
        Intent intent = new Intent(activity, RepeatTaskActivity.class);
        intent.putExtra("recurrence", viewModel.getRecurrenceTask());
        startActivityForResult(intent, REQ_REPEAT_ACTIVITY);

    }

    @Override
    public void onClickRemRepeat(View view) {
        Intent intent = new Intent(activity, RepeatTaskActivity.class);
        intent.putExtra("recurrence", viewModel.getRecurrenceReminder());
        startActivityForResult(intent, REQ_REM_REPEAT_ACTIVITY);

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


}
