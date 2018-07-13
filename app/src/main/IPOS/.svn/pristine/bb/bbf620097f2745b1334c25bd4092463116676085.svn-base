package quay.com.ipos.dashboard.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import quay.com.ipos.R;

import quay.com.ipos.dashboard.adapter.UpcomingShipmentListAdapter;
import quay.com.ipos.dashboard.modal.LowInventoryModal;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.SpacesItemDecoration;



public class UpcomingShipmentActivity extends AppCompatActivity implements InitInterface, MyListener {
    private Toolbar toolbarCustomerInfo;


    private UpcomingShipmentListAdapter inventoryListAdapter;

    MyListener listener;
    private ArrayList<LowInventoryModal> inventoryList = new ArrayList<>();
    private Context mContext;
    private RecyclerView recycler_viewInventory;
    String[] inventory = {"Order Reference # 1", "Order Reference # 2", "Order Reference # 3","Order Reference # 4", "Order Reference # 5","Order Reference # 6", "Order Reference # 7","Order Reference # 8", "Order Reference # 9"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upcoming_shipment_activity);
        mContext = UpcomingShipmentActivity.this;
        listener = UpcomingShipmentActivity.this;
        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
    }

    @Override
    public void findViewById() {
        toolbarCustomerInfo = findViewById(R.id.toolbarCustomerInfo);


    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbarCustomerInfo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarCustomerInfo.setTitle(getResources().getString(R.string.upcoming_shipment));
        toolbarCustomerInfo.setTitleTextColor(getResources().getColor(R.color.white));

        recycler_viewInventory = (RecyclerView) findViewById(R.id.recyclerviewCustomerCard);
        GridLayoutManager mLayoutManager4 = new GridLayoutManager(UpcomingShipmentActivity.this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewInventory.setLayoutManager(mLayoutManager4);
        recycler_viewInventory.addItemDecoration(new SpacesItemDecoration(10));
        inventoryListAdapter = new UpcomingShipmentListAdapter(UpcomingShipmentActivity.this, inventoryList);
        recycler_viewInventory.setAdapter(inventoryListAdapter);

    //    customerInfoModalArrayList.clear();
      ////  getCustomerData();


        getInventoryData();

    }
    private void getInventoryData() {
        for (int i = 0; i < inventory.length; i++) {
            LowInventoryModal lowInventoryModal = new LowInventoryModal();
            lowInventoryModal.setTitle(inventory[i]);

            inventoryList.add(lowInventoryModal);

        }
        inventoryListAdapter.notifyDataSetChanged();
    }



    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(toolbarCustomerInfo, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onRowClicked(int position) {
/*        CustomerInfoModal customerInfoModal = customerInfoModalArrayList.get(position);

        Intent i = new Intent(UpcomingShipmentActivity.this, CustomerInfoDetailsActivity.class);
        i.putExtra("customerName", customerInfoModal.customerName);
        i.putExtra("customerMobile", customerInfoModal.customerMobileNumber);
        i.putExtra("customerEmail", customerInfoModal.customerEmail);
        i.putExtra("customerBilling", customerInfoModal.customerBillingDate);
        i.putExtra("customerAmount", customerInfoModal.customerBillingAmount);
        i.putExtra("customerBirthDay", customerInfoModal.customerBirthDay);
        i.putExtra("customerPoints", customerInfoModal.customerPoints);
        startActivity(i);*/

    }

    @Override
    public void onRowClicked(int position, int value) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
