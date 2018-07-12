package quay.com.ipos.offerdiscount.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.retailsales.fragment.OfferDiscountOverviewFragment;

/**
 * Created by aditi.bhuranda on 11-07-2018.
 */

public class OfferDiscountFragment extends BaseFragment {


    private View rootView;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private LinearLayout bottom_sheet;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.frag_tab_layout, container, false);

        initializeComponent(rootView);
        return rootView;
    }

    private void initializeComponent(View rootView) {
        viewPager = rootView.findViewById(R.id.viewPager);


        viewPager.addOnPageChangeListener(myOnPageChangeListener);
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        bottom_sheet = rootView.findViewById(R.id.bottom_sheet);
        bottom_sheet.setVisibility(View.VISIBLE);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
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
            //  if (position == 0 || position == 1) {
            //    findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
            //   } else {
//            if (isApprover)
//                rootView.findViewById(R.id.bottom_sheet).setVisibility(View.VISIBLE);
//            else
//                rootView.findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
            //  }

           /* if (position == 5 || position == 1) {
                fab.setVisibility(View.GONE);
            } else {
                fab.setVisibility(View.VISIBLE);
            }*/
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };
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

        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setText(getResources().getString(R.string.od_tab_1));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_relationship_white, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getResources().getString(R.string.od_tab_2));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_business_white, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree.setText(getResources().getString(R.string.od_tab_3));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_contact_white, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);


    }

    private Fragment getFragmentByPosition(int position) {
        switch (position) {
            case 0:
                return OfferDiscountOverviewFragment.newInstance("FirstFragment, Instance 1", "0");
            case 1:
                return new OfferDiscountOverviewFragment();
            case 2:
                return new OfferDiscountOverviewFragment();

            default:
                return OfferDiscountOverviewFragment.newInstance("FirstFragment, Instance 1", "0");
        }
    }
}
