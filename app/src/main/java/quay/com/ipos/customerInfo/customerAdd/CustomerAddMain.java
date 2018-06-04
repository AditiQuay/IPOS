package quay.com.ipos.customerInfo.customerAdd;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.ViewPagerAdapter;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerSpinner;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerSpinnerServerModel;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.InitInterface;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_add_main);
        mContext = CustomerAddMain.this;
        dbHelper = new DatabaseHandler(mContext);
//        getSpinnerList();
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CustomerAddQuickFragment(), "QUICK INFO");
        adapter.addFragment(new CustomerAddFullFragment(), "FULL INFO");
        viewPager.setAdapter(adapter);
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
            JSONArray cityList = jsonObject1.optJSONArray("cityList");
            JSONArray stateList = jsonObject.optJSONArray("stateList");
            JSONArray countryList = jsonObject.optJSONArray("countryList");
            JSONArray designationList = jsonObject.optJSONArray("designationList");
            JSONArray companyArray = jsonObject.optJSONArray("companyList");
            JSONArray relationshipList = jsonObject.optJSONArray("relationshipList");

            long id = dbHelper.insertSpinnerItems(cityList.toString(), stateList.toString(), countryList.toString(), designationList.toString(), companyArray.toString(), relationshipList.toString());
            Log.e(TAG, "Newly added Customer***" + id);
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
                dbHelper.removeAll();
                fetchSpinnerResponse(serverResponse);
            }
     //       fetchSpinnerResponse(serverResponse);
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
