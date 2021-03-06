package quay.com.ipos.dayClosure.dayClosureActivity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.ui.CustomEditText;
import quay.com.ipos.ui.CustomTextView;

/**
 * Created by niraj.kumar on 7/13/2018.
 */

public class DayClosureSales extends AppCompatActivity implements InitInterface {
    private Toolbar toolbar;
    private CustomTextView toolbarTtile;
    private static final String TAG = DayClosureSales.class.getSimpleName();
    private Context mContext;


    //Cash Amount
    private CustomTextView tvOpeningBalanceAmount, tvCashSalesAmount, tvTotalAmount, tvDifferenceAmount;
    private CheckBox checkBoxBalance, checkBoxAgree;
    private CustomEditText tieBalanceInHand,tvBankDepositAmount;

    //Card Amount
    private CustomTextView tvTotalWalletSaleAmount, tvCardDifferenceAmount;
    private CheckBox checkBoxCardAgree;
    private CustomEditText tvConfirmSwipeMachineAmount;

    //Wallet Amount
    private CustomTextView tvTotalCardSaleAmount, tvWalletDifferenceAmount;
    private CheckBox checkBoxWalletAgree;
    private CustomEditText tvConfirmWalletSaleAmount;

    //Button
    private RelativeLayout rlBottom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day_colsure_sales_detail);
        mContext = DayClosureSales.this;

        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        toolbarTtile = findViewById(R.id.toolbarTtile);

        //Cash amount and check box
        tvOpeningBalanceAmount = findViewById(R.id.tvOpeningBalanceAmount);
        tvCashSalesAmount = findViewById(R.id.tvCashSalesAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tieBalanceInHand = findViewById(R.id.tieBalanceInHand);
        tvDifferenceAmount = findViewById(R.id.tvDifferenceAmount);
        tvBankDepositAmount = findViewById(R.id.tvBankDepositAmount);
        checkBoxAgree = findViewById(R.id.checkBoxAgree);

        //Card amount and check box
        tvTotalWalletSaleAmount = findViewById(R.id.tvTotalWalletSaleAmount);
        tvConfirmSwipeMachineAmount = findViewById(R.id.tvConfirmSwipeMachineAmount);
        tvCardDifferenceAmount = findViewById(R.id.tvCardDifferenceAmount);
        checkBoxCardAgree = findViewById(R.id.checkBoxCardAgree);

        //Wallet amount and check box
        tvTotalCardSaleAmount = findViewById(R.id.tvTotalCardSaleAmount);
        tvConfirmWalletSaleAmount = findViewById(R.id.tvConfirmWalletSaleAmount);
        tvWalletDifferenceAmount = findViewById(R.id.tvWalletDifferenceAmount);
        checkBoxWalletAgree = findViewById(R.id.checkBoxWalletAgree);

        //Submit btn
        rlBottom = findViewById(R.id.rlBottom);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        toolbarTtile.setText("Day Closure");

        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }
}
