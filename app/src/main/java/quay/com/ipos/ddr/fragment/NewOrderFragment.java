package quay.com.ipos.ddr.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
import android.widget.CompoundButton;
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
import quay.com.ipos.ddr.activity.NewOrderDetailsActivity;
import quay.com.ipos.ddr.adapter.NewOrderListAdapter;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.ScannerProductListener;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.realmbean.RealmPinnedResults;
import quay.com.ipos.retailsales.activity.AddProductActivity;
import quay.com.ipos.retailsales.activity.PaymentModeActivity;
import quay.com.ipos.retailsales.adapter.RetailSalesAdapter;
import quay.com.ipos.retailsales.fragment.FullScannerFragment;
import quay.com.ipos.ui.DiscountDeleteFragment;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.MessageDialogFragment;
import quay.com.ipos.ui.MyDialogFragment;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 03-05-2018.
 */

public class NewOrderFragment extends Fragment implements View.OnClickListener , CompoundButton.OnCheckedChangeListener ,AdapterListener,MessageDialogFragment.MessageDialogListener,ScannerProductListener {
    private TextView tvMoreDetails,tvItemNo,tvItemQty,tvTotalItemPrice,
            tvTotalGST,tvTotalItemGSTPrice,tvTotalDiscountDetail,tvTotalDiscountPrice,tvCGSTPrice,tvSGSTPrice,
            tvLessDetails,tvRoundingOffPrice,tvPay;
    private FrameLayout flScanner;
    private Fragment scanner_fragment;
    private LinearLayout llTotalDiscountDetail,ll_item_pay,llTotalGST;
    private ImageView imvPin,imvRight;
    private ToggleButton chkBarCode;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private String TAG= NewOrderFragment.class.getSimpleName();
    private NewOrderListAdapter mNewOrderListAdapter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private OrderList mOrderListResult;
    Dialog myDialog;
    double otcDiscount=0.0;
    View rootView;
    private double totalAmount=0;
    private boolean isFragmentDisplayed = true;
//    private ArrayList<ProductList.Datum> mList= new ArrayList<>();

    Double afterDiscountPrice;
    ArrayList<RealmPinnedResults.Info> mInfoArrayList = new ArrayList<>();
    private String json;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_order_dashboard, container, false);
        initializeComponent(rootView);
        myDialog = new Dialog(getActivity());
        setHasOptionsMenu(true);
        Util.hideSoftKeyboard(getActivity());
        return rootView;
    }



    private void initializeComponent(View rootView) {
        flScanner = rootView.findViewById(R.id.flScanner);
        imvPin = rootView.findViewById(R.id.imvPin);
        chkBarCode = rootView.findViewById(R.id.chkBarCode);
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

        mRecyclerView =  rootView.findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dim_5),
                        getResources().getInteger(R.integer.photo_list_preview_columns)));
        setListener();
        setAdapter();
        setTextDefault();
    }

    private void setTextDefault() {
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
        IPOSApplication.mOrderList.clear();
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



    private void setListener() {
        tvMoreDetails.setOnClickListener(this);
        tvLessDetails.setOnClickListener(this);
        imvPin.setOnClickListener(this);
        tvPay.setOnClickListener(this);

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
                    if(IPOSApplication.mOrderList.size()>0)
                        Util.showMessageDialog(NewOrderFragment.this,"Do you want to save the Cart?","YES","NO", Constants.APP_DIALOG_Cart,"",getActivity().getSupportFragmentManager());
                    else
                        return true;
                }
                return false;
            }
        } );
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
//        Intent mIntent = new Intent(getActivity(), AddProductActivity.class);
//        startActivityForResult(mIntent,1);
    }

    private void setAdapter() {

        mNewOrderListAdapter = new NewOrderListAdapter(getActivity(), this, mRecyclerView, IPOSApplication.mOrderList,this,this);
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

    int childPosition= -1;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode == 1){
                mNewOrderListAdapter.notifyDataSetChanged();
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
            IPOSApplication.mOrderList.clear();
            String json = Util.getAssetJsonResponse(getActivity(), "product_list.json");
            mOrderListResult = Util.getCustomGson().fromJson(json,OrderList.class);
            AppLog.e(NewOrderFragment.class.getSimpleName(),"Get Order: "+Util.getCustomGson().toJson(IPOSApplication.mOrderList));
            IPOSApplication.mOrderList.addAll(mOrderListResult.getData());
            setDefaultValues();
//
            mNewOrderListAdapter.notifyDataSetChanged();
            setUpdateValues(IPOSApplication.mOrderList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setDefaultValues() {

        Double totalPrice;
        for(int i=0 ; i < IPOSApplication.mOrderList.size();i++ )
        {
            OrderList.Datum datum = IPOSApplication.mOrderList.get(i);
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
            IPOSApplication.mOrderList.set(i,datum);
        }
    }


    private void setUpdateValues(ArrayList<OrderList.Datum> mList) {

        AppLog.e(quay.com.ipos.retailsales.fragment.RetailSalesFragment.class.getSimpleName(), "IPOSApplication.mOrderList:Frag: "+ Util.getCustomGson().toJson(IPOSApplication.mOrderList));
        if(mList.size()==1 || mList.size() == 0) {
            tvItemNo.setText("Item " + mList.size() + " item");
        }else {
            tvItemNo.setText("Items " + mList.size()+ " item");
        }
        if(mList.size()==0){
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
                OrderList.Datum datum = mList.get(i);
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
                IPOSApplication.mOrderList.set(i,datum);
            }
            tvItemQty.setText(qty+" Qty");
            tvTotalItemPrice.setText(getActivity().getResources().getString(R.string.Rs) +" "+sum);
            tvTotalDiscountPrice.setText("-"+getActivity().getResources().getString(R.string.Rs) +" "+(discount+otcDiscountPerc));
            tvTotalDiscountDetail.setText("(Item "+ discountItem+")");
            AppLog.e(TAG,"totalGst: "+totalGst);
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
            AppLog.e(NewOrderListAdapter.class.getSimpleName(),"updated: " + Util.getCustomGson().toJson(IPOSApplication.mOrderList));

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

            case R.id.tvMinus:
                setOnClickMinus(view);
                break;

            case R.id.tvPlus:
                setOnClickPlus(view);
                break;


            case R.id.imvPin:

                break;


            case R.id.imvClear:
                final int posClear = (int) view.getTag();
                (new AlertDialog.Builder(getActivity())).setTitle("Confirm action")
                        .setMessage("Do you want to Delete this Item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                IPOSApplication.mOrderList.remove(posClear);
                                mNewOrderListAdapter.notifyItemChanged(posClear);
                                setUpdateValues(IPOSApplication.mOrderList);
                            }
                        }).setNegativeButton("No", null).show();

                break;
            case R.id.tvPay:
                if (totalAmount>0) {
                    Intent i = new Intent(getActivity(), NewOrderDetailsActivity.class);
                    i.putExtra(Constants.TOTAL_AMOUNT,totalAmount+"");
                    getActivity().startActivity(i);
                }else {
                    Util.showToast("Please add atleast one item to proceed.");
                }
                break;

        }
    }


    private void setOnClickPlus(View view) {
        Util.hideSoftKeyboard(getActivity());
        Util.animateView(view);
        int posPlus = (int) view.getTag();
        OrderList.Datum datum1 = IPOSApplication.mOrderList.get(posPlus);
        int qty1 = datum1.getQty();
        if(Integer.parseInt(datum1.getSProductPoints())<=qty1){
            Util.showToast("Quantity limit exceed",getActivity());
        }else {
            datum1.setQty(qty1 + 1);
            IPOSApplication.mOrderList.set(posPlus, datum1);
            mNewOrderListAdapter.notifyItemChanged(posPlus);
            setUpdateValues(IPOSApplication.mOrderList);
        }
    }

    private void setOnClickMinus(View view) {
        Util.hideSoftKeyboard(getActivity());
        Util.animateView(view);
        int posMinus = (int) view.getTag();
        OrderList.Datum datum = IPOSApplication.mOrderList.get(posMinus);
        int qty = datum.getQty();
        if(qty==1){
            Util.showToast("Cannot purchase with 0 quantity",getActivity());
            return;
        }else {
            datum.setQty(qty - 1);
            IPOSApplication.mOrderList.set(posMinus, datum);
            mNewOrderListAdapter.notifyItemChanged(posMinus);
            setUpdateValues(IPOSApplication.mOrderList);
        }
    }

    int mAllChecked = 0;
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int id = compoundButton.getId();
        switch (id) {
//                case R.id.chkOTCDiscount:
//                    if (compoundButton.isPressed()){
//                        final int posItem = (int) compoundButton.getTag();
//                        mRecyclerView.post(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                ProductList.Datum datum = IPOSApplication.mOrderList.get(posItem);
//                                if (!datum.isDiscSelected())
//                                    datum.setDiscSelected(true);
//                                else {
//                                    datum.setDiscSelected(false);
//                                }
//                                IPOSApplication.mProductList.set(posItem, datum);
//                                mNewOrderListAdapter.notifyItemChanged(posItem);
//                                setUpdateValues(IPOSApplication.mProductList);
//                            }
//                        });
//                    }
//                    break;
//                case R.id.chkItem:
//                    if (compoundButton.isPressed()){
//                        final int posItem = (int) compoundButton.getTag();
//                        mRecyclerView.post(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                ProductList.Datum datum = IPOSApplication.mProductList.get(posItem);
//                                if (!datum.isOTCselected())
//                                    datum.setOTCselected(true);
//                                else {
//                                    datum.setOTCselected(false);
//                                    chkOTC.setChecked(false);
//                                }
//                                IPOSApplication.mProductList.set(posItem, datum);
//                                mRetailSalesAdapter.notifyItemChanged(posItem);
//
//                            }
//                        });
//                        for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
//                            ProductList.Datum datum1 = IPOSApplication.mProductList.get(i);
//                            if(datum1.isOTCselected()){
//                                mAllChecked++;
//                            }
//                        }
//                        if(IPOSApplication.mProductList.size()-1==mAllChecked){
//                            chkOTC.setChecked(true);
//                        }else
//                            chkOTC.setChecked(false);
//                    }
//                    break;
//                case R.id.chkOTC:
//                    if (compoundButton.isPressed()){
//                        mRecyclerView.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if(chkOTC.isChecked()) {
//                                    for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
//                                        ProductList.Datum datum = IPOSApplication.mProductList.get(i);
//                                        datum.setOTCselected(true);
//                                        IPOSApplication.mProductList.set(i, datum);
//                                    }
//                                }else {
//                                    for (int i = 0; i < IPOSApplication.mProductList.size(); i++) {
//                                        ProductList.Datum datum = IPOSApplication.mProductList.get(i);
//                                        datum.setOTCselected(false);
//                                        IPOSApplication.mProductList.set(i, datum);
//                                    }
//                                }
//                                mRetailSalesAdapter.notifyDataSetChanged();
//                            }
//                        });
//                    }
//                    break;
//                case R.id.chkDiscount:
//                    if (compoundButton.isPressed()) {
//                        posDeleteItem = (int) compoundButton.getTag();
//                        mRecyclerView.post(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                ProductList.Datum datum = IPOSApplication.mProductList.get(posDeleteItem);
//                                if(datum.isDiscItemSelected()) {
//                                    FragmentManager fragmentManager = getChildFragmentManager();
//                                    mDiscountDeleteFragment = DiscountDeleteFragment.newInstance();
//                                    mDiscountDeleteFragment.setDialogInfo(NewOrderFragment.this,datum);
//                                    mDiscountDeleteFragment.show(fragmentManager, "Delete Discount");
//                                }else {
//                                    addDeleteDiscount();
//                                }
//                            }
//                        });
//                    }
//                    break;
        }
    }
    int posDeleteItem=0;
    DiscountDeleteFragment mDiscountDeleteFragment;
    private void setDeleteDiscount() {
        OrderList.Datum datum = IPOSApplication.mOrderList.get(posDeleteItem);
        if (!datum.isDiscItemSelected()) {
            datum.setDiscItemSelected(true);
        }
        else {
            datum.setDiscItemSelected(false);
        }
        IPOSApplication.mOrderList.set(posDeleteItem, datum);
        mNewOrderListAdapter.notifyItemChanged(posDeleteItem);
        setUpdateValues(IPOSApplication.mOrderList);
        mDiscountDeleteFragment.dismiss();
    }

    private void addDeleteDiscount() {
        OrderList.Datum datum = IPOSApplication.mOrderList.get(posDeleteItem);
        if (!datum.isDiscItemSelected()) {
            datum.setDiscItemSelected(true);
        }

        IPOSApplication.mOrderList.set(posDeleteItem, datum);
        mNewOrderListAdapter.notifyItemChanged(posDeleteItem);
        setUpdateValues(IPOSApplication.mOrderList);
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
        OrderList.Datum datum1 = IPOSApplication.mOrderList.get(position);

        if ( value<=Integer.parseInt(datum1.getSProductPoints())) {

            datum1.setQty(value);
            IPOSApplication.mOrderList.set(position, datum1);
            mNewOrderListAdapter.notifyItemChanged(position);
            setUpdateValues(IPOSApplication.mOrderList);
        }else {
            datum1.setQty(Integer.parseInt(datum1.getSProductPoints()));
            IPOSApplication.mOrderList.set(position, datum1);
            mNewOrderListAdapter.notifyItemChanged(position);
            setUpdateValues(IPOSApplication.mOrderList);
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
    public void onDialogPositiveClick(DialogFragment dialog, int mCallType) {
        if(mCallType==Constants.APP_DIALOG_OTC) {
            dialog.dismiss();
        }else if(mCallType==Constants.APP_DIALOG_Cart){

        }
    }

    @Override
    public void onDialogNegetiveClick(DialogFragment dialog, int mCallType) {
        if(mCallType==Constants.APP_DIALOG_Cart){
            IPOSApplication.mOrderList.clear();
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public void setProductOnListener(String mDatum) {
        ArrayList<OrderList.Datum> arrData= new ArrayList<>();
        json = SharedPrefUtil.getString(Constants.Order_List,"",getActivity());
        arrData = Util.getCustomGson().fromJson(json, new TypeToken<ArrayList<OrderList.Datum>>(){}.getType());
        IPOSApplication.mOrderList.add(arrData.get(0));
        mNewOrderListAdapter.notifyDataSetChanged();

    }

}
