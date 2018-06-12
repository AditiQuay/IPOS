package quay.com.ipos.partnerConnect.kyc;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 6/11/2018.
 */

public class KYCViewAll extends AppCompatActivity implements InitInterface {
    private Context mContext;
    private static final String TAG = KYCViewAll.class.getSimpleName();
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_view_all);
        mContext = KYCViewAll.this;
    }

    @Override
    public void findViewById() {

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
