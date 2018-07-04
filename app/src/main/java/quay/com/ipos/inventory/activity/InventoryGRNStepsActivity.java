package quay.com.ipos.inventory.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.InventoryGRNStepsAdapter;
import quay.com.ipos.inventory.adapter.InventoryListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailListAdapter;
import quay.com.ipos.inventory.adapter.WorkFLowUserAdapter;
import quay.com.ipos.inventory.modal.GRNListModel;
import quay.com.ipos.inventory.modal.UserModal;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.LayoutClickListener;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.realmbean.RealmBusinessPlaces;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmCustomerInfoModal;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.realmbean.RealmOrderCentreSummary;
import quay.com.ipos.realmbean.RealmOrderList;
import quay.com.ipos.realmbean.RealmPOInventory;
import quay.com.ipos.realmbean.RealmUserDetail;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SpacesItemDecoration;

/**
 * Created by niraj.kumar on 6/13/2018.
 */

public class InventoryGRNStepsActivity extends AppCompatActivity implements InitInterface, View.OnClickListener, MyListener, LayoutClickListener {

    private static final String TAG = InventoryGRNStepsActivity.class.getSimpleName();
    String[] address = {"1/82"};
    String[] items = {"PO180001", "PO180002"};
    String[] user = {"KGM Traders", "McCoy"};


    private RecyclerView recycler_viewRecentOrders, recycleview, recylerViewRoles;
    private ItemsDetailListAdapter recentOrdersListAdapter;

    private InventoryListAdapter inventoryListAdapter;
    private ArrayList<RecentOrderModal> arrSearchList = new ArrayList<>();
    private ArrayList<RealmPOInventory> inventoryModels = new ArrayList<>();
    private ArrayList<GRNListModel> grnInventories = new ArrayList<>();


    private ArrayList<RealmBusinessPlaces> addressList = new ArrayList<>();
    private ArrayList<UserModal> stringArrayListRoles = new ArrayList<>();
    private WorkFLowUserAdapter workFLowUserAdapter;


    private RecyclerView recycleviewGrnCard;
    private TextView textViewAdd, tvOpen, tvGrnNumberCount, tvPoNumber, poQtyCount, grnQtyCount, apQtyCount, balanceQtyCount;
    private Toolbar toolbar;
    private LinearLayoutManager layoutManager;
    private Context mContext;

    private InventoryGRNStepsAdapter kycViewAllAdapter;
    private String requestJson, busineesPlaceId;
    private TextView tvPO, tvGrn;
    private RelativeLayout llgrnn, rlTab;
    private LinearLayout lLayoutGrn, poLayout;
    private String empCode, businessPlaceId, poNumber;
    private String poNum, poStatus;
    private int poItemQty, poGRNQty, poAPQty, poBalanceQty;
    private boolean qcVisible;
    private LinearLayout llQCList;
    private String newGRNCreated,supplierName;
    public static Activity fa;
    private String isGrn="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grn_steps_activity);
        mContext = InventoryGRNStepsActivity.this;
        fa = this;
        Intent i = getIntent();
        poNumber = i.getStringExtra("poNumber");
        newGRNCreated = i.getStringExtra("newGRNCreated");
        supplierName = i.getStringExtra("supplierName");
        isGrn=i.getStringExtra("isGrn");

        empCode = Prefs.getStringPrefs(Constants.employeeCode.trim());
        businessPlaceId = "1";

        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
        getIntents();
        clearRealm();

        if (TextUtils.isEmpty(newGRNCreated)) {
            getPODetails();
        } else {
            tvPO.setBackgroundResource(R.drawable.text_view_circle_grey);
            tvGrn.setBackgroundResource(R.drawable.textview_circle_app_color);
            recycleview.setVisibility(View.GONE);
            recycleviewGrnCard.setVisibility(View.VISIBLE);
            rlTab.setVisibility(View.VISIBLE);
            llgrnn.setVisibility(View.VISIBLE);

            getGrnDetails();

        }

        if (isGrn.equalsIgnoreCase("1")){
            tvPO.setBackgroundResource(R.drawable.text_view_circle_grey);
            tvGrn.setBackgroundResource(R.drawable.textview_circle_app_color);
            recycleview.setVisibility(View.GONE);
            recycleviewGrnCard.setVisibility(View.VISIBLE);
            rlTab.setVisibility(View.VISIBLE);
            llgrnn.setVisibility(View.VISIBLE);

            getGrnDetails();

        }


    }

    private void getRecentOrdersData() {
        for (int i = 0; i < items.length; i++) {
            RealmPOInventory inventoryModel = new RealmPOInventory();
            inventoryModel.setPoNumber(items[i]);

            inventoryModels.add(inventoryModel);

        }
        inventoryListAdapter.notifyDataSetChanged();
    }

    private void getIntents() {
        Intent i = getIntent();
        if (i != null)
            requestJson = i.getStringExtra("request");
        assert i != null;
        busineesPlaceId = i.getStringExtra("businessPlaceId");


    }

    @Override
    public void findViewById() {
        llQCList = findViewById(R.id.llQCList);
        toolbar = findViewById(R.id.toolbar);
        recycleviewGrnCard = findViewById(R.id.recycleviewCard);
        textViewAdd = findViewById(R.id.textViewAdd);
        tvOpen = findViewById(R.id.tvOpen);
        tvGrnNumberCount = findViewById(R.id.tvGrnNumberCount);
        tvPoNumber = findViewById(R.id.tvPoNumber);
        poQtyCount = findViewById(R.id.poCount);
        grnQtyCount = findViewById(R.id.grnCount);
        apQtyCount = findViewById(R.id.apCount);
        balanceQtyCount = findViewById(R.id.balanceQtyCount);

        rlTab = findViewById(R.id.rlTab);
        llgrnn = findViewById(R.id.llgrnn);
        tvGrn = findViewById(R.id.tvGrn);
        lLayoutGrn = findViewById(R.id.lLayoutGrn);
        tvPO = findViewById(R.id.tvPO);
        poLayout = findViewById(R.id.poLayout);

        textViewAdd.setOnClickListener(this);
        poLayout.setOnClickListener(this);
        lLayoutGrn.setOnClickListener(this);

    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("INVENTORY");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        GridLayoutManager mLayoutManager5 = new GridLayoutManager(this, 1);
        recycleview.setLayoutManager(mLayoutManager5);
        recycleview.addItemDecoration(new SpacesItemDecoration(10));
        inventoryListAdapter = new InventoryListAdapter(this, inventoryModels, this);
        recycleview.setAdapter(inventoryListAdapter);


        layoutManager = new LinearLayoutManager(mContext);
        recycleviewGrnCard.setHasFixedSize(true);
        recycleviewGrnCard.setLayoutManager(layoutManager);


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
            case R.id.lLayoutGrn:
                tvPO.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvGrn.setBackgroundResource(R.drawable.textview_circle_app_color);
                recycleview.setVisibility(View.GONE);
                recycleviewGrnCard.setVisibility(View.VISIBLE);
                rlTab.setVisibility(View.VISIBLE);
                llgrnn.setVisibility(View.VISIBLE);

                getGrnDetails();

                break;
            case R.id.poLayout:
                tvGrn.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvPO.setBackgroundResource(R.drawable.textview_circle_app_color);
                recycleview.setVisibility(View.VISIBLE);
                recycleviewGrnCard.setVisibility(View.GONE);
                rlTab.setVisibility(View.GONE);
                llgrnn.setVisibility(View.GONE);


            default:
                break;
        }
        if (v == textViewAdd) {
            Intent i = new Intent(mContext, InventoryGRNDetails.class);
            i.putExtra("poNumber", tvPoNumber.getText().toString());
            i.putExtra("supplierName",supplierName);
            startActivity(i);
        }
    }

    private void getGrnDetails() {
        busineesPlaceId = "1";
        final ProgressDialog progressDialog = new ProgressDialog(InventoryGRNStepsActivity.this);
        JSONObject jsonObject1 = new JSONObject();
        try {
            jsonObject1.put("empCode", empCode);
            jsonObject1.put("businessPlaceId", busineesPlaceId);
            jsonObject1.put("poNumber", poNumber);
            jsonObject1.put("isGRN", false);
            jsonObject1.put("isGRNOrQC", "NA");

            progressDialog.show();
            OkHttpClient okHttpClient = APIClient.getHttpClient();
            RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());

            String url = IPOSAPI.WEB_SERVICE_GET_GRN_SUMMARY;

            Log.e(TAG, "Url::" + url);
            final Request request = APIClient.getPostRequest(this, url, requestBody);
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, final IOException e) {
                    progressDialog.dismiss();

                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    // dismissProgress();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                        }
                    });
                    try {
                        if (response != null && response.isSuccessful()) {
                            grnInventories.clear();
                            String responseData = response.body().string();
                            Log.e(TAG, "Response***" + responseData);

                            if (responseData != null) {
                                JSONObject jsonObject = new JSONObject(responseData);
                                poNum = jsonObject.optString("poNumber");
                                poStatus = jsonObject.optString("poStatus");
                                poItemQty = jsonObject.optInt("poItemQty");
                                poGRNQty = jsonObject.optInt("poGRNQty");
                                poAPQty = jsonObject.optInt("poAPQty");
                                poBalanceQty = jsonObject.optInt("poBalanceQty");
                                qcVisible = jsonObject.optBoolean("qcVisible");


                                JSONArray jsonArray = jsonObject.optJSONArray("gRNList");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GRNListModel grnListModel1 = new GRNListModel();
                                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                                    grnListModel1.setGrnNumber(jsonObject2.optString("grnNumber"));
                                    grnListModel1.setGrnStatus(jsonObject2.optString("grnStatus"));
                                    grnListModel1.setGrnDate(jsonObject2.optString("grnDate"));
                                    grnListModel1.setGrnQty(jsonObject2.optInt("grnQty"));
                                    grnListModel1.setGrnAPQty(jsonObject2.optInt("grnAPQty"));
                                    grnListModel1.setGrnValue(jsonObject2.optInt("grnValue"));
                                    grnListModel1.setAttachment(jsonObject2.optBoolean("isAttachment"));
                                    grnListModel1.setAction(jsonObject2.optBoolean("isAction"));
                                    grnInventories.add(grnListModel1);
                                }

                                // saveResponseLocalCreateOrder(jsonObject,requestId);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (qcVisible) {
                                            llQCList.setVisibility(View.VISIBLE);
                                        } else {
                                            llQCList.setVisibility(View.GONE);
                                        }
                                        setGrnData(poNum, poStatus, poItemQty, poGRNQty, poAPQty, poBalanceQty);
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

        } catch (JSONException e) {
            e.printStackTrace();
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

    private void setGrnData(String poNum, String poStatus, int poItemQty, int poGRNQty, int poAPQty, int poBalanceQty) {


        tvPoNumber.setText(poNum);
        tvOpen.setText(poStatus);
        poQtyCount.setText(poItemQty + "");
        grnQtyCount.setText(poGRNQty + "");
        apQtyCount.setText(poAPQty + "");
        balanceQtyCount.setText(poBalanceQty + "");
        tvGrnNumberCount.setText("GRN (" + grnInventories.size() + ")");

        kycViewAllAdapter = new InventoryGRNStepsAdapter(mContext, grnInventories, this);
        recycleviewGrnCard.setAdapter(kycViewAllAdapter);
    }


    public void getPODetails() {

        final ProgressDialog progressDialog = new ProgressDialog(InventoryGRNStepsActivity.this);
        JSONObject jsonObject1 = new JSONObject();

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, requestJson);
        String url = IPOSAPI.WEB_SERVICE_INventoryPONUMBERS;

        final Request request = APIClient.getPostRequest(this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                progressDialog.dismiss();
                //  dismissProgress();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // dismissProgress();

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

                            JSONObject jsonObject = new JSONObject(responseData);
                            JSONArray array = jsonObject.optJSONArray("pODetails");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject2 = array.optJSONObject(i);
                                JSONObject jsonObject3 = new JSONObject();
                                jsonObject3.put("poNumber", jsonObject2.optString("poNumber"));
                                jsonObject3.put("id", jsonObject2.optString("supplierCode"));
                                jsonObject3.put("date", jsonObject2.optString("poDate"));
                                jsonObject3.put("value", jsonObject2.optString("poValue"));
                                jsonObject3.put("company", jsonObject2.optString("supplierName"));
                                jsonObject3.put("poStatus", jsonObject2.optString("poStatus"));
                                new RealmController().savePODetails(jsonObject3.toString());
                            }

                            // saveResponseLocalCreateOrder(jsonObject,requestId);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setAllData();
                                }
                            });


                        }


                    } else {


                    }

                } catch (Exception e) {
                    e.printStackTrace();


                }
            }
        });
    }


    private void setAllData() {
        inventoryModels.clear();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmPOInventory> realmPOInventories = realm.where(RealmPOInventory.class).findAll();
        for (RealmPOInventory realmNewOrderCart : realmPOInventories) {
            RealmPOInventory realmPOInventory = realm.copyFromRealm(realmNewOrderCart);
            inventoryModels.add(realmPOInventory);
        }
        inventoryListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onRowClicked(int position) {
        Intent i = new Intent(mContext, ExpandablePODetailsActivity.class);
        i.putExtra("poNumber", inventoryModels.get(position).getPoNumber());
        i.putExtra("businessPlaceId", busineesPlaceId);
        i.putExtra("supplierName", inventoryModels.get(position).getCompany());
        mContext.startActivity(i);
    }

    @Override
    public void onRowClicked(int position, int value) {

    }


    @Override
    public void onCardClicked(int position) {
        GRNListModel grnListModel = grnInventories.get(position);

        Intent i = new Intent(mContext, InventoryGRNDetails.class);
        i.putExtra("grnNumber", grnListModel.getGrnNumber());
        i.putExtra("cardClick", "yes");
        i.putExtra("poNumber", tvPoNumber.getText().toString());
        i.putExtra("supplierName",supplierName);
        startActivity(i);
    }
    public void clearRealm() {

        Realm realm = Realm.getDefaultInstance();
        try {
            realm.beginTransaction();
            realm.delete(RealmPOInventory.class);


           /* Prefs.clearValue(AppConstants.UserId);
            Prefs.clearValue(AppConstants.Login_Status);
            Prefs.clearValue(AppConstants.USERNAME);
            Prefs.clearValue(AppConstants.EMPLOYEE_NAME);
            Prefs.clearValue(AppConstants.employee_name);
            Prefs.clearValue(AppConstants.name);*/
       /*     realm.delete(RealmAnswers.class);
            realm.delete(RealmCategory.class);
            realm.delete(RealmCategoryAnswers.class);
            realm.delete(RealmClient.class);
            realm.delete(RealmCustomer.class);
            realm.delete(RealmRoles.class);

            realm.delete(RealmQuestion.class);
            realm.delete(RealmQuestions.class);
            realm.delete(RealmSurveys.class);
            realm.delete(RealmWorkFlow.class);
            realm.delete(RealmSurveysList.class);
            realm.delete(RealmUser.class);
            realm.delete(RealmSurveyQuestion.class);*/


        } catch (Exception e) {
            realm.cancelTransaction();
            realm.close();
            e.printStackTrace();
        } finally {
            realm.commitTransaction();
            realm.close();
        }
    }
}
