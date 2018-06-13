package quay.com.ipos.inventory;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import quay.com.ipos.R;

/**
 * Created by niraj.kumar on 6/12/2018.
 */

public class InventoryProduct extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_product_details);
    }
}
