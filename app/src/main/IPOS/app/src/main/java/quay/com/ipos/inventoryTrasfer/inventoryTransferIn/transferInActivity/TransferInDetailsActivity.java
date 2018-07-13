package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInActivity;

import android.app.Activity;
import android.app.ProgressDialog;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import io.realm.Realm;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.TransferInDetailViewAdapter;
import quay.com.ipos.inventory.fragment.InventoryProduct;
import quay.com.ipos.inventory.modal.GrnItemQtyModel;
import quay.com.ipos.inventoryTrasfer.TransferStepsActivity;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter.TransferInItemAdapter;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.RealmTransferDetail;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInItemQtyModel;
import quay.com.ipos.listeners.DataUpdateListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.ui.CustomTextView;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.DateAndTimeUtil;
import quay.com.ipos.utility.Prefs;

import static quay.com.ipos.utility.DateAndTimeUtil.DATE_AND_TIME_FORMAT_INDIA;
import static quay.com.ipos.utility.DateAndTimeUtil.DATE_AND_TIME_FORMAT_SIMPLE;


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

    TransferInItemAdapter transferInItemAdapter;
    private String poNumber, getGrnNumber, cardClick, supplierName;
    private String transporterEWayBillValidityDate;
    private TransferInDetailViewAdapter transferInDetailViewAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_in_details);
        mContext = TransferInDetailsActivity.this;

        SharedPreferences = mContext.getSharedPreferences(Preference, Context.MODE_PRIVATE);
        Editor = SharedPreferences.edit();

        calendar = Calendar.getInstance();
        eWayBillValidityCalender = Calendar.getInstance();

        Intent i = getIntent();
        poNumber = i.getStringExtra("poNumber");
        getGrnNumber = i.getStringExtra("grnNumber");
        cardClick = i.getStringExtra("cardClick");
        supplierName = i.getStringExtra("supplierName");


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
        if (TextUtils.isEmpty(cardClick)) {
            TransferInItemQtyModel transferInItemQtyModel = transferInItemQtyModels.get(position);
            Intent gotToProductDetail = new Intent(mContext, TransferInBatchActivity.class);
            gotToProductDetail.putExtra("position", position);
            gotToProductDetail.putExtra("poQty", poQuantity);
            gotToProductDetail.putExtra("openQty", transferInItemQtyModel.getOpenQty());
            gotToProductDetail.putExtra("lengthOfProduct", transferInItemQtyModels.size());
            startActivityForResult(gotToProductDetail, 1);
        }


//        if (TextUtils.isEmpty(cardClick)) {
//            TransferInItemQtyModel transferInItemQtyModel = transferInItemQtyModels.get(position);
//            Intent gotToProductDetail = new Intent(mContext, TransferInBatchActivity.class);
//            gotToProductDetail.putExtra("position", position);
//            gotToProductDetail.putExtra("poQty", poQuantity);
//            gotToProductDetail.putExtra("openQty", transferInItemQtyModel.getOpenQty());
//            gotToProductDetail.putExtra("lengthOfProduct", transferInItemQtyModels.size());
//            startActivityForResult(gotToProductDetail, 1);
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                transferInItemQtyModels.clear();
                getExpandableData();
            }
        }

    }

    @Override
    public void onRowClicked(int position, int value) {

    }

    @Override
    public void onUpdateData(final int position, final int inQty, final int apQty, final int balanceQty) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int inQts = inQty;
                int appQts = apQty;
                Log.e(TAG, "position***" + position);
                Log.e(TAG, "inQty***" + inQty);
                Log.e(TAG, "appQty***" + apQty);
                Log.e(TAG, "balanceQty***" + balanceQty);

                Realm realm = Realm.getDefaultInstance();
                RealmTransferDetail realmTransferDetail = realm.where(RealmTransferDetail.class).findFirst();
                try {


                    Gson gson = new GsonBuilder().create();

                    try {
                        String json = gson.toJson(realm.copyFromRealm(realmTransferDetail));
                        JSONObject jsonObject = new JSONObject(json);
                        JSONArray poItemDetailsArray = new JSONArray(jsonObject.optString("poItemDetails"));
                        JSONArray arrayPoAttachment = new JSONArray(jsonObject.optString("poAttachments"));
                        JSONArray arrayPoIncco = new JSONArray(jsonObject.optString("poIncoTerms"));
                        JSONArray arrayPayTerms = new JSONArray(jsonObject.optString("poPaymentTerms"));
                        JSONArray arrayTermsAndCondition = new JSONArray(jsonObject.optString("poTermsAndConditions"));
                        JSONObject jsonObject2 = poItemDetailsArray.getJSONObject(position);


                        jsonObject2.put("inQty", inQty);
                        jsonObject2.put("apQty", apQty);
                        jsonObject2.put("balanceQty", balanceQty);

                        double unitP = 0;
                        double unitPrice = jsonObject2.getDouble("unitPrice");
                        double totalUnitPrice = unitPrice * (inQty + apQty);
                        unitP += totalUnitPrice;


                        poItemDetailsArray.put(position, jsonObject2);

                        int quanOpenTotal = 0, quanBalanceTotal = 0, quanIn = 0, quanApp = 0, quanPo = 0, totalGRN = 0, in = 0, app = 0;
                        double unitPri = 0, upIn = 0, upApp = 0;
                        for (int k = 0; k < poItemDetailsArray.length(); k++) {
                            JSONObject jsonObject1 = poItemDetailsArray.optJSONObject(k);
                            quanPo += jsonObject1.optInt("poQty");
                            quanOpenTotal += jsonObject1.optInt("openQty");
                            quanBalanceTotal += jsonObject1.optInt("balanceQty");
                            in += jsonObject1.optInt("inQty");
                            app += jsonObject1.optInt("apQty");
                            double up = jsonObject1.getDouble("unitPrice");

                            upIn += jsonObject1.optInt("inQty") * up;
                            upApp += jsonObject1.optInt("apQty") * up;

                        }

                        jsonObject.put("poQty", quanPo + (in + app));
                        jsonObject.put("value", upIn + upApp);
                        jsonObject.put("balanceQty", quanBalanceTotal);
                        jsonObject.put("poItemDetails", poItemDetailsArray);
                        jsonObject.put("poAttachments", arrayPoAttachment);
                        jsonObject.put("poIncoTerms", arrayPoIncco);
                        jsonObject.put("poPaymentTerms", arrayPayTerms);
                        jsonObject.put("poTermsAndConditions", arrayTermsAndCondition);

                        new RealmController().saveTransferInDetails(jsonObject.toString().replaceAll("\\\\", ""));
                        transferInItemQtyModels.clear();


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    realm.close();
                }
                getExpandableData();
            }
        });

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
            switch (view.getId()) {
                case R.id.etName:
                    etName.setError(null);
                    Editor.putString("Name", etName.getText().toString());
                    Editor.commit();
                    break;
                case R.id.et_received_date:
                    et_received_date.setError(null);
                    Editor.putString("ReceivedDate", et_received_date.getText().toString());
                    Editor.commit();
                    break;
                case R.id.etLrn:
                    etLrn.setError(null);
                    Editor.putString("LRNNumber", etLrn.getText().toString());
                    Editor.commit();
                    break;
                case R.id.etEwayBill:
                    etEwayBill.setError(null);
                    Editor.putString("EWayBillNumber", etEwayBill.getText().toString());
                    Editor.commit();
                    break;
                case R.id.etDriverName:
                    etDriverName.setError(null);
                    Editor.putString("DriverName", etDriverName.getText().toString());
                    Editor.commit();
                    break;
                case R.id.etTruckNumber:
                    etTruckNumber.setError(null);
                    Editor.putString("TruckNumber", etTruckNumber.getText().toString());
                    Editor.commit();
                    break;
                case R.id.etEWayBillValidity:
                    etEWayBillValidity.setError(null);
                    Editor.putString("EwayBillValidity", etEWayBillValidity.getText().toString());
                    Editor.commit();
                    break;
                case R.id.driverMobileNumber:
                    driverMobileNumber.setError(null);
                    Editor.putString("DriverMobileNumber", driverMobileNumber.getText().toString());
                    Editor.commit();
                    break;
                case R.id.etAddress:
                    etAddress.setError(null);
                    Editor.putString("Address", etAddress.getText().toString());
                    Editor.commit();
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
                SharedPreferences = mContext.getSharedPreferences(Preference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = SharedPreferences.edit();
                editor.clear();
                editor.apply();
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
            addTranferInDetails();
        } else {

            etName.setEnabled(false);
            etLrn.setEnabled(false);
            et_received_date.setEnabled(false);
            etEwayBill.setEnabled(false);
            etDriverName.setEnabled(false);
            etTruckNumber.setEnabled(false);
            etEWayBillValidity.setEnabled(false);
            driverMobileNumber.setEnabled(false);
            etAddress.setEnabled(false);
            btnSave.setVisibility(View.GONE);
            ivItemAdd.setVisibility(View.GONE);

            cardTransferInDetail();

        }

    }

    public void cardTransferInDetail() {

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        JSONObject jsonObject1 = new JSONObject();

        try {
            jsonObject1.put("empCode", Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("businessPlaceId", String.valueOf(Prefs.getIntegerPrefs("WorklocationID")));
            jsonObject1.put("tranID", getGrnNumber);
            jsonObject1.put("isGRN", true);
            jsonObject1.put("isGRNOrQC", "trgrn");
            jsonObject1.put("tran", poNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.GET_TRANSFER_OUT_GRN_SUMMARY_DETAIL;

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
                                Log.e(TAG, "Response***" + response.body().toString());
                            }
                        });

                        final String responseData = response.body().string();
                        if (responseData != null) {
                            clearRealm();
                            new RealmController().saveTransferInDetails(responseData);

                            //saveResponseLocalCreateOrder(jsonObject,requestId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getGrnCardExpandableData();
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

    private void getGrnCardExpandableData() {
        Realm realm = Realm.getDefaultInstance();

        RealmTransferDetail realmTransferDetail = realm.where(RealmTransferDetail.class).findFirst();

//        if (getGrnNumber != null) {
//            realmGRNDetails = realm.where(RealmGRNDetails.class).equalTo("grnNumber", getGrnNumber + "").findFirst();
//        } else {
//            realmGRNDetails = realm.where(RealmGRNDetails.class).findFirst();
//        }

        if (realmTransferDetail != null) {
            try {
                int totalItem = (int) realmTransferDetail.getTotalItems();
                int value = (int) realmTransferDetail.getValue();
                int poQt = (int) realmTransferDetail.getPoQty();
                poQuantity = poQt;
                int openQt = (int) realmTransferDetail.getOpenQty();
                int balanceQt = (int) realmTransferDetail.getBalanceQty();

                grnNumber.setText(realmTransferDetail.getGrnNumber());
                et_received_date.setText(realmTransferDetail.getReceivedDate());
                et_totalItems.setText(totalItem + "");
                et_value.setText(realmTransferDetail.getValue() + "");
                grnQty.setText(openQt + "");
                openQty.setText(poQt + "");
                balanceQty.setText(balanceQt + "");
                transporterEWayBillValidityDate = realmTransferDetail.getTransporterEWayBillValidityDate();

                etName.setText(realmTransferDetail.getTransporterName());
                et_received_date.setText(realmTransferDetail.getReceivedDate());
                etLrn.setText(realmTransferDetail.getTransporterLRName());
                etTruckNumber.setText(realmTransferDetail.getTransporterTruckNumber());
                etDriverName.setText(realmTransferDetail.getTransporterDriverName());
                etEWayBillValidity.setText(realmTransferDetail.getTransporterEWayBillValidityDate());
                driverMobileNumber.setText(realmTransferDetail.getTransporterDriverMobileNumber());
                etAddress.setText(realmTransferDetail.getTransporterAddress());
                etEwayBill.setText(realmTransferDetail.getTransporterEWayBillNumber());

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

                setGrnItemViewDetail();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    private void setGrnItemViewDetail() {
        recycler_viewItemDetails.setLayoutManager(new LinearLayoutManager(mContext));
        transferInDetailViewAdapter = new TransferInDetailViewAdapter(mContext, transferInItemQtyModels);
        recycler_viewItemDetails.setAdapter(transferInDetailViewAdapter);
    }

    private void addTranferInDetails() {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("empCode", Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("businessPlaceId", String.valueOf(Prefs.getIntegerPrefs("WorklocationID")));
            jsonObject1.put("tranID", poNumber);
            jsonObject1.put("isGRN", false);
            jsonObject1.put("isGRNOrQC", "NA");
            jsonObject1.put("tran", "NA");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.GET_TRANSFER_OUT_GRN_SUMMARY_DETAIL;

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
                                Log.e(TAG, "Response***" + response.body().toString());
                            }
                        });

                        final String responseData = response.body().string();
                        if (responseData != null) {
                            clearRealm();
                            new RealmController().saveTransferInDetails(responseData);
                            new RealmController().saveTransferInBatchDetails(responseData);

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

            if (TextUtils.isEmpty(cardClick)) {
                submitTransferInDetailsData();
            }


        } else {
            Toast.makeText(mContext, "Please fill all required (*) fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitTransferInDetailsData() {
        double totalItem = Double.parseDouble(et_totalItems.getText().toString());
        double value = Double.parseDouble(et_value.getText().toString());
        double poQt = Double.parseDouble(grnQty.getText().toString());
        double openQt = Double.parseDouble(openQty.getText().toString());
        double balanceQt = Double.parseDouble(balanceQty.getText().toString());

        JSONObject jsonObject = new JSONObject();
        JSONArray poDetails = new JSONArray();
        try {
            for (int j = 0; j < transferInItemQtyModels.size(); j++) {

                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("materialCode", transferInItemQtyModels.get(j).getMaterialCode());
                jsonObject1.put("materialName", transferInItemQtyModels.get(j).getMaterialName());
                jsonObject1.put("openQty", transferInItemQtyModels.get(j).getOpenQty());
                jsonObject1.put("inQty", transferInItemQtyModels.get(j).getInQty());
                jsonObject1.put("apQty", transferInItemQtyModels.get(j).getApQty());
                jsonObject1.put("balanceQty", transferInItemQtyModels.get(j).getBalanceQty());
                if (transferInItemQtyModels.get(j).getIsBatch() == 0) {
                    jsonObject1.put("isBatch", transferInItemQtyModels.get(j).getIsBatch());
                } else {
                    jsonObject1.put("isBatch", transferInItemQtyModels.get(j).getIsBatch());
                }
                jsonObject1.put("gRNItemInfoDetails", new JSONObject(transferInItemQtyModels.get(j).getgRNItemInfoDetails()));

                poDetails.put(jsonObject1);
            }

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

            if (value == 0) {
                jsonObject.put("value", 0);
            } else {
                jsonObject.put("value", value);
            }

            jsonObject.put("receivedDate", DateAndTimeUtil.toCustomStringDateAndTime(calendar, DATE_AND_TIME_FORMAT_SIMPLE));
            jsonObject.put("transporterName", etName.getText().toString());
            jsonObject.put("transporterLRName", etLrn.getText().toString());
            jsonObject.put("transporterTruckNumber", etTruckNumber.getText().toString());
            jsonObject.put("transporterEWayBillNumber", etEwayBill.getText().toString());
            jsonObject.put("transporterEWayBillValidityDate", DateAndTimeUtil.toCustomStringDateAndTime(eWayBillValidityCalender, DATE_AND_TIME_FORMAT_SIMPLE));
            jsonObject.put("transporterDriverName", etDriverName.getText().toString());
            jsonObject.put("transporterDriverMobileNumber", driverMobileNumber.getText().toString());
            jsonObject.put("transporterAddress", etAddress.getText().toString());
            jsonObject.put("poItemDetails", poDetails);
            jsonObject.put("poPaymentTermsType", "");
            jsonObject.put("poPaymentTerms", new JSONArray());
            jsonObject.put("poTermsAndConditions", new JSONArray());
            jsonObject.put("poIncoTerms", new JSONArray());
            jsonObject.put("poAttachments", new JSONArray());
            jsonObject.put("employeeCode", Prefs.getStringPrefs(Constants.employeeCode));
            Log.e(TAG, "Requuest::" + jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setCancelable(false);
        progressDialog.show();

        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject.toString().replaceAll("\\\\", ""));
        String url = IPOSAPI.GET_TRANSFER_OUT_GRN_SUBMIT_DETAIL;
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
                                TransferStepsActivity.fa.finish();
                                Intent i = new Intent(mContext, TransferStepsActivity.class);
                                i.putExtra("newGRNCreated", "GrnCreated");
                                i.putExtra("poNumber", poNumber);
                                i.putExtra("isGrn", "0");
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(i);
                                finish();

                            }
                        });

                        final String responseData = response.body().string();
                        if (responseData != null) {

                            //saveResponseLocalCreateOrder(jsonObject,requestId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

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
        SharedPreferences = mContext.getSharedPreferences(Preference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SharedPreferences.edit();
        editor.clear();
        editor.apply();
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
