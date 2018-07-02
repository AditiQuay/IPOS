package quay.com.ipos.customerInfo.customerAdd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.CustomerChildAdapter;
import quay.com.ipos.customerInfo.customerInfoModal.AddCustomerModel;
import quay.com.ipos.customerInfo.customerInfoModal.CityListModel;
import quay.com.ipos.customerInfo.customerInfoModal.CountryListModel;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerSpinner;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerTypeListModel;
import quay.com.ipos.customerInfo.customerInfoModal.RelationListModel;
import quay.com.ipos.customerInfo.customerInfoModal.StateListModel;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MySubmitButton;
import quay.com.ipos.listeners.SendDataActivityToFragment;
import quay.com.ipos.listeners.YourFragmentInterface;
import quay.com.ipos.retailsales.activity.PaymentModeActivity;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 5/31/2018.
 */

public class CustomerAddFullFragment extends Fragment implements SendDataActivityToFragment, MySubmitButton, YourFragmentInterface, InitInterface, AdapterView.OnItemSelectedListener, ButtonListener, View.OnClickListener, DatePickerDialog.OnDateSetListener, ServiceTask.ServiceResultListener, TextWatcher, CustomerChildAdapter.MyChildValidation {
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
    private TextInputEditText tiePinCode, tieAddress, tieMobileNumPrimary, tieChildDOB, tieEmail2, tieEmail1;
    private MaterialSpinner countrySpinner, stateSpinner, citySpinner;

    //Professional information
    private TextView textViewProfessionalHeading;
    private MaterialSpinner companySpinner, customerTypeSpinner, relationShipSpinner;
    private TextInputLayout tilDesignation;
    private TextInputEditText tieGstin, tieDesignation;
    private static final String ARG_PARAM1 = "param1";

    private RecyclerView recyclerViewChild;
    private CustomerChildAdapter customerChildAdapter;
    private Context mContext;
    private Button btnAddChild, btnRemoveChild;
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
    private String[] maritalStatus = {"Unmarried", "Married"};
    private Bundle bundle;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "Data";

    private TextInputLayout tilChildfname, tilChildDob, tilSecondaryMobileNumber, tilEmail2, tilPinCode, tilCompany;
    private TextInputEditText tieChildFirstName, tieChildLastName, tieChildDob, tieMobileNumSecondary, tieCompany;
    private MaterialSpinner childGenderSpinner;
    private List<String> listPosition = new ArrayList<>();
    private List<String> genderPosition = new ArrayList<>();
    private TextInputLayout tilGstn;
    ArrayList<CustomerSpinner> customerSpinner = new ArrayList<>();

    public static final String quickPreference = "QuickData";
    SharedPreferences.Editor quickEditor;
    private SharedPreferences quickSharedPreferences;
    private String statusCode;
    String customerCode, customerId, titleString, genderString;
    private int localId;
    String title, gender;
    public static SendDataActivityToFragment sendDataActivityToFragment;

    public CustomerAddFullFragment() {

    }

    // TODO: Rename and change types and number of parameters
    public static CustomerAddFullFragment newInstance(String param1) {
        CustomerAddFullFragment fragment = new CustomerAddFullFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate method  called");
        Intent i = getActivity().getIntent();
        customerId = i.getStringExtra("customerId");
        customerCode = i.getStringExtra("customerCode");
        titleString = i.getStringExtra("title");
        genderString = i.getStringExtra("gender");
        localId = i.getIntExtra("localId", 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.customer_add_full_fragment, container, false);
        mContext = getActivity();

        dbHelper = new DatabaseHandler(mContext);
        quickSharedPreferences = mContext.getSharedPreferences(quickPreference, Context.MODE_PRIVATE);
        quickEditor = quickSharedPreferences.edit();

        listPosition = Arrays.asList(nameTitle);
        genderPosition = Arrays.asList(genderTitle);

        calendar = Calendar.getInstance();
        AppLog.e(TAG, "onCreateView Called");
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();


        setData();

        return main;
    }

    public void setData() {
        sharedpreferences = mContext.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (!TextUtils.isEmpty(titleString)) {
            title = titleString;
        } else {
            title = sharedpreferences.getString("title", "");

        }

        if (!TextUtils.isEmpty(genderString)) {
            gender = genderString;
            if (genderString.contains("M")) {
                gender = "Male";
            } else {
                gender = "Female";
            }
        } else {
            gender = sharedpreferences.getString("gender", "");
        }

        String firstName = sharedpreferences.getString("firstName", "");
        String lastName = sharedpreferences.getString("lastName", "");
        String MobileNumber = sharedpreferences.getString("MobileNumber", "");
        String email = sharedpreferences.getString("email", "");
        String bDay = sharedpreferences.getString("bDay", "");

        if (!TextUtils.isEmpty(title)) {
            if (listPosition.contains(title)) {
                int index = listPosition.indexOf(title);
                titleSpinner.setSelection(index + 1);
                titleSpinner.setEnabled(false);
            } else {
                titleSpinner.setEnabled(true);
            }
        }
        if (!TextUtils.isEmpty(gender)) {
            if (genderPosition.contains(gender)) {
                int index = genderPosition.indexOf(gender);
                genderSpinner.setSelection(index + 1);
                genderSpinner.setSelection(index + 1);
                genderSpinner.setEnabled(false);
            } else {
                genderSpinner.setEnabled(true);
            }
        }
        if (!TextUtils.isEmpty(firstName)) {
            tieFirstName.setText(firstName);
            tieFirstName.setFocusable(false);
        }
        if (!TextUtils.isEmpty(lastName)) {
            tieLastName.setText(lastName);
            tieLastName.setFocusable(false);
        }
        if (!TextUtils.isEmpty(MobileNumber)) {
            tieMobileNumPrimary.setText(MobileNumber);
        }
        if (!TextUtils.isEmpty(email)) {
            tieEmail1.setText(email);
            tieEmail1.setFocusable(false);
        }
        if (!TextUtils.isEmpty(bDay)) {
            tieDOB.setText(bDay);
            tieDOB.setFocusable(false);
            tieDOB.setClickable(false);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "Resume method called");

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
        tilEmail2 = main.findViewById(R.id.tilEmail2);
        tilMobileNumPrimary = main.findViewById(R.id.tilMobileNumPrimary);
        tieChildDOB = main.findViewById(R.id.tieChildDOB);
        genderSpinner = main.findViewById(R.id.genderSpinner);
        maritalStatusSpinner = main.findViewById(R.id.maritalStatusSpinner);
        lLayoutSpouse = main.findViewById(R.id.lLayoutSpouse);
        lLayoutChild = main.findViewById(R.id.lLayoutChild);
        tilSpouseFirstName = main.findViewById(R.id.tilSpouseFirstName);
        tilSpouseLastName = main.findViewById(R.id.tilSpouseLastName);
        tilSpouseDob = main.findViewById(R.id.tilSpouseDob);
        tilEmail1 = main.findViewById(R.id.tilEmail1);
        textViewSpouseHeading = main.findViewById(R.id.textViewSpouseHeading);
        tieSpouseFirstName = main.findViewById(R.id.tieSpouseFirstName);
        tieSpouseLastName = main.findViewById(R.id.tieSpouseLastName);
        childSpinner = main.findViewById(R.id.childSpinner);
        tieSpouseDOB = main.findViewById(R.id.tieSpouseDOB);
        textViewChildHeading = main.findViewById(R.id.textViewChildHeading);
        childGenderSpinner = main.findViewById(R.id.childGenderSpinner);
        tilPinCode = main.findViewById(R.id.tilPinCode);
        //KycContact info
        textViewContactHeading = main.findViewById(R.id.textViewContactHeading);
        tiePinCode = main.findViewById(R.id.tiePinCode);
        tieAddress = main.findViewById(R.id.editAddress);
        tieMobileNumSecondary = main.findViewById(R.id.tieMobileNumSecondary);
        tieMobileNumPrimary = main.findViewById(R.id.tieMobileNumPrimary);
        tilSecondaryMobileNumber = main.findViewById(R.id.tilSecondaryMobileNumber);
        tieMobileNumSecondary = main.findViewById(R.id.tieMobileNumSecondary);
        tilDesignation = main.findViewById(R.id.tilDesignation);
        tieDesignation = main.findViewById(R.id.tieDesignation);
        tilCompany = main.findViewById(R.id.tilCompany);
        tieCompany = main.findViewById(R.id.tieCompany);
        tieEmail2 = main.findViewById(R.id.tieEmail2);
        tieEmail1 = main.findViewById(R.id.tieEmail1);
        countrySpinner = main.findViewById(R.id.countrySpinner);
        stateSpinner = main.findViewById(R.id.editState);
        citySpinner = main.findViewById(R.id.citySpinner);
        btnFullFragmentCancel = main.findViewById(R.id.btnFullFragmentCancel);
        //Professional info
        textViewProfessionalHeading = main.findViewById(R.id.textViewProfessionalHeading);
        customerTypeSpinner = main.findViewById(R.id.customerTypeSpinner);
        relationShipSpinner = main.findViewById(R.id.relationShipSpinner);
        tilGstn = main.findViewById(R.id.tilGstn);
        tieGstin = main.findViewById(R.id.editGstin);

        recyclerViewChild = main.findViewById(R.id.recyclerViewChild);
        btnAddChild = main.findViewById(R.id.btnAddChild);
        btnRemoveChild = main.findViewById(R.id.btnRemoveChild);
        btnRemoveChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childModels.size() > 1) {
                    childModels.remove(childModels.size() - 1);
                    customerChildAdapter.notifyDataSetChanged();
                } else {
                    btnRemoveChild.setVisibility(View.GONE);
                }
            }
        });
        btnAddChild.setEnabled(true);
        btnAddChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (childModels.size() <= 2) {
                    btnAddChild.setEnabled(true);
                    btnAddChild.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_drawable_blue));

                    boolean isAnyEmpty = false;
                    for (int i = 0; i < childModels.size(); i++) {
                        View view = recyclerViewChild.getLayoutManager().findViewByPosition(i);
                        tilChildfname = view.findViewById(R.id.tilChildfname);
                        tieChildFirstName = view.findViewById(R.id.tieChildFirstName);

                        tilLastName = view.findViewById(R.id.tilLastName);
                        tieChildLastName = view.findViewById(R.id.tieChildLastName);

                        tilChildDob = view.findViewById(R.id.tilChildDob);
                        tieChildDob = view.findViewById(R.id.tieChildDOB);
                        tieChildFirstName.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                tilChildfname.setErrorEnabled(false);
                                tilChildfname.setError(null);
                            }
                        });


                        tieChildLastName.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                tilLastName.setErrorEnabled(false);
                                tilLastName.setError(null);

                            }
                        });


                        tieChildDob.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                tilChildDob.setErrorEnabled(false);
                                tilChildDob.setError(null);
                            }
                        });

                        childGenderSpinner = view.findViewById(R.id.childGenderSpinner);
                        if (tieChildFirstName.getText().toString().isEmpty()) {
                            tilChildfname.setErrorEnabled(true);
                            tilChildfname.setError(getResources().getString(R.string.invalid_f_name));
                            isAnyEmpty = true;
                            break;
                        }
                        if (tieChildLastName.getText().toString().isEmpty()) {
                            tilLastName.setErrorEnabled(true);
                            tilLastName.setError(getResources().getString(R.string.invalid_l_name));
                            isAnyEmpty = true;
                            break;
                        }
                        if (tieChildDob.getText().toString().isEmpty()) {
                            tilChildDob.setErrorEnabled(true);
                            tilChildDob.setError(getResources().getString(R.string.dateerror));
                            isAnyEmpty = true;
                            break;
                        }
                        if (String.valueOf(childGenderSpinner.getSelectedItem()).equalsIgnoreCase("null")) {
                            childGenderSpinner.setEnableErrorLabel(true);
                            childGenderSpinner.setError(getResources().getString(R.string.invalid_gender));
                            isAnyEmpty = true;
                            break;
                        }
                    }
                    if (!isAnyEmpty) {
                        tilChildfname.setFocusable(false);
                        tilLastName.setFocusable(false);
                        tilChildDob.setFocusable(false);

                        tilChildfname.setError(null);
                        tilLastName.setError(null);
                        tilChildDob.setError(null);
                        childGenderSpinner.setEnableErrorLabel(false);


                        AddCustomerModel.CustomerChildBean model = new AddCustomerModel.CustomerChildBean();
                        model.setCustomerChildFirstName("");
                        model.setCustomerChildLastName("");
                        model.setCustomerChildDob("");
                        model.setCustomerChildGender("");
                        childModels.add(model);
                        customerChildAdapter.notifyDataSetChanged();

                        if (childModels.size() <= 0) {
                            btnRemoveChild.setVisibility(View.GONE);
                        } else {
                            btnRemoveChild.setVisibility(View.VISIBLE);
                        }

                    }
                } else {
                    Toast.makeText(mContext, "You are not allowed to add more than 3 children details", Toast.LENGTH_SHORT).show();
                    btnAddChild.setBackgroundResource(R.drawable.button_rectangle_grey);
                }
            }
        });
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

        if (bundle != null) {
            String value = getArguments().getString("firstName");
            tieFirstName.setText(value);
        }
        tieMobileNumSecondary.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    tilSecondaryMobileNumber.setErrorEnabled(false);
                    tilSecondaryMobileNumber.setError(null);
                } else {
                    String mobileSecondary = tieMobileNumSecondary.getText().toString();
                    if (mobileSecondary.length() < 10 || mobileSecondary.length() > 10) {
                        tilSecondaryMobileNumber.setErrorEnabled(true);
                        tilSecondaryMobileNumber.setError("Enter 10 digits mobile number");
                    } else {
                        tilSecondaryMobileNumber.setErrorEnabled(false);
                        tilSecondaryMobileNumber.setError(getResources().getString(R.string.invalid_phone));
                    }
                }
            }
        });

        tieEmail2.addTextChangedListener(this);

        tiePinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tiePinCode.getText().toString().length() < 6) {
                    tilPinCode.setErrorEnabled(true);
                    tilPinCode.setError("Please enter a valid pin code");
                } else {
                    tilPinCode.setErrorEnabled(false);
                    tilPinCode.setError(null);
                }
            }
        });
        tieGstin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (tieGstin.getText().toString().length() > 15 || tieGstin.getText().toString().length() < 15) {
                    tilGstn.setErrorEnabled(true);
                    tilGstn.setError("Please enter a valid GSTN number");
                } else {
                    tilGstn.setErrorEnabled(false);
                    tilGstn.setError(null);
                }
            }
        });
        titleSpinner.setOnItemSelectedListener(this);
        genderSpinner.setOnItemSelectedListener(this);
    }

    @Override
    public void applyInitValues() {

        try {
            customerSpinner.addAll(dbHelper.getCustomerSpinner());

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
                customerTypeSpinner.setOnItemSelectedListener(this);
            }

            //Creating the ArrayAdapter instance having the KycRelationship list name list
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
            customerChildAdapter = new CustomerChildAdapter(mContext, childModels, this, this, this);
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


        //KycContact
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
                }
                if (maritalStatusSpinner.getSelectedItem().toString().equalsIgnoreCase("Unmarried")) {
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

                    customerChildAdapter = new CustomerChildAdapter(mContext, childModels, this, this, this);
                    recyclerViewChild.setAdapter(customerChildAdapter);
                } else {
                    lLayoutChild.setVisibility(View.GONE);
                }
            } else if (materialSpinner.getId() == R.id.citySpinner) {

                ArrayList<CustomerSpinner> customerSpinner = dbHelper.getCustomerSpinner();
                CityListModel[] cityListModels = new Gson().fromJson(customerSpinner.get(0).getCityList(), CityListModel[].class);
                cityName = cityListModels[position].getCityName();

            } else if (materialSpinner.getId() == R.id.editState) {

                ArrayList<CustomerSpinner> customerSpinner = dbHelper.getCustomerSpinner();
                StateListModel[] stateListModel = new Gson().fromJson(customerSpinner.get(0).getStateList(), StateListModel[].class);
                stateCode = stateListModel[position].getStateCode();


            } else if (materialSpinner.getId() == R.id.countrySpinner) {

                ArrayList<CustomerSpinner> customerSpinner = dbHelper.getCustomerSpinner();
                CountryListModel[] countryListModel = new Gson().fromJson(customerSpinner.get(0).getCountryList(), CountryListModel[].class);
                countryCode = countryListModel[position].getCountryCode();


            } else if (materialSpinner.getId() == R.id.titleSpinner) {
                quickEditor.putString("title", selectedSpinner);
                quickEditor.apply();
            } else if (materialSpinner.getId() == R.id.genderSpinner) {
                quickEditor.putString("gender", selectedSpinner);
                quickEditor.apply();
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
    public void onPartnerAdd(int position, String distributerType, String companyName, String cinNumber, String panNumber, String contactPerson, String contactPosition, String partnerState, String partnerCity, String partnerPinCode, String partnerZone) {

    }

    @Override
    public void onContactAdd(int position, String role, String name, String primaryMobileNum, String secondaryMobileNum) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFullFragmentCancel:
                sharedpreferences = mContext.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();

                quickSharedPreferences = mContext.getSharedPreferences(quickPreference, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor1 = quickSharedPreferences.edit();
                editor1.clear();
                editor1.apply();

                getActivity().finish();
                break;
            case R.id.btnFullFragmentSubmit:

                String title = String.valueOf(titleSpinner.getSelectedItem());
                String gender = String.valueOf(genderSpinner.getSelectedItem());
                String maritalStatus = String.valueOf(maritalStatusSpinner.getSelectedItem());
                String childStatus = String.valueOf(childSpinner.getSelectedItem());


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
                    }
                    if (childStatus.equalsIgnoreCase("null")) {
                        isFail = true;
                        childSpinner.setEnableErrorLabel(true);
                        childSpinner.setError("Please select child status");
                    }


                    if (childStatus.equalsIgnoreCase("Yes")) {
                        if (childStatus.equalsIgnoreCase("Yes")) {

                            for (int i = 0; i < childModels.size(); i++) {
                                View view = recyclerViewChild.getLayoutManager().findViewByPosition(i);
                                tilChildfname = view.findViewById(R.id.tilChildfname);
                                tieChildFirstName = view.findViewById(R.id.tieChildFirstName);

                                tilLastName = view.findViewById(R.id.tilLastName);
                                tieChildLastName = view.findViewById(R.id.tieChildLastName);

                                tilChildDob = view.findViewById(R.id.tilChildDob);
                                tieChildDob = view.findViewById(R.id.tieChildDOB);

                                tieChildFirstName.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        tilChildfname.setErrorEnabled(false);
                                        tilChildfname.setError(null);
                                    }
                                });


                                tieChildLastName.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        tilLastName.setErrorEnabled(false);
                                        tilLastName.setError(null);

                                    }
                                });


                                tieChildDob.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        tilChildDob.setErrorEnabled(false);
                                        tilChildDob.setError(null);
                                    }
                                });

                                childGenderSpinner = view.findViewById(R.id.childGenderSpinner);
                                if (tieChildFirstName.getText().toString().isEmpty()) {
                                    tilChildfname.setErrorEnabled(true);
                                    tilChildfname.setError(getResources().getString(R.string.invalid_f_name));
                                    isFail = true;
                                    break;
                                }
                                if (tieChildLastName.getText().toString().isEmpty()) {
                                    tilLastName.setErrorEnabled(true);
                                    tilLastName.setError(getResources().getString(R.string.invalid_l_name));
                                    isFail = true;
                                    break;
                                }
                                if (tieChildDob.getText().toString().isEmpty()) {
                                    tilChildDob.setErrorEnabled(true);
                                    tilChildDob.setError(getResources().getString(R.string.dateerror));
                                    isFail = true;
                                    break;
                                }
                                if (String.valueOf(childGenderSpinner.getSelectedItem()).equalsIgnoreCase("null")) {
                                    childGenderSpinner.setEnableErrorLabel(true);
                                    childGenderSpinner.setError(getResources().getString(R.string.invalid_gender));
                                    isFail = true;
                                    break;
                                }
                            }
                        }
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
                    tilEmail1.setError("Email is required");
                }
                if (!emailValidator(tieEmail1.getText().toString())) {
                    isFail = true;
                    tilEmail1.setErrorEnabled(true);
                    tilEmail1.setError(getResources().getString(R.string.invalid_email));
                }
                if (TextUtils.isEmpty(tieMobileNumPrimary.getText().toString())) {
                    isFail = true;
                    tilMobileNumPrimary.setErrorEnabled(true);
                    tieMobileNumPrimary.setError(getResources().getString(R.string.invalid_phone));
                }
                if (tieMobileNumPrimary.getText().toString().length() < 10 || tieMobileNumPrimary.getText().toString().length() > 10) {
                    isFail = true;
                    tilMobileNumPrimary.setErrorEnabled(true);
                    tilMobileNumPrimary.setError(getResources().getString(R.string.invalid_phone));

                }

                if (!TextUtils.isEmpty(tiePinCode.getText().toString())) {
                    if (tiePinCode.getText().toString().length() < 6 || tiePinCode.getText().toString().length() > 6) {
                        tilPinCode.setErrorEnabled(true);
                        tiePinCode.setError("Please enter a valid pin code");
                    }
                }
                if (!TextUtils.isEmpty(tieGstin.getText().toString())) {
                    if (tieGstin.getText().toString().length() < 15 || tieGstin.getText().toString().length() > 15) {
                        tilGstn.setErrorEnabled(true);
                        tilGstn.setError("Please enter a valid GSTN number");
                    }
                }
                if (!TextUtils.isEmpty(tieMobileNumSecondary.getText().toString())) {
                    if (tieMobileNumSecondary.getText().toString().length() < 10 || tieMobileNumSecondary.getText().toString().length() > 10) {
                        tilSecondaryMobileNumber.setErrorEnabled(true);
                        tilSecondaryMobileNumber.setError(getResources().getString(R.string.invalid_phone));

                    }
                }

                if (!isFail) {

                    titleSpinner.setEnableErrorLabel(false);
                    tilFirstName.setErrorEnabled(false);
                    tilLastName.setErrorEnabled(false);
                    tilCompany.setErrorEnabled(false);
                    tilDOB.setErrorEnabled(false);
                    tilSpouseFirstName.setErrorEnabled(false);
                    tilSpouseLastName.setErrorEnabled(false);
                    tilSpouseDob.setErrorEnabled(false);
                    genderSpinner.setEnableErrorLabel(false);
                    tilDOB.setErrorEnabled(false);


                    maritalStatusSpinner.setEnableErrorLabel(false);

                    boolean isNotValidated = false;
                    if (!TextUtils.isEmpty(tieMobileNumSecondary.getText().toString())) {
                        if (tieMobileNumSecondary.getText().toString().length() < 10 || tieMobileNumSecondary.getText().toString().length() > 10) {
                            isNotValidated = true;
                            tilSecondaryMobileNumber.setErrorEnabled(true);
                            tilSecondaryMobileNumber.setError(getResources().getString(R.string.invalid_phone));

                        }
                    }
                    if (!TextUtils.isEmpty(tieEmail2.getText().toString())) {
                        if (!Util.validateEmail(tieEmail2.getText().toString())) {
                            isNotValidated = true;
                            tilEmail2.setErrorEnabled(true);
                            tilEmail2.setError(getResources().getString(R.string.invalid_email));
                        }
                    }
                    if (!TextUtils.isEmpty(tiePinCode.getText().toString())) {
                        if (tiePinCode.getText().toString().length() < 6 || tiePinCode.getText().toString().length() > 6) {
                            isNotValidated = true;
                            tilPinCode.setErrorEnabled(true);
                            tilPinCode.setError("Please enter a valid pin code");
                        }
                    }
                    if (!TextUtils.isEmpty(tieGstin.getText().toString())) {
                        if (tieGstin.getText().toString().length() < 15 || tieGstin.getText().toString().length() > 15) {
                            isNotValidated = true;
                            tilGstn.setErrorEnabled(true);
                            tilGstn.setError("Please enter a valid GSTN number");
                        }
                    }

                    if (!isNotValidated) {
                        sendCustomerData();
                    }


                } else {
                    Toast.makeText(mContext, "Please enter all the required fields", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tieDOB:
                //change boolean value
                clicked = true;
                Calendar minDate = Calendar.getInstance();
                minDate.set(1900, 0, 1);

                Calendar maxDate = Calendar.getInstance();
                maxDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(true);
                datePickerDialog.setMinDate(minDate);
                datePickerDialog.setMaxDate(maxDate);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                break;
            case R.id.tieSpouseDOB:
                isSpouseDobClicked = true;
                Calendar c1 = Calendar.getInstance();
                c1.set(1900, 0, 1);

                Calendar maximunDate = Calendar.getInstance();
                maximunDate.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog = DatePickerDialog.newInstance(this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(true);
                datePickerDialog.setMinDate(c1);
                datePickerDialog.setMaxDate(maximunDate);
                datePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.setVersion(DatePickerDialog.Version.VERSION_2);
                datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
            default:
                break;

        }
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
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
        String designation = tieDesignation.getText().toString().trim();
        String company = tieCompany.getText().toString().trim();
        String mobileNumberPrimary = tieMobileNumPrimary.getText().toString().trim();
        String mobileNumberSecondary = tieMobileNumSecondary.getText().toString().trim();
        String address = tieAddress.getText().toString().trim();
        String pin = tiePinCode.getText().toString().trim();
        String gstn = tieGstin.getText().toString().trim();
        String customerType = String.valueOf(customerTypeSpinner.getSelectedItem());
        String relationShip = String.valueOf(relationShipSpinner.getSelectedItem());


        AddCustomerModel addCustomerModel = new AddCustomerModel();

        if (localId != 0) {
            addCustomerModel.setLocalId(localId);
        } else {
            addCustomerModel.setLocalId(0);
        }

        if (TextUtils.isEmpty(customerId)) {
            addCustomerModel.setCustomerID("");
            addCustomerModel.setCustomerCode("");
        } else {
            addCustomerModel.setCustomerID(customerId);
            addCustomerModel.setCustomerCode(customerId);
        }

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
        addCustomerModel.setCustomerDesignation(designation);
        addCustomerModel.setCustomerCompany(company);
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
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            Date date = calendar.getTime();

            String date1 = Util.getFormattedDates(date);
            Log.e(TAG, "date1" + date1);

            tieDOB.setText(date1);
            tilDOB.setErrorEnabled(false);
            tilDOB.setError(null);
            clicked = false;

        }

        if (isSpouseDobClicked) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

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
        Log.e(TAG, "Response**" + serverResponse);

        try {
            JSONArray jsonArray = new JSONArray(serverResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                statusCode = jsonObject.optString("StatusCode");
                message = jsonObject.optString("Message");
            }
            if (statusCode.equalsIgnoreCase("500")) {
                Toast.makeText(mContext, "Mobile number already exist", Toast.LENGTH_SHORT).show();
            }
            if (statusCode.equalsIgnoreCase("200")) {
                Toast.makeText(mContext, R.string.form_submitted_successfully, Toast.LENGTH_SHORT).show();

                sharedpreferences = mContext.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                String payment = sharedpreferences.getString("paymentModeClicked", "");
                if (payment.equalsIgnoreCase("clicked")) {
                    Intent i = new Intent(mContext, PaymentModeActivity.class);
                    startActivity(i);
                    getActivity().finish();

                    sharedpreferences = mContext.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.apply();
                } else {
                    getActivity().finish();
                    sharedpreferences = mContext.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.apply();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


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
            quickEditor.putString("firstName", tieFirstName.getText().toString());
            quickEditor.apply();
        }
        if (s == tieLastName.getEditableText()) {
            tilLastName.setErrorEnabled(false);
            tilLastName.setError(null);
            quickEditor.putString("lastName", tieLastName.getText().toString());
            quickEditor.apply();
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
            quickEditor.putString("mobileNumber", tieMobileNumPrimary.getText().toString());
            quickEditor.apply();
        }
        if (s == tieMobileNumSecondary.getEditableText()) {
            tilSecondaryMobileNumber.setErrorEnabled(false);
            tilSecondaryMobileNumber.setError(null);
        }
        if (s == tieEmail2.getEditableText()) {
            tilEmail2.setErrorEnabled(false);
            tilEmail2.setError(null);
        }
    }

    @Override
    public void onClicked(int position, String firstName, String lastName, String childGender, String childDOB) {

    }

    @Override
    public void childValidated(boolean value) {
        if (!value) {
            sendCustomerData();
        }
    }

    @Override
    public void childPreviousValidated(boolean value) {
        if (!value) {
            AddCustomerModel.CustomerChildBean model = new AddCustomerModel.CustomerChildBean();
            model.setCustomerChildFirstName("");
            model.setCustomerChildLastName("");
            model.setCustomerChildDob("");
            childModels.add(model);
            customerChildAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void fragmentBecameVisible() {
        sharedpreferences = mContext.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        String title = sharedpreferences.getString("title", "");
        String gender = sharedpreferences.getString("gender", "");
        if (!TextUtils.isEmpty(title)) {
            if (listPosition.contains(title)) {
                int index = listPosition.indexOf(title);
                titleSpinner.setSelection(index + 1);
                titleSpinner.setEnabled(false);
            } else {
                titleSpinner.setEnabled(true);
            }
        }
        if (!TextUtils.isEmpty(gender)) {
            if (genderPosition.contains(gender)) {
                int index = genderPosition.indexOf(gender);
                genderSpinner.setSelection(index + 1);
                genderSpinner.setEnabled(false);
            } else {
                genderSpinner.setEnabled(true);
            }
        }

        if (sharedpreferences.contains("firstName")) {
            tieFirstName.setText(sharedpreferences.getString("firstName", ""));
            tieFirstName.setFocusable(false);
        }
        if (sharedpreferences.contains("lastName")) {
            tieLastName.setText(sharedpreferences.getString("lastName", ""));
            tieLastName.setFocusable(false);
        }
        if (sharedpreferences.contains("MobileNumber")) {
            tieMobileNumPrimary.setText(sharedpreferences.getString("MobileNumber", ""));
        }
        if (sharedpreferences.contains("email")) {
            if (TextUtils.isEmpty(sharedpreferences.getString("email", ""))) {
                tieEmail1.setText(sharedpreferences.getString("email", ""));
                tieEmail1.setFocusable(true);

            } else {
                tieEmail1.setText(sharedpreferences.getString("email", ""));
                tieEmail1.setFocusable(false);

            }
        }
        if (sharedpreferences.contains("bDay")) {
            tieDOB.setText(sharedpreferences.getString("bDay", ""));
            tieDOB.setFocusable(false);
            tieDOB.setClickable(false);
        }

    }

    @Override
    public void sendData(String customerC, String title, String gender, String customerI, int localId) {


    }
}
