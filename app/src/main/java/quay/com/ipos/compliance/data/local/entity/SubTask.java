package quay.com.ipos.compliance.data.local.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import quay.com.ipos.compliance.constants.KeyConstant;

/**
 * Created by deepak.kumar1 on 28-03-2018.
 */
@Entity(tableName = "sub_task")
public class SubTask {

    @Expose
    @SerializedName("LocalID")
    @PrimaryKey(autoGenerate = true)
    public int sub_task_id;//used as notification_id.//Local auto increment primary key


    @Expose
    @SerializedName("ID")
    public int id;//serverKey.//it is zero by default and updated by server key


    //used in Sync
    public boolean isSync;

    @Expose
    @SerializedName("TaskSchedulerID")
    public int task_scheduler_id;//used as Foreign Key.

    @Expose
    @SerializedName("TASK_TYPE")
    public String task_type;

    @Expose
    @NonNull
    @SerializedName("SubTaskName")
    public String task_name;//":"Principle Certificate Expiry",

    @Expose
    @SerializedName("SubTaskDescription")
    public String task_description;//":"Monthly activity of bill book closing for the respective month",

    @Expose
    @NonNull
    //Not in use only showing
    @SerializedName("SubTaskStartDateTime")
    public String task_start_date;

    @Expose
    @NonNull
    //Not in use only showing
    @SerializedName("SubTaskEndDateTime")
    public String task_end_date;

    @Expose
    //Not in use only showing
    @SerializedName("CreateDate")
    public String task_created_date;

    //When user complete the task
    @SerializedName("task_complete_date")
    public String task_complete_date;

    @Expose
    @SerializedName("SubTaskAssignTo")
    public String task_assign_to;//here employee Id is inserted.


    @Expose
    @SerializedName("RecurrenceCount")
    public int recurrence_count;

    //*** Use to show notification
    @Expose
    @NonNull
    @SerializedName("NextScheduleDateTime")
    public String next_schedule_date;

    @Expose
    @SerializedName("LastScheduleRunDateTime")
    public String last_run_date;

    @Expose
    @SerializedName("IntervalTypeID")
    public int interval_type_id;

    @Expose
    @SerializedName("IntervalType")
    public String interval_type;


    @Expose
    @NonNull
    @SerializedName("IntervalValue")
    public int interval_value;


    @Expose
    @SerializedName("Status")
    public int progress_state;

    @Expose
    @SerializedName("Remarks")
    public String remarks;

    @Expose
    @SerializedName("CompletedScheduleDateTime")
    public String completed_datetime;

    @Expose
    @SerializedName("RemNextScheduleDateTime")
    public String rem_next_schedule_datetime;

    @Expose
    @SerializedName("RemLastRunDateTime")
    public String rem_last_run_datetime;

    @Expose
    @SerializedName("RemIntervalTypeID")
    public int rem_interval_type_id;

    @Expose
    @SerializedName("RemIntervalType")
    public String rem_interval_type;

    @Expose
    @SerializedName("RemIntervalValue")
    public int rem_interval_value;


    @Expose
    @Ignore
    @SerializedName("AttachmentList")
    @Relation(parentColumn = "sub_task_id", entityColumn = "txId")
    public List<AttachmentEntity> attachmentList;


    //Reminder Start Here

    @SerializedName("task_due_date")
    public String task_due_date;

    //alert Data start

    public int alertBeforeDueDateAndTime;

    public String label;


    //alert Data end
    //Reminder End Here

    //RecurrenceData Start Here
    public String mRepeatFrequency;

    public String mRepeatInterval;

    public String mRepeatOnDays;

    public String mRepeatUntil;

    public String mRepeatSummary;

    public int numberToShow;

    public int numberShown;

    public String colour;

    public String icon;

    public String content;

    public String title;

    public SubTask() {
    }

    // sample "NextScheduleDateTime": "2018-05-21T13:07:46.203",
    public String getDateAndTime() {
        return this.next_schedule_date;
    }

    public void setDateAndTime(String dateAndTime) {
        this.next_schedule_date = dateAndTime;
    }


    public String getRepeatType() {
        if (mRepeatFrequency != null) {
            return mRepeatFrequency;
        } else
            return KeyConstant.FREQUENCY_NEVER;

    }

    public int getInterval() {
        try {
            String number = mRepeatInterval.replaceAll("[^0-9]", "");
            return Integer.parseInt(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public int getTask_scheduler_id() {
        return task_scheduler_id;
    }

    public void setTask_scheduler_id(int task_scheduler_id) {
        this.task_scheduler_id = task_scheduler_id;
    }

    public int getSub_task_id() {
        return sub_task_id;
    }

   /* public void setSub_task_id(int sub_task_id) {
        this.sub_task_id = sub_task_id;
    }
*/

    @NonNull
    public String getSubTaskName() {
        return task_name;
    }

    public void setSubTaskName(@NonNull String subtaskName) {
        this.task_name = subtaskName;
    }

    public String getSubTaskDescription() {
        return task_description;
    }

    public void setSubTaskDescription(String subtaskDescription) {
        this.task_description = subtaskDescription;
    }

    public void setSubTaskRecurrenceCount(int recurrenceCount) {
        this.recurrence_count = recurrenceCount;
    }

    public int getSubTaskRecurrenceCount() {
        return recurrence_count;
    }


    @NonNull
    public String getSubTaskStartDate() {
        return task_start_date;
    }

    public void setSubTaskStartDate(@NonNull String taskStartDate) {
        this.task_start_date = taskStartDate;
    }

    public void setSubTaskCreatedDate(String tastCreatedDate) {
        this.task_created_date = tastCreatedDate;
    }

    public String getSubTaskCreatedDate() {
        return task_created_date;
    }

    public void setSubTaskCompleteDate(String task_complete_date) {
        this.task_complete_date = task_complete_date;
    }

    public String getSubTaskCompleteDate() {
        return task_complete_date;
    }


    public void setNext_schedule_date(String next_schedule_date) {
        this.next_schedule_date = next_schedule_date;
    }

    public String getNext_schedule_date() {
        return next_schedule_date;
    }

    public void setRem_next_schedule_datetime(String rem_next_schedule_datetime) {
        this.rem_next_schedule_datetime = rem_next_schedule_datetime;
    }

    public String getRem_next_schedule_datetime() {
        return rem_next_schedule_datetime;
    }

    public void setRem_last_run_datetime(String rem_last_run_datetime) {
        this.rem_last_run_datetime = rem_last_run_datetime;
    }

    public String getRem_last_run_datetime() {
        return rem_last_run_datetime;
    }

    public void setLast_run_date(String last_run_date) {
        this.last_run_date = last_run_date;
    }

    public String getLast_run_date() {
        return last_run_date;
    }

    public void setInterval_type_id(int interval_type_id) {
        this.interval_type_id = interval_type_id;
    }

    public int getInterval_type_id() {
        return interval_type_id;
    }

    public void setInterval_type(String interval_type) {
        this.interval_type = interval_type;
    }

    public String getInterval_type() {
        return interval_type;
    }

    public void setInterval_value(int interval_value) {
        this.interval_value = interval_value;
    }

    public void setRem_interval_value(int rem_interval_value) {
        this.rem_interval_value = rem_interval_value;
    }

    public void setProgress_state(int progress_state) {
        this.progress_state = progress_state;
    }

    public int getProgress_state() {
        return progress_state;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setTask_assign_to(String task_assign_to) {
        this.task_assign_to = task_assign_to;
    }

    public String getTask_assign_to() {
        return task_assign_to;
    }

    public void setRem_interval_type_id(int rem_interval_type_id) {
        this.rem_interval_type_id = rem_interval_type_id;
    }

    public int getRem_interval_type_id() {
        return rem_interval_type_id;
    }

    public void setRem_interval_type(@NonNull String rem_interval_type) {
        this.rem_interval_type = rem_interval_type;
    }

    public void setTask_end_date(@NonNull String task_end_date) {
        this.task_end_date = task_end_date;
    }

    public void setServerId(int id) {
        this.id = id;
    }
    public int getServerId(){
        return id;
    }


    //for local use
   /* public Recurrence getRepeat() {
        Recurrence reminder = null;
            reminder = new RecurrenceNever();
            reminder.setRepeatFrequencyId(this.interval_type_id);
            reminder.setRepeatFrequency(this.interval_type);
            reminder.setRepeatInterval(this.interval_value);
           return reminder;
    } public Recurrence getReminder() {
        Recurrence reminder = null;
            reminder = new RecurrenceNever();
            reminder.setRepeatFrequencyId(this.rem_interval_type_id);
            reminder.setRepeatFrequency(this.rem_interval_type);
            reminder.setRepeatInterval(this.rem_interval_value);



        return reminder;
    }*/
}
