package quay.com.ipos.retailsales.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.modal.BillingSync;
import quay.com.ipos.modal.Name;
import quay.com.ipos.modal.OrderSubmitResult;
import quay.com.ipos.modal.PaymentRequest;
import quay.com.ipos.retailsales.adapter.NameAdapter;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.NetworkStateChecker;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 14-06-2018.
 */

public class OutboxActivity extends BaseActivity implements ServiceTask.ServiceResultListener{
    NameAdapter nameAdapter;
    TextView tvNoItemAvailable;
    ImageView imvRefresh,imvBack;
    ArrayList<BillingSync> billingSyncs = new ArrayList<>();
    ArrayList<BillingSync> billingSyncs1 = new ArrayList<>();
    PaymentRequest paymentRequest;

    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    private ListView listViewNames;
    DatabaseHandler db;
    private Toolbar toolbar;
    //List to store all the names
    private ArrayList<BillingSync> names;
    private static OutboxActivity mainActivityRunningInstance;
    public static OutboxActivity  getInstace(){
        return mainActivityRunningInstance;
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbox_list);
        toolbar = findViewById(R.id.toolbar);
        imvRefresh = findViewById(R.id.imvRefresh);
        imvBack = findViewById(R.id.imvBack);
        tvNoItemAvailable = findViewById(R.id.tvNoItemAvailable);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("Retail Outbox");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        mainActivityRunningInstance =this;
        listViewNames = findViewById(R.id.listViewNames);
        //initializing views and objects
        db = new DatabaseHandler(this);
        update();
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.hideSoftKeyboard(OutboxActivity.this);
                finish();
            }
        });

        imvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDataToServer();
            }
        });

    }
    public void sendDataToServer() {
        billingSyncs = db.getUnSyncedRetailOrders();
        AppLog.e("tag","sendDataToServer");
        if(billingSyncs.size()>0) {
            for (int i = 0; i < billingSyncs.size(); i++) {
                BillingSync billingSync = billingSyncs.get(i);
                paymentRequest = Util.getCustomGson().fromJson(billingSync.getBilling(), PaymentRequest.class);
                paymentRequest.setOrderDateTime(billingSync.getOrderDateTime());
                paymentRequest.setOrderTimestamp(billingSync.getOrderTimestamp());
                callServicePayment();
            }
        }else {
            db.deleteTable(DatabaseHandler.TABLE_RETAIL_BILLING);
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
//                        IPOSApplication.mProductListResult.clear();
//                        IPOSApplication.totalAmount = 0.0;
//                            Util.showToast(mOrderSubmitResult.getMessage(), IPOSApplication.getContext());
                        editBillToLocalStorage(paymentRequest, NAME_SYNCED_WITH_SERVER);

                    } else {

                        editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
//                            Util.showToast(mOrderSubmitResult.getErrorDescription(), IPOSApplication.getContext());
                    }
                }
            }
        } else if (httpStatusCode == Constants.BAD_REQUEST) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
//            Toast.makeText(context, context.getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
//            Toast.makeText(context, context.getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
//            Toast.makeText(context, context.getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
//            Toast.makeText(context, context.getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
//            Toast.makeText(context, context.getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
        }
    }

    private void editBillToLocalStorage(PaymentRequest paymentRequest, int status) {
        try {
            if (!db.isRetailMasterEmpty(DatabaseHandler.TABLE_RETAIL_BILLING)) {
                billingSyncs1 = db.getUnSyncedRetailOrders();
                if (billingSyncs1.size() > 0) {
//                    for (int i = 0; i < billingSyncs1.size(); i++) {
                    if (db.checkIfBillingRecordExist(paymentRequest.getOrderTimestamp()))
                        db.updateSync(status, paymentRequest.getOrderTimestamp());
//                            if (paymentRequest.getOrderTimestamp().equalsIgnoreCase(billingSyncs1.get(i).getOrderTimestamp())) {
//                                db.updateSync(status, paymentRequest.getOrderTimestamp());
//                            } else {
//
//                            }

//                    }
                    sendDataToServer();
                } else {
                    db.deleteTable(DatabaseHandler.TABLE_RETAIL_BILLING);
                }
                billingSyncs1 = db.getAllRetailBillingOrders();
//                if (status == NAME_SYNCED_WITH_SERVER) {
//                    db.deleteRetailBillingTable(paymentRequest.getOrderTimestamp());
//                }

                AppLog.e("tag", "RetailMaster Not Empty");
            } else {
                AppLog.e("tag", "RetailMasterEmpty");
            }
        } catch (Exception e) {

        }

        update();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Util.hideSoftKeyboard(OutboxActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

   public void update(){
        names = new ArrayList<>();
        names = db.getUnSyncedRetailOrders();
        nameAdapter = new NameAdapter(this, names);
        listViewNames.setAdapter(nameAdapter);
        if(names.size()>0) {
            nameAdapter.notifyDataSetChanged();
            tvNoItemAvailable.setVisibility(View.GONE);
            listViewNames.setVisibility(View.VISIBLE);
        }else {
            db.deleteTable(DatabaseHandler.TABLE_RETAIL_BILLING);
            nameAdapter.notifyDataSetChanged();
            Util.showToast("No Outbox list available", IPOSApplication.getAppInstance());
//            finish();
            tvNoItemAvailable.setVisibility(View.VISIBLE);
            listViewNames.setVisibility(View.GONE);
        }
    }

//    public void updateUI(final String str) {
//        MainActivity.this.runOnUiThread(new Runnable() {
//            public void run() {
//                //use findFragmentById for fragments defined in XML ((SimpleFragment)getSupportFragmentManager().findFragmentByTag(fragmentTag)).updateUI(str);
//            }
//        });
//    }


}
