package quay.com.ipos.dashboard.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import quay.com.ipos.R;
import quay.com.ipos.dashboard.adapter.FastMovingListAdapter;
import quay.com.ipos.dashboard.adapter.InventoryListAdapter;
import quay.com.ipos.dashboard.adapter.LowInventoryListAdapter;
import quay.com.ipos.dashboard.adapter.TopStoresListAdapter;
import quay.com.ipos.dashboard.modal.LowInventoryModal;
import quay.com.ipos.enums.DashboardKeys;
import quay.com.ipos.listeners.FilterListener;
import quay.com.ipos.modal.SpinnerList;
import quay.com.ipos.utility.SelectionItemListDialog;
import quay.com.ipos.utility.SpacesItemDecoration;
import quay.com.ipos.utility.Util;


/**
 * Created by ankush.bansal on 05-04-2018.
 */

@SuppressLint("ValidFragment")
public class DashboardItemFragment extends Fragment implements FilterListener {
    String[] title = {"Nidan 4G", "Sure", "Built", "Extra Super", "Missile"};
    String[] product = {"Bavistin", "Abacin", "Snapper", "Kyoto", "Nutrozen"};
    String[] topstores = {"My store 1", "Top Store 2", "Top store 3", "Top store 4", "Top store 5"};
    String[] inventory = {"Reference # 1", "Reference # 2"};
    private RecyclerView recyclerView, recycler_view_top_stores, recycler_viewFastMoving,recycler_viewInventory;
    private LowInventoryListAdapter adapter;
    private TopStoresListAdapter topStoresListAdapter;
    private FastMovingListAdapter fastMovingListAdapter;
    private InventoryListAdapter inventoryListAdapter;
    private ArrayList<LowInventoryModal> responseList = new ArrayList<>();
    private ArrayList<LowInventoryModal> topStoresList = new ArrayList<>();
    private ArrayList<LowInventoryModal> inventoryList = new ArrayList<>();
    private ArrayList<LowInventoryModal> fastMovingList = new ArrayList<>();
    private int position;
    private String strImage;
    private TextView tvMyStore,tvOnlineValue,tvotherValue,tvSalesValue,tvTotalSalesTarget,tvTotalSalesAcheviment,tvEndSeasonAcheivement,tvEndSeasonTarget,tvEndSeasonSales
            ,tvAWAcheviment,tvAWTarget,tvAWSales,tvFootprintTotal,tvFootprintRepeated,tvFootprintNew,getTvFootprintOnline
            ,getTvFootprintLoyalty,tvFeedBackExcellent,tvFeedbackGood,tvFeedbackAverage,tvFeedbackPoor,tvFeedbackVeryPoor,tvFeedbackSpinner,tvTotalSalesSpinner,tvFootprintSpinner;
    private boolean isPopupVisible = false;
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


        findbyViewId(view);

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


        recycler_viewInventory = (RecyclerView) view.findViewById(R.id.recycler_viewInventory);
        GridLayoutManager mLayoutManager4 = new GridLayoutManager(getActivity(), 1);
        //   recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recycler_viewInventory.setLayoutManager(mLayoutManager4);
        recycler_viewInventory.addItemDecoration(new SpacesItemDecoration(10));
        inventoryListAdapter = new InventoryListAdapter(getActivity(), inventoryList);
        recycler_viewInventory.setAdapter(inventoryListAdapter);

      //  getLowInventoryData();
        parsingJson();
        getTopStoresData();
        getFastMovingData();
        getInventoryData();
        prepareSpinnerList(tvTotalSalesSpinner);
        prepareSpinnerList(tvFeedbackSpinner);
        prepareSpinnerList(tvFootprintSpinner);

        return view;

    }

    private void findbyViewId(View view){
        // pending orders
        tvMyStore=(TextView)view.findViewById(R.id.tvMyStore);
        tvOnlineValue=(TextView)view.findViewById(R.id.tvOnlineValue);
        tvotherValue=(TextView)view.findViewById(R.id.tvotherValue);

        // total sales
        tvSalesValue=(TextView)view.findViewById(R.id.tvSalesValue);
        tvTotalSalesTarget=(TextView)view.findViewById(R.id.tvTotalSalesTarget);
        tvTotalSalesAcheviment=(TextView)view.findViewById(R.id.tvTotalSalesAcheviment);

        // End of Season total sales
        tvEndSeasonSales=(TextView)view.findViewById(R.id.tvEndSeasonSales);
        tvEndSeasonTarget=(TextView)view.findViewById(R.id.tvEndSeasonTarget);
        tvEndSeasonAcheivement=(TextView)view.findViewById(R.id.tvEndSeasonAcheivement);

        // A/W Colelction total sales
        tvAWSales=(TextView)view.findViewById(R.id.tvAWSales);
        tvAWTarget=(TextView)view.findViewById(R.id.tvAWTarget);
        tvAWAcheviment=(TextView)view.findViewById(R.id.tvAWAcheviment);


        //Customer FootPrint
        tvFootprintTotal=(TextView)view.findViewById(R.id.tvFootprintTotal);
        tvFootprintRepeated=(TextView)view.findViewById(R.id.tvFootprintRepeated);
        tvFootprintNew=(TextView)view.findViewById(R.id.tvFootprintNew);
        getTvFootprintOnline=(TextView)view.findViewById(R.id.tvFootprintOnline);
        getTvFootprintLoyalty=(TextView)view.findViewById(R.id.tvFootprintLoyalty);

        //Customer Feedback
        tvFeedBackExcellent=(TextView)view.findViewById(R.id.tvFeedBackExcellent);
        tvFeedbackGood=(TextView)view.findViewById(R.id.tvFeedbackGood);
        tvFeedbackAverage=(TextView)view.findViewById(R.id.tvFeedbackAverage);
        tvFeedbackPoor=(TextView)view.findViewById(R.id.tvFeedbackPoor);
        tvFeedbackVeryPoor=(TextView)view.findViewById(R.id.tvFeedbackCVeryPoor);

        tvFeedbackSpinner=(TextView)view.findViewById(R.id.tvFeedbackSpinner);
        tvTotalSalesSpinner=(TextView)view.findViewById(R.id.tvTotalSalesSpinner);
        tvFootprintSpinner=(TextView)view.findViewById(R.id.tvFootprintSpinner);

    }

    private void parsingJson(){

        String pendingOrders=Util.getAssetJsonResponse(getActivity(),"pendingOrders");

        try {
            JSONObject jsonObject=new JSONObject(pendingOrders);
            tvMyStore.setText(jsonObject.optString(DashboardKeys.mystores.toString()));
            tvOnlineValue.setText(jsonObject.optString(DashboardKeys.onlineStores.toString()));
            tvotherValue.setText(jsonObject.optString(DashboardKeys.otherStores.toString()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String lowInventory=Util.getAssetJsonResponse(getActivity(),"lowInventory");

        try {

            JSONObject jsonObject=new JSONObject(lowInventory);
            JSONArray array=jsonObject.optJSONArray(DashboardKeys.lowInventory.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject1=array.optJSONObject(i);
                LowInventoryModal lowInventoryModal = new LowInventoryModal();
                lowInventoryModal.setTitle(jsonObject1.optString(DashboardKeys.title.toString()));

                responseList.add(lowInventoryModal);

            }
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String totalSales=Util.getAssetJsonResponse(getActivity(),"totalSales");

        try {
            JSONObject jsonObject=new JSONObject(totalSales);

            tvSalesValue.setText(getString(R.string.Rs)+" "+jsonObject.optJSONObject(DashboardKeys.totalSales.toString()).optString(DashboardKeys.totalAmount.toString()));
            tvTotalSalesTarget.setText(jsonObject.optJSONObject(DashboardKeys.totalSales.toString()).optString(DashboardKeys.target.toString()));
            tvTotalSalesAcheviment.setText(jsonObject.optJSONObject(DashboardKeys.totalSales.toString()).optString(DashboardKeys.achievement.toString()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        String schemePerformance=Util.getAssetJsonResponse(getActivity(),"schemePerformance.json");

        try {
            JSONObject jsonObject=new JSONObject(schemePerformance);

            tvEndSeasonSales.setText(getString(R.string.Rs)+" "+jsonObject.optJSONObject(DashboardKeys.endofSeason.toString()).optString(DashboardKeys.totalAmount.toString()));
            tvEndSeasonTarget.setText(jsonObject.optJSONObject(DashboardKeys.endofSeason.toString()).optString(DashboardKeys.target.toString()));
            tvEndSeasonAcheivement.setText(jsonObject.optJSONObject(DashboardKeys.endofSeason.toString()).optString(DashboardKeys.achievement.toString()));

            tvAWSales.setText(getString(R.string.Rs)+" "+jsonObject.optJSONObject(DashboardKeys.awCollection.toString()).optString(DashboardKeys.totalAmount.toString()));
            tvAWTarget.setText(jsonObject.optJSONObject(DashboardKeys.awCollection.toString()).optString(DashboardKeys.target.toString()));
            tvAWAcheviment.setText(jsonObject.optJSONObject(DashboardKeys.awCollection.toString()).optString(DashboardKeys.achievement.toString()));

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }
    private void getInventoryData() {

        for (int i = 0; i < inventory.length; i++) {
            LowInventoryModal lowInventoryModal = new LowInventoryModal();
            lowInventoryModal.setTitle(inventory[i]);

            inventoryList.add(lowInventoryModal);

        }
        inventoryListAdapter.notifyDataSetChanged();
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


    @Override
    public void onUpdateTitle(String title) {
        System.out.println("DIALOG CALLL");
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


                    SpinnerList  spn ;
                    spnPanchyatList = new ArrayList<>();
                    spn = new SpinnerList();
                    spn.setName("Day");
                    spn.setId((0)+"");
                    spnPanchyatList.add(spn);
                    spn = new SpinnerList();
                    spn.setName("Month");
                    spn.setId((1)+"");
                    spnPanchyatList.add(spn);
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
