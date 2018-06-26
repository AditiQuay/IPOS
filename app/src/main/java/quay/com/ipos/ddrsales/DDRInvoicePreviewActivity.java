package quay.com.ipos.ddrsales;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.ddrsales.adapter.DDRIncoTermsAdapter;
import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.ddrsales.model.InvoiceData;
import quay.com.ipos.ddrsales.model.response.Address;
import quay.com.ipos.ddrsales.model.response.DDRIncoTerms;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.ProductSearchResult;


public class DDRInvoicePreviewActivity extends BaseActivity implements InitInterface, View.OnClickListener {
    private static final String TAG = DDRInvoicePreviewActivity.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private TextView mDDRDetails;
    private ImageView mDDRDetailsIcon;
    private TextView textBillingAddress;
    private TextView textShippingAddress;

    private TextView tvItemQty;
    private TextView tvTotalItemPrice;

    private RecyclerView recycleViewIncoTerms;

    private DDRIncoTermsAdapter adapterIncoTerms;


    private List<DDRIncoTerms> incoTermsList=new ArrayList<>();

    private DDR mDdr;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_ddr_pre_invoice);
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mDdr = (DDR) getIntent().getSerializableExtra("ddr");


        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
    }


    public void onSubmitAction(View view) {
        Intent intent = new Intent(activity, DDRBillPreviewActivity.class);
        intent.putExtra("ddr", mDdr);
        startActivity(intent);
    }
    public void onSaveAndCloseAction(View view) {
        Intent intent = new Intent(activity, DDROrderCenterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void findViewById() {
        mDDRDetails = findViewById(R.id.mDDRDetails);
        mDDRDetailsIcon = findViewById(R.id.mDDRDetailsIcon);

        tvItemQty = findViewById(R.id.tvItemQty);
        tvTotalItemPrice = findViewById(R.id.tvTotalItemPrice);


        textBillingAddress = findViewById(R.id.textBillingAddress);
        textShippingAddress = findViewById(R.id.textShippingAddress);

        recycleViewIncoTerms = findViewById(R.id.recycleViewIncoTerms);


        recycleViewIncoTerms.setLayoutManager(new LinearLayoutManager(mContext));
        adapterIncoTerms = new DDRIncoTermsAdapter(mContext, incoTermsList);
        recycleViewIncoTerms.setAdapter(adapterIncoTerms);


    }

    @Override
    public void applyInitValues() {
        if (mDdr != null) {
            mDDRDetails.setText(mDdr.mDDRCode + " - " + mDdr.mDDRName);
            mDDRDetailsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "" + mDdr.mDDRCode + " - " + mDdr.mDDRName, Toast.LENGTH_SHORT).show();
                }
            });
        }


        List<Address> address = InvoiceData.getInstance().address;
        if (address.size() > 1) {
            textBillingAddress.setText(address.get(0).name);
            textShippingAddress.setText(address.get(1).name);
        }
        incoTermsList.clear();
        incoTermsList.addAll(InvoiceData.getInstance().ddrIncoTerms);
        adapterIncoTerms.notifyDataSetChanged();



        setPriceSum();


    }

    private void setPriceSum() {
        List<ProductSearchResult.Datum> cartList = InvoiceData.getInstance().cartList;
        int totalCount = 0;
        for (ProductSearchResult.Datum cart : cartList) {
            totalCount = totalCount + cart.getQty();
        }
        tvItemQty.setText(totalCount+"");
        tvTotalItemPrice.setText(totalCount+"");

    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View view) {

    }
}
