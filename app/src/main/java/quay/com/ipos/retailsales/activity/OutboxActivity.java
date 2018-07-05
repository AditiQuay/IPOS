package quay.com.ipos.retailsales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.modal.BillingSync;
import quay.com.ipos.modal.OrderSubmitResult;
import quay.com.ipos.modal.PaymentRequest;
import quay.com.ipos.retailsales.adapter.NameAdapter;
import quay.com.ipos.retailsales.adapter.OutboxAdapter;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.WrapContentLinearLayoutManager;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 14-06-2018.
 */

public class OutboxActivity extends BaseActivity implements ServiceTask.ServiceResultListener{
    OutboxAdapter nameAdapter;
    TextView tvNoItemAvailable;
    LinearLayout llSearch;
    ArrayList<BillingSync> billingSyncs = new ArrayList<>();
    ArrayList<BillingSync> billingSyncsFilter = new ArrayList<>();
    PaymentRequest paymentRequest;
    SwipeRefreshLayout swipeToRefresh;
    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    private RecyclerView rvOutBox;
    DatabaseHandler db;
    private Toolbar toolbar;
    private LinearLayoutManager mLayoutManager;

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
        swipeToRefresh = findViewById(R.id.swipeToRefresh);
        llSearch = findViewById(R.id.llSearch);
        llSearch.setVisibility(View.GONE);
        swipeToRefresh.setEnabled(false);
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
        rvOutBox = findViewById(R.id.rvOutBox);
        rvOutBox.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
//        mRecyclerView.setLayoutManager(mLayoutManager);
        rvOutBox.setLayoutManager(new WrapContentLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        rvOutBox.addItemDecoration(
                new ItemDecorationAlbumColumns(mContext.getResources().getDimensionPixelSize(R.dimen.dim_5),
                        mContext.getResources().getInteger(R.integer.photo_list_preview_columns)));
        //initializing views and objects
        db = new DatabaseHandler(this);
        update();
//        swipeToRefresh.setEnabled(false);
//        swipeToRefresh.setOnRefreshListener(
//                new SwipeRefreshLayout.OnRefreshListener() {
//                    @Override
//                    public void onRefresh() {
////                        sendDataToServer();
//                    }
//                }
//        );

//        listViewNames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                callPrintViewActivity(names.get(i).getReceipt());
//            }
//        });


        final GestureDetector mGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        Util.hideSoftKeyboard(OutboxActivity.this);
                        return true;
                    }

                });

        rvOutBox.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean arg0) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean onInterceptTouchEvent(RecyclerView arg0, MotionEvent motionEvent) {
                View child = arg0.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

//                    Util.hideSoftKeyboard(getActivity());
                    callPrintViewActivity(names.get(arg0.getChildAdapterPosition(child)).getReceipt());
                    return true;

                }

                return false;
            }

        });
    }
    public void sendDataToServer() {
        billingSyncs = db.getUnSyncedRetailOrders();
        billingSyncsFilter = db.getUnSyncedRetailOrders();

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
//            swipeToRefresh.invalidate();
            db.deleteTable(DatabaseHandler.TABLE_RETAIL_BILLING);
        }
    }

    public void callPrintViewActivity(String mPrintViewResult){
        Intent mIntent = new Intent(OutboxActivity.this, PrintReceiptActivity.class);
        mIntent.putExtra(Constants.RECEIPT,mPrintViewResult);
        mIntent.putExtra(Constants.RECEIPT_FROM,Constants.outboxMode);
        startActivity(mIntent);
    }

    private void callServicePayment() {
        showProgressDialog(R.string.please_wait);
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
                        editBillToLocalStorage(paymentRequest, NAME_SYNCED_WITH_SERVER);
                    } else {
                        editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
                    }
                }
            }
        } else if (httpStatusCode == Constants.BAD_REQUEST) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            editBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
        }
    }

    private void editBillToLocalStorage(PaymentRequest paymentRequest, int status) {
        try {
            if (!db.isRetailMasterEmpty(DatabaseHandler.TABLE_RETAIL_BILLING)) {
                billingSyncs = db.getUnSyncedRetailOrders();
                billingSyncsFilter = db.getUnSyncedRetailOrders();
                if (billingSyncs.size() > 0) {
                    if (db.checkIfBillingRecordExist(paymentRequest.getOrderTimestamp()))
                        db.updateSync(status, paymentRequest.getOrderTimestamp());
                    sendDataToServer();
                } else {
//                    swipeToRefresh.invalidate();
                    db.deleteTable(DatabaseHandler.TABLE_RETAIL_BILLING);
                }

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
        nameAdapter = new OutboxAdapter(this, names);
        rvOutBox.setAdapter(nameAdapter);
        if(names.size()>0) {
            nameAdapter.notifyDataSetChanged();
            tvNoItemAvailable.setVisibility(View.GONE);
            rvOutBox.setVisibility(View.VISIBLE);
//            swipeToRefresh.setVisibility(View.VISIBLE);
            for(int i =0 ; i < names.size(); i++){
                if(names.get(i).getSync()==1){
                    names.remove(i);
                    i--;
                }
            }
        }else {
            db.deleteTable(DatabaseHandler.TABLE_RETAIL_BILLING);
            nameAdapter.notifyDataSetChanged();
//            swipeToRefresh.setVisibility(View.GONE);
//            Util.showToast("No Outbox list available", IPOSApplication.getAppInstance());
//            finish();
            tvNoItemAvailable.setVisibility(View.VISIBLE);
            rvOutBox.setVisibility(View.GONE);
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
