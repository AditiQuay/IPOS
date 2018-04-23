package quay.com.ipos.retailsales.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 4/19/2018.
 */

public class RetailLoyaltyFragment extends Fragment implements InitInterface {
    private View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.retail_sales_loyalty_points,container,false);
        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
        return rootView;
    }

    @Override
    public void findViewById() {

    }

    @Override
    public void applyInitValues() {

    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }
}
