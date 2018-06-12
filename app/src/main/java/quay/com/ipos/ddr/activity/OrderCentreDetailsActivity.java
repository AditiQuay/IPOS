package quay.com.ipos.ddr.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
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
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.ddr.adapter.NewOrderItemsDetailListAdapter;
import quay.com.ipos.ddr.adapter.WorkFLowAdapter;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.ddr.adapter.AddressListAdapter;
import quay.com.ipos.ddr.adapter.ItemsDetailListAdapter;
import quay.com.ipos.ddr.adapter.WorkFLowUserAdapter;
import quay.com.ipos.ddr.modal.UserModal;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.realmbean.RealmBusinessPlaces;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.realmbean.RealmOrderList;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 20-04-2018.
 */

public class OrderCentreDetailsActivity extends BaseActivity implements MyListener{
    String[] address = {"1/82"};
    String[] items={"SoudaFoam 1k","SoudaFoam Pro"};
    String[] user={"KGM Traders","McCoy"};
    private TextView toolbarTtile,btnAccept;
    private RelativeLayout rlETA;
    private View viewETA;
    LinearLayout menu_item_container;
    GridLayoutManager mLayoutManager4,mLayoutManager5;
    private RecyclerView recycler_viewRecentOrders,recycler_viewAddress,recylerViewRoles;
    private NewOrderItemsDetailListAdapter recentOrdersListAdapter;
    private AddressListAdapter addressListAdapter;
    private ArrayList<RecentOrderModal> arrSearchList=new ArrayList<>();
    private ArrayList<RecentOrderModal> recentOrderModalArrayList=new ArrayList<>();
    private ArrayList<RealmBusinessPlaces> addressList=new ArrayList<>();

    private ArrayList<UserModal> stringArrayListRoles=new ArrayList<>();
    private ArrayList<UserModal> stringArrayListFlow=new ArrayList<>();
    private WorkFLowUserAdapter workFLowUserAdapter;
    private WorkFLowAdapter workFLowAdapter;
    private RecyclerView recylerViewFlow;
    TextView tvOrderName,OrderDate,tvStatus,orderValue,orderDiscount,deliverDate,loyaltyPoints,
            accumulatedPoints,totalPoints,customerName,discount,tvOrderValue,tvWith,tvDate,tvTime,
    tvAccept,tvCancel;
    private String serverResponse;
    private String poNumber;
    private LinearLayout llDetails,llFlow;
    private boolean isBack=false;
    LinearLayout llbottom_buttons;
    private LinearLayout llAccept,llCancel;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_center_detail);

        setHeader();
        menu_item_container=findViewById(R.id.menu_item_container);
        menu_item_container.setVisibility(View.GONE);

        intitiateView();
        getOrderCentre();
        toolbarTtile=findViewById(R.id.toolbarTtile);
        toolbarTtile.setText(getString(R.string.order_centre));
        btnAccept=findViewById(R.id.btnAccept);
        btnAccept.setText(getString(R.string.accept));

        rlETA= findViewById(R.id.rlETA);
        rlETA.setVisibility(View.VISIBLE);
        viewETA=findViewById(R.id.viewETA);
        viewETA.setVisibility(View.VISIBLE);


        //work flow
        recylerViewFlow = (RecyclerView) findViewById(R.id.recylerViewFlow);
        GridLayoutManager mLayoutManager7 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recylerViewFlow.setLayoutManager(mLayoutManager7);

        workFLowAdapter = new WorkFLowAdapter(this, stringArrayListFlow);
        recylerViewFlow.setAdapter(workFLowAdapter);
        recylerViewFlow.setVisibility(View.VISIBLE);


        //order items product
        recycler_viewRecentOrders = (RecyclerView) findViewById(R.id.recycler_viewItems);


        mLayoutManager4 = new GridLayoutManager(this, 1);
        recycler_viewRecentOrders.setLayoutManager(mLayoutManager4);
        recycler_viewRecentOrders.addItemDecoration(new SpacesItemDecoration(10));
        recentOrdersListAdapter = new NewOrderItemsDetailListAdapter(OrderCentreDetailsActivity.this, recentOrderModalArrayList);
        recycler_viewRecentOrders.setAdapter(recentOrdersListAdapter);

        // address
        recycler_viewAddress =  findViewById(R.id.recycler_viewAddress);
        mLayoutManager5 = new GridLayoutManager(this, 1);
        recycler_viewAddress.setLayoutManager(mLayoutManager5);
        recycler_viewAddress.addItemDecoration(new SpacesItemDecoration(10));

        addressListAdapter = new AddressListAdapter(this, addressList);
        recycler_viewAddress.setAdapter(addressListAdapter);

        recylerViewRoles = findViewById(R.id.recylerViewRoles);
        recylerViewRoles.setVisibility(View.VISIBLE);

        //user flow
        workFLowUserAdapter = new WorkFLowUserAdapter(mContext, stringArrayListRoles,this);
        recylerViewRoles.addItemDecoration(new SpacesItemDecoration(10));
        recylerViewRoles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recylerViewRoles.setAdapter(workFLowUserAdapter);


        if (serverResponse!=null)
        setAllData(serverResponse);

        llFlow=(LinearLayout)findViewById(R.id.llFLow);
      llDetails=(LinearLayout)findViewById(R.id.llDetails);
        LinearLayout llRetailer=(LinearLayout)findViewById(R.id.llRetailer);
       // LinearLayout llPartner=(LinearLayout)findViewById(R.id.llPartner);
        final LinearLayout menu_item_container=(LinearLayout)findViewById(R.id.menu_item_container);
        final ImageView imgArrow=(ImageView)findViewById(R.id.imgArrow);
        LinearLayout llbottom_buttons=(LinearLayout)findViewById(R.id.llbottom_buttons);
        llbottom_buttons.setVisibility(View.VISIBLE);

        llRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llDetails.setVisibility(View.GONE);
                llFlow.setVisibility(View.VISIBLE);
                menu_item_container.setVisibility(View.GONE);
                imgArrow.setImageResource(R.drawable.arrow_down);
            }
        });

       /* llPartner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llDetails.setVisibility(View.VISIBLE);
                llFlow.setVisibility(View.GONE);
                menu_item_container.setVisibility(View.VISIBLE);
                imgArrow.setImageResource(R.drawable.icon_right_arrow);


            }
        });*/

    /*    getRecentOrdersData();
        getAddressData();
        getuserData();*/
     //   getFlow();
//        getRecentOrdersData();
//        getAddressData();
//        getuserData();

    }

    private void intitiateView(){
        llAccept=findViewById(R.id.llAccept);
        llCancel=findViewById(R.id.llCancel);
        tvAccept=findViewById(R.id.btnAccept);
        tvCancel=findViewById(R.id.btnCancel);
        tvAccept.setText("Accept");
        tvCancel.setText("Reject");
        llbottom_buttons=findViewById(R.id.llbottom_buttons);
        llbottom_buttons.setVisibility(View.VISIBLE);
        tvOrderName = findViewById(R.id.tvOrderName);
        OrderDate = findViewById(R.id.OrderDate);
        tvStatus = findViewById(R.id.tvStatus);
        orderValue = findViewById(R.id.orderValue);
        orderDiscount = findViewById(R.id.orderDiscount);
        deliverDate = findViewById(R.id.deliverDate);
        loyaltyPoints = findViewById(R.id.loyaltyPoints);
        accumulatedPoints = findViewById(R.id.accumulatedPoints);
        totalPoints = findViewById(R.id.totalPoints);
        customerName = findViewById(R.id.customerName);
        discount = findViewById(R.id.discount);
        tvOrderValue = findViewById(R.id.tvOrderValue);
        tvWith = findViewById(R.id.tvWith);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);


        llAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder(2);
            }
        });

        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createOrder(3);
            }
        });
    }



    public void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isBack){
                    llDetails.setVisibility(View.VISIBLE);
                    llFlow.setVisibility(View.GONE);
                    isBack=true;
                }else {
                    finish();
                }
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void getRecentOrdersData() {
        for (int i = 0; i < items.length; i++) {
            RecentOrderModal recentOrderModal = new RecentOrderModal();
            recentOrderModal.setTitle(items[i]);

            recentOrderModalArrayList.add(recentOrderModal);

        }
        recentOrdersListAdapter.notifyDataSetChanged();
    }


    private void getAddressData(String businessPlaceCode) {

        Realm realm=Realm.getDefaultInstance();
        RealmResults<RealmBusinessPlaces> realmBusinessPlaces=realm.where(RealmBusinessPlaces.class).findAll();
        for (int i = 0; i < realmBusinessPlaces.size(); i++) {
            RealmBusinessPlaces realmBusinessPlaces1 = new RealmBusinessPlaces();
            realmBusinessPlaces1.setHeader("Shopping Details "+(i+1));
            realmBusinessPlaces1.setBuisnessPlaceName(realmBusinessPlaces.get(i).getBuisnessPlaceName());
            realmBusinessPlaces1.setBuisnessPlaceId(realmBusinessPlaces.get(i).getBuisnessPlaceId());
            realmBusinessPlaces1.setBuisnessLocationStateCode(realmBusinessPlaces.get(i).getBuisnessLocationStateCode());
            if (businessPlaceCode.equalsIgnoreCase(realmBusinessPlaces.get(i).getBuisnessPlaceId()+"")) {
                realmBusinessPlaces1.setSelected(true);

            }
            addressList.add(realmBusinessPlaces1);
        }
        addressListAdapter.notifyDataSetChanged();
    }




    private void getFlow(String listspendRequestHistoryPhaseModel) {
        try {
            JSONArray arrayCart=new JSONArray(listspendRequestHistoryPhaseModel);

            for (int p=0;p<arrayCart.length();p++){
                JSONObject jsonObject=arrayCart.optJSONObject(p);
                JSONObject jsonObject1=jsonObject.getJSONObject("listSpendRequestHistoryModel");
                UserModal userModal=new UserModal();
                userModal.setId(jsonObject.optString("positionStatus"));
                userModal.setUserDateStatus(""+jsonObject1.optString("header"));
                userModal.setUserName(""+jsonObject.optString("phaseName"));
                userModal.setUserStatus(""+jsonObject1.optString("status"));
                userModal.setComment(jsonObject1.optString("comment"));
                userModal.setFlag(jsonObject1.optString("flag"));
                userModal.setDate(jsonObject1.optString("date"));

                stringArrayListRoles.add(userModal);
                stringArrayListFlow.add(userModal);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        workFLowUserAdapter.notifyDataSetChanged();
        workFLowAdapter.notifyDataSetChanged();
    }
    private void setAllData(String serverResponse){


        try {
            JSONObject jsonObject=new JSONObject(serverResponse);
            poNumber=jsonObject.optString("poNumber");
            tvOrderName.setText(jsonObject.optString("poNumber"));
            OrderDate.setText(Util.getFormattedDates(jsonObject.optString("poDate"),Constants.formatDate,Constants.format2));
        //    OrderDate.setText(jsonObject.optString("poDate"));
            if (jsonObject.optString("poStatus").equalsIgnoreCase("1")){
                tvStatus.setText("Pending");
            }

            orderValue.setText(getResources().getString(R.string.Rs)+ " "+jsonObject.optInt("orderValue"));
            orderDiscount.setText(getResources().getString(R.string.Rs)+ " "+jsonObject.optInt("discountValue"));
          //  deliverDate.setText(jsonObject.optString("deliveryBy"));
            deliverDate.setText(Util.getFormattedDates(jsonObject.optString("deliveryBy"),Constants.formatDate,Constants.format2));

            loyaltyPoints.setText(jsonObject.optInt("orderLoyality")+"");
            accumulatedPoints.setText(jsonObject.optInt("accumulatedLoyality")+"");
            totalPoints.setText(jsonObject.optInt("totalLoyality")+"");
            customerName.setText(jsonObject.optString("customerName"));
            discount.setText(getResources().getString(R.string.Rs)+ " "+jsonObject.optInt("discountValue"));
            tvOrderValue.setText(getResources().getString(R.string.Rs)+ " "+jsonObject.optInt("orderValue"));
            getFlow(jsonObject.optString("listspendRequestHistoryPhaseModel")+"");

            getAddressData(jsonObject.optString("businessPlaceCode"));
            try {
                JSONArray arrayCart= null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    arrayCart = new JSONArray(jsonObject.optString("cartDetail"));
                }

                for (int p=0;p<arrayCart.length();p++){
                    JSONObject jsonObject1=arrayCart.optJSONObject(p);
                    RecentOrderModal recentOrderModal=new RecentOrderModal();
                    recentOrderModal.setTitle(jsonObject1.optString("materialName"));
                    recentOrderModal.setQty(""+jsonObject1.optInt("materialQty"));
                    recentOrderModal.setDiscountValue(""+jsonObject1.optInt("materialDiscountValue"));
                    recentOrderModal.setValue(""+jsonObject1.optInt("materialValue"));

                    recentOrderModalArrayList.add(recentOrderModal);


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
       recentOrdersListAdapter.notifyDataSetChanged();




    }


    public void getOrderCentre() {
        final ProgressDialog progressDialog=new ProgressDialog(OrderCentreDetailsActivity.this);
        JSONObject jsonObject1=new JSONObject();

        try {
            jsonObject1.put("employeeCode","1");
            jsonObject1.put("employeeRole","user");
            jsonObject1.put("businessPlaceCode","1");
            jsonObject1.put("entityRole","manager");
            jsonObject1.put("entityCode","1");
            jsonObject1.put("searchParam","18000001");
            jsonObject1.put("barCodeNumber","string");
            jsonObject1.put("entityStateCode","06");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.WEB_SERVICE_NOOrderDetail;

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
                            serverResponse=responseData;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setAllData(serverResponse);
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
        isBack=false;
        llDetails.setVisibility(View.GONE);
        llFlow.setVisibility(View.VISIBLE);

        String date=stringArrayListRoles.get(position).getDate();
        String header=stringArrayListRoles.get(position).getUserDateStatus();

        tvWith.setText(header);
        if (Util.validateString(Html.fromHtml(date).toString())){
            tvDate.setText(Html.fromHtml(date).toString().split(" ")[0]);
            tvTime.setText(Html.fromHtml(date).toString().split(" ")[1]);
        }else {
            tvDate.setText("");
            tvTime.setText("");
        }


    }

    @Override
    public void onRowClicked(int position, int value) {




    }

    private void createOrder(int status){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmNewOrderCart> realmNewOrderCarts1 = realm.where(RealmNewOrderCart.class).findAll();

        JSONArray arrayCart=new JSONArray();
        int qty = 0;
        double payAmount=0.0;
        int discountItems = 0;
        int gst = 0;
        int totalGST = 0;
        int cgst = 0;
        int sgst = 0;
        double totalItemsAmount = 0.0;
        double discountPrice = 0.0;
        int totalPoints = 0;
        int noOfItems = 0;
        String poNumber = null;
        for (RealmNewOrderCart realmNewOrderCart : realmNewOrderCarts1) {

            JSONObject jsonObjectCartDetail=new JSONObject();
            if (!realmNewOrderCart.isFreeItem())
                noOfItems = noOfItems + 1;
            poNumber=realmNewOrderCart.getOrderId();
            qty = qty + realmNewOrderCart.getQty();
            totalItemsAmount = totalItemsAmount + realmNewOrderCart.getTotalPrice();
            if (realmNewOrderCart.isDiscount() && !realmNewOrderCart.isFreeItem()) {
                discountItems = discountItems + 1;
            }
            if (!realmNewOrderCart.isFreeItem()) {
                try {
                    JSONArray array = new JSONArray(realmNewOrderCart.getDiscount());
                    for (int k = 0; k < array.length(); k++) {
                        JSONObject jsonObject = array.optJSONObject(k);
                        if (jsonObject.has("discountTotal")) {
                            discountPrice = discountPrice + jsonObject.optInt("discountTotal");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                discountPrice = discountPrice + realmNewOrderCart.getTotalPrice();
            }
            totalGST = (realmNewOrderCart.getGstPerc() * realmNewOrderCart.getTotalPrice() / 100);
            gst = gst + totalGST;



            cgst = cgst + (realmNewOrderCart.getCgst() * realmNewOrderCart.getTotalPrice() / 100);
            sgst = sgst + (realmNewOrderCart.getSgst() * realmNewOrderCart.getTotalPrice() / 100);




            totalPoints = totalPoints + realmNewOrderCart.getTotalPoints();
            JSONArray scheme=new JSONArray();

            try {
                JSONArray discountArray = new JSONArray(realmNewOrderCart.getDiscount());
                for (int k = 0; k < discountArray.length(); k++) {
                    JSONObject jsonObjectScheme=new JSONObject();
                    JSONObject jsonObject = discountArray.optJSONObject(k);

                    JSONArray jsonArrayRule=  jsonObject.getJSONArray("rule");

                    for (int m=0;m<jsonArrayRule.length();m++){

                        JSONObject jsonObject1=jsonArrayRule.optJSONObject(m);
                        if (jsonObject1.optBoolean("isRuleApplied")){
                            jsonObjectScheme.put("schemeID",k+1);
                            jsonObjectScheme.put("ruleID",jsonObject1.optString("ruleID"));
                            jsonObjectScheme.put("discountValue",jsonObject.optString("discountTotal"));
                            jsonObjectScheme.put("discountPerc",jsonObject1.optString("sDiscountValue"));
                            jsonObjectScheme.put("oldSchemeID",k+1);
                            jsonObjectScheme.put("oldRuleID",jsonObject1.optString("ruleID"));


                        }

                    }
                    if (!jsonObjectScheme.toString().equalsIgnoreCase("{}"))
                        scheme.put(jsonObjectScheme);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }




            try {
                jsonObjectCartDetail.put("oldMaterialCode",realmNewOrderCart.getiProductModalId());
                jsonObjectCartDetail.put("materialCode",realmNewOrderCart.getiProductModalId());
                jsonObjectCartDetail.put("materialName",realmNewOrderCart.getsProductName());
                jsonObjectCartDetail.put("materialValue",realmNewOrderCart.getTotalPrice());
                jsonObjectCartDetail.put("materialQty",realmNewOrderCart.getQty());
                jsonObjectCartDetail.put("materialDiscountValue",discountPrice);
                jsonObjectCartDetail.put("materialUnitValue",realmNewOrderCart.getsProductPrice());
                jsonObjectCartDetail.put("materialCGSTRate",realmNewOrderCart.getCgst());
                jsonObjectCartDetail.put("materialCGSTValue",cgst);
                jsonObjectCartDetail.put("materialSGSTRate",realmNewOrderCart.getSgst());
                jsonObjectCartDetail.put("materialSGSTValue",sgst);
                jsonObjectCartDetail.put("materialIGSTRate",realmNewOrderCart.getGstPerc());
                jsonObjectCartDetail.put("materialIGSTValue",gst);
                jsonObjectCartDetail.put("scheme",scheme);
                jsonObjectCartDetail.put("discountValue",discountPrice);
                jsonObjectCartDetail.put("discountPerc",0);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            arrayCart.put(jsonObjectCartDetail);

        }
        payAmount = (totalItemsAmount + gst) - discountPrice;

        double totalValueWithTax=totalItemsAmount+gst;
        double totalValueWithoutTax=totalItemsAmount;
        JSONObject jsonObject=new JSONObject();


        try {
            jsonObject.put("employeeCode","6000013");
            jsonObject.put("employeeRole","user");
            jsonObject.put("poDate",Util.getCurrentDate());
            jsonObject.put("poStatus","Pending");
            jsonObject.put("orderValue",payAmount);
            jsonObject.put("discountValue",discountPrice);
            jsonObject.put("deliveryBy",Util.getCurrentDate());
            jsonObject.put("orderLoyality",totalPoints);
            jsonObject.put("accumulatedLoyality",0);
            jsonObject.put("totalLoyality",totalPoints);
            jsonObject.put("businessPlace","Store 4 ,noida , sector 56 ,Noida ,UP ,180090");
            jsonObject.put("businessPlaceCode","1");
            jsonObject.put("entityID","1");
            jsonObject.put("totalValueWithTax",totalValueWithTax);
            jsonObject.put("totalCGSTValue",cgst);
            jsonObject.put("totalIGSTValue",gst);
            jsonObject.put("totalSGSTValue",sgst);
            jsonObject.put("totalValueWithoutTax",totalValueWithoutTax);
            jsonObject.put("totalTaxValue",cgst+sgst);
            jsonObject.put("totalDiscountValue",discountPrice);
            jsonObject.put("totalRoundingOffValue",0);
            jsonObject.put("cartDetail",arrayCart);
            //  jsonObject.put("listspendRequestHistoryPhaseModel",new JSONArray());
            jsonObject.put("approvalStat",status);
            jsonObject.put("quantity",qty);
            jsonObject.put("customerName","");
            jsonObject.put("discount",new JSONArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        submitRequest(jsonObject.toString());


    }
    public void submitRequest(String jsonObject1) {
        final ProgressDialog progressDialog=new ProgressDialog(OrderCentreDetailsActivity.this);

        JSONObject jsonObject= null;
        try {
            jsonObject = new JSONObject(serverResponse);
            jsonObject.put("discount",new JSONArray());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject.toString());
        String url = IPOSAPI.WEB_SERVICE_NOTransaction;

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

                            final JSONObject jsonObject1=new JSONObject(responseData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Util.showToast(jsonObject1.optString("message"));
                                    finish();
                                }
                            });



                        }


                    } else {
                        finish();


                    }

                } catch (Exception e) {
                    e.printStackTrace();



                }
            }
        });
    }

}
