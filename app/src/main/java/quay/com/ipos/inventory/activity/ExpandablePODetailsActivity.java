package quay.com.ipos.inventory.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import quay.com.ipos.enums.NoGetEntityEnums;
import quay.com.ipos.inventory.adapter.AddressListAdapter;
import quay.com.ipos.inventory.adapter.AttachmentsPOListAdapter;
import quay.com.ipos.inventory.adapter.CustomExpandableListAdapter;
import quay.com.ipos.inventory.adapter.ExpandableListAdapter;
import quay.com.ipos.inventory.adapter.INCOTermsPOListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailsPOListAdapter;
import quay.com.ipos.inventory.adapter.MilestonePOListAdapter;
import quay.com.ipos.inventory.adapter.NewOrderItemsDetailListAdapter;
import quay.com.ipos.inventory.adapter.TermsPOListAdapter;
import quay.com.ipos.inventory.modal.InventoryPOModal;
import quay.com.ipos.inventory.modal.ItemsDetailsModal;
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
    CustomExpandableListAdapter expandableListAdapter;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.po_expandable_details);

        setHeader();

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
        ArrayList<RealmBusinessPlaces> discounts=new ArrayList<>();
        RealmBusinessPlaces realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("5");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("3");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("3");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("10");
        discounts.add(realmBusinessPlaces1);
         recycler_viewItemDetail=findViewById(R.id.recycler_viewItemDetail);
        recycler_viewItemDetail.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExpandablePODetailsActivity.this);
        recycler_viewItemDetail.setLayoutManager(mLayoutManager);
         itemListDataAdapter = new ItemsDetailsPOListAdapter(ExpandablePODetailsActivity.this, discounts);
        recycler_viewItemDetail.setAdapter(itemListDataAdapter);
    }

    private void setIncoTerms(){
        ArrayList<RealmBusinessPlaces> discounts=new ArrayList<>();
        RealmBusinessPlaces realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("Loading");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("Shipping");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("Unload");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("Toll");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("E-Way Bill");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("Unload 1");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("Total");
        discounts.add(realmBusinessPlaces1);
        recycler_viewInco=findViewById(R.id.recycler_viewInco);
        recycler_viewInco.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExpandablePODetailsActivity.this);
        recycler_viewInco.setLayoutManager(mLayoutManager);
        incoTermsPOListAdapter = new INCOTermsPOListAdapter(ExpandablePODetailsActivity.this, discounts);
        recycler_viewInco.setAdapter(incoTermsPOListAdapter);
    }

    private void setPaymentTerms(){
        ArrayList<RealmBusinessPlaces> discounts=new ArrayList<>();
        RealmBusinessPlaces realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("Advance");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("On Delivery");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("After Delivery");
        discounts.add(realmBusinessPlaces1);
        recycler_viewPayment=findViewById(R.id.recycler_viewPayment);
        recycler_viewPayment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExpandablePODetailsActivity.this);
        recycler_viewPayment.setLayoutManager(mLayoutManager);
        milestonePOListAdapter = new MilestonePOListAdapter(ExpandablePODetailsActivity.this, discounts);
        recycler_viewPayment.setAdapter(milestonePOListAdapter);
    }

    private void setTermsCondition(){
        ArrayList<RealmBusinessPlaces> discounts=new ArrayList<>();
        RealmBusinessPlaces realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("1. Lorem Ipsum is simply dummy text of the printing and typesetting industry");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("2. Lorem Ipsum is simply dummy text of the printing and typesetting industry");
        discounts.add(realmBusinessPlaces1);
        recycler_viewTerms=findViewById(R.id.recycler_viewTerms);
        recycler_viewTerms.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExpandablePODetailsActivity.this);
        recycler_viewTerms.setLayoutManager(mLayoutManager);
        termsPOListAdapter = new TermsPOListAdapter(ExpandablePODetailsActivity.this, discounts);
        recycler_viewTerms.setAdapter(termsPOListAdapter);
    }


    private void setAttahcments(){
        ArrayList<RealmBusinessPlaces> discounts=new ArrayList<>();
        RealmBusinessPlaces realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("PO Copy");
        discounts.add(realmBusinessPlaces1);
        realmBusinessPlaces1=new RealmBusinessPlaces();
        realmBusinessPlaces1.setHeader("Batch Details");
        discounts.add(realmBusinessPlaces1);
        recycler_viewAttachment=findViewById(R.id.recycler_viewAttachment);
        recycler_viewAttachment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ExpandablePODetailsActivity.this);
        recycler_viewAttachment.setLayoutManager(mLayoutManager);
        attachmentsPOListAdapter = new AttachmentsPOListAdapter(ExpandablePODetailsActivity.this, discounts);
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

        try {
            JSONObject jsonObject=new JSONObject(response);

            JSONArray array=jsonObject.optJSONArray("data");


                for (int j = 0; j < array.length(); j++) {
                    MenuModal menuModal = new MenuModal();
                    JSONObject jsonObject1 = array.optJSONObject(j);

                    menuModal.setGroupTitle(jsonObject1.optString("title"));
                    JSONArray jsonArray2 = jsonObject1.optJSONArray("child");
                    ArrayList<InventoryPOModal> childList = new ArrayList<InventoryPOModal>();
                    if (menuModal.getGroupTitle().equalsIgnoreCase("#PO")) {

                    JSONObject jsonObject2 = jsonArray2.optJSONObject(0);
                    InventoryPOModal inventoryPOModal = new InventoryPOModal();
                    inventoryPOModal.setBillingAddress(jsonObject2.optString("title"));
                    inventoryPOModal.setDeliveryAddress(jsonObject2.optString("title"));
                    inventoryPOModal.setGetPoSupplierGSTIN(jsonObject2.optString("title"));
                    inventoryPOModal.setGstValue(jsonObject2.optDouble("title"));
                    inventoryPOModal.setPoDate(jsonObject2.optString("title"));
                    inventoryPOModal.setPoSupplierAddress(jsonObject2.optString("title"));
                    inventoryPOModal.setPoNumber(jsonObject2.optString("title"));
                    inventoryPOModal.setPoValidityDate(jsonObject2.optString("title"));
                    inventoryPOModal.setPoSupplierName(jsonObject2.optString("title"));
                    inventoryPOModal.setValue(jsonObject2.optDouble("title"));





                    childList.add(inventoryPOModal);
                }else if (menuModal.getGroupTitle().equalsIgnoreCase("Items Detail")){
                        ArrayList<ItemsDetailsModal> itemsDetailsModals=new ArrayList<>();

                        for (int k=0;k<jsonArray2.length();k++){
                            JSONObject jsonObject2=jsonArray2.optJSONObject(k);
                        }

                    }



                    menuModal.setInventoryPOModals(childList);

                    expandableListDetail.put(menuModal, childList);
                }


        } catch (JSONException e) {
            e.printStackTrace();
        }





    }




}
