package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import quay.com.ipos.R;
import quay.com.ipos.inventory.fragment.InventoryProduct;
import quay.com.ipos.inventory.modal.GrnItemQtyModel;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter.TransferInItemAdapter;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.RealmTransferDetail;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInItemQtyModel;
import quay.com.ipos.listeners.DataUpdateListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.ui.CustomTextView;
import quay.com.ipos.utility.DateAndTimeUtil;
import quay.com.ipos.utility.Util;

import static quay.com.ipos.utility.DateAndTimeUtil.DATE_AND_TIME_FORMAT_INDIA;


/**
 * Created by niraj.kumar on 7/6/2018.
 */

public class TransferInDetailsActivity extends AppCompatActivity implements InitInterface, View.OnClickListener, MyListener, DataUpdateListener {
    private static final String TAG = TransferInDetailsActivity.class.getSimpleName();
    private Context mContext;
    private Toolbar toolbar;
    private CustomTextView toolbarTtile;

    //GRN id's
    private RelativeLayout rGrn;
    private EditText grnNumber, et_totalItems, et_received_date, et_value, openQty, grnQty, balanceQty;
    private ImageView ivReceivedDateCalender, ivGrnGroupArrow;
    private LinearLayout lGrn;

    //Items id's
    private RelativeLayout rItemsDetails;
    private ImageView ivItemAdd, ivItemDetailsGroupArrow;
    private RecyclerView recycler_viewItemDetails;
    private LinearLayout lItemsDetails;

    //Transporter id's
    private ImageView ivTransporterGroupArrow, imValidtyCalender;
    private EditText etName, etLrn, etEwayBill, etDriverName, etTruckNumber, etEWayBillValidity, driverMobileNumber, etAddress;
    private RelativeLayout lTransporter;
    private RelativeLayout rTransporter;


    private Button btnSave;
    boolean clicked = false;
    boolean isEWayBillClicked = false;
    boolean isGrnClick = false;
    boolean isTransporterClick = false;
    boolean isItemDetailsClick = false;

    private Calendar calendar, eWayBillValidityCalender;

    private ArrayList<TransferInItemQtyModel> transferInItemQtyModels = new ArrayList<>();
    private int poQuantity;
    public static final String Preference = "TransferTransporterData";
    android.content.SharedPreferences.Editor Editor;
    private SharedPreferences SharedPreferences;
    private TransferInItemAdapter transferInItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_in_details);
        mContext = TransferInDetailsActivity.this;

        SharedPreferences = mContext.getSharedPreferences(Preference, Context.MODE_PRIVATE);
        Editor = SharedPreferences.edit();

        calendar = Calendar.getInstance();
        eWayBillValidityCalender = Calendar.getInstance();
        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        toolbarTtile = findViewById(R.id.toolbarTtile);

        //Grn Id's
        rGrn = findViewById(R.id.rGrn);
        grnNumber = findViewById(R.id.grnNumber);
        et_totalItems = findViewById(R.id.et_totalItems);
        et_received_date = findViewById(R.id.et_received_date);
        et_value = findViewById(R.id.et_value);
        openQty = findViewById(R.id.openQty);
        grnQty = findViewById(R.id.grnQty);
        balanceQty = findViewById(R.id.balanceQty);
        ivReceivedDateCalender = findViewById(R.id.ivReceivedDateCalender);
        ivGrnGroupArrow = findViewById(R.id.ivGrnGroupArrow);
        lGrn = findViewById(R.id.lGrn);

        //Items id's
        rItemsDetails = findViewById(R.id.rItemsDetails);
        ivItemAdd = findViewById(R.id.ivItemAdd);
        ivItemDetailsGroupArrow = findViewById(R.id.ivItemDetailsGroupArrow);
        recycler_viewItemDetails = findViewById(R.id.recycler_viewItemDetails);
        lItemsDetails = findViewById(R.id.lItemsDetails);

        //Transporter id's
        ivTransporterGroupArrow = findViewById(R.id.ivTransporterGroupArrow);
        imValidtyCalender = findViewById(R.id.imValidtyCalender);
        etName = findViewById(R.id.etName);
        etLrn = findViewById(R.id.etLrn);
        etEwayBill = findViewById(R.id.etEwayBill);
        etDriverName = findViewById(R.id.etDriverName);
        etTruckNumber = findViewById(R.id.etTruckNumber);
        etEWayBillValidity = findViewById(R.id.etEWayBillValidity);
        driverMobileNumber = findViewById(R.id.driverMobileNumber);
        etAddress = findViewById(R.id.etAddress);
        btnSave = findViewById(R.id.btnSave);

        rTransporter = findViewById(R.id.rTransporter);
        lTransporter = findViewById(R.id.lTransporter);


        et_received_date.setClickable(true);
        et_received_date.setOnClickListener(this);

        etEWayBillValidity.setClickable(true);
        etEWayBillValidity.setOnClickListener(this);

        ivItemAdd.setOnClickListener(this);
        imValidtyCalender.setOnClickListener(this);
        ivReceivedDateCalender.setOnClickListener(this);

        btnSave.setOnClickListener(this);

        etName.addTextChangedListener(new GenericTextWatcher(etName));
        et_received_date.addTextChangedListener(new GenericTextWatcher(et_received_date));
        etLrn.addTextChangedListener(new GenericTextWatcher(etLrn));
        etEwayBill.addTextChangedListener(new GenericTextWatcher(etEwayBill));
        etDriverName.addTextChangedListener(new GenericTextWatcher(etDriverName));
        etTruckNumber.addTextChangedListener(new GenericTextWatcher(etTruckNumber));
        etEWayBillValidity.addTextChangedListener(new GenericTextWatcher(etEWayBillValidity));
        driverMobileNumber.addTextChangedListener(new GenericTextWatcher(driverMobileNumber));
        etAddress.addTextChangedListener(new GenericTextWatcher(etAddress));

        rGrn.setOnClickListener(this);
        rTransporter.setOnClickListener(this);
        rItemsDetails.setOnClickListener(this);


    }

    @Override
    public void onRowClicked(int position) {
        TransferInItemQtyModel transferInItemQtyModel = transferInItemQtyModels.get(position);
        Intent gotToProductDetail = new Intent(mContext, TransferInBatchActivity.class);
//        gotToProductDetail.putExtra("position", position);
//        gotToProductDetail.putExtra("poQty", poQuantity);
//        gotToProductDetail.putExtra("openQty", transferInItemQtyModel.getOpenQty());
//        gotToProductDetail.putExtra("lengthOfProduct", transferInItemQtyModel.size());
        startActivity(gotToProductDetail);
//        startActivityForResult(gotToProductDetail, 1);
    }

    @Override
    public void onRowClicked(int position, int value) {

    }

    @Override
    public void onUpdateData(int position, int inQty, int apQty, int balanceQty) {

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
//                    Editor.putString("Name", etName.getText().toString());
//                    Editor.commit();
                    break;
                case R.id.et_received_date:
                    et_received_date.setError(null);
//                    Editor.putString("ReceivedDate", et_received_date.getText().toString());
//                    Editor.commit();
                    break;
                case R.id.etLrn:
                    etLrn.setError(null);
//                    Editor.putString("LRNNumber", etLrn.getText().toString());
//                    Editor.commit();
                    break;
                case R.id.etEwayBill:
                    etEwayBill.setError(null);
//                    Editor.putString("EWayBillNumber", etEwayBill.getText().toString());
//                    Editor.commit();
                    break;
                case R.id.etDriverName:
                    etDriverName.setError(null);
//                    Editor.putString("DriverName", etDriverName.getText().toString());
//                    Editor.commit();
                    break;
                case R.id.etTruckNumber:
                    etTruckNumber.setError(null);
//                    Editor.putString("TruckNumber", etTruckNumber.getText().toString());
//                    Editor.commit();
                    break;
                case R.id.etEWayBillValidity:
                    etEWayBillValidity.setError(null);
//                    Editor.putString("EwayBillValidity", etEWayBillValidity.getText().toString());
//                    Editor.commit();
                    break;
                case R.id.driverMobileNumber:
                    driverMobileNumber.setError(null);
//                    Editor.putString("DriverMobileNumber", driverMobileNumber.getText().toString());
//                    Editor.commit();
                    break;
                case R.id.etAddress:
                    etAddress.setError(null);
//                    Editor.putString("Address", etAddress.getText().toString());
//                    Editor.commit();
                default:
                    break;
            }
        }
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        toolbarTtile.setText(R.string.inventory_toolbar_text);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
//                SharedPreferences = mContext.getSharedPreferences(Preference, Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = SharedPreferences.edit();
//                editor.clear();
//                editor.apply();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        addTranferInDetails();
    }

    private void addTranferInDetails() {
        String transfer_in_items = Util.getAssetJsonResponse(mContext, "transfer_in_items");
        clearRealm();
        new RealmController().saveTransferInDetails(transfer_in_items);
        getExpandableData();
    }

    private void getExpandableData() {
        transferInItemQtyModels.clear();
        Realm realm = Realm.getDefaultInstance();

        RealmTransferDetail realmTransferDetail = realm.where(RealmTransferDetail.class).findFirst();
        if (realmTransferDetail != null) {
            try {
                int totalItem = (int) realmTransferDetail.getTotalItems();
                int value = (int) realmTransferDetail.getValue();
                int poQt = (int) realmTransferDetail.getPoQty();
                poQuantity = poQt;
                int openQt = (int) realmTransferDetail.getOpenQty();
                int balanceQt = (int) realmTransferDetail.getBalanceQty();

                grnNumber.setText(realmTransferDetail.getGrnNumber());

                et_totalItems.setText(totalItem + "");
                et_value.setText(realmTransferDetail.getValue() + "");
                openQty.setText(openQt + "");
                grnQty.setText(poQt + "");
                balanceQty.setText(balanceQt + "");

                //SharedPreference Values
                String name = SharedPreferences.getString("Name", "");
                String receivedDate = SharedPreferences.getString("ReceivedDate", "");
                String lRnNumber = SharedPreferences.getString("LRNNumber", "");
                String eWayBillNumber = SharedPreferences.getString("EWayBillNumber", "");
                String driverName = SharedPreferences.getString("DriverName", "");
                String truckNumber = SharedPreferences.getString("TruckNumber", "");
                String ewayBillValidity = SharedPreferences.getString("EwayBillValidity", "");
                String driverMobNumber = SharedPreferences.getString("DriverMobileNumber", "");
                String address = SharedPreferences.getString("Address", "");


                if (TextUtils.isEmpty(receivedDate)) {
                    et_received_date.setText(realmTransferDetail.getReceivedDate());
                } else {
                    et_received_date.setText(receivedDate);
                }

                if (TextUtils.isEmpty(name)) {
                    etName.setText(realmTransferDetail.getTransporterName());
                } else {
                    etName.setText(name);
                }

                if (TextUtils.isEmpty(lRnNumber)) {
                    etLrn.setText(realmTransferDetail.getTransporterLRName());
                } else {
                    etLrn.setText(lRnNumber);
                }

                if (TextUtils.isEmpty(eWayBillNumber)) {
                    etTruckNumber.setText(realmTransferDetail.getTransporterEWayBillNumber());
                } else {
                    etTruckNumber.setText(eWayBillNumber);
                }


                if (TextUtils.isEmpty(driverName)) {
                    etDriverName.setText(realmTransferDetail.getTransporterDriverName());
                } else {
                    etDriverName.setText(driverName);
                }

                if (TextUtils.isEmpty(truckNumber)) {
                    etTruckNumber.setText(realmTransferDetail.getTransporterTruckNumber());
                } else {
                    etTruckNumber.setText(truckNumber);
                }

                if (TextUtils.isEmpty(ewayBillValidity)) {
                    etEWayBillValidity.setText(realmTransferDetail.getTransporterEWayBillValidityDate());
                } else {
                    etEWayBillValidity.setText(ewayBillValidity.trim());
                }

                if (TextUtils.isEmpty(driverMobNumber)) {
                    driverMobileNumber.setText(realmTransferDetail.getTransporterDriverMobileNumber());

                } else {
                    driverMobileNumber.setText(driverMobNumber);

                }

                if (TextUtils.isEmpty(address)) {
                    etAddress.setText(realmTransferDetail.getTransporterAddress());
                } else {
                    etAddress.setText(address);
                }

                if (TextUtils.isEmpty(eWayBillNumber)) {
                    etEwayBill.setText(realmTransferDetail.getTransporterEWayBillNumber());
                } else {
                    etEwayBill.setText(eWayBillNumber);
                }

                Log.e(TAG, "PoItemDetails:::" + realmTransferDetail.getPoItemDetails());

                //Items Details
                JSONArray array = new JSONArray(realmTransferDetail.getPoItemDetails());
                for (int j = 0; j < array.length(); j++) {
                    JSONObject jsonObject1 = array.optJSONObject(j);
                    TransferInItemQtyModel transferInItemQtyModel = new TransferInItemQtyModel();
                    transferInItemQtyModel.setMaterialCode(jsonObject1.optString("materialCode"));
                    transferInItemQtyModel.setMaterialName(jsonObject1.optString("materialName"));
                    transferInItemQtyModel.setOpenQty(jsonObject1.optDouble("openQty"));
                    transferInItemQtyModel.setInQty(jsonObject1.optDouble("inQty"));
                    transferInItemQtyModel.setApQty(jsonObject1.optDouble("apQty"));
                    transferInItemQtyModel.setBalanceQty(jsonObject1.optDouble("balanceQty"));
                    transferInItemQtyModel.setIsBatch(jsonObject1.optDouble("isBatch"));
                    transferInItemQtyModel.setgRNItemInfoDetails(jsonObject1.optJSONObject("gRNItemInfoDetails").toString());
                    transferInItemQtyModels.add(transferInItemQtyModel);
                }
                setItemDetails();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void setItemDetails() {
        recycler_viewItemDetails.setLayoutManager(new LinearLayoutManager(mContext));
        transferInItemAdapter = new TransferInItemAdapter(mContext, transferInItemQtyModels, this, this);
        recycler_viewItemDetails.setAdapter(transferInItemAdapter);
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
            case R.id.ivReceivedDateCalender:
                clicked = true;
                clickAddDate();
                break;
            case R.id.et_received_date:
                clicked = true;
                clickAddDate();
                break;
            case R.id.etEWayBillValidity:
                isEWayBillClicked = true;
                seteWayBillValidityCalenderAddDate();

                break;
            case R.id.ivItemAdd:

                break;
            case R.id.imValidtyCalender:
                isEWayBillClicked = true;
                seteWayBillValidityCalenderAddDate();
                break;
            case R.id.btnSave:
                checkValidation();
                break;
            case R.id.rGrn:
                if (isGrnClick) {
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    ivGrnGroupArrow.setImageResource(R.drawable.ic_action_arrow_right_blue);


                    isGrnClick = false;
                } else {
                    lGrn.setVisibility(View.VISIBLE);
                    lTransporter.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    ivGrnGroupArrow.setImageResource(R.drawable.ic_action_arrow_down_blue);

                    isGrnClick = true;
                }
                break;
            case R.id.rTransporter:
                if (isTransporterClick) {
                    lTransporter.setVisibility(View.GONE);
                    lGrn.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    ivTransporterGroupArrow.setImageResource(R.drawable.ic_action_arrow_right_blue);


                    isTransporterClick = false;
                } else {
                    lTransporter.setVisibility(View.VISIBLE);
                    lGrn.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    ivTransporterGroupArrow.setImageResource(R.drawable.ic_action_arrow_down_blue);
                    ivGrnGroupArrow.setImageResource(R.drawable.ic_action_arrow_right_blue);
                    ivItemDetailsGroupArrow.setImageResource(R.drawable.ic_action_arrow_right_blue);

                    isTransporterClick = true;
                }
                break;
            case R.id.rItemsDetails:
                if (isItemDetailsClick) {
                    lItemsDetails.setVisibility(View.GONE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);

                    ivItemDetailsGroupArrow.setImageResource(R.drawable.ic_action_arrow_right_blue);

                    isItemDetailsClick = false;
                } else {
                    lItemsDetails.setVisibility(View.VISIBLE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    ivItemDetailsGroupArrow.setImageResource(R.drawable.ic_action_arrow_down_blue);


                    isItemDetailsClick = true;
                }

            default:
                break;

        }
    }

    private void checkValidation() {
        boolean isFail = false;

        String name = etName.getText().toString();
        String receivedDate = et_received_date.getText().toString();
        String LRNumber = etLrn.getText().toString();
        String eWayBillNumber = etEwayBill.getText().toString();
        String driverName = etDriverName.getText().toString();
        String truckNumber = etTruckNumber.getText().toString();
        String etEWayBillValidityDate = etEWayBillValidity.getText().toString();
        String driverMobileNum = driverMobileNumber.getText().toString();
        String address = etAddress.getText().toString();


        if (TextUtils.isEmpty(name)) {
            isFail = true;
            etName.setError(getResources().getString(R.string.iv_error_name));
        }
        if (TextUtils.isEmpty(receivedDate)) {
            isFail = true;
            et_received_date.setError("PLease enter received date");
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

        if (TextUtils.isEmpty(address)) {
            isFail = true;
            etAddress.setError(getResources().getString(R.string.iv_error_address));
        }

        if (!isFail) {
            etName.setError(null);
            et_received_date.setError(null);
            etLrn.setError(null);
            etEwayBill.setError(null);
            etDriverName.setError(null);
            etTruckNumber.setError(null);
            etEWayBillValidity.setError(null);
            driverMobileNumber.setError(null);
            etAddress.setError(null);

//            if (TextUtils.isEmpty(cardClick)) {
//                submitGRNDetails();
//            }


        } else {
            Toast.makeText(mContext, "Please fill all required (*) fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void clickAddDate() {
        if (clicked) {
            android.app.DatePickerDialog datePicker = new android.app.DatePickerDialog(mContext, new android.app.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    et_received_date.setText(DateAndTimeUtil.toCustomStringDateAndTime(calendar, DATE_AND_TIME_FORMAT_INDIA));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
            clicked = false;
        }
    }

    public void seteWayBillValidityCalenderAddDate() {
        if (isEWayBillClicked) {
            android.app.DatePickerDialog datePicker = new android.app.DatePickerDialog(mContext, new android.app.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    eWayBillValidityCalender.set(Calendar.YEAR, year);
                    eWayBillValidityCalender.set(Calendar.MONTH, month);
                    eWayBillValidityCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    etEWayBillValidity.setText(DateAndTimeUtil.toCustomStringDateAndTime(eWayBillValidityCalender, DATE_AND_TIME_FORMAT_INDIA));
                }
            }, eWayBillValidityCalender.get(Calendar.YEAR), eWayBillValidityCalender.get(Calendar.MONTH), eWayBillValidityCalender.get(Calendar.DAY_OF_MONTH));
            datePicker.show();
            isEWayBillClicked = false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void clearRealm() {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.delete(RealmTransferDetail.class);

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
