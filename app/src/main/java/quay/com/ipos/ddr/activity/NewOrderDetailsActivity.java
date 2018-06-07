package quay.com.ipos.ddr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.enums.RetailSalesEnum;
import quay.com.ipos.modal.NewOrderPinnedResults;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.ddr.adapter.AddressListAdapter;
import quay.com.ipos.ddr.adapter.ItemsDetailListAdapter;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.realmbean.RealmOrderList;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;



public class NewOrderDetailsActivity extends BaseActivity implements View.OnClickListener{
    String[] address = {"1/82"};
    //    String[] items={"SoudaFoam 1k","SoudaFoam Pro"};
    String totalAmount;
    String json;
    OrderList mOrderList;
    TextView tvTotalQty,tvTotalPriceBeforeGst,tvCGSTPrice,tvSGSTPrice,tvRoundingOffPrice,btnAccept;

    private RecyclerView recycler_viewRecentOrders,recycler_viewAddress;
    private ItemsDetailListAdapter recentOrdersListAdapter;
    private AddressListAdapter addressListAdapter;
    private ArrayList<RecentOrderModal> recentOrderModalArrayList=new ArrayList<>();
    private ArrayList<RecentOrderModal> addressList=new ArrayList<>();
    TextView tvOrderName,OrderDate,tvStatus,orderValue,orderDiscount,deliverDate,loyaltyPoints,
            accumulatedPoints,totalPoints,customerName,discount,tvOrderValue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_detail);

        setHeader();
        initializeComponent();
        totalAmount = getIntent().getStringExtra(Constants.TOTAL_AMOUNT);
        json = getIntent().getStringExtra(Constants.Order_List);
        if (!json.equalsIgnoreCase(""))
            mOrderList = Util.getCustomGson().fromJson(json,OrderList.class);
//            mOrderList = Util.getCustomGson().fromJson(json, new TypeToken<ArrayList<OrderList>>() {
//            }.getType());
        addressListAdapter = new AddressListAdapter(this, recentOrderModalArrayList);
        recycler_viewAddress.setAdapter(addressListAdapter);

        getRecentOrdersData();
        getAddressData();
        //setOrdersData();
    }


    private void setAllData(){

        Realm realm=Realm.getDefaultInstance();
        RealmOrderList realmOrderLists=realm.where(RealmOrderList.class).equalTo("poNumber","P00001").findFirst();

        if (realmOrderLists!=null){


            tvOrderName.setText(realmOrderLists.getPoNumber());
            OrderDate.setText(realmOrderLists.getPoDate());
            tvStatus.setText(realmOrderLists.getPoStatus());
            orderValue.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getOrderValue());
            orderDiscount.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getDiscountValue());
            deliverDate.setText(realmOrderLists.getDeliveryBy());
            loyaltyPoints.setText(realmOrderLists.getOrderLoyality());
            accumulatedPoints.setText(realmOrderLists.getAccumulatedLoyality());
            totalPoints.setText(realmOrderLists.getTotalLoyality());
            customerName.setText(realmOrderLists.getCustomerName());
            discount.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getDiscountValue());
            tvOrderValue.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getOrderValue());
            tvTotalQty.setText(realmOrderLists.getQuantity()+"");
            tvTotalPriceBeforeGst.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getOrderValue()+"");
            tvCGSTPrice.setText("+ "+getResources().getString(R.string.Rs)+ " "+realmOrderLists.getTotalCGSTValue()+"");
            tvSGSTPrice.setText("+ "+getResources().getString(R.string.Rs)+ " "+realmOrderLists.getTotalSGSTValue()+"");
            tvRoundingOffPrice.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getTotalRoundingOffValue()+"");

        }




    }


    private void initializeComponent() {

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


        tvTotalQty = findViewById(R.id.tvTotalQty);
        tvTotalPriceBeforeGst = findViewById(R.id.tvTotalPriceBeforeGst);
        tvCGSTPrice = findViewById(R.id.tvCGSTPrice);
        tvSGSTPrice = findViewById(R.id.tvSGSTPrice);
        tvRoundingOffPrice = findViewById(R.id.tvRoundingOffPrice);
        btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);
        recycler_viewRecentOrders =  findViewById(R.id.recycler_viewItems);
        GridLayoutManager mLayoutManager4 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewRecentOrders.setLayoutManager(mLayoutManager4);
        recycler_viewRecentOrders.addItemDecoration(new SpacesItemDecoration(10));


        recycler_viewAddress =  findViewById(R.id.recycler_viewAddress);
        GridLayoutManager mLayoutManager5 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewAddress.setLayoutManager(mLayoutManager5);
        recycler_viewAddress.addItemDecoration(new SpacesItemDecoration(10));
    }


    public void setHeader() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar.setTitle("New Order");
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

    private void getRecentOrdersData() {
        recentOrdersListAdapter = new ItemsDetailListAdapter(this, IPOSApplication.mOrderList);
        recycler_viewRecentOrders.setAdapter(recentOrdersListAdapter);
    }


    private void getAddressData() {
        for (int i = 0; i < address.length; i++) {
            RecentOrderModal recentOrderModal = new RecentOrderModal();
            recentOrderModal.setTitle(address[i]);
            addressList.add(recentOrderModal);

        }
        addressListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.btnAccept:
//                Intent mIntent = new Intent(this,OrderCentreDetailsActivity.class);
//                startActivity(mIntent);
//                Intent mIntent = new Intent();
//                setResult(6,mIntent);
//                finish();
                Intent mIntent = new Intent();
                setResult(6,mIntent);
                finish();
                break;
        }
    }
}
