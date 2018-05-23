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

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.CustomerRecentOrdersAdapter;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.enums.CustomerEnum;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.CustomerList;
import quay.com.ipos.utility.CircleImageView;
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
    private String customerId;
    private RecyclerView recyclerviewRecentOrder;
    private CustomerRecentOrdersAdapter customerRecentOrdersAdapter;
    private ArrayList<CustomerList.RecentOrder> recentOrders = new ArrayList<>();
    private DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info_details);
        mContext = CustomerInfoDetailsActivity.this;
        db = new DatabaseHandler(mContext);
        Intent i = getIntent();
        customerId = i.getStringExtra("customerID");


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


        CustomerModel customerModel = db.getCustomer(customerId);

        if (Util.validateString(customerModel.getCustomerName())) {
            textViewUserName.setText(customerModel.getCustomerName());
        }
        if (Util.validateString(customerModel.getCustomerPhone())) {
            textViewMob.setText(customerModel.getCustomerPhone());
        }
        if (Util.validateString(customerModel.getCustomerEmail())) {
            textViewEmail.setText(customerModel.getCustomerEmail());
        }
        if (Util.validateString(customerModel.getCustomerBday())) {
            textViewBirthDay.setText(getResources().getString(R.string.text_birthday) + customerModel.getCustomerBday() + ")");
        }
        if (Util.validateString(customerModel.getCustomerPoints())) {
            textViewPoints.setText(customerModel.getCustomerPoints() + getResources().getString(R.string.text_points));
        }
        textViewBill.setText(getResources().getString(R.string.text_Last_Billing) + customerModel.getLastBillingDate() + " | " + getResources().getString(R.string.Rs) + " " + customerModel.getLastBillingAmount());

        String address = customerModel.getCustomerAddress();
        String state = customerModel.getCustomerState();
        String city = customerModel.getCustomerCity();
        String pin = customerModel.getCustomerPin();
        String finalAddress = address + ", " + state + ", " + city + ", " + pin;
        textViewBillingAddress.setText(finalAddress);
        textViewWarningText.setText(customerModel.getSuggestion());

        try {
            JSONArray jsonArray = new JSONArray(customerModel.getRecentOrders());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                CustomerList.RecentOrder recentOrder = new CustomerList.RecentOrder();
                recentOrder.setFromStoreName(jsonObject3.optString(CustomerEnum.ColoumnFromStoreName.toString().trim()));
                recentOrder.setStoreCity(jsonObject3.optString(CustomerEnum.ColoumnStoreCity.toString().trim()));
                recentOrder.setStoreState(jsonObject3.optString(CustomerEnum.ColoumnStoreState.toString().trim()));
                recentOrder.setBillDate(jsonObject3.optString(CustomerEnum.ColoumnBillDate.toString().trim()));
                recentOrder.setBillPrice(jsonObject3.optString(CustomerEnum.ColoumnBillPrice.toString().trim()));
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
