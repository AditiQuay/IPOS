package quay.com.ipos.compliance.receiver;

import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.data.local.entity.SubTask;

/**
 * Created by deepak.kumar1 on 01-05-2018.
 */
public class AlarmReceiver extends BroadcastReceiver {

    private static final String TAG = AlarmReceiver.class.getSimpleName();

    public void onReceive(final Context context, Intent intent) {

        try {


           // DatabaseHelper database = DatabaseHelper.getInstance(context);

           // TaskData taskData = database.getNotification(intent.getIntExtra("NOTIFICATION_ID", 0));
          //  taskData.setNumberShown(taskData.getNumberShown() + 1);
           // database.addNotification(taskData);
            Log.i("NotificationId", intent.getIntExtra("NOTIFICATION_ID", -1) + "");
            int id = intent.getIntExtra("NOTIFICATION_ID", -1);
            if(id==-1){
                Log.e(TAG, "NotificationId is -1");
                return;
            }


            IPOSApplication.getDatabase().subtaskDao().getSubTaskById(id).observeForever(new Observer<SubTask>() {
                @Override
                public void onChanged(@Nullable SubTask subTask) {
                    Log.e(TAG, "Called");
                    if(subTask==null)
                        return;

                   /* Reminder reminder = new Reminder();
                    reminder.setId(subTask.getSub_task_id());
                    reminder.setContent(subTask.task_name);

                    NotificationUtil.createNotification(context, reminder);
*/
                    // Check if new alarm needs to be set
                  /*  if (taskData.getNumberToShow() > taskData.getNumberShown() || Boolean.parseBoolean(taskData.getForeverState())) {
                        AlarmUtil.setNextAlarm(context, taskData, database);
                    }*/
                    Intent updateIntent = new Intent("BROADCAST_REFRESH");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);
                }
            });
           /* NotificationUtil.createNotification(context, taskData);

            // Check if new alarm needs to be set
            if (taskData.getNumberToShow() > taskData.getNumberShown() || Boolean.parseBoolean(taskData.getForeverState())) {
                AlarmUtil.setNextAlarm(context, taskData, database);
            }
            Intent updateIntent = new Intent("BROADCAST_REFRESH");
            LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent);*/
        } catch (Exception e) {
            e.printStackTrace();
        }







      //  database.close();
       /* NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

       // Notification notification = intent.getParcelableExtra(NOTIFICATION);
        int id = intent.getIntExtra("NOTIFICATION_ID", 0);

        //for oreo start
        NotificationChannel mChannel = null;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = context.getResources().getString(R.string.app_name);// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            assert notificationManager != null;
            notificationManager.createNotificationChannel(mChannel);
        }
         //for oreo end

        notificationManager.notify(id, notification);
*/


    }
}