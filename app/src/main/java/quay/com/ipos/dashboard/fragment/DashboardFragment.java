package quay.com.ipos.dashboard.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import quay.com.ipos.R;


public class DashboardFragment extends Fragment {

    private Context mContext;
    private ArrayList<String> dashboardList = new ArrayList<>();
/*    private ViewPager mViewPager;
    private DashboardAdapter adapter;*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mContext = getActivity();

      /*  mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
         adapter= new DashboardAdapter(getChildFragmentManager());
        // Set an Adapter on the ViewPager
        mViewPager.setAdapter(adapter);
        mViewPager.setPadding(50, 0, 50, 0);
        mViewPager.setClipToPadding(false);
        mViewPager.setPageMargin(0);
        mViewPager.setOffscreenPageLimit(3);*/

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





/*    public class DashboardAdapter extends FragmentStatePagerAdapter {

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
            return dashboardList.size();
        }

    }*/



}
