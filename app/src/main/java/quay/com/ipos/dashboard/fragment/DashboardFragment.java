package quay.com.ipos.dashboard.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import quay.com.ipos.R;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.dashboard.adapter.SpinnerDropDownAdapter;
import quay.com.ipos.modal.SpinnerList;
import quay.com.ipos.utility.SelectionItemListDialog;
import quay.com.ipos.utility.Util;


public class DashboardFragment extends Fragment {

    private Context mContext;
    private ViewPager mViewPager;
    private DashboardAdapter adapter;
    private MainActivity mainActivity;
    private Spinner spStoreName;
    private LinearLayout llFilter;
    private boolean isPopupVisible = false;


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

        mainActivity = (MainActivity) mContext;

        spStoreName= view.findViewById(R.id.spStoreName);


      /*  tvStoreName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.onUpdateTitle("filter");
            }
        });*/
        prepareFilterList();
        final TextView page1=view.findViewById(R.id.tvFirstPage);
        final TextView page2=view.findViewById(R.id.tvSecondPage);
        final TextView page3=view.findViewById(R.id.tvThirdPage);
        llFilter = view.findViewById(R.id.llFilter);
        llFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spStoreName.performClick();
            }
        });
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        adapter= new DashboardAdapter(getChildFragmentManager());
        // Set an Adapter on the ViewPager
        mViewPager.setAdapter(adapter);
        //   mViewPager.setPadding(50, 0, 50, 0);
        //   mViewPager.setClipToPadding(false);
        //   mViewPager.setPageMargin(0);
        //   mViewPager.setOffscreenPageLimit(3);
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
//                if (position==0){
//                    page1.setTextColor(getResources().getColor(R.color.black));
//                    page1.setBackgroundResource(R.drawable.textview_circle_white);
//                    page2.setTextColor(getResources().getColor(R.color.white));
//                    page2.setBackgroundResource(R.drawable.textview_circle_app_color);
//                    page3.setTextColor(getResources().getColor(R.color.white));
//                    page3.setBackgroundResource(R.drawable.textview_circle_app_color);
//                }else if (position==1){
//                    page1.setTextColor(getResources().getColor(R.color.white));
//                    page1.setBackgroundResource(R.drawable.textview_circle_app_color);
//                    page2.setTextColor(getResources().getColor(R.color.black));
//                    page2.setBackgroundResource(R.drawable.textview_circle_white);
//                    page3.setTextColor(getResources().getColor(R.color.white));
//                    page3.setBackgroundResource(R.drawable.textview_circle_app_color);
//                }else {
//                    page1.setTextColor(getResources().getColor(R.color.white));
//                    page1.setBackgroundResource(R.drawable.textview_circle_app_color);
//                    page2.setTextColor(getResources().getColor(R.color.white));
//                    page2.setBackgroundResource(R.drawable.textview_circle_app_color);
//                    page3.setTextColor(getResources().getColor(R.color.black));
//                    page3.setBackgroundResource(R.drawable.textview_circle_white);
//                }
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
        ((MainActivity) getActivity()).setToolbarTitle(getString(R.string.dashboard));
    }





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

    private void prepareFilterList() {
//        tvStoreName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (isPopupVisible) return;
//                isPopupVisible = true;
//                List<SpinnerList> spnPanchyatList = null;
//                final Realm realm = Realm.getDefaultInstance();
//                try {
//
//
//                    SpinnerList  spn ;
//                    spnPanchyatList = new ArrayList<>();
//
//                    for (int i = 0; i < 5; i++) {
//                        spn = new SpinnerList();
//                        spn.setName("My Store"+(i+1));
//                        spn.setId((i+1)+"");
//                        spnPanchyatList.add(spn);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    realm.close();
//                } finally {
//                    realm.close();
//                }
//                showSelectionListPanchyat(getActivity(), tvStoreName, spnPanchyatList, getString(R.string.filter));
//            }
//        });
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item,getResources().getStringArray(R.array.stores));
        SpinnerDropDownAdapter sddadapter = new SpinnerDropDownAdapter(getActivity(),getResources().getStringArray(R.array.stores));
        spStoreName.setAdapter(sddadapter);

    }

    private void showSelectionListPanchyat(Context context, final TextView textView, List<SpinnerList> list, final String defaultMsg) {
        if (list != null && list.size() > 0) {
            SelectionItemListDialog selectionPickerDialog = new SelectionItemListDialog(context, defaultMsg, textView.getText().toString().trim(), list, R.layout.pop_up_question_list, new SelectionItemListDialog.ItemPickerListner() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void OnDoneButton(Dialog ansPopup, String strAns, SpinnerList spinnerItem) {
                    ansPopup.dismiss();
                    if (Util.validateString(strAns)) {
                        textView.setText(strAns);
                        textView.setTag(spinnerItem);
                        mainActivity.onUpdateTitle(strAns);
                    } else {
                        textView.setText(defaultMsg);
                    }

                }
            });
            if (!selectionPickerDialog.isShowing()) {
                selectionPickerDialog.show();
            }
            selectionPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isPopupVisible = false;
                }
            });
        } else {
            isPopupVisible = false;
            //   showToastMessage(getString(R.string.no_data));
        }
    }

}
