package quay.com.ipos.ddrsales;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.ddrsales.model.InvoiceData;
import quay.com.ipos.ddrsales.model.request.DDRListReq;
import quay.com.ipos.ddrsales.model.request.DDRProductReq;
import quay.com.ipos.ddrsales.model.response.DDRProductListResponse;
import quay.com.ipos.ddrsales.model.response.GetDDRList;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.CommonParams;
import quay.com.ipos.modal.LoginResult;
import quay.com.ipos.modal.ProductListResult;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.retailsales.adapter.DDRProductAdapter;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by deepak.kumar on 20-04-2018.
 */
public class DDRAddProductActivity extends BaseActivity implements InitInterface, View.OnClickListener {

    private static final String TAG = DDRAddProductActivity.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private TextView mDDRDetails;
    private ImageView mDDRDetailsIcon;
    private TextView tvClear;
    private EditText searchView;
    private RecyclerView mRecyclerView;


    private LinearLayoutManager mLayoutManager;
    private ImageView imvClearAdded;

    private TextView tvItemSize, tvNoItemAvailable, tvItemAddedSize;
    private DDRProductAdapter mDDRProductAdapter;
    private LinearLayout llAccept, llSize, llAdded;

    private DDR mDdr;

    private List<ProductSearchResult.Datum> arrSearchlist = new ArrayList<>();
    private List<ProductSearchResult.Datum> mProductList = new ArrayList<>();

    private MutableLiveData<DDRProductListResponse> mutableLiveData = new MutableLiveData<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_ddr_add_product);

        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mDdr = (DDR) getIntent().getSerializableExtra("ddr");


        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
        if (Util.isConnected()) {
            getServerData();
        } else {
            Util.showToast(getResources().getString(R.string.no_internet_connection_warning_server_error));
        }

        setAdapter();
        updateItem();

    }

    private void setAdapter() {
        if (IPOSApplication.mProductListResult.size() > 0)
            for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                for (int j = 0; j < mProductList.size(); j++) {
                    if (IPOSApplication.mProductListResult.get(i).getIProductModalId().equalsIgnoreCase(mProductList.get(j).getIProductModalId())) {
                        ProductSearchResult.Datum datum = mProductList.get(j);
                        count++;
                        datum.setAdded(true);
                        mProductList.set(j, datum);
                    }

                }
            }
        mDDRProductAdapter = new DDRProductAdapter(this, this, mRecyclerView, arrSearchlist);
        mRecyclerView.setAdapter(mDDRProductAdapter);
    }



    private void filter(String charText, List<ProductSearchResult.Datum> responseList) {
        if (arrSearchlist != null && responseList != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            arrSearchlist.clear();
            if (charText.length() == 0) {
                arrSearchlist.addAll(responseList);
            } else {
                for (ProductSearchResult.Datum wp : responseList) {
                    if (wp.getSProductName() != null) {

                        if (wp.getSProductName().toLowerCase(Locale.getDefault()).contains(charText)) {
                            arrSearchlist.add(wp);
                        }
                    }
                }
            }
        }
    }

    int count = 0;

    @Override
    public void onClick(final View view) {

        int id = view.getId();
        switch (id) {
            case R.id.llAdd:
                Util.hideSoftKeyboard(DDRAddProductActivity.this);
                boolean found = false;
                int pos = (int) view.getTag();
                if (IPOSApplication.mProductListResult.size() > 0) {
                    for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                        if (arrSearchlist.get(pos).getIProductModalId().equalsIgnoreCase(IPOSApplication.mProductListResult.get(i).getIProductModalId())) {
                            ProductSearchResult.Datum mProductSearchResultData = arrSearchlist.get(pos);
                            if (mProductSearchResultData.isAdded()) {
                                mProductSearchResultData.setAdded(false);
                                count--;
                                mProductSearchResultData.setQty(mProductSearchResultData.getQty() - 1);
                                IPOSApplication.mProductListResult.set(i, mProductSearchResultData);
                                arrSearchlist.set(pos, mProductSearchResultData);

                                Util.showToast(getString(R.string.product_removed_successfully), DDRAddProductActivity.this);
                            } else {
                                mProductSearchResultData.setQty(mProductSearchResultData.getQty() + 1);
                                mProductSearchResultData.setAdded(true);
                                count++;
                                IPOSApplication.mProductListResult.set(i, mProductSearchResultData);
                                arrSearchlist.set(pos, mProductSearchResultData);
                                Util.showToast(getString(R.string.product_added_successfully), DDRAddProductActivity.this);
                            }
                            found = true;
                        } else {

                        }
                    }
                    if (!found) {
                        ProductSearchResult.Datum mProductSearchResultData = arrSearchlist.get(pos);
                        mProductSearchResultData.setQty(mProductSearchResultData.getQty() + 0);
                        mProductSearchResultData.setAdded(true);
                        count++;
                        IPOSApplication.mProductListResult.add(0, mProductSearchResultData);

                        Util.showToast(getString(R.string.product_added_successfully), DDRAddProductActivity.this);
                    }
                } else {
                    ProductSearchResult.Datum mProductSearchResultData = arrSearchlist.get(pos);
                    mProductSearchResultData.setQty(mProductSearchResultData.getQty() + 0);
                    mProductSearchResultData.setAdded(true);
                    count++;
                    IPOSApplication.mProductListResult.add(0, mProductSearchResultData);
                    Util.showToast(getString(R.string.product_added_successfully), DDRAddProductActivity.this);
                }


                updateItem();
                mDDRProductAdapter.notifyItemChanged(pos);

                break;

            case R.id.tvClear:
                searchView.setText("");
                arrSearchlist.clear();
                arrSearchlist.addAll(mProductList);
                mDDRProductAdapter.notifyDataSetChanged();
                break;

            case R.id.imvClearAdded:
                llAdded.setVisibility(View.GONE);
                IPOSApplication.mProductListResult.clear();
                llAccept.setVisibility(View.GONE);
                if (arrSearchlist.size() > 0) {
                    for (int i = 0; i < arrSearchlist.size(); i++) {
                        ProductSearchResult.Datum datum = arrSearchlist.get(i);
                        datum.setAdded(false);
                        arrSearchlist.set(i, datum);
                    }
                }
                mDDRProductAdapter.notifyDataSetChanged();
                break;
            case R.id.imvBack:

                onBackPressed();
                break;
            case R.id.llAccept:

                if (count > 0) {
                    Intent mIntent1 = new Intent();
                    setResult(1, mIntent1);
                    onBackPressed();
                } else {
                    Util.showToast(getString(R.string.product_not_added), DDRAddProductActivity.this);
                }
                break;
        }

    }

    private void updateItem() {

        if (count > 0) {
            llAdded.setVisibility(View.VISIBLE);
            tvItemAddedSize.setText(count + "");
        } else {
            llAdded.setVisibility(View.GONE);
        }
        llAccept.setVisibility(View.VISIBLE);
        tvItemSize.setVisibility(View.VISIBLE);
        tvItemSize.setText("Showing " + arrSearchlist.size() + " Products");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void findViewById() {
        mDDRDetails = findViewById(R.id.mDDRDetails);
        mDDRDetailsIcon = findViewById(R.id.mDDRDetailsIcon);


        imvClearAdded = findViewById(R.id.imvClearAdded);
        tvItemAddedSize = findViewById(R.id.tvItemAddedSize);
        llAccept = findViewById(R.id.llAccept);
        llAdded = findViewById(R.id.llAdded);
        llSize = findViewById(R.id.llSize);
        searchView = findViewById(R.id.searchView);
        searchView.setHint(getResources().getString(R.string.enter_product));
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mRecyclerView = findViewById(R.id.recycler_view);
        tvItemSize = findViewById(R.id.tvItemSize);
        tvClear = findViewById(R.id.tvClear);
        tvNoItemAvailable = findViewById(R.id.tvNoItemAvailable);
        imvClearAdded.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        llAccept.setOnClickListener(this);
        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(tvClear, iconFont);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(8));
        //   mRecyclerView.addItemDecoration(new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dim_3), getResources().getInteger(R.integer.photo_list_preview_columns)));

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    if (mProductList.size() > 0) {
                        tvClear.setVisibility(View.VISIBLE);
                        llSize.setVisibility(View.VISIBLE);
                        filter(charSequence.toString(), mProductList);
                    } else {
                        tvClear.setVisibility(View.GONE);
                        llSize.setVisibility(View.GONE);
                    }
                    updateItem();
                    mDDRProductAdapter.notifyDataSetChanged();
                } else {
//                    arrSearchlist.clear();
                    mDDRProductAdapter.notifyDataSetChanged();
//                    tvItemSize.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void applyInitValues() {

        if (mDdr != null) {
            mDDRDetails.setText(mDdr.mDDRCode + " - " + mDdr.mDDRName);
            mDDRDetailsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "" + mDdr.mDDRCode + " - " + mDdr.mDDRName, Toast.LENGTH_SHORT).show();
                }
            });
        }


        getLiveServerData().observe(this, new Observer<DDRProductListResponse>() {
            @Override
            public void onChanged(@Nullable DDRProductListResponse mProductList) {
                try {
                    if (mProductList != null) {

                        setData(mProductList);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }


    private void getServerData() {

        DDRProductReq req = new DDRProductReq();
        Log.i("mProductList", new Gson().toJson(req));
        Call<DDRProductListResponse> call = RestService.getApiServiceSimple().DDR_GetDDRProductList(req);
        call.enqueue(new Callback<DDRProductListResponse>() {
            @Override
            public void onResponse(Call<DDRProductListResponse> call, Response<DDRProductListResponse> response) {
                Log.d(TAG, "response.raw().request().url();" + response.raw().request().url());
                if (response.code() != 200) {
                    Toast.makeText(activity, "Code:" + response.code() + ", Message:" + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if (response.body() != null) {
                        InvoiceData.getInstance().setInitData(response.body());
                        mutableLiveData.setValue(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<DDRProductListResponse> call, Throwable t) {
                Toast.makeText(activity, " Message:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "ERROR OCCURED");
                Log.i("JsonObject", t.toString());
                t.printStackTrace();
            }
        });

    }

    private void setData(DDRProductListResponse response) {
        this.mProductList = response.productList;

        arrSearchlist.clear();
        arrSearchlist.addAll(mProductList);
        mDDRProductAdapter.notifyDataSetChanged();

    }

    public MutableLiveData<DDRProductListResponse> getLiveServerData() {
        return mutableLiveData;
    }
}
