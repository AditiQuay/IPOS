package quay.com.ipos.customerInfo;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerAdd.CustomerAddMain;
import quay.com.ipos.customerInfo.customerInfoAdapter.CustomerRecentOrdersAdapter;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.customerInfo.customerInfoModal.RecentOrderList;
import quay.com.ipos.enums.CustomerEnum;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.realmbean.RealmPinnedResults;
import quay.com.ipos.ui.CustomTextView;
import quay.com.ipos.utility.CircleImageView;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 5/1/2018.
 */

public class CustomerInfoDetailsActivity extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private Toolbar toolbarCustomerInfoDetail;
    private TextView textViewUserName, textViewMob, textViewEmail, textViewBill, textViewBirthDay, textViewPoints, textViewBillingAddress, textViewSuggestedBy, textViewWarningText, textViewRecentOrder, textViewStoreCount, textViewStoreAddress, textViewDate, textViewAmount, tvPinCount;
    private CustomTextView textViewUpdateAndProceed;
    private ImageView imvBilling, imvPin;
    private CircleImageView imageViewProfileDummy;
    private LinearLayout lLayoutBottom;
    private Context mContext;
    private String customerId="",mCustomerEmail="";
    private double customerPointsPer=0;
    private int customerPoints =0;
    private RecyclerView recyclerviewRecentOrder;
    private CustomerRecentOrdersAdapter customerRecentOrdersAdapter;
    private ArrayList<RecentOrderList> recentOrders = new ArrayList<>();
    private DatabaseHandler db;
    private ArrayList<RealmPinnedResults.Info> mInfoArrayList = new ArrayList<RealmPinnedResults.Info>();
    private SharedPreferences sharedpreferences;
    public static final String mypreference = "Data";
    private SharedPreferences.Editor editor;
    private RelativeLayout rLayoutContent;
    private String paymentModeClicked;
    private RelativeLayout rInfoLay;
    private Dialog myDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info_details);
        mContext = CustomerInfoDetailsActivity.this;
        db = new DatabaseHandler(mContext);

        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        Intent i = getIntent();
        customerId = i.getStringExtra("customerID");
        paymentModeClicked = i.getStringExtra("paymentModeClicked");

        sharedpreferences = mContext.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
    }

    @Override
    public void findViewById() {
        imageViewProfileDummy = findViewById(R.id.imageViewProfileDummy);
        toolbarCustomerInfoDetail = findViewById(R.id.toolbarCustomerInfoDetail);
        textViewUserName = findViewById(R.id.textViewUserName);
        textViewMob = findViewById(R.id.textViewMob);
        textViewEmail = findViewById(R.id.textViewEmail);
        textViewBill = findViewById(R.id.textViewBill);
        textViewBirthDay = findViewById(R.id.textViewBirthDay);
        textViewPoints = findViewById(R.id.textViewPoints);
        textViewBillingAddress = findViewById(R.id.textViewBillingAddress);
        tvPinCount = findViewById(R.id.tvPinCount);
        imvBilling = findViewById(R.id.imvBilling);
        imvPin = findViewById(R.id.imvPin);
        lLayoutBottom = findViewById(R.id.lLayoutBottom);
        rLayoutContent = findViewById(R.id.rLayoutContent);

        textViewSuggestedBy = findViewById(R.id.textViewSuggestedBy);
        textViewWarningText = findViewById(R.id.textViewWarningText);

        textViewRecentOrder = findViewById(R.id.textViewRecentOrder);
        textViewUpdateAndProceed = findViewById(R.id.textViewUpdateAndProceed);
        recyclerviewRecentOrder = findViewById(R.id.recyclerviewRecentOrder);
        rInfoLay = findViewById(R.id.rInfoLay);
        rInfoLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogBox();
            }
        });

        pinnedUpdate();
        lLayoutBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mCustomerEmail.trim().equalsIgnoreCase("")) {
                    Intent mIntent = new Intent();
                    mIntent.putExtra(Constants.KEY_CUSTOMER, customerId);
                    mIntent.putExtra(Constants.KEY_CUSTOMER_POINTS, customerPoints);
                    mIntent.putExtra(Constants.KEY_CUSTOMER_POINTS_PER, customerPointsPer);
                    mIntent.putExtra(Constants.KEY_CUSTOMER_POINTS_EMAIL, mCustomerEmail);
                    setResult(Constants.ACT_CUSTOMER, mIntent);
                    finish();
                }
                else
                    Util.showToast(getString(R.string.redeem_customer_email_not_authorised), mContext);

            }
        });
        imvBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                setResult(Constants.ACT_PAYMENT_NEW_BILLING, mIntent);
                finish();
            }
        });
        imvPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                setResult(Constants.ACT_PINNED, mIntent);
                finish();
            }
        });
        rLayoutContent.setOnClickListener(this);

    }

    private void showDialogBox() {
        CustomerModel customerModel = db.getCustomer(customerId);
        ImageView ImvClose;
        TextView textViewCustomerPoints,textViewCustomerTotalPoints,textViewTotalRedeemPoints,textViewTotalAdjustedPoints,textViewTotalExpirePoints,textViewTotalReversePoints;


        myDialog.setContentView(R.layout.view_info_dialog);
        ImvClose = myDialog.findViewById(R.id.imvClose);
        textViewCustomerPoints = myDialog.findViewById(R.id.textViewCustomerPoints);
        textViewCustomerTotalPoints = myDialog.findViewById(R.id.textViewCustomerTotalPoints);
        textViewTotalRedeemPoints = myDialog.findViewById(R.id.textViewTotalRedeemPoints);
        textViewTotalAdjustedPoints = myDialog.findViewById(R.id.textViewTotalAdjustedPoints);
        textViewTotalExpirePoints = myDialog.findViewById(R.id.textViewTotalExpirePoints);
        textViewTotalReversePoints = myDialog.findViewById(R.id.textViewTotalReversePoints);

        textViewCustomerPoints.setText(customerModel.getCustomerPoints()+"");
        textViewCustomerTotalPoints.setText(customerModel.getPointsPerValue()+"");
        textViewTotalRedeemPoints.setText(customerModel.getCustomerRedeemPoints()+"");
        textViewTotalAdjustedPoints.setText(customerModel.getCustomerAdjustPoints()+"");
        textViewTotalExpirePoints.setText(customerModel.getCustomerExpirePoints()+"");
        textViewTotalReversePoints.setText(customerModel.getCustomerReversePoints()+"");


        ImvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void pinnedUpdate() {
        if (SharedPrefUtil.getString("mInfoArrayList", "", mContext) != null) {
            String json2 = SharedPrefUtil.getString("mInfoArrayList", "", mContext);
            if (!json2.equalsIgnoreCase(""))
                mInfoArrayList = Util.getCustomGson().fromJson(json2, new TypeToken<ArrayList<RealmPinnedResults.Info>>() {
                }.getType());
            if (mInfoArrayList.size() > 0) {
                tvPinCount.setText("" + mInfoArrayList.size());
                tvPinCount.setVisibility(View.VISIBLE);
            } else {
                tvPinCount.setVisibility(View.GONE);
            }
        } else {
            tvPinCount.setVisibility(View.GONE);
        }
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
        if (!TextUtils.isEmpty(customerModel.getCustomerImage())) {
            Picasso.get().load(customerModel.getCustomerImage()).placeholder(R.drawable.placeholder).into(imageViewProfileDummy);
        } else {
            imageViewProfileDummy.setImageResource(R.drawable.placeholder);

        }
        if (!TextUtils.isEmpty(customerModel.getCustomerName())) {
            textViewUserName.setText(customerModel.getCustomerName());
        } else {
            String firstName = customerModel.getCustomerFirstName();
            String lastName = customerModel.getCustomerLastName();
            String finalName = firstName + " " + lastName;
            textViewUserName.setText(finalName);

        }
        if (Util.validateString(customerModel.getCustomerName())) {
            textViewUserName.setText(customerModel.getCustomerName());
        }
        if (Util.validateString(customerModel.getCustomerPhone())) {
            textViewMob.setText(customerModel.getCustomerPhone());
        }
        if (Util.validateString(customerModel.getCustomerEmail())) {
            textViewEmail.setText(customerModel.getCustomerEmail());
            mCustomerEmail = customerModel.getCustomerEmail();
        }
        if (Util.validateString(customerModel.getCustomerBday())) {
            textViewBirthDay.setText(getResources().getString(R.string.text_birthday) + customerModel.getCustomerBday() + ")");
        }
        if (Util.validateString(customerModel.getCustomerPoints())) {
            textViewPoints.setText(customerModel.getCustomerPoints() + getResources().getString(R.string.text_points));
            customerPoints = Integer.parseInt(customerModel.getCustomerPoints());
        }
        if (TextUtils.isEmpty(customerModel.getLastBillingDate())) {
            textViewBill.setText(mContext.getResources().getString(R.string.text_Last_Billing) + " " + " | " + mContext.getResources().getString(R.string.Rs) + " " + customerModel.getLastBillingAmount());
        } else {
            String date1 = Util.getFormattedDates(customerModel.getLastBillingDate(), Constants.format12, Constants.format13);
            textViewBill.setText(mContext.getResources().getString(R.string.text_Last_Billing) + date1 + " | " + mContext.getResources().getString(R.string.Rs) + " " + customerModel.getLastBillingAmount());
        }

        String address = customerModel.getCustomerAddress();
        String state = customerModel.getCustomerState();
        String city = customerModel.getCustomerCity();
        String pin = customerModel.getCustomerPin();
        customerPointsPer = customerModel.getCustomerPointsPerValue();
        String finalAddress = address + ", " + state + ", " + city + ", " + pin;
        textViewBillingAddress.setText(finalAddress);
        textViewWarningText.setText(customerModel.getSuggestion());

        try {
            JSONArray jsonArray = new JSONArray(customerModel.getRecentOrders());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject3 = jsonArray.getJSONObject(i);
                RecentOrderList recentOrder = new RecentOrderList();
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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rLayoutContent:
                CustomerModel customerModel = db.getCustomer(customerId);
                editor.putString("firstName", customerModel.getCustomerFirstName());
                editor.putString("lastName", customerModel.getCustomerLastName());
                editor.putString("MobileNumber", customerModel.getCustomerPhone());
                editor.putString("email", customerModel.getCustomerEmail());
                editor.putString("bDay", customerModel.getCustomerBday());
                editor.apply();

                Intent i = new Intent(mContext, CustomerAddMain.class);
                i.putExtra("Count", 1);
                i.putExtra("isDetailClick",1);
                i.putExtra("paymentModeClicked", paymentModeClicked);
                startActivity(i);
                break;
        }
    }
}
