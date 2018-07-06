package quay.com.ipos.inventoryTrasfer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import quay.com.ipos.R;
import quay.com.ipos.listeners.InitInterface;

/**
 * Created by niraj.kumar on 7/6/2018.
 */

public class TransferStepsActivity extends AppCompatActivity implements InitInterface{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_transfer_steps_activity);
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
