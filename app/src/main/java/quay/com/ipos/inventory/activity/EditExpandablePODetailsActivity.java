package quay.com.ipos.inventory.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
import quay.com.ipos.inventory.adapter.AttachmentsPOListAdapter;
import quay.com.ipos.inventory.adapter.INCOTermsPOListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailsPOEditListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailsPOListAdapter;
import quay.com.ipos.inventory.adapter.MilestonePOListAdapter;
import quay.com.ipos.inventory.adapter.TermsPOListAdapter;
import quay.com.ipos.inventory.attachments.AttachFileModel;
import quay.com.ipos.inventory.modal.InventoryPOModal;
import quay.com.ipos.inventory.modal.POAttachments;
import quay.com.ipos.inventory.modal.POIncoTerms;
import quay.com.ipos.inventory.modal.POItemDetail;
import quay.com.ipos.inventory.modal.POPaymentTerms;
import quay.com.ipos.inventory.modal.POTermsCondition;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.modal.MenuModal;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmInventoryProducts;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;


public class EditExpandablePODetailsActivity extends BaseActivity implements MyListener{
    private Dialog myDialog;
    ExpandableListView expandableListView;
    //CustomExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<MenuModal, List<InventoryPOModal>> expandableListDetail = new LinkedHashMap<MenuModal, List<InventoryPOModal>>();
    LinearLayout llTermsC,llAttachments,llPODetails,llItemsDetails,llIncoTerms,llPaymentTerms;
    RelativeLayout POHashitems,POitemsDetails,POIncoTerms,POPaymentTerms,POTermsandCondition,POAttachment;
    EditText edtPoNumber,edtPoDate,edtPoValDate,edtPoValue,edtPoGST,edtSupplierName,edtSupAddress,edtSupGSTIN,edtBillingAddress,edtDeliveryAddress;
    private RecyclerView recycler_viewItemDetail,recycler_viewInco,recycler_viewPayment,recycler_viewTerms,recycler_viewAttachment;
    ItemsDetailsPOEditListAdapter itemListDataAdapter;
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
    private TextView tvAddTerms;
    private View ImvClose;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.po_expandable_details);
        context=EditExpandablePODetailsActivity.this;
        setHeader();

        inializeViews();
        setLisner();
        //getPODetails();

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
        tvAddTerms=findViewById(R.id.tvAddTerms);
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

        edtPoNumber.setEnabled(true);
        edtBillingAddress.setEnabled(true);
        edtDeliveryAddress.setEnabled(true);
        edtPoGST.setEnabled(true);
        edtPoValue.setEnabled(true);
        edtSupAddress.setEnabled(true);
        edtSupGSTIN.setEnabled(true);
        edtSupplierName.setEnabled(true);


        tvAddTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final TextInputLayout tilMessage;
                final TextInputEditText tieMessage;
                Button btnSubmit;

                myDialog.setContentView(R.layout.view_note_dialog);
                ImvClose = myDialog.findViewById(R.id.ImvClose);
                ImvClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                tilMessage = myDialog.findViewById(R.id.tilMessage);
                tieMessage = myDialog.findViewById(R.id.tieMessage);
                tieMessage.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        tilMessage.setErrorEnabled(false);
                        tilMessage.setError(null);
                    }
                });
                btnSubmit = myDialog.findViewById(R.id.btnSubmit);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(tieMessage.getText().toString())) {
                            tilMessage.setErrorEnabled(true);
                            tilMessage.setError("Please write a Note.");
                        } else {
                            POTermsCondition termsCondition=new POTermsCondition();
                            termsCondition.setpOTermsAndConditionDetail(tieMessage.getText().toString());
                            termsCondition.setpOTermsAndConditionSrNo(1);

                            poTermsConditions.add(termsCondition);

                            termsPOListAdapter.notifyDataSetChanged();


                        }
                    }
                });


                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

            }
        });
    }

    private void getProducts(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmInventoryProducts> realmNewOrderCarts1 = realm.where(RealmInventoryProducts.class).findAll();
        poItemDetails.clear();

        for (RealmInventoryProducts realmNewOrderCart : realmNewOrderCarts1) {
            RealmInventoryProducts realmNewOrderCarts = realm.copyFromRealm(realmNewOrderCart);

            POItemDetail poItemDetail=new POItemDetail();
            poItemDetail.setTitle(realmNewOrderCarts.getsProductName());
            poItemDetail.setPoItemUnitPrice(realmNewOrderCarts.getsProductPrice());
            poItemDetail.setPoItemAmount(realmNewOrderCarts.getQty()*realmNewOrderCarts.getsProductPrice());
            poItemDetail.setPoItemIGSTValue(((realmNewOrderCarts.getSgst()+realmNewOrderCarts.getCgst())*realmNewOrderCarts.getQty()*realmNewOrderCarts.getsProductPrice())/100);
            poItemDetail.setPoItemCGSTPer(realmNewOrderCarts.getCgst());
            poItemDetail.setPoItemSGSTPer(realmNewOrderCarts.getSgst());

            poItemDetails.add(poItemDetail);
        }

        //  mList.addAll(realmNewOrderCarts1);

        setItemDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProducts();
    }

    /*private void createJson() {
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
            for (int j = 0; j < poIncoTerms.size(); j++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnIncoDetail", grnInccoTermsModels.get(j).getGrnIncoDetail());
                jsonObject1.put("grnPayBySender", grnInccoTermsModels.get(j).isGrnPayBySender());
                jsonObject1.put("grnPayByReceiver", grnInccoTermsModels.get(j).isGrnPayByReceiver());
                jsonObject1.put("grnPayAmount", grnInccoTermsModels.get(j).getGrnPayAmount());

                IncoTermsArray.put(jsonObject1);
            }

            JSONArray jsonArrayAttachments = new JSONArray();
            for (int j = 0; j < poAttachments.size(); j++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnAttachmentName", grnAttachments.get(j).getGrnAttachmentName());
                jsonObject1.put("grnAttachmentUrl", grnAttachments.get(j).getGrnAttachmentUrl());
                jsonObject1.put("grnAttachmentType", grnAttachments.get(j).getGrnAttachmentType());
                jsonArrayAttachments.put(jsonObject1);
            }

            //attach new

            for (int i = 0; i < poAttachments.size(); i++) {
                AttachFileModel fileModel = poAttachments.get(i);
                Uri returnUri = fileModel.uri;
                Cursor returnCursor = getContentResolver().query(returnUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                returnCursor.moveToFirst();
                String fileName = returnCursor.getString(nameIndex);
                String fileSize = Long.toString(returnCursor.getLong(sizeIndex));
                String mimeType = getContentResolver().getType(returnUri);
                Log.i("Type", mimeType);

              *//*  SpendRequestAttachment spendRequestAttachment = new SpendRequestAttachment();
                spendRequestAttachment.AttachmentBase = getBase64StringNew(returnUri, Integer.parseInt(fileSize));
                spendRequestAttachment.AttachmentExtension = "No Info";
                spendRequestAttachment.AttachmentName = fileName;
                spendRequestAttachment.AttachmentType = mimeType;
                spendRequestAttachmentList.add(spendRequestAttachment);
               *//*
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnAttachmentName", fileName);
                jsonObject1.put("grnAttachmentUrl", getBase64StringNew(returnUri, Integer.parseInt(fileSize)));
                jsonObject1.put("grnAttachmentType",  mimeType);
                jsonArrayAttachments.put(jsonObject1);


            }
            //attach end



            edtBillingAddress=findViewById(R.id.edtBillingAddress);
            edtDeliveryAddress=findViewById(R.id.edtDeliveryAddress);
            edtPoDate=findViewById(R.id.edtPoDate);
            edtPoGST=findViewById(R.id.edtPoGST);
            edtPoValDate=findViewById(R.id.edtPoValDate);
            edtPoValue=findViewById(R.id.edtPoValue);
            edtSupAddress=findViewById(R.id.edtSupAddress);
            edtSupGSTIN=findViewById(R.id.edtSupGSTIN);
            edtSupplierName=findViewById(R.id.edtSupplierName);


            jsonObject.put("poNumber", edtPoNumber.getText().toString());
            jsonObject.put("poDate", edtPoDate.getText().toString());
            jsonObject.put("poValidityDate", edtPoValDate.getText().toString());
            jsonObject.put("poValue", edtPoValue.getText().toString());
            jsonObject.put("poIGSTValue", edtPoGST.getText().toString());
            jsonObject.put("poCGSTValue",0);
            jsonObject.put("poSGSTValue", 0);
            jsonObject.put("supplierCode", edtSupplierName.getText().toString());
            jsonObject.put("supplierName", edtSupplierName.getText().toString());
            jsonObject.put("supplierAddress", edtSupAddress.getText().toString());
            jsonObject.put("supplierGSTIN", edtSupGSTIN.getText().toString());
            jsonObject.put("billingAddress", edtBillingAddress.getText().toString());
            jsonObject.put("deliveryAddress", edtDeliveryAddress.getText().toString());
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
*/
    private void setItemDetails(){


         recycler_viewItemDetail=findViewById(R.id.recycler_viewItemDetail);
        recycler_viewItemDetail.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewItemDetail.setLayoutManager(mLayoutManager);
         itemListDataAdapter = new ItemsDetailsPOEditListAdapter(EditExpandablePODetailsActivity.this, poItemDetails,this);
        recycler_viewItemDetail.setAdapter(itemListDataAdapter);
    }

    private void setIncoTerms(){


        recycler_viewInco=findViewById(R.id.recycler_viewInco);
        recycler_viewInco.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewInco.setLayoutManager(mLayoutManager);
        incoTermsPOListAdapter = new INCOTermsPOListAdapter(EditExpandablePODetailsActivity.this, poIncoTerms);
        recycler_viewInco.setAdapter(incoTermsPOListAdapter);
    }

    private void setPaymentTerms(){


        recycler_viewPayment=findViewById(R.id.recycler_viewPayment);
        recycler_viewPayment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewPayment.setLayoutManager(mLayoutManager);
        milestonePOListAdapter = new MilestonePOListAdapter(EditExpandablePODetailsActivity.this, poPaymentTerms);
        recycler_viewPayment.setAdapter(milestonePOListAdapter);
    }

    private void setTermsCondition(){


        recycler_viewTerms=findViewById(R.id.recycler_viewTerms);
        recycler_viewTerms.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewTerms.setLayoutManager(mLayoutManager);
        termsPOListAdapter = new TermsPOListAdapter(EditExpandablePODetailsActivity.this, poTermsConditions);
        recycler_viewTerms.setAdapter(termsPOListAdapter);
    }


    private void setAttahcments(){


        recycler_viewAttachment=findViewById(R.id.recycler_viewAttachment);
        recycler_viewAttachment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewAttachment.setLayoutManager(mLayoutManager);
        attachmentsPOListAdapter = new AttachmentsPOListAdapter(EditExpandablePODetailsActivity.this, poAttachments);
        recycler_viewAttachment.setAdapter(attachmentsPOListAdapter);
    }

    private void setLisner(){

        POHashitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                llPODetails.setVisibility(View.VISIBLE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);
            }
        });


        POitemsDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.VISIBLE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);
            }
        });

        POIncoTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.VISIBLE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);
            }
        });
        POPaymentTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.VISIBLE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.GONE);
            }
        });
        POTermsandCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.VISIBLE);
                llAttachments.setVisibility(View.GONE);
            }
        });
        POAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llPODetails.setVisibility(View.GONE);
                llItemsDetails.setVisibility(View.GONE);
                llIncoTerms.setVisibility(View.GONE);
                llPaymentTerms.setVisibility(View.GONE);
                llTermsC.setVisibility(View.GONE);
                llAttachments.setVisibility(View.VISIBLE);
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
            list.add("One Time with Recurring");
            list.add("Milestone Based");
            list.add("On Invoice Based");
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
        final ProgressDialog progressDialog=new ProgressDialog(EditExpandablePODetailsActivity.this);
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


    @Override
    public void onRowClicked(int position) {

    }

    @Override
    public void onRowClicked(int position, int value) {

        POItemDetail poItemDetail=new POItemDetail();
        poItemDetail.setTitle(poItemDetails.get(position).getTitle());
        poItemDetail.setPoItemUnitPrice(poItemDetails.get(position).getPoItemUnitPrice());
        poItemDetail.setPoItemAmount(value*poItemDetails.get(position).getPoItemUnitPrice());
        poItemDetail.setPoItemIGSTValue(((poItemDetails.get(position).getPoItemSGSTPer()+poItemDetails.get(position).getPoItemCGSTPer())*value*poItemDetails.get(position).getPoItemUnitPrice())/100);

        poItemDetails.set(position,poItemDetail);

        termsPOListAdapter.notifyItemChanged(position);

    }
}
