package quay.com.ipos.ddrsales;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import quay.com.ipos.R;

import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.RunTimePermissionActivity;
import quay.com.ipos.data.remote.RestService;

import quay.com.ipos.data.remote.model.APIError;
import quay.com.ipos.data.remote.model.DDRSubmitResponse;
import quay.com.ipos.data.remote.model.ErrorUtils;
import quay.com.ipos.ddrsales.adapter.AddressAdapter;
import quay.com.ipos.ddrsales.adapter.DDRIncoTermsAdapter;
import quay.com.ipos.ddrsales.adapter.DDRProductBatchAdapter;
import quay.com.ipos.ddrsales.model.AddressType;
import quay.com.ipos.ddrsales.model.DDR;

import quay.com.ipos.ddrsales.model.InvoiceData;
import quay.com.ipos.ddrsales.model.LogisticsData;
import quay.com.ipos.ddrsales.model.OrderModel;
import quay.com.ipos.ddrsales.model.RealmDDROrderList;
import quay.com.ipos.ddrsales.model.request.DDRDetailInfo;
import quay.com.ipos.ddrsales.model.request.DDRPaymentDetail;
import quay.com.ipos.ddrsales.model.request.DDRProductCart;
import quay.com.ipos.ddrsales.model.request.DDROrderDetail;
import quay.com.ipos.ddrsales.model.request.PODetailReq;
import quay.com.ipos.ddrsales.model.response.Address;
import quay.com.ipos.ddrsales.model.response.DDRBatch;
import quay.com.ipos.ddrsales.model.response.DDRIncoTerm;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.DateAndTimeUtil;
import quay.com.ipos.utility.NumberFormatEditText;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static quay.com.ipos.utility.DateAndTimeUtil.DATE_AND_TIME_FORMAT_SIMPLE;


public class DDRApproveInvoiceActivity extends RunTimePermissionActivity implements InitInterface, View.OnClickListener, DDRIncoTermsAdapter.OnCalculateTotalIncoTermsListener, AddressAdapter.OnItemSelectionListener {
    private static final String TAG = DDRApproveInvoiceActivity.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private TextView mDDRDetails;
    private ImageView mDDRDetailsIcon;


    //total price
    private TextView tvItemQty;
    private TextView textSGST, textCGST, tvTotalPriceBeforeGst, textDiscount, tvTotalItemPrice, textRoundingOff, textTotalInvoice;


    private Spinner textIncoTermsOthers;


    //Credit Page Invoice
    private TextView textCreditPageInvoice, textAvaCreditLimit, textCreditPageBalance;

    //Logistics
    private Spinner spinnerMode;
    private EditText editLRNumber, editTransporter, editEWayBillNo, editTruckNumber;
    private EditText editAddress, editDriverMobileNumber, editDriverName, editTrackMobileNumber, editEwayBillVal;


    private TextView textTotalIncoTerms;


    private RecyclerView recycleViewBillingAddress;
    private RecyclerView recycleViewShippingAddress;
    private RecyclerView recycleViewIncoTerms;
    private RecyclerView recycleViewProductBatch;

    private AddressAdapter adapterAddBilling;
    private AddressAdapter adapterAddShipping;

    private DDRIncoTermsAdapter adapterIncoTerms;
    private DDRProductBatchAdapter adapterProductBatch;


    private List<DDRIncoTerm> incoTermsList = new ArrayList<>();
    private List<DDRProductCart> ddrProductCartList = new ArrayList<>();

    private List<Address> addressList = new ArrayList<>();


    private String rs;



    //submit data
    private DDR mDdr;
    private RealmDDROrderList invoiceSummary;
    private Address billing;
    private Address shipping;
    private LogisticsData logisticsData = new LogisticsData();
    private List<DDRIncoTerm> ddrIncoTerms;
    private List<DDRProductCart> dDRCartDetails;
    private List<DDRPaymentDetail> dDRPaymentDetails;
    //submit data end


    private OrderModel orderModel;
    private View bottomBar;
    private MutableLiveData< DDROrderDetail> mutableLiveData = new MutableLiveData<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_ddr_approve_invoice);
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.rs = getString(R.string.Rs) + " ";
        mDdr = (DDR) getIntent().getSerializableExtra("ddr");
        orderModel = (OrderModel) getIntent().getSerializableExtra("data");

        findViewById();

        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
        
        getServerData();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    public void onSubmitAction(View view) {

        Realm realm = Realm.getDefaultInstance();
        // RealmDDROrderList realmOrderLists = realm.where(RealmDDROrderList.class).equalTo("poNumber", "P00001").findFirst();
        RealmDDROrderList realmOrderLists = realm.where(RealmDDROrderList.class).findFirst();

        //updateDataToWS(realmOrderLists);

    }

    private void onSubmitSuccess() {
        Intent intent = new Intent(activity, DDRBillPreviewActivity.class);
        intent.putExtra("ddr", mDdr);
        startActivity(intent);
    }

    public void onSaveAndCloseAction(View view) {
        Intent intent = new Intent(activity, DDROrderCenterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void findViewById() {
        bottomBar = findViewById(R.id.commonbottomBar);

        mDDRDetails = findViewById(R.id.mDDRDetails);
        mDDRDetailsIcon = findViewById(R.id.mDDRDetailsIcon);
        // total pricr detail
        tvItemQty = findViewById(R.id.tvItemQty);
        tvTotalItemPrice = findViewById(R.id.tvTotalItemPrice);
        textDiscount = findViewById(R.id.textDiscount);
        tvTotalPriceBeforeGst = findViewById(R.id.textNextTotal);
        textSGST = findViewById(R.id.textSGST);
        textCGST = findViewById(R.id.textCGST);
        textTotalInvoice = findViewById(R.id.textTotalInvoice);
        textRoundingOff = findViewById(R.id.textRoundingOff);


        //credit section
        textCreditPageInvoice = findViewById(R.id.textCreditPageInvoice);
        textAvaCreditLimit = findViewById(R.id.textAvaCreditLimit);
        textCreditPageBalance = findViewById(R.id.textCreditPageBalance);


        textTotalIncoTerms = findViewById(R.id.textTotalIncoTerms);


        //logistics
        spinnerMode = findViewById(R.id.spinnerMode);
        editTruckNumber = findViewById(R.id.editTruckNumber);
        editTransporter = findViewById(R.id.editTransporter);
        editLRNumber = findViewById(R.id.editLRNumber);
        editEWayBillNo = findViewById(R.id.editEWayBillNo);
        editAddress = findViewById(R.id.editAddress);
        editDriverMobileNumber = findViewById(R.id.editDriverMobileNumber);
        editDriverName = findViewById(R.id.editDriverName);
        editTrackMobileNumber = findViewById(R.id.editTrackMobileNumber);
        editEwayBillVal = findViewById(R.id.editEwayBillVal);

        editTruckNumber.addTextChangedListener(generalTextWatcher);
        editTransporter.addTextChangedListener(generalTextWatcher);
        editLRNumber.addTextChangedListener(generalTextWatcher);
        editEWayBillNo.addTextChangedListener(generalTextWatcher);
        editAddress.addTextChangedListener(generalTextWatcher);
        editDriverMobileNumber.addTextChangedListener(generalTextWatcher);
        editDriverName.addTextChangedListener(generalTextWatcher);
        editTrackMobileNumber.addTextChangedListener(generalTextWatcher);
        // editEwayBillVal.addTextChangedListener(generalTextWatcher);


        //billing and shipping
        recycleViewBillingAddress = findViewById(R.id.recycleViewBillingAddress);
        recycleViewBillingAddress.setLayoutManager(new LinearLayoutManager(mContext));
        adapterAddBilling = new AddressAdapter(mContext, addressList, this, AddressType.ADDRESS_TYPE_BILLING);
        recycleViewBillingAddress.setAdapter(adapterAddBilling);

        recycleViewShippingAddress = findViewById(R.id.recycleViewShippingAddress);
        recycleViewShippingAddress.setLayoutManager(new LinearLayoutManager(mContext));
        adapterAddShipping = new AddressAdapter(mContext, addressList, this, AddressType.ADDRESS_TYPE_SHIPPING);
        recycleViewShippingAddress.setAdapter(adapterAddShipping);


        textIncoTermsOthers = findViewById(R.id.textIncoTermsOthers);
        //textIncoTermsOthers.setOnClickListener(this);

        recycleViewIncoTerms = findViewById(R.id.recycleViewIncoTerms);
        recycleViewProductBatch = findViewById(R.id.recycleViewProductBatch);


        //inco terms
        recycleViewIncoTerms.setLayoutManager(new LinearLayoutManager(mContext));
        adapterIncoTerms = new DDRIncoTermsAdapter(mContext, incoTermsList, this);
        recycleViewIncoTerms.setAdapter(adapterIncoTerms);

        //batch
        recycleViewProductBatch.setLayoutManager(new LinearLayoutManager(mContext));
        adapterProductBatch = new DDRProductBatchAdapter(mContext, ddrProductCartList);
        recycleViewProductBatch.setAdapter(adapterProductBatch);


    }

    @Override
    public void applyInitValues() {
        if (mDdr != null) {
            textAvaCreditLimit.setText(rs + NumberFormatEditText.getText(mDdr.mDDRCreditLimit + ""));
            mDDRDetails.setText(mDdr.mDDRCode + " - " + mDdr.mDDRName);
            mDDRDetailsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "" + mDdr.mDDRCode + " - " + mDdr.mDDRName, Toast.LENGTH_SHORT).show();
                }
            });
        }


        setIncotermsData();



        getLiveServerData().observe(this, new Observer< DDROrderDetail>() {
            @Override
            public void onChanged(@Nullable  DDROrderDetail data) {
                try {
                    if (data != null) {

                        setData(data);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void setData(DDROrderDetail data) {
        if (data == null) {
            Util.showToast("Data is null");
            return;
        }
      //  bottomBar.setVisibility(data.isApprover==1 ? View.VISIBLE : View.INVISIBLE);

       // InvoiceData.setInitData2(data);
        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setInitData2(data);

       // setAllData();
        logisticsData = invoiceData.logisticsData;
        if (logisticsData != null) {
            setTransportMode();
        }

        List<Address> address = invoiceData.address;
        addressList.clear();
        addressList.addAll(address);

        if (addressList.size() > 0) {
            billing = shipping = addressList.get(0);
            billing.setSelected(true);
            shipping.setSelected(true);
        }

        adapterAddBilling.notifyDataSetChanged();
        adapterAddShipping.notifyDataSetChanged();


        incoTermsList.clear();
        incoTermsList.addAll(invoiceData.ddrIncoTerms);
        adapterIncoTerms.notifyDataSetChanged();

    }



    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textIncoTermsOthers:
                addOtherIncoTerms();
                break;
        }

    }


    public void onClickAddDate(View view) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editEwayBillVal.setText(DateAndTimeUtil.toCustomStringDateAndTime(calendar, DATE_AND_TIME_FORMAT_SIMPLE));
                logisticsData.eWayBillValidity = DateAndTimeUtil.toCustomStringDateAndTime(calendar, DATE_AND_TIME_FORMAT_SIMPLE);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    private void addOtherIncoTerms() {

    }

    @Override
    public void funIncoTermsTotalCount(double totalIncoTerms) {
        textTotalIncoTerms.setText(totalIncoTerms + "");
        Log.i(TAG, "Total IncoTerms:" + totalIncoTerms);
    }

    private TextWatcher generalTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before,
                                  int count) {
            //logisticsData case
          /*  if (editEwayBillVal.getText().hashCode() == charSequence.hashCode()) {
                logisticsData.eWayBillValidity = charSequence.toString();

            } else*/
            if (editTrackMobileNumber.getText().hashCode() == charSequence.hashCode()) {
                logisticsData.trackMobileNumber = charSequence.toString();

            } else if (editDriverName.getText().hashCode() == charSequence.hashCode()) {
                logisticsData.driverName = charSequence.toString();

            } else if (editDriverMobileNumber.getText().hashCode() == charSequence.hashCode()) {
                logisticsData.driverMobileNumber = charSequence.toString();

            } else if (editAddress.getText().hashCode() == charSequence.hashCode()) {
                logisticsData.address = charSequence.toString();

            } else if (editLRNumber.getText().hashCode() == charSequence.hashCode()) {
                logisticsData.lrNumber = charSequence.toString();

            } else if (editTruckNumber.getText().hashCode() == charSequence.hashCode()) {
                logisticsData.truckNumber = charSequence.toString();

            } else if (editTransporter.getText().hashCode() == charSequence.hashCode()) {
                logisticsData.transporter = charSequence.toString();

            } else if (editEWayBillNo.getText().hashCode() == charSequence.hashCode()) {
                logisticsData.eWayBillNumber = charSequence.toString();

            }

            //logistic End
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {


        }

        @Override
        public void afterTextChanged(Editable s) {

        }

    };

    private void setTransportMode() {
        ArrayAdapter partnerTypeHeading = new ArrayAdapter(activity, android.R.layout.simple_spinner_item, LogisticsData.TransportMode);
        partnerTypeHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMode.setAdapter(partnerTypeHeading);
        final List<String> stringList = Arrays.asList(LogisticsData.TransportMode);
        if (logisticsData.mode != null) {
            if (stringList.contains(logisticsData.mode)) {
                int index = stringList.indexOf(logisticsData.mode);
                spinnerMode.setSelection(index + 1);
            }
        }


        spinnerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == -1) {
                    logisticsData.mode = "";
                    return;
                }
                try {

                    String partnerType = stringList.get(i);
                    Log.i("mPartnerType", partnerType);
                    logisticsData.mode = partnerType;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        editLRNumber.setText(valueOf(logisticsData.lrNumber));
        editTransporter.setText(valueOf(logisticsData.transporter));
        editAddress.setText(valueOf(logisticsData.address));
        editEWayBillNo.setText(valueOf(logisticsData.eWayBillNumber));
        editEwayBillVal.setText(valueOf(logisticsData.eWayBillValidity));
        editTruckNumber.setText(valueOf(logisticsData.truckNumber));
        editDriverMobileNumber.setText(valueOf(logisticsData.driverMobileNumber));
        editDriverName.setText(valueOf(logisticsData.driverName));
        editTrackMobileNumber.setText(valueOf(logisticsData.trackMobileNumber));



        editLRNumber.addTextChangedListener(generalTextWatcher);
        editTransporter.addTextChangedListener(generalTextWatcher);
        editAddress.addTextChangedListener(generalTextWatcher);
        editEWayBillNo.addTextChangedListener(generalTextWatcher);
        editEwayBillVal.addTextChangedListener(generalTextWatcher);
        editTruckNumber.addTextChangedListener(generalTextWatcher);
        editDriverMobileNumber.addTextChangedListener(generalTextWatcher);
        editDriverName.addTextChangedListener(generalTextWatcher);
        editTrackMobileNumber.addTextChangedListener(generalTextWatcher);


    }


    private void setAllData() {

        Realm realm = Realm.getDefaultInstance();
        // RealmDDROrderList realmOrderLists = realm.where(RealmDDROrderList.class).equalTo("poNumber", "P00001").findFirst();
        RealmDDROrderList realmOrderLists = realm.where(RealmDDROrderList.class).findFirst();

        if (realmOrderLists == null) {
            return;
        }


        textTotalInvoice.setText(getResources().getString(R.string.Rs) + " " + (Util.indianNumberFormat(realmOrderLists.getOrderValue())));

        textCreditPageInvoice.setText(getResources().getString(R.string.Rs) + " " + (Util.indianNumberFormat(realmOrderLists.getOrderValue())));
        double balanceData = mDdr.mDDRCreditLimit - realmOrderLists.getOrderValue();
        if (balanceData < 0) {
            balanceData = 0;
        }
        textCreditPageBalance.setText(getResources().getString(R.string.Rs) + " " + (Util.indianNumberFormat(balanceData)));

        // orderDiscount.setText(getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat(realmOrderLists.getDiscountValue()));


        textDiscount.setText(getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat(realmOrderLists.getDiscountValue()));
        tvTotalItemPrice.setText("Order Value " + getResources().getString(R.string.Rs) + " " + (realmOrderLists.getOrderValue()));
        tvItemQty.setText(realmOrderLists.getQuantity() + "");
        tvTotalPriceBeforeGst.setText(getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat((realmOrderLists.getTotalValueWithoutTax() - realmOrderLists.getDiscountValue())));
        textCGST.setText("+ " + getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat(realmOrderLists.getTotalCGSTValue()) + "");
        textSGST.setText("+ " + getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat(realmOrderLists.getTotalSGSTValue()) + "");
        textRoundingOff.setText(getResources().getString(R.string.Rs) + " " + realmOrderLists.getTotalRoundingOffValue() + "");


        try {
            JSONArray arrayCart = new JSONArray(realmOrderLists.getCartDetail());
            Log.e("DDD", arrayCart.toString());
            ddrProductCartList.clear();

            for (int p = 0; p < arrayCart.length(); p++) {
                JSONObject jsonObject = arrayCart.optJSONObject(p);
                DDRProductCart cartDetailsSubmit = new DDRProductCart();
                cartDetailsSubmit.isBatch = 1;
                cartDetailsSubmit.discountValue = jsonObject.optInt("materialDiscountValue");
                cartDetailsSubmit.isFreeItem = jsonObject.optBoolean("isFreeItem");
                cartDetailsSubmit.materialCGSTRate = jsonObject.optDouble("materialCGSTRate");
                cartDetailsSubmit.materialCGSTValue = jsonObject.optDouble("materialCGSTValue");
                cartDetailsSubmit.materialSGSTRate = jsonObject.optDouble("materialSGSTRate");
                cartDetailsSubmit.materialSGSTValue = jsonObject.optDouble("materialSGSTValue");
                cartDetailsSubmit.materialIGSTRate = jsonObject.optDouble("materialIGSTRate");
                cartDetailsSubmit.materialIGSTValue = jsonObject.optDouble("materialIGSTValue");
                cartDetailsSubmit.materialName = jsonObject.optString("materialName");
                cartDetailsSubmit.materialCode = jsonObject.optString("materialCode");
                ;
                cartDetailsSubmit.materialQty = jsonObject.optInt("materialQty");
                cartDetailsSubmit.materialValue = jsonObject.optInt("materialValue");
                cartDetailsSubmit.materialUnitValue = jsonObject.optDouble("materialUnitValue");
                cartDetailsSubmit.ddrBatchDetail = new ArrayList<>();
                cartDetailsSubmit.dDRScheme = new ArrayList<>();
                ddrProductCartList.add(cartDetailsSubmit);
            }
            adapterProductBatch.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isSubmitReq = false;

   private void updateDataToWS(RealmDDROrderList realmOrderLists) {
        this.invoiceSummary = realmOrderLists;
        ddrIncoTerms = incoTermsList;
        dDRCartDetails = ddrProductCartList;
        dDRPaymentDetails = getPaymentDetail();


        if (!validateData()) {
            return;
        }
        if (isSubmitReq) {
            return;
        }
        isSubmitReq = true;
        showProgress("Please Wait...");
        DDROrderDetail DDROrderDetail = new DDROrderDetail(mDdr,
                invoiceSummary,
                billing,
                shipping,
                logisticsData,
                ddrIncoTerms,
                dDRCartDetails,
                dDRPaymentDetails);

        Log.i("contact", new Gson().toJson(DDROrderDetail));

        //  writeFile(new Gson().toJson(pcModelUpdate));


        Call<DDRSubmitResponse> call = RestService.getApiServiceSimple().DDR_SUBMIT(null);
        call.enqueue(new Callback<DDRSubmitResponse>() {
            @Override
            public void onResponse(Call<DDRSubmitResponse> call, Response<DDRSubmitResponse> response) {
                if (response.isSuccessful()) {
                    // use response data and do some fancy stuff :)
                } else {
                    // parse the response body …
                    APIError error = ErrorUtils.parseError(response);
                    // … and use it to show error information

                    // … or just log the issue like we’re doing :)
                    Log.d("error message", error.message());
                }

                try {
                    isSubmitReq = false;
                    dismissProgress();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "response.raw().request().url();" + response.raw().request().url());

                if (response.code() != 200) {
                    IPOSApplication.showToast("Code:" + response.code() + " message:" + response.message());
                    if (response.body() == null) {
                        return;
                    }
                    Log.e(TAG, "Server Code:" + response.body().statusCode + ",Server Message:" + response.body().errorDescription);
                    Toast.makeText(activity, "Code:" + response.body().statusCode + ", Message:" + response.body().errorDescription, Toast.LENGTH_SHORT).show();

                    return;
                }
                try {
                    Log.i(TAG, "Code:" + response.code() + " message:" + response.message());

                    IPOSApplication.showToast("" + response.body().message);
                    onSubmitSuccess();
                    Log.i("response", response.body().statusCode + "," + response.body().message);
                    Log.i("JsonObject", response.toString() + response.body());
                    if (response.body() != null) {
                        DDRSubmitResponse response1 = response.body();
                        if (response1 != null) {
                            Log.i("updateDataResponse", new Gson().toJson(response1));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<DDRSubmitResponse> call, Throwable t) {
                try {
                    isSubmitReq = false;
                    dismissProgress();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "ERROR OCCURED");
                Log.i("JsonObject", t.toString());
                t.printStackTrace();
            }
        });

    }

    private List<DDRPaymentDetail> getPaymentDetail() {
        List<DDRPaymentDetail> paymentDetails = new ArrayList<>();
        DDRPaymentDetail paymentDetail = new DDRPaymentDetail();
        paymentDetail.paymentType = "Credit";
        List<DDRDetailInfo> ddrDetailInfoList = new ArrayList<>();
        DDRDetailInfo ddrDetailInfo = new DDRDetailInfo();
        ddrDetailInfo.cardExpirationDate = "29-06-2018";
        ddrDetailInfo.cardLastDigits = "1234";
        ddrDetailInfo.cardPaymentAmt = 10000.0;
        ddrDetailInfo.cardTxnId = "TX10001";
        ddrDetailInfo.cardExpirationDate = "11/18";

        ddrDetailInfo.cashIsCOD = false;
        ddrDetailInfo.cashReceivedAmt = 5000.0;
        ddrDetailInfo.cashReturnAmt = 0.0;
        ddrDetailInfo.totalAmt = 500000.0;
        ddrDetailInfo.cardType = "VISA";


        ddrDetailInfoList.add(ddrDetailInfo);
        paymentDetail.dDRDetailInfo = ddrDetailInfoList;
        paymentDetails.add(paymentDetail);
        return paymentDetails;
    }


    private boolean validateData() {
        if (mDdr == null) {
            Util.showToast("DDR is required");
            return false;
        }
        if (mDdr.mDDRCode == null) {
            Util.showToast("DDR Code is required");
            return false;
        }

        //logistic validation
        if (logisticsData == null) {
            Util.showToast("Logistics is required");
            return false;
        }
        if (logisticsData == null) {
            Util.showToast("Logistics is required");
            return false;
        }
        if (logisticsData.mode == null || logisticsData.mode.isEmpty()) {
            Util.showToast("Logistics -> Mode is required");
            return false;
        }
        if (logisticsData.transporter == null || logisticsData.mode.isEmpty()) {
            Util.showToast("Logistics -> Transporter is required");
            return false;
        }
        if (logisticsData.truckNumber == null || logisticsData.truckNumber.isEmpty()) {
            Util.showToast("Logistics -> Truck Number is required");
            return false;
        }
        if (logisticsData.eWayBillNumber == null || logisticsData.eWayBillNumber.isEmpty()) {
            Util.showToast("Logistics -> E-Way Bill Number is required");
            return false;
        }
        if (logisticsData.eWayBillValidity == null || logisticsData.eWayBillValidity.isEmpty()) {
            Util.showToast("Logistics -> E-Way Bill Validity is required");
            return false;
        }
        if (logisticsData.trackMobileNumber == null || logisticsData.trackMobileNumber.isEmpty()) {
            Util.showToast("Logistics -> Track Mobile Number  is required");
            return false;
        }
        if (logisticsData.driverName == null || logisticsData.driverName.isEmpty()) {
            Util.showToast("Logistics -> Driver Name  is required");
            return false;
        }
        if (logisticsData.driverMobileNumber == null || logisticsData.driverMobileNumber.isEmpty()) {
            Util.showToast("Logistics -> Driver Mobile Number  is required");
            return false;
        }
        if (logisticsData.address == null || logisticsData.address.isEmpty()) {
            Util.showToast("Logistics -> Address is required");
            return false;
        }


        if (billing == null) {
            Util.showToast("Billing is required");
            return false;
        }
        if (billing.id == 0) {
            Util.showToast("Billing Id is required");
            return false;
        }
        if (billing.name == null || billing.name.isEmpty()) {
            Util.showToast("Billing Name is required");
            return false;
        }

        if (shipping == null) {
            Util.showToast("Shipping is required");
            return false;
        }
        if (shipping.id == 0) {
            Util.showToast("Shipping Id is required");
            return false;
        }
        if (shipping.name == null || shipping.name.isEmpty()) {
            Util.showToast("Shipping Name is required");
            return false;
        }


        //inco terms validation
        if (ddrIncoTerms == null) {
            Util.showToast("IncoTerms is required");
            return false;
        }
        if (!ddrIncoTerms.isEmpty()) {
            for (DDRIncoTerm incoTerm : ddrIncoTerms) {
                if (!incoTerm.grnPayByReceiver && !incoTerm.grnPayBySender) {
                    Util.showToast("IncoTerms Sender or Receiver is required!");
                    return false;
                }
            }

        }

        //batch validation
        if (dDRCartDetails == null || dDRCartDetails.isEmpty()) {
            Util.showToast("Product Data is required");
            return false;
        }

        if (!dDRCartDetails.isEmpty()) {
            for (DDRProductCart productCart : dDRCartDetails) {
                if (productCart.ddrBatchDetail == null || productCart.ddrBatchDetail.isEmpty()) {
                    Util.showToast("Batch is required!");
                    return false;
                }

                if (!productCart.ddrBatchDetail.isEmpty()) {
                    for (DDRBatch ddrBatch : productCart.ddrBatchDetail) {
                        if (ddrBatch.number == null || ddrBatch.number.isEmpty()) {
                            Util.showToast("Batch Number is required!");
                            return false;
                        }
                        if (ddrBatch.qty == 0) {
                            Util.showToast("Batch Quantity is required!");
                            return false;
                        }
                    }

                }

                if (productCart.materialQty != productCart.batchQty) {
                    Util.showToast("Batch Quantity Sum is not equal to Product Quantity!");
                    return false;
                }

            }

        }


        return true;

    }

    @Override
    public void onItemSelected(int position, int addressType) {
        if (addressType == AddressType.ADDRESS_TYPE_SHIPPING) {
            billing = addressList.get(position);
        }
        if (addressType == AddressType.ADDRESS_TYPE_BILLING) {
            shipping = addressList.get(position);
        }
    }


    public void addProduct(View view) {
        finish();
    }




    private void getServerData() {
        if (orderModel == null) {
            Util.showToast("PO Order is required!");
            return;
        }

        int entityCode = Prefs.getIntegerPrefs(Constants.entityCode);
        Log.i(TAG + "entityCode", entityCode + "");

        if (entityCode == 0) {
            entityCode = 1;
            Log.i(TAG, "entityCode Hardcoded if entityCode is 0" + entityCode + "");
        }
        PODetailReq poDetailReq = new PODetailReq();
        {/*{
            "employeeCode": "6000013",
                    "employeeRole": "user",
                    "businessPlaceId": "1",
                    "stateCode": "06",
                    "entityID": "1",
                    "entityType": "distributor",
                    "entityUserCode": "D000001",
                    "entityUserRole": "string",
                    "poNumber": "PO18000016",
                    "searchParam": "PO18000016",
                    "moduleType": "DDR",
                    "barCodeNumber": "NA"
        }*/
            poDetailReq.employeeCode = Prefs.getStringPrefs(Constants.employeeCode);
            poDetailReq.employeeRole = Prefs.getStringPrefs(Constants.employeeRole);
            poDetailReq.businessPlaceId = "1";
            poDetailReq.stateCode = "06";
            poDetailReq.stateCode = "06";
            poDetailReq.entityID = "1";
            poDetailReq.entityType = "distributor";
            poDetailReq.entityUserCode = "D000001";
            poDetailReq.entityUserRole = "string";

            poDetailReq.poNumber = orderModel.requestCode;
            poDetailReq.searchParam = orderModel.requestCode;
            poDetailReq.moduleType = "DDR";
            poDetailReq.barCodeNumber = "NA";


        }


        Call< DDROrderDetail> call = RestService.getApiServiceSimple().GET_ORDER_DETAIL(poDetailReq);
        call.enqueue(new Callback< DDROrderDetail>() {
            @Override
            public void onResponse(Call< DDROrderDetail> call, Response< DDROrderDetail> response) {
                Log.d(TAG, "response.raw().request().url();" + response.raw().request().url());
                if (response.code() != 200) {
                    Toast.makeText(activity, "Code:" + response.code() + ", Message:" + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                try {

                    Log.i("JsonObject", response.toString() + response.body());
                    if (response.body() != null) {
                        mutableLiveData.setValue(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call< DDROrderDetail> call, Throwable t) {
                Toast.makeText(activity, " Message:" + t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.e(TAG, "ERROR OCCURED");
                Log.i("JsonObject", t.toString());
                t.printStackTrace();
            }
        });

    }

    public MutableLiveData< DDROrderDetail> getLiveServerData() {
        return mutableLiveData;
    }


    private void setIncotermsData() {
        final ArrayList<String> strings = new ArrayList<>();
        strings.add("Options");
        strings.add("Loading");
        strings.add("Shipping");
        strings.add("Unload");
        strings.add("Toll");
        strings.add("E-Way Bill");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, strings);
        textIncoTermsOthers.setAdapter(stringArrayAdapter);
        final ArrayList<String> strings1 = new ArrayList<>();

        textIncoTermsOthers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {
                    int count = 0;
                    for (int k = 0; k < strings1.size(); k++) {
                        if (strings1.get(k).equalsIgnoreCase(strings.get(i)))
                            count = count + 1;
                    }
                    DDRIncoTerm poIncoTerms1 = new DDRIncoTerm();
                    if (!strings1.contains(strings.get(i))) {
                        poIncoTerms1.grnIncoDetail = strings.get(i);
                    } else {
                        poIncoTerms1.grnIncoDetail = strings.get(i) + " " + (count);
                    }
                    poIncoTerms1.grnPayAmount = 0;
                    poIncoTerms1.grnPayByReceiver = true;
                    poIncoTerms1.grnPayBySender = false;
                    strings1.add(strings.get(i));
                    incoTermsList.add(poIncoTerms1);
                    adapterIncoTerms.notifyDataSetChanged();
                }
                textIncoTermsOthers.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
