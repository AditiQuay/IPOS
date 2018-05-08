package quay.com.ipos.ddr.activity;

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
import quay.com.ipos.ddr.adapter.AddNewOrderAdapter;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.retailsales.adapter.RetailSalesAdapter;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 04-05-2018.
 */

public class AddNewOrderActivity extends BaseActivity implements View.OnClickListener,AdapterListener{

    private static final String TAG = quay.com.ipos.retailsales.activity.AddProductActivity.class.getSimpleName();
    ArrayList<OrderList.Datum> arrSearlist= new ArrayList<>();
    private EditText searchView;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private LinearLayoutManager mLayoutManager;
    private OrderList mOrderListResult;
    private TextView tvItemSize,tvNoItemAvailable;
    private AddNewOrderAdapter mAddNewOrderAdapter;
    private TextView tvClear;
    ArrayList<OrderList.Datum> arrData= new ArrayList<>();

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_customer_list);
        setHeader();

        initializeComponent();
        setAdapter();
        getProduct();
//            setDefaultValues();
    }
    private void setDefaultValues() {

        Double totalPrice;
        for(int i=0 ; i < arrData.size();i++ )
        {
            OrderList.Datum datum = arrData.get(i);
            if(datum.getQty()==0)
                datum.setQty(1);
            if(!datum.isDiscItemSelected())
                datum.setDiscItemSelected(true);
            totalPrice = (Double.parseDouble(datum.getSProductPrice()) * datum.getQty());
            datum.setTotalPrice(totalPrice);
            if(datum.getIsDiscount()) {
                Double discount = Double.parseDouble(datum.getSDiscountPrice()) * totalPrice / 100;
                datum.setDiscount(discount);
            }else {
                datum.setDiscount(0.0);
            }
            arrData.set(i,datum);
        }

    }
    private void setAdapter() {
        mAddNewOrderAdapter = new AddNewOrderAdapter(this,this,mRecyclerView,arrSearlist,this);
        mRecyclerView.setAdapter(mAddNewOrderAdapter);
    }
    public void setHeader() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setTitle("New Order");
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
                mAddNewOrderAdapter.notifyDataSetChanged();
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
            mOrderListResult = Util.getCustomGson().fromJson(json,OrderList.class);
            AppLog.e(RetailSalesAdapter.class.getSimpleName(),Util.getCustomGson().toJson(mOrderListResult));
            arrData.addAll(mOrderListResult.getData());
            setDefaultValues();

//            Util.cacheData(arrData);
            SharedPrefUtil.putString(Constants.Order_List,Util.getCustomGson().toJson(arrData),this);
            mAddNewOrderAdapter.notifyDataSetChanged();
//            setUpdateValues(IPOSApplication.mOrderList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void filter(String charText, ArrayList<OrderList.Datum> responseList) {
        if (arrSearlist != null && responseList != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            arrSearlist.clear();
            if (charText.length() == 0) {
                arrSearlist.addAll(responseList);
            } else {
                for (OrderList.Datum wp : responseList) {
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
            case R.id.tvAddCart:
                Util.hideSoftKeyboard(AddNewOrderActivity.this);
                boolean found = false;
                int pos = (int) view.getTag();
                if( IPOSApplication.mOrderList.size()>0) {
                    for (int i = 0; i < IPOSApplication.mOrderList.size(); i++) {
                        if (arrSearlist.get(pos).getIProductModalId().toString().equalsIgnoreCase (IPOSApplication.mOrderList.get(i).getIProductModalId().toString())) {
                            OrderList.Datum mOrderListData = arrSearlist.get(i);
                            OrderList.Datum mOrderListData1 = IPOSApplication.mOrderList.get(i);

                            if(mOrderListData.getQty()==0)
                                mOrderListData.setQty(mOrderListData.getQty() + 1);
                            else {
                                mOrderListData.setQty(mOrderListData.getQty() + mOrderListData1.getQty());
                            }
                            IPOSApplication.mOrderList.set(i, mOrderListData);
                            found=true;
                        } else {
//                            IPOSApplication.mOrderList.add(0,arrSearlist.get(pos));
                        }
                    }
                    if(!found){
                        IPOSApplication.mOrderList.add(0,arrSearlist.get(pos));
                    }
                }else {
                    IPOSApplication.mOrderList.add(0,arrSearlist.get(pos));
                }
                AppLog.e(TAG,"click" + Util.getCustomGson().toJson(IPOSApplication.mOrderList));
                Intent mIntent = new Intent();
                setResult(3,mIntent);
                finish();

                break;

            case R.id.tvClear:
                searchView.setText("");
                break;
            case R.id.tvMinus:
                setOnClickMinus(view);
                break;

            case R.id.tvPlus:
                setOnClickPlus(view);
                break;
        }

    }

    @Override
    public void onRowClicked(int position) {

    }

    @Override
    public void onRowClicked(int position, int value) {
        OrderList.Datum datum1 = arrSearlist.get(position);

        if ( value<=Integer.parseInt(datum1.getSProductPoints())) {

            datum1.setQty(value);
            arrSearlist.set(position, datum1);
        }else {
            datum1.setQty(Integer.parseInt(datum1.getSProductPoints()));
            arrSearlist.set(position, datum1);
            Util.showToast(datum1.getSProductPoints()+" "+getString(R.string.qty_available),this);
        }
//        mRecyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }});
        mAddNewOrderAdapter.notifyItemChanged(position);
        Util.hideSoftKeyboard(this);
    }

    private void setOnClickPlus(View view) {
        Util.hideSoftKeyboard(this);
        Util.animateView(view);
        int posPlus = (int) view.getTag();
        OrderList.Datum datum1 =arrSearlist.get(posPlus);
        int qty1 = datum1.getQty();
        if(Integer.parseInt(datum1.getSProductPoints())<=qty1){
            Util.showToast("Quantity limit exceed",this);
        }else {
            datum1.setQty(qty1 + 1);
            arrSearlist.set(posPlus, datum1);
            mAddNewOrderAdapter.notifyItemChanged(posPlus);

        }
    }

    private void setOnClickMinus(View view) {
        Util.hideSoftKeyboard(this);
        Util.animateView(view);
        int posMinus = (int) view.getTag();
        OrderList.Datum datum = arrSearlist.get(posMinus);
        int qty = datum.getQty();
        if(qty==1){
            Util.showToast("Cannot purchase with 0 quantity",this);
            return;
        }else {
            datum.setQty(qty - 1);
            arrSearlist.set(posMinus, datum);
            mAddNewOrderAdapter.notifyItemChanged(posMinus);

        }
    }
}
