package quay.com.ipos.retailsales.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseFragment;

/**
 * Created by aditi.bhuranda on 03-07-2018.
 */

public class RetailOrderCentreFragment extends BaseFragment{

    private TextView tvNoItemAvailable;
    private RecyclerView rvOutBox;
    private LinearLayoutManager mLayoutManager;

    /**
     * The Root view.
     */
    View rootView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.order_centre_retail, container, false);
        return rootView;
    }


}
