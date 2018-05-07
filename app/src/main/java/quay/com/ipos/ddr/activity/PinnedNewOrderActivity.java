package quay.com.ipos.ddr.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.ddr.adapter.NewOrderPinnedAdapter;
import quay.com.ipos.modal.NewOrderPinnedResults;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.realmbean.RealmPinnedResults;
import quay.com.ipos.retailsales.activity.AddProductActivity;
import quay.com.ipos.retailsales.activity.PinnedRetailActivity;
import quay.com.ipos.retailsales.adapter.AddProductAdapter;
import quay.com.ipos.retailsales.adapter.PinnedAdapter;
import quay.com.ipos.ui.FontManager;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 04-05-2018.
 */

public class PinnedNewOrderActivity extends BaseActivity {
        private static final String TAG = PinnedNewOrderActivity.class.getSimpleName();
        //    ArrayList<RealmPinnedResults.Info> arrPinned= new ArrayList<>();
        private EditText searchView;
        private RecyclerView mRecyclerView;
        private FloatingActionButton fab;
        private LinearLayoutManager mLayoutManager;
        private LinearLayout llSearch;
        private ProductList mProductListResult;
        private AddProductAdapter mAddProductAdapter;
        private TextView tvClear;
        private NewOrderPinnedAdapter mNewOrderPinnedAdapter;
        private int mSelectedpos;
        ArrayList<NewOrderPinnedResults.Info> mOrderInfoArrayList = new ArrayList<>();
        String json ;
        @Override
        public void onCreate( Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.act_customer_list);
            setHeader();
            initializeComponent();
            setAdapter();
//        getProduct();
        }

        private void setAdapter() {
//       arrPinned= new RealmController().getAllPinnedData().getInfo();

            mOrderInfoArrayList.clear();
            json = SharedPrefUtil.getString(Constants.mOrderInfoArrayList,"",this);
            mOrderInfoArrayList = Util.getCustomGson().fromJson(json, new TypeToken<ArrayList<NewOrderPinnedResults.Info>>(){}.getType());
//        if(Util.getCachedPinned()!=null) {
//            NewOrderPinnedResults mNewOrderPinnedResults = Util.getCachedPinnedData();
//            mOrderInfoArrayList.addAll(Util.getCachedPinnedData());
//        AppLog.e(PinnedRetailActivity.class.getSimpleName(),"mOrderInfoArrayList: "+ Util.getCustomGson().toJson(Util.getCachedPinnedData()) );
            AppLog.e(quay.com.ipos.retailsales.activity.PinnedRetailActivity.class.getSimpleName(), Util.getCustomGson().toJson(mOrderInfoArrayList));
//        }
            mNewOrderPinnedAdapter = new NewOrderPinnedAdapter(this,mRecyclerView,mOrderInfoArrayList);
            mRecyclerView.setAdapter(mNewOrderPinnedAdapter);
        }


        private void initializeComponent() {
            searchView = findViewById(R.id.searchView);
            mRecyclerView = findViewById(R.id.recycler_view);
            tvClear = findViewById(R.id.tvClear);
            fab = findViewById(R.id.fab);
            fab.setVisibility(View.GONE);
            llSearch = findViewById(R.id.llSearch);
            llSearch.setVisibility(View.GONE);
            Typeface iconFont = FontManager.getTypeface(this, FontManager.FONTAWESOME);
            FontManager.markAsIconContainer(tvClear, iconFont);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.addItemDecoration(
                    new ItemDecorationAlbumColumns(getResources().getDimensionPixelSize(R.dimen.dim_5),
                            getResources().getInteger(R.integer.photo_list_preview_columns)));

            final GestureDetector mGestureDetector = new GestureDetector(this,
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
                        selectPinned(arg0.getChildPosition(child));
                        return true;

                    }

                    return false;
                }

            });
        }

        private void selectPinned(int childPosition) {
            IPOSApplication.mOrderList.clear();

            IPOSApplication.mOrderList.addAll(mOrderInfoArrayList.get(childPosition).getData());
            Intent mIntent = new Intent();
            mIntent.putExtra("pinned_order_position",childPosition);
            setResult(1,mIntent);
            finish();

        }

        public void setHeader() {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            // toolbar.setNavigationIcon(getResources().getDrawable(R.mipmap.back));
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            if (getSupportActionBar() != null) {
                getSupportActionBar().setHomeButtonEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowCustomEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }


}
