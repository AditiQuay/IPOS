package quay.com.ipos.retailsales.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.modal.LoginResult;
import quay.com.ipos.modal.PrintViewResult;
import quay.com.ipos.modal.RetailOrderCentreRequest;
import quay.com.ipos.retailsales.adapter.GSTSummaryAdapter;
import quay.com.ipos.retailsales.adapter.ItemDetailAdapter;
import quay.com.ipos.retailsales.adapter.PaymentAdapter;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.LinearLayoutManagerDummy;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 29-06-2018.
 */

public class PrintReceiptActivity extends BaseActivity implements ServiceTask.ServiceResultListener{

    String json="",orderID="";
    String jsonFrom;
    private Toolbar toolbarInfoDetail;
    private LinearLayout llRedeem;
    private RecyclerView rvItemDetails,rvGST,rvPayment;
    private TextView tvStoreName,tvStoreAddress,tvStorePhone,tvStoreEmail,tvStoreCIN,tvStoreGSTIN,tvTaxVoice,tvBillNumber,
            tvDateTime,tvCustomerDetails,tvItemDetails,tvItemSize,tvTotalQty,tvTotalAmount,tvTotalDiscountPrice,
            tvTotalPoints,tvTotalRedeemValue,tvNetTotal,tvCGSTPrice,tvSGSTPrice,tvRoundingOffPrice,tvSaleValue,
            tvPaymentDetails,tvGSTSummary,tvTotalGSTValue;
    private LinearLayoutManagerDummy mLayoutManager;
    private ItemDetailAdapter mItemDetailAdapter;
    private GSTSummaryAdapter mGSTSummaryAdapter;
    private PaymentAdapter paymentAdapter;
    ArrayList<PrintViewResult.ItemDetail> itemDetails = new ArrayList<>();
    ArrayList<PrintViewResult.PaymentsDetail> paymentsDetails = new ArrayList<>();
    ArrayList<PrintViewResult.GstSummary> gstSummaries = new ArrayList<>();
    ArrayList<PrintViewResult.GstSummary> gstSummariesGroupBy = new ArrayList<>();
    PrintViewResult mPrintViewResult;
    String fromDate="NA",toDate="NA",paymentMode="NA";
    LoginResult loginResult;
    String mobile="";
    String name="";
    DatabaseHandler db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_print_view);
        initializeComponent();
//        try {
        db = new DatabaseHandler(this);
        if (getIntent() != null) {

            jsonFrom = getIntent().getStringExtra(Constants.RECEIPT_FROM);
            if (jsonFrom.equalsIgnoreCase(Constants.paymentMode) ) {
                toolbarInfoDetail.setTitle(getResources().getString(R.string.retail_sales));
                json = getIntent().getStringExtra(Constants.RECEIPT);
                if (!json.equalsIgnoreCase("")) {
                    AppLog.e(PrintReceiptActivity.class.getSimpleName(),"PrintViewResult : "+json);
                    mPrintViewResult = Util.getCustomGson().fromJson(json, PrintViewResult.class);
                    setValues();
                }
            }else if(jsonFrom.equalsIgnoreCase(Constants.outboxMode)){
                toolbarInfoDetail.setTitle(getResources().getString(R.string.retail_outbox));
                json = getIntent().getStringExtra(Constants.RECEIPT);
                if (!json.equalsIgnoreCase("")) {
                    AppLog.e(PrintReceiptActivity.class.getSimpleName(),"PrintViewResult : "+json);
                    mPrintViewResult = Util.getCustomGson().fromJson(json, PrintViewResult.class);
                    setValues();
                }
            }else if(jsonFrom.equalsIgnoreCase(Constants.OrderCenterMode)){
                toolbarInfoDetail.setTitle(getResources().getString(R.string.retail_sales_order_centre));
                orderID = getIntent().getStringExtra(Constants.KEY_ORDER_ID);
                callServiceRetailOrderCenter(fromDate,toDate,paymentMode,orderID);
            }
        }
//        }catch (Exception e){
//            AppLog.e(PrintReceiptActivity.class.getSimpleName(),e.getMessage());
//        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void callServiceRetailOrderCenter(String fromDate,String toDate,String paymentMode,String searchParam) {
        loginResult = Util.getCustomGson().fromJson(SharedPrefUtil.getString(Constants.Login_result,"",this),LoginResult.class);
        RetailOrderCentreRequest orderCentreRequest = new RetailOrderCentreRequest();
        orderCentreRequest.setBusinessPlaceCode(loginResult.getUserAccess().getWorklocationID()+"");
        orderCentreRequest.setBusinessStateCode(loginResult.getUserAccess().getWorklocations().get(0).getLocationStateCode());
        orderCentreRequest.setEmployeeCode(loginResult.getUserAccess().getEmpCode());
        orderCentreRequest.setEmployeeRole(loginResult.getUserAccess().getUserRole());
        orderCentreRequest.setStoreId(loginResult.getUserAccess().getWorklocationID()+"");
        orderCentreRequest.setFromDate(fromDate);
        orderCentreRequest.setToDate(toDate);
        orderCentreRequest.setPaymentType(paymentMode);
        orderCentreRequest.setSearchParam(searchParam);
        orderCentreRequest.setType("NA");

        showProgressDialog(R.string.please_wait);
        ServiceTask mServiceTask = new ServiceTask();
        mServiceTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_ORDER_CENTER_PRINT);
        mServiceTask.setParamObj(orderCentreRequest);
        mServiceTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mServiceTask.setListener(this);
        mServiceTask.setResultType(PrintViewResult.class);
        mServiceTask.execute();
    }
    private void setValues() {
//        try {


        itemDetails.addAll(mPrintViewResult.getItemDetails());
        mItemDetailAdapter.notifyDataSetChanged();
        paymentsDetails.addAll(mPrintViewResult.getPaymentsDetails());
        paymentAdapter.notifyDataSetChanged();
        gstSummaries.addAll(mPrintViewResult.getGstSummary());
        db.deleteTable(DatabaseHandler.TABLE_RETAIL_GST);
        for(int i = 0 ; i < gstSummaries.size() ; i++){
            db.addGST(gstSummaries.get(i));
        }
        db.getGST();
        gstSummaries.clear();
        gstSummaries.addAll( db.getGSTGROUPBY());
        mGSTSummaryAdapter.notifyDataSetChanged();
        tvStoreName.setText(mPrintViewResult.getLocationName()+"");
        tvStoreAddress.setText(mPrintViewResult.getBusinessPlaceName()+"");
        tvStorePhone.setText("Phone: "+mPrintViewResult.getLocationPhone1()+"");
        if(mPrintViewResult.getLocationEmail1()==null)
            tvStoreEmail.setText("Email: "+"");
        else
            tvStoreEmail.setText("Email: "+mPrintViewResult.getLocationEmail1()+"");
        tvStoreGSTIN.setText("GSTIN: "+mPrintViewResult.getGstin()+"");
        tvStoreCIN.setText("CIN: "+mPrintViewResult.getCin()+"");
        tvBillNumber.setText(mPrintViewResult.getOrderNo()+"");
        tvDateTime.setText(mPrintViewResult.getOrderDate()+"");
//            if(mPrintViewResult.getCustomerName()!=null && mPrintViewResult.getCustomerName().equalsIgnoreCase(""))
//                tvCustomerDetails.setText(mPrintViewResult.getMobile()+" - "+mPrintViewResult.getCustomerName());
//            else {
//                tvCustomerDetails.setText("NA");
//            }

        if(mPrintViewResult.getMobile().equalsIgnoreCase("") || mPrintViewResult.getMobile().equalsIgnoreCase("NA")){
            mobile = "9999 9999";
        }else
            mobile= mPrintViewResult.getMobile();

        if(mPrintViewResult.getCustomerName().equalsIgnoreCase("") || mPrintViewResult.getCustomerName().equalsIgnoreCase("NA") || mPrintViewResult.getCustomerName().equalsIgnoreCase("NA NA")){
            name = "Default Customer";
        }else
            name= mPrintViewResult.getCustomerName();

        tvCustomerDetails.setText(mobile+" - "+name);
        tvItemSize.setText(itemDetails.size()+" items");
        tvTotalQty.setText("Qty "+ mPrintViewResult.getTotalQty());
        tvTotalAmount.setText(Util.getIndianNumberFormat( mPrintViewResult.getTotalAmount()));
        tvTotalDiscountPrice.setText(Util.getIndianNumberFormat(mPrintViewResult.getTotalDiscountAmount()));
        tvNetTotal.setText(Util.getIndianNumberFormat(mPrintViewResult.getNetTotalAmount()));
        tvCGSTPrice.setText(Util.getIndianNumberFormat(mPrintViewResult.getTotalCgst()));
        tvSGSTPrice.setText(Util.getIndianNumberFormat(mPrintViewResult.getTotalSgst()));
        tvRoundingOffPrice.setText(Util.getIndianNumberFormat(mPrintViewResult.getRoundingOff()));
        tvSaleValue.setText(Util.getIndianNumberFormatWithout(Double.parseDouble(mPrintViewResult.getTotalSaleAmount()))+"");
        tvTotalGSTValue.setText(Util.getIndianNumberFormat( mPrintViewResult.getTotalIgst()));
//        try {
//            if(!mPrintViewResult.getTotalPointsToRedeem().equalsIgnoreCase("")){
//                llRedeem.setVisibility(View.VISIBLE);
//                tvTotalRedeemValue.setText(Util.getIndianNumberFormat(mPrintViewResult.getTotalPointsToRedeemValue()));
//                tvTotalPoints.setText(Util.getIndianNumberFormat(mPrintViewResult.getTotalPointsToRedeem()));
//            }else {
//                llRedeem.setVisibility(View.GONE);
//            }
//        }catch (Exception e){
//
//        }
//        if(mPrintViewResult.get)
//        }catch (Exception e){
//
//        }
    }

    private void initializeComponent() {
        tvItemDetails = findViewById(R.id.tvItemDetails);
        toolbarInfoDetail = findViewById(R.id.toolbarInfoDetail);
        tvTaxVoice = findViewById(R.id.tvTaxVoice);
        tvPaymentDetails = findViewById(R.id.tvPaymentDetails);
        tvGSTSummary = findViewById(R.id.tvGSTSummary);
        tvStoreName = findViewById(R.id.tvStoreName);
        tvStoreAddress = findViewById(R.id.tvStoreAddress);
        tvStorePhone = findViewById(R.id.tvStorePhone);
        tvStoreEmail = findViewById(R.id.tvStoreEmail);
        tvStoreCIN = findViewById(R.id.tvStoreCIN);
        tvStoreGSTIN = findViewById(R.id.tvStoreGSTIN);
        tvBillNumber = findViewById(R.id.tvBillNumber);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvCustomerDetails = findViewById(R.id.tvCustomerDetails);
        tvItemSize = findViewById(R.id.tvItemSize);
        tvTotalQty = findViewById(R.id.tvTotalQty);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTotalDiscountPrice = findViewById(R.id.tvTotalDiscountPrice);
        tvTotalPoints = findViewById(R.id.tvTotalPoints);
        tvTotalRedeemValue = findViewById(R.id.tvTotalRedeemValue);
        tvNetTotal = findViewById(R.id.tvNetTotal);
        tvCGSTPrice = findViewById(R.id.tvCGSTPrice);
        tvSGSTPrice = findViewById(R.id.tvSGSTPrice);
        tvRoundingOffPrice = findViewById(R.id.tvRoundingOffPrice);
        tvSaleValue = findViewById(R.id.tvSaleValue);
        tvTotalGSTValue = findViewById(R.id.tvTotalGSTValue);
        rvItemDetails = findViewById(R.id.rvItemDetails);
        rvGST = findViewById(R.id.rvGST);
        rvPayment = findViewById(R.id.rvPayment);
        setSupportActionBar(toolbarInfoDetail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }


        toolbarInfoDetail.setTitleTextColor(getResources().getColor(R.color.white));
        //        >>>>>>>>>>>>>>>>>>>>>>>>>> rvItemDetails <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        rvItemDetails.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManagerDummy(mContext, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        rvItemDetails.setLayoutManager(mLayoutManager);

        rvItemDetails.addItemDecoration(
                new ItemDecorationAlbumColumns(mContext.getResources().getDimensionPixelSize(R.dimen.dim_5),
                        mContext.getResources().getInteger(R.integer.photo_list_preview_columns)));


        //        >>>>>>>>>>>>>>>>>>>>>>>>>> rvGST <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        rvGST.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManagerDummy(mContext, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        rvGST.setLayoutManager(mLayoutManager);
        rvGST.addItemDecoration(
                new ItemDecorationAlbumColumns(mContext.getResources().getDimensionPixelSize(R.dimen.dim_5),
                        mContext.getResources().getInteger(R.integer.photo_list_preview_columns)));



        //        >>>>>>>>>>>>>>>>>>>>>>>>>> rvPayment <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        rvPayment.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManagerDummy(mContext, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        rvPayment.setLayoutManager(mLayoutManager);
        rvPayment.addItemDecoration(
                new ItemDecorationAlbumColumns(mContext.getResources().getDimensionPixelSize(R.dimen.dim_5),
                        mContext.getResources().getInteger(R.integer.photo_list_preview_columns)));


        tvItemDetails.setPaintFlags(tvItemDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvTaxVoice.setPaintFlags(tvTaxVoice.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvPaymentDetails.setPaintFlags(tvPaymentDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvGSTSummary.setPaintFlags(tvGSTSummary.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        setAdapterItemDetails();
    }

    private void setAdapterItemDetails() {
//        try {

        mItemDetailAdapter = new ItemDetailAdapter(this, itemDetails);
        rvItemDetails.setAdapter(mItemDetailAdapter);



        mGSTSummaryAdapter = new GSTSummaryAdapter(this,gstSummaries);
        rvGST.setAdapter(mGSTSummaryAdapter);

        paymentAdapter = new PaymentAdapter(this,paymentsDetails);
        rvPayment.setAdapter(paymentAdapter);
//        }catch (Exception e){
//
//        }
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        dismissProgress();
        if(httpStatusCode == Constants.SUCCESS){
//            try {
            if (serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_RETAIL_ORDER_CENTER_PRINT)) {
                if (resultObj != null) {
                    mPrintViewResult = (PrintViewResult) resultObj;
                    if (mPrintViewResult != null) {
                        setValues();
                        setAdapterItemDetails();
                    }

                }
            }
//            }catch (Exception e){
//
//            }
        }else if (httpStatusCode == Constants.BAD_REQUEST) {
            Toast.makeText(this, this.getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();

        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            Toast.makeText(this, this.getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            Toast.makeText(this, this.getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            Toast.makeText(this, this.getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            Toast.makeText(this, this.getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();

        }
    }
}
