package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 7/10/2018.
 */

public class TransferInBatchActivity extends AppCompatActivity implements InitInterface {
    private Toolbar toolbar;
    private TextView textViewProductName, textViewProductQuantity, textViewProductBalance;
    private RelativeLayout rLayoutBatchNumber;
    private ImageView imgArrowLeft, imgArrowRight;
    private SwitchCompat switchBatchButton;

    private RecyclerView recyclerviewButton;

    private Button btnTabOther, btnAddBatch;
    private EditText batchEditText;
    private CheckBox checkBox1;
    private Button btnAction, btnSave;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_in_batch_layout);
        mContext = TransferInBatchActivity.this;

        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        textViewProductName = findViewById(R.id.textViewProductName);
        textViewProductQuantity = findViewById(R.id.textViewProductQuantity);
        textViewProductBalance = findViewById(R.id.textViewProductBalance);
        rLayoutBatchNumber = findViewById(R.id.rLayoutBatchNumber);
        imgArrowLeft = findViewById(R.id.imgArrowLeft);
        imgArrowRight = findViewById(R.id.imgArrowRight);
        switchBatchButton = findViewById(R.id.switchBatchButton);
        recyclerviewButton = findViewById(R.id.recyclerviewButton);
        btnTabOther = findViewById(R.id.btnTabOther);
        btnAddBatch = findViewById(R.id.btnAddBatch);
        batchEditText = findViewById(R.id.batchEditText);
        checkBox1 = findViewById(R.id.checkBox1);
        btnAction = findViewById(R.id.btnAction);
        btnSave = findViewById(R.id.btnSave);
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

    }



    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                confirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmationDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(TransferInBatchActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(TransferInBatchActivity.this);
        }
        builder.setTitle("Alert")
                .setMessage("Are you sure you want to exit the screen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        refreshGrnData();
                        finish();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onBackPressed() {
        confirmationDialog();
    }
}
