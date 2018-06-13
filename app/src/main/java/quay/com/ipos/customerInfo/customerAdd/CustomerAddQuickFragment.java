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
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.customerInfo.customerInfoModal.CustomeChildListModel;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerServerRequestModel;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.inventory.activity.InventoryGRNStepsActivity;
import quay.com.ipos.inventory.activity.InventoryStepsActivity;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.YourFragmentInterface;
import quay.com.ipos.modal.CustomerList;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

/**
 * Created by niraj.kumar on 5/31/2018.
 */

public class CustomerAddQuickFragment extends Fragment implements InitInterface, YourFragmentInterface, View.OnFocusChangeListener, ServiceTask.ServiceResultListener, View.OnClickListener, TextWatcher, AdapterView.OnItemSelectedListener {
    private static final String TAG = CustomerAddQuickFragment.class.getSimpleName();
    private View main;
    private TextView textViewPersonalHeading;
    private MaterialSpinner genderSpinner, titleSpinner;
    private TextInputLayout tilFirstName, tilLastName, tilMobileNumber;
    private TextInputEditText tieFirstName, tieLastName, tieMobileNumber;
    private TextView textViewMadatory;
    String[] nameTitle = {"Mr.", "Mrs.", "Miss."};
    String[] genderTitle = {"Male", "Female"};
    private Context mContext;
    private DatabaseHandler dbHelper;
    private Button btnCancel, btnsubmit;
    private ArrayList<CustomerModel> customerModels = new ArrayList<>();
    AwesomeValidation mAwesomeValidation;
    private ArrayList<CustomerList.ChildResponseModel> customerChild = new ArrayList<>();
    private ArrayList<CustomerList.Customer> customer = new ArrayList<>();
    private String gender1;
    private String localId;
    private String customerCode;
    private ProgressDialog m_Dialog;
    YourFragmentInterface yourFragmentInterface;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "Data";
    SharedPreferences.Editor editor;

    public CustomerAddQuickFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.customer_add_quick_fragment, container, false);
        mContext = getActivity();
        mAwesomeValidation = new AwesomeValidation(BASIC);
        dbHelper = new DatabaseHandler(mContext);
        sharedpreferences = mContext.getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();

        return main;
    }


    @Override
    public void findViewById() {
        textViewPersonalHeading = main.findViewById(R.id.textViewPersonalHeading);
        titleSpinner = main.findViewById(R.id.titleSpinner);
        genderSpinner = main.findViewById(R.id.genderSpinner);
        tilFirstName = main.findViewById(R.id.tilFirstName);
        tilLastName = main.findViewById(R.id.tilLastName);
        tilMobileNumber = main.findViewById(R.id.tilMobileNumber);
        tieFirstName = main.findViewById(R.id.tieFirstName);
        tieLastName = main.findViewById(R.id.tieLastName);
        tieMobileNumber = main.findViewById(R.id.tieMobileNumber);
        textViewMadatory = main.findViewById(R.id.textViewMadatory);
        btnCancel = main.findViewById(R.id.btnCancel);
        btnsubmit = main.findViewById(R.id.btnSubmit);

        btnCancel.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);

        tieFirstName.setOnFocusChangeListener(this);
        tieFirstName.addTextChangedListener(this);
        tieLastName.addTextChangedListener(this);
        tieMobileNumber.addTextChangedListener(this);

        titleSpinner.setOnItemSelectedListener(this);
    }

    private void storeCustomerDataToLocalDb() {
        String customerId = " ";
        String title = String.valueOf(titleSpinner.getSelectedItem());
        String firstName = tieFirstName.getText().toString();
        String lastName = tieLastName.getText().toString();
        String gender = String.valueOf(genderSpinner.getSelectedItem());
        String mobileNumber = tieMobileNumber.getText().toString();
        if (gender.equalsIgnoreCase("Male")) {
            gender1 = "M";
        } else {
            gender1 = "F";
        }


        //   String childjson = Util.getAssetJsonResponse(getActivity(), "childArray.json");
        //  JSONArray jsonArray = new JSONArray();
        CustomeChildListModel customeChildListModel = new CustomeChildListModel();
        String childjson = new Gson().toJson(customeChildListModel.customerChildList);
        Log.i("dddd", childjson);

        if (!dbHelper.checkIfRecordExist(mobileNumber)) {
            //Storing data to local DB.
            dbHelper.insertCustomer(customerId, title, "", firstName, lastName, gender1.trim(), "", "false",
                    "", "", "", "false", childjson,
                    "", "", mobileNumber.trim(), "", "", "", "", "",
                    "", "", "", "", "", "", "",
                    "", "", "", "", "", "", "", "", "", "", "", "", "1", 0, 0);

            customerModels.addAll(dbHelper.getAllOfflineCustomer());
            String accessToken = SharedPrefUtil.getAccessToken(Constants.ACCESS_TOKEN.trim(), "", mContext);


            m_Dialog = Util.showProgressDialog(mContext, "Loading");
            ServiceTask mTask = new ServiceTask();
            mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
            mTask.setApiMethod(IPOSAPI.WEB_SERVICE_CUSTOMER_DATA);
            mTask.setApiCallType(Constants.API_METHOD_POST);
            mTask.setParamObj(customerModels);
            mTask.setApiToken("Bearer " + accessToken.trim());
            mTask.setListener(this);
            mTask.setResultType(CustomerServerRequestModel[].class);
            mTask.execute();


        } else {
            Toast.makeText(mContext, "Mobile number already exist", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void applyInitValues() {
        //Creating the ArrayAdapter instance having the name title list
        ArrayAdapter nameHeading = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, nameTitle);
        nameHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        titleSpinner.setAdapter(nameHeading);

        //Creating the ArrayAdapter instance having the name title list
        ArrayAdapter genderHeading = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, genderTitle);
        genderHeading.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderHeading);


    }

    @Override
    public void applyTypeFace() {

        FontUtil.applyTypeface(textViewPersonalHeading, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(titleSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(genderSpinner, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tilFirstName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tilLastName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tilMobileNumber, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieFirstName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieLastName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(tieMobileNumber, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(textViewMadatory, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.tieFirstName:

                break;
            case R.id.tieLastName:
            default:
                break;
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
        Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();

        try {
            JSONArray jsonArray = new JSONArray(serverResponse);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                localId = jsonObject.optString("LocalID");
                customerCode = jsonObject.optString("ServerId");

                long id = dbHelper.updateServerId(Integer.parseInt(localId), customerCode);
                Log.e(TAG, "Id***" + id);
            }

            Intent i = new Intent(mContext, CustomerInfoActivity.class);
            startActivity(i);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnCancel) {
        Intent i = new Intent(mContext,InventoryGRNStepsActivity.class);
        startActivity(i);

        } else if (v == btnsubmit) {

            String title = String.valueOf(titleSpinner.getSelectedItem());
            String firstName = tieFirstName.getText().toString();
            String lastName = tieLastName.getText().toString();
            String gender = String.valueOf(genderSpinner.getSelectedItem());
            String mobileNumber = tieMobileNumber.getText().toString();

            boolean isFail = false;
            if (title.equalsIgnoreCase("null")) {
                isFail = true;
                titleSpinner.setEnableErrorLabel(true);
                titleSpinner.setError(getResources().getString(R.string.invalid_title));
            }
            if (gender.equalsIgnoreCase("null")) {
                isFail = true;
                genderSpinner.setEnableErrorLabel(true);
                genderSpinner.setError(getResources().getString(R.string.invalid_gender));
            }
            if (TextUtils.isEmpty(tieFirstName.getText().toString())) {
                isFail = true;
                tilFirstName.setErrorEnabled(true);
                tieFirstName.setError(getResources().getString(R.string.invalid_f_name));
            }
            if (TextUtils.isEmpty(tieLastName.getText().toString())) {
                isFail = true;
                tilLastName.setErrorEnabled(true);
                tieLastName.setError(getResources().getString(R.string.invalid_l_name));
            }
            if (TextUtils.isEmpty(tieMobileNumber.getText().toString())) {
                isFail = true;
                tilMobileNumber.setErrorEnabled(true);
                tieMobileNumber.setError(getResources().getString(R.string.invalid_phone));
            }
            if (!TextUtils.isEmpty(tieMobileNumber.getText().toString())) {
                if (tieMobileNumber.getText().toString().length() < 10 || tieMobileNumber.getText().toString().length() > 10) {
                    isFail = true;
                    tilMobileNumber.setErrorEnabled(true);
                    tieMobileNumber.setError("Please enter correct mobile number");

                }
            }

            if (!isFail) {
                titleSpinner.setEnableErrorLabel(false);
                genderSpinner.setEnableErrorLabel(false);
                tilFirstName.setErrorEnabled(false);
                tilLastName.setErrorEnabled(false);
                tilMobileNumber.setErrorEnabled(false);
                storeCustomerDataToLocalDb();
            } else {
                Toast.makeText(mContext, "Please enter all the required fields", Toast.LENGTH_SHORT).show();
            }


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
            tilFirstName.setError(null);
            tilFirstName.setErrorEnabled(false);
            editor.putString("firstName", tieFirstName.getText().toString());
            editor.apply();
        }
        if (s == tieLastName.getEditableText()) {
            tilLastName.setError(null);
            tilLastName.setErrorEnabled(false);
            editor.putString("lastName", tieLastName.getText().toString());
            editor.apply();
        }
        if (s == tieMobileNumber.getEditableText()) {
            tilMobileNumber.setErrorEnabled(false);
            tilMobileNumber.setError(null);
            editor.putString("MobileNumber", tieMobileNumber.getText().toString());
            editor.apply();
        }
    }

    @Override
    public void fragmentBecameVisible() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MaterialSpinner materialSpinner = (MaterialSpinner) parent;
        String selectedSpinner = String.valueOf(materialSpinner.getSelectedItem());
        if (materialSpinner.getId() == R.id.titleSpinner) {
            editor.putString("title", selectedSpinner);
            editor.apply();
        }if (materialSpinner.getId()==R.id.genderSpinner){
            editor.putString("gender",selectedSpinner);
            editor.apply();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
