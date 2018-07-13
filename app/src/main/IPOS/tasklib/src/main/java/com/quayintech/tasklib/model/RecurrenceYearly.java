package com.quayintech.tasklib.model;

import com.quayintech.tasklib.constants.KeyConstant;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by deepak.kumar1 on 07-05-2018.
 */

public class RecurrenceYearly extends Recurrence{

    public RecurrenceYearly() {
        super();
        setRepeatFrequencyId(Recurrence.YEARLY);

        setRepeatFrequency(KeyConstant.FREQUENCY_YEARLY);
        setRepeatInterval(KeyConstant.INTERVAL_DEFAULT_YEARLY);
        setRepeatUntil(KeyConstant.REPEAT_UNTIL_DEFAULT);

    }

}
