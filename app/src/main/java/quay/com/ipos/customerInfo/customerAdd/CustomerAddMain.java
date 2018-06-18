package quay.com.ipos.customerInfo.customerAdd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.ViewPagerAdapter;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerSpinnerServerModel;
import quay.com.ipos.enums.CustomerEnum;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.YourFragmentInterface;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.SharedPrefUtil;

/**
 * Created by niraj.kumar on 5/31/2018.
 */

public class CustomerAddMain extends AppCompatActivity implements InitInterface, ServiceTask.ServiceResultListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context mContext;

    private DatabaseHandler dbHelper;
    private String TAG = CustomerAddMain.class.getSimpleName();
    SharedPreferences sharedpreferences, quickPref;
    public static final String mypreference = "Data";
    public static final String quickPreference = "QuickData";

    int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_add_main);
        mContext = CustomerAddMain.this;
        dbHelper = new DatabaseHandler(mContext);
        Intent i = getIntent();
        count = i.getIntExtra("Count", 0);
        getSpinnerList();
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    private void getSpinnerList() {
        String accessToken = SharedPrefUtil.getAccessToken(Constants.ACCESS_TOKEN.trim(), "", mContext);

        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_SPINNER_LIST);
        mTask.setApiCallType(Constants.API_METHOD_GET);
        mTask.setApiToken("Bearer " + accessToken.trim());
        mTask.setGetParameters("");
        mTask.setResultType(CustomerSpinnerServerModel.class);
        mTask.setListener(this);
        mTask.execute();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                sharedpreferences = mContext.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();

                quickPref = mContext.getSharedPreferences(quickPreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = quickPref.edit();
                editor1.clear();
                editor1.apply();
                finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("Add Customer");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(final ViewPager viewPager) {
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CustomerAddQuickFragment(), "QUICK INFO");
        adapter.addFragment(new CustomerAddFullFragment(), "FULL INFO");
        viewPager.setAdapter(adapter);
        if (count != 0) {
            viewPager.setCurrentItem(1);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                YourFragmentInterface fragment = (YourFragmentInterface) adapter.instantiateItem(viewPager, position);
                fragment.fragmentBecameVisible();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(toolbar, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tabLayout, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

    }

    private void fetchSpinnerResponse(String serverResponse) {
        try {
            JSONObject jsonObject = new JSONObject(serverResponse);
            JSONObject jsonObject1 = jsonObject.getJSONObject("spinnerData");
            JSONArray cityList = jsonObject1.optJSONArray(CustomerEnum.ColoumnCityList.toString());
            JSONArray stateList = jsonObject1.optJSONArray(CustomerEnum.ColoumnStateList.toString());
            JSONArray countryList = jsonObject1.optJSONArray(CustomerEnum.ColoumnCountryList.toString());
            JSONArray designationList = jsonObject1.optJSONArray(CustomerEnum.ColoumnDesignationList.toString());
            JSONArray companyArray = jsonObject1.optJSONArray(CustomerEnum.ColoumnCompanyList.toString());
            JSONArray relationshipList = jsonObject1.optJSONArray(CustomerEnum.ColoumnRelationShipList.toString());
            JSONArray customerTypeList = jsonObject1.optJSONArray(CustomerEnum.ColoumnTypeList.toString());


            dbHelper.insertSpinnerItems(cityList.toString(), stateList.toString(), countryList.toString(), designationList.toString(), companyArray.toString(), relationshipList.toString(), customerTypeList.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        if (httpStatusCode == Constants.SUCCESS) {
            if (resultObj != null) {
                dbHelper.removeSpinnerList();
                fetchSpinnerResponse(serverResponse);
            }
        } else if (httpStatusCode == Constants.BAD_REQUEST) {
            Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
        }
    }
}
