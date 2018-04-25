package quay.com.ipos.neworder.fragment;

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

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.dashboard.adapter.LowInventoryListAdapter;
import quay.com.ipos.dashboard.adapter.TopProductsListAdapter;
import quay.com.ipos.dashboard.adapter.TopStoresListAdapter;
import quay.com.ipos.dashboard.adapter.UpcomingShipmentListAdapter;
import quay.com.ipos.dashboard.modal.LowInventoryModal;
import quay.com.ipos.neworder.adapter.NewOrderListAdapter;
import quay.com.ipos.utility.SpacesItemDecoration;


/**
 * Created by ankush.bansal on 05-04-2018.
 */

@SuppressLint("ValidFragment")
public class OrderCentreFragment extends Fragment {

    String[] inventory = {"KGM Traders", "KGM Traders"};
    private RecyclerView recyclerView;

    private NewOrderListAdapter newOrderListAdapter;
    private ArrayList<LowInventoryModal> responseList = new ArrayList<>();


    // newInstance constructor for creating fragment with arguments
    public OrderCentreFragment newInstance(int position) {
        OrderCentreFragment fragmentFirst = new OrderCentreFragment();
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
        View view = inflater.inflate(R.layout.fragment_order_centre, container, false);



        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(getActivity(), 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(mLayoutManager2);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        newOrderListAdapter = new NewOrderListAdapter(getActivity(), responseList);
        recyclerView.setAdapter(newOrderListAdapter);



        getInventoryData();

        return view;

    }
    private void getInventoryData() {
        for (int i = 0; i < inventory.length; i++) {
            LowInventoryModal lowInventoryModal = new LowInventoryModal();
            lowInventoryModal.setTitle(inventory[i]);

            responseList.add(lowInventoryModal);

        }
        newOrderListAdapter.notifyDataSetChanged();
    }





}
