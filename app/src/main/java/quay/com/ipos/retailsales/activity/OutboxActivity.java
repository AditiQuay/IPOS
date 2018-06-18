package quay.com.ipos.retailsales.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.modal.BillingSync;
import quay.com.ipos.modal.Name;
import quay.com.ipos.retailsales.adapter.NameAdapter;
import quay.com.ipos.ui.NetworkStateChecker;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 14-06-2018.
 */

public class OutboxActivity extends BaseActivity implements NetworkStateChecker.NetworkStateCheckerListener{
    NameAdapter nameAdapter;
    TextView tvNoItemAvailable;
    private ListView listViewNames;
    DatabaseHandler db;
    private Toolbar toolbar;
    //List to store all the names
    private ArrayList<BillingSync> names;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbox_list);
        toolbar = findViewById(R.id.toolbar);
        tvNoItemAvailable = findViewById(R.id.tvNoItemAvailable);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("Retail Outbox");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));


        listViewNames = findViewById(R.id.listViewNames);
        NetworkStateChecker networkStateChecker = new NetworkStateChecker();
        networkStateChecker.setListener(this);
        //initializing views and objects
        db = new DatabaseHandler(this);
        update();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Util.hideSoftKeyboard(OutboxActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void update(){
        names = new ArrayList<>();
        names = db.getUnSyncedRetailOrders();
        nameAdapter = new NameAdapter(this, names);
        listViewNames.setAdapter(nameAdapter);
        if(names.size()>0) {
            nameAdapter.notifyDataSetChanged();
            tvNoItemAvailable.setVisibility(View.GONE);
            listViewNames.setVisibility(View.VISIBLE);
        }else {
            db.deleteTable(DatabaseHandler.TABLE_RETAIL_BILLING);
            nameAdapter.notifyDataSetChanged();
            Util.showToast("No Outbox list available", IPOSApplication.getAppInstance());
//            finish();
            tvNoItemAvailable.setVisibility(View.VISIBLE);
            listViewNames.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateList() {
        update();
    }
}
