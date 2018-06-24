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

import me.dm7.barcodescanner.zbar.ZBarScannerView;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.listeners.AdapterListener;
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


public class DDRInvoicePreviewActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = DDRInvoicePreviewActivity.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private TextView mDDRDetails;
    private ImageView mDDRDetailsIcon;


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
        mDDRDetails = findViewById(R.id.mDDRDetails);
        mDDRDetailsIcon = findViewById(R.id.mDDRDetailsIcon);

        if (mDdr != null) {
            mDDRDetails.setText(mDdr.mDDRCode + " - " + mDdr.mDDRName);
            mDDRDetailsIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(activity, "" + mDdr.mDDRCode + " - " + mDdr.mDDRName, Toast.LENGTH_SHORT).show();
                }
            });
        }
        initView();
    }


    public void initView() {


    }


    public void onSubmitAction(View view) {
        Intent intent = new Intent(activity, DDRBillPreviewActivity.class);
        intent.putExtra("ddr", mDdr);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
