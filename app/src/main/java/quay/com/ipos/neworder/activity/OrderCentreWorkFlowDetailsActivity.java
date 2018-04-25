package quay.com.ipos.neworder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.neworder.adapter.AddressListAdapter;
import quay.com.ipos.neworder.adapter.ItemsDetailListAdapter;
import quay.com.ipos.neworder.adapter.WorkFLowUserAdapter;
import quay.com.ipos.neworder.modal.UserModal;
import quay.com.ipos.utility.SpacesItemDecoration;

/**
 * Created by ankush.bansal on 20-04-2018.
 */

public class OrderCentreWorkFlowDetailsActivity extends BaseActivity{
    String[] address = {"1/82"};
    String[] items={"SoudaFoam 1k","SoudaFoam Pro"};
    String[] user={"KGM Traders","McCoy"};

    private RecyclerView recycler_viewRecentOrders,recycler_viewAddress,recylerViewRoles;
    private ItemsDetailListAdapter recentOrdersListAdapter;
    private AddressListAdapter addressListAdapter;
    private ArrayList<RecentOrderModal> arrSearchList=new ArrayList<>();
    private ArrayList<RecentOrderModal> recentOrderModalArrayList=new ArrayList<>();
    private ArrayList<RecentOrderModal> addressList=new ArrayList<>();
    private ArrayList<UserModal> stringArrayListRoles=new ArrayList<>();
    private WorkFLowUserAdapter workFLowUserAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_detail);

        setHeader();
        LinearLayout menu_item_container=(LinearLayout)findViewById(R.id.menu_item_container);
        menu_item_container.setVisibility(View.GONE);

        TextView toolbarTtile=(TextView)findViewById(R.id.toolbarTtile);
        toolbarTtile.setText(getString(R.string.order_centre));
        TextView btnAccept=(TextView)findViewById(R.id.btnAccept);
        btnAccept.setText(getString(R.string.accept));

        RelativeLayout rlETA=(RelativeLayout)findViewById(R.id.rlETA);
        rlETA.setVisibility(View.VISIBLE);
        View viewETA=(View)findViewById(R.id.viewETA);
        viewETA.setVisibility(View.VISIBLE);
        recycler_viewRecentOrders = (RecyclerView) findViewById(R.id.recycler_viewItems);
        GridLayoutManager mLayoutManager4 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewRecentOrders.setLayoutManager(mLayoutManager4);
        recycler_viewRecentOrders.addItemDecoration(new SpacesItemDecoration(10));
        recentOrdersListAdapter = new ItemsDetailListAdapter(this, recentOrderModalArrayList);
        recycler_viewRecentOrders.setAdapter(recentOrdersListAdapter);

        recycler_viewAddress = (RecyclerView) findViewById(R.id.recycler_viewAddress);
        GridLayoutManager mLayoutManager5 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewAddress.setLayoutManager(mLayoutManager5);
        recycler_viewAddress.addItemDecoration(new SpacesItemDecoration(10));
        addressListAdapter = new AddressListAdapter(this, recentOrderModalArrayList);
        recycler_viewAddress.setAdapter(addressListAdapter);
        recylerViewRoles = findViewById(R.id.recylerViewRoles);
        recylerViewRoles.setVisibility(View.VISIBLE);
        workFLowUserAdapter = new WorkFLowUserAdapter(mContext, stringArrayListRoles);
        recylerViewRoles.addItemDecoration(new SpacesItemDecoration(10));
        recylerViewRoles.setAdapter(workFLowUserAdapter);
        recylerViewRoles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        getRecentOrdersData();
        getAddressData();
        getuserData();
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
        for (int i = 0; i < items.length; i++) {
            RecentOrderModal recentOrderModal = new RecentOrderModal();
            recentOrderModal.setTitle(items[i]);

            recentOrderModalArrayList.add(recentOrderModal);

        }
        recentOrdersListAdapter.notifyDataSetChanged();
    }


    private void getAddressData() {
        for (int i = 0; i < address.length; i++) {
            RecentOrderModal recentOrderModal = new RecentOrderModal();
            recentOrderModal.setTitle(address[i]);

            addressList.add(recentOrderModal);

        }
        addressListAdapter.notifyDataSetChanged();
    }

    private void getuserData() {
        for (int i = 0; i < user.length; i++) {
            UserModal userModal = new UserModal();
            userModal.setUserName(user[i]);
            if (i==0)
            userModal.setUserStatus("Submitted");
            else
                userModal.setUserStatus("Initiated");

            stringArrayListRoles.add(userModal);

        }
        workFLowUserAdapter.notifyDataSetChanged();
    }

}
