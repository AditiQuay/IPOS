package quay.com.ipos.ddrsales;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zbar.ZBarScannerView;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.ddrsales.model.InvoiceData;
import quay.com.ipos.ddrsales.model.response.Address;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyAdapterTags;
import quay.com.ipos.listeners.ScanFilterListener;
import quay.com.ipos.listeners.ScannerProductListener;
import quay.com.ipos.modal.PaymentRequest;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.modal.ProductListResult;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.realmbean.RealmPinnedResults;
import quay.com.ipos.retailsales.activity.OutboxActivity;
import quay.com.ipos.retailsales.activity.PaymentModeActivity;
import quay.com.ipos.retailsales.activity.PinnedRetailActivity;
import quay.com.ipos.retailsales.adapter.RetailSalesAdapter;
import quay.com.ipos.retailsales.fragment.FullScannerFragment;
import quay.com.ipos.retailsales.fragment.RetailSalesFragment;
import quay.com.ipos.ui.DiscountDeleteFragment;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.MessageDialog;
import quay.com.ipos.ui.MyDialogFragment;
import quay.com.ipos.ui.WrapContentLinearLayoutManager;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

import static quay.com.ipos.application.IPOSApplication.isClicked;
import static quay.com.ipos.application.IPOSApplication.totalAmount;


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
