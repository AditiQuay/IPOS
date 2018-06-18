package quay.com.ipos.partnerConnect.kyc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.kycPartnerConnect.KYCMain;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.partnerConnect.kyc.kycAdapter.KycPssDetailAdapter;
import quay.com.ipos.partnerConnect.kyc.model.KycPSSDetailsModel;
import quay.com.ipos.partnerConnect.kyc.model.RealmKycDetails;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;

/**
 * Created by niraj.kumar on 6/11/2018.
 */

public class KYCActivity extends AppCompatActivity implements InitInterface, View.OnClickListener, MyListener {
    private static final String TAG = KYCActivity.class.getSimpleName();
    private Button btnViewAll;
    private TextView textViewNewCount, textViewInProcessCount, textViewVerifiedCount, textViewAllPartnersCount;
    private TextView textViewKycNumber, textViewKycPartner, textViewKycStatus, textViewKycPendingSince, textViewKycPendingTime, textViewBank, textViewDocuments;
    private Context mContext;
    private RecyclerView recyclerViewCardList;
    private ArrayList<KycPSSDetailsModel> kycPSSDetailsModels = new ArrayList<>();
    private KycPssDetailAdapter kycPssDetailAdapter;
    private LinearLayout lLayoutNew, lLayoutInprocess, lLayoutVerified, lLayoutAllPartners;
    private TextView textViewNew, textViewInProcess, textViewVerified, textViewAllPartners;
    private android.support.v7.widget.Toolbar toolbar;
    int newCount, inProcessCount, verifiedCount;
    int entityCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyc_activity_main);
        mContext = KYCActivity.this;
        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        String EmpCode = Prefs.getStringPrefs(Constants.employeeCode.trim());
        entityCode = Prefs.getIntegerPrefs(Constants.entityCode.trim());
        getSummary(EmpCode);
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);

        btnViewAll = findViewById(R.id.btnViewAll);
        textViewNewCount = findViewById(R.id.textViewNewCount);
        textViewInProcessCount = findViewById(R.id.textViewInProcessCount);
        textViewVerifiedCount = findViewById(R.id.textViewVerifiedCount);
        textViewAllPartnersCount = findViewById(R.id.textViewAllPartnersCount);

        //Status
        textViewKycNumber = findViewById(R.id.textViewKycNumber);
        textViewKycPartner = findViewById(R.id.textViewKycPartner);
        textViewKycStatus = findViewById(R.id.textViewKycStatus);
        textViewKycPendingSince = findViewById(R.id.textViewKycPendingSince);
        textViewKycPendingTime = findViewById(R.id.textViewKycPendingTime);
        textViewBank = findViewById(R.id.textViewBank);
        textViewDocuments = findViewById(R.id.textViewDocuments);

        textViewNew = findViewById(R.id.textViewNew);
        textViewInProcess = findViewById(R.id.textViewInProcess);
        textViewVerified = findViewById(R.id.textViewVerified);
        textViewAllPartners = findViewById(R.id.textViewAllPartners);

        lLayoutNew = findViewById(R.id.lLayoutNew);
        lLayoutInprocess = findViewById(R.id.lLayoutInprocess);
        lLayoutVerified = findViewById(R.id.lLayoutVerified);
        lLayoutAllPartners = findViewById(R.id.lLayoutAllPartners);

        recyclerViewCardList = findViewById(R.id.recyclerViewCardList);
        btnViewAll.setOnClickListener(this);
        lLayoutNew.setOnClickListener(this);
        lLayoutInprocess.setOnClickListener(this);
        lLayoutVerified.setOnClickListener(this);
        lLayoutAllPartners.setOnClickListener(this);


    }

    @Override
    public void applyInitValues() {

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle(getResources().getString(R.string.toolBar_title_kyc_activity));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));


        setAdapter();
    }

    public void getSummary(String EmpCode) {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.show();


        OkHttpClient okHttpClient = APIClient.getHttpClient();
        String url = IPOSAPI.WEB_SERVICE_KYS_PSS_DETAIL + "?EmpCode=" + EmpCode;

        final Request request = APIClient.getKycRequest(mContext, url);
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
                            JSONObject jsonObject1 = new JSONObject(responseData);
                            JSONArray array = jsonObject1.optJSONArray("response");
                            new RealmController().saveKycSummary(array.toString());

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setRealmData("New");
                                }
                            });
                        }
                    } else if (response.code() == Constants.BAD_REQUEST) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.INTERNAL_SERVER_ERROR) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.URL_NOT_FOUND) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.UNAUTHORIZE_ACCESS) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else if (response.code() == Constants.CONNECTION_OUT) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });
    }

    private void setRealmData(String key) {
        kycPSSDetailsModels.clear();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmKycDetails> realmKycDetails = realm.where(RealmKycDetails.class).findAll();
        if (realmKycDetails.size() > 0) {
            for (RealmKycDetails realmOrderCentreSummary : realmKycDetails) {

                if (realmOrderCentreSummary.getName().equalsIgnoreCase("New")) {
                    try {
                        JSONArray jsonArray = new JSONArray(realmOrderCentreSummary.getData());
                        newCount = jsonArray.length();
                        textViewNewCount.setText(jsonArray.length() + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                if (realmOrderCentreSummary.getName().equalsIgnoreCase("In Process")) {
                    try {
                        JSONArray jsonArray = new JSONArray(realmOrderCentreSummary.getData());
                        inProcessCount = jsonArray.length();
                        textViewInProcessCount.setText(jsonArray.length() + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (realmOrderCentreSummary.getName().equalsIgnoreCase("Verified")) {
                    try {
                        JSONArray jsonArray = new JSONArray(realmOrderCentreSummary.getData());
                        verifiedCount = jsonArray.length();
                        textViewVerifiedCount.setText(jsonArray.length() + "");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                int firstCount = newCount + inProcessCount + verifiedCount;
                textViewAllPartnersCount.setText(firstCount + "");
                if (key.equals(realmOrderCentreSummary.getName())) {
                    JSONArray array1 = null;
                    try {
                        array1 = new JSONArray(realmOrderCentreSummary.getData());
                        for (int k = 0; k < array1.length(); k++) {
                            JSONObject jsonObject = array1.optJSONObject(k);
                            KycPSSDetailsModel kycPSSDetailsModel = new KycPSSDetailsModel();
                            kycPSSDetailsModel.setID(jsonObject.optString("ID"));
                            kycPSSDetailsModel.setREQUEST_CODE(jsonObject.optString("REQUEST_CODE"));
                            kycPSSDetailsModel.setRequestor_Code(jsonObject.optString("Requestor_Code"));
                            kycPSSDetailsModel.setOverall_Status(jsonObject.optInt("Overall_Status"));
                            kycPSSDetailsModel.setCurrent_Approver(jsonObject.optString("Current_Approver"));
                            kycPSSDetailsModel.setFlag(jsonObject.optString("flag"));
                            kycPSSDetailsModel.setJustification(jsonObject.optString("Justification"));
                            kycPSSDetailsModel.setRequest_Date(jsonObject.optString("Request_Date"));
                            kycPSSDetailsModel.setCREATED_DATE(jsonObject.optString("CREATED_DATE"));
                            kycPSSDetailsModel.setMOD_DATE(jsonObject.optString("MOD_DATE"));
                            kycPSSDetailsModel.setBusinessPlaceCode(jsonObject.optString("BusinessPlaceCode"));
                            kycPSSDetailsModel.setSectionChanged(jsonObject.optString("SectionChange"));
                            kycPSSDetailsModels.add(kycPSSDetailsModel);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }


            setAdapter();
        }
    }

    private void setAdapter() {
        recyclerViewCardList.setHasFixedSize(true);
        recyclerViewCardList.setLayoutManager(new LinearLayoutManager(mContext));
        kycPssDetailAdapter = new KycPssDetailAdapter(mContext, kycPSSDetailsModels, this);
        recyclerViewCardList.setAdapter(kycPssDetailAdapter);

    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lLayoutNew:
                lLayoutNew.setBackgroundResource(R.drawable.card_selector_kyc);
                lLayoutInprocess.setBackgroundResource(0);
                lLayoutVerified.setBackgroundResource(0);
                lLayoutAllPartners.setBackgroundResource(0);

                textViewNewCount.setTextColor(getResources().getColor(R.color.white));
                textViewNew.setTextColor(getResources().getColor(R.color.white));

                break;
            case R.id.lLayoutInprocess:
                lLayoutInprocess.setBackgroundResource(R.drawable.card_selector_kyc);
                lLayoutNew.setBackgroundResource(0);
                lLayoutVerified.setBackgroundResource(0);
                lLayoutAllPartners.setBackgroundResource(0);

                textViewNewCount.setTextColor(getResources().getColor(R.color.grey));
                textViewNew.setTextColor(getResources().getColor(R.color.grey));

                textViewInProcessCount.setTextColor(getResources().getColor(R.color.white));
                textViewInProcess.setTextColor(getResources().getColor(R.color.white));

                break;
            case R.id.lLayoutVerified:
                lLayoutVerified.setBackgroundResource(R.drawable.card_selector_kyc);
                lLayoutNew.setBackgroundResource(0);
                lLayoutInprocess.setBackgroundResource(0);
                lLayoutAllPartners.setBackgroundResource(0);

                textViewInProcessCount.setTextColor(getResources().getColor(R.color.grey));
                textViewInProcess.setTextColor(getResources().getColor(R.color.grey));

                textViewVerifiedCount.getResources().getColor(R.color.white);
                textViewVerified.setTextColor(getResources().getColor(R.color.white));

                break;
            case R.id.lLayoutAllPartners:
                lLayoutAllPartners.setBackgroundResource(R.drawable.card_selector_kyc);
                lLayoutNew.setBackgroundResource(0);
                lLayoutInprocess.setBackgroundResource(0);
                lLayoutVerified.setBackgroundResource(0);

                textViewVerifiedCount.setTextColor(getResources().getColor(R.color.grey));
                textViewVerified.setTextColor(getResources().getColor(R.color.grey));

                textViewVerifiedCount.getResources().getColor(R.color.white);
                textViewAllPartners.getResources().getColor(R.color.white);

                break;
            case R.id.BtnViewAll:
//                Intent i = new Intent(mContext, KYCViewAll.class);
//                startActivity(i);

            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRowClicked(int position) {
        KycPSSDetailsModel kycPSSDetailsModel = kycPSSDetailsModels.get(position);
        Intent i = new Intent(mContext, KYCMain.class);
        i.putExtra("EntityCode", entityCode);
        i.putExtra("RequestCode", kycPSSDetailsModel.getREQUEST_CODE());
        startActivity(i);
    }

    @Override
    public void onRowClicked(int position, int value) {

    }
}
