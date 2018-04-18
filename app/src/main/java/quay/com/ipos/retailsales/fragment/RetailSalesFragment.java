package quay.com.ipos.retailsales.fragment;


import android.support.v4.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.retailsales.activity.FullScannerActivity;
import quay.com.ipos.retailsales.adapter.RetailSalesAdapter;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.utility.AppLog;

/**
 * Created by aditi.bhuranda on 16-04-2018.
 */

public class RetailSalesFragment extends Fragment implements View.OnClickListener {
    private TextView tvUserAdd,tvPin,tvRedeem,tvRight,tvRight1,tvMoreDetails,tvItemNo,tvItemQty,tvItemPrice,
            tvTotalGST,tvItemGSTPrice,tvTotalDiscountQty,tvTotalDiscountPrice,tvCGST,tvCGSTPrice,tvSGST,tvSGSTPrice,
            tvLessDetails,tvRoundingOffPrice,tvTotalDiscount,tvPay,tvOTCDiscount,tvClearOTC,tvClearOTC1;
    private ToggleButton tbPerc,tbRs;
    private CheckBox chkOTC;
    LinearLayout layoutOtcDiscount;
    private LinearLayout llTotalDiscountDetail,ll_item_pay,llOTCSelect,llTotalGST;
    private ImageView imvDicount,imvGlobe,imvQRCode;
    private ToggleButton chkBarCode;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private String TAG=RetailSalesFragment.class.getSimpleName();
    private RetailSalesAdapter mRetailSalesAdapter;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private int mSelectedpos=0;
    private ArrayList<String> mList= new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.retail_dashboard, container, false);
        initializeComponent(rootView);
        return rootView;
    }

    private void initializeComponent(View rootView) {
        layoutOtcDiscount = rootView.findViewById(R.id.layout_otc_discount);
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
        tvItemPrice = rootView.findViewById(R.id.tvItemPrice);
        tvTotalGST = rootView.findViewById(R.id.tvTotalGST);
        tvItemGSTPrice = rootView.findViewById(R.id.tvItemGSTPrice);
        llTotalDiscountDetail = rootView.findViewById(R.id.llTotalDiscountDetail);
        tvTotalDiscountQty = rootView.findViewById(R.id.tvTotalDiscountQty);
        tvTotalDiscountPrice = rootView.findViewById(R.id.tvTotalDiscountPrice);
        tvCGST = rootView.findViewById(R.id.tvCGST);
        tvCGSTPrice = rootView.findViewById(R.id.tvCGSTPrice);
        tvSGST = rootView.findViewById(R.id.tvSGST);
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
        swipeRefreshLayout =  rootView.findViewById(R.id.swipeRefreshLayout);

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
        setDefaultValues();
    }

    private void setDefaultValues() {

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

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

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
                    AppLog.e(TAG, "item:: " + arg0.getChildPosition(child));
                    // AppUtil.LogMsg(TAG,
                    // AppUtil.getCustomGson().toJson(moduleList.get(arg0.getChildPosition(child))));
//                    openDetailActivity(arg0.getChildPosition(child));
                    return true;

                }

                return false;
            }

        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                mOffset = 0;
//                nextOffset = 6;
//                callBelongList();
            }
        });
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

    private void setAdapter() {
        mList.add("asd");
        mList.add("wer");
        mRetailSalesAdapter = new RetailSalesAdapter(getActivity(), this, mRecyclerView, mList);
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
                ll_item_pay.setVisibility(View.GONE);
                llOTCSelect.setVisibility(View.VISIBLE);
                break;
            case R.id.tvOTCDiscount:
                AppLog.e(TAG,"click");
                layoutOtcDiscount.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);
                break;
            case R.id.tvClearOTC:
                layoutOtcDiscount.setVisibility(View.GONE);
                ll_item_pay.setVisibility(View.VISIBLE);
                llOTCSelect.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.tvClearOTC1:
                layoutOtcDiscount.setVisibility(View.GONE);
                ll_item_pay.setVisibility(View.VISIBLE);
                llOTCSelect.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                break;
        }
    }
}
