package quay.com.ipos.customerInfo.customerAdd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.CustomerChildAdapter;
import quay.com.ipos.customerInfo.customerInfoModal.AddCustomerModel;
import quay.com.ipos.customerInfo.customerInfoModal.CityListModel;
import quay.com.ipos.customerInfo.customerInfoModal.CompanyListModel;
import quay.com.ipos.customerInfo.customerInfoModal.CountryListModel;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerSpinner;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerTypeListModel;
import quay.com.ipos.customerInfo.customerInfoModal.DesignationListModel;
import quay.com.ipos.customerInfo.customerInfoModal.RelationListModel;
import quay.com.ipos.customerInfo.customerInfoModal.StateListModel;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

/**
 * Created by niraj.kumar on 5/31/2018.
 */

public class CustomerAddFullFragment extends Fragment implements InitInterface, AdapterView.OnItemSelectedListener, ButtonListener, View.OnClickListener, DatePickerDialog.OnDateSetListener, ServiceTask.ServiceResultListener, TextWatcher {
    private static final String TAG = CustomerAddFullFragment.class.getSimpleName();
    private View main;
    private TextView textViewMadatory, textViewPersonalHeading, textViewSpouseHeading, textViewChildHeading, textViewChild;
    private TextInputLayout tilDOB;
    private TextInputEditText tieFirstName, tieLastName, tieDOB, tieSpouseFirstName, tieSpouseLastName, tieSpouseDOB;
    private MaterialSpinner genderSpinner, titleSpinner, maritalStatusSpinner, childSpinner;
    private LinearLayout lLayoutChild, lLayoutSpouse;
    private TextInputLayout tilFirstName, tilSpouseFirstName, tilSpouseLastName;
    String[] nameTitle = {"Mr.", "Mrs.", "Miss."};
    String[] genderTitle = {"Male", "Female"};
    String[] martialStatus = {"Unmarried", "Married"};
    String[] child = {"No", "Yes"};
    private ArrayList<AddCustomerModel.CustomerChildBean> childModels = new ArrayList<>();
    //ContactInformation
    private TextView textViewContactHeading;
    private TextInputEditText tiePinCode, tieAddress, tieMobileNumSecondary, tieMobileNumPrimary, tieChildDOB, tieEmail2, tieEmail1;
    private MaterialSpinner countrySpinner, stateSpinner, citySpinner;

    //Professional information
    private TextView textViewProfessionalHeading;
    private MaterialSpinner designationSpinner, companySpinner, customerTypeSpinner, relationShipSpinner, childGenderSpinner;
    private TextInputEditText tieGstin;
    private TextInputLayout tilChildDob;

    private RecyclerView recyclerViewChild;
    private CustomerChildAdapter customerChildAdapter;
    private Context mContext;
    private Button btnAddChild;
    private AwesomeValidation awesomeValidation;
    private Button btnFullFragmentCancel, btnFullFragmentSubmit;
    private DatabaseHandler dbHelper;
    private Calendar calendar;
    private DatePickerDialog datePickerDialog;
    private int Year, Month, Day;
    Date currentDateFormat = null;
    //declare boolean
    boolean clicked = false;
    boolean isSpouseDobClicked = false;
    private String cityName = "";
    private String stateCode = "";
    private String countryCode = "";
    private ProgressDialog m_Dialog;
    private String message;
    private TextInputLayout tilLastName, tilSpouseDob, tilEmail1, tilMobileNumPrimary;

    public CustomerAddFullFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.customer_add_full_fragment, container, false);
        mContext = getActivity();
        awesomeValidation = new AwesomeValidation(BASIC);
        dbHelper = new DatabaseHandler(mContext);

        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        return main;
    }

    @Override
    public void findViewById() {
        textViewMadatory = main.findViewById(R.id.textViewMadatory);
        textViewPersonalHeading = main.findViewById(R.id.textViewPersonalHeading);
        titleSpinner = main.findViewById(R.id.titleSpinner);
        tieFirstName = main.findViewById(R.id.tieFirstName);
        tilFirstName = main.findViewById(R.id.tilFirstName);
        tieLastName = main.findViewById(R.id.tieLastName);
        tilLastName = main.findViewById(R.id.tilLastName);
        tilDOB = main.findViewById(R.id.tilDOB);
        tieDOB = main.findViewById(R.id.tieDOB);
        tilSpouseDob = main.findViewById(R.id.tilSpouseDob);
        tilMobileNumPrimary = main.findViewById(R.id.tilMobileNumPrimary);
        tieChildDOB = main.findViewById(R.id.tieChildDOB);
        genderSpinner = main.findViewById(R.id.genderSpinner);
        maritalStatusSpinner = main.findViewById(R.id.maritalStatusSpinner);
        lLayoutSpouse = main.findViewById(R.id.lLayoutSpouse);
        lLayoutChild = main.findViewById(R.id.lLayoutChild);
        tilSpouseFirstName = main.findViewById(R.id.tilSpouseFirstName);
        tilSpouseLastName = main.findViewById(R.id.tilSpouseLastName);
        tilSpouseDob = main.findViewById(R.id.tilSpouseDob);
        tilChildDob = main.findViewById(R.id.tilChildDob);
        tilChildDob = main.findViewById(R.id.tilChildDob);
        tilEmail1 = main.findViewById(R.id.tilEmail1);
        textViewSpouseHeading = main.findViewById(R.id.textViewSpouseHeading);
        tieSpouseFirstName = main.findViewById(R.id.tieSpouseFirstName);
        tieSpouseLastName = main.findViewById(R.id.tieSpouseLastName);
        childSpinner = main.findViewById(R.id.childSpinner);
        tieSpouseDOB = main.findViewById(R.id.tieSpouseDOB);
        textViewChildHeading = main.findViewById(R.id.textViewChildHeading);
        childGenderSpinner = main.findViewById(R.id.childGenderSpinner);
        //Contact info
        textViewContactHeading = main.findViewById(R.id.textViewContactHeading);
        tiePinCode = main.findViewById(R.id.tiePinCode);
        tieAddress = main.findViewById(R.id.tieAddress);
        tieMobileNumSecondary = main.findViewById(R.id.tieMobileNumSecondary);
        tieMobileNumPrimary = main.findViewById(R.id.tieMobileNumPrimary);
        tieEmail2 = main.findViewById(R.id.tieEmail2);
        tieEmail1 = main.findViewById(R.id.tieEmail1);
        countrySpinner = main.findViewById(R.id.countrySpinner);
        stateSpinner = main.findViewById(R.id.stateSpinner);
        citySpinner = main.findViewById(R.id.citySpinner);

        //Professional info
        textViewProfessionalHeading = main.findViewById(R.id.textViewProfessionalHeading);
        designationSpinner = main.findViewById(R.id.designationSpinner);
        companySpinner = main.findViewById(R.id.companySpinner);
        customerTypeSpinner = main.findViewById(R.id.customerTypeSpinner);
        relationShipSpinner = main.findViewById(R.id.relationShipSpinner);
        tieGstin = main.findViewById(R.id.tieGstin);

        recyclerViewChild = main.findViewById(R.id.recyclerViewChild);
        btnAddChild = main.findViewById(R.id.btnAddChild);
        btnAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (childModels.size() <= 2) {
                    AddCustomerModel.CustomerChildBean model = new AddCustomerModel.CustomerChildBean();
                    model.setCustomerChildFirstName("");
                    model.setCustomerChildLastName("");
                    model.setCustomerChildDob("");
                    childModels.add(model);
                    customerChildAdapter.notifyDataSetChanged();

                }
            }
        });
        btnFullFragmentCancel = main.findViewById(R.id.btnFullFragmentCancel);
        btnFullFragmentSubmit = main.findViewById(R.id.btnFullFragmentSubmit);

        btnFullFragmentCancel.setOnClickListener(this);
        btnFullFragmentSubmit.setOnClickListener(this);
        tieDOB.setClickable(true);
        tieDOB.setOnClickListener(this);
        tieSpouseDOB.setOnClickListener(this);

        tieFirstName.addTextChangedListener(this);
        tieLastName.addTextChangedListener(this);
        tieSpouseDOB.addTextChangedListener(this);
        tieSpouseFirstName.addTextChangedListener(this);
        tieSpouseLastName.addTextChangedListener(this);
        tieSpouseDOB.addTextChangedListener(this);
        tieEmail1.addTextChangedListener(this);
        tieMobileNumPrimary.addTextChangedListener(this);
    }

    @Override
    public void applyInitValues() {
        try {

            ArrayList<CustomerSpinner> customerSpinner = dbHelper.getCustomerSpinner();

            tilDOB.setError(null);
            tilDOB.setErrorEnabled(false);


            //Creating the ArrayAdapter instance having the name title list
            ArrayAdapter nameHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, nameTitle);
            nameHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            titleSpinner.setAdapter(nameHeading);

            //Creating the ArrayAdapter instance having the gender name list
            ArrayAdapter genderHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, genderTitle);
            genderHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genderSpinner.setAdapter(genderHeading);

            //Creating the ArrayAdapter instance having the marital status list
            ArrayAdapter maritalHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, martialStatus);
            maritalHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            maritalStatusSpinner.setAdapter(maritalHeading);
            maritalStatusSpinner.setOnItemSelectedListener(this);

            //Creating the ArrayAdapter instance having the marital status list
            ArrayAdapter childHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, child);
            childHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            childSpinner.setAdapter(childHeading);
            childSpinner.setOnItemSelectedListener(this);

            //Creating the ArrayAdapter instance having the city name list
            if (customerSpinner != null && customerSpinner.size() > 0) {

                CityListModel[] cityListModels = new Gson().fromJson(customerSpinner.get(0).getCityList(), CityListModel[].class);
                String[] cityArray = new String[cityListModels.length];
                for (int i = 0; i < cityListModels.length; i++) {
                    cityArray[i] = cityListModels[i].getCityName();
                }
                ArrayAdapter cityHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, cityArray);
                cityHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                citySpinner.setAdapter(cityHeading);
                citySpinner.setOnItemSelectedListener(this);
            }


            //Creating the ArrayAdapter instance having the state name list
            if (customerSpinner != null && customerSpinner.size() > 0) {
                StateListModel[] stateListModels = new Gson().fromJson(customerSpinner.get(0).getStateList(), StateListModel[].class);
                String[] stateArray = new String[stateListModels.length];
                for (int i = 0; i < stateListModels.length; i++) {
                    stateArray[i] = stateListModels[i].getStateName();
                }
                ArrayAdapter stateHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, stateArray);
                stateHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                stateSpinner.setAdapter(stateHeading);
                stateSpinner.setOnItemSelectedListener(this);
            }


            //Creating the ArrayAdapter instance having the country name list
            if (customerSpinner != null && customerSpinner.size() > 0) {
                CountryListModel[] countryListModels = new Gson().fromJson(customerSpinner.get(0).getCountryList(), CountryListModel[].class);
                String[] countryArray = new String[countryListModels.length];
                for (int i = 0; i < countryListModels.length; i++) {
                    countryArray[i] = countryListModels[i].getCountryName();
                }
                ArrayAdapter countryHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, countryArray);
                countryHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                countrySpinner.setAdapter(countryHeading);
                countrySpinner.setOnItemSelectedListener(this);

            }

            //Creating the ArrayAdapter instance having the designation name list
            if (customerSpinner != null && customerSpinner.size() > 0) {
                DesignationListModel[] designationListModels = new Gson().fromJson(customerSpinner.get(0).getDesignationList(), DesignationListModel[].class);
                String[] designationArray = new String[designationListModels.length];
                for (int i = 0; i < designationListModels.length; i++) {
                    designationArray[i] = designationListModels[i].getDesignationName();
                }
                ArrayAdapter designationHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, designationArray);
                designationHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                designationSpinner.setAdapter(designationHeading);
                designationSpinner.setOnItemSelectedListener(this);

            }

            //Creating the ArrayAdapter instance having the company name list
            if (customerSpinner != null && customerSpinner.size() > 0) {

                CompanyListModel[] companyListModels = new Gson().fromJson(customerSpinner.get(0).getCompanyList(), CompanyListModel[].class);
                String[] companyArray = new String[companyListModels.length];
                for (int i = 0; i < companyListModels.length; i++) {
                    companyArray[i] = companyListModels[i].getCompaneyName();
                }
                ArrayAdapter companyHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, companyArray);
                companyHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                companySpinner.setAdapter(companyHeading);
                companySpinner.setOnItemSelectedListener(this);
            }

            //Creating the ArrayAdapter instance having the customer Type name list
            if (customerSpinner != null && customerSpinner.size() > 0) {

                CustomerTypeListModel[] customerTypeListModels = new Gson().fromJson(customerSpinner.get(0).getCustomerTypeList(), CustomerTypeListModel[].class);
                String[] customerTypeArray = new String[customerTypeListModels.length];
                for (int i = 0; i < customerTypeListModels.length; i++) {
                    customerTypeArray[i] = customerTypeListModels[i].getCustomerType();
                }
                //Creating the ArrayAdapter instance having the customer Type name list
                ArrayAdapter customerTypeHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, customerTypeArray);
                customerTypeHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                customerTypeSpinner.setAdapter(customerTypeHeading);
                companySpinner.setOnItemSelectedListener(this);
            }

            //Creating the ArrayAdapter instance having the Relationship list name list
            if (customerSpinner != null && customerSpinner.size() > 0) {
                RelationListModel[] relationListModels = new Gson().fromJson(customerSpinner.get(0).getRelationshipList(), RelationListModel[].class);
                String[] relationShipArray = new String[relationListModels.length];
                for (int i = 0; i < relationListModels.length; i++) {
                    relationShipArray[i] = relationListModels[i].getRelationshipName();
                }
                ArrayAdapter relationShipHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, relationShipArray);
                relationShipHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                relationShipSpinner.setAdapter(relationShipHeading);

            }


            recyclerViewChild.setHasFixedSize(true);
            recyclerViewChild.setLayoutManager(new LinearLayoutManager(mContext));
            customerChildAdapter = new CustomerChildAdapter(mContext, childModels, this);
            recyclerViewChild.setAdapter(customerChildAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyTypeFace() {
        //Personal
        FontUtil.applyTypeface(textViewMadatory, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewPersonalHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(titleSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieFirstName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieLastName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieDOB, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(genderSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(maritalStatusSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewSpouseHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieSpouseLastName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieSpouseFirstName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(childSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieSpouseDOB, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewChildHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewChild, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));


        //Contact
        FontUtil.applyTypeface(textViewContactHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
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
        try {


            MaterialSpinner materialSpinner = (MaterialSpinner) parent;
            String selectedSpinner = String.valueOf(materialSpinner.getSelectedItem());
            if (materialSpinner.getId() == R.id.maritalStatusSpinner) {
                if (selectedSpinner.equalsIgnoreCase("Married")) {
                    lLayoutSpouse.setVisibility(View.VISIBLE);
                    lLayoutChild.setVisibility(View.GONE);
                } else {
                    lLayoutSpouse.setVisibility(View.GONE);
                }if (maritalStatusSpinner.getSelectedItem().toString().equalsIgnoreCase("Unmarried")){
                    tilSpouseDob.setErrorEnabled(false);
                    tilSpouseFirstName.setErrorEnabled(false);
                    tilSpouseLastName.setErrorEnabled(false);
                    childSpinner.setEnableErrorLabel(false);

                    tilSpouseDob.setError(null);
                    tilSpouseFirstName.setError(null);
                    tilSpouseLastName.setError(null);
                    childSpinner.setError(null);
                }
            } else if (materialSpinner.getId() == R.id.childSpinner) {
                if (selectedSpinner.equalsIgnoreCase("Yes")) {
                    lLayoutSpouse.setVisibility(View.VISIBLE);
                    lLayoutChild.setVisibility(View.VISIBLE);
                    childModels = new ArrayList<>();
                    AddCustomerModel.CustomerChildBean model = new AddCustomerModel.CustomerChildBean();
                    model.setCustomerChildFirstName("");
                    model.setCustomerChildLastName("");
                    model.setCustomerChildDob("");
                    childModels.add(model);

                    customerChildAdapter = new CustomerChildAdapter(mContext, childModels, this);
                    recyclerViewChild.setAdapter(customerChildAdapter);
                } else {
                    lLayoutChild.setVisibility(View.GONE);
                }
            } else if (materialSpinner.getId() == R.id.citySpinner) {

                ArrayList<CustomerSpinner> customerSpinner = dbHelper.getCustomerSpinner();
                CityListModel[] cityListModels = new Gson().fromJson(customerSpinner.get(0).getCityList(), CityListModel[].class);
                cityName = cityListModels[position].getCityName();

            } else if (materialSpinner.getId() == R.id.stateSpinner) {

                ArrayList<CustomerSpinner> customerSpinner = dbHelper.getCustomerSpinner();
                StateListModel[] stateListModel = new Gson().fromJson(customerSpinner.get(0).getStateList(), StateListModel[].class);
                stateCode = stateListModel[position].getStateCode();


            } else if (materialSpinner.getId() == R.id.countrySpinner) {

                ArrayList<CustomerSpinner> customerSpinner = dbHelper.getCustomerSpinner();
                CountryListModel[] countryListModel = new Gson().fromJson(customerSpinner.get(0).getCountryList(), CountryListModel[].class);
                countryCode = countryListModel[position].getCountryCode();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAdd(int position, String firstName, String lastName, String childGender, String childDOB) {
        AddCustomerModel.CustomerChildBean model = new AddCustomerModel.CustomerChildBean();
        model.setCustomerChildFirstName(firstName);
        model.setCustomerChildLastName(lastName);
        model.setCustomerChildGender(childGender);
        model.setCustomerChildDob(childDOB);
        childModels.set(position, model);

    }

    @Override
    public void onPartnerAdd(int position, String distributerType, String companyName, String cinNumber, String panNumber, String contactPerson, String contactPosition,String partnerState,String partnerCity,String partnerPinCode,String partnerZone) {

    }

    @Override
    public void onContactAdd(int position, String role, String name, String primaryMobileNum, String secondaryMobileNum) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                break;
            case R.id.btnFullFragmentSubmit:



                String title = String.valueOf(titleSpinner.getSelectedItem());
                String gender = String.valueOf(genderSpinner.getSelectedItem());
                String maritalStatus = String.valueOf(maritalStatusSpinner.getSelectedItem());
                String childStatus = String.valueOf(childSpinner.getSelectedItem());
                String designation = String.valueOf(designationSpinner.getSelectedItem());
                String company = String.valueOf(companySpinner.getSelectedItem());

                boolean isFail = false;
                if (title.equalsIgnoreCase("null")) {
                    isFail = true;
                    titleSpinner.setEnableErrorLabel(true);
                    titleSpinner.setError(getResources().getString(R.string.invalid_title));
                }
                if (TextUtils.isEmpty(tieFirstName.getText().toString())) {
                    isFail = true;
                    tilFirstName.setErrorEnabled(true);
                    tilFirstName.setError(getResources().getString(R.string.invalid_f_name));
                }
                if (TextUtils.isEmpty(tieLastName.getText().toString())) {
                    isFail = true;
                    tilLastName.setErrorEnabled(true);
                    tilLastName.setError(getResources().getString(R.string.invalid_l_name));

                }
                if (gender.equalsIgnoreCase("null")) {
                    isFail = true;

                    genderSpinner.setEnableErrorLabel(true);
                    genderSpinner.setError(getResources().getString(R.string.invalid_gender));
                }
                if (TextUtils.isEmpty(tieDOB.getText().toString())) {
                    isFail = true;
                    tilDOB.setErrorEnabled(true);
                    tilDOB.setError(getResources().getString(R.string.dateerror));
                }
                if (maritalStatus.equalsIgnoreCase("null")) {
                    isFail = true;
                    maritalStatusSpinner.setEnableErrorLabel(true);
                    maritalStatusSpinner.setError(getResources().getString(R.string.invalid_maritalStatus));
                }

                if (maritalStatus.equalsIgnoreCase("Married")) {
                    if (TextUtils.isEmpty(tieSpouseFirstName.getText().toString())) {
                        isFail = true;
                        tilSpouseFirstName.setErrorEnabled(true);
                        tilSpouseFirstName.setError(getResources().getString(R.string.invalid_spouse_f_name));
                    }
                    if (TextUtils.isEmpty(tieSpouseLastName.getText().toString())) {
                        isFail = true;
                        tilSpouseLastName.setErrorEnabled(true);
                        tilSpouseLastName.setError(getResources().getString(R.string.invalid_spouse_l_name));
                    }
                    if (TextUtils.isEmpty(tieSpouseDOB.getText().toString())) {
                        isFail = true;
                        tilSpouseDob.setErrorEnabled(true);
                        tilSpouseDob.setError(getResources().getString(R.string.dateerror));
                    }if (childStatus.equalsIgnoreCase("null")){
                        isFail = true;
                        childSpinner.setError("Please select child");
                        childSpinner.setEnableErrorLabel(true);
                    }

                }
                if (maritalStatus.equalsIgnoreCase("Unmarried")) {
                    tilSpouseDob.setErrorEnabled(false);
                    tilSpouseFirstName.setErrorEnabled(false);
                    tilSpouseLastName.setErrorEnabled(false);
                    childSpinner.setEnableErrorLabel(false);

                    tilSpouseDob.setError(null);
                    tilSpouseFirstName.setError(null);
                    tilSpouseLastName.setError(null);
                    childSpinner.setError(null);
                }
                if (TextUtils.isEmpty(tieEmail1.getText().toString())) {
                    isFail = true;
                    tilEmail1.setErrorEnabled(true);
                    tilEmail1.setError(getResources().getString(R.string.invalid_email));
                }
                if (TextUtils.isEmpty(tieMobileNumPrimary.getText().toString())) {
                    isFail = true;
                    tilMobileNumPrimary.setErrorEnabled(true);
                    tilMobileNumPrimary.setError(getResources().getString(R.string.invalid_phone));

                }
                if (designation.equalsIgnoreCase("null")) {
                    isFail = true;
                    designationSpinner.setEnableErrorLabel(true);
                    designationSpinner.setError(getResources().getText(R.string.invalid_designation));
                }
                if (company.equalsIgnoreCase("null")) {
                    isFail = true;
                    companySpinner.setEnableErrorLabel(true);
                    companySpinner.setError(getResources().getString(R.string.invalid_company));
                }



                if (!isFail) {

                    titleSpinner.setEnableErrorLabel(false);
                    tilFirstName.setErrorEnabled(false);
                    tilLastName.setErrorEnabled(false);
                    tilDOB.setErrorEnabled(false);
                    tilSpouseFirstName.setErrorEnabled(false);
                    tilSpouseLastName.setErrorEnabled(false);
                    tilSpouseDob.setErrorEnabled(false);
                    genderSpinner.setEnableErrorLabel(false);
                    tilDOB.setErrorEnabled(false);

                    maritalStatusSpinner.setEnableErrorLabel(false);
                    designationSpinner.setEnableErrorLabel(false);
                    companySpinner.setEnableErrorLabel(false);
                    View itemView = recyclerViewChild.getLayoutManager().findContainingItemView(v);
                    TextInputEditText child = itemView.findViewById(R.id.tieChildFirstName);


                    sendCustomerData();

                }

                break;
            case R.id.tieDOB:
                //change boolean value
                clicked = true;
                datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                break;
            case R.id.tieSpouseDOB:
                isSpouseDobClicked = true;
                datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            default:
                break;

        }
    }

    private void sendCustomerData() {
        String dob = tieDOB.getText().toString();
        String title = String.valueOf(titleSpinner.getSelectedItem());
        String firstName = tieFirstName.getText().toString().trim();
        String lastName = tieLastName.getText().toString().trim();
        String gender = String.valueOf(genderSpinner.getSelectedItem());
        String maritalStatus = String.valueOf(maritalStatusSpinner.getSelectedItem());
        String spouseFirstName = tieSpouseFirstName.getText().toString();
        String spouseLastName = tieSpouseLastName.getText().toString();
        String spouseDob = tieSpouseDOB.getText().toString();
        String childStatus = String.valueOf(childSpinner.getSelectedItem());
        String email1 = tieEmail1.getText().toString().trim();
        String email2 = tieEmail2.getText().toString().trim();
        String mobileNumberPrimary = tieMobileNumPrimary.getText().toString().trim();
        String mobileNumberSecondary = tieMobileNumSecondary.getText().toString().trim();
        String address = tieAddress.getText().toString().trim();
        String pin = tiePinCode.getText().toString().trim();
        String designation = String.valueOf(designationSpinner.getSelectedItem());
        String company = String.valueOf(companySpinner.getSelectedItem());
        String gstn = tieGstin.getText().toString().trim();
        String customerType = String.valueOf(customerTypeSpinner.getSelectedItem());
        String relationShip = String.valueOf(relationShipSpinner.getSelectedItem());


        AddCustomerModel addCustomerModel = new AddCustomerModel();
        addCustomerModel.setLocalId(0);
        addCustomerModel.setCustomerID("");
        addCustomerModel.setCustomerCode("");

        if (title.equalsIgnoreCase("null")) {
            addCustomerModel.setCustomerTitle("");
        } else {
            addCustomerModel.setCustomerTitle(title);
        }

        if (TextUtils.isEmpty(firstName)) {
            addCustomerModel.setCustomerFirstName("");
        } else {
            addCustomerModel.setCustomerFirstName(firstName);
        }

        if (TextUtils.isEmpty(lastName)) {
            addCustomerModel.setCustomerLastName("");
        } else {
            addCustomerModel.setCustomerLastName(lastName);
        }
        addCustomerModel.setCustomerName("");

        if (gender.equalsIgnoreCase("null")) {
            addCustomerModel.setCustomerGender("");
        } else if (gender.equalsIgnoreCase("Male")) {
            addCustomerModel.setCustomerGender("M");

        } else if (gender.equalsIgnoreCase("Female")) {
            addCustomerModel.setCustomerGender("F");
        }

        if (Util.validateString(dob)) {
            addCustomerModel.setCustomerBday("");
        } else {
            addCustomerModel.setCustomerBday(dob);
        }

        if (maritalStatus.equalsIgnoreCase("null")) {
            addCustomerModel.setCustomerMaritalStatus("false");
        } else {
            addCustomerModel.setCustomerMaritalStatus(maritalStatus);
        }

        if (maritalStatus.equalsIgnoreCase("Married")) {
            addCustomerModel.setCustomerSpouseFirstName(spouseFirstName);
            addCustomerModel.setCustomerSpouseLastName(spouseLastName);
            addCustomerModel.setCustomerSpouseDob(spouseDob);
            if (childStatus.equalsIgnoreCase("Yes")) {
                addCustomerModel.setCustomerChild(childModels);
                addCustomerModel.setCustomerChildSatus("true");
            } else if (childStatus.equalsIgnoreCase("No")) {
                addCustomerModel.setCustomerChild(childModels);
                addCustomerModel.setCustomerStatus("false");
            }
        } else if (maritalStatus.equalsIgnoreCase("Unmarried")) {
            addCustomerModel.setCustomerSpouseFirstName("");
            addCustomerModel.setCustomerSpouseLastName("");
            addCustomerModel.setCustomerSpouseDob("");
            addCustomerModel.setCustomerChild(childModels);
        } else if (maritalStatus.equalsIgnoreCase("null")) {
            addCustomerModel.setCustomerChild(childModels);
            addCustomerModel.setCustomerSpouseFirstName("");
            addCustomerModel.setCustomerSpouseLastName("");
            addCustomerModel.setCustomerSpouseDob("");
            addCustomerModel.setCustomerChildSatus("false");
        }

        addCustomerModel.setCustomerEmail(email1);
        addCustomerModel.setCustomerEmail2(email2);
        addCustomerModel.setCustomerPhone(mobileNumberPrimary);
        addCustomerModel.setCustomerPhone2(mobileNumberSecondary);
        addCustomerModel.setCustomerAddress(address);
        addCustomerModel.setCustomerCity(cityName);
        addCustomerModel.setCustomerState(stateCode);
        addCustomerModel.setCustomerPin(pin);
        addCustomerModel.setCustomerCountry(countryCode);
        addCustomerModel.setCustomerPhone3("");
        addCustomerModel.setCustomerDom("");
        addCustomerModel.setCustomerStatus("");

        if (designation.equalsIgnoreCase("null")) {
            addCustomerModel.setCustomerDesignation("");
        } else {
            addCustomerModel.setCustomerDesignation(designation);
        }

        if (company.equalsIgnoreCase("null")) {
            addCustomerModel.setCustomerCompany("");
        } else {
            addCustomerModel.setCustomerCompany(company);
        }

        addCustomerModel.setCustoemrGstin(gstn);

        if (customerType.equalsIgnoreCase("null")) {
            addCustomerModel.setCustomerType("");
        } else {
            addCustomerModel.setCustomerType(customerType);
        }

        if (relationShip.equalsIgnoreCase("null")) {
            addCustomerModel.setCustomerRelationship("");
        } else {
            addCustomerModel.setCustomerRelationship(relationShip);
        }
        addCustomerModel.setCfactor("");
        addCustomerModel.setRegisteredBusinessPlaceID(String.valueOf(0));
        addCustomerModel.setIsSync(1);
        List<AddCustomerModel> list = new ArrayList<>();
        list.add(addCustomerModel);
        Log.e(TAG, "Arraylist***" + list);

        String accessToken = SharedPrefUtil.getAccessToken(Constants.ACCESS_TOKEN.trim(), "", mContext);

        m_Dialog = Util.showProgressDialog(mContext, "Loading");
        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_CUSTOMER_DATA);
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(list);
        mTask.setApiToken("Bearer " + accessToken.trim());
        mTask.setListener(this);
        mTask.setResultType(AddCustomerModel[].class);
        mTask.execute();

    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if (clicked) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            Date date = calendar.getTime();

            String date1 = Util.getFormattedDates(date);
            Log.e(TAG,"date1"+date1);

            tieDOB.setText(date1);
            tilDOB.setErrorEnabled(false);
            tilDOB.setError(null);
            clicked = false;

        }

        if (isSpouseDobClicked) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            Date date = calendar.getTime();

            String date1 = Util.getFormattedDates(date);

            tieSpouseDOB.setText(date1);
            tilSpouseDob.setErrorEnabled(false);
            tilSpouseDob.setError(null);
            isSpouseDobClicked = false;
        }

    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        if (m_Dialog != null && m_Dialog.isShowing()) {
            m_Dialog.dismiss();

        }
        if (httpStatusCode == Constants.SUCCESS) {
            if (resultObj != null) {
                fetchResponse(serverResponse);
            }
        } else if (httpStatusCode == Constants.BAD_REQUEST) {
            Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchResponse(String serverResponse) {
        Toast.makeText(mContext, R.string.form_submitted_successfully, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s == tieFirstName.getEditableText()) {
            tilFirstName.setErrorEnabled(false);
            tilFirstName.setError(null);
        }
        if (s == tieLastName.getEditableText()) {
            tilLastName.setErrorEnabled(false);
            tilLastName.setError(null);
        }
        if (s == tieDOB.getEditableText()) {
            tilDOB.setErrorEnabled(false);
            tilDOB.setError(null);
        }
        if (s == tieSpouseDOB.getEditableText()) {
            tilSpouseDob.setErrorEnabled(false);
            tilSpouseDob.setError(null);
        }
        if (s == tieSpouseFirstName.getEditableText()) {
            tilSpouseFirstName.setErrorEnabled(false);
            tilSpouseFirstName.setError(null);
        }
        if (s == tieSpouseLastName.getEditableText()) {
            tilSpouseLastName.setErrorEnabled(false);
            tilSpouseLastName.setError(null);
        }
        if (s == tieEmail1.getEditableText()) {
            tilEmail1.setErrorEnabled(false);
            tilEmail1.setError(null);
        }
        if (s == tieMobileNumPrimary.getEditableText()) {
            tilMobileNumPrimary.setErrorEnabled(false);
            tilMobileNumPrimary.setError(null);
        }
    }
}
