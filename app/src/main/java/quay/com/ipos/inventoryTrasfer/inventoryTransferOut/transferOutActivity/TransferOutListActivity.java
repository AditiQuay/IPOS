//package quay.com.ipos.inventoryTrasfer.transferOut;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.text.Html;
//import android.view.GestureDetector;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.ExpandableListView;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import io.realm.Realm;
//import io.realm.RealmResults;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//import quay.com.ipos.IPOSAPI;
//import quay.com.ipos.R;
//import quay.com.ipos.base.BaseActivity;
//import quay.com.ipos.base.MainActivity;
//import quay.com.ipos.enums.NoGetEntityEnums;
//import quay.com.ipos.inventory.adapter.AddressListAdapter;
//import quay.com.ipos.inventory.adapter.NewOrderItemsDetailListAdapter;
//import quay.com.ipos.inventory.adapter.WorkFLowAdapter;
//import quay.com.ipos.inventory.adapter.WorkFLowUserAdapter;
//import quay.com.ipos.inventory.modal.NOGetEntityBuisnessPlacesModal;
//import quay.com.ipos.inventory.modal.NoGetEntityResultModal;
//import quay.com.ipos.inventory.modal.UserModal;
//import quay.com.ipos.inventoryTrasfer.TransferStepsActivity;
//import quay.com.ipos.inventoryTrasfer.adapter.TransferOutPendingAdapter;
//import quay.com.ipos.inventoryTrasfer.adapter.TransferOutShortAdapter;
//import quay.com.ipos.inventoryTrasfer.adapter.TransferOutTransitAdapter;
//import quay.com.ipos.inventoryTrasfer.modal.TransferOutListModal;
//import quay.com.ipos.listeners.MyListener;
//import quay.com.ipos.modal.OrderList;
//import quay.com.ipos.modal.RecentOrderModal;
//import quay.com.ipos.pss_order.activity.AddNewOrderActivity;
//import quay.com.ipos.pss_order.activity.OrderCentreDetailsActivity;
//import quay.com.ipos.pss_order.adapter.ExpandableListAdapter;
//import quay.com.ipos.pss_order.adapter.OrderCentreAccListAdapter;
//import quay.com.ipos.pss_order.adapter.OrderCentreCanListAdapter;
//import quay.com.ipos.pss_order.adapter.OrderCentreDelListAdapter;
//import quay.com.ipos.pss_order.adapter.OrderCentreDisListAdapter;
//import quay.com.ipos.pss_order.adapter.OrderCentreListAdapter;
//import quay.com.ipos.pss_order.fragment.OrderCentreListFragment;
//import quay.com.ipos.pss_order.modal.OrderCentreModal;
//import quay.com.ipos.realmbean.RealmBusinessPlaces;
//import quay.com.ipos.realmbean.RealmController;
//import quay.com.ipos.realmbean.RealmNewOrderCart;
//import quay.com.ipos.realmbean.RealmOrderCentreSummary;
//import quay.com.ipos.realmbean.RealmOrderList;
//import quay.com.ipos.service.APIClient;
//import quay.com.ipos.service.ServiceTask;
//import quay.com.ipos.utility.Constants;
//import quay.com.ipos.utility.Prefs;
//import quay.com.ipos.utility.SpacesItemDecoration;
//import quay.com.ipos.utility.Util;
//
///**
// * Created by aditi.bhuranda on 20-04-2018.
// */
//
//public class TransferOutListActivity extends BaseActivity implements View.OnClickListener{
//
//
//    private RecyclerView recyclerView;
//    private LinearLayout llNew, llAccepted, llDispatched;
//    private View vNew, vAccepted, vDispatched;
//    private TransferOutPendingAdapter mOrderCentreListAdapter;
//    private TransferOutTransitAdapter orderCentreAccListAdapter;
//
//    private TransferOutShortAdapter orderCentreDisListAdapter;
//
//    private ArrayList<TransferOutListModal> orderList= new ArrayList<>();
//    private ArrayList<TransferOutListModal> orderListA= new ArrayList<>();
//    private ArrayList<TransferOutListModal> orderListDis= new ArrayList<>();
//
//    private int mSelectedpos;
//    ExpandableListAdapter listAdapter;
//    ExpandableListView expListView;
//    ArrayList<String> listDataHeader;
//    HashMap<String, ArrayList<OrderList.Datum>> listDataChild;
//    TextView tvNew,tvAccepted,tvDispatched;
//    private LinearLayout btnViewAll;
//    TextView tvNewStatus;
//    private TextView tvNewStatusCannceled,tvNewStatusDelivered,tvNewStatusDispatch,tvNewStatusAccept;
//    private RecyclerView recycler_viewAccepted,recycler_viewDispatch;
//    private LinearLayout llNewRecyclerDispatch,llNewRecyclerAccept,llNewRecycler;
//
//    int click=1;
//    private String businessPlaceId="";
//
//    @Override
//    public void onCreate( Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.transfer_out_list_activity);
//
//        setHeader();
//
//        Intent i=getIntent();
//        businessPlaceId=i.getStringExtra("businessPlaceId");
//        clearData();
//        initializeComponent();
//        setAdapter();
//    }
//
//    private void clearData(){
//        Realm realm=Realm.getDefaultInstance();
//        RealmResults<RealmOrderCentreSummary> realmOrderCentreSummaries=realm.where(RealmOrderCentreSummary.class).findAll();
//
//        realm.beginTransaction();
//
//        try{
//            realmOrderCentreSummaries.deleteAllFromRealm();
//        }catch (Exception e){
//            realm.cancelTransaction();
//            e.printStackTrace();
//        }finally {
//            realm.commitTransaction();
//            realm.close();
//        }
//    }
//
//    private void setAdapter() {
//        if ( orderList.size()>0){
//
//            mOrderCentreListAdapter = new TransferOutPendingAdapter(TransferOutListActivity.this,this,recyclerView, orderList);
//            recyclerView.setAdapter(mOrderCentreListAdapter);
//        }
//        if (orderListA.size()>0){
//            orderCentreAccListAdapter = new TransferOutTransitAdapter(TransferOutListActivity.this,this,recycler_viewAccepted, orderListA);
//            recycler_viewAccepted.setAdapter(orderCentreAccListAdapter);
//        }
//        if (orderListDis.size()>0){
//            orderCentreDisListAdapter = new TransferOutShortAdapter(TransferOutListActivity.this,this,recycler_viewDispatch, orderListDis);
//            recycler_viewDispatch.setAdapter(orderCentreDisListAdapter);
//        }
//
//
//    }
//
//    private void setRealmData(int key){
//        orderList.clear();
//        orderListA.clear();
//        orderListDis.clear();
//
//        Realm realm=Realm.getDefaultInstance();
//        RealmResults<RealmOrderCentreSummary> realmOrderCentreSummaries=realm.where(RealmOrderCentreSummary.class).findAll();
//        orderList.clear();
//        if (realmOrderCentreSummaries.size()>0){
//
//            for (RealmOrderCentreSummary realmOrderCentreSummary:realmOrderCentreSummaries){
//
//                if (realmOrderCentreSummary.getId()==1){
//                    tvNew.setText(realmOrderCentreSummary.getCount()+"");
//                }
//                if (realmOrderCentreSummary.getId()==2){
//                    tvAccepted.setText(realmOrderCentreSummary.getCount()+"");
//                }
//                if (realmOrderCentreSummary.getId()==3){
//                    tvDispatched.setText(realmOrderCentreSummary.getCount()+"");
//                }
//
//
//                if (key==realmOrderCentreSummary.getId()) {
//                    JSONArray array1 = null;
//                    try {
//                        array1 = new JSONArray(realmOrderCentreSummary.getModel());
//                        for (int k = 0; k < array1.length(); k++) {
//                            JSONObject jsonObject = array1.optJSONObject(k);
//                            TransferOutListModal orderCentreModal = new TransferOutListModal();
//
//                            orderCentreModal.setDate(jsonObject.optString("tranDate"));
//                            orderCentreModal.setAmount(jsonObject.optDouble("totalTransferValue"));
//                            orderCentreModal.setId(jsonObject.optString("tranID"));
//                            orderCentreModal.setReceiverAddress(jsonObject.optString("receiverBusinessPlaceName"));
//                            orderCentreModal.setReceiverName("Receiver");
//                            orderCentreModal.setSenderAddress(jsonObject.optString("senderBusinessPlaceName"));
//                            orderCentreModal.setSenderName("Sender");
//
//
//
//                            if (realmOrderCentreSummary.getId()==1)
//                                orderList.add(orderCentreModal);
//                            if (realmOrderCentreSummary.getId()==2)
//                                orderListA.add(orderCentreModal);
//                            if (realmOrderCentreSummary.getId()==3)
//                                orderListDis.add(orderCentreModal);
//
//
//                        }
//
//
//
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }else if (key==6){
//                    JSONArray array1 = null;
//                    try {
//                        array1 = new JSONArray(realmOrderCentreSummary.getModel());
//
//                        for (int k = 0; k < array1.length(); k++) {
//                            JSONObject jsonObject = array1.optJSONObject(k);
//                            TransferOutListModal orderCentreModal = new TransferOutListModal();
//
//                            orderCentreModal.setDate("14 May 2018");
//                            orderCentreModal.setAmount(654444);
//                            orderCentreModal.setId("TR18190001");
//                            orderCentreModal.setReceiverAddress("KGM Traders, Palam Vihar,Haryana");
//                            orderCentreModal.setReceiverName("Receiver");
//                            orderCentreModal.setSenderAddress("KGM Traders, Kriti Nagar,Delhi,Haryana");
//                            orderCentreModal.setSenderName("Sender");
//                            if (realmOrderCentreSummary.getId()==1)
//                                orderList.add(orderCentreModal);
//                            if (realmOrderCentreSummary.getId()==2)
//                                orderListA.add(orderCentreModal);
//                            if (realmOrderCentreSummary.getId()==3)
//                                orderListDis.add(orderCentreModal);
//
//
//                        }
//
//
//
//
//
//
//
//
//                      /*  if (realmOrderCentreSummary.getId()==1 && orderList.size()>0){
//
//                            mOrderCentreListAdapter = new OrderCentreListAdapter(TransferOutListActivity.this,this,recyclerView, orderList);
//                            recyclerView.setAdapter(mOrderCentreListAdapter);
//                        }
//                        if (realmOrderCentreSummary.getId()==2 && orderListA.size()>0){
//                            orderCentreAccListAdapter = new OrderCentreAccListAdapter(TransferOutListActivity.this,this,recycler_viewAccepted, orderListA);
//                            recycler_viewAccepted.setAdapter(orderCentreAccListAdapter);
//                        }
//                        if (realmOrderCentreSummary.getId()==3 && orderListDis.size()>0){
//                            orderCentreDisListAdapter = new OrderCentreDisListAdapter(TransferOutListActivity.this,this,recycler_viewDispatch, orderListDis);
//                            recycler_viewDispatch.setAdapter(orderCentreDisListAdapter);
//                        }
//                        if (realmOrderCentreSummary.getId()==4 && orderListDel.size()>0){
//                            orderCentreDelListAdapter = new OrderCentreDelListAdapter(TransferOutListActivity.this,this,recycler_viewDelivered, orderListDel);
//                            recycler_viewDelivered.setAdapter(orderCentreDelListAdapter);
//
//                        }
//                        if (realmOrderCentreSummary.getId()==5 && orderListCan.size()>0){
//                            orderCentreCanListAdapter = new OrderCentreCanListAdapter(TransferOutListActivity.this,this,recycler_viewCancelled, orderListCan);
//                            recycler_viewCancelled.setAdapter(orderCentreCanListAdapter);
//                        }*/
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//
//
//            setAdapter();
//        }
//    }
//
//    private void initializeComponent() {
//
//        llNewRecycler=findViewById(R.id.llNewRecycler);
//        llNewRecyclerAccept=findViewById(R.id.llNewRecyclerAccept);
//        llNewRecyclerDispatch=findViewById(R.id.llNewRecyclerDispatch);
//
//        tvAccepted=findViewById(R.id.tvAccepted);
//        tvDispatched=findViewById(R.id.tvDispatched);
//        tvNewStatus=findViewById(R.id.tvNewStatus);
//
//
//        btnViewAll=findViewById(R.id.btnViewAll);
//        btnViewAll.setOnClickListener(this);
//
//        recyclerView = findViewById(R.id.recycler_view);
//        GridLayoutManager mLayoutManager2 = new GridLayoutManager(TransferOutListActivity.this, 1);
//        recyclerView.setLayoutManager(mLayoutManager2);
//        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
//
//        recycler_viewAccepted = findViewById(R.id.recycler_viewAccepted);
//        GridLayoutManager mLayoutManager8 = new GridLayoutManager(TransferOutListActivity.this, 1);
//        recycler_viewAccepted.setLayoutManager(mLayoutManager8);
//        recycler_viewAccepted.addItemDecoration(new SpacesItemDecoration(10));
//
//
//
//        recycler_viewDispatch = findViewById(R.id.recycler_viewDispatch);
//        GridLayoutManager mLayoutManager10 = new GridLayoutManager(TransferOutListActivity.this, 1);
//        recycler_viewDispatch.setLayoutManager(mLayoutManager10);
//        recycler_viewDispatch.addItemDecoration(new SpacesItemDecoration(10));
//
//        llNew = findViewById(R.id.llNew);
//        llAccepted = findViewById(R.id.llAccepted);
//        llDispatched = findViewById(R.id.llDispatched);
//
//        vNew = findViewById(R.id.vNew);
//        vAccepted = findViewById(R.id.vAccepted);
//        vDispatched = findViewById(R.id.vDispatched);
//
//        tvNew=findViewById(R.id.tvNew);
//        tvAccepted=findViewById(R.id.tvAccepted);
//
//        tvDispatched=findViewById(R.id.tvDispatched);
//
//        // get the listview
//        expListView = (ExpandableListView) findViewById(R.id.lvExp);
//        selectItemNew();
//        setExpandableAdapter();
//        llNew.setOnClickListener(this);
//        llAccepted.setOnClickListener(this);
//        llDispatched.setOnClickListener(this);
//
//        final GestureDetector mGestureDetector = new GestureDetector(TransferOutListActivity.this,
//                new GestureDetector.SimpleOnGestureListener() {
//
//                    @Override
//                    public boolean onSingleTapUp(MotionEvent e) {
//                        return true;
//                    }
//
//                });
//
//        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
//
//            @Override
//            public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {
//
//            }
//
//            @Override
//            public void onRequestDisallowInterceptTouchEvent(boolean arg0) {
//
//            }
//
//            @SuppressWarnings("deprecation")
//            @Override
//            public boolean onInterceptTouchEvent(RecyclerView arg0, MotionEvent motionEvent) {
//                View child = arg0.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
//
//                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
//                    mSelectedpos = arg0.getChildPosition(child);
////                    AppLog.e(TAG, "item:: " + arg0.getChildPosition(child));
////                    selectPinned(arg0.getChildPosition(child));
//
//                    if (Util.isConnected()) {
//                        if (click==1){
//                            Intent mIntent = new Intent(TransferOutListActivity.this, TransferStepsActivity.class);
//                            mIntent.putExtra("poNumber", orderList.get(mSelectedpos).getId());
//                            mIntent.putExtra("businessPlaceId", businessPlaceId);
//                            TransferOutListActivity.this.startActivity(mIntent);
//                        }else if (click==2){
//                            Intent mIntent = new Intent(TransferOutListActivity.this, TransferStepsActivity.class);
//                            mIntent.putExtra("poNumber", orderList.get(mSelectedpos).getId());
//                            mIntent.putExtra("businessPlaceId", businessPlaceId);   TransferOutListActivity.this.startActivity(mIntent);
//                        }else if (click==3){
//                            Intent mIntent = new Intent(TransferOutListActivity.this, TransferStepsActivity.class);
//                            mIntent.putExtra("poNumber", orderList.get(mSelectedpos).getId());
//                            mIntent.putExtra("businessPlaceId", businessPlaceId);  TransferOutListActivity.this.startActivity(mIntent);
//                        }
//
//                    }else {
//                        Util.showToast("No Internet Available");
//                    }
//                    return true;
//
//                }
//
//                return false;
//            }
//
//        });
//    }
//
//    private void setExpandableAdapter() {
//        // preparing list data
//        prepareListData();
//
//        listAdapter = new ExpandableListAdapter(TransferOutListActivity.this, listDataHeader, listDataChild);
//
//        // setting list adapter
//        expListView.setAdapter(listAdapter);
//    }
//
//
//
//
//    /*
//     * Preparing the list data
//     */
//    private void prepareListData() {
//        listDataHeader = new ArrayList<String>();
//        listDataChild = new HashMap<String, ArrayList<OrderList.Datum>>();
//
//        // Adding child data
//        listDataHeader.add("Top 250");
//        listDataHeader.add("Now Showing");
//        listDataHeader.add("Coming Soon..");
//
////        // Adding child data
////        ArrayList<OrderList.Datum> top250 = new ArrayList<String>();
////        top250.add("The Shawshank Redemption");
////        top250.add("The Godfather");
////        top250.add("The Godfather: Part II");
////        top250.add("Pulp Fiction");
////        top250.add("The Good, the Bad and the Ugly");
////        top250.add("The Dark Knight");
////        top250.add("12 Angry Men");
////
////        List<String> nowShowing = new ArrayList<String>();
////        nowShowing.add("The Conjuring");
////        nowShowing.add("Despicable Me 2");
////        nowShowing.add("Turbo");
////        nowShowing.add("Grown Ups 2");
////        nowShowing.add("Red 2");
////        nowShowing.add("The Wolverine");
////
////        List<String> comingSoon = new ArrayList<String>();
////        comingSoon.add("2 Guns");
////        comingSoon.add("The Smurfs 2");
////        comingSoon.add("The Spectacular Now");
////        comingSoon.add("The Canyons");
////        comingSoon.add("Europa Report");
//
////        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
////        listDataChild.put(listDataHeader.get(1), nowShowing);
////        listDataChild.put(listDataHeader.get(2), comingSoon);
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//
//        switch (id){
//            case R.id.llNew:
//                click=1;
//                selectItemNew();
//                setRealmData(1);
//                llNewRecycler.setVisibility(View.VISIBLE);
//                llNewRecyclerDispatch.setVisibility(View.GONE);
//                llNewRecyclerAccept.setVisibility(View.GONE);
//
//
//                break;
//
//            case R.id.llAccepted:
//                click=2;
//                selectItemAccepted();
//                setRealmData(2);
//                llNewRecycler.setVisibility(View.GONE);
//
//                llNewRecyclerDispatch.setVisibility(View.GONE);
//
//                llNewRecyclerAccept.setVisibility(View.VISIBLE);
//                break;
//
//            case R.id.llDispatched:
//                click=3;
//                selectItemDispatched();
//                setRealmData(3);
//                llNewRecycler.setVisibility(View.GONE);
//
//                llNewRecyclerDispatch.setVisibility(View.VISIBLE);
//
//                llNewRecyclerAccept.setVisibility(View.GONE);
//                break;
//
//            case R.id.llDelivered:
//                click=4;
//                selectItemDelivered();
//                setRealmData(4);
//                llNewRecycler.setVisibility(View.GONE);
//
//                llNewRecyclerDispatch.setVisibility(View.GONE);
//
//                llNewRecyclerAccept.setVisibility(View.GONE);
//                break;
//
//            case R.id.llCancelled:
//                click=5;
//                selectItemCancel();
//                setRealmData(5);
//                llNewRecycler.setVisibility(View.GONE);
//
//                llNewRecyclerDispatch.setVisibility(View.GONE);
//
//                llNewRecyclerAccept.setVisibility(View.GONE);
//                break;
//            case R.id.btnViewAll:
//                vNew.setVisibility(View.VISIBLE);
//                vAccepted.setVisibility(View.VISIBLE);
//
//                vDispatched.setVisibility(View.VISIBLE);
//
//                llNewRecycler.setVisibility(View.VISIBLE);
//
//                llNewRecyclerDispatch.setVisibility(View.VISIBLE);
//
//                llNewRecyclerAccept.setVisibility(View.VISIBLE);
//                setRealmData(6);
//                break;
//        }
//
//    }
//
//    private void selectItemNew() {
//        vNew.setVisibility(View.VISIBLE);
//        vAccepted.setVisibility(View.INVISIBLE);
//
//        vDispatched.setVisibility(View.INVISIBLE);
//
//    }
//
//    private void selectItemAccepted() {
//        vNew.setVisibility(View.INVISIBLE);
//        vAccepted.setVisibility(View.VISIBLE);
//
//        vDispatched.setVisibility(View.INVISIBLE);
//
//    }
//
//    private void selectItemDelivered() {
//        vNew.setVisibility(View.INVISIBLE);
//        vAccepted.setVisibility(View.INVISIBLE);
//
//        vDispatched.setVisibility(View.INVISIBLE);
//
//    }
//
//    private void selectItemDispatched() {
//        vNew.setVisibility(View.INVISIBLE);
//        vAccepted.setVisibility(View.INVISIBLE);
//
//        vDispatched.setVisibility(View.VISIBLE);
//
//    }
//
//
//    private void selectItemCancel() {
//        vNew.setVisibility(View.INVISIBLE);
//        vAccepted.setVisibility(View.INVISIBLE);
//
//        vDispatched.setVisibility(View.INVISIBLE);
//
//    }
//
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        getSummary("");
//       // ((MainActivity) TransferOutListActivity.this).setToolbarTitle(getString(R.string.order_centre));
//    }
//    public void getSummary(String jsonObject) {
//
//
//        final ProgressDialog progressDialog=new ProgressDialog(TransferOutListActivity.this);
//        progressDialog.show();
//
//        JSONObject jsonObject1=new JSONObject();
//
//        try {
//            jsonObject1.put("empCode", Prefs.getStringPrefs(Constants.employeeCode));
//
//            jsonObject1.put("businessPlaceId",businessPlaceId);
//
//            jsonObject1.put("tranID","string");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        OkHttpClient okHttpClient = APIClient.getHttpClient();
//        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
//        String url = IPOSAPI.WEB_SERVICE_TransferOutSummary;
//
//        final Request request = APIClient.getPostRequest(TransferOutListActivity.this, url, requestBody);
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, final IOException e) {
//                progressDialog.dismiss();
//
//            }
//
//            @Override
//            public void onResponse(Call call, final Response response) throws IOException {
//                progressDialog.dismiss();
//                try {
//                    if (response != null && response.isSuccessful()) {
//
//                        String responseData = response.body().string();
//                        if (responseData != null) {
//
//                            JSONObject jsonObject1=new JSONObject(responseData);
//
//                            JSONArray array=jsonObject1.optJSONArray("data");
//                            new RealmController().saveOrderCentreSummary(array.toString());
//
//
//                            TransferOutListActivity.this.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    setRealmData(1);
//                                }
//                            });
//
//
//
//                        }
//
//
//                    } else {
//                        TransferOutListActivity.this.runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Util.showToast("Something Went Wrong");
//                            }
//                        });
//
//
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//
//
//
//                }
//            }
//        });
//    }
//
//
//
//    public void setHeader() {
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
//        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                    finish();
//
//            }
//        });
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setHomeButtonEnabled(true);
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowCustomEnabled(true);
//            getSupportActionBar().setDisplayShowTitleEnabled(false);
//        }
//    }
//
//}
