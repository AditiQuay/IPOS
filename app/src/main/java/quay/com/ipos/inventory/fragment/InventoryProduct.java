package quay.com.ipos.inventory.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.BatchTabAdapter;
import quay.com.ipos.inventory.adapter.InventoryProdcutDetailAdapter;
import quay.com.ipos.inventory.modal.ActionListModel;
import quay.com.ipos.inventory.modal.GRNProductDetailModel;
import quay.com.ipos.inventory.modal.OthersTabList;
import quay.com.ipos.inventory.modal.RealmInventoryTabData;
import quay.com.ipos.listeners.EdittClickListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.listeners.TabListenerr;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmGRNDetails;

/**
 * Created by niraj.kumar on 6/12/2018.
 */

public class InventoryProduct extends AppCompatActivity implements InitInterface, View.OnClickListener, EdittClickListener, MyListener, ListDialogFragment.DialogListener, ActionListFragment.ActionListener, TabListenerr {
    private static final String TAG = InventoryProduct.class.getSimpleName();
    private Button btnSave, btnAction, btnAddBatch, btnOthers;
    private RecyclerView recyclerviewBatch, recyclerviewButton;
    private EditText batchEditText;
    private SwitchCompat switchBatchButton;
    private RelativeLayout rLayoutBatchNumber;
    private TextView textViewProductName, textViewProductBalance, textViewProductQuantity;
    private Toolbar toolbar;
    private ImageView imgArrowLeft, imgArrowRight, imvBarcode;
    private Context mContext;
    private LinearLayoutManager layoutManager;
    private List<GRNProductDetailModel> dataNormalList = new ArrayList<>();
    private List<GRNProductDetailModel> dataDefectList = new ArrayList<>();

    //private List<BatchListData> batchListData = new ArrayList<>();
    private List<OthersTabList> otherTabList = new ArrayList<>();
    private List<ActionListModel> actionListModels = new ArrayList<>();

    private int pos;
    private InventoryProdcutDetailAdapter inventoryProdcutDetailAdapter;
    boolean isDefect;
    private BatchTabAdapter batchTabAdapter;
    private Dialog myDialog;


    private List<RealmInventoryTabData> tabData = new ArrayList<>();
    private RealmInventoryTabData selectedtabData;
    private List<GRNProductDetailModel> filterModelList = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // tabData.get(0).modelList.addAll(filterModelList);

        setContentView(R.layout.inventory_product_details);

        mContext = InventoryProduct.this;
        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Intent i = getIntent();
        pos = i.getIntExtra("position", pos);

        findViewById();
        applyInitValues();
        applyLocalValidation();
        applyTypeFace();
    }

    @Override
    public void findViewById() {
        toolbar = findViewById(R.id.toolbar);
        textViewProductName = findViewById(R.id.textViewProductName);

        //Button Id's
        btnSave = findViewById(R.id.btnSave);
        btnAction = findViewById(R.id.btnAction);
        btnAddBatch = findViewById(R.id.btnAddBatch);


        recyclerviewBatch = findViewById(R.id.recyclerviewBatch);
        recyclerviewButton = findViewById(R.id.recyclerviewButton);
        batchEditText = findViewById(R.id.batchEditText);
        imvBarcode = findViewById(R.id.imvBarcode);
        switchBatchButton = findViewById(R.id.switchBatchButton);
        rLayoutBatchNumber = findViewById(R.id.rLayoutBatchNumber);
        textViewProductQuantity = findViewById(R.id.textViewProductQuantity);
        textViewProductBalance = findViewById(R.id.textViewProductBalance);
        imgArrowLeft = findViewById(R.id.imgArrowLeft);
        imgArrowRight = findViewById(R.id.imgArrowRight);

        btnSave.setOnClickListener(this);
        btnAction.setOnClickListener(this);
        btnAddBatch.setOnClickListener(this);

        imgArrowRight.setOnClickListener(this);
        imgArrowLeft.setOnClickListener(this);


    }

    @Override
    public void applyInitValues() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setTitle("INVENTORY");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        setBatchTab(pos);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerviewButton.setLayoutManager(horizontalLayoutManager);
        batchTabAdapter = new BatchTabAdapter(mContext, tabData, this, this);
        recyclerviewButton.setAdapter(batchTabAdapter);


        getBatchList(pos);


    }

    private void setBatchTab(int position) {
        Realm realm = Realm.getDefaultInstance();
        RealmGRNDetails realmGRNDetails = realm.where(RealmGRNDetails.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmGRNDetails));
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = new JSONArray(jsonObject.optString("poItemDetails").replaceAll("\\\\", ""));
            JSONObject jsonObject2 = array.getJSONObject(position);
            textViewProductName.setText(jsonObject2.optString("materialName"));
            textViewProductQuantity.setText(jsonObject2.optString("openQty"));
            textViewProductBalance.setText("Balance" + "" + jsonObject2.optString("balanceQty"));


            tabData.clear();
            JSONObject jsonObject3 = jsonObject2.getJSONObject("gRNItemInfoDetails");
            JSONArray jsonArray = jsonObject3.getJSONArray("tabList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                RealmInventoryTabData batchList = new RealmInventoryTabData();
                batchList.setTabId(jsonObject1.optInt("tabId"));
                batchList.setTabTitle(jsonObject1.optString("tabTitle"));
                batchList.modelList = new ArrayList<>();
                tabData.add(batchList);
            }
            batchTabAdapter.notifyDataSetChanged();
            getBatchList(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //onAdd others
    void onAddOthers(int id, String title) {
        RealmInventoryTabData batchList = new RealmInventoryTabData();
        batchList.setTabId(id);
        batchList.setTabTitle(title);
        batchList.modelList = new ArrayList<>();
        tabData.add(batchList);
        batchTabAdapter.notifyDataSetChanged();
    }

    private void setOthersTab() {
        otherTabList.clear();
        Realm realm = Realm.getDefaultInstance();
        RealmGRNDetails realmGRNDetails = realm.where(RealmGRNDetails.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmGRNDetails));
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = new JSONArray(jsonObject.optString("poItemDetails").replaceAll("\\\\", ""));
            JSONObject jsonObject2 = array.getJSONObject(pos);

            JSONObject jsonObject3 = jsonObject2.getJSONObject("gRNItemInfoDetails");
            JSONArray jsonArray = jsonObject3.getJSONArray("tabList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                if (jsonObject1.optString("tabTitle").equalsIgnoreCase("Others")) {
                    JSONArray jsonArray1 = jsonObject1.optJSONArray("tabRelationList");
                    for (int j = 0; j < jsonArray1.length(); j++) {
                        JSONObject jsonObject11 = jsonArray1.optJSONObject(j);
                        OthersTabList othersTabList = new OthersTabList();
                        othersTabList.tabId = jsonObject11.optInt("tabId");
                        othersTabList.tabTitle = jsonObject11.optString("tabTitle");
                        otherTabList.add(othersTabList);

                    }
                }

            }

            showListDialog(otherTabList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBatchList(int pos) {
        Realm realm = Realm.getDefaultInstance();
        RealmGRNDetails realmGRNDetails = realm.where(RealmGRNDetails.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmGRNDetails));
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = new JSONArray(jsonObject.optString("poItemDetails").replaceAll("\\\\", ""));
            JSONObject jsonObject2 = array.getJSONObject(pos);
            JSONObject jsonObject3 = jsonObject2.getJSONObject("gRNItemInfoDetails");
            JSONArray jsonArray = jsonObject3.optJSONArray("data");
            new RealmController().saveInventoryTabDetails(jsonArray.toString());


            setRealmData(1);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void setRealmData(int key) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<RealmInventoryTabData> realmInventoryTabData = realm.where(RealmInventoryTabData.class).findAll();
        if (realmInventoryTabData.size() > 0) {
            for (RealmInventoryTabData realmInventoryTabData1 : realmInventoryTabData) {

                if (key == realmInventoryTabData1.getTabId()) {
                    realmInventoryTabData1.modelList = new ArrayList<>();
                    JSONArray array1 = null;
                    try {
                        array1 = new JSONArray(realmInventoryTabData1.getModel());
                        for (int k = 0; k < array1.length(); k++) {
                            JSONObject jsonObject = array1.optJSONObject(k);
                            GRNProductDetailModel grnProductDetailModel = new GRNProductDetailModel();
                            grnProductDetailModel.setNumber(jsonObject.optString("number"));
                            grnProductDetailModel.setActionTitle(jsonObject.optString("actionTitle"));
                            grnProductDetailModel.setActionID(jsonObject.optInt("actionID"));
                            grnProductDetailModel.setQty(jsonObject.getInt("qty"));
                            realmInventoryTabData1.modelList.add(grnProductDetailModel);
                            for (RealmInventoryTabData model : tabData) {
                                if (model.getTabId() == key) {
                                    if (!model.flag)
                                        model.modelList.add(grnProductDetailModel);
                                    model.flag = true;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }


            for (RealmInventoryTabData model : tabData) {
                if (model.getTabId() == key) {

                    selectedtabData = model;
                    recyclerviewBatch.setHasFixedSize(true);
                    recyclerviewBatch.setLayoutManager(new LinearLayoutManager(mContext));
                    inventoryProdcutDetailAdapter = new InventoryProdcutDetailAdapter(mContext, model.modelList, this);
                    recyclerviewBatch.setAdapter(inventoryProdcutDetailAdapter);
                }
            }
        }


    }

    @Override
    public void applyTypeFace() {

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgArrowRight:
                int count = pos + 1;
                setBatchTab(count);
                break;
            case R.id.imgArrowLeft:
                if (pos == 0) {
                    setBatchTab(pos);
                } else {
                    int firstPosition = pos;
                    int secondPosition = firstPosition - 1;
                    setBatchTab(secondPosition);
                }
                break;
            case R.id.btnSave:
                updateData();
                break;
            case R.id.btnAction:
                getActionList();

                break;
            case R.id.btnAddBatch:
                saveBatchData();
            default:
                break;

        }
    }

    private void getActionList() {
        actionListModels.clear();
        Realm realm = Realm.getDefaultInstance();
        RealmGRNDetails realmGRNDetails = realm.where(RealmGRNDetails.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmGRNDetails));
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = new JSONArray(jsonObject.optString("poItemDetails").replaceAll("\\\\", ""));
            JSONObject jsonObject2 = array.getJSONObject(pos);

            JSONObject jsonObject3 = jsonObject2.getJSONObject("gRNItemInfoDetails");
            JSONArray jsonArray = jsonObject3.getJSONArray("actionList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                ActionListModel actionListModel = new ActionListModel();
                actionListModel.actionID = jsonObject1.optInt("actionID");
                actionListModel.actionTitle = jsonObject1.optString("actionTitle");
                actionListModels.add(actionListModel);
            }
            showActionListDialog(actionListModels);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setChangeAction(int key, boolean isMove, boolean isDelete, String remark) {
        filterModelList = new ArrayList<>();
        for (GRNProductDetailModel grnProductDetailModel : selectedtabData.modelList) {
             if (grnProductDetailModel.isSelected) {
                  grnProductDetailModel.remark = remark;
                  filterModelList.add(grnProductDetailModel);
              }
        }
        //set new
        if (!isDelete) {
            for (RealmInventoryTabData model : tabData) {
                if (model.getTabId() == key) {
                    model.modelList.addAll(filterModelList);
                    // selectedtabData = model;
                }
            }
        }
        if (isMove || isDelete) {

            selectedtabData.modelList.removeAll(filterModelList);
        }
        inventoryProdcutDetailAdapter = new InventoryProdcutDetailAdapter(mContext, selectedtabData.modelList, this);
        recyclerviewBatch.setAdapter(inventoryProdcutDetailAdapter);


    }

    private void saveBatchData() {
        GRNProductDetailModel grnProductDetailModel = new GRNProductDetailModel();
        grnProductDetailModel.setNumber(batchEditText.getText().toString());
        grnProductDetailModel.setActionTitle(selectedtabData.getTabTitle());
        grnProductDetailModel.setActionID(selectedtabData.getTabId());
        grnProductDetailModel.setQty(0);
        selectedtabData.modelList.add(grnProductDetailModel);
//        batchTabAdapter.notifyDataSetChanged();
        inventoryProdcutDetailAdapter = new InventoryProdcutDetailAdapter(mContext, selectedtabData.modelList, this);
        recyclerviewBatch.setAdapter(inventoryProdcutDetailAdapter);

    }

    private void updateData() {
//        dataNormalList.addAll(dataDefectList);
        Realm realm = Realm.getDefaultInstance();
        RealmGRNDetails realmGRNDetails = realm.where(RealmGRNDetails.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmGRNDetails));
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = new JSONArray(jsonObject.optString("poItemDetails").replaceAll("\\\\", ""));
            JSONArray arrayPoAttachment = new JSONArray(jsonObject.optString("poAttachments").replaceAll("\\\\", ""));
            JSONArray arrayPoIncco = new JSONArray(jsonObject.optString("poIncoTerms").replaceAll("\\\\", ""));
            JSONObject jsonObject2 = array.getJSONObject(pos);
            JSONObject jsonObject3 = jsonObject2.getJSONObject("gRNItemInfoDetails");

            JSONObject jsonObject1 = null;
            JSONArray jsonArray = new JSONArray();
            JSONArray jsonArrayDefects = new JSONArray();
            JSONArray jsonArray1 = new JSONArray();
            int quantity = 0, quantityDefect = 0;
            for (int j = 0; j < selectedtabData.modelList.size(); j++) {
                GRNProductDetailModel grnProductDetailModel = selectedtabData.modelList.get(j);
                jsonObject1 = new JSONObject();
                jsonObject1.put("number", grnProductDetailModel.getNumber());
                jsonObject1.put("actionTitle", grnProductDetailModel.getActionTitle());
                jsonObject1.put("actionID", grnProductDetailModel.getActionID());
                jsonObject1.put("qty", grnProductDetailModel.getQty());


                if (grnProductDetailModel.getActionID() == 1) {
                    quantity = quantity + grnProductDetailModel.getQty();
                    jsonArray.put(jsonObject1);
                }
                if (grnProductDetailModel.getActionID() == 2) {
                    quantityDefect = quantityDefect + grnProductDetailModel.getQty();
                    jsonArrayDefects.put(jsonObject1);
                }


            }


            JSONObject normalBatch = new JSONObject();
            normalBatch.put("tabTitle", "Normal");
            normalBatch.put("tabId", 1);
            normalBatch.put("count", 1);
            normalBatch.put("model", jsonArray);
            jsonArray1.put(normalBatch);

            JSONObject defects = new JSONObject();
            defects.put("tabTitle", "Defect");
            defects.put("tabId", 2);
            defects.put("count", 1);
            defects.put("model", jsonArrayDefects);
            jsonArray1.put(defects);

            jsonObject2.put("inQty", quantity);
            jsonObject2.put("openQty", quantityDefect);


            jsonObject3.put("data", jsonArray1);
            jsonObject2.put("gRNItemInfoDetails", jsonObject3);
            array.put(pos, jsonObject2);
            jsonObject.put("poItemDetails", array);
            jsonObject.put("poAttachments", arrayPoAttachment);
            jsonObject.put("poIncoTerms", arrayPoIncco);

            Log.e(TAG, "Data::" + jsonObject2.toString());
            new RealmController().saveGRNDetails(jsonObject.toString());

            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //----------------------------------------------------show end
        //update-----start

    }

    @Override
    public void updateValue(final int position, String value, String title) {

    }

    @Override
    public void onRowClicked(int position) {
        setOthersTab();
    }

    private void showListDialog(List<OthersTabList> othersTabLists) {
        ListDialogFragment listDialogFragment = ListDialogFragment.newInstance(othersTabLists);
        listDialogFragment.show(getSupportFragmentManager(), "TAG");

    }

    private void showActionListDialog(List<ActionListModel> actionListModels){
        Toast.makeText(mContext,"Action clicked", Toast.LENGTH_SHORT).show();
//        ActionListFragment actionListFragment = ActionListFragment.newInstance(actionListModels);
//        actionListFragment.show(getSupportFragmentManager(), "TAG");
    }

    @Override
    public void onRowClicked(int position, int value) {

    }


    @Override
    public void tabClick(int position) {
        RealmInventoryTabData realmInventoryTabData = tabData.get(position);
        setRealmData(realmInventoryTabData.getTabId());
    }

    @Override
    public void onFinishListDialog(int tabId, String tabTitle) {
        onAddOthers(tabId, tabTitle);
    }

    @Override
    public void onActionListClicked(int actionId, String actionTitle) {
//        if(actionId == )
        boolean isMove = false;
        boolean isDelete = false;
        if (actionTitle.contains("Copy")) {
            isMove = false;
        }
        if (actionTitle.contains("Move")) {
            isMove = true;
            ///
        }
        if (actionTitle.contains("Delete")) {
            isMove = true;
        }
        String remark = "";
        if (actionId == 10 || actionId ==11) {
            remark = actionTitle;
        }
        setChangeAction(actionId, isMove, isDelete,remark);

//        setChangeAction(actionId);
    }
}
