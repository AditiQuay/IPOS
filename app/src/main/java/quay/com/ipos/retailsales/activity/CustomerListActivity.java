package quay.com.ipos.retailsales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.retailsales.adapter.CustomerListAdapter;
import quay.com.ipos.retailsales.adapter.RecentOrdersListAdapter;
import quay.com.ipos.utility.SpacesItemDecoration;

/**
 * Created by ankush.bansal on 20-04-2018.
 */

public class CustomerListActivity extends BaseActivity{
    String[] inventory = {"Ramesh","Suresh","Mukesh"};

    private RecyclerView recycler_viewRecentOrders;
    private CustomerListAdapter recentOrdersListAdapter;
    private ArrayList<RecentOrderModal> arrSearchList=new ArrayList<>();
    private ArrayList<RecentOrderModal> recentOrderModalArrayList=new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_list);

        setHeader();
        recycler_viewRecentOrders = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager mLayoutManager4 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewRecentOrders.setLayoutManager(mLayoutManager4);
        recycler_viewRecentOrders.addItemDecoration(new SpacesItemDecoration(10));
        recentOrdersListAdapter = new CustomerListAdapter(this, recentOrderModalArrayList);
        recycler_viewRecentOrders.setAdapter(recentOrdersListAdapter);

        EditText searchView=(EditText)findViewById(R.id.searchView);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    filter(charSequence.toString(),recentOrderModalArrayList);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        getRecentOrdersData();
    }
    private void filter(String charText, List<RecentOrderModal> responseList) {
        if ( responseList != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            arrSearchList.clear();
            if (charText.length() == 0) {
                arrSearchList.addAll(responseList);
            } else {
                for (RecentOrderModal wp : responseList) {
                    if (wp.getTitle() != null) {

                        if (wp.getTitle().toLowerCase(Locale.getDefault()).contains(charText)) {
                            arrSearchList.add(wp);
                        }
                    }
                }
            }
        }

        recentOrdersListAdapter = new CustomerListAdapter(this, arrSearchList);
        recycler_viewRecentOrders.setAdapter(recentOrdersListAdapter);


    }



    public void setHeader() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void getRecentOrdersData() {
        for (int i = 0; i < inventory.length; i++) {
            RecentOrderModal recentOrderModal = new RecentOrderModal();
            recentOrderModal.setTitle(inventory[i]);

            recentOrderModalArrayList.add(recentOrderModal);

        }
        recentOrdersListAdapter.notifyDataSetChanged();
    }

}
