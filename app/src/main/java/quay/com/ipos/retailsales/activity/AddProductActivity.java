package quay.com.ipos.retailsales.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.retailsales.adapter.AddProductAdapter;
import quay.com.ipos.retailsales.adapter.RetailSalesAdapter;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 20-04-2018.
 */

public class AddProductActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = AddProductActivity.class.getSimpleName();
    ArrayList<ProductList.Datum> arrSearlist= new ArrayList<>();
    private EditText searchView;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private LinearLayoutManager mLayoutManager;
    private ProductList mProductListResult;
    private TextView tvItemSize,tvNoItemAvailable;
    private AddProductAdapter mAddProductAdapter;
    private TextView tvClear;
    ArrayList<ProductList.Datum> arrData= new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_list);
        setHeader();
        initializeComponent();
        setAdapter();
        getProduct();
    }

    private void setAdapter() {
        mAddProductAdapter = new AddProductAdapter(this,this,mRecyclerView,arrSearlist);
        mRecyclerView.setAdapter(mAddProductAdapter);
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

    private void initializeComponent() {
        searchView = findViewById(R.id.searchView);
        searchView.setHint(getResources().getString(R.string.enter_product));
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mRecyclerView = findViewById(R.id.recycler_view);
        tvItemSize = findViewById(R.id.tvItemSize);
        tvClear = findViewById(R.id.tvClear);
        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
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
                tvItemSize.setText("Showing "+arrSearlist.size() + " Products");
                mAddProductAdapter.notifyDataSetChanged();
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


    private void getProduct() {
        try {
            arrData.clear();
            String json = Util.getAssetJsonResponse(this, "product_list.json");
            mProductListResult = Util.getCustomGson().fromJson(json,ProductList.class);
            AppLog.e(RetailSalesAdapter.class.getSimpleName(),Util.getCustomGson().toJson(mProductListResult));
            arrData.addAll(mProductListResult.getData());
//            setDefaultValues();

            Util.cacheData(arrData);
            mAddProductAdapter.notifyDataSetChanged();
//            setUpdateValues(IPOSApplication.mProductList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void filter(String charText, ArrayList<ProductList.Datum> responseList) {
        if (arrSearlist != null && responseList != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            arrSearlist.clear();
            if (charText.length() == 0) {
                arrSearlist.addAll(responseList);
            } else {
                for (ProductList.Datum wp : responseList) {
                    if (wp.getSProductName() != null) {

                        if (wp.getSProductName().toLowerCase(Locale.getDefault()).contains(charText)) {
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
            case R.id.tvAdd:
                Util.hideSoftKeyboard(AddProductActivity.this);
                boolean found = false;
                int pos = (int) view.getTag();
                if( IPOSApplication.mProductList.size()>0) {
                    for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
                        if (arrSearlist.get(pos).getIProductModalId().toString().equalsIgnoreCase (IPOSApplication.mProductList.get(i).getIProductModalId().toString())) {
                            ProductList.Datum mProductListData = IPOSApplication.mProductList.get(i);

                            mProductListData.setQty(mProductListData.getQty() + 1);
                            IPOSApplication.mProductList.set(i, mProductListData);
                            found=true;
                        } else {
//                            IPOSApplication.mProductList.add(0,arrSearlist.get(pos));
                        }
                    }
                    if(!found){
                        IPOSApplication.mProductList.add(0,arrSearlist.get(pos));
                    }
                }else {
                    IPOSApplication.mProductList.add(0,arrSearlist.get(pos));
                }
                AppLog.e(TAG,"click" + Util.getCustomGson().toJson(IPOSApplication.mProductList));
                Intent mIntent = new Intent();
                setResult(1,mIntent);
                finish();

                break;

            case R.id.tvClear:
                searchView.setText("");
                break;
        }

    }
}
