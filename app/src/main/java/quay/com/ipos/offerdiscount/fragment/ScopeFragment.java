package quay.com.ipos.offerdiscount.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseFragment;

/**
 * Created by aditi.bhuranda on 12-07-2018.
 */

public class ScopeFragment extends BaseFragment implements View.OnClickListener{

    private View rootView;
    private TextView tvLOB,tvCustomerGroup,tvState,tvBusinessPlaces,tvEntity;
    private RecyclerView rvLOB,rvCustomerGroup,rvState,rvBusinessPlaces,rvEntity;
    private LinearLayout llEntity;

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.fragment_scope, container, false);

        initializeComponent(rootView);
        return rootView;
    }

    private void initializeComponent(View rootView) {
        llEntity = rootView.findViewById(R.id.llEntity);
        tvLOB = rootView.findViewById(R.id.tvLOB);
        tvBusinessPlaces = rootView.findViewById(R.id.tvBusinessPlaces);
        tvCustomerGroup = rootView.findViewById(R.id.tvCustomerGroup);
        tvEntity = rootView.findViewById(R.id.tvEntity);
        tvState = rootView.findViewById(R.id.tvState);
        rvLOB = rootView.findViewById(R.id.rvLOB);
        rvBusinessPlaces = rootView.findViewById(R.id.rvBusinessPlaces);
        rvCustomerGroup = rootView.findViewById(R.id.rvCustomerGroup);
        rvState = rootView.findViewById(R.id.rvState);
        rvEntity = rootView.findViewById(R.id.rvEntity);
        tvLOB.setOnClickListener(this);
        tvBusinessPlaces.setOnClickListener(this);
        tvCustomerGroup.setOnClickListener(this);
        tvState.setOnClickListener(this);
        tvEntity.setOnClickListener(this);
    }

    public ScopeFragment() {

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tvLOB:
                visibleLOB();
                break;
            case R.id.tvEntity:
                visibleEntity();
                break;
            case R.id.tvCustomerGroup:
                visibleCustomerGroup();
                break;
            case R.id.tvState:
                visibleState();
                break;
            case R.id.tvBusinessPlaces:
                visibleBusinessPlaces();
                break;
        }
    }

    void visibleLOB(){
        rvLOB.setVisibility(View.VISIBLE);
        rvCustomerGroup.setVisibility(View.GONE);
        rvState.setVisibility(View.GONE);
        rvEntity.setVisibility(View.GONE);
        rvBusinessPlaces.setVisibility(View.GONE);

    }

    void visibleEntity(){
        rvLOB.setVisibility(View.GONE);
        rvEntity.setVisibility(View.VISIBLE);
        rvCustomerGroup.setVisibility(View.GONE);
        rvState.setVisibility(View.GONE);
        rvBusinessPlaces.setVisibility(View.GONE);
    }

    void visibleCustomerGroup(){
        rvCustomerGroup.setVisibility(View.VISIBLE);
        rvLOB.setVisibility(View.GONE);
        rvEntity.setVisibility(View.VISIBLE);
        rvState.setVisibility(View.GONE);
        rvBusinessPlaces.setVisibility(View.GONE);
    }

    void visibleState(){
        rvLOB.setVisibility(View.GONE);
        rvCustomerGroup.setVisibility(View.GONE);
        rvEntity.setVisibility(View.GONE);
        rvState.setVisibility(View.VISIBLE);
        rvBusinessPlaces.setVisibility(View.GONE);
    }

    void visibleBusinessPlaces(){
        rvLOB.setVisibility(View.GONE);
        rvCustomerGroup.setVisibility(View.GONE);
        rvEntity.setVisibility(View.GONE);
        rvState.setVisibility(View.GONE);
        rvBusinessPlaces.setVisibility(View.VISIBLE);
    }
}
