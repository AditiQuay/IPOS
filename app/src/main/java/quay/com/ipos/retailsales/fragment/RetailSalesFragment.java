package quay.com.ipos.retailsales.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import me.dm7.barcodescanner.zbar.ZBarScannerView;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.MyAdapterTags;
import quay.com.ipos.listeners.ScanFilterListener;
import quay.com.ipos.listeners.ScannerProductListener;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.modal.ProductListResult;
import quay.com.ipos.modal.ProductSearchResult;
import quay.com.ipos.realmbean.RealmPinnedResults;
import quay.com.ipos.retailsales.activity.AddProductActivity;
import quay.com.ipos.retailsales.activity.PaymentModeActivity;
import quay.com.ipos.retailsales.activity.PinnedRetailActivity;
import quay.com.ipos.retailsales.adapter.RetailSalesAdapter;
import quay.com.ipos.ui.DiscountDeleteFragment;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.MessageDialog;
import quay.com.ipos.ui.MyDialogFragment;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

import static quay.com.ipos.application.IPOSApplication.isClicked;
import static quay.com.ipos.application.IPOSApplication.minDiscount;
import static quay.com.ipos.application.IPOSApplication.totalAmount;

/**
 * Created by aditi.bhuranda on 16-04-2018.
 */
public class RetailSalesFragment extends BaseFragment implements  View.OnClickListener , CompoundButton.OnCheckedChangeListener ,AdapterListener ,MessageDialog.MessageDialogListener,ScannerProductListener,ScanFilterListener,MyAdapterTags {


    ProductListResult productListResult = null;
    private MainActivity mainActivity;
    /**
     * The Array searchlist.
     */
//    ArrayList<ProductListResult.Datum> arrSearchlist = new ArrayList<>();
    private TextView tvRight1, tvMoreDetails, tvItemNo, tvItemQty, tvTotalItemPrice,
            tvTotalGST, tvTotalItemGSTPrice, tvTotalDiscountDetail, tvTotalDiscountPrice, tvCGSTPrice, tvSGSTPrice,
            tvLessDetails, tvRoundingOffPrice, tvTotalDiscount, tvPay, tvOTCDiscount, tvApplyOTC, tvApplyOTC2, tvPinCount;
    private FrameLayout flScanner,flScanLayout;
    private ToggleButton tbPerc, tbRs;
    private EditText etDiscountAmt;
    private CheckBox chkOTC;
    private Fragment scanner_fragment;
    private LinearLayout llTotalDiscountDetail, ll_item_pay, llOTCSelect, llTotalGST, llOTCConfirmation;
    private ImageView imvDicount, imvGlobe, imvUserAdd, imvPin, imvRedeem, imvRight, imvClearOTC, imvBarcode,imvStatus;
    //    private ToggleButton chkBarCode;
    private LinearLayoutManager mLayoutManager;
    MyAdapterTags myAdapterTags;
    private RecyclerView mRecyclerView;
    private String TAG = RetailSalesFragment.class.getSimpleName();
    private RetailSalesAdapter mRetailSalesAdapter;
    private boolean loading = true;
    /**
     * The Past visibles items.
     */
    int pastVisiblesItems, /**
     * The Visible item count.
     */
    visibleItemCount, /**
     * The Total item count.
     */
    totalItemCount;
    private ProductList mProductListResult;
    /**
     * The My dialog.
     */
    Dialog myDialog;
    /**
     * The Otc discount.
     */
    double otcDiscount = 0.0;
    /**
     * The Root view.
     */
    View rootView;

    private boolean isFragmentDisplayed = true;
//    private ArrayList<ProductList.Datum> mList= new ArrayList<>();

    /**
     * The Is otc.
     */
    boolean isOTC = false;
    /**
     * The After discount price.
     */
    Double afterDiscountPrice;
    private ArrayList<RealmPinnedResults.Info> mInfoArrayList = new ArrayList<RealmPinnedResults.Info>();
    private String json;
    private ZBarScannerView mScannerView;
    private Context mContext;

    /**
     * On create view view.
     *
     * @param inflater           the inflater
     * @param container          the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.retail_dashboard, container, false);
        mainActivity = (MainActivity) getActivity();
        mContext = getActivity();
        initializeComponent(rootView);
        setTextDefault();
        myDialog = new Dialog(getActivity());
        setHasOptionsMenu(true);
        Util.hideSoftKeyboard(getActivity());
        try {
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(listener,
                    new IntentFilter("CUSTOM_ACTION"));
        }catch (Exception e){

        }
        return rootView;
    }


    // initialize views
    private void initializeComponent(View rootView) {
        flScanner = rootView.findViewById(R.id.flScanner);
        flScanLayout = rootView.findViewById(R.id.flScanLayout);
        imvUserAdd = rootView.findViewById(R.id.imvUserAdd);
        imvPin = rootView.findViewById(R.id.imvPin);
        imvRedeem = rootView.findViewById(R.id.imvRedeem);
        imvStatus = rootView.findViewById(R.id.imvStatus);
        imvGlobe = rootView.findViewById(R.id.imvGlobe);
        imvDicount = rootView.findViewById(R.id.imvDicount);
        imvBarcode = rootView.findViewById(R.id.imvBarcode);
        imvRight = rootView.findViewById(R.id.imvRight);
        tvRight1 = rootView.findViewById(R.id.tvRight1);
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
        tvTotalDiscount = rootView.findViewById(R.id.tvTotalDiscount);
        tvPay = rootView.findViewById(R.id.tvPay);
        llOTCSelect = rootView.findViewById(R.id.llOTCSelect);
        llTotalGST = rootView.findViewById(R.id.llTotalGST);
        chkOTC = rootView.findViewById(R.id.chkOTC);
        tvOTCDiscount = rootView.findViewById(R.id.tvOTCDiscount);
        tvPinCount = rootView.findViewById(R.id.tvPinCount);

        mRecyclerView = rootView.findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new ItemDecorationAlbumColumns(mContext.getResources().getDimensionPixelSize(R.dimen.dim_5),
                        mContext.getResources().getInteger(R.integer.photo_list_preview_columns)));
        setFontText();
        setListener();
        setAdapter();


    }


    // initialize & declare textviews
    private void setTextDefault() {
        tvItemNo.setText("Item 0");
        tvItemQty.setText("0 Qty");
        tvTotalItemPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvTotalItemGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscount.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvPay.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvCGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvSGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvRoundingOffPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvSGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscountDetail.setText("(Item 0)");
        IPOSApplication.mProductListResult.clear();
        flScanner.setVisibility(View.GONE);
//        chkBarCode.setChecked(false);
//        closeFragment();

        pinnedUpdate();

    }

    private void pinnedUpdate() {
        if (SharedPrefUtil.getString("mInfoArrayList", "", mContext) != null) {
            String json2 = SharedPrefUtil.getString("mInfoArrayList", "", mContext);
            if (!json2.equalsIgnoreCase(""))
                mInfoArrayList = Util.getCustomGson().fromJson(json2, new TypeToken<ArrayList<RealmPinnedResults.Info>>() {
                }.getType());
            if (mInfoArrayList.size() > 0) {
                tvPinCount.setText("" + mInfoArrayList.size());
                tvPinCount.setVisibility(View.VISIBLE);
            } else {
                tvPinCount.setVisibility(View.GONE);
            }
        } else {
            tvPinCount.setVisibility(View.GONE);
        }
    }


    // set fontAwesonme
    private void setFontText() {
        Typeface iconFont = FontManager.getTypeface(mContext, FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(tvRight1, iconFont);
    }

    private void setListener() {
        tvMoreDetails.setOnClickListener(this);
        tvLessDetails.setOnClickListener(this);
        imvDicount.setOnClickListener(this);
        tvOTCDiscount.setOnClickListener(this);
        tvRight1.setOnClickListener(this);
        imvPin.setOnClickListener(this);
        chkOTC.setOnCheckedChangeListener(this);
        imvRedeem.setOnClickListener(this);
        imvUserAdd.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        imvRight.setOnClickListener(this);
        closeFragment();
        // Set the click listener for the button.
        flScanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ((MainActivity) mContext).launchActivity(true);
//                    chkBarCode.setChecked(false);
                    setTextDefault();
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


    /**
     * Close fragment.
     */
    public void closeFragment() {
        // Get the FragmentManager.
        FragmentManager fragmentManager = getChildFragmentManager();
        // Check to see if the fragment is already showing.
        FullScannerFragment simpleFragment = (FullScannerFragment) fragmentManager
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


    /**
     * Display fragment.
     */
    public void displayFragment() {
        FullScannerFragment simpleFragment = new FullScannerFragment();
        // TODO: Get the FragmentManager and start a transaction.
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        simpleFragment.setListener(this);
        // TODO: Add the SimpleFragment.
        // Add the SimpleFragment.
        //    simpleFragment.setTargetFragment(RetailSalesFragment.this,2000);

        fragmentTransaction.add(R.id.scanner_fragment,
                simpleFragment).addToBackStack("fragment").commit();
        simpleFragment.onResumeCamera();
        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true;
    }

    /**
     * The Is back.
     */
    boolean isBack = false;



    /**
     * On resume.
     */
    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) mContext).setToolbarTitle(getString(R.string.retail_sales));

        //You need to add the following line for this solution to work; thanks skayred
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (IPOSApplication.mProductListResult.size() >= 1 && !isBack) {
                        Util.showMessageDialog(mContext,RetailSalesFragment.this, getResources().getString(R.string.save_cart_message), getResources().getString(R.string.yes), getResources().getString(R.string.no), Constants.APP_DIALOG_Cart, "", getActivity().getSupportFragmentManager());

                        isBack = true;
                    } else
                        isBack = false;

                }
                return isBack;
            }
        });
    }

    /**
     * The Dialog otc.
     */
    Dialog dialogOTC;

    private void dialogOTCTask() {
        dialogOTC = new Dialog(mContext);
        dialogOTC.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOTC.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        // Include dialog.xml file
        dialogOTC.setContentView(R.layout.otc_discount);
        dialogOTC.show();
        imvClearOTC = dialogOTC.findViewById(R.id.imvClearOTC);
        etDiscountAmt = dialogOTC.findViewById(R.id.etDiscountAmt);
        tbRs = dialogOTC.findViewById(R.id.tbRs);
        tbPerc = dialogOTC.findViewById(R.id.tbPerc);
        tvApplyOTC = dialogOTC.findViewById(R.id.tvApplyOTC);
        llOTCConfirmation = dialogOTC.findViewById(R.id.llOTCConfirmation);
        tvApplyOTC2 = dialogOTC.findViewById(R.id.tvApplyOTC2);
        tvApplyOTC2.setOnClickListener(this);
        imvClearOTC.setOnClickListener(this);
        tvApplyOTC.setOnClickListener(this);
        tbPerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbRs.setChecked(false);
                tbPerc.setChecked(true);
                tbRs.setTextColor(mContext.getResources().getColor(R.color.accent_color));
                tbPerc.setTextColor(mContext.getResources().getColor(R.color.white));
                if (!etDiscountAmt.getText().toString().trim().equalsIgnoreCase(""))
                    if (Integer.parseInt(etDiscountAmt.getText().toString()) > 100) {
//                        (new AlertDialog.Builder(mContext)).setTitle("Confirm action")
//                                .setMessage("Please enter valid discount percentage")
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        etDiscountAmt.setText("");
//                                    }
//                                }).show();
                        Util.showMessageDialog(mContext,RetailSalesFragment.this, "Please enter valid discount percentage", "OK", null, Constants.APP_DIALOG_OTC, "", getActivity().getSupportFragmentManager());
                    }
            }
        });

        tbRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbPerc.setChecked(false);
                tbRs.setChecked(true);
                tbRs.setTextColor(mContext.getResources().getColor(R.color.white));
                tbPerc.setTextColor(mContext.getResources().getColor(R.color.accent_color));
            }
        });
    }


    /**
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
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


    /**
     * On search button.
     */
    public void onSearchButton() {
        Intent mIntent = new Intent(mContext, AddProductActivity.class);
        startActivityForResult(mIntent, 1);
    }

    private void setAdapter() {

        mRetailSalesAdapter = new RetailSalesAdapter(mContext, this, mRecyclerView, IPOSApplication.mProductListResult, this, this,this);
        mRecyclerView.setAdapter(mRetailSalesAdapter);

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
      /*  mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
               // mRecyclerView.removeOnLayoutChangeListener(this);
                AppLog.e(TAG, "updated");
           //    mRetailSalesAdapter.notifyDataSetChanged();
            //     getProduct();
            }
        });*/
    }
    ArrayList<ProductSearchResult.Datum> arrSearchlist = IPOSApplication.mProductListResult;


    private BroadcastReceiver listener = new BroadcastReceiver() {
        @Override
        public void onReceive( Context context, Intent intent ) {
            String data = intent.getStringExtra("CUSTOM_ACTION");
            //  Log.e( "Received data : ", data);


            mRetailSalesAdapter.notifyDataSetChanged();
            getProduct();
        }
    };

    /**
     * The Child position.
     */
    int childPosition = -1;

    /**
     * On activity result.
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        isClicked = true;
        if (requestCode == 1) {
            if (resultCode == 1) {
                if(IPOSApplication.mProductListResult.size()>0){
                    for(int i = 0 ; i < IPOSApplication.mProductListResult.size(); i++){
                        if(!IPOSApplication.mProductListResult.get(i).isAdded()) {
                            IPOSApplication.mProductListResult.remove(i);
                            i--;
                        }
                    }
                }
             //   mRetailSalesAdapter.notifyDataSetChanged();
                //  mRetailSalesAdapter.setDiscountAdapter(userViewHolder);
                getProduct();
            }
        } else if (requestCode == 2) {
            if (resultCode == 1) {
                childPosition = data.getIntExtra("pinned_position", 0);
                pinnedUpdate();
                getProduct();
            } else if (resultCode == 0) {
                pinnedUpdate();
                getProduct();
            }
        }
     /*   if (resultCode == RESULT_OK) {
            if (requestCode==2000){
                String scanCode=data.getStringExtra("scancode");
            }
        }*/
        Util.hideSoftKeyboard(getActivity());
    }

    private void getProduct() {
        try {
//            IPOSApplication.mProductList.clear();
//            String json = Util.getAssetJsonResponse(mContext, "product_list.json");
//            mProductListResult = Util.getCustomGson().fromJson(json,ProductList.class);
            AppLog.e(RetailSalesFragment.class.getSimpleName(), "Get Product: " + Util.getCustomGson().toJson(IPOSApplication.mProductListResult));
//            IPOSApplication.mProductList.addAll(mProductListResult.getData());
            setDefaultValues();
            setAdapter();

            setUpdateValues(IPOSApplication.mProductListResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setDefaultValues() {

        Double totalPrice;
        for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
            ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(i);
            if (datum.getQty() == 0)
                datum.setQty(1);

            if (!datum.isDiscItemSelected())
                datum.setDiscItemSelected(true);
            totalPrice = (datum.getSProductPrice() * datum.getQty());
            datum.setTotalPrice(totalPrice);
//            if (datum.getIsDiscount()) {
//                Double discount = datum.getSDiscountPrice() * totalPrice / 100;
//                datum.setDiscount(discount);
//            } else {
//                datum.setDiscount(0.0);
//            }
            IPOSApplication.mProductListResult.set(i, datum);
        }
    }
    ArrayList<ProductSearchResult.Discount> discounts = new ArrayList<>();

    private void setUpdateValues(ArrayList<ProductSearchResult.Datum> mList) {

        AppLog.e(RetailSalesFragment.class.getSimpleName(), "IPOSApplication.mProductListResult:Frag: " + Util.getCustomGson().toJson(IPOSApplication.mProductListResult));
        if (mList.size() == 1 || mList.size() == 0) {
            tvItemNo.setText("Item " + mList.size() + " item");
        } else {
            tvItemNo.setText("Items " + mList.size() + " item");
        }
        if (mList.size() == 0) {
            tvItemQty.setText("0 Qty");
            tvTotalItemPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
            tvTotalItemGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
            tvTotalDiscount.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
            tvPay.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
            tvCGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
            tvSGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
            tvRoundingOffPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
            tvSGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
            tvTotalDiscountPrice.setText(mContext.getResources().getString(R.string.Rs) + " 0.0");
            tvTotalDiscountDetail.setText("(Item 0)");
        } else {

            int qty = 0;
            double totalPrice = 0.0;
            double sum = 0;
            double discount = 0, totalGst = 0.0, cgst = 0.0, sgst = 0.0;
            int discountItem = 0;
            int mSelectedpos = 0;
            double totalAfterGSt = 0.0;
            double otcDiscountPerc = 0.0;

            for (int i = 0; i < mList.size(); i++) {
                ProductSearchResult.Datum datum = mList.get(i);
                qty += mList.get(i).getQty();
                datum.setTotalQty(qty);
                totalPrice = mList.get(i).getQty() * mList.get(i).getSalesPrice();
                sum = totalPrice + sum;
                datum.setTotalPrice(sum);
                if (mList.get(i).isDiscItemSelected()) {
//                    if (mList.get(i).getIsDiscount()) {
//                        discount = discount + mList.get(i).getSDiscountPrice() * totalPrice / 100;
//                        datum.setDiscount(discount);
//                        discountItem++;
//                    }
                }

//                for (int p = 0; p < minDiscount.size(); p++){
//
//                }

                if(mList.get(i).getIsDiscount()){
                    discounts = mList.get(i).getDiscount();
                    for (int j = 0 ; j < discounts.size(); j++){
                        discount = discount + discounts.get(j).getDiscountTotal();
                    }
                }


                if (mList.get(i).isDiscSelected()) {
                    if (!mList.get(i).isDiscItemSelected())
                        discountItem++;
                    otcDiscountPerc += mList.get(i).getOTCDiscount();
                } else {
                    otcDiscountPerc = 0;
                }
                totalGst = mList.get(i).getGstPerc() * sum / 100;
                totalGst += totalGst;
                sgst = mList.get(i).getSgst() * sum / 100;
                sgst += sgst;
                cgst = mList.get(i).getCgst() * sum / 100;
                cgst += cgst;
//                totalPrice += mList.get(i).getTotalPrice();
                IPOSApplication.mProductListResult.set(i, datum);
            }
            tvItemQty.setText(qty + " Qty");
            tvTotalItemPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + sum);
            tvTotalDiscountPrice.setText("-" + mContext.getResources().getString(R.string.Rs) + " " + (discount + otcDiscountPerc));
            tvTotalDiscount.setText(mContext.getResources().getString(R.string.Rs) + " " + (discount + otcDiscountPerc));
            tvTotalDiscountDetail.setText("(Item " + discountItem + ")");
            AppLog.e(RetailSalesFragment.class.getSimpleName(), "totalGst: " + totalGst);
            tvTotalItemGSTPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + totalGst);

            tvSGSTPrice.setText("+" + mContext.getResources().getString(R.string.Rs) + " " + sgst);

            tvCGSTPrice.setText("+" + mContext.getResources().getString(R.string.Rs) + " " + cgst);
            totalAfterGSt = (sum - discount) + (sgst + cgst) - (otcDiscountPerc);
//            double floorValue = Math.round(totalAfterGSt);


            double roundOff = totalAfterGSt - Math.floor(totalAfterGSt);
            tvRoundingOffPrice.setText(mContext.getResources().getString(R.string.Rs) + " " + (Util.round(roundOff, 1)));
            totalAfterGSt = totalAfterGSt + (Util.round(roundOff, 1));
            totalAmount = Math.round(totalAfterGSt);
            tvPay.setText(mContext.getResources().getString(R.string.Rs) + " " + Math.round(totalAfterGSt));
            AppLog.e(RetailSalesAdapter.class.getSimpleName(), "updated: " + Util.getCustomGson().toJson(IPOSApplication.mProductListResult));
            isClicked = false;
        }
    }

    /**
     * On click.
     *
     * @param view the view
     */
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
//            case R.id.imvQRCode:
//                ((MainActivity) mContext).launchActivity(FullScannerActivity.class);
//                break;
            case R.id.tvMoreDetails:
                llTotalDiscountDetail.setVisibility(View.VISIBLE);
                llTotalGST.setVisibility(View.GONE);
                break;
            case R.id.tvLessDetails:
                llTotalDiscountDetail.setVisibility(View.GONE);
                llTotalGST.setVisibility(View.VISIBLE);
                break;
            case R.id.imvDicount:
                if (IPOSApplication.mProductListResult.size() > 0)
                    setonImvDiscount();
                else
                    Util.showToast("Discount cannot be applied with empty list", mContext);

                break;
            case R.id.tvOTCDiscount:
                AppLog.e(TAG, "click");
                if (IPOSApplication.mProductListResult.size() > 0)
                    setOTCDiscount();
                else
                    Util.showToast("Discount cannot be applied with empty list", mContext);
                break;
            case R.id.tvRight1:
                AppLog.e(TAG, "click right");
                if (IPOSApplication.mProductListResult.size() > 0)
                    setOTCDiscount();
                else
                    Util.showToast("Discount cannot be applied with empty list", mContext);
                break;
            case R.id.imvClearOTC:
                dialogOTC.dismiss();
                ll_item_pay.setVisibility(View.VISIBLE);
                llOTCSelect.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                    ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(i);
                    datum.setItemSelected(false);
                    datum.setOTCselected(false);
                    IPOSApplication.mProductListResult.set(i, datum);
                }
                mRetailSalesAdapter.notifyDataSetChanged();
                break;

            case R.id.tvMinus:
                setOnClickMinus(view);
                break;

            case R.id.tvPlus:
                setOnClickPlus(view);
                break;

            case R.id.tvApplyOTC:
                if (!etDiscountAmt.getText().toString().trim().equals("")) {
                    if (tbPerc.isChecked()) {
                        if (Integer.parseInt(etDiscountAmt.getText().toString()) > 100) {
                            (new AlertDialog.Builder(mContext, R.style.Theme_AppCompat_DayNight_Dialog_Alert))
                                    .setTitle("Confirm action")
                                    .setMessage("Please enter valid discount percentage")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            etDiscountAmt.setText("");
                                            etDiscountAmt.setEnabled(true);
                                        }
                                    }).show();
                        } else {
                            etDiscountAmt.setEnabled(false);
                            tvApplyOTC.setBackgroundResource(R.drawable.button_rectangle_grey);
                            llOTCConfirmation.setVisibility(View.VISIBLE);
                        }
                    } else {
                        etDiscountAmt.setEnabled(false);
                        tvApplyOTC.setBackgroundResource(R.drawable.button_rectangle_grey);
                        llOTCConfirmation.setVisibility(View.VISIBLE);
                    }
                } else {
                    etDiscountAmt.setEnabled(true);
                    Util.showToast("Please enter Discount Value", mContext);
                }
                break;
            case R.id.tvApplyOTC2:
                if (!etDiscountAmt.getText().toString().trim().equals("")) {
                    if (tbPerc.isChecked()) {
                        if (Integer.parseInt(etDiscountAmt.getText().toString()) > 100) {
                            (new AlertDialog.Builder(mContext)).setTitle("Confirm action")
                                    .setMessage("Please enter valid discount percentage")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            etDiscountAmt.setText("");
                                        }
                                    }).show();
                        } else {
                            ApplyOTC();
                            ll_item_pay.setVisibility(View.VISIBLE);
                            llOTCSelect.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                                ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(i);
                                datum.setItemSelected(false);
                                IPOSApplication.mProductListResult.set(i, datum);
                            }
                            mRetailSalesAdapter.notifyDataSetChanged();
                            dialogOTC.dismiss();
                        }
                    } else {
                        ApplyOTC();
                        ll_item_pay.setVisibility(View.VISIBLE);
                        llOTCSelect.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        dialogOTC.dismiss();
                        for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                            ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(i);
                            datum.setItemSelected(false);
                            IPOSApplication.mProductListResult.set(i, datum);
                        }
                        mRetailSalesAdapter.notifyDataSetChanged();
                    }
                } else {
                    Util.showToast("Please enter Discount Value", mContext);
                }
                break;

            case R.id.imvPin:
//                setArrayPinned();
                cachedPinned();
                if (SharedPrefUtil.getString(Constants.mInfoArrayList, "", mContext) != null && !SharedPrefUtil.getString(Constants.mInfoArrayList, "", mContext).equalsIgnoreCase("")) {

                    openPinnedDetailActivity(true);
                } else {

                    Util.showToast(getString(R.string.pinned_empty), mContext);
                }
                totalAmount =0.0;
                break;

            case R.id.imvRedeem:

                if (IPOSApplication.mProductListResult.size() > 0)
                    showRedeemLoyaltyPopup(rootView);
                else
                    Util.showToast(getString(R.string.redeem_cannot_applied), mContext);

                break;
            case R.id.buttonSendOtp:
                AppLog.e(TAG, "buttonSendOtp click");
                Util.showToast(getString(R.string.sending_otp), mContext);
                break;
            case R.id.buttonRedeem:

                break;
            case R.id.buttonVerify:

                break;

            case R.id.ImvClose:
                mDiscountDeleteFragment.dismiss();
                addDeleteDiscount();
                break;

            case R.id.btnNo:
                mDiscountDeleteFragment.dismiss();
                addDeleteDiscount();
                break;

            case R.id.btnYes:
                setDeleteDiscount();
                break;

            case R.id.imvClear:
                final int posClear = (int) view.getTag();
                (new AlertDialog.Builder(mContext)).setTitle(R.string.confirm_action)
                        .setMessage(R.string.delete_item_message)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                IPOSApplication.mProductListResult.remove(posClear);
//                                mRetailSalesAdapter.notifyItemRemoved(posClear);
//                                mRetailSalesAdapter.notifyItemRangeChanged(posClear, IPOSApplication.mProductListResult.size());
                                setUpdateValues(IPOSApplication.mProductListResult);
                            }
                        }).setNegativeButton(R.string.no, null).show();

                break;
            case R.id.imvUserAdd:
                Intent mIntent = new Intent(mContext, CustomerInfoActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tvPay:
                flScanner.setVisibility(View.GONE);
                closeFragment();
                if (IPOSApplication.mProductListResult.size() > 0) {
                    Intent i = new Intent(mContext, PaymentModeActivity.class);
                    i.putExtra(Constants.TOTAL_AMOUNT, totalAmount + "");
                    mContext.startActivity(i);
                } else {
                    Util.showToast("Please add atleast one item to proceed.", mContext);
                }
                totalAmount =0.0;
                break;
            case R.id.imvRight:
                if (totalAmount > 0) {
                    Intent i = new Intent(mContext, PaymentModeActivity.class);
                    i.putExtra(Constants.TOTAL_AMOUNT, totalAmount + "");
                    mContext.startActivity(i);
                } else {
                    Util.showToast("Please add atleast one item to proceed.", mContext);
                }
                totalAmount =0.0;
                break;
        }
    }


    private void cachedPinned() {

        if (IPOSApplication.mProductListResult != null)
            if (IPOSApplication.mProductListResult.size() > 0) {
                if (SharedPrefUtil.getString("mInfoArrayList", "", mContext) != null) {
                    String json2 = SharedPrefUtil.getString("mInfoArrayList", "", mContext);
                    if (!json2.equalsIgnoreCase(""))
                        mInfoArrayList = Util.getCustomGson().fromJson(json2, new TypeToken<ArrayList<RealmPinnedResults.Info>>() {
                        }.getType());
                }


                RealmPinnedResults mPinnedResult = new RealmPinnedResults();
                RealmPinnedResults.Info mInfo = mPinnedResult.new Info();
                if (childPosition != -1) {
                    mInfo.setKey(mInfoArrayList.get(childPosition).getKey());
                    mInfo.setData(IPOSApplication.mProductListResult);
                    mInfoArrayList.set(childPosition, mInfo);
                } else {
                    mInfo.setKey(Util.getCurrentTimeStamp());
                    mInfo.setData(IPOSApplication.mProductListResult);
                    if (mInfoArrayList == null) {
                        mInfoArrayList = new ArrayList<>();
                    }
                    mInfoArrayList.add(0, mInfo);
                }

                String json = Util.getCustomGson().toJson(mInfoArrayList);
                SharedPrefUtil.putString(Constants.mInfoArrayList, json, mContext);
//            IPOSApplication.mProductListResult.clear();

            }

    }

    void openPinnedDetailActivity(boolean showScreen) {
        if (showScreen) {
            Intent mIntent = new Intent(mContext, PinnedRetailActivity.class);
            startActivityForResult(mIntent, 2);
            IPOSApplication.mProductListResult.clear();
            mRetailSalesAdapter.notifyDataSetChanged();
        }
    }

    private void setArrayPinned() {
//        String key = Util.getCurrentTimeStamp();
//        RealmPinnedResults mPinnedResult = new RealmPinnedResults();
//        RealmPinnedResults.Info mInfo = mPinnedResult.new Info();
//        mInfo.setKey(key);
//        mInfo.setData(IPOSApplication.mProductList);
//        mInfoArrayList.add(0,mInfo);
//        mPinnedResult.setInfo(mInfoArrayList);
//        new RealmController().savePin(mPinnedResult);
//        Intent mIntent = new Intent(mContext, PinnedRetailActivity.class);
//        startActivityForResult(mIntent,2);
    }

    private void ApplyOTC() {
        tvApplyOTC.setBackgroundResource(R.drawable.button_rectangle_grey);
        tvApplyOTC.setEnabled(false);
        for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
            ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(i);
            isOTC = datum.isOTCselected();
            if (isOTC) {
                datum.setDiscSelected(true);
                datum.setItemSelected(false);
                datum.setOTCselected(false);
//                if (datum.getIsDiscount()) {
//                    Double price = datum.getSProductPrice();
//                    Double discount = (datum.getSDiscountPrice() * price) / 100;
//                    afterDiscountPrice = price - discount;
//                } else {
//                    afterDiscountPrice = datum.getSProductPrice();
//                }
                if (tbPerc.isChecked())
                    otcDiscount = (Double.parseDouble(etDiscountAmt.getText().toString()) * afterDiscountPrice) / 100;
                else
                    otcDiscount = Double.parseDouble(etDiscountAmt.getText().toString());
                datum.setOTCDiscount(Util.round(otcDiscount, 1));
                IPOSApplication.mProductListResult.set(i, datum);

            }
        }
        if (isOTC)
            Util.showToast("OTC Discount Applied!", mContext);
        mRetailSalesAdapter.notifyDataSetChanged();
        setUpdateValues(IPOSApplication.mProductListResult);
    }

    private void setOTCDiscount() {

        for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
            if (IPOSApplication.mProductListResult.get(i).isOTCselected())
                isOTC = true;
        }
        if (isOTC) {
            dialogOTCTask();
            mRecyclerView.setVisibility(View.GONE);
        } else {
            Util.showToast("Please select atleast one Item", mContext);
        }
    }

    private void setOnClickPlus(View view) {
        Util.hideSoftKeyboard(getActivity());
        Util.animateView(view);
        isClicked = true;
        int posPlus = (int) view.getTag();
        ProductSearchResult.Datum datum1 = IPOSApplication.mProductListResult.get(posPlus);
        int qty1 = datum1.getQty();
        if (datum1.getSProductStock() <= qty1) {
            Util.showToast("Quantity limit exceed", mContext);
        } else {


            datum1.setQty(qty1 + 1);
            IPOSApplication.mProductListResult.set(posPlus, datum1);
            mRetailSalesAdapter.notifyItemChanged(posPlus);
            setUpdateValues(IPOSApplication.mProductListResult);
        }

    }

    private void setOnClickMinus(View view) {
        Util.hideSoftKeyboard(getActivity());
        Util.animateView(view);
        isClicked = true;
        int posMinus = (int) view.getTag();
        ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(posMinus);
        int qty = datum.getQty();
        if (qty == 1) {
            Util.showToast("Cannot purchase with 0 quantity", mContext);
            return;
        } else {
            datum.setQty(qty - 1);
            IPOSApplication.mProductListResult.set(posMinus, datum);
            mRetailSalesAdapter.notifyItemChanged(posMinus);
            setUpdateValues(IPOSApplication.mProductListResult);
        }

    }

    private void setonImvDiscount() {

        if (llOTCSelect.getVisibility() == View.GONE) {
            ll_item_pay.setVisibility(View.GONE);
            llOTCSelect.setVisibility(View.VISIBLE);
            for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(i);
                datum.setItemSelected(true);
                IPOSApplication.mProductListResult.set(i, datum);

            }
        } else {
            ll_item_pay.setVisibility(View.VISIBLE);
            llOTCSelect.setVisibility(View.GONE);
            for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(i);
                datum.setItemSelected(false);
                datum.setOTCselected(false);
                IPOSApplication.mProductListResult.set(i, datum);

            }
        }
        mRetailSalesAdapter.notifyDataSetChanged();
    }

    /**
     * The all checked.
     */
    int mAllChecked = 0;

    /**
     * On checked changed.
     *
     * @param compoundButton the compound button
     * @param isChecked      the is checked
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int id = compoundButton.getId();
        switch (id) {
            case R.id.chkOTCDiscount:
                if (compoundButton.isPressed()) {
                    final int posItem = (int) compoundButton.getTag();
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {

                            ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(posItem);
                            if (!datum.isDiscSelected())
                                datum.setDiscSelected(true);
                            else {
                                datum.setDiscSelected(false);
                            }
                            IPOSApplication.mProductListResult.set(posItem, datum);
                            mRetailSalesAdapter.notifyItemChanged(posItem);
                            setUpdateValues(IPOSApplication.mProductListResult);
                        }
                    });
                }
                break;
            case R.id.chkItem:
                if (compoundButton.isPressed()) {
                    final int posItem = (int) compoundButton.getTag();
                  /*  mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {

                            ProductList.Datum datum = IPOSApplication.mProductList.get(posItem);
                            if (!datum.isOTCselected())
                                datum.setOTCselected(true);
                            else {
                                datum.setOTCselected(false);
                                chkOTC.setChecked(false);
                            }
                            IPOSApplication.mProductList.set(posItem, datum);
                            mRetailSalesAdapter.notifyItemChanged(posItem);

                        }
                    });*/
                    ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(posItem);
                    if (!datum.isOTCselected())
                        datum.setOTCselected(true);
                    else {
                        datum.setOTCselected(false);
                        chkOTC.setChecked(false);
                    }
                    IPOSApplication.mProductListResult.set(posItem, datum);
                    mRetailSalesAdapter.notifyItemChanged(posItem);
                    mAllChecked = 0;
                    for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                        ProductSearchResult.Datum datum1 = IPOSApplication.mProductListResult.get(i);
                        if (datum1.isOTCselected()) {
                            mAllChecked++;
                        } else {
                            mAllChecked--;
                        }
                    }
                    if (IPOSApplication.mProductListResult.size() == mAllChecked) {
                        chkOTC.setChecked(true);
                    } else
                        chkOTC.setChecked(false);
                }
                break;
            case R.id.chkOTC:
                if (compoundButton.isPressed()) {
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (chkOTC.isChecked()) {
                                for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                                    ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(i);
                                    datum.setOTCselected(true);
                                    IPOSApplication.mProductListResult.set(i, datum);
                                }
                            } else {
                                for (int i = 0; i < IPOSApplication.mProductListResult.size(); i++) {
                                    ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(i);
                                    datum.setOTCselected(false);
                                    IPOSApplication.mProductListResult.set(i, datum);
                                }
                            }
                            mRetailSalesAdapter.notifyDataSetChanged();
                        }
                    });
                }
                break;
            case R.id.chkDiscount:
                if (compoundButton.isPressed()) {
                    posDeleteItem = (int) compoundButton.getTag();
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {

                            ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(posDeleteItem);
                            if (datum.isDiscItemSelected()) {
                                FragmentManager fragmentManager = getChildFragmentManager();
                                mDiscountDeleteFragment = DiscountDeleteFragment.newInstance();
                                mDiscountDeleteFragment.setDialogInfo(RetailSalesFragment.this, datum);
                                mDiscountDeleteFragment.show(fragmentManager, "Delete Discount");
                            } else {
                                addDeleteDiscount();
                            }
                        }
                    });
                }
                break;
        }
    }

    /**
     * The Pos delete item.
     */
    int posDeleteItem = 0;
    /**
     * The M discount delete fragment.
     */
    DiscountDeleteFragment mDiscountDeleteFragment;

    private void setDeleteDiscount() {
        ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(posDeleteItem);
        if (!datum.isDiscItemSelected()) {
            datum.setDiscItemSelected(true);
        } else {
            datum.setDiscItemSelected(false);
        }
        IPOSApplication.mProductListResult.set(posDeleteItem, datum);
        mRetailSalesAdapter.notifyItemChanged(posDeleteItem);
        setUpdateValues(IPOSApplication.mProductListResult);
        mDiscountDeleteFragment.dismiss();
    }

    private void addDeleteDiscount() {
        ProductSearchResult.Datum datum = IPOSApplication.mProductListResult.get(posDeleteItem);
        if (!datum.isDiscItemSelected()) {
            datum.setDiscItemSelected(true);
        }

        IPOSApplication.mProductListResult.set(posDeleteItem, datum);
        mRetailSalesAdapter.notifyItemChanged(posDeleteItem);
        setUpdateValues(IPOSApplication.mProductListResult);
        mDiscountDeleteFragment.dismiss();
    }

    /**
     * The Points.
     */
    int points = 500;

    /**
     * Show redeem loyalty popup.
     *
     * @param v the v
     */
    public void showRedeemLoyaltyPopup(View v) {
        Bundle args = new Bundle();
        args.putInt("points", points);

        FragmentManager fragmentManager = getChildFragmentManager();
        MyDialogFragment mMyDialogFragment = MyDialogFragment.newInstance();
        mMyDialogFragment.setDialogInfo(this);
        mMyDialogFragment.setArguments(args);
        mMyDialogFragment.show(fragmentManager, "Redeem");

    }

    /**
     * On row clicked.
     *
     * @param position the position
     */
    @Override
    public void onRowClicked(int position) {
        setUpdateValues(IPOSApplication.mProductListResult);

    }
    ArrayList<ProductSearchResult.Datum> datumArrayList1 = new ArrayList<>();
    @Override
    public void onRowClicked(int position, int value, String tag) {
        if(tag.equalsIgnoreCase(Constants.DISCOUNT+"")){

            for (int j = 0 ; j < IPOSApplication.datumArrayList.size() ; j++){
//                    if(IPOSApplication.datumArrayList.get(j).getProductCode().equalsIgnoreCase(IPOSApplication.mProductListResult.get(position).getProductCode())){
                ProductSearchResult.Datum datum = IPOSApplication.datumArrayList.get(j);

//                        if (datumArrayList1.size()>0) {
//                            for (int k = 0; k < datumArrayList1.size(); k++) {
//                                if (datum.getProductCode().equalsIgnoreCase(datumArrayList1.get(k).getProductCode())) {
//                                    datumArrayList1.add(datum);
//                                    return;
//
//                                }
//                            }
//                        }else {
//                            datumArrayList1.add(datum);
//                        }
                if(IPOSApplication.mProductListResult.size()>0) {
                    if (IPOSApplication.datumArrayList.get(j).getProductCode().equalsIgnoreCase(IPOSApplication.mProductListResult.get(position).getProductCode())) {
                        datumArrayList1.add(datum);

                    } else {
                        datumArrayList1 = new ArrayList<>();
                        datumArrayList1.add(datum);
                    }
                }else {
                    datumArrayList1 = new ArrayList<>();
                    datumArrayList1.add(datum);
                }

                IPOSApplication.datumSameCode.put(IPOSApplication.datumArrayList.get(j).getProductCode(), datumArrayList1);

            }
        }
    }

    /**
     * On row clicked.
     *
     * @param position the position
     * @param value    the value
     */
    @Override
    public void onRowClicked(final int position, final int value) {
        ProductSearchResult.Datum datum1 = IPOSApplication.mProductListResult.get(position);

        if (value <= datum1.getSProductStock()) {

            datum1.setQty(value);
            IPOSApplication.mProductListResult.set(position, datum1);
            mRetailSalesAdapter.notifyItemChanged(position);
            setUpdateValues(IPOSApplication.mProductListResult);
        } else {
            datum1.setQty(datum1.getSProductStock());
            IPOSApplication.mProductListResult.set(position, datum1);
            mRetailSalesAdapter.notifyItemChanged(position);
            setUpdateValues(IPOSApplication.mProductListResult);
            Util.showToast(datum1.getSProductStock() + " " + getString(R.string.qty_available), mContext);
        }
//        mRecyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }});
        Util.hideSoftKeyboard(getActivity());
    }

    /**
     * On dialog positive click.
     *
     * @param dialog    the dialog
     * @param mCallType the m call type
     */
    @Override
    public void onDialogPositiveClick(Dialog dialog, int mCallType) {
        if (mCallType == Constants.APP_DIALOG_OTC) {
            dialog.dismiss();
            etDiscountAmt.setText("");
        } else if (mCallType == Constants.APP_DIALOG_Cart) {
            if (IPOSApplication.mProductListResult.size() > 0)
                cachedPinned();
            else
                Util.showToast("Cannot save empty list", mContext);
            pinnedUpdate();
            dialog.dismiss();
            getFragmentManager().popBackStack();

        }
    }

    /**
     * On dialog negetive click.
     *
     * @param dialog    the dialog
     * @param mCallType the m call type
     */
    @Override
    public void onDialogNegetiveClick(Dialog dialog, int mCallType) {
        if (mCallType == Constants.APP_DIALOG_Cart) {
            if (IPOSApplication.mProductListResult.size() > 0) {
                IPOSApplication.mProductListResult.clear();
                getFragmentManager().popBackStack();
            }
            dialog.dismiss();

        }

    }

    /**
     * Sets product on listener.
     *
     * @param mDatum the m datum
     */
    @Override
    public void setProductOnListener(String mDatum) {
        ArrayList<ProductSearchResult.Datum> arrData = new ArrayList<>();
        json = SharedPrefUtil.getString(Constants.Product_List, "", mContext);
        arrData = Util.getCustomGson().fromJson(json, new TypeToken<ArrayList<ProductListResult.Datum>>() {
        }.getType());
        IPOSApplication.mProductListResult.add(arrData.get(0));
        mRetailSalesAdapter.notifyDataSetChanged();

    }

    /**
     * On pause.
     */
/*  @Override
      public void handleResult(Result rawResult) {
          Toast.makeText(mContext, "Contents = " + rawResult.getContents() +
                  ", Format = " + rawResult.getBarcodeFormat().getName(), Toast.LENGTH_SHORT).show();
          // Note:
          // * Wait 2 seconds to resume the preview.
          // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
          // * I don't know why this is the case but I don't have the time to figure out.
          Handler handler = new Handler();
          handler.postDelayed(new Runnable() {
              @Override
              public void run() {
                  mScannerView.resumeCameraPreview(RetailSalesFragment.this);
              }
          }, 2000);
      }

  */
    @Override
    public void onPause() {
        super.onPause();
        //   mScannerView.stopCamera();
    }

    void openRetailSales(Context mContext) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.retail_dashboard, null);
        mainActivity = (MainActivity) mContext;
        this.mContext = mContext;
        initializeComponent(rootView);
        myDialog = new Dialog(mContext);
        setHasOptionsMenu(true);

    }

    @Override
    public void onUpdate(String title, Context mContext) {
        AppLog.e(TAG, "DIALOG CALLL" + title);
        getProduct();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            mainActivity = (MainActivity) context;
        }
    }
}
