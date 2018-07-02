package com.quayintech.tasklib.utils;

import android.text.TextUtils;

import java.sql.Time;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

//Date Time Util Class
public class DateTimeUtils {

    public static final String DATE_TIME_FORMAT_yyyyMMddTHHmmss = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_TIME_FORMAT_yyyyMMddTHHmm = "yyyy-MM-dd'T'HH:mm";

    public static final String DATE_FORMAT_yyyyMMdd = "yyyy-MM-dd";

    public static final String DATE_FORMAT_ddMMMyyyy = "dd MMM yyyy";

    public static final String DATE_FORMAT_ddMMMyyyyhhmma = "dd MMM yyyy, hh:mm aa";

    public static final String DATE_FORMAT_ddMMyyyy = "dd MM yyyy";
    public static final String DATE_FORMAT_dd_MMM_yyyy = "dd-MMM-yyyy";

    public static final String TIME_FORMAT_hhmm = "hh:mm";

    public static final String TIME_FORMAT_HHmm = "HH:mm";

    public static final String TIME_FORMAT_hhmmss = "HH:mm:ss";

    public static final String TIME_FORMAT_HHmmss = "HH:mm:ss";

    public static final String TIME_FORMAT_hhmma = "hh:mm aa";


    /**
     * get current date in dd MMM yyyy Format
     *
     * @return
     */
    public static String getCurrnetTime() {

        DateFormat df = new SimpleDateFormat(TIME_FORMAT_hhmmss);
        String date = df.format(Calendar.getInstance().getTime());
        return date;
    }

    /**
     * get current date in yyyyMMddTHHmmss Format
     *
     * @return
     */
    public static String getCurrentDate(String sPattern) {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(sPattern);
        String formattedDate = df.format(c.getTime());

        return formattedDate;
    }

    public static String getCurrentDay() {
        String weekDay = "";

        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        if (Calendar.MONDAY == dayOfWeek) {
            weekDay = "Mon";
        } else if (Calendar.TUESDAY == dayOfWeek) {
            weekDay = "Tue";
        } else if (Calendar.WEDNESDAY == dayOfWeek) {
            weekDay = "Wed";
        } else if (Calendar.THURSDAY == dayOfWeek) {
            weekDay = "Thu";
        } else if (Calendar.FRIDAY == dayOfWeek) {
            weekDay = "Fri";
        } else if (Calendar.SATURDAY == dayOfWeek) {
            weekDay = "Sat";
        } else if (Calendar.SUNDAY == dayOfWeek) {
            weekDay = "Sun";
        }
        return weekDay;
    }


    /**
     * @param dDate
     * @param sPattern
     * @return
     */
    public static String dateToString(Date dDate, String sPattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(sPattern);
            return df.format(dDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param sDate
     * @param sPattern
     * @return
     */
    public static Date stringToDate(String sDate, String sPattern) {
        SimpleDateFormat df = new SimpleDateFormat(sPattern);
        Date date = null;
        try {
            date = df.parse(sDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }


    /**
     * @param dDate
     * @param sPattern
     * @return
     */
    public static String dateToStringUTC(Date dDate, String sPattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(sPattern);
            df.setTimeZone(TimeZone.getTimeZone("UTC"));
            return df.format(dDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param sDate
     * @param sDatePattern
     * @param sReturnPattern
     * @return
     */
    public static final String getDateTimeUTC(String sDate, String sDatePattern, String sReturnPattern) {

        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat(sDatePattern);
            DateFormat targetFormat = new SimpleDateFormat(sReturnPattern);
            targetFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date1 = originalFormat.parse(sDate);
            formattedDate = targetFormat.format(date1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return formattedDate;
    }

    /**
     * @param sDate
     * @param sDatePattern
     * @param sReturnPattern
     * @return
     */
    public static final String getDateTimeLocale(String sDate, String sDatePattern, String sReturnPattern) {

        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat(sDatePattern);
            originalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            DateFormat targetFormat = new SimpleDateFormat(sReturnPattern);
            Date date = originalFormat.parse(sDate);
            formattedDate = targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }


    public static final String getDateOrTime(String sDate) {
        String DateOrTime = null;

        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat(DATE_TIME_FORMAT_yyyyMMddTHHmmss);
            originalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = originalFormat.parse(sDate);
            DateFormat targetDateFormate = new SimpleDateFormat(DATE_FORMAT_ddMMMyyyy);
            DateFormat targetTimeFormate = new SimpleDateFormat(TIME_FORMAT_hhmma);
            formattedDate = targetDateFormate.format(date);

            String currentDate = getCurrentDate(DATE_FORMAT_ddMMMyyyy);
            int result = currentDate.compareTo(formattedDate);
            if (result == 0) {
                return targetTimeFormate.format(date);
            } else {
                return formattedDate;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DateOrTime;
    }


    /**
     * @param sDate
     * @return
     */
    public static final String getDate(String sDate, String sDatePattern, String sReturnPattern) {

        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat(sDatePattern);
            DateFormat targetFormat = new SimpleDateFormat(sReturnPattern);
            Date date = originalFormat.parse(sDate);
            formattedDate = targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    /**
     * @param sTime
     * @return
     */
    public static final String getTimeUTC(String sTime, String sTimePattern, String sReturnPattern) {

        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat(sTimePattern);
            DateFormat targetFormat = new SimpleDateFormat(sReturnPattern);
            targetFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = originalFormat.parse(sTime);
            formattedDate = targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    /**
     * @param sTime
     * @return
     */
    public static final String getTimeLocale(String sTime, String sTimePattern, String sReturnPattern) {
        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat(sTimePattern);
            originalFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            DateFormat targetFormat = new SimpleDateFormat(sReturnPattern);
            Date date = originalFormat.parse(sTime);
            formattedDate = targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    /**
     * @param sTime
     * @return
     */
    public static final String getTime(String sTime, String sTimePattern, String sReturnPattern) {
        String formattedDate = null;
        try {
            DateFormat originalFormat = new SimpleDateFormat(sTimePattern);
            DateFormat targetFormat = new SimpleDateFormat(sReturnPattern);
            Date date = originalFormat.parse(sTime);
            formattedDate = targetFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedDate;
    }


    /**
     * get current year
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }


    /**
     * get current month
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;

    }


    /**
     * get currnet date
     *
     * @param date
     * @return
     */
    public static int getDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * @param time
     * @return
     */
    public static String translateDate(Long time) {
        long oneDay = 24 * 60 * 60 * 1000;
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));

        //  Calendar.HOUR——12 Calendar.HOUR_OF_DAY——24
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        long todayStartTime = today.getTimeInMillis();

        if (time >= todayStartTime && time < todayStartTime + oneDay) { // today
            return "Today";
        } else if (time >= todayStartTime - oneDay && time < todayStartTime) { // yesterday
            return "Yesterday";
        } else if (time >= todayStartTime - oneDay * 2 && time < todayStartTime - oneDay) { // the day before yesterday
            return "The day before yesterday";
        } else if (time > todayStartTime + oneDay) { // future
            return "Future";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(time);
            return dateFormat.format(date);
        }
    }


    /**
     * @param time
     * @param curTime
     * @return
     */
    private String translateDate(long time, long curTime) {
        long oneDay = 24 * 60 * 60;
        Calendar today = Calendar.getInstance();    //Today
        today.setTimeInMillis(curTime * 1000);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        long todayStartTime = today.getTimeInMillis() / 1000;
        if (time >= todayStartTime) {
            long d = curTime - time;
            if (d <= 60) {
                return "1 minute ago";
            } else if (d <= 60 * 60) {
                long m = d / 60;
                if (m <= 0) {
                    m = 1;
                }
                return m + "minutes ago";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("Nowadays HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        } else {
            if (time < todayStartTime && time > todayStartTime - oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yesterday HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {

                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else if (time < todayStartTime - oneDay && time > todayStartTime - 2 * oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("The day before yesterday HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        }
    }

    public static String getDateStringFromLong(long time) {
        String getDateTimeString = DateFormat.getDateTimeInstance().format(time);
        return getDateTimeString;
    }

    public static long getLongFromDateTime(String dd_MMM_yyyy) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss");
            Date d1 = format.parse(dd_MMM_yyyy);
            return d1.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    } public static long getLongFromDate(String dd_MMM_yyyy) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-mm-yyyy");
            Date d1 = format.parse(dd_MMM_yyyy);
            return d1.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }public static long getLongFromTime(String HH_mm) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            Date d1 = format.parse(HH_mm);
            return d1.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }



    public static String getCurrentDate() {
        String date = new SimpleDateFormat("EEE dd MMM, yyyy").format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getCurrentTime() {
        String date = new SimpleDateFormat("hh:mm aa").format(Calendar.getInstance().getTime());
        return date;
    }

    public static String getTime(int hr,int min) {
        Time tme = new Time(hr,min,0);//seconds by default set to zero
        Format formatter;
        formatter = new SimpleDateFormat("h:mm a");
        return formatter.format(tme);
    }
    public static String getDate(int year,int month,int day) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat format = new SimpleDateFormat("EEE dd MMM, yyyy");
        String strDate = format.format(calendar.getTime());
        return strDate;
    }

    public static String getDayOfMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        Date d = new Date();
        String dayOfTheMonth = sdf.format(d);
        return dayOfTheMonth;
    }

    public static String getDayOfTheWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        return dayOfTheWeek;
    }
    public static int getWeekNo() {
        Calendar date = Calendar.getInstance();
        int weekNo1 = date.get(Calendar.WEEK_OF_MONTH);
        return weekNo1-1;
    }
}
