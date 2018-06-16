package quay.com.ipos.ddrsales;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.data.remote.model.PartnerConnectResponse;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.PartnerConnectMain;
import quay.com.ipos.partnerConnect.RelationShipFragment;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DDROrderCenterActivity extends AppCompatActivity implements InitInterface,
        RelationShipFragment.OnFragmentInteractionListener {
    private static final String TAG = PartnerConnectMain.class.getSimpleName();
    private Activity activity;
    private TabLayout tabLayout;

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private MutableLiveData<PCModel> pcModelLiveData = new MutableLiveData<>();
    private View btnViewAll;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;


        setContentView(R.layout.activity_b2bordercenter);

        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();

        //getServerData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public void findViewById() {
        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_b2b_order_center));

        btnViewAll = findViewById(R.id.btnViewAll);


        tabLayout = findViewById(R.id.tabs);

        createTabIcons();


        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DDRListActivity.class);
                startActivity(intent);
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

        RelativeLayout tabOne = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_b2b_order_center, null);
        TextView tabTextTitleOne = tabOne.findViewById(R.id.tabTitle);
        tabTextTitleOne.setText(getResources().getString(R.string.ddrb2bNew));
//        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_relationship_white, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabOne));

        RelativeLayout tabTwo = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_b2b_order_center, null);
        TextView tabTextTitleTwo = tabTwo.findViewById(R.id.tabTitle);
        tabTextTitleTwo.setText(getResources().getString(R.string.ddrb2bAccepted));
        //   tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_business_white, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabTwo));

        RelativeLayout tabThree = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_b2b_order_center, null);
        TextView tabTextTitleThree = tabThree.findViewById(R.id.tabTitle);
        tabTextTitleThree.setText(getResources().getString(R.string.ddrb2bDispatched));
        //  tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_contact_white, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabThree));

        RelativeLayout tabFour = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_b2b_order_center, null);
        TextView tabTextTitleFour = tabFour.findViewById(R.id.tabTitle);
        tabTextTitleFour.setText(getResources().getString(R.string.ddrb2bDelivered));
        //   tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_bank_white, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabFour));

        RelativeLayout tabFive = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.custom_tab_b2b_order_center, null);
        TextView tabTextTitleFive = tabFive.findViewById(R.id.tabTitle);
        tabTextTitleFive.setText(getResources().getString(R.string.ddrb2bCancelled));
        //    tabFive.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.pc_billing_white, 0, 0);
        tabLayout.addTab(tabLayout.newTab().setCustomView(tabFive));


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

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

    private String getServerData() {
        int entityCode = Prefs.getIntegerPrefs(Constants.entityCode);
        Log.i(TAG + "entityCode", entityCode + "");

        if (entityCode == 0) {
            entityCode = 1;
            Log.i(TAG, "entityCode Hardcoded if entityCode is 0" + entityCode + "");
        }

        Call<PartnerConnectResponse> call = RestService.getApiServiceSimple(IPOSApplication.getContext()).loadPartnerConnectData(entityCode + "");
        call.enqueue(new Callback<PartnerConnectResponse>() {
            @Override
            public void onResponse(Call<PartnerConnectResponse> call, Response<PartnerConnectResponse> response) {
                if (response.code() != 200) {
                    Toast.makeText(activity, "Code:" + response.code() + ", Message:" + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Log.i("response", response.body().statusCode + "," + response.body().message);
                    Log.i("JsonObject", response.toString() + response.body());
                    if (response.body() != null) {
                        PartnerConnectResponse response1 = response.body();
                        if (response1 != null) {
                            PCModel pcModel = response1.response;
                            if (pcModel != null) {
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
                Toast.makeText(activity, " Message:" + t.getMessage(), Toast.LENGTH_SHORT).show();

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


}

