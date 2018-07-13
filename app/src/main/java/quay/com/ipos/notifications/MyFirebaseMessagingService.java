package quay.com.ipos.notifications;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import quay.com.ipos.compliance.DashboardActivity;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        if (remoteMessage == null) {
            return;
        }
        Log.e(TAG, "isAppIsInBackground");
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getTitle());
        }

        if (remoteMessage.getData().size() > 0) {
            if (!remoteMessage.getData().containsKey("message")) {
                Toast.makeText(this, "No message key found!", Toast.LENGTH_SHORT).show();
                return;
            }
            String message = remoteMessage.getData().get("message");
            Log.i(TAG + "Message", message);
            //Show Notification title.
            handleNotification(message);

            //get Json notification data and set to model class
//            settleExpensesListModel = new SettleExpensesListModel();
//            try {
//                String response = remoteMessage.getData().get("body");
//                JSONObject json = new JSONObject(response);
//                JSONArray jsonArray = json.getJSONArray(ServerResponseKeyConstants.KEY_SETTLE_EXPENSE_CATEGORY_ITEMS);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject obj = jsonArray.getJSONObject(i);
//                    SettleExpensesCategoryItemModel settleExpensesCategoryItemModel = new SettleExpensesCategoryItemModel();
//                    settleExpensesCategoryItemModel.RequestCode = obj.optInt(ServerResponseKeyConstants.KEY_REQUEST_CODE);
//                    settleExpensesCategoryItemModel.SettleExpensesCategoryName = obj.optString(ServerResponseKeyConstants.KEY_SETTLE_EXPENSE_CATEGORY_NAME);
//                    settleExpensesCategoryItemModel.DateDuration = obj.optString(ServerResponseKeyConstants.KEY_DATE_AND_DURATION);
//                    settleExpensesCategoryItemModel.TAAmount = obj.optString(ServerResponseKeyConstants.KEY_TA_AMOUNT);
//                    settleExpensesCategoryItemModel.DAAmount = obj.optString(ServerResponseKeyConstants.KEY_DA_AMOUNT);
//                    settleExpensesCategoryItemModel.Status = obj.optString(ServerResponseKeyConstants.KEY_STATUS);
//                    settleExpensesCategoryItemModel.TotalDistance = obj.optString(ServerResponseKeyConstants.KEY_TOTAL_DISTANCE);
//                    settleExpensesCategoryItemModel.TotalAmount = obj.optString(ServerResponseKeyConstants.KEY_TOTAL_AMOUNT);
//                    settleExpensesCategoryItemModel.PlacesVisited = obj.optString(ServerResponseKeyConstants.KEY_PLACES_VISITED);
//                    settleExpensesCategoryItemModel.ViewFlag = obj.optString(ServerResponseKeyConstants.KEY_VIEW_FLAG);
//                    settleExpensesCategoryItemModel.NotificationType = obj.optString(ServerResponseKeyConstants.KEY_KEY_TYPE);
//                    settleExpensesCategoryItemModel.ActionTypeRoleWise = obj.optString(ServerResponseKeyConstants.KEY_ACTION_TYPE_ROLE_WISE);
//                    settleExpensesCategoryItemModelsSet.add(settleExpensesCategoryItemModel);
        }
//                settleExpensesListModel.SettleExpensesCategoryItemModelList = settleExpensesCategoryItemModelsSet;
//                settleExpensesListModel.save(new RushCallback() {
//                    @Override
//                    public void complete() {
//
//                    }
//                });
//            } catch (Exception e) {
//                Log.e(TAG, "Exception: " + e.getMessage());
//            }
//
//        }
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in background, show the notification in notification tray
            Intent resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
            resultIntent.putExtra("message", message);
            showNotificationMessage(getApplicationContext(), message, resultIntent);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        } else {

            Intent resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
            resultIntent.putExtra("message", message);
            showNotificationMessage(getApplicationContext(), message, resultIntent);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String message, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(message, intent);
        Log.e(TAG, "isAppIsInBackground");
    }
}