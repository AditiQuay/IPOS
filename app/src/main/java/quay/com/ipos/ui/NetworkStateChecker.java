package quay.com.ipos.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.modal.BillingSync;
import quay.com.ipos.modal.OrderSubmitResult;
import quay.com.ipos.modal.PaymentRequest;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Util;

public class NetworkStateChecker extends BroadcastReceiver implements ServiceTask.ServiceResultListener{

    //context and database helper object
    private Context context;
    private DatabaseHandler db;
    ArrayList<BillingSync> billingSyncs = new ArrayList<>();
    PaymentRequest paymentRequest;

    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;

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

                //getting all the UnSyncedRetailOrders names
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
        AppLog.e("tag","sendDataToServer");
        if(billingSyncs.size()>0)
            for (int i = 0 ; i < billingSyncs.size() ; i++){
                BillingSync billingSync = billingSyncs.get(i);
                paymentRequest = Util.getCustomGson().fromJson(billingSync.getBilling(),PaymentRequest.class);
                paymentRequest.setOrderDateTime(billingSync.getOrderDateTime());
                paymentRequest.setOrderTimestamp(billingSync.getOrderTimestamp());
                callServicePayment();
            }
    }

    private void callServicePayment() {
        ServiceTask mServiceTask = new ServiceTask();
        mServiceTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_ORDER_SUBMIT);
        mServiceTask.setParamObj(paymentRequest);
        mServiceTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mServiceTask.setListener(this);
        mServiceTask.setResultType(OrderSubmitResult.class);
        mServiceTask.execute();
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        if(httpStatusCode == Constants.SUCCESS){
                if (serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_RETAIL_ORDER_SUBMIT)) {
                    if (resultObj != null) {
                        OrderSubmitResult mOrderSubmitResult = (OrderSubmitResult) resultObj;
                        if (mOrderSubmitResult.getError() == 200) {
                            IPOSApplication.mProductListResult.clear();
                            IPOSApplication.totalAmount = 0.0;
//                            Util.showToast(mOrderSubmitResult.getMessage(), IPOSApplication.getContext());
                            editBillToLocalStorage(paymentRequest, NAME_SYNCED_WITH_SERVER);

                        } else {

                            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
                            Util.showToast(mOrderSubmitResult.getErrorDescription(), IPOSApplication.getContext());
                        }
                    }
                }
        } else if (httpStatusCode == Constants.BAD_REQUEST) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
            Toast.makeText(context, context.getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
            Toast.makeText(context, context.getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
            Toast.makeText(context, context.getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
            Toast.makeText(context, context.getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
            Toast.makeText(context, context.getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
        }
    }

    private void editBillToLocalStorage(PaymentRequest paymentRequest, int status) {

        if(!db.isRetailMasterEmpty(DatabaseHandler.TABLE_RETAIL_BILLING)) {
            db.updateSync(status, paymentRequest.getCustomerID());
            AppLog.e("tag","RetailMaster Not Empty");
        }else {
            AppLog.e("tag","RetailMasterEmpty");
        }
    }
}