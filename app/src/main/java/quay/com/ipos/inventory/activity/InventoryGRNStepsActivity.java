package quay.com.ipos.inventory.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.InventoryGRNStepsAdapter;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 6/13/2018.
 */

public class InventoryGRNStepsActivity extends AppCompatActivity implements InitInterface {
    private RecyclerView recycleviewCard;
    private TextView textViewAdd, tvGrnNumberCount, tvPoNumber, poQtyCount, grnQtyCount, apQtyCount, balanceQtyCount;
    private Toolbar toolbar;
    private LinearLayoutManager layoutManager;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grn_steps_activity);
        mContext = InventoryGRNStepsActivity.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        recycleviewCard = findViewById(R.id.recycleviewCard);
        textViewAdd = findViewById(R.id.textViewAdd);
        tvGrnNumberCount = findViewById(R.id.tvGrnNumberCount);
        tvPoNumber = findViewById(R.id.tvPoNumber);
        poQtyCount = findViewById(R.id.poQtyCount);
        grnQtyCount = findViewById(R.id.grnQtyCount);
        apQtyCount = findViewById(R.id.apQtyCount);
        balanceQtyCount = findViewById(R.id.balanceQtyCount);
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
        recycleviewCard.setHasFixedSize(true);
        recycleviewCard.setLayoutManager(layoutManager);
//        InventoryGRNStepsAdapter kycViewAllAdapter = new InventoryGRNStepsAdapter(mContext, kycModels, this);
//        recyclerviewBatch.setAdapter(kycViewAllAdapter);
    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }
}
