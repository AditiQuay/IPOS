package quay.com.ipos.partnerConnect;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.MutableLiveData;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.data.remote.model.PartnerConnectResponse;
import quay.com.ipos.data.remote.model.PartnerConnectUpdateResponse;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.model.Account;
import quay.com.ipos.partnerConnect.model.BillnDelivery;
import quay.com.ipos.partnerConnect.model.Cheques;
import quay.com.ipos.partnerConnect.model.NewContact;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.ui.MessageDialog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.NetUtil;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;
import quay.com.ipos.utility.ValidateUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static quay.com.ipos.utility.Constants.employeeCode;

/**
 * Created by niraj.kumar on 6/3/2018.
 */

public class PartnerConnectMain extends AppCompatActivity implements InitInterface,
        RelationShipFragment.OnFragmentInteractionListener {
    private static final String TAG = PartnerConnectMain.class.getSimpleName();
    private Activity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    public FloatingActionButton fab;
    private MutableLiveData<PCModel> pcModelLiveData = new MutableLiveData<>();
    private View btnCancel, btnsubmit;

    private String filename = "SampleFile.txt";
    private String filepath = "IPOSFileStorage";
    File myExternalFile;
    String myData = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;


        setContentView(R.layout.activity_partner_connect);


        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();


        getServerData();
        initFile();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void findViewById() {
        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_partner_connect));
        btnsubmit = findViewById(R.id.btnSubmit);
        btnCancel = findViewById(R.id.btnCancel);
        viewPager = findViewById(R.id.viewPager);


        viewPager.addOnPageChangeListener(myOnPageChangeListener);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataToWS();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    @Override
    public void applyInitValues() {

    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText(getResources().getString(R.string.pc_tab_1));
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_relationship_white, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(getResources().getString(R.string.pc_tab_2));
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_business_white, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText(getResources().getString(R.string.pc_tab_3));
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_contact_white, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText(getResources().getString(R.string.pc_tab_4));
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_bank_white, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFive.setText(getResources().getString(R.string.pc_tab_5));
        tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_billing_white, 0, 0);
        tabLayout.getTabAt(4).setCustomView(tabFive);

        TextView tabSix = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabSix.setText(getResources().getString(R.string.pc_tab_6));
        tabSix.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_documents_white, 0, 0);
        tabLayout.getTabAt(5).setCustomView(tabSix);
    }

    public int getCurrentViewPagerPos() {
        if (viewPager != null) {
            return viewPager.getCurrentItem();
        }
        return 0;
    }

    ViewPager.OnPageChangeListener myOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        //declare key
        Boolean first = true;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (first && positionOffset == 0 && positionOffsetPixels == 0) {
                onPageSelected(0);
                first = false;
            }
        }

        @Override
        public void onPageSelected(int position) {
            //do what need
           /* if (mListener != null) {
                mListener.onPageSelected(position);
            }*/
            try {


                if (getPcModelData().getValue().shouldShowBottom()) {
                    if (position == 0 || position == 1) {
                        findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
                    } else {
                        findViewById(R.id.bottom_sheet).setVisibility(View.VISIBLE);
                    }
                }

                if (position == 5 || position == 1) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position) {
            return getFragmentByPosition(position);

        }

        @Override
        public int getCount() {
            return 6;
        }
    }

    private Fragment getFragmentByPosition(int position) {
        switch (position) {
            case 0:
                return RelationShipFragment.newInstance("FirstFragment, Instance 1", "0");
            case 1:
                return new BusinessFragment();
            case 2:
                return new ContactFragment();
            case 3:
                return new AccountFragment();
            case 4:
                return new BillingAddressFragment();
            case 5:
                return new DocumentsFragment();
            default:
                return RelationShipFragment.newInstance("FirstFragment, Instance 1", "0");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            getData();
            return true;

        }
        if (id == R.id.action_help) {
            getServerData();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }


    public void getData() {


        String localData = getLocalData();
        PartnerConnectResponse pcModel = new Gson().fromJson(localData, PartnerConnectResponse.class);
        Log.i("localData", pcModel.toString());
        pcModelLiveData.setValue(pcModel.response);
    }

    private String getLocalData() {
        String json = Util.getAssetJsonResponse(this, "pss_connect");
        return json;

    }

    private void getServerData() {


        int entityCode = Prefs.getIntegerPrefs(Constants.entityCode);
        Log.i(TAG + "entityCode", entityCode + "");

        if (entityCode == 0) {
            entityCode = 1;
            Log.i(TAG, "entityCode Hardcoded if entityCode is 0" + entityCode + "");
        }

        Call<PartnerConnectResponse> call = RestService.getApiServiceSimple().loadPartnerConnectData(entityCode + "");

        if (!NetUtil.isNetworkConnected(activity)) {
            Toast.makeText(activity, "" + getResources().getString(R.string.internet_connection_error_string), Toast.LENGTH_SHORT).show();
            return;
        }

        call.enqueue(new Callback<PartnerConnectResponse>() {
            @Override
            public void onResponse(Call<PartnerConnectResponse> call, Response<PartnerConnectResponse> response) {
                Log.d(TAG, "response.raw().request().url();" + response.raw().request().url());
                if (response.code() != 200) {
                    Log.i(TAG, "Code:" + response.code() + ", Message:" + response.message());
                    Toast.makeText(PartnerConnectMain.this, "Code:" + response.code() + ", Message:" + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    // Log.i("response", response.body().statusCode + "," + response.body().message);
                    // Log.i("JsonObject", response.toString() + response.body());
                    if (response.body() != null) {
                        PartnerConnectResponse response1 = response.body();
                        if (response1 != null) {
                            PCModel pcModel = response1.response;
                            if (pcModel != null) {
                                String empId = Prefs.getStringPrefs(employeeCode);
                                Log.i(TAG, "empId" + empId);
                                if (empId == null) {
                                    Log.e(TAG, "empId is null");
                                    return;
                                }
                                Log.i(TAG + " pcModel response:", new Gson().toJson(pcModel));
                                pcModel.empCode = empId;
                                if (response1.keyValuePairs == null) {
                                    response1.setDefaultValue();
                                }

                                pcModelLiveData.setValue(pcModel);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PartnerConnectResponse> call, Throwable t) {
                Toast.makeText(PartnerConnectMain.this, " Message:" + t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.e(TAG, "ERROR OCCURED");
                Log.i("JsonObject", t.toString());
                t.printStackTrace();
            }
        });
    }

    public MutableLiveData<PCModel> getPcModelData() {
        return pcModelLiveData;
    }


    private void updateDataToWS() {
        if (!validateData()) {
            return;
        }
        //  PCModel pcModel = new PCModel();
        //  pcModel.setLog(getServerData().getValue());

        //  Log.i("updateData pcModel", new Gson().toJson(pcModel));
        // Log.i("updateData pcModel", new Gson().toJson(getServerData().getValue()));
        writeFile(new Gson().toJson(getPcModelData().getValue()));

        Call<PartnerConnectUpdateResponse> call = RestService.getApiServiceSimple().updatePartnerConnectData(getPcModelData().getValue());
        call.enqueue(new Callback<PartnerConnectUpdateResponse>() {
            @Override
            public void onResponse(Call<PartnerConnectUpdateResponse> call, Response<PartnerConnectUpdateResponse> response) {
                Log.d(TAG, "response.raw().request().url();" + response.raw().request().url());
                if (response.code() != 200) {
                    IPOSApplication.showToast("Code:" + response.code() + " message:" + response.message());
                    return;
                }
                try {
                    Log.i(TAG, "Code:" + response.code() + " message:" + response.message());

                    IPOSApplication.showToast("Data Updated.");
                    Log.i("response", response.body().statusCode + "," + response.body().message);
                    Log.i("JsonObject", response.toString() + response.body());
                    if (response.body() != null) {
                        PartnerConnectUpdateResponse response1 = response.body();
                        if (response1 != null) {
                            Log.i("updateDataResponse", new Gson().toJson(response1));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<PartnerConnectUpdateResponse> call, Throwable t) {
                Log.e(TAG, "ERROR OCCURED");
                Log.i("JsonObject", t.toString());
                t.printStackTrace();
            }
        });

    }

    private boolean validateData() {


        PCModel pcModel = getPcModelData().getValue();
        if (pcModel == null) {
            String error = "pcModel is null";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }


       /* if (pcModel.Business == null) {
            String error = "Business is null";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }


        if (pcModel.Business.KeyBusinessInfo == null) {
            String error = "Business KeyBusinessInfo is null";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }*/

        //validate business info
      /*  for (KeyBusinessInfo keyBusinessInfo : pcModel.Business.KeyBusinessInfo) {


            if (keyBusinessInfo.mPartnerType == null || keyBusinessInfo.mPartnerType.isEmpty()) {
                String error = "Business -> PartnerType is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (keyBusinessInfo.mCompanyName == null || keyBusinessInfo.mCompanyName.isEmpty()) {
                Log.i(TAG, "mCompanyName:" + keyBusinessInfo.mCompanyName);
                String error = "Business -> CompanyName is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (keyBusinessInfo.mCIN == null || keyBusinessInfo.mCIN.isEmpty()) {
                Log.i(TAG, "mCIN:" + keyBusinessInfo.mCIN);
                String error = "Business -> CIN is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (keyBusinessInfo.mPAN == null || keyBusinessInfo.mPAN.isEmpty()) {
                String error = "Business -> PAN is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }

            if (keyBusinessInfo.mContactPerson == null || keyBusinessInfo.mContactPerson.isEmpty()) {
                String error = "Business -> ContactPerson is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (keyBusinessInfo.mContactPosition == null || keyBusinessInfo.mContactPosition.isEmpty()) {
                String error = "BusinessInfo -> ContactPosition is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (keyBusinessInfo.BusinessLocation == null) {
                String error = "Business -> BusinessLocation is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (keyBusinessInfo.BusinessLocation.mState == null || keyBusinessInfo.BusinessLocation.mState.isEmpty()) {
                String error = "Business -> State is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (keyBusinessInfo.BusinessLocation.mPINCode == null || keyBusinessInfo.BusinessLocation.mPINCode.isEmpty() || keyBusinessInfo.BusinessLocation.mPINCode.length() < 6) {
                String error = "Business -> PINCode is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (keyBusinessInfo.BusinessLocation.mCity == null || keyBusinessInfo.BusinessLocation.mCity.isEmpty()) {
                String error = "Business -> City is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }

            if (keyBusinessInfo.BusinessLocation.mZone == null || keyBusinessInfo.BusinessLocation.mZone.isEmpty()) {
                String error = "Business -> Zone is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }

        }
*/

        //validation of contact
        if (pcModel.Contact == null) {
            String error = "Contact -> ContactDetail is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }
        if (pcModel.Contact.keyBusinessContactInfo == null) {
            String error = "Contact -> ContactInfo is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }
        if (pcModel.Contact.keyBusinessContactInfo.keyDesignation == null || pcModel.Contact.keyBusinessContactInfo.keyDesignation.isEmpty()) {
            String error = "Contact -> Contact Position is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }
        if (pcModel.Contact.keyBusinessContactInfo.keyEmpName == null || pcModel.Contact.keyBusinessContactInfo.keyEmpName.isEmpty()) {
            String error = "Contact -> Contact Person Name is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }
        if (pcModel.Contact.keyBusinessContactInfo.keyMobile == null || pcModel.Contact.keyBusinessContactInfo.keyMobile.isEmpty()) {
            String error = "Contact -> Primary Mobile No. is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }

        if (!ValidateUtils.isValidMobile(pcModel.Contact.keyBusinessContactInfo.keyMobile)) {
            String error = "Contact-> Primary Mobile should be 10 digit!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }

        if (pcModel.Contact.keyBusinessContactInfo.keyMobile2 != null && pcModel.Contact.keyBusinessContactInfo.keyMobile2.length() > 0 && !ValidateUtils.isValidMobile(pcModel.Contact.keyBusinessContactInfo.keyMobile2)) {
            String error = "Contact-> Secondary Mobile should be 10 digit!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }

        /*if (pcModel.Contact.keyBusinessContactInfo.keyMobile2 == null || pcModel.Contact.keyBusinessContactInfo.keyMobile2.isEmpty()) {
            String error = "Contact -> Secondary Mobile No. is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }*/
        if (pcModel.Contact.keyBusinessContactInfo.keyEmail == null || pcModel.Contact.keyBusinessContactInfo.keyEmail.isEmpty()) {
            String error = "Contact-> Email is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }

        if (pcModel.Contact.keyBusinessContactInfo.NewContact == null) {
            String error = "Contact-> Contact Other is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }
        for (NewContact newContact : pcModel.Contact.keyBusinessContactInfo.NewContact) {
            if (newContact.Role == null || newContact.Role.isEmpty()) {
                String error = "Contact-> Role is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (newContact.Name == null || newContact.Name.isEmpty()) {
                String error = "Contact-> Name is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }

            if (newContact.PrimaryMobile == null || newContact.PrimaryMobile.isEmpty()) {
                String error = "Contact-> Primary Mobile is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (!ValidateUtils.isValidMobile(newContact.PrimaryMobile)) {
                String error = "Contact-> Primary Mobile should be 10 digit!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
          /*  if (newContact.SecondaryMobile == null || newContact.SecondaryMobile.isEmpty() || ValidateUtils.isValidMobile(newContact.SecondaryMobile)) {
                String error = "Contact-> Secondary Mobile is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }*/

            if (newContact.SecondaryMobile != null && newContact.SecondaryMobile.length() > 0 && !ValidateUtils.isValidMobile(newContact.SecondaryMobile)) {
                String error = "Contact-> Secondary Mobile should be 10 digit!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }

            if (newContact.Email == null || newContact.Email.isEmpty()) {
                String error = "Contact-> Email is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }

        }
        //validation of account
        if (pcModel.Account == null) {
            String error = "Account -> Account data is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }
        for (Account account : pcModel.Account) {
            if (account.mAccountHolderName == null || account.mAccountHolderName.isEmpty()) {
                String error = "Account -> Account Holder Name is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.mAccountNo == null || account.mAccountNo.isEmpty()) {
                String error = "Account -> Account No. is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.mAccountType == null || account.mAccountType.isEmpty()) {
                String error = "Account -> Account Type is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.mBankName == null || account.mBankName.isEmpty()) {
                String error = "Account -> Bank Name is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.mIFSCCode == null || account.mIFSCCode.isEmpty()) {
                String error = "Account -> IFSCCode is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.mBranchAdddres == null || account.mBranchAdddres.isEmpty()) {
                String error = "Account -> mBranch Address is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.cheques != null) {
                for (Cheques cheque : account.cheques) {
                    if (cheque.mSecurityCheque == null || cheque.mSecurityCheque.isEmpty()) {
                        String error = "Account ->   Security Cheque is required!";
                        Log.e(TAG, error);
                        IPOSApplication.showToast(error);
                        return false;
                    }

                    if (cheque.mSecurityCheque.contains("Yes")) {
                        if (cheque.mDrawnAccountNo == null || cheque.mDrawnAccountNo.isEmpty()) {
                            String error = "Account ->   DrawnAccountNo is required!";
                            Log.e(TAG, error);
                            IPOSApplication.showToast(error);
                            return false;
                        }
                        if (cheque.mMaxLimitAmount == null || cheque.mMaxLimitAmount.isEmpty()) {
                            String error = "Account ->   MaxLimitAmount is required!";
                            Log.e(TAG, error);
                            IPOSApplication.showToast(error);
                            return false;
                        }
                        if (cheque.mChequeNo == null || cheque.mChequeNo.isEmpty()) {
                            String error = "Account ->  ChequeNo is required!";
                            Log.e(TAG, error);
                            IPOSApplication.showToast(error);
                            return false;
                        }
                    }
                }


            }
        }

        //validation of Bill and Delivery
        if (pcModel.BillandDelivery == null) {
            String error = "Bill & Delivery ->  Bill & Delivery data is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }

        for (BillnDelivery billnDelivery : pcModel.BillandDelivery) {
            if (billnDelivery.mAddressType == null || billnDelivery.mAddressType.isEmpty()) {
                String error = "Bill & Delivery ->  AddressType data is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (billnDelivery.mBusinessType == null || billnDelivery.mBusinessType.isEmpty()) {
                String error = "Bill & Delivery -> BusinessType is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (billnDelivery.mBusinessAddress == null || billnDelivery.mBusinessAddress.isEmpty()) {
                String error = "Bill & Delivery -> Address is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (billnDelivery.mCity == null || billnDelivery.mCity.isEmpty()) {
                String error = "Bill & Delivery -> City is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (billnDelivery.mState == null || billnDelivery.mState.isEmpty()) {
                String error = "Bill & Delivery -> State is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (billnDelivery.mGSTIN == null || billnDelivery.mGSTIN.isEmpty()) {
                String error = "Bill & Delivery -> GSTIN is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (billnDelivery.mContactPerson == null || billnDelivery.mContactPerson.isEmpty()) {
                String error = "Bill & Delivery -> Contact Person is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (billnDelivery.mMobile == null || billnDelivery.mMobile.isEmpty()) {
                String error = "Bill & Delivery -> Contact Number is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }

            if (!ValidateUtils.isValidMobile(billnDelivery.mMobile)) {
                String error = "Bill & Delivery -> Contact Number should be 10 digit!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
        }


        return true;
    }


    void initFile() {
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            //saveButton.setEnabled(false);
            Log.i(TAG, "External Storage Not Available");
        } else {
            myExternalFile = new File(getExternalFilesDir(filepath), filename);
        }
    }

    void writeFile(String data) {
        try {
            FileOutputStream fos = new FileOutputStream(myExternalFile);
            fos.write(data.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  inputText.setText("");
        Log.i(TAG, "SampleFile.txt saved to External Storage...");

    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        //super.onBackPressed();
        Util.showMessageDialog(activity, new MessageDialog.MessageDialogListener() {
            @Override
            public void onDialogPositiveClick(Dialog dialog, int mCallType) {
                finish();
                dialog.dismiss();
            }

            @Override
            public void onDialogNegetiveClick(Dialog dialog, int mCallType) {
                dialog.dismiss();
            }

            @Override
            public void onDialogCancelClick(Dialog dialog, int mCallType) {
                dialog.dismiss();
            }
        }, getString(R.string.exit_message), getResources().getString(R.string.yes), getResources().getString(R.string.no), Constants.APP_DIALOG_BACK, "", getSupportFragmentManager());

    }
}
