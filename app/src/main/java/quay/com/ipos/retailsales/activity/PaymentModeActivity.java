package quay.com.ipos.retailsales.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

import quay.com.ipos.R;

/**
 * Created by ankush.bansal on 30-04-2018.
 */

public class PaymentModeActivity extends AppCompatActivity {
    private ImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_payment_mode);

        findViewbyId();

        spnCardType();
    }

    private void findViewbyId() {
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
