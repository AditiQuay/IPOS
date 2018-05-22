package quay.com.ipos.customerInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.CustomerRecentOrdersAdapter;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.CustomerList;
import quay.com.ipos.realmbean.RealmCustomerInfoModal;
import quay.com.ipos.utility.CircleImageView;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 5/1/2018.
 */

public class CustomerInfoDetailsActivity extends AppCompatActivity implements InitInterface {
    private Toolbar toolbarCustomerInfoDetail;
    private TextView textViewUserName, textViewMob, textViewEmail, textViewBill, textViewBirthDay, textViewPoints, textViewBillingAddress, textViewSuggestedBy, textViewWarningText, textViewRecentOrder, textViewStoreCount, textViewStoreAddress, textViewDate, textViewAmount, textViewUpdateAndProceed;
    private CircleImageView imageViewProfileDummy;
    private Context mContext;
    private int customerId;
    private RecyclerView recyclerviewRecentOrder;
    private CustomerRecentOrdersAdapter customerRecentOrdersAdapter;
    private ArrayList<CustomerList.RecentOrder> recentOrders = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info_details);
        mContext = CustomerInfoDetailsActivity.this;
        Intent i = getIntent();
        customerId = i.getIntExtra("customerID", 0);


        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
    }

    @Override
    public void findViewById() {
        toolbarCustomerInfoDetail = findViewById(R.id.toolbarCustomerInfoDetail);
        textViewUserName = findViewById(R.id.textViewUserName);
        textViewMob = findViewById(R.id.textViewMob);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewBill = findViewById(R.id.textViewBill);
        textViewBirthDay = findViewById(R.id.textViewBirthDay);
        textViewPoints = findViewById(R.id.textViewPoints);
        textViewBillingAddress = findViewById(R.id.textViewBillingAddress);

        textViewSuggestedBy = findViewById(R.id.textViewSuggestedBy);
        textViewWarningText = findViewById(R.id.textViewWarningText);

        textViewRecentOrder = findViewById(R.id.textViewRecentOrder);
        textViewUpdateAndProceed = findViewById(R.id.textViewUpdateAndProceed);
        recyclerviewRecentOrder = findViewById(R.id.recyclerviewRecentOrder);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbarCustomerInfoDetail);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarCustomerInfoDetail.setTitle(getResources().getString(R.string.toolbar_title_customer_screen_details));
        toolbarCustomerInfoDetail.setTitleTextColor(getResources().getColor(R.color.white));

        Realm realm = Realm.getDefaultInstance();
        RealmCustomerInfoModal realmCustomerInfoModals = realm.where(RealmCustomerInfoModal.class).equalTo("customerID", customerId).findFirst();


        if (Util.validateString(realmCustomerInfoModals.getCustomer_name())) {
            textViewUserName.setText(realmCustomerInfoModals.getCustomer_name());
        }
        if (Util.validateString(realmCustomerInfoModals.getCustomer_phone())) {
            textViewMob.setText(realmCustomerInfoModals.getCustomer_phone());
        }
        if (Util.validateString(realmCustomerInfoModals.getCustomer_email())) {
            textViewEmail.setText(realmCustomerInfoModals.getCustomer_email());
        }
        if (Util.validateString(realmCustomerInfoModals.getCustomer_bday())) {
            textViewBirthDay.setText(getResources().getString(R.string.text_birthday) + realmCustomerInfoModals.getCustomer_bday() + ")");
        }
        if (Util.validateString(realmCustomerInfoModals.getCustomer_points())) {
            textViewPoints.setText(realmCustomerInfoModals.getCustomer_points() + getResources().getString(R.string.text_points));
        }
        try {
            //Setting last billing details
            JSONObject jsonObject = new JSONObject(realmCustomerInfoModals.getLast_billing());
            String last_billing_date = jsonObject.getString(Constants.KEY_LAST_BILLING_DATE.trim());
            String last_billing_amount = jsonObject.getString(Constants.KEY_LAST_BILLING_AMOUNT.trim());
            textViewBill.setText(getResources().getString(R.string.text_Last_Billing) + last_billing_date + " | " + getResources().getString(R.string.Rs) + " " + last_billing_amount);


            //Getting and setting address of customer
            JSONObject jsonObject2 = new JSONObject(realmCustomerInfoModals.getCustomer_address());
            String address = jsonObject2.optString(Constants.KEY_ADDRESS.trim());
            String state = jsonObject2.optString(Constants.KEY_STATE.trim());
            String city = jsonObject2.optString(Constants.KEY_CITY.trim());
            String pin = jsonObject2.optString(Constants.KEY_PIN.trim());
            String finalAddress = address + ", " + state + ", " + city + ", " + pin;
            textViewBillingAddress.setText(finalAddress);

            //Setting suggestion.
            JSONObject jsonObject1 = jsonObject.getJSONObject(Constants.KEY_SUGGESTION.trim());
            String suggestion = jsonObject1.getString(Constants.KEY_SUGGESTION.trim());
            textViewWarningText.setText(suggestion);

            JSONArray jsonArray = new JSONArray(realmCustomerInfoModals.getRecent_orders());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                CustomerList.RecentOrder recentOrder = new CustomerList.RecentOrder();
                recentOrder.setFromStoreName(jsonObject3.optString(Constants.KEY_FROM_STORE_NAME.trim()));
                recentOrder.setStoreCity(jsonObject3.optString(Constants.KEY_STORE_CITY.trim()));
                recentOrder.setStoreState(jsonObject3.optString(Constants.KEY_STORE_STATE.trim()));
                recentOrder.setBillDate(jsonObject3.optString(Constants.KEY_BILL_DATE.trim()));
                recentOrder.setBillPrice(jsonObject3.optString(Constants.KEY_BILL_PRICE.trim()));
                recentOrders.add(recentOrder);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerviewRecentOrder.setHasFixedSize(true);
        recyclerviewRecentOrder.setLayoutManager(new LinearLayoutManager(mContext));
        customerRecentOrdersAdapter = new CustomerRecentOrdersAdapter(mContext, recentOrders);
        recyclerviewRecentOrder.setAdapter(customerRecentOrdersAdapter);
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

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewUserName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewMob, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewEmail, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewBill, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewBirthDay, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewPoints, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewBillingAddress, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewWarningText, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewStoreCount, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewStoreAddress, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewDate, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewAmount, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewUpdateAndProceed, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(toolbarCustomerInfoDetail, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


        FontUtil.applyTypeface(textViewSuggestedBy, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewRecentOrder, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }
}
