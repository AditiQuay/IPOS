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
    private TextView textViewMadatory, textViewPersonalHeading, textViewTitle, textViewGender, textViewMaritalStatus;
    private TextInputEditText tieFirstName, tieLastName, tieDOB;
    private Spinner genderSpinner, titleSpinner, maritalStatusSpinner;
    String[] nameTitle = {"Mr.", "Mrs.", "Miss."};
    String[] genderTitle = {"Male", "Female"};
    String[] martialStatus = {"Unmarried", "Married"};
    String[] city = {"Delhi", "Gurgaon", "Noida"};
    String[] state = {"Delhi", "Haryana", "Rajasthan", "Pubjab"};
    String[] country = {"India", "US", "China"};

    //ContactInformation
    private TextView textViewContactHeading, textViewCountry, textViewState, textViewCity;
    private TextInputEditText tiePinCode, tieAddress, tieMobileNumSecondary, tieMobileNumPrimary, tieEmail2, tieEmail1;
    private Spinner countrySpinner, stateSpinner, citySpinner;

    //Professional information
    private TextView textViewProfessionalHeading,textViewTitleDesignation,textViewCompanyTitle,textViewCustomerType,textViewRelationShip;
    private Spinner designationSpinner,companySpinner,customerTypeSpinner,relationShipSpinner;
    private TextInputEditText tieGstin;


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
        textViewPersonalHeading = findViewById(R.id.textViewPersonalHeading);
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
        textViewCountry = findViewById(R.id.textViewCountry);
        textViewState = findViewById(R.id.textViewState);
        textViewCity = findViewById(R.id.textViewCity);
        tiePinCode = findViewById(R.id.tiePinCode);
        tieAddress = findViewById(R.id.tieAddress);
        tieMobileNumSecondary = findViewById(R.id.tieMobileNumSecondary);
        tieMobileNumPrimary = findViewById(R.id.tieMobileNumPrimary);
        tieEmail2 = findViewById(R.id.tieEmail2);
        tieEmail1 = findViewById(R.id.tieEmail1);
        countrySpinner = findViewById(R.id.countrySpinner);
        stateSpinner = findViewById(R.id.stateSpinner);
        citySpinner = findViewById(R.id.citySpinner);

        //Professional info
        textViewProfessionalHeading = findViewById(R.id.textViewProfessionalHeading);
        textViewTitleDesignation = findViewById(R.id.textViewTitleDesignation);
        textViewCompanyTitle = findViewById(R.id.textViewCompanyTitle);
        textViewCustomerType = findViewById(R.id.textViewCustomerType);
        textViewRelationShip = findViewById(R.id.textViewRelationShip);
        designationSpinner = findViewById(R.id.designationSpinner);
        companySpinner = findViewById(R.id.companySpinner);
        customerTypeSpinner = findViewById(R.id.customerTypeSpinner);
        relationShipSpinner = findViewById(R.id.relationShipSpinner);
        tieGstin = findViewById(R.id.tieGstin);


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
        citySpinner.setAdapter(cityHeading);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter stateHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, state);
        stateHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateHeading);

        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter countryHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country);
        countryHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryHeading);
    }

    @Override
    public void applyTypeFace() {
        //Personal
        FontUtil.applyTypeface(textViewMadatory, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(toolbar, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewPersonalHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewTitle, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(titleSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewGender, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieFirstName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieLastName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieDOB, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(genderSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(maritalStatusSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewMaritalStatus, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


        //Contact
        FontUtil.applyTypeface(textViewContactHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewCountry, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewState, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewCity, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tiePinCode, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieAddress, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieMobileNumSecondary, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieMobileNumPrimary, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieEmail2, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieEmail1, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(countrySpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(stateSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(citySpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


        //Professional

        FontUtil.applyTypeface(textViewProfessionalHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewTitleDesignation, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewCompanyTitle, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewCustomerType, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewRelationShip, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(designationSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(companySpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(customerTypeSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(relationShipSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieGstin, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));



    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }
}
