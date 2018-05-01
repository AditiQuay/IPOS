package quay.com.ipos.retailsales.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.utility.Constants;

/**
 * Created by ankush.bansal on 30-04-2018.
 */

public class PaymentModeActivity extends AppCompatActivity {
    private ImageView btnBack;
    private TextView tvPay;
    private String totalAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_payment_mode);


        findViewbyId();
        getIntentValues();
        spnCardType();
    }

    private void getIntentValues(){
        Intent intent=getIntent();
        if (intent!=null){
            totalAmount=intent.getStringExtra(Constants.TOTAL_AMOUNT);

        }
        tvPay.setText(getResources().getString(R.string.Rs)+" "+totalAmount);
    }

    private void findViewbyId() {
        tvPay=(TextView)findViewById(R.id.tvPay);
        btnBack=(ImageView)findViewById(R.id.btnBack);
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
        categories.add("VISA Card");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }
}
