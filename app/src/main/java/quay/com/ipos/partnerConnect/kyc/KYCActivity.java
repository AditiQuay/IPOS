package quay.com.ipos.partnerConnect.kyc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 6/11/2018.
 */

public class KYCActivity extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private Button btnViewAll;
    private TextView textViewNewCount, textViewInProcessCount, textViewVerifiedCount, textViewAllPartnersCount;
    private TextView textViewKycNumber, textViewKycPartner, textViewKycStatus, textViewKycPendingSince, textViewKycPendingTime, textViewBank, textViewDocuments;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_activity_main);
        mContext = KYCActivity.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        btnViewAll = findViewById(R.id.btnViewAll);
        textViewNewCount = findViewById(R.id.textViewNewCount);
        textViewInProcessCount = findViewById(R.id.textViewInProcessCount);
        textViewVerifiedCount = findViewById(R.id.textViewVerifiedCount);
        textViewAllPartnersCount = findViewById(R.id.textViewAllPartnersCount);


        //Status
        textViewKycNumber = findViewById(R.id.textViewKycNumber);
        textViewKycPartner = findViewById(R.id.textViewKycPartner);
        textViewKycStatus = findViewById(R.id.textViewKycStatus);
        textViewKycPendingSince = findViewById(R.id.textViewKycPendingSince);
        textViewKycPendingTime = findViewById(R.id.textViewKycPendingTime);
        textViewBank = findViewById(R.id.textViewBank);
        textViewDocuments = findViewById(R.id.textViewDocuments);

        btnViewAll.setOnClickListener(this);

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
        switch (v.getId()) {
            case R.id.BtnViewAll:
                Intent i = new Intent(mContext, KYCViewAll.class);
                startActivity(i);

            default:
                break;
        }
    }
}
