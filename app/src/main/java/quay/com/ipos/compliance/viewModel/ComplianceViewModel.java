package quay.com.ipos.compliance.viewModel;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.databinding.BaseObservable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;


import com.quayintech.tasklib.model.Alert;
import com.quayintech.tasklib.model.AlertUtils;
import com.quayintech.tasklib.model.Recurrence;
import com.quayintech.tasklib.model.RecurrenceNever;

import java.util.Calendar;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.TaskDetailActivity;
import quay.com.ipos.compliance.constants.AnnotationTaskState;
import quay.com.ipos.compliance.constants.KeyConstants;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.data.local.AppDatabase;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.DateAndTimeUtil;
import quay.com.ipos.utility.SharedPrefUtil;

/**
 * Created by deepak.kumar1 on 30-03-2018.
 */

public class ComplianceViewModel extends BaseObservable {

    private Context context;
    public String strStartDate;
    public String strStartTime;
    public String strDueDate;
    public String strDueTime;
    private Calendar calendarStart = Calendar.getInstance();

    private Calendar calendar = Calendar.getInstance();

    public String strDesc;

    public String strProgressState = "Pending";
    public String strTaskAssignTo;
    public String strTaskAssignToName = "Self";

    private String empId;
    private Employee userProfileModel;
    public String txtTaskName, txtSubCategory;


    //recurrence and reminder option
    private Recurrence recurrence = new RecurrenceNever();
    public String strRepeat;
    private Alert alert = AlertUtils.getDefaultAlert();
    public String strAlert = alert.getLabel();

    private Task task;

    private boolean isComplete;

    public ComplianceViewModel(Context context) {
        this.context = context;
        this.empId = SharedPrefUtil.getUserId(Constants.employeeCode, "", context);
        strTaskAssignTo = this.empId;//by default
        strTaskAssignToName = "Self";

    }

    public Alert getAlert() {
        return alert;
    }


    /* public ComplianceViewModel(Task task, Context context) {
         this.task = task;
         if (task.getLabel() != null) {
             this.strAlert = task.getLabel();
         } else {
             this.strAlert = alert.getLabel();
         }

         if (task.getmRepeatFrequency() != null) {
             recurrence.setRepeatFrequency(task.getmRepeatFrequency());
         }
         if (task.getmRepeatInterval() != null) {
             recurrence.setRepeatInterval(task.getmRepeatInterval());
         }
         if (task.getmRepeatOnDays() != null) {
             recurrence.setRepeatOnDays(task.getmRepeatOnDays());
         }
         if (task.getmRepeatUntil() != null) {
             recurrence.setRepeatUntil(task.getmRepeatUntil());
         }
         if (task.getmRepeatSummary() != null) {
             recurrence.setRepeatSummary(task.getmRepeatSummary());
         }

         setRecurrenceTask(recurrence);
         this.context = context;
         this.strDesc = task.task_description;
         if (task.task_assign_to != null) {
             this.strTaskAssignTo = task.task_assign_to;
         }
         calendar = DateAndTimeUtil.parseDateAndTime(task.getDateAndTime());

         this.strDueDate = DateAndTimeUtil.toStringReadableDate(calendar);
         this.strEndTime = DateAndTimeUtil.toStringReadableTime(calendar, context);
         if (task.progress_state == 0) {
             this.strProgressState = context.getString(R.string.task_pending);
         } else {
             this.strProgressState = context.getString(R.string.task_done);

         }
         notifyChange();
     } */

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        try {
            AppDatabase.getAppDatabase(IPOSApplication.getContext()).employeeDao().getTaskById(task.getTask_assign_to()).observe((TaskDetailActivity) context, new Observer<Employee>() {
                @Override
                public void onChanged(@Nullable Employee employee) {
                    if (employee != null) {
                        strTaskAssignToName = employee.empName;
                        notifyChange();
                    }
                }
            });
            this.task = task;
            txtSubCategory = task.task_sub_category;
            txtTaskName = task.task_name;
            if (task.getLabel() != null) {
                this.strAlert = task.getLabel();
            } else {
                this.strAlert = alert.getLabel();
            }

            if (task.getmRepeatFrequency() != null) {
                recurrence.setRepeatFrequency(task.getmRepeatFrequency());
            }
            if (task.getmRepeatInterval() != null) {
                // recurrence.setRepeatInterval(task.getmRepeatInterval());
            }
            if (task.getmRepeatOnDays() != null) {
                recurrence.setRepeatOnDays(task.getmRepeatOnDays());
            }
            if (task.getmRepeatUntil() != null) {
                recurrence.setRepeatUntil(task.getmRepeatUntil());
            }
            if (task.getmRepeatSummary() != null) {
                recurrence.setRepeatSummary(task.getmRepeatSummary());
            }

            setRecurrence(recurrence);
            this.context = context;
            this.strDesc = task.task_description;
            if (task.task_assign_to != null) {
                this.strTaskAssignTo = task.task_assign_to;
            }

            calendarStart = DateAndTimeUtil.parseDateAndTime(task.task_start_date);

            calendar = DateAndTimeUtil.parseDateAndTime(task.task_end_date);

            this.strStartDate = DateAndTimeUtil.toStringReadableDate(calendarStart);
            this.strStartTime = DateAndTimeUtil.toStringReadableTime(calendarStart, context);





            this.strDueDate = DateAndTimeUtil.toStringReadableDate(calendar);
            this.strDueTime = DateAndTimeUtil.toStringReadableTime(calendar, context);



            if (task.progress_state == AnnotationTaskState.PENDING) {
                this.strProgressState = context.getString(R.string.task_pending);
                setComplete(false);
            } else if (task.progress_state == AnnotationTaskState.DONE) {
                this.strProgressState = context.getString(R.string.task_done);
                setComplete(true);

            } else if (task.progress_state == AnnotationTaskState.CANCEL) {
                this.strProgressState = context.getString(R.string.task_cancel);
                setComplete(false);

            }
            notifyChange();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void setStrProgressState(@AnnotationTaskState.TaskState int mTaskType) {
        String string = null;
        if (mTaskType == AnnotationTaskState.DONE) {
            string = "Done";
        }
        if (mTaskType == AnnotationTaskState.PENDING) {
            string = "Pending";
        }
        if (mTaskType == AnnotationTaskState.CANCEL) {
            string = "Cancel";
        }
        this.strProgressState = string;
        notifyChange();
    }


    public void setStrDueDate(String date) {
        this.strDueDate = date;
        notifyChange();
    }

    public void setStrDueTime(String strDueTime) {
        this.strDueTime = strDueTime;
        notifyChange();
    }

    private String getStrTaskAssignTo() {
        try {
            if (userProfileModel.empCode.contentEquals(empId))
                return "Self";
            else
                return "" + userProfileModel.empName;
        } catch (Exception e) {

        }
        return null;

    }

    public void setAssignUser(Employee userProfileModel) {
        this.userProfileModel = userProfileModel;
        strTaskAssignTo = getStrTaskAssignTo();
        notifyChange();
    }

    public void updateTask(final long task_id) {

        if (task == null) {
            return;
        }
        if (task != null) {

            task.task_description = strDesc;
            task.task_assign_to = strTaskAssignTo;
            task.setDateAndTime(DateAndTimeUtil.toStringDateAndTime(calendar));

            if (strProgressState.contentEquals(context.getString(R.string.task_pending))) {
                task.progress_state = AnnotationTaskState.PENDING;
                setComplete(false);

            } else {
                task.progress_state = AnnotationTaskState.DONE;
                setComplete(true);
            }


            //for alert
            task.setSelected(true);
            task.setAlertBeforeDueDateAndTime(alert.getDuration());
            task.setLabel(alert.getLabel());

            //for recurrence
            task.setmRepeatFrequency(recurrence.getRepeatFrequency());
            //  task.setmRepeatInterval(recurrence.getRepeatInterval());
            task.setmRepeatOnDays(recurrence.getRepeatOnDays());
            task.setmRepeatUntil(recurrence.getRepeatUntil());
            task.setmRepeatSummary(recurrence.getRepeatSummary());


          
           /* task.save(new RushCallback() {
                @Override
                public void complete() {

                    Intent alarmIntent = new Intent(context, AlarmReceiver.class);
                    calendar.set(Calendar.SECOND, 0);
                    //for alert before
                    calendar.add(Calendar.MINUTE, -task.getAlertBeforeDueDateAndTime());

                    AlarmUtil.setAlarm(context, alarmIntent, task.getNotificationId(), calendar);

                    ((Activity) context).finish();
                }
            });*/

            new DatabaseAsync().execute();
        } else {
            Log.i("task is null", "null");

        }

    }

    public void onTextChangedDesc(CharSequence s, int start, int before, int count) {
        Log.w("tag", "onTextChanged " + s);
        strDesc = s.toString();

    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
        this.strAlert = alert.getLabel();
        notifyChange();
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
        setStrRepeat(recurrence.getRepeatFrequency());
    }

    public void setStrRepeat(String strRepeat) {
        this.strRepeat = strRepeat;
        notifyChange();
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
        notifyChange();
    }


    private class DatabaseAsync extends AsyncTask<Void, Void, Long> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //Perform pre-adding operation here.
        }

        @Override
        protected Long doInBackground(Void... voids) {

            long taskSavedId = IPOSApplication.getDatabase().taskDao().saveTask(task);
            Log.i("taskSavedId", taskSavedId + "");

            return taskSavedId;
        }

        @Override
        protected void onPostExecute(Long aVoid) {
            super.onPostExecute(aVoid);
            ((Activity) context).finish();
            //To after addition operation here.
        }
    }
}
