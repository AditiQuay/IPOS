package quay.com.ipos.base;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.adapter.DrawerItemCustomAdapter;
import quay.com.ipos.adapter.NavigationViewExpeListViewAdapter;
import quay.com.ipos.constant.ExpandableListDataPump;
import quay.com.ipos.dashboard.fragment.DashboardFragment;
import quay.com.ipos.dashboard.fragment.DashboardItemFragment;
import quay.com.ipos.dashboard.fragment.McCOYDashboardFragment;
import quay.com.ipos.listeners.FilterListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.DrawerModal;
import quay.com.ipos.productCatalogue.ProductMain;
import quay.com.ipos.retailsales.activity.PaymentModeActivity;
import quay.com.ipos.retailsales.fragment.RetailSalesFragment;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.CircleImageView;
import quay.com.ipos.utility.FontUtil;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, InitInterface, View.OnClickListener,FilterListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String[] mNavigationDrawerItemTitles;
    private ListView listViewContent;
    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ExpandableListView expandableListView1;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private NavigationViewExpeListViewAdapter navigationViewExpeListViewAdapter;
    private int lastExpandedGroup;
    public static int containerId;
    private static final int CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private Fragment dashboardFragment = null, productCatalogueMainFragment = null, retailSalesFragment = null;
    boolean doubleBackToExitPressedOnce = false, exit = false, toggle = false;
    private Menu menu1;
    private LinearLayout lLaoutBtnP, lLaoutBtnI, lLaoutBtnM;
    private View viewM, viewI, viewP;
    private CircleImageView imageViewProfileDummy;
    private TextView textViewMyBusiness, textViewAccount;
    private TextView textViewP, textViewI, textViewM;
    public static DashboardItemFragment dashboardItemFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        applyInitValues();
        applyTypeFace();
        setDashBoard();
        dashboardItemFragment=new DashboardItemFragment();
    }

    private void setDashBoard() {
        dashboardFragment = new McCOYDashboardFragment();
        addFragment(dashboardFragment, containerId);
        toolbar.setTitle(getString(R.string.dashboard));
    }

    @Override
    public void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_title_catalogue_product_details));


        containerId = R.id.fragment_container;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        listViewContent = findViewById(R.id.listViewContent);

        lLaoutBtnP = findViewById(R.id.lLaoutBtnP);
        lLaoutBtnI = findViewById(R.id.lLaoutBtnI);
        lLaoutBtnM = findViewById(R.id.lLaoutBtnM);

        viewP = findViewById(R.id.viewP);
        viewI = findViewById(R.id.viewI);
        viewM = findViewById(R.id.viewM);

        imageViewProfileDummy = findViewById(R.id.imageViewProfileDummy);

        textViewMyBusiness = findViewById(R.id.textViewMyBusiness);
        textViewAccount = findViewById(R.id.textViewAccount);

        textViewP = findViewById(R.id.textViewP);
        textViewI = findViewById(R.id.textViewI);
        textViewM = findViewById(R.id.textViewM);


        expandableListView1 = findViewById(R.id.expandableListView1);
        expandableListView1.setGroupIndicator(null);
        expandableListView1.setChildIndicator(null);
        expandableListView1.setChildDivider(getResources().getDrawable(R.color.white));
        expandableListView1.setDivider(getResources().getDrawable(R.color.expand_list_color));
        expandableListView1.setDividerHeight(0);

//        profileImageSwitch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (!toggle) {
//                    profileImageSwitch.setImageResource(R.drawable.profile_bg);
//                    profileImage.setImageResource(R.drawable.place_holder);
//
//                    expandableListView1.setVisibility(View.VISIBLE);
//                    llNavigation.setVisibility(View.GONE);
//                    toggle = true;
//                } else {
//                    profileImageSwitch.setImageResource(R.drawable.place_holder);
//                    profileImage.setImageResource(R.drawable.profile_bg);
//                    expandableListView1.setVisibility(View.GONE);
//                    llNavigation.setVisibility(View.VISIBLE);
//                    toggle = false;
//                }
//
//
//            }
//        });
        lLaoutBtnP.setOnClickListener(this);
        lLaoutBtnI.setOnClickListener(this);
        lLaoutBtnM.setOnClickListener(this);
    }

    @Override
    public void applyInitValues() {


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        DrawerModal[] drawerItem = new DrawerModal[7];

        drawerItem[0] = new DrawerModal(R.drawable.cart, "New Order");
        drawerItem[1] = new DrawerModal(R.drawable.order_center, "Order Centre");
        drawerItem[2] = new DrawerModal(R.drawable.insights, "Dashboard & Insight");
        drawerItem[3] = new DrawerModal(R.drawable.catalogue, "Product Catalogue");
        drawerItem[4] = new DrawerModal(R.drawable.stock_price, "Stock & Price");
        drawerItem[5] = new DrawerModal(R.drawable.loyalty, "Loyalty Program");
        drawerItem[6] = new DrawerModal(R.drawable.partner, "Partner Connect");


        int UnSelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        int SelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());


        viewP.setBackgroundColor(getResources().getColor(R.color.menu_strip));
        textViewP.setLayoutParams(new RelativeLayout.LayoutParams(SelectSize, SelectSize));
        textViewP.setBackgroundResource(R.drawable.menu_background_select);


        viewI.setBackgroundColor(getResources().getColor(R.color.transparent_color));
        textViewI.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
        textViewI.setBackgroundResource(R.drawable.menu_background_unselect);

        viewM.setBackgroundColor(getResources().getColor(R.color.transparent_color));
        textViewM.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
        textViewM.setBackgroundResource(R.drawable.menu_background_unselect);


        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.drawer_list_items, drawerItem);
        listViewContent.setAdapter(adapter);
        listViewContent.setOnItemClickListener(new DrawerItemClickListener());

        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        navigationViewExpeListViewAdapter = new NavigationViewExpeListViewAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView1.setAdapter(navigationViewExpeListViewAdapter);
        expandableListView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

//                //Toast.makeText(mContext,"Group clicked",Toast.LENGTH_SHORT).show();
//                switch (groupPosition) {
//                    case 0:
//                        break;
//                    case 1:
//                        retailSalesFragment = new RetailSalesFragment();
//                        replaceFragment(retailSalesFragment,containerId);
//                        drawer.closeDrawer(GravityCompat.START);
//                        toolbar.setTitle(getString(R.string.retail_sales));
//                        menu1.findItem(R.id.action_notification).setVisible(false);
//                        menu1.findItem(R.id.action_search).setVisible(true);
//                        break;
//                    case 2:
//                        menu1.findItem(R.id.action_notification).setVisible(false);
//                        drawer.closeDrawer(GravityCompat.START);
//                        break;
//                    case 3:
//                        menu1.findItem(R.id.action_notification).setVisible(false);
//                        drawer.closeDrawer(GravityCompat.START);
//                        break;
//                    case 4:
//                        menu1.findItem(R.id.action_notification).setVisible(true);
//                        dashboardFragment = new DashboardFragment();
//                        replaceFragment(dashboardFragment, containerId);
//                        drawer.closeDrawer(GravityCompat.START);
//                        toolbar.setTitle(getString(R.string.dashboard));
//                        menu1.findItem(R.id.action_search).setVisible(true);
//                        break;
//                    default:
//                        break;
//                }
                return false;
            }
        });
        expandableListView1.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedGroup != groupPosition) {
                    expandableListView1.collapseGroup(lastExpandedGroup);
                }
/*
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
                lastExpandedGroup = groupPosition;
            }
        });

        expandableListView1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int index = parent.getFlatListPosition(ExpandableListView
                        .getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);

                String mainMenu = expandableListTitle.get(groupPosition).toString();
                String subMenu = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).toString();
//                Toast.makeText(getApplicationContext(), mainMenu + " -> " + subMenu, Toast.LENGTH_SHORT).show();

                if (groupPosition == 1) {
                    if (childPosition == 0) {
                        retailSalesFragment = new RetailSalesFragment();
                        replaceFragment(retailSalesFragment, containerId);
                        drawer.closeDrawer(GravityCompat.START);
                        toolbar.setTitle(getString(R.string.retail_sales));
                        menu1.findItem(R.id.action_notification).setVisible(false);
                        menu1.findItem(R.id.action_search).setVisible(true);
                    }
                } else if (groupPosition == 4) {
                    if (childPosition == 1) {
                        menu1.findItem(R.id.action_notification).setVisible(true);
                        dashboardFragment = new DashboardFragment();
                        replaceFragment(dashboardFragment, containerId);
                        drawer.closeDrawer(GravityCompat.START);
                        toolbar.setTitle(getString(R.string.dashboard));
                        menu1.findItem(R.id.action_search).setVisible(false);
                    }
                }
                return true;
            }
        });
    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewMyBusiness, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
        FontUtil.applyTypeface(toolbar, FontUtil.getTypeFaceRobotTiteliumRegular(mContext));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View v) {
        int UnSelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        int SelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());


        if (v == lLaoutBtnP) {

            listViewContent.setVisibility(View.VISIBLE);
            expandableListView1.setVisibility(View.GONE);


            viewP.setBackgroundColor(getResources().getColor(R.color.menu_strip));
            textViewP.setLayoutParams(new RelativeLayout.LayoutParams(SelectSize, SelectSize));
            textViewP.setBackgroundResource(R.drawable.menu_background_select);


            viewI.setBackgroundColor(getResources().getColor(R.color.transparent_color));
            textViewI.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
            textViewI.setBackgroundResource(R.drawable.menu_background_unselect);

            viewM.setBackgroundColor(getResources().getColor(R.color.transparent_color));
            textViewM.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
            textViewM.setBackgroundResource(R.drawable.menu_background_unselect);

            imageViewProfileDummy.setImageResource(R.drawable.cystal);
        }
        if (v == lLaoutBtnI) {
            listViewContent.setVisibility(View.GONE);
            expandableListView1.setVisibility(View.VISIBLE);

            viewI.setBackgroundColor(getResources().getColor(R.color.menu_strip));
            textViewI.setBackgroundResource(R.drawable.menu_background_select);
            textViewI.setLayoutParams(new RelativeLayout.LayoutParams(SelectSize, SelectSize));


            viewP.setBackgroundColor(getResources().getColor(R.color.transparent_color));
            textViewP.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
            textViewP.setBackgroundResource(R.drawable.menu_background_unselect);

            viewM.setBackgroundColor(getResources().getColor(R.color.transparent_color));
            textViewM.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
            textViewM.setBackgroundResource(R.drawable.menu_background_unselect);

            imageViewProfileDummy.setImageResource(R.drawable.profile_thumb);
        }
        if (v == lLaoutBtnM) {
            viewM.setBackgroundColor(getResources().getColor(R.color.menu_strip));
            textViewM.setLayoutParams(new RelativeLayout.LayoutParams(SelectSize, SelectSize));
            textViewM.setBackgroundResource(R.drawable.menu_background_select);

            viewI.setBackgroundColor(getResources().getColor(R.color.transparent_color));
            textViewI.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
            textViewI.setBackgroundResource(R.drawable.menu_background_unselect);

            viewP.setBackgroundColor(getResources().getColor(R.color.transparent_color));
            textViewP.setLayoutParams(new RelativeLayout.LayoutParams(UnSelectSize, UnSelectSize));
            textViewP.setBackgroundResource(R.drawable.menu_background_unselect);

        }
    }

    @Override
    public void onUpdateTitle(String title) {
        dashboardItemFragment.onUpdateTitle("dialog");


    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            setItemNormal();
            setItemSelected(view, position);

            selectItem(position, view);
        }

    }

    private void setItemNormal() {
        for (int i = 0; i < listViewContent.getChildCount(); i++) {
            View v = listViewContent.getChildAt(i);
            View border = v.findViewById(R.id.vListGrp);
            border.setVisibility(View.GONE);
            listViewContent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.menu_strip_color));
        }
    }

    private void setItemSelected(View view, int pos) {
        View borderView = view.findViewById(R.id.vListGrp);
        borderView.setVisibility(View.VISIBLE);
        listViewContent.getChildAt(pos).setBackgroundColor(getResources().getColor(R.color.light_blue));
    }

    private void selectItem(int position, View view) {
        switch (position) {
            case 0:

                Intent i=new Intent(MainActivity.this, PaymentModeActivity.class);
                startActivity(i);

                break;
            case 1:

                break;
            case 2:
                dashboardFragment = new McCOYDashboardFragment();
                replaceFragment(dashboardFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.dashboard));
                menu1.findItem(R.id.action_notification).setVisible(true);
                menu1.findItem(R.id.action_search).setVisible(false);

                drawer.closeDrawer(GravityCompat.START);
                break;
            case 3:
                productCatalogueMainFragment = new ProductMain();
                replaceFragment(productCatalogueMainFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.toolbar_title_catalogue_product_details));
                menu1.findItem(R.id.action_notification).setVisible(false);
                menu1.findItem(R.id.action_search).setVisible(false);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case 4:

                break;
            case 5:

                break;
            case 6:
                retailSalesFragment = new RetailSalesFragment();
                replaceFragment(retailSalesFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.retail_sales));
//                menu1.findItem(R.id.action_notification).setVisible(false);
//                menu1.findItem(R.id.action_search).setVisible(true);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//
//        }
        if (drawer.isDrawerOpen(Gravity.START)) {
            closeDrawer();
            return;
        }
        // super.onBackPressed();
        else {
            Fragment mFrag = getVisibleFragment();


            if (mFrag == dashboardFragment) {
                (new AlertDialog.Builder(this)).setTitle("Confirm action")
                        .setMessage("Do you want to Exit?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                doubleBackToExitPressedOnce = true;
                                exit = true;
                                finish();
                            }
                        }).setNegativeButton("No", null).show();
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    return;
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false; // exit = false;
                        AppLog.e(TAG, "doubleBackToExitPressedOnce here false");
                    }
                }, 5000);
            } else {
                super.onBackPressed();
            }

        }
    }

    private void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu1 = menu;
        // Retrieve the SearchView and plug it into SearchManager
//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
