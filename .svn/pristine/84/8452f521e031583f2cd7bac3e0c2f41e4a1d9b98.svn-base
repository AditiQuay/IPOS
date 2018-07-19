package com.quayintech.tasklib.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.quayintech.tasklib.constants.KeyConstant;
import com.quayintech.tasklib.interfaces.RepeatPageHandler;
import com.quayintech.tasklib.model.Alert;
import com.quayintech.tasklib.model.AlertUtils;
import com.quayintech.tasklib.model.Recurrence;
import com.quayintech.tasklib.model.Weeks;
import com.quayintech.tasklib.utils.DateTimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;


/**
 * Created by deepak.kumar1 on 03-04-2018.
 */

public class RepeatPageViewModel extends BaseObservable {
    private static final String MY_TAG =RepeatPageViewModel.class.getSimpleName() ;
    private Recurrence recurrence;
    public RepeatPageHandler repeatPageHandler;
    public boolean mNameError, mDescError;
    public String strId;
    public String strName;
    public String strDesc;
    public String strFrequency = "";
    public String strUntil;
    public String strSummary = "This event will never repeat";
    public String empId = "1";
    public Context context;
    public long selectedDueDate = System.currentTimeMillis();

    public String strInterval = "";
    public String strOnDays;


    public boolean viewInerval = false;
    public boolean viewOnDays = false;
    public boolean viewUntil = false;



    public RepeatPageViewModel(Recurrence recurrence, RepeatPageHandler repeatPageHandler, Context context) {
        this.recurrence = recurrence;
        setRecurrence(this.recurrence);
        this.repeatPageHandler = repeatPageHandler;
        this.context = context;

        notifyChange();
    }





    public void onDestroy() {

    }




    public void setStrFrequency(String strFrequency) {
        this.strFrequency = strFrequency;
        if (KeyConstant.FREQUENCY_DAILY.contentEquals(strFrequency)) {
            viewInerval = true;
            viewUntil = true;
            viewOnDays = false;
        }
        if (KeyConstant.FREQUENCY_MONTHLY.contentEquals(strFrequency)) {
            viewInerval = true;
            viewUntil = true;
            viewOnDays = true;
        }
        if (KeyConstant.FREQUENCY_WEEKLY.contentEquals(strFrequency)) {
            viewInerval = true;
            viewUntil = true;
            viewOnDays = true;
        }
        if (KeyConstant.FREQUENCY_YEARLY.contentEquals(strFrequency)) {
            viewInerval = true;
            viewUntil = true;
            viewOnDays = false;
        }
        if (KeyConstant.FREQUENCY_NEVER.contentEquals(strFrequency)) {
            viewInerval = false;
            viewUntil = false;
            viewOnDays = false;
        }
        notifyChange();
    }

    public void setStrInterval(String strInterval) {
        this.strInterval = strInterval;

        recurrence.setRepeatInterval(recurrence.getIntInterval(strInterval));
        notifyChange();
    }

    public void setStrSummary(String strSummary) {
        this.strSummary = strSummary;
        notifyChange();
    }

    public void setStrOnDays(String strOnDays) {
        this.strOnDays = strOnDays;
        recurrence.setRepeatOnDays(strOnDays);
        notifyChange();
    }

    public void setStrOnDays(List<Weeks> list) {
        boolean isAboveFour = list.size() >= 4;
        ArrayList<String> strings = new ArrayList<>(list.size());
        for (Weeks weeks : list) {
            if (weeks.isSelected()) {
                String label = weeks.getLabel();
                if (isAboveFour) {
                    label = label.substring(0, 3);
                }
                strings.add(label);
            }
        }
        String joined = TextUtils.join(", ", strings);
        this.setStrOnDays(joined);
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
        setStrFrequency(recurrence.getRepeatFrequency());
        setStrInterval(recurrence.getRepeatInterval()+" "+recurrence.intervalSuffix());
        setStrUntil(recurrence.getRepeatUntil());
        setStrOnDays(recurrence.getRepeatOnDays());
        setStrSummary(recurrence.getRepeatSummary());

    }

    public void setStrUntil(String strUntil) {
        this.strUntil = strUntil;
    }
}
