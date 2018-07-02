package quay.com.ipos.compliance.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import quay.com.ipos.R;
import quay.com.ipos.compliance.constants.AnnotationComplianceType;
import quay.com.ipos.compliance.constants.AnnotationStoreType;


public class ComplianceFragmentMain extends Fragment {
    private static final String TAG = ComplianceFragmentMain.class.getSimpleName();

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private @AnnotationStoreType.StoreType
     int mStoreType;
    private ViewPager viewPager;
    private  ComplianceTypeListener mListener;
    private int parent_curr_pos;



    public ComplianceFragmentMain() {
        // Required empty public constructor
    }


    public static ComplianceFragmentMain newInstance(String param1, @AnnotationStoreType.StoreType int storeType, int parent_curr_pos) {
        ComplianceFragmentMain fragment = new ComplianceFragmentMain();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, storeType);
        args.putInt(ARG_PARAM3, parent_curr_pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mStoreType = getArguments().getInt(ARG_PARAM2);
            parent_curr_pos = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.c_fragment_compliance_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        viewPager = view.findViewById(R.id.viewPager);
        viewPager.addOnPageChangeListener(myOnPageChangeListener);
        viewPager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));

        Log.e(TAG, "parent_curr_pos: "+parent_curr_pos+"");

        viewPager.postDelayed(new Runnable() {

            @Override
            public void run() {
                viewPager.setCurrentItem(parent_curr_pos);
            }
        }, 100);

    }

    public int getCurrentViewPagerPos(){
        if (viewPager != null) {
            return  viewPager.getCurrentItem();
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
            if (mListener != null) {
                mListener.onPageSelected(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComplianceTypeListener) {
            mListener = (ComplianceTypeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStoreSelectionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void updateFilter(@AnnotationComplianceType.ComplianceType int complianceType) {
        //complianceType name is 0,1,2
        viewPager.setCurrentItem(complianceType);
    }


    public interface ComplianceTypeListener {
        // TODO: Update argument type and name
        void onPageSelected(@AnnotationComplianceType.ComplianceType int complianceType);

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            if (mStoreType == AnnotationStoreType.ALL) {

               return AllStorewiseComplianceFragment.newInstance("FirstFragment, Instance 1",position);
            }
            if (mStoreType == AnnotationStoreType.SINGLE) {
               /* switch (position) {

                    case 0:
                        return ComplianceFragmentSingleStoreDetail_ABS.newInstance(""+mParam1, "0");
                    case 1:
                        return ComplianceFragmentSingleStoreDetail_ABS.newInstance("SecondFragment, Instance 1", "1");
                    case 2:
                        return ComplianceFragmentSingleStoreDetail_ABS.newInstance("ThirdFragment, Instance 1", "2");

                    default:
                        return ComplianceFragmentA.newInstance("ThirdFragment, Default", "0");
                }*/
              return ComplianceFragmentSingleStoreDetail_ABS.newInstance(""+mParam1,position);

            }
           // return ComplianceFragmentA.newInstance("ThirdFragment, Default", "0");
          //  return AllStorewiseComplianceFragment.newInstance("FirstFragment, Instance 1",position);
            return null;

        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
