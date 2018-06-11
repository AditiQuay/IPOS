package quay.com.ipos.retailsales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.modal.LoginResult;
import quay.com.ipos.modal.PaymentRequest;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 30-04-2018.
 */

public class PaymentModeActivity extends BaseActivity implements View.OnClickListener, ServiceTask.ServiceResultListener {
    private ImageView imvUserAdd,imvBilling,imvPin;
    private Button btnPayCash,btnPayCard;
    Spinner spinner;
    private EditText etCashAmount,etReceivedAmount,etReturnAmount,etCardAmount,etLastDigit,etExpMonth,etExpYear,etTransID;
    private TextView tvPay,tvRedeemPoints,tvBalance;
    private LinearLayout llCash,llCard,llPoints,llPrintReceipt,llToggle;
    private CardView cvCash,cvCard,cvPoints;
    private String mTotalAmount;
    private double totalAmount;
    private Menu menu1;
    Toolbar toolbar_default;
    private String mCustomerID;
    private int mCustomerPoints;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_payment_mode);


        findViewbyId();
        getIntentValues();
        spnCardType();
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
        Intent intent=getIntent();
        if (intent!=null){
            mTotalAmount=intent.getStringExtra(Constants.TOTAL_AMOUNT);

        }
        totalAmount= IPOSApplication.totalAmount;
        tvPay.setText(getResources().getString(R.string.Rs)+" "+mTotalAmount);
        tvBalance.setText(getResources().getString(R.string.Rs)+" "+totalAmount);
        etCashAmount.setText(totalAmount+"");
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
        llToggle=findViewById(R.id.llToggle);
        spinner = (Spinner) findViewById(R.id.spnCardType);
        btnPayCash=findViewById(R.id.btnPayCash);
        btnPayCard=findViewById(R.id.btnPayCard);
        etCashAmount=findViewById(R.id.etCashAmount);
        etReceivedAmount=findViewById(R.id.etReceivedAmount);
        etReturnAmount=findViewById(R.id.etReturnAmount);
        etCardAmount=findViewById(R.id.etCardAmount);
        etExpYear=findViewById(R.id.etExpYear);
        etLastDigit=findViewById(R.id.etLastDigit);
        etExpMonth=findViewById(R.id.etExpMonth);
        etTransID=findViewById(R.id.etTransID);
        tvBalance=findViewById(R.id.tvBalance);
        imvUserAdd=findViewById(R.id.imvUserAdd);
        imvBilling=findViewById(R.id.imvBilling);
        tvRedeemPoints=findViewById(R.id.tvRedeemPoints);
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
        imvUserAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(PaymentModeActivity.this, CustomerInfoActivity.class);
                startActivityForResult(mIntent,Constants.ACT_CUSTOMER);
            }
        });
        imvBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                setResult(Constants.ACT_PAYMENT_NEW_BILLING,mIntent);
                finish();
            }
        });
        etReceivedAmount.addTextChangedListener(new MyTextWatcher());
        etCashAmount.addTextChangedListener(new MyTextWatcher());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Constants.ACT_CUSTOMER){
            if(resultCode==Constants.ACT_CUSTOMER){
                mCustomerID = data.getStringExtra(Constants.KEY_CUSTOMER);
                mCustomerPoints = data.getIntExtra(Constants.KEY_CUSTOMER_POINTS,0);
                tvRedeemPoints.setVisibility(View.VISIBLE);
                tvRedeemPoints.setText(mCustomerPoints+"");
            }
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



        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Master Card");
        categories.add("AMEX");
        categories.add("Maestro Card");
        categories.add("RuPay");
        categories.add("VISA");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }
    PaymentRequest paymentRequest = new PaymentRequest();
    double cashAmount,receivedAmt,cashReturnAmt,cardAmount;
    String lastDigit,expMonth,expYear,transID,cardType;
    String json,json1;
    LoginResult loginResult ;
    private ArrayList<PaymentRequest.PaymentDetail> arrPaymentDetail = new ArrayList<>();
    private ArrayList<PaymentRequest.DetailInfo> detailInfo = new ArrayList<>();
    boolean checkCash=false;
    boolean checkCard=false;

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id){
            case R.id.llPrintReceipt:

                if( SharedPrefUtil.getString(Constants.Login_result,"",PaymentModeActivity.this)!=null){
                    json1 =  SharedPrefUtil.getString(Constants.Login_result,"",PaymentModeActivity.this);

                    loginResult = Util.getCustomGson().fromJson(json1,LoginResult.class);
                }
                if(totalAmount==0.0){
                    if( SharedPrefUtil.getString(Constants.PAYMENT_REQUEST,"",PaymentModeActivity.this)!=null){
                        json =  SharedPrefUtil.getString(Constants.PAYMENT_REQUEST,"",PaymentModeActivity.this);

                        paymentRequest = Util.getCustomGson().fromJson(json,PaymentRequest.class);
                    }
                    if(paymentRequest!=null && loginResult!=null){
                        if(loginResult.getUserAccess()!=null){
                            paymentRequest.setBusinessPlace(loginResult.getUserAccess().getStoreName());
                            paymentRequest.setBusinessPlaceCode(loginResult.getUserAccess().getWorklocationID());
                            paymentRequest.setEmployeeCode(loginResult.getUserAccess().getRoleCode()+"");
                            paymentRequest.setEmployeeRole("user");
                            paymentRequest.setEntityID("1");

                        }
                        PaymentRequest.PaymentDetail paymentDetail=new PaymentRequest().new PaymentDetail();
                        PaymentRequest.DetailInfo mDetailInfo=new PaymentRequest().new DetailInfo();
                        if(checkCash) {
                            paymentDetail.setPaymentType("cash");
                            arrPaymentDetail.add(paymentDetail);
                            mDetailInfo.setTotalAmt(cashAmount);
                            mDetailInfo.setCashReceivedAmt(receivedAmt);
                            mDetailInfo.setCashReturnAmt(cashReturnAmt);
                            detailInfo.add(mDetailInfo);
                            paymentDetail.setDetailInfo(detailInfo);
                        }
                        if(checkCard){
                            paymentDetail.setPaymentType("card");
                            arrPaymentDetail.add(paymentDetail);
                            mDetailInfo.setCardPaymentAmt(cardAmount);
                            mDetailInfo.setCardExpirationDate(expMonth+"/"+expYear);
                            mDetailInfo.setCardLastDigits(lastDigit);
                            mDetailInfo.setCardType(cardType);
                            mDetailInfo.setCardTxnId(transID);
                            detailInfo.add(mDetailInfo);
                            paymentDetail.setDetailInfo(detailInfo);
                        }
                        paymentRequest.setCustomerID(mCustomerID);
                        paymentRequest.setPaymentDetail(arrPaymentDetail);
                        callServicePayment();
                    }
                }else {
                    Util.showToast("Balance is still left!",PaymentModeActivity.this);
                }
                break;
            case R.id.llPoints:
                if(cvPoints.getVisibility()==View.GONE) {
                    if(totalAmount>0.0)
                    cvPoints.setVisibility(View.VISIBLE);
                    else
                        Util.showToast("Balance is empty!",PaymentModeActivity.this);
                }
                else
                    cvPoints.setVisibility(View.GONE);
                break;
            case R.id.llCard:
                if(cvCard.getVisibility()==View.GONE) {
                    if (totalAmount > 0.0)
                        cvCard.setVisibility(View.VISIBLE);
                    else
                        Util.showToast("Balance is empty!", PaymentModeActivity.this);
                }
                else
                    cvCard.setVisibility(View.GONE);
                break;
            case R.id.llCash:
                if(cvCash.getVisibility()==View.GONE) {
                    if (totalAmount > 0.0)
                        cvCash.setVisibility(View.VISIBLE);
                    else
                        Util.showToast("Balance is empty!", PaymentModeActivity.this);
                }
                else
                    cvCash.setVisibility(View.GONE);
                break;
            case R.id.btnPayCash:
//                if(mCustomerID!=null){
//
//                }else {
//                    Util.showToast("Customer is not selected",PaymentModeActivity.this);
//                }
                if(!etCashAmount.getText().toString().trim().equalsIgnoreCase(""))
                    cashAmount = Double.parseDouble(etCashAmount.getText().toString());
                else
                    cashAmount = 0.0;
                if(!etReturnAmount.getText().toString().trim().equalsIgnoreCase(""))
                    cashReturnAmt = Double.parseDouble(etReturnAmount.getText().toString());
                else
                    cashReturnAmt = 0.0;
                if(!etReceivedAmount.getText().toString().trim().equalsIgnoreCase(""))
                    receivedAmt = Double.parseDouble(etReceivedAmount.getText().toString());
                else
                    receivedAmt = 0.0;


                if(cashAmount>0.0 && cashAmount < IPOSApplication.totalAmount){
                    checkCash = true;
                }else{
                    checkCash = false;
                    Util.showToast("Please enter cash amount",PaymentModeActivity.this);
                    return;
                }
                if(cashReturnAmt>=0.0){
                    checkCash = true;
                }else{
                    checkCash = false;
                    Util.showToast("Please enter return amount",PaymentModeActivity.this);
                    return;
                }

                if(receivedAmt>0.0){
                    checkCash = true;
                }else{
                    checkCash = false;
                    Util.showToast("Please enter received amount",PaymentModeActivity.this);
                    return;
                }

                if(receivedAmt>0.0 && cashReturnAmt>=0.0 && cashAmount>0.0 && cashAmount>IPOSApplication.totalAmount){
                   totalAmount=totalAmount-cashAmount;
                    tvBalance.setText(totalAmount+"");
                    btnPayCash.setVisibility(View.GONE);
                    llToggle.setVisibility(View.GONE);
                }

                break;
            case R.id.btnPayCard:

                if(!etCardAmount.getText().toString().trim().equalsIgnoreCase("")) {
                    cardAmount = Double.parseDouble(etCardAmount.getText().toString());
                    checkCard=true;
                }
                else {
                    checkCard=false;
                    Util.showToast("Please enter cash amount",PaymentModeActivity.this);
                    cardAmount = 0.0;
                }
                cardType = spinner.getSelectedItem().toString();

                if(!etLastDigit.getText().toString().trim().equalsIgnoreCase("")) {
                    lastDigit = etLastDigit.getText().toString().trim();
                    checkCard=true;
                }
                else {
                    checkCard=false;
                    Util.showToast("Please enter lastDigit", PaymentModeActivity.this);
                    lastDigit = "";
                }
                if(!etExpMonth.getText().toString().trim().equalsIgnoreCase("")) {
                    expMonth = etExpMonth.getText().toString().trim();
                    checkCard=true;
                }
                else {
                    expMonth = "";
                    checkCard=false;
                    Util.showToast("Please enter expiry month", PaymentModeActivity.this);
                }
                if(!etExpYear.getText().toString().trim().equalsIgnoreCase("")) {
                    expYear = etExpYear.getText().toString().trim();
                    checkCard=true;
                }
                else {
                    expYear = "";
                    checkCard=false;
                    Util.showToast("Please enter expiry year", PaymentModeActivity.this);
                }
                if(!etTransID.getText().toString().trim().equalsIgnoreCase("")) {
                    transID = etTransID.getText().toString().trim();
                    checkCard=true;
                }
                else {
                    transID = "";
                    checkCard=false;
                    Util.showToast("Please enter expiry year", PaymentModeActivity.this);
                }

                if(cardAmount>0.0 && !lastDigit.equalsIgnoreCase("") && !expMonth.equalsIgnoreCase("") && !expYear.equalsIgnoreCase("") && !transID.equalsIgnoreCase("")){
                    totalAmount=totalAmount-cardAmount;
                    tvBalance.setText(totalAmount+"");
                    btnPayCard.setVisibility(View.GONE);
                }
                break;
        }

    }

    private void callServicePayment() {

        ServiceTask mServiceTask = new ServiceTask();
        mServiceTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_ORDER_SUBMIT);
        mServiceTask.setParamObj(paymentRequest);
        mServiceTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mServiceTask.setListener(this);
        mServiceTask.setResultObj(null);
        mServiceTask.execute();
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        dismissProgress();
        if(serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_RETAIL_ORDER_SUBMIT)){

            if(httpStatusCode==200){
                IPOSApplication.mProductListResult.clear();
                IPOSApplication.totalAmount=0.0;
                setResult(200);
                finish();
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
                double CashAmount = Double.parseDouble(etCashAmount.getText().toString());
                double ReceivedAmount = Double.parseDouble(etReceivedAmount.getText().toString());
                double ReturnAmount = ReceivedAmount - CashAmount;
                etReturnAmount.setText(ReturnAmount+"");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
