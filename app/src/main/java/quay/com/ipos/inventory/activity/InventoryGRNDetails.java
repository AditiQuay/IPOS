package quay.com.ipos.inventory.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.InventoryAttachmentAdapter;
import quay.com.ipos.inventory.adapter.InventoryGrnInccoAdapter;
import quay.com.ipos.inventory.adapter.InventoryGrnItemsListAdapter;
import quay.com.ipos.inventory.adapter.MilestonePOListAdapter;
import quay.com.ipos.inventory.adapter.TermsPOListAdapter;
import quay.com.ipos.inventory.attachments.AttachFileModel;
import quay.com.ipos.inventory.fragment.InventoryProduct;
import quay.com.ipos.inventory.modal.GrnAttachment;
import quay.com.ipos.inventory.modal.GrnInccoTermsModel;
import quay.com.ipos.inventory.modal.GrnItemQtyModel;
import quay.com.ipos.inventory.modal.POPaymentTerms;
import quay.com.ipos.inventory.modal.POTermsCondition;
import quay.com.ipos.listeners.AttachmentListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmGRNDetails;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 6/14/2018.
 */

public class InventoryGRNDetails extends AppCompatActivity implements InitInterface, View.OnClickListener, InventoryGrnInccoAdapter.OnCalculateTotalIncoTermsListener, MyListener, AttachmentListener, DatePickerDialog.OnDateSetListener {
    private static final String TAG = InventoryGRNDetails.class.getSimpleName();
    private Toolbar toolbar;
    private Button btnAction, btnSave;
    private Context mContext;
    private RelativeLayout rGrn, rTransporter, rItemsDetails, rIncco, rAttachment;
    private LinearLayout lGrn, lItemsDetails, llIncoTerms, llTermsC;
    private RelativeLayout lTransporter;
    private EditText etEWayBillValidity;
    ArrayList<AttachFileModel> attachFileModels = new ArrayList<>();

    private RecyclerView recycler_viewItemDetail, rvIncco, recycler_viewPayment, recycler_viewTerms, rvAttachment;
    //Inventory Grn
    InventoryGrnItemsListAdapter itemListDataAdapter;
    InventoryGrnInccoAdapter inventoryGrnInccoAdapter;
    InventoryAttachmentAdapter inventoryAttachmentAdapter;

    MilestonePOListAdapter milestonePOListAdapter;
    TermsPOListAdapter termsPOListAdapter;
    boolean clicked = false;
    boolean isEWayBillClicked = false;

    ArrayList<GrnItemQtyModel> grnListModels = new ArrayList<>();
    ArrayList<GrnInccoTermsModel> grnInccoTermsModels = new ArrayList<>();
    ArrayList<GrnAttachment> grnAttachments = new ArrayList<>();

    ArrayList<POPaymentTerms> poPaymentTerms = new ArrayList<>();
    ArrayList<POTermsCondition> poTermsConditions = new ArrayList<>();
    private int Year, Month, Day;
    String poNumber, businessPlaceId;
    private EditText grnNumber, et_received_date, et_totalItems, et_value, poQty, openQty, balanceQty, etName, etLrn, etTruckNumber, etEwayBill, etTrackNumber, etDriverName, driverMobileNumber, etAddress;
    boolean isGrnClick = false;
    boolean isTransporterClick = false;
    boolean isItemDetailsClick = false;
    boolean isInccoClick = false;
    boolean isAttachmentClick = false;
    private String transporterEWayBillValidityDate;
    private TextView textTotalIncoTerms;
    private Calendar calendar;
    private View ivAttAdd;
    String getGrnNumber;
    String cardClick;
    private DatePickerDialog datePickerDialog;
    private int poQuantity;
    private ImageView ivGrnGroupArrow, ivTransporterGroupArrow, ivItemDetailsGroupArrow, ivIncoGroupArrow, ivAttGroupArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grn_expandable);
        mContext = InventoryGRNDetails.this;
        Intent i = getIntent();

        calendar = Calendar.getInstance();
        poNumber = i.getStringExtra("poNumber");
        getGrnNumber = i.getStringExtra("grnNumber");
        cardClick = i.getStringExtra("cardClick");

        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();


    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        btnAction = findViewById(R.id.btnAction);
        btnSave = findViewById(R.id.btnSave);
        ivAttAdd = findViewById(R.id.ivAttAdd);

        rGrn = findViewById(R.id.rGrn);
        rTransporter = findViewById(R.id.rTransporter);
        rItemsDetails = findViewById(R.id.rItemsDetails);
        rIncco = findViewById(R.id.rIncco);
        rAttachment = findViewById(R.id.rAttachment);

        grnNumber = findViewById(R.id.grnNumber);
        et_received_date = findViewById(R.id.et_received_date);
        et_totalItems = findViewById(R.id.et_totalItems);
        et_value = findViewById(R.id.et_value);
        poQty = findViewById(R.id.poQty);
        openQty = findViewById(R.id.openQty);
        balanceQty = findViewById(R.id.balanceQty);
        textTotalIncoTerms = findViewById(R.id.textTotalIncoTerms);
        etEWayBillValidity = findViewById(R.id.etEWayBillValidity);


        ivGrnGroupArrow = findViewById(R.id.ivGrnGroupArrow);
        ivTransporterGroupArrow = findViewById(R.id.ivTransporterGroupArrow);
        ivItemDetailsGroupArrow = findViewById(R.id.ivItemDetailsGroupArrow);
        ivIncoGroupArrow = findViewById(R.id.ivIncoGroupArrow);
        ivAttGroupArrow = findViewById(R.id.ivAttGroupArrow);


        ivAttAdd.setOnClickListener(this);
        rGrn.setOnClickListener(this);
        rTransporter.setOnClickListener(this);
        rItemsDetails.setOnClickListener(this);
        rIncco.setOnClickListener(this);
        rAttachment.setOnClickListener(this);

        lGrn = findViewById(R.id.lGrn);
        lTransporter = findViewById(R.id.lTransporter);
        lItemsDetails = findViewById(R.id.lItemsDetails);
        llIncoTerms = findViewById(R.id.llIncoTerms);
        llTermsC = findViewById(R.id.llTermsC);

        btnSave.setOnClickListener(this);
        btnAction.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etLrn = findViewById(R.id.etLrn);
        etTruckNumber = findViewById(R.id.etTruckNumber);
        etTrackNumber = findViewById(R.id.etTrackNumber);
        etDriverName = findViewById(R.id.etDriverName);
        driverMobileNumber = findViewById(R.id.driverMobileNumber);
        etAddress = findViewById(R.id.etAddress);
        etEwayBill = findViewById(R.id.etEwayBill);

        recycler_viewItemDetail = findViewById(R.id.recycler_viewItemDetails);
        rvIncco = findViewById(R.id.rvIncco);
        rvAttachment = findViewById(R.id.rvAttachment);

        et_received_date.setClickable(true);
        et_received_date.setOnClickListener(this);

        etEWayBillValidity.setClickable(true);
        etEWayBillValidity.setOnClickListener(this);


        etName.addTextChangedListener(new GenericTextWatcher(etName));
        etLrn.addTextChangedListener(new GenericTextWatcher(etLrn));
        etEwayBill.addTextChangedListener(new GenericTextWatcher(etEwayBill));
        etDriverName.addTextChangedListener(new GenericTextWatcher(etDriverName));
        etTruckNumber.addTextChangedListener(new GenericTextWatcher(etTruckNumber));
        etEWayBillValidity.addTextChangedListener(new GenericTextWatcher(etEWayBillValidity));
        driverMobileNumber.addTextChangedListener(new GenericTextWatcher(driverMobileNumber));
        etTrackNumber.addTextChangedListener(new GenericTextWatcher(etTrackNumber));
        etAddress.addTextChangedListener(new GenericTextWatcher(etAddress));
    }


    private void setOnTextChangesListener() {

    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        if (TextUtils.isEmpty(cardClick)) {

            addGRNDetails();
        } else {
            btnSave.setVisibility(View.GONE);
            btnSave.setEnabled(false);
            cardGrnDetail();
        }

        //Getting GRN details from server

    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivAttAdd:
                onAttachFileClicked();
                break;

            case R.id.btnAction:
                Intent i = new Intent(mContext, InventoryWorkFlowActivity.class);
                startActivity(i);
                break;
            case R.id.btnSave:


                checkValidation();
//
                break;
            case R.id.rGrn:
                if (isGrnClick) {
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);
                    ivGrnGroupArrow.setImageResource(R.drawable.ic_action_arrow_right_blue);

                    isGrnClick = false;
                } else {
                    lGrn.setVisibility(View.VISIBLE);
                    lTransporter.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);
                    ivGrnGroupArrow.setImageResource(R.drawable.ic_action_arrow_down_blue);

                    isGrnClick = true;
                }

                break;
            case R.id.rTransporter:
                if (isTransporterClick) {
                    lTransporter.setVisibility(View.GONE);
                    lGrn.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);
                    ivTransporterGroupArrow.setImageResource(R.drawable.ic_action_arrow_right_blue);


                    isTransporterClick = false;
                } else {
                    lTransporter.setVisibility(View.VISIBLE);
                    lGrn.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);
                    ivTransporterGroupArrow.setImageResource(R.drawable.ic_action_arrow_down_blue);


                    isTransporterClick = true;
                }


                break;
            case R.id.et_received_date:
                clicked = true;
                Calendar minDate = Calendar.getInstance();
                minDate.set(1900, 0, 1);

                Calendar maxDate = Calendar.getInstance();
                maxDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(true);
                datePickerDialog.setMinDate(minDate);
                datePickerDialog.setMaxDate(maxDate);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                break;

            case R.id.etEWayBillValidity:
                isEWayBillClicked = true;
                Calendar miDate = Calendar.getInstance();
                miDate.set(1900, 0, 1);

                Calendar maDate = Calendar.getInstance();
                maDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(true);
                datePickerDialog.setMinDate(miDate);
                datePickerDialog.setMaxDate(maDate);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

                break;
            case R.id.rItemsDetails:
                if (isItemDetailsClick) {
                    lItemsDetails.setVisibility(View.GONE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);

                    ivItemDetailsGroupArrow.setImageResource(R.drawable.ic_action_arrow_right_blue);

                    isItemDetailsClick = false;
                } else {
                    lItemsDetails.setVisibility(View.VISIBLE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);
                    ivItemDetailsGroupArrow.setImageResource(R.drawable.ic_action_arrow_down_blue);


                    isItemDetailsClick = true;
                }


                break;
            case R.id.rIncco:
                if (isInccoClick) {
                    llIncoTerms.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);
                    ivIncoGroupArrow.setImageResource(R.drawable.ic_action_arrow_right_blue);

                    isInccoClick = false;
                } else {
                    llIncoTerms.setVisibility(View.VISIBLE);
                    lItemsDetails.setVisibility(View.GONE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);

                    ivIncoGroupArrow.setImageResource(R.drawable.ic_action_arrow_down_blue);

                    isInccoClick = true;
                }


                break;
            case R.id.rAttachment:
                if (isAttachmentClick) {
                    llTermsC.setVisibility(View.GONE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);

                    ivAttGroupArrow.setImageResource(R.drawable.ic_action_arrow_right_blue);

                    isAttachmentClick = false;
                } else {
                    llTermsC.setVisibility(View.VISIBLE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);

                    ivAttGroupArrow.setImageResource(R.drawable.ic_action_arrow_down_blue);
                    isAttachmentClick = true;
                }

            default:
                break;

        }
    }

    private void checkValidation() {
        boolean isFail = false;

        String name = etName.getText().toString();
        String LRNumber = etLrn.getText().toString();
        String eWayBillNumber = etEwayBill.getText().toString();
        String driverName = etDriverName.getText().toString();
        String truckNumber = etTruckNumber.getText().toString();
        String etEWayBillValidityDate = etEWayBillValidity.getText().toString();
        String driverMobileNum = driverMobileNumber.getText().toString();
        String trackNumber = etTrackNumber.getText().toString();
        String address = etAddress.getText().toString();


        if (TextUtils.isEmpty(name)) {
            isFail = true;
            etName.setError(getResources().getString(R.string.iv_error_name));
        }
        if (TextUtils.isEmpty(LRNumber)) {
            isFail = true;
            etLrn.setError(getResources().getString(R.string.iv_error_LRNumber));
        }
        if (TextUtils.isEmpty(eWayBillNumber)) {
            isFail = true;
            etEwayBill.setError(getResources().getString(R.string.iv_error_eway_billNumber));
        }
        if (TextUtils.isEmpty(driverName)) {
            isFail = true;
            etDriverName.setError(getResources().getString(R.string.iv_error_driver_name));
        }
        if (TextUtils.isEmpty(truckNumber)) {
            isFail = true;
            etTruckNumber.setError(getResources().getString(R.string.iv_error_truck_number));
        }
        if (TextUtils.isEmpty(etEWayBillValidityDate)) {
            isFail = true;
            etEWayBillValidity.setError(getResources().getString(R.string.iv_error_eway_billValidity));
        }
        if (TextUtils.isEmpty(driverMobileNum)) {
            isFail = true;
            driverMobileNumber.setError(getResources().getString(R.string.iv_error_mobile_number));
        }
        if (TextUtils.isEmpty(trackNumber)) {
            isFail = true;
            etTrackNumber.setError(getResources().getString(R.string.iv_error_track_number));
        }
        if (TextUtils.isEmpty(address)) {
            isFail = true;
            etAddress.setError(getResources().getString(R.string.iv_error_address));
        }

        if (!isFail) {
            etName.setError(null);
            etLrn.setError(null);
            etEwayBill.setError(null);
            etDriverName.setError(null);
            etTruckNumber.setError(null);
            etEWayBillValidity.setError(null);
            driverMobileNumber.setError(null);
            etTrackNumber.setError(null);
            etAddress.setError(null);

            if (TextUtils.isEmpty(cardClick)) {
                submitGRNDetails();
            }


        } else {
            Toast.makeText(mContext, "Please enter all required fields", Toast.LENGTH_SHORT).show();
        }
    }


    private void getExpandableData() {
        Realm realm = Realm.getDefaultInstance();
        RealmGRNDetails realmGRNDetails;
        realmGRNDetails = realm.where(RealmGRNDetails.class).findFirst();

//        if (getGrnNumber != null) {
//            realmGRNDetails = realm.where(RealmGRNDetails.class).equalTo("grnNumber", getGrnNumber + "").findFirst();
//        } else {
//            realmGRNDetails = realm.where(RealmGRNDetails.class).findFirst();
//        }

        if (realmGRNDetails != null) {
            try {
                int totalItem = (int) realmGRNDetails.getTotalItems();
                int value = (int) realmGRNDetails.getValue();
                int poQt = (int) realmGRNDetails.getPoQty();
                poQuantity = poQt;
                int openQt = (int) realmGRNDetails.getOpenQty();
                int balanceQt = (int) realmGRNDetails.getBalanceQty();

                grnNumber.setText(realmGRNDetails.getGrnNumber());
                et_received_date.setText(realmGRNDetails.getReceivedDate());
                et_totalItems.setText(totalItem + "");
                et_value.setText(realmGRNDetails.getValue() + "");
                poQty.setText(poQt + "");
                openQty.setText(openQt + "");
                balanceQty.setText(balanceQt + "");
                transporterEWayBillValidityDate = realmGRNDetails.getTransporterEWayBillValidityDate();

                etName.setText(realmGRNDetails.getTransporterName());
                etLrn.setText(realmGRNDetails.getTransporterLRName());
                etTruckNumber.setText(realmGRNDetails.getTransporterTruckNumber());
                etTrackNumber.setText(realmGRNDetails.getTransporterTruckNumber());
                etDriverName.setText(realmGRNDetails.getTransporterDriverName());
                etEWayBillValidity.setText(realmGRNDetails.getTransporterEWayBillValidityDate());
                driverMobileNumber.setText(realmGRNDetails.getTransporterDriverMobileNumber());
                etAddress.setText(realmGRNDetails.getTransporterAddress());
                etEwayBill.setText(realmGRNDetails.getTransporterEWayBillNumber());

                Log.e(TAG, "PoItemDetails:::" + realmGRNDetails.getPoItemDetails());

                //Items Details
                JSONArray array = new JSONArray(realmGRNDetails.getPoItemDetails());
                for (int j = 0; j < array.length(); j++) {
                    JSONObject jsonObject1 = array.optJSONObject(j);
                    GrnItemQtyModel grnItemQtyModel = new GrnItemQtyModel();
                    grnItemQtyModel.setMaterialCode(jsonObject1.optString("materialCode"));
                    grnItemQtyModel.setMaterialName(jsonObject1.optString("materialName"));
                    grnItemQtyModel.setOpenQty(jsonObject1.optDouble("openQty"));
                    grnItemQtyModel.setInQty(jsonObject1.optDouble("inQty"));
                    grnItemQtyModel.setApQty(jsonObject1.optDouble("apQty"));
                    grnItemQtyModel.setBalanceQty(jsonObject1.optDouble("balanceQty"));
                    grnItemQtyModel.setIsBatch(jsonObject1.optDouble("isBatch"));
                    grnItemQtyModel.setgRNItemInfoDetails(jsonObject1.optJSONObject("gRNItemInfoDetails").toString());
                    grnListModels.add(grnItemQtyModel);
                }


                //Incco terms
                JSONArray jsonArray = new JSONArray(realmGRNDetails.getPoIncoTerms());
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject1 = jsonArray.optJSONObject(j);
                    GrnInccoTermsModel grnInccoTermsModel = new GrnInccoTermsModel();
                    grnInccoTermsModel.grnIncoDetail = jsonObject1.optString("grnIncoDetail");
                    grnInccoTermsModel.grnPayBySender = jsonObject1.optBoolean("grnPayBySender");
                    grnInccoTermsModel.grnPayByReceiver = jsonObject1.optBoolean("grnPayByReceiver");
                    grnInccoTermsModel.grnPayAmount = jsonObject1.optDouble("grnPayAmount");
                    grnInccoTermsModels.add(grnInccoTermsModel);
                }

//                POIncoTerms poIncoTerms2 = new POIncoTerms();
//                poIncoTerms2.setPoIncoDetail("Total");
//                poIncoTerms2.setPoPayAmount(total);
//                poIncoTerms2.setPoPayByReceiver(false);
//                poIncoTerms2.setPoPayBySender(false);
//                poIncoTerms.add(poIncoTerms2);

                //PoPayment terms
//                JSONArray jsonArray1 = new JSONArray(realmGRNDetails.getPoPaymentTerms());
//                for (int j = 0; j < jsonArray1.length(); j++) {
//                    JSONObject jsonObject1 = jsonArray1.optJSONObject(j);
//                    POPaymentTerms poIncoTerms1 = new POPaymentTerms();
//                    poIncoTerms1.setPoPaymentTermsDetail(jsonObject1.optString("grnPaymentTermsDetail"));
//                    poIncoTerms1.setPoPaymentTermsInvoiceDue(jsonObject1.optString("grnPaymentTermsInvoiceDue"));
//                    poIncoTerms1.setPoPaymentTermsPer(jsonObject1.optDouble("grnPaymentTermsPer"));
//                    poPaymentTerms.add(poIncoTerms1);
//                }

//                //Po terms and conditions
//                JSONArray jsonArray2 = new JSONArray(realmGRNDetails.getPoTermsAndConditions());
//                for (int j = 0; j < jsonArray2.length(); j++) {
//                    JSONObject jsonObject1 = jsonArray2.optJSONObject(j);
//                    POTermsCondition termsCondition = new POTermsCondition();
//                    termsCondition.setpOTermsAndConditionDetail(jsonObject1.optString("grnTermsAndConditionDetail"));
//                    termsCondition.setpOTermsAndConditionSrNo(jsonObject1.optInt("grnTermsAndConditionSrNo"));
//                    poTermsConditions.add(termsCondition);
//                }

                //Attachments
                JSONArray jsonArray3 = new JSONArray(realmGRNDetails.getPoAttachments());
                for (int j = 0; j < jsonArray3.length(); j++) {
                    JSONObject jsonObject1 = jsonArray3.optJSONObject(j);
                    GrnAttachment grnAttachment = new GrnAttachment();
                    grnAttachment.setGrnAttachmentName(jsonObject1.optString("grnAttachmentName"));
                    grnAttachment.setGrnAttachmentUrl(jsonObject1.optString("grnAttachmentUrl"));
                    grnAttachment.setGrnAttachmentUrl(jsonObject1.optString("grnAttachmentType"));
                    grnAttachments.add(grnAttachment);
                }


                setItemDetails();
                setIncoTerms();
//                setAttahcments();
                // setPaymentTerms();
                //  setTermsCondition();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public void addGRNDetails() {

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        JSONObject jsonObject1 = new JSONObject();

        try {
            jsonObject1.put("empCode", Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("businessPlaceId", "1");
            jsonObject1.put("poNumber", poNumber);
            jsonObject1.put("isGRN", false);
            jsonObject1.put("isGRNOrQC", "NA");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.WEB_SERVICE_GET_GRN_SUMMARY_DETAIL;

        final Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // dismissProgress();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
                try {
                    if (response != null && response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Response***" + response.body().toString());
                            }
                        });

                        final String responseData = response.body().string();
                        if (responseData != null) {
                            clearRealm();
                            new RealmController().saveGRNDetails(responseData);
                            //saveResponseLocalCreateOrder(jsonObject,requestId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getExpandableData();
                                }
                            });

                        }

                    } else if (response.code() == Constants.BAD_REQUEST) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.INTERNAL_SERVER_ERROR) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.URL_NOT_FOUND) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.UNAUTHORIZE_ACCESS) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.CONNECTION_OUT) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });
    }

    public void cardGrnDetail() {

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        JSONObject jsonObject1 = new JSONObject();

        try {
            jsonObject1.put("empCode", Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("businessPlaceId", "1");
            jsonObject1.put("poNumber", getGrnNumber);
            jsonObject1.put("isGRN", true);
            jsonObject1.put("isGRNOrQC", "grn");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.WEB_SERVICE_GET_GRN_SUMMARY_DETAIL;

        final Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // dismissProgress();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
                try {
                    if (response != null && response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                                Log.e(TAG, "Response***" + response.body().toString());
                            }
                        });

                        final String responseData = response.body().string();
                        if (responseData != null) {
                            clearRealm();
                            new RealmController().saveGRNDetails(responseData);
                            //saveResponseLocalCreateOrder(jsonObject,requestId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getExpandableData();
                                }
                            });

                        }

                    } else if (response.code() == Constants.BAD_REQUEST) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.INTERNAL_SERVER_ERROR) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.URL_NOT_FOUND) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.UNAUTHORIZE_ACCESS) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.CONNECTION_OUT) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });
    }

    private void setItemDetails() {
        recycler_viewItemDetail.setLayoutManager(new LinearLayoutManager(mContext));
        itemListDataAdapter = new InventoryGrnItemsListAdapter(mContext, grnListModels, this);
        recycler_viewItemDetail.setAdapter(itemListDataAdapter);
    }

    private void setIncoTerms() {
        rvIncco.setLayoutManager(new LinearLayoutManager(mContext));
        inventoryGrnInccoAdapter = new InventoryGrnInccoAdapter(mContext, grnInccoTermsModels, this);
        rvIncco.setAdapter(inventoryGrnInccoAdapter);
    }

//    private void setPaymentTerms() {
//        recycler_viewPayment = findViewById(R.id.recycler_viewPayment);
//        recycler_viewPayment.setHasFixedSize(true);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
//        recycler_viewPayment.setLayoutManager(mLayoutManager);
//        milestonePOListAdapter = new MilestonePOListAdapter(mContext, poPaymentTerms);
//        recycler_viewPayment.setAdapter(milestonePOListAdapter);
//    }

//    private void setTermsCondition() {
//
//        recycler_viewTerms = findViewById(R.id.recycler_viewTerms);
//        recycler_viewTerms.setHasFixedSize(true);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
//        recycler_viewTerms.setLayoutManager(mLayoutManager);
//        termsPOListAdapter = new TermsPOListAdapter(mContext, poTermsConditions);
//        recycler_viewTerms.setAdapter(termsPOListAdapter);
//    }


    private void setAttahcments() {
        rvAttachment.setLayoutManager(new LinearLayoutManager(mContext));
        inventoryAttachmentAdapter = new InventoryAttachmentAdapter(mContext, grnAttachments, this);
        rvAttachment.setAdapter(inventoryAttachmentAdapter);
    }


    private void createJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray poDetails = new JSONArray();

        try {

            for (int j = 0; j < grnListModels.size(); j++) {

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("materialCode", grnListModels.get(j).getMaterialCode());
                jsonObject1.put("materialName", grnListModels.get(j).getMaterialName());
                jsonObject1.put("openQty", grnListModels.get(j).getOpenQty());
                jsonObject1.put("inQty", grnListModels.get(j).getInQty());
                jsonObject1.put("apQty", grnListModels.get(j).getApQty());
                jsonObject1.put("balanceQty", grnListModels.get(j).getBalanceQty());
                jsonObject1.put("gRNItemInfoDetails", grnListModels.get(j).getgRNItemInfoDetails());

                poDetails.put(jsonObject1);
            }

            JSONArray IncoTermsArray = new JSONArray();
            for (int j = 0; j < grnInccoTermsModels.size(); j++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnIncoDetail", grnInccoTermsModels.get(j).grnIncoDetail);
                jsonObject1.put("grnPayBySender", grnInccoTermsModels.get(j).grnPayBySender);
                jsonObject1.put("grnPayByReceiver", grnInccoTermsModels.get(j).grnPayByReceiver);
                jsonObject1.put("grnPayAmount", grnInccoTermsModels.get(j).grnPayAmount);

                IncoTermsArray.put(jsonObject1);
            }

            JSONArray jsonArrayAttachments = new JSONArray();
            for (int j = 0; j < grnAttachments.size(); j++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnAttachmentName", grnAttachments.get(j).getGrnAttachmentName());
                jsonObject1.put("grnAttachmentUrl", grnAttachments.get(j).getGrnAttachmentUrl());
                jsonObject1.put("grnAttachmentType", grnAttachments.get(j).getGrnAttachmentType());
                jsonArrayAttachments.put(jsonObject1);
            }

            //attach new

            for (int i = 0; i < attachFileModels.size(); i++) {
                AttachFileModel fileModel = attachFileModels.get(i);
                Uri returnUri = fileModel.uri;
                Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                String fileName = returnCursor.getString(nameIndex);
                String fileSize = Long.toString(returnCursor.getLong(sizeIndex));
                String mimeType = getContentResolver().getType(returnUri);
                Log.i("Type", mimeType);

              /*  SpendRequestAttachment spendRequestAttachment = new SpendRequestAttachment();
                spendRequestAttachment.AttachmentBase = getBase64StringNew(returnUri, Integer.parseInt(fileSize));
                spendRequestAttachment.AttachmentExtension = "No Info";
                spendRequestAttachment.AttachmentName = fileName;
                spendRequestAttachment.AttachmentType = mimeType;
                spendRequestAttachmentList.add(spendRequestAttachment);
               */
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnAttachmentName", fileName);
                jsonObject1.put("grnAttachmentUrl", getBase64StringNew(returnUri, Integer.parseInt(fileSize)));
                jsonObject1.put("grnAttachmentType", mimeType);
                jsonArrayAttachments.put(jsonObject1);


            }
            //attach end


            jsonObject.put("poNumber", poNumber);
            jsonObject.put("grnNumber", grnNumber.getText().toString());
            jsonObject.put("receivedDate", et_received_date.getText().toString());
            jsonObject.put("totalItems", et_totalItems.getText().toString());
            jsonObject.put("value", et_value.getText().toString());
            jsonObject.put("poQty", poQty.getText().toString());
            jsonObject.put("openQty", openQty.getText().toString());
            jsonObject.put("balanceQty", balanceQty.getText().toString());
            jsonObject.put("transporterName", etName.getText().toString());
            jsonObject.put("transporterLRName", etLrn.getText().toString());
            jsonObject.put("transporterTruckNumber", etTruckNumber.getText().toString());
            jsonObject.put("transporterEWayBillNumber", etEwayBill.getText().toString());
            jsonObject.put("transporterEWayBillValidityDate", etEWayBillValidity.getText().toString());
            jsonObject.put("transporterDriverName", etDriverName.getText().toString());
            jsonObject.put("transporterDriverMobileNumber", driverMobileNumber.getText().toString());
            jsonObject.put("transporterAddress", etAddress.getText().toString());
            jsonObject.put("poItemDetails", poDetails);
            jsonObject.put("poIncoTerms", IncoTermsArray);
            jsonObject.put("poPaymentTermsType", new JSONArray());
            jsonObject.put("poTermsAndConditions", new JSONArray());
            jsonObject.put("poAttachments", jsonArrayAttachments);
            jsonObject.put("employeeCode", Prefs.getStringPrefs(Constants.employeeCode));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new RealmController().saveGRNDetails(jsonObject.toString());

    }


    public void submitGRNDetails() {

        double totalItem = Double.parseDouble(et_totalItems.getText().toString());
        double value = Double.parseDouble(et_value.getText().toString());
        double poQt = Double.parseDouble(poQty.getText().toString());
        double openQt = Double.parseDouble(openQty.getText().toString());
        double balanceQt = Double.parseDouble(balanceQty.getText().toString());

        JSONObject jsonObject = new JSONObject();
        JSONArray poDetails = new JSONArray();

        try {

            for (int j = 0; j < grnListModels.size(); j++) {

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("materialCode", grnListModels.get(j).getMaterialCode());
                jsonObject1.put("materialName", grnListModels.get(j).getMaterialName());
                jsonObject1.put("openQty", grnListModels.get(j).getOpenQty());
                jsonObject1.put("inQty", grnListModels.get(j).getInQty());
                jsonObject1.put("apQty", grnListModels.get(j).getApQty());
                jsonObject1.put("balanceQty", grnListModels.get(j).getBalanceQty());
                if (grnListModels.get(j).getIsBatch() == 0) {
                    jsonObject1.put("isBatch", grnListModels.get(j).getIsBatch());
                } else {
                    jsonObject1.put("isBatch", grnListModels.get(j).getIsBatch());
                }
                jsonObject1.put("gRNItemInfoDetails", new JSONObject(grnListModels.get(j).getgRNItemInfoDetails()));

                poDetails.put(jsonObject1);
            }

            JSONArray IncoTermsArray = new JSONArray();
            for (int j = 0; j < grnInccoTermsModels.size(); j++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnIncoDetail", grnInccoTermsModels.get(j).grnIncoDetail);
                jsonObject1.put("grnPayBySender", grnInccoTermsModels.get(j).grnPayBySender);
                jsonObject1.put("grnPayByReceiver", grnInccoTermsModels.get(j).grnPayByReceiver);
                jsonObject1.put("grnPayAmount", grnInccoTermsModels.get(j).grnPayAmount);

                IncoTermsArray.put(jsonObject1);
            }

            JSONArray jsonArrayAttachments = new JSONArray();

            //attach new

            for (int i = 0; i < attachFileModels.size(); i++) {
                AttachFileModel fileModel = attachFileModels.get(i);
                Uri returnUri = fileModel.uri;
                Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                String fileName = returnCursor.getString(nameIndex);
                String fileSize = Long.toString(returnCursor.getLong(sizeIndex));
                String mimeType = getContentResolver().getType(returnUri);
                Log.i("Type", mimeType);

              /*  SpendRequestAttachment spendRequestAttachment = new SpendRequestAttachment();
                spendRequestAttachment.AttachmentBase = getBase64StringNew(returnUri, Integer.parseInt(fileSize));
                spendRequestAttachment.AttachmentExtension = "No Info";
                spendRequestAttachment.AttachmentName = fileName;
                spendRequestAttachment.AttachmentType = mimeType;
                spendRequestAttachmentList.add(spendRequestAttachment);
               */
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnAttachmentName", fileName);
                jsonObject1.put("grnAttachmentUrl", getBase64StringNew(returnUri, Integer.parseInt(fileSize)));
                jsonObject1.put("grnAttachmentType", mimeType);
                jsonArrayAttachments.put(jsonObject1);


            }
            //attach end


            jsonObject.put("poNumber", poNumber);
            jsonObject.put("grnNumber", grnNumber.getText().toString());
            jsonObject.put("receivedDate", et_received_date.getText().toString());
            if (totalItem == 0) {
                jsonObject.put("totalItems", 0);
            } else {
                jsonObject.put("totalItems", totalItem);
            }

            if (value == 0) {
                jsonObject.put("value", 0);
            } else {
                jsonObject.put("value", value);
            }

            if (poQt == 0) {
                jsonObject.put("poQty", 0);
            } else {
                jsonObject.put("poQty", poQt);
            }

            if (openQt == 0) {
                jsonObject.put("openQty", 0);
            } else {
                jsonObject.put("openQty", openQt);
            }

            if (balanceQt == 0) {
                jsonObject.put("balanceQty", 0);
            } else {
                jsonObject.put("balanceQty", balanceQt);
            }

            jsonObject.put("transporterName", etName.getText().toString());
            jsonObject.put("transporterLRName", etLrn.getText().toString());
            jsonObject.put("transporterTruckNumber", etTruckNumber.getText().toString());
            jsonObject.put("transporterEWayBillNumber", etEwayBill.getText().toString());
            jsonObject.put("transporterEWayBillValidityDate", etEWayBillValidity.getText().toString());
            jsonObject.put("transporterDriverName", etDriverName.getText().toString());
            jsonObject.put("transporterDriverMobileNumber", driverMobileNumber.getText().toString());
            jsonObject.put("transporterAddress", etAddress.getText().toString());
            jsonObject.put("poItemDetails", poDetails);
            jsonObject.put("poIncoTerms", IncoTermsArray);
            jsonObject.put("poPaymentTermsType", "");
            jsonObject.put("poTermsAndConditions", new JSONArray());
            jsonObject.put("poAttachments", jsonArrayAttachments);
            jsonObject.put("employeeCode", Prefs.getStringPrefs(Constants.employeeCode));
            Log.e(TAG, "Requuest::" + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.show();

//        Realm realm = Realm.getDefaultInstance();
//        RealmGRNDetails realmGRNDetails = realm.where(RealmGRNDetails.class).equalTo("grnNumber", grnNumber.getText().toString()).findFirst();
//        Gson gson = new GsonBuilder().create();
//        String json = gson.toJson(realm.copyFromRealm(realmGRNDetails));
//

        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject.toString().replaceAll("\\\\", ""));
        String url = IPOSAPI.WEB_SERVICE_GET_GRN_SUMMARY_SUBMIT;

        final Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
                try {
                    if (response != null && response.isSuccessful()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
//                                Log.e(TAG, "Response***" + response.body().toString());
                                InventoryGRNStepsActivity.fa.finish();
                                Intent i = new Intent(mContext, InventoryGRNStepsActivity.class);
                                i.putExtra("newGRNCreated", "GrnCreated");
                                i.putExtra("poNumber", poNumber);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(i);

                            }
                        });

                        final String responseData = response.body().string();
                        if (responseData != null) {

                            //saveResponseLocalCreateOrder(jsonObject,requestId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Util.showToast("Save Successfully");
                                }
                            });

                        }

                    } else if (response.code() == Constants.BAD_REQUEST) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.INTERNAL_SERVER_ERROR) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.URL_NOT_FOUND) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.UNAUTHORIZE_ACCESS) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.CONNECTION_OUT) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });
    }


    @Override
    public void onRowClicked(int position) {
        if (TextUtils.isEmpty(cardClick)) {
            GrnItemQtyModel grnItemQtyModel = grnListModels.get(position);
            Intent gotToProductDetail = new Intent(mContext, InventoryProduct.class);
            gotToProductDetail.putExtra("position", position);
            gotToProductDetail.putExtra("poQty", poQuantity);
            gotToProductDetail.putExtra("openQty", grnItemQtyModel.getOpenQty());
            startActivityForResult(gotToProductDetail, 1);

        }


    }

    @Override
    public void onRowClicked(int position, int value) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityResultAttachment(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                grnListModels.clear();
                grnInccoTermsModels.clear();
                grnAttachments.clear();

                getExpandableData();
            }
        }

    }

    @Override
    public void onAttachmentClicked(int position) {

    }

    @Override
    public void funIncoTermsTotalCount(double totalIncoTerms) {
        textTotalIncoTerms.setText(totalIncoTerms + "");
        Log.i(TAG, "Total IncoTerms:" + totalIncoTerms);
    }

    private static final int PICKFILE_RESULT_CODE = 101;

    private void onAttachFileClicked() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, PICKFILE_RESULT_CODE);

    }


    public void onActivityResultAttachment(int requestCode, int resultCode, Intent data) {

        try {
            switch (requestCode) {
                case PICKFILE_RESULT_CODE:
                    if (resultCode == RESULT_OK) {
                        Uri uri = data.getData();
                        Cursor returnCursor =
                                getContentResolver().query(uri, null, null, null, null);
                        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                        returnCursor.moveToFirst();

                        String fileName = returnCursor.getString(nameIndex);
                        long fileSize = returnCursor.getLong(sizeIndex);
                        String mimeType = getContentResolver().getType(uri);
                        Log.i("Type", mimeType);
                        Log.i("fileSize", fileSize + "");
                        long twoMb = 1024 * 1024 * 2;

                        if (fileSize <= twoMb) {
                            AttachFileModel fileModel = new AttachFileModel();
                            fileModel.fileName = fileName;
                            fileModel.mimeType = mimeType;
                            fileModel.uri = uri;

                            attachFileModels.add(fileModel);
                            updateSize();
                            String FilePath = data.getData().getPath();
                        } else {
                            Toast.makeText(getApplicationContext(), "Oops! File Size must be less than 2 MB", Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateSize() {
        int attachFileSize = attachFileModels.size();
        int attachVoiceSize = 0;
        int totalSize = attachFileSize;
        // textViewAttachmentSize.setText("(" + totalSize + ")");
        //    Toast.makeText(getActivity(), "attachFileModels" + attachFileModels.size(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < attachFileModels.size(); i++) {
            Log.v("attachFileModels", "attachFileModels" + attachFileModels.get(i));
        }
        if (attachFileSize > 0) {
            rvAttachment.setLayoutManager(new LinearLayoutManager(mContext));
            // inventoryAttachmentAdapter = new InventoryAttachmentAdapter(mContext, grnAttachments, this);
            //  rvAttachment.setAdapter(inventoryAttachmentAdapter);

            rvAttachment.setAdapter(new AttachFileAdapter(attachFileModels));

        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (clicked) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date date = calendar.getTime();

            String date1 = Util.getFormattedDates(date);
            Log.e(TAG, "date1" + date1);

            et_received_date.setText(date1);
            clicked = false;

        }
        if (isEWayBillClicked) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date date = calendar.getTime();

            String date1 = Util.getFormattedDates(date);
            Log.e(TAG, "date1" + date1);

            etEWayBillValidity.setText(date1);
            isEWayBillClicked = false;

        }
    }

    private class AttachVH extends RecyclerView.ViewHolder {
        public TextView textView;
        public View btnClear;

        public AttachVH(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            btnClear = itemView.findViewById(R.id.btnClear);
            btnClear.setVisibility(View.VISIBLE);
        }
    }

    private class AttachFileAdapter extends RecyclerView.Adapter<AttachVH> {
        private List<AttachFileModel> spendRequestAttachment;

        public AttachFileAdapter(List<AttachFileModel> spendRequestAttachment) {
            this.spendRequestAttachment = spendRequestAttachment;
        }

        @NonNull
        @Override
        public AttachVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_attachfile_item, parent, false);
            return new AttachVH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AttachVH holder, final int position) {
            final AttachFileModel fileModel = spendRequestAttachment.get(position);
            final String fileName = fileModel.fileName;
            // String name = fileName.substring(fileName.lastIndexOf("/"));
            //SpannableString content = new SpannableString("" + name);
            //content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
            // textView.setText(content);
            //holder.textView.setText(name);
            holder.textView.setText(fileName);
            Log.v("path", "---------------------" + fileName);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Uri path = Uri.parse(attachment);
                    String type = attachment;

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(attachment));*/
                    // intent.setDataAndType(spendRequestAttachment.get(position),"*/*");
                    //   intent.setDataAndType(path, type);
                    //intent.addFlag(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    //Toast.makeText(getActivity(), "In Progress!", Toast.LENGTH_SHORT).show();
                 /*   startActivity(intent);*/
                    final Intent shareIntent = new Intent(Intent.ACTION_VIEW);
                    //   shareIntent.setType("*/*");
                    //  shareIntent.setDataAndType(Uri.parse(fileModel.uri.toString()), "image/*");
                    shareIntent.setDataAndType(Uri.parse(fileModel.uri.toString()), fileModel.mimeType);
                    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    //final File photoFile = new File(getFilesDir(), "foo.jpg");

                    startActivity(Intent.createChooser(shareIntent, "View file using"));
                }
            });
            holder.btnClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    spendRequestAttachment.remove(fileModel);
                    notifyDataSetChanged();

                    int attachFileSize = attachFileModels.size();
                    // textViewAttachmentSize.setText("(" + attachFileSize + ")");
                }
            });
        }

        @Override
        public int getItemCount() {
            return spendRequestAttachment.size();
        }
    }

    private String getBase64StringNew(Uri uri, int filelength) {
        String imageStr = null;
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);

           /* InputStream finput = new FileInputStream(file);
            byte[] imageBytes = new byte[(int)file.length()];
            finput.read(imageBytes, 0, imageBytes.length);
            finput.close();
            String imageStr = Base64.encodeBase64String(imageBytes);*/

            //InputStream finput = new FileInputStream(file);
            byte[] byteFileArray = new byte[filelength];
            inputStream.read(byteFileArray, 0, byteFileArray.length);
            inputStream.close();
            imageStr = android.util.Base64.encodeToString(byteFileArray, android.util.Base64.NO_WRAP);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return imageStr;
    }

    private class GenericTextWatcher implements TextWatcher {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }


        public void afterTextChanged(Editable editable) {
            String text = editable.toString();
            switch (view.getId()) {
                case R.id.etName:
                    etName.setError(null);
                    break;
                case R.id.etLrn:
                    etLrn.setError(null);
                    break;
                case R.id.etEwayBill:
                    etEwayBill.setError(null);
                    break;
                case R.id.etDriverName:
                    etDriverName.setError(null);
                    break;
                case R.id.etTruckNumber:
                    etTruckNumber.setError(null);
                    break;
                case R.id.etEWayBillValidity:
                    etEWayBillValidity.setError(null);
                    break;
                case R.id.driverMobileNumber:
                    driverMobileNumber.setError(null);
                    break;
                case R.id.etTrackNumber:
                    etTrackNumber.setError(null);
                    break;
                case R.id.etAddress:
                    etAddress.setError(null);
                    break;
                default:
                    break;
            }
        }
    }

    public void clearRealm() {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.delete(RealmGRNDetails.class);

        } catch (Exception e) {
            realm.cancelTransaction();
            realm.close();
            e.printStackTrace();
        } finally {
            realm.commitTransaction();
            realm.close();
        }
    }
}
