package quay.com.ipos.inventoryTrasfer;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 7/6/2018.
 */

public class TransferStepsActivity extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private static final String TAG = TransferStepsActivity.class.getSimpleName();
    private Context mContext;
    //Tab
    private LinearLayout lLayTransferOut, lLayoutShipment, lLayoutTransferIn;
    private TextView tvTransferOut, tvShipment, tvTransferIn;

    //TransferIn id's
    private RelativeLayout rlTab, llgrnn;
    private TextView tvTransferNumber, tvOpen, tranferOutCount, tranferInCount, apCount, balanceQtyCount, tvGrnNumberCount, textViewAdd;
    private RecyclerView recycleviewList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_transfer_steps_activity);
        mContext = TransferStepsActivity.this;
    }

    @Override
    public void findViewById() {

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
        tvGrnNumberCount = findViewById(R.id.tvGrnNumberCount);
        textViewAdd = findViewById(R.id.textViewAdd);

        recycleviewList = findViewById(R.id.recycleviewList);


        textViewAdd.setOnClickListener(this);

    }

    @Override
    public void applyInitValues() {

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
        switch (v.getId()){

        }
    }
}
