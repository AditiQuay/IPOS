package quay.com.ipos.inventory.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.AttachmentsPOListAdapter;
import quay.com.ipos.inventory.adapter.CustomGrnTermsExpandableListAdapter;
import quay.com.ipos.inventory.adapter.INCOTermsPOListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailsPOListAdapter;
import quay.com.ipos.inventory.adapter.MilestonePOListAdapter;
import quay.com.ipos.inventory.adapter.TermsPOListAdapter;
import quay.com.ipos.inventory.modal.POAttachments;
import quay.com.ipos.inventory.modal.POIncoTerms;
import quay.com.ipos.inventory.modal.POItemDetail;
import quay.com.ipos.inventory.modal.POPaymentTerms;
import quay.com.ipos.inventory.modal.POTermsCondition;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmGRNDetails;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;

/**
 * Created by niraj.kumar on 6/14/2018.
 */

public class InventoryGRNDetails extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private Toolbar toolbar;
    private Button btnAction, btnSave;
    private Context mContext;
    private RelativeLayout rGrn,rTransporter,rItemsDetails,rIncco,rAttachment;
    private LinearLayout lGrn,lItemsDetails,llIncoTerms,llTermsC;
    private RelativeLayout lTransporter;

    private RecyclerView recycler_viewItemDetail,recycler_viewInco,recycler_viewPayment,recycler_viewTerms,recycler_viewAttachment;
    ItemsDetailsPOListAdapter itemListDataAdapter;
    INCOTermsPOListAdapter incoTermsPOListAdapter;
    MilestonePOListAdapter milestonePOListAdapter;
    TermsPOListAdapter termsPOListAdapter;
    AttachmentsPOListAdapter attachmentsPOListAdapter;
    Context context;
    ArrayList<POItemDetail> poItemDetails=new ArrayList<>();
    ArrayList<POIncoTerms> poIncoTerms=new ArrayList<>();
    ArrayList<POPaymentTerms> poPaymentTerms=new ArrayList<>();
    ArrayList<POTermsCondition> poTermsConditions=new ArrayList<>();
    ArrayList<POAttachments> poAttachments=new ArrayList<>();
    String poNumber,businessPlaceId;
    private EditText grnNumber,et_received_date,et_totalItems,et_value,poQty,openQty,balanceQty,etName,etLrn,etTrackNumber1,etEwayBill,etTruckNumber2
            ,etDriverName,driverMobileNumber,etAddress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grn_expandable);
        mContext = InventoryGRNDetails.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        getPODetails();
    }

    @Override
    public void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        btnAction = findViewById(R.id.btnAction);
        btnSave = findViewById(R.id.btnSave);

        rGrn = findViewById(R.id.rGrn);
        rTransporter = findViewById(R.id.rTransporter);
        rItemsDetails = findViewById(R.id.rItemsDetails);
        rIncco = findViewById(R.id.rIncco);
        rAttachment = findViewById(R.id.rAttachment);

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

                break;
            case R.id.rGrn:
                lGrn.setVisibility(View.VISIBLE);
                lTransporter.setVisibility(View.GONE);
                lItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);

                break;
            case R.id.rTransporter:
                lTransporter.setVisibility(View.VISIBLE);
                lGrn.setVisibility(View.GONE);
                lItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);

                break;
            case R.id.rItemsDetails:
                lItemsDetails.setVisibility(View.VISIBLE);
                lGrn.setVisibility(View.GONE);
                lTransporter.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);

                break;
            case R.id.rIncco:
                llIncoTerms.setVisibility(View.VISIBLE);
                lItemsDetails.setVisibility(View.GONE);
                lGrn.setVisibility(View.GONE);
                lTransporter.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);

                break;
            case R.id.rAttachment:
                llTermsC.setVisibility(View.VISIBLE);
                lGrn.setVisibility(View.GONE);
                lTransporter.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                lItemsDetails.setVisibility(View.GONE);

            default:
                break;

        }
    }


    private void getExpandableData(String response){


        grnNumber=findViewById(R.id.grnNumber);
        et_received_date=findViewById(R.id.et_received_date);
        et_totalItems=findViewById(R.id.et_totalItems);
        et_value=findViewById(R.id.et_value);
        poQty=findViewById(R.id.poQty);
        openQty=findViewById(R.id.openQty);
        balanceQty=findViewById(R.id.balanceQty);


        etName=findViewById(R.id.etName);
        etLrn=findViewById(R.id.etLrn);
        etTrackNumber1=findViewById(R.id.etEwayBill);
        etTruckNumber2=findViewById(R.id.etTruckNumber2);
        etDriverName=findViewById(R.id.etDriverName);
        driverMobileNumber=findViewById(R.id.driverMobileNumber);
        etAddress=findViewById(R.id.etAddress);
        etEwayBill=findViewById(R.id.etEwayBill);

        Realm realm=Realm.getDefaultInstance();
    RealmGRNDetails realmGRNDetails=realm.where(RealmGRNDetails.class).equalTo("grnNumber","123").findFirst();

    if (realmGRNDetails!=null) {
        try {
            grnNumber.setText(realmGRNDetails.getGrnNumber());
            et_received_date.setText(realmGRNDetails.getReceivedDate());
            et_totalItems.setText(realmGRNDetails.getTotalItems());
            et_value.setText(realmGRNDetails.getValue());
            poQty.setText(realmGRNDetails.getPoQty());
            openQty.setText(realmGRNDetails.getOpenQty());
            balanceQty.setText(realmGRNDetails.getBalanceQty());
            etName.setText(realmGRNDetails.getTransporterName());
            etLrn.setText(realmGRNDetails.getTransporterLRName());
            etTrackNumber1.setText(realmGRNDetails.getTransporterDriverMobileNumber());
            etTruckNumber2.setText(realmGRNDetails.getTransporterTruckNumber());
            etDriverName.setText(realmGRNDetails.getTransporterDriverName());
            driverMobileNumber.setText(realmGRNDetails.getTransporterDriverMobileNumber());
            etAddress.setText(realmGRNDetails.getTransporterAddress());
            etEwayBill.setText(realmGRNDetails.getTransporterEWayBillNumber());




            JSONArray array =new JSONArray(realmGRNDetails.getPoItemDetails());


            for (int j = 0; j < array.length(); j++) {

                JSONObject jsonObject1 = array.optJSONObject(j);

                POItemDetail poItemDetail = new POItemDetail();
                poItemDetail.setPoItemQty(jsonObject1.optDouble("openQty"));
                poItemDetail.setPoItemAmount(jsonObject1.optDouble("apQty"));
                poItemDetail.setPoItemUnitPrice(jsonObject1.optDouble("inQty"));
                poItemDetail.setPoItemIGSTValue(jsonObject1.optDouble("balanceQty"));
                poItemDetail.setTitle(jsonObject1.optString("materialName"));
                poItemDetails.add(poItemDetail);
            }


            JSONArray jsonArray =new JSONArray(realmGRNDetails.getPoIncoTerms());


            double total = 0;
            for (int j = 0; j < jsonArray.length(); j++) {

                JSONObject jsonObject1 = jsonArray.optJSONObject(j);

                POIncoTerms poIncoTerms1 = new POIncoTerms();
                poIncoTerms1.setPoIncoDetail(jsonObject1.optString("grnIncoDetail"));
                poIncoTerms1.setPoPayAmount(jsonObject1.optDouble("grnPayAmount"));
                poIncoTerms1.setPoPayByReceiver(jsonObject1.optBoolean("grnPayByReceiver"));
                poIncoTerms1.setPoPayBySender(jsonObject1.optBoolean("grnPayBySender"));
                poIncoTerms.add(poIncoTerms1);

                total = total + jsonObject1.optDouble("poPayAmount");


            }
            POIncoTerms poIncoTerms2 = new POIncoTerms();
            poIncoTerms2.setPoIncoDetail("Total");
            poIncoTerms2.setPoPayAmount(total);
            poIncoTerms2.setPoPayByReceiver(false);
            poIncoTerms2.setPoPayBySender(false);
            poIncoTerms.add(poIncoTerms2);


            JSONArray jsonArray1 = new JSONArray(realmGRNDetails.getPoPaymentTerms());


            for (int j = 0; j < jsonArray1.length(); j++) {

                JSONObject jsonObject1 = jsonArray1.optJSONObject(j);

                POPaymentTerms poIncoTerms1 = new POPaymentTerms();
                poIncoTerms1.setPoPaymentTermsDetail(jsonObject1.optString("grnPaymentTermsDetail"));
                poIncoTerms1.setPoPaymentTermsInvoiceDue(jsonObject1.optString("grnPaymentTermsInvoiceDue"));
                poIncoTerms1.setPoPaymentTermsPer(jsonObject1.optDouble("grnPaymentTermsPer"));

                poPaymentTerms.add(poIncoTerms1);


            }

            JSONArray jsonArray2 = new JSONArray(realmGRNDetails.getPoTermsAndConditions());


            for (int j = 0; j < jsonArray2.length(); j++) {

                JSONObject jsonObject1 = jsonArray2.optJSONObject(j);

                POTermsCondition termsCondition = new POTermsCondition();
                termsCondition.setpOTermsAndConditionDetail(jsonObject1.optString("grnTermsAndConditionDetail"));
                termsCondition.setpOTermsAndConditionSrNo(jsonObject1.optInt("grnTermsAndConditionSrNo"));

                poTermsConditions.add(termsCondition);


            }

            JSONArray jsonArray3 = new JSONArray(realmGRNDetails.getPoAttachments());


            for (int j = 0; j < jsonArray3.length(); j++) {

                JSONObject jsonObject1 = jsonArray3.optJSONObject(j);

                POAttachments poAttachments1 = new POAttachments();
                poAttachments1.setpOAttachmentName(jsonObject1.optString("grnAttachmentName"));
                poAttachments1.setpOAttachmentType(jsonObject1.optString("grnAttachmentType"));
                poAttachments1.setpOAttachmentUrl(jsonObject1.optString("grnAttachmentUrl"));

                poAttachments.add(poAttachments1);


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

    public void getPODetails() {

        final ProgressDialog progressDialog=new ProgressDialog(InventoryGRNDetails.this);
        JSONObject jsonObject1=new JSONObject();

        try {
            jsonObject1.put("empCode", Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("businessPlaceId",businessPlaceId);
            jsonObject1.put("poNumber","PO18000001");
            jsonObject1.put("isGRN",true);
            jsonObject1.put("isGRNOrQC","grn");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.WEB_SERVICE_GetPOSummaryDetail;

        final Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                progressDialog.dismiss();
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

                        final String responseData = response.body().string();
                        if (responseData != null) {
                            new RealmController().saveGRNDetails(responseData);
                            //saveResponseLocalCreateOrder(jsonObject,requestId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    getExpandableData(responseData);
                                }
                            });


                        }


                    } else {



                    }

                } catch (Exception e) {
                    e.printStackTrace();



                }
            }
        });
    }
    private void setItemDetails(){


        recycler_viewItemDetail=findViewById(R.id.recycler_viewItemDetails);
        recycler_viewItemDetail.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(InventoryGRNDetails.this);
        recycler_viewItemDetail.setLayoutManager(mLayoutManager);
        itemListDataAdapter = new ItemsDetailsPOListAdapter(InventoryGRNDetails.this, poItemDetails);
        recycler_viewItemDetail.setAdapter(itemListDataAdapter);
    }

    private void setIncoTerms(){


        recycler_viewInco=findViewById(R.id.recycler_viewInco);
        recycler_viewInco.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(InventoryGRNDetails.this);
        recycler_viewInco.setLayoutManager(mLayoutManager);
        incoTermsPOListAdapter = new INCOTermsPOListAdapter(InventoryGRNDetails.this, poIncoTerms);
        recycler_viewInco.setAdapter(incoTermsPOListAdapter);
    }

    private void setPaymentTerms(){


        recycler_viewPayment=findViewById(R.id.recycler_viewPayment);
        recycler_viewPayment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(InventoryGRNDetails.this);
        recycler_viewPayment.setLayoutManager(mLayoutManager);
        milestonePOListAdapter = new MilestonePOListAdapter(InventoryGRNDetails.this, poPaymentTerms);
        recycler_viewPayment.setAdapter(milestonePOListAdapter);
    }

    private void setTermsCondition(){


        recycler_viewTerms=findViewById(R.id.recycler_viewTerms);
        recycler_viewTerms.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(InventoryGRNDetails.this);
        recycler_viewTerms.setLayoutManager(mLayoutManager);
        termsPOListAdapter = new TermsPOListAdapter(InventoryGRNDetails.this, poTermsConditions);
        recycler_viewTerms.setAdapter(termsPOListAdapter);
    }


    private void setAttahcments(){


        recycler_viewAttachment=findViewById(R.id.recycler_viewAttachment);
        recycler_viewAttachment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(InventoryGRNDetails.this);
        recycler_viewAttachment.setLayoutManager(mLayoutManager);
        attachmentsPOListAdapter = new AttachmentsPOListAdapter(InventoryGRNDetails.this, poAttachments);
        recycler_viewAttachment.setAdapter(attachmentsPOListAdapter);
    }
}
