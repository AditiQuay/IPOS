package quay.com.ipos.utility;

import java.text.SimpleDateFormat;

/**
 * Created by gaurav.pandey on 23-01-2018.
 */

public class Constants {
    /**
     * The Constant MB.
     */
    public static final int MB = 1 * 1024 * 1024;

    /**
     * The Constant MAX_BITMAP_CACHE_SIZE.
     */
    public static final int MAX_MEMORY_CACHE_SIZE = 4 * MB;

    /**
     * The Constant MAX_DISK_CACHE_SIZE.
     */
    public static final int MAX_DISK_CACHE_SIZE = 50 * MB;
    /**
     * The Constant APP_SHARED_PREF_NAME.
     */
    public static final String APP_SHARED_PREF_NAME = "profile";

    public static final int SUCCESS = 200;
    public static final String DATE_FORMAT = "dd MMM yyyy";
    public static final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat format11 = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat format2 = new SimpleDateFormat(DATE_FORMAT);
    public static final String format8 = "MMM dd, yyyy hh:mm:ss";
    //    2018-12-05T00:00:00.000Z
    public static final SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd'T'00:00:00.000'Z'");
    public static final SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd");
    public static final String utc_format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String utc_format1 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final String format6 = "yyyy-MM-dd";
    public static final String format5 = "MMM dd, yyyy hh:mm:ss a";
    public static final SimpleDateFormat format10 = new SimpleDateFormat(Constants.format5);
    public static final String format = "dd/MMM/yyyy";

    // mandatory or not mandatory
    public static final String ASTERIK_SIGN = "<font color='#03A9F4'> *</font>";
    public static final String text = "<font color='#03A9F4'> 63%</font>";

    public static final int API_METHOD_GET = 0;
    public static final String SP_KEY_USER_DETAIL = "data";
}

