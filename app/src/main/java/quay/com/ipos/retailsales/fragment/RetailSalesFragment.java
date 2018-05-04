package quay.com.ipos.retailsales.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.customerInfo.CustomerInfoActivity;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.ScannerProductListener;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.realmbean.RealmPinnedResults;
import quay.com.ipos.retailsales.activity.AddProductActivity;
import quay.com.ipos.retailsales.activity.CustomerListActivity;
import quay.com.ipos.retailsales.activity.PaymentModeActivity;
import quay.com.ipos.retailsales.activity.PinnedRetailActivity;
import quay.com.ipos.retailsales.adapter.RetailSalesAdapter;
import quay.com.ipos.ui.DiscountDeleteFragment;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.MessageDialogFragment;
import quay.com.ipos.ui.MyDialogFragment;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

import static android.app.Activity.RESULT_OK;

/**
 * Created by aditi.bhuranda on 16-04-2018.
 */

public class RetailSalesFragment extends Fragment implements View.OnClickListener , CompoundButton.OnCheckedChangeListener ,AdapterListener ,MessageDialogFragment.MessageDialogListener,ScannerProductListener {
    private TextView tvRight1,tvMoreDetails,tvItemNo,tvItemQty,tvTotalItemPrice,
            tvTotalGST,tvTotalItemGSTPrice,tvTotalDiscountDetail,tvTotalDiscountPrice,tvCGSTPrice,tvSGSTPrice,
            tvLessDetails,tvRoundingOffPrice,tvTotalDiscount,tvPay,tvOTCDiscount,tvApplyOTC,tvApplyOTC2;
    private FrameLayout flScanner;
    private ToggleButton tbPerc,tbRs;
    private EditText etDiscountAmt ;
    private CheckBox chkOTC;
    private Fragment scanner_fragment;
    private LinearLayout llTotalDiscountDetail,ll_item_pay,llOTCSelect,llTotalGST,llOTCConfirmation;
    private ImageView imvDicount,imvGlobe,imvUserAdd,imvPin,imvRedeem,imvRight,imvClearOTC;
    private ToggleButton chkBarCode;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private String TAG=RetailSalesFragment.class.getSimpleName();
    private RetailSalesAdapter mRetailSalesAdapter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private ProductList mProductListResult;
    Dialog myDialog;
    double otcDiscount=0.0;
    View rootView;
    private double totalAmount=0;
    private boolean isFragmentDisplayed = true;
//    private ArrayList<ProductList.Datum> mList= new ArrayList<>();

    boolean isOTC=false;
    Double afterDiscountPrice;
    ArrayList<RealmPinnedResults.Info> mInfoArrayList = new ArrayList<>();
    private String json;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.retail_dashboard, container, false);
        initializeComponent(rootView);
        myDialog = new Dialog(getActivity());
        setHasOptionsMenu(true);
        Util.hideSoftKeyboard(getActivity());
        return rootView;
    }



    private void initializeComponent(View rootView) {
        flScanner = rootView.findViewById(R.id.flScanner);
        imvUserAdd = rootView.findViewById(R.id.imvUserAdd);
        imvPin = rootView.findViewById(R.id.imvPin);
        imvRedeem = rootView.findViewById(R.id.imvRedeem);
        imvGlobe = rootView.findViewById(R.id.imvGlobe);
        imvDicount = rootView.findViewById(R.id.imvDicount);
        chkBarCode = rootView.findViewById(R.id.chkBarCode);
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

        mRecyclerView =  rootView.findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dim_5),
                        getResources().getInteger(R.integer.photo_list_preview_columns)));
        setFontText();
        setListener();
        setAdapter();
        setTextDefault();
    }

    private void setTextDefault() {
        tvItemNo.setText("Item 0");
        tvItemQty.setText("0 Qty");
        tvTotalItemPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvTotalItemGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscount.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvPay.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvCGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvSGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvRoundingOffPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvSGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscountPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
        tvTotalDiscountDetail.setText("(Item 0)");
        IPOSApplication.mProductList.clear();
        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            flScanner.setVisibility(View.GONE);
            chkBarCode.setChecked(false);
            boolean request = ((MainActivity) getActivity()).launchActivity();
            if(request ||((MainActivity) getActivity()).CameraPermission )
            {
                setTextDefault();
            }
        }else {
            flScanner.setVisibility(View.VISIBLE);
            chkBarCode.setChecked(true);
            displayFragment();

        }
    }


    private void setFontText() {
        Typeface iconFont = FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME);
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
//        chkBarCode.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (chkBarCode.isChecked()) {
//                    imvQRCode.setVisibility(View.VISIBLE);
////                    Toast.makeText(getActivity(), "ON", Toast.LENGTH_SHORT).show();
//                } else {
//                    imvQRCode.setVisibility(View.GONE);
////                    Toast.makeText(getActivity(), "OFF", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        // Set the click listener for the button.
        chkBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ((MainActivity) getActivity()).launchActivity();
                    chkBarCode.setChecked(false);
                    setTextDefault();
                }else {

                    if (flScanner.getVisibility()==View.GONE) {
                        flScanner.setVisibility(View.VISIBLE);
                        chkBarCode.setChecked(true);
                    } else {
                        flScanner.setVisibility(View.GONE);
                        chkBarCode.setChecked(false);
                    }
                }
            }
        });
    }

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

    public void displayFragment() {
        FullScannerFragment simpleFragment = FullScannerFragment.newInstance();
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

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setToolbarTitle(getString(R.string.retail_sales));
        //You need to add the following line for this solution to work; thanks skayred
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    if(IPOSApplication.mProductList.size()>0)
                    Util.showMessageDialog(RetailSalesFragment.this,"Do you want to save the Cart?","YES","NO",Constants.APP_DIALOG_Cart,"",getActivity().getSupportFragmentManager());
                    else
                        return true;
                }
                return false;
            }
        } );
    }

    Dialog dialogOTC;

    private void dialogOTCTask() {
        dialogOTC = new Dialog(getActivity());
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
                tbRs.setTextColor(getActivity().getResources().getColor(R.color.accent_color));
                tbPerc.setTextColor(getActivity().getResources().getColor(R.color.white));
                if(!etDiscountAmt.getText().toString().trim().equalsIgnoreCase(""))
                    if(Integer.parseInt(etDiscountAmt.getText().toString())>100){
//                        (new AlertDialog.Builder(getActivity())).setTitle("Confirm action")
//                                .setMessage("Please enter valid discount percentage")
//                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                        etDiscountAmt.setText("");
//                                    }
//                                }).show();
                        Util.showMessageDialog(RetailSalesFragment.this,"Please enter valid discount percentage","OK",null,Constants.APP_DIALOG_OTC,"",getActivity().getSupportFragmentManager());
                    }
            }
        });

        tbRs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbPerc.setChecked(false);
                tbRs.setChecked(true);
                tbRs.setTextColor(getActivity().getResources().getColor(R.color.white));
                tbPerc.setTextColor(getActivity().getResources().getColor(R.color.accent_color));
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


    public void onSearchButton(){
        Intent mIntent = new Intent(getActivity(), AddProductActivity.class);
        startActivityForResult(mIntent,1);
    }

    private void setAdapter() {

        mRetailSalesAdapter = new RetailSalesAdapter(getActivity(), this, mRecyclerView, IPOSApplication.mProductList,this,this);
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
    }

    int childPosition= -1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode == 1){
                mRetailSalesAdapter.notifyDataSetChanged();
                getProduct();
            }
        }else if(requestCode==2){
            if(resultCode == 1){
                childPosition = data.getIntExtra("pinned_position",0);
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
//            String json = Util.getAssetJsonResponse(getActivity(), "product_list.json");
//            mProductListResult = Util.getCustomGson().fromJson(json,ProductList.class);
            AppLog.e(RetailSalesFragment.class.getSimpleName(),"Get Product: "+Util.getCustomGson().toJson(IPOSApplication.mProductList));
//            IPOSApplication.mProductList.addAll(mProductListResult.getData());
            setDefaultValues();
//
            mRetailSalesAdapter.notifyDataSetChanged();
            setUpdateValues(IPOSApplication.mProductList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setDefaultValues() {

        Double totalPrice;
        for(int i=0 ; i < IPOSApplication.mProductList.size();i++ )
        {
            ProductList.Datum datum = IPOSApplication.mProductList.get(i);
            if(datum.getQty()==0)
                datum.setQty(1);
            if(!datum.isDiscItemSelected())
                datum.setDiscItemSelected(true);
            totalPrice = (Double.parseDouble(datum.getSProductPrice()) * datum.getQty());
            datum.setTotalPrice(totalPrice);
            if(datum.getIsDiscount()) {
                Double discount = Double.parseDouble(datum.getSDiscountPrice()) * totalPrice / 100;
                datum.setDiscount(discount);
            }else {
                datum.setDiscount(0.0);
            }
            IPOSApplication.mProductList.set(i,datum);
        }
    }


    private void setUpdateValues(ArrayList<ProductList.Datum> mList) {

        AppLog.e(RetailSalesFragment.class.getSimpleName(), "IPOSApplication.mProductList:Frag: "+ Util.getCustomGson().toJson(IPOSApplication.mProductList));
        if(mList.size()==1 || mList.size() == 0) {
            tvItemNo.setText("Item " + mList.size() + " item");
        }else {
            tvItemNo.setText("Items " + mList.size()+ " item");
        }
        if(mList.size()==0){
            tvItemQty.setText("0 Qty");
            tvTotalItemPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
            tvTotalItemGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
            tvTotalDiscount.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
            tvPay.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
            tvCGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
            tvSGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
            tvRoundingOffPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
            tvSGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
            tvTotalDiscountPrice.setText(getActivity().getResources().getString(R.string.Rs) + " 0.0");
            tvTotalDiscountDetail.setText("(Item 0)");
        }else {

            int qty = 0;
            double totalPrice=0.0;
            double sum=0;
            double discount=0, totalGst=0.0, cgst = 0.0, sgst = 0.0;
            int discountItem=0;
            int mSelectedpos=0;
            double totalAfterGSt=0.0;
            double otcDiscountPerc=0.0;
            for(int i = 0 ; i < mList.size(); i++ ) {
                ProductList.Datum datum = mList.get(i);
                qty += mList.get(i).getQty();
                datum.setTotalQty(qty);
                totalPrice=mList.get(i).getQty()*Double.parseDouble(mList.get(i).getSProductPrice());
                sum=totalPrice+sum;
                datum.setTotalPrice(sum);
                if(mList.get(i).isDiscItemSelected()){
                    if(mList.get(i).getIsDiscount()) {
                        discount = discount+Double.parseDouble(mList.get(i).getSDiscountPrice()) * totalPrice / 100;
                        datum.setDiscount(discount);
                        discountItem++;
                    }
                }
                if(mList.get(i).isDiscSelected()) {
                    if(!mList.get(i).isDiscItemSelected())
                        discountItem++;
                    otcDiscountPerc +=mList.get(i).getOTCDiscount();
                }else {
                    otcDiscountPerc -=mList.get(i).getOTCDiscount();
                }
                totalGst = mList.get(i).getGSTPerc()*sum/100;
                totalGst +=totalGst;
                sgst = mList.get(i).getSGST()*sum/100;
                sgst+=sgst;
                cgst = mList.get(i).getCGST()*sum/100;
                cgst+=cgst;
//                totalPrice += mList.get(i).getTotalPrice();
                IPOSApplication.mProductList.set(i,datum);
            }
            tvItemQty.setText(qty+" Qty");
            tvTotalItemPrice.setText(getActivity().getResources().getString(R.string.Rs) +" "+sum);
            tvTotalDiscountPrice.setText("-"+getActivity().getResources().getString(R.string.Rs) +" "+(discount+otcDiscountPerc));
            tvTotalDiscount.setText(getActivity().getResources().getString(R.string.Rs) +" "+(discount+otcDiscountPerc));
            tvTotalDiscountDetail.setText("(Item "+ discountItem+")");
            AppLog.e(RetailSalesFragment.class.getSimpleName(),"totalGst: "+totalGst);
            tvTotalItemGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " + totalGst);

            tvSGSTPrice.setText("+"+getActivity().getResources().getString(R.string.Rs) + " " +sgst);

            tvCGSTPrice.setText("+"+getActivity().getResources().getString(R.string.Rs) + " " +cgst);
            totalAfterGSt = (sum-discount)+(sgst+cgst)-(otcDiscountPerc);
//            double floorValue = Math.round(totalAfterGSt);


            double roundOff = totalAfterGSt - Math.floor( totalAfterGSt );
            tvRoundingOffPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " + (Util.round(roundOff,1)));
            totalAfterGSt = totalAfterGSt +  (Util.round(roundOff,1));
            totalAmount=Math.round(totalAfterGSt);
            tvPay.setText(getActivity().getResources().getString(R.string.Rs) + " " +  Math.round(totalAfterGSt));
            AppLog.e(RetailSalesAdapter.class.getSimpleName(),"updated: " + Util.getCustomGson().toJson(IPOSApplication.mProductList));

        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
//            case R.id.imvQRCode:
//                ((MainActivity) getActivity()).launchActivity(FullScannerActivity.class);
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
                if(IPOSApplication.mProductList.size()>0)
                    setonImvDiscount();
                else
                    Util.showToast("Discount cannot be applied with empty list",getActivity());

                break;
            case R.id.tvOTCDiscount:
                AppLog.e(TAG,"click");
                if(IPOSApplication.mProductList.size()>0)
                    setOTCDiscount();
                else
                    Util.showToast("Discount cannot be applied with empty list",getActivity());
                break;
            case R.id.tvRight1:
                AppLog.e(TAG,"click right");
                if(IPOSApplication.mProductList.size()>0)
                    setOTCDiscount();
                else
                    Util.showToast("Discount cannot be applied with empty list",getActivity());
                break;
            case R.id.imvClearOTC:
                dialogOTC.dismiss();
                ll_item_pay.setVisibility(View.VISIBLE);
                llOTCSelect.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
                    ProductList.Datum datum = IPOSApplication.mProductList.get(i);
                    datum.setItemSelected(false);
                    datum.setOTCselected(false);
                    IPOSApplication.mProductList.set(i, datum);
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
                if(!etDiscountAmt.getText().toString().trim().equals("")) {
                    if(tbPerc.isChecked()) {
                        if (Integer.parseInt(etDiscountAmt.getText().toString()) > 100) {
                            (new AlertDialog.Builder(getActivity(),R.style.Theme_AppCompat_DayNight_Dialog_Alert)).setTitle("Confirm action")
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
                    }else {
                        etDiscountAmt.setEnabled(false);
                        tvApplyOTC.setBackgroundResource(R.drawable.button_rectangle_grey);
                        llOTCConfirmation.setVisibility(View.VISIBLE);
                    }
                }else {
                    etDiscountAmt.setEnabled(true);
                    Util.showToast("Please enter Discount Value",getActivity());
                }
                break;
            case R.id.tvApplyOTC2:
                if(!etDiscountAmt.getText().toString().trim().equals("")) {
                    if(tbPerc.isChecked()) {
                        if (Integer.parseInt(etDiscountAmt.getText().toString()) > 100) {
                            (new AlertDialog.Builder(getActivity())).setTitle("Confirm action")
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
                            for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
                                ProductList.Datum datum = IPOSApplication.mProductList.get(i);
                                datum.setItemSelected(false);
                                IPOSApplication.mProductList.set(i, datum);
                            }
                            mRetailSalesAdapter.notifyDataSetChanged();
                            dialogOTC.dismiss();
                        }
                    }else {
                        ApplyOTC();
                        ll_item_pay.setVisibility(View.VISIBLE);
                        llOTCSelect.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        dialogOTC.dismiss();
                        for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
                            ProductList.Datum datum = IPOSApplication.mProductList.get(i);
                            datum.setItemSelected(false);
                            IPOSApplication.mProductList.set(i, datum);
                        }
                        mRetailSalesAdapter.notifyDataSetChanged();
                    }
                }else {
                    Util.showToast("Please enter Discount Value",getActivity());
                }
                break;

            case R.id.imvPin:
//                setArrayPinned();
                if(IPOSApplication.mProductList.size()>0)
                    cachedPinned();
                else
                    Util.showToast("Cannot pin empty list",getActivity());

                break;
            case R.id.imvRedeem:

                if(IPOSApplication.mProductList.size()>0)
                    showRedeemLoyaltyPopup(rootView);
                else
                    Util.showToast("Redeem cannot be applied with empty list",getActivity());

                break;
            case R.id.buttonSendOtp:
                AppLog.e(TAG,"buttonSendOtp click");
                Util.showToast("Sending OTP",getActivity());
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
                (new AlertDialog.Builder(getActivity())).setTitle("Confirm action")
                        .setMessage("Do you want to Delete this Item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                IPOSApplication.mProductList.remove(posClear);
                                mRetailSalesAdapter.notifyItemChanged(posClear);
                                setUpdateValues(IPOSApplication.mProductList);
                            }
                        }).setNegativeButton("No", null).show();

                break;
            case R.id.imvUserAdd:
                Intent mIntent = new Intent(getActivity(), CustomerInfoActivity.class);
                startActivity(mIntent);
                break;
            case R.id.tvPay:
                if (totalAmount>0) {
                    Intent i = new Intent(getActivity(), PaymentModeActivity.class);
                    i.putExtra(Constants.TOTAL_AMOUNT,totalAmount+"");
                    getActivity().startActivity(i);
                }else {
                    Util.showToast("Please add atleast one item to proceed.");
                }
                break;
            case R.id.imvRight:
                if (totalAmount>0) {
                    Intent i = new Intent(getActivity(), PaymentModeActivity.class);
                    i.putExtra(Constants.TOTAL_AMOUNT,totalAmount+"");
                    getActivity().startActivity(i);
                }else {
                    Util.showToast("Please add atleast one item to proceed.");
                }
                break;
        }
    }




    private void cachedPinned() {

        if(IPOSApplication.mProductList!=null)
            if(IPOSApplication.mProductList.size()>0) {
                if( SharedPrefUtil.getString("mInfoArrayList","",getActivity())!=null) {
                    String json2 = SharedPrefUtil.getString("mInfoArrayList", "", getActivity());
                    if (json2.equalsIgnoreCase(""))
                        mInfoArrayList = Util.getCustomGson().fromJson(json2, new TypeToken<ArrayList<RealmPinnedResults.Info>>() {
                        }.getType());
                }


                RealmPinnedResults mPinnedResult = new RealmPinnedResults();
                RealmPinnedResults.Info mInfo = mPinnedResult.new Info();
                if(childPosition!=-1) {
                    mInfo.setKey(mInfoArrayList.get(childPosition).getKey());
                    mInfo.setData(IPOSApplication.mProductList);
                    mInfoArrayList.set(childPosition, mInfo);
                }
                else {
                    mInfo.setKey(Util.getCurrentTimeStamp());
                    mInfo.setData(IPOSApplication.mProductList);
                    mInfoArrayList.add(0, mInfo);
                }

                String json = Util.getCustomGson().toJson(mInfoArrayList);
                SharedPrefUtil.putString("mInfoArrayList", json, getActivity());
//            IPOSApplication.mProductList.clear();
                Intent mIntent = new Intent(getActivity(), PinnedRetailActivity.class);
                startActivityForResult(mIntent,2);
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
//        Intent mIntent = new Intent(getActivity(), PinnedRetailActivity.class);
//        startActivityForResult(mIntent,2);
    }

    private void ApplyOTC() {
        tvApplyOTC.setBackgroundResource(R.drawable.button_rectangle_grey);
        tvApplyOTC.setEnabled(false);
        for(int i = 0 ; i < IPOSApplication.mProductList.size();i++){
            ProductList.Datum datum = IPOSApplication.mProductList.get(i);
            isOTC = datum.isOTCselected();
            if(isOTC) {
                datum.setDiscSelected(true);
                datum.setItemSelected(false);
                datum.setOTCselected(false);
                if(datum.getIsDiscount()){
                    Double price = Double.parseDouble(datum.getSProductPrice());
                    Double discount = (Double.parseDouble(datum.getSDiscountPrice())*price)/100;
                    afterDiscountPrice = price - discount;
                }else {
                    afterDiscountPrice = Double.parseDouble(datum.getSProductPrice());
                }
                if (tbPerc.isChecked())
                    otcDiscount = (Double.parseDouble(etDiscountAmt.getText().toString()) * afterDiscountPrice )/ 100;
                else
                    otcDiscount =  Double.parseDouble(etDiscountAmt.getText().toString());
                datum.setOTCDiscount(Util.round(otcDiscount,1));
                IPOSApplication.mProductList.set(i, datum);

            }
        }
        if(isOTC)
            Util.showToast("OTC Discount Applied!",getActivity());
        mRetailSalesAdapter.notifyDataSetChanged();
        setUpdateValues(IPOSApplication.mProductList);
    }

    private void setOTCDiscount() {

        for(int i = 0 ; i < IPOSApplication.mProductList.size();i++){
            if(IPOSApplication.mProductList.get(i).isOTCselected())
                isOTC=true;
        }
        if(isOTC) {
            dialogOTCTask();
            mRecyclerView.setVisibility(View.GONE);
        }else{
            Util.showToast("Please select atleast one Item",getActivity());
        }
    }

    private void setOnClickPlus(View view) {
        Util.hideSoftKeyboard(getActivity());
        Util.animateView(view);
        int posPlus = (int) view.getTag();
        ProductList.Datum datum1 = IPOSApplication.mProductList.get(posPlus);
        int qty1 = datum1.getQty();
        if(Integer.parseInt(datum1.getSProductPoints())<=qty1){
            Util.showToast("Quantity limit exceed",getActivity());
        }else {
            datum1.setQty(qty1 + 1);
            IPOSApplication.mProductList.set(posPlus, datum1);
            mRetailSalesAdapter.notifyItemChanged(posPlus);
            setUpdateValues(IPOSApplication.mProductList);
        }
    }

    private void setOnClickMinus(View view) {
        Util.hideSoftKeyboard(getActivity());
        Util.animateView(view);
        int posMinus = (int) view.getTag();
        ProductList.Datum datum = IPOSApplication.mProductList.get(posMinus);
        int qty = datum.getQty();
        if(qty==1){
            Util.showToast("Cannot purchase with 0 quantity",getActivity());
            return;
        }else {
            datum.setQty(qty - 1);
            IPOSApplication.mProductList.set(posMinus, datum);
            mRetailSalesAdapter.notifyItemChanged(posMinus);
            setUpdateValues(IPOSApplication.mProductList);
        }
    }

    private void setonImvDiscount() {
        if(llOTCSelect.getVisibility()==View.GONE) {
            ll_item_pay.setVisibility(View.GONE);
            llOTCSelect.setVisibility(View.VISIBLE);
            for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
                ProductList.Datum datum = IPOSApplication.mProductList.get(i);
                datum.setItemSelected(true);
                IPOSApplication.mProductList.set(i, datum);

            }
        }else {
            ll_item_pay.setVisibility(View.VISIBLE);
            llOTCSelect.setVisibility(View.GONE);
            for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
                ProductList.Datum datum = IPOSApplication.mProductList.get(i);
                datum.setItemSelected(false);
                datum.setOTCselected(false);
                IPOSApplication.mProductList.set(i, datum);

            }
        }
        mRetailSalesAdapter.notifyDataSetChanged();
    }

    int mAllChecked = 0;
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int id = compoundButton.getId();
        switch (id) {
            case R.id.chkOTCDiscount:
                if (compoundButton.isPressed()){
                    final int posItem = (int) compoundButton.getTag();
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {

                            ProductList.Datum datum = IPOSApplication.mProductList.get(posItem);
                            if (!datum.isDiscSelected())
                                datum.setDiscSelected(true);
                            else {
                                datum.setDiscSelected(false);
                            }
                            IPOSApplication.mProductList.set(posItem, datum);
                            mRetailSalesAdapter.notifyItemChanged(posItem);
                            setUpdateValues(IPOSApplication.mProductList);
                        }
                    });
                }
                break;
            case R.id.chkItem:
                if (compoundButton.isPressed()){
                    final int posItem = (int) compoundButton.getTag();
                    mRecyclerView.post(new Runnable() {
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
                    });
                    for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
                        ProductList.Datum datum1 = IPOSApplication.mProductList.get(i);
                        if(datum1.isOTCselected()){
                            mAllChecked++;
                        }
                    }
                    if(IPOSApplication.mProductList.size()-1==mAllChecked){
                        chkOTC.setChecked(true);
                    }else
                        chkOTC.setChecked(false);
                }
                break;
            case R.id.chkOTC:
                if (compoundButton.isPressed()){
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            if(chkOTC.isChecked()) {
                                for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
                                    ProductList.Datum datum = IPOSApplication.mProductList.get(i);
                                    datum.setOTCselected(true);
                                    IPOSApplication.mProductList.set(i, datum);
                                }
                            }else {
                                for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
                                    ProductList.Datum datum = IPOSApplication.mProductList.get(i);
                                    datum.setOTCselected(false);
                                    IPOSApplication.mProductList.set(i, datum);
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

                            ProductList.Datum datum = IPOSApplication.mProductList.get(posDeleteItem);
                            if(datum.isDiscItemSelected()) {
                                FragmentManager fragmentManager = getChildFragmentManager();
                                mDiscountDeleteFragment = DiscountDeleteFragment.newInstance();
                                mDiscountDeleteFragment.setDialogInfo(RetailSalesFragment.this,datum);
                                mDiscountDeleteFragment.show(fragmentManager, "Delete Discount");
                            }else {
                                addDeleteDiscount();
                            }
                        }
                    });
                }
                break;
        }
    }
    int posDeleteItem=0;
    DiscountDeleteFragment mDiscountDeleteFragment;
    private void setDeleteDiscount() {
        ProductList.Datum datum = IPOSApplication.mProductList.get(posDeleteItem);
        if (!datum.isDiscItemSelected()) {
            datum.setDiscItemSelected(true);
        }
        else {
            datum.setDiscItemSelected(false);
        }
        IPOSApplication.mProductList.set(posDeleteItem, datum);
        mRetailSalesAdapter.notifyItemChanged(posDeleteItem);
        setUpdateValues(IPOSApplication.mProductList);
        mDiscountDeleteFragment.dismiss();
    }

    private void addDeleteDiscount() {
        ProductList.Datum datum = IPOSApplication.mProductList.get(posDeleteItem);
        if (!datum.isDiscItemSelected()) {
            datum.setDiscItemSelected(true);
        }

        IPOSApplication.mProductList.set(posDeleteItem, datum);
        mRetailSalesAdapter.notifyItemChanged(posDeleteItem);
        setUpdateValues(IPOSApplication.mProductList);
        mDiscountDeleteFragment.dismiss();
    }
    int points = 500;
    public void showRedeemLoyaltyPopup(View v) {
        Bundle args = new Bundle();
        args.putInt("points", points);

        FragmentManager fragmentManager = getChildFragmentManager();
        MyDialogFragment mMyDialogFragment = MyDialogFragment.newInstance();
        mMyDialogFragment.setDialogInfo(this);
        mMyDialogFragment.setArguments(args);
        mMyDialogFragment.show(fragmentManager, "Redeem");

    }

    @Override
    public void onRowClicked(int position) {



    }

    @Override
    public void onRowClicked(final int position, final int value) {
        ProductList.Datum datum1 = IPOSApplication.mProductList.get(position);

        if ( value<=Integer.parseInt(datum1.getSProductPoints())) {

            datum1.setQty(value);
            IPOSApplication.mProductList.set(position, datum1);
            mRetailSalesAdapter.notifyItemChanged(position);
            setUpdateValues(IPOSApplication.mProductList);
        }else {
            datum1.setQty(Integer.parseInt(datum1.getSProductPoints()));
            IPOSApplication.mProductList.set(position, datum1);
            mRetailSalesAdapter.notifyItemChanged(position);
            setUpdateValues(IPOSApplication.mProductList);
            Util.showToast(datum1.getSProductPoints()+" "+getString(R.string.qty_available),getActivity());
        }
//        mRecyclerView.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }});
        Util.hideSoftKeyboard(getActivity());
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog,int mCallType) {
        if(mCallType==Constants.APP_DIALOG_OTC) {
            dialog.dismiss();
            etDiscountAmt.setText("");
        }else if(mCallType==Constants.APP_DIALOG_Cart){

        }
    }

    @Override
    public void onDialogNegetiveClick(DialogFragment dialog, int mCallType) {
        if(mCallType==Constants.APP_DIALOG_Cart){
            IPOSApplication.mProductList.clear();
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public void setProductOnListener(String mDatum) {
        ArrayList<ProductList.Datum> arrData= new ArrayList<>();
        json = SharedPrefUtil.getString(Constants.Product_List,"",getActivity());
        arrData = Util.getCustomGson().fromJson(json, new TypeToken<ArrayList<ProductList.Datum>>(){}.getType());
        IPOSApplication.mProductList.add(arrData.get(0));
        mRetailSalesAdapter.notifyDataSetChanged();

    }
}
