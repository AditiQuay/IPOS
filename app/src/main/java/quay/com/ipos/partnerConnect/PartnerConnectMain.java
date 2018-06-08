package quay.com.ipos.partnerConnect;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.data.remote.model.PartnerConnectResponse;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by niraj.kumar on 6/3/2018.
 */

public class PartnerConnectMain extends AppCompatActivity implements InitInterface,
        RelationShipFragment.OnFragmentInteractionListener {
    private static final String TAG = PartnerConnectMain.class.getSimpleName();
    private Activity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private MutableLiveData<PCModel> pcModelLiveData = new MutableLiveData<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_partner_connect);

        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();

        getServerData();
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.title_partner_connect));

        viewPager = findViewById(R.id.viewPager);


        viewPager.addOnPageChangeListener(myOnPageChangeListener);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
    }

    @Override
    public void applyInitValues() {

    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(getResources().getString(R.string.pc_tab_1));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_relationship_white, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getResources().getString(R.string.pc_tab_2));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_business_white, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getResources().getString(R.string.pc_tab_3));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_contact_white, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(getResources().getString(R.string.pc_tab_4));
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_bank_white, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFive.setText(getResources().getString(R.string.pc_tab_5));
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_billing_white, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);

        TextView tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSix.setText(getResources().getString(R.string.pc_tab_6));
        tabSix.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_documents_white, 0, 0);
        tabLayout.getTabAt(5).setCustomView(tabSix);
    }

    public int getCurrentViewPagerPos() {
        if (viewPager != null) {
            return viewPager.getCurrentItem();
        }
        return 0;
    }

    ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        //declare key
        Boolean first = true;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (first && positionOffset == 0 && positionOffsetPixels == 0) {
                onPageSelected(0);
                first = false;
            }
        }

        @Override
        public void onPageSelected(int position) {
            //do what need
           /* if (mListener != null) {
                mListener.onPageSelected(position);
            }*/
            if (position == 0) {
                findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
            } else {
                findViewById(R.id.bottom_sheet).setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return getFragmentByPosition(position);

        }

        @Override
        public int getCount() {
            return 6;
        }
    }

    private Fragment getFragmentByPosition(int position) {
        switch (position) {
            case 0:
                return RelationShipFragment.newInstance("FirstFragment, Instance 1", "0");
            case 1:
                return new BusinessFragment();
            case 2:
                return new ContactFragment();
            case 3:
                return new AccountFragment();
            case 4:
                return new BillingAddressFragment();
            case 5:
                return new AccountFragment();
            default:
                return RelationShipFragment.newInstance("FirstFragment, Instance 1", "0");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            getData();
            return true;

        }
        if (id == R.id.action_help) {
            getServerData();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    public void getData() {


        String localData = getLocalData();
        PartnerConnectResponse pcModel = new Gson().fromJson(localData, PartnerConnectResponse.class);
        Log.i("localData", pcModel.toString());
        pcModelLiveData.setValue(pcModel.response);
    }

    private String getLocalData() {
        String json = Util.getAssetJsonResponse(this, "pss_connect");
        return json;

    }

    private String getServerData() {
        Call<PartnerConnectResponse> call = RestService.getApiServiceSimple(IPOSApplication.getContext()).loadPartnerConnectData();
        call.enqueue(new Callback<PartnerConnectResponse>() {
            @Override
            public void onResponse(Call<PartnerConnectResponse> call, Response<PartnerConnectResponse> response) {
                try {
                    Log.i("response", response.body().statusCode + "," + response.body().message);
                    Log.i("JsonObject", response.toString() + response.body());
                    if (response.body() != null) {
                        PartnerConnectResponse response1 = response.body();
                        if (response1 != null) {
                            PCModel pcModel = response1.response;
                            if (pcModel != null) {
                                pcModelLiveData.setValue(pcModel);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PartnerConnectResponse> call, Throwable t) {
                Log.e(TAG, "ERROR OCCURED");
                Log.i("JsonObject", t.toString());
                t.printStackTrace();
            }
        });
        return "";
    }

    public MutableLiveData<PCModel> getPcModelData() {
        return pcModelLiveData;
    }
}
