package quay.com.ipos.offerdiscount.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseFragment;

/**
 * Created by aditi.bhuranda on 12-07-2018.
 */

public class OfferDiscountOverviewFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private View rootView;
    private TextInputLayout tilName,tilDesc,tilDisplayName;
    private TextInputEditText tieName,tieDesc,tieDisplayName;
    private LinearLayout llStartDate,llEndDate;
    private TextView tvStartDate,tvEndDate;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.fragment_offer_discount_overview, container, false);
        initializeComponent(rootView);
        return rootView;
    }

    private void initializeComponent(View rootView) {
        tilName = rootView.findViewById(R.id.tilName);
        tilDesc = rootView.findViewById(R.id.tilDesc);
        tilDisplayName = rootView.findViewById(R.id.tilDisplayName);
        tieName = rootView.findViewById(R.id.tieName);
        tieDesc = rootView.findViewById(R.id.tieDesc);
        tieDisplayName = rootView.findViewById(R.id.tieDisplayName);
        llStartDate = rootView.findViewById(R.id.llStartDate);
        llEndDate = rootView.findViewById(R.id.llEndDate);
        tvStartDate = rootView.findViewById(R.id.tvStartDate);
        tvEndDate = rootView.findViewById(R.id.tvEndDate);
    }

    public OfferDiscountOverviewFragment() {

    }




    // Rename and change types and number of parameters
    public static OfferDiscountOverviewFragment newInstance(String param1, String param2) {
        OfferDiscountOverviewFragment fragment = new OfferDiscountOverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

}
