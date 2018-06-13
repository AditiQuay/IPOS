package quay.com.ipos.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;

import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.modal.BillingSync;
import quay.com.ipos.modal.PaymentRequest;

public class NetworkStateChecker extends BroadcastReceiver {

    //context and database helper object
    private Context context;
    private DatabaseHandler db;
    ArrayList<BillingSync> billingSyncs = new ArrayList<>();
    PaymentRequest paymentRequest;


    @Override
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        db = new DatabaseHandler(context);
        paymentRequest= new PaymentRequest();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //if there is a network
        if (activeNetwork != null) {
            //if connected to wifi or mobile data plan
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {

                //getting all the unsynced names
//                Cursor cursor = db.getUnsyncedNames();
                billingSyncs = db.getUnSyncedRetailOrders();
                sendDataToServer();
//                if (cursor.moveToFirst()) {
//                    do {
//                        //calling the method to save the unsynced name to MySQL
//                        saveName(
//                                cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)),
//                                cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME))
//                        );
//                    } while (cursor.moveToNext());
//                }
            }
        }
    }

    private void sendDataToServer() {

    }
}