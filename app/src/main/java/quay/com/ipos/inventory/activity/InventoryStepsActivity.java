/*
package quay.com.ipos.inventory.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.inventory.adapter.AddressListAdapter;
import quay.com.ipos.inventory.adapter.InventoryListAdapter;
import quay.com.ipos.inventory.adapter.ItemsDetailListAdapter;
import quay.com.ipos.inventory.adapter.WorkFLowUserAdapter;
import quay.com.ipos.inventory.modal.InventoryModel;
import quay.com.ipos.inventory.modal.UserModal;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.realmbean.RealmBusinessPlaces;
import quay.com.ipos.utility.SpacesItemDecoration;

*/
/**
 * Created by ankush.bansal on 20-04-2018.
 *//*


public class InventoryStepsActivity extends BaseActivity implements MyListener{
    String[] address = {"1/82"};
    String[] items={"PO180001","PO180002"};
    String[] user={"KGM Traders","McCoy"};

    private RecyclerView recycler_viewRecentOrders, recycleview,recylerViewRoles;
    private ItemsDetailListAdapter recentOrdersListAdapter;
    private InventoryListAdapter inventoryListAdapter;
    private ArrayList<RecentOrderModal> arrSearchList=new ArrayList<>();
    private ArrayList<InventoryModel> inventoryModels=new ArrayList<>();
    private ArrayList<RealmBusinessPlaces> addressList=new ArrayList<>();
    private ArrayList<UserModal> stringArrayListRoles=new ArrayList<>();
    private WorkFLowUserAdapter workFLowUserAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_steps_activity);

        setHeader();


        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        GridLayoutManager mLayoutManager5 = new GridLayoutManager(this, 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycleview.setLayoutManager(mLayoutManager5);
        recycleview.addItemDecoration(new SpacesItemDecoration(10));
        inventoryListAdapter = new InventoryListAdapter(this, inventoryModels);
        recycleview.setAdapter(inventoryListAdapter);

        getRecentOrdersData();

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

    private void getRecentOrdersData() {
        for (int i = 0; i < items.length; i++) {
            InventoryModel inventoryModel = new InventoryModel();
            inventoryModel.setPoNumber(items[i]);

            inventoryModels.add(inventoryModel);

        }
        inventoryListAdapter.notifyDataSetChanged();
    }


   */
/* private void getAddressData() {
        for (int i = 0; i < address.length; i++) {
            RecentOrderModal recentOrderModal = new RecentOrderModal();
            recentOrderModal.setTitle(address[i]);

            addressList.add(recentOrderModal);

        }
        addressListAdapter.notifyDataSetChanged();
    }*//*




    @Override
    public void onRowClicked(int position) {

    }

    @Override
    public void onRowClicked(int position, int value) {

    }
}
*/
