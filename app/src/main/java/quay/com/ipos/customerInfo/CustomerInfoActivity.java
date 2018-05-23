package quay.com.ipos.customerInfo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.CustomerInfoAdapter;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/30/2018.
 */

public class CustomerInfoActivity extends AppCompatActivity implements InitInterface, MyListener {
    private static final String TAG = CustomerInfoActivity.class.getSimpleName();
    private Toolbar toolbarCustomerInfo;
    private SearchView searchViewCatalogue;
    private RecyclerView recyclerviewCustomerCard;
    private CustomerInfoAdapter customerInfoAdapter;
    MyListener listener;
    private Context mContext;


    private ArrayList<CustomerModel> customerModelList = new ArrayList<>();
    private ArrayList<CustomerModel> arrSearlist = new ArrayList<>();

    private DatabaseHandler dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info_activity);
        mContext = CustomerInfoActivity.this;
        listener = CustomerInfoActivity.this;
        dbHelper = new DatabaseHandler(this);
        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
    }

    @Override
    public void findViewById() {
        toolbarCustomerInfo = findViewById(R.id.toolbarCustomerInfo);
        searchViewCatalogue = findViewById(R.id.searchViewCatalogue);
        recyclerviewCustomerCard = findViewById(R.id.recyclerviewCustomerCard);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbarCustomerInfo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarCustomerInfo.setTitle(getResources().getString(R.string.toolbar_title_customer_screen));
        toolbarCustomerInfo.setTitleTextColor(getResources().getColor(R.color.white));


//        customerModelList.addAll(dbHelper.getAllOfflineCustomer());
        customerModelList.addAll(dbHelper.getAllNotes());
        arrSearlist.addAll(customerModelList);

        recyclerviewCustomerCard.setHasFixedSize(true);
        recyclerviewCustomerCard.setLayoutManager(new LinearLayoutManager(mContext));
        customerInfoAdapter = new CustomerInfoAdapter(mContext, customerModelList, this);
        recyclerviewCustomerCard.setAdapter(customerInfoAdapter);

        customerModelList.addAll(dbHelper.getAllOfflineCustomer());
        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete) searchViewCatalogue.findViewById(android.support.v7.appcompat.R.id.search_src_text);


        searchAutoComplete.setTextColor(getResources().getColor(R.color.colorPrimary));
        SearchManager searchManager = (SearchManager) mContext.getSystemService(Context.SEARCH_SERVICE);
        assert searchManager != null;
        searchViewCatalogue.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        final EditText et = (EditText) searchViewCatalogue.findViewById(R.id.search_src_text);
        ImageView searchImage = searchViewCatalogue.findViewById(R.id.search_button);

        final ImageView searchClose = (ImageView) searchViewCatalogue.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
                searchViewCatalogue.setQuery("", false);

            }
        });
        searchViewCatalogue.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query, customerModelList);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText, customerModelList);
                return false;
            }
        });

    }


    /**
     * Filter.
     *
     * @param charText the char text
     */
    private void filter(String charText, List<CustomerModel> responseList) {
        if (arrSearlist != null && responseList != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            arrSearlist.clear();
            if (charText.length() == 0) {
                arrSearlist.addAll(responseList);
            } else {
                for (CustomerModel wp : responseList) {
                    if (wp.getCustomerName() != null) {

                        if (wp.getCustomerName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getCustomerPhone().toLowerCase(Locale.getDefault()).contains(charText)) {
                            arrSearlist.add(wp);
                        }
                    }
                }
            }
        }

        recyclerviewCustomerCard.setLayoutManager(new LinearLayoutManager(mContext));
        customerInfoAdapter = new CustomerInfoAdapter(mContext, arrSearlist, this);
        recyclerviewCustomerCard.setAdapter(customerInfoAdapter);


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
        CustomerModel customerInfoModal = customerModelList.get(position);
        Intent i = new Intent(CustomerInfoActivity.this, CustomerInfoDetailsActivity.class);
        i.putExtra("customerID", customerInfoModal.getCustomerID());
        startActivity(i);

    }

    @Override
    public void onRowClicked(int position, int value) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Util.hideSoftKeyboard(CustomerInfoActivity.this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Measure used memory and give garbage collector time to free up some
     * space.
     *
     * @param callback Callback operations to be done when memory is free.
     */
    public static void waitForGarbageCollector(final Runnable callback) {

        Runtime runtime;
        long maxMemory;
        long usedMemory;
        double availableMemoryPercentage = 1.0;
        final double MIN_AVAILABLE_MEMORY_PERCENTAGE = 0.1;
        final int DELAY_TIME = 5 * 1000;

        runtime =
                Runtime.getRuntime();

        maxMemory =
                runtime.maxMemory();

        usedMemory =
                runtime.totalMemory() -
                        runtime.freeMemory();

        availableMemoryPercentage =
                1 -
                        (double) usedMemory /
                                maxMemory;

        if (availableMemoryPercentage < MIN_AVAILABLE_MEMORY_PERCENTAGE) {

            try {
                Thread.sleep(DELAY_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            waitForGarbageCollector(
                    callback);
        } else {

            // Memory resources are availavle, go to next operation:

            callback.run();
        }
    }

}
