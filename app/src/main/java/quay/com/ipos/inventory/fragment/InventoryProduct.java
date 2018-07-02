package quay.com.ipos.inventory.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import quay.com.ipos.R;
import quay.com.ipos.inventory.adapter.BatchTabAdapter;
import quay.com.ipos.inventory.adapter.InventoryProdcutDetailAdapter;
import quay.com.ipos.inventory.modal.ActionListModel;
import quay.com.ipos.inventory.modal.GRNProductDetailModel;
import quay.com.ipos.inventory.modal.OthersTabList;
import quay.com.ipos.inventory.modal.RealmInventoryTabData;
import quay.com.ipos.listeners.BatchTabButtonClick;
import quay.com.ipos.listeners.EdittClickListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.listeners.TabListenerr;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmGRNDetails;

/**
 * Created by niraj.kumar on 6/12/2018.
 */

public class InventoryProduct extends AppCompatActivity implements InitInterface, View.OnClickListener, EdittClickListener, MyListener, ListDialogFragment.DialogListener, ActionDialogFragment.ActionListener, TabListenerr, BatchTabButtonClick {
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


    private List<OthersTabList> otherTabList = new ArrayList<>();
    private List<ActionListModel> actionListModels = new ArrayList<>();

    private int pos;
    private InventoryProdcutDetailAdapter inventoryProdcutDetailAdapter;
    boolean isDefect;
    private BatchTabAdapter batchTabAdapter;
    private Dialog myDialog;
    private CheckBox checkBox1;
    private Button btnTabOther;

    private List<RealmInventoryTabData> tabData = new ArrayList<>();
    private RealmInventoryTabData selectedtabData;
    private List<GRNProductDetailModel> filterModelList = new ArrayList<>();
    private double openQty;
    private boolean isRemarkEmpty = false;
    private int poQty, lengthOgItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_product_details);

        mContext = InventoryProduct.this;
        myDialog = new Dialog(this);
        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent i = getIntent();
        pos = i.getIntExtra("position", pos);
        lengthOgItem = i.getIntExtra("lengthOfProduct", lengthOgItem);
        openQty = i.getDoubleExtra("openQty", 0);
        poQty = i.getIntExtra("poQty", 0);


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
        checkBox1 = findViewById(R.id.checkBox1);
        switchBatchButton = findViewById(R.id.switchBatchButton);
        rLayoutBatchNumber = findViewById(R.id.rLayoutBatchNumber);
        textViewProductQuantity = findViewById(R.id.textViewProductQuantity);
        textViewProductBalance = findViewById(R.id.textViewProductBalance);
        imgArrowLeft = findViewById(R.id.imgArrowLeft);
        imgArrowRight = findViewById(R.id.imgArrowRight);
        btnTabOther = findViewById(R.id.btnTabOther);

        btnSave.setOnClickListener(this);
        btnAction.setOnClickListener(this);
        btnAddBatch.setOnClickListener(this);

        imgArrowRight.setOnClickListener(this);
        imgArrowLeft.setOnClickListener(this);
        checkBox1.setOnClickListener(this);
        btnTabOther.setOnClickListener(this);

        switchBatchButton.setChecked(true);


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


        recyclerviewButton.setLayoutManager(new GridLayoutManager(this, 3));
        if (tabData.size() > 0) {
            tabData.get(0).isSelected = true;
        }

        batchTabAdapter = new BatchTabAdapter(mContext, tabData, this, this, this);
        recyclerviewButton.setAdapter(batchTabAdapter);
        setBatchTab(pos);


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
            JSONArray jsonArray = jsonObject3.getJSONArray("data");
            JSONArray jsonArraySample = jsonObject3.getJSONArray("tabList");
            if (jsonArray == null || jsonArray.length() == 0) {
                if (jsonArraySample == null || jsonArraySample.length() == 0) {
                    return;
                }
                for (int i = 0; i < jsonArraySample.length(); i++) {
                    JSONObject jsonObject1 = jsonArraySample.optJSONObject(i);
                    if (jsonObject1.optInt("tabId") == 3) {
                        continue;
                    }
                    RealmInventoryTabData batchList = new RealmInventoryTabData();
                    batchList.setTabId(jsonObject1.optInt("tabId"));
                    batchList.setTabTitle(jsonObject1.optString("tabTitle"));
                    batchList.modelList = new ArrayList<>();
                    tabData.add(batchList);
                }


            } else {
                //data Available

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                    RealmInventoryTabData batchList = new RealmInventoryTabData();
                    batchList.setTabId(jsonObject1.optInt("tabId"));
                    batchList.setTabTitle(jsonObject1.optString("tabTitle"));
                    batchList.setCount(jsonObject1.optInt("count"));
                    batchList.modelList = new ArrayList<>();
                    List<GRNProductDetailModel> modelList = Arrays.asList(new Gson().fromJson(jsonObject1.optString("model"), GRNProductDetailModel[].class));
                    if (modelList != null)
                        batchList.modelList.addAll(modelList);
                    tabData.add(batchList);


                }
            }
            batchTabAdapter.notifyDataSetChanged();


            tabData.get(0).setSelected(true);
            selectedtabData = tabData.get(0);
            recyclerviewBatch.setHasFixedSize(true);
            recyclerviewBatch.setLayoutManager(new LinearLayoutManager(mContext));
            inventoryProdcutDetailAdapter = new InventoryProdcutDetailAdapter(mContext, tabData.get(0).modelList, this);
            recyclerviewBatch.setAdapter(inventoryProdcutDetailAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //onAdd others
    void onAddOthers(int id, String title) {
        if (tabData.size() > 0) {
            for (int i = 0; i < tabData.size(); i++) {
                RealmInventoryTabData realmInventoryTabData = tabData.get(i);
                if (realmInventoryTabData.getTabId() == id) {
                    Toast.makeText(mContext, "Category already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
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


    private void setRealmData(int key, RealmInventoryTabData realmInventoryTabData1) {
        selectedtabData = realmInventoryTabData1;
        recyclerviewBatch.setHasFixedSize(true);
        recyclerviewBatch.setLayoutManager(new LinearLayoutManager(mContext));
        inventoryProdcutDetailAdapter = new InventoryProdcutDetailAdapter(mContext, realmInventoryTabData1.modelList, this);
        recyclerviewBatch.setAdapter(inventoryProdcutDetailAdapter);


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
                imgArrowLeft.setVisibility(View.VISIBLE);
                pos = count;
                if (pos == lengthOgItem - 1) {
                    imgArrowRight.setVisibility(View.GONE);

                }
                break;
            case R.id.imgArrowLeft:
                if (pos == 0) {
                    imgArrowLeft.setVisibility(View.INVISIBLE);
                    setBatchTab(pos);
                } else {
                    imgArrowLeft.setVisibility(View.GONE);
                    imgArrowRight.setVisibility(View.VISIBLE);
                    int firstPosition = pos;
                    int secondPosition = firstPosition - 1;
                    pos = secondPosition;
                    setBatchTab(secondPosition);
                }
                break;
            case R.id.checkBox1:
                if (checkBox1.isChecked()) {
                    for (GRNProductDetailModel model : selectedtabData.modelList) {
                        model.setSelected(true);
                    }
                } else {
                    for (GRNProductDetailModel model : selectedtabData.modelList) {
                        model.setSelected(false);
                    }
                }
                inventoryProdcutDetailAdapter.notifyDataSetChanged();

                break;
            case R.id.btnSave:
//                for (int i = 0; i < selectedtabData.modelList.size(); i++) {
//                    GRNProductDetailModel gr = selectedtabData.modelList.get(i);
//                    if (selectedtabData.getTabId() != 1) {
//                        String remark = gr.getActionTitle();
//                        isRemarkEmpty = TextUtils.isEmpty(remark);
//                    }
//
//                }
//
//                if (!isRemarkEmpty) {
//                    updateData();
//                } else {
//                    Toast.makeText(mContext, "Please perform action", Toast.LENGTH_SHORT).show();
//                }
                updateData();
                break;
            case R.id.btnAction:

                getActionList();
                break;
            case R.id.btnAddBatch:
                hideKeyboard();
                saveBatchData();
                batchEditText.setText("");
                break;
            case R.id.btnTabOther:
                setOthersTab();
                break;
            default:
                break;

        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            assert getSystemService(Context.INPUT_METHOD_SERVICE) != null;
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
                if (jsonObject1.optInt("actionId") == 13) {
                    continue;
                }

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

    private void setChangeAction(int key, boolean isMove, boolean isCopy, boolean isDelete, String remark, int actionId) {
        filterModelList = new ArrayList<>();
        for (GRNProductDetailModel grnProductDetailModel : selectedtabData.modelList) {
            if (grnProductDetailModel.isSelected) {
                GRNProductDetailModel newBatch = new GRNProductDetailModel();
                newBatch.actionTitle = remark;
                newBatch.setActionID(actionId);
                newBatch.setActionTitle(remark);
                newBatch.setQty(grnProductDetailModel.getQty());
                newBatch.setNumber(grnProductDetailModel.getNumber());

                if (actionId == 12 || actionId == 13 || actionId == 14 || actionId == 15)
                    grnProductDetailModel.actionTitle = remark;
                grnProductDetailModel.setSelected(false);


                grnProductDetailModel.setActionID(actionId);
//                grnProductDetailModel.setActionTitle("Defect");

                // filterModelList.add(grnProductDetailModel);
                if (isMove || isDelete) {
                    filterModelList.add(grnProductDetailModel);
                } else {
                    filterModelList.add(newBatch);
                }
            }
        }
        if (!isDelete) {
            for (RealmInventoryTabData model : tabData) {
                if (model.getTabId() == key) {
                    model.modelList.addAll(filterModelList);
                }
            }
        }
        if (isMove || isDelete) {
            selectedtabData.modelList.removeAll(filterModelList);
        }
        inventoryProdcutDetailAdapter = new InventoryProdcutDetailAdapter(mContext, selectedtabData.modelList, this);
        recyclerviewBatch.setAdapter(inventoryProdcutDetailAdapter);

        notifyCount();
    }

    private void saveBatchData() {
        if (selectedtabData.modelList.size() > 0) {
            for (int i = 0; i < selectedtabData.modelList.size(); i++) {
                GRNProductDetailModel grnProductDetailModel = selectedtabData.modelList.get(i);
                if (grnProductDetailModel.getNumber().equalsIgnoreCase(batchEditText.getText().toString())) {
                    Toast.makeText(mContext, "Batch number already exist", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        GRNProductDetailModel grnProductDetailModel1 = new GRNProductDetailModel();
        grnProductDetailModel1.setNumber(batchEditText.getText().toString());
        grnProductDetailModel1.setActionTitle("");
        grnProductDetailModel1.setActionID(selectedtabData.getTabId());
        grnProductDetailModel1.setQty(0);
        selectedtabData.setCount(selectedtabData.getCount() + 1);
        selectedtabData.modelList.add(grnProductDetailModel1);

        inventoryProdcutDetailAdapter = new InventoryProdcutDetailAdapter(mContext, selectedtabData.modelList, this);
        recyclerviewBatch.setAdapter(inventoryProdcutDetailAdapter);


        notifyCount();

    }

    private void notifyCount() {

        for (RealmInventoryTabData tabData : tabData) {
            tabData.setCount(tabData.modelList.size());

        }
        batchTabAdapter = new BatchTabAdapter(mContext, tabData, this, this, this);
        recyclerviewButton.setAdapter(batchTabAdapter);

    }

    private void updateData() {
        Realm realm = Realm.getDefaultInstance();
        RealmGRNDetails realmGRNDetails = realm.where(RealmGRNDetails.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmGRNDetails));
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray poItemDetailsArray = new JSONArray(jsonObject.optString("poItemDetails"));
            JSONArray arrayPoAttachment = new JSONArray(jsonObject.optString("poAttachments"));
            JSONArray arrayPoIncco = new JSONArray(jsonObject.optString("poIncoTerms"));
            JSONObject jsonObject2 = poItemDetailsArray.getJSONObject(pos);
            JSONObject jsonObject3 = jsonObject2.getJSONObject("gRNItemInfoDetails");


            //  JSONArray jsonArrayDefects = new JSONArray();
            JSONArray jsonArray1 = new JSONArray();

            int quantity = 0, quantityDefect = 0, qantityOthers = 0;
            for (RealmInventoryTabData tabDatum : tabData) {

                JSONArray jsonArray = new JSONArray();

                for (GRNProductDetailModel grnProductDetailModel : tabDatum.modelList) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("number", grnProductDetailModel.getNumber());
                    jsonObject1.put("actionTitle", grnProductDetailModel.getActionTitle());
                    jsonObject1.put("actionID", grnProductDetailModel.getActionID());
                    jsonObject1.put("qty", grnProductDetailModel.getQty());


                    if (tabDatum.getTabId() == 1) {
                        quantity += grnProductDetailModel.getQty();
                    }
                    if (tabDatum.getTabId() == 2) {
                        quantityDefect += grnProductDetailModel.getQty();
                    }
                    if (tabDatum.getTabId() > 2) {
                        qantityOthers += grnProductDetailModel.getQty();
                    }
                    jsonArray.put(jsonObject1);

                }

                JSONObject normalBatch = new JSONObject();
                normalBatch.put("tabTitle", tabDatum.getTabTitle());
                normalBatch.put("tabId", tabDatum.getTabId());
                normalBatch.put("count", jsonArray.length());
                normalBatch.put("model", jsonArray);
                jsonArray1.put(normalBatch);
            }


            jsonObject2.put("inQty", quantity);

            int apQty = quantityDefect + qantityOthers;
            jsonObject2.put("apQty", apQty);

            int balanceQty = (int) (openQty - (quantity + apQty));
            jsonObject2.put("balanceQty", balanceQty);
            jsonObject2.put("isBatch", 1);


            jsonObject3.put("data", jsonArray1);
            jsonObject2.put("gRNItemInfoDetails", jsonObject3);

            poItemDetailsArray.put(pos, jsonObject2);

            int quanOpenTotal = 0, quanBalanceTotal = 0, quanInQuant = 0, quanApp = 0;
            for (int k = 0; k < poItemDetailsArray.length(); k++) {
                JSONObject jsonObject1 = poItemDetailsArray.optJSONObject(k);
                quanOpenTotal += jsonObject1.optInt("openQty");
                quanBalanceTotal += jsonObject1.optInt("balanceQty");
                quanInQuant += jsonObject1.optInt("inQty");
                quanApp += jsonObject1.optInt("apQty");

            }

            jsonObject.put("openQty", poQty - (quanInQuant + quanApp));
            jsonObject.put("balanceQty", quanBalanceTotal+balanceQty);
            jsonObject.put("poItemDetails", poItemDetailsArray);
            jsonObject.put("poAttachments", arrayPoAttachment);
            jsonObject.put("poIncoTerms", arrayPoIncco);
            jsonObject.put("poPaymentTerms", "");

            Log.e(TAG, "Data::" + jsonObject2.toString());

            Log.e("Data1111", new Gson().toJson(jsonObject2));

            new RealmController().saveGRNDetails(jsonObject.toString());

            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //----------------------------------------------------show end

    }

    @Override
    public void updateValue(final int position, String value, String title) {

    }

    @Override
    public void onRowClicked(int position) {


    }

    private void showListDialog(List<OthersTabList> othersTabLists) {
        ListDialogFragment listDialogFragment = ListDialogFragment.newInstance(othersTabLists);
        listDialogFragment.show(getSupportFragmentManager(), "TAG");

    }


    private void showActionListDialog(List<ActionListModel> actionListModels) {
        ActionDialogFragment actionListFragment = ActionDialogFragment.newInstance(actionListModels);
        actionListFragment.show(getSupportFragmentManager(), "TAG");

    }

    @Override
    public void onRowClicked(int position, int value) {

    }


    @Override
    public void tabClick(int position) {
        Log.i(TAG, "position" + position);
        batchEditText.setText("");
        RealmInventoryTabData realmInventoryTabData = tabData.get(position);
        setRealmData(realmInventoryTabData.getTabId(), realmInventoryTabData);
    }

    @Override
    public void onFinishListDialog(int tabId, String tabTitle) {

        onAddOthers(tabId, tabTitle);
    }

    @Override
    public void onActionListClicked(int actionId, String actionTitle) {

        int action = 0;
        boolean isMove = false;
        boolean isCopy = false;
        boolean isDelete = false;
        if (actionId == 9) {
            isMove = false;
            isCopy = true;

            if (selectedtabData.getTabId() == 1) {
                action = 2;
            }


        }
        if (actionId == 10) {
            isMove = true;
            action = 2;
        }
        if (actionId == 11) {
            isMove = true;
        }

        String remark = "";
        if (actionId == 12 || actionId == 13 || actionId == 14 || actionId == 15) {
            isMove = false;
            remark = actionTitle;
        }
        setChangeAction(action, isMove, isCopy, isDelete, remark, actionId);

    }


    @Override
    public void onTabClick(int position) {

    }


}
