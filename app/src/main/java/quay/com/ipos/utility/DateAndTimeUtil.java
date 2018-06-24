package quay.com.ipos.utility;

import android.content.Context;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import quay.com.ipos.R;

//Date Time Util Class
public class DateAndTimeUtil {

    //Previously used "yyyyMMddHHmm" now using  "yyyy-MM-dd'T'HH:mm:ss.SSS" // sample "NextScheduleDateTime": "2018-05-21T13:07:46.203",
    private static final SimpleDateFormat DATE_AND_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());

    private static final SimpleDateFormat DATE_AND_TIME_FORMAT_JSON_STANDARD = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault());
    private static final SimpleDateFormat DATE_AND_TIME_WITH_SECONDS_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
    private static final SimpleDateFormat READABLE_DAY_MONTH_FORMAT = new SimpleDateFormat("d MMMM", Locale.getDefault());
    private static final SimpleDateFormat READABLE_DAY_MONTH_YEAR_FORMAT = new SimpleDateFormat("d MMMM yyyy", Locale.getDefault());
    private static final SimpleDateFormat READABLE_TIME_24_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());
    private static final SimpleDateFormat READABLE_TIME_FORMAT = new SimpleDateFormat("h:mm a", Locale.getDefault());
    private static final SimpleDateFormat WEEK_DAYS_FORMAT = new SimpleDateFormat("EEEE", Locale.getDefault());
    private static final SimpleDateFormat SHORT_WEEK_DAYS_FORMAT = new SimpleDateFormat("E", Locale.getDefault());

    public static String toStringReadableDate(Calendar calendar) {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }

    public static String toStringReadableTime(Calendar calendar, Context context) {
        if (android.text.format.DateFormat.is24HourFormat(context)) {
            return READABLE_TIME_24_FORMAT.format(calendar.getTime());
        } else {
            return READABLE_TIME_FORMAT.format(calendar.getTime());
        }
    }

    public static Long toLongDateAndTime(Calendar calendar) {
        return Long.parseLong(DATE_AND_TIME_FORMAT.format(calendar.getTime()));
    }

    public static String toStringDateAndTime(Calendar calendar) {
        return DATE_AND_TIME_FORMAT.format(calendar.getTime());
    }

    public static String toStringDateTimeWithSeconds(Calendar calendar) {
        return DATE_AND_TIME_WITH_SECONDS_FORMAT.format(calendar.getTime());
    }

    public static String getAppropriateDateFormat(Context context, Calendar calendar) {
        if (isThisYear(calendar)) {
            if (isThisMonth(calendar) && isThisDayOfMonth(calendar)) {
                return "Today";
            } else {
                return READABLE_DAY_MONTH_FORMAT.format(calendar.getTime());
            }
        } else {
            return READABLE_DAY_MONTH_YEAR_FORMAT.format(calendar.getTime());
        }
    }

    public static Calendar parseDateAndTime(String dateAndTime) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(DATE_AND_TIME_FORMAT.parse(dateAndTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static Calendar parseDateAndTimeTest(String dateAndTime) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(DATE_AND_TIME_FORMAT_JSON_STANDARD.parse(dateAndTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static String[] getWeekDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String[] weekDays = new String[7];
        for (int i = 0; i < 7; i++) {
            weekDays[i] = WEEK_DAYS_FORMAT.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        return weekDays;
    }

    public static String[] getShortWeekDays() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String[] weekDays = new String[7];
        for (int i = 0; i < 7; i++) {
            weekDays[i] = SHORT_WEEK_DAYS_FORMAT.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        return weekDays;
    }

    private static Boolean isThisYear(Calendar calendar) {
        Calendar nowCalendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR) == nowCalendar.get(Calendar.YEAR);
    }

    private static Boolean isThisMonth(Calendar calendar) {
        Calendar nowCalendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) == nowCalendar.get(Calendar.MONTH);
    }

    private static Boolean isThisDayOfMonth(Calendar calendar) {
        Calendar nowCalendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH) == nowCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public static String getCustomDateAndTime(String dateAndTime) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(DATE_AND_TIME_FORMAT.parse(dateAndTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Calendar calendarToday = Calendar.getInstance();
        calendarToday.set(Calendar.HOUR_OF_DAY, 0);
        calendarToday.set(Calendar.MINUTE, 0);
        calendarToday.set(Calendar.SECOND, 0);
        calendarToday.set(Calendar.MILLISECOND, 0);

        Date today = calendarToday.getTime();

        calendarToday.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendarToday.getTime();


        Date dueDate = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd MMM, yyyy");
        String strDate = "Due on " + format.format(dueDate);
        if (dueDate.compareTo(today) == 0) {
            strDate = "Due Today | 0 day left";
        }
        if (dueDate.compareTo(tomorrow) == 0) {
            strDate = "Due Tomorrow | 1 day left";
        }
        return strDate;
    }





    public static String getMyDateAndTime(String suffix,String dateAndTime) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(DATE_AND_TIME_FORMAT.parse(dateAndTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Date dueDate = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
        String strDate = suffix + format.format(dueDate);
        return strDate;
    }

}
