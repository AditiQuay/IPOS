package quay.com.ipos.pss_order.fragment;

import android.Manifest;
import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.pss_order.activity.AddNewOrderActivity;
import quay.com.ipos.pss_order.activity.NewOrderDetailsActivity;
import quay.com.ipos.pss_order.activity.PinnedOrderActivity;
import quay.com.ipos.pss_order.adapter.CustomAdapter;
import quay.com.ipos.pss_order.adapter.NewOrderListAdapter;
import quay.com.ipos.pss_order.modal.DiscountModal;
import quay.com.ipos.pss_order.modal.NOGetEntityBuisnessPlacesModal;
import quay.com.ipos.pss_order.modal.NewOrderProductsResult;
import quay.com.ipos.pss_order.modal.NoGetEntityResultModal;
import quay.com.ipos.pss_order.modal.ProductSearchRequest;
import quay.com.ipos.enums.NoGetEntityEnums;
import quay.com.ipos.enums.RetailSalesEnum;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.MyCheckedChangedListener;
import quay.com.ipos.listeners.MyListenerProduct;
import quay.com.ipos.listeners.ScannerProductListener;

import quay.com.ipos.listeners.SendScannerBarcodeListener;
import quay.com.ipos.modal.NewOrderPinnedResults;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.realmbean.RealmOrderList;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.MessageDialog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 03-05-2018.
 */

public class NewOrderFragment extends BaseFragment implements SendScannerBarcodeListener,MyCheckedChangedListener,MyListenerProduct,ServiceTask.ServiceResultListener ,View.OnClickListener , CompoundButton.OnCheckedChangeListener ,AdapterListener,MessageDialog.MessageDialogListener,ScannerProductListener {
    private TextView tvMoreDetails, tvItemNo, tvItemQty, tvTotalItemPrice,
            tvTotalGST, tvTotalItemGSTPrice, tvTotalDiscountDetail, tvTotalDiscountPrice, tvCGSTPrice, tvSGSTPrice,
            tvLessDetails, tvRoundingOffPrice, tvPay, tvPinCount;

    RelativeLayout flScanLayout;
    private FrameLayout flScanner;
    private Fragment scanner_fragment;
    private LinearLayout llTotalDiscountDetail, ll_item_pay, llTotalGST;
    private ImageView imvPin, imvRight;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private String TAG = NewOrderFragment.class.getSimpleName();
    private NewOrderListAdapter mNewOrderListAdapter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private OrderList mOrderListResult;
    Dialog myDialog;
    double otcDiscount = 0.0;
    View rootView;
    private double totalAmount = 0;
    private boolean isFragmentDisplayed = true;
    private ArrayList<RealmNewOrderCart> mList = new ArrayList<>();

    Double afterDiscountPrice;
    ArrayList<NewOrderPinnedResults.Info> mOrderInfoArrayList = new ArrayList<>();
    private String json;
    private RelativeLayout llBelowPaymentDetail;
    private TextView tvMessage;
    private AppCompatSpinner spnAddress;
    private Context mContext;
    private ArrayList<NoGetEntityResultModal.BuisnessPlacesBean> noGetEntityBuisnessPlacesModals = new ArrayList<>();
    private String entityStateCode = "";
    private int businessPlaceCode;
    private boolean isSync;
    private String strPlace;
    private ImageView imvStatus;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_order_dashboard_dummy, container, false);
        initializeComponent(rootView);
        mContext = getActivity();
        myDialog = new Dialog(getActivity());
        setHasOptionsMenu(true);
        Util.hideSoftKeyboard(getActivity());

        closeFragment();
        try {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(listener,
                    new IntentFilter("BarcodeScan"));
        }catch (Exception e){

        }
        return rootView;
    }

 /*   RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.TOUCH_SLOP_PAGING || newState != RecyclerView.SCROLL_STATE_IDLE) {
                hideViews();
            } else {
                llBelowPaymentDetail.animate().alpha(1.0f).translationY(0).setInterpolator(new DecelerateInterpolator(1)).start();
                showViews();
            }
        }
    };
*/

    private void initializeComponent(View rootView) {
        tvMessage = rootView.findViewById(R.id.tvMessage);
        flScanner = rootView.findViewById(R.id.flScanner);

        flScanLayout = rootView.findViewById(R.id.flScanLayout);
        imvStatus = rootView.findViewById(R.id.imvStatus);
        tvPinCount = rootView.findViewById(R.id.tvPinCount);
        imvPin = rootView.findViewById(R.id.imvPin);

        imvRight = rootView.findViewById(R.id.imvRight);
        tvMoreDetails = rootView.findViewById(R.id.tvMoreDetails);
        tvItemNo = rootView.findViewById(R.id.tvItemNo);
        tvItemQty = rootView.findViewById(R.id.tvItemQty);
        tvTotalItemPrice = rootView.findViewById(R.id.tvTotalItemPrice);
        tvTotalGST = rootView.findViewById(R.id.tvTotalGST);
        tvTotalItemGSTPrice = rootView.findViewById(R.id.tvTotalItemGSTPrice);
        llTotalDiscountDetail = rootView.findViewById(R.id.llTotalDiscountDetail);
        tvTotalDiscountDetail = rootView.findViewById(R.id.tvTotalDiscountDetail);
        tvTotalDiscountPrice = rootView.findViewById(R.id.tvTotalDiscountPrice);
        tvCGSTPrice = rootView.findViewById(R.id.tvCGSTPrice);
        tvSGSTPrice = rootView.findViewById(R.id.tvSGSTPrice);
        tvLessDetails = rootView.findViewById(R.id.tvLessDetails);
        tvRoundingOffPrice = rootView.findViewById(R.id.tvRoundingOffPrice);
        ll_item_pay = rootView.findViewById(R.id.ll_item_pay);
        tvPay = rootView.findViewById(R.id.tvPay);
        llTotalGST = rootView.findViewById(R.id.llTotalGST);
        spnAddress = rootView.findViewById(R.id.spnAddress);
        mRecyclerView = rootView.findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dim_5),
                        getResources().getInteger(R.integer.photo_list_preview_columns)));
      //  mRecyclerView.addOnScrollListener(listener);
        llBelowPaymentDetail = rootView.findViewById(R.id.llBelowPaymentDetail);

        setSpinnerData();
        setListener();
        setAdapter();

    }

    private void setSpinnerData() {

        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strPlace=noGetEntityBuisnessPlacesModals.get(i).getBuisnessPlaceName();
                entityStateCode = noGetEntityBuisnessPlacesModals.get(i).getBuisnessLocationStateCode();
                businessPlaceCode = noGetEntityBuisnessPlacesModals.get(i).getBuisnessPlaceId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        showProgressDialog(mContext,R.string.msg_load_default);
        NOGetEntityBuisnessPlacesModal noGetEntityBuisnessPlacesModal = new NOGetEntityBuisnessPlacesModal();
        noGetEntityBuisnessPlacesModal.setEntityCode(Prefs.getIntegerPrefs(Constants.entityCode)+"");
        noGetEntityBuisnessPlacesModal.setEntityRole(Prefs.getStringPrefs(Constants.entityRole));
        noGetEntityBuisnessPlacesModal.setEntityType(Prefs.getStringPrefs(Constants.entityRole));


        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_NOGetEntityBuisnessPlaces);
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(noGetEntityBuisnessPlacesModal);
        mTask.setListener(this);
        mTask.setResultType(NoGetEntityResultModal.class);
        mTask.execute();

    }



    private void setListener() {
        tvMoreDetails.setOnClickListener(this);
        tvLessDetails.setOnClickListener(this);
        imvPin.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        imvRight.setOnClickListener(this);
        // Set the click listener for the button.
       /* chkBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ((MainActivity) getActivity()).launchActivity(true);
                    chkBarCode.setChecked(false);
                    setTextDefault();
                }else {
                    displayFragment();
                    if (flScanner.getVisibility()==View.GONE) {
                        flScanner.setVisibility(View.VISIBLE);
                        chkBarCode.setChecked(true);
                    } else {
                        flScanner.setVisibility(View.GONE);
                        chkBarCode.setChecked(false);
                    }
                }
            }
        });*/


        flScanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ((MainActivity) mContext).launchActivity(true);
//                    chkBarCode.setChecked(false);
                    flScanner.setVisibility(View.GONE);
                } else {

                    if (flScanner.getVisibility() == View.GONE) {
                        flScanner.setVisibility(View.VISIBLE);
                        imvStatus.setBackgroundResource(R.drawable.circle_activate);
//                        chkBarCode.setChecked(true);
                        displayFragment();
                    } else {
                        imvStatus.setBackgroundResource(R.drawable.circle_disabled);
                        flScanner.setVisibility(View.GONE);
                        closeFragment();
//                        chkBarCode.setChecked(false);
                    }
                }
            }
        });
    }

    public void closeFragment() {
        // Get the FragmentManager.
        FragmentManager fragmentManager = getChildFragmentManager();
        // Check to see if the fragment is already showing.
        NewOrderScannerFragment simpleFragment = (NewOrderScannerFragment) fragmentManager
                .findFragmentById(R.id.scanner_fragment);
        if (simpleFragment != null) {
            // Create and commit the transaction to remove the fragment.
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.remove(simpleFragment).commit();
        }
        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayed = false;
    }

    public void displayFragment() {
        NewOrderScannerFragment simpleFragment = NewOrderScannerFragment.newInstance();
        // TODO: Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        simpleFragment.setListener(this);
        // TODO: Add the SimpleFragment.
        // Add the SimpleFragment.
        // simpleFragment.setTargetFragment(RetailSalesFragment.this,2000);

        fragmentTransaction.add(R.id.scanner_fragment,
                simpleFragment).addToBackStack(null).commit();
        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true;
    }

    boolean isBack = false;

    @Override
    public void onResume() {
        super.onResume();
        getProduct();
        ((MainActivity) getActivity()).setToolbarTitle(getString(R.string.new_orders));
        //You need to add the following line for this solution to work; thanks skayred
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (IPOSApplication.mOrderList.size() >= 1) {
                        Util.showMessageDialog(getActivity(), NewOrderFragment.this, "Do you want to save the Cart?", "YES", "NO", Constants.APP_DIALOG_Cart, "", getActivity().getSupportFragmentManager());

                        isBack = true;
                    } else
                        isBack = false;

                }
                return isBack;
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                // Do onlick on menu action here
                onSearchButton();
                return true;
        }
        return false;
    }


    public void onSearchButton() {
        Intent mIntent = new Intent(getActivity(), AddNewOrderActivity.class);
        mIntent.putExtra(Constants.businessPlaceCode, businessPlaceCode);
        mIntent.putExtra(Constants.entityStateCode, entityStateCode);
        startActivityForResult(mIntent, 3);
    }

    private void setAdapter() {

        mNewOrderListAdapter = new NewOrderListAdapter(getActivity(), this, mRecyclerView, mList, this, this,this);
        mRecyclerView.setAdapter(mNewOrderListAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) // check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
//							AppUtil.LogMsg("...", "Last Item Wow !");
                            // Do pagination.. i.e. fetch new data
//
//                            mOffset = nextOffset + 1;
//                            nextOffset = nextOffset + mMaxlimit;
//                            callBelongList();
                        }
                    }
                }
            }
        });
    }

    int childPosition = -1;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3) {
            if (resultCode == 1) {
                getProduct();
            }
        } else if (requestCode == 5) {
            if (resultCode == 5) {
                childPosition = data.getIntExtra("pinned_order_position", 0);
                getProduct();
            }
        } else if (requestCode == 601) {
            if (resultCode == 6) {
                getProduct();

            }
        }

        Util.hideSoftKeyboard(getActivity());
    }

    private void getProduct() {


        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmNewOrderCart> realmNewOrderCarts1 = realm.where(RealmNewOrderCart.class).findAll();
        mList.clear();

        for (RealmNewOrderCart realmNewOrderCart : realmNewOrderCarts1) {
            RealmNewOrderCart realmNewOrderCarts = realm.copyFromRealm(realmNewOrderCart);

            mList.add(realmNewOrderCarts);
        }

        //  mList.addAll(realmNewOrderCarts1);
        mNewOrderListAdapter.notifyDataSetChanged();
        setCalculatedValues();


        if (mList.isEmpty()){
            tvMessage.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            // onSearchButton();
        }else{
            tvMessage.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
        }

    }

    private void setCalculatedValues() {

        tvItemNo.setText("Item 0");
        tvItemQty.setText("0 Qty");
        tvTotalItemPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvTotalItemGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvPay.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvCGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvSGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvRoundingOffPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvSGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscountPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscountDetail.setText("(Item 0)");

        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmNewOrderCart> realmNewOrderCarts1 = realm.where(RealmNewOrderCart.class).findAll();

        int qty = 0;
        double payAmount=0.0;
        int discountItems = 0;
        int gst = 0;
        int totalGST = 0;
        int cgst = 0;
        int sgst = 0;
        double totalItemsAmount = 0.0;
        double discountPrice = 0.0;
        int totalPoints = 0;
        int noOfItems = 0;
        for (RealmNewOrderCart realmNewOrderCart : realmNewOrderCarts1) {
            if (!realmNewOrderCart.isFreeItem())
                noOfItems = noOfItems + 1;

            qty = qty + realmNewOrderCart.getQty();
            totalItemsAmount = totalItemsAmount + realmNewOrderCart.getTotalPrice();
            if (realmNewOrderCart.isDiscount() && !realmNewOrderCart.isFreeItem()) {
                discountItems = discountItems + 1;
            }
            if (!realmNewOrderCart.isFreeItem()) {
                try {
                    JSONArray array = new JSONArray(realmNewOrderCart.getDiscount());
                    for (int k = 0; k < array.length(); k++) {
                        JSONObject jsonObject = array.optJSONObject(k);
                        if (jsonObject.has("discountTotal") && !jsonObject.optBoolean("discountTotalStrike")) {
                            discountPrice = discountPrice + jsonObject.optInt("discountTotal");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                discountPrice = discountPrice + realmNewOrderCart.getTotalPrice();
            }
            totalGST = (realmNewOrderCart.getGstPerc() * realmNewOrderCart.getTotalPrice() / 100);
            gst = gst + totalGST;



            cgst = cgst + (realmNewOrderCart.getCgst() * realmNewOrderCart.getTotalPrice() / 100);
            sgst = sgst + (realmNewOrderCart.getSgst() * realmNewOrderCart.getTotalPrice() / 100);




            totalPoints = totalPoints + realmNewOrderCart.getTotalPoints();

        }
        payAmount = (totalItemsAmount + gst) - discountPrice;

        tvItemNo.setText("Item " + noOfItems);
        tvItemQty.setText(qty + " Qty");
        tvTotalItemPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " + totalItemsAmount);
        tvTotalItemGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " + gst);
        tvPay.setText(getActivity().getResources().getString(R.string.Rs) + " " + payAmount);
        tvCGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " + cgst);
        tvSGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " + sgst);
        tvRoundingOffPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscountPrice.setText(getActivity().getResources().getString(R.string.Rs) + " "+discountPrice);
        tvTotalDiscountDetail.setText("(Item " + discountItems + ")");


    }






    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
//            case R.id.imvQRCode:
//                ((MainActivity) getActivity()).launchActivity(FullScannerActivity.class);
//                break;
            case R.id.tvMoreDetails:
                Util.animateView(view);
                llTotalDiscountDetail.setVisibility(View.VISIBLE);
                llTotalGST.setVisibility(View.GONE);
                break;
            case R.id.tvLessDetails:
                Util.animateView(view);
                llTotalDiscountDetail.setVisibility(View.GONE);
                llTotalGST.setVisibility(View.VISIBLE);
                break;

            case R.id.tvMinus:
                Util.animateView(view);
                setOnClickMinus(view);
                break;

            case R.id.tvPlus:
                Util.animateView(view);
                setOnClickPlus(view);
                break;


            case R.id.imvPin:
                cachedPinned(true);
                break;


            case R.id.imvClear:
                Util.animateView(view);
                final int posClear = (int) view.getTag();
                (
                        new AlertDialog.Builder(getActivity())).setTitle("Confirm action")
                        .setMessage("Do you want to Delete this Item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (mList.get(posClear).isFreeItem())
                                    deleteItemFree(mList.get(posClear).getiProductModalId());
                                else {
                                    deleteItems(mList.get(posClear).getiProductModalId());
                                }

                                getProduct();

                            }
                        }).setNegativeButton("No", null).show();

                break;
            case R.id.tvPay:
                Util.animateView(view);
                if (mList.size() > 0) {
                    createOrder();
                    Intent i = new Intent(getActivity(), NewOrderDetailsActivity.class);
                    i.putExtra(NoGetEntityEnums.OrderId.toString(), "P00001");
                  startActivityForResult(i,601);
                } else {
                    Util.showToast("Please add atleast one item to proceed.");
                }
                break;
            case R.id.imvRight:
                Util.animateView(view);
                if (mList.size()  > 0) {
                    createOrder();
                    Intent i = new Intent(getActivity(), NewOrderDetailsActivity.class);
                    i.putExtra(NoGetEntityEnums.OrderId.toString(), "P00001");

                    startActivityForResult(i,601);
                } else {
                    Util.showToast("Please add atleast one item to proceed.");
                }
                break;


        }
    }


    private void setOnClickPlus(View view) {
        Util.hideSoftKeyboard(getActivity());
        Util.animateView(view);
        int posPlus = (int) view.getTag();

        Realm realm = Realm.getDefaultInstance();
        RealmNewOrderCart realmNewOrderCarts = realm.where(RealmNewOrderCart.class).equalTo(NoGetEntityEnums.iProductModalId.toString(), mList.get(posPlus).getiProductModalId()).findFirst();
        Gson gson = new GsonBuilder().create();
        if (realmNewOrderCarts != null) {

            String strJson = gson.toJson(mList.get(posPlus));
            try {
                JSONObject jsonObject = new JSONObject(strJson);
                jsonObject.put(RetailSalesEnum.isAdded.toString(), true);
                jsonObject.put(RetailSalesEnum.qty.toString(), realmNewOrderCarts.getQty() + 1);
                jsonObject.put(RetailSalesEnum.totalPrice.toString(), (realmNewOrderCarts.getQty() + 1) * realmNewOrderCarts.getsProductPrice());

                int totalPoints = getTotalPoints(realmNewOrderCarts, (realmNewOrderCarts.getQty() + 1) * realmNewOrderCarts.getsProductPrice());
                jsonObject.put(RetailSalesEnum.totalPoints.toString(), totalPoints);
                saveResponseLocal(jsonObject, "P00001");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            setCalculatedValues();

            mNewOrderListAdapter.notifyItemChanged(posPlus);
            calculateOPS(realmNewOrderCarts.getProductCode(), realmNewOrderCarts.getiProductModalId());
             getProduct();
        }
    }


    private void calculateOPS(String productCode, String productId) {
        boolean isApplied = false;
        boolean isUpdateApplied = false;
        boolean solutionFound = false;

        JSONArray arrayRule = new JSONArray();
        JSONArray discountArray = new JSONArray();
        Realm realm = Realm.getDefaultInstance();
        RealmNewOrderCart realmNewOrderCarts = realm.where(RealmNewOrderCart.class).equalTo(RetailSalesEnum.isFreeItem.toString(), false).equalTo(RetailSalesEnum.iProductModalId.toString(), productId).findFirst();

        if (realmNewOrderCarts != null) {
            try {
                JSONArray array = new JSONArray(realmNewOrderCarts.getDiscount());


                for (int i = 0; i < array.length(); i++) {
                    double discount = 0.0;
                    JSONObject jsonObject = array.getJSONObject(i);
                    JSONArray array2 = jsonObject.getJSONArray("rule");
                    ArrayList<JSONObject> myJsonArrayAsList = new ArrayList<JSONObject>();
                    for (int d = 0; d < array2.length(); d++)
                        myJsonArrayAsList.add(array2.getJSONObject(d));

                    Collections.sort(myJsonArrayAsList, new Comparator<JSONObject>() {
                        @Override
                        public int compare(JSONObject jsonObjectA, JSONObject jsonObjectB) {
                            int compare = 0;
                            try {
                                int keyA = jsonObjectA.getInt("ruleSequence");
                                int keyB = jsonObjectB.getInt("ruleSequence");
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                    compare = Integer.compare(keyA, keyB);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return compare;
                        }
                    });
                    JSONArray array1 = new JSONArray();
                    for (int p = 0; p < myJsonArrayAsList.size(); p++) {
                        JSONObject object = myJsonArrayAsList.get(p);
                        array1.put(object);
                    }

                    arrayRule = array1;
                    for (int k = 0; k < array1.length(); k++) {
                        isApplied = false;
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
                        boolean isRuleApplied = jsonObject1.optBoolean(RetailSalesEnum.isRuleApplied.toString());

                        if (opsType.equalsIgnoreCase("P")) {
                            if (ruleType.equalsIgnoreCase("I")) {
                                if (packSize > 0) {

                                    if (sEligibilityBasedOn.equalsIgnoreCase("Q")) {

                                        isApplied = getQuantityBasedOnDiscountItems(isApplied, realmNewOrderCarts, slabFrom, slabTO, opsCriteria, sDiscountBasedOn, realm, packSize, productCode);

                                    } else {
                                        isApplied = getValueBasedOnDiscountItems(isApplied, realmNewOrderCarts, slabFrom, slabTO, opsCriteria, sDiscountBasedOn, realm, packSize, productCode);

                                    }

                                } else {

                                    if (sEligibilityBasedOn.equalsIgnoreCase("Q")) {

                                        discount = getQuantityBasedOnDiscountZeroPacksize(realmNewOrderCarts.getTotalPrice(), sDiscountType, sDiscountValue, realmNewOrderCarts, slabFrom, slabTO, opsCriteria, sDiscountBasedOn, realm, packSize, productCode);

                                        if (discount > 0) {
                                            isApplied = true;
                                        }
                                    } else {
                                        discount = getValueBasedOnDiscountZeroPacksize(realmNewOrderCarts.getTotalPrice(), sDiscountType, sDiscountValue, realmNewOrderCarts, slabFrom, slabTO, opsCriteria, sDiscountBasedOn, realm, packSize, productCode);
                                        if (discount > 0) {
                                            isApplied = true;
                                        }
                                    }
                                }

                            } else {


                                boolean isRulApplied = isCheckPrecessorApply(ruleProdecessors, jsonObject);
                                if (isRulApplied) {
                                    if (packSize > 0) {

                                        if (sEligibilityBasedOn.equalsIgnoreCase("Q")) {

                                            isApplied = getQuantityBasedOnDiscountItems(isApplied, realmNewOrderCarts, slabFrom, slabTO, opsCriteria, sDiscountBasedOn, realm, packSize, productCode);

                                        } else {
                                            isApplied = getValueBasedOnDiscountItems(isApplied, realmNewOrderCarts, slabFrom, slabTO, opsCriteria, sDiscountBasedOn, realm, packSize, productCode);

                                        }

                                    } else {

                                        if (sEligibilityBasedOn.equalsIgnoreCase("Q")) {

                                            discount = getQuantityBasedOnDiscountZeroPacksize(realmNewOrderCarts.getTotalPrice(), sDiscountType, sDiscountValue, realmNewOrderCarts, slabFrom, slabTO, opsCriteria, sDiscountBasedOn, realm, packSize, productCode);

                                            if (discount > 0) {
                                                isApplied = true;
                                            }
                                        } else {
                                            discount = getValueBasedOnDiscountZeroPacksize(realmNewOrderCarts.getTotalPrice(), sDiscountType, sDiscountValue, realmNewOrderCarts, slabFrom, slabTO, opsCriteria, sDiscountBasedOn, realm, packSize, productCode);
                                            if (discount > 0) {
                                                isApplied = true;
                                            }
                                        }
                                    }
                                } else {

                                    break;
                                }


                            }

                            // getOPSForProduct(sDiscountType,sDiscountValue,sEligibilityBasedOn,slabFrom,sDiscountBasedOn,slabTO,packSize,opsCriteria,ruleType,ruleID,ruleSequence,ruleProdecessors);


                        } else if (opsType.equalsIgnoreCase("V")) {

                        } else if (opsType.equalsIgnoreCase("O")) {

                        }

                        if (isApplied) {
                            isUpdateApplied = true;
                            jsonObject1.put(RetailSalesEnum.isRuleApplied.toString(), true);
                            arrayRule.put(k, jsonObject1);
                        } else {
                            jsonObject1.put(RetailSalesEnum.isRuleApplied.toString(), false);
                            arrayRule.put(k, jsonObject1);
                        }

                    }

                    jsonObject.put(RetailSalesEnum.rule.toString(), arrayRule);
                    jsonObject.put(RetailSalesEnum.discountTotal.toString(), discount);
                    discountArray.put(i, jsonObject);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (isUpdateApplied) {


                Gson gson = new GsonBuilder().create();
                String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts));
                try {
                    JSONObject jsonObject = new JSONObject(strJson);

                    jsonObject.put(RetailSalesEnum.discount.toString(), discountArray);
                    saveResponseLocal(jsonObject, "P00001");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private void checkPredecessor(int predecessor, boolean isRuleApplied, int ruleID, int ruleSequence, String ruleType) {


    }

    private boolean isCheckPrecessorApply(int predecessor, JSONObject jsonObject) {

        boolean solutionFound = false;
        try {
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
                boolean isRuleApplied = false;
                if (jsonObject1.has(RetailSalesEnum.isRuleApplied.toString())) {
                    isRuleApplied = jsonObject1.optBoolean(RetailSalesEnum.isRuleApplied.toString());
                }

                if (predecessor == ruleID) {
                    if (isRuleApplied) {
                        solutionFound = true;

                    } else {
                        solutionFound = false;
                    }


                    // getOPSForProduct(sDiscountType,sDiscountValue,sEligibilityBasedOn,slabFrom,sDiscountBasedOn,slabTO,packSize,opsCriteria,ruleType,ruleID,ruleSequence,ruleProdecessors);


                } else if (opsType.equalsIgnoreCase("V")) {

                } else if (opsType.equalsIgnoreCase("O")) {

                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return solutionFound;

    }

    private double getValueBasedOnDiscountZeroPacksize(int totalPrice, String sDiscountType, int sDiscountValue, RealmNewOrderCart realmNewOrderCarts, int slabFrom, int slabTO, String opsCriteria, String sDiscountBasedOn, Realm realm, int packSize, String productCode) {

        double discount = 0.0;
        int productQty = realmNewOrderCarts.getQty();

        if (totalPrice >= slabFrom && totalPrice <= slabTO || totalPrice > slabTO) {

            if (sDiscountBasedOn.equalsIgnoreCase("SP")) {


                discount = getDiscount(productQty, realmNewOrderCarts.getSalesPrice(), sDiscountType, sDiscountValue);


            } else if (sDiscountBasedOn.equalsIgnoreCase("nrv")) {
                discount = getDiscount(productQty, realmNewOrderCarts.getNrv(), sDiscountType, sDiscountValue);

            } else if (sDiscountBasedOn.equalsIgnoreCase("gpl")) {
                discount = getDiscount(productQty, realmNewOrderCarts.getGpl(), sDiscountType, sDiscountValue);

            } else if (sDiscountBasedOn.equalsIgnoreCase("mrp")) {
                discount = getDiscount(productQty, realmNewOrderCarts.getMrp(), sDiscountType, sDiscountValue);

            }

        }

        return discount;

    }

    private boolean getValueBasedOnDiscountItems(boolean isApplied, RealmNewOrderCart realmNewOrderCarts, int slabFrom, int slabTO, String opsCriteria, String sDiscountBasedOn, Realm realm, int packSize, String productCode) {

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

    private double getQuantityBasedOnDiscountZeroPacksize(int totalPrice, String sDiscountType, int sDiscountValue, RealmNewOrderCart realmNewOrderCarts, int slabFrom, int slabTO, String opsCriteria, String sDiscountBasedOn, Realm realm, int packSize, String productCode) {

        double discount = 0.0;
        int productQty = realmNewOrderCarts.getQty();

        if (productQty >= slabFrom && productQty <= slabTO || productQty > slabTO) {

            if (sDiscountBasedOn.equalsIgnoreCase("SP")) {


                discount = getDiscount(productQty, realmNewOrderCarts.getSalesPrice(), sDiscountType, sDiscountValue);


            } else if (sDiscountBasedOn.equalsIgnoreCase("nrv")) {
                discount = getDiscount(productQty, realmNewOrderCarts.getNrv(), sDiscountType, sDiscountValue);

            } else if (sDiscountBasedOn.equalsIgnoreCase("gpl")) {
                discount = getDiscount(productQty, realmNewOrderCarts.getGpl(), sDiscountType, sDiscountValue);

            } else if (sDiscountBasedOn.equalsIgnoreCase("mrp")) {
                discount = getDiscount(productQty, realmNewOrderCarts.getMrp(), sDiscountType, sDiscountValue);

            }

        }

        return discount;
    }

    private double getDiscount(int productQty, int salesPrice, String sDiscountType, int sDiscountValue) {

        double discount = 0.0;
        int price = productQty * salesPrice;
        if (sDiscountType.equalsIgnoreCase("p")) {

            discount = price * sDiscountValue / 100;


        } else {
            discount = price - sDiscountValue;
        }

        return discount;
    }

    private boolean getQuantityBasedOnDiscountItems(boolean isApplied, RealmNewOrderCart realmNewOrderCarts, int slabFrom, int slabTO, String opsCriteria, String sDiscountBasedOn, Realm realm, int packSize, String productCode) {

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

    private boolean getaddHighestFreeItems(RealmNewOrderCart realmNewOrderCarts, boolean isApplied, Realm realm, String opsCriteria, String productCode, int packSize, int slabFrom, int productQty) {


        RealmResults<RealmNewOrderCart> realmNewOrderCarts1 = realm.where(RealmNewOrderCart.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),false).findAllSorted(RetailSalesEnum.sProductPrice.toString(), Sort.DESCENDING);

        long   sum     = realmNewOrderCarts1.sum(RetailSalesEnum.qty.toString()).longValue();

        int countt= (int) sum;
        int loopSize = realmNewOrderCarts1.size();
        int itemsPerFree = countt / (packSize + slabFrom);

        int freeItems = 0;
        if (itemsPerFree > 0) {
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

                                saveResponseLocal(jsonObject, "P00001");
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

                                    saveResponseLocal(jsonObject, "P00001");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            freeItems = 0;

                        } else if (qty < freeItems) {
                            for (int m = 0; m < qty; m++) {
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

                                    saveResponseLocal(jsonObject, "P00001");
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
                                    jsonObject.put(RetailSalesEnum.totalPrice.toString(), (m + 1) * realmNewOrderCarts1.get(l).getsProductPrice());
                                    jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                    jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                    saveResponseLocal(jsonObject, "P00001");
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
                RealmResults<RealmNewOrderCart> allSorted = realm.where(RealmNewOrderCart.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),true).findAll();

                allSorted.deleteAllFromRealm();

            }
        return isApplied;

    }

    private boolean getaddLowestFreeItems(RealmNewOrderCart realmNewOrderCarts, boolean isApplied, Realm realm, String opsCriteria, String productCode, int packSize, int slabFrom, int productQty) {


        RealmResults<RealmNewOrderCart> realmNewOrderCarts1 = realm.where(RealmNewOrderCart.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),false).findAllSorted(RetailSalesEnum.sProductPrice.toString(), Sort.ASCENDING);
        long   sum     = realmNewOrderCarts1.sum(RetailSalesEnum.qty.toString()).longValue();

        int countt= (int) sum;
        int loopSize = realmNewOrderCarts1.size();
        int itemsPerFree = countt / (packSize + slabFrom);
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

                        saveResponseLocal(jsonObject, "P00001");
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

                                saveResponseLocal(jsonObject, "P00001");
                                isApplied = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        freeItems = 0;

                    } else if (qty < freeItems) {

                        for (int m = 0; m < qty; m++) {
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

                                saveResponseLocal(jsonObject, "P00001");
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

                                saveResponseLocal(jsonObject, "P00001");
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
            RealmResults<RealmNewOrderCart> allSorted = realm1.where(RealmNewOrderCart.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),true).findAll();

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


    private void getOPSForProduct(String sDiscountType, int sDiscountValue, String sEligibilityBasedOn, int slabFrom, String sDiscountBasedOn, int slabTO, int packSize, String opsCriteria, String ruleType, int ruleID, int ruleSequence, int ruleProdecessors) {



    }

    private int getTotalPoints(RealmNewOrderCart realmNewOrderCarts, int totalPrice){
        int totalPoints=0;
        if (realmNewOrderCarts.getPointsBasedOn().equalsIgnoreCase("M")){
            totalPoints=realmNewOrderCarts.getPoints()*totalPrice;

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

    private void setOnClickMinus(View view) {
        Util.hideSoftKeyboard(getActivity());
        Util.animateView(view);
        int posMinus = (int) view.getTag();

        Realm realm=Realm.getDefaultInstance();
        RealmNewOrderCart realmNewOrderCarts=realm.where(RealmNewOrderCart.class).equalTo(NoGetEntityEnums.iProductModalId.toString(),mList.get(posMinus).getiProductModalId()).findFirst();

        Gson gson = new GsonBuilder().create();
        if (realmNewOrderCarts!=null) {
if (realmNewOrderCarts.getQty()>1) {
    String strJson = gson.toJson(mList.get(posMinus));
    try {
        JSONObject jsonObject = new JSONObject(strJson);
        jsonObject.put(RetailSalesEnum.isAdded.toString(), true);
        jsonObject.put(RetailSalesEnum.qty.toString(), realmNewOrderCarts.getQty() - 1);
        jsonObject.put(RetailSalesEnum.totalPrice.toString(), (realmNewOrderCarts.getQty() - 1) * realmNewOrderCarts.getsProductPrice());

        int totalPoints=getTotalPoints(realmNewOrderCarts,(realmNewOrderCarts.getQty()-1)*realmNewOrderCarts.getsProductPrice());
        jsonObject.put(RetailSalesEnum.totalPoints.toString(),totalPoints);
        saveResponseLocal(jsonObject, "P00001");
    } catch (JSONException e) {
        e.printStackTrace();
    }
    setCalculatedValues();
    mNewOrderListAdapter.notifyItemChanged(posMinus);
    calculateOPS(realmNewOrderCarts.getProductCode(),realmNewOrderCarts.getiProductModalId());

    getProduct();

}else {
    Util.showToast("Quantity atleast one or greater than one.");
}


        }
    }




    @Override
    public void onRowClicked(int position) {



    }

    @Override
    public void onRowClicked(final int position, final int value) {

        if (position>=0) {
            Realm realm = Realm.getDefaultInstance();
            RealmNewOrderCart realmNewOrderCarts = realm.where(RealmNewOrderCart.class).equalTo(NoGetEntityEnums.iProductModalId.toString(), mList.get(position).getiProductModalId()).findFirst();
            Gson gson = new GsonBuilder().create();
            if (realmNewOrderCarts != null) {

                String strJson = gson.toJson(mList.get(position));
                try {
                    JSONObject jsonObject = new JSONObject(strJson);
                    jsonObject.put(RetailSalesEnum.qty.toString(), value);
                    jsonObject.put(RetailSalesEnum.totalPrice.toString(), value * realmNewOrderCarts.getsProductPrice());

                    int totalPoints = getTotalPoints(realmNewOrderCarts, value * realmNewOrderCarts.getsProductPrice());
                    jsonObject.put(RetailSalesEnum.totalPoints.toString(), totalPoints);
                    saveResponseLocal(jsonObject, "P00001");
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                setCalculatedValues();

                mNewOrderListAdapter.notifyItemChanged(position);
                calculateOPS(realmNewOrderCarts.getProductCode(), realmNewOrderCarts.getiProductModalId());
                getProduct();
            }
        }
        Util.hideSoftKeyboard(getActivity());
    }



    private void cachedPinned(boolean showScreen) {

        if(IPOSApplication.mOrderList!=null)
            if(IPOSApplication.mOrderList.size()>0) {

                if (SharedPrefUtil.getString(Constants.mOrderInfoArrayList, "", getActivity()) != null) {
                    String json2 = SharedPrefUtil.getString(Constants.mOrderInfoArrayList, "", getActivity());
                    if (!json2.equalsIgnoreCase(""))
                        mOrderInfoArrayList = Util.getCustomGson().fromJson(json2, new TypeToken<ArrayList<NewOrderPinnedResults.Info>>() {
                        }.getType());
                }


                NewOrderPinnedResults mPinnedResult = new NewOrderPinnedResults();
                NewOrderPinnedResults.Info mInfo = mPinnedResult.new Info();
                if (childPosition != -1) {
                    mInfo.setKey(mOrderInfoArrayList.get(childPosition).getKey());
                    mInfo.setData(IPOSApplication.mOrderList);
                    mOrderInfoArrayList.set(childPosition, mInfo);
                } else {
                    mInfo.setKey(Util.getCurrentTimeStamp());
                    mInfo.setData(IPOSApplication.mOrderList);
                    if (mOrderInfoArrayList == null) {
                        mOrderInfoArrayList = new ArrayList<>();
                    }
                    mOrderInfoArrayList.add(0, mInfo);
                }

                String json = Util.getCustomGson().toJson(mOrderInfoArrayList);
                SharedPrefUtil.putString(Constants.mOrderInfoArrayList, json, getActivity());
//            IPOSApplication.mOrderList.clear();
            }
        if (showScreen) {
            Intent mIntent = new Intent(getActivity(), PinnedOrderActivity.class);
            startActivityForResult(mIntent, 5);
            IPOSApplication.mOrderList.clear();
            mNewOrderListAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onDialogPositiveClick(Dialog dialog, int mCallType) {
        if(mCallType==Constants.APP_DIALOG_OTC) {
            dialog.dismiss();
        }else if(mCallType==Constants.APP_DIALOG_Cart){
            if(IPOSApplication.mOrderList.size()>0)
                cachedPinned(false);
            else
                Util.showToast("Cannot save empty list",getActivity());
            dialog.dismiss();
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onDialogNegetiveClick(Dialog dialog, int mCallType) {
        if(mCallType==Constants.APP_DIALOG_Cart){
            if(IPOSApplication.mOrderList.size()>0) {
                IPOSApplication.mOrderList.clear();
                getFragmentManager().popBackStack();
            }
            dialog.dismiss();

        }

    }

    @Override
    public void onDialogCancelClick(Dialog dialog, int mCallType) {

    }

    @Override
    public void setProductOnListener(String mDatum) {
     /*   ArrayList<OrderList.Datum> arrData= new ArrayList<>();
        json = SharedPrefUtil.getString(Constants.Order_List,"",getActivity());
        arrData = Util.getCustomGson().fromJson(json, new TypeToken<ArrayList<OrderList.Datum>>(){}.getType());
        IPOSApplication.mOrderList.add(arrData.get(0));
        mNewOrderListAdapter.notifyDataSetChanged();
*/
    }


    private void showViews() {
        // TODO uncomment this Hide Footer in android when Scrolling
        llBelowPaymentDetail.animate().alpha(1.0f).translationY(0).setInterpolator(new DecelerateInterpolator(1.4f)).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                llBelowPaymentDetail.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                llBelowPaymentDetail.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private void hideViews() {
        // TODO (+mToolbar)  plus means  2 view forward ho jaye or not visible to user
        llBelowPaymentDetail.animate().alpha(0f).translationY(+llBelowPaymentDetail.getHeight()).setInterpolator(new AccelerateInterpolator(1.4f)).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                llBelowPaymentDetail.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
     //   hideProgressDialog();

        if (serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_NOProductSearch)){

            if (isAdded()) {
                if (httpStatusCode == Constants.SUCCESS) {

                    if (Util.validateString(serverResponse)) {

                        try {
                            JSONObject jsonObject = new JSONObject(serverResponse);
                            addBarcodeScanProduct(jsonObject.optString("iProductModalId"), jsonObject.optString("productCode"), serverResponse);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Util.showToast("Product Scanned Successfully");
                        imvStatus.setBackgroundResource(R.drawable.circle_disabled);
                        flScanner.setVisibility(View.GONE);
                        closeFragment();

                    }


                } else if (httpStatusCode == Constants.BAD_REQUEST) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
                } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
                } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
                } else if (httpStatusCode == Constants.CONNECTION_OUT) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
                }
            }
        }else {
            if (httpStatusCode == Constants.SUCCESS) {

                if (Util.validateString(serverResponse)){

                    try {
                        JSONObject jsonObject=new JSONObject(serverResponse);
                        JSONArray array=jsonObject.optJSONArray(NoGetEntityEnums.buisnessPlaces.toString());
                        new RealmController().saveBusinessPlaces(array.toString());
                        for (int i=0;i<array.length();i++){
                            NoGetEntityResultModal.BuisnessPlacesBean noGetEntityBuisnessPlacesModal=new NoGetEntityResultModal.BuisnessPlacesBean();
                            JSONObject jsonObject1=array.optJSONObject(i);
                            noGetEntityBuisnessPlacesModal.setBuisnessPlaceId(jsonObject1.optInt(NoGetEntityEnums.buisnessPlaceId.toString()));
                            noGetEntityBuisnessPlacesModal.setBuisnessPlaceName(jsonObject1.optString(NoGetEntityEnums.buisnessPlaceName.toString()));
                            noGetEntityBuisnessPlacesModal.setBuisnessLocationStateCode(jsonObject1.optString(NoGetEntityEnums.buisnessLocationStateCode.toString()));
                            noGetEntityBuisnessPlacesModals.add(noGetEntityBuisnessPlacesModal);



                        }

                        CustomAdapter adapter = new CustomAdapter(mContext, R.layout.spinner_item_pss,R.id.text1,noGetEntityBuisnessPlacesModals);
                        adapter.setDropDownViewResource(R.layout.spinner_item_pss);
                        spnAddress.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


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


    }



    @Override
    public void onUpdateCart(int position) {


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



                realm.createOrUpdateObjectFromJson(RealmNewOrderCart.class, jsonSubmitReq);


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

    protected void saveResponseLocalCreateOrder(JSONObject jsonSubmitReq, String orderId) {
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
                jsonSubmitReq.put(NoGetEntityEnums.poNumber.toString(), orderId);
                if (isSync) {
                    jsonSubmitReq.put(Constants.ISUPDATE, false);
                } else {
                    jsonSubmitReq.put(Constants.ISSYNC, false);
                }



                realm.createOrUpdateObjectFromJson(RealmOrderList.class, jsonSubmitReq);


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


    private void createOrder(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmNewOrderCart> realmNewOrderCarts1 = realm.where(RealmNewOrderCart.class).findAll();

        JSONArray arrayCart=new JSONArray();
        int qty = 0;
        double payAmount=0.0;
        int discountItems = 0;
        int gst = 0;
        int totalGST = 0;
        int cgst = 0;
        int sgst = 0;
        double totalItemsAmount = 0.0;
        double discountPrice = 0.0;
        int totalPoints = 0;
        int noOfItems = 0;
        String poNumber = null;
        for (RealmNewOrderCart realmNewOrderCart : realmNewOrderCarts1) {

            JSONObject jsonObjectCartDetail=new JSONObject();
            if (!realmNewOrderCart.isFreeItem())
                noOfItems = noOfItems + 1;
            poNumber=realmNewOrderCart.getOrderId();
            qty = qty + realmNewOrderCart.getQty();
            totalItemsAmount = totalItemsAmount + realmNewOrderCart.getTotalPrice();
            if (realmNewOrderCart.isDiscount() && !realmNewOrderCart.isFreeItem()) {
                discountItems = discountItems + 1;
            }
            if (!realmNewOrderCart.isFreeItem()) {
                try {
                    JSONArray array = new JSONArray(realmNewOrderCart.getDiscount());
                    for (int k = 0; k < array.length(); k++) {
                        JSONObject jsonObject = array.optJSONObject(k);
                        if (jsonObject.has("discountTotal")) {
                            discountPrice = discountPrice + jsonObject.optInt("discountTotal");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                discountPrice = discountPrice + realmNewOrderCart.getTotalPrice();
            }
            totalGST = (realmNewOrderCart.getGstPerc() * realmNewOrderCart.getTotalPrice() / 100);
            gst = gst + totalGST;



            cgst = cgst + (realmNewOrderCart.getCgst() * realmNewOrderCart.getTotalPrice() / 100);
            sgst = sgst + (realmNewOrderCart.getSgst() * realmNewOrderCart.getTotalPrice() / 100);




            totalPoints = totalPoints + realmNewOrderCart.getTotalPoints();
            JSONArray scheme=new JSONArray();

            try {
                JSONArray discountArray = new JSONArray(realmNewOrderCart.getDiscount());
                for (int k = 0; k < discountArray.length(); k++) {
                    JSONObject jsonObjectScheme=new JSONObject();
                    JSONObject jsonObject = discountArray.optJSONObject(k);

                  JSONArray jsonArrayRule=  jsonObject.getJSONArray("rule");

                  for (int m=0;m<jsonArrayRule.length();m++){

                      JSONObject jsonObject1=jsonArrayRule.optJSONObject(m);
                      if (jsonObject1.optBoolean("isRuleApplied")){
                          jsonObjectScheme.put("schemeID",k+1);
                          jsonObjectScheme.put("ruleID",jsonObject1.optString("ruleID"));
                          jsonObjectScheme.put("discountValue",jsonObject.optString("discountTotal"));
                          jsonObjectScheme.put("discountPerc",jsonObject1.optString("sDiscountValue"));
                          jsonObjectScheme.put("oldSchemeID",k+1);
                          jsonObjectScheme.put("oldRuleID",jsonObject1.optString("ruleID"));


                      }

                  }
                  if (!jsonObjectScheme.toString().equalsIgnoreCase("{}"))
                    scheme.put(jsonObjectScheme);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }




            try {
                jsonObjectCartDetail.put("oldMaterialCode",realmNewOrderCart.getiProductModalId());
                jsonObjectCartDetail.put("materialCode",realmNewOrderCart.getiProductModalId());
                jsonObjectCartDetail.put("materialName",realmNewOrderCart.getsProductName());
                jsonObjectCartDetail.put("materialValue",realmNewOrderCart.getTotalPrice());
                jsonObjectCartDetail.put("materialQty",realmNewOrderCart.getQty());
                jsonObjectCartDetail.put("materialDiscountValue",discountPrice);
                jsonObjectCartDetail.put("materialUnitValue",realmNewOrderCart.getsProductPrice());
                jsonObjectCartDetail.put("materialCGSTRate",realmNewOrderCart.getCgst());
                jsonObjectCartDetail.put("materialCGSTValue",cgst);
                jsonObjectCartDetail.put("materialSGSTRate",realmNewOrderCart.getSgst());
                jsonObjectCartDetail.put("materialSGSTValue",sgst);
                jsonObjectCartDetail.put("materialIGSTRate",realmNewOrderCart.getGstPerc());
                jsonObjectCartDetail.put("materialIGSTValue",gst);
                jsonObjectCartDetail.put("scheme",scheme);
                jsonObjectCartDetail.put("discountValue",discountPrice);
                jsonObjectCartDetail.put("discountPerc",0);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            arrayCart.put(jsonObjectCartDetail);

        }
        payAmount = (totalItemsAmount + gst) - discountPrice;

        double totalValueWithTax=totalItemsAmount+gst;
        double totalValueWithoutTax=totalItemsAmount;
        JSONObject jsonObject=new JSONObject();


        try {
            jsonObject.put("employeeCode",Prefs.getStringPrefs(Constants.employeeCode));
            jsonObject.put("employeeRole",Prefs.getStringPrefs(Constants.employeeRole));
            jsonObject.put("poDate",Util.getCurrentDate());
            jsonObject.put("poStatus","Pending");
            jsonObject.put("orderValue",payAmount);
            jsonObject.put("discountValue",discountPrice);
            jsonObject.put("deliveryBy",Util.getCurrentDate());
            jsonObject.put("orderLoyality",totalPoints);
            jsonObject.put("accumulatedLoyality",0);
            jsonObject.put("totalLoyality",totalPoints);
            jsonObject.put("businessPlace",strPlace);
            jsonObject.put("businessPlaceCode",businessPlaceCode);
            jsonObject.put("entityID",Prefs.getIntegerPrefs(Constants.entityCode));
            jsonObject.put("totalValueWithTax",totalValueWithTax);
            jsonObject.put("totalCGSTValue",cgst);
            jsonObject.put("totalIGSTValue",gst);
            jsonObject.put("totalSGSTValue",sgst);
            jsonObject.put("totalValueWithoutTax",totalValueWithoutTax);
            jsonObject.put("totalTaxValue",cgst+sgst);
            jsonObject.put("totalDiscountValue",discountPrice);
            jsonObject.put("totalRoundingOffValue",0);
            jsonObject.put("cartDetail",arrayCart);
          //  jsonObject.put("listspendRequestHistoryPhaseModel",new JSONArray());
            jsonObject.put("approvalStat",1);
            jsonObject.put("quantity",qty);
            jsonObject.put("customerName",Prefs.getStringPrefs(Constants.EntityName));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        saveResponseLocalCreateOrder(jsonObject,"P00001");

    }

    private void deleteItems(String productId){
        Realm realm1=Realm.getDefaultInstance();
        RealmResults<RealmNewOrderCart> allSorted = realm1.where(RealmNewOrderCart.class).equalTo(RetailSalesEnum.iProductModalId.toString(),productId).or().equalTo(NoGetEntityEnums.parentProductId.toString(), productId).findAll();

        realm1.beginTransaction();

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
    private void deleteItemFree(String productId){
        Realm realm1=Realm.getDefaultInstance();
        RealmNewOrderCart allSorted = realm1.where(RealmNewOrderCart.class).equalTo(NoGetEntityEnums.iProductModalId.toString(), productId).equalTo(RetailSalesEnum.isFreeItem.toString(),true).findFirst();

        realm1.beginTransaction();

        try {
            allSorted.deleteFromRealm();
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


    private void getCheckBox(DiscountModal discountModal, String productId, int position,boolean strike){
        Realm realm=Realm.getDefaultInstance();
        RealmNewOrderCart realmNewOrderCart=realm.where(RealmNewOrderCart.class).equalTo(RetailSalesEnum.iProductModalId.toString(),productId).equalTo(RetailSalesEnum.isFreeItem.toString(),false).findFirst();
        Gson gson = new GsonBuilder().create();

        try {
            String responseRealm = gson.toJson(realm.copyFromRealm(realmNewOrderCart));
            JSONObject jsonObject = new JSONObject(responseRealm);
          JSONArray array=  new JSONArray(jsonObject.optString("discount").replaceAll("\\\\",""));
           JSONObject jsonObject1=array.getJSONObject(position);
            jsonObject1.put("discountTotalStrike",strike);
          //  jsonObject1.put("discountTotal",0);
           // discountModal.setDiscountTotal(0);
            array.put(position,jsonObject1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                jsonObject.put(RetailSalesEnum.discount.toString(), array);
            }
            saveResponseLocal(jsonObject, realmNewOrderCart.getOrderId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDiscount(DiscountModal discountModal, int position, boolean b, String productId, String productCode) {

        if (b){
            getCheckBox(discountModal,productId,position,false);
            calculateOPS(productCode,productId);
            getProduct();

        }else {
            getCheckBox(discountModal,productId,position,true);
            getProduct();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

    }


    private void addBarcodeScanProduct(String productId,String productCode,String json){

        Realm realm = Realm.getDefaultInstance();
        RealmNewOrderCart realmNewOrderCarts = realm.where(RealmNewOrderCart.class).equalTo(NoGetEntityEnums.iProductModalId.toString(), productId).findFirst();
        Gson gson = new GsonBuilder().create();
        if (realmNewOrderCarts != null) {

            String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts));
            try {
                JSONObject jsonObject = new JSONObject(strJson);
                jsonObject.put(RetailSalesEnum.isAdded.toString(), true);
                jsonObject.put(RetailSalesEnum.qty.toString(), realmNewOrderCarts.getQty() + 1);
                jsonObject.put(RetailSalesEnum.totalPrice.toString(), (realmNewOrderCarts.getQty() + 1) * realmNewOrderCarts.getsProductPrice());

                int totalPoints = getTotalPoints(realmNewOrderCarts, (realmNewOrderCarts.getQty() + 1) * realmNewOrderCarts.getsProductPrice());
                jsonObject.put(RetailSalesEnum.totalPoints.toString(), totalPoints);
                saveResponseLocal(jsonObject, "P00001");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setCalculatedValues();
            calculateOPS(realmNewOrderCarts.getProductCode(), realmNewOrderCarts.getiProductModalId());
            getProduct();
        }else {
            try {
                JSONObject jsonObject=new JSONObject(json);
                jsonObject.put(RetailSalesEnum.isAdded.toString(),true);
                jsonObject.put(RetailSalesEnum.qty.toString(),1);
                jsonObject.put(RetailSalesEnum.totalPrice.toString(),jsonObject.optDouble("SProductPrice"));
              //  int totalPoints=getTotalPoints(dataBeans.get(pos),dataBeans.get(pos).getSProductPrice());
            //    jsonObject.put(RetailSalesEnum.totalPoints.toString(),totalPoints);
                saveResponseLocal(jsonObject,"P00001");
                calculateOPS(productId,productCode);
                getProduct();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    private BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive( Context context, Intent intent ) {
            if (intent != null &&intent.getAction()!=null) {
                if (intent.getAction().equalsIgnoreCase("BarcodeScan")) {
                    String data = intent.getStringExtra("messageScan");
                    //  Log.e( "Received data : ", data);

                    searchProductCall(data);

                }
            }
        }
    };

    @Override
    public void onScanBarcode(String title, FragmentActivity activity) {
        mContext=activity;


    }

    private void searchProductCall(String s) {
//        showProgress(getResources().getString(R.string.please_wait));
        ProductSearchRequest productSearchRequest = new ProductSearchRequest();
        productSearchRequest.setEntityCode(Prefs.getIntegerPrefs(Constants.entityCode)+"");
        productSearchRequest.setEntityRole(Prefs.getStringPrefs(Constants.entityRole));
        productSearchRequest.setEntityStateCode(entityStateCode);
        productSearchRequest.setSearchParam("NA");
        productSearchRequest.setBusinessPlaceCode(businessPlaceCode+"");
        productSearchRequest.setBarCodeNumber(s);
        productSearchRequest.setModuleType("NO");
        productSearchRequest.setEmployeeCode(Prefs.getStringPrefs(Constants.employeeCode));
        productSearchRequest.setEmployeeRole(Prefs.getStringPrefs(Constants.employeeRole));
        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_NOProductSearch);
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(productSearchRequest);
        mTask.setListener(this);
        mTask.setResultType(NewOrderProductsResult.class);
        if(Util.isConnected())
            mTask.execute();
        else
            Util.showToast(getResources().getString(R.string.no_internet_connection_warning_server_error));
    }


}
