package quay.com.ipos.customerInfo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 4/30/2018.
 */

public class CustomerInfoActivity extends AppCompatActivity implements InitInterface {
    private Toolbar toolbarCustomerInfo;
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_info_activity);
        mContext = CustomerInfoActivity.this;
        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();
    }

    @Override
    public void findViewById() {
        toolbarCustomerInfo = findViewById(R.id.toolbarCustomerInfo);
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbarCustomerInfo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbarCustomerInfo.setTitle(getResources().getString(R.string.toolbar_title_customer_screen));
        toolbarCustomerInfo.setTitleTextColor(getResources().getColor(R.color.white));

    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(toolbarCustomerInfo,FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }
}
