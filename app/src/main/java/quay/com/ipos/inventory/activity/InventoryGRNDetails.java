package quay.com.ipos.inventory.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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

public class InventoryGRNDetails extends AppCompatActivity implements InitInterface, View.OnClickListener, MyListener, AttachmentListener {
    private static final String TAG = InventoryGRNDetails.class.getSimpleName();
    private Toolbar toolbar;
    private Button btnAction, btnSave;
    private Context mContext;
    private RelativeLayout rGrn, rTransporter, rItemsDetails, rIncco, rAttachment;
    private LinearLayout lGrn, lItemsDetails, llIncoTerms, llTermsC;
    private RelativeLayout lTransporter;

    private RecyclerView recycler_viewItemDetail, rvIncco, recycler_viewPayment, recycler_viewTerms, rvAttachment;
    //Inventory Grn
    InventoryGrnItemsListAdapter itemListDataAdapter;
    InventoryGrnInccoAdapter inventoryGrnInccoAdapter;
    InventoryAttachmentAdapter inventoryAttachmentAdapter;

    MilestonePOListAdapter milestonePOListAdapter;
    TermsPOListAdapter termsPOListAdapter;

    ArrayList<GrnItemQtyModel> grnListModels = new ArrayList<>();
    ArrayList<GrnInccoTermsModel> grnInccoTermsModels = new ArrayList<>();
    ArrayList<GrnAttachment> grnAttachments = new ArrayList<>();

    ArrayList<POPaymentTerms> poPaymentTerms = new ArrayList<>();
    ArrayList<POTermsCondition> poTermsConditions = new ArrayList<>();

    String poNumber, businessPlaceId;
    private EditText grnNumber, et_received_date, et_totalItems, et_value, poQty, openQty, balanceQty, etName, etLrn, etTrackNumber1, etEwayBill, etTruckNumber2, etDriverName, driverMobileNumber, etAddress;
    boolean isGrnClick = false;
    boolean isTransporterClick = false;
    boolean isItemDetailsClick = false;
    boolean isInccoClick = false;
    boolean isAttachmentClick = false;
    private String transporterEWayBillValidityDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grn_expandable);
        mContext = InventoryGRNDetails.this;
        Intent i = getIntent();
        poNumber = i.getStringExtra("poNumber");

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
        etTrackNumber1 = findViewById(R.id.etEwayBill);
        etTruckNumber2 = findViewById(R.id.etTruckNumber2);
        etDriverName = findViewById(R.id.etDriverName);
        driverMobileNumber = findViewById(R.id.driverMobileNumber);
        etAddress = findViewById(R.id.etAddress);
        etEwayBill = findViewById(R.id.etEwayBill);

        recycler_viewItemDetail = findViewById(R.id.recycler_viewItemDetails);
        rvIncco = findViewById(R.id.rvIncco);
        rvAttachment = findViewById(R.id.rvAttachment);

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


        //Getting GRN details from server
        getGRNDetails();
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
            case R.id.btnAction:
                Intent i = new Intent(mContext, InventoryWorkFlowActivity.class);
                startActivity(i);
                break;
            case R.id.btnSave:
                submitGRNDetails();
                break;
            case R.id.rGrn:
                if (isGrnClick) {
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);

                    isGrnClick = false;
                } else {
                    lGrn.setVisibility(View.VISIBLE);
                    lTransporter.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);
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
                    isTransporterClick = false;
                } else {
                    lTransporter.setVisibility(View.VISIBLE);
                    lGrn.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);
                    isTransporterClick = true;
                }


                break;
            case R.id.rItemsDetails:
                if (isItemDetailsClick) {
                    lItemsDetails.setVisibility(View.GONE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);

                    isItemDetailsClick = false;
                } else {
                    lItemsDetails.setVisibility(View.VISIBLE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);
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
                    isInccoClick = false;
                } else {
                    llIncoTerms.setVisibility(View.VISIBLE);
                    lItemsDetails.setVisibility(View.GONE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    llTermsC.setVisibility(View.GONE);
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

                    isAttachmentClick = false;
                } else {
                    llTermsC.setVisibility(View.VISIBLE);
                    lGrn.setVisibility(View.GONE);
                    lTransporter.setVisibility(View.GONE);
                    llIncoTerms.setVisibility(View.GONE);
                    lItemsDetails.setVisibility(View.GONE);

                    isAttachmentClick = true;
                }

            default:
                break;

        }
    }


    private void getExpandableData() {
        Realm realm = Realm.getDefaultInstance();
        RealmGRNDetails realmGRNDetails = realm.where(RealmGRNDetails.class).findFirst();
        if (realmGRNDetails != null) {
            try {
                grnNumber.setText(realmGRNDetails.getGrnNumber());
                et_received_date.setText(realmGRNDetails.getReceivedDate());
                et_totalItems.setText(realmGRNDetails.getTotalItems() + "");
                et_value.setText(realmGRNDetails.getValue() + "");
                poQty.setText(realmGRNDetails.getPoQty() + "");
                openQty.setText(realmGRNDetails.getOpenQty() + "");
                balanceQty.setText(realmGRNDetails.getBalanceQty() + "");
                transporterEWayBillValidityDate = realmGRNDetails.getTransporterEWayBillValidityDate();

                etName.setText(realmGRNDetails.getTransporterName());
                etLrn.setText(realmGRNDetails.getTransporterLRName());
                etTrackNumber1.setText(realmGRNDetails.getTransporterDriverMobileNumber());
                etTruckNumber2.setText(realmGRNDetails.getTransporterTruckNumber());
                etDriverName.setText(realmGRNDetails.getTransporterDriverName());
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
                    grnItemQtyModel.setgRNItemInfoDetails(jsonObject1.optJSONObject("gRNItemInfoDetails").toString());
                    grnListModels.add(grnItemQtyModel);
                }


                //Incco terms
                JSONArray jsonArray = new JSONArray(realmGRNDetails.getPoIncoTerms());
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject jsonObject1 = jsonArray.optJSONObject(j);
                    GrnInccoTermsModel grnInccoTermsModel = new GrnInccoTermsModel();
                    grnInccoTermsModel.setGrnIncoDetail(jsonObject1.optString("grnIncoDetail"));
                    grnInccoTermsModel.setGrnPayBySender(jsonObject1.optBoolean("grnPayBySender"));
                    grnInccoTermsModel.setGrnPayByReceiver(jsonObject1.optBoolean("grnPayByReceiver"));
                    grnInccoTermsModel.setGrnPayAmount(jsonObject1.optDouble("grnPayAmount"));
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
                setAttahcments();
                // setPaymentTerms();
                //  setTermsCondition();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    public void getGRNDetails() {

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        JSONObject jsonObject1 = new JSONObject();

        try {
            jsonObject1.put("empCode", Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("businessPlaceId", "1");
            jsonObject1.put("poNumber", "GRN18000001");
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
                //  dismissProgress();
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
        inventoryGrnInccoAdapter = new InventoryGrnInccoAdapter(mContext, grnInccoTermsModels);
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
                jsonObject1.put("grnIncoDetail", grnInccoTermsModels.get(j).getGrnIncoDetail());
                jsonObject1.put("grnPayBySender", grnInccoTermsModels.get(j).isGrnPayBySender());
                jsonObject1.put("grnPayByReceiver", grnInccoTermsModels.get(j).isGrnPayByReceiver());
                jsonObject1.put("grnPayAmount", grnInccoTermsModels.get(j).getGrnPayAmount());

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
            jsonObject.put("transporterTruckNumber", etTrackNumber1.getText().toString());
            jsonObject.put("transporterEWayBillNumber", etEwayBill.getText().toString());
            jsonObject.put("transporterEWayBillValidityDate", transporterEWayBillValidityDate);
            jsonObject.put("transporterDriverName", etDriverName.getText().toString());
            jsonObject.put("transporterDriverMobileNumber", driverMobileNumber.getText().toString());
            jsonObject.put("transporterAddress", etAddress.getText().toString());
            jsonObject.put("poItemDetails", poDetails);
            jsonObject.put("poIncoTerms", IncoTermsArray);
            jsonObject.put("poPaymentTermsType", new JSONArray());
            jsonObject.put("poTermsAndConditions", new JSONArray());
            jsonObject.put("poAttachments", jsonArrayAttachments);
            jsonObject.put("employeeCode", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new RealmController().saveGRNDetails(jsonObject.toString());

    }


    public void submitGRNDetails() {

        createJson();
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        JSONObject jsonObject1 = new JSONObject();


     /*   //show
        Realm realm=Realm.getDefaultInstance();
        RealmGRNDetails realmGRNDetails=realm.where(RealmGRNDetails.class).equalTo("grnNumber",grnNumber.getText().toString()).findFirst();
        Gson gson= new GsonBuilder().create();
        String json=gson.toJson(realmGRNDetails);

        JSONObject jsonObject=new JSONObject(json);

      JSONArray  array=  jsonObject.getJSONArray("poItemDetails");

       JSONObject jsonObject2= array.getJSONObject(position);

       JSONObject jsonObject3=jsonObject2.getJSONObject("gRnItemInfoDetails");

       //----------------------------------------------------show end
       //update-----start


        JSONArray jsArryBatch=new JSONArray();

        JSONObject position=new JSONObject();
        position.put("tabTitle",,"");
        jsArryBatch.put(position);
        jsonObject3.put("data",jsArryBatch);
        jsonObject2.put("gRnItemInfoDetails",jsonObject2);

        new RealmController().saveGRNDetails(jsonObject2.toString());*/

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, "");
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
                //  dismissProgress();
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
        GrnItemQtyModel grnItemQtyModel = grnListModels.get(position);
        Intent gotToProductDetail = new Intent(mContext, InventoryProduct.class);
        gotToProductDetail.putExtra("position", position);
        gotToProductDetail.putExtra("openQty",grnItemQtyModel.getOpenQty());
        startActivityForResult(gotToProductDetail, 1);

    }

    @Override
    public void onRowClicked(int position, int value) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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
}
