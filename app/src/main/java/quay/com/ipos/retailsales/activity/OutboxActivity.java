package quay.com.ipos.retailsales.activity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
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

    private ListView listViewNames;
    DatabaseHandler db;

    //List to store all the names
    private ArrayList<BillingSync> names;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbox_list);

        listViewNames = findViewById(R.id.listViewNames);
        NetworkStateChecker networkStateChecker = new NetworkStateChecker();
        networkStateChecker.setListener(this);
        //initializing views and objects
        db = new DatabaseHandler(this);
        update();

    }

    void update(){
        names = new ArrayList<>();
        names = db.getUnSyncedRetailOrders();
        if(names.size()>0) {
            nameAdapter = new NameAdapter(this, names);
            listViewNames.setAdapter(nameAdapter);
        }else {
            Util.showToast("No Outbox list available", IPOSApplication.getAppInstance());
            finish();
        }
    }

    @Override
    public void updateList() {
        update();
    }
}
