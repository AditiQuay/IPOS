package quay.com.ipos.pss_order.activity;

import android.Manifest;
import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.enums.NoGetEntityEnums;
import quay.com.ipos.enums.RetailSalesEnum;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.MyCheckedChangedListener;
import quay.com.ipos.listeners.MyListenerProduct;
import quay.com.ipos.listeners.ScannerProductListener;
import quay.com.ipos.listeners.SendScannerBarcodeListener;
import quay.com.ipos.modal.NewOrderPinnedResults;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.pss_order.adapter.CustomAdapter;
import quay.com.ipos.pss_order.adapter.EditOrderCentreListAdapter;
import quay.com.ipos.pss_order.fragment.OrderCentreScannerFragment;
import quay.com.ipos.pss_order.modal.DiscountModal;
import quay.com.ipos.pss_order.modal.NOGetEntityBuisnessPlacesModal;
import quay.com.ipos.pss_order.modal.NewOrderProductsResult;
import quay.com.ipos.pss_order.modal.NoGetEntityResultModal;
import quay.com.ipos.pss_order.modal.ProductSearchRequest;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmOrderCentre;
import quay.com.ipos.realmbean.RealmOrderList;
import quay.com.ipos.service.APIClient;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.InformationDialogNewOrder;
import quay.com.ipos.ui.InformationDialogNewOrderEdit;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.MessageDialog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 03-05-2018.
 */

public class EditOrderCentreActivity extends AppCompatActivity implements SendScannerBarcodeListener,MyCheckedChangedListener,MyListenerProduct,ServiceTask.ServiceResultListener ,View.OnClickListener , CompoundButton.OnCheckedChangeListener ,AdapterListener,MessageDialog.MessageDialogListener,ScannerProductListener {
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
    private String TAG = EditOrderCentreActivity.class.getSimpleName();
    private EditOrderCentreListAdapter mNewOrderListAdapter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private OrderList mOrderListResult;
    Dialog myDialog;
    double otcDiscount = 0.0;
    View rootView;
    private double totalAmount = 0;
    private boolean isFragmentDisplayed = true;
    private ArrayList<RealmOrderCentre> mList = new ArrayList<>();

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
    private String poNumber;
    private int postionCheckStock;
    private LinearLayout llArrows;
    private ImageView imgArrow;
    private boolean isArrowCLick=false;
    private ArrayList<NoGetEntityResultModal.BuisnessPlacesBean> distributotes=new ArrayList<>();
    private AppCompatSpinner spnTraders;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.edit_ordercentreactivity);
        setHeader();
        mContext = EditOrderCentreActivity.this;
        initializeComponent(rootView);

        myDialog = new Dialog(mContext);
        Util.hideSoftKeyboard(EditOrderCentreActivity.this);

        closeFragment();
        try {
            LocalBroadcastManager.getInstance(EditOrderCentreActivity.this).registerReceiver(listener,
                    new IntentFilter("BarcodeScan"));
        }catch (Exception e){

        }

        NoGetEntityResultModal.BuisnessPlacesBean noGetEntityBuisnessPlacesModal=new NoGetEntityResultModal.BuisnessPlacesBean();
        noGetEntityBuisnessPlacesModal.setBuisnessPlaceId(1);
        noGetEntityBuisnessPlacesModal.setBuisnessPlaceName(Prefs.getStringPrefs("EntityName"));
        noGetEntityBuisnessPlacesModal.setBuisnessPlaceName(Prefs.getStringPrefs(Constants.EntityName));
        noGetEntityBuisnessPlacesModal.setBuisnessLocationStateCode(Prefs.getStringPrefs(Constants.entityStateCode));
        distributotes.add(noGetEntityBuisnessPlacesModal);

        CustomAdapter adapter = new CustomAdapter(mContext, R.layout.spinner_item_pss,R.id.text1,distributotes);
        adapter.setDropDownViewResource(R.layout.spinner_item_pss);
        spnTraders.setAdapter(adapter);
    }

    public void setHeader() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mList.size()>0){
                    finish();
                }else{
                    Util.showToast("Please add atleast one product.");
                }


            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
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
        spnTraders=findViewById(R.id.spnTraders);
        llArrows=findViewById(R.id.llArrows);
        llArrows.setOnClickListener(this);
        imgArrow=findViewById(R.id.imgArrow);
        tvMessage = findViewById(R.id.tvMessage);
        flScanner = findViewById(R.id.flScanner);

        flScanLayout = findViewById(R.id.flScanLayout);
        imvStatus = findViewById(R.id.imvStatus);
        tvPinCount = findViewById(R.id.tvPinCount);
        imvPin = findViewById(R.id.imvPin);

        imvRight = findViewById(R.id.imvRight);
        tvMoreDetails = findViewById(R.id.tvMoreDetails);
        tvItemNo = findViewById(R.id.tvItemNo);
        tvItemQty = findViewById(R.id.tvItemQty);
        tvTotalItemPrice = findViewById(R.id.tvTotalItemPrice);
        tvTotalGST = findViewById(R.id.tvTotalGST);
        tvTotalItemGSTPrice = findViewById(R.id.tvTotalItemGSTPrice);
        llTotalDiscountDetail = findViewById(R.id.llTotalDiscountDetail);
        tvTotalDiscountDetail = findViewById(R.id.tvTotalDiscountDetail);
        tvTotalDiscountPrice = findViewById(R.id.tvTotalDiscountPrice);
        tvCGSTPrice = findViewById(R.id.tvCGSTPrice);
        tvSGSTPrice = findViewById(R.id.tvSGSTPrice);
        tvLessDetails = findViewById(R.id.tvLessDetails);
        tvRoundingOffPrice = findViewById(R.id.tvRoundingOffPrice);
        ll_item_pay = findViewById(R.id.ll_item_pay);
        tvPay = findViewById(R.id.tvPay);
        llTotalGST = findViewById(R.id.llTotalGST);
        spnAddress = findViewById(R.id.spnAddress);
        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dim_5),
                        getResources().getInteger(R.integer.photo_list_preview_columns)));
      //  mRecyclerView.addOnScrollListener(listener);
        llBelowPaymentDetail = findViewById(R.id.llBelowPaymentDetail);

        setSpinnerData();
        setListener();
        setAdapter();

        Intent i=getIntent();
        if (i!=null){
            poNumber=i.getStringExtra("poNumber");
        }

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
        FragmentManager fragmentManager = getSupportFragmentManager();
        // Check to see if the fragment is already showing.
        OrderCentreScannerFragment simpleFragment = (OrderCentreScannerFragment) fragmentManager
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
        OrderCentreScannerFragment simpleFragment = OrderCentreScannerFragment.newInstance();
        // TODO: Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getSupportFragmentManager();
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
        Intent mIntent = new Intent(mContext, AddOrderCentreActivity.class);
        mIntent.putExtra(Constants.businessPlaceCode, businessPlaceCode);
        mIntent.putExtra(Constants.entityStateCode, entityStateCode);
        startActivityForResult(mIntent, 3);
    }

    private void setAdapter() {

        mNewOrderListAdapter = new EditOrderCentreListAdapter(mContext, this, mRecyclerView, mList, this, this,this);
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

        Util.hideSoftKeyboard(EditOrderCentreActivity.this);
    }

    private void getProduct() {


        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmOrderCentre> realmNewOrderCarts1 = realm.where(RealmOrderCentre.class).equalTo("OrderId",poNumber).findAll();
        mList.clear();

        for (RealmOrderCentre realmNewOrderCart : realmNewOrderCarts1) {
            RealmOrderCentre realmNewOrderCarts = realm.copyFromRealm(realmNewOrderCart);

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
        tvTotalItemPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvTotalItemGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvPay.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvCGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvSGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvRoundingOffPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvSGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscountDetail.setText("(Item 0)");

        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmOrderCentre> realmNewOrderCarts1 = realm.where(RealmOrderCentre.class).equalTo("OrderId",poNumber).findAll();

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
        for (RealmOrderCentre realmNewOrderCart : realmNewOrderCarts1) {
            if (!realmNewOrderCart.isFreeItem())
                noOfItems = noOfItems + 1;

            qty = qty + realmNewOrderCart.getQty();
            totalItemsAmount = totalItemsAmount + realmNewOrderCart.getTotalPrice();
            if (realmNewOrderCart.isDiscount() && !realmNewOrderCart.isFreeItem()) {
                discountItems = discountItems + 1;
            }
            if (realmNewOrderCart.getDiscountPrice()<=0) {
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
                } else {
                    discountPrice = discountPrice + realmNewOrderCart.getTotalPrice();
                }
            }else {
                discountPrice = discountPrice + realmNewOrderCart.getDiscountPrice();


            }
            totalGST = (realmNewOrderCart.getGstPerc() * realmNewOrderCart.getTotalPrice() / 100);
            gst = gst + totalGST;



            cgst = cgst + (realmNewOrderCart.getCgst() * realmNewOrderCart.getTotalPrice() / 100);
            sgst = sgst + (realmNewOrderCart.getSgst() * realmNewOrderCart.getTotalPrice() / 100);




            totalPoints = totalPoints + realmNewOrderCart.getTotalPoints();

        }
        payAmount = (totalItemsAmount + cgst+sgst) - discountPrice;

        tvItemNo.setText("Item " + noOfItems);
        tvItemQty.setText(qty + " Qty");
        tvTotalItemPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat(totalItemsAmount));
        tvTotalItemGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat((cgst+sgst)));
        tvPay.setText(mContext.getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat(payAmount));
        tvCGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat(cgst));
        tvSGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + Util.indianNumberFormat(sgst));
        tvRoundingOffPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) + " "+Util.indianNumberFormat(discountPrice));
        tvTotalDiscountDetail.setText("(Item " + discountItems + ")");


    }






    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
//            case R.id.imvQRCode:
//                ((MainActivity) mContext).launchActivity(FullScannerActivity.class);
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
            case R.id.llArrows:
                if (!isArrowCLick) {
                    isArrowCLick=true;
                    imgArrow.setBackgroundResource(R.drawable.baseline_keyboard_arrow_down_black_18dp);
                    Util.animateView(view);
                    llTotalDiscountDetail.setVisibility(View.VISIBLE);
                    llTotalGST.setVisibility(View.GONE);
                }else {
                    isArrowCLick=false;
                    imgArrow.setBackgroundResource(R.drawable.baseline_keyboard_arrow_up_black_18dp);
                    Util.animateView(view);
                    llTotalDiscountDetail.setVisibility(View.GONE);
                    llTotalGST.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.imvInfo:
                Util.hideSoftKeyboard(EditOrderCentreActivity.this);
                int infoPos = (int) view.getTag();
                new InformationDialogNewOrderEdit(mContext,mList.get(infoPos));
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
                        new AlertDialog.Builder(EditOrderCentreActivity.this)).setTitle("Confirm action")
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
                    Intent i=new Intent();
                    setResult(6,i);
                    finish();
                } else {
                    Util.showToast("Please add atleast one item to proceed.");
                }


                break;
            case R.id.imvRight:
                Util.animateView(view);
                if (mList.size()  > 0) {
                    createOrder();
                    Intent i=new Intent();
                    setResult(6,i);
                    finish();
                } else {
                    Util.showToast("Please add atleast one item to proceed.");
                }

                break;
            case R.id.llRefreshStocks:
                final int posDeleteCheckStock = (int) view.getTag();
                RealmOrderCentre realmNewOrderCart=mList.get(posDeleteCheckStock);
                realmNewOrderCart.setmCheckStock(0);
                realmNewOrderCart.setCheckStockClick(false);
                mList.set(posDeleteCheckStock,realmNewOrderCart);
                mNewOrderListAdapter.notifyItemChanged(posDeleteCheckStock);
                break;
            case R.id.tvCheckStock:
                Util.animateView(view);

                final int posCheck = (int) view.getTag();
                postionCheckStock=posCheck;
                if (mList.size()  > 0) {
                    getCheckStockAPI(mList.get(posCheck).getiProductModalId());
                } else {
                    Util.showToast("Please add atleast one item to proceed.");
                }
                break;


        }
    }


    private void setOnClickPlus(View view) {
        Util.hideSoftKeyboard(EditOrderCentreActivity.this);
        Util.animateView(view);
        int posPlus = (int) view.getTag();

        Realm realm = Realm.getDefaultInstance();
        RealmOrderCentre realmNewOrderCarts = realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.iProductModalId.toString(), mList.get(posPlus).getiProductModalId()).equalTo("OrderId",poNumber).findFirst();
        Gson gson = new GsonBuilder().create();
        if (realmNewOrderCarts != null) {

            String strJson = gson.toJson(mList.get(posPlus));
            try {
                JSONObject jsonObject = new JSONObject(strJson);
                jsonObject.put(RetailSalesEnum.isAdded.toString(), true);
                jsonObject.put(RetailSalesEnum.discountPrice.toString(),0);
                jsonObject.put(RetailSalesEnum.qty.toString(), realmNewOrderCarts.getQty() + 1);
                jsonObject.put(RetailSalesEnum.totalPrice.toString(), (realmNewOrderCarts.getQty() + 1) * realmNewOrderCarts.getsProductPrice());

                int totalPoints = getTotalPoints((realmNewOrderCarts.getQty() + 1) ,realmNewOrderCarts, (realmNewOrderCarts.getQty() + 1) * realmNewOrderCarts.getsProductPrice());
                jsonObject.put(RetailSalesEnum.totalPoints.toString(), totalPoints);
                saveResponseLocal(jsonObject, poNumber);
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
        RealmOrderCentre realmNewOrderCarts = realm.where(RealmOrderCentre.class).equalTo(RetailSalesEnum.isFreeItem.toString(), false).equalTo(RetailSalesEnum.iProductModalId.toString(), productId).equalTo("OrderId",poNumber).findFirst();

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
                    saveResponseLocal(jsonObject, poNumber);

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

    private double getValueBasedOnDiscountZeroPacksize(int totalPrice, String sDiscountType, int sDiscountValue, RealmOrderCentre realmNewOrderCarts, int slabFrom, int slabTO, String opsCriteria, String sDiscountBasedOn, Realm realm, int packSize, String productCode) {

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

    private boolean getQuantityBasedOnDiscountItems(boolean isApplied, RealmOrderCentre realmNewOrderCarts, int slabFrom, int slabTO, String opsCriteria, String sDiscountBasedOn, Realm realm, int packSize, String productCode) {

        int productQty = realmNewOrderCarts.getQty();
      //  if (productQty >= slabFrom && productQty <= slabTO || productQty > slabTO) {

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

     //   }
        return isApplied;
    }

    private boolean getaddHighestFreeItems(RealmOrderCentre realmNewOrderCarts, boolean isApplied, Realm realm, String opsCriteria, String productCode, int packSize, int slabFrom, int productQty) {


        RealmResults<RealmOrderCentre> realmNewOrderCarts1 = realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),false).equalTo("OrderId",poNumber).findAllSorted(RetailSalesEnum.sProductPrice.toString(), Sort.DESCENDING);

        long   sum     = realmNewOrderCarts1.sum(RetailSalesEnum.qty.toString()).longValue();

        int countt= (int) sum;
        int loopSize = realmNewOrderCarts1.size();
        int itemsPerFree = countt / (packSize);

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

                                    saveResponseLocal(jsonObject, poNumber);
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
                RealmResults<RealmOrderCentre> allSorted = realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),true).equalTo("OrderId",poNumber).findAll();

                allSorted.deleteAllFromRealm();

            }
        return isApplied;

    }

    private boolean getaddLowestFreeItems(RealmOrderCentre realmNewOrderCarts, boolean isApplied, Realm realm, String opsCriteria, String productCode, int packSize, int slabFrom, int productQty) {


        RealmResults<RealmOrderCentre> realmNewOrderCarts1 = realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),false).equalTo("OrderId",poNumber).findAllSorted(RetailSalesEnum.sProductPrice.toString(), Sort.ASCENDING);
        long   sum     = realmNewOrderCarts1.sum(RetailSalesEnum.qty.toString()).longValue();

        int countt= (int) sum;
        int loopSize = realmNewOrderCarts1.size();
        int itemsPerFree = countt / (packSize);
        int freeItems = 0;
        if (itemsPerFree > 0){
            freeItems = itemsPerFree * packSize;
        if (loopSize == 1) {
            for (int l = 0; l < loopSize; l++) {
                if (freeItems>0) {
             //   for (int m = 0; m < freeItems; m++) {
                    Gson gson = new GsonBuilder().create();
                    String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                    try {
                        JSONObject jsonObject = new JSONObject(strJson);
                        jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                        jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                        jsonObject.put(RetailSalesEnum.qty.toString(), freeItems);
                        jsonObject.put(RetailSalesEnum.totalPrice.toString(), (freeItems) * realmNewOrderCarts1.get(l).getsProductPrice());
                        jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                        jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                        saveResponseLocal(jsonObject, poNumber);
                        isApplied = true;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
             //   }
            }

            }
        } else {
            for (int l = 0; l < loopSize; l++) {

                if (freeItems>0) {
                    int qty = realmNewOrderCarts1.get(l).getQty();
                    if (qty == freeItems) {
                       // for (int m = 0; m < freeItems; m++) {
                            Gson gson = new GsonBuilder().create();
                            String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                            try {
                                JSONObject jsonObject = new JSONObject(strJson);
                                jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                jsonObject.put(RetailSalesEnum.qty.toString(),freeItems);
                                jsonObject.put(RetailSalesEnum.totalPrice.toString(), (freeItems) * realmNewOrderCarts1.get(l).getsProductPrice());
                                jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                saveResponseLocal(jsonObject, poNumber);
                                isApplied = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                    //    }

                        freeItems = 0;

                    } else if (qty < freeItems) {

                       // for (int m = 0; m < qty; m++) {
                            Gson gson = new GsonBuilder().create();
                            String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                            try {
                                JSONObject jsonObject = new JSONObject(strJson);
                                jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                jsonObject.put(RetailSalesEnum.qty.toString(), qty);
                                jsonObject.put(RetailSalesEnum.totalPrice.toString(), (qty) * realmNewOrderCarts1.get(l).getsProductPrice());
                                jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                saveResponseLocal(jsonObject, poNumber);
                                isApplied = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            freeItems = freeItems - qty;
                       // }


                    } else if (qty > freeItems) {
                        int size=freeItems;
                       // for (int m = 0; m < size; m++) {
                            Gson gson = new GsonBuilder().create();
                            String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts1.get(l)));
                            try {
                                JSONObject jsonObject = new JSONObject(strJson);
                                jsonObject.put(NoGetEntityEnums.parentProductId.toString(), realmNewOrderCarts.getiProductModalId());
                                jsonObject.put(RetailSalesEnum.isFreeItem.toString(), true);
                                jsonObject.put(RetailSalesEnum.qty.toString(), size);
                                jsonObject.put(RetailSalesEnum.totalPrice.toString(), (size) * (realmNewOrderCarts1.get(l)).getsProductPrice());
                                jsonObject.put(RetailSalesEnum.totalPoints.toString(), 0);
                                jsonObject.put(RetailSalesEnum.iProductModalId.toString(), realmNewOrderCarts1.get(l).getiProductModalId() + "free");

                                saveResponseLocal(jsonObject, poNumber);
                                isApplied = true;
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            freeItems = freeItems - size;
                       // }


                    }
                }
            }
        }
    }else{
            Realm realm1=Realm.getDefaultInstance();
            RealmResults<RealmOrderCentre> allSorted = realm1.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.productCode.toString(), productCode).equalTo(RetailSalesEnum.isFreeItem.toString(),true).equalTo("OrderId",poNumber).findAll();

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

    private int getTotalPoints(int i, RealmOrderCentre realmNewOrderCarts, int totalPrice){
        int totalPoints=0;
        if (realmNewOrderCarts.getPointsBasedOn().equalsIgnoreCase("M")){
            totalPoints=realmNewOrderCarts.getPoints()*i;

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
        Util.hideSoftKeyboard(EditOrderCentreActivity.this);
        Util.animateView(view);
        int posMinus = (int) view.getTag();

        Realm realm=Realm.getDefaultInstance();
        RealmOrderCentre realmNewOrderCarts=realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.iProductModalId.toString(),mList.get(posMinus).getiProductModalId()).equalTo("OrderId",poNumber).findFirst();

        Gson gson = new GsonBuilder().create();
        if (realmNewOrderCarts!=null) {
if (realmNewOrderCarts.getQty()>1) {
    String strJson = gson.toJson(mList.get(posMinus));
    try {
        JSONObject jsonObject = new JSONObject(strJson);
        jsonObject.put(RetailSalesEnum.isAdded.toString(), true);
        jsonObject.put(RetailSalesEnum.discountPrice.toString(),0);
        jsonObject.put(RetailSalesEnum.qty.toString(), realmNewOrderCarts.getQty() - 1);
        jsonObject.put(RetailSalesEnum.totalPrice.toString(), (realmNewOrderCarts.getQty() - 1) * realmNewOrderCarts.getsProductPrice());

        int totalPoints=getTotalPoints((realmNewOrderCarts.getQty() - 1), realmNewOrderCarts,(realmNewOrderCarts.getQty()-1)*realmNewOrderCarts.getsProductPrice());
        jsonObject.put(RetailSalesEnum.totalPoints.toString(),totalPoints);
        saveResponseLocal(jsonObject, poNumber);
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
            RealmOrderCentre realmNewOrderCarts = realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.iProductModalId.toString(), mList.get(position).getiProductModalId()).equalTo("OrderId",poNumber).findFirst();
            Gson gson = new GsonBuilder().create();
            if (realmNewOrderCarts != null) {

                String strJson = gson.toJson(mList.get(position));
                try {
                    JSONObject jsonObject = new JSONObject(strJson);
                    jsonObject.put(RetailSalesEnum.qty.toString(), value);
                    jsonObject.put(RetailSalesEnum.discountPrice.toString(),0);
                    jsonObject.put(RetailSalesEnum.totalPrice.toString(), value * realmNewOrderCarts.getsProductPrice());

                    int totalPoints = getTotalPoints(value, realmNewOrderCarts, value * realmNewOrderCarts.getsProductPrice());
                    jsonObject.put(RetailSalesEnum.totalPoints.toString(), totalPoints);
                    saveResponseLocal(jsonObject, poNumber);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                setCalculatedValues();

                mNewOrderListAdapter.notifyItemChanged(position);
                calculateOPS(realmNewOrderCarts.getProductCode(), realmNewOrderCarts.getiProductModalId());
                getProduct();
            }
        }
        Util.hideSoftKeyboard(EditOrderCentreActivity.this);
    }



    private void cachedPinned(boolean showScreen) {

        if(IPOSApplication.mOrderList!=null)
            if(IPOSApplication.mOrderList.size()>0) {

                if (SharedPrefUtil.getString(Constants.mOrderInfoArrayList, "", EditOrderCentreActivity.this) != null) {
                    String json2 = SharedPrefUtil.getString(Constants.mOrderInfoArrayList, "", EditOrderCentreActivity.this);
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
                SharedPrefUtil.putString(Constants.mOrderInfoArrayList, json, EditOrderCentreActivity.this);
//            IPOSApplication.mOrderList.clear();
            }
        if (showScreen) {
            Intent mIntent = new Intent(EditOrderCentreActivity.this, PinnedOrderActivity.class);
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
                Util.showToast("Cannot save empty list",EditOrderCentreActivity.this);
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
        json = SharedPrefUtil.getString(Constants.Order_List,"",EditOrderCentreActivity.this);
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

        if (serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_NOPRODUCTSEARCH)){


                if (httpStatusCode == Constants.SUCCESS) {

                    if (Util.validateString(serverResponse)) {

                        try {
                            JSONObject jsonObject = new JSONObject(serverResponse);
                            JSONArray array=jsonObject.optJSONArray("data");
                            if (array.length() == 0) {
                                Util.showToast("Product not found");

                                return;
                            }
                            JSONObject jsonObject1=array.optJSONObject(0);
                            addBarcodeScanProduct(jsonObject1.optString("iProductModalId"), jsonObject1.optString("productCode"), jsonObject1.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Util.showToast("Product Scanned Successfully");
                        imvStatus.setBackgroundResource(R.drawable.circle_disabled);
                        flScanner.setVisibility(View.GONE);
                        closeFragment();

                    }


                } else if (httpStatusCode == Constants.BAD_REQUEST) {
                    Toast.makeText(EditOrderCentreActivity.this, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
                } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
                    Toast.makeText(EditOrderCentreActivity.this, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
                } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
                    Toast.makeText(EditOrderCentreActivity.this, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
                } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
                    Toast.makeText(EditOrderCentreActivity.this, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
                } else if (httpStatusCode == Constants.CONNECTION_OUT) {
                    Toast.makeText(EditOrderCentreActivity.this, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
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
        RealmResults<RealmOrderCentre> realmNewOrderCarts1 = realm.where(RealmOrderCentre.class).equalTo("OrderId",poNumber).findAll();

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
        double accumulatedPoints=0;
        double discountPartiItem=0;
        for (RealmOrderCentre realmNewOrderCart : realmNewOrderCarts1) {

            JSONObject jsonObjectCartDetail=new JSONObject();
            if (!realmNewOrderCart.isFreeItem())
                noOfItems = noOfItems + 1;
            poNumber=realmNewOrderCart.getOrderId();
            accumulatedPoints=realmNewOrderCart.getAccumulatedLoyality();
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
                            discountPartiItem=discountPartiItem+jsonObject.optInt("discountTotal");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                discountPartiItem=realmNewOrderCart.getTotalPrice();
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
                    JSONObject jsonObjectScheme = new JSONObject();
                    JSONObject jsonObject = discountArray.optJSONObject(k);

                    if (!jsonObject.optBoolean("discountTotalStrike")) {
                        JSONArray jsonArrayRule = jsonObject.getJSONArray("rule");

                        for (int m = 0; m < jsonArrayRule.length(); m++) {

                            JSONObject jsonObject1 = jsonArrayRule.optJSONObject(m);
                            if (jsonObject1.optBoolean("isRuleApplied")) {
                                jsonObjectScheme.put("schemeID", k + 1);
                                jsonObjectScheme.put("ruleID", jsonObject1.optString("ruleID"));
                                jsonObjectScheme.put("discountValue", jsonObject.optString("discountTotal"));
                                jsonObjectScheme.put("discountPerc", jsonObject1.optString("sDiscountValue"));
                                jsonObjectScheme.put("oldSchemeID", k + 1);
                                jsonObjectScheme.put("oldRuleID", jsonObject1.optString("ruleID"));


                            }

                        }
                        if (!jsonObjectScheme.toString().equalsIgnoreCase("{}"))
                            scheme.put(jsonObjectScheme);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }




            try {
                jsonObjectCartDetail.put("oldMaterialCode",realmNewOrderCart.getiProductModalId());
                jsonObjectCartDetail.put("materialCode",realmNewOrderCart.getProductCode());
                jsonObjectCartDetail.put("materialName",realmNewOrderCart.getsProductName());
                jsonObjectCartDetail.put("materialValue",realmNewOrderCart.getTotalPrice());
                jsonObjectCartDetail.put("materialQty",realmNewOrderCart.getQty());
                jsonObjectCartDetail.put("materialDiscountValue",discountPartiItem);
                jsonObjectCartDetail.put("materialUnitValue",realmNewOrderCart.getsProductPrice());
                jsonObjectCartDetail.put("materialCGSTRate",realmNewOrderCart.getCgst());
                jsonObjectCartDetail.put("materialCGSTValue",cgst);
                jsonObjectCartDetail.put("materialSGSTRate",realmNewOrderCart.getSgst());
                jsonObjectCartDetail.put("materialSGSTValue",sgst);
                jsonObjectCartDetail.put("materialIGSTRate",realmNewOrderCart.getGstPerc());
                jsonObjectCartDetail.put("materialIGSTValue",cgst+sgst);
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
            jsonObject.put("accumulatedLoyality",accumulatedPoints);
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

        saveResponseLocalCreateOrder(jsonObject,poNumber);

    }

    private void deleteItems(String productId){
        Realm realm1=Realm.getDefaultInstance();
        RealmResults<RealmOrderCentre> allSorted = realm1.where(RealmOrderCentre.class).equalTo(RetailSalesEnum.iProductModalId.toString(),productId).or().equalTo(NoGetEntityEnums.parentProductId.toString(), productId).equalTo("OrderId",poNumber).findAll();

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
        RealmOrderCentre allSorted = realm1.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.iProductModalId.toString(), productId).equalTo(RetailSalesEnum.isFreeItem.toString(),true).equalTo("OrderId",poNumber).findFirst();

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
        RealmOrderCentre realmNewOrderCart=realm.where(RealmOrderCentre.class).equalTo(RetailSalesEnum.iProductModalId.toString(),productId).equalTo(RetailSalesEnum.isFreeItem.toString(),false).equalTo("OrderId",poNumber).findFirst();
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
        RealmOrderCentre realmNewOrderCarts = realm.where(RealmOrderCentre.class).equalTo(NoGetEntityEnums.iProductModalId.toString(), productId).equalTo("OrderId",poNumber).findFirst();
        Gson gson = new GsonBuilder().create();
        if (realmNewOrderCarts != null) {

            String strJson = gson.toJson(realm.copyFromRealm(realmNewOrderCarts));
            try {
                JSONObject jsonObject = new JSONObject(strJson);
                jsonObject.put(RetailSalesEnum.isAdded.toString(), true);
                jsonObject.put(RetailSalesEnum.qty.toString(), realmNewOrderCarts.getQty() + 1);
                jsonObject.put(RetailSalesEnum.totalPrice.toString(), (realmNewOrderCarts.getQty() + 1) * realmNewOrderCarts.getsProductPrice());

                int totalPoints = getTotalPoints((realmNewOrderCarts.getQty() + 1), realmNewOrderCarts, (realmNewOrderCarts.getQty() + 1) * realmNewOrderCarts.getsProductPrice());
                jsonObject.put(RetailSalesEnum.totalPoints.toString(), totalPoints);
                saveResponseLocal(jsonObject, poNumber);
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
                jsonObject.put(RetailSalesEnum.totalPrice.toString(),jsonObject.optInt("sProductPrice"));
              //  int totalPoints=getTotalPoints(dataBeans.get(pos),dataBeans.get(pos).getSProductPrice());
            //    jsonObject.put(RetailSalesEnum.totalPoints.toString(),totalPoints);
                saveResponseLocal(jsonObject,poNumber);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem menu12 = menu.findItem(R.id.action_notification);
        menu12.setVisible(false);



        return true;
    }

    public void getCheckStockAPI(String productId) {
        final ProgressDialog progressDialog=new ProgressDialog(EditOrderCentreActivity.this);
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

        final Request request = APIClient.getPostRequest(EditOrderCentreActivity.this, url, requestBody);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {

                progressDialog.dismiss();
                //  dismissProgress();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // dismissProgress();

                EditOrderCentreActivity.this.runOnUiThread(new Runnable() {
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

                            EditOrderCentreActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    RealmOrderCentre realmNewOrderCart=mList.get(postionCheckStock);
                                    realmNewOrderCart.setmCheckStock(jsonObject.optInt("stockQty"));
                                    realmNewOrderCart.setCheckStockClick(true);
                                    mList.set(postionCheckStock,realmNewOrderCart);
                                    mNewOrderListAdapter.notifyItemChanged(postionCheckStock);
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

    @Override
    public void onBackPressed() {

        if (mList.size()>0){
            finish();
        }else{
            Util.showToast("Please add atleast one product.");
        }
    }
}
