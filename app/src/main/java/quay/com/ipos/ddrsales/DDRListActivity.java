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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import quay.com.ipos.R;
import quay.com.ipos.data.remote.RestService;
import quay.com.ipos.ddrsales.adapter.DDRAdapter;
import quay.com.ipos.ddrsales.ddrdetail.DDRCUActivity;
import quay.com.ipos.ddrsales.model.DDR;
import quay.com.ipos.ddrsales.model.DDRProduct;
import quay.com.ipos.ddrsales.model.InvoiceData;
import quay.com.ipos.ddrsales.model.request.DDRListReq;
import quay.com.ipos.ddrsales.model.response.GetDDRList;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.partnerConnect.PartnerConnectMain;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.utility.DisplayUtils;
import quay.com.ipos.utility.EqualSpacingItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DDRListActivity extends AppCompatActivity implements InitInterface {
    private static final String TAG = PartnerConnectMain.class.getSimpleName();
    private Activity activity;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private TextView tvClear;
    private EditText searchView;
    private DDRAdapter ddrAdapter;

    List<DDR> ddrList = new ArrayList<>();
    List<DDR> filterDDRList = new ArrayList<>();

    private MutableLiveData<GetDDRList> mutableLiveData = new MutableLiveData<>();


    private FloatingActionButton fab;


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


        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();

        getServerData();
   }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_help) {
            getServerData();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }





    private void getServerData() {

        DDRListReq req = new DDRListReq();
        Log.i("data", new Gson().toJson(req));
        Call<GetDDRList> call = RestService.getApiServiceSimple().DDR_GetDDRList(req);
        call.enqueue(new Callback<GetDDRList>() {
            @Override
            public void onResponse(Call<GetDDRList> call, Response<GetDDRList> response) {
                Log.d(TAG, "response.raw().request().url();" + response.raw().request().url());
                if (response.code() != 200) {
                    Toast.makeText(activity, "Code:" + response.code() + ", Message:" + response.message(), Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    if (response.body() != null) {
                      //  GetDDRList getDDRList = response.body();
                      //  List<DDRInvoiceData> ddrInvoiceDataList=IPOSApplication.getDatabase().ddrInvoiceDao().fetchAllData();
                        mutableLiveData.setValue(response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<GetDDRList> call, Throwable t) {
                Toast.makeText(activity, " Message:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "ERROR OCCURED");
                Log.i("JsonObject", t.toString());
                t.printStackTrace();
            }
        });

    }





    private void filter(String charText, List<DDR> responseList) {
        if (filterDDRList != null && responseList != null) {
            charText = charText.toLowerCase(Locale.getDefault());
            filterDDRList.clear();
            if (charText.length() == 0) {
                filterDDRList.addAll(responseList);
            } else {
                for (DDR wp : responseList) {
                    if (wp.mDDRName != null) {

                        if (wp.mDDRName.toLowerCase(Locale.getDefault()).contains(charText)) {
                            filterDDRList.add(wp);
                        }
                    }if (wp.mDDRCode != null) {

                        if (wp.mDDRCode.toLowerCase(Locale.getDefault()).contains(charText)) {
                            filterDDRList.add(wp);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void findViewById() {
        fab = findViewById(R.id.fab);

        searchView = findViewById(R.id.searchView);
        tvClear = findViewById(R.id.tvClear);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(DisplayUtils.dip2px(activity, 16)));


       /* DDR ddr1 = new DDR("A12123", "Argo Care", "New Delhi", "Delhi", 1006679900, 200, 500);
        DDR ddr2 = new DDR("B12123", "Berco Electrical", "Tilak Nagar", "Delhi", 10000, 200, 0);
        DDR ddr3 = new DDR("C12123", "Shopper Stop", "Gurgaon", "Haryana", 10000, 200, 500);
        ddrList.add(ddr1);
        ddrList.add(ddr2);
        ddrList.add(ddr3);

        filterDDRList.addAll(ddrList);*/

        ddrAdapter = new DDRAdapter(activity, filterDDRList, new DDRAdapter.OnDDRSelectListener() {
            @Override
            public void onSelect(DDR ddr) {

                InvoiceData.getInstance().cleanData();
                new RealmController().clearRealm(DDRProduct.class);

                Intent mIntent = new Intent(activity, DDRCartDetails.class);
                mIntent.putExtra("ddr", ddr);
                startActivityForResult(mIntent, 1);
            }
        });
        recyclerView.setAdapter(ddrAdapter);


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    if (ddrList.size() > 0) {
                        //tvClear.setVisibility(View.VISIBLE);
                        //llSize.setVisibility(View.VISIBLE);
                        filter(charSequence.toString(), ddrList);
                    } else {
                        // tvClear.setVisibility(View.GONE);
                        //llSize.setVisibility(View.GONE);
                    }

                    ddrAdapter.notifyDataSetChanged();
                } else {
//                    arrSearchlist.clear();
                    ddrAdapter.notifyDataSetChanged();
//                    tvItemSize.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(activity, DDRCUActivity.class);
                startActivity(intent);*/
            }
        });

    }

    @Override
    public void applyInitValues() {
        getLiveServerData().observe(this, new Observer<GetDDRList>() {
            @Override
            public void onChanged(@Nullable GetDDRList data) {
                try {
                    if (data != null) {

                        setData(data);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void setData(GetDDRList data) {
        ddrList = data.dDRList;
        filterDDRList.clear();
        filterDDRList.addAll(ddrList);
        ddrAdapter.notifyDataSetChanged();

    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }
    public MutableLiveData<GetDDRList> getLiveServerData() {
        return mutableLiveData;
    }


}

