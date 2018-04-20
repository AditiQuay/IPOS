package quay.com.ipos.dashboard.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONObject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.dashboard.adapter.FastMovingListAdapter;
import quay.com.ipos.dashboard.adapter.InventoryListAdapter;
import quay.com.ipos.dashboard.adapter.LowInventoryListAdapter;
import quay.com.ipos.dashboard.adapter.TopProductsListAdapter;
import quay.com.ipos.dashboard.adapter.TopStoresListAdapter;
import quay.com.ipos.dashboard.adapter.UpcomingShipmentListAdapter;
import quay.com.ipos.dashboard.modal.LowInventoryModal;
import quay.com.ipos.utility.SpacesItemDecoration;


/**
 * Created by ankush.bansal on 05-04-2018.
 */

@SuppressLint("ValidFragment")
public class McCOYDashboardFragment extends Fragment {
    String[] title = {"Nidan 4G", "Sure", "Built", "Extra Super", "Missile"};
    String[] product = {"Bavistin", "Abacin", "Snapper", "Kyoto", "Nutrozen"};
    String[] topstores = {"My store 1", "Top Store 2", "Top store 3", "Top store 4", "Top store 5"};
    String[] inventory = {"Order Reference # 1", "Order Reference # 2"};
    private RecyclerView recyclerView, recycler_view_top_stores, recycler_viewOrderValue,recycler_viewInventory;
    private LowInventoryListAdapter adapter;
    private TopStoresListAdapter topStoresListAdapter;
    private TopProductsListAdapter topProductsListAdapter;
    private UpcomingShipmentListAdapter inventoryListAdapter;
    private ArrayList<LowInventoryModal> responseList = new ArrayList<>();
    private ArrayList<LowInventoryModal> topStoresList = new ArrayList<>();
    private ArrayList<LowInventoryModal> inventoryList = new ArrayList<>();
    private ArrayList<LowInventoryModal> fastMovingList = new ArrayList<>();
    private int position;
    private String strImage;

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

        getFastMovingData();
        getInventoryData();
        createOrderChart(view);
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



    private void getFastMovingData() {
        for (int i = 0; i < product.length; i++) {
            LowInventoryModal lowInventoryModal = new LowInventoryModal();
            lowInventoryModal.setTitle(product[i]);

            fastMovingList.add(lowInventoryModal);

        }
        topProductsListAdapter.notifyDataSetChanged();
    }

    private void createOrderChart(final View view) {
        final BarChart barChart = (BarChart) view.findViewById(R.id.ordervalue);
//  mChart.setDrawValueAboveBar(false);
        barChart.setHighlightFullBarEnabled(false);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter());
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        barChart.getAxisRight().setEnabled(false);
        String[] l = getResources().getStringArray(R.array.ordervalue);
        XAxis xLabels = barChart.getXAxis();
        xLabels.setDrawLabels(true);
        xLabels.setLabelCount(l.length);
        xLabels.setValueFormatter(new IndexAxisValueFormatter(l));
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        ArrayList<BarEntry> bargroup3 = new ArrayList<>();
        ArrayList<BarEntry> bargroup4 = new ArrayList<>();


        bargroup1.add(new BarEntry(0, (float) 5));
        bargroup1.add(new BarEntry(1, (float) 4));
        bargroup1.add(new BarEntry(2, (float) 3));

        bargroup2.add(new BarEntry(0, (float) 5));
        bargroup2.add(new BarEntry(1, (float) 4));
        bargroup2.add(new BarEntry(2, (float) 3));

        bargroup3.add(new BarEntry(0, (float) 6));
        bargroup3.add(new BarEntry(1, (float) 4));
        bargroup3.add(new BarEntry(2, (float) 2));
        bargroup4.add(new BarEntry(0, (float) 6));
        bargroup4.add(new BarEntry(1, (float) 4));
        bargroup4.add(new BarEntry(2, (float) 2));

        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Apr");
        barDataSet1.setColor(Color.rgb(76, 152, 207));

        BarDataSet barDataSet2 = new BarDataSet(bargroup2, "May");
        barDataSet2.setColor(Color.rgb(76, 152, 207));

        BarDataSet barDataSet3 = new BarDataSet(bargroup3, "Jun");
        barDataSet3.setColor(Color.rgb(76, 152, 207));

        BarDataSet barDataSet4 = new BarDataSet(bargroup4, "Jul");
        barDataSet4.setColor(Color.rgb(76, 152, 207));

        barDataSet1.setStackLabels(l);
        barDataSet2.setStackLabels(l);
        barDataSet3.setStackLabels(l);
        barDataSet4.setStackLabels(l);

        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);
        dataSets.add(barDataSet4);
        BarData data = new BarData(dataSets);
        data.setValueTextColor(Color.WHITE);

        barChart.setData(data);
        barChart.setFitBars(true);

        barChart.getDescription().setEnabled(false);
        barChart.invalidate();


    }

    class MyAxisValueFormatter implements IAxisValueFormatter {

        private DecimalFormat mFormat;

        /**
         * Instantiates a new My axis value formatter.
         */
        public MyAxisValueFormatter() {
            mFormat = new DecimalFormat("###,###,##"); // use one decimal
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            NumberFormat format = DecimalFormat.getInstance();
            format.setRoundingMode(RoundingMode
                    .FLOOR);
            format.setMinimumFractionDigits(0);
            format.setMaximumFractionDigits(2);
            return mFormat.format(value);
//            return mFormat.format(value); // e.g. append a dollar-sign
        }
    }


    public class LabelFormatter implements IAxisValueFormatter {
        private List<String> lableList;

        public LabelFormatter(List<String> labels) {
            lableList = labels;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return lableList.get((int) value);
        }
    }

}
