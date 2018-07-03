package quay.com.ipos.inventory.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.pss_order.activity.OrderCentreDetailsActivity;
import quay.com.ipos.enums.NoGetEntityEnums;
import quay.com.ipos.inventory.adapter.AddressListAdapter;
import quay.com.ipos.inventory.adapter.AttachmentsPOListAdapter;

import quay.com.ipos.inventory.adapter.ExpandableListAdapter;
import quay.com.ipos.inventory.adapter.INCOTermsPOListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailsPOListAdapter;
import quay.com.ipos.inventory.adapter.MilestonePOListAdapter;
import quay.com.ipos.inventory.adapter.NewOrderItemsDetailListAdapter;
import quay.com.ipos.inventory.adapter.TermsPOListAdapter;
import quay.com.ipos.inventory.modal.InventoryPOModal;
import quay.com.ipos.inventory.modal.ItemsDetailsModal;
import quay.com.ipos.inventory.modal.POAttachments;
import quay.com.ipos.inventory.modal.POIncoTerms;
import quay.com.ipos.inventory.modal.POItemDetail;
import quay.com.ipos.inventory.modal.POPaymentTerms;
import quay.com.ipos.inventory.modal.POTermsCondition;
import quay.com.ipos.modal.MenuModal;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.realmbean.RealmBusinessPlaces;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.realmbean.RealmOrderList;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;


public class ExpandablePODetailsActivity extends BaseActivity {

    ExpandableListView expandableListView;
    //CustomExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<MenuModal, List<InventoryPOModal>> expandableListDetail = new LinkedHashMap<MenuModal, List<InventoryPOModal>>();
    LinearLayout llTermsC,llAttachments,llPODetails,llItemsDetails,llIncoTerms,llPaymentTerms;
    RelativeLayout POHashitems,POitemsDetails,POIncoTerms,POPaymentTerms,POTermsandCondition,POAttachment;
    EditText edtPoNumber,edtPoDate,edtPoValDate,edtPoValue,edtPoGST,edtSupplierName,edtSupAddress,edtSupGSTIN,edtBillingAddress,edtDeliveryAddress;
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
    private TextView tvHeaderPoNumber,tvHeaderPOItemDetail;
    private Spinner spnMilestone;
    private boolean isPOHeader,isItemDetails,isInco,isPayment,isTerms,isAttachments;
    private ImageView imgri,imgRight,imgPaymentTerms,imgIncoTerms,imgItems,arrowPO;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.po_expandable_details);
        context=ExpandablePODetailsActivity.this;
        setHeader();
        Intent i=getIntent();
        if (i!=null){
            poNumber=i.getStringExtra("poNumber");
            businessPlaceId=i.getStringExtra("businessPlaceId");
        }
        inializeViews();
        setLisner();
        getPODetails();

    }
    public void setHeader() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        setSupportActionBar(toolbar);
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

    private void inializeViews(){
        imgri=findViewById(R.id.imgri);
        imgRight=findViewById(R.id.imgRight);
        imgPaymentTerms=findViewById(R.id.imgPaymentTerms);
        imgIncoTerms=findViewById(R.id.imgIncoTerms);
        imgItems=findViewById(R.id.imgItems);
        arrowPO=findViewById(R.id.arrowPO);

        imgri.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
        imgRight.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
        imgPaymentTerms.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
        imgIncoTerms.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
        imgItems.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
        arrowPO.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);



        spnMilestone=findViewById(R.id.spnMilestone);
        tvHeaderPoNumber=findViewById(R.id.tvHeaderPoNumber);
        tvHeaderPOItemDetail=findViewById(R.id.tvHeaderPOItemDetail);
        llPODetails=findViewById(R.id.llPODetails);
        llItemsDetails=findViewById(R.id.llItemsDetails);
        llIncoTerms=findViewById(R.id.llIncoTerms);
        llPaymentTerms=findViewById(R.id.llPaymentTerms);
        llTermsC=findViewById(R.id.llTermsC);
        llAttachments=findViewById(R.id.llAttachments);

        POHashitems=findViewById(R.id.POHashitems);
        POitemsDetails=findViewById(R.id.POitemsDetails);
        POIncoTerms=findViewById(R.id.POIncoTerms);
        POPaymentTerms=findViewById(R.id.POPaymentTerms);
        POTermsandCondition=findViewById(R.id.POTermsandCondition);
        POAttachment=findViewById(R.id.POAttachment);

        edtPoNumber=findViewById(R.id.edtPoNumber);
        edtBillingAddress=findViewById(R.id.edtBillingAddress);
        edtDeliveryAddress=findViewById(R.id.edtDeliveryAddress);
        edtPoDate=findViewById(R.id.edtPoDate);
        edtPoGST=findViewById(R.id.edtPoGST);
        edtPoValDate=findViewById(R.id.edtPoValDate);
        edtPoValue=findViewById(R.id.edtPoValue);
        edtSupAddress=findViewById(R.id.edtSupAddress);
        edtSupGSTIN=findViewById(R.id.edtSupGSTIN);
        edtSupplierName=findViewById(R.id.edtSupplierName);


    }

    private void setItemDetails(){


         recycler_viewItemDetail=findViewById(R.id.recycler_viewItemDetail);
        recycler_viewItemDetail.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExpandablePODetailsActivity.this);
        recycler_viewItemDetail.setLayoutManager(mLayoutManager);
         itemListDataAdapter = new ItemsDetailsPOListAdapter(ExpandablePODetailsActivity.this, poItemDetails);
        recycler_viewItemDetail.setAdapter(itemListDataAdapter);
    }

    private void setIncoTerms(){


        recycler_viewInco=findViewById(R.id.recycler_viewInco);
        recycler_viewInco.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExpandablePODetailsActivity.this);
        recycler_viewInco.setLayoutManager(mLayoutManager);
        incoTermsPOListAdapter = new INCOTermsPOListAdapter(ExpandablePODetailsActivity.this, poIncoTerms);
        recycler_viewInco.setAdapter(incoTermsPOListAdapter);
    }

    private void setPaymentTerms(){


        recycler_viewPayment=findViewById(R.id.recycler_viewPayment);
        recycler_viewPayment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExpandablePODetailsActivity.this);
        recycler_viewPayment.setLayoutManager(mLayoutManager);
        milestonePOListAdapter = new MilestonePOListAdapter(ExpandablePODetailsActivity.this, poPaymentTerms);
        recycler_viewPayment.setAdapter(milestonePOListAdapter);
    }

    private void setTermsCondition(){


        recycler_viewTerms=findViewById(R.id.recycler_viewTerms);
        recycler_viewTerms.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExpandablePODetailsActivity.this);
        recycler_viewTerms.setLayoutManager(mLayoutManager);
        termsPOListAdapter = new TermsPOListAdapter(ExpandablePODetailsActivity.this, poTermsConditions);
        recycler_viewTerms.setAdapter(termsPOListAdapter);
    }


    private void setAttahcments(){


        recycler_viewAttachment=findViewById(R.id.recycler_viewAttachment);
        recycler_viewAttachment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExpandablePODetailsActivity.this);
        recycler_viewAttachment.setLayoutManager(mLayoutManager);
        attachmentsPOListAdapter = new AttachmentsPOListAdapter(ExpandablePODetailsActivity.this, poAttachments);
        recycler_viewAttachment.setAdapter(attachmentsPOListAdapter);
    }

    private void setLisner(){

        POHashitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isPOHeader){
                    llPODetails.setVisibility(View.VISIBLE);
                    isPOHeader=true;
                    arrowPO.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    arrowPO.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llPODetails.setVisibility(View.GONE);
                    isPOHeader=false;
                }
            /*    llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);*/
            }
        });


        POitemsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isItemDetails){
                    llItemsDetails.setVisibility(View.VISIBLE);
                    isItemDetails=true;
                    imgItems.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgItems.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llItemsDetails.setVisibility(View.GONE);
                    isItemDetails=false;
                }
                /*llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.VISIBLE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);*/
            }
        });

        POIncoTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isInco){
                    llIncoTerms.setVisibility(View.VISIBLE);
                    isInco=true;
                    imgIncoTerms.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgIncoTerms.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llIncoTerms.setVisibility(View.GONE);
                    isInco=false;
                }
               /* llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.VISIBLE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);*/
            }
        });
        POPaymentTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPayment){
                    llPaymentTerms.setVisibility(View.VISIBLE);
                    isPayment=true;
                    imgPaymentTerms.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgPaymentTerms.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llPaymentTerms.setVisibility(View.GONE);
                    isPayment=false;
                }
          /*      llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.VISIBLE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);*/
            }
        });
        POTermsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isTerms){
                    llTermsC.setVisibility(View.VISIBLE);
                    isTerms=true;
                    imgRight.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgRight.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llTermsC.setVisibility(View.GONE);
                    isTerms=false;
                }
           /*     llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.VISIBLE);
                llAttachments.setVisibility(View.GONE);*/
            }
        });
        POAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isAttachments){
                    llAttachments.setVisibility(View.VISIBLE);
                    isAttachments=true;
                    imgri.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgri.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llAttachments.setVisibility(View.GONE);
                    isAttachments=false;
                }
               /* llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.VISIBLE);*/
            }
        });



    }


    private void getExpandableData(String response){
        edtPoNumber=findViewById(R.id.edtPoNumber);
        edtBillingAddress=findViewById(R.id.edtBillingAddress);
        edtDeliveryAddress=findViewById(R.id.edtDeliveryAddress);
        edtPoDate=findViewById(R.id.edtPoDate);
        edtPoGST=findViewById(R.id.edtPoGST);
        edtPoValDate=findViewById(R.id.edtPoValDate);
        edtPoValue=findViewById(R.id.edtPoValue);
        edtSupAddress=findViewById(R.id.edtSupAddress);
        edtSupGSTIN=findViewById(R.id.edtSupGSTIN);
        edtSupplierName=findViewById(R.id.edtSupplierName);
        try {
            JSONObject jsonObject=new JSONObject(response);
            tvHeaderPoNumber.setText("#"+jsonObject.optString("poNumber"));

            edtPoNumber.setText(jsonObject.optString("poNumber"));
            edtBillingAddress.setText(jsonObject.optString("billingAddress"));
            edtDeliveryAddress.setText(jsonObject.optString("deliveryAddress"));
            edtPoDate.setText(jsonObject.optString("poDate"));
            edtPoGST.setText(jsonObject.optDouble("poIGSTValue")+"");
            edtPoValDate.setText(jsonObject.optString("poValidityDate"));
            edtPoValue.setText(jsonObject.optDouble("poValue")+"");
            edtSupAddress.setText(jsonObject.optString("supplierAddress"));
            edtSupGSTIN.setText(jsonObject.optString("supplierGSTIN"));
            edtSupplierName.setText(jsonObject.optString("supplierName"));


            ArrayList<String> list=new ArrayList<>();
           // list.add("One Time with Recurring");
            list.add("Milestone Based");
          //  list.add("On Invoice Based");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item,list);
            spnMilestone.setAdapter(adapter);


            JSONArray array=jsonObject.optJSONArray("pOItemDetails");


            int qty=0;
                for (int j = 0; j < array.length(); j++) {

                    JSONObject jsonObject1 = array.optJSONObject(j);
                    qty=qty+jsonObject1.optInt("poItemQty");
                    POItemDetail poItemDetail=new POItemDetail();
                    poItemDetail.setPoItemQty(jsonObject1.optInt("poItemQty"));
                    poItemDetail.setPoItemAmount(jsonObject1.optDouble("poItemAmount"));
                    poItemDetail.setPoItemUnitPrice(jsonObject1.optDouble("poItemUnitPrice"));
                    poItemDetail.setPoItemIGSTValue(jsonObject1.optDouble("poItemIGSTValue"));
                    poItemDetail.setTitle(jsonObject1.optString("materialName"));
                    poItemDetails.add(poItemDetail);
                }

            tvHeaderPOItemDetail.setText("Item Details | "+ array.length()+" Items | Qty "+qty );

            JSONArray jsonArray=jsonObject.optJSONArray("pOIncoTerms");


                double total=0;
            for (int j = 0; j < jsonArray.length(); j++) {

                JSONObject jsonObject1 = jsonArray.optJSONObject(j);

                POIncoTerms poIncoTerms1=new POIncoTerms();
                poIncoTerms1.setPoIncoDetail(jsonObject1.optString("poIncoDetail"));
                poIncoTerms1.setPoPayAmount(jsonObject1.optDouble("poPayAmount"));
                poIncoTerms1.setPoPayByReceiver(jsonObject1.optBoolean("poPayByReceiver"));
                poIncoTerms1.setPoPayBySender(jsonObject1.optBoolean("poPayBySender"));
                poIncoTerms.add(poIncoTerms1);

                total=total+jsonObject1.optDouble("poPayAmount");



            }
            POIncoTerms poIncoTerms2=new POIncoTerms();
            poIncoTerms2.setPoIncoDetail("Total");
            poIncoTerms2.setPoPayAmount(total);
            poIncoTerms2.setPoPayByReceiver(false);
            poIncoTerms2.setPoPayBySender(false);
            poIncoTerms.add(poIncoTerms2);


            JSONArray jsonArray1=jsonObject.optJSONArray("pOPaymentTerms");


            for (int j = 0; j < jsonArray1.length(); j++) {

                JSONObject jsonObject1 = jsonArray1.optJSONObject(j);

                POPaymentTerms poIncoTerms1=new POPaymentTerms();
                poIncoTerms1.setPoPaymentTermsDetail(jsonObject1.optString("poPaymentTermsDetail"));
                poIncoTerms1.setPoPaymentTermsInvoiceDue(jsonObject1.optString("poPaymentTermsInvoiceDue"));
                poIncoTerms1.setPoPaymentTermsPer(jsonObject1.optDouble("poPaymentTermsPer"));

                poPaymentTerms.add(poIncoTerms1);


            }

            JSONArray jsonArray2=jsonObject.optJSONArray("pOTermsAndConditions");


            for (int j = 0; j < jsonArray2.length(); j++) {

                JSONObject jsonObject1 = jsonArray2.optJSONObject(j);

                POTermsCondition termsCondition=new POTermsCondition();
                termsCondition.setpOTermsAndConditionDetail(jsonObject1.optString("pOTermsAndConditionDetail"));
                termsCondition.setpOTermsAndConditionSrNo(jsonObject1.optInt("pOTermsAndConditionSrNo"));

                poTermsConditions.add(termsCondition);


            }

            JSONArray jsonArray3=jsonObject.optJSONArray("pOAttachments");


            for (int j = 0; j < jsonArray3.length(); j++) {

                JSONObject jsonObject1 = jsonArray3.optJSONObject(j);

                POAttachments poAttachments1=new POAttachments();
                poAttachments1.setpOAttachmentName(jsonObject1.optString("pOAttachmentName"));
                poAttachments1.setpOAttachmentType(jsonObject1.optString("pOAttachmentType"));
                poAttachments1.setpOAttachmentUrl(jsonObject1.optString("pOAttachmentUrl"));

                poAttachments.add(poAttachments1);


            }


            setItemDetails();
            setIncoTerms();
            setAttahcments();
            setPaymentTerms();
            setTermsCondition();



        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    public void getPODetails() {
        final ProgressDialog progressDialog=new ProgressDialog(ExpandablePODetailsActivity.this);
        JSONObject jsonObject1=new JSONObject();

        try {
            jsonObject1.put("empCode",Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("businessPlaceId",businessPlaceId);
            jsonObject1.put("poNumber",poNumber);

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
                            JSONObject jsonObject=new JSONObject(responseData);

                            // saveResponseLocalCreateOrder(jsonObject,requestId);
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


}
