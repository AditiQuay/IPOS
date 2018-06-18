package quay.com.ipos.pss_order.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

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
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.pss_order.adapter.NewOrderItemsDetailListAdapter;
import quay.com.ipos.enums.NoGetEntityEnums;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.pss_order.adapter.AddressListAdapter;
import quay.com.ipos.realmbean.RealmBusinessPlaces;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.realmbean.RealmOrderList;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.ui.MyDialogFragment;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;



public class NewOrderDetailsActivity extends BaseActivity implements View.OnClickListener,MyDialogFragment.RedeemListener{
    String[] address = {"1/82"};

    TextView tvTotalQty,tvTotalPriceBeforeGst,tvCGSTPrice,tvSGSTPrice,tvRoundingOffPrice,btnAccept,btnCancel;

    private RecyclerView recycler_viewRecentOrders,recycler_viewAddress;
    private NewOrderItemsDetailListAdapter recentOrdersListAdapter;
    private AddressListAdapter addressListAdapter;
    private ArrayList<RecentOrderModal> recentOrderModalArrayList=new ArrayList<>();
    private ArrayList<RealmBusinessPlaces> addressList=new ArrayList<>();
    TextView tvOrderName,OrderDate,tvStatus,orderValue,orderDiscount,loyaltyPoints,
            accumulatedPoints,totalPoints,customerName,discount,tvOrderValue;
    private String jsonObjectsubmit;
    private JSONObject jsonObjectSubmitJson;
    private String poNumber;
    private LinearLayout llAccept,llCancel,llDate,llRedeem;
    private TextView deliverDate;
    private double dAccumulatedPoints;
    private double perPoints=0;
    private TextView tvResendOTP;
    private LinearLayout llRedeemValue;
    private EditText etRedeemValue;
    double pointstoRedeem = 0;
    private LinearLayout llVerifyRedeem;
    private LinearLayout lLayoutView;
    private Button buttonSendOtp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_detail);

        setHeader();
        initializeComponent();

        addressListAdapter = new AddressListAdapter(this, addressList);
        recycler_viewAddress.setAdapter(addressListAdapter);
        getRecentOrdersData();
        setAllData();


        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateDialogfrom();
            }
        });
      //  getAddressData();
        //setOrdersData();
    }
    public  void dateDialogfrom() {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(mContext,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        String erg = year+"";
                        erg += "-" + String.valueOf(monthOfYear + 1);
                        erg += "-" + String.valueOf(dayOfMonth);

                        try {
                            jsonObjectSubmitJson.put("deliveryBy",erg);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        deliverDate.setText(Util.getFormattedDates(erg,Constants.format6,Constants.format2));

                    }

                }, y, m, d);
        dp.setTitle("Calender");
        dp.show();

        dp.getDatePicker().setMinDate(System.currentTimeMillis());


    }

    private void prepareData(Realm realm, RealmOrderList realmOrderLists){
        Gson gson = new GsonBuilder().create();


            String responseRealm = gson.toJson(realm.copyFromRealm(realmOrderLists));
        try {
            jsonObjectSubmitJson=new JSONObject(responseRealm);
            jsonObjectSubmitJson.put("cartDetail",new JSONArray(realmOrderLists.getCartDetail()));
            jsonObjectSubmitJson.remove("listspendRequestHistoryPhaseModel");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //  jsonObjectsubmit = responseRealm.replaceAll("\\\\","");




    }

    public void submitLoginRequest(String jsonObject) {
        final ProgressDialog progressDialog=new ProgressDialog(NewOrderDetailsActivity.this);

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject);
        String url = IPOSAPI.WEB_SERVICE_NOSubmitOrder;

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
                            deleteItems(poNumber);
                            final JSONObject jsonObject1=new JSONObject(responseData);


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Util.showToast(jsonObject1.optString("message"));
                                    Intent mIntent = new Intent();
                                    setResult(6,mIntent);
                                    finish();
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




    private void setAllData(){

        Realm realm=Realm.getDefaultInstance();
        RealmOrderList realmOrderLists=realm.where(RealmOrderList.class).equalTo("poNumber","P00001").findFirst();

        if (realmOrderLists!=null){
            poNumber=realmOrderLists.getPoNumber();
            prepareData(realm,realmOrderLists);
            tvOrderName.setText(realmOrderLists.getPoNumber());
            OrderDate.setText(Util.getFormattedDates(realmOrderLists.getPoDate(),Constants.format6,Constants.format2));
            tvStatus.setText(realmOrderLists.getPoStatus());
            if (realmOrderLists.getPoStatus().equalsIgnoreCase("1")){
                tvStatus.setText("Submitted");
            }else if (realmOrderLists.getPoStatus().equalsIgnoreCase("2")){
                tvStatus.setText("Approved");
            }else if (realmOrderLists.getPoStatus().equalsIgnoreCase("3")){
                tvStatus.setText("Rejected");
            }else if (realmOrderLists.getPoStatus().equalsIgnoreCase("0")){
                tvStatus.setText("Pending");
            }

            orderValue.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getOrderValue());
            orderDiscount.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getDiscountValue());
            deliverDate.setText(Util.getFormattedDates(realmOrderLists.getDeliveryBy(),Constants.format6,Constants.format2));
           // deliverDate.setText(realmOrderLists.getDeliveryBy());
            loyaltyPoints.setText(realmOrderLists.getOrderLoyality()+"");
            accumulatedPoints.setText(realmOrderLists.getAccumulatedLoyality()+"");
            dAccumulatedPoints=realmOrderLists.getAccumulatedLoyality();
            totalPoints.setText(realmOrderLists.getTotalLoyality()+"");
            customerName.setText(Prefs.getStringPrefs(Constants.EntityName));
            discount.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getDiscountValue());
            tvOrderValue.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getOrderValue());
            tvTotalQty.setText(realmOrderLists.getQuantity()+"");
            tvTotalPriceBeforeGst.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getOrderValue()+"");
            tvCGSTPrice.setText("+ "+getResources().getString(R.string.Rs)+ " "+realmOrderLists.getTotalCGSTValue()+"");
            tvSGSTPrice.setText("+ "+getResources().getString(R.string.Rs)+ " "+realmOrderLists.getTotalSGSTValue()+"");
            tvRoundingOffPrice.setText(getResources().getString(R.string.Rs)+ " "+realmOrderLists.getTotalRoundingOffValue()+"");
            getAddressData(realmOrderLists.getBusinessPlaceCode()+"");
            try {
                JSONArray arrayCart=new JSONArray(realmOrderLists.getCartDetail());

                for (int p=0;p<arrayCart.length();p++){
                    JSONObject jsonObject=arrayCart.optJSONObject(p);
                    RecentOrderModal recentOrderModal=new RecentOrderModal();
                    recentOrderModal.setTitle(jsonObject.optString("materialName"));
                    recentOrderModal.setQty(""+jsonObject.optInt("materialQty"));
                    recentOrderModal.setDiscountValue(""+jsonObject.optInt("materialDiscountValue"));
                    recentOrderModal.setValue(""+jsonObject.optInt("materialValue"));

                    recentOrderModalArrayList.add(recentOrderModal);

                    recentOrdersListAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }




    }


    private void initializeComponent() {
        llRedeem=findViewById(R.id.llRedeem);
        deliverDate=findViewById(R.id.deliverDate);
        llDate=findViewById(R.id.llDate);
        llAccept=findViewById(R.id.llAccept);
        llCancel=findViewById(R.id.llCancel);
        tvOrderName = findViewById(R.id.tvOrderName);
        OrderDate = findViewById(R.id.OrderDate);
        tvStatus = findViewById(R.id.tvStatus);
        orderValue = findViewById(R.id.orderValue);
        orderDiscount = findViewById(R.id.orderDiscount);
        deliverDate = findViewById(R.id.deliverDate);
        loyaltyPoints = findViewById(R.id.loyaltyPoints);
        accumulatedPoints = findViewById(R.id.accumulatedPoints);
        totalPoints = findViewById(R.id.totalPoints);
        customerName = findViewById(R.id.customerName);
        discount = findViewById(R.id.discount);
        tvOrderValue = findViewById(R.id.tvOrderValue);


        tvTotalQty = findViewById(R.id.tvTotalQty);
        tvTotalPriceBeforeGst = findViewById(R.id.tvTotalPriceBeforeGst);
        tvCGSTPrice = findViewById(R.id.tvCGSTPrice);
        tvSGSTPrice = findViewById(R.id.tvSGSTPrice);
        tvRoundingOffPrice = findViewById(R.id.tvRoundingOffPrice);
        btnAccept = findViewById(R.id.btnAccept);
        btnAccept.setOnClickListener(this);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        recycler_viewRecentOrders =  findViewById(R.id.recycler_viewItems);
        GridLayoutManager mLayoutManager4 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewRecentOrders.setLayoutManager(mLayoutManager4);
        recycler_viewRecentOrders.addItemDecoration(new SpacesItemDecoration(10));


        recycler_viewAddress =  findViewById(R.id.recycler_viewAddress);
        GridLayoutManager mLayoutManager5 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewAddress.setLayoutManager(mLayoutManager5);
        recycler_viewAddress.addItemDecoration(new SpacesItemDecoration(10));
        llAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.animateView(view);
                submitLoginRequest(jsonObjectSubmitJson.toString());

            }
        });
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.animateView(view);
                deleteItems(poNumber);
                Intent mIntent = new Intent();
                setResult(6,mIntent);
                finish();
            }
        });

        llRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogOTP(NewOrderDetailsActivity.this,dAccumulatedPoints);
            }
        });
    }


    public void setHeader() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        toolbar.setTitle("New Order");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void getRecentOrdersData() {
        recentOrdersListAdapter = new NewOrderItemsDetailListAdapter(this, recentOrderModalArrayList);
        recycler_viewRecentOrders.setAdapter(recentOrdersListAdapter);
    }


    private void getAddressData(String businesplaceCode) {

        Realm realm=Realm.getDefaultInstance();
        RealmResults<RealmBusinessPlaces> realmBusinessPlaces=realm.where(RealmBusinessPlaces.class).findAll();
        for (int i = 0; i < realmBusinessPlaces.size(); i++) {
            RealmBusinessPlaces realmBusinessPlaces1 = new RealmBusinessPlaces();
            realmBusinessPlaces1.setHeader("Shipping Details "+(i+1));
            realmBusinessPlaces1.setBuisnessPlaceName(realmBusinessPlaces.get(i).getBuisnessPlaceName());
            realmBusinessPlaces1.setBuisnessPlaceId(realmBusinessPlaces.get(i).getBuisnessPlaceId());
            realmBusinessPlaces1.setBuisnessLocationStateCode(realmBusinessPlaces.get(i).getBuisnessLocationStateCode());
            if (businesplaceCode.equalsIgnoreCase(realmBusinessPlaces.get(i).getBuisnessPlaceId()+"")){
                realmBusinessPlaces1.setSelected(true);

            }

            addressList.add(realmBusinessPlaces1);
        }
        addressListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();


    }


    private void deleteItems(String poNumber){
        Realm realm1=Realm.getDefaultInstance();
        RealmResults<RealmNewOrderCart> allSorted = realm1.where(RealmNewOrderCart.class).findAll();
        RealmOrderList realmOrderList = realm1.where(RealmOrderList.class).equalTo(NoGetEntityEnums.poNumber.toString(),poNumber).findFirst();

        realm1.beginTransaction();

        try {
            allSorted.deleteAllFromRealm();
            realmOrderList.deleteFromRealm();
        }catch (Exception e){
            if (realm1.isInTransaction())
                realm1.cancelTransaction();

        }finally {
            if (realm1.isInTransaction())
                realm1.commitTransaction();
            if (!realm1.isClosed())
                realm1.close();
        }


    }


    private void redeemDialog(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyDialogFragment mMyDialogFragment = MyDialogFragment.newInstance();
        mMyDialogFragment.setDialogInfo(this,100,1,"","",this);
//        mMyDialogFragment.setArguments(args);
        mMyDialogFragment.show(fragmentManager, "Redeem");
    }

    @Override
    public void redeem(double pointsToRedeem, double pointsToRedeemValue) {

    }

    public void showDialogOTP(Activity activity, final double dAccumulatedPoints){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.redeem_point_dialog);
        ImageView imageViewCancel=(ImageView)dialog.findViewById(R.id.imageViewCancel);
        final EditText etOTP=(EditText)dialog.findViewById(R.id.etOTP);
        lLayoutView=(LinearLayout)dialog.findViewById(R.id.lLayoutView);
        llVerifyRedeem=(LinearLayout)dialog.findViewById(R.id.llVerifyRedeem);
        llRedeemValue=(LinearLayout)dialog.findViewById(R.id.llRedeemValue);
        etRedeemValue=(EditText)dialog.findViewById(R.id.etRedeemValue);
        TextView tvRedeemPoints = (TextView) dialog.findViewById(R.id.tvRedeemPoints);
         tvResendOTP=(TextView)dialog.findViewById(R.id.tvResendOTP); 
        tvRedeemPoints.setText(dAccumulatedPoints+"");
        buttonSendOtp=(Button)dialog.findViewById(R.id.buttonSendOtp);
        EditText etPointToRedeem=dialog.findViewById(R.id.etPointToRedeem);
        if (Util.validateString(etPointToRedeem.getText().toString()))
            pointstoRedeem = Double.parseDouble(etPointToRedeem.getText().toString());
        Button buttonSendOtp = (Button) dialog.findViewById(R.id.buttonSendOtp);
        Button buttonVerify = (Button) dialog.findViewById(R.id.buttonVerify);
        final double finalPointstoRedeem = pointstoRedeem;
        tvResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dAccumulatedPoints>0 && finalPointstoRedeem >0 && finalPointstoRedeem<=dAccumulatedPoints){
                    getOTP(finalPointstoRedeem);
                }else {
                    Util.showToast("please check your available points");
                }
            }
        });

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.validateString(etOTP.getText().toString())) {
                    getVerifyOTP(etOTP.getText().toString(), finalPointstoRedeem);
                    dialog.dismiss();
                } else {
                    Util.showToast("please enter otp");
                }
            }
        });
        buttonSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (dAccumulatedPoints>0 && finalPointstoRedeem >0 && finalPointstoRedeem<=dAccumulatedPoints){
                    getOTP(finalPointstoRedeem);
                }else {
                    Util.showToast("please check your available points");
                }

            }
        });

        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    public void getOTP(final double pointsToRedeem) {
        final ProgressDialog progressDialog=new ProgressDialog(NewOrderDetailsActivity.this);
        JSONObject jsonObject1=new JSONObject();

        try {
            jsonObject1.put("employeeCode",Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("employeeRole",Prefs.getStringPrefs(Constants.employeeRole));
            jsonObject1.put("entityRole",Prefs.getStringPrefs(Constants.entityRole));
            jsonObject1.put("entityCode",Prefs.getIntegerPrefs(Constants.entityCode));
            jsonObject1.put("pointsToRedeem",pointsToRedeem);
            jsonObject1.put("emailId",Prefs.getStringPrefs("email"));
            jsonObject1.put("requestOtp","NO");
            jsonObject1.put("entityStateCode","06");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.WEB_SERVICE_NOPointsRedeem;

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
                            final JSONObject jsonObject=new JSONObject(responseData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Util.showToast(jsonObject.optString("message"));
                                    perPoints=jsonObject.optDouble("pointsPer");
                                    tvResendOTP.setEnabled(false);
                                    llRedeemValue.setVisibility(View.VISIBLE);
                                    etRedeemValue.setText((perPoints*pointstoRedeem)+"");
                                    llVerifyRedeem.setVisibility(View.VISIBLE);
                                    lLayoutView.setVisibility(View.GONE);
                                    buttonSendOtp.setVisibility(View.GONE);
                                }
                            });
                        }


                    } else {
                        tvResendOTP.setEnabled(true);


                    }

                } catch (Exception e) {
                    e.printStackTrace();



                }
            }
        });
    }

    public void getVerifyOTP(final String code,double pointsToRedeem) {
        final ProgressDialog progressDialog=new ProgressDialog(NewOrderDetailsActivity.this);
        JSONObject jsonObject1=new JSONObject();

        try {
            jsonObject1.put("employeeCode",Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("employeeRole",Prefs.getStringPrefs(Constants.employeeRole));
            jsonObject1.put("entityRole",Prefs.getStringPrefs(Constants.entityRole));
            jsonObject1.put("entityCode",Prefs.getIntegerPrefs(Constants.entityCode));
            jsonObject1.put("pointsToRedeem",pointsToRedeem);
            jsonObject1.put("emailId",Prefs.getStringPrefs("email"));
            jsonObject1.put("requestOtp",code);
            jsonObject1.put("entityStateCode","06");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.WEB_SERVICE_ValidateNOCustomerRedeemPoint;

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
                            final JSONObject jsonObject=new JSONObject(responseData);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Util.showToast(jsonObject.optString("message"));

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

}
