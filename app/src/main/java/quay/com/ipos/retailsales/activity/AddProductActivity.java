package quay.com.ipos.retailsales.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.modal.CommonParams;
import quay.com.ipos.modal.ProductListResult;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.retailsales.adapter.AddProductAdapter;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 20-04-2018.
 */
public class AddProductActivity extends BaseActivity implements View.OnClickListener, ServiceTask.ServiceResultListener{

    private static final String TAG = AddProductActivity.class.getSimpleName();
    /**
     * The Array searchlist.
     */
    ArrayList<ProductSearchResult.Datum> arrSearchlist= new ArrayList<>();
    public ArrayList<ProductSearchResult.Datum> data= new ArrayList<>();
    private EditText searchView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ImageView imvClearAdded,imvBack;
    private ProductListResult mProductListResult;
    private TextView tvItemSize,tvNoItemAvailable,tvItemAddedSize;
    private AddProductAdapter mAddProductAdapter;
    private TextView tvClear;
    private LinearLayout llAccept,llSize,llAdded;
    DatabaseHandler databaseHandler;
    /**
     * The data.
     */
    public ArrayList<ProductSearchResult.Datum> data= new ArrayList<>();

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_list);
        initializeComponent();
        setAdapter();
        databaseHandler = new DatabaseHandler(this);
        if(databaseHandler.isRetailMasterEmpty()) {
            searchProductCall("1");
        }else {
            data = databaseHandler.getAllProduct();
        }
    }

    private void setAdapter() {
        mAddProductAdapter = new AddProductAdapter(this,this,mRecyclerView,arrSearchlist);
        mRecyclerView.setAdapter(mAddProductAdapter);
    }


    private void initializeComponent() {
        imvClearAdded = findViewById(R.id.imvClearAdded);
        tvItemAddedSize = findViewById(R.id.tvItemAddedSize);
        llAccept = findViewById(R.id.llAccept);
        imvBack = findViewById(R.id.imvBack);
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
        imvBack.setOnClickListener(this);
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
                    if(data.size()>0)
                    filter(charSequence.toString(),data);
                }
                else {
                    arrSearchlist.clear();
                    mAddProductAdapter.notifyDataSetChanged();
                    tvItemSize.setVisibility(View.GONE);
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


//    private void getProduct() {
//        try {
//            arrData.clear();
//            String json = Util.getAssetJsonResponse(this, "product_list.json");
//            mProductListResult = Util.getCustomGson().fromJson(json,ProductList.class);
//            AppLog.e(RetailSalesAdapter.class.getSimpleName(),Util.getCustomGson().toJson(mProductListResult));
//            arrData.addAll(mProductListResult.getData());
////            setDefaultValues();
//
////            Util.cacheData(arrData);
//            SharedPrefUtil.putString(Constants.Product_List,Util.getCustomGson().toJson(arrData),this);
//            mAddProductAdapter.notifyDataSetChanged();
////            setUpdateValues(IPOSApplication.mProductListResult);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

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

    @Override
    public void onClick(final View view) {

        int id = view.getId();
        switch (id){
            case R.id.llAdd:
                Util.hideSoftKeyboard(AddProductActivity.this);
                boolean found = false;
                int pos = (int) view.getTag();
                if( IPOSApplication.mProductListResult.size()>0) {
                    for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                        if (arrSearchlist.get(pos).getIProductModalId().equalsIgnoreCase (IPOSApplication.mProductListResult.get(i).getIProductModalId())) {
                            ProductSearchResult.Datum mProductSearchResultData = IPOSApplication.mProductListResult.get(i);
                            if(mProductSearchResultData.isAdded()){
                                mProductSearchResultData.setAdded(false);
                                mProductSearchResultData.setQty(mProductSearchResultData.getQty() - 1);
                                IPOSApplication.mProductListResult.remove(i);
                            }else{
                                mProductSearchResultData.setQty(mProductSearchResultData.getQty() + 1);
                                mProductSearchResultData.setAdded(true);
                                IPOSApplication.mProductListResult.set(i, mProductSearchResultData);
                            }
                            found=true;
                        } else {
//                            IPOSApplication.mProductSearchResult.add(0,arrSearchlist.get(pos));
                        }
                    }
                    if(!found){
                        ProductSearchResult.Datum mProductSearchResultData = arrSearchlist.get(pos);
                        mProductSearchResultData.setQty(mProductSearchResultData.getQty() + 1);
                        mProductSearchResultData.setAdded(true);
                        IPOSApplication.mProductListResult.add(0,mProductSearchResultData);
                    }
                }else {
                    ProductSearchResult.Datum mProductSearchResultData = arrSearchlist.get(pos);
                    mProductSearchResultData.setQty(mProductSearchResultData.getQty() + 1);
                    mProductSearchResultData.setAdded(true);
                    IPOSApplication.mProductListResult.add(0,mProductSearchResultData);
                }
//                AppLog.e(TAG,"click" + Util.getCustomGson().toJson(IPOSApplication.mProductSearchResult));

                updateItem();
                mAddProductAdapter.notifyItemChanged(pos);
                Util.showToast(getString(R.string.product_added_successfully),AddProductActivity.this);
                break;

            case R.id.tvClear:
                searchView.setText("");
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
                mAddProductAdapter.notifyDataSetChanged();
                break;
            case R.id.imvBack:
                Intent mIntent = new Intent();
                setResult(1, mIntent);
                onBackPressed();
                break;
            case R.id.llAccept:
//                if(IPOSApplication.mProductListResult.size()>0) {
                Intent mIntent1 = new Intent();
                setResult(1, mIntent1);
//                }else {
//                    Intent mIntent2 = new Intent();
//                    setResult(0, mIntent2);
//                }
                onBackPressed();
                break;
        }

    }

    private void updateItem() {
        if(IPOSApplication.mProductListResult.size()>0){
            llAdded.setVisibility(View.VISIBLE);
            tvItemAddedSize.setText(IPOSApplication.mProductListResult.size()+"");
            llAccept.setVisibility(View.VISIBLE);
        }else {
            llAdded.setVisibility(View.GONE);
            llAccept.setVisibility(View.GONE);
        }
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

                    if(databaseHandler.isRetailMasterEmpty()) {
                        for (int i = 0; i < arrSearchlist.size(); i++) {
                            databaseHandler.addProduct(arrSearchlist.get(i));
                        }
                    }
                }
//                if (resultObj != null) {
//                    productListResult = (ProductListResult) resultObj;
//                    arrSearchlist.addAll(productListResult.getData());
//                }
//                if(arrSearchlist.size()>0) {
//                    tvClear.setVisibility(View.VISIBLE);
//                    llSize.setVisibility(View.VISIBLE);
//                    if(IPOSApplication.mProductSearchResult.size()>0)
//                        for(int i = 0 ; i < arrSearchlist.size(); i++){
//
//                        for (int k=0;k<IPOSApplication.mProductSearchResult.size();k++){
//                            if(arrSearchlist.get(i).getIProductModalId().equalsIgnoreCase(IPOSApplication.mProductSearchResult.get(k).getIProductModalId())){
//                                ProductListResult.Datum datum = arrSearchlist.get(i);
//                                datum.setAdded(true);
//                                arrSearchlist.set(i,datum);
//                            }
//                        }
//
//                        }
//                }
//                else {
//                    tvClear.setVisibility(View.GONE);
//                    llSize.setVisibility(View.GONE);
//                    arrSearchlist.clear();
//                }
                tvItemSize.setVisibility(View.VISIBLE);
                tvItemSize.setText("Showing "+arrSearchlist.size() + " Products");
                mAddProductAdapter.notifyDataSetChanged();
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
        if(arrSearchlist.size()==0){
            tvNoItemAvailable.setVisibility(View.VISIBLE);
            tvNoItemAvailable.setText(getResources().getString(R.string.no_match_found));
        }else
            tvNoItemAvailable.setVisibility(View.GONE);
    }
}
