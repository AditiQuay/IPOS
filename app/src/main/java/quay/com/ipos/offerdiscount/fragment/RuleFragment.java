package quay.com.ipos.offerdiscount.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseFragment;

/**
 * Created by aditi.bhuranda on 12-07-2018.
 */

public class RuleFragment extends BaseFragment {
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.fragment_offer_discount_overview, container, false);

        return rootView;
    }

    public RuleFragment() {

    }
}
