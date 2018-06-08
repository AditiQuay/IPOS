package quay.com.ipos.ddr.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.ddr.adapter.WorkFLowAdapter;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.ddr.adapter.AddressListAdapter;
import quay.com.ipos.ddr.adapter.ItemsDetailListAdapter;
import quay.com.ipos.ddr.adapter.WorkFLowUserAdapter;
import quay.com.ipos.ddr.modal.UserModal;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.utility.SpacesItemDecoration;

/**
 * Created by aditi.bhuranda on 20-04-2018.
 */

public class OrderCentreDetailsActivity extends BaseActivity{
    String[] address = {"1/82"};
    String[] items={"SoudaFoam 1k","SoudaFoam Pro"};
    String[] user={"KGM Traders","McCoy"};
    private TextView toolbarTtile,btnAccept;
    private RelativeLayout rlETA;
    private View viewETA;
    LinearLayout menu_item_container;
    GridLayoutManager mLayoutManager4,mLayoutManager5;
    private RecyclerView recycler_viewRecentOrders,recycler_viewAddress,recylerViewRoles;
    private ItemsDetailListAdapter recentOrdersListAdapter;
    private AddressListAdapter addressListAdapter;
    private ArrayList<RecentOrderModal> arrSearchList=new ArrayList<>();
    private ArrayList<RecentOrderModal> recentOrderModalArrayList=new ArrayList<>();
    private ArrayList<RecentOrderModal> addressList=new ArrayList<>();

    private ArrayList<UserModal> stringArrayListRoles=new ArrayList<>();
    private ArrayList<UserModal> stringArrayListFlow=new ArrayList<>();
    private WorkFLowUserAdapter workFLowUserAdapter;
    private WorkFLowAdapter workFLowAdapter;
    private RecyclerView recylerViewFlow,recycler_view;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_center_detail);

        setHeader();
        menu_item_container=findViewById(R.id.menu_item_container);
        menu_item_container.setVisibility(View.GONE);

        toolbarTtile=findViewById(R.id.toolbarTtile);
        toolbarTtile.setText(getString(R.string.order_centre));
        btnAccept=findViewById(R.id.btnAccept);
        btnAccept.setText(getString(R.string.accept));

        rlETA= findViewById(R.id.rlETA);
        rlETA.setVisibility(View.VISIBLE);
        viewETA=findViewById(R.id.viewETA);
        viewETA.setVisibility(View.VISIBLE);


        recylerViewFlow = (RecyclerView) findViewById(R.id.recylerViewFlow);
        GridLayoutManager mLayoutManager7 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recylerViewFlow.setLayoutManager(mLayoutManager7);

        workFLowAdapter = new WorkFLowAdapter(this, stringArrayListFlow);
        recylerViewFlow.setAdapter(workFLowAdapter);
        recylerViewFlow.setVisibility(View.VISIBLE);
        recycler_viewRecentOrders = (RecyclerView) findViewById(R.id.recycler_viewItems);


        mLayoutManager4 = new GridLayoutManager(this, 1);
        recycler_viewRecentOrders.setLayoutManager(mLayoutManager4);
        recycler_viewRecentOrders.addItemDecoration(new SpacesItemDecoration(10));

        recycler_viewAddress =  findViewById(R.id.recycler_viewAddress);
        mLayoutManager5 = new GridLayoutManager(this, 1);
        recycler_viewAddress.setLayoutManager(mLayoutManager5);
        recycler_viewAddress.addItemDecoration(new SpacesItemDecoration(10));

        addressListAdapter = new AddressListAdapter(this, recentOrderModalArrayList);
        recycler_viewAddress.setAdapter(addressListAdapter);

        recylerViewRoles = findViewById(R.id.recylerViewRoles);
        recylerViewRoles.setVisibility(View.VISIBLE);

        workFLowUserAdapter = new WorkFLowUserAdapter(mContext, stringArrayListRoles);
        recylerViewRoles.addItemDecoration(new SpacesItemDecoration(10));
        recylerViewRoles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recylerViewRoles.setAdapter(workFLowUserAdapter);




        final LinearLayout llFlow=(LinearLayout)findViewById(R.id.llFLow);
        final LinearLayout llDetails=(LinearLayout)findViewById(R.id.llDetails);
        LinearLayout llRetailer=(LinearLayout)findViewById(R.id.llRetailer);
       // LinearLayout llPartner=(LinearLayout)findViewById(R.id.llPartner);
        final LinearLayout menu_item_container=(LinearLayout)findViewById(R.id.menu_item_container);
        final ImageView imgArrow=(ImageView)findViewById(R.id.imgArrow);
        LinearLayout llbottom_buttons=(LinearLayout)findViewById(R.id.llbottom_buttons);
        llbottom_buttons.setVisibility(View.GONE);

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
        getFlow();
//        getRecentOrdersData();
//        getAddressData();
//        getuserData();

    }




    public void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar);
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

    private void getFlow() {
        for (int i = 0; i < user.length; i++) {
            UserModal userModal = new UserModal();
            userModal.setUserName(user[i]);
            if (i==0)
                userModal.setUserStatus("Submitted");
            else
                userModal.setUserStatus("Initiated");

            stringArrayListFlow.add(userModal);

        }
        workFLowAdapter.notifyDataSetChanged();
    }

}
