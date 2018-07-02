package com.quayintech.tasklib.model;

import java.io.Serializable;

/**
 * Created by deepak.kumar1 on 07-05-2018.
 */

public abstract class Recurrence implements Serializable {
    // Reminder types
    public static final int ACTIVE = 1;
    public static final int INACTIVE = 2;

    // Repetition types
    public static final int DOES_NOT_REPEAT = 0;
    public static final int HOURLY = 1;
    public static final int DAILY = 2;
    public static final int WEEKLY = 3;
    public static final int MONTHLY = 4;
    public static final int YEARLY = 5;
    public static final int SPECIFIC_DAYS = 6;
    public static final int ADVANCED = 7;


    private int mRepeatFrequencyId = 0;
    private int mRepeatInterval;
    private String mRepeatFrequency;
    private String mRepeatOnDays;
    private String mRepeatUntil;
    private String mRepeatSummary;

    public int getRepeatInterval() {
        return mRepeatInterval;
    }

    public String intervalSuffix() {
        if (mRepeatFrequencyId == DAILY) {

            return mRepeatInterval > 1 ? "days" : "day";
        }
        if (mRepeatFrequencyId == MONTHLY) {

            return mRepeatInterval > 1 ? "months" : "month";
        }
        if (mRepeatFrequencyId == WEEKLY) {

            return mRepeatInterval > 1 ? "weeks" : "week";
        }
        if (mRepeatFrequencyId == YEARLY) {

            return mRepeatInterval > 1 ? "years" : "year";
        }

        return "";
    }

    public void setRepeatInterval(int mRepeatInterval) {
        this.mRepeatInterval = mRepeatInterval;
    }

    public String getRepeatFrequency() {
        return mRepeatFrequency;
    }

    public void setRepeatFrequency(String mRepeatFrequency) {
        this.mRepeatFrequency = mRepeatFrequency;
    }

    public String getRepeatOnDays() {
        return mRepeatOnDays;
    }

    public void setRepeatOnDays(String mRepeatOnDays) {
        this.mRepeatOnDays = mRepeatOnDays;
    }

    public String getRepeatUntil() {
        return mRepeatUntil;
    }

    public void setRepeatUntil(String mRepeatUntil) {
        this.mRepeatUntil = mRepeatUntil;
    }

    public String getRepeatSummary() {
        return mRepeatSummary;
    }

    public void setRepeatSummary(String mRepeatSummary) {
        this.mRepeatSummary = mRepeatSummary;
    }

    public int getRepeatFrequencyId() {
        return mRepeatFrequencyId;
    }

    public void setRepeatFrequencyId(int mRepeatFrequencyId) {
        this.mRepeatFrequencyId = mRepeatFrequencyId;
    }

    public int getIntInterval(String strInterval) {
          String numberOnly= strInterval.replaceAll("[^0-9]", "");
        return Integer.parseInt(numberOnly);
    }
}
