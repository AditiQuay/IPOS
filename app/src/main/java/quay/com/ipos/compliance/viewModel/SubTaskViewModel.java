package quay.com.ipos.compliance.viewModel;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.quayintech.tasklib.model.Alert;
import com.quayintech.tasklib.model.AlertUtils;
import com.quayintech.tasklib.model.Recurrence;
import com.quayintech.tasklib.model.RecurrenceNever;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.SubTaskActivity;
import quay.com.ipos.compliance.constants.AnnotationTaskState;
import quay.com.ipos.compliance.data.local.entity.AttachmentEntity;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.remote.SyncData;
import quay.com.ipos.inventory.attachments.AttachFileModel;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.DateAndTimeUtil;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;


/**
 * Created by deepak.kumar1 on 03-04-2018.
 */

public class SubTaskViewModel extends BaseObservable {
    private static final String TAG = SubTaskViewModel.class.getSimpleName();
    private Calendar calendar;
    private Calendar calendarStart;
    private SubTask subTask;
    private boolean mNameError, mDescError;
    public int mServerId;
    public String strName;
    public String strDesc;
    public String strStartDate;
    public String strStartTime;
    public String strEndDate;
    public String strEndTime;
    private String strProgressState;
    public String strTaskAssignTo;
    public String strTaskAssignToName = "Self";
    private String empId;
    private Employee employee;
    private Context context;

    private int taskId;
    private boolean isComplete;
    //recurrenceTask and reminder option
    private Recurrence recurrenceTask = new RecurrenceNever();
    private Recurrence recurrenceReminder = new RecurrenceNever();
    public String strRepeat;
    public String strReminder;
    public String strAlert;
    private Alert alert = AlertUtils.getDefaultAlert();
   public ArrayList<AttachFileModel> attachFileModels = new ArrayList<>();
    public Alert getAlert() {
        return alert;
    }


    public SubTaskViewModel(int taskId, Context context) {
        this.empId = Prefs.getStringPrefs(Constants.employeeCode);
        strTaskAssignTo = this.empId;//by default
        strTaskAssignToName = "Self";
        setStrRepeat(recurrenceTask.getRepeatFrequency());
        setStrReminder(recurrenceReminder.getRepeatFrequency());
        this.taskId = taskId;
        calendarStart = Calendar.getInstance();
        calendar = Calendar.getInstance();
        this.context = context;
        strProgressState = context.getString(R.string.task_pending);

        this.strAlert = alert.getLabel();

        // strEndDate = DateAndTimeUtil.getDateStringFromLong(selectedDueDate);
        strStartDate = "Today";
        strStartTime = "Now";

        strEndDate = "Today";
        strEndTime = "Now";
        notifyChange();
    }
    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
        notifyChange();
    }
    public String getStrName() {
        return strName;
    }

    public SubTask getSubTask() {
        return subTask;
    }

    public void setSubTask(SubTask subTask) {
        this.subTask = subTask;
        notifyChange();
    }


    public String getStrProgressState() {
        return strProgressState;
    }

    public void setStrProgressState(@AnnotationTaskState.TaskState int mTaskType) {
        String string = null;
        if (mTaskType == AnnotationTaskState.DONE) {
            string = "Done";
            setComplete(true);

        }
        if (mTaskType == AnnotationTaskState.PENDING) {
            string = "Pending";
            setComplete(false);

        }
        this.strProgressState = string;
        notifyChange();
    }

    public String getStrEndDate() {
        return strEndDate;
    }

   /* public void setStrEndDate(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day,
                hour, minute, 0);
        selectedDueDate = calendar.getTimeInMillis();
        this.strEndDate = DateAndTimeUtil.getDateStringFromLong(selectedDueDate);
        notifyChange();
    }*/

    public void setStrStartDate(String date) {
        this.strStartDate = date;
        notifyChange();
    }

    public void setStrStartTime(String time) {
        this.strStartTime = time;
        notifyChange();
    }

    public void setStrEndDate(String date) {
        this.strEndDate = date;
        notifyChange();
    }

    public void setStrEndTime(String time) {
        this.strEndTime = time;
        notifyChange();
    }

    public void saveSubTask() {
        validation();
        saveToDB();
    }


    private void validation() {
        if (strName == null || strName.isEmpty()) {
            mNameError = true;
            notifyChange();
            return;
        }
        if (strDesc == null || strDesc.isEmpty()) {
            mDescError = true;
            notifyChange();
            return;
        }
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Log.w("tag", "onTextChanged " + s);
        strName = s.toString();
    }

    public void onTextChangedDesc(CharSequence s, int start, int before, int count) {
        Log.w("tag", "onTextChanged " + s);
        strDesc = s.toString();

    }


    public void setData(SubTask subTask) {
        if (subTask == null)
            return;
        this.subTask = subTask;
        this.strTaskAssignTo = subTask.getTask_assign_to();
        IPOSApplication.getDatabase().employeeDao().getTaskById(subTask.getTask_assign_to()).observe((SubTaskActivity) context, new Observer<Employee>() {
            @Override
            public void onChanged(@Nullable Employee employee) {
                if (employee != null) {
                    strTaskAssignToName = employee.empName;
                    notifyChange();
                }
            }
        });

        if (subTask.progress_state == AnnotationTaskState.DONE) {
            this.strProgressState = context.getString(R.string.task_done);
             setComplete(true);
        }
        if (subTask.progress_state == AnnotationTaskState.PENDING) {
            this.strProgressState = context.getString(R.string.task_pending);
            setComplete(false);

        }
        if (subTask.progress_state == AnnotationTaskState.CANCEL) {
            this.strProgressState = context.getString(R.string.task_cancel);
            setComplete(false);

        }

        this.mServerId = subTask.getServerId();
        this.strName = subTask.getSubTaskName();
        this.strDesc = subTask.getSubTaskDescription();
        calendarStart = DateAndTimeUtil.parseDateAndTime(subTask.task_start_date);
        calendar = DateAndTimeUtil.parseDateAndTime(subTask.task_end_date);

        this.strStartDate = DateAndTimeUtil.toStringReadableDate(calendarStart);
        this.strStartTime = DateAndTimeUtil.toStringReadableTime(calendarStart, context);

        this.strEndDate = DateAndTimeUtil.toStringReadableDate(calendar);
        this.strEndTime = DateAndTimeUtil.toStringReadableTime(calendar, context);



        recurrenceTask = subTask.getRepeat();
        setStrRepeat(recurrenceTask.getRepeatFrequency());

        recurrenceReminder = subTask.getReminder();
        setStrReminder(recurrenceReminder.getRepeatFrequency());


        notifyChange();
    }

    private String getStrTaskAssignTo() {
        try {
            if (employee.empCode.contentEquals(empId))
                return "Self";
            else
                return "" + employee.empName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public void setAssignUser(Employee userProfileModel) {
        this.employee = userProfileModel;
        strTaskAssignToName = getStrTaskAssignTo();
        strTaskAssignTo = userProfileModel.empCode;
        notifyChange();
    }

    public Recurrence getRecurrenceTask() {
        return recurrenceTask;
    }

    public Recurrence getRecurrenceReminder() {
        return recurrenceReminder;
    }

    public void setRecurrenceTask(Recurrence recurrenceTask) {
        this.recurrenceTask = recurrenceTask;
        setStrRepeat(recurrenceTask.getRepeatFrequency());

        Log.e(TAG, "" + new Gson().toJson(recurrenceTask));
    }

    public void setRecurrenceReminder(Recurrence recurrenceReminder) {
        this.recurrenceReminder = recurrenceReminder;
        setStrReminder(recurrenceReminder.getRepeatFrequency());
    }

    public void setStrRepeat(String strRepeat) {
        this.strRepeat = strRepeat;
        notifyChange();
    }

    public void setStrReminder(String strReminder) {
        this.strReminder = strReminder;
        notifyChange();
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
        this.strAlert = alert.getLabel();
        notifyChange();
    }

    private void saveToDB() {

        if (strTaskAssignTo == null || strTaskAssignTo.isEmpty()) {
            Util.showToast("Task Assign To is Required!");
            return;
        }


        if (mServerId == 0) {
            subTask = new SubTask();
            int uniqueID = (int) System.currentTimeMillis();
            mServerId = 0;//uniqueID;

            Log.i("uniqueID", uniqueID + "");
        } else {
            Log.i("not null", "not null");

        }
        subTask.setServerId(mServerId);
        subTask.setTask_scheduler_id(taskId);
        subTask.setSubTaskCreatedDate(DateAndTimeUtil.toStringDateAndTime(Calendar.getInstance()));
        subTask.setSubTaskName(strName);
        subTask.setSubTaskDescription(strDesc);
        subTask.setTask_assign_to(strTaskAssignTo);
        subTask.task_type = "subtask";

        subTask.setNext_schedule_date(DateAndTimeUtil.toStringDateAndTime(calendar));
        subTask.setSubTaskStartDate(DateAndTimeUtil.toStringDateAndTime(calendarStart));
        subTask.setTask_end_date(DateAndTimeUtil.toStringDateAndTime(calendar));

        subTask.isSync = true;

        //load recurrence data
        subTask.setInterval_type_id(recurrenceTask.getRepeatFrequencyId());//hc
        subTask.setInterval_type(recurrenceTask.getRepeatFrequency());
        subTask.setInterval_value(recurrenceTask.getRepeatInterval());

        subTask.setRem_interval_type_id(recurrenceReminder.getRepeatFrequencyId());//hc
        subTask.setRem_interval_type(recurrenceReminder.getRepeatFrequency());
        subTask.setRem_interval_value(recurrenceReminder.getRepeatInterval());

        if (strProgressState.contentEquals(context.getString(R.string.task_pending))) {
            subTask.progress_state = AnnotationTaskState.PENDING;
        } else {
            subTask.progress_state = AnnotationTaskState.DONE;


        }

        //new DatabaseAsync().execute();
        loadToServer(subTask);



      /*  subTask.save(new RushCallback() {
            @Override
            public void complete() {*/
               /* final Reminder reminder = new Reminder();
                reminder.setTagId(subTask.subtask_id);
                reminder.setTagType(ReminderType.KEY_SUBTASK);
                //alert info
                reminder.setNotificationId(alert.getId());
                reminder.setAlertBeforeDueDateAndTime(alert.getDuration());
                reminder.setDateAndTime(DateAndTimeUtil.toStringDateAndTime(calendar));
                reminder.setLabel("" + strName);
                reminder.setSelected(alert.isSelected());
                //recurrenceTask info
                reminder.setmRepeatFrequency(recurrenceTask.getRepeatFrequency());
                reminder.setmRepeatInterval(recurrenceTask.getRepeatInterval());
                reminder.setmRepeatOnDays(recurrenceTask.getRepeatOnDays());
                reminder.setmRepeatUntil(recurrenceTask.getRepeatUntil());
                reminder.setmRepeatSummary(recurrenceTask.getRepeatSummary());
                //save to db
                RushCore.getInstance().save(reminder, new RushCallback() {
                    @Override
                    public void complete() {
                        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                        calendar.set(Calendar.SECOND, 0);
                        AlarmUtil.setAlarm(context, alarmIntent, reminder.getNotificationId(), calendar);
                        ((Activity) context).finish();
                    }
                });*/
/*

            }
        });
*/


       /* subTask.save(new RushCallback() {
            @Override
            public void complete() {
                Log.i("Data", "Saved");
            }
        });*/
    }


    public Calendar getCalendar() {
        return calendar;
    }

    public Calendar getCalendarStart() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }



    private void loadToServer(SubTask subTask) {

        List<AttachmentEntity> attachmentList = new ArrayList<>();

        for (AttachFileModel fileModel : attachFileModels) {

            Uri returnUri = fileModel.uri;
            Cursor returnCursor =
                    context.getContentResolver().query(returnUri, null, null, null, null);
            /*
             * Get the column indexes of the data in the Cursor,
             * move to the first row in the Cursor, get the data,
             * and display it.
             */
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();

            String fileName = returnCursor.getString(nameIndex);
            String fileSize = Long.toString(returnCursor.getLong(sizeIndex));
            String mimeType = context.getContentResolver().getType(returnUri);
            Log.i("Type", mimeType);

            AttachmentEntity spendRequestAttachment = new AttachmentEntity();
            spendRequestAttachment.base = getBase64StringNew(returnUri, Integer.parseInt(fileSize));
            spendRequestAttachment.extension = "No Info";
            spendRequestAttachment.name = fileName;
            spendRequestAttachment.type = mimeType;
            spendRequestAttachment.txId = subTask.taskTrId;

            attachmentList.add(spendRequestAttachment);

        }


        subTask.attachmentList = attachmentList;

        List<SubTask> subTaskList = new ArrayList<>();

        subTaskList.add(subTask);

        ((SubTaskActivity)context).writeFile(new Gson().toJson(subTaskList));


        SyncData syncData = new SyncData(subTaskList);
        syncData.setListener(new SyncData.OnDataSyncListener() {
            @Override
            public void onDataSync(boolean isSync) {
                if (isSync)
                    ((Activity) context).finish();
                else {
                    Util.showToast("Data not update to Server!");
                }
            }
        });
        syncData.execute();
    }


    private String getBase64StringNew(Uri uri, int filelength) {
        String imageStr = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);

           /* InputStream finput = new FileInputStream(file);
            byte[] imageBytes = new byte[(int)file.length()];
            finput.read(imageBytes, 0, imageBytes.length);
            finput.close();
            String imageStr = Base64.encodeBase64String(imageBytes);*/

            //InputStream finput = new FileInputStream(file);
            byte[] byteFileArray = new byte[filelength];
            inputStream.read(byteFileArray, 0, byteFileArray.length);
            inputStream.close();
            imageStr = android.util.Base64.encodeToString(byteFileArray, android.util.Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageStr;
    }


}
