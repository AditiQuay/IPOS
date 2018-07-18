package quay.com.ipos.offerdiscount.fragment;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.base.RunTimePermissionActivity;
import quay.com.ipos.offerdiscount.Model.OfferDiscountModel;
import quay.com.ipos.offerdiscount.Model.RuleModel;

/**
 * Created by aditi.bhuranda on 11-07-2018.
 */

public class OfferDiscountFragment extends RunTimePermissionActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private LinearLayout bottom_sheet;
    private Button btnReject,btnAccept;
    public FloatingActionButton fab;
    private Toolbar toolbar;
    private MutableLiveData<OfferDiscountModel> pcModelLiveData = new MutableLiveData<>();
    OfferDiscountModel offerDiscountModel;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_tab_layout);
        initializeComponent();

    }

    private void initializeComponent() {
        viewPager = findViewById(R.id.viewPager);
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.offer_discount));
        // viewPager.setOffscreenPageLimit(0);

        viewPager.addOnPageChangeListener(myOnPageChangeListener);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        bottom_sheet = findViewById(R.id.bottom_sheet);
        fab = findViewById(R.id.fab);

        bottom_sheet.setVisibility(View.VISIBLE);
        tabLayout =  findViewById(R.id.tabs);
        btnReject =  findViewById(R.id.btnReject);
        btnAccept =  findViewById(R.id.btnAccept);
        btnAccept.setText("Apply");
        btnReject.setText("Reset");
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();

        offerDiscountModel = new OfferDiscountModel();
        offerDiscountModel.setsBusinessPlaces("");
        offerDiscountModel.setsCustomerGroup("");
        offerDiscountModel.setsDescription("");
        offerDiscountModel.setsDisplayImage("");
        offerDiscountModel.setsDisplayName("");
        offerDiscountModel.setsEndDate("");
        offerDiscountModel.setsEntity("");
        offerDiscountModel.setsLOB("");
        offerDiscountModel.setsName("");
        pcModelLiveData.setValue(offerDiscountModel);
    }
    @Override
    public void onResume() {
        super.onResume();
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

            if (position == 2 ) {
                btnAccept.setVisibility(View.VISIBLE);
                btnReject.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
            } else {
                btnAccept.setVisibility(View.GONE);
                btnReject.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    public void onPermissionsGranted(int requestCode) {

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
            return 3;
        }
    }
    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(getResources().getString(R.string.od_tab_1));
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getResources().getString(R.string.od_tab_2));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getResources().getString(R.string.od_tab_3));
        tabLayout.getTabAt(2).setCustomView(tabThree);


    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public MutableLiveData<OfferDiscountModel> getPcModelData() {
        return pcModelLiveData;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    private Fragment getFragmentByPosition(int position) {
        switch (position) {
            case 0:
                return OfferDiscountOverviewFragment.newInstance("FirstFragment, Instance 1", "0");
            case 1:
                return new ScopeFragment();
            case 2:
                return new RuleFragment();

            default:
                return OfferDiscountOverviewFragment.newInstance("FirstFragment, Instance 1", "0");
        }
    }
}
