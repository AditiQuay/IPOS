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

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.adapter.ProductCatalogueViewAllAdapter;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.ProductCatalogueModal;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 4/24/2018.
 */

public class ProductCatalogueViewAll extends AppCompatActivity implements InitInterface {
    private Toolbar toolbar;
    private TextView textViewProductName;
    private RecyclerView recyclerViewProductsList;
    private Context mContext;
    String ProductGroup;
    private ArrayList<ProductCatalogueModal> productCatalogueModals;
    private ProductCatalogueViewAllAdapter productCatalogueViewAllAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_catalogue_view_all);
        mContext = ProductCatalogueViewAll.this;
        Intent i = getIntent();
        ProductGroup = i.getStringExtra("Group");
        productCatalogueModals = (ArrayList<ProductCatalogueModal>) i.getSerializableExtra("Products");
        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();

    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        textViewProductName = findViewById(R.id.textViewProductName);
        recyclerViewProductsList = findViewById(R.id.recyclerViewProductsList);
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

        textViewProductName.setText(ProductGroup);

        recyclerViewProductsList.setHasFixedSize(true);
        recyclerViewProductsList.setLayoutManager(new LinearLayoutManager(mContext));
        productCatalogueViewAllAdapter = new ProductCatalogueViewAllAdapter(mContext, productCatalogueModals);
        recyclerViewProductsList.setAdapter(productCatalogueViewAllAdapter);

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
        FontUtil.applyTypeface(textViewProductName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }
}
