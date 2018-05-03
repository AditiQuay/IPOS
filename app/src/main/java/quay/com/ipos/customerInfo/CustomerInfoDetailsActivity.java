package quay.com.ipos.customerInfo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.utility.CircleImageView;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 5/1/2018.
 */

public class CustomerInfoDetailsActivity extends AppCompatActivity implements InitInterface {
    private Toolbar toolbarCustomerInfoDetail;
    private TextView textViewUserName, textViewMob, textViewEmail, textViewBill, textViewBirthDay, textViewPoints, textViewBillingAddress, textViewSuggestedBy, textViewWarningText, textViewRecentOrder, textViewStoreCount, textViewStoreAddress, textViewDate, textViewAmount, textViewUpdateAndProceed;
    private CircleImageView imageViewProfileDummy;
    private Context mContext;
    private String customerName, customerMobile, customerEmail, customerBillingDate, customerAmount, customerBirthday, customerPoints;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info_details);
        mContext = CustomerInfoDetailsActivity.this;
        Intent i = getIntent();
        customerName = i.getStringExtra("customerName");
        customerMobile = i.getStringExtra("customerMobile");
        customerEmail = i.getStringExtra("customerEmail");
        customerBillingDate = i.getStringExtra("customerBilling");
        customerAmount = i.getStringExtra("customerAmount");
        customerBirthday = i.getStringExtra("customerBirthDay");
        customerPoints = i.getStringExtra("customerPoints");

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
        textViewStoreCount = findViewById(R.id.textViewStoreCount);
        textViewStoreAddress = findViewById(R.id.textViewStoreAddress);
        textViewDate = findViewById(R.id.textViewDate);
        textViewAmount = findViewById(R.id.textViewAmount);
        textViewUpdateAndProceed = findViewById(R.id.textViewUpdateAndProceed);

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

        textViewUserName.setText(customerName);
        textViewMob.setText(customerMobile);
        textViewEmail.setText(customerEmail);
        textViewBill.setText("Last Billing " + customerBillingDate + " | " + getResources().getString(R.string.Rs)+" "+customerAmount);
        textViewBirthDay.setText("Wish Birthday (" + customerBirthday + ")");
        textViewPoints.setText(customerPoints + " Pts.");

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
