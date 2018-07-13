package com.quayintech.tasklib.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.quayintech.tasklib.interfaces.RepeatUntilPageHandler;
import com.quayintech.tasklib.model.Alert;
import com.quayintech.tasklib.model.AlertUtils;
import com.quayintech.tasklib.utils.DateTimeUtils;

import java.util.Calendar;


/**
 * Created by deepak.kumar1 on 03-04-2018.
 */

public class DayofMonthViewModel extends BaseObservable {
    public RepeatUntilPageHandler repeatPageHandler;
    public String strFrequency = "Never";
    public String strUntil;
    public String strTaskAssignTo;
    public Context context;
    public long selectedDueDate = System.currentTimeMillis();

    public String strOnDays;
    private Alert alert = AlertUtils.getDefaultAlert();


    public boolean viewCalender = true;
    public boolean viewWeek = false;


    public Alert getAlert() {
        return alert;
    }

    /**
     *
     */


    //  Realm realm;
    public DayofMonthViewModel(RepeatUntilPageHandler repeatPageHandler, Context context) {
        this.repeatPageHandler = repeatPageHandler;
        this.context = context;
        strUntil = "Forever";
        strFrequency = "Never";
        strTaskAssignTo = "Self";
        this.strOnDays = alert.getLabel();
        notifyChange();
    }


    public void setStrDueDate(int year, int month, int dayOfMonth, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth,
                hour, minute, 0);
        selectedDueDate = calendar.getTimeInMillis();
        this.strFrequency = DateTimeUtils.getDateStringFromLong(selectedDueDate);
        notifyChange();
    }


    public void onDestroy() {

    }


    public void setViewCalender(boolean viewCalender) {
        this.viewCalender = viewCalender;
        notifyChange();

    }

    public void setViewWeek(boolean viewWeek) {
        this.viewWeek = viewWeek;
        notifyChange();
    }
}
