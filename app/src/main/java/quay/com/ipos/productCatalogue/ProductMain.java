package quay.com.ipos.productCatalogue;

import android.app.SearchManager;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.ProductMainSectionAdapter;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.SearchedItemsAdapter;
import quay.com.ipos.productCatalogue.productModal.ProductItemModal;
import quay.com.ipos.productCatalogue.productModal.ProductSectionModal;
import quay.com.ipos.productCatalogue.productModal.SearchedItemModal;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/25/2018.
 */

public class ProductMain extends Fragment implements InitInterface, SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private Context mContext;
    private RecyclerView recyclerviewCategory, recyclerviewFilter;
    private SwipeRefreshLayout swipeToRefresh;
    ArrayList<ProductSectionModal> productSectionModals = new ArrayList<>();
    ArrayList<SearchedItemModal> searchedItems = new ArrayList<SearchedItemModal>();

    private SearchView searchViewCatalogue;
    private SearchedItemsAdapter searchedItemsAdapter;

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


    private void getServerData() {
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(Util.getAssetJsonResponse(mContext, "product_data_main.Json"));
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.optJSONArray("info");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ProductSectionModal dm = new ProductSectionModal();
                dm.setHeaderTitle(jsonObject.optString("section"));


                JSONArray jsonArray3 = jsonObject.optJSONArray("sectionItems");
                ArrayList<ProductItemModal> singleItem = new ArrayList<ProductItemModal>();
                for (int j = 0; j < jsonArray3.length(); j++) {
                    JSONObject jsonObject1 = jsonArray3.getJSONObject(j);
                    ProductItemModal productItemModal = new ProductItemModal();
                    productItemModal.setProductId(jsonObject1.optInt("productId"));
                    productItemModal.setProductName(jsonObject1.optString("productName"));
                    productItemModal.setProductUrl(jsonObject1.optString("productUrl"));
                    singleItem.add(productItemModal);
                }
                dm.setProductItemModals(singleItem);
                productSectionModals.add(dm);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getSearchedData() {
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(Util.getAssetJsonResponse(mContext, "product_data_main.Json"));
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.optJSONArray("info");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                JSONArray jsonArray1 = jsonObject.optJSONArray("sectionItems");
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    SearchedItemModal searchedItemModal = new SearchedItemModal();
                    searchedItemModal.setProductId(jsonObject1.optInt("productId"));
                    searchedItemModal.setProductName(jsonObject1.optString("productName"));
                    searchedItemModal.setProductUrl(jsonObject1.optString("productUrl"));
                    searchedItems.add(searchedItemModal);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyInitValues() {
        productSectionModals.clear();
        searchedItems.clear();

        getServerData();
        getSearchedData();

        recyclerviewCategory.setHasFixedSize(true);
        ProductMainSectionAdapter adapter = new ProductMainSectionAdapter(mContext, productSectionModals);
        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerviewCategory.setAdapter(adapter);
        swipeToRefresh.setOnRefreshListener(this);


        //Search item list
        recyclerviewFilter.setHasFixedSize(true);
        searchedItemsAdapter = new SearchedItemsAdapter(mContext, searchedItems);
        recyclerviewFilter.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerviewFilter.setAdapter(searchedItemsAdapter);
        ////////////////////////////////////////////////////////

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
        swipeToRefresh.setRefreshing(false);
    }
}
