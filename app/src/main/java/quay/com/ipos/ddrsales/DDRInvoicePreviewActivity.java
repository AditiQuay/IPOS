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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.ddrsales.adapter.AddressAdapter;
import quay.com.ipos.ddrsales.adapter.DDRIncoTermsAdapter;
import quay.com.ipos.ddrsales.adapter.DDRProductBatchAdapter;
import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.ddrsales.model.InvoiceData;
import quay.com.ipos.ddrsales.model.LogisticsData;
import quay.com.ipos.ddrsales.model.response.Address;
import quay.com.ipos.ddrsales.model.response.DDRIncoTerms;
import quay.com.ipos.ddrsales.model.response.DDTProductBatch;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.utility.NumberFormatEditText;


public class DDRInvoicePreviewActivity extends BaseActivity implements InitInterface, View.OnClickListener, DDRIncoTermsAdapter.OnCalculateTotalIncoTermsListener, AddressAdapter.OnItemSelecteListener {
    private static final String TAG = DDRInvoicePreviewActivity.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private TextView mDDRDetails;
    private ImageView mDDRDetailsIcon;
   // private TextView textBillingAddress;
   // private TextView textShippingAddress;

    private TextView tvItemQty;
    private TextView tvTotalItemPrice;


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


    private List<DDRIncoTerms> incoTermsList = new ArrayList<>();
    private List<DDTProductBatch> ddtProductBatchList = new ArrayList<>();
    private LogisticsData logisticsData;

    private List<Address> addressList = new ArrayList<>();
    private int selPostionBillAddress;
    private int selPostionShipAddress;

    private DDR mDdr;
    private String rs;

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


    public void onSubmitAction(View view) {
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

        tvItemQty = findViewById(R.id.tvItemQty);
        tvTotalItemPrice = findViewById(R.id.tvTotalItemPrice);
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
        adapterAddBilling = new AddressAdapter(mContext,addressList , this);
        recycleViewBillingAddress.setAdapter(adapterAddBilling);

        recycleViewShippingAddress = findViewById(R.id.recycleViewShippingAddress);
        recycleViewShippingAddress.setLayoutManager(new LinearLayoutManager(mContext));
        adapterAddShipping = new AddressAdapter(mContext, addressList, this);
        recycleViewShippingAddress.setAdapter(adapterAddShipping);





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
            textAvaCreditLimit.setText(rs+ NumberFormatEditText.getText(mDdr.mDDRCreditLimit+""));
            mDDRDetails.setText(mDdr.mDDRCode + " - " + mDdr.mDDRName);
            mDDRDetailsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "" + mDdr.mDDRCode + " - " + mDdr.mDDRName, Toast.LENGTH_SHORT).show();
                }
            });
        }
        logisticsData = InvoiceData.getInstance().logisticsData;
        if (logisticsData != null) {
            setTransportMode();
        }

        List<Address> address = InvoiceData.getInstance().address;
        addressList.clear();
        addressList.addAll(address);
        adapterAddBilling.notifyDataSetChanged();
        adapterAddShipping.notifyDataSetChanged();



        incoTermsList.clear();
        incoTermsList.addAll(InvoiceData.getInstance().ddrIncoTerms);
        adapterIncoTerms.notifyDataSetChanged();

        ddtProductBatchList.clear();
    /*    for (ProductSearchResult.Datum datum : InvoiceData.getInstance().cartList) {
            DDTProductBatch productBatch = new DDTProductBatch();
            productBatch.batchList = new ArrayList<>();
            productBatch.totalQty = datum.getTotalQty();
            productBatch.itemName = datum.getSProductName();
            ddtProductBatchList.add(productBatch);
        }*/
        adapterProductBatch.notifyDataSetChanged();


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

    @Override
    public void onItemSelected(View v, int position) {

    }
}
