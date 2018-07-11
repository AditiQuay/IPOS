package quay.com.ipos.inventoryTrasfer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInActivity.TransferInDetailsActivity;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 7/6/2018.
 */

public class TransferStepsActivity extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private static final String TAG = TransferStepsActivity.class.getSimpleName();
    private Context mContext;
    private Toolbar toolbar;
    //Tab
    private LinearLayout lLayTransferOut, lLayoutShipment, lLayoutTransferIn;
    private TextView tvTransferOut, tvShipment, tvTransferIn;

    //TransferIn id's
    private RelativeLayout rlTab, llgrnn;
    private TextView tvTransferNumber, tvOpen, tranferOutCount, tranferInCount, apCount, balanceQtyCount;


    //Grn header
    private RelativeLayout rGrn;
    private TextView tvGrnNumberCount, textViewAdd;
    ;
    private RecyclerView recycleviewList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_transfer_steps_activity);
        mContext = TransferStepsActivity.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        //Tab Id's
        lLayTransferOut = findViewById(R.id.lLayTransferOut);
        lLayoutShipment = findViewById(R.id.lLayoutShipment);
        lLayoutTransferIn = findViewById(R.id.lLayoutTransferIn);
        tvTransferOut = findViewById(R.id.tvTransferOut);
        tvShipment = findViewById(R.id.tvShipment);
        tvTransferIn = findViewById(R.id.tvTransferIn);

        //TransferIn Id's
        rlTab = findViewById(R.id.rlTab);
        llgrnn = findViewById(R.id.llgrnn);
        tvTransferNumber = findViewById(R.id.tvTransferNumber);
        tvOpen = findViewById(R.id.tvOpen);
        tranferOutCount = findViewById(R.id.tranferOutCount);
        tranferInCount = findViewById(R.id.tranferInCount);
        apCount = findViewById(R.id.apCount);
        balanceQtyCount = findViewById(R.id.balanceQtyCount);


        //GRN header view
        rGrn = findViewById(R.id.rGrn);
        tvGrnNumberCount = findViewById(R.id.tvGrnNumberCount);
        textViewAdd = findViewById(R.id.textViewAdd);
        recycleviewList = findViewById(R.id.recycleviewList);

        textViewAdd.setOnClickListener(this);
        lLayoutTransferIn.setOnClickListener(this);

    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("Inventory");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

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
        switch (v.getId()) {
            case R.id.textViewAdd:
                Intent i = new Intent(mContext, TransferInDetailsActivity.class);
                startActivity(i);

                break;
            case R.id.lLayoutTransferIn:
                rlTab.setVisibility(View.VISIBLE);
                rGrn.setVisibility(View.VISIBLE);
                tvTransferOut.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvTransferIn.setBackgroundResource(R.drawable.textview_circle_app_color);
                recycleviewList.setVisibility(View.VISIBLE);

            default:
                break;
        }
    }
}
