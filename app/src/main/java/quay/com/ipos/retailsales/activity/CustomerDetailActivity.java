package quay.com.ipos.retailsales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.retailsales.adapter.RecentOrdersListAdapter;
import quay.com.ipos.utility.SpacesItemDecoration;

/**
 * Created by ankush.bansal on 20-04-2018.
 */

public class CustomerDetailActivity extends BaseActivity{
    String[] inventory = {"My Store 1", "Ecommerce Online","My Store 2"};

    private RecyclerView recycler_viewRecentOrders;
    private RecentOrdersListAdapter recentOrdersListAdapter;
    private ArrayList<RecentOrderModal> recentOrderModalArrayList=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_detail);

        setHeader();
        recycler_viewRecentOrders = (RecyclerView) findViewById(R.id.recycler_viewRecentOrders);
        GridLayoutManager mLayoutManager4 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewRecentOrders.setLayoutManager(mLayoutManager4);
        recycler_viewRecentOrders.addItemDecoration(new SpacesItemDecoration(10));
        recentOrdersListAdapter = new RecentOrdersListAdapter(this, recentOrderModalArrayList);
        recycler_viewRecentOrders.setAdapter(recentOrdersListAdapter);

        getRecentOrdersData();
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

    private void getRecentOrdersData() {
        for (int i = 0; i < inventory.length; i++) {
            RecentOrderModal recentOrderModal = new RecentOrderModal();
            recentOrderModal.setTitle(inventory[i]);

            recentOrderModalArrayList.add(recentOrderModal);

        }
        recentOrdersListAdapter.notifyDataSetChanged();
    }

}
