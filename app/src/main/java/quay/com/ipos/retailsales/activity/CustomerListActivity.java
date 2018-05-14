package quay.com.ipos.retailsales.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.modal.CustomerResult;
import quay.com.ipos.retailsales.adapter.CustomerListAdapter;
import quay.com.ipos.retailsales.adapter.RetailSalesAdapter;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 26-04-2018.
 */
public class CustomerListActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = AddProductActivity.class.getSimpleName();
    /**
     * The Arr searlist.
     */
    ArrayList<CustomerResult.Customer> arrSearlist= new ArrayList<>();
    private EditText searchView;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private LinearLayoutManager mLayoutManager;
    private CustomerResult customerResult;
    private TextView tvItemSize,tvNoItemAvailable;
    private CustomerListAdapter mCustomerListAdapterNew;
    private TextView tvClear;
    /**
     * The Arr data.
     */
    ArrayList<CustomerResult.Customer> arrData= new ArrayList<>();
    private int mSelectedpos;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_list);
        setHeader();
        initializeComponent();
        setAdapter();
        getCustomerData();
    }

    private void setAdapter() {
        mCustomerListAdapterNew = new CustomerListAdapter(this,this,mRecyclerView,arrSearlist);
        mRecyclerView.setAdapter(mCustomerListAdapterNew);
        final GestureDetector mGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean arg0) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean onInterceptTouchEvent(RecyclerView arg0, MotionEvent motionEvent) {
                View child = arg0.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    mSelectedpos = arg0.getChildPosition(child);
                    AppLog.e(TAG, "item:: " + arg0.getChildPosition(child));
                    // AppUtil.LogMsg(TAG,
                    // AppUtil.getCustomGson().toJson(moduleList.get(arg0.getChildPosition(child))));
                    openCustomerDetailActivity(arg0.getChildPosition(child));
                    return true;

                }

                return false;
            }

        });
    }

    private void openCustomerDetailActivity(int childPosition) {
        Intent mIntent = new Intent(this,CustomerDetailActivity.class);
        mIntent.putExtra(Constants.customer_detail,Util.getCustomGson().toJson(arrSearlist.get(childPosition)));
        startActivity(mIntent);
        finish();
    }

    /**
     * Sets header.
     */
    public void setHeader() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.hideSoftKeyboard(CustomerListActivity.this);
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

    private void initializeComponent() {
        searchView = findViewById(R.id.searchView);
        searchView.setHint(getResources().getString(R.string.enter_product));
        mRecyclerView = findViewById(R.id.recycler_view);
        tvItemSize = findViewById(R.id.tvItemSize);
        tvClear = findViewById(R.id.tvClear);
        fab = findViewById(R.id.fab);
        tvNoItemAvailable = findViewById(R.id.tvNoItemAvailable);

        tvClear.setOnClickListener(this);
        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(tvClear, iconFont);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dim_3),
                        getResources().getInteger(R.integer.photo_list_preview_columns)));
        searchView.setHint(getResources().getString(R.string.enter_number));

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString(),arrData);
                if(charSequence.toString().length()>0) {
                    tvClear.setVisibility(View.VISIBLE);
                    tvItemSize.setVisibility(View.VISIBLE);
                }
                else {
                    tvClear.setVisibility(View.GONE);
                    tvItemSize.setVisibility(View.GONE);
                    arrSearlist.clear();
                }
                mCustomerListAdapterNew.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(arrSearlist.size()==0){
                    tvNoItemAvailable.setVisibility(View.VISIBLE);
                }else
                    tvNoItemAvailable.setVisibility(View.GONE);
            }
        });
    }


    private void getCustomerData() {
        try {
            arrData.clear();
            String json = Util.getAssetJsonResponse(this, "customer.json");
            customerResult = Util.getCustomGson().fromJson(json,CustomerResult.class);
            AppLog.e(RetailSalesAdapter.class.getSimpleName(),Util.getCustomGson().toJson(customerResult));
            arrData.addAll(customerResult.getCustomer());
//            setDefaultValues();

            Util.cacheCustomerData(arrData);
            mCustomerListAdapterNew.notifyDataSetChanged();
//            setUpdateValues(IPOSApplication.mProductList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void filter(String charText, ArrayList<CustomerResult.Customer> responseList) {
        if (arrSearlist != null && responseList != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            arrSearlist.clear();
            if (charText.length() == 0) {
                arrSearlist.addAll(responseList);
            } else {
                for (CustomerResult.Customer wp : responseList) {
                    if (wp.getCustomerPhone() != null) {

                        if (wp.getCustomerPhone().toLowerCase(Locale.getDefault()).contains(charText)) {
                            arrSearlist.add(wp);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onClick(final View view) {

        int id = view.getId();
        switch (id){

            case R.id.tvClear:
                searchView.setText("");
                break;
        }

    }
}