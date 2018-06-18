package quay.com.ipos.pss_order.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.pss_order.adapter.AddOrderCentreAdapter;
import quay.com.ipos.pss_order.modal.NewOrderProductsResult;
import quay.com.ipos.pss_order.modal.ProductSearchRequest;
import quay.com.ipos.enums.NoGetEntityEnums;
import quay.com.ipos.enums.RetailSalesEnum;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.realmbean.RealmOrderCentre;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;


public class AddOrderCentreActivity extends BaseActivity implements View.OnClickListener,AdapterListener,ServiceTask.ServiceResultListener{

    private static final String TAG = quay.com.ipos.retailsales.activity.AddProductActivity.class.getSimpleName();
    ArrayList<OrderList.Datum> arrSearlist= new ArrayList<>();
    private EditText searchView;
    private RecyclerView mRecyclerView;
    private FloatingActionButton fab;
    private LinearLayoutManager mLayoutManager;
    private OrderList mOrderListResult;
    private TextView tvItemSize,tvNoItemAvailable;
    private AddOrderCentreAdapter mAddOrderCentreAdapter;
    private TextView tvClear,tvItemAddedSize;
    private String entityStateCode="";
    private int businessPlaceCode;
    ArrayList<NewOrderProductsResult> arrData= new ArrayList<>();
    ArrayList<NewOrderProductsResult.DataBean> dataBeans= new ArrayList<>();
    private boolean isSync;
    private LinearLayout llAccept;
    private String poNumber;
    private int postionCheckStock;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_add_list);
        setHeader();

        getIntentValues();
        initializeComponent();
        searchProductCall("NA");
        Realm realm=Realm.getDefaultInstance();
        RealmResults<RealmOrderCentre> realmNewOrderCarts1=realm.where(RealmOrderCentre.class).findAll();

        if (realmNewOrderCarts1!=null){
            tvItemAddedSize.setText(realmNewOrderCarts1.size()+"");

            if (realmNewOrderCarts1.size()>0){
                llAccept.setVisibility(View.VISIBLE);
            }else {
                llAccept.setVisibility(View.GONE);
            }
        }

        setAdapter();

    //    getProduct();
//            setDefaultValues();
    }

    private void getIntentValues() {

        Intent i=getIntent();
        if (i!=null){
            poNumber=i.getStringExtra("poNumber");
            entityStateCode=i.getStringExtra(Constants.entityStateCode);
            businessPlaceCode=i.getIntExtra(Constants.businessPlaceCode,0);
        }
    }

   /* private void setDefaultValues() {

        Double totalPrice;
        for(int i=0 ; i < arrData.size();i++ )
        {
            OrderList.Datum datum = arrData.get(i);
            if(datum.getQty()==0)
                datum.setQty(1);
            if(!datum.isDiscItemSelected())
                datum.setDiscItemSelected(true);
            totalPrice = (Double.parseDouble(datum.getSProductPrice()) * datum.getQty());
            datum.setTotalPrice(totalPrice);
            if(datum.getIsDiscount()) {
                Double discount = Double.parseDouble(datum.getSDiscountPrice()) * totalPrice / 100;
              //  datum.setDiscount(discount);
            }else {
             //   datum.setDiscount(0.0);
            }
            arrData.set(i,datum);
        }

    }*/
    private void setAdapter() {
        mAddOrderCentreAdapter = new AddOrderCentreAdapter(this,this,dataBeans,this);
        mRecyclerView.setAdapter(mAddOrderCentreAdapter);
    }
    public void setHeader() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setTitle("New Order");
        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
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

    private void initializeComponent() {
        tvItemAddedSize=findViewById(R.id.tvItemAddedSize);
        llAccept = findViewById(R.id.llAccept);
        llAccept.setOnClickListener(this);
        searchView = findViewById(R.id.searchView);
        searchView.setHint(getResources().getString(R.string.enter_product));
        searchView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mRecyclerView = findViewById(R.id.recycler_view);
        tvItemSize = findViewById(R.id.tvItemSize);
        tvClear = findViewById(R.id.tvClear);
        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        tvNoItemAvailable = findViewById(R.id.tvNoItemAvailable);

        tvClear.setOnClickListener(this);
        Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(tvClear, iconFont);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dim_3),
                        getResources().getInteger(R.integer.photo_list_preview_columns)));

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchProductCall(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


 /*   private void getProduct() {
        try {
            arrData.clear();
            String json = Util.getAssetJsonResponse(this, "product_list.json");
            mOrderListResult = Util.getCustomGson().fromJson(json,OrderList.class);
            AppLog.e(RetailSalesAdapter.class.getSimpleName(),Util.getCustomGson().toJson(mOrderListResult));
            arrData.addAll(mOrderListResult.getData());
            setDefaultValues();

//            Util.cacheData(arrData);
            SharedPrefUtil.putString(Constants.Order_List,Util.getCustomGson().toJson(arrData),this);
            mAddOrderCentreAdapter.notifyDataSetChanged();
//            setUpdateValues(IPOSApplication.mOrderList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/



    @Override
    public void onClick(final View view) {

        int id = view.getId();
        switch (id){
            case R.id.llAdd:
                Util.hideSoftKeyboard(AddOrderCentreActivity.this);
                int pos = (int) view.getTag();
                Realm realm=Realm.getDefaultInstance();
               RealmOrderCentre realmNewOrderCarts=realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.iProductModalId.toString(),dataBeans.get(pos).getIProductModalId()).findFirst();
                Gson gson = new GsonBuilder().create();
                if (realmNewOrderCarts!=null) {
                    realm.beginTransaction();
                    try {
                        realmNewOrderCarts.deleteFromRealm();
                        RealmResults<RealmOrderCentre> realmNewOrderCarts1=realm.where(RealmOrderCentre.class).findAll();

                        if (realmNewOrderCarts1!=null){
                            tvItemAddedSize.setText(realmNewOrderCarts1.size()+"");
                            if (realmNewOrderCarts1.size()>0){
                                llAccept.setVisibility(View.VISIBLE);
                            }else {
                                llAccept.setVisibility(View.GONE);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        realm.cancelTransaction();
                    }finally {
                        realm.commitTransaction();
                        realm.close();
                    }



                }else {

                    String strJson=gson.toJson(dataBeans.get(pos));
                    try {
                        JSONObject jsonObject=new JSONObject(strJson);
                        jsonObject.put(RetailSalesEnum.isAdded.toString(),true);
                        jsonObject.put(RetailSalesEnum.qty.toString(),1);
                        jsonObject.put(RetailSalesEnum.totalPrice.toString(),dataBeans.get(pos).getSProductPrice());
                        int totalPoints=getTotalPoints(dataBeans.get(pos),dataBeans.get(pos).getSProductPrice());
                        jsonObject.put(RetailSalesEnum.totalPoints.toString(),totalPoints);
                        saveResponseLocal(jsonObject,poNumber);
                        calculateOPS(dataBeans.get(pos).getProductCode(),dataBeans.get(pos).getIProductModalId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Realm realm1=Realm.getDefaultInstance();
                    RealmResults<RealmOrderCentre> realmNewOrderCarts1=realm1.where(RealmOrderCentre.class).equalTo(RetailSalesEnum.isFreeItem.toString(),false).findAll();

                    if (realmNewOrderCarts1!=null){
                        tvItemAddedSize.setText(realmNewOrderCarts1.size()+"");
                        if (realmNewOrderCarts1.size()>0){
                            llAccept.setVisibility(View.VISIBLE);
                        }else {
                            llAccept.setVisibility(View.GONE);
                        }

                    }
                }

                mAddOrderCentreAdapter.notifyItemChanged(pos);



                break;

            case R.id.tvClear:
                searchView.setText("");
                break;


            case R.id.llAccept:

                Intent mIntent1 = new Intent();
                setResult(1, mIntent1);
                onBackPressed();
                break;
            case R.id.llRefreshStocks:
                final int posDeleteCheckStock = (int) view.getTag();
                NewOrderProductsResult.DataBean realmNewOrderCart=dataBeans.get(posDeleteCheckStock);
                realmNewOrderCart.setmCheckStock(0);
                realmNewOrderCart.setCheckStockClick(false);
                dataBeans.set(posDeleteCheckStock,realmNewOrderCart);
                mAddOrderCentreAdapter.notifyItemChanged(posDeleteCheckStock);
                break;
            case R.id.tvCheckStock:
                Util.animateView(view);

                final int posCheck = (int) view.getTag();
                postionCheckStock=posCheck;
                if (dataBeans.size()  > 0) {
                    getCheckStockAPI(dataBeans.get(posCheck).getIProductModalId());
                } else {
                    Util.showToast("Please add atleast one item to proceed.");
                }
                break;
        }

    }
    private int getTotalPoints(NewOrderProductsResult.DataBean realmNewOrderCarts, int totalPrice){
        int totalPoints=0;
        if (realmNewOrderCarts.getPointsBasedOn().equalsIgnoreCase("M")){
            totalPoints=realmNewOrderCarts.getPoints();

        }else if (realmNewOrderCarts.getPointsBasedOn().equalsIgnoreCase("P")){
            int valuefrom=realmNewOrderCarts.getValueFrom();
            int valueTo=realmNewOrderCarts.getValueTo();
            int perPoints=realmNewOrderCarts.getPointsPer();
            int points=realmNewOrderCarts.getPoints();

            if (totalPrice>=valuefrom && totalPrice<=valueTo){
                totalPoints=perPoints*totalPrice/points;
            }else if (totalPrice>valueTo){
                totalPoints=perPoints*valueTo/points;
            }

        }else if (realmNewOrderCarts.getPointsBasedOn().equalsIgnoreCase("V")){
            int valuefrom=realmNewOrderCarts.getValueFrom();
            int valueTo=realmNewOrderCarts.getValueTo();
            int perPoints=realmNewOrderCarts.getPointsPer();
            int points=realmNewOrderCarts.getPoints();

            if (totalPrice>=valuefrom && totalPrice<=valueTo){
                totalPoints=perPoints*totalPrice/points;
            }else if (totalPrice>valueTo){
                totalPoints=perPoints*valueTo/points;
            }

        }

        return totalPoints;
    }

    @Override
    public void onRowClicked(int position) {

    }

    @Override
    public void onRowClicked(int position, int value) {
      /*  OrderList.Datum datum1 = arrSearlist.get(position);

        if ( value<=Integer.parseInt(datum1.getSProductPoints())) {

            datum1.setQty(value);
            arrSearlist.set(position, datum1);
        }else {
            datum1.setQty(Integer.parseInt(datum1.getSProductPoints()));
            arrSearlist.set(position, datum1);
            Util.showToast(datum1.getSProductPoints()+" "+getString(R.string.qty_available),this);
        }
//        mRecyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }});
        mAddOrderCentreAdapter.notifyItemChanged(position);
        Util.hideSoftKeyboard(this);*/
    }

    private void setOnClickPlus(View view) {
        Util.hideSoftKeyboard(this);
        Util.animateView(view);
        int posPlus = (int) view.getTag();
        OrderList.Datum datum1 =arrSearlist.get(posPlus);
        int qty1 = datum1.getQty();
        if(Integer.parseInt(datum1.getSProductPoints())<=qty1){
            Util.showToast("Quantity limit exceed",this);
        }else {
            datum1.setQty(qty1 + 1);
            arrSearlist.set(posPlus, datum1);
            mAddOrderCentreAdapter.notifyItemChanged(posPlus);

        }
    }

    private void setOnClickMinus(View view) {
        Util.hideSoftKeyboard(this);
        Util.animateView(view);
        int posMinus = (int) view.getTag();
        OrderList.Datum datum = arrSearlist.get(posMinus);
        int qty = datum.getQty();
        if(qty==1){
            Util.showToast("Cannot purchase with 0 quantity",this);
            return;
        }else {
            datum.setQty(qty - 1);
            arrSearlist.set(posMinus, datum);
            mAddOrderCentreAdapter.notifyItemChanged(posMinus);

        }
    }

    private void searchProductCall(String s) {
//        showProgress(getResources().getString(R.string.please_wait));
        ProductSearchRequest productSearchRequest = new ProductSearchRequest();
        productSearchRequest.setEntityCode(Prefs.getIntegerPrefs(Constants.entityCode)+"");
        productSearchRequest.setEntityRole(Prefs.getStringPrefs(Constants.entityRole));
        productSearchRequest.setEntityStateCode(entityStateCode);
        productSearchRequest.setSearchParam(s);
        productSearchRequest.setBusinessPlaceCode("1");
        productSearchRequest.setBarCodeNumber("All");
        productSearchRequest.setModuleType("NO");
        productSearchRequest.setEmployeeCode(Prefs.getStringPrefs(Constants.employeeCode));
        productSearchRequest.setEmployeeRole(Prefs.getStringPrefs(Constants.employeeRole));
        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_NOPRODUCTSEARCH);
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(productSearchRequest);
        mTask.setListener(this);
        mTask.setResultType(NewOrderProductsResult.class);
        if(Util.isConnected())
            mTask.execute();
        else
            Util.showToast(getResources().getString(R.string.no_internet_connection_warning_server_error));
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {





        if (httpStatusCode == Constants.SUCCESS) {

            if (Util.validateString(serverResponse)){

                NewOrderProductsResult noGetEntityResultModal=(NewOrderProductsResult)resultObj;
                arrData.add(noGetEntityResultModal);


                for (int i=0;i<arrData.size();i++){



                    dataBeans.addAll(arrData.get(0).getData());
                }

             /*   try {
                    JSONObject jsonObject=new JSONObject(serverResponse);
                    JSONArray array=jsonObject.optJSONArray(NoGetEntityEnums.data)

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/




                // dataBeans.add(arrData)

            }
            if(dataBeans.size()>0) {
                tvClear.setVisibility(View.VISIBLE);
                tvItemSize.setVisibility(View.VISIBLE);
            }
            else {
                tvClear.setVisibility(View.GONE);
                tvItemSize.setVisibility(View.GONE);
                arrData.clear();
            }
            tvItemSize.setText("Showing "+dataBeans.size() + " Products");
            mAddOrderCentreAdapter.notifyDataSetChanged();

            if(dataBeans.size()==0){
                tvNoItemAvailable.setVisibility(View.VISIBLE);
            }else
                tvNoItemAvailable.setVisibility(View.GONE);

        } else if (httpStatusCode == Constants.BAD_REQUEST) {
            Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
        }
    }


    protected void saveResponseLocal(JSONObject jsonSubmitReq, String orderId) {
        if (jsonSubmitReq != null) {
            Realm realm = Realm.getDefaultInstance();
            if (!realm.isInTransaction())
                realm.beginTransaction();
            try {
               /* if (Util.validateString(orderId)) {
                    jsonSubmitReq.put(NoGetEntityEnums.OrderId.toString(), orderId);
                } else {
                    if (jsonSubmitReq != null && !jsonSubmitReq.has(NoGetEntityEnums.OrderId.toString())) {
                        UUID randomId = UUID.randomUUID();
                        String id = String.valueOf(randomId);

                    }
                }*/
                jsonSubmitReq.put(NoGetEntityEnums.OrderId.toString(), orderId);
                if (isSync) {
                    jsonSubmitReq.put(Constants.ISUPDATE, false);
                } else {
                    jsonSubmitReq.put(Constants.ISSYNC, false);
                }



                realm.createOrUpdateObjectFromJson(RealmOrderCentre.class, jsonSubmitReq);


            } catch (Exception e) {
                if (realm.isInTransaction())
                    realm.cancelTransaction();
                if (!realm.isClosed())
                    realm.close();
            } finally {
                if (realm.isInTransaction())
                    realm.commitTransaction();
                if (!realm.isClosed())
                    realm.close();
            }
        }
    }

    private void calculateOPS(String productCode, String productId){
        boolean isApplied=false;
        boolean isUpdateApplied=false;
        boolean solutionFound=false;

        JSONArray arrayRule=new JSONArray();
        JSONArray discountArray=new JSONArray();
        Realm realm=Realm.getDefaultInstance();
        RealmOrderCentre realmNewOrderCarts=realm.where(RealmOrderCentre.class).equalTo(RetailSalesEnum.isFreeItem.toString(),false).equalTo(RetailSalesEnum.iProductModalId.toString(),productId).findFirst();

        if (realmNewOrderCarts!=null){
            try {
                JSONArray array=new JSONArray(realmNewOrderCarts.getDiscount());


                for (int i=0;i<array.length();i++){
                    double discount=0.0;
                    JSONObject jsonObject=array.getJSONObject(i);
                    JSONArray array2=jsonObject.getJSONArray("rule");
                    ArrayList<JSONObject> myJsonArrayAsList = new ArrayList<JSONObject>();
                    for (int d = 0; d < array2.length(); d++)
                        myJsonArrayAsList.add(array2.getJSONObject(d));

                    Collections.sort(myJsonArrayAsList, new Comparator<JSONObject>() {
                        @Override
                        public int compare(JSONObject jsonObjectA, JSONObject jsonObjectB) {
                            int compare = 0;
                            try
                            {
                                int keyA = jsonObjectA.getInt("ruleSequence");
                                int keyB = jsonObjectB.getInt("ruleSequence");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    compare = Integer.compare(keyA, keyB);
                                }
                            }
                            catch(JSONException e)
                            {
                                e.printStackTrace();
                            }
                            return compare;
                        }
                    });
                    JSONArray array1=new JSONArray();
                    for (int p=0;p<myJsonArrayAsList.size();p++){
                        JSONObject object=myJsonArrayAsList.get(p);
                        array1.put(object);
                    }

                    arrayRule=array1;
                    for (int k=0;k<array1.length();k++){
                        isApplied=false;
                        JSONObject  jsonObject1=array1.getJSONObject(k);
                        String sDiscountType=jsonObject1.optString("sDiscountType");
                        int sDiscountValue=jsonObject1.optInt("sDiscountValue");
                        String sEligibilityBasedOn=jsonObject1.optString("sEligibilityBasedOn");
                        int slabFrom=jsonObject1.optInt("slabFrom");
                        String sDiscountBasedOn=jsonObject1.optString("sDiscountBasedOn");
                        int slabTO=jsonObject1.optInt("slabTO");
                        int packSize=jsonObject1.optInt("packSize");
                        String opsCriteria=jsonObject1.optString("opsCriteria");
                        String ruleType=jsonObject1.optString("ruleType");
                        int ruleID=jsonObject1.optInt("ruleID");
                        int ruleSequence=jsonObject1.optInt("ruleSequence");
                        int ruleProdecessors=jsonObject1.optInt("ruleProdecessors");
                        String opsType=jsonObject1.optString("opsType");
                        boolean isRuleApplied=jsonObject1.optBoolean(RetailSalesEnum.isRuleApplied.toString());

                        if (opsType.equalsIgnoreCase("P")){
                            if (ruleType.equalsIgnoreCase("I")){
                                if (packSize>0){

                                    if (sEligibilityBasedOn.equalsIgnoreCase("Q")){

                                        isApplied=  getQuantityBasedOnDiscountItems(isApplied,realmNewOrderCarts,slabFrom,slabTO,opsCriteria,sDiscountBasedOn,realm,packSize,productCode);

                                    }else {
                                        isApplied=  getValueBasedOnDiscountItems(isApplied,realmNewOrderCarts,slabFrom,slabTO,opsCriteria,sDiscountBasedOn,realm,packSize,productCode);

                                    }

                                }else {

                                    if (sEligibilityBasedOn.equalsIgnoreCase("Q")){

                                        discount=   getQuantityBasedOnDiscountZeroPacksize(realmNewOrderCarts.getTotalPrice(),sDiscountType,sDiscountValue,realmNewOrderCarts,slabFrom,slabTO,opsCriteria,sDiscountBasedOn,realm,packSize,productCode);

                                        if (discount>0){
                                            isApplied=true;
                                        }
                                    }else {
                                        discount=   getValueBasedOnDiscountZeroPacksize(realmNewOrderCarts.getTotalPrice(),sDiscountType,sDiscountValue,realmNewOrderCarts,slabFrom,slabTO,opsCriteria,sDiscountBasedOn,realm,packSize,productCode);
                                        if (discount>0){
                                            isApplied=true;
                                        }
                                    }
                                }

                            }else {


/*

                                boolean isRulApplied=isCheckPrecessorApply(ruleProdecessors,jsonObject);
                                if (isRulApplied){
                                    if (packSize>0){

                                        if (sEligibilityBasedOn.equalsIgnoreCase("Q")){

                                            isApplied=  getQuantityBasedOnDiscountItems(isApplied,realmNewOrderCarts,slabFrom,slabTO,opsCriteria,sDiscountBasedOn,realm,packSize,productCode);

                                        }else {
                                            isApplied=  getValueBasedOnDiscountItems(isApplied,realmNewOrderCarts,slabFrom,slabTO,opsCriteria,sDiscountBasedOn,realm,packSize,productCode);

                                        }

                                    }else {

                                        if (sEligibilityBasedOn.equalsIgnoreCase("Q")){

                                            discount=   getQuantityBasedOnDiscountZeroPacksize(realmNewOrderCarts.getTotalPrice(),sDiscountType,sDiscountValue,realmNewOrderCarts,slabFrom,slabTO,opsCriteria,sDiscountBasedOn,realm,packSize,productCode);

                                            if (discount>0){
                                                isApplied=true;
                                            }
                                        }else {
                                            discount=   getValueBasedOnDiscountZeroPacksize(realmNewOrderCarts.getTotalPrice(),sDiscountType,sDiscountValue,realmNewOrderCarts,slabFrom,slabTO,opsCriteria,sDiscountBasedOn,realm,packSize,productCode);
                                            if (discount>0){
                                                isApplied=true;
                                            }
                                        }
                                    }
                                }else {

                                    break;
                                }

*/





                            }

                            // getOPSForProduct(sDiscountType,sDiscountValue,sEligibilityBasedOn,slabFrom,sDiscountBasedOn,slabTO,packSize,opsCriteria,ruleType,ruleID,ruleSequence,ruleProdecessors);


                        }else if (opsType.equalsIgnoreCase("V")){

                        }else if (opsType.equalsIgnoreCase("O")){

                        }

                        if (isApplied){
                            isUpdateApplied=true;
                            jsonObject1.put(RetailSalesEnum.isRuleApplied.toString(),true);
                            arrayRule.put(k,jsonObject1);
                        }else {
                            jsonObject1.put(RetailSalesEnum.isRuleApplied.toString(),false);
                            arrayRule.put(k,jsonObject1);
                        }

                    }

                    jsonObject.put(RetailSalesEnum.rule.toString(),arrayRule);
                    jsonObject.put(RetailSalesEnum.discountTotal.toString(),discount);
                    discountArray.put(i,jsonObject);

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (isUpdateApplied){


                Gson gson=new GsonBuilder().create();
                String strJson=gson.toJson(realm.copyFromRealm(realmNewOrderCarts));
                try {
                    JSONObject jsonObject=new JSONObject(strJson);

                    jsonObject.put(RetailSalesEnum.discount.toString(),discountArray);
                    saveResponseLocal(jsonObject,poNumber);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }






    }

    private void checkPredecessor(int predecessor, boolean isRuleApplied, int ruleID, int ruleSequence, String ruleType) {



    }

    private boolean isCheckPrecessorApply(int predecessor, JSONArray array) {

        boolean solutionFound=false;
        try {

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                JSONArray array1 = jsonObject.getJSONArray("rule");

                for (int k = 0; k < array1.length(); k++) {

                    JSONObject jsonObject1 = array1.getJSONObject(k);
                    String sDiscountType = jsonObject1.optString("sDiscountType");
                    int sDiscountValue = jsonObject1.optInt("sDiscountValue");
                    String sEligibilityBasedOn = jsonObject1.optString("sEligibilityBasedOn");
                    int slabFrom = jsonObject1.optInt("slabFrom");
                    String sDiscountBasedOn = jsonObject1.optString("sDiscountBasedOn");
                    int slabTO = jsonObject1.optInt("slabTO");
                    int packSize = jsonObject1.optInt("packSize");
                    String opsCriteria = jsonObject1.optString("opsCriteria");
                    String ruleType = jsonObject1.optString("ruleType");
                    int ruleID = jsonObject1.optInt("ruleID");
                    int ruleSequence = jsonObject1.optInt("ruleSequence");
                    int ruleProdecessors = jsonObject1.optInt("ruleProdecessors");
                    String opsType = jsonObject1.optString("opsType");
                    boolean isRuleApplied=jsonObject1.optBoolean(RetailSalesEnum.isRuleApplied.toString());
                    if (predecessor==ruleID){
                        if (isRuleApplied){
                            solutionFound=true;

                        } else {
                            solutionFound=false;
                        }



                        // getOPSForProduct(sDiscountType,sDiscountValue,sEligibilityBasedOn,slabFrom,sDiscountBasedOn,slabTO,packSize,opsCriteria,ruleType,ruleID,ruleSequence,ruleProdecessors);


                    } else if (opsType.equalsIgnoreCase("V")) {

                    } else if (opsType.equalsIgnoreCase("O")) {

                    }



                }


            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        return solutionFound;

    }

    private double getValueBasedOnDiscountZeroPacksize(int totalPrice, String sDiscountType, int sDiscountValue, RealmOrderCentre realmNewOrderCarts, int slabFrom, int slabTO, String opsCriteria, String sDiscountBasedOn, Realm realm, int packSize, String productCode) {

        double discount=0.0;
        int productQty=  realmNewOrderCarts.getQty();

        if (totalPrice>=slabFrom && totalPrice<=slabTO || totalPrice>slabTO){

            if (sDiscountBasedOn.equalsIgnoreCase("SP")){


                discount=  getDiscount(productQty,realmNewOrderCarts.getSalesPrice(),sDiscountType,sDiscountValue);




            }else if (sDiscountBasedOn.equalsIgnoreCase("nrv")){
                discount=  getDiscount(productQty,realmNewOrderCarts.getNrv(),sDiscountType,sDiscountValue);

            }else if (sDiscountBasedOn.equalsIgnoreCase("gpl")){
                discount=  getDiscount(productQty,realmNewOrderCarts.getGpl(),sDiscountType,sDiscountValue);

            }else if (sDiscountBasedOn.equalsIgnoreCase("mrp")){
                discount=  getDiscount(productQty,realmNewOrderCarts.getMrp(),sDiscountType,sDiscountValue);

            }

        }

        return discount;

    }

    private boolean getValueBasedOnDiscountItems(boolean isApplied, RealmOrderCentre realmNewOrderCarts, int slabFrom, int slabTO, String opsCriteria, String sDiscountBasedOn, Realm realm, int packSize, String productCode) {

        int productQty = realmNewOrderCarts.getsProductPrice();

        if (productQty >= slabFrom && productQty <= slabTO || productQty > slabTO) {

            if (sDiscountBasedOn.equalsIgnoreCase("SP")) {
                if (opsCriteria.equalsIgnoreCase("L")) {
                    isApplied = getaddLowestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                } else {
                    isApplied = getaddHighestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                }


            } else if (sDiscountBasedOn.equalsIgnoreCase("nrv")) {
                if (opsCriteria.equalsIgnoreCase("L")) {
                    isApplied = getaddLowestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                } else {
                    isApplied = getaddHighestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                }
            } else if (sDiscountBasedOn.equalsIgnoreCase("gpl")) {
                if (opsCriteria.equalsIgnoreCase("L")) {
                    isApplied = getaddLowestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                } else {
                    isApplied = getaddHighestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                }
            } else if (sDiscountBasedOn.equalsIgnoreCase("mrp")) {
                if (opsCriteria.equalsIgnoreCase("L")) {
                    isApplied = getaddLowestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                } else {
                    isApplied = getaddHighestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                }
            }

        }

        return isApplied;
    }
    private double getQuantityBasedOnDiscountZeroPacksize(int totalPrice, String sDiscountType, int sDiscountValue, RealmOrderCentre realmNewOrderCarts, int slabFrom, int slabTO, String opsCriteria, String sDiscountBasedOn, Realm realm, int packSize, String productCode) {

        double discount=0.0;
        int productQty=  realmNewOrderCarts.getQty();

        if (productQty>=slabFrom && productQty<=slabTO || productQty>slabTO){

            if (sDiscountBasedOn.equalsIgnoreCase("SP")){


                discount=  getDiscount(productQty,realmNewOrderCarts.getSalesPrice(),sDiscountType,sDiscountValue);




            }else if (sDiscountBasedOn.equalsIgnoreCase("nrv")){
                discount=  getDiscount(productQty,realmNewOrderCarts.getNrv(),sDiscountType,sDiscountValue);

            }else if (sDiscountBasedOn.equalsIgnoreCase("gpl")){
                discount=  getDiscount(productQty,realmNewOrderCarts.getGpl(),sDiscountType,sDiscountValue);

            }else if (sDiscountBasedOn.equalsIgnoreCase("mrp")){
                discount=  getDiscount(productQty,realmNewOrderCarts.getMrp(),sDiscountType,sDiscountValue);

            }

        }

        return discount;
    }

    private double getDiscount(int productQty, int salesPrice, String sDiscountType, int sDiscountValue) {

        double discount=0.0;
        int price=productQty*salesPrice;
        if (sDiscountType.equalsIgnoreCase("p")){

            discount=price*sDiscountValue/100;


        }else {
            discount=price-sDiscountValue;
        }

        return discount;
    }


    private boolean getQuantityBasedOnDiscountItems(boolean isApplied, RealmOrderCentre realmNewOrderCarts, int slabFrom, int slabTO, String opsCriteria, String sDiscountBasedOn, Realm realm, int packSize, String productCode) {

        int productQty = realmNewOrderCarts.getQty();
        if (productQty >= slabFrom && productQty <= slabTO || productQty > slabTO) {

            if (sDiscountBasedOn.equalsIgnoreCase("SP")) {
                if (opsCriteria.equalsIgnoreCase("L")) {
                    isApplied = getaddLowestFreeItems(realmNewOrderCarts,isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                } else {
                    isApplied = getaddHighestFreeItems(realmNewOrderCarts,isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                }


            } else if (sDiscountBasedOn.equalsIgnoreCase("nrv")) {
                if (opsCriteria.equalsIgnoreCase("L")) {
                    isApplied = getaddLowestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                } else {
                    isApplied = getaddHighestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                }
            } else if (sDiscountBasedOn.equalsIgnoreCase("gpl")) {
                if (opsCriteria.equalsIgnoreCase("L")) {
                    isApplied = getaddLowestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                } else {
                    isApplied = getaddHighestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                }
            } else if (sDiscountBasedOn.equalsIgnoreCase("mrp")) {
                if (opsCriteria.equalsIgnoreCase("L")) {
                    isApplied = getaddLowestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                } else {
                    isApplied = getaddHighestFreeItems(realmNewOrderCarts, isApplied, realm, opsCriteria, productCode, packSize, slabFrom, productQty);

                }
            }

        }
        return isApplied;
    }

    private boolean getaddHighestFreeItems(RealmOrderCentre realmNewOrderCarts, boolean isApplied, Realm realm, String opsCriteria, String productCode, int packSize, int slabFrom, int productQty) {


        RealmResults<RealmOrderCentre> realmNewOrderCarts1 = realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),false).findAllSorted(RetailSalesEnum.sProductPrice.toString(), Sort.DESCENDING);


        int itemsPerFree = productQty / (packSize + slabFrom);
        int freeItems = 0;
        if (itemsPerFree > 0) {
            freeItems = itemsPerFree * packSize;
            int loopSize = realmNewOrderCarts1.size();
            if (loopSize == 1) {
                for (int l = 0; l < loopSize; l++) {
                    if (freeItems>0) {
                        for (int m = 0; m < freeItems; m++) {
                            Gson gson = new GsonBuilder().create();
                            String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                            try {
                                JSONObject jsonObject = new JSONObject(strJson);
                                jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                jsonObject.put(RetailSalesEnum.qty.toString(), m + 1);
                                jsonObject.put(RetailSalesEnum.totalPrice.toString(), (m + 1) * realmNewOrderCarts1.get(l).getsProductPrice());
                                jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                saveResponseLocal(jsonObject, poNumber);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            } else {
                for (int l = 0; l < loopSize; l++) {
                    if (freeItems>0) {
                        int qty = realmNewOrderCarts1.get(l).getQty();
                        if (qty == freeItems) {
                            for (int m = 0; m < freeItems; m++) {
                                Gson gson = new GsonBuilder().create();
                                String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                                try {
                                    JSONObject jsonObject = new JSONObject(strJson);
                                    jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                    jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                    jsonObject.put(RetailSalesEnum.qty.toString(), m + 1);
                                    jsonObject.put(RetailSalesEnum.totalPrice.toString(), (m + 1) * realmNewOrderCarts1.get(l).getsProductPrice());
                                    jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                    jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                    saveResponseLocal(jsonObject, poNumber);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            freeItems = 0;

                        } else if (qty < freeItems) {
                            for (int m = 0; m <= qty; m++) {
                                Gson gson = new GsonBuilder().create();
                                String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                                try {
                                    JSONObject jsonObject = new JSONObject(strJson);
                                    jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                    jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                    jsonObject.put(RetailSalesEnum.qty.toString(), m + 1);
                                    jsonObject.put(RetailSalesEnum.totalPrice.toString(), (m + 1) * realmNewOrderCarts1.get(l).getsProductPrice());
                                    jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                    jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                    saveResponseLocal(jsonObject, poNumber);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                freeItems = freeItems - 1;
                            }


                        } else if (qty > freeItems) {
                            int size=freeItems;
                            for (int m = 0; m <= size; m++) {
                                Gson gson = new GsonBuilder().create();
                                String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                                try {
                                    JSONObject jsonObject = new JSONObject(strJson);
                                    jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                    jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                    jsonObject.put(RetailSalesEnum.qty.toString(), m + 1);
                                    jsonObject.put(RetailSalesEnum.totalPrice.toString(), (m + 1) * realmNewOrderCarts1.get(l).getsProductPrice());
                                    jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                    jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                    saveResponseLocal(jsonObject, poNumber);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                freeItems = freeItems - 1;
                            }


                        }
                    }
                }
            }
        }else{
            RealmResults<RealmOrderCentre> allSorted = realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),true).findAll();

            allSorted.deleteAllFromRealm();

        }
        return isApplied;

    }

    private boolean getaddLowestFreeItems(RealmOrderCentre realmNewOrderCarts, boolean isApplied, Realm realm, String opsCriteria, String productCode, int packSize, int slabFrom, int productQty) {


        RealmResults<RealmOrderCentre> realmNewOrderCarts1 = realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),false).findAllSorted(RetailSalesEnum.sProductPrice.toString(), Sort.ASCENDING);


        int loopSize = realmNewOrderCarts1.size();
        int itemsPerFree = productQty / (packSize + slabFrom);
        int freeItems = 0;
        if (itemsPerFree > 0){
            freeItems = itemsPerFree * packSize;
            if (loopSize == 1) {
                for (int l = 0; l < loopSize; l++) {
                    if (freeItems>0) {
                        for (int m = 0; m < freeItems; m++) {
                            Gson gson = new GsonBuilder().create();
                            String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                            try {
                                JSONObject jsonObject = new JSONObject(strJson);
                                jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                jsonObject.put(RetailSalesEnum.qty.toString(), m + 1);
                                jsonObject.put(RetailSalesEnum.totalPrice.toString(), (m + 1) * realmNewOrderCarts1.get(l).getsProductPrice());
                                jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                saveResponseLocal(jsonObject, poNumber);
                                isApplied = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            } else {
                for (int l = 0; l < loopSize; l++) {

                    if (freeItems>0) {
                        int qty = realmNewOrderCarts1.get(l).getQty();
                        if (qty == freeItems) {
                            for (int m = 0; m < freeItems; m++) {
                                Gson gson = new GsonBuilder().create();
                                String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                                try {
                                    JSONObject jsonObject = new JSONObject(strJson);
                                    jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                    jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                    jsonObject.put(RetailSalesEnum.qty.toString(), m + 1);
                                    jsonObject.put(RetailSalesEnum.totalPrice.toString(), (m + 1) * realmNewOrderCarts1.get(l).getsProductPrice());
                                    jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                    jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                    saveResponseLocal(jsonObject, poNumber);
                                    isApplied = true;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            freeItems = 0;

                        } else if (qty < freeItems) {

                            for (int m = 0; m <= qty; m++) {
                                Gson gson = new GsonBuilder().create();
                                String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                                try {
                                    JSONObject jsonObject = new JSONObject(strJson);
                                    jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                    jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                    jsonObject.put(RetailSalesEnum.qty.toString(), m + 1);
                                    jsonObject.put(RetailSalesEnum.totalPrice.toString(), (m + 1) * realmNewOrderCarts1.get(l).getsProductPrice());
                                    jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                    jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                    saveResponseLocal(jsonObject, poNumber);
                                    isApplied = true;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                freeItems = freeItems - 1;
                            }


                        } else if (qty > freeItems) {
                            int size=freeItems;
                            for (int m = 0; m < size; m++) {
                                Gson gson = new GsonBuilder().create();
                                String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                                try {
                                    JSONObject jsonObject = new JSONObject(strJson);
                                    jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                    jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                    jsonObject.put(RetailSalesEnum.qty.toString(), m + 1);
                                    jsonObject.put(RetailSalesEnum.totalPrice.toString(), (m + 1) * (realmNewOrderCarts1.get(l)).getsProductPrice());
                                    jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                    jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                    saveResponseLocal(jsonObject, poNumber);
                                    isApplied = true;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                freeItems = freeItems - 1;
                            }


                        }
                    }
                }
            }
        }else{
            Realm realm1=Realm.getDefaultInstance();
            RealmResults<RealmOrderCentre> allSorted = realm1.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),true).findAll();

            realm1.beginTransaction();
            isApplied = true;
            try {
                allSorted.deleteAllFromRealm();
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

        return isApplied;

    }

    public void getCheckStockAPI(String productId) {
        final ProgressDialog progressDialog=new ProgressDialog(AddOrderCentreActivity.this);
        JSONObject jsonObject1=new JSONObject();

        try {
            jsonObject1.put("employeeCode",Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("employeeRole",Prefs.getStringPrefs(Constants.employeeRole));
            jsonObject1.put("businessPlaceCode",businessPlaceCode);
            jsonObject1.put("entityRole",Prefs.getStringPrefs(Constants.entityRole));
            jsonObject1.put("entityCode",Prefs.getIntegerPrefs(Constants.entityCode));
            jsonObject1.put("searchParam",productId);
            jsonObject1.put("barCodeNumber","string");
            jsonObject1.put("moduleType","NO");
            jsonObject1.put("entityStateCode",entityStateCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progressDialog.show();
        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.WEB_SERVICE_CheckStock;

        final Request request = APIClient.getPostRequest(AddOrderCentreActivity.this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                progressDialog.dismiss();
                //  dismissProgress();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // dismissProgress();

                AddOrderCentreActivity.this.runOnUiThread(new Runnable() {
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

                            AddOrderCentreActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    NewOrderProductsResult.DataBean realmNewOrderCart=dataBeans.get(postionCheckStock);
                                    realmNewOrderCart.setmCheckStock(jsonObject.optInt("stockQty"));
                                    realmNewOrderCart.setCheckStockClick(true);
                                    dataBeans.set(postionCheckStock,realmNewOrderCart);
                                    mAddOrderCentreAdapter.notifyItemChanged(postionCheckStock);
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
