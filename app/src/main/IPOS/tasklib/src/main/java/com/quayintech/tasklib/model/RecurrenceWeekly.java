package com.quayintech.tasklib.model;

import com.quayintech.tasklib.constants.KeyConstant;
import com.quayintech.tasklib.utils.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by deepak.kumar1 on 07-05-2018.
 */

public class RecurrenceWeekly extends Recurrence{

    public RecurrenceWeekly() {
        super();
        setRepeatFrequencyId(Recurrence.WEEKLY);

        setRepeatFrequency(KeyConstant.FREQUENCY_WEEKLY);
        setRepeatInterval(KeyConstant.INTERVAL_WEEKLY_DEFAULT);
        setRepeatUntil(KeyConstant.REPEAT_UNTIL_DEFAULT_WEEKLY);


        setRepeatOnDays(DateTimeUtils.getDayOfTheWeek());

    }

}
