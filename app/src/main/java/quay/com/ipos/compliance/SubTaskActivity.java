package quay.com.ipos.compliance;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.constants.AnnotationTaskState;
import quay.com.ipos.compliance.data.local.entity.AttachmentEntity;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.compliance.data.remote.model.TaskProgressReq;
import quay.com.ipos.compliance.fragment.AssignTaskDialogFragment;
import quay.com.ipos.compliance.fragment.CommentFragment;
import quay.com.ipos.compliance.fragment.TaskStateSelectorDialogFragment;
import quay.com.ipos.compliance.interfaces.SubTaskHandler;
import quay.com.ipos.compliance.interfaces.UserSelectionListener;
import quay.com.ipos.compliance.viewModel.SubTaskViewModel;
import quay.com.ipos.data.remote.APIService;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.data.remote.model.PartnerConnectUpdateResponse;
import quay.com.ipos.databinding.ActivitySubTaskBinding;
import quay.com.ipos.inventory.attachments.AttachFileModel;
import quay.com.ipos.utility.DateAndTimeUtil;
import quay.com.ipos.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubTaskActivity extends BaseTaskActivity implements SubTaskHandler {

    private static final int REQ_REPEAT_ACTIVITY = 121;
    private static final int REQ_REM_REPEAT_ACTIVITY = 122;
    private final String TAG = SubTaskActivity.class.getSimpleName();
    private SubTaskViewModel viewModel;
    private int task_id;
    private int taskTrId;
    private int id;



    private static final int PICKFILE_REQ_CODE = 1;
    private TextView textViewAttachmentSize;
    private View btnAttachFile;
    private View viewProgressState;
    private RecyclerView recyclerViewFiles;


    private Task mTask;
    //mTask.task_end_date 2018-05-21T13:07:46.203
    private Calendar mTaskEndDateCalender;
    private Calendar mTaskStartDateCalender;


    private Activity activity;
    private TextView title;

    private String filename = "SampleFile.txt";
    private String filepath = "IPOSFileStorage";
    File myExternalFile;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = SubTaskActivity.this;
        try {
            ActivitySubTaskBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_sub_task);
            viewProgressState = findViewById(R.id.viewProgressState);
            btnAttachFile = findViewById(R.id.btnAttachFile);
            recyclerViewFiles = findViewById(R.id.recyclerViewFiles);

            textViewAttachmentSize = findViewById(R.id.textViewAttachmentSize);
            btnAttachFile.setOnClickListener(new onAttachFileClicked());


            task_id = getIntent().getIntExtra("task_id", 0);
            taskTrId = getIntent().getIntExtra("taskTrId", 0);
            id = getIntent().getIntExtra("id", 0);
            title = findViewById(R.id.title);

            if (task_id ==  0) {
                Toast.makeText(activity, "No any Task Found!", Toast.LENGTH_SHORT).show();
                return;
            }
            if (taskTrId == 0 && id == 0) {
                title.setText("Create New Sub Task");
                viewProgressState.setVisibility(View.GONE);

            } else {
                title.setText("Update Sub Task");
                viewProgressState.setVisibility(View.VISIBLE);
            }


            viewModel = new SubTaskViewModel(task_id, activity);


            binding.setViewModel(viewModel);
            binding.setHandler(this);
            binding.setViewModel(viewModel);

            loadData();

            //for base class
            findViewById();

        } catch (Exception e) {
            e.printStackTrace();
        }

        initFile();
    }

    void initFile() {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            //saveButton.setEnabled(false);
            Log.i(TAG, "External Storage Not Available");
        } else {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public void writeFile(String data) {
        try {
            FileOutputStream fos = new FileOutputStream(myExternalFile);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error in Write File");
        }
        //  inputText.setText("");
        Log.i(TAG, "SampleFile.txt saved to External Storage...");

    }
    private void loadData() {

        IPOSApplication.getDatabase().taskDao().getTaskById(task_id).observe(this, new Observer<Task>() {
            @Override
            public void onChanged(@Nullable Task task) {
                if (task == null) {
                    Log.e(TAG, "No Task Found from taskTrId" + task_id);
                    Toast.makeText(activity, "No Task Found from taskTrId" + task_id, Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    mTask = task;
                    if (mTask.task_end_date != null) {
                        Log.i(TAG, "mTask.task_end_date " + mTask.task_end_date);
                        //   mTaskEndDateCalender = DateAndTimeUtil.parseDateAndTime("2018-06-21T13:07:46.203");
                        //  mTaskStartDateCalender = DateAndTimeUtil.parseDateAndTime("2018-06-21T13:07:46.203");

                        mTaskStartDateCalender = DateAndTimeUtil.parseDateAndTime(mTask.task_start_date);
                        mTaskEndDateCalender = DateAndTimeUtil.parseDateAndTime(mTask.task_end_date);
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
        IPOSApplication.getDatabase().subtaskDao().getSubTaskByTRID_And_Id(id, taskTrId).observe(this, new Observer<SubTask>() {
            @Override
            public void onChanged(@Nullable SubTask subTask) {
                Gson gson = new Gson();
                Log.v(TAG, "subtaskData" + gson.toJson(subTask));
                viewModel.setData(subTask);


            }
        });

        IPOSApplication.getDatabase().attachmentDao().getAttachments(taskTrId).observe(this, new Observer<List<AttachmentEntity>>() {
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

        if (requestCode == PICKFILE_REQ_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Cursor returnCursor = getContentResolver().query(uri, null, null, null, null);
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();

            String fileName = returnCursor.getString(nameIndex);
            long fileSize = returnCursor.getLong(sizeIndex);
            String mimeType = getContentResolver().getType(uri);
            Log.i("Type", mimeType);
            Log.i("fileSize", fileSize + "");
            long twoMb = 1024 * 1024 * 2;

            if (fileSize <= twoMb) {
                AttachFileModel fileModel = new AttachFileModel();
                fileModel.fileName = fileName;
                fileModel.mimeType = mimeType;
                fileModel.uri = uri;

                viewModel.attachFileModels.add(fileModel);
                updateSize();
                String FilePath = data.getData().getPath();
            } else {
                Toast.makeText(getApplicationContext(), "Oops! File Size must be less than 2 MB", Toast.LENGTH_SHORT).show();
            }

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
    public void onClickAddStartDate(View view) {
        // DatePickerFragment.showDatePickerDialog(getSupportFragmentManager(), System.currentTimeMillis(), taskData.task_due_date);
        final Calendar calendar = viewModel.getCalendarStart();
        DatePickerDialog datePicker = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                viewModel.setStrStartDate(DateAndTimeUtil.toStringReadableDate(calendar));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        //
        if (  mTaskEndDateCalender.getTime().getTime() > System.currentTimeMillis()) {
            datePicker.getDatePicker().setMaxDate(mTaskEndDateCalender.getTime().getTime());
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
            datePicker.show();

        }
    }

    @Override
    public void onClickAddStartTime(View view) {
        final Calendar calendar = viewModel.getCalendarStart();
        TimePickerDialog timePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                viewModel.setStrStartTime(DateAndTimeUtil.toStringReadableTime(calendar, getApplicationContext()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), android.text.format.DateFormat.is24HourFormat(this));
        timePicker.show();
        // TimePickerFragment.showTimePickerDialog(getSupportFragmentManager());

    }

    @Override
    public void onClickAddEndDate(View view) {

        // DatePickerFragment.showDatePickerDialog(getSupportFragmentManager(), System.currentTimeMillis(), taskData.task_due_date);
        final Calendar calendar = viewModel.getCalendar();
        DatePickerDialog datePicker = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                viewModel.setStrEndDate(DateAndTimeUtil.toStringReadableDate(calendar));

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        //
        if (mTaskEndDateCalender.getTime().getTime()>System.currentTimeMillis()) {
            datePicker.getDatePicker().setMaxDate(mTaskEndDateCalender.getTime().getTime());
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
            datePicker.show();

        }
    }


    @Override
    public void onClickAddEndTime(View view) {
        final Calendar calendar = viewModel.getCalendar();
        TimePickerDialog timePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                viewModel.setStrEndTime(DateAndTimeUtil.toStringReadableTime(calendar, getApplicationContext()));
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

                if (mTaskType == AnnotationTaskState.DONE) {

                    if (taskTrId == 0) {
                        Util.showToast("Transaction Id is not generated!");
                        return;

                    }
                    openRemarkDialog();
                } else {
                    viewModel.setStrProgressState(mTaskType);
                }

            }
        });
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
        taskProgressReq.taskTrID = viewModel.getSubTask().taskTrId + "";
        taskProgressReq.remark = "" + remark;
        taskProgressReq.tasK_TYPE = "subtask";
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
                                SubTask subTask = viewModel.getSubTask();
                                subTask.setProgress_state(AnnotationTaskState.DONE);
                                IPOSApplication.getDatabase().subtaskDao().saveSubTask(subTask);
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


    private class onAttachFileClicked implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, PICKFILE_REQ_CODE);
        }
    }


    private void updateSize() {
        int attachFileSize = viewModel.attachFileModels.size();
        int attachVoiceSize = 0;
        int totalSize = attachFileSize;
        textViewAttachmentSize.setText("(" + totalSize + ")");
        for (int i = 0; i < viewModel.attachFileModels.size(); i++) {
            Log.v("attachFileModels", "attachFileModels" + viewModel.attachFileModels.get(i));
        }
        if (attachFileSize > 0) {

            recyclerViewFiles.setAdapter(new AttachFileAdapter(viewModel.attachFileModels));

        }
    }

    private class AttachFileAdapter extends RecyclerView.Adapter<AttachVH> {
        private List<AttachFileModel> spendRequestAttachment;

        public AttachFileAdapter(List<AttachFileModel> spendRequestAttachment) {
            this.spendRequestAttachment = spendRequestAttachment;
        }

        @NonNull
        @Override
        public AttachVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_attachfile_item, parent, false);
            return new AttachVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AttachVH holder, final int position) {
            final AttachFileModel fileModel = spendRequestAttachment.get(position);
            final String fileName = fileModel.fileName;
            // String name = fileName.substring(fileName.lastIndexOf("/"));
            //SpannableString content = new SpannableString("" + name);
            //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            // textView.setText(content);
            //holder.textView.setText(name);
            holder.textView.setText(fileName);
            Log.v("path", "---------------------" + fileName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Uri path = Uri.parse(attachment);
                    String type = attachment;

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(attachment));*/
                    // intent.setDataAndType(spendRequestAttachment.get(position),"*/*");
                    //   intent.setDataAndType(path, type);
                    //intent.addFlag(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    //Toast.makeText(getActivity(), "In Progress!", Toast.LENGTH_SHORT).show();
                    /*   startActivity(intent);*/
                    final Intent shareIntent = new Intent(Intent.ACTION_VIEW);
                    //   shareIntent.setType("*/*");
                    //  shareIntent.setDataAndType(Uri.parse(fileModel.uri.toString()), "image/*");
                    shareIntent.setDataAndType(Uri.parse(fileModel.uri.toString()), fileModel.mimeType);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //final File photoFile = new File(getFilesDir(), "foo.jpg");

                    startActivity(Intent.createChooser(shareIntent, "View file using"));
                }
            });
            holder.btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spendRequestAttachment.remove(fileModel);
                    notifyDataSetChanged();

                    int attachFileSize = viewModel.attachFileModels.size();
                    textViewAttachmentSize.setText("(" + attachFileSize + ")");
                }
            });
        }

        @Override
        public int getItemCount() {
            return spendRequestAttachment.size();
        }
    }

    private class AttachVH extends RecyclerView.ViewHolder {
        public TextView textView;
        public View btnClear;

        public AttachVH(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            btnClear = itemView.findViewById(R.id.btnClear);
            btnClear.setVisibility(View.VISIBLE);
        }
    }


}
