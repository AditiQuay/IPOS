package quay.com.ipos.ddrsales;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.helper.DatabaseHandler;
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

/**
 * Created by aditi.bhuranda on 20-04-2018.
 */
public class DDRAddProductActivityOld extends BaseActivity implements View.OnClickListener, ServiceTask.ServiceResultListener{

    private static final String TAG = DDRAddProductActivityOld.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private TextView mDDRDetails;
    private ImageView mDDRDetailsIcon;
    private TextView tvClear;
    private EditText searchView;
    private RecyclerView mRecyclerView;

    ArrayList<ProductSearchResult.Datum> arrSearchlist= new ArrayList<>();

    private LinearLayoutManager mLayoutManager;
    private ImageView imvClearAdded;
    private ProductListResult mProductListResult;
    private TextView tvItemSize,tvNoItemAvailable,tvItemAddedSize;
    private DDRProductAdapter mDDRProductAdapter;
    private LinearLayout llAccept,llSize,llAdded;
    DatabaseHandler databaseHandler;
    LoginResult loginResult;

    private DDR mDdr;
    /**
     * The data.
     */
    public ArrayList<ProductSearchResult.Datum> data= new ArrayList<>();

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_ddr_add_product);

        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mDdr = (DDR) getIntent().getSerializableExtra("ddr");
        mDDRDetails =  findViewById(R.id.mDDRDetails);
        mDDRDetailsIcon = findViewById(R.id.mDDRDetailsIcon);

        if (mDdr != null) {
            mDDRDetails.setText(mDdr.mDDRCode +" - "+ mDdr.mDDRName);
            mDDRDetailsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, ""+mDdr.mDDRCode +" - "+ mDdr.mDDRName, Toast.LENGTH_SHORT).show();
                }
            });
        }

        initializeComponent();

        databaseHandler = new DatabaseHandler(this);
        if(databaseHandler.isRetailMasterEmpty(databaseHandler.TABLE_RETAIL)) {
            loginResult = Util.getCustomGson().fromJson(SharedPrefUtil.getString(Constants.Login_result,"",DDRAddProductActivityOld.this),LoginResult.class);
            searchProductCall(loginResult.getUserAccess().getWorklocationID()+"");
        }else {
            data = databaseHandler.getAllProduct();
            IPOSApplication.datumArrayList.addAll(data);
            arrSearchlist.addAll(data);
        }

        setAdapter();
        updateItem();

    }

    private void setAdapter() {
        if(IPOSApplication.mProductListResult.size()>0)
            for (int i = 0 ; i < IPOSApplication.mProductListResult.size();i++){
                for (int j = 0 ; j < data.size(); j++)
                {
                    if (IPOSApplication.mProductListResult.get(i).getIProductModalId().equalsIgnoreCase(data.get(j).getIProductModalId())){
                        ProductSearchResult.Datum datum = data.get(j);
                        count++;
                        datum.setAdded(true);
                        data.set(j,datum);
                    }

                }
            }
        mDDRProductAdapter = new DDRProductAdapter(this,this,mRecyclerView,arrSearchlist);
        mRecyclerView.setAdapter(mDDRProductAdapter);
    }


    private void initializeComponent() {
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
                if (charSequence.toString().trim().length()>0)
                {
                    if(data.size()>0) {
                        tvClear.setVisibility(View.VISIBLE);
                        llSize.setVisibility(View.VISIBLE);
                        filter(charSequence.toString(), data);
                    }else {
                        tvClear.setVisibility(View.GONE);
                        llSize.setVisibility(View.GONE);
                    }
                    updateItem();
                    mDDRProductAdapter.notifyDataSetChanged();
                }
                else {
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

    private void searchProductCall(String s) {
//        showProgress(getResources().getString(R.string.please_wait));
        CommonParams mCommonParams = new CommonParams();
        mCommonParams.setStoreId(s);
        mCommonParams.setSearchParam("NA");
        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_SEARCH_PRODUCT);
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(mCommonParams);
        mTask.setListener(this);
        mTask.setResultType(ProductSearchResult.class);
        if(Util.isConnected())
            mTask.execute();
        else
            Util.showToast(getResources().getString(R.string.no_internet_connection_warning_server_error));
    }


    private void filter(String charText, ArrayList<ProductSearchResult.Datum> responseList) {
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

    int count =0;

    @Override
    public void onClick(final View view) {

        int id = view.getId();
        switch (id){
            case R.id.llAdd:
                Util.hideSoftKeyboard(DDRAddProductActivityOld.this);
                boolean found = false;
                int pos = (int) view.getTag();
                if( IPOSApplication.mProductListResult.size()>0) {
                    for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                        if (arrSearchlist.get(pos).getIProductModalId().equalsIgnoreCase(IPOSApplication.mProductListResult.get(i).getIProductModalId())) {
                            ProductSearchResult.Datum mProductSearchResultData = arrSearchlist.get(pos);
                            if (mProductSearchResultData.isAdded()) {
                                mProductSearchResultData.setAdded(false);
                                count--;
                                mProductSearchResultData.setQty(mProductSearchResultData.getQty() - 1);
                                IPOSApplication.mProductListResult.set(i, mProductSearchResultData);
                                arrSearchlist.set(pos, mProductSearchResultData);

                                Util.showToast(getString(R.string.product_removed_successfully), DDRAddProductActivityOld.this);
                            } else {
                                mProductSearchResultData.setQty(mProductSearchResultData.getQty() + 1);
                                mProductSearchResultData.setAdded(true);
                                count++;
                                IPOSApplication.mProductListResult.set(i, mProductSearchResultData);
                                arrSearchlist.set(pos, mProductSearchResultData);
                                Util.showToast(getString(R.string.product_added_successfully), DDRAddProductActivityOld.this);
                            }
                            found = true;
                        } else {

                        }
                    }
                    if(!found){
                        ProductSearchResult.Datum mProductSearchResultData = arrSearchlist.get(pos);
                        mProductSearchResultData.setQty(mProductSearchResultData.getQty() + 0);
                        mProductSearchResultData.setAdded(true);
                        count++;
                        IPOSApplication.mProductListResult.add(0,mProductSearchResultData);

                        Util.showToast(getString(R.string.product_added_successfully),DDRAddProductActivityOld.this);
                    }
                }else {
                    ProductSearchResult.Datum mProductSearchResultData = arrSearchlist.get(pos);
                    mProductSearchResultData.setQty(mProductSearchResultData.getQty() + 0);
                    mProductSearchResultData.setAdded(true);
                    count++;
                    IPOSApplication.mProductListResult.add(0,mProductSearchResultData);
                    Util.showToast(getString(R.string.product_added_successfully),DDRAddProductActivityOld.this);
                }


                updateItem();
                mDDRProductAdapter.notifyItemChanged(pos);

                break;

            case R.id.tvClear:
                searchView.setText("");
                arrSearchlist.clear();
                arrSearchlist.addAll(data);
                mDDRProductAdapter.notifyDataSetChanged();
                break;

            case R.id.imvClearAdded:
                llAdded.setVisibility(View.GONE);
                IPOSApplication.mProductListResult.clear();
                llAccept.setVisibility(View.GONE);
                if( arrSearchlist.size()>0) {
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

                if(count>0) {
                    Intent mIntent1 = new Intent();
                    setResult(1, mIntent1);
                    onBackPressed();
                }else {
                    Util.showToast(getString(R.string.product_not_added),DDRAddProductActivityOld.this);
                }
                break;
        }

    }

    private void updateItem() {

        if(count>0){
            llAdded.setVisibility(View.VISIBLE);
            tvItemAddedSize.setText(count+"");
        }else {
            llAdded.setVisibility(View.GONE);
        }
        llAccept.setVisibility(View.VISIBLE);
        tvItemSize.setVisibility(View.VISIBLE);
        tvItemSize.setText("Showing "+arrSearchlist.size() + " Products");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    ProductSearchResult mProductSearchResult;


    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj,String responseJson) {
        dismissProgress();
        data.clear();
        if (httpStatusCode == Constants.SUCCESS) {
            if(serviceUrl!=null && serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_SEARCH_PRODUCT)) {
                if(resultObj!=null){

                    mProductSearchResult = (ProductSearchResult) resultObj;
                    data.addAll(mProductSearchResult.getData());
                    arrSearchlist.addAll(data);
                    setAdapter();
                    IPOSApplication.datumArrayList.addAll(data);
                    if(databaseHandler.isRetailMasterEmpty(databaseHandler.TABLE_RETAIL)) {
                        for (int i = 0; i < data.size(); i++) {
                            databaseHandler.addProduct(data.get(i));
                        }
                    }
                }
//                if (resultObj != null) {
//                    productListResult = (ProductListResult) resultObj;
//                    arrSearchlist.addAll(productListResult.getData());
//                }
                if(data.size()>0) {
                    tvClear.setVisibility(View.VISIBLE);
                    llSize.setVisibility(View.VISIBLE);
                    if(IPOSApplication.mProductListResult.size()>0) {
                        for (int i = 0; i < arrSearchlist.size(); i++) {

                            for (int k = 0; k < IPOSApplication.mProductListResult.size(); k++) {
                                if (arrSearchlist.get(i).getIProductModalId().equalsIgnoreCase(IPOSApplication.mProductListResult.get(k).getIProductModalId())) {
                                    ProductSearchResult.Datum datum = arrSearchlist.get(i);
                                    datum.setAdded(true);
                                    arrSearchlist.set(i, datum);
                                }
                            }

                        }
                    }else {
                        tvClear.setVisibility(View.GONE);
//                        llSize.setVisibility(View.GONE);
//                        arrSearchlist.clear();
                    }
                }
                else {
                    tvClear.setVisibility(View.GONE);
                    llSize.setVisibility(View.GONE);
//                    arrSearchlist.clear();
                }

                mDDRProductAdapter.notifyDataSetChanged();
                updateItem();
            }
        } else if (httpStatusCode == Constants.BAD_REQUEST) {
            Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
        }
        if(data.size()==0){
            tvNoItemAvailable.setVisibility(View.VISIBLE);
            tvNoItemAvailable.setText(getResources().getString(R.string.no_match_found));
        }else
            tvNoItemAvailable.setVisibility(View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
