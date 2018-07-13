package quay.com.ipos.offerdiscount.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.fragment_offer_discount_overview, container, false);
        return rootView;
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
