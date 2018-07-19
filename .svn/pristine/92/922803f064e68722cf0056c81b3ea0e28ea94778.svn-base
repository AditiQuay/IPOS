package quay.com.ipos.compliance.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import java.util.Calendar;
import java.util.List;

import quay.com.ipos.compliance.constants.ReminderConstant;
import quay.com.ipos.utility.DateAndTimeUtil;

public class BootReceiver extends BroadcastReceiver {
    public final static String TAG = BootReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
       /* DatabaseHelper database = DatabaseHelper.getInstance(context);
        Log.i(TAG, "onReceive called");
        List<TaskData> TaskDataList = database.getNotificationList(ReminderConstant.ACTIVE);
       // database.close();
        Intent alarmIntent = new Intent(context, AlarmReceiver.class);

        for (TaskData taskData : TaskDataList) {
            Calendar calendar = DateAndTimeUtil.parseDateAndTime(taskData.getDateAndTime());
            calendar.set(Calendar.SECOND, 0);
            //for alert before
            calendar.add(Calendar.MINUTE,-taskData.getAlertBeforeDueDateAndTime());

            AlarmUtil.setAlarm(context, alarmIntent, taskData.getNotificationId(), calendar);
        }*/
    }
}