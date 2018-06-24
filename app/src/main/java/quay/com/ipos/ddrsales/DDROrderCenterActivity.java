package quay.com.ipos.ddrsales;

import android.app.Activity;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.ddrsales.adapter.POAdapter;
import quay.com.ipos.ddrsales.model.OrderModel;
import quay.com.ipos.ddrsales.model.POSummary;
import quay.com.ipos.ddrsales.model.POSummaryData;
import quay.com.ipos.ddrsales.model.request.POSummaryReq;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.PartnerConnectMain;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DDROrderCenterActivity extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private static final String TAG = PartnerConnectMain.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private MutableLiveData<POSummary> mutableLiveData = new MutableLiveData<>();
    private View btnViewAll;


    private View lLayoutNew, lLayoutAccepted, lLayoutDispatched, lLayoutDelivered, lLayoutCancelled;
    private RecyclerView recyclerViewNew, recyclerViewAccepted, recyclerViewDispatched, recyclerViewDelivered, recyclerViewCancelled;
    private POAdapter adapterNew, adapterAccepted, adapterDispatched, adapterDelivered, adapterCancelled;
    private TextView textNewCount, textAcceptedCount, textDispatchedCount, textDeliveredCount, textCancelledCount;

    private List<OrderModel> DataNew = new ArrayList<>();
    private List<OrderModel> DataAccepted = new ArrayList<>();
    private List<OrderModel> DataDispatched = new ArrayList<>();
    private List<OrderModel> DataDelivered = new ArrayList<>();
    private List<OrderModel> DataCancelled = new ArrayList<>();

    private View blockNew, blockAccepted, blockDispatched, blockDelivered, blockCancelled;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;


        setContentView(R.layout.activity_b2bordercenter);

        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();

        getServerData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(activity, "onNewIntent", Toast.LENGTH_SHORT).show();
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
        lLayoutNew = findViewById(R.id.lLayoutNew);
        lLayoutAccepted = findViewById(R.id.lLayoutAccepted);
        lLayoutDispatched = findViewById(R.id.lLayoutDispatched);
        lLayoutDelivered = findViewById(R.id.lLayoutDelivered);
        lLayoutCancelled = findViewById(R.id.lLayoutCancelled);


        textNewCount = findViewById(R.id.textNewCount);
        textAcceptedCount = findViewById(R.id.textAcceptedCount);
        textDispatchedCount = findViewById(R.id.textDispatchedCount);
        textDeliveredCount = findViewById(R.id.textDeliveredCount);
        textCancelledCount = findViewById(R.id.textCancelledCount);


        blockNew = findViewById(R.id.blockNew);
        blockAccepted = findViewById(R.id.blockAccepted);
        blockDispatched = findViewById(R.id.blockDispatched);
        blockDelivered = findViewById(R.id.blockDelivered);
        blockCancelled = findViewById(R.id.blockCancelled);


        recyclerViewNew = findViewById(R.id.recyclerViewNew);
        recyclerViewAccepted = findViewById(R.id.recyclerViewAccepted);
        recyclerViewDispatched = findViewById(R.id.recyclerViewDispatched);
        recyclerViewDelivered = findViewById(R.id.recyclerViewDelivered);
        recyclerViewCancelled = findViewById(R.id.recyclerViewCancelled);

        recyclerViewNew.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAccepted.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDispatched.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDelivered.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCancelled.setLayoutManager(new LinearLayoutManager(this));

        adapterNew = new POAdapter(activity);
        adapterAccepted = new POAdapter(activity);
        adapterDispatched = new POAdapter(activity);
        adapterDelivered = new POAdapter(activity);
        adapterCancelled = new POAdapter(activity);

        recyclerViewNew.setAdapter(adapterNew);
        recyclerViewAccepted.setAdapter(adapterAccepted);
        recyclerViewDispatched.setAdapter(adapterDispatched);
        recyclerViewDelivered.setAdapter(adapterDelivered);
        recyclerViewCancelled.setAdapter(adapterCancelled);


        setAllSelectedFalse();
        lLayoutNew.setSelected(true);
        blockNew.setVisibility(View.VISIBLE);

        lLayoutNew.setOnClickListener(this);
        lLayoutAccepted.setOnClickListener(this);
        lLayoutDispatched.setOnClickListener(this);
        lLayoutDelivered.setOnClickListener(this);
        lLayoutCancelled.setOnClickListener(this);


        btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllSelectedTrue();
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
        getLiveServerData().observe(this, new Observer<POSummary>() {
            @Override
            public void onChanged(@Nullable POSummary poSummary) {
                try {
                    if (poSummary != null) {

                        setData(poSummary);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setData(POSummary poSummary) {
        List<POSummaryData> poSummaryData = poSummary.data;

        for (POSummaryData data : poSummaryData) {

            if (data.id == 1) {
                textNewCount.setText(data.count + "");
                DataNew = data.modelList;
            }
            if (data.id == 2) {
                textAcceptedCount.setText(data.count + "");
                DataAccepted = data.modelList;
            }
            if (data.id == 3) {
                textDispatchedCount.setText(data.count + "");
                DataDispatched = data.modelList;
            }
            if (data.id == 4) {
                textDeliveredCount.setText(data.count + "");
                DataDelivered = data.modelList;
            }
            if (data.id == 5) {
                textCancelledCount.setText(data.count + "");
                DataCancelled = data.modelList;
            }


        }


        adapterNew.setList(DataNew);
        adapterAccepted.setList(DataAccepted);
        adapterDispatched.setList(DataDispatched);
        adapterDelivered.setList(DataDelivered);
        adapterCancelled.setList(DataCancelled);
    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
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
        // String localData = getLocalData();
        // POSummary pcModel = new Gson().fromJson(localData, POSummary.class);
        // Log.i("localData", pcModel.toString());
        // mutableLiveData.setValue(pcModel.response);
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
        POSummaryReq poSummaryReq = new POSummaryReq();
        {
            poSummaryReq.employeeCode = "6000014";
            poSummaryReq.employeeRole = "user";
            poSummaryReq.businessCode = "1";
            poSummaryReq.entityID = "1";
            poSummaryReq.type = "no";
        }


        Call<POSummary> call = RestService.getApiServiceSimple().DDR_NO_SUMMARY(poSummaryReq);
        call.enqueue(new Callback<POSummary>() {
            @Override
            public void onResponse(Call<POSummary> call, Response<POSummary> response) {
                Log.d(TAG, "response.raw().request().url();" + response.raw().request().url());
                if (response.code() != 200) {
                    Toast.makeText(activity, "Code:" + response.code() + ", Message:" + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                try {

                    Log.i("JsonObject", response.toString() + response.body());
                    if (response.body() != null) {
                        mutableLiveData.setValue(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<POSummary> call, Throwable t) {
                Toast.makeText(activity, " Message:" + t.getMessage(), Toast.LENGTH_SHORT).show();

                Log.e(TAG, "ERROR OCCURED");
                Log.i("JsonObject", t.toString());
                t.printStackTrace();
            }
        });

    }

    public MutableLiveData<POSummary> getLiveServerData() {
        return mutableLiveData;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lLayoutNew:
                setAllSelectedFalse();
                blockNew.setVisibility(View.VISIBLE);
                lLayoutNew.setSelected(true);
                break;
            case R.id.lLayoutAccepted:
                setAllSelectedFalse();
                blockAccepted.setVisibility(View.VISIBLE);
                lLayoutAccepted.setSelected(true);
                break;

            case R.id.lLayoutDelivered:
                setAllSelectedFalse();
                blockDelivered.setVisibility(View.VISIBLE);
                lLayoutDelivered.setSelected(true);
                break;
            case R.id.lLayoutDispatched:
                setAllSelectedFalse();
                blockDispatched.setVisibility(View.VISIBLE);
                lLayoutDispatched.setSelected(true);
                break;
            case R.id.lLayoutCancelled:
                setAllSelectedFalse();
                blockCancelled.setVisibility(View.VISIBLE);
                lLayoutCancelled.setSelected(true);
                break;

        }
    }

    private void setAllSelectedFalse() {
        lLayoutNew.setSelected(false);
        lLayoutDelivered.setSelected(false);
        lLayoutDispatched.setSelected(false);
        lLayoutAccepted.setSelected(false);
        lLayoutCancelled.setSelected(false);

        blockNew.setVisibility(View.GONE);
        blockDelivered.setVisibility(View.GONE);
        blockDispatched.setVisibility(View.GONE);
        blockAccepted.setVisibility(View.GONE);
        blockCancelled.setVisibility(View.GONE);
    }

    private void setAllSelectedTrue() {
        lLayoutNew.setSelected(true);
        lLayoutDelivered.setSelected(true);
        lLayoutDispatched.setSelected(true);
        lLayoutAccepted.setSelected(true);
        lLayoutCancelled.setSelected(true);

        blockNew.setVisibility(View.VISIBLE);
        blockDelivered.setVisibility(View.VISIBLE);
        blockDispatched.setVisibility(View.VISIBLE);
        blockAccepted.setVisibility(View.VISIBLE);
        blockCancelled.setVisibility(View.VISIBLE);
    }


}

