package quay.com.ipos.ddr.fragment;

import android.Manifest;
import android.animation.Animator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
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
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.ddr.activity.AddNewOrderActivity;
import quay.com.ipos.ddr.activity.NewOrderDetailsActivity;
import quay.com.ipos.ddr.activity.PinnedOrderActivity;
import quay.com.ipos.ddr.adapter.NewOrderListAdapter;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.ScannerProductListener;
import quay.com.ipos.modal.NewOrderPinnedResults;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.retailsales.fragment.FullScannerFragment;
import quay.com.ipos.ui.DiscountDeleteFragment;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.MessageDialogFragment;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 03-05-2018.
 */

public class NewOrderFragment extends BaseFragment implements View.OnClickListener , CompoundButton.OnCheckedChangeListener ,AdapterListener,MessageDialogFragment.MessageDialogListener,ScannerProductListener {
    private TextView tvMoreDetails,tvItemNo,tvItemQty,tvTotalItemPrice,
            tvTotalGST,tvTotalItemGSTPrice,tvTotalDiscountDetail,tvTotalDiscountPrice,tvCGSTPrice,tvSGSTPrice,
            tvLessDetails,tvRoundingOffPrice,tvPay,tvPinCount;

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
    ArrayList<NewOrderPinnedResults.Info> mOrderInfoArrayList = new ArrayList<>();
    private String json;
    private LinearLayout llBelowPaymentDetail;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.new_order_dashboard_dummy, container, false);
        initializeComponent(rootView);
        myDialog = new Dialog(getActivity());
        setHasOptionsMenu(true);
        Util.hideSoftKeyboard(getActivity());
        return rootView;
    }

    RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener(){
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.TOUCH_SLOP_PAGING || newState != RecyclerView.SCROLL_STATE_IDLE) {
                hideViews();
            } else {
                llBelowPaymentDetail.animate().alpha(1.0f).translationY(0).setInterpolator(new DecelerateInterpolator(1)).start();
                showViews();
            }
        }
    };



    private void initializeComponent(View rootView) {
        flScanner = rootView.findViewById(R.id.flScanner);
        tvPinCount =  rootView.findViewById(R.id.tvPinCount);
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
        mRecyclerView.addOnScrollListener(listener);
        llBelowPaymentDetail=rootView.findViewById(R.id.llBelowPaymentDetail);

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
        if (SharedPrefUtil.getString(Constants.mOrderInfoArrayList, "", getActivity()) != null) {
            String json2 = SharedPrefUtil.getString(Constants.mOrderInfoArrayList, "", getActivity());
            if (!json2.equalsIgnoreCase(""))
                mOrderInfoArrayList = Util.getCustomGson().fromJson(json2, new TypeToken<ArrayList<NewOrderPinnedResults.Info>>() {}.getType());
            if(mOrderInfoArrayList.size()>0){
                tvPinCount.setText(""+mOrderInfoArrayList.size());
                tvPinCount.setVisibility(View.VISIBLE);
            }else {
                tvPinCount.setVisibility(View.GONE);
            }
        }else {
            tvPinCount.setVisibility(View.GONE);
        }
//        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            flScanner.setVisibility(View.GONE);
//            chkBarCode.setChecked(false);
//            boolean request = ((MainActivity) getActivity()).launchActivity(false);
//            if(request && ((MainActivity) getActivity()).CameraPermission )
//            {
//                setTextDefault();
//            }
//        }else {
        flScanner.setVisibility(View.GONE);
        chkBarCode.setChecked(false);
        closeFragment();
//            displayFragment();
//        }
    }



    private void setListener() {
        tvMoreDetails.setOnClickListener(this);
        tvLessDetails.setOnClickListener(this);
        imvPin.setOnClickListener(this);
        tvPay.setOnClickListener(this);
        imvRight.setOnClickListener(this);
        // Set the click listener for the button.
        chkBarCode.setOnClickListener(new View.OnClickListener() {
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

    boolean isBack=false;

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).setToolbarTitle(getString(R.string.new_orders));
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
                    if(IPOSApplication.mOrderList.size()>=1) {
                        Util.showMessageDialog(NewOrderFragment.this, "Do you want to save the Cart?", "YES", "NO", Constants.APP_DIALOG_Cart, "", getActivity().getSupportFragmentManager());

                        isBack = true;
                    }

                    else
                        isBack =  false;

                }
                return isBack;
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
        Intent mIntent = new Intent(getActivity(), AddNewOrderActivity.class);
        startActivityForResult(mIntent,3);
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

        if(requestCode==3){
            if(resultCode == 3){
                mNewOrderListAdapter.notifyDataSetChanged();
                getProduct();
            }
        }else if(requestCode==5){
            if(resultCode == 5){
                childPosition = data.getIntExtra("pinned_order_position",0);
                getProduct();
            }
        }else if(requestCode==6){
            if(resultCode == 6){
//                childPosition = data.getIntExtra("pinned_order_position",0);
//                getProduct();
                IPOSApplication.mOrderList.clear();
                mNewOrderListAdapter.notifyDataSetChanged();
                setUpdateValues(IPOSApplication.mOrderList);
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
//            IPOSApplication.mOrderList.clear();
//            String json = Util.getAssetJsonResponse(getActivity(), "product_list.json");
//            mOrderListResult = Util.getCustomGson().fromJson(json,OrderList.class);
            AppLog.e(NewOrderFragment.class.getSimpleName(),"Get Order: "+Util.getCustomGson().toJson(IPOSApplication.mOrderList));
//            IPOSApplication.mOrderList.addAll(mOrderListResult.getData());
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

    OrderList mOrderList = new OrderList();
    private void setUpdateValues(ArrayList<OrderList.Datum> mList) {

        AppLog.e(TAG, "IPOSApplication.mProductList:Frag: "+ Util.getCustomGson().toJson(IPOSApplication.mOrderList));
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
            double discount=0,discountPrice=0.0, totalGst=0.0, cgst = 0.0, sgst = 0.0;
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
                    otcDiscountPerc =0;
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

            // Total Qty
            tvItemQty.setText(qty+" Qty");
            mOrderList.setTotalQty(qty);

            // Total price befor discount & gst
            tvTotalItemPrice.setText(getActivity().getResources().getString(R.string.Rs) +" "+sum);
            mOrderList.setTotalPrice(sum);

            // discountPrice
            discountPrice = discount+otcDiscountPerc;
            tvTotalDiscountPrice.setText("-"+getActivity().getResources().getString(R.string.Rs) +" "+(discountPrice));
            mOrderList.setDiscountPrice(discountPrice);

//            discountItem
            tvTotalDiscountDetail.setText("(Item "+ discountItem+")");
            mOrderList.setDiscountItem(discountItem);

//            totalGst
            AppLog.e(TAG,"totalGst: "+totalGst);
            tvTotalItemGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " + totalGst);
            mOrderList.setTotalGst(totalGst);

//            sgst
            tvSGSTPrice.setText("+"+getActivity().getResources().getString(R.string.Rs) + " " +sgst);
            mOrderList.setSgst(sgst);

//            cgst
            tvCGSTPrice.setText("+"+getActivity().getResources().getString(R.string.Rs) + " " +cgst);
            mOrderList.setCgst(cgst);

            totalAfterGSt = (sum-discount)+(sgst+cgst)-(otcDiscountPerc);
//            double floorValue = Math.round(totalAfterGSt);


            double roundOff = totalAfterGSt - Math.floor( totalAfterGSt );
            double round_off = (Util.round(roundOff,1));
            tvRoundingOffPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " + round_off);
            mOrderList.setRound_off(round_off);
            totalAfterGSt = totalAfterGSt +  (Util.round(roundOff,1));
            totalAmount=Math.round(totalAfterGSt);
            tvPay.setText(getActivity().getResources().getString(R.string.Rs) + " " +  totalAmount);
            mOrderList.setTotalGst(totalAmount);
            AppLog.e(TAG,"updated: " + Util.getCustomGson().toJson(IPOSApplication.mOrderList));
            mOrderList.setData(IPOSApplication.mOrderList);
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
                cachedPinned(true);
                break;


            case R.id.imvClear:
                final int posClear = (int) view.getTag();
                (
                        new AlertDialog.Builder(getActivity())).setTitle("Confirm action")
                        .setMessage("Do you want to Delete this Item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                IPOSApplication.mOrderList.remove(posClear);
                                mNewOrderListAdapter.notifyItemRemoved(posClear);
                                mNewOrderListAdapter.notifyItemRangeChanged(posClear,IPOSApplication.mProductList.size());

                                setUpdateValues(IPOSApplication.mOrderList);
                            }
                        }).setNegativeButton("No", null).show();

                break;
            case R.id.tvPay:
                if (totalAmount>0) {
                    Intent i = new Intent(getActivity(), NewOrderDetailsActivity.class);
                    i.putExtra(Constants.TOTAL_AMOUNT,totalAmount+"");
                    i.putExtra(Constants.Order_List,Util.getCustomGson().toJson(mOrderList));
                    getActivity().startActivityForResult(i,6);
                }else {
                    Util.showToast("Please add atleast one item to proceed.");
                }
                break;
            case R.id.imvRight:
                if (totalAmount>0) {
                    Intent i = new Intent(getActivity(), NewOrderDetailsActivity.class);
                    i.putExtra(Constants.TOTAL_AMOUNT,totalAmount+"");
                    i.putExtra(Constants.Order_List,Util.getCustomGson().toJson(mOrderList));
                    startActivityForResult(i,6);
                }else {
                    Util.showToast("Please add atleast one item to proceed.");
                }
                break;
            case R.id.btnNo:
                mDiscountDeleteFragment.dismiss();
                addDeleteDiscount();
                break;

            case R.id.btnYes:
                setDeleteDiscount();
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
    public void onDialogPositiveClick(DialogFragment dialog, int mCallType) {
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
    public void onDialogNegetiveClick(DialogFragment dialog, int mCallType) {
        if(mCallType==Constants.APP_DIALOG_Cart){
            if(IPOSApplication.mOrderList.size()>0) {
                IPOSApplication.mOrderList.clear();
                getFragmentManager().popBackStack();
            }
            dialog.dismiss();

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


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int id = compoundButton.getId();
        switch (id) {

            case R.id.chkDiscount:
                if (compoundButton.isPressed()) {
                    posDeleteItem = (int) compoundButton.getTag();
                    mRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {

                            OrderList.Datum datum = IPOSApplication.mOrderList.get(posDeleteItem);
                            if(datum.isDiscItemSelected()) {
                                FragmentManager fragmentManager = getChildFragmentManager();
                                mDiscountDeleteFragment = DiscountDeleteFragment.newInstance();
                                mDiscountDeleteFragment.setDialogInfoOrder(NewOrderFragment.this,datum);
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

}
