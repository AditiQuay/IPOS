package quay.com.ipos.retailsales.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.modal.CustomerResult;
import quay.com.ipos.retailsales.adapter.RecentOrdersListAdapter;
import quay.com.ipos.ui.CircleTransform;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 26-04-2018.
 */

public class CustomerDetailActivityNew extends BaseActivity {
    private RecyclerView recycler_viewRecentOrders;
    private RecentOrdersListAdapter recentOrdersListAdapter;
    String json;
    CustomerResult.Customer mCustomer;
    private ArrayList<CustomerResult.RecentOrder> recentOrderModalArrayList=new ArrayList<>();
    private ImageView imvUserImage;
    private TextView tvName,tvMobileNo,tvEmail,tvBillingAmount,tvBillingDate,tvBirthday,tvRedeemPoints;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_detail);
        json = getIntent().getStringExtra(Constants.customer_detail);
        setHeader();
        initializeComponent();
        setAdapter();
    }

    private void setAdapter() {
        recentOrdersListAdapter = new RecentOrdersListAdapter(this, recentOrderModalArrayList);
        recycler_viewRecentOrders.setAdapter(recentOrdersListAdapter);
    }

    private void initializeComponent() {
        tvName =  findViewById(R.id.tvName);
        tvMobileNo =  findViewById(R.id.tvMobileNo);
        tvEmail =  findViewById(R.id.tvEmail);
        tvBillingAmount =  findViewById(R.id.tvBillingAmount);
        tvBillingDate =  findViewById(R.id.tvBillingDate);
        tvBirthday =  findViewById(R.id.tvBirthday);
        tvRedeemPoints = findViewById(R.id.tvRedeemPoints);
        imvUserImage = findViewById(R.id.imvUserImage);
        recycler_viewRecentOrders =  findViewById(R.id.recycler_viewRecentOrders);
        GridLayoutManager mLayoutManager4 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewRecentOrders.setLayoutManager(mLayoutManager4);
        recycler_viewRecentOrders.addItemDecoration(new SpacesItemDecoration(10));

        mCustomer = Util.getCustomGson().fromJson(json, CustomerResult.Customer.class);
        recentOrderModalArrayList.addAll(mCustomer.getRecentOrders());

        tvName.setText(mCustomer.getCustomerName());
        tvBillingDate.setText(mCustomer.getLastBilling().getLastBillingDate());
        tvMobileNo.setText(mCustomer.getCustomerPhone());
        tvBillingAmount.setText(mCustomer.getLastBilling().getLastBillingAmount());
        tvBirthday.setText(mCustomer.getCustomerBday());
        tvEmail.setText(mCustomer.getCustomerEmail());
        tvRedeemPoints.setText(mCustomer.getCustomerPoints());
        Glide.with(mContext).load(Constants.UserProfilePic)
                .thumbnail(0.5f)
                .crossFade()
                .transform(new CircleTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imvUserImage);
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

}
