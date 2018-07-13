package quay.com.ipos.compliance.viewModel;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.quayintech.tasklib.model.Alert;
import com.quayintech.tasklib.model.AlertUtils;
import com.quayintech.tasklib.model.Recurrence;
import com.quayintech.tasklib.model.RecurrenceNever;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.BaseTaskActivity;
import quay.com.ipos.compliance.SubTaskActivity;
import quay.com.ipos.compliance.constants.AnnotationTaskState;
import quay.com.ipos.compliance.data.local.entity.AttachmentEntity;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.compliance.data.remote.SyncData;
import quay.com.ipos.compliance.receiver.AlarmReceiver;
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
    private SubTask subTask;
    private boolean mNameError, mDescError;
    public int mServerId;
    public String strName;
    public String strDesc;
    private String strDueDate;
    public String strDueTime;
    private String strProgressState;
    public String strTaskAssignTo;
    public String strTaskAssignToName = "Self";
    private String empId;
    private Employee employee;
    private Context context;

    private int taskId;

    //recurrenceTask and reminder option
    private Recurrence recurrenceTask = new RecurrenceNever();
    private Recurrence recurrenceReminder = new RecurrenceNever();
    public String strRepeat;
    public String strReminder;
    public String strAlert;
    private Alert alert = AlertUtils.getDefaultAlert();

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
        calendar = Calendar.getInstance();
        this.context = context;
        strProgressState = context.getString(R.string.task_pending);

        this.strAlert = alert.getLabel();

        // strDueDate = DateAndTimeUtil.getDateStringFromLong(selectedDueDate);
        strDueDate = "Today";
        strDueTime = "Now";
        //realm = Realm.getDefaultInstance();
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
        }
        if (mTaskType == AnnotationTaskState.PENDING) {
            string = "Pending";
        }
        this.strProgressState = string;
        notifyChange();
    }

    public String getStrDueDate() {
        return strDueDate;
    }

   /* public void setStrDueDate(int year, int month, int day, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day,
                hour, minute, 0);
        selectedDueDate = calendar.getTimeInMillis();
        this.strDueDate = DateAndTimeUtil.getDateStringFromLong(selectedDueDate);
        notifyChange();
    }*/

    public void setStrDueDate(String date) {
        this.strDueDate = date;
        notifyChange();
    }

    public void setStrDueTime(String strDueTime) {
        this.strDueTime = strDueTime;
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

        this.mServerId = subTask.getServerId();
        this.strName = subTask.getSubTaskName();
        this.strDesc = subTask.getSubTaskDescription();
        calendar = DateAndTimeUtil.parseDateAndTime(subTask.getDateAndTime());

        this.strDueDate = DateAndTimeUtil.toStringReadableDate(calendar);
        this.strDueTime = DateAndTimeUtil.toStringReadableTime(calendar, context);
        if (subTask.progress_state == 0) {
            this.strProgressState = context.getString(R.string.task_pending);
        } else {
            this.strProgressState = context.getString(R.string.task_done);

        }
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
        subTask.setSubTaskStartDate(DateAndTimeUtil.toStringDateAndTime(calendar));
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

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }


    private class DatabaseAsync extends AsyncTask<Void, Void, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Long doInBackground(Void... voids) {
            subTask.isSync = true;
            try {
                final long subtaskSavedId = IPOSApplication.getDatabase().subtaskDao().saveSubTask(subTask);
                Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                calendar.set(Calendar.SECOND, 0);
            //    AlarmUtil.setAlarm(context, alarmIntent, (int) subtaskSavedId, calendar);

                List<AttachmentEntity> fileModelList = ((BaseTaskActivity) context).getAttachFileModels();
                if (fileModelList != null) {
                    List<AttachmentEntity> attachmentEntityList = new ArrayList<>(fileModelList.size());
                    for (AttachmentEntity attachFileModel : fileModelList) {
                        AttachmentEntity attachmentEntity = new AttachmentEntity();
                        attachmentEntity.name = attachFileModel.name;
                        attachmentEntity.type = attachFileModel.type;
                        attachmentEntity.txId = subtaskSavedId;
                        attachmentEntity.base = attachFileModel.base;
                        attachmentEntity.isSync = true;
                        attachmentEntityList.add(attachmentEntity);

                    }
                    IPOSApplication.getDatabase().attachmentDao().saveAttachment(attachmentEntityList);

                    Log.i("subtaskSavedId", subtaskSavedId + "");
                    return subtaskSavedId;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return -1L;
        }

        @Override
        protected void onPostExecute(Long subtaskSavedId) {
            super.onPostExecute(subtaskSavedId);

            //To after addition operation here.

           // ((Activity) context).finish();

           // loadToServer();
        }
    }


    private void loadToServer(SubTask subTask) {

        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(subTask);
        SyncData syncData =   new SyncData(subTaskList);
        syncData.setListener(new SyncData.OnDataSyncListener() {
            @Override
            public void onDataSync(boolean isSync) {
                if(isSync)
                ((Activity) context).finish();
                else {
                    Util.showToast("Data not update to Server!");
                }
            }
        });
      syncData.execute();
    }


}
