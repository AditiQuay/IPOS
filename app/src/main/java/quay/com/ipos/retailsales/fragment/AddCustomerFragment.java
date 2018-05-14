package quay.com.ipos.retailsales.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.dashboard.fragment.DashboardItemFragment;


/**
 * The type Add customer fragment.
 */
public class AddCustomerFragment extends Fragment {

    private Context mContext;
    private ViewPager mViewPager;
    private DashboardAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.act_customer_list, container, false);
        mContext = getActivity();


        final TextView page1=(TextView)view.findViewById(R.id.tvFirstPage);
        final TextView page2=(TextView)view.findViewById(R.id.tvSecondPage);
        final TextView page3=(TextView)view.findViewById(R.id.tvThirdPage);
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
         adapter= new DashboardAdapter(getChildFragmentManager());
        // Set an Adapter on the ViewPager
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position==0){
                    page1.setTextColor(getResources().getColor(R.color.black));
                    page1.setBackgroundResource(R.drawable.textview_circle_white);
                    page2.setTextColor(getResources().getColor(R.color.white));
                    page2.setBackgroundResource(R.drawable.textview_circle_app_color);
                    page3.setTextColor(getResources().getColor(R.color.white));
                    page3.setBackgroundResource(R.drawable.textview_circle_app_color);
                }else if (position==1){
                    page1.setTextColor(getResources().getColor(R.color.white));
                    page1.setBackgroundResource(R.drawable.textview_circle_app_color);
                    page2.setTextColor(getResources().getColor(R.color.black));
                    page2.setBackgroundResource(R.drawable.textview_circle_white);
                    page3.setTextColor(getResources().getColor(R.color.white));
                    page3.setBackgroundResource(R.drawable.textview_circle_app_color);
                }else {
                    page1.setTextColor(getResources().getColor(R.color.white));
                    page1.setBackgroundResource(R.drawable.textview_circle_app_color);
                    page2.setTextColor(getResources().getColor(R.color.white));
                    page2.setBackgroundResource(R.drawable.textview_circle_app_color);
                    page3.setTextColor(getResources().getColor(R.color.black));
                    page3.setBackgroundResource(R.drawable.textview_circle_white);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    page1.setTextColor(getResources().getColor(R.color.black));
                    page1.setBackgroundResource(R.drawable.textview_circle_white);
                    page2.setTextColor(getResources().getColor(R.color.white));
                    page2.setBackgroundResource(R.drawable.textview_circle_app_color);
                    page3.setTextColor(getResources().getColor(R.color.white));
                    page3.setBackgroundResource(R.drawable.textview_circle_app_color);
                }else if (position==1){
                    page1.setTextColor(getResources().getColor(R.color.white));
                    page1.setBackgroundResource(R.drawable.textview_circle_app_color);
                    page2.setTextColor(getResources().getColor(R.color.black));
                    page2.setBackgroundResource(R.drawable.textview_circle_white);
                    page3.setTextColor(getResources().getColor(R.color.white));
                    page3.setBackgroundResource(R.drawable.textview_circle_app_color);
                }else {
                    page1.setTextColor(getResources().getColor(R.color.white));
                    page1.setBackgroundResource(R.drawable.textview_circle_app_color);
                    page2.setTextColor(getResources().getColor(R.color.white));
                    page2.setBackgroundResource(R.drawable.textview_circle_app_color);
                    page3.setTextColor(getResources().getColor(R.color.black));
                    page3.setBackgroundResource(R.drawable.textview_circle_white);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return view;
    }




    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    /**
     * The type Dashboard adapter.
     */
    public class DashboardAdapter extends FragmentStatePagerAdapter {

        private DashboardAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new DashboardItemFragment().newInstance(position);
                default:
                    return new DashboardItemFragment().newInstance(position);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

    }



}
