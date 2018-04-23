package quay.com.ipos.retailsales.fragment;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.retailsales.activity.AddProductActivity;
import quay.com.ipos.retailsales.activity.FullScannerActivity;
import quay.com.ipos.retailsales.adapter.RetailSalesAdapter;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 16-04-2018.
 */

public class RetailSalesFragment extends Fragment implements View.OnClickListener , CompoundButton.OnCheckedChangeListener {
    private TextView tvUserAdd,tvPin,tvRedeem,tvRight,tvRight1,tvMoreDetails,tvItemNo,tvItemQty,tvTotalItemPrice,
            tvTotalGST,tvTotalItemGSTPrice,tvTotalDiscountDetail,tvTotalDiscountPrice,tvCGSTPrice,tvSGSTPrice,
            tvLessDetails,tvRoundingOffPrice,tvTotalDiscount,tvPay,tvOTCDiscount,tvClearOTC,tvClearOTC1;
    private ToggleButton tbPerc,tbRs;
    private EditText etDiscountAmt ;
    private CheckBox chkOTC;
    LinearLayout layoutOtcDiscount;
    private LinearLayout llTotalDiscountDetail,ll_item_pay,llOTCSelect,llTotalGST;
    private ImageView imvDicount,imvGlobe,imvQRCode;
    private ToggleButton chkBarCode;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private String TAG=RetailSalesFragment.class.getSimpleName();
    private RetailSalesAdapter mRetailSalesAdapter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private ProductList mProductListResult;
    int qty = 0;
    double totalPrice=0.0;
    double sum=0;
    double discount=0, totalGst=0.0, cgst = 0.0, sgst = 0.0;
    int discountItem=0;
    private int mSelectedpos=0;
    private double totalAfterGSt=0.0;
//    private ArrayList<ProductList.Datum> mList= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.retail_dashboard, container, false);
        initializeComponent(rootView);
        setHasOptionsMenu(true);
        return rootView;
    }

    private void initializeComponent(View rootView) {
        layoutOtcDiscount = rootView.findViewById(R.id.layout_otc_discount);
        etDiscountAmt = rootView.findViewById(R.id.etDiscountAmt);
        tvUserAdd = rootView.findViewById(R.id.tvUserAdd);
        tbRs = rootView.findViewById(R.id.tbRs);
        tbPerc = rootView.findViewById(R.id.tbPerc);
        tvPin = rootView.findViewById(R.id.tvPin);
        tvRedeem = rootView.findViewById(R.id.tvRedeem);
        imvGlobe = rootView.findViewById(R.id.imvGlobe);
        imvDicount = rootView.findViewById(R.id.imvDicount);
        imvQRCode = rootView.findViewById(R.id.imvQRCode);
        chkBarCode = rootView.findViewById(R.id.chkBarCode);
        tvRight = rootView.findViewById(R.id.tvRight);
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
        tvClearOTC = rootView.findViewById(R.id.tvClearOTC);
        tvClearOTC1 = rootView.findViewById(R.id.tvClearOTC1);
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
        setViewResponse();
    }



    private void setViewResponse() {
    }

    private void setFontText() {
        Typeface iconFont = FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(tvUserAdd, iconFont);
        FontManager.markAsIconContainer(tvPin, iconFont);
        FontManager.markAsIconContainer(tvRedeem, iconFont);
        FontManager.markAsIconContainer(tvRight, iconFont);
        FontManager.markAsIconContainer(tvRight1, iconFont);
        FontManager.markAsIconContainer(tvClearOTC, iconFont);
        FontManager.markAsIconContainer(tvClearOTC1, iconFont);
    }

    private void setListener() {
        imvQRCode.setOnClickListener(this);
        tvMoreDetails.setOnClickListener(this);
        tvLessDetails.setOnClickListener(this);
        imvDicount.setOnClickListener(this);
        tvOTCDiscount.setOnClickListener(this);
        tvClearOTC.setOnClickListener(this);
        tvClearOTC1.setOnClickListener(this);
        chkBarCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (chkBarCode.isChecked()) {
                    imvQRCode.setVisibility(View.VISIBLE);
//                    Toast.makeText(getActivity(), "ON", Toast.LENGTH_SHORT).show();
                } else {
                    imvQRCode.setVisibility(View.GONE);
//                    Toast.makeText(getActivity(), "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });
        final GestureDetector mGestureDetector = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                });

//        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
//                    // AppUtil.LogMsg(TAG,
//                    // AppUtil.getCustomGson().toJson(moduleList.get(arg0.getChildPosition(child))));
////                    openDetailActivity(arg0.getChildPosition(child));
//                    return true;
//
//                }
//
//                return false;
//            }
//
//        });

        tbPerc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tbRs.setChecked(false);
                tbPerc.setChecked(true);
                tbRs.setTextColor(getActivity().getResources().getColor(R.color.accent_color));
                tbPerc.setTextColor(getActivity().getResources().getColor(R.color.white));
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
        startActivity(mIntent);
    }

    private void setAdapter() {

        mRetailSalesAdapter = new RetailSalesAdapter(getActivity(), this, mRecyclerView, IPOSApplication.mProductList,this);
        mRecyclerView.setAdapter(mRetailSalesAdapter);
        getProduct();

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

    private void getProduct() {
        try {
            IPOSApplication.mProductList.clear();
            String json = Util.getAssetJsonResponse(getActivity(), "product_list.json");
            mProductListResult = Util.getCustomGson().fromJson(json,ProductList.class);
            AppLog.e(RetailSalesAdapter.class.getSimpleName(),Util.getCustomGson().toJson(mProductListResult));
            IPOSApplication.mProductList.addAll(mProductListResult.getData());
            setDefaultValues();

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
            datum.setQty(1);
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
            tvItemNo.setText("Item " + mList.size());
        }else {
            tvItemNo.setText("Items " + mList.size());
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


            for(int i = 0 ; i < mList.size(); i++ ) {
                ProductList.Datum datum = mList.get(i);
                qty += mList.get(i).getQty();
                datum.setTotalQty(qty);
                totalPrice=mList.get(i).getQty()*Double.parseDouble(mList.get(i).getSProductPrice());
                sum=totalPrice+sum;
                datum.setTotalPrice(sum);
                if(mList.get(i).getIsDiscount()) {
                    discount = Double.parseDouble(mList.get(i).getSDiscountPrice()) * sum / 100;
                    datum.setDiscount(discount);
                    discountItem++;
                }

//                totalPrice += mList.get(i).getTotalPrice();
                IPOSApplication.mProductList.set(i,datum);
            }
            tvItemQty.setText(qty+" Qty");
            tvTotalItemPrice.setText(getActivity().getResources().getString(R.string.Rs) +" "+sum);
            tvTotalDiscountPrice.setText(getActivity().getResources().getString(R.string.Rs) +" "+discount);
            tvTotalDiscount.setText(getActivity().getResources().getString(R.string.Rs) +" "+discount);
            tvTotalDiscountDetail.setText("(Item "+ discountItem+")");
            totalGst = mProductListResult.getGSTPerc()*sum/100;
            tvTotalItemGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " + totalGst);
            sgst = mProductListResult.getSGST()*sum/100;
            tvSGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " +sgst);
            cgst = mProductListResult.getCGST()*sum/100;
            tvCGSTPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " +cgst);
            totalAfterGSt = sum+discount+totalGst+sgst+cgst;
            double floorValue = Math.round(totalAfterGSt);
            double roundOff = Util.numberFormat(floorValue-totalAfterGSt);
            tvRoundingOffPrice.setText(getActivity().getResources().getString(R.string.Rs) + " " + (roundOff));
            totalAfterGSt = Util.round(totalAfterGSt,1) + roundOff;
            tvPay.setText(getActivity().getResources().getString(R.string.Rs) + " " + totalAfterGSt);
            AppLog.e(RetailSalesAdapter.class.getSimpleName(),"updated: " + Util.getCustomGson().toJson(IPOSApplication.mProductList));
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.imvQRCode:
                ((MainActivity) getActivity()).launchActivity(FullScannerActivity.class);
                break;
            case R.id.tvMoreDetails:
                llTotalDiscountDetail.setVisibility(View.VISIBLE);
                llTotalGST.setVisibility(View.GONE);
                break;
            case R.id.tvLessDetails:
                llTotalDiscountDetail.setVisibility(View.GONE);
                llTotalGST.setVisibility(View.VISIBLE);
                break;
            case R.id.imvDicount:
                setonImvDiscount();
                break;
            case R.id.tvOTCDiscount:
                AppLog.e(TAG,"click");
                layoutOtcDiscount.setVisibility(View.VISIBLE);
                break;
            case R.id.tvClearOTC:
                layoutOtcDiscount.setVisibility(View.GONE);
                ll_item_pay.setVisibility(View.VISIBLE);
                llOTCSelect.setVisibility(View.GONE);
                break;
            case R.id.tvClearOTC1:
                layoutOtcDiscount.setVisibility(View.GONE);
                ll_item_pay.setVisibility(View.VISIBLE);
                llOTCSelect.setVisibility(View.GONE);
                break;
            case R.id.imvMinus:
                setOnClickMinus(view);
                break;

            case R.id.imvPlus:
                setOnClickPlus(view);
                break;
        }
    }

    private void setOnClickPlus(View view) {
        Util.animateView(view);
        int posPlus = (int) view.getTag();
        ProductList.Datum datum1 = IPOSApplication.mProductList.get(posPlus);
        int qty1 = datum1.getQty();
        datum1.setQty(qty1+1);
        IPOSApplication.mProductList.set(posPlus,datum1);
        mRetailSalesAdapter.notifyDataSetChanged();
        setUpdateValues(IPOSApplication.mProductList);
    }

    private void setOnClickMinus(View view) {
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
            mRetailSalesAdapter.notifyDataSetChanged();
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
                IPOSApplication.mProductList.set(i, datum);

            }
        }
        mRetailSalesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int id = compoundButton.getId();
        switch (id){
            case R.id.chkItem:

                final int posItem = (int) compoundButton.getTag();
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {

                        ProductList.Datum datum = IPOSApplication.mProductList.get(posItem);
                        if(!datum.isOTCselected())
                            datum.setOTCselected(true);
                        else
                            datum.setOTCselected(false);
                        IPOSApplication.mProductList.set(posItem, datum);
                        mRetailSalesAdapter.notifyItemChanged(posItem);
                    }
                });
                break;
        }
    }
}
