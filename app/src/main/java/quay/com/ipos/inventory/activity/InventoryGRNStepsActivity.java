package quay.com.ipos.inventory.activity;

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

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.InventoryGRNStepsAdapter;
import quay.com.ipos.inventory.adapter.InventoryListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailListAdapter;
import quay.com.ipos.inventory.adapter.WorkFLowUserAdapter;
import quay.com.ipos.inventory.modal.InventoryGRNModel;
import quay.com.ipos.inventory.modal.InventoryModel;
import quay.com.ipos.inventory.modal.UserModal;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.realmbean.RealmBusinessPlaces;
import quay.com.ipos.utility.SpacesItemDecoration;

/**
 * Created by niraj.kumar on 6/13/2018.
 */

public class InventoryGRNStepsActivity extends AppCompatActivity implements InitInterface, View.OnClickListener {

    String[] address = {"1/82"};
    String[] items = {"PO180001", "PO180002"};
    String[] user = {"KGM Traders", "McCoy"};

    private RecyclerView recycler_viewRecentOrders, recycleview, recylerViewRoles;
    private ItemsDetailListAdapter recentOrdersListAdapter;
    private InventoryListAdapter inventoryListAdapter;
    private ArrayList<RecentOrderModal> arrSearchList = new ArrayList<>();
    private ArrayList<InventoryModel> inventoryModels = new ArrayList<>();
    private ArrayList<RealmBusinessPlaces> addressList = new ArrayList<>();
    private ArrayList<UserModal> stringArrayListRoles = new ArrayList<>();
    private WorkFLowUserAdapter workFLowUserAdapter;


    private RecyclerView recycleviewCard;
    private TextView textViewAdd, tvGrnNumberCount, tvPoNumber, poQtyCount, grnQtyCount, apQtyCount, balanceQtyCount;
    private Toolbar toolbar;
    private LinearLayoutManager layoutManager;
    private Context mContext;
    private ArrayList<InventoryGRNModel> inventoryGRNModels = new ArrayList<>();
    private InventoryGRNStepsAdapter kycViewAllAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grn_steps_activity);
        mContext = InventoryGRNStepsActivity.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();

        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        GridLayoutManager mLayoutManager5 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleview.setLayoutManager(mLayoutManager5);
        recycleview.addItemDecoration(new SpacesItemDecoration(10));
        inventoryListAdapter = new InventoryListAdapter(this, inventoryModels);
        recycleview.setAdapter(inventoryListAdapter);

        getRecentOrdersData();

        final RelativeLayout rlTab = findViewById(R.id.rlTab);
        final RelativeLayout llgrnn = findViewById(R.id.llgrnn);
        final TextView tvGrn = findViewById(R.id.tvGrn);
        LinearLayout lLayoutGrn = findViewById(R.id.lLayoutGrn);

        final TextView tvPO = findViewById(R.id.tvPO);
        LinearLayout poLayout = findViewById(R.id.poLayout);

//        final RelativeLayout rLayoutMain = findViewById(R.id.rLayoutMain);
        poLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvGrn.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvPO.setBackgroundResource(R.drawable.textview_circle_app_color);
                recycleview.setVisibility(View.VISIBLE);
                recycleviewCard.setVisibility(View.GONE);
                rlTab.setVisibility(View.GONE);
                llgrnn.setVisibility(View.GONE);
//                rLayoutMain.setVisibility(View.GONE);
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
//                rLayoutMain.setVisibility(View.VISIBLE);
            }
        });

    }

    private void getRecentOrdersData() {
        for (int i = 0; i < items.length; i++) {
            InventoryModel inventoryModel = new InventoryModel();
            inventoryModel.setPoNumber(items[i]);

            inventoryModels.add(inventoryModel);

        }
        inventoryListAdapter.notifyDataSetChanged();
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

            Intent i = new Intent(mContext, InventoryGRNDetails.class);
            startActivity(i);

//
//            InventoryGRNModel inventoryGRNModel = new InventoryGRNModel();
//            inventoryGRNModel.grnQty = "1";
//            inventoryGRNModel.apQTY = "2";
//            inventoryGRNModel.value = "26,480";
//            inventoryGRNModels.add(inventoryGRNModel);
//            kycViewAllAdapter.notifyDataSetChanged();
        }
    }
}
