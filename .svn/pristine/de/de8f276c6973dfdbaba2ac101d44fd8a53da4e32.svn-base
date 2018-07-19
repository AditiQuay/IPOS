package com.quayintech.tasklib.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;

import com.quayintech.tasklib.constants.KeyConstant;
import com.quayintech.tasklib.interfaces.RepeatUntilPageHandler;

/**
 * Created by deepak.kumar1 on 03-04-2018.
 */

public class RepeatUntilPageViewModel extends BaseObservable {
    private String mRepeatFrequency;
    public RepeatUntilPageHandler repeatPageHandler;

    public String strUntil="Forever";
    public String strTaskAssignTo;
    public Context context;
    public boolean viewCalender = false;
    public String strDefaultTime = "Forever";

    public RepeatUntilPageViewModel(String mRepeatFrequency, RepeatUntilPageHandler repeatPageHandler, Context context) {
        this.mRepeatFrequency = mRepeatFrequency;
        this.repeatPageHandler = repeatPageHandler;
        this.context = context;
        this.strDefaultTime = getstrDefaultTime();
        this.strUntil = this.strDefaultTime;
        notifyChange();
    }

    private String getstrDefaultTime() {
        String string ="Forever" ;
        if (mRepeatFrequency != null) {
            if (mRepeatFrequency.contentEquals(KeyConstant.FREQUENCY_DAILY)) {
                string = KeyConstant.REPEAT_UNTIL_DEFAULT_DAILY;
            }
           if (mRepeatFrequency.contentEquals(KeyConstant.FREQUENCY_WEEKLY)) {
                string = KeyConstant.REPEAT_UNTIL_DEFAULT_WEEKLY;
            }
           if (mRepeatFrequency.contentEquals(KeyConstant.FREQUENCY_MONTHLY)) {
                string = KeyConstant.REPEAT_UNTIL_DEFAULT_MONTHLY;
            }
          if (mRepeatFrequency.contentEquals(KeyConstant.FREQUENCY_YEARLY)) {
                string = KeyConstant.REPEAT_UNTIL_DEFAULT;
            }
        }

        return string;
    }

    public void onDestroy() {

    }

    public void setViewCalender(boolean viewCalender) {
        this.viewCalender = viewCalender;
        notifyChange();

    }

    public void setStrUntil(String strUntil) {
        this.strUntil = strUntil;
        notifyChange();
    }

    public void setStrDefaultTime() {
        strUntil = this.strDefaultTime;
        notifyChange();
    }

    public String getRepeatUntil() {
        String s = ""+strUntil;
        return s;
    }
}
