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
import android.support.annotation.NonNull;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
import quay.com.ipos.inventory.adapter.InventorPOInccoAdapter;
import quay.com.ipos.inventory.adapter.InventoryAttachmentAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailsPOEditListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailsPOListAdapter;
import quay.com.ipos.inventory.adapter.MilestonePOListAdapter;
import quay.com.ipos.inventory.adapter.PaymentTermsPOListAdapter;
import quay.com.ipos.inventory.adapter.TermsPOListAdapter;
import quay.com.ipos.inventory.attachments.AttachFileModel;
import quay.com.ipos.inventory.modal.GrnAttachment;
import quay.com.ipos.inventory.modal.GrnInccoTermsModel;
import quay.com.ipos.inventory.modal.InventoryPOModal;
import quay.com.ipos.inventory.modal.POAttachments;
import quay.com.ipos.inventory.modal.POIncoTerms;
import quay.com.ipos.inventory.modal.POItemDetail;
import quay.com.ipos.inventory.modal.POPaymentTerms;
import quay.com.ipos.inventory.modal.POTermsCondition;
import quay.com.ipos.listeners.AttachmentListener;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.modal.MenuModal;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmInventoryProducts;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;


public class EditExpandablePODetailsActivity extends BaseActivity implements MyListener,InventorPOInccoAdapter.OnCalculateTotalIncoTermsListener,AttachmentListener {
    private Dialog myDialog;
    ExpandableListView expandableListView;
    ArrayList<AttachFileModel> attachFileModels = new ArrayList<>();
    //CustomExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<MenuModal, List<InventoryPOModal>> expandableListDetail = new LinkedHashMap<MenuModal, List<InventoryPOModal>>();
    LinearLayout llTermsC,llAttachments,llPODetails,llItemsDetails,llIncoTerms,llPaymentTerms;
    RelativeLayout POHashitems,POitemsDetails,POIncoTerms,POPaymentTerms,POTermsandCondition,POAttachment;
    EditText edtPoNumber,edtPoDate,edtPoValDate,edtPoValue,edtPoGST,edtSupplierName,edtSupAddress,edtSupGSTIN,edtBillingAddress,edtDeliveryAddress;
    private RecyclerView recycler_viewItemDetail,recycler_viewInco,recycler_viewPayment,recycler_viewTerms,recycler_viewAttachment;
    ItemsDetailsPOEditListAdapter itemListDataAdapter;
    InventorPOInccoAdapter incoTermsPOListAdapter;
    PaymentTermsPOListAdapter milestonePOListAdapter;
    TermsPOListAdapter termsPOListAdapter;
    AttachFileAdapter attachmentsPOListAdapter;
    Context context;
    ArrayList<POItemDetail> poItemDetails=new ArrayList<>();
    ArrayList<GrnInccoTermsModel> poIncoTerms=new ArrayList<>();
    ArrayList<POPaymentTerms> poPaymentTerms=new ArrayList<>();
    ArrayList<POTermsCondition> poTermsConditions=new ArrayList<>();
    ArrayList<GrnAttachment> poAttachments=new ArrayList<>();
    String poNumber,businessPlaceId;
    private TextView tvHeaderPoNumber,tvHeaderPOItemDetail;
    private Spinner spnMilestone;
    private TextView tvAddTerms;
    private View ImvClose;
    private TextView textTotalIncoTerms;
    private TextView tvAddAttach;

    private Button btnSave;
    private TextView tvAddItems;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.po_expandable_details);
        context=EditExpandablePODetailsActivity.this;
        setHeader();
        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
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
        btnSave=findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitGRNDetails();
            }
        });
        tvAddItems=findViewById(R.id.tvAddItems);
        tvAddItems.setVisibility(View.VISIBLE);
        tvAddItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EditExpandablePODetailsActivity.this,InventoryItemAddNewOrderActivity.class);
                startActivity(i);
            }
        });
        tvAddAttach = findViewById(R.id.tvAddAttach);
        tvAddAttach.setVisibility(View.VISIBLE);
        tvAddAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 onAttachFileClicked();
            }
        });
        textTotalIncoTerms = findViewById(R.id.textTotalIncoTerms);
        tvAddTerms=findViewById(R.id.tvAddTerms);
        tvAddTerms.setVisibility(View.VISIBLE);
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

                            setTermsCondition();
                            myDialog.dismiss();


                        }
                    }
                });


                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();

            }
        });

        setIncosTerms();
    }

    private void setIncosTerms(){
        JSONArray jsonArray= null;
        try {
            jsonArray = new JSONArray(Util.getAssetJsonResponse(EditExpandablePODetailsActivity.this,"inco.json"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        double total=0;
        for (int j = 0; j < jsonArray.length(); j++) {

            JSONObject jsonObject1 = jsonArray.optJSONObject(j);

            GrnInccoTermsModel poIncoTerms1=new GrnInccoTermsModel();
            poIncoTerms1.grnIncoDetail=jsonObject1.optString("poIncoDetail");
            poIncoTerms1.grnPayAmount=jsonObject1.optDouble("poPayAmount");
            poIncoTerms1.grnPayByReceiver=jsonObject1.optBoolean("poPayByReceiver");
            poIncoTerms1.grnPayBySender=jsonObject1.optBoolean("poPayBySender");
            poIncoTerms.add(poIncoTerms1);

            total=total+jsonObject1.optDouble("poPayAmount");

            setIncoTerms();

        }
    }

    private void getProducts(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmInventoryProducts> realmNewOrderCarts1 = realm.where(RealmInventoryProducts.class).findAll();
        poItemDetails.clear();

        int qty=0;
        for (RealmInventoryProducts realmNewOrderCart : realmNewOrderCarts1) {
            RealmInventoryProducts realmNewOrderCarts = realm.copyFromRealm(realmNewOrderCart);

            POItemDetail poItemDetail=new POItemDetail();
            poItemDetail.setTitle(realmNewOrderCarts.getsProductName());
            poItemDetail.setPoItemUnitPrice(realmNewOrderCarts.getsProductPrice());
            poItemDetail.setPoItemAmount(realmNewOrderCarts.getQty()*realmNewOrderCarts.getsProductPrice());
            poItemDetail.setPoItemIGSTValue(((realmNewOrderCarts.getSgst()+realmNewOrderCarts.getCgst())*realmNewOrderCarts.getQty()*realmNewOrderCarts.getsProductPrice())/100);
            poItemDetail.setPoItemCGSTPer(realmNewOrderCarts.getCgst());
            poItemDetail.setPoItemSGSTPer(realmNewOrderCarts.getSgst());
            poItemDetail.setPoItemQty(1);
            qty=qty+1;
            poItemDetails.add(poItemDetail);
        }
        tvHeaderPOItemDetail.setText("Item Details | "+ poItemDetails.size()+" Items | Qty "+qty );
        //  mList.addAll(realmNewOrderCarts1);

        setItemDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProducts();
    }

    private String createJson() {
        JSONObject jsonObject = new JSONObject();
        JSONArray poDetails = new JSONArray();

        try {



            JSONArray IncoTermsArray = new JSONArray();
            for (int j = 0; j < poIncoTerms.size(); j++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnIncoDetail", poIncoTerms.get(j).grnIncoDetail);
                jsonObject1.put("grnPayBySender", poIncoTerms.get(j).grnPayBySender);
                jsonObject1.put("grnPayByReceiver", poIncoTerms.get(j).grnPayByReceiver);
                jsonObject1.put("grnPayAmount", poIncoTerms.get(j).grnPayAmount);

                IncoTermsArray.put(jsonObject1);
            }

            JSONArray jsonArrayAttachments = new JSONArray();
          /*  for (int j = 0; j < poAttachments.size(); j++) {
                JSONObject jsonObject1 = new JSONObject();
                jsonObject1.put("grnAttachmentName", grnAttachments.get(j).getGrnAttachmentName());
                jsonObject1.put("grnAttachmentUrl", grnAttachments.get(j).getGrnAttachmentUrl());
                jsonObject1.put("grnAttachmentType", grnAttachments.get(j).getGrnAttachmentType());
                jsonArrayAttachments.put(jsonObject1);
            }
*/
            //attach new

            for (int i = 0; i < poAttachments.size(); i++) {
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

        return jsonObject.toString();

      //  new RealmController().saveGRNDetails(jsonObject.toString());

    }

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
        incoTermsPOListAdapter = new InventorPOInccoAdapter(EditExpandablePODetailsActivity.this, poIncoTerms,this);
        recycler_viewInco.setAdapter(incoTermsPOListAdapter);
    }

    private void setPaymentTerms(){


        recycler_viewPayment=findViewById(R.id.recycler_viewPayment);
        recycler_viewPayment.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(EditExpandablePODetailsActivity.this);
        recycler_viewPayment.setLayoutManager(mLayoutManager);
        milestonePOListAdapter = new PaymentTermsPOListAdapter(EditExpandablePODetailsActivity.this, poPaymentTerms);
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
        attachmentsPOListAdapter = new AttachFileAdapter(attachFileModels);
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

                GrnInccoTermsModel poIncoTerms1=new GrnInccoTermsModel();
                poIncoTerms1.grnIncoDetail=jsonObject1.optString("poIncoDetail");
                poIncoTerms1.grnPayAmount=jsonObject1.optDouble("poPayAmount");
                poIncoTerms1.grnPayByReceiver=jsonObject1.optBoolean("poPayByReceiver");
                poIncoTerms1.grnPayBySender=jsonObject1.optBoolean("poPayBySender");
                poIncoTerms.add(poIncoTerms1);

                total=total+jsonObject1.optDouble("poPayAmount");



            }
          /*  POIncoTerms poIncoTerms2=new POIncoTerms();
            poIncoTerms2.setPoIncoDetail("Total");
            poIncoTerms2.setPoPayAmount(total);
            poIncoTerms2.setPoPayByReceiver(false);
            poIncoTerms2.setPoPayBySender(false);
            poIncoTerms.add(poIncoTerms2);*/


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

                GrnAttachment poAttachments1=new GrnAttachment();
                poAttachments1.setGrnAttachmentName(jsonObject1.optString("pOAttachmentName"));
                poAttachments1.setGrnAttachmentType(jsonObject1.optString("pOAttachmentType"));
                poAttachments1.setGrnAttachmentUrl(jsonObject1.optString("pOAttachmentUrl"));

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
        poItemDetail.setPoItemQty(value);
        poItemDetails.set(position,poItemDetail);

        itemListDataAdapter.notifyItemChanged(position);
        int qty=0;
        for (int l=0;l<poItemDetails.size();l++) {
            qty+=poItemDetails.get(l).getPoItemQty();

        }
        tvHeaderPOItemDetail.setText("Item Details | " + poItemDetails.size() + " Items | Qty " +qty);
    }

    @Override
    public void funIncoTermsTotalCount(double totalIncoTerms) {
        textTotalIncoTerms.setText(totalIncoTerms + "");

    }

    private static final int PICKFILE_RESULT_CODE = 101;

    @Override
    public void onAttachmentClicked(int position) {

    }

    public void onAttachFileClicked() {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, PICKFILE_RESULT_CODE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        onActivityResultAttachment(requestCode,resultCode,data);
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
                        Log.i("fileSize", fileSize+"");
                        long twoMb = 1024 * 1024 * 2;

                        if(fileSize <= twoMb) {
                            AttachFileModel fileModel = new AttachFileModel();
                            fileModel.fileName = fileName;
                            fileModel.mimeType = mimeType;
                            fileModel.uri = uri;

                            attachFileModels.add(fileModel);
                            updateSize();
                            String FilePath = data.getData().getPath();
                        }else {
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
            recycler_viewAttachment.setLayoutManager(new LinearLayoutManager(mContext));
            // inventoryAttachmentAdapter = new InventoryAttachmentAdapter(mContext, grnAttachments, this);
            //  rvAttachment.setAdapter(inventoryAttachmentAdapter);

            recycler_viewAttachment.setAdapter(new AttachFileAdapter(attachFileModels));

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
                    shareIntent.setDataAndType(Uri.parse(fileModel.uri.toString()),fileModel.mimeType);
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

    public void submitGRNDetails() {


        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, createJson());
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
}
