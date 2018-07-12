package quay.com.ipos.retailsales.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.kycPartnerConnect.KYCRelationShipFragment;

/**
 * Created by aditi.bhuranda on 12-07-2018.
 */

public class OfferDiscountOverviewFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public OfferDiscountOverviewFragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static OfferDiscountOverviewFragment newInstance(String param1, String param2) {
        OfferDiscountOverviewFragment fragment = new OfferDiscountOverviewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

}
