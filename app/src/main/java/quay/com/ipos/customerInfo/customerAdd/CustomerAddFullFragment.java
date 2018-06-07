package quay.com.ipos.customerInfo.customerAdd;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoAdapter.CustomerChildAdapter;
import quay.com.ipos.customerInfo.customerInfoModal.ChildModel;
import quay.com.ipos.customerInfo.customerInfoModal.CityModel;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerSpinner;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.ButtonListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.CustomerList;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.Util;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

/**
 * Created by niraj.kumar on 5/31/2018.
 */

public class CustomerAddFullFragment extends Fragment implements InitInterface, AdapterView.OnItemSelectedListener, ButtonListener, View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private View main;
    private TextView textViewMadatory, textViewPersonalHeading, textViewSpouseHeading, textViewChildHeading, textViewChild;
    private TextInputLayout tilDOB;
    private TextInputEditText tieFirstName, tieLastName, tieDOB, tieSpouseFirstName, tieSpouseLastName, tieSpouseDOB;
    private MaterialSpinner genderSpinner, titleSpinner, maritalStatusSpinner, childSpinner;
    private LinearLayout lLayoutChild, lLayoutSpouse;
    private TextInputLayout tilFirstName, tilSpouseFirstName, tilSpouseLastName, tilSpouseDob, tilChildDob;
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
    private ArrayList<ChildModel> childModels = new ArrayList<>();
    //ContactInformation
    private TextView textViewContactHeading;
    private TextInputEditText tiePinCode, tieAddress, tieMobileNumSecondary, tieMobileNumPrimary, tieEmail2, tieEmail1;
    private MaterialSpinner countrySpinner, stateSpinner, citySpinner;

    //Professional information
    private TextView textViewProfessionalHeading;
    private MaterialSpinner designationSpinner, companySpinner, customerTypeSpinner, relationShipSpinner,childGenderSpinner;
    private TextInputEditText tieGstin;

    private RecyclerView recyclerViewChild;
    private CustomerChildAdapter customerChildAdapter;
    private Context mContext;
    private Button btnAddChild;
    private AwesomeValidation awesomeValidation;
    private Button btnCancel, btnSubmit;
    private int year, month, day;
    private Calendar calendar;
    private android.app.DatePickerDialog datePickerDialog;
    private DatabaseHandler dbHelper;
    private ArrayList<CustomerSpinner> customerSpinners = new ArrayList<>();
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
        tilDOB = main.findViewById(R.id.tilDOB);
        tieDOB = main.findViewById(R.id.tieDOB);
        genderSpinner = main.findViewById(R.id.genderSpinner);
        maritalStatusSpinner = main.findViewById(R.id.maritalStatusSpinner);
        lLayoutSpouse = main.findViewById(R.id.lLayoutSpouse);
        lLayoutChild = main.findViewById(R.id.lLayoutChild);
        tilSpouseFirstName = main.findViewById(R.id.tilSpouseFirstName);
        tilSpouseLastName = main.findViewById(R.id.tilSpouseLastName);
        tilSpouseDob = main.findViewById(R.id.tilSpouseDob);
        tilChildDob = main.findViewById(R.id.tilChildDob);

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
                    ChildModel model = new ChildModel();
                    model.setFirstName("");
                    model.setLastName("");
                    model.setChildDateOfBirth("");
                    childModels.add(model);
                    customerChildAdapter.notifyDataSetChanged();

                }
            }
        });
        btnCancel = main.findViewById(R.id.btnCancel);
        btnSubmit = main.findViewById(R.id.btnSubmit);

        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        tieDOB.setClickable(true);
        tieDOB.setOnClickListener(this);
    }

    @Override
    public void applyInitValues() {
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

//        CustomerSpinner customerSpinner = dbHelper.getCustomerSpinner();
//        String cityList = customerSpinner.getCityList();
//        List<CityModel> cityModels = (List<CityModel>) new Gson().fromJson(cityList,CityModel.class);
//        String[] cityArray = new String[cityModels.size()];
//        for (int i = 0; i < cityModels.size(); i++) {
//            cityArray[i] = cityModels.get(i).getCityName();
//        }

        //Creating the ArrayAdapter instance having the city name list
        ArrayAdapter cityHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, city);
        cityHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(cityHeading);

        //Creating the ArrayAdapter instance having the state name list
        ArrayAdapter stateHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, state);
        stateHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateSpinner.setAdapter(stateHeading);

        //Creating the ArrayAdapter instance having the country name list
        ArrayAdapter countryHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, country);
        countryHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(countryHeading);

        //Creating the ArrayAdapter instance having the designation name list
        ArrayAdapter designationHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, designation);
        designationHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        designationSpinner.setAdapter(designationHeading);

        //Creating the ArrayAdapter instance having the company name list
        ArrayAdapter companyHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, company);
        companyHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        companySpinner.setAdapter(companyHeading);

        //Creating the ArrayAdapter instance having the customer Type name list
        ArrayAdapter customerTypeHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, customerType);
        customerTypeHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        customerTypeSpinner.setAdapter(customerTypeHeading);

        //Creating the ArrayAdapter instance having the relationship name list
        ArrayAdapter relationShipHeading = new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, relationship);
        relationShipHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relationShipSpinner.setAdapter(relationShipHeading);


        recyclerViewChild.setHasFixedSize(true);
        recyclerViewChild.setLayoutManager(new LinearLayoutManager(mContext));
        customerChildAdapter = new CustomerChildAdapter(mContext, childModels, this);
        recyclerViewChild.setAdapter(customerChildAdapter);
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
        MaterialSpinner materialSpinner = (MaterialSpinner) parent;
        String selectedSpinner = String.valueOf(materialSpinner.getSelectedItem());
        if (materialSpinner.getId() == R.id.maritalStatusSpinner) {
            if (selectedSpinner.equalsIgnoreCase("Married")) {
                lLayoutSpouse.setVisibility(View.VISIBLE);
                lLayoutChild.setVisibility(View.GONE);
            } else {
                lLayoutSpouse.setVisibility(View.GONE);
            }
        } else if (materialSpinner.getId() == R.id.childSpinner) {
            if (selectedSpinner.equalsIgnoreCase("Yes")) {
                lLayoutSpouse.setVisibility(View.VISIBLE);
                lLayoutChild.setVisibility(View.VISIBLE);
                childModels = new ArrayList<>();
                ChildModel model = new ChildModel();
                model.setFirstName("");
                model.setLastName("");
                model.setChildDateOfBirth("");
                childModels.add(model);
                customerChildAdapter = new CustomerChildAdapter(mContext, childModels, this);
                recyclerViewChild.setAdapter(customerChildAdapter);
            } else {
                lLayoutChild.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onAdd(int position, String firstName, String lastName, String childGender, String childDOB) {

        ChildModel model = new ChildModel();
        model.setFirstName(firstName);
        model.setLastName(lastName);
        model.setChildGender(childGender);
        model.setChildDateOfBirth(childDOB);
        childModels.set(position, model);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCancel:
                break;
            case R.id.btnSubmit:
                awesomeValidation.addValidation(getActivity(), R.id.titleSpinner, RegexTemplate.NOT_EMPTY, R.string.invalid_title);
                awesomeValidation.addValidation(getActivity(), R.id.tieFirstName, RegexTemplate.NOT_EMPTY, R.string.invalid_f_name);
                awesomeValidation.addValidation(getActivity(), R.id.tieLastName, RegexTemplate.NOT_EMPTY, R.string.invalid_l_name);
                awesomeValidation.addValidation(getActivity(), R.id.genderSpinner, RegexTemplate.NOT_EMPTY, R.string.invalid_gender);
                awesomeValidation.addValidation(getActivity(), R.id.tilDOB, RegexTemplate.NOT_EMPTY, R.string.dateerror);

                awesomeValidation.addValidation(getActivity(), R.id.maritalStatusSpinner, RegexTemplate.NOT_EMPTY, R.string.invalid_maritalStatus);
                awesomeValidation.addValidation(getActivity(), R.id.tilSpouseFirstName, RegexTemplate.NOT_EMPTY, R.string.invalid_spouse_f_name);
                awesomeValidation.addValidation(getActivity(), R.id.tilSpouseLastName, RegexTemplate.NOT_EMPTY, R.string.invalid_spouse_l_name);
                awesomeValidation.addValidation(getActivity(), R.id.tilSpouseDob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dateerror);
                awesomeValidation.addValidation(getActivity(), R.id.childGenderSpinner, RegexTemplate.NOT_EMPTY, R.string.invalid_gender);
                awesomeValidation.addValidation(getActivity(), R.id.tilChildDob, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.dateerror);
                awesomeValidation.addValidation(getActivity(), R.id.tieEmail1, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
                awesomeValidation.addValidation(getActivity(), R.id.tieMobileNumPrimary, "^[2-9]{2}[0-9]{8}$", R.string.invalid_phone);
                awesomeValidation.addValidation(getActivity(), R.id.designationSpinner, RegexTemplate.NOT_EMPTY, R.string.invalid_designation);
                awesomeValidation.addValidation(getActivity(), R.id.companySpinner, RegexTemplate.NOT_EMPTY, R.string.invalid_company);

                String title = String.valueOf(titleSpinner.getSelectedItem());
                String gender = String.valueOf(genderSpinner.getSelectedItem());
                String maritalStatus = String.valueOf(maritalStatusSpinner.getSelectedItem());
                String designation = String.valueOf(designationSpinner.getSelectedItem());
                String company = String.valueOf(companySpinner.getSelectedItem());

                if (awesomeValidation.validate()) {
                    if (title.equalsIgnoreCase("null")) {
                        titleSpinner.setEnableErrorLabel(true);
                        titleSpinner.setError("Please select title");
                    }
                    if (gender.equalsIgnoreCase("null")) {
                        genderSpinner.setEnableErrorLabel(true);
                        genderSpinner.setError(getResources().getString(R.string.invalid_gender));
                    }
                    if (maritalStatus.equalsIgnoreCase("null")) {
                        maritalStatusSpinner.setEnableErrorLabel(true);
                        maritalStatusSpinner.setError(getResources().getString(R.string.invalid_maritalStatus));
                    }
                    if (designation.equalsIgnoreCase("null")) {
                        designationSpinner.setEnableErrorLabel(true);
                        designationSpinner.setError(getResources().getString(R.string.invalid_designation));
                    }
                    if (company.equalsIgnoreCase("null")) {
                        companySpinner.setEnableErrorLabel(true);
                        companySpinner.setError(getResources().getString(R.string.invalid_company));
                    } else {
                        titleSpinner.setEnableErrorLabel(false);
                        genderSpinner.setEnableErrorLabel(false);
                        maritalStatusSpinner.setEnableErrorLabel(false);
                        designationSpinner.setEnableErrorLabel(false);
                        companySpinner.setEnableErrorLabel(false);
                    }
                    Toast.makeText(mContext, "Form validated", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tieDOB:

            default:
                break;

        }
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }
}
