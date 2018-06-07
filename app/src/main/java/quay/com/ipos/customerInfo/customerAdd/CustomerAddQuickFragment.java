package quay.com.ipos.customerInfo.customerAdd;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.SharedPrefUtil;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;

/**
 * Created by niraj.kumar on 5/31/2018.
 */

public class CustomerAddQuickFragment extends Fragment implements InitInterface, View.OnFocusChangeListener, ServiceTask.ServiceResultListener, View.OnClickListener {
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
    public CustomerAddQuickFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        main = inflater.inflate(R.layout.customer_add_quick_fragment, container, false);
        mContext = getActivity();
        mAwesomeValidation = new AwesomeValidation(BASIC);
        dbHelper = new DatabaseHandler(mContext);
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();

        return main;
    }

    private void sendCustomerData() {
        int storeId = SharedPrefUtil.getStoreId(Constants.STORE_ID.trim(), 0, mContext);
        AppLog.e(TAG, "StoreId" + storeId);

        CustomerModel loginParams = new CustomerModel();
        loginParams.setSearchParam("NA");
        loginParams.setStoreId(String.valueOf(storeId));

        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_CUSTOMER_LIST);
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(loginParams);
        mTask.setListener(this);
        mTask.setResultType(CustomerModel.class);
        mTask.execute();
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
        btnsubmit = main.findViewById(R.id.btnsubmit);

        btnCancel.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);

        tieFirstName.setOnFocusChangeListener(this);
    }

    private void storeCustomerDataToLocalDb() {
        // inserting note in db and getting
        // newly inserted note id
        long id = dbHelper.insertCustomer("", "Mr", "", "Niraj", "Kumar", "Male", "", "",
                "", "", "", "", "",
                "", "", "9555517491", "", "", "", "", "",
                "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", 0);

       dbHelper.getAllOfflineCustomer();




//        long id = dbHelper.insertCustomer(customerID, customerName, customerPoints, customerPhone, customerPhone2, customerPhone3,
//                customerEmail, customerEmail2, customerDom, customerBday, customerGender, customerFirstName, customerLastName,
//                custoemrGstin, customerStatus, customerDesignation, customerCompany, customerRelationship, cfactor,
//                customerAddress, customerState, customerCity, customerPin, customerImage, lastBillingDate,
//                lastBillingAmount, issuggestion, suggestion, recentOrders.toString(), 1);
    }

    @NonNull
    private SpannableStringBuilder setRedStarToLabel(String text) {
        String simple = text;
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();
        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
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

    }

    @Override
    public void onClick(View v) {
        if (v == btnCancel) {

        } else if (v == btnsubmit) {

            mAwesomeValidation.addValidation(getActivity(), R.id.titleSpinner, RegexTemplate.NOT_EMPTY, R.string.invalid_title);
            mAwesomeValidation.addValidation(getActivity(), R.id.tilFirstName, RegexTemplate.NOT_EMPTY, R.string.invalid_f_name);
            mAwesomeValidation.addValidation(getActivity(), R.id.tilLastName, RegexTemplate.NOT_EMPTY, R.string.invalid_l_name);
            mAwesomeValidation.addValidation(getActivity(), R.id.genderSpinner, RegexTemplate.NOT_EMPTY, R.string.invalid_gender);
            mAwesomeValidation.addValidation(getActivity(), R.id.tieMobileNumber, "^[2-9]{2}[0-9]{8}$", R.string.invalid_phone);

            String title = String.valueOf(titleSpinner.getSelectedItem());
            String gender = String.valueOf(genderSpinner.getSelectedItem());
            if (mAwesomeValidation.validate()) {
                if (title.equalsIgnoreCase("null")) {
                    titleSpinner.setEnableErrorLabel(true);
                    titleSpinner.setError("Please select title");
                }
                if (gender.equalsIgnoreCase("null")) {
                    genderSpinner.setEnableErrorLabel(true);
                    genderSpinner.setError("Please select gender");
                } else {
                    titleSpinner.setEnableErrorLabel(false);
                    genderSpinner.setEnableErrorLabel(false);
                    storeCustomerDataToLocalDb();
                    ArrayList<CustomerModel> customerModel = dbHelper.getAllOfflineCustomer();

                }
            }

        }

    }
}
