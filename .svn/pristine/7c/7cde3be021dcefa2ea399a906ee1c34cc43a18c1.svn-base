package quay.com.ipos.compliance.data.local.entity;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import quay.com.ipos.compliance.constants.KeyConstant;

/**
 * Created by deepak.kumar1 on 28-03-2018.
 */
@Entity(tableName = "task", primaryKeys = "task_id")
public class Task {
    @Expose
    @SerializedName("ID")
    public int task_id; //public int notificationId;//used as notification_id.

    public boolean isSync;

    @Expose
    @SerializedName("ControlMasterID")
    public long control_master_id;

    @Expose
    @SerializedName("TASK_TYPE")
    public String task_type;

    @Expose
    @SerializedName("LocationCode")
    public long store_id;


    @Expose
    @SerializedName("ComplianceTypeName")//Category
    public String task_category;//:"statutory",

    @Expose
    @SerializedName("TaskSubCategory")
    public String task_sub_category;//":"Yearly Recurring Activity",

    @Expose
    @SerializedName("TaskName")
    public String task_name;//":"Principle Certificate Expiry",

    @Expose
    @SerializedName("TaskDescription")
    public String task_description;//":"Monthly activity of bill book closing for the respective month",


    //Not in use only showing
    @Expose
    @SerializedName("TaskStartDateTime")
    public String task_start_date;

    //Not in use only showing
    @Expose
    @SerializedName("TaskEndDateTime")
    public String task_end_date;

    //Not in use only showing
    @Expose
    @SerializedName("CreateDate")
    public String task_created_date;

    //When user complete the task
    @SerializedName("task_complete_date")
    public String task_complete_date;

    //*** Use to show notification
    @Expose
    @SerializedName("NextScheduleDateTime")
    public String next_schedule_date;

    @Expose
    @SerializedName("LastScheduleRunDateTime")
    public String last_run_date;


    @Expose
    @SerializedName("Status")
    public int progress_state;


    @Expose
    @SerializedName("TaskAssignTo")
    public String task_assign_to;//here employee Id is inserted.

    //Reminder Start Here
    //public int notificationId;//used as notification_id.

    //@SerializedName("task_due_date")
    public String task_due_date;

    //alert Data start

    public int alertBeforeDueDateAndTime;

    public String label;

    public boolean isSelected;
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

    public Task() {
    }

    // sample "NextScheduleDateTime": "2018-05-21T13:07:46.203",
    public String getDateAndTime() {
        return this.next_schedule_date;
    }

    public void setDateAndTime(String dateAndTime) {
        this.task_due_date = dateAndTime;
    }

    public long getNotificationId() {
        //return notificationId;
        return task_id;
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


    public String getIcon() {
        return icon;
    }

    public Task setIcon(String icon) {
        this.icon = icon;
        return this;
    }


    public String getContent() {
        return task_description;
    }


    public String getTitle() {
        return task_name;
    }

    public String getLabel() {
        return label;
    }


    public void setAlertBeforeDueDateAndTime(int alertBeforeDueDateAndTime) {
        this.alertBeforeDueDateAndTime = alertBeforeDueDateAndTime;
    }

    public void setLabel(String label) {
        this.label = label;
    }


    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public void setmRepeatFrequency(String mRepeatFrequency) {
        this.mRepeatFrequency = mRepeatFrequency;
    }

    public void setmRepeatInterval(String mRepeatInterval) {
        this.mRepeatInterval = mRepeatInterval;
    }

    public void setmRepeatOnDays(String mRepeatOnDays) {
        this.mRepeatOnDays = mRepeatOnDays;
    }

    public void setmRepeatUntil(String mRepeatUntil) {
        this.mRepeatUntil = mRepeatUntil;
    }

    public void setmRepeatSummary(String mRepeatSummary) {
        this.mRepeatSummary = mRepeatSummary;
    }

    public String getmRepeatFrequency() {
        return mRepeatFrequency;
    }

    public String getmRepeatInterval() {
        return mRepeatInterval;
    }

    public String getmRepeatOnDays() {
        return mRepeatOnDays;
    }

    public String getmRepeatSummary() {
        return mRepeatSummary;
    }

    public String getmRepeatUntil() {
        return mRepeatUntil;
    }


    public String getTask_assign_to() {
        return task_assign_to;
    }
}
