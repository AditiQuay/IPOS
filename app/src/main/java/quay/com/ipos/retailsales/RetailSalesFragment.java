package quay.com.ipos.retailsales;


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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.utility.AppLog;

/**
 * Created by aditi.bhuranda on 16-04-2018.
 */

public class RetailSalesFragment extends Fragment implements View.OnClickListener {
    private TextView tvUserAdd,tvPin,tvRedeem;
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
    private ArrayList<String> mList= new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.retail_dashboard, container, false);
        initializeComponent(rootView);
        return rootView;
    }

    private void initializeComponent(View rootView) {
        tvUserAdd = rootView.findViewById(R.id.tvUserAdd);
        tvPin = rootView.findViewById(R.id.tvPin);
        tvRedeem = rootView.findViewById(R.id.tvRedeem);
        imvGlobe = rootView.findViewById(R.id.imvGlobe);
        imvDicount = rootView.findViewById(R.id.imvDicount);
        imvQRCode = rootView.findViewById(R.id.imvQRCode);
        chkBarCode = rootView.findViewById(R.id.chkBarCode);

        Typeface iconFont = FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(tvUserAdd, iconFont);
        FontManager.markAsIconContainer(tvPin, iconFont);
        FontManager.markAsIconContainer(tvRedeem, iconFont);
        imvQRCode.setOnClickListener(this);
        chkBarCode.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (chkBarCode.isChecked()) {
                    imvQRCode.setVisibility(View.VISIBLE);
                    Toast.makeText(getActivity(), "ON", Toast.LENGTH_SHORT).show();
                } else {
                    imvQRCode.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycleView);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(
                new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dim_5),
                        getResources().getInteger(R.integer.photo_list_preview_columns)));

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
        setAdapter();

    }

    private void setAdapter() {
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
               // ((MainActivity) getActivity()).launchActivity(FullScannerActivity.class);
                break;
        }
    }
}
