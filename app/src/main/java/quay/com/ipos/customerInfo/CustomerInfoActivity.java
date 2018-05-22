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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.CustomerInfoAdapter;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmCustomerInfoModal;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/30/2018.
 */

public class CustomerInfoActivity extends AppCompatActivity implements InitInterface, MyListener {
    private Toolbar toolbarCustomerInfo;
    private SearchView searchViewCatalogue;
    private RecyclerView recyclerviewCustomerCard;
    private CustomerInfoAdapter customerInfoAdapter;
    private ArrayList<RealmCustomerInfoModal> customerInfoModalArrayList = new ArrayList<>();
    MyListener listener;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info_activity);
        mContext = CustomerInfoActivity.this;
        listener = CustomerInfoActivity.this;
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


//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
////               getCustomerData();
//
//
//
//
//            }
//        });
        fetchCustomerInfo();

        recyclerviewCustomerCard.setHasFixedSize(true);
        recyclerviewCustomerCard.setLayoutManager(new LinearLayoutManager(mContext));
        customerInfoAdapter = new CustomerInfoAdapter(mContext, customerInfoModalArrayList, this);
        recyclerviewCustomerCard.setAdapter(customerInfoAdapter);

        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmCustomerInfoModal> realmCustomerInfoModals = realm.where(RealmCustomerInfoModal.class).findAll();
        Log.e("realmCustomerInfoModals", realmCustomerInfoModals.size() + "");
        customerInfoModalArrayList.addAll(realmCustomerInfoModals);
        customerInfoAdapter.notifyDataSetChanged();

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
                customerInfoAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customerInfoAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void fetchCustomerInfo() {
        try {
            JSONObject jsonObject = new JSONObject(Util.getAssetJsonResponse(mContext, "customer_info.json"));
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.KEY_CUSTOMER.trim());
            new RealmController().saveCustomers(jsonArray.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//    private void getCustomerData() {
//
//        try {
//            // Creating JSONObject from String
//            JSONObject jsonObjMain = new JSONObject(Util.getAssetJsonResponse(mContext, "customer.json"));
//            // Creating JSONArray from JSONObject
//            JSONArray jsonArray = jsonObjMain.getJSONArray("customer");
//
//            JSONArray jsonArray1 = new JSONArray();
//            for (int i = 0; i < 100000; i++) {
//                JSONObject jsonObject = new JSONObject();
//                //        CustomerInfoModal customerInfoModal = new CustomerInfoModal();
//                jsonObject.put("customerID", i);
//                jsonObject.put("customer_name", "Niraj");
//                jsonObject.put("customer_points", "23");
//                jsonObject.put("customer_phone", "9876554345");
//                jsonObject.put("customer_email", "rahul.sharma@xyz.com");
//                jsonObject.put("customer_bday", "11 jun");
//                jsonObject.put("last_billing", "12 jun");
//                jsonObject.put("recent_orders", "23");
//                jsonObject.put("i" + 0 + "", i);
//                jsonObject.put("i" + 1 + "", i);
//                jsonObject.put("i" + 2 + "", i);
//                jsonObject.put("i" + 3 + "", i);
//                jsonObject.put("i" + 4 + "", i);
//                jsonObject.put("i" + 5 + "", i);
//                jsonObject.put("i" + 6 + "", i);
//                jsonObject.put("i" + 7 + "", i);
//                jsonObject.put("i" + 8 + "", i);
//                jsonObject.put("i" + 9 + "", i);
//                jsonObject.put("i" + 10 + "", i);
//                jsonObject.put("i" + 11 + "", i);
//                jsonObject.put("i" + 12 + "", i);
//                jsonObject.put("i" + 13 + "", i);
//                jsonObject.put("i" + 4 + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//                jsonObject.put("i" + i + "", i);
//
//
//
//
//
//
//
//
//
//
//       /*         customerInfoModal.id = jsonObject.optInt("customerID");
//                customerInfoModal.customerName = jsonObject.optString("customer_name");
//                customerInfoModal.customerPoints = jsonObject.optString("customer_points");
//                customerInfoModal.customerMobileNumber = jsonObject.optString("customer_phone");
//                customerInfoModal.customerEmail = jsonObject.optString("customer_email");
//                customerInfoModal.customerBirthDay = jsonObject.optString("customer_bday");
//
//                JSONObject jsonObject1 = jsonObject.getJSONObject("last_billing");
//                customerInfoModal.customerBillingDate = jsonObject1.optString("last_billing_date");
//                customerInfoModal.customerBillingAmount = jsonObject1.optString("last_billing_amount");
//
//                customerInfoModalArrayList.add(customerInfoModal);*/
//                jsonArray1.put(jsonObject);
//            }
//
//            new RealmController().saveCustomers(jsonArray1.toString());
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

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
        RealmCustomerInfoModal customerInfoModal = customerInfoModalArrayList.get(position);

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
