package quay.com.ipos.pss_order.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.base.MainActivity;
//import quay.com.ipos.pss_order.activity.OrderCentreDetailsActivity;
import quay.com.ipos.pss_order.activity.OrderCentreDetailsActivity;
import quay.com.ipos.pss_order.adapter.ExpandableListAdapter;
import quay.com.ipos.pss_order.adapter.OrderCentreAccListAdapter;
import quay.com.ipos.pss_order.adapter.OrderCentreCanListAdapter;
import quay.com.ipos.pss_order.adapter.OrderCentreDelListAdapter;
import quay.com.ipos.pss_order.adapter.OrderCentreDisListAdapter;
import quay.com.ipos.pss_order.adapter.OrderCentreListAdapter;
import quay.com.ipos.pss_order.modal.OrderCentreModal;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmOrderCentreSummary;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 08-05-2018.
 */

public class OrderCentreListFragment extends BaseFragment implements View.OnClickListener {

    private final String TAG = OrderCentreListFragment.class.getSimpleName();
    String[] inventory = {"KGM Traders", "KGM Traders"};

    private RecyclerView recyclerView;
    private LinearLayout llNew, llAccepted, llDispatched, llDelivered, llCancelled;
    private View vNew, vAccepted, vDispatched, vDelivered, vCancelled;
    private OrderCentreListAdapter mOrderCentreListAdapter;
    private OrderCentreAccListAdapter orderCentreAccListAdapter;
    private OrderCentreDelListAdapter orderCentreDelListAdapter;
    private OrderCentreDisListAdapter orderCentreDisListAdapter;
    private OrderCentreCanListAdapter orderCentreCanListAdapter;
//    private ArrayList<LowInventoryModal> responseList = new ArrayList<>();
    private ArrayList<OrderCentreModal> orderList= new ArrayList<>();
    private ArrayList<OrderCentreModal> orderListA= new ArrayList<>();
    private ArrayList<OrderCentreModal> orderListDis= new ArrayList<>();
    private ArrayList<OrderCentreModal> orderListDel= new ArrayList<>();
    private ArrayList<OrderCentreModal> orderListCan= new ArrayList<>();

    private int mSelectedpos;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    ArrayList<String> listDataHeader;
    HashMap<String, ArrayList<OrderList.Datum>> listDataChild;
    TextView tvNew,tvCancelled,tvDelivered,tvAccepted,tvDispatched;
    private LinearLayout btnViewAll;
    TextView tvNewStatus;
    private TextView tvNewStatusCannceled,tvNewStatusDelivered,tvNewStatusDispatch,tvNewStatusAccept;
    private RecyclerView recycler_viewAccepted,recycler_viewDispatch,recycler_viewDelivered,recycler_viewCancelled;
    private LinearLayout llNewRecyclerCancelled,llNewRecyclerDelivered,llNewRecyclerDispatch,llNewRecyclerAccept,llNewRecycler;

    int click=1;
    // newInstance constructor for creating fragment with arguments
    public OrderCentreListFragment newInstance(int position) {
        OrderCentreListFragment fragmentFirst = new OrderCentreListFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_centre, container, false);
        clearData();
        initializeComponent(view);
        setAdapter();



        return view;
    }



    private void clearData(){
        Realm realm=Realm.getDefaultInstance();
        RealmResults<RealmOrderCentreSummary> realmOrderCentreSummaries=realm.where(RealmOrderCentreSummary.class).findAll();

        realm.beginTransaction();

        try{
            realmOrderCentreSummaries.deleteAllFromRealm();
        }catch (Exception e){
            realm.cancelTransaction();
            e.printStackTrace();
        }finally {
            realm.commitTransaction();
            realm.close();
        }
    }

    private void setAdapter() {
       if ( orderList.size()>0){

                            mOrderCentreListAdapter = new OrderCentreListAdapter(getActivity(),this,recyclerView, orderList);
                            recyclerView.setAdapter(mOrderCentreListAdapter);
                        }
                        if (orderListA.size()>0){
                            orderCentreAccListAdapter = new OrderCentreAccListAdapter(getActivity(),this,recycler_viewAccepted, orderListA);
                            recycler_viewAccepted.setAdapter(orderCentreAccListAdapter);
                        }
                        if (orderListDis.size()>0){
                            orderCentreDisListAdapter = new OrderCentreDisListAdapter(getActivity(),this,recycler_viewDispatch, orderListDis);
                            recycler_viewDispatch.setAdapter(orderCentreDisListAdapter);
                        }
                        if (orderListDel.size()>0){
                            orderCentreDelListAdapter = new OrderCentreDelListAdapter(getActivity(),this,recycler_viewDelivered, orderListDel);
                            recycler_viewDelivered.setAdapter(orderCentreDelListAdapter);

                        }
                        if ( orderListCan.size()>0){
                            orderCentreCanListAdapter = new OrderCentreCanListAdapter(getActivity(),this,recycler_viewCancelled, orderListCan);
                            recycler_viewCancelled.setAdapter(orderCentreCanListAdapter);
                        }


    }

    private void setRealmData(int key){
        orderList.clear();
        orderListCan.clear();
        orderListA.clear();
        orderListDis.clear();
        orderListDel.clear();
        Realm realm=Realm.getDefaultInstance();
        RealmResults<RealmOrderCentreSummary> realmOrderCentreSummaries=realm.where(RealmOrderCentreSummary.class).findAll();
        orderList.clear();
        if (realmOrderCentreSummaries.size()>0){

            for (RealmOrderCentreSummary realmOrderCentreSummary:realmOrderCentreSummaries){

                if (realmOrderCentreSummary.getId()==1){
                    tvNew.setText(realmOrderCentreSummary.getCount()+"");
                }
                if (realmOrderCentreSummary.getId()==2){
                    tvAccepted.setText(realmOrderCentreSummary.getCount()+"");
                }
                if (realmOrderCentreSummary.getId()==3){
                    tvDispatched.setText(realmOrderCentreSummary.getCount()+"");
                }
                if (realmOrderCentreSummary.getId()==4){
                    tvDelivered.setText(realmOrderCentreSummary.getCount()+"");
                }
                if (realmOrderCentreSummary.getId()==5){
                    tvCancelled.setText(realmOrderCentreSummary.getCount()+"");
                }

                if (key==realmOrderCentreSummary.getId()) {
                    JSONArray array1 = null;
                    try {
                        array1 = new JSONArray(realmOrderCentreSummary.getModel());
                        for (int k = 0; k < array1.length(); k++) {
                            JSONObject jsonObject = array1.optJSONObject(k);
                            OrderCentreModal orderCentreModal = new OrderCentreModal();

                            orderCentreModal.setEtaDate(jsonObject.optString("etaDate"));
                            orderCentreModal.setItemQty(jsonObject.optInt("itemQty"));
                            orderCentreModal.setModifiedDate(jsonObject.optString("modifiedDate"));
                            orderCentreModal.setOrderValue(jsonObject.optInt("orderValue"));
                            orderCentreModal.setRequestCode(jsonObject.optString("requestCode"));
                            orderCentreModal.setTotalItem(jsonObject.optInt("totalItem"));

                            if (realmOrderCentreSummary.getId()==1)
                                orderList.add(orderCentreModal);
                            if (realmOrderCentreSummary.getId()==2)
                                orderListA.add(orderCentreModal);
                            if (realmOrderCentreSummary.getId()==3)
                                orderListDis.add(orderCentreModal);
                            if (realmOrderCentreSummary.getId()==4)
                                orderListDel.add(orderCentreModal);
                            if (realmOrderCentreSummary.getId()==5)
                                orderListCan.add(orderCentreModal);

                        }




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else if (key==6){
                    JSONArray array1 = null;
                    try {
                        array1 = new JSONArray(realmOrderCentreSummary.getModel());

                        for (int k = 0; k < array1.length(); k++) {
                            JSONObject jsonObject = array1.optJSONObject(k);
                            OrderCentreModal orderCentreModal = new OrderCentreModal();

                            orderCentreModal.setEtaDate(jsonObject.optString("etaDate"));
                            orderCentreModal.setItemQty(jsonObject.optInt("itemQty"));
                            orderCentreModal.setModifiedDate(jsonObject.optString("modifiedDate"));
                            orderCentreModal.setOrderValue(jsonObject.optInt("orderValue"));
                            orderCentreModal.setRequestCode(jsonObject.optString("requestCode"));
                            orderCentreModal.setTotalItem(jsonObject.optInt("totalItem"));

                            if (realmOrderCentreSummary.getId()==1)
                            orderList.add(orderCentreModal);
                            if (realmOrderCentreSummary.getId()==2)
                                orderListA.add(orderCentreModal);
                            if (realmOrderCentreSummary.getId()==3)
                                orderListDis.add(orderCentreModal);
                            if (realmOrderCentreSummary.getId()==4)
                                orderListDel.add(orderCentreModal);
                            if (realmOrderCentreSummary.getId()==5)
                            orderListCan.add(orderCentreModal);

                        }








                      /*  if (realmOrderCentreSummary.getId()==1 && orderList.size()>0){

                            mOrderCentreListAdapter = new OrderCentreListAdapter(getActivity(),this,recyclerView, orderList);
                            recyclerView.setAdapter(mOrderCentreListAdapter);
                        }
                        if (realmOrderCentreSummary.getId()==2 && orderListA.size()>0){
                            orderCentreAccListAdapter = new OrderCentreAccListAdapter(getActivity(),this,recycler_viewAccepted, orderListA);
                            recycler_viewAccepted.setAdapter(orderCentreAccListAdapter);
                        }
                        if (realmOrderCentreSummary.getId()==3 && orderListDis.size()>0){
                            orderCentreDisListAdapter = new OrderCentreDisListAdapter(getActivity(),this,recycler_viewDispatch, orderListDis);
                            recycler_viewDispatch.setAdapter(orderCentreDisListAdapter);
                        }
                        if (realmOrderCentreSummary.getId()==4 && orderListDel.size()>0){
                            orderCentreDelListAdapter = new OrderCentreDelListAdapter(getActivity(),this,recycler_viewDelivered, orderListDel);
                            recycler_viewDelivered.setAdapter(orderCentreDelListAdapter);

                        }
                        if (realmOrderCentreSummary.getId()==5 && orderListCan.size()>0){
                            orderCentreCanListAdapter = new OrderCentreCanListAdapter(getActivity(),this,recycler_viewCancelled, orderListCan);
                            recycler_viewCancelled.setAdapter(orderCentreCanListAdapter);
                        }*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }


            setAdapter();
        }
    }

    private void initializeComponent(View view) {

        llNewRecycler=view.findViewById(R.id.llNewRecycler);
        llNewRecyclerAccept=view.findViewById(R.id.llNewRecyclerAccept);
        llNewRecyclerCancelled=view.findViewById(R.id.llNewRecyclerCancelled);
        llNewRecyclerDelivered=view.findViewById(R.id.llNewRecyclerDelivered);
        llNewRecyclerDispatch=view.findViewById(R.id.llNewRecyclerDispatch);

        tvAccepted=view.findViewById(R.id.tvAccepted);
        tvCancelled=view.findViewById(R.id.tvCancelled);
        tvDelivered=view.findViewById(R.id.tvDelivered);
        tvDispatched=view.findViewById(R.id.tvDispatched);
        tvNewStatus=view.findViewById(R.id.tvNewStatus);


        btnViewAll=view.findViewById(R.id.btnViewAll);
        btnViewAll.setOnClickListener(this);

        recyclerView = view.findViewById(R.id.recycler_view);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager2);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));

        recycler_viewAccepted = view.findViewById(R.id.recycler_viewAccepted);
        GridLayoutManager mLayoutManager8 = new GridLayoutManager(getActivity(), 1);
        recycler_viewAccepted.setLayoutManager(mLayoutManager8);
        recycler_viewAccepted.addItemDecoration(new SpacesItemDecoration(10));

        recycler_viewCancelled = view.findViewById(R.id.recycler_viewCancelled);
        GridLayoutManager mLayoutManager7 = new GridLayoutManager(getActivity(), 1);
        recycler_viewCancelled.setLayoutManager(mLayoutManager7);
        recycler_viewCancelled.addItemDecoration(new SpacesItemDecoration(10));

        recycler_viewDelivered = view.findViewById(R.id.recycler_viewDelivered);
        GridLayoutManager mLayoutManager9 = new GridLayoutManager(getActivity(), 1);
        recycler_viewDelivered.setLayoutManager(mLayoutManager9);
        recycler_viewDelivered.addItemDecoration(new SpacesItemDecoration(10));

        recycler_viewDispatch = view.findViewById(R.id.recycler_viewDispatch);
        GridLayoutManager mLayoutManager10 = new GridLayoutManager(getActivity(), 1);
        recycler_viewDispatch.setLayoutManager(mLayoutManager10);
        recycler_viewDispatch.addItemDecoration(new SpacesItemDecoration(10));

        llNew = view.findViewById(R.id.llNew);
        llAccepted = view.findViewById(R.id.llAccepted);
        llDispatched = view.findViewById(R.id.llDispatched);
        llDelivered = view.findViewById(R.id.llDelivered);
        llCancelled = view.findViewById(R.id.llCancelled);
        vNew = view.findViewById(R.id.vNew);
        vAccepted = view.findViewById(R.id.vAccepted);
        vDispatched = view.findViewById(R.id.vDispatched);
        vDelivered = view.findViewById(R.id.vDelivered);
        vCancelled = view.findViewById(R.id.vCancelled);
        tvNew=view.findViewById(R.id.tvNew);
        tvAccepted=view.findViewById(R.id.tvAccepted);
        tvCancelled=view.findViewById(R.id.tvCancelled);
        tvDelivered=view.findViewById(R.id.tvDelivered);
        tvDispatched=view.findViewById(R.id.tvDispatched);

        // get the listview
        expListView = (ExpandableListView) view.findViewById(R.id.lvExp);
        selectItemNew();
    setExpandableAdapter();
        llNew.setOnClickListener(this);
        llAccepted.setOnClickListener(this);
        llDispatched.setOnClickListener(this);
        llDelivered.setOnClickListener(this);
        llCancelled.setOnClickListener(this);
        final GestureDetector mGestureDetector = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                });

        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean arg0) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean onInterceptTouchEvent(RecyclerView arg0, MotionEvent motionEvent) {
                View child = arg0.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    mSelectedpos = arg0.getChildPosition(child);
//                    AppLog.e(TAG, "item:: " + arg0.getChildPosition(child));
//                    selectPinned(arg0.getChildPosition(child));

                    if (Util.isConnected()) {
                        if (click==1){
                            Intent mIntent = new Intent(getActivity(), OrderCentreDetailsActivity.class);
                            mIntent.putExtra("etaDate", orderList.get(mSelectedpos).getEtaDate().replace("PO", ""));
                            mIntent.putExtra("requestCode", orderList.get(mSelectedpos).getRequestCode().replace("PO", ""));
                            getActivity().startActivity(mIntent);
                        }else if (click==2){
                            Intent mIntent = new Intent(getActivity(), OrderCentreDetailsActivity.class);
                            mIntent.putExtra("etaDate", orderListA.get(mSelectedpos).getEtaDate().replace("PO", ""));
                            mIntent.putExtra("requestCode", orderListA.get(mSelectedpos).getRequestCode().replace("PO", ""));
                            getActivity().startActivity(mIntent);
                        }else if (click==3){
                            Intent mIntent = new Intent(getActivity(), OrderCentreDetailsActivity.class);
                            mIntent.putExtra("etaDate", orderListDis.get(mSelectedpos).getEtaDate().replace("PO", ""));
                            mIntent.putExtra("requestCode", orderListDis.get(mSelectedpos).getRequestCode().replace("PO", ""));
                            getActivity().startActivity(mIntent);
                        }else if (click==4){
                            Intent mIntent = new Intent(getActivity(), OrderCentreDetailsActivity.class);
                            mIntent.putExtra("etaDate", orderListDel.get(mSelectedpos).getEtaDate().replace("PO", ""));
                            mIntent.putExtra("requestCode", orderListDel.get(mSelectedpos).getRequestCode().replace("PO", ""));
                            getActivity().startActivity(mIntent);
                        }else if (click==5){
                            Intent mIntent = new Intent(getActivity(), OrderCentreDetailsActivity.class);
                            mIntent.putExtra("etaDate", orderListCan.get(mSelectedpos).getEtaDate().replace("PO", ""));
                            mIntent.putExtra("requestCode", orderListCan.get(mSelectedpos).getRequestCode().replace("PO", ""));
                            getActivity().startActivity(mIntent);
                        }

                    }else {
                        Util.showToast("No Internet Available");
                    }
                    return true;

                }

                return false;
            }

        });
    }

    private void setExpandableAdapter() {
        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }




    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, ArrayList<OrderList.Datum>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

//        // Adding child data
//        ArrayList<OrderList.Datum> top250 = new ArrayList<String>();
//        top250.add("The Shawshank Redemption");
//        top250.add("The Godfather");
//        top250.add("The Godfather: Part II");
//        top250.add("Pulp Fiction");
//        top250.add("The Good, the Bad and the Ugly");
//        top250.add("The Dark Knight");
//        top250.add("12 Angry Men");
//
//        List<String> nowShowing = new ArrayList<String>();
//        nowShowing.add("The Conjuring");
//        nowShowing.add("Despicable Me 2");
//        nowShowing.add("Turbo");
//        nowShowing.add("Grown Ups 2");
//        nowShowing.add("Red 2");
//        nowShowing.add("The Wolverine");
//
//        List<String> comingSoon = new ArrayList<String>();
//        comingSoon.add("2 Guns");
//        comingSoon.add("The Smurfs 2");
//        comingSoon.add("The Spectacular Now");
//        comingSoon.add("The Canyons");
//        comingSoon.add("Europa Report");

//        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), nowShowing);
//        listDataChild.put(listDataHeader.get(2), comingSoon);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.llNew:
                click=1;
                selectItemNew();
                setRealmData(1);
                llNewRecycler.setVisibility(View.VISIBLE);
                llNewRecyclerDelivered.setVisibility(View.GONE);
                llNewRecyclerDispatch.setVisibility(View.GONE);
                llNewRecyclerCancelled.setVisibility(View.GONE);
                llNewRecyclerAccept.setVisibility(View.GONE);


                break;

            case R.id.llAccepted:
                click=2;
                selectItemAccepted();
                setRealmData(2);
                llNewRecycler.setVisibility(View.GONE);
                llNewRecyclerDelivered.setVisibility(View.GONE);
                llNewRecyclerDispatch.setVisibility(View.GONE);
                llNewRecyclerCancelled.setVisibility(View.GONE);
                llNewRecyclerAccept.setVisibility(View.VISIBLE);
                break;

            case R.id.llDispatched:
                click=3;
                selectItemDispatched();
                setRealmData(3);
                llNewRecycler.setVisibility(View.GONE);
                llNewRecyclerDelivered.setVisibility(View.GONE);
                llNewRecyclerDispatch.setVisibility(View.VISIBLE);
                llNewRecyclerCancelled.setVisibility(View.GONE);
                llNewRecyclerAccept.setVisibility(View.GONE);
                break;

            case R.id.llDelivered:
                click=4;
                selectItemDelivered();
                setRealmData(4);
                llNewRecycler.setVisibility(View.GONE);
                llNewRecyclerDelivered.setVisibility(View.VISIBLE);
                llNewRecyclerDispatch.setVisibility(View.GONE);
                llNewRecyclerCancelled.setVisibility(View.GONE);
                llNewRecyclerAccept.setVisibility(View.GONE);
                break;

            case R.id.llCancelled:
                click=5;
                selectItemCancel();
                setRealmData(5);
                llNewRecycler.setVisibility(View.GONE);
                llNewRecyclerDelivered.setVisibility(View.GONE);
                llNewRecyclerDispatch.setVisibility(View.GONE);
                llNewRecyclerCancelled.setVisibility(View.VISIBLE);
                llNewRecyclerAccept.setVisibility(View.GONE);
                break;
            case R.id.btnViewAll:
                vNew.setVisibility(View.VISIBLE);
                vAccepted.setVisibility(View.VISIBLE);
                vDelivered.setVisibility(View.VISIBLE);
                vDispatched.setVisibility(View.VISIBLE);
                vCancelled.setVisibility(View.VISIBLE);
                llNewRecycler.setVisibility(View.VISIBLE);
                llNewRecyclerDelivered.setVisibility(View.VISIBLE);
                llNewRecyclerDispatch.setVisibility(View.VISIBLE);
                llNewRecyclerCancelled.setVisibility(View.VISIBLE);
                llNewRecyclerAccept.setVisibility(View.VISIBLE);
                setRealmData(6);
                break;
        }

    }

    private void selectItemNew() {
        vNew.setVisibility(View.VISIBLE);
        vAccepted.setVisibility(View.INVISIBLE);
        vDelivered.setVisibility(View.INVISIBLE);
        vDispatched.setVisibility(View.INVISIBLE);
        vCancelled.setVisibility(View.INVISIBLE);
    }

    private void selectItemAccepted() {
        vNew.setVisibility(View.INVISIBLE);
        vAccepted.setVisibility(View.VISIBLE);
        vDelivered.setVisibility(View.INVISIBLE);
        vDispatched.setVisibility(View.INVISIBLE);
        vCancelled.setVisibility(View.INVISIBLE);
    }

    private void selectItemDelivered() {
        vNew.setVisibility(View.INVISIBLE);
        vAccepted.setVisibility(View.INVISIBLE);
        vDelivered.setVisibility(View.VISIBLE);
        vDispatched.setVisibility(View.INVISIBLE);
        vCancelled.setVisibility(View.INVISIBLE);
    }

    private void selectItemDispatched() {
        vNew.setVisibility(View.INVISIBLE);
        vAccepted.setVisibility(View.INVISIBLE);
        vDelivered.setVisibility(View.INVISIBLE);
        vDispatched.setVisibility(View.VISIBLE);
        vCancelled.setVisibility(View.INVISIBLE);
    }


    private void selectItemCancel() {
        vNew.setVisibility(View.INVISIBLE);
        vAccepted.setVisibility(View.INVISIBLE);
        vDelivered.setVisibility(View.INVISIBLE);
        vDispatched.setVisibility(View.INVISIBLE);
        vCancelled.setVisibility(View.VISIBLE);
    }


    @Override
    public void onResume() {
        super.onResume();
        getSummary("");
        ((MainActivity) getActivity()).setToolbarTitle(getString(R.string.order_centre));
    }
    public void getSummary(String jsonObject) {


        final ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.show();

        JSONObject jsonObject1=new JSONObject();

        try {
            jsonObject1.put("employeeCode", Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject1.put("employeeRole",Prefs.getStringPrefs(Constants.employeeRole));
            jsonObject1.put("businessCode","1");
            jsonObject1.put("entityID",Prefs.getIntegerPrefs(Constants.entityCode));
            jsonObject1.put("type","string");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        OkHttpClient okHttpClient = APIClient.getHttpClient();
        RequestBody requestBody = RequestBody.create(IPOSAPI.JSON, jsonObject1.toString());
        String url = IPOSAPI.WEB_SERVICE_NOSummary;

        final Request request = APIClient.getPostRequest(getActivity(), url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                progressDialog.dismiss();

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                progressDialog.dismiss();
                try {
                    if (response != null && response.isSuccessful()) {

                        String responseData = response.body().string();
                        if (responseData != null) {

                            JSONObject jsonObject1=new JSONObject(responseData);

                            JSONArray array=jsonObject1.optJSONArray("data");
                            new RealmController().saveOrderCentreSummary(array.toString());


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setRealmData(1);
                                }
                            });



                        }


                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Util.showToast("Something Went Wrong");
                            }
                        });


                    }

                } catch (Exception e) {
                    e.printStackTrace();



                }
            }
        });
    }

}
