package com.quayintech.tasklib.model;

import com.quayintech.tasklib.constants.KeyConstant;
import com.quayintech.tasklib.utils.DateTimeUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by deepak.kumar1 on 07-05-2018.
 */

public class RecurrenceMonthly extends Recurrence{

    public RecurrenceMonthly() {
        super();
        setRepeatFrequencyId(Recurrence.MONTHLY);

        setRepeatFrequency(KeyConstant.FREQUENCY_MONTHLY);
        setRepeatInterval(KeyConstant.INTERVAL_DEFAULT_MONTHLY);
        setRepeatUntil(KeyConstant.REPEAT_UNTIL_DEFAULT_MONTHLY);
        setRepeatOnDays(DateTimeUtils.getDayOfMonth());

    }

}
