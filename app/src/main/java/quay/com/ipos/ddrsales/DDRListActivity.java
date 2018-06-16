package quay.com.ipos.ddrsales;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.data.remote.model.PartnerConnectResponse;
import quay.com.ipos.ddrsales.adapter.DDRAdapter;
import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.partnerConnect.PartnerConnectMain;
import quay.com.ipos.partnerConnect.model.PCModel;
import quay.com.ipos.retailsales.activity.AddProductActivity;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.DividerItemDecoration;
import quay.com.ipos.utility.EqualSpacingItemDecoration;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DDRListActivity extends AppCompatActivity {
    private static final String TAG = PartnerConnectMain.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private MutableLiveData<PCModel> pcModelLiveData = new MutableLiveData<>();
    private RecyclerView recyclerView;
    private DDRAdapter ddrAdapter;

    List<DDR> ddrList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;


        setContentView(R.layout.activity_ddrlist);

        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.title_b2b_ddr_select));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(16));


        DDR ddr1 = new DDR("A12123", "Argo Care", "New Delhi", "Delhi", 10000, 200, 500);
        DDR ddr2 = new DDR("B12123", "Berco Electrical", "Tilak Nagar", "Delhi", 10000, 200, 0);
        DDR ddr3 = new DDR("C12123", "Shopper Stop", "Gurgaon", "Haryana", 10000, 200, 500);
        ddrList.add(ddr1);
        ddrList.add(ddr2);
        ddrList.add(ddr3);

        ddrAdapter = new DDRAdapter(activity, ddrList, new DDRAdapter.OnDDRSelectListener() {
            @Override
            public void onSelect(DDR ddr) {
                Intent mIntent = new Intent(activity, DDRAddProductActivity.class);
                startActivityForResult(mIntent, 1);
            }
        });
        recyclerView.setAdapter(ddrAdapter);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 1) {
            if (resultCode == 1) {
                IPOSApplication.isClicked = true;
                if (IPOSApplication.mProductListResult.size() > 0) {
                    for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                        if (!IPOSApplication.mProductListResult.get(i).isAdded()) {
                            IPOSApplication.mProductListResult.remove(i);
                            i--;
                        }
                    }
                }

              //  getProduct();
                IPOSApplication.isClicked = true;
            }
        }
    }
        @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

