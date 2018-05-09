package quay.com.ipos.customerInfo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.CustomerInfoAdapter;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerInfoModal;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.ProductCatalogueViewAllAdapter;
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
    private ArrayList<CustomerInfoModal> customerInfoModalArrayList = new ArrayList<>();
    MyListener listener;
    private Context mContext;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
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


        recyclerviewCustomerCard.setHasFixedSize(true);
        recyclerviewCustomerCard.setLayoutManager(new LinearLayoutManager(mContext));
        customerInfoAdapter = new CustomerInfoAdapter(mContext, customerInfoModalArrayList, this);
        recyclerviewCustomerCard.setAdapter(customerInfoAdapter);

        customerInfoModalArrayList.clear();
        getCustomerData();

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

    private void getCustomerData() {
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(Util.getAssetJsonResponse(mContext, "customer.json"));
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray("customer");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CustomerInfoModal customerInfoModal = new CustomerInfoModal();
                customerInfoModal.id = jsonObject.optInt("customerID");
                customerInfoModal.customerName = jsonObject.optString("customer_name");
                customerInfoModal.customerPoints = jsonObject.optString("customer_points");
                customerInfoModal.customerMobileNumber = jsonObject.optString("customer_phone");
                customerInfoModal.customerEmail = jsonObject.optString("customer_email");
                customerInfoModal.customerBirthDay = jsonObject.optString("customer_bday");

                JSONObject jsonObject1 = jsonObject.getJSONObject("last_billing");
                customerInfoModal.customerBillingDate = jsonObject1.optString("last_billing_date");
                customerInfoModal.customerBillingAmount = jsonObject1.optString("last_billing_amount");

                customerInfoModalArrayList.add(customerInfoModal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        CustomerInfoModal customerInfoModal = customerInfoModalArrayList.get(position);

        Intent i = new Intent(CustomerInfoActivity.this, CustomerInfoDetailsActivity.class);
        i.putExtra("customerName", customerInfoModal.customerName);
        i.putExtra("customerMobile", customerInfoModal.customerMobileNumber);
        i.putExtra("customerEmail", customerInfoModal.customerEmail);
        i.putExtra("customerBilling", customerInfoModal.customerBillingDate);
        i.putExtra("customerAmount", customerInfoModal.customerBillingAmount);
        i.putExtra("customerBirthDay", customerInfoModal.customerBirthDay);
        i.putExtra("customerPoints", customerInfoModal.customerPoints);
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

}
