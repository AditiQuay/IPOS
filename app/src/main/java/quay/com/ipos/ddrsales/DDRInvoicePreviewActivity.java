package quay.com.ipos.ddrsales;

import android.app.Activity;
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
import java.util.List;

import io.realm.Realm;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.base.RunTimePermissionActivity;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.data.remote.model.DDRSubmitResponse;
import quay.com.ipos.ddrsales.adapter.AddressAdapter;
import quay.com.ipos.ddrsales.adapter.DDRIncoTermsAdapter;
import quay.com.ipos.ddrsales.adapter.DDRProductBatchAdapter;
import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.ddrsales.model.InvoiceData;
import quay.com.ipos.ddrsales.model.LogisticsData;
import quay.com.ipos.ddrsales.model.RealmDDROrderList;
import quay.com.ipos.ddrsales.model.request.DDRCartDetailsSubmit;
import quay.com.ipos.ddrsales.model.request.DDRPaymentDetail;
import quay.com.ipos.ddrsales.model.request.InvoiceDataSubmit;
import quay.com.ipos.ddrsales.model.response.Address;
import quay.com.ipos.ddrsales.model.response.DDRIncoTerm;
import quay.com.ipos.ddrsales.model.response.DDTProductBatch;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.partnerConnect.PartnerConnectMain;
import quay.com.ipos.partnerConnect.model.BillnDelivery;
import quay.com.ipos.partnerConnect.model.Cheques;
import quay.com.ipos.partnerConnect.model.NewContact;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.NumberFormatEditText;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;
import quay.com.ipos.utility.ValidateUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DDRInvoicePreviewActivity extends RunTimePermissionActivity implements InitInterface, View.OnClickListener, DDRIncoTermsAdapter.OnCalculateTotalIncoTermsListener, AddressAdapter.OnItemSelecteListener {
    private static final String TAG = DDRInvoicePreviewActivity.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private TextView mDDRDetails;
    private ImageView mDDRDetailsIcon;
    // private TextView textBillingAddress;
    // private TextView textShippingAddress;


    //total price
    private TextView tvItemQty;
    private TextView textSGST, textCGST, tvTotalPriceBeforeGst, textDiscount, tvTotalItemPrice, textRoundingOff, textTotalInvoice;


    private TextView textIncoTermsOthers;


    private TextView textAvaCreditLimit;

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
    private List<DDTProductBatch> ddtProductBatchList = new ArrayList<>();

    private List<Address> addressList = new ArrayList<>();


    private String rs;


    //submit data
    private DDR mDdr;
    private RealmDDROrderList invoiceSummary;
    private Address billing;
    private Address shipping;
    private LogisticsData logisticsData;
    private List<DDRIncoTerm> ddrIncoTerms;
    private List<DDRCartDetailsSubmit> dDRCartDetails;
    private List<DDRPaymentDetail> dDRPaymentDetails;
    //submit data end

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_ddr_pre_invoice);
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        this.rs = getString(R.string.Rs) + " ";
        mDdr = (DDR) getIntent().getSerializableExtra("ddr");


        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }


    public void onSubmitAction(View view) {

        Realm realm = Realm.getDefaultInstance();
        // RealmDDROrderList realmOrderLists = realm.where(RealmDDROrderList.class).equalTo("poNumber", "P00001").findFirst();
        RealmDDROrderList realmOrderLists = realm.where(RealmDDROrderList.class).findFirst();

        updateDataToWS(realmOrderLists);

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


        textAvaCreditLimit = findViewById(R.id.textAvaCreditLimit);

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
        editEwayBillVal.addTextChangedListener(generalTextWatcher);


        //billing and shipping
        recycleViewBillingAddress = findViewById(R.id.recycleViewBillingAddress);
        recycleViewBillingAddress.setLayoutManager(new LinearLayoutManager(mContext));
        adapterAddBilling = new AddressAdapter(mContext, addressList, this,Address.ADDRESS_TYPE_BILLING);
        recycleViewBillingAddress.setAdapter(adapterAddBilling);

        recycleViewShippingAddress = findViewById(R.id.recycleViewShippingAddress);
        recycleViewShippingAddress.setLayoutManager(new LinearLayoutManager(mContext));
        adapterAddShipping = new AddressAdapter(mContext, addressList, this,Address.ADDRESS_TYPE_SHIPPING);
        recycleViewShippingAddress.setAdapter(adapterAddShipping);



        textIncoTermsOthers = findViewById(R.id.textIncoTermsOthers);
        textIncoTermsOthers.setOnClickListener(this);

        recycleViewIncoTerms = findViewById(R.id.recycleViewIncoTerms);
        recycleViewProductBatch = findViewById(R.id.recycleViewProductBatch);


        //inco terms
        recycleViewIncoTerms.setLayoutManager(new LinearLayoutManager(mContext));
        adapterIncoTerms = new DDRIncoTermsAdapter(mContext, incoTermsList, this);
        recycleViewIncoTerms.setAdapter(adapterIncoTerms);

        //batch
        recycleViewProductBatch.setLayoutManager(new LinearLayoutManager(mContext));
        adapterProductBatch = new DDRProductBatchAdapter(mContext, ddtProductBatchList);
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

        setAllData();
        logisticsData = InvoiceData.getInstance().logisticsData;
        if (logisticsData != null) {
            setTransportMode();
        }

        List<Address> address = InvoiceData.getInstance().address;
        addressList.clear();
        addressList.addAll(address);

        billing = shipping = addressList.get(0);

        adapterAddBilling.notifyDataSetChanged();
        adapterAddShipping.notifyDataSetChanged();


        incoTermsList.clear();
        incoTermsList.addAll(InvoiceData.getInstance().ddrIncoTerms);
        adapterIncoTerms.notifyDataSetChanged();

        /*ddtProductBatchList.clear();
         *//*    for (ProductSearchResult.Datum datum : InvoiceData.getInstance().cartList) {
            DDTProductBatch productBatch = new DDTProductBatch();
            productBatch.batchList = new ArrayList<>();
            productBatch.totalQty = datum.getTotalQty();
            productBatch.itemName = datum.getSProductName();
            ddtProductBatchList.add(productBatch);
        }*//*
        adapterProductBatch.notifyDataSetChanged();*/


        //  setPriceSum();


    }

   /* private void setPriceSum() {
        List<ProductSearchResult.Datum> cartList = InvoiceData.getInstance().cartList;
        int totalCount = 0;
        for (ProductSearchResult.Datum cart : cartList) {
            totalCount = totalCount + cart.getQty();
        }
        tvItemQty.setText(totalCount + "");
        tvTotalItemPrice.setText(totalCount + "");

    }*/

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
            if (editEwayBillVal.getText().hashCode() == charSequence.hashCode()) {
                logisticsData.eWayBillValidity = charSequence.toString();

            } else if (editTrackMobileNumber.getText().hashCode() == charSequence.hashCode()) {
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
    }




    private void setAllData() {

        Realm realm = Realm.getDefaultInstance();
        // RealmDDROrderList realmOrderLists = realm.where(RealmDDROrderList.class).equalTo("poNumber", "P00001").findFirst();
        RealmDDROrderList realmOrderLists = realm.where(RealmDDROrderList.class).findFirst();

        if (realmOrderLists == null) {
            return;
        }


        textTotalInvoice.setText(getResources().getString(R.string.Rs) + " " + (Util.indianNumberFormat(realmOrderLists.getOrderValue())));
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
            List<RecentOrderModal> recentOrderModalArrayList = new ArrayList<>();
            recentOrderModalArrayList.clear();
            for (int p = 0; p < arrayCart.length(); p++) {
                JSONObject jsonObject = arrayCart.optJSONObject(p);
                RecentOrderModal recentOrderModal = new RecentOrderModal();
                recentOrderModal.setTitle(jsonObject.optString("materialName"));
                recentOrderModal.setQty("" + jsonObject.optInt("materialQty"));
                recentOrderModal.setDiscountValue("" + jsonObject.optInt("materialDiscountValue"));
                recentOrderModal.setValue("" + jsonObject.optInt("materialValue"));
                recentOrderModal.setUnitprice(jsonObject.optDouble("materialUnitValue"));

                recentOrderModal.setFreeItem(jsonObject.optBoolean("isFreeItem"));
                recentOrderModalArrayList.add(recentOrderModal);

                //  recentOrdersListAdapter.notifyDataSetChanged();


                // realmOrderLists.cartDetail;

                ddtProductBatchList.clear();
                for (RecentOrderModal datum : recentOrderModalArrayList) {
                    DDTProductBatch productBatch = new DDTProductBatch();
                    productBatch.batchList = new ArrayList<>();
                    productBatch.totalQty = Integer.parseInt(datum.getQty());
                    productBatch.itemName = datum.getTitle();
                    ddtProductBatchList.add(productBatch);
                }
                adapterProductBatch.notifyDataSetChanged();


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isSubmitReq = false;

    private void updateDataToWS(RealmDDROrderList realmOrderLists) {
        this.invoiceSummary = realmOrderLists;

        if (!validateData()) {
            return;
        }
        if (isSubmitReq) {
            return;
        }
        isSubmitReq = true;
        showProgress("Please Wait...");
        InvoiceDataSubmit invoiceDataSubmit = new InvoiceDataSubmit(mDdr,
                invoiceSummary,
                billing,
                shipping,
                logisticsData,
                ddrIncoTerms,
                dDRCartDetails,
                dDRPaymentDetails);
        invoiceDataSubmit.entityID = Prefs.getIntegerPrefs(Constants.entityCode) + "";
        invoiceDataSubmit.employeeCode = Prefs.getStringPrefs(Constants.employeeCode);
        Log.i("contact", new Gson().toJson(invoiceDataSubmit));

        //  writeFile(new Gson().toJson(pcModelUpdate));


        Call<DDRSubmitResponse> call = RestService.getApiServiceSimple().DDR_SUBMIT(invoiceDataSubmit);
        call.enqueue(new Callback<DDRSubmitResponse>() {
            @Override
            public void onResponse(Call<DDRSubmitResponse> call, Response<DDRSubmitResponse> response) {

                Log.e("data", new Gson().toJson(response.errorBody()));
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
                    finish();
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

    private boolean validateData() {
        if (mDdr == null) {
            Util.showToast("DDR is required");
            return false;
        }
        if (mDdr.mDDRCode == null) {
            Util.showToast("DDR Code is required");
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
        return true;

    }

    @Override
    public void onItemSelected(View v, int position, int addressType) {
        if (addressType == Address.ADDRESS_TYPE_SHIPPING) {
           billing =  addressList.get(position);
        }
        if (addressType == Address.ADDRESS_TYPE_BILLING) {
           shipping =  addressList.get(position);
        }
    }
}
