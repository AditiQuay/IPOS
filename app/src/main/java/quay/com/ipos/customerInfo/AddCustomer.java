package quay.com.ipos.customerInfo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.base.RunTimePermissionActivity;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 5/29/2018.
 */

public class AddCustomer extends RunTimePermissionActivity implements InitInterface, AdapterView.OnItemSelectedListener {
    private Toolbar toolbar;
    private TextView textViewMadatory, textViewPersonalHeading, textViewTitle, textViewGender, textViewMaritalStatus, textViewSpouseHeading, textViewChildHeading, textViewChild;
    private TextInputEditText tieFirstName, tieLastName, tieDOB, tieSpouseFirstName, tieSpouseLastName, tieSpouseDOB;
    private Spinner genderSpinner, titleSpinner, maritalStatusSpinner, childSpinner;
    private LinearLayout lLayoutChild, lLayoutSpouse;
    String[] nameTitle = {"Mr.", "Mrs.", "Miss."};
    String[] genderTitle = {"Male", "Female"};
    String[] martialStatus = {"Unmarried", "Married"};
    String[] child = {"No", "Yes"};
    String[] city = {"Delhi", "Gurgaon", "Noida"};
    String[] state = {"Delhi", "Haryana", "Rajasthan", "Pubjab"};
    String[] country = {"India", "US", "China"};
    String[] designation = {"Manager", "Project Lead", "Mobile Developer", "Backend Developer"};
    String[] company = {"Quay Intech pvt ltd", "Crystal crop ltd"};
    String[] customerType = {"Whole seller", "Retailer"};
    String[] relationship = {"Dummy", "Dummy"};

    //ContactInformation
    private TextView textViewContactHeading, textViewCountry, textViewState, textViewCity;
    private TextInputEditText tiePinCode, tieAddress, tieMobileNumSecondary, tieMobileNumPrimary, tieEmail2, tieEmail1;
    private Spinner countrySpinner, stateSpinner, citySpinner;

    //Professional information
    private TextView textViewProfessionalHeading, textViewTitleDesignation, textViewCompanyTitle, textViewCustomerType, textViewRelationShip;
    private Spinner designationSpinner, companySpinner, customerTypeSpinner, relationShipSpinner;
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
        lLayoutSpouse = findViewById(R.id.lLayoutSpouse);
        lLayoutChild = findViewById(R.id.lLayoutChild);

        textViewSpouseHeading = findViewById(R.id.textViewSpouseHeading);
        tieSpouseFirstName = findViewById(R.id.tieSpouseFirstName);
        tieSpouseLastName = findViewById(R.id.tieSpouseLastName);
        childSpinner = findViewById(R.id.childSpinner);
        tieSpouseDOB = findViewById(R.id.tieSpouseDOB);
        textViewChildHeading = findViewById(R.id.textViewChildHeading);
        textViewChild = findViewById(R.id.textViewChild);

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

        //Creating the ArrayAdapter instance having the name title list
        ArrayAdapter nameHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, nameTitle);
        nameHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(nameHeading);

        //Creating the ArrayAdapter instance having the gender name list
        ArrayAdapter genderHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, genderTitle);
        genderHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderHeading);

        //Creating the ArrayAdapter instance having the marital status list
        ArrayAdapter maritalHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, martialStatus);
        maritalHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maritalStatusSpinner.setAdapter(maritalHeading);
        maritalStatusSpinner.setOnItemSelectedListener(this);

        //Creating the ArrayAdapter instance having the marital status list
        ArrayAdapter childHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, child);
        childHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        childSpinner.setAdapter(childHeading);
        childSpinner.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the city name list
        ArrayAdapter cityHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, city);
        cityHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityHeading);

        //Creating the ArrayAdapter instance having the state name list
        ArrayAdapter stateHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, state);
        stateHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateHeading);

        //Creating the ArrayAdapter instance having the country name list
        ArrayAdapter countryHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, country);
        countryHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryHeading);

        //Creating the ArrayAdapter instance having the designation name list
        ArrayAdapter designationHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, designation);
        designationHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        designationSpinner.setAdapter(designationHeading);

        //Creating the ArrayAdapter instance having the company name list
        ArrayAdapter companyHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, company);
        companyHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companySpinner.setAdapter(companyHeading);

        //Creating the ArrayAdapter instance having the customer Type name list
        ArrayAdapter customerTypeHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, customerType);
        customerTypeHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerTypeSpinner.setAdapter(customerTypeHeading);

        //Creating the ArrayAdapter instance having the relationship name list
        ArrayAdapter relationShipHeading = new ArrayAdapter(this, android.R.layout.simple_spinner_item, relationship);
        relationShipHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationShipSpinner.setAdapter(relationShipHeading);
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
        FontUtil.applyTypeface(textViewSpouseHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieSpouseLastName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieSpouseFirstName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(childSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieSpouseDOB, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewChildHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewChild, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        String text = parent.getSelectedItem().toString();
        if (spinner.getId() == R.id.maritalStatusSpinner) {
            if (text.equalsIgnoreCase("Married")) {
                lLayoutSpouse.setVisibility(View.VISIBLE);
                lLayoutChild.setVisibility(View.GONE);
            } else {
                lLayoutSpouse.setVisibility(View.GONE);
            }
        } else if (spinner.getId() == R.id.childSpinner) {
            if (text.equalsIgnoreCase("Yes")) {
                lLayoutSpouse.setVisibility(View.VISIBLE);
                lLayoutChild.setVisibility(View.VISIBLE);
            } else {
                lLayoutChild.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
