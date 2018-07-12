package quay.com.ipos.inventoryTrasfer;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.inventory.activity.ExpandablePODetailsActivity;
import quay.com.ipos.inventory.adapter.AttachmentsPOListAdapter;
import quay.com.ipos.inventory.adapter.INCOTermsPOListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailsPOListAdapter;
import quay.com.ipos.inventory.adapter.MilestonePOListAdapter;
import quay.com.ipos.inventory.adapter.TermsPOListAdapter;
import quay.com.ipos.inventory.modal.InventoryPOModal;
import quay.com.ipos.inventory.modal.POAttachments;
import quay.com.ipos.inventory.modal.POIncoTerms;
import quay.com.ipos.inventory.modal.POItemDetail;
import quay.com.ipos.inventory.modal.POPaymentTerms;
import quay.com.ipos.inventory.modal.POTermsCondition;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInActivity.TransferInDetailsActivity;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.MenuModal;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 7/6/2018.
 */

public class TransferStepsActivity extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private static final String TAG = TransferStepsActivity.class.getSimpleName();
    private Context mContext;
    private Toolbar toolbar;
    //Tab
    private LinearLayout lLayTransferOut, lLayoutShipment, lLayoutTransferIn,llShipment,llPODetailsShipment;
    private TextView tvTransferOut, tvShipment, tvTransferIn;

    //TransferIn id's
    private RelativeLayout rlTab, llgrnn,rShipment;
    private TextView tvTransferNumber, tvOpen, tranferOutCount, tranferInCount, apCount, balanceQtyCount;


    //Grn header
    private RelativeLayout rGrn;
    private TextView tvGrnNumberCount, textViewAdd;
    private LinearLayout llTransferOut;
    private RecyclerView recycleviewList;

    LinearLayout includeCardView;
    ExpandableListView expandableListView;
    //CustomExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<MenuModal, List<InventoryPOModal>> expandableListDetail = new LinkedHashMap<MenuModal, List<InventoryPOModal>>();
    LinearLayout llTermsC,llAttachments,llTRans,llItemsDetails,llIncoTerms,llPaymentTerms;
    RelativeLayout lTransporter;
    RelativeLayout POHashitems,POitemsDetails,POIncoTerms,POPaymentTerms,POTermsandCondition,POAttachment;
    EditText edtPoNumber,edtPoDate,edtPoValDate,edtPoValue,edtPoGST,edtSupplierName,edtSupGSTIN,edtDeliveryAddress;
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
    private boolean isPOHeader,isItemDetails,isInco,isPayment,isTerms,isAttachments,isShipment;
    private ImageView imgri,imgRight,imgPaymentTerms,imgIncoTerms,imgItems,arrowPO,imgShipment;
    private String supplierName="";
    private TextView toolbarTtile;
    private EditText edtDocHash,edtDocValue,edtGSTValue,edtSenderGSTIN,edtReceiverGSTIN,edtDocDate,etTruckNumber,etEwayBill,etEWayBillValidity,
            etName,etLrn,etDriverName,driverMobileNumber,etAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_transfer_steps_activity);
        mContext = TransferStepsActivity.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {

        toolbar = findViewById(R.id.toolbar);
        //Tab Id's
        lLayTransferOut = findViewById(R.id.lLayTransferOut);
        lLayoutShipment = findViewById(R.id.lLayoutShipment);
        lLayoutTransferIn = findViewById(R.id.lLayoutTransferIn);
        tvTransferOut = findViewById(R.id.tvTransferOut);
        tvShipment = findViewById(R.id.tvShipment);
        tvTransferIn = findViewById(R.id.tvTransferIn);

        //TransferIn Id's
        rlTab = findViewById(R.id.rlTab);
        llgrnn = findViewById(R.id.llgrnn);
        tvTransferNumber = findViewById(R.id.tvTransferNumber);
        tvOpen = findViewById(R.id.tvOpen);
        tranferOutCount = findViewById(R.id.tranferOutCount);
        tranferInCount = findViewById(R.id.tranferInCount);
        apCount = findViewById(R.id.apCount);
        balanceQtyCount = findViewById(R.id.balanceQtyCount);


        //GRN header view
        rGrn = findViewById(R.id.rGrn);
        tvGrnNumberCount = findViewById(R.id.tvGrnNumberCount);
        textViewAdd = findViewById(R.id.textViewAdd);
        recycleviewList = findViewById(R.id.recycleviewList);


        //transferOut ids
        llTransferOut=findViewById(R.id.llTransferOut);
        llShipment=findViewById(R.id.llShipment);

        textViewAdd.setOnClickListener(this);
        lLayoutTransferIn.setOnClickListener(this);
        lLayTransferOut.setOnClickListener(this);
        lLayoutShipment.setOnClickListener(this);


        Intent i=getIntent();
        if (i!=null){
            poNumber=i.getStringExtra("poNumber");
            businessPlaceId=i.getStringExtra("businessPlaceId");
            supplierName=i.getStringExtra("supplierName");
        }


        inializeViews();
        setLisner();
        getPODetails();

    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("Inventory");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
            case R.id.textViewAdd:
                Intent i = new Intent(mContext, TransferInDetailsActivity.class);
                startActivity(i);

                break;
            case R.id.lLayoutTransferIn:
                rlTab.setVisibility(View.VISIBLE);
                rGrn.setVisibility(View.VISIBLE);
                tvTransferOut.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvShipment.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvTransferIn.setBackgroundResource(R.drawable.textview_circle_app_color);
                recycleviewList.setVisibility(View.VISIBLE);
                llTransferOut.setVisibility(View.GONE);
                llShipment.setVisibility(View.GONE);
                break;
            case R.id.lLayTransferOut:
                rlTab.setVisibility(View.GONE);
                rGrn.setVisibility(View.GONE);
                tvTransferIn.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvShipment.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvTransferOut.setBackgroundResource(R.drawable.textview_circle_app_color);
                recycleviewList.setVisibility(View.GONE);
                llTransferOut.setVisibility(View.VISIBLE);
                llShipment.setVisibility(View.GONE);
                break;
            case R.id.lLayoutShipment:
                rlTab.setVisibility(View.GONE);
                rGrn.setVisibility(View.GONE);
                llTransferOut.setVisibility(View.GONE);
                tvTransferIn.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvTransferOut.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvShipment.setBackgroundResource(R.drawable.textview_circle_app_color);
                recycleviewList.setVisibility(View.GONE);
                llShipment.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

    private void inializeViews(){
        llPODetailsShipment=findViewById(R.id.llPODetailsShipment);
        imgShipment=findViewById(R.id.imgShipment);
        rShipment=findViewById(R.id.rShipment);
        edtDocHash=findViewById(R.id.edtDocHash);
        etTruckNumber=findViewById(R.id.etTruckNumber);
        etEwayBill=findViewById(R.id.etEwayBill);
        etEWayBillValidity=findViewById(R.id.etEWayBillValidity);
        etName=findViewById(R.id.etName);
        etLrn=findViewById(R.id.etLrn);
        etDriverName=findViewById(R.id.etDriverName);
        driverMobileNumber=findViewById(R.id.driverMobileNumber);
        etAddress=findViewById(R.id.etAddress);

        etTruckNumber.setEnabled(false);
        etEwayBill.setEnabled(false);
        etEWayBillValidity.setEnabled(false);
        etName.setEnabled(false);
        etLrn.setEnabled(false);
        etDriverName.setEnabled(false);
        driverMobileNumber.setEnabled(false);
        etAddress.setEnabled(false);



        edtReceiverGSTIN=findViewById(R.id.edtReceiverGSTIN);
        edtDocValue=findViewById(R.id.edtDocValue);
        edtGSTValue=findViewById(R.id.edtGSTValue);
        edtDocDate=findViewById(R.id.edtDocDate);
        edtSenderGSTIN=findViewById(R.id.edtSenderGSTIN);
        edtDeliveryAddress=findViewById(R.id.edtDeliveryAddress);

        edtReceiverGSTIN.setEnabled(false);
        edtDocValue.setEnabled(false);
        edtGSTValue.setEnabled(false);
        edtDocDate.setEnabled(false);
        edtSenderGSTIN.setEnabled(false);
        edtDeliveryAddress.setEnabled(false);


        includeCardView=findViewById(R.id.includeCardView);
        //  includeCardView.setVisibility(View.GONE);
        toolbarTtile=findViewById(R.id.toolbarTtile);
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
        llTRans=findViewById(R.id.llTRans);
        llItemsDetails=findViewById(R.id.llItemsDetails);
        llIncoTerms=findViewById(R.id.llIncoTerms);

        lTransporter=findViewById(R.id.lTransporter);
        llTermsC=findViewById(R.id.llTermsC);
        llAttachments=findViewById(R.id.llAttachments);

        POHashitems=findViewById(R.id.POHashitems);
        POitemsDetails=findViewById(R.id.POitemsDetails);
        POIncoTerms=findViewById(R.id.POIncoTerms);
        POPaymentTerms=findViewById(R.id.POPaymentTerms);
        POTermsandCondition=findViewById(R.id.POTermsandCondition);
        POAttachment=findViewById(R.id.POAttachment);



        edtDeliveryAddress=findViewById(R.id.edtDeliveryAddress);





        edtSupGSTIN=findViewById(R.id.edtSupGSTIN);
        edtSupplierName=findViewById(R.id.edtSupplierName);



        edtDeliveryAddress.setEnabled(false);





        edtSupGSTIN.setEnabled(false);
        edtSupplierName.setEnabled(false);



    }

    private void setItemDetails(){


        recycler_viewItemDetail=findViewById(R.id.recycler_viewItemDetail);
        recycler_viewItemDetail.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(TransferStepsActivity.this);
        recycler_viewItemDetail.setLayoutManager(mLayoutManager);
        itemListDataAdapter = new ItemsDetailsPOListAdapter(TransferStepsActivity.this, poItemDetails);
        recycler_viewItemDetail.setAdapter(itemListDataAdapter);
    }

    private void setIncoTerms(){


        recycler_viewInco=findViewById(R.id.recycler_viewInco);
        recycler_viewInco.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(TransferStepsActivity.this);
        recycler_viewInco.setLayoutManager(mLayoutManager);
        incoTermsPOListAdapter = new INCOTermsPOListAdapter(TransferStepsActivity.this, poIncoTerms);
        recycler_viewInco.setAdapter(incoTermsPOListAdapter);
    }

    private void setPaymentTerms(){


        recycler_viewPayment=findViewById(R.id.recycler_viewPayment);
        recycler_viewPayment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(TransferStepsActivity.this);
        recycler_viewPayment.setLayoutManager(mLayoutManager);
        milestonePOListAdapter = new MilestonePOListAdapter(TransferStepsActivity.this, poPaymentTerms);
        recycler_viewPayment.setAdapter(milestonePOListAdapter);
    }

    private void setTermsCondition(){


        recycler_viewTerms=findViewById(R.id.recycler_viewTerms);
        recycler_viewTerms.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(TransferStepsActivity.this);
        recycler_viewTerms.setLayoutManager(mLayoutManager);
        termsPOListAdapter = new TermsPOListAdapter(TransferStepsActivity.this, poTermsConditions);
        recycler_viewTerms.setAdapter(termsPOListAdapter);
    }


    private void setAttahcments(){


        recycler_viewAttachment=findViewById(R.id.recycler_viewAttachment);
        recycler_viewAttachment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(TransferStepsActivity.this);
        recycler_viewAttachment.setLayoutManager(mLayoutManager);
        attachmentsPOListAdapter = new AttachmentsPOListAdapter(TransferStepsActivity.this, poAttachments);
        recycler_viewAttachment.setAdapter(attachmentsPOListAdapter);
    }

    private void setLisner(){

        POHashitems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isPOHeader){
                    llTRans.setVisibility(View.VISIBLE);
                    isPOHeader=true;
                    arrowPO.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    arrowPO.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llTRans.setVisibility(View.GONE);
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
                    lTransporter.setVisibility(View.VISIBLE);
                    isPayment=true;
                    imgPaymentTerms.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgPaymentTerms.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    lTransporter.setVisibility(View.GONE);
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
        rShipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isShipment){
                    llPODetailsShipment.setVisibility(View.VISIBLE);
                    isShipment=true;
                    imgShipment.setBackgroundResource(R.drawable.ic_action_arrow_right_blue);
                }else {
                    imgShipment.setBackgroundResource(R.drawable.ic_action_arrow_down_blue);
                    llPODetailsShipment.setVisibility(View.GONE);
                    isShipment=false;
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

        try {
            JSONObject jsonObject=new JSONObject(response);

            edtDocHash.setText(jsonObject.optString("documentNumber"));
            edtDeliveryAddress.setText(jsonObject.optString("receiverBusinessPlaceAddress"));
            edtSupplierName.setText(jsonObject.optString("senderBusinessPlaceAddress"));
            edtDocDate.setText(jsonObject.optString("documentDate"));
            edtGSTValue.setText(Util.round(jsonObject.optDouble("documentIGSTValue"),2)+"");
            edtDocValue.setText(Util.round(jsonObject.optDouble("documentValue"),2)+"");
            edtSenderGSTIN.setText(jsonObject.optString("senderBusinessPlaceGSTIN"));
            edtReceiverGSTIN.setText(jsonObject.optString("receiverBusinessPlaceGSTIN"));

            // transporter set data
            etEWayBillValidity.setText(jsonObject.optString("transporterEWayBillValidityDate"));
            etAddress.setText(jsonObject.optString("transporterAddress"));
            etDriverName.setText(jsonObject.optString("transporterDriverName"));
            etEwayBill.setText(jsonObject.optString("transporterEWayBillNumber"));
            etLrn.setText(jsonObject.optString("transporterLRName"));
            etName.setText(jsonObject.optString("transporterName"));
            etTruckNumber.setText(jsonObject.optString("transporterTruckNumber"));
            driverMobileNumber.setText(jsonObject.optString("transporterDriverMobileNumber"));

/*
            ArrayList<String> list=new ArrayList<>();
            // list.add("One Time with Recurring");
            list.add("Milestone Based");
            //  list.add("On Invoice Based");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item,list);
            spnMilestone.setAdapter(adapter);*/


            JSONArray array=jsonObject.optJSONArray("itemDetails");


            if (array!=null) {
                int qty = 0;
                for (int j = 0; j < array.length(); j++) {

                    JSONObject jsonObject1 = array.optJSONObject(j);
                    qty = qty + jsonObject1.optInt("poItemQty");
                    POItemDetail poItemDetail = new POItemDetail();
                    poItemDetail.setPoItemQty(jsonObject1.optInt("poItemQty"));
                    poItemDetail.setPoItemAmount(jsonObject1.optDouble("poItemAmount"));
                    poItemDetail.setPoItemUnitPrice(jsonObject1.optDouble("poItemUnitPrice"));
                    poItemDetail.setPoItemIGSTValue(jsonObject1.optDouble("poItemIGSTValue"));
                    poItemDetail.setTitle(jsonObject1.optString("materialName"));
                    poItemDetails.add(poItemDetail);
                }

                tvHeaderPOItemDetail.setText("Item Details | " + array.length() + " Items | Qty " + qty);
            }
            JSONArray jsonArray=jsonObject.optJSONArray("incoTerms");


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


         /*   JSONArray jsonArray1=jsonObject.optJSONArray("pOPaymentTerms");


            for (int j = 0; j < jsonArray1.length(); j++) {

                JSONObject jsonObject1 = jsonArray1.optJSONObject(j);

                POPaymentTerms poIncoTerms1=new POPaymentTerms();
                poIncoTerms1.setPoPaymentTermsDetail(jsonObject1.optString("poPaymentTermsDetail"));
                poIncoTerms1.setPoPaymentTermsInvoiceDue(jsonObject1.optString("poPaymentTermsInvoiceDue"));
                poIncoTerms1.setPoPaymentTermsPer(jsonObject1.optInt("poPaymentTermsPer"));

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
*/
            JSONArray jsonArray3=jsonObject.optJSONArray("attachments");


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
          //  setPaymentTerms();
          //  setTermsCondition();



        } catch (JSONException e) {
            e.printStackTrace();
        }





    }

    public void getPODetails() {
        final ProgressDialog progressDialog=new ProgressDialog(TransferStepsActivity.this);
        JSONObject jsonObject1=new JSONObject();

        try {
            jsonObject1.put("empCode", Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("businessPlaceId",businessPlaceId);
            jsonObject1.put("tranID",poNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.WEB_SERVICE_TransferOutSummaryDetail;

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


                    }else if (response.code() == Constants.BAD_REQUEST) {
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
}
