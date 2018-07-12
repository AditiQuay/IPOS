package quay.com.ipos.inventoryTrasfer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.inventory.activity.InventoryGRNStepsActivity;
import quay.com.ipos.inventory.modal.GRNListModel;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInActivity.TransferInDetailsActivity;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;

/**
 * Created by niraj.kumar on 7/6/2018.
 */

public class TransferStepsActivity extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private static final String TAG = TransferStepsActivity.class.getSimpleName();
    private Context mContext;
    private Toolbar toolbar;
    //Tab
    private LinearLayout lLayTransferOut, lLayoutShipment, lLayoutTransferIn;
    private TextView tvTransferOut, tvShipment, tvTransferIn;

    //TransferIn id's
    private RelativeLayout rlTab, llgrnn;
    private TextView tvTransferNumber, tvOpen, tranferOutCount, tranferInCount, apCount, balanceQtyCount;


    //Grn header
    private RelativeLayout rGrn;
    private TextView tvGrnNumberCount, textViewAdd;
    private RecyclerView recycleviewList;


    public static Activity fa;
    private String isGrn="";
    private String poNumber,newGRNCreated,supplierName,businessPlaceId;
    private String empCode;
    private String busineesPlaceId;

    private ArrayList<GRNListModel> grnListModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_transfer_steps_activity);
        mContext = TransferStepsActivity.this;
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

//        rlTab.setVisibility(View.VISIBLE);
//        rGrn.setVisibility(View.VISIBLE);
//        tvTransferOut.setBackgroundResource(R.drawable.text_view_circle_grey);
//        tvTransferIn.setBackgroundResource(R.drawable.textview_circle_app_color);
//        recycleviewList.setVisibility(View.VISIBLE);

    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        //Tab Id's
        lLayTransferOut = findViewById(R.id.lLayTransferOut);
        lLayoutShipment = findViewById(R.id.lLayoutShipment);
        lLayoutTransferIn = findViewById(R.id.lLayoutTransferIn);
        tvTransferOut = findViewById(R.id.tvTransferOut);
        tvShipment = findViewById(R.id.tvShipment);
        tvTransferIn = findViewById(R.id.tvTransferIn);

        //TransferIn Id's
        rlTab = findViewById(R.id.rlTab);
        llgrnn = findViewById(R.id.llgrnn);
        tvTransferNumber = findViewById(R.id.tvTransferNumber);
        tvOpen = findViewById(R.id.tvOpen);
        tranferOutCount = findViewById(R.id.tranferOutCount);
        tranferInCount = findViewById(R.id.tranferInCount);
        apCount = findViewById(R.id.apCount);
        balanceQtyCount = findViewById(R.id.balanceQtyCount);


        //GRN header view
        rGrn = findViewById(R.id.rGrn);
        tvGrnNumberCount = findViewById(R.id.tvGrnNumberCount);
        textViewAdd = findViewById(R.id.textViewAdd);
        recycleviewList = findViewById(R.id.recycleviewList);

        textViewAdd.setOnClickListener(this);
        lLayoutTransferIn.setOnClickListener(this);

    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("Inventory");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

    }

//    private void getTGrnDetails() {
//        busineesPlaceId = "1";
//        final ProgressDialog progressDialog = new ProgressDialog(mContext);
//        JSONObject jsonObject1 = new JSONObject();
//        try {
//            jsonObject1.put("empCode", empCode);
//            jsonObject1.put("businessPlaceId", busineesPlaceId);
//            jsonObject1.put("tranID", poNumber);
//            jsonObject1.put("isGRN", false);
//            jsonObject1.put("isGRNOrQC", "NA");
//            jsonObject1.put("tran", "NA");
//
//            progressDialog.show();
//            OkHttpClient okHttpClient = APIClient.getHttpClient();
//            RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
//
//            String url = IPOSAPI.GET_TRANSFER_OUT_GRN_SUMMARY;
//
//            Log.e(TAG, "Url::" + url);
//            final Request request = APIClient.getPostRequest(this, url, requestBody);
//            okHttpClient.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, final IOException e) {
//                    progressDialog.dismiss();
//
//                }
//
//                @Override
//                public void onResponse(Call call, final Response response) throws IOException {
//                    // dismissProgress();
//
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressDialog.dismiss();
//                        }
//                    });
//                    try {
//                        if (response != null && response.isSuccessful()) {
//                            grnListModels.clear();
//                            String responseData = response.body().string();
//                            Log.e(TAG, "Response***" + responseData);
//
//                            if (responseData != null) {
//                                JSONObject jsonObject = new JSONObject(responseData);
//                                poNum = jsonObject.optString("poNumber");
//                                poStatus = jsonObject.optString("poStatus");
//                                poItemQty = jsonObject.optInt("poItemQty");
//                                poGRNQty = jsonObject.optInt("poGRNQty");
//                                poAPQty = jsonObject.optInt("poAPQty");
//                                poBalanceQty = jsonObject.optInt("poBalanceQty");
//                                qcVisible = jsonObject.optBoolean("qcVisible");
//
//
//                                JSONArray jsonArray = jsonObject.optJSONArray("gRNList");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    GRNListModel grnListModel1 = new GRNListModel();
//                                    JSONObject jsonObject2 = jsonArray.optJSONObject(i);
//                                    grnListModel1.setGrnNumber(jsonObject2.optString("grnNumber"));
//                                    grnListModel1.setGrnStatus(jsonObject2.optString("grnStatus"));
//                                    grnListModel1.setGrnDate(jsonObject2.optString("grnDate"));
//                                    grnListModel1.setGrnQty(jsonObject2.optInt("grnQty"));
//                                    grnListModel1.setGrnAPQty(jsonObject2.optInt("grnAPQty"));
//                                    grnListModel1.setGrnValue(jsonObject2.optInt("grnValue"));
//                                    grnListModel1.setAttachment(jsonObject2.optBoolean("isAttachment"));
//                                    grnListModel1.setAction(jsonObject2.optBoolean("isAction"));
//                                    grnInventories.add(grnListModel1);
//                                }
//
//                                // saveResponseLocalCreateOrder(jsonObject,requestId);
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        if (qcVisible) {
//                                            llQCList.setVisibility(View.VISIBLE);
//                                        } else {
//                                            llQCList.setVisibility(View.GONE);
//                                        }
//                                        setGrnData(poNum, poStatus, poItemQty, poGRNQty, poAPQty, poBalanceQty);
//                                    }
//                                });
//
//
//                            }
//
//
//                        } else if (response.code() == Constants.BAD_REQUEST) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else if (response.code() == Constants.INTERNAL_SERVER_ERROR) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else if (response.code() == Constants.URL_NOT_FOUND) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else if (response.code() == Constants.UNAUTHORIZE_ACCESS) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        } else if (response.code() == Constants.CONNECTION_OUT) {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//
//
//                    }
//                }
//            });
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
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
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textViewAdd:
                Intent i = new Intent(mContext, TransferInDetailsActivity.class);
                startActivity(i);

                break;
            case R.id.lLayoutTransferIn:
                rlTab.setVisibility(View.VISIBLE);
                rGrn.setVisibility(View.VISIBLE);
                tvTransferOut.setBackgroundResource(R.drawable.text_view_circle_grey);
                tvTransferIn.setBackgroundResource(R.drawable.textview_circle_app_color);
                recycleviewList.setVisibility(View.VISIBLE);

//                getTGrnDetails();
            default:
                break;
        }
    }
}
