package quay.com.ipos.inventory.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

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
import quay.com.ipos.inventory.adapter.InventoryGRNStepsAdapter;
import quay.com.ipos.inventory.adapter.InventoryListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailListAdapter;
import quay.com.ipos.inventory.adapter.WorkFLowUserAdapter;
import quay.com.ipos.inventory.modal.InventoryGRNModel;
import quay.com.ipos.inventory.modal.InventoryModel;
import quay.com.ipos.inventory.modal.UserModal;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.realmbean.RealmBusinessPlaces;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.realmbean.RealmOrderCentre;
import quay.com.ipos.realmbean.RealmPOInventory;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.SpacesItemDecoration;

/**
 * Created by niraj.kumar on 6/13/2018.
 */

public class InventoryGRNStepsActivity extends AppCompatActivity implements InitInterface, View.OnClickListener,MyListener {

    String[] address = {"1/82"};
    String[] items={"PO180001","PO180002"};
    String[] user={"KGM Traders","McCoy"};

    private RecyclerView recycler_viewRecentOrders, recycleview,recylerViewRoles;
    private ItemsDetailListAdapter recentOrdersListAdapter;
    private InventoryListAdapter inventoryListAdapter;
    private ArrayList<RecentOrderModal> arrSearchList=new ArrayList<>();
    private ArrayList<RealmPOInventory> inventoryModels=new ArrayList<>();
    private ArrayList<RealmBusinessPlaces> addressList=new ArrayList<>();
    private ArrayList<UserModal> stringArrayListRoles=new ArrayList<>();
    private WorkFLowUserAdapter workFLowUserAdapter;


    private RecyclerView recycleviewCard;
    private TextView textViewAdd, tvGrnNumberCount, tvPoNumber, poQtyCount, grnQtyCount, apQtyCount, balanceQtyCount;
    private Toolbar toolbar;
    private LinearLayoutManager layoutManager;
    private Context mContext;
    private ArrayList<InventoryGRNModel> inventoryGRNModels = new ArrayList<>();
    private InventoryGRNStepsAdapter kycViewAllAdapter;
    private  String requestJson,busineesPlaceId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grn_steps_activity);
        mContext = InventoryGRNStepsActivity.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        getIntents();
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        GridLayoutManager mLayoutManager5 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleview.setLayoutManager(mLayoutManager5);
        recycleview.addItemDecoration(new SpacesItemDecoration(10));
        inventoryListAdapter = new InventoryListAdapter(this, inventoryModels,this);
        recycleview.setAdapter(inventoryListAdapter);

        //getRecentOrdersData();

        final RelativeLayout rlTab=findViewById(R.id.rlTab);
        final RelativeLayout llgrnn=findViewById(R.id.llgrnn);
        final TextView tvGrn = findViewById(R.id.tvGrn);
        LinearLayout lLayoutGrn=findViewById(R.id.lLayoutGrn);

        final TextView tvPO=findViewById(R.id.tvPO);
        LinearLayout poLayout=findViewById(R.id.poLayout);

   //     final RelativeLayout rLayoutMain=findViewById(R.id.rLayoutMain);
        poLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGrn.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvPO.setBackgroundResource(R.drawable.textview_circle_app_color);
                recycleview.setVisibility(View.VISIBLE);
                recycleviewCard.setVisibility(View.GONE);
                rlTab.setVisibility(View.GONE);
                llgrnn.setVisibility(View.GONE);
              //  rLayoutMain.setVisibility(View.GONE);
            }
        });

        lLayoutGrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvPO.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvGrn.setBackgroundResource(R.drawable.textview_circle_app_color);
                recycleview.setVisibility(View.GONE);
                recycleviewCard.setVisibility(View.VISIBLE);
                rlTab.setVisibility(View.VISIBLE);
                llgrnn.setVisibility(View.VISIBLE);
             //   rLayoutMain.setVisibility(View.VISIBLE);
            }
        });

        getPODetails();

    }
    private void getRecentOrdersData() {
        for (int i = 0; i < items.length; i++) {
            RealmPOInventory inventoryModel = new RealmPOInventory();
            inventoryModel.setPoNumber(items[i]);

            inventoryModels.add(inventoryModel);

        }
        inventoryListAdapter.notifyDataSetChanged();
    }

    private void getIntents(){
        Intent i=getIntent();
        if (i!=null)
        requestJson=i.getStringExtra("request");
        assert i != null;
        busineesPlaceId=i.getStringExtra("businessPlaceId");


    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        recycleviewCard = findViewById(R.id.recycleviewCard);
        textViewAdd = findViewById(R.id.textViewAdd);
        tvGrnNumberCount = findViewById(R.id.tvGrnNumberCount);
        tvPoNumber = findViewById(R.id.tvPoNumber);
        poQtyCount = findViewById(R.id.poQtyCount);
        grnQtyCount = findViewById(R.id.grnQtyCount);
        apQtyCount = findViewById(R.id.apQtyCount);
        balanceQtyCount = findViewById(R.id.balanceQtyCount);
        textViewAdd.setOnClickListener(this);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("INVENTORY");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));


        layoutManager = new LinearLayoutManager(mContext);
        recycleviewCard.setHasFixedSize(true);
        recycleviewCard.setLayoutManager(layoutManager);
        kycViewAllAdapter = new InventoryGRNStepsAdapter(mContext, inventoryGRNModels);
        recycleviewCard.setAdapter(kycViewAllAdapter);
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
        if (v == textViewAdd) {
            InventoryGRNModel inventoryGRNModel = new InventoryGRNModel();
            inventoryGRNModel.grnQty = "1";
            inventoryGRNModel.apQTY = "2";
            inventoryGRNModel.value = "26,480";
            inventoryGRNModels.add(inventoryGRNModel);
            kycViewAllAdapter.notifyDataSetChanged();
        }
    }


    public void getPODetails() {
        final ProgressDialog progressDialog=new ProgressDialog(InventoryGRNStepsActivity.this);
        JSONObject jsonObject1=new JSONObject();



        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, requestJson);
        String url = IPOSAPI.WEB_SERVICE_INventoryPONUMBERS;

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

                        String responseData = response.body().string();
                        if (responseData != null) {

                            JSONObject jsonObject=new JSONObject(responseData);
                            JSONArray array=jsonObject.optJSONArray("pODetails");

                            for (int i=0;i<array.length();i++){
                                JSONObject jsonObject2=array.optJSONObject(i);
                                JSONObject jsonObject3=new JSONObject();
                                jsonObject3.put("poNumber",jsonObject2.optString("poNumber"));
                                jsonObject3.put("id",jsonObject2.optString("supplierCode"));
                                jsonObject3.put("date",jsonObject2.optString("poDate"));
                                jsonObject3.put("value",jsonObject2.optString("poValue"));
                                jsonObject3.put("company",jsonObject2.optString("supplierName"));
                                jsonObject3.put("poStatus",jsonObject2.optString("poStatus"));
                                new RealmController().savePODetails(jsonObject3.toString());
                            }

                            // saveResponseLocalCreateOrder(jsonObject,requestId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setAllData();
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

    private void setAllData() {

        inventoryModels.clear();
        Realm realm=Realm.getDefaultInstance();
        RealmResults<RealmPOInventory> realmPOInventories=realm.where(RealmPOInventory.class).findAll();
        for (RealmPOInventory realmNewOrderCart : realmPOInventories) {
            RealmPOInventory realmPOInventory = realm.copyFromRealm(realmNewOrderCart);
            inventoryModels.add(realmPOInventory);
        }
        inventoryListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onRowClicked(int position) {
        Intent i=new Intent(mContext, ExpandablePODetailsActivity.class);
        i.putExtra("poNumber",inventoryModels.get(position).getPoNumber());
        i.putExtra("businessPlaceId",busineesPlaceId);
        mContext.startActivity(i);
    }

    @Override
    public void onRowClicked(int position, int value) {

    }
}


