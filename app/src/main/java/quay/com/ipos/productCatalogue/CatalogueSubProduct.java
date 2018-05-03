package quay.com.ipos.productCatalogue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.CatalogueSubCatalogueFragmentAdapter;
import quay.com.ipos.productCatalogue.productModal.CatalogueModal;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 4/17/2018.
 */

public class CatalogueSubProduct extends AppCompatActivity implements InitInterface, MyListener{
    private TextView textViewProductName,textViewProductCountTitle,textViewProductCount;
    private RecyclerView recyclerviewProduct;
    private Context mContext;
    private ArrayList<quay.com.ipos.productCatalogue.productModal.CatalogueModal> catalogueModalsSet = new ArrayList<>();
    private CatalogueSubCatalogueFragmentAdapter catalogueSubCatalogueFragmentAdapter;
    private LinearLayoutManager layoutManager;
    private String productName;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_sub_product);
        Intent i = getIntent();
        //Retrieve the value
        productName = i.getStringExtra("Product Name");
        mContext = CatalogueSubProduct.this;
        findViewById();
        applyInitValues();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbarCatalogueSubProduct);
        textViewProductName = findViewById(R.id.textViewProductName);
        recyclerviewProduct = findViewById(R.id.recyclerviewProduct);
        textViewProductCountTitle = findViewById(R.id.textViewProductCountTitle);
        textViewProductCount = findViewById(R.id.textViewProductCount);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle(getResources().getString(R.string.toolbar_title_catalogue_product_details));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));


        textViewProductName.setText(productName);

        layoutManager = new LinearLayoutManager(mContext);
        recyclerviewProduct.setLayoutManager(layoutManager);
        catalogueSubCatalogueFragmentAdapter = new CatalogueSubCatalogueFragmentAdapter(mContext, catalogueModalsSet, this);
        recyclerviewProduct.setAdapter(catalogueSubCatalogueFragmentAdapter);

        catalogueModalsSet.clear();
        getServerData();

    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewProductName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(toolbar,FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewProductCountTitle,FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewProductCount,FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

    }

    private void getServerData() {
        try {
            // Creating JSONObject from String
            JSONObject jsonObjMain = new JSONObject(Util.getAssetJsonResponse(mContext, "catalogue_product_details.Json"));
            // Creating JSONArray from JSONObject
            JSONArray jsonArray = jsonObjMain.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                quay.com.ipos.productCatalogue.productModal.CatalogueModal catalogueModal = new quay.com.ipos.productCatalogue.productModal.CatalogueModal();
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
        Intent gotToProductDetail = new Intent(mContext, ProductDetails.class);
        gotToProductDetail.putExtra("ProductName", catalogueModal.sProductName);
        startActivity(gotToProductDetail);
    }

    @Override
    public void onRowClicked(int position, int value) {

    }
}
