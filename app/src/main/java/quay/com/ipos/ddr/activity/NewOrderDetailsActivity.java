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

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.modal.NewOrderPinnedResults;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.ddr.adapter.AddressListAdapter;
import quay.com.ipos.ddr.adapter.ItemsDetailListAdapter;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;

/**
 * Created by ankush.bansal on 20-04-2018.
 */

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
        setOrdersData();
    }

    private void setOrdersData() {
        tvTotalQty.setText(mOrderList.getTotalQty()+"");
        tvTotalPriceBeforeGst.setText(getResources().getString(R.string.Rs)+ " "+mOrderList.getTotalPrice()+"");
        tvCGSTPrice.setText("+ "+getResources().getString(R.string.Rs)+ " "+mOrderList.getCgst()+"");
        tvSGSTPrice.setText("+ "+getResources().getString(R.string.Rs)+ " "+mOrderList.getSgst()+"");
        if(mOrderList.getRound_off()==0.0)
            tvRoundingOffPrice.setText(getResources().getString(R.string.Rs)+ " "+mOrderList.getRound_off()+"");
        else
            tvRoundingOffPrice.setText("+ "+getResources().getString(R.string.Rs)+ " "+mOrderList.getRound_off()+"");
    }

    private void initializeComponent() {
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
                Intent mIntent = new Intent();
                setResult(6,mIntent);
                finish();
                break;
        }
    }
}
