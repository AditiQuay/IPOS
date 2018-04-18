package quay.com.ipos.dashboard.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.dashboard.adapter.FastMovingListAdapter;
import quay.com.ipos.dashboard.adapter.LowInventoryListAdapter;
import quay.com.ipos.dashboard.adapter.TopStoresListAdapter;
import quay.com.ipos.dashboard.modal.LowInventoryModal;
import quay.com.ipos.utility.SpacesItemDecoration;


/**
 * Created by ankush.bansal on 05-04-2018.
 */

@SuppressLint("ValidFragment")
public class DashboardItemFragment extends Fragment {
    String[] title = {"Nidan 4G", "Sure", "Built", "Extra Super", "Missile"};
    String[] product = {"Bavistin", "Abacin", "Snapper", "Kyoto", "Nutrozen"};
    String[] topstores = {"My store 1", "Top Store 2", "Top store 3", "Top store 4", "Top store 5"};
    private RecyclerView recyclerView, recycler_view_top_stores, recycler_viewFastMoving;
    private LowInventoryListAdapter adapter;
    private TopStoresListAdapter topStoresListAdapter;
    private FastMovingListAdapter fastMovingListAdapter;
    private ArrayList<LowInventoryModal> responseList = new ArrayList<>();
    private ArrayList<LowInventoryModal> topStoresList = new ArrayList<>();
    private ArrayList<LowInventoryModal> fastMovingList = new ArrayList<>();
    private int position;
    private String strImage;

    // newInstance constructor for creating fragment with arguments
    public DashboardItemFragment newInstance(int position) {
        DashboardItemFragment fragmentFirst = new DashboardItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard_item, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10));
        adapter = new LowInventoryListAdapter(getActivity(), responseList);
        recyclerView.setAdapter(adapter);


        recycler_view_top_stores = (RecyclerView) view.findViewById(R.id.recycler_view_top_stores);
        GridLayoutManager mLayoutManager1 = new GridLayoutManager(getActivity(), 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_view_top_stores.setLayoutManager(mLayoutManager1);
        recycler_view_top_stores.addItemDecoration(new SpacesItemDecoration(10));
        topStoresListAdapter = new TopStoresListAdapter(getActivity(), topStoresList);
        recycler_view_top_stores.setAdapter(topStoresListAdapter);

        recycler_viewFastMoving = (RecyclerView) view.findViewById(R.id.recycler_viewFastMoving);
        GridLayoutManager mLayoutManager2 = new GridLayoutManager(getActivity(), 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewFastMoving.setLayoutManager(mLayoutManager2);
        recycler_viewFastMoving.addItemDecoration(new SpacesItemDecoration(10));
        fastMovingListAdapter = new FastMovingListAdapter(getActivity(), fastMovingList);
        recycler_viewFastMoving.setAdapter(fastMovingListAdapter);


        getLowInventoryData();

        getTopStoresData();
        getFastMovingData();
        return view;

    }

    private void getLowInventoryData() {
        for (int i = 0; i < title.length; i++) {
            LowInventoryModal lowInventoryModal = new LowInventoryModal();
            lowInventoryModal.setTitle(title[i]);

            responseList.add(lowInventoryModal);

        }
        adapter.notifyDataSetChanged();
    }

    private void getTopStoresData() {
        for (int i = 0; i < topstores.length; i++) {
            LowInventoryModal lowInventoryModal = new LowInventoryModal();
            lowInventoryModal.setTitle(topstores[i]);

            topStoresList.add(lowInventoryModal);

        }
        topStoresListAdapter.notifyDataSetChanged();
    }

    private void getFastMovingData() {
        for (int i = 0; i < product.length; i++) {
            LowInventoryModal lowInventoryModal = new LowInventoryModal();
            lowInventoryModal.setTitle(product[i]);

            fastMovingList.add(lowInventoryModal);

        }
        fastMovingListAdapter.notifyDataSetChanged();
    }


}
