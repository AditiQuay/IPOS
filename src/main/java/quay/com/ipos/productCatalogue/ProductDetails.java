package quay.com.ipos.productCatalogue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.adapter.ImageSliderViewPagerAdapter;
import quay.com.ipos.base.RunTimePermissionActivity;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.ProductDetailsExpandableListAdapter;
import quay.com.ipos.productCatalogue.productModal.CatalogueServerModel;
import quay.com.ipos.productCatalogue.productModal.ProductDetailModel;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.SharedPrefUtil;

/**
 * Created by niraj.kumar on 4/18/2018.
 */

public class ProductDetails extends RunTimePermissionActivity implements InitInterface, ViewPager.OnPageChangeListener, ServiceTask.ServiceResultListener {
    private static final String TAG = ProductDetails.class.getSimpleName();
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private Toolbar toolbar;
    private TextView textViewProductName;
    private String productName, productId;
    private ExpandableListView expandableListViewProduct;
    private ArrayList<ProductDetailModel> productDetailsArrayList = new ArrayList<>();
    ArrayList<ProductDetailModel> expandableListTitle;
    //    HashMap<String, List<String>> expandableListDetail;
    private ProductDetailsExpandableListAdapter productDetailsExpandableListAdapter;
    private Context mContext;
    private int lastExpandedGroup;
    LinkedHashMap<ProductDetailModel, List<ProductDetailModel.ProductChild>> expandableListDetail = new LinkedHashMap<ProductDetailModel, List<ProductDetailModel.ProductChild>>();
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);
        mContext = ProductDetails.this;
        Intent i = getIntent();
        productName = i.getStringExtra("ProductName");
        productId = i.getStringExtra("ProductId");

        getProductList();
        findViewById();
        applyInitValues();
        applyTypeFace();
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    private void getProductList() {
        showProgress("Loading please wait...");

        int storeId = SharedPrefUtil.getStoreId(Constants.STORE_ID.trim(), 0, mContext);
        AppLog.e(TAG, "StoreId" + storeId);

        ProductDetailModel productDetailModel = new ProductDetailModel();
        productDetailModel.setCompanyName("Quay");
        productDetailModel.setProductId(productId);
        productDetailModel.setStoreId(String.valueOf(storeId));

        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL.trim());
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_PRODUCT_DESCRIPTION.trim());
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(productDetailModel);
        mTask.setListener(this);
        mTask.setResultType(CatalogueServerModel.class);
        mTask.execute();
    }

    private void getServerResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.optJSONArray("media");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                ProductDetailModel productDetailModel = new ProductDetailModel();
                productDetailModel.setImage(jsonObject1.optBoolean("isImage"));
                productDetailModel.setVideo(jsonObject1.optBoolean("isVideo"));
                productDetailModel.setImageURL(jsonObject1.optString("imageUrl"));
                productDetailModel.setVideo(jsonObject1.optString("video"));
                productDetailModel.setVideoPreviewImage(jsonObject1.optString("videoPreviewImage"));
                productDetailsArrayList.add(productDetailModel);
            }

            JSONObject jsonObject1 = jsonObject.getJSONObject("productDetail");
            JSONArray jsonArray1 = jsonObject1.optJSONArray("data");
            for (int k = 0; k < jsonArray1.length(); k++) {
                ProductDetailModel productDetailModel = new ProductDetailModel();
                JSONObject jsonObject2 = jsonArray1.optJSONObject(k);
                productDetailModel.setProductTitle(jsonObject2.optString("title"));
                productDetailModel.setProductIcon(jsonObject2.optString("icon"));
                JSONArray jsonArray2 = jsonObject2.optJSONArray("dataDescription");

                ArrayList<ProductDetailModel.ProductChild> childList = new ArrayList<>();
                for (int j = 0; j < jsonArray2.length(); j++) {
                    ProductDetailModel.ProductChild productChild = new ProductDetailModel.ProductChild();
                    JSONObject jsonObject3 = jsonArray2.optJSONObject(j);
                    productChild.setHeading(jsonObject3.optString("heading"));
                    productChild.setDescription(jsonObject3.optString("description"));
                    productChild.setCheckStock(jsonObject3.optBoolean("isCheckStock"));
                    childList.add(productChild);
                }
                productDetailModel.setProductDescription(childList);
                expandableListDetail.put(productDetailModel, childList);
            }

            if (productDetailsArrayList.size() > 0) {
                ImageSliderViewPagerAdapter viewPagerAdapter = new ImageSliderViewPagerAdapter(this, productDetailsArrayList);
                viewPager.setAdapter(viewPagerAdapter);
                dotscount = viewPagerAdapter.getCount();
                dots = new ImageView[dotscount];

                for (int i = 0; i < dotscount; i++) {
                    dots[i] = new ImageView(this);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8, 0, 8, 0);
                    sliderDotspanel.addView(dots[i], params);

                }
                dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
                NUM_PAGES = dots.length;

                // Auto start of viewpager
                final Handler handler = new Handler();
                final Runnable Update = new Runnable() {
                    public void run() {
                        if (currentPage == NUM_PAGES) {
                            currentPage = 0;
                        }
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                };
                Timer swipeTimer = new Timer();
                swipeTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(Update);
                    }
                }, 2000, 2000);
            }


            expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
            productDetailsExpandableListAdapter = new ProductDetailsExpandableListAdapter(this, expandableListTitle, expandableListDetail, expandableListViewProduct);
            expandableListViewProduct.setAdapter(productDetailsExpandableListAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        textViewProductName = findViewById(R.id.textViewProductName);

        expandableListViewProduct = findViewById(R.id.expandableListViewProduct);
//        expandableListViewProduct.setGroupIndicator(null);
//        expandableListViewProduct.setChildIndicator(null);
//        expandableListViewProduct.setChildDivider(getResources().getDrawable(R.color.transparent_color));
//        expandableListViewProduct.setDivider(getResources().getDrawable(R.color.black));
//        expandableListViewProduct.setDividerHeight(2);
        expandableListViewProduct.setChildDivider(getResources().getDrawable(R.color.white));
    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle(getResources().getString(R.string.toolbar_title_catalogue_product_details));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        textViewProductName.setText(productName);

        viewPager.addOnPageChangeListener(this);


        expandableListViewProduct.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                //Toast.makeText(mContext,"Group clicked",Toast.LENGTH_SHORT).show();
//                switch (groupPosition) {
//                    case 0:
//                       Toast.makeText(mContext,"Position "+groupPosition+" Clicked",Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        Toast.makeText(mContext,"Position "+groupPosition+" Clicked",Toast.LENGTH_SHORT).show();
//                        break;
//                    case 2:
//                        Toast.makeText(mContext,"Position "+groupPosition+" Clicked",Toast.LENGTH_SHORT).show();
//                        break;
//                    case 3:
//                        Toast.makeText(mContext,"Position "+groupPosition+" Clicked",Toast.LENGTH_SHORT).show();
//                        break;
//                    case 4:
//                        Toast.makeText(mContext,"Position "+groupPosition+" Clicked",Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
                return false;
            }
        });
        expandableListViewProduct.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedGroup != groupPosition) {
                    expandableListViewProduct.collapseGroup(lastExpandedGroup);
                }
/*
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
                lastExpandedGroup = groupPosition;
            }
        });
        expandableListViewProduct.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String mainMenu = expandableListTitle.get(groupPosition).toString();
                String subMenu = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).toString();

                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewProductName, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(toolbar, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotscount; i++) {
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
        }
        dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        dismissProgress();
        if (httpStatusCode == Constants.SUCCESS) {
            if (resultObj != null) {
                getServerResponse(serverResponse);

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
