package quay.com.ipos.productCatalogue;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.enums.ProductCatalogueEnum;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.ProductMainSectionAdapter;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.SearchedItemsAdapter;
import quay.com.ipos.productCatalogue.productCatalogueHelper.ProductCatalogueUtils;
import quay.com.ipos.productCatalogue.productModal.CatalogueModal;
import quay.com.ipos.productCatalogue.productModal.ProductCatalogueServerModal;
import quay.com.ipos.productCatalogue.productModal.ProductItemModal;
import quay.com.ipos.productCatalogue.productModal.ProductSectionModal;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;

/**
 * Created by niraj.kumar on 4/25/2018.
 */

public class ProductMain extends Fragment implements InitInterface, SwipeRefreshLayout.OnRefreshListener, ServiceTask.ServiceResultListener {
    private static final String TAG = ProductMain.class.getSimpleName();
    private View rootView;
    private Context mContext;
    private RecyclerView recyclerviewCategory, recyclerviewFilter;
    private SwipeRefreshLayout swipeToRefresh;
    ArrayList<ProductSectionModal> productSectionModals = new ArrayList<>();
    private ArrayList<ProductItemModal> singleItem = new ArrayList<>();

    private SearchView searchViewCatalogue;
    private SearchedItemsAdapter searchedItemsAdapter;
    private ProductMainSectionAdapter productMainSectionAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.product_main_fragment, container, false);
        mContext = getActivity();


        findViewById();
        applyInitValues();
        applyTypeFace();


        return rootView;
    }

    @Override
    public void findViewById() {
        swipeToRefresh = rootView.findViewById(R.id.swipeToRefresh);
        recyclerviewCategory = rootView.findViewById(R.id.recyclerviewCategory);
        searchViewCatalogue = rootView.findViewById(R.id.searchViewCatalogue);

        //Searched item recycler view
        recyclerviewFilter = rootView.findViewById(R.id.recyclerviewFilter);
        swipeToRefresh.setColorSchemeResources(R.color.colorPrimary);
    }


    private void getServerData(String response) {
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(response);
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.optJSONArray(ProductCatalogueEnum.info.toString());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ProductSectionModal dm = new ProductSectionModal();
                dm.setHeaderTitle(jsonObject.optString(ProductCatalogueEnum.section.toString()));
                dm.setSectionProduct(jsonObject.optString(ProductCatalogueEnum.sectionProduct.toString()));


                JSONArray jsonArray3 = jsonObject.optJSONArray(ProductCatalogueEnum.sectionItems.toString());
                singleItem = new ArrayList<>();
                for (int j = 0; j < jsonArray3.length(); j++) {
                    JSONObject jsonObject1 = jsonArray3.getJSONObject(j);
                    ProductItemModal productItemModal = new ProductItemModal();
                    productItemModal.setProductId(jsonObject1.optInt(ProductCatalogueEnum.productId.toString()));
                    productItemModal.setProductName(jsonObject1.optString(ProductCatalogueEnum.productNameMain.toString()));
                    productItemModal.setProductUrl(jsonObject1.optString(ProductCatalogueEnum.productMainUrl.toString()));
                    productItemModal.setCount(jsonObject1.optString(ProductCatalogueEnum.count.toString()));
                    singleItem.add(productItemModal);
                }
                dm.setProductItemModals(singleItem);
                productSectionModals.add(dm);
            }
            ProductCatalogueUtils.clearProductData(mContext);
            ProductCatalogueUtils.clearSearchedProductData(mContext);


            ProductCatalogueUtils.saveProductData(mContext, productSectionModals);
            ProductCatalogueUtils.saveSearchedProductData(mContext, singleItem);

            productMainSectionAdapter = new ProductMainSectionAdapter(mContext, ProductCatalogueUtils.getProductSectionModals(mContext));
            recyclerviewCategory.setAdapter(null);
            recyclerviewCategory.setAdapter(productMainSectionAdapter);

            searchedItemsAdapter = new SearchedItemsAdapter(mContext, ProductCatalogueUtils.getSearchedItems(mContext));
            recyclerviewFilter.setAdapter(null);
            recyclerviewFilter.setAdapter(searchedItemsAdapter);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setToolbarTitle(getString(R.string.toolbar_title_catalogue_product_details));
    }

    private void getProductList() {
        int storeId = SharedPrefUtil.getStoreId(Constants.STORE_ID.trim(), 0, mContext);
        AppLog.e(TAG, "StoreId" + storeId);


        ProductSectionModal productSectionModalParam = new ProductSectionModal();
        productSectionModalParam.setCompanyName("Quay");
        productSectionModalParam.setProductId("NA");
        productSectionModalParam.setStoreID(String.valueOf(storeId));

        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL.trim());
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_PRODUCT_MAIN.trim());
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(productSectionModalParam);
        mTask.setListener(this);
        mTask.setResultType(ProductCatalogueServerModal.class);
        mTask.execute();
    }

    @Override
    public void applyInitValues() {
        //Normal item list
        recyclerviewCategory.setHasFixedSize(true);
        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        if (ProductCatalogueUtils.getProductSectionModals(mContext)!=null){
            productMainSectionAdapter = new ProductMainSectionAdapter(mContext, ProductCatalogueUtils.getProductSectionModals(mContext));
            recyclerviewCategory.setAdapter(null);
            recyclerviewCategory.setAdapter(productMainSectionAdapter);
        }
        swipeToRefresh.setOnRefreshListener(this);


        //Search item list
        recyclerviewFilter.setHasFixedSize(true);
        recyclerviewFilter.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        if (ProductCatalogueUtils.getSearchedItems(mContext)!=null){
            searchedItemsAdapter = new SearchedItemsAdapter(mContext, ProductCatalogueUtils.getSearchedItems(mContext));
            recyclerviewFilter.setAdapter(null);
            recyclerviewFilter.setAdapter(searchedItemsAdapter);
        }

        getProductList();
        SearchView.SearchAutoComplete searchAutoComplete =
                (SearchView.SearchAutoComplete) searchViewCatalogue.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setTextColor(getResources().getColor(R.color.colorPrimary));
        SearchManager searchManager = (SearchManager) mContext.getSystemService(Context.SEARCH_SERVICE);
        assert searchManager != null;
        searchViewCatalogue.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        final EditText et = (EditText) searchViewCatalogue.findViewById(R.id.search_src_text);
        ImageView searchImage = searchViewCatalogue.findViewById(android.support.v7.appcompat.R.id.search_button);

        final ImageView searchClose = (ImageView) searchViewCatalogue.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        searchClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.setText("");
                searchViewCatalogue.setQuery("", false);
                recyclerviewCategory.setVisibility(View.VISIBLE);
                recyclerviewFilter.setVisibility(View.GONE);


            }
        });
        searchViewCatalogue.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchedItemsAdapter.getFilter().filter(query);
                if (query.isEmpty()) {
                    recyclerviewCategory.setVisibility(View.VISIBLE);
                    recyclerviewFilter.setVisibility(View.GONE);
                } else {
                    searchedItemsAdapter.getFilter().filter(query);
                    recyclerviewCategory.setVisibility(View.GONE);
                    recyclerviewFilter.setVisibility(View.VISIBLE);
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    recyclerviewCategory.setVisibility(View.VISIBLE);
                    recyclerviewFilter.setVisibility(View.GONE);
                } else {
                    searchedItemsAdapter.getFilter().filter(newText);
                    recyclerviewCategory.setVisibility(View.GONE);
                    recyclerviewFilter.setVisibility(View.VISIBLE);
                }

                return false;
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


    @Override
    public void onRefresh() {
        getProductList();

    }

    private void disableSwipeToRefresh() {
        if (swipeToRefresh.isRefreshing()) {
            swipeToRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        disableSwipeToRefresh();
        if (httpStatusCode == Constants.SUCCESS) {
            disableSwipeToRefresh();

            if (resultObj != null) {
                if (productSectionModals.size()>0){
                    productSectionModals.clear();
                }
                if (singleItem.size()>0){
                    singleItem.clear();
                }
                getServerData(serverResponse);
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


    }
}
