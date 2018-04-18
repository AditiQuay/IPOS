package quay.com.ipos.base;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.adapter.DrawerItemCustomAdapter;
import quay.com.ipos.adapter.NavigationViewExpeListViewAdapter;
import quay.com.ipos.dashboard.fragment.DashboardFragment;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.DrawerModal;
import quay.com.ipos.productCatalogue.ProductCatalogueMainFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, InitInterface {
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        applyInitValues();


    }

    @Override
    public void findViewById() {
        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        containerId = R.id.fragment_container;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        listViewContent = findViewById(R.id.listViewContent);

        expandableListView1 = findViewById(R.id.expandableListView1);
    }

    @Override
    public void applyInitValues() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        DrawerModal[] drawerItem = new DrawerModal[5];

        drawerItem[0] = new DrawerModal(R.drawable.ic_action_dashboard, "Dashboard & Insight");
        drawerItem[1] = new DrawerModal(R.drawable.ic_action_order_centre, "Product Catalogue");
        drawerItem[2] = new DrawerModal(R.drawable.ic_action_cart, "Stock & Price");
        drawerItem[3] = new DrawerModal(R.drawable.ic_action_loyalty, "Loyalty Program");
        drawerItem[4] = new DrawerModal(R.drawable.ic_action_cart, "Partner Connect");

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.drawer_list_items, drawerItem);
        listViewContent.setAdapter(adapter);

        listViewContent.setOnItemClickListener(new DrawerItemClickListener());

//        expandableListDetail = ExpandableListDataPump.getData();
//        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
//        navigationViewExpeListViewAdapter = new NavigationViewExpeListViewAdapter(this, expandableListTitle, expandableListDetail);
//        expandableListView1.setAdapter(navigationViewExpeListViewAdapter);
//        expandableListView1.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                Toast.makeText(mContext,"Group clicked",Toast.LENGTH_SHORT).show();
//
//                return false;
//            }
//        });
//        expandableListView1.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (lastExpandedGroup != groupPosition) {
//                    expandableListView1.collapseGroup(lastExpandedGroup);
//                }
///*
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();*/
//                lastExpandedGroup = groupPosition;
//            }
//        });
//        expandableListView1.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                String mainMenu = expandableListTitle.get(groupPosition).toString();
//                String subMenu = expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition).toString();
//                Toast.makeText(getApplicationContext(), mainMenu + " -> " + subMenu, Toast.LENGTH_SHORT).show();
//
//                return false;
//            }
//        });
    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        switch (position) {
            case 0:
                addFragment(new DashboardFragment(),containerId);
                Toast.makeText(mContext, "Position 1 clicked", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case 1:
                addFragment(new ProductCatalogueMainFragment(), containerId);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case 2:
                Toast.makeText(mContext, "Position 3 clicked", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case 3:
                Toast.makeText(mContext, "Position 4 clicked", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case 4:
                Toast.makeText(mContext, "Position 5 clicked", Toast.LENGTH_SHORT).show();
                drawer.closeDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
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
}
