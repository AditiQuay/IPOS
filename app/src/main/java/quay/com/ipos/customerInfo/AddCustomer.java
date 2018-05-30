package quay.com.ipos.customerInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.base.RunTimePermissionActivity;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 5/29/2018.
 */

public class AddCustomer extends RunTimePermissionActivity implements InitInterface {
    private Toolbar toolbar;
    private TextView textViewMadatory, textViewHeading, textViewTitle, textViewGender, textViewMaritalStatus;
    private TextInputEditText tieFirstName, tieLastName, tieDOB;
    private Spinner genderSpinner, titleSpinner, maritalStatusSpinner;
    String[] nameTitle = {"Mr.", "Mrs.", "Miss."};
    String[] genderTitle = {"Male", "Female"};
    String[] martialStatus = {"Unmarried", "Married"};
    String[] city = {"Delhi", "Gurgaon", "Noida"};
    String[] state = {"Delhi", "Haryana", "Rajasthan", "Pubjab"};
    String[] country = {"India", "US", "China"};

    //ContactInformation
    private TextView textViewContactHeading, textViewContactCountry, textViewContactState, textViewContactCity;
    private TextInputEditText tieContactPinCode, tieContactAddress, tieContactMobileNumSecondary, tieContactMobileNumPrimary, tieContactEmail2, tieContactEmail1;
    private Spinner countryContactSpinner, stateContactSpinner, cityContactSpinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        textViewMadatory = findViewById(R.id.textViewMadatory);
        textViewHeading = findViewById(R.id.textViewHeading);
        textViewTitle = findViewById(R.id.textViewTitle);
        titleSpinner = findViewById(R.id.titleSpinner);
        textViewGender = findViewById(R.id.textViewGender);
        tieFirstName = findViewById(R.id.tieFirstName);
        tieLastName = findViewById(R.id.tieLastName);
        tieDOB = findViewById(R.id.tieDOB);
        genderSpinner = findViewById(R.id.genderSpinner);
        maritalStatusSpinner = findViewById(R.id.maritalStatusSpinner);
        textViewMaritalStatus = findViewById(R.id.textViewMaritalStatus);

        //Contact info
        textViewContactHeading = findViewById(R.id.textViewContactHeading);
        textViewContactCountry = findViewById(R.id.textViewContactCountry);
        textViewContactState = findViewById(R.id.textViewContactState);
        textViewContactCity = findViewById(R.id.textViewContactCity);
        tieContactPinCode = findViewById(R.id.tieContactPinCode);
        tieContactAddress = findViewById(R.id.tieContactAddress);
        tieContactMobileNumSecondary = findViewById(R.id.tieContactMobileNumSecondary);
        tieContactMobileNumPrimary = findViewById(R.id.tieContactMobileNumPrimary);
        tieContactEmail2 = findViewById(R.id.tieContactEmail2);
        tieContactEmail1 = findViewById(R.id.tieContactEmail1);
        countryContactSpinner = findViewById(R.id.countryContactSpinner);
        stateContactSpinner = findViewById(R.id.stateContactSpinner);
        cityContactSpinner = findViewById(R.id.cityContactSpinner);


    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("Add Customer");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter nameHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nameTitle);
        nameHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(nameHeading);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter genderHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderTitle);
        genderHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderHeading);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter maritalHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, martialStatus);
        maritalHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maritalStatusSpinner.setAdapter(maritalHeading);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter cityHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, city);
        cityHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityContactSpinner.setAdapter(cityHeading);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter stateHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, state);
        stateHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateContactSpinner.setAdapter(stateHeading);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter countryHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country);
        countryHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryContactSpinner.setAdapter(countryHeading);
    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewMadatory, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(toolbar, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewTitle, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(titleSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewGender, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieFirstName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieLastName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieDOB, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(genderSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(maritalStatusSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewMaritalStatus, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


        FontUtil.applyTypeface(textViewContactHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewContactCountry, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewContactState, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewContactCity, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieContactPinCode, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieContactAddress, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieContactMobileNumSecondary, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieContactMobileNumPrimary, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieContactEmail2, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieContactEmail1, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(countryContactSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(stateContactSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(cityContactSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }
}
