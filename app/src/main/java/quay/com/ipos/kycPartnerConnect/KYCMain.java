package quay.com.ipos.kycPartnerConnect;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.data.remote.model.KCYApproveResponse;
import quay.com.ipos.data.remote.model.KycPartnerConnectResponse;
import quay.com.ipos.data.remote.model.PartnerConnectResponse;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.RelationShipFragment;
import quay.com.ipos.partnerConnect.model.Account;
import quay.com.ipos.partnerConnect.model.BillnDelivery;
import quay.com.ipos.partnerConnect.model.Cheques;
import quay.com.ipos.partnerConnect.model.NewContact;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by niraj.kumar on 6/3/2018.
 */

public class KYCMain extends AppCompatActivity implements InitInterface,
        RelationShipFragment.OnFragmentInteractionListener, View.OnClickListener {
    private static final String TAG = KYCMain.class.getSimpleName();
    private Activity activity;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private MutableLiveData<PCModel> pcModelLiveData = new MutableLiveData<>();
    private Button btnReject, btnAccept;
    private int entityCode;
    private String requestCode;
    private Dialog myDialog;
    private String employeeCode;


    public boolean isApprover;

    private String entityName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.kyc_partner_pss);
        entityName = Prefs.getStringPrefs(Constants.entityName);
        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);



        Intent i = getIntent();
        entityCode = i.getIntExtra("EntityCode", 0);
        requestCode = i.getStringExtra("RequestCode");
        isApprover = i.getBooleanExtra("isApprover", false);

        employeeCode = Prefs.getStringPrefs(Constants.employeeCode.trim());
        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();

        getServerData(String.valueOf(entityCode), requestCode);
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
        getSupportActionBar().setTitle(  getResources().getString(R.string.title_kyc_connect) +" - "+ entityName);
        btnAccept = findViewById(R.id.btnAccept);
        btnReject = findViewById(R.id.btnReject);
        viewPager = findViewById(R.id.viewPager);


        viewPager.addOnPageChangeListener(myOnPageChangeListener);
        viewPager.setAdapter(new KYCMain.MyPagerAdapter(getSupportFragmentManager()));

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();


        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDataToWS();
            }
        });
//        btnAccept.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
        btnReject.setOnClickListener(this);
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
            //  if (position == 0 || position == 1) {
            //    findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
            //   } else {
            if (isApprover)
                findViewById(R.id.bottom_sheet).setVisibility(View.VISIBLE);
            else
                findViewById(R.id.bottom_sheet).setVisibility(View.GONE);
            //  }

           /* if (position == 5 || position == 1) {
                fab.setVisibility(View.GONE);
            } else {
                fab.setVisibility(View.VISIBLE);
            }*/
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnReject) {
            ImageView ImvClose;
            final TextInputLayout tilMessage;
            final TextInputEditText tieMessage;
            Button btnSubmit;

            myDialog.setContentView(R.layout.view_note_dialog);
            ImvClose = myDialog.findViewById(R.id.ImvClose);
            ImvClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.dismiss();
                }
            });
            tilMessage = myDialog.findViewById(R.id.tilMessage);
            tieMessage = myDialog.findViewById(R.id.tieMessage);
            tieMessage.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    tilMessage.setErrorEnabled(false);
                    tilMessage.setError(null);
                }
            });
            btnSubmit = myDialog.findViewById(R.id.btnSubmit);
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(tieMessage.getText().toString())) {
                        tilMessage.setErrorEnabled(true);
                        tilMessage.setError("Please write a Note.");
                    } else {
                        postRejection(employeeCode, requestCode, tieMessage.getText().toString());

                    }
                }
            });


            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();

        }
    }

    public void postRejection(String employeeCode, String requestCode, String message) {
        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.show();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("empCode", employeeCode);
            jsonObject.put("jsutification", message);
            jsonObject.put("requestCode", requestCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject.toString());
        String url = IPOSAPI.WEB_SERVICE_KYC_PSS_REJECT;


        final Request request = APIClient.getPostRequest(activity, url, requestBody);
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, final IOException e) {
                Log.e(TAG, "Exception::" + e.getMessage());
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {
                Log.e(TAG, "Response****" + response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                    }
                });
                try {
                    if (response != null && response.isSuccessful()) {

                        String responseData = response.body().string();
                        if (responseData != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                                    myDialog.dismiss();
                                }
                            });
                        }
                    } else if (response.code() == Constants.BAD_REQUEST) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.INTERNAL_SERVER_ERROR) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.URL_NOT_FOUND) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.UNAUTHORIZE_ACCESS) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.CONNECTION_OUT) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });
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
                return KYCRelationShipFragment.newInstance("FirstFragment, Instance 1", "0");
            case 1:
                return new KycBusinessFragment();
            case 2:
                return new KycContactFragment();
            case 3:
                return new KycAccountFragment();
            case 4:
                return new KYCBillingAddressFragment();
            case 5:
                return new KycDocumentsFragment();
            default:
                return KYCRelationShipFragment.newInstance("FirstFragment, Instance 1", "0");
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
            getServerData(String.valueOf(entityCode), requestCode);
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

    private String getServerData(String entity, String requestCode) {
        int entityCode = Prefs.getIntegerPrefs(Constants.entityCode);
        Log.i(TAG + "entityCode", entityCode + "");

        if (entityCode == 0) {
            entityCode = 1;
            Log.i(TAG, "entityCode Hardcoded if entityCode is 0" + entityCode + "");
        }

        Call<KycPartnerConnectResponse> call = RestService.getApiServiceSimple(  ).getKYCConnectData(entity, requestCode);
        call.enqueue(new Callback<KycPartnerConnectResponse>() {
            @Override
            public void onResponse(Call<KycPartnerConnectResponse> call, Response<KycPartnerConnectResponse> response) {
                Log.d(TAG, "response.raw().request().url();" + response.raw().request().url());

                if (response.code() != 200) {
                    Toast.makeText(KYCMain.this, "Code:" + response.code() + ", Message:" + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Log.i("response", response.body().statusCode + "," + response.body().message);
                    Log.i("JsonObject", response.toString() + response.body());
                    if (response.body() != null) {
                        KycPartnerConnectResponse response1 = response.body();
                        if (response1 != null) {
                            PCModel pcModel = response1.response;

                            if (pcModel != null) {


                                Log.e(TAG, "pcModel" + new Gson().toJson(pcModel));
                                Log.e(TAG, "pcDataContact" + new Gson().toJson(pcModel.Contact));
                                pcModelLiveData.setValue(pcModel);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<KycPartnerConnectResponse> call, Throwable t) {
                Toast.makeText(KYCMain.this, " Message:" + t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.e(TAG, "ERROR OCCURED");
                Log.i("JsonObject", t.toString());
                t.printStackTrace();
            }
        });

        return "";
    }

    public MutableLiveData<PCModel> getPcModelData() {
        return pcModelLiveData;
    }


    private void updateDataToWS() {

        if (employeeCode == null || employeeCode.isEmpty()) {

            Log.e(TAG, "employeeCode is null or empty");
        } else {
            Log.e(TAG, "employeeCode " + employeeCode);
        }

        KYCAcceptData kycAcceptData = new KYCAcceptData();
        kycAcceptData.empCode = employeeCode.trim();
        kycAcceptData.jsutification = "";
        kycAcceptData.requestCode = requestCode;
        kycAcceptData.pssRespnce = getPcModelData().getValue();


        Log.i("updateData", new Gson().toJson(getPcModelData().getValue()));
        Call<KCYApproveResponse> call = RestService.getApiServiceSimple( ).kycConnectUpdateDataAccept(kycAcceptData);
        call.enqueue(new Callback<KCYApproveResponse>() {
            @Override
            public void onResponse(Call<KCYApproveResponse> call, Response<KCYApproveResponse> response) {
                Log.d(TAG, "response.raw().request().url();" + response.raw().request().url());
                Log.i(TAG, "Code:" + response.code() + " message:" + response.message());
                if (response.code() != 200) {
                    IPOSApplication.showToast("Code:" + response.code() + " message:" + response.message());
                    return;
                }
                try {
                      if (response.body() != null) {
                        KCYApproveResponse response1 = response.body();
                        IPOSApplication.showToast("" + response1.message);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<KCYApproveResponse> call, Throwable t) {
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
        if (pcModel.Contact.keyBusinessContactInfo.keyMobile2 == null || pcModel.Contact.keyBusinessContactInfo.keyMobile2.isEmpty()) {
            String error = "Contact -> Secondary Mobile No. is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }
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
            if (newContact.SecondaryMobile == null || newContact.SecondaryMobile.isEmpty()) {
                String error = "Contact-> Secondary Mobile is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }

           /* if (newContact.Email == null || newContact.Email.isEmpty()) {
                String error = "Contact Other -> Email is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }*/

        }
        //validation of account
        if (pcModel.Account == null) {
            String error = "KycAccount -> KycAccount data is required!";
            Log.e(TAG, error);
            IPOSApplication.showToast(error);
            return false;
        }
        for (Account account : pcModel.Account) {
            if (account.mAccountHolderName == null || account.mAccountHolderName.isEmpty()) {
                String error = "KycAccount -> KycAccount Holder Name is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.mAccountNo == null || account.mAccountNo.isEmpty()) {
                String error = "KycAccount -> KycAccount No. is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.mAccountType == null || account.mAccountType.isEmpty()) {
                String error = "KycAccount -> KycAccount Type is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.mBankName == null || account.mBankName.isEmpty()) {
                String error = "KycAccount -> Bank Name is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.mIFSCCode == null || account.mIFSCCode.isEmpty()) {
                String error = "KycAccount -> IFSCCode is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.mBranchAdddres == null || account.mBranchAdddres.isEmpty()) {
                String error = "KycAccount -> mBranch Address is required!";
                Log.e(TAG, error);
                IPOSApplication.showToast(error);
                return false;
            }
            if (account.cheques != null) {
                for (Cheques cheque : account.cheques) {
                    if (cheque.mSecurityCheque == null || cheque.mSecurityCheque.isEmpty()) {
                        String error = "KycAccount ->   Security Cheque is required!";
                        Log.e(TAG, error);
                        IPOSApplication.showToast(error);
                        return false;
                    }

                    if (cheque.mSecurityCheque.contains("Yes")) {
                        if (cheque.mDrawnAccountNo == null || cheque.mDrawnAccountNo.isEmpty()) {
                            String error = "KycAccount ->   DrawnAccountNo is required!";
                            Log.e(TAG, error);
                            IPOSApplication.showToast(error);
                            return false;
                        }
                        if (cheque.mMaxLimitAmount == null || cheque.mMaxLimitAmount.isEmpty()) {
                            String error = "KycAccount ->   MaxLimitAmount is required!";
                            Log.e(TAG, error);
                            IPOSApplication.showToast(error);
                            return false;
                        }
                        if (cheque.mChequeNo == null || cheque.mChequeNo.isEmpty()) {
                            String error = "KycAccount ->  ChequeNo is required!";
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
        }


        return true;
    }


}

