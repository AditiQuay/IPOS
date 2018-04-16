package quay.com.ipos.dashboard.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import quay.com.ipos.R;


/**
 * Created by ankush.bansal on 05-04-2018.
 */

@SuppressLint("ValidFragment")
public  class DashboardItemFragment extends Fragment {

    private int position;
    private String strImage;

    // newInstance constructor for creating fragment with arguments
    public  DashboardItemFragment newInstance(int position) {
        DashboardItemFragment fragmentFirst = new DashboardItemFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images, container, false);


        return view;}











}
