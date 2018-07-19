package quay.com.ipos.utility;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;


/**
 * Display Util Class
 * dp2px, px2dp, hide keyboard etc
 */
public final class DisplayUtils {


    /**
     * Don't let anyone instantiate this class.
     */
    private DisplayUtils() {
        throw new Error("Do not need instantiate!");
    }


    /**
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }


    /**
     * @param activity
     * @return
     */
    public static int getDialogW(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = activity.getResources().getDisplayMetrics();
        int w = dm.widthPixels - 100;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth() - 100;
        return w;
    }


    /**
     * @param activity
     * @return
     */
    public static int getScreenW(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = activity.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        // int w = aty.getWindowManager().getDefaultDisplay().getWidth();
        return w;
    }


    /**
     * @param activity
     * @return
     */
    public static int getScreenH(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        dm = activity.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        // int h = aty.getWindowManager().getDefaultDisplay().getHeight();
        return h;
    }


    /**
     * Toggle keyboard If the keyboard is visible,then hidden it,if it's
     * invisible,then show it
     *
     * @param context
     */
    public static void toggleKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * hide keyboard
     * @param activity
     */
    public static void hideVirturalKeyboard(Activity activity) {

        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm != null) {
            if(activity.getWindow().getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(activity.getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
}
