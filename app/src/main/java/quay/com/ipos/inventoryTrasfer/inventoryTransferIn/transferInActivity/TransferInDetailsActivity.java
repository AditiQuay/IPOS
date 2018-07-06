package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 7/6/2018.
 */

public class TransferInDetailsActivity extends AppCompatActivity implements InitInterface {
    //GRN id's
    private RelativeLayout rGrn;
    private EditText grnNumber, et_totalItems, et_received_date, et_value, openQty, grnQty, balanceQty;
    private ImageView ivReceivedDateCalender;

    //Items id's
    private RelativeLayout rItemsDetails;
    private ImageView ivItemAdd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_in_details);
    }

    @Override
    public void findViewById() {
//        //Grn
//        rGrn = findViewById(R.id.rGrn);
//        grnNumber = findViewById(R.id.grnNumber);
//        et_totalItems = findViewById(R.id.et_totalItems);
//        et_received_date = findViewById(R.id.et_received_date);
//        et_value = findViewById(R.id.et_value);
//        openQty = findViewById(R.id.openQty);
//        grnQty = findViewById(R.id.grnQty);
//        balanceQty = findViewById(R.id.balanceQty);
//        ivReceivedDateCalender = findViewById(R.id.ivReceivedDateCalender);
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
}
