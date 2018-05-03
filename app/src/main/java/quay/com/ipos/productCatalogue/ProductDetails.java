package quay.com.ipos.productCatalogue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.adapter.ImageSliderViewPagerAdapter;
import quay.com.ipos.constant.ExpandabelProductDetails;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.productCatalogue.productCatalogueAdapter.ProductDetailsExpandableListAdapter;
import quay.com.ipos.utility.FontUtil;

/**
 * Created by niraj.kumar on 4/18/2018.
 */

public class ProductDetails extends AppCompatActivity implements InitInterface, ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    private Toolbar toolbar;
    private TextView textViewProductName;
    private String productName;
    private ExpandableListView expandableListViewProduct;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private ProductDetailsExpandableListAdapter productDetailsExpandableListAdapter;
    private Context mContext;
    private int lastExpandedGroup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);
        mContext = ProductDetails.this;
        Intent i = getIntent();
        productName = i.getStringExtra("ProductName");

        findViewById();
        applyInitValues();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        textViewProductName = findViewById(R.id.textViewProductName);

        expandableListViewProduct = findViewById(R.id.expandableListViewProduct);
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

        ImageSliderViewPagerAdapter viewPagerAdapter = new ImageSliderViewPagerAdapter(this);
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
        viewPager.addOnPageChangeListener(this);


        expandableListDetail = ExpandabelProductDetails.getData();

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        productDetailsExpandableListAdapter = new ProductDetailsExpandableListAdapter(this, expandableListTitle, expandableListDetail,expandableListViewProduct);
        expandableListViewProduct.setAdapter(productDetailsExpandableListAdapter);
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
}
