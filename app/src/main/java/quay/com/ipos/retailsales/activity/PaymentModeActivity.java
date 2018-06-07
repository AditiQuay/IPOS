package quay.com.ipos.retailsales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.utility.Constants;

/**
 * Created by aditi.bhuranda on 30-04-2018.
 */

public class PaymentModeActivity extends AppCompatActivity {
    private ImageView btnBack,imvUserAdd,imvBilling;
    private TextView tvPay;
    private String totalAmount;
    private Menu menu1;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_payment_mode);


        findViewbyId();
        getIntentValues();
        spnCardType();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu1 = menu;
        MenuItem menu12 = menu1.findItem(R.id.action_help);
        View actionView = MenuItemCompat.getActionView(menu12);

        TextView cart_badge = actionView.findViewById(R.id.cart_badge);
//        cart_badge.setText(count + "");
        return true;
    }


    private void getIntentValues(){
        Intent intent=getIntent();
        if (intent!=null){
            totalAmount=intent.getStringExtra(Constants.TOTAL_AMOUNT);

        }
        tvPay.setText(getResources().getString(R.string.Rs)+" "+totalAmount);
    }

    private void findViewbyId() {
        tvPay=findViewById(R.id.tvPay);
        btnBack=findViewById(R.id.btnBack);
        imvUserAdd=findViewById(R.id.imvUserAdd);
        imvBilling=findViewById(R.id.imvBilling);
        imvUserAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(PaymentModeActivity.this, CustomerInfoActivity.class);
                startActivity(mIntent);
            }
        });
        imvBilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent();
                setResult(Constants.ACT_PAYMENT_NEW_BILLING,mIntent);
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void spnCardType() {

        Spinner spinner = (Spinner) findViewById(R.id.spnCardType);

        // Spinner Drop down elements
        ArrayList<String> categories = new ArrayList<String>();
        categories.add("Master Card");
        categories.add("AMEX");
        categories.add("Maestro Card");
        categories.add("RuPay");
        categories.add("VISA");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }
}
