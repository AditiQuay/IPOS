package quay.com.ipos.compliance.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by deepak.kumar1 on 23-03-2018.
 */

public class AnnotationIntervalState {

    public static final int DOES_NOT_REPEAT = 0;
    public static final int MINUTE_WISE = 1;
    public static final int HOURLY = 2;
    public static final int DAILY = 3;
    public static final int WEEKLY = 4;
    public static final int MONTHLY = 5;
    public static final int YEARLY = 6;
    public static final int SPECIFIC_DAYS = 7;
    public static final int ADVANCED = 8;



    @IntDef({DOES_NOT_REPEAT, HOURLY, DAILY,WEEKLY,MONTHLY,YEARLY,SPECIFIC_DAYS,ADVANCED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface IntervalState {
    }


}

