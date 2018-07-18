package quay.com.ipos.retailsales.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.customerInfo.CustomerInfoDetailsActivity;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.customerInfo.customerInfoModal.RecentOrderList;
import quay.com.ipos.dashboard.adapter.SpinnerDropDownAdapter;
import quay.com.ipos.enums.CustomerEnum;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.modal.BillingSync;
import quay.com.ipos.modal.CustomerPointsRedeemRequest;
import quay.com.ipos.modal.CustomerPointsRedeemResult;
import quay.com.ipos.modal.LoginResult;
import quay.com.ipos.modal.OrderSubmitResult;
import quay.com.ipos.modal.PaymentRequest;
import quay.com.ipos.modal.PrintViewResult;
import quay.com.ipos.realmbean.RealmPinnedResults;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

import static quay.com.ipos.application.IPOSApplication.mCustomerEmail;
import static quay.com.ipos.application.IPOSApplication.mCustomerID;
import static quay.com.ipos.application.IPOSApplication.mCustomerNumber;
import static quay.com.ipos.application.IPOSApplication.mCustomerPoints;
import static quay.com.ipos.application.IPOSApplication.mCustomerPointsPer;
import static quay.com.ipos.application.IPOSApplication.totalpointsToRedeem;
import static quay.com.ipos.application.IPOSApplication.totalpointsToRedeemValue;

/**
 * Created by aditi.bhuranda on 30-04-2018.
 */

public class PaymentModeActivity extends BaseActivity implements View.OnClickListener, ServiceTask.ServiceResultListener {
    private ImageView imvUserAdd,imvBilling,imvPin,imvTick;
    int points=0, points1=0;
    double pointsPer=0;
    int points2=0;
    ToggleButton toggleCOD;
    private Button btnPayCash,btnPayCard,buttonSendOtp,buttonVerify,buttonRedeem,btnEditCash,btnEditCard,btnEditPoints;
    Spinner spinner;
    FrameLayout flCustomer;
    private EditText etCashAmount,etReceivedAmount,etReturnAmount,etCardAmount,etLastDigit,etExpMonth,etExpYear,etTransID,etPointToRedeem,etRedeemValue,etOTP;
    private TextView tvPay,tvRedeemPoints,tvBalance,tvAvailablePoints,tvResendOTP;
    private LinearLayout llCash,llCard,llPoints,llPrintReceipt,llToggle,llVerifyRedeem,llCashReceived;
    private CardView cvCash,cvCard,cvPoints;
    private String mTotalAmount="";
    private int totalAmount=0;
    DatabaseHandler db;
    private Menu menu1;
    Toolbar toolbar_default;
    private List<String> mCardArray = new ArrayList<>();
    CustomerModel customerModel=null;
    private ArrayList<RealmPinnedResults.Info> mInfoArrayList = new ArrayList<RealmPinnedResults.Info>();
    ArrayList<BillingSync> billingSyncs = new ArrayList<>();
    int orderNumber=0;
    boolean isContained=false;
    String billingDate_Time="";
    String billingTimeStamp="";
    double redeemValue=0;
    Context context;

    PaymentRequest paymentRequest = new PaymentRequest();
    int cashAmount,receivedAmt,cashReturnAmt,cardAmount;
    String lastDigit="",expMonth="",expYear="",transID="",cardType="";
    String json,json1;
    LoginResult loginResult ;
    private ArrayList<PaymentRequest.PaymentDetail> arrPaymentDetail = new ArrayList<>();
    private ArrayList<PaymentRequest.DetailInfo> detailInfo = new ArrayList<>();
    private ArrayList<PaymentRequest.DetailInfo> detailInfo1 = new ArrayList<>();
    private ArrayList<PaymentRequest.DetailInfo> detailInfo2 = new ArrayList<>();
    boolean checkCash=false;
    boolean checkCardAmt=false, checkCard=false,checkCardExpYear=false,checkCardMonth=false,checkCardDigit=false;
    String[] arrMonth;
    boolean sendOTP=false,sendVerify = false,sendRedeem=false,redeemed=false;
    //1 means data is synced and 0 means data is not synced
    public static final int NAME_SYNCED_WITH_SERVER = 1;
    public static final int NAME_NOT_SYNCED_WITH_SERVER = 0;
    private boolean sendCOD= false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_payment_mode);
        try {
            db = new DatabaseHandler(PaymentModeActivity.this);
            findViewbyId();
            getIntentValues();
            spnCardType();
            context = IPOSApplication.getContext();
            arrMonth = context.getResources().getStringArray(R.array.months);
        }catch (Exception e){

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu1 = menu;
        MenuItem menu12 = menu1.findItem(R.id.action_help);
        View actionView = MenuItemCompat.getActionView(menu12);

//        TextView cart_badge = actionView.findViewById(R.id.cart_badge);
//        cart_badge.setText(count + "");
        return true;
    }



    private void getIntentValues(){
        if (SharedPrefUtil.getString(Constants.PAYMENT_REQUEST,"",this)!=null) {
            json =SharedPrefUtil.getString(Constants.PAYMENT_REQUEST,"",this);

            paymentRequest = Util.getCustomGson().fromJson(json, PaymentRequest.class);
        }
        Intent intent=getIntent();
        if (intent!=null){

            mTotalAmount=intent.getStringExtra(Constants.TOTAL_AMOUNT);
            mCustomerID = intent.getStringExtra(Constants.KEY_CUSTOMER);
            if(!mCustomerID.equalsIgnoreCase("") && mCustomerID != null) {
                mCustomerPoints = intent.getIntExtra(Constants.KEY_CUSTOMER_POINTS, 0);
                IPOSApplication.mCustomerPointsPer = intent.getDoubleExtra(Constants.KEY_CUSTOMER_POINTS_PER, 0);
                IPOSApplication.mCustomerEmail = intent.getStringExtra(Constants.KEY_CUSTOMER_POINTS_EMAIL);
                IPOSApplication.mCustomerNumber = intent.getStringExtra(Constants.KEY_CUSTOMER_POINTS_NUMBER);
                tvRedeemPoints.setVisibility(View.VISIBLE);

                tvRedeemPoints.setText((mCustomerPoints + ""));
                imvTick.setVisibility(View.VISIBLE);
            }else {
                mCustomerID = "";
                mCustomerPoints=0;
                mCustomerEmail="";
                mCustomerNumber="";
                mCustomerPointsPer=0;
                tvRedeemPoints.setVisibility(View.GONE);
                imvTick.setVisibility(View.GONE);
            }
        }
        totalAmount = IPOSApplication.totalAmount;
        tvPay.setText(getResources().getString(R.string.Rs) + " " +mTotalAmount+"");
        tvBalance.setText(getResources().getString(R.string.Rs) + " " +totalAmount+"");
        etCashAmount.setText(totalAmount+"");
        etCardAmount.setText(totalAmount+"");
        if(IPOSApplication.totalpointsToRedeemValue!=0){
            setllPoints();
        }
    }

    private void findViewbyId() {
        toolbar_default = findViewById(R.id.toolbar_default);
        setSupportActionBar(toolbar_default);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar_default.setTitle(getResources().getString(R.string.toolbar_title_payment_screen));
        toolbar_default.setTitleTextColor(getResources().getColor(R.color.white));

        tvPay=findViewById(R.id.tvPay);
        llCashReceived=findViewById(R.id.llCashReceived);
        llToggle=findViewById(R.id.llToggle);
        toggleCOD=findViewById(R.id.toggleCOD);
        spinner =  findViewById(R.id.spnCardType);
        btnPayCash=findViewById(R.id.btnPayCash);
        btnPayCard=findViewById(R.id.btnPayCard);
        buttonRedeem=findViewById(R.id.buttonRedeem);
        buttonVerify=findViewById(R.id.buttonVerify);
        buttonSendOtp=findViewById(R.id.buttonSendOtp);
        btnEditPoints=findViewById(R.id.btnEditPoints);
        btnEditCard=findViewById(R.id.btnEditCard);
        btnEditCash=findViewById(R.id.btnEditCash);
        llVerifyRedeem=findViewById(R.id.llVerifyRedeem);
        etPointToRedeem=findViewById(R.id.etPointToRedeem);
        etRedeemValue=findViewById(R.id.etRedeemValue);
        etOTP=findViewById(R.id.etOTP);
        tvResendOTP=findViewById(R.id.tvResendOTP);
        tvRedeemPoints=findViewById(R.id.tvRedeemPoints);
        imvTick=findViewById(R.id.imvTick);
        tvAvailablePoints=findViewById(R.id.tvAvailablePoints);
        etCashAmount=findViewById(R.id.etCashAmount);
        etReceivedAmount=findViewById(R.id.etReceivedAmount);
        etReturnAmount=findViewById(R.id.etReturnAmount);
        etReturnAmount.setEnabled(false);
        etCardAmount=findViewById(R.id.etCardAmount);
        etExpYear=findViewById(R.id.etExpYear);
        etLastDigit=findViewById(R.id.etLastDigit);
        etExpMonth=findViewById(R.id.etExpMonth);
        etTransID=findViewById(R.id.etTransID);
        tvBalance=findViewById(R.id.tvBalance);
        imvUserAdd=findViewById(R.id.imvUserAdd);
        flCustomer=findViewById(R.id.flCustomer);
        imvBilling=findViewById(R.id.imvBilling);
        llCash=findViewById(R.id.llCash);
        llCard = findViewById(R.id.llCard);
        llPoints = findViewById(R.id.llPoints);
        llPrintReceipt = findViewById(R.id.llPrintReceipt);
        cvCash = findViewById(R.id.cvCash);
        cvCard = findViewById(R.id.cvCard);
        cvPoints = findViewById(R.id.cvPoints);
        llCash.setOnClickListener(this);
        llCard.setOnClickListener(this);
        llPoints.setOnClickListener(this);
        btnPayCash.setOnClickListener(this);
        btnPayCard.setOnClickListener(this);
        llPrintReceipt.setOnClickListener(this);
        buttonSendOtp.setOnClickListener(this);
//        }else {
//            Util.showToast("No points to redeem", getActivity() );
//        }
        tvResendOTP.setOnClickListener(this);
        buttonRedeem.setOnClickListener(this);
        buttonVerify.setOnClickListener(this);
        btnEditCard.setOnClickListener(this);
        btnEditCash.setOnClickListener(this);
        btnEditPoints.setOnClickListener(this);
        flCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent mIntent = new Intent(PaymentModeActivity.this, CustomerInfoActivity.class);
//                mIntent.putExtra("paymentMode","clicked");
//                startActivityForResult(mIntent,Constants.ACT_CUSTOMER);
                if(mCustomerNumber.equalsIgnoreCase("")) {
                    Intent mIntent = new Intent(mContext, CustomerInfoActivity.class);
                    mIntent.putExtra("paymentModeClicked", "clicked");
                    startActivityForResult(mIntent, Constants.ACT_CUSTOMER);
                } else {
                    CustomerModel customerModel = db.getCustomerMobile(mCustomerNumber);
                    Intent i = new Intent(PaymentModeActivity.this, CustomerInfoDetailsActivity.class);
                    i.putExtra("customerID", customerModel.getCustomerID());
                    i.putExtra("paymentModeClicked", "clicked");
                    i.putExtra("paymentModeSearch", "search");
                    startActivityForResult(i,Constants.ACT_CUSTOMER);
                }
            }
        });
        imvBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnPayCash.getVisibility()==View.VISIBLE && btnPayCard.getVisibility()==View.VISIBLE && buttonRedeem.getVisibility()==View.VISIBLE) {
                    Intent mIntent = new Intent();
                    setResult(Constants.ACT_PAYMENT_NEW_BILLING, mIntent);
                    finish();
                }else {
                    Util.showToast("Please finish payment procedure!",PaymentModeActivity.this);
                }
            }
        });
        etReceivedAmount.addTextChangedListener(new MyTextWatcher());
        etCashAmount.addTextChangedListener(new MyTextWatcher());
        etPointToRedeem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(!charSequence.toString().trim().equals("")) {

                    points2 = Integer.parseInt(charSequence.toString());
                    if(points1>=points2) {
                        redeemValue = points2 * pointsPer;
                        etRedeemValue.setText(redeemValue + "");
                        sendRedeem = false;
                    }else {
                        Util.showToast("Points exceeded!",PaymentModeActivity.this);
                        sendRedeem = true;
                    }
                }else {
                    etRedeemValue.setText(0 + "");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etLastDigit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().equalsIgnoreCase("")){
                    if(charSequence.toString().length()==4){
                        etExpMonth.setFocusable(true);
                        etExpMonth.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        etExpMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().equalsIgnoreCase("")){
                    if(charSequence.toString().length()==2){
                        etExpYear.setFocusable(true);
                        etExpYear.requestFocus();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        toggleCOD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sendCOD=true;
                    llCashReceived.setVisibility(View.GONE);
                    llPoints.setEnabled(false);
                    llCard.setEnabled(false);
                    llCash.setEnabled(false);
                    cvPoints.setVisibility(View.GONE);
                    cvCard.setVisibility(View.GONE);
                    llPoints.setBackgroundResource(R.drawable.rect_four_white);
                    llCard.setBackgroundResource(R.drawable.rect_four_white);
                }else {
                    sendCOD=false;
                    llCashReceived.setVisibility(View.VISIBLE);
                    llPoints.setEnabled(true);
                    llCard.setEnabled(true);
                    llCash.setEnabled(true);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.ACT_CUSTOMER){
            if(resultCode==Constants.ACT_CUSTOMER){
                mCustomerID = data.getStringExtra(Constants.KEY_CUSTOMER);
                mCustomerPoints = data.getIntExtra(Constants.KEY_CUSTOMER_POINTS,0);
                IPOSApplication. mCustomerPointsPer = data.getDoubleExtra(Constants.KEY_CUSTOMER_POINTS_PER,0);
                IPOSApplication. mCustomerEmail = data.getStringExtra(Constants.KEY_CUSTOMER_POINTS_EMAIL);
                IPOSApplication. mCustomerNumber = data.getStringExtra(Constants.KEY_CUSTOMER_POINTS_NUMBER);
                tvRedeemPoints.setVisibility(View.VISIBLE);
                tvRedeemPoints.setText(mCustomerPoints+"");
                imvTick.setVisibility(View.VISIBLE);
            }
        }
        if (!IPOSApplication.mCustomerID.equalsIgnoreCase("")) {
            tvRedeemPoints.setVisibility(View.VISIBLE);
            imvTick.setVisibility(View.VISIBLE);
            tvRedeemPoints.setText(mCustomerPoints + "");

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Util.hideSoftKeyboard(PaymentModeActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void spnCardType() {


//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerDropDownAdapter mSpinnerDropDownAdapter = new SpinnerDropDownAdapter(this,getResources().getStringArray(R.array.card_type));
        mSpinnerDropDownAdapter.setColor(true);
        // attaching data adapter to spinner
        spinner.setAdapter(mSpinnerDropDownAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    //saving the name to local storage
    private void saveBillToLocalStorage(PaymentRequest paymentRequest, int status) {
        if(Prefs.getStringPrefs(Constants.KEY_ORDER_ID)==null || Prefs.getStringPrefs(Constants.KEY_ORDER_ID)==""){
            orderNumber = 1;
            Prefs.putStringPrefs(Constants.KEY_ORDER_ID,orderNumber+"");
        }else {
            String order = Prefs.getStringPrefs(Constants.KEY_ORDER_ID);
            orderNumber = Integer.parseInt(order);

            if (SharedPrefUtil.getString("mInfoArrayList", "", mContext) != null) {
                String json2 = SharedPrefUtil.getString("mInfoArrayList", "", mContext);
                if (!json2.equalsIgnoreCase(""))
                    mInfoArrayList = Util.getCustomGson().fromJson(json2, new TypeToken<ArrayList<RealmPinnedResults.Info>>() {
                    }.getType());
                for (int i = 0 ; i < mInfoArrayList.size(); i++){
                    if(mInfoArrayList.get(i).getKey().contains(order)){
                        isContained=true;
                    }
                }
//                if(!isContained){
//                    orderNumber++;
                order = orderNumber+"";
                Prefs.putStringPrefs(Constants.KEY_ORDER_ID,Util.generateOrderFormat(orderNumber)+"");
//                }else {
//
//                    orderNumber = Integer.parseInt(order);
//                }
            }

        }

        try {
            billingDate_Time = Util.getCurrentDate() +" "+ Util.getCurrentTime();
            billingTimeStamp = Util.getCurrentTimeStamp();
//            billingSyncs = db.getAllRetailBillingOrders();
//            if(!db.checkIfBillingRecordExist(billingTimeStamp)) {

            if(!mCustomerID.equalsIgnoreCase("")){
                customerModel = db.getCustomer(mCustomerID);
                RecentOrderList recentOrder = new RecentOrderList();
                try {

                    JSONArray jsonArray = new JSONArray(customerModel.getRecentOrders());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                        recentOrder = new RecentOrderList();
                        recentOrder.setFromStoreName(jsonObject3.optString(CustomerEnum.ColoumnFromStoreName.toString().trim()));
                        recentOrder.setStoreCity(jsonObject3.optString(CustomerEnum.ColoumnStoreCity.toString().trim()));
                        recentOrder.setStoreState(jsonObject3.optString(CustomerEnum.ColoumnStoreState.toString().trim()));
                        recentOrder.setBillDate(jsonObject3.optString(CustomerEnum.ColoumnBillDate.toString().trim()));
                        recentOrder.setBillPrice(jsonObject3.optString(CustomerEnum.ColoumnBillPrice.toString().trim()));
                        recentOrders.add(recentOrder);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loginResult = Util.getCustomGson().fromJson(SharedPrefUtil.getString(Constants.Login_result,"",this),LoginResult.class);
                recentOrder= new RecentOrderList();
                recentOrder.setBillDate(billingDate_Time);
                recentOrder.setBillPrice(paymentRequest.getTotalValueWithTax()+"");
                recentOrder.setFromStoreName(loginResult.getUserAccess().getStoreName());
                recentOrder.setStoreState("");
                recentOrder.setStoreCity("");
                recentOrders.add(recentOrder);
                db.updateCustomerRecentOrders(Util.getCustomGson().toJson(recentOrders), mCustomerID);
                db.updateCustomerBillDate(billingDate_Time, mCustomerID);
                db.updateCustomerBillPrice(paymentRequest.getTotalValueWithTax()+"", mCustomerID);
            }
            for (int i =0 ; i < paymentRequest.getCartDetail().size(); i++)
            {
                int updatedQty = paymentRequest.getCartDetail().get(i).getMaterialStockAvail()-paymentRequest.getCartDetail().get(i).getMaterialQty();
                db.updateProductStock(updatedQty,paymentRequest.getCartDetail().get(i).getMaterialID());
            }
//            }
        }catch (Exception e){
            System.out.println(e);
            AppLog.e("TAG",e.getMessage());
        }

        convertPaymentToPrintViewStatus(paymentRequest,loginResult,customerModel,status,billingDate_Time,billingTimeStamp);
    }

    ArrayList<PrintViewResult.ItemDetail> itemDetails = new ArrayList<>();
    ArrayList<PrintViewResult.GstSummary> gstSummaries = new ArrayList<>();
    ArrayList<PrintViewResult.PaymentsDetail> paymentsDetails = new ArrayList<>();

    private void convertPaymentToPrintViewStatus(PaymentRequest paymentRequest, LoginResult loginResult, CustomerModel customerModel, int status, String billingDate_Time, String billingTimeStamp) {
        PrintViewResult mPrintViewResult = new PrintViewResult();
        mPrintViewResult.setBusinessPlaceName(loginResult.getUserAccess().getWorklocations().get(0).getAddress1());
        mPrintViewResult.setCin(loginResult.getUserAccess().getWorklocations().get(0).getCIN());
        if(customerModel!=null) {
            mPrintViewResult.setCustomerName(customerModel.getCustomerFirstName()+" "+customerModel.getCustomerLastName());
            mPrintViewResult.setMobile(customerModel.getCustomerPhone());
        }else {
            mPrintViewResult.setCustomerName("NA");
            mPrintViewResult.setMobile("");
        }
        mPrintViewResult.setGstin(loginResult.getUserAccess().getWorklocations().get(0).getGSTIN());
        mPrintViewResult.setLocationEmail1(loginResult.getUserAccess().getWorklocations().get(0).getLocationEmail1());
        mPrintViewResult.setLocationPhone1(loginResult.getUserAccess().getWorklocations().get(0).getLocationPhone1());
        mPrintViewResult.setLocationName(loginResult.getUserAccess().getWorklocations().get(0).getLocationName());
        mPrintViewResult.setOrderDate(billingDate_Time);
        mPrintViewResult.setOrderNo("ODR"+Util.generateOrderFormat(orderNumber)+"");
        for(int i =0 ; i < paymentRequest.getCartDetail().size(); i++) {
            PaymentRequest.CartDetail mCartDetail = paymentRequest.getCartDetail().get(i);
            PrintViewResult.ItemDetail itemDetail = new PrintViewResult().new ItemDetail();
            itemDetail.setMaterialName(mCartDetail.getMaterialName());
            itemDetail.setQuantity(mCartDetail.getMaterialQty());
            itemDetail.setIsFreeItem(mCartDetail.getIsFreeItem());
            itemDetail.setDiscountValue(mCartDetail.getDiscountValue());
            itemDetail.setUnitPrice(mCartDetail.getMaterialUnitValue());
            itemDetail.setHsnCode(mCartDetail.getHsnCode());
            itemDetail.setTotalPrice(mCartDetail.getMaterialTotalValue());
            itemDetail.setCGSTRate(mCartDetail.getMaterialCGSTRate());
            itemDetail.setCGSTValue(mCartDetail.getMaterialCGSTValue());
            itemDetail.setIGSTRate(mCartDetail.getMaterialIGSTRate());
            itemDetail.setIGSTValue(mCartDetail.getMaterialIGSTValue());
            itemDetail.setSGSTRate(mCartDetail.getMaterialSGSTRate());
            itemDetail.setSGSTValue(mCartDetail.getMaterialSGSTValue());

            PrintViewResult.GstSummary gstSummary = new PrintViewResult().new GstSummary();
            if(mCartDetail.getMaterialTotalValue()>0.0) {
                gstSummary.setTotalPrice(mCartDetail.getMaterialTotalValue());
                gstSummary.setCGSTRate(mCartDetail.getMaterialCGSTRate());
                gstSummary.setCGSTValue(mCartDetail.getMaterialCGSTValue());
                gstSummary.setSGSTRate(mCartDetail.getMaterialSGSTRate());
                gstSummary.setSGSTValue(mCartDetail.getMaterialSGSTValue());
                gstSummaries.add(gstSummary);
            }
            itemDetails.add(itemDetail);
        }
        mPrintViewResult.setItemDetails(itemDetails);
        mPrintViewResult.setGstSummary(gstSummaries);
        mPrintViewResult.setTotalQty(paymentRequest.getTotalQty()+"");
        mPrintViewResult.setTotalItem(paymentRequest.getItemQty()+"");
        mPrintViewResult.setTotalAmount(paymentRequest.getTotalValueWithoutTax()+"");
        mPrintViewResult.setTotalDiscountAmount(paymentRequest.getTotalDiscountValue()+"");
        mPrintViewResult.setTotalCgst(Util.getLastTwoDigits(paymentRequest.getTotalCGSTValue())+"");
        mPrintViewResult.setTotalSaleAmount(paymentRequest.getTotalValueWithTax()+"");
        mPrintViewResult.setTotalSgst(Util.getLastTwoDigits(paymentRequest.getTotalSGSTValue())+"");
        mPrintViewResult.setNetTotalAmount(paymentRequest.getNetTotal()+"");
        mPrintViewResult.setRoundingOff(paymentRequest.getTotalRoundingOffValue()+"");
        mPrintViewResult.setTotalIgst(paymentRequest.getTotalIGSTValue()+"");
        mPrintViewResult.setTotalPointsToRedeem(paymentRequest.getPointsToRedeem()+"");
        mPrintViewResult.setTotalPointsToRedeemValue(paymentRequest.getPointsToRedeemValue()+"");

        if(paymentRequest.getPaymentDetail().size()>0){
            for(int j = 0 ; j < paymentRequest.getPaymentDetail().size(); j++){
                PaymentRequest.PaymentDetail mPDetail = paymentRequest.getPaymentDetail().get(j);
                PrintViewResult.PaymentsDetail mPaymentsDetail = new PrintViewResult().new PaymentsDetail();
                if(mPDetail.getPaymentType().equalsIgnoreCase("cash")) {
                    for (int k = 0; k < mPDetail.getDetailInfo().size(); k++) {
                        mPaymentsDetail.setAmount(mPDetail.getDetailInfo().get(k).getTotalAmt());
                        mPaymentsDetail.setPaymentID("NA");
                        mPaymentsDetail.setModeOfPayment(mPDetail.getPaymentType());
                        mPaymentsDetail.setOrderNo("ODR"+Util.generateOrderFormat(orderNumber)+"");
                        mPaymentsDetail.setReturnValue(mPDetail.getDetailInfo().get(k).getCashReturnAmt()+"");
                    }
                }
                if(mPDetail.getPaymentType().equalsIgnoreCase("card")) {
                    for (int k = 0; k < mPDetail.getDetailInfo().size(); k++) {
                        mPaymentsDetail.setAmount(mPDetail.getDetailInfo().get(k).getCardPaymentAmt());
                        mPaymentsDetail.setPaymentID("NA");
                        mPaymentsDetail.setCardNo(mPDetail.getDetailInfo().get(k).getCardLastDigits());
                        mPaymentsDetail.setExpiryDate(mPDetail.getDetailInfo().get(k).getCardExpirationDate());
                        mPaymentsDetail.setModeOfPayment(mPDetail.getPaymentType());
                        mPaymentsDetail.setNameOnCard("");
                        mPaymentsDetail.setOrderNo("ODR"+Util.generateOrderFormat(orderNumber)+"");
                        mPaymentsDetail.setCardType(mPDetail.getDetailInfo().get(k).getCardType());
                    }
                }
                if(mPDetail.getPaymentType().equalsIgnoreCase("points")) {
                    for (int k = 0; k < mPDetail.getDetailInfo().size(); k++) {
                        mPaymentsDetail.setModeOfPayment(mPDetail.getPaymentType());
                        mPaymentsDetail.setPointsToRedeem(mPDetail.getDetailInfo().get(k).getPointsToRedeem()+"");
                        mPaymentsDetail.setPointsToRedeemValue(mPDetail.getDetailInfo().get(k).getPointsValue()+"");
                    }
                }

                paymentsDetails.add(mPaymentsDetail);
            }
        }
        mPrintViewResult.setPaymentsDetails(paymentsDetails);

        BillingSync billingSync = new BillingSync();
        billingSync.setBilling(Util.getCustomGson().toJson(paymentRequest));
        billingSync.setCustomerID(mCustomerID);
        billingSync.setOrderID("ODR"+Util.generateOrderFormat(orderNumber));
        billingSync.setOrderDateTime(billingDate_Time);
        billingSync.setOrderTimestamp(billingTimeStamp);
        billingSync.setSync(status);
        billingSync.setReceipt(Util.getCustomGson().toJson(mPrintViewResult));
        try {
//            if (!db.checkIfBillingRecordExist(billingTimeStamp)) {
            db.addRetailBilling(billingSync);

            Intent mIntent = new Intent(PaymentModeActivity.this, PrintReceiptActivity.class);
            mIntent.putExtra(Constants.RECEIPT,Util.getCustomGson().toJson(mPrintViewResult));
            mIntent.putExtra(Constants.RECEIPT_FROM,Constants.paymentMode);
            startActivity(mIntent);

//            }
        }catch (Exception e){

        }

    }

    private ArrayList<RecentOrderList> recentOrders = new ArrayList<>();
    @Override
    public void onClick(View view) {
        try{
            int id = view.getId();
            switch (id) {
                case R.id.btnEditCard:
                    btnEditCard.setVisibility(View.GONE);
                    btnPayCard.setVisibility(View.VISIBLE);
//                llCard.performClick();
                    totalAmount = totalAmount + Integer.parseInt(etCardAmount.getText().toString());
                    tvBalance.setText(getResources().getString(R.string.Rs) + " " + totalAmount);
                    etCardAmount.setEnabled(true);
                    etExpMonth.setEnabled(true);
                    etLastDigit.setEnabled(true);
                    etTransID.setEnabled(true);
                    etExpYear.setEnabled(true);
                    spinner.setEnabled(true);
                    break;
                case R.id.btnEditCash:
                    btnEditCash.setVisibility(View.GONE);
                    btnPayCash.setVisibility(View.VISIBLE);
//                llCash.performClick();
                    totalAmount = totalAmount + Integer.parseInt(etCashAmount.getText().toString());
                    tvBalance.setText(getResources().getString(R.string.Rs) + " " + totalAmount);
                    etCashAmount.setEnabled(true);
                    etReceivedAmount.setEnabled(true);
                    break;
                case R.id.btnEditPoints:
                    btnEditPoints.setVisibility(View.GONE);
//                btnP.setVisibility(View.VISIBLE);
//                llPoints.performClick();
                    cvPoints.setVisibility(View.VISIBLE);
                    llPoints.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                    tvAvailablePoints.setText(mCustomerPoints + "");
                    points = mCustomerPoints;
                    pointsPer = IPOSApplication.mCustomerPointsPer;
                    redeemValue = points * pointsPer;
                    if (totalAmount >= redeemValue) {
                        points1 = (int)(redeemValue / pointsPer);
                        redeemValue = points1 * pointsPer;
                    } else {
                        points1 = (int)(totalAmount / pointsPer);
                        redeemValue = points1 * pointsPer;
                    }
                    etPointToRedeem.setEnabled(true);
                    etRedeemValue.setEnabled(true);
                    redeemed=false;
                    sendRedeem=false;
                    sendVerify=false;
                    sendOTP=false;
                    buttonRedeem.setBackgroundResource(R.drawable.button_drawable);
                    buttonSendOtp.setBackgroundResource(R.drawable.button_drawable);
                    buttonVerify.setBackgroundResource(R.drawable.button_drawable);
                    buttonVerify.setEnabled(true);
                    buttonSendOtp.setEnabled(true);
                    buttonRedeem.setEnabled(true);
                    tvResendOTP.setEnabled(true);
                    break;
                case R.id.llPrintReceipt:

                    if (SharedPrefUtil.getString(Constants.Login_result, "", PaymentModeActivity.this) != null) {
                        json1 = SharedPrefUtil.getString(Constants.Login_result, "", PaymentModeActivity.this);

                        loginResult = Util.getCustomGson().fromJson(json1, LoginResult.class);
                    }
                    if (totalAmount == 0.0) {

                        if (paymentRequest != null && loginResult != null) {
                            if (loginResult.getUserAccess() != null) {
                                paymentRequest.setBusinessPlace(loginResult.getUserAccess().getStoreName());
                                paymentRequest.setBusinessPlaceCode(loginResult.getUserAccess().getWorklocationID());
                                paymentRequest.setEmployeeCode(loginResult.getUserAccess().getEmpCode() + "");
                                paymentRequest.setEmployeeRole(loginResult.getUserAccess().getUserRole());
                                paymentRequest.setEntityID(loginResult.getUserAccess().getEntityId());

                            }
                            PaymentRequest.PaymentDetail paymentDetail = new PaymentRequest().new PaymentDetail();
                            PaymentRequest.DetailInfo mDetailInfo = new PaymentRequest().new DetailInfo();
                            detailInfo.clear();
                            arrPaymentDetail.clear();

                            if (checkCash) {
                                detailInfo.clear();
                                paymentDetail = new PaymentRequest().new PaymentDetail();
                                paymentDetail.setPaymentType("cash");
                                mDetailInfo = new PaymentRequest().new DetailInfo();
                                mDetailInfo.setTotalAmt((double)cashAmount);
                                if (sendCOD) {
                                    mDetailInfo.setCashReceivedAmt(0.0);
                                    mDetailInfo.setCashReturnAmt(0.0);
                                } else {
                                    mDetailInfo.setCashReceivedAmt((double)receivedAmt);
                                    mDetailInfo.setCashReturnAmt((double)cashReturnAmt);
                                }
                                mDetailInfo.setCashIsCOD(sendCOD);
                                mDetailInfo.setCardPaymentAmt((double)cardAmount);
                                mDetailInfo.setCardExpirationDate(expMonth + "/" + expYear);
                                mDetailInfo.setCardLastDigits(lastDigit);
                                mDetailInfo.setCardType(cardType);
                                mDetailInfo.setCardTxnId(transID);
                                detailInfo.add(mDetailInfo);
                                paymentDetail.setDetailInfo(detailInfo);
                                arrPaymentDetail.add(paymentDetail);
                            }
                            if (checkCard) {
                                detailInfo1.clear();
                                paymentDetail = new PaymentRequest().new PaymentDetail();
                                paymentDetail.setPaymentType("card");
                                PaymentRequest.DetailInfo mDetailInfo1 = new PaymentRequest().new DetailInfo();
                                mDetailInfo1.setTotalAmt(0.0);
                                mDetailInfo1.setCashReceivedAmt(0.0);
                                mDetailInfo1.setCashReturnAmt(0.0);
                                mDetailInfo1.setCashIsCOD(false);
                                mDetailInfo1.setCardPaymentAmt((double)cardAmount);
                                mDetailInfo1.setCardExpirationDate(expMonth + "/" + expYear);
                                mDetailInfo1.setCardLastDigits(lastDigit);
                                mDetailInfo1.setCardType(cardType);
                                mDetailInfo1.setCardTxnId(transID);
                                detailInfo1.add(mDetailInfo1);
                                paymentDetail.setDetailInfo(detailInfo1);
                                arrPaymentDetail.add(paymentDetail);
                            }
                            if(redeemed){
                                detailInfo2.clear();
                                paymentDetail = new PaymentRequest().new PaymentDetail();
                                paymentDetail.setPaymentType("points");
                                PaymentRequest.DetailInfo mDetailInfo1 = new PaymentRequest().new DetailInfo();
                                mDetailInfo1.setTotalAmt(0.0);
                                mDetailInfo1.setCashReceivedAmt(0.0);
                                mDetailInfo1.setCashReturnAmt(0.0);
                                mDetailInfo1.setCashIsCOD(false);
                                mDetailInfo1.setCardPaymentAmt(0.0);
                                mDetailInfo1.setCardExpirationDate("" + "/" + "");
                                mDetailInfo1.setCardLastDigits("");
                                mDetailInfo1.setCardType("");
                                mDetailInfo1.setCardTxnId("");
                                mDetailInfo1.setPointsValue(IPOSApplication.totalpointsToRedeemValue);
                                mDetailInfo1.setPointsToRedeem(IPOSApplication.totalpointsToRedeem);
                                mDetailInfo1.setPointsOtp(etOTP.getText().toString());
                                detailInfo2.add(mDetailInfo1);
                                paymentDetail.setDetailInfo(detailInfo2);
                                arrPaymentDetail.add(paymentDetail);
                            }

                            if(mCustomerID.equalsIgnoreCase(""))
                                paymentRequest.setCustomerID("NA");
                            else {
                                paymentRequest.setCustomerID(mCustomerID);
                                if(IPOSApplication.totalpointsToRedeemValue>0.0){
                                    paymentRequest.setPointsToRedeem(IPOSApplication.totalpointsToRedeem);
                                    paymentRequest.setPointsToRedeemValue(IPOSApplication.totalpointsToRedeemValue);
                                }
                            }
                            paymentRequest.setPaymentDetail(arrPaymentDetail);
                            try {
                                CustomerModel customerModel;
                                if(mCustomerID.equalsIgnoreCase("") || mCustomerID.equalsIgnoreCase("NA")){
                                    customerModel = new CustomerModel();
                                    customerModel.setCustomerTitle("NA");
                                    customerModel.setCustomerPhone("NA");
                                    customerModel.setCustomerFirstName("NA");
                                    customerModel.setCustomerLastName("NA");
                                    customerModel.setCustomerGender("NA");
                                    paymentRequest.setCustomerJson(customerModel);
                                }else {

                                    String loyalty = "";
                                    int points = 0;
                                    customerModel = db.getCustomer(mCustomerID);
                                    if (customerModel != null) {
                                        if (!customerModel.getCustomerPoints().equalsIgnoreCase("")) {
                                            loyalty = customerModel.getCustomerPoints();
                                            points = (int)(paymentRequest.getOrderLoyality() + Integer.parseInt(loyalty))-(int)paymentRequest.getPointsToRedeem();
                                            db.updateCustomerPoints(points + "", mCustomerID);
                                        } else {
                                            db.updateCustomerPoints((paymentRequest.getOrderLoyality() - paymentRequest.getPointsToRedeem()) + "", mCustomerID);
                                        }
                                    }

//                            db.updateCustomerPoints(paymentRequest.getOrderLoyality().toString(),mCustomerID);
                                    paymentRequest.setPointsToRedeem(IPOSApplication.totalpointsToRedeem);
                                    paymentRequest.setPointsToRedeemValue(IPOSApplication.totalpointsToRedeemValue);
                                }
                                customerModel = db.getCustomerMobile(mCustomerNumber);
                                paymentRequest.setCustomerJson(customerModel);
                            }catch (Exception e){

                            }
                            if (Util.isConnected())
                                callServicePayment();
                            else {
//                            db.deleteTable(DatabaseHandler.TABLE_RETAIL_BILLING);
                                saveBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
                                IPOSApplication.mProductListResult.clear();
                                IPOSApplication.totalAmount = 0;
                                setResult(200);
                                Util.showToast("Order Saved Successfully", IPOSApplication.getContext());
                                finish();
                            }
                        }
                    } else {
                        Util.showToast("Balance is still left!", PaymentModeActivity.this);
                    }
                    break;
                case R.id.llPoints:
                    if (cvPoints.getVisibility() == View.GONE) {
                        if (totalAmount > 0.0) {
                            if (!mCustomerID.equalsIgnoreCase("")) {
                                if(!mCustomerNumber.equalsIgnoreCase("0000000000")) {
                                    if (mCustomerPoints > 0) {
                                        setllPoints();

                                    }
                                    else
                                        Util.showToast(getString(R.string.redeem_customer_points_not_sufficient), mContext);
                                } else
                                    Util.showToast(getString(R.string.redeem_customer_phone_not_authorised), mContext);
                            } else {
                                Util.showToast("Please select customer first.", PaymentModeActivity.this);
                            }
                        } else
                            Util.showToast("Balance is empty!", PaymentModeActivity.this);


                    } else {
                        if (buttonRedeem.getVisibility() == View.VISIBLE) {
                            cvPoints.setVisibility(View.GONE);
                            llPoints.setBackgroundResource(R.drawable.rect_four_white);
                        }
                    }
                    if(cvCash.getVisibility()==View.GONE && cvCard.getVisibility()==View.GONE && cvPoints.getVisibility()==View.GONE){
                        cvPoints.setVisibility(View.VISIBLE);
                        llPoints.setBackgroundResource(R.drawable.button_rectangle_light_gray);

                    }
                    if(redeemed){
                        cvPoints.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.llCard:
                    if (cvCard.getVisibility() == View.GONE) {
                        if (totalAmount > 0.0) {
                            cvCard.setVisibility(View.VISIBLE);
                            llCard.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                        } else
                            Util.showToast("Balance is empty!", PaymentModeActivity.this);
                    } else {
                        if (btnPayCard.getVisibility() == View.VISIBLE) {
                            cvCard.setVisibility(View.GONE);
                            llCard.setBackgroundResource(R.drawable.rect_four_white);
                        }
                    }
                    if(cvCash.getVisibility()==View.GONE && cvCard.getVisibility()==View.GONE && cvPoints.getVisibility()==View.GONE){
                        cvCard.setVisibility(View.VISIBLE);
                        llCard.setBackgroundResource(R.drawable.button_rectangle_light_gray);

                    }
                    if(btnPayCard.getVisibility()==View.GONE){
                        cvCard.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.llCash:
                    if (cvCash.getVisibility() == View.GONE) {
                        if (totalAmount > 0.0) {
                            cvCash.setVisibility(View.VISIBLE);
                            llCash.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                        } else {
                            Util.showToast("Balance is empty!", PaymentModeActivity.this);
                        }
                    } else {
                        if (btnPayCash.getVisibility() == View.VISIBLE) {
                            cvCash.setVisibility(View.GONE);
                            llCash.setBackgroundResource(R.drawable.rect_four_white);
                        }
                    }
                    if(cvCash.getVisibility()==View.GONE && cvCard.getVisibility()==View.GONE && cvPoints.getVisibility()==View.GONE){
                        cvCash.setVisibility(View.VISIBLE);
                        llCash.setBackgroundResource(R.drawable.button_rectangle_light_gray);

                    }
                    if(btnPayCash.getVisibility()==View.GONE){
                        cvCash.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.btnPayCash:
//                if(mCustomerID!=null){
//
//                }else {
//                    Util.showToast("Customer is not selected",PaymentModeActivity.this);
//                }
                    if (sendCOD) {
                        if (!etCashAmount.getText().toString().trim().equalsIgnoreCase(""))
                            cashAmount = Integer.parseInt(etCashAmount.getText().toString());
                        else
                            cashAmount = 0;
                        llCashReceived.setVisibility(View.GONE);

                        if (cashAmount > 0.0) {
                            if (cashAmount <= IPOSApplication.totalAmount) {
                            } else {
                                Util.showToast("Please enter correct cash amount", PaymentModeActivity.this);
                            }
                        } else {
                            Util.showToast("Please enter cash amount", PaymentModeActivity.this);
                            return;
                        }

                        if (cashAmount > 0.0 && cashAmount <= IPOSApplication.totalAmount) {
                            totalAmount = totalAmount - cashAmount;
                            tvBalance.setText(getResources().getString(R.string.Rs) + " " +totalAmount+"");
                            btnPayCash.setVisibility(View.GONE);
                            llToggle.setVisibility(View.VISIBLE);
//                        toggleCOD.setEnabled(false);
                            checkCash = true;
                            etCashAmount.setEnabled(false);
                            if (!checkCard) {
                                etCardAmount.setText(totalAmount + "");
                                cardAmount = totalAmount;
                            }
                            btnEditCash.setVisibility(View.VISIBLE);
                        } else {
                            checkCash = false;
                            Util.showToast("Please enter valid cash details", PaymentModeActivity.this);
                        }

                    }else {
                        llCashReceived.setVisibility(View.VISIBLE);
                        if (!etCashAmount.getText().toString().trim().equalsIgnoreCase(""))
                            cashAmount = Integer.parseInt(etCashAmount.getText().toString());
                        else
                            cashAmount = 0;
                        if (!etReturnAmount.getText().toString().trim().equalsIgnoreCase(""))
                            cashReturnAmt = Integer.parseInt(etReturnAmount.getText().toString());
                        else
                            cashReturnAmt = 0;
                        if (!etReceivedAmount.getText().toString().trim().equalsIgnoreCase(""))
                            receivedAmt = Integer.parseInt(etReceivedAmount.getText().toString());
                        else
                            receivedAmt = 0;


                        if (cashAmount > 0.0) {
                            if (cashAmount <= IPOSApplication.totalAmount) {
                            } else {
                                Util.showToast("Please enter correct cash amount", PaymentModeActivity.this);
                            }
                        } else {
                            Util.showToast("Please enter cash amount", PaymentModeActivity.this);
                            return;
                        }

                        if (cashReturnAmt >= 0.0) {
                        } else {
                            Util.showToast("Please enter return amount", PaymentModeActivity.this);
                            return;
                        }

                        if (receivedAmt > 0.0) {
                            if (receivedAmt >= cashAmount) {
                            } else {
                                Util.showToast("Please enter correct received amount", PaymentModeActivity.this);
                            }
                        } else {
                            Util.showToast("Please enter received amount", PaymentModeActivity.this);
                            return;
                        }

                        if (receivedAmt > 0.0 && receivedAmt >= cashAmount && cashReturnAmt >= 0.0 && cashAmount > 0.0 && cashAmount <= IPOSApplication.totalAmount) {
                            totalAmount = totalAmount - cashAmount;
                            tvBalance.setText(getResources().getString(R.string.Rs) + " " +totalAmount+"");
                            btnPayCash.setVisibility(View.GONE);
                            llToggle.setVisibility(View.GONE);
                            etReceivedAmount.setEnabled(false);
                            checkCash = true;
                            etCashAmount.setEnabled(false);
                            if (!checkCard) {
                                etCardAmount.setText(totalAmount + "");
                                cardAmount = totalAmount;
                            }
                            btnEditCash.setVisibility(View.VISIBLE);
                        } else {
                            checkCash = false;
                            Util.showToast("Please enter cash valid details", PaymentModeActivity.this);
                        }
                    }
                    break;
                case R.id.btnPayCard:

                    if(!etCardAmount.getText().toString().trim().equalsIgnoreCase("")) {
                        cardAmount = Integer.parseInt(etCardAmount.getText().toString());
                        if(cardAmount <= IPOSApplication.totalAmount) {
                            checkCardAmt = true;
                        }else {
                            checkCardAmt = false;
                            cardAmount = 0;
                            Util.showToast("Please enter correct card amount",PaymentModeActivity.this);
                        }
                    }
                    else {
                        checkCardAmt=false;
                        Util.showToast("Please enter cash amount",PaymentModeActivity.this);
                        cardAmount = 0;
                    }



                    if(spinner.getSelectedItem()!=null && !spinner.getSelectedItem().toString().equalsIgnoreCase(""))
                        if(spinner.getSelectedItemPosition()!=0) {
                            mCardArray = Arrays.asList(getResources().getStringArray(R.array.card_type));
                            cardType = mCardArray.get(spinner.getSelectedItemPosition());
                        }
                        else
                            Util.showToast("Please select Card type", PaymentModeActivity.this);
                    else {
                        Util.showToast("Please select Card type", PaymentModeActivity.this);

                    }
                    if(!etLastDigit.getText().toString().trim().equalsIgnoreCase("")) {
                        lastDigit = etLastDigit.getText().toString().trim();
                        if(lastDigit.length()==4){
                            checkCardDigit=true;
                        }else {
                            Util.showToast("Please enter correct last 4-digit",PaymentModeActivity.this);
                            checkCardDigit=false;
                        }

                    }
                    else {
                        checkCardDigit=false;
                        Util.showToast("Please enter last 4-digit", PaymentModeActivity.this);
                        lastDigit = "";
                    }

                    if(!etExpMonth.getText().toString().trim().equalsIgnoreCase("")) {
                        expMonth = etExpMonth.getText().toString().trim();
                        for(int i = 0 ; i < arrMonth.length; i++){
                            if(arrMonth[i].equalsIgnoreCase(expMonth)){
                                checkCardMonth =true;
                            }
                        }
                        if(!checkCardMonth){
                            Util.showToast("Please enter valid expiry month", PaymentModeActivity.this);
                        }
                    }
                    else {
                        expMonth = "";
                        checkCardMonth=false;
                        Util.showToast("Please enter expiry month", PaymentModeActivity.this);
                    }


                    if(!etExpYear.getText().toString().trim().equalsIgnoreCase("")) {
                        expYear = etExpYear.getText().toString().trim();
                        if(Util.checkExpiryYear(expYear,expMonth)){
                            checkCardExpYear=true;
                        }else {
                            Util.showToast("Please enter correct expiry year", PaymentModeActivity.this);
                            checkCardExpYear = false;
                        }

                    }
                    else {
                        expYear = "";
                        checkCardExpYear=false;
                        Util.showToast("Please enter expiry year", PaymentModeActivity.this);
                    }


                    if(!etTransID.getText().toString().trim().equalsIgnoreCase("")) {
                        transID = etTransID.getText().toString().trim();
                    }
                    else {
                        transID = "";
                        Util.showToast("Please enter expiry year", PaymentModeActivity.this);
                    }

                    if(checkCardAmt && checkCardDigit && checkCardMonth && checkCardExpYear && !transID.equalsIgnoreCase("")){
                        totalAmount=totalAmount-cardAmount;
                        tvBalance.setText(getResources().getString(R.string.Rs) + " " +totalAmount+"");
                        btnPayCard.setVisibility(View.GONE);
                        etCardAmount.setEnabled(false);
                        etExpMonth.setEnabled(false);
                        etExpYear.setEnabled(false);
                        spinner.setEnabled(false);
                        etTransID.setEnabled(false);
                        etLastDigit.setEnabled(false);
                        if(!checkCash){
                            etCashAmount.setText(totalAmount+"");
                            cashAmount=totalAmount;
                        }
                        btnEditCard.setVisibility(View.VISIBLE);
                        checkCard=true;
                    }else {
                        checkCard=false;
//                    Util.showToast("Please enter card valid details",PaymentModeActivity.this);
                    }
                    break;
                case R.id.buttonSendOtp:
                    if(totalAmount>0) {
                        if (!mCustomerEmail.trim().equalsIgnoreCase("")) {
                            if (!etPointToRedeem.getText().toString().equalsIgnoreCase("")) {
                                if (!sendOTP)
                                    if (points1 >= points2) {
                                        points1 = points2;
                                        totalpointsToRedeem = points1;
                                        totalpointsToRedeemValue = redeemValue;
                                        if (points1 > 0) {
                                            if (redeemValue > 0) {
                                                sendOTP = true;
                                                etPointToRedeem.setEnabled(false);
                                                etRedeemValue.setEnabled(false);
                                                tvResendOTP.setVisibility(View.VISIBLE);
                                                sendOTPtoServer();
                                            } else
                                                Util.showToast("No points to redeem", PaymentModeActivity.this);
                                        } else {
                                            Util.showToast("No points to redeem", PaymentModeActivity.this);
                                        }
                                    } else {
                                        Util.showToast("Points exceeded!", PaymentModeActivity.this);
                                    }
                            } else {
                                Util.showToast("Enter points to redeem", PaymentModeActivity.this);
                            }
                        } else {
                            Util.showToast(getString(R.string.redeem_customer_email_not_authorised), PaymentModeActivity.this);
                        }
                    }else
                        Util.showToast("Balance is empty", PaymentModeActivity.this);
                    break;
                case R.id.tvResendOTP:
                    if(totalAmount>0) {
                        if(!mCustomerEmail.trim().equalsIgnoreCase("")) {
                            if(!sendVerify){
                                if(redeemValue>0) {
                                    if(redeemValue<=points) {
                                        sendOTP = true;
                                        etPointToRedeem.setEnabled(false);
                                        etRedeemValue.setEnabled(false);
                                        sendOTPtoServer();
                                    }
                                    else {
                                        Util.showToast("Redeem points exceeds", PaymentModeActivity.this);
                                    }
                                }else
                                    Util.showToast("No points to redeem", PaymentModeActivity.this );
                            }else {
                                Util.showToast("ID already verified!", PaymentModeActivity.this );
                            }
                        } else {
                            Util.showToast(getString(R.string.redeem_customer_email_not_authorised), PaymentModeActivity.this);
                        }
                    }else
                        Util.showToast("Balance is empty", PaymentModeActivity.this);
                    break;
                case R.id.buttonVerify:
                    if(!sendVerify)
                        if(redeemValue>0){
                            sendVerify = true;
                            if(!etOTP.getText().toString().trim().equalsIgnoreCase(""))
                                if(etOTP.getText().toString().trim().length()>=4) {
                                    sendOTPtoServer();
                                    sendVerify = true;
                                }
                                else
                                    Util.showToast("Please enter valid otp", PaymentModeActivity.this );
                            else
                                Util.showToast("Please enter otp", PaymentModeActivity.this );
                        }else {
                            Util.showToast("No points to redeem", PaymentModeActivity.this );
                        }
                    break;
                case R.id.buttonRedeem:
                    if(!sendVerify) {
                        if (redeemValue > 0) {
                            sendRedeem = true;
                            if (sendRedeem) {
                                sendRedeem = false;
                                redeemed = true;
                                Util.showToast("Redeem points successfully", PaymentModeActivity.this);
                                llVerifyRedeem.setVisibility(View.VISIBLE);
                                IPOSApplication.totalpointsToRedeem = points1;
                                IPOSApplication.totalpointsToRedeemValue = redeemValue;
//                            mRedeemListener.redeem(points1,redeemValue);
//                            f.dismiss();
                                totalAmount = (int)((double) totalAmount -  redeemValue);
                                tvBalance.setText(getResources().getString(R.string.Rs) + " " +totalAmount+"");
                                buttonRedeem.setVisibility(View.GONE);
                                if(!checkCash){
                                    etCashAmount.setText(totalAmount+"");
                                    cashAmount=totalAmount;
                                }
                                if(!checkCard){
                                    etCardAmount.setText(totalAmount+"");
                                    cardAmount=totalAmount;
                                }
                                btnEditPoints.setVisibility(View.VISIBLE);

                            } else {
                                sendRedeem = false;
                            }
                        } else {
                            redeemed=false;
                        }
                    }else {
                        Util.showToast("not verified", PaymentModeActivity.this);
                    }
                    break;

            }
            if(btnPayCard.getVisibility()==View.GONE || sendRedeem==true){
//            toggleCOD.setEnabled(false);
            }else {
//            toggleCOD.setEnabled(true);
            }
        }catch (Exception e){

        }
    }

    private void setllPoints() {

        if (cvPoints.getVisibility() == View.GONE) {
            if (totalAmount > 0.0) {
                cvPoints.setVisibility(View.VISIBLE);
                llPoints.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                tvAvailablePoints.setText(mCustomerPoints + "");
                if (IPOSApplication.totalpointsToRedeemValue == 0) {
                    points = mCustomerPoints;
                    pointsPer = IPOSApplication.mCustomerPointsPer;
                    redeemValue = points * pointsPer;
                    if (totalAmount >= redeemValue) {
                        points1 = (int)(redeemValue / pointsPer);
                        redeemValue = points1 * pointsPer;
                    } else {
                        points1 = (int)(totalAmount / pointsPer);
                        redeemValue = points1 * pointsPer;
                    }
//                            if(totalAmount>)
//                    etPointToRedeem.setText("");
//                    etRedeemValue.setText(redeemValue + "");
                    etPointToRedeem.setEnabled(true);
                    etRedeemValue.setEnabled(true);
                    redeemed=false;
                } else {

                    etPointToRedeem.setText(IPOSApplication.totalpointsToRedeem + "");
                    etRedeemValue.setText(IPOSApplication.totalpointsToRedeemValue + "");
                    buttonSendOtp.setEnabled(false);
                    etPointToRedeem.setEnabled(false);
                    etRedeemValue.setEnabled(false);
                    redeemed=true;
                    llVerifyRedeem.setVisibility(View.GONE);
                    buttonSendOtp.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                    Util.showToast("Redeem already applied!", PaymentModeActivity.this);
                }
            } else
                Util.showToast("Balance is empty!", PaymentModeActivity.this);
        } else {
            if (buttonRedeem.getVisibility() == View.VISIBLE) {
                cvPoints.setVisibility(View.GONE);
                llPoints.setBackgroundResource(R.drawable.rect_four_white);
            }

        }
    }

    private void sendOTPtoServer() {
        CustomerPointsRedeemRequest customerPointsRedeemRequest = new CustomerPointsRedeemRequest();
        customerPointsRedeemRequest.setCustomerId(mCustomerID);
        customerPointsRedeemRequest.setEmailId(IPOSApplication.mCustomerEmail);
        customerPointsRedeemRequest.setEmployeeCode(Prefs.getStringPrefs(Constants.employeeCode.trim()));
        customerPointsRedeemRequest.setPointsRedeemValue(redeemValue);
        customerPointsRedeemRequest.setPointsToRedeem(points1);
        if(sendVerify)
            customerPointsRedeemRequest.setRequestOTP(etOTP.getText().toString());
        else
            customerPointsRedeemRequest.setRequestOTP("");
        showProgressDialog(R.string.please_wait);
        ServiceTask mServiceTask = new ServiceTask();
        if(sendVerify)
            mServiceTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_ValidateCustomerPointsRedeemRequest);
        else
            mServiceTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_CustomerPointsRedeemRequest);
        mServiceTask.setParamObj(customerPointsRedeemRequest);
        mServiceTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mServiceTask.setListener(this);
        mServiceTask.setResultType(CustomerPointsRedeemResult.class);
        mServiceTask.execute();
    }

    private void callServicePayment() {
        AppLog.e(PrintReceiptActivity.class.getSimpleName(),"paymentRequest Final : "+Util.getCustomGson().toJson(paymentRequest));
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
        dismissProgress();
        if(serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_RETAIL_ORDER_SUBMIT)) {
            try{
                if (httpStatusCode == Constants.SUCCESS) {
                    if (resultObj != null) {
                        OrderSubmitResult mOrderSubmitResult = (OrderSubmitResult) resultObj;
                        if (mOrderSubmitResult.getError() == 200) {
                            IPOSApplication.mProductListResult.clear();
                            IPOSApplication.totalAmount = 0;
                            setResult(200);
                            Util.showToast(mOrderSubmitResult.getMessage(), IPOSApplication.getContext());
                            saveBillToLocalStorage(paymentRequest, NAME_SYNCED_WITH_SERVER);
//                            Util.showToast("Order Sent Successfully", IPOSApplication.getContext());
                            finish();
                        } else {
                            saveBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
                            Util.showToast(mOrderSubmitResult.getErrorDescription(), IPOSApplication.getContext());
                            IPOSApplication.mProductListResult.clear();
                            IPOSApplication.totalAmount = 0;
                            setResult(200);
                            Util.showToast("Order Saved Successfully", IPOSApplication.getContext());
                            finish();
                        }
                    }
                }else if (httpStatusCode == Constants.BAD_REQUEST) {
                    saveBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
                    Toast.makeText(context, context.getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();

                    Util.showToast("Order Saved Successfully", IPOSApplication.getContext());
                } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
                    saveBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
                    Toast.makeText(context, context.getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                    Util.showToast("Order Saved Successfully", IPOSApplication.getContext());
                } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
                    saveBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
                    Toast.makeText(context, context.getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
                    Util.showToast("Order Saved Successfully", IPOSApplication.getContext());
                } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
                    saveBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
                    Toast.makeText(context, context.getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
                    Util.showToast("Order Saved Successfully", IPOSApplication.getContext());
                } else if (httpStatusCode == Constants.CONNECTION_OUT) {
                    saveBillToLocalStorage(paymentRequest, NAME_NOT_SYNCED_WITH_SERVER);
                    Toast.makeText(context, context.getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
                    Util.showToast("Order Saved Successfully", IPOSApplication.getContext());
                }

                IPOSApplication.mProductListResult.clear();
                IPOSApplication.totalAmount = 0;
                setResult(200);
//                            Util.showToast(mOrderSubmitResult.getMessage(), IPOSApplication.getContext());
                finish();
                IPOSApplication.mCustomerID="";
                mCustomerPoints=0;
                mCustomerEmail="";
                mCustomerNumber="";
                mCustomerPointsPer=0;
            }catch (Exception e){
                System.out.println(e);
            }
        }  if(serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_RETAIL_CustomerPointsRedeemRequest)){
            if(resultObj!=null) {
                CustomerPointsRedeemResult customerPointsRedeemResult = (CustomerPointsRedeemResult) resultObj;
                if(customerPointsRedeemResult!=null){
                    if(customerPointsRedeemResult.getError()==200){
                        Util.showToast(customerPointsRedeemResult.getMessage(), PaymentModeActivity.this);
                        if(sendOTP) {
                            sendOTP=false;
                            Util.showToast(customerPointsRedeemResult.getMessage(), PaymentModeActivity.this);
                            llVerifyRedeem.setVisibility(View.VISIBLE);

                            buttonVerify.setVisibility(View.VISIBLE);
                            buttonVerify.setBackgroundResource(R.drawable.button_drawable);
                            buttonVerify.setEnabled(true);

                            buttonRedeem.setVisibility(View.INVISIBLE);
                            buttonRedeem.setEnabled(false);
                            buttonRedeem.setBackgroundResource(R.drawable.button_rectangle_light_gray);

                            buttonSendOtp.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                            buttonSendOtp.setEnabled(false);
                        }else {
                            sendOTP=false;
                        }
                    }else {
                        Util.showToast(customerPointsRedeemResult.getErrorDescription(), PaymentModeActivity.this);
                    }

                }
            }
        }else  if(serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_RETAIL_ValidateCustomerPointsRedeemRequest))
        {
            if(resultObj!=null) {
                CustomerPointsRedeemResult customerPointsRedeemResult = (CustomerPointsRedeemResult) resultObj;
                if (customerPointsRedeemResult != null) {
                    if (customerPointsRedeemResult.getError() == 200) {
                        if(customerPointsRedeemResult.getIsValid()) {
                            if (sendVerify) {
                                sendVerify = false;
                                Util.showToast(customerPointsRedeemResult.getMessage(), PaymentModeActivity.this);
                                llVerifyRedeem.setVisibility(View.VISIBLE);

                                buttonVerify.setVisibility(View.VISIBLE);
                                buttonVerify.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                                buttonVerify.setEnabled(false);

                                tvResendOTP.setEnabled(false);
                                buttonRedeem.setVisibility(View.VISIBLE);
                                buttonRedeem.setEnabled(true);
                                buttonRedeem.setBackgroundResource(R.drawable.button_drawable);

                                buttonSendOtp.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                                buttonSendOtp.setEnabled(false);
                            } else {
                                sendVerify = false;
                            }
                        } else {
                            Util.showToast(customerPointsRedeemResult.getMessage(), PaymentModeActivity.this);
                            sendVerify = false;
                        }
                    }
                }
            }
        }
    }

    private class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            try {
                if(btnPayCash.getVisibility()==View.VISIBLE) {
                    int CashAmount = Integer.parseInt(etCashAmount.getText().toString());
                    int ReceivedAmount = Integer.parseInt(etReceivedAmount.getText().toString());
                    int ReturnAmount = 0;
                    if (CashAmount <= totalAmount) {
                        if (ReceivedAmount >= CashAmount)
                            ReturnAmount = ReceivedAmount - CashAmount;

                    }
                    etReturnAmount.setText(ReturnAmount + "");
                }
                if(btnPayCash.getVisibility()==View.GONE){
                    if(totalAmount>0.0) {
                        etCardAmount.setText(totalAmount + "");
                        cardAmount = totalAmount;
                    }
                }

                if(btnPayCard.getVisibility()==View.GONE){
                    if(totalAmount>0.0) {
                        etCashAmount.setText(totalAmount + "");
                        cashAmount = totalAmount;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
