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
import android.view.View;

import java.util.ArrayList;
import java.util.Locale;

import quay.com.ipos.R;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.CatalogueSubCatalogueFragmentAdapter;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.ProductRangeAdapter;
import quay.com.ipos.retailsales.adapter.AddProductAdapter;
import quay.com.ipos.utility.Constants;

/**
 * Created by niraj.kumar on 5/23/2018.
 */

public class ProductRangeActivity extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private Toolbar toolbarProductRange;
    private RecyclerView recyclerViewProductList;
    private LinearLayoutManager layoutManager;
    private Context mContext;
    private AddProductAdapter productRangeAdapter;
    private DatabaseHandler databaseHandler;
    public ArrayList<ProductSearchResult.Datum> data = new ArrayList<>();
    String productCode;
    ArrayList<ProductSearchResult.Datum> responseList=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_range);
        mContext = ProductRangeActivity.this;
        databaseHandler = new DatabaseHandler(this);
        data = databaseHandler.getAllProduct();
        Intent i = getIntent();
        productCode = i.getStringExtra("ProductCode");

        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();

    }

    private void filter(String charText, ArrayList<ProductSearchResult.Datum> responseList1) {
        if (data != null && responseList1 != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            responseList.clear();
            if (charText.length() == 0) {
                responseList.addAll(responseList1);
            } else {
                for (ProductSearchResult.Datum wp : responseList1) {
                    if (wp.getIProductModalId() != null) {

                        if (wp.getIProductModalId().toLowerCase(Locale.getDefault()).contains(charText)) {
                            responseList.add(wp);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void findViewById() {
        toolbarProductRange = findViewById(R.id.toolbarProductRange);
        recyclerViewProductList = findViewById(R.id.recyclerViewProductList);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbarProductRange);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarProductRange.setTitle(getResources().getString(R.string.toolbar_title_catalogue_product_details));
        toolbarProductRange.setTitleTextColor(getResources().getColor(R.color.white));


        layoutManager = new LinearLayoutManager(mContext);
        recyclerViewProductList.setLayoutManager(layoutManager);

        filter(productCode, data);
        productRangeAdapter = new AddProductAdapter(this, this, recyclerViewProductList, responseList);
        recyclerViewProductList.setAdapter(productRangeAdapter);



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
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
