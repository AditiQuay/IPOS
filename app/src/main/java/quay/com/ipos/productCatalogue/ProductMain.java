package quay.com.ipos.productCatalogue;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.ProductMainSectionAdapter;
import quay.com.ipos.productCatalogue.productModal.ProductItemModal;
import quay.com.ipos.productCatalogue.productModal.ProductSectionModal;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/25/2018.
 */

public class ProductMain extends Fragment implements InitInterface, SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private Context mContext;
    private RecyclerView recyclerviewCategory;
    private SwipeRefreshLayout swipeToRefresh;
    ArrayList<ProductSectionModal> productSectionModals = new ArrayList<>();

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
                    productItemModal.setProductDescription(jsonObject1.optString("productDescription"));
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

    @Override
    public void applyInitValues() {
        productSectionModals.clear();
        getServerData();

        recyclerviewCategory.setHasFixedSize(true);
        ProductMainSectionAdapter adapter = new ProductMainSectionAdapter(mContext, productSectionModals);
        recyclerviewCategory.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        recyclerviewCategory.setAdapter(adapter);

        swipeToRefresh.setOnRefreshListener(this);
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
