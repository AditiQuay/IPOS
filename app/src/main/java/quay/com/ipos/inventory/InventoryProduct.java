package quay.com.ipos.inventory;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.adapter.KYCViewAllAdapter;

/**
 * Created by niraj.kumar on 6/12/2018.
 */

public class InventoryProduct extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private Button btnSubmit, btnCancel, btnAdd, btnOthers, btnLeakage, btnDefeat, btnNormal;
    private RecyclerView recyclerviewBatch;
    private EditText batchEditText;
    private SwitchCompat switchBarCodeButton, switchBatchButton;
    private RelativeLayout rLayoutBatchNumber;
    private TextView textViewProductBalance, textViewProductQuantity;
    private Toolbar toolbar;
    private ImageView imgArrowLeft, imgArrowRight;
    private Context mContext;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_product_details);
        mContext = InventoryProduct.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);

        //Button Id's
        btnSubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        btnAdd = findViewById(R.id.btnAdd);
        btnOthers = findViewById(R.id.btnOthers);
        btnLeakage = findViewById(R.id.btnLeakage);
        btnDefeat = findViewById(R.id.btnDefeat);
        btnNormal = findViewById(R.id.btnNormal);

        //
        recyclerviewBatch = findViewById(R.id.recyclerviewBatch);
        batchEditText = findViewById(R.id.batchEditText);
        switchBarCodeButton = findViewById(R.id.switchBarCodeButton);
        switchBatchButton = findViewById(R.id.switchBatchButton);
        rLayoutBatchNumber = findViewById(R.id.rLayoutBatchNumber);
        textViewProductQuantity = findViewById(R.id.textViewProductQuantity);
        textViewProductBalance = findViewById(R.id.textViewProductBalance);
        imgArrowLeft = findViewById(R.id.imgArrowLeft);
        imgArrowRight = findViewById(R.id.imgArrowRight);

        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnOthers.setOnClickListener(this);
        btnLeakage.setOnClickListener(this);
        btnDefeat.setOnClickListener(this);
        btnNormal.setOnClickListener(this);


    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("INVENTORY");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        layoutManager = new LinearLayoutManager(mContext);
        recyclerviewBatch.setHasFixedSize(true);
        recyclerviewBatch.setLayoutManager(layoutManager);
//        KYCViewAllAdapter kycViewAllAdapter = new KYCViewAllAdapter(mContext, kycModels, this);
//        recyclerviewBatch.setAdapter(kycViewAllAdapter);
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
        switch (v.getId()) {
            case R.id.btnSubmit:
                break;
            case R.id.btnCancel:
                break;
            case R.id.btnAdd:
                break;
            case R.id.btnOthers:
                break;
            case R.id.btnLeakage:
                break;
            case R.id.btnDefeat:
                break;
            case R.id.btnNormal:
            default:
                break;

        }
    }
}
