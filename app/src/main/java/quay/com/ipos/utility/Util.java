package quay.com.ipos.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.modal.CustomerList;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.realmbean.RealmPinnedResults;
import quay.com.ipos.ui.MessageDialog;
import quay.com.ipos.ui.MessageDialogFragment;


public class Util {
    private static final int BUILD_VERSION = Build.VERSION.SDK_INT;
    private static final int VERSION_LOLLIPOP = Build.VERSION_CODES.LOLLIPOP;
    private static final int VERSION_JELLYBEAN = Build.VERSION_CODES.JELLY_BEAN;
    private static final String IMAGE_NAME = "SavedImage.png";
    private static Context mContext = IPOSApplication.getContext();
    private static String formatedDate;
    private static String erg = "";
    private static TelephonyManager telephonyManager;


    /** The typeface cache. */
    public static Map<String, Typeface> typefaceCache = new HashMap<String, Typeface>();

    public static boolean isJellyBean() {
        return BUILD_VERSION >= VERSION_JELLYBEAN;
    }

    public static boolean isLollipop() {
        return BUILD_VERSION >= VERSION_LOLLIPOP;
    }

    public static boolean validateString(String value) {
        return value != null && !value.isEmpty();
    }

    public static boolean validateEmail(String email) {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS;
        return emailPattern.matcher(email).matches();
    }

    public static boolean validateURL(String URL) {
        Pattern urlPattern = Patterns.WEB_URL;
// Pattern urlPattern = Pattern.compile("(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?[a-zA-Z_0-9\\-]+(\\.\\w[a-zA-Z_0-9\\-]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?");
        return urlPattern.matcher(URL).matches();
    }

    /**
     * Gets the custom gson.
     *
     * @return the custom gson
     */
    public static Gson getCustomGson() {
        GsonBuilder gb = new GsonBuilder();
        return gb.create();
    }
    /*Util for progress dialog*/
    public static ProgressDialog showProgressDialog(Context context, String message) {
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage(message);
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        m_Dialog.setCanceledOnTouchOutside(false);
        m_Dialog.show();
        return m_Dialog;

    }
    /**
     * Sets the typeface.
     *
     * @param attrs
     *            the attrs
     * @param textView
     *            the text view
     */
    public static void setTypeface(AttributeSet attrs, TextView textView) {
        Context context = textView.getContext();
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView);
        String typefaceName = values.getString(R.styleable.CustomTextView_typeface);

        if (typefaceCache.containsKey(typefaceName)) {
            textView.setTypeface(typefaceCache.get(typefaceName));
        } else {
            Typeface typeface;
            try {



//                typeface = Typeface.createFromAsset(textView.getContext().getAssets(),
//                        context.getString(R.string.assets_fonts_folder) + typefaceName);
                if(typefaceName==null)
                typeface = Typeface.createFromAsset(textView.getContext().getAssets(),
                        context.getString(R.string.assets_fonts_folder) + "/TitilliumWeb-Regular.ttf");
                else
                    typeface = Typeface.createFromAsset(textView.getContext().getAssets(),
                        context.getString(R.string.assets_fonts_folder) + typefaceName);
            } catch (Exception e) {
                AppLog.v(context.getString(R.string.app_name), e.toString());
//				AppLog.v(context.getString(R.string.app_name),
//						String.format(context.getString(R.string.typeface_not_found), typefaceName));
                return;
            }

            typefaceCache.put(typefaceName, typeface);
            textView.setTypeface(typeface);
        }

        values.recycle();
    }
    public static boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static Point getDisplayInfo(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(point);
        } else {
            display.getSize(point);
        }
        return point;
    }

    public static void showToast(int msgId) {
        Toast.makeText(mContext, msgId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String message,Context mContext) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static int getIntRes(int resId) {
        return mContext.getResources().getInteger(resId);
    }

    public static String getStringRes(int resId) {
        return mContext.getResources().getString(resId);
    }

    public static String[] getStringArray(int resId) {
        return mContext.getResources().getStringArray(resId);
    }

    public static int getColorRes(int resId) {
        return mContext.getResources().getColor(resId);
    }

    public static float getDimenRes(int resId) {
        return mContext.getResources().getDimension(resId);
    }

    public static Drawable getDrawableRes(int id) {
        return mContext.getResources().getDrawable(id);
    }

    public static Drawable getDrawableImage(String name) {
        int drawable = mContext.getResources().getIdentifier(name, "drawable", mContext.getPackageName());
        return ContextCompat.getDrawable(mContext, drawable);
    }

    public static File getSavedImage(boolean cache) {
        String path;
        if (cache) {
            path = mContext.getCacheDir() + "/" + System.currentTimeMillis() + ".png";
        } else {
            path = mContext.getCacheDir() + IMAGE_NAME;
        }
        return new File(path);
    }

    public static String getCurrentTimeStamp(){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        return ts;
    }

    public static int getSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }


    @SuppressLint("NewApi")
    public static void setDrawable(View view, int value) {
        if (mContext != null && view != null) {
            Drawable drawable = getDrawableRes(value);
            if (isJellyBean()) {
                view.setBackground(drawable);
            } else {
                view.setBackgroundDrawable(drawable);
            }
        }
    }



    public static void hideKeyboard(EditText editText) {
        hideKeyboard(editText, false);
    }

    public static void hideKeyboard(EditText editText, boolean clear) {
        InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        if (clear) {
            editText.setText("");
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showKeyboard(EditText editText) {
        editText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
    }

    public static boolean doesPackageExist(String targetPackage) {
        PackageManager pm = mContext.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    public static PackageInfo getPackageInfo() {
        PackageInfo packageInfo = null;
        PackageManager manager = mContext.getPackageManager();
        try {
            packageInfo = manager.getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo;
    }


    public static String getCurrentDay() {
        int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
                return "Tuesday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
                return "Saturday";
        }
        return "Sunday";
    }

    public static String getCurrentTime() {
        Calendar calander = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

        String time = simpleDateFormat.format(calander.getTime());
        return time;
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        formatedDate = df.format(c.getTime());
        return formatedDate;
    }

    public static String dateDialogfrom() {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        erg = String.valueOf(dayOfMonth);
                        erg += "-" + String.valueOf(monthOfYear + 1);
                        erg += "-" + year;

                    }

                }, y, m, d);
        dp.setTitle("Calender");
        dp.show();

        return erg;
    }


    public static String replaceSpaceToDashes(String value) {
        return value.replaceAll("\\s", "-");
    }


//    private static String getTodaysDate() {
//        DateTime date = new DateTime();
//        date = date.minusHours(7);
//
//        DateTimeFormatter dtf = DateTimeFormat.forPattern(AppConstants.utc_format);
//        return dtf.print(date);
//    }



    public static String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    public static void setTextView(String title, TextView textViewId, boolean isMandatoryError) {
        if (Util.validateString(title)) {
            if (title.contains("*")) {
                title = title.replace("*", "");
            }
        }
        if (isMandatoryError) {
            title = "<font color='#ff0000'>" + title + " * </font>";
        } else {
            title = "<font color='#000000'>" + title + "</font> " + Constants.ASTERIK_SIGN;
        }
        if (textViewId != null) {
            textViewId.setText(Html.fromHtml(title), TextView.BufferType.SPANNABLE);
        }
    }


    public static double round(double value, int places) {
        try {
            if (places > 0) {
                long factor = (long) Math.pow(10, places);
                value = value * factor;
                long tmp = Math.round(value);
                return (double) tmp / factor;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isValidEmaillId(String email) {

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }





    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static void setLocale(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }



    public static String getFormattedDates(String strCurrentDate, String dateFormat1, SimpleDateFormat format3) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat1);
            Date newDate = format.parse(strCurrentDate);
            return format3.format(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void animateView(View v) {
        if (v != null) {
            Animation fadeIn = new AlphaAnimation(0.5f, 1);
            fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
            fadeIn.setDuration(50);
            fadeIn.setStartOffset(100);
            Animation fadeOut = new AlphaAnimation(1, 0.5f);
            fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
            fadeOut.setDuration(50);
            AnimationSet animation = new AnimationSet(false); //change to false
            animation.addAnimation(fadeOut);
            animation.addAnimation(fadeIn);
            v.startAnimation(animation);
        }
    }


    public static String getAssetJsonResponse(Context context, String filename) {
        AssetManager assetManager = context.getAssets();
        InputStream input;
        String text = "";

        try {
            input = assetManager.open(filename);

            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            // byte buffer into a string
            text = new String(buffer);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return text;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the app shared preference.
     *
     * @param aContext
     *            the a context
     * @return the app shared preference
     */

    public static SharedPreferences getAppSharedPreference(final Context aContext) {
        SharedPreferences sp = null;

        if (null != aContext) {
            sp = aContext.getSharedPreferences(Constants.APP_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }

        return sp;
    }


    /**
     * Gets the cached email user.
     *
     * @return the cached email user
     */
//    public static User getCachedUser() {
//        User user = null;
//        Context context = SharekhanApplication.getAppInstance().getApplicationContext();
//        SharedPreferences sp = getAppSharedPreference(context);
//
//        if (null != sp) {
//            String userDetail = sp.getString(AppConstants.SP_KEY_USER_DETAIL, "");
//
//            if (false == userDetail.isEmpty()) {
//                Gson gson = AppUtil.getCustomGson();
//                user = gson.fromJson(userDetail, User.class);
//            }
//        }
//        return user;
//    }

    /**
     * Cache user.
     *
     *  user
     *            the user
     */
//    public static void cacheUser(User user) {
//        Context context = SharekhanApplication.getAppInstance().getApplicationContext();
//        SharedPreferences sp = getAppSharedPreference(context);
//
//        if (null != user && null != sp) {
//            Gson gson = AppUtil.getCustomGson();
//            String userjson = gson.toJson(user);
//            SharedPreferences.Editor ed = sp.edit();
//            ed.putString(AppConstants.SP_KEY_USER_DETAIL, userjson);
//            ed.commit();
//        }
//    }

//    public static ArrayList<ProductList.Datum> getCachedData() {
//        List<ProductList.Datum> mQuestionList = null;
//        Context context = IPOSApplication.getAppInstance().getApplicationContext();
//        SharedPreferences sp = getAppSharedPreference(context);
//
//        if (null != sp) {
//            String questionList = sp.getString("data", "");
//
//            if (false == questionList.isEmpty()) {
//                Gson gson = Util.getCustomGson();
//                ProductList.Datum[] mQuestionListData = gson.fromJson(questionList, ProductList.Datum[].class);
//                mQuestionList = Arrays.asList(mQuestionListData);
//                mQuestionList = new ArrayList<>(mQuestionList);
//            }
//        }
//        return (ArrayList<ProductList.Datum>) mQuestionList;
//    }

//        public static RealmPinnedResults getCachedPinned() {
//            RealmPinnedResults user = null;
//        Context context = IPOSApplication.getAppInstance();
//        SharedPreferences sp = getAppSharedPreference(context);
//
//        if (null != sp) {
//            String userDetail = sp.getString("Pinned_data", "");
//
//            if (false == userDetail.isEmpty()) {
//                Gson gson = Util.getCustomGson();
//                user = gson.fromJson(userDetail, RealmPinnedResults.class);
//            }
//        }
//        return user;
//    }

    public static void cacheData(ArrayList<ProductList.Datum> mDatum) {
        Context context = IPOSApplication.getAppInstance().getApplicationContext();
        SharedPreferences sp = getAppSharedPreference(context);

        if (null != mDatum && null != sp) {
            Gson gson = Util.getCustomGson();
            String userjson = gson.toJson(mDatum);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("data", userjson);
            ed.commit();
        }
    }


    public static void cacheCustomerData(ArrayList<CustomerList.Customer> mDatum) {
        Context context = IPOSApplication.getAppInstance().getApplicationContext();
        SharedPreferences sp = getAppSharedPreference(context);

        if (null != mDatum && null != sp) {
            Gson gson = Util.getCustomGson();
            String userjson = gson.toJson(mDatum);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("customer_data", userjson);
            ed.commit();
        }
    }

    public static void cachePinnedData(ArrayList<RealmPinnedResults.Info> mDatum) {
        Context context = IPOSApplication.getAppInstance().getApplicationContext();
        SharedPreferences sp = getAppSharedPreference(context);

        if (null != mDatum && null != sp) {
            Gson gson = Util.getCustomGson();
            String userjson = gson.toJson(mDatum);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("Pinned_data", userjson);
            ed.commit();
        }
    }


    public static ArrayList<RealmPinnedResults.Info> getCachedPinnedData() {
        List<RealmPinnedResults.Info> mQuestionList = null;
        Context context = IPOSApplication.getAppInstance().getApplicationContext();
        SharedPreferences sp = getAppSharedPreference(context);

        if (null != sp) {
            String questionList = sp.getString("Pinned_data", "");

            if (false == questionList.isEmpty()) {
                Gson gson = Util.getCustomGson();
                RealmPinnedResults.Info[] mQuestionListData = gson.fromJson(questionList, RealmPinnedResults.Info[].class);
                mQuestionList = Arrays.asList(mQuestionListData);
                mQuestionList = new ArrayList<>(mQuestionList);
            }
        }
        return (ArrayList<RealmPinnedResults.Info>) mQuestionList;
    }

        public static void cachePinnedResults(RealmPinnedResults user) {
        Context context = IPOSApplication.getContext();
        SharedPreferences sp = getAppSharedPreference(context);

        if (null != user && null != sp) {
            Gson gson = Util.getCustomGson();
            String userjson = gson.toJson(user);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString(Constants.SP_KEY_USER_DETAIL, userjson);
            ed.commit();
        }
    }


    public static RealmPinnedResults getCachedPinnedResults() {
            RealmPinnedResults user = null;
        Context context = IPOSApplication.getContext();
        SharedPreferences sp = getAppSharedPreference(context);

        if (null != sp) {
            String userDetail = sp.getString(Constants.SP_KEY_USER_DETAIL, "");

            if (false == userDetail.isEmpty()) {
                Gson gson = Util.getCustomGson();
                user = gson.fromJson(userDetail, RealmPinnedResults.class);
            }
        }
        return user;
    }



    public static double numberFormat(double number){
        try{
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(1);// set as you need
            String myString = nf.format(number);
            AppLog.e("Util","string : " + myString);
            number = Double.parseDouble(myString);
        }catch(Exception e){
            System.out.println("ex="+e);
        }
        return number;
    }

    public static void OpenSetting(Context ctx){
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", ctx.getPackageName(), null);
        intent.setData(uri);
        ctx.startActivity(intent);
    }

    public static void showMessageDialog(Context mContext,MessageDialog.MessageDialogListener listener, String message, String yesButton, String noButton, int mCallType, String Title, FragmentManager supportFragmentManager) {
        MessageDialog fragment = new MessageDialog(mContext,Title, message,yesButton,noButton, listener,mCallType);

//        fragment.show(supportFragmentManager, "scan_results");
    }
    public static void showMessageDialog(Context mContext,MessageDialog.MessageDialogListener listener, String message, String yesButton, String noButton,String cancel, int mCallType, String Title, FragmentManager supportFragmentManager) {
        MessageDialog fragment = new MessageDialog(mContext,Title, message,yesButton,noButton,cancel, listener,mCallType);

//        fragment.show(supportFragmentManager, "scan_results");
    }


}
