package quay.com.ipos.base;

import android.Manifest;
import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import io.realm.Realm;
import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.adapter.DrawerRoleAdapter;
import quay.com.ipos.adapter.NavigationViewExpeListViewAdapter;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.constant.ExpandableListDataPump;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerModel;
import quay.com.ipos.customerInfo.customerInfoModal.CustomerServerModel;
import quay.com.ipos.dashboard.fragment.DashboardFragment;
import quay.com.ipos.dashboard.fragment.DashboardItemFragment;
import quay.com.ipos.dashboard.fragment.McCOYDashboardFragment;
import quay.com.ipos.data.local.dao.MostUsedFunDao;
import quay.com.ipos.data.local.entity.MostUsed;
import quay.com.ipos.pss_order.fragment.NewOrderFragment;
import quay.com.ipos.pss_order.fragment.OrderCentreListFragment;
import quay.com.ipos.enums.CustomerEnum;
import quay.com.ipos.helper.DatabaseHandler;
import quay.com.ipos.inventory.fragment.InventoryFragment;
import quay.com.ipos.listeners.FilterListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.ScanFilterListener;
import quay.com.ipos.listeners.SendScannerBarcodeListener;
import quay.com.ipos.modal.DrawerRoleModal;
import quay.com.ipos.modal.MenuModal;
import quay.com.ipos.partnerConnect.PartnerConnectMain;
import quay.com.ipos.productCatalogue.ProductMain;
import quay.com.ipos.realmbean.RealmUserDetail;
import quay.com.ipos.retailsales.fragment.RetailSalesFragment;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.MessageDialog;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.CircleImageView;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.NetUtil;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

public class MainActivity extends BaseActivity
        implements SendScannerBarcodeListener,NavigationView.OnNavigationItemSelectedListener, ServiceTask.ServiceResultListener, InitInterface, FilterListener, MessageDialog.MessageDialogListener, AdapterView.OnItemClickListener, ScanFilterListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private String[] mNavigationDrawerItemTitles;
    private ListView listViewContent;
    private DrawerLayout drawer;
    private FloatingActionButton fab;

    //tes
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ExpandableListView expandableListView1;
    ArrayList<MenuModal> expandableListTitle;
    HashMap<MenuModal, List<String>> expandableListDetail;
    private NavigationViewExpeListViewAdapter navigationViewExpeListViewAdapter;
    private int lastExpandedGroup;
    public static int containerId;
    private static final int CAMERA_PERMISSION = 1;
    private Class<?> mClss;
    private Fragment dashboardFragment = null,inventaortFragment=null, productCatalogueMainFragment = null, retailSalesFragment = null, mNewOrderFragment = null, mOrderCentreListFragment = null;
    boolean doubleBackToExitPressedOnce = false, exit = false, toggle = false;
    private Menu menu1;
    private LinearLayout lLaoutBtnP, lLaoutBtnI, lLaoutBtnM;
    private View viewM, viewI, viewP;
    private CircleImageView imageViewProfileDummy;
    private TextView textViewMyBusiness, textViewAccount;
    private TextView textViewP, textViewI, textViewM;
    public static DashboardItemFragment dashboardItemFragment;
    public static NewOrderFragment newOrderScannerFragment;
    private int preMenu = -1;
    private View view1;
    private ArrayList<Integer> inMenu = new ArrayList<>();
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private int count = 0;
    private ListView lvMenu;
    int mSelectedItemPosition;
    ArrayList<DrawerRoleModal> drawerRoleModals = new ArrayList<>();
    private DrawerRoleAdapter drawerRoleAdapter;
    //a broadcast to know weather the data is synced or not
    public static final String DATA_SAVED_BROADCAST = "ipos.datasaved";
    //Broadcast receiver to know the sync status
    private BroadcastReceiver broadcastReceiver;

    public static RetailSalesFragment retailSalesFragment1;

    private DatabaseHandler dbHelper;
    private String customerID;
    private String customerTitle;
    private String customerName;
    private String customerFirstName;
    private String customerLastName;
    private String customerGender;
    private String customerBday;
    private String customerMaritalStatus;
    private String customerSpouseFirstName;
    private String customerSpouseLastName;
    private String customerSpouseDob;
    private String customerChildSatus;
    private JSONArray customerChild;
    private String customerEmail;
    private String customerEmail2;
    private String customerPhone;
    private String customerPhone2;
    private String customerPhone3;
    private String customerAddress;
    private String customerState;
    private String customerCity;
    private String customerPin;
    private String customerCountry;
    private String customerDesignation;
    private String customerCompany;
    private String custoemrGstin;
    private String customer;
    private String customerRelationship;
    private String customerImage;
    private String lastBillingDate;
    private String lastBillingAmount;
    private String customerPoints;
    private String issuggestion;
    private String suggestion;
    private JSONArray recentOrders;
    private String cFactore;
    private String customerType;
    private String customerCode;
    private String registeredBusinessPlaceID;
    private String customerDom;
    private Context mContext;
    private String customerStatus;
    private double customerPointsPerValue;

    private int mActivePosition = 1;
    private int currentType = -1;
    private boolean firstTime = true;
    private List<String> mostUsedFunList = new ArrayList<>();


    private ArrayList<CustomerModel> customerModels = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHandler(this);
        mContext = MainActivity.this;
        if (NetUtil.isNetworkAvailable(mContext)) {
            getCustomerData();

        }
        findViewById();
        applyInitValues();
        applyTypeFace();
        setDashBoard();
        dashboardItemFragment = new DashboardItemFragment();
        newOrderScannerFragment=new NewOrderFragment();

        retailSalesFragment1 = new RetailSalesFragment();
        //the broadcast receiver to update sync status
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                //loading the names again
//                loadNames();
                AppLog.e("tag","onReceive");
            }
        };
        try {
            LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
        }catch (Exception e){

        }

    }

    protected void onPostResume() {
        super.onPostResume();

        try {
            LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(DATA_SAVED_BROADCAST));
        }catch (Exception e){

        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        try{
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e){
            // already unregistered
        }
    }
    private void getCustomerData() {
        int storeId = SharedPrefUtil.getStoreId(Constants.STORE_ID.trim(), 0, mContext);
        AppLog.e(TAG, "StoreId" + storeId);

        CustomerModel customerModel = new CustomerModel();
        customerModel.setSearchParam("NA");
        customerModel.setStoreId(String.valueOf(1));

        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_CUSTOMER_LIST);
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(customerModel);
        mTask.setListener(this);
        mTask.setResultType(CustomerServerModel.class);
        mTask.execute();
    }

    private void setDashBoard() {
        dashboardFragment = new McCOYDashboardFragment();
        addFragment(dashboardFragment, containerId);
        toolbar.setTitle(getString(R.string.dashboard));

    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.toolbar_title_catalogue_product_details));
        launchActivity(false);

        containerId = R.id.fragment_container;
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        listViewContent = findViewById(R.id.listViewContent);
        lvMenu = findViewById(R.id.lvMenu);

        imageViewProfileDummy = findViewById(R.id.imageViewProfileDummy);

        textViewMyBusiness = findViewById(R.id.textViewMyBusiness);
        textViewAccount = findViewById(R.id.textViewAccount);

        expandableListView1 = findViewById(R.id.expandableListView1);
        expandableListView1.setGroupIndicator(null);
        expandableListView1.setChildIndicator(null);
        expandableListView1.setChildDivider(getResources().getDrawable(R.color.white));
        expandableListView1.setDivider(getResources().getDrawable(R.color.expand_list_color));
        expandableListView1.setDividerHeight(0);

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Constants.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications


                } else if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    MenuItem menu = menu1.findItem(R.id.action_notification);
                    View actionView = MenuItemCompat.getActionView(menu);
                    count++;
                    TextView cart_badge = actionView.findViewById(R.id.cart_badge);
                    cart_badge.setText(count + "");
                    String message = intent.getStringExtra("message");


                    //txtMessage.setText(message);
                }
            }
        };
    }

    @Override
    public void applyInitValues() {


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        Realm realm = Realm.getDefaultInstance();
        RealmUserDetail realmUserDetails = realm.where(RealmUserDetail.class).findFirst();
        if (realmUserDetails != null) {
            try {
                JSONArray jsonArray = new JSONArray(realmUserDetails.getUserMenu());
                drawerRoleModals = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    DrawerRoleModal modal = new DrawerRoleModal();
                    JSONObject jsonObject = jsonArray.optJSONObject(i);
                    JSONArray jsonArray1 = jsonObject.optJSONArray("data");
                    if (jsonObject.has("key") && jsonArray1.length() > 0 && !Util.validateString(textViewMyBusiness.getText().toString())) {
                        textViewMyBusiness.setText(jsonArray.optJSONObject(i).optString("userName"));
                        textViewAccount.setText(jsonArray.optJSONObject(i).optString("account"));
                    }
                    if (jsonObject.has("key") && jsonArray1.length() > 0) {
                        modal.setName(jsonObject.optString("key"));
                        drawerRoleModals.add(modal);

                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        if (drawerRoleModals.size() > 0)
            drawerRoleModals.get(0).setSelected(true);
        drawerRoleAdapter = new DrawerRoleAdapter(this, R.layout.drawer_role_item, drawerRoleModals);
        lvMenu.setAdapter(drawerRoleAdapter);
        lvMenu.setOnItemClickListener(this);


        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        navigationViewExpeListViewAdapter = new NavigationViewExpeListViewAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView1.setAdapter(navigationViewExpeListViewAdapter);
        expandableListView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });


        expandableListView1.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedGroup != groupPosition) {
                    expandableListView1.collapseGroup(lastExpandedGroup);
                }
                String mainMenu = expandableListTitle.get(groupPosition).getGroupTitle();

                if (expandableListTitle.get(groupPosition).getArrayList().size() == 0) {
                    applyMenuBGImage(mainMenu);
                }

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
                String subMenu = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition);
                applyMenuBGImage(subMenu);
                return true;
            }
        });


    }

    public void openRetailSalesFragment() {
        retailSalesFragment = new RetailSalesFragment();
        replaceFragment(retailSalesFragment, containerId);
    }

    public void setToolbarTitle(String name) {
        toolbar.setTitle(name);
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
    public void onUpdateTitle(String title) {
        dashboardItemFragment.onUpdateTitle("dialog");
    }

    @Override
    public void onDialogPositiveClick(Dialog dialog, int mCallType) {
        if (mCallType == Constants.APP_DIALOG_PERMISSION) {
            Util.OpenSetting(MainActivity.this);
        } else if (mCallType == Constants.APP_DIALOG_BACK) {
            doubleBackToExitPressedOnce = true;
            exit = true;
            finish();
            dialog.dismiss();
        }
    }

    @Override
    public void onDialogNegetiveClick(Dialog dialog, int mCallType) {
        if (mCallType == Constants.APP_DIALOG_BACK) {
            doubleBackToExitPressedOnce = false;
            exit = false;
            dialog.dismiss();
        }
    }

    @Override
    public void onUpdate(String title, Context mContext) {

        retailSalesFragment1.onUpdate(title, MainActivity.this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentType = position;
        int UnSelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        int SelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());
        setMenuItemNormal();
        if (mSelectedItemPosition != -1 && mSelectedItemPosition != position) {
            view = lvMenu.getChildAt(position);
            View borderView = view.findViewById(R.id.viewP);
            TextView textView = view.findViewById(R.id.textViewP);

            borderView.setBackgroundColor(mContext.getResources().getColor(R.color.menu_strip));
            textView.setLayoutParams(new RelativeLayout.LayoutParams(SelectSize, SelectSize));
            textView.setBackgroundResource(R.drawable.menu_background_select);
            drawerRoleModals.get(position).setSelected(true);

        }
        mSelectedItemPosition = position;
        drawerRoleAdapter.notifyDataSetChanged();

        expandableListDetail = expandableDataonClick(drawerRoleModals.get(position).name);
        expandableListTitle = new ArrayList<>(expandableListDetail.keySet());
        navigationViewExpeListViewAdapter = new NavigationViewExpeListViewAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView1.setAdapter(navigationViewExpeListViewAdapter);
    }

    private void setMenuItemNormal() {
        int UnSelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        int SelectSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());

        for (int i = 0; i < lvMenu.getChildCount(); i++) {
            View v = lvMenu.getChildAt(i);
            View border = v.findViewById(R.id.viewP);
            TextView textView = v.findViewById(R.id.textViewP);

            border.setBackgroundColor(mContext.getResources().getColor(R.color.transparent_color));
            textView.setLayoutParams(new RelativeLayout.LayoutParams(SelectSize, SelectSize));
            textView.setBackgroundResource(R.drawable.menu_background_unselect);
            drawerRoleModals.get(i).setSelected(false);
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
        View v = listViewContent.getChildAt(pos);
        View borderView = v.findViewById(R.id.vListGrp);
        borderView.setVisibility(View.VISIBLE);
        listViewContent.getChildAt(pos).setBackgroundColor(getResources().getColor(R.color.light_blue));
    }

    public void applyMenuBGImage(String ImageName) {
        Log.i("currentType", currentType + "");
        if (currentType == 1) {
            if (!ImageName.contains("Mostly Used")) {
                if (!mostUsedFunList.contains(ImageName)) {
                    saveToDatabase(ImageName);
                } else {
                    update(ImageName);
                }
            }
        }

        switch (ImageName) {
            case "Mostly Used":

                break;
            case "Billing & Cash":
                retailSalesFragment = new RetailSalesFragment();
                replaceFragment(retailSalesFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.retail_sales));
                menu1.findItem(R.id.action_notification).setVisible(false);
                menu1.findItem(R.id.action_search).setVisible(true);
                break;
            case "Retail Sales (OTC & Online)":
                retailSalesFragment = new RetailSalesFragment();
                replaceFragment(retailSalesFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.retail_sales));
                menu1.findItem(R.id.action_notification).setVisible(false);
                menu1.findItem(R.id.action_search).setVisible(true);
                break;

            case "Manage Store":

                break;

            case "Inventory In/Out":
                inventaortFragment = new InventoryFragment();
                replaceFragment(inventaortFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.inventory));
                menu1.findItem(R.id.action_notification).setVisible(false);
                menu1.findItem(R.id.action_search).setVisible(false);

                drawer.closeDrawer(GravityCompat.START);

                break;
            case "Manage Business":

                break;
            case "Insights & Analytics":
                menu1.findItem(R.id.action_notification).setVisible(true);
                dashboardFragment = new DashboardFragment();
                replaceFragment(dashboardFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.dashboard));
                menu1.findItem(R.id.action_search).setVisible(false);
                break;
            case "New Order":
                mNewOrderFragment = new NewOrderFragment();
                replaceFragment(mNewOrderFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.new_orders));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case "Order Center":
                mOrderCentreListFragment = new OrderCentreListFragment();
                replaceFragment(mOrderCentreListFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.order_centre));
                drawer.closeDrawer(GravityCompat.START);
                break;
            case "Dashboard & Insights":
                dashboardFragment = new McCOYDashboardFragment();
                replaceFragment(dashboardFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.dashboard));
                menu1.findItem(R.id.action_notification).setVisible(true);
                menu1.findItem(R.id.action_search).setVisible(false);

                drawer.closeDrawer(GravityCompat.START);
                break;
            case "Product Catalogue":
                productCatalogueMainFragment = new ProductMain();
                replaceFragment(productCatalogueMainFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.toolbar_title_catalogue_product_details));
                menu1.findItem(R.id.action_notification).setVisible(false);
                menu1.findItem(R.id.action_search).setVisible(false);

                drawer.closeDrawer(GravityCompat.START);
                break;
            case "Stock & Price":
                              //   imageId = R.drawable.insights;
                break;
            case "Loyalty Program":

                //  imageId = R.drawable.insights;
                break;
            case "Partner Connect":
                // imageId = R.drawable.insights;
                Intent intent = new Intent(getApplicationContext(), PartnerConnectMain.class);
                startActivity(intent);
                break;


        }


    }

    private void saveToDatabase(String imageName) {
        MostUsed mostUsed = new MostUsed();
        mostUsed.funName = imageName;
        mostUsed.count = 0;
        insert(mostUsed);


    }

    private void selectItem(int position) {
        switch (position) {
            case 0:
                mNewOrderFragment = new NewOrderFragment();
                replaceFragment(mNewOrderFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.new_orders));

                break;
            case 1:
                mOrderCentreListFragment = new OrderCentreListFragment();
                replaceFragment(mOrderCentreListFragment, containerId);
                drawer.closeDrawer(GravityCompat.START);
                toolbar.setTitle(getString(R.string.order_centre));
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
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        if (fragmentManager.getBackStackEntryCount() > 0) {
//            fragmentManager.popBackStack("fragment", FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            if (inMenu.size() > 0) {
//                setItemNormal();
//                setItemSelected(view1, inMenu.get(inMenu.size() - 1));
//                selectItem(inMenu.get(inMenu.size() - 1));
//                inMenu.remove(inMenu.size() - 1);
//
//            } else {
//                finish();
//            }
//        } else {
//            super.onBackPressed();
//        }
     /*   setItemNormal();
        setItemSelected(view1, preMenu);
        selectItem(preMenu);*/
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
                Util.showMessageDialog(mContext, this, getString(R.string.exit_message), getResources().getString(R.string.yes), getResources().getString(R.string.no), Constants.APP_DIALOG_BACK, "", getSupportFragmentManager());
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

    @Override
    public void onDialogCancelClick(Dialog dialog, int mCallType) {

    }

    private void closeDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        this.menu1 = menu;
        MenuItem menu12 = menu1.findItem(R.id.action_notification);
        View actionView = MenuItemCompat.getActionView(menu12);

        TextView cart_badge = actionView.findViewById(R.id.cart_badge);
        cart_badge.setText(count + "");
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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean CameraPermission = false;
    boolean settingPermission = false;

    public boolean launchActivity(boolean settingPermission) {
        this.settingPermission = settingPermission;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
//            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION);
            CameraPermission = false;
        } else {
            CameraPermission = true;
        }
        return CameraPermission;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CameraPermission = true;
                } else {
                    if (!settingPermission)
                        Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                    else
                        Util.showMessageDialog(mContext, this, "Please grant camera permission to use the QR Scanner ", "Open Settings", null, Constants.APP_DIALOG_PERMISSION, "Alert!", getSupportFragmentManager());
                    CameraPermission = false;
                }
                return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        // NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);

        super.onPause();
    }


    private HashMap<MenuModal, List<String>> expandableDataonClick(String key) {
        LinkedHashMap<MenuModal, List<String>> expandableListDetail = new LinkedHashMap<>();

        Realm realm = Realm.getDefaultInstance();
        RealmUserDetail realmUserDetails = realm.where(RealmUserDetail.class).findFirst();

        try {
            JSONArray jsonArray = new JSONArray(realmUserDetails.getUserMenu());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.optJSONObject(i);
                String matchKey = jsonObject.optString("key");


                if (matchKey.equalsIgnoreCase(key)) {
                    JSONArray jsonArray1 = jsonObject.optJSONArray("data");
                    textViewMyBusiness.setText(jsonObject.optString("userName"));
                    textViewAccount.setText(jsonObject.optString("account"));
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        MenuModal menuModal = new MenuModal();
                        JSONObject jsonObject1 = jsonArray1.optJSONObject(j);
                        menuModal.setGroupTitle(jsonObject1.optString("title"));
                        menuModal.setGroupIcon(jsonObject1.optString("icon"));
                        JSONArray jsonArray2 = jsonObject1.optJSONArray("child");
                        ArrayList<String> childList = new ArrayList<>();
                        for (int k = 0; k < jsonArray2.length(); k++) {
                            JSONObject jsonObject2 = jsonArray2.optJSONObject(k);
                            childList.add(jsonObject2.optString("name"));

                        }

                        if (j == 0 && currentType == 1) {
                            if (mostUsedFunList != null) {
                                childList.addAll(mostUsedFunList);
                            }
                        }

                        menuModal.setArrayList(childList);

                        expandableListDetail.put(menuModal, childList);
                    }
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return expandableListDetail;
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        if (httpStatusCode == Constants.SUCCESS) {
            if (resultObj != null) {
                if (dbHelper.isCustomerDataEmpty()) {
                    fetchCustomerData(serverResponse);
                }

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

    private void fetchCustomerData(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray(Constants.KEY_CUSTOMER.trim());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                customerID = String.valueOf(object.optInt(CustomerEnum.ColoumnCustomerID.toString().trim()));
                customerTitle = String.valueOf(object.optInt(CustomerEnum.ColoumnCustomerTitle.toString().trim()));
                customerFirstName = object.optString(CustomerEnum.ColoumnCustomerFirstName.toString().trim());
                customerLastName = object.optString(CustomerEnum.ColoumnCustomerLastName.toString().trim());
                customerGender = object.optString(CustomerEnum.ColoumnCustomerGender.toString().trim());
                customerBday = object.optString(CustomerEnum.ColoumnCustomerBday.toString().trim());
                customerMaritalStatus = object.optString(CustomerEnum.ColoumnCustomerMaritalStatus.toString().trim());
                customerSpouseFirstName = object.optString(CustomerEnum.ColoumnCustomerSpouseFirstName.toString().trim());
                customerSpouseLastName = object.optString(CustomerEnum.ColoumnCustomerSpouseLastName.toString().trim());
                customerSpouseDob = object.optString(CustomerEnum.ColoumnCustomerSpouseDob.toString().trim());
                customerChildSatus = object.optString(CustomerEnum.ColoumnCustomerChildStatus.toString().trim());
                customerChild = object.getJSONArray(CustomerEnum.ColoumnCustomerChild.toString().trim());
                customerEmail = object.optString(CustomerEnum.ColoumnCustomerEmail.toString().trim());
                customerEmail2 = object.optString(CustomerEnum.ColoumnCustomerEmail2.toString().trim());
                customerPhone = object.optString(CustomerEnum.ColoumnCustomerPhone.toString().trim());
                customerPhone2 = object.optString(CustomerEnum.ColoumnCustomerPhone2.toString().trim());
                customerPhone3 = object.optString(CustomerEnum.ColoumnCustomerPhone3.toString().trim());
                customerAddress = object.optString(CustomerEnum.ColoumnCustomerAddress.toString().trim());
                customerStatus = object.optString(CustomerEnum.ColoumnCustomerCustomerStatus.toString().trim());
                customerPin = object.optString(CustomerEnum.ColoumnCustomerPin.toString().trim());
                customerCountry = object.optString(CustomerEnum.ColoumnCustomerCountry.toString().trim());
                customerDesignation = object.optString(CustomerEnum.ColoumnCustomerDesignation.toString().trim());
                customerCompany = object.optString(CustomerEnum.ColoumnCustomerCompany.toString().trim());
                custoemrGstin = object.optString(CustomerEnum.ColoumnCustomerGstin.toString().trim());
                customer = object.optString(CustomerEnum.ColoumnCustomer.toString().trim());
                customerRelationship = object.optString(CustomerEnum.ColoumnCustomerRelationship.toString().trim());
                customerImage = object.optString(CustomerEnum.ColoumnCustomerImage.toString().trim());
                lastBillingDate = object.optString(CustomerEnum.ColoumnLastBillingDate.toString().trim());
                lastBillingAmount = object.optString(CustomerEnum.ColoumnLastBillingAmount.toString().trim());
                issuggestion = object.optString(CustomerEnum.ColoumnIsSuggestion.toString().trim());
                suggestion = object.optString(CustomerEnum.ColoumnSuggestion.toString().trim());
                recentOrders = object.getJSONArray(CustomerEnum.ColoumnRecentOrders.toString().trim());
                customerPoints = object.optString(CustomerEnum.ColoumnCustomerPoint.toString().trim());
                customerName = object.optString(CustomerEnum.ColoumnCustomerName.toString().trim());
                customerDom = object.optString(CustomerEnum.ColoumncCustomerDOM.toString().trim());
                cFactore = object.optString(CustomerEnum.ColoumncFactor.toString().trim());
                customerState = object.optString(CustomerEnum.ColoumnCustomerState.toString().trim());
                customerCity = object.optString(CustomerEnum.ColoumnCustomerCity.toString().trim());
                customerType = object.optString(CustomerEnum.ColoumncType.toString().trim());
                customerPointsPerValue = object.optDouble(CustomerEnum.ColoumnPointsPerValue.toString().trim());


                // inserting note in db and getting
                // newly inserted note id

                dbHelper.insertCustomer(customerID, customerTitle, customerName, customerFirstName, customerLastName, customerGender, customerBday, customerMaritalStatus,
                        customerSpouseFirstName, customerSpouseLastName, customerSpouseDob, customerChildSatus, customerChild.toString(),
                        customerEmail, customerEmail2, customerPhone, customerPhone2, customerPhone3, customerAddress, customerState, customerCity,
                        customerPin, customerCountry, customerDesignation, customerCompany, custoemrGstin, customer, customerRelationship,
                        customerImage, lastBillingDate, lastBillingAmount, issuggestion, suggestion, customerPoints, recentOrders.toString(), customerStatus, cFactore, customerType, customerDom, "", "", customerPointsPerValue, 1);


//                // get the newly inserted note from db

//                CustomerModel n = dbHelper.getCustomer(id);
//                Log.e(TAG,"New Added"+ n);
//                if (n != null) {
//                    // adding new note to array list at 0 position
//                    customerModelList.add(0, n);
//
//                    // refreshing the list
//                    customerInfoAdapter.notifyDataSetChanged();
//                }

            }
            //Getting count of data in database.
            int recordCount = (int) dbHelper.getRecordsCount();
            Log.e(TAG, "CustomerData Count******" + recordCount);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        //  Toast.makeText(mContext, "onWindowFocusChanged", Toast.LENGTH_SHORT).show();
        try {


            if (firstTime) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Log.i("count", lvMenu.getAdapter().getCount() + "");
                        lvMenu.performItemClick(
                                lvMenu.getChildAt(mActivePosition),
                                mActivePosition,
                                lvMenu.getAdapter().getItemId(mActivePosition));

                    }
                }, 5000);


                getMostUsedData();
                firstTime = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void getMostUsedData() {
        IPOSApplication.getDatabase().mostUsedFunDao().fetchAllDataWITHLIMIT().observe(MainActivity.this, new Observer<List<MostUsed>>() {
            @Override
            public void onChanged(@Nullable List<MostUsed> mostUseds) {
                mostUsedFunList.clear();
                Log.i("data", new Gson().toJson(mostUseds));
                for (MostUsed mostUsed : mostUseds) {
                    mostUsedFunList.add(mostUsed.funName);
                }
                Log.i("data", new Gson().toJson(mostUsedFunList));
            }
        });
    }

    public void insert(MostUsed word) {
        MostUsedFunDao mostUsedFunDao = IPOSApplication.getDatabase().mostUsedFunDao();
        new insertAsyncTask(mostUsedFunDao).execute(word);
    }

    public void update(String word) {
        MostUsedFunDao mostUsedFunDao = IPOSApplication.getDatabase().mostUsedFunDao();
        new updateAsyncTask(mostUsedFunDao).execute(word);
    }

    @Override
    public void onScanBarcode(String title, FragmentActivity activity) {
        newOrderScannerFragment.onScanBarcode(title, activity);
    }

    private static class insertAsyncTask extends android.os.AsyncTask<MostUsed, Void, Void> {

        private MostUsedFunDao mAsyncTaskDao;

        insertAsyncTask(MostUsedFunDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MostUsed... params) {
            long i = mAsyncTaskDao.saveTask(params[0]);
            Log.i("insert:", i + "");
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<String, Void, Void> {

        private MostUsedFunDao mAsyncTaskDao;

        updateAsyncTask(MostUsedFunDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            long i = mAsyncTaskDao.updateFunction(params[0]);
            Log.i("update", i + "");
            return null;
        }
    }
}
