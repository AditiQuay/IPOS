package quay.com.ipos.notifications;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;


import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.constants.Config;
import quay.com.ipos.notifications.model.NotificationBody;
import quay.com.ipos.notifications.model.TaskData;

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();
    static ArrayList<String> notifications = new ArrayList<>();
    private Context mContext;

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationMessage(String message, Intent intent) {
        try {
            showNotificationMessage(message, intent, null);
            Log.e(TAG, "isAppIsInBackground");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void showNotificationMessage(final String message, Intent intent, String imageUrl) {
        // Check for empty push message
        Log.e(TAG, "isAppIsInBackground");
        if (TextUtils.isEmpty(message))
            return;


        // notification icon
        final int icon = R.mipmap.ic_launcher;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mContext,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                mContext);

        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + mContext.getPackageName() + "/raw/notification");

        if (!TextUtils.isEmpty(imageUrl)) {
            Log.e(TAG, "isAppIsInBackground");
            if (imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null) {
                    Log.e(TAG, "isAppIsInBackground");
                    //showSmallNotification(mBuilder, icon, title, resultPendingIntent, alarmSound);
                    showBigNotificationMessageOnly(mBuilder,icon,message,resultPendingIntent,alarmSound);
                } else {
                    Log.e(TAG, "isAppIsInBackground");
                   // showSmallNotification(mBuilder, icon, title, resultPendingIntent, alarmSound);
                    showBigNotificationMessageOnly(mBuilder,icon,message,resultPendingIntent,alarmSound);
                }
            }
        } else {
            Log.e(TAG, "isAppIsInBackground");
            //showSmallNotification(mBuilder, icon, title, resultPendingIntent, alarmSound);
            showBigNotificationMessageOnly(mBuilder,icon,message,resultPendingIntent,alarmSound);
            playNotificationSound();
        }
    }


    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("iPOS");
        notifications.add(title);
        inboxStyle.setSummaryText("You have " + notifications.size() + " Notifications.");
        for (int i = 0; i < notifications.size(); i++) {
            inboxStyle.addLine(notifications.get(i));
        }
        Notification notification;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            notification = mBuilder
                    .setTicker(title)
                    .setWhen(0)
                    .setAutoCancel(true)
                    .setNumber(notifications.size())
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setSound(alarmSound)
                    .setTicker(title)
                    .setStyle(inboxStyle)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(title)
                    .build();
        } else {
            notification = mBuilder
                    .setTicker(title)
                    .setWhen(0)
                    .setAutoCancel(true)
                    .setNumber(notifications.size())
                    .setContentTitle(title)
                    .setContentIntent(resultPendingIntent)
                    .setSound(alarmSound)
                    .setTicker(title)
                    .setStyle(inboxStyle)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(title)
                    .build();
        }

        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(Config.NOTIFICATION_ID, notification);
    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(bitmap);
        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setContentText(message)
                .build();
        Log.e(TAG, "isAppIsInBackground");
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification);
    }

    public void showBigNotificationMessageOnly(NotificationCompat.Builder mBuilder, int icon, String message, PendingIntent resultPendingIntent, Uri alarmSound) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
       /* String arr[] = message.split(",");
        for (int i = 0; i < arr.length-1; i++) {
            inboxStyle.addLine(arr[i]);
        }
        String messageContextText = arr[arr.length - 1];
*/

        if (message != null) {
            NotificationBody notificationBody = new Gson().fromJson(message, NotificationBody.class);
            TaskData taskData = notificationBody.taskData;
            String messageContextText = taskData.taskName;
            inboxStyle.addLine(taskData.taskName);
            inboxStyle.addLine(taskData.taskDescription);
            inboxStyle.addLine(taskData.taskEndDateTime);


        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = IPOSApplication.getContext().getString(R.string.app_name);// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }

        //  bigPictureStyle.bigPicture(bitmap);
        Notification notification;

        notification = mBuilder.setSmallIcon(icon).setTicker(messageContextText).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(IPOSApplication.getContext().getString(R.string.app_name))
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setChannelId(CHANNEL_ID)
                .setContentText(messageContextText)   // hint .setContentText("Pending For Your Action")
                .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                .setSmallIcon(R.mipmap.quay_ic)
                .setStyle(inboxStyle)

                .build();
        Log.e(TAG, "isAppIsInBackground");
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }

        assert notificationManager != null;
        notificationManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification);
    }
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + mContext.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(mContext, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            assert am != null;
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            assert am != null;
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.cancelAll();
    }

    public static long getTimeMilliSec(String timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = format.parse(timeStamp);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static void textNotification(Context context, Intent intent){
        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                context);
        NotificationUtils utils = new NotificationUtils(context);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );
        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getPackageName() + "/raw/notification");
        //  String title = "Summary,Amount,CC,GL,Message";
        String message = "a notification if you want to add multiple short summary lines, such as snippets,Amount,CC,GL,Message";
        utils.showBigNotificationMessageOnly(mBuilder,R.mipmap.ic_launcher,message,resultPendingIntent,alarmSound);

    }
}
