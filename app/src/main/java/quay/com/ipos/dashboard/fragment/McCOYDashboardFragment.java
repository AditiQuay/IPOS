package quay.com.ipos.dashboard.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;



import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import quay.com.ipos.R;
import quay.com.ipos.dashboard.activity.UpcomingShipmentActivity;
import quay.com.ipos.dashboard.adapter.BarGraphAdapter;
import quay.com.ipos.dashboard.adapter.FastMovingListAdapter;
import quay.com.ipos.dashboard.adapter.InventoryListAdapter;
import quay.com.ipos.dashboard.adapter.LowInventoryListAdapter;
import quay.com.ipos.dashboard.adapter.TopProductsListAdapter;
import quay.com.ipos.dashboard.adapter.TopStoresListAdapter;
import quay.com.ipos.dashboard.adapter.UpcomingShipmentListAdapter;
import quay.com.ipos.dashboard.modal.BarGraphModal;
import quay.com.ipos.dashboard.modal.LowInventoryModal;
import quay.com.ipos.modal.SpinnerList;
import quay.com.ipos.utility.SelectionItemListDialog;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;


/**
 * Created by ankush.bansal on 05-04-2018.
 */

@SuppressLint("ValidFragment")
public class McCOYDashboardFragment extends Fragment {
    String[] days={"Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec","Jan","Feb","Mar"};

    int[] progress={10,20,30,40,50,40,30,20,10,80,60,50};
    String[] money={"42,660","32,634","37,828","32,634","38,635","39,984","37,634","50,627","39,631","41,630","22,132","31,148"};
    String[] title = {"Nidan 4G", "Sure", "Built", "Extra Super", "Missile"};
    String[] product = {"Bavistin", "Abacin", "Snapper", "Kyoto", "Nutrozen"};
    String[] topstores = {"My store 1", "Top Store 2", "Top store 3", "Top store 4", "Top store 5"};
    String[] inventory = {"Order Reference # 1", "Order Reference # 2"};
    private RecyclerView recyclerView, recycler_view_top_stores, recycler_viewOrderValue,recycler_viewInventory,recycler_viewBarGraph;
    private LowInventoryListAdapter adapter;
    private TopStoresListAdapter topStoresListAdapter;
    private TopProductsListAdapter topProductsListAdapter;
    private UpcomingShipmentListAdapter inventoryListAdapter;
    private ArrayList<LowInventoryModal> responseList = new ArrayList<>();
    private ArrayList<LowInventoryModal> topStoresList = new ArrayList<>();
    private ArrayList<LowInventoryModal> inventoryList = new ArrayList<>();
    private ArrayList<LowInventoryModal> fastMovingList = new ArrayList<>();
    private ArrayList<BarGraphModal> barGraphModals=new ArrayList<>();
    private int position;
    private String strImage;
    private BarGraphAdapter barGraphAdapter;
    private boolean isPopupVisible = false;

    // newInstance constructor for creating fragment with arguments
    public McCOYDashboardFragment newInstance(int position) {
        McCOYDashboardFragment fragmentFirst = new McCOYDashboardFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mccoy_dashboard_item, container, false);

        recycler_viewBarGraph = (RecyclerView) view.findViewById(R.id.recycler_viewBarGraph);
        GridLayoutManager mLayoutManager3 = new GridLayoutManager(getActivity(), 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewBarGraph.setLayoutManager(mLayoutManager3);
        recycler_viewBarGraph.addItemDecoration(new SpacesItemDecoration(0));
        barGraphAdapter = new BarGraphAdapter(getActivity(), barGraphModals);
        recycler_viewBarGraph.setAdapter(barGraphAdapter);


        recycler_viewOrderValue = (RecyclerView) view.findViewById(R.id.recycler_viewOrderValue);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(getActivity(), 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewOrderValue.setLayoutManager(mLayoutManager2);
        recycler_viewOrderValue.addItemDecoration(new SpacesItemDecoration(10));
        topProductsListAdapter = new TopProductsListAdapter(getActivity(), fastMovingList);
        recycler_viewOrderValue.setAdapter(topProductsListAdapter);


        recycler_viewInventory = (RecyclerView) view.findViewById(R.id.recycler_viewInventory);
        GridLayoutManager mLayoutManager4 = new GridLayoutManager(getActivity(), 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewInventory.setLayoutManager(mLayoutManager4);
        recycler_viewInventory.addItemDecoration(new SpacesItemDecoration(10));
        inventoryListAdapter = new UpcomingShipmentListAdapter(getActivity(), inventoryList);
        recycler_viewInventory.setAdapter(inventoryListAdapter);

        Spinner spinner = (Spinner) view.findViewById(R.id.spnCardType);
        TextView tvMonth=(TextView)view.findViewById(R.id.tvMonth);
        final View viewMonth=(View)view.findViewById(R.id.viewMonth);
        TextView tvYTD=(TextView)view.findViewById(R.id.tvYTD);
        final View viewYear=(View)view.findViewById(R.id.viewYear);
        TextView tvOrderMonth=(TextView)view.findViewById(R.id.tvOrderMonth);
        TextView tvTop=(TextView)view.findViewById(R.id.tvTop);
        LinearLayout llViewAll=(LinearLayout)view.findViewById(R.id.llViewAll);
        llViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), UpcomingShipmentActivity.class);
                startActivity(i);
            }
        });

        tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMonth.setVisibility(View.VISIBLE);
                viewYear.setVisibility(View.GONE);
            }
        });
        tvYTD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewMonth.setVisibility(View.GONE);
                viewYear.setVisibility(View.VISIBLE);
            }
        });

        getFastMovingData();
        getInventoryData();
      //  createOrderChart(view);
        getBarGraph();
        prepareSpinnerList(tvOrderMonth);
        prepareSpinnerList(tvTop);
        return view;

    }
    private void getInventoryData() {
        for (int i = 0; i < inventory.length; i++) {
            LowInventoryModal lowInventoryModal = new LowInventoryModal();
            lowInventoryModal.setTitle(inventory[i]);

            inventoryList.add(lowInventoryModal);

        }
        inventoryListAdapter.notifyDataSetChanged();
    }
    private void getBarGraph() {
        for (int i = 0; i < days.length; i++) {
            BarGraphModal barGraphModal = new BarGraphModal();
            barGraphModal.setTitle(days[i]);
            barGraphModal.setProgress(progress[i]);
            barGraphModal.setMoney(money[i]);

            barGraphModals.add(barGraphModal);

        }
        barGraphAdapter.notifyDataSetChanged();
    }



    private void getFastMovingData() {
        for (int i = 0; i < product.length; i++) {
            LowInventoryModal lowInventoryModal = new LowInventoryModal();
            lowInventoryModal.setTitle(product[i]);

            fastMovingList.add(lowInventoryModal);

        }
        topProductsListAdapter.notifyDataSetChanged();
    }



    private void prepareSpinnerList(final TextView textView) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPopupVisible) return;
                isPopupVisible = true;
                List<SpinnerList> spnPanchyatList = null;
                final Realm realm = Realm.getDefaultInstance();
                try {

                if (textView.getId()==R.id.tvOrderMonth) {
                    SpinnerList spn;
                    spnPanchyatList = new ArrayList<>();
                    spn = new SpinnerList();
                    spn.setName("Day");
                    spn.setId((0) + "");
                    spnPanchyatList.add(spn);
                    spn = new SpinnerList();
                    spn.setName("Month");
                    spn.setId((1) + "");
                    spnPanchyatList.add(spn);
                }else {
                    SpinnerList spn;
                    spnPanchyatList = new ArrayList<>();
                    spn = new SpinnerList();
                    spn.setName("Top 5");
                    spn.setId((0) + "");
                    spnPanchyatList.add(spn);
                    spn = new SpinnerList();
                    spn.setName("Top 10");
                    spn.setId((1) + "");
                    spnPanchyatList.add(spn);
                    spn = new SpinnerList();
                    spn.setName("Top 20");
                    spn.setId((1) + "");
                    spnPanchyatList.add(spn);
                    spn = new SpinnerList();
                    spn.setName("Top 50");
                    spn.setId((1) + "");
                    spnPanchyatList.add(spn);
                }
                } catch (Exception e) {
                    e.printStackTrace();
                    realm.close();
                } finally {
                    realm.close();
                }
                showSelectionListPanchyat(getActivity(), textView, spnPanchyatList, getString(R.string.filter));
            }
        });
    }

    private void showSelectionListPanchyat(Context context, final TextView textView, List<SpinnerList> list, final String defaultMsg) {
        if (list != null && list.size() > 0) {
            SelectionItemListDialog selectionPickerDialog = new SelectionItemListDialog(context, defaultMsg, textView.getText().toString().trim(), list, R.layout.pop_up_question_list, new SelectionItemListDialog.ItemPickerListner() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void OnDoneButton(Dialog ansPopup, String strAns, SpinnerList spinnerItem) {
                    ansPopup.dismiss();
                    if (Util.validateString(strAns)) {
                        textView.setText(strAns);
                        textView.setTag(spinnerItem);
                    } else {
                        textView.setText(defaultMsg);
                    }

                }
            });
            if (!selectionPickerDialog.isShowing()) {
                selectionPickerDialog.show();
            }
            selectionPickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    isPopupVisible = false;
                }
            });
        } else {
            isPopupVisible = false;
            //   showToastMessage(getString(R.string.no_data));
        }
    }
}
