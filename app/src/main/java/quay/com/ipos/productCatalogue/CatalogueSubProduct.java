package quay.com.ipos.productCatalogue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.modal.CatalogueModal;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.CatalogueSubCatalogueFragmentAdapter;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class CatalogueSubProduct extends BaseFragment implements InitInterface, MyListener {
    private View rootView;
    private TextView textViewProductName;
    private RecyclerView recyclerviewProduct;
    private Context mContext;
    private ArrayList<CatalogueModal> catalogueModalsSet = new ArrayList<>();
    private CatalogueSubCatalogueFragmentAdapter catalogueSubCatalogueFragmentAdapter;
    private LinearLayoutManager layoutManager;
    private String productName;
    private MyListener listener;
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.catalogue_sub_product, container, false);
        //Retrieve the value
        productName = getArguments().getString("Product Name");
        mContext = getActivity();
        listener = CatalogueSubProduct.this;
        setHasOptionsMenu(true);
        findViewById();
        applyInitValues();
        return rootView;
    }

    @Override
    public void findViewById() {
        textViewProductName = rootView.findViewById(R.id.textViewProductName);
        recyclerviewProduct = rootView.findViewById(R.id.recyclerviewProduct);
    }


    @Override
    public void applyInitValues() {
        textViewProductName.setText(productName);

        layoutManager = new LinearLayoutManager(mContext);
        recyclerviewProduct.setLayoutManager(layoutManager);
        catalogueSubCatalogueFragmentAdapter = new CatalogueSubCatalogueFragmentAdapter(mContext, catalogueModalsSet,this);
        recyclerviewProduct.setAdapter(catalogueSubCatalogueFragmentAdapter);

        catalogueModalsSet.clear();
        getServerData();

    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewProductName, FontUtil.getTypceFaceRobotoMedium_0(mContext));

    }

    private void getServerData() {
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(Util.getAssetJsonResponse(mContext, "catalogue_product_details.Json"));
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CatalogueModal catalogueModal = new CatalogueModal();
                catalogueModal.sProductName = jsonObject.getString("sProductName");
                catalogueModal.sProductFeature = jsonObject.getString("sProductFeature");
                catalogueModal.sProductPrice = jsonObject.getString("sProductPrice");
                catalogueModalsSet.add(catalogueModal);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onRowClicked(int position) {
        CatalogueModal catalogueModal = catalogueModalsSet.get(position);
        Intent gotToProductDetail = new Intent(mContext,ProductDetails.class);
        gotToProductDetail.putExtra("ProductName",catalogueModal.sProductName);
        startActivity(gotToProductDetail);
    }

    @Override
    public void onRowClicked(int position, int value) {

    }
}
