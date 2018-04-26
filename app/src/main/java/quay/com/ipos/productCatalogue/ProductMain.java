package quay.com.ipos.productCatalogue;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class ProductMain extends Fragment implements InitInterface {
    private View rootView;
    private Context mContext;
    private RecyclerView recyclerviewCategory;
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
        recyclerviewCategory = rootView.findViewById(R.id.recyclerviewCategory);
    }

    private void getServerData() {
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(Util.getAssetJsonResponse(mContext, "product_data_main.Json"));
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray("info");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ProductSectionModal dm = new ProductSectionModal();
                dm.setHeaderTitle(jsonObject.getString("key"));


                JSONArray jsonArray3 = jsonObject.getJSONArray("sectionItems");
                ArrayList<ProductItemModal> singleItem = new ArrayList<ProductItemModal>();
                for (int j = 0; j < jsonArray3.length(); j++) {
                    JSONObject jsonObject1 = jsonArray3.getJSONObject(j);
                    ProductItemModal productItemModal = new ProductItemModal();
                    productItemModal.setProductName(jsonObject1.getString("sProductName"));
                    productItemModal.setProductDescription(jsonObject1.getString("sProductDetail"));
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
    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }


}
