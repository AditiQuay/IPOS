package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter.TransferInBatchListDetailAdapter;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter.TransferInTabListAdapter;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInFragment.TransferInActionDialogFragment;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInFragment.TransferInOtherListDialogFragment;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.RealmTransferDetail;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.RealmTransferDetailsUpdate;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.RealmTransferTabData;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInActionList;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInBatchDetailModel;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInOtherTabList;
import quay.com.ipos.listeners.BatchTabButtonClick;
import quay.com.ipos.listeners.EdittClickListener;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.listeners.TabListenerr;
import quay.com.ipos.realmbean.RealmController;

/**
 * Created by niraj.kumar on 7/10/2018.
 */

public class TransferInBatchActivity extends AppCompatActivity implements InitInterface, View.OnClickListener, EdittClickListener, TransferInBatchListDetailAdapter.NotifyCount, MyListener, TransferInOtherListDialogFragment.DialogListener, TransferInActionDialogFragment.ActionListener, TabListenerr, BatchTabButtonClick {
    private static final String TAG = TransferInBatchActivity.class.getSimpleName();
    private Toolbar toolbar;
    private TextView textViewProductName, textViewProductQuantity, textViewProductBalance;
    private RelativeLayout rLayoutBatchNumber;
    private ImageView imgArrowLeft, imgArrowRight;
    private SwitchCompat switchBatchButton;

    private RecyclerView recyclerviewButton;

    private Button btnTabOther, btnAddBatch;
    private EditText batchEditText;
    private CheckBox checkBox1;
    private Button btnAction, btnSave;

    private Context mContext;

    private List<TransferInOtherTabList> transferInOtherTabLists = new ArrayList<>();
    private List<TransferInActionList> transferInActionLists = new ArrayList<>();
    private int pos;
    private TransferInBatchListDetailAdapter transferInBatchListDetailAdapter;
    boolean isDefect;
    private TransferInTabListAdapter transferInTabListAdapter;
    private Dialog myDialog;

    private List<RealmTransferTabData> realmInventoryTabData = new ArrayList<>();
    private RealmTransferTabData selectedtabData;
    private List<TransferInBatchDetailModel> filterModelList = new ArrayList<>();
    private double openQty;
    private boolean isRemarkEmpty = false;
    private int poQty, lengthOgItem;
    private RecyclerView recyclerviewBatch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_in_batch_layout);
        mContext = TransferInBatchActivity.this;
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
        textViewProductQuantity = findViewById(R.id.textViewProductQuantity);
        textViewProductBalance = findViewById(R.id.textViewProductBalance);
        rLayoutBatchNumber = findViewById(R.id.rLayoutBatchNumber);
        imgArrowLeft = findViewById(R.id.imgArrowLeft);
        imgArrowRight = findViewById(R.id.imgArrowRight);
        switchBatchButton = findViewById(R.id.switchBatchButton);
        recyclerviewButton = findViewById(R.id.recyclerviewButton);
        recyclerviewBatch = findViewById(R.id.recyclerviewBatch);
        btnTabOther = findViewById(R.id.btnTabOther);
        btnAddBatch = findViewById(R.id.btnAddBatch);
        batchEditText = findViewById(R.id.batchEditText);
        checkBox1 = findViewById(R.id.checkBox1);
        btnAction = findViewById(R.id.btnAction);
        btnSave = findViewById(R.id.btnSave);


        btnSave.setOnClickListener(this);
        btnAction.setOnClickListener(this);
        btnAddBatch.setOnClickListener(this);

        imgArrowRight.setOnClickListener(this);
        imgArrowLeft.setOnClickListener(this);
        checkBox1.setOnClickListener(this);
        btnTabOther.setOnClickListener(this);

        switchBatchButton.setChecked(true);
        switchBatchButton.setClickable(false);

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

//        if (lengthOgItem==1){
//            imgArrowRight.setVisibility(View.GONE);
//            imgArrowLeft.setVisibility(View.GONE);
//        }
        recyclerviewButton.setHasFixedSize(false);
        recyclerviewButton.setLayoutManager(new GridLayoutManager(this, 2));
        if (realmInventoryTabData.size() > 0) {
            realmInventoryTabData.get(0).isSelected = true;
        }

        transferInTabListAdapter = new TransferInTabListAdapter(mContext, realmInventoryTabData, this, this, this);
        recyclerviewButton.setAdapter(transferInTabListAdapter);
        setBatchTab(pos);


    }

    private void setBatchTab(int position) {
        Realm realm = Realm.getDefaultInstance();
        RealmTransferDetail realmTransferDetail = realm.where(RealmTransferDetail.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmTransferDetail));
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = new JSONArray(jsonObject.optString("poItemDetails").replaceAll("\\\\", ""));
            JSONObject jsonObject2 = array.getJSONObject(position);
            textViewProductName.setText(jsonObject2.optString("materialName"));
            textViewProductQuantity.setText(jsonObject2.optString("openQty"));
            textViewProductBalance.setText("Balance" + " " + jsonObject2.optString("balanceQty"));


            realmInventoryTabData.clear();
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
                        break;
                    }
                    RealmTransferTabData realmTransferTabData = new RealmTransferTabData();
                    realmTransferTabData.setTabId(jsonObject1.optInt("tabId"));
                    realmTransferTabData.setTabTitle(jsonObject1.optString("tabTitle"));
                    realmTransferTabData.modelList = new ArrayList<>();
                    realmInventoryTabData.add(realmTransferTabData);
                }


            } else {
                //data Available

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                    if (jsonObject1.optInt("tabId") == 3) {
                        break;
                    }
                    RealmTransferTabData realmTransferTabData = new RealmTransferTabData();
                    realmTransferTabData.setTabId(jsonObject1.optInt("tabId"));
                    realmTransferTabData.setTabTitle(jsonObject1.optString("tabTitle"));
                    realmTransferTabData.setCount(jsonObject1.optInt("count"));
                    realmTransferTabData.modelList = new ArrayList<>();
                    List<TransferInBatchDetailModel> modelList = Arrays.asList(new Gson().fromJson(jsonObject1.optString("model"), TransferInBatchDetailModel[].class));
                    if (modelList != null)
                        realmTransferTabData.modelList.addAll(modelList);
                    realmInventoryTabData.add(realmTransferTabData);


                }
            }
            transferInTabListAdapter.notifyDataSetChanged();


            realmInventoryTabData.get(0).setSelected(true);
            selectedtabData = realmInventoryTabData.get(0);
            recyclerviewBatch.setHasFixedSize(false);
            recyclerviewBatch.setLayoutManager(new LinearLayoutManager(mContext));
            transferInBatchListDetailAdapter = new TransferInBatchListDetailAdapter(mContext, realmInventoryTabData.get(0).modelList, this, this);
            recyclerviewBatch.setAdapter(transferInBatchListDetailAdapter);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //onAdd others
    void onAddOthers(int id, String title) {
        if (realmInventoryTabData.size() > 0) {
            for (int i = 0; i < realmInventoryTabData.size(); i++) {
                RealmTransferTabData realmTransferTabData = realmInventoryTabData.get(i);
                if (realmTransferTabData.getTabId() == id) {
                    Toast.makeText(mContext, "Category already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        RealmTransferTabData realmTransferTabData = new RealmTransferTabData();
        realmTransferTabData.setTabId(id);
        realmTransferTabData.setTabTitle(title);
        realmTransferTabData.modelList = new ArrayList<>();
        realmInventoryTabData.add(realmTransferTabData);
        transferInTabListAdapter.notifyDataSetChanged();
    }

    private void setOthersTab() {
        transferInOtherTabLists.clear();
        Realm realm = Realm.getDefaultInstance();
        RealmTransferDetail realmTransferDetail = realm.where(RealmTransferDetail.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmTransferDetail));
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
                        TransferInOtherTabList transferInOtherTabList = new TransferInOtherTabList();
                        transferInOtherTabList.tabId = jsonObject11.optInt("tabId");
                        transferInOtherTabList.tabTitle = jsonObject11.optString("tabTitle");
                        transferInOtherTabLists.add(transferInOtherTabList);

                    }
                }

            }

            showListDialog(transferInOtherTabLists);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRealmData(int key, RealmTransferTabData realmTransferTabData) {
        selectedtabData = realmTransferTabData;
        recyclerviewBatch.setHasFixedSize(false);
        recyclerviewBatch.setLayoutManager(new LinearLayoutManager(mContext));
        transferInBatchListDetailAdapter = new TransferInBatchListDetailAdapter(mContext, realmTransferTabData.modelList, this, this);
        recyclerviewBatch.setAdapter(transferInBatchListDetailAdapter);

    }

    @Override
    public void applyTypeFace() {

    }

    private void showListDialog(List<TransferInOtherTabList> othersTabLists) {
        TransferInOtherListDialogFragment transferInOtherListDialogFragment = TransferInOtherListDialogFragment.newInstance(othersTabLists);
        transferInOtherListDialogFragment.show(getSupportFragmentManager(), "TAG");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                confirmationDialog();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void confirmationDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(TransferInBatchActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(TransferInBatchActivity.this);
        }
        builder.setTitle("Alert")
                .setMessage("Are you sure you want to exit the screen?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        refreshTransferInData();
                        finish();

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void refreshTransferInData() {
        Realm realm = Realm.getDefaultInstance();
        RealmTransferDetailsUpdate realmTransferDetailsUpdate = realm.where(RealmTransferDetailsUpdate.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmTransferDetailsUpdate));
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray poItemDetailsArray = new JSONArray(jsonObject.optString("poItemDetails"));

            jsonObject.put("poItemDetails", poItemDetailsArray);
            Log.e(TAG, "Data::" + jsonObject.toString());
            Log.e("Data1111", new Gson().toJson(jsonObject));

            new RealmController().saveTransferInDetails(jsonObject.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        //----------------------------------------------------show end

    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onBackPressed() {
        confirmationDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgArrowRight:
                if (lengthOgItem > 1) {
                    int count = pos + 1;
                    saveDataToLocal(count - 1);
                    setBatchTab(count);
                    imgArrowLeft.setVisibility(View.VISIBLE);
                    pos = count;
                    if (pos == lengthOgItem - 1) {
                        imgArrowRight.setVisibility(View.GONE);
                    }
                } else {
                    saveDataToLocal(pos);
                    setBatchTab(pos);
                    imgArrowRight.setVisibility(View.GONE);
                    imgArrowLeft.setVisibility(View.GONE);
                }

                break;
            case R.id.imgArrowLeft:
                if (pos == 0) {
                    imgArrowLeft.setVisibility(View.INVISIBLE);
                    saveDataToLocal(pos);
                    setBatchTab(pos);
                } else {

                    imgArrowRight.setVisibility(View.VISIBLE);
                    int firstPosition = pos;
                    int secondPosition = firstPosition - 1;
                    pos = secondPosition;
                    saveDataToLocal(firstPosition);
                    setBatchTab(pos);
                    if (pos == 0) {
                        imgArrowLeft.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.checkBox1:
                if (checkBox1.isChecked()) {
                    for (TransferInBatchDetailModel model : selectedtabData.modelList) {
                        model.setSelected(true);
                    }
                } else {
                    for (TransferInBatchDetailModel model : selectedtabData.modelList) {
                        model.setSelected(false);
                    }
                }
                transferInBatchListDetailAdapter.notifyDataSetChanged();

                break;
            case R.id.btnSave:
                updateData();
                break;
            case R.id.btnAction:

                getActionList();
                break;
            case R.id.btnAddBatch:
                hideKeyboard();
                if (TextUtils.isEmpty(batchEditText.getText().toString())) {
                    Toast.makeText(mContext, "Please enter batch number", Toast.LENGTH_SHORT).show();
                } else {
                    saveBatchData();
                }
                batchEditText.setText("");

                break;
            case R.id.btnTabOther:
                setOthersTab();

            default:
                break;
        }
    }

    private void saveBatchData() {
        if (selectedtabData.modelList.size() > 0) {
            for (int i = 0; i < selectedtabData.modelList.size(); i++) {
                TransferInBatchDetailModel transferInBatchDetailModel = selectedtabData.modelList.get(i);
                if (transferInBatchDetailModel.getNumber().equalsIgnoreCase(batchEditText.getText().toString())) {
                    Toast.makeText(mContext, "Batch number already exist", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }

        TransferInBatchDetailModel transferInBatchDetailModel = new TransferInBatchDetailModel();
        transferInBatchDetailModel.setNumber(batchEditText.getText().toString());
        transferInBatchDetailModel.setActionTitle("");
        transferInBatchDetailModel.setActionID(selectedtabData.getTabId());
        transferInBatchDetailModel.setQty(0);
        selectedtabData.setCount(selectedtabData.getCount() + 1);
        selectedtabData.modelList.add(transferInBatchDetailModel);

        transferInBatchListDetailAdapter = new TransferInBatchListDetailAdapter(mContext, selectedtabData.modelList, this, this);
        recyclerviewBatch.setAdapter(transferInBatchListDetailAdapter);


        notifyCount();

    }

    private void notifyCount() {

        for (RealmTransferTabData tabData : realmInventoryTabData) {
            int count = 0;
            for (TransferInBatchDetailModel model : tabData.modelList) {
                count += model.getQty();
            }
            tabData.setCount(count);

        }
        transferInTabListAdapter = new TransferInTabListAdapter(mContext, realmInventoryTabData, this, this, this);
        recyclerviewButton.setAdapter(transferInTabListAdapter);

    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            assert getSystemService(Context.INPUT_METHOD_SERVICE) != null;
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void getActionList() {
        transferInActionLists.clear();
        Realm realm = Realm.getDefaultInstance();
        RealmTransferDetail realmTransferDetail = realm.where(RealmTransferDetail.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmTransferDetail));
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = new JSONArray(jsonObject.optString("poItemDetails").replaceAll("\\\\", ""));
            JSONObject jsonObject2 = array.getJSONObject(pos);

            JSONObject jsonObject3 = jsonObject2.getJSONObject("gRNItemInfoDetails");
            JSONArray jsonArray = jsonObject3.getJSONArray("actionList");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                if (jsonObject1.optInt("actionId") == 13) {
                    break;
                }

                TransferInActionList transferInActionList = new TransferInActionList();
                transferInActionList.actionID = jsonObject1.optInt("actionID");
                transferInActionList.actionTitle = jsonObject1.optString("actionTitle");
                transferInActionLists.add(transferInActionList);
            }
            showActionListDialog(transferInActionLists);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void showActionListDialog(List<TransferInActionList> transferInActionLists) {
        TransferInActionDialogFragment actionListFragment = TransferInActionDialogFragment.newInstance(transferInActionLists);
        actionListFragment.show(getSupportFragmentManager(), "TAG");

    }

    private void updateData() {
        Realm realm = Realm.getDefaultInstance();
        RealmTransferDetail realmTransferDetail = realm.where(RealmTransferDetail.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmTransferDetail));
        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray poItemDetailsArray = new JSONArray(jsonObject.optString("poItemDetails"));
            JSONArray arrayPoAttachment = new JSONArray(jsonObject.optString("poAttachments"));
            JSONArray arrayPoIncco = new JSONArray(jsonObject.optString("poIncoTerms"));
            JSONArray arrayPayTerms = new JSONArray(jsonObject.optString("poPaymentTerms"));
            JSONArray arrayTermsAndCondition = new JSONArray(jsonObject.optString("poTermsAndConditions"));


            JSONObject jsonObject2 = poItemDetailsArray.getJSONObject(pos);
            double unitPrice = jsonObject2.getDouble("unitPrice");

            JSONObject jsonObject3 = jsonObject2.getJSONObject("gRNItemInfoDetails");


            //  JSONArray jsonArrayDefects = new JSONArray();
            JSONArray jsonArray1 = new JSONArray();

            int quantity = 0, quantityDefect = 0, qantityOthers = 0;

            for (RealmTransferTabData tabDatum : realmInventoryTabData) {

                JSONArray jsonArray = new JSONArray();

                for (TransferInBatchDetailModel transferInBatchDetailModel : tabDatum.modelList) {
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject1.put("number", transferInBatchDetailModel.getNumber());
                    jsonObject1.put("actionTitle", transferInBatchDetailModel.getActionTitle());
                    jsonObject1.put("actionID", transferInBatchDetailModel.getActionID());
                    jsonObject1.put("qty", transferInBatchDetailModel.getQty());


                    if (tabDatum.getTabId() == 1) {
                        quantity += transferInBatchDetailModel.getQty();
                    }
                    if (tabDatum.getTabId() == 2) {
                        quantityDefect += transferInBatchDetailModel.getQty();
                    }
                    if (tabDatum.getTabId() > 2) {
                        qantityOthers += transferInBatchDetailModel.getQty();
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

//            double unitP = 0;
//            double totalUnitPrice = unitPrice*(quantity+apQty);
//            unitP+=totalUnitPrice;

//            jsonObject2.put("unitPrice",totalUnitPrice);


            jsonObject3.put("data", jsonArray1);
            jsonObject2.put("gRNItemInfoDetails", jsonObject3);

            poItemDetailsArray.put(pos, jsonObject2);


            int quanOpenTotal = 0, quanBalanceTotal = 0, quanInQuant = 0, quanApp = 0, quanPo = 0;
            double unitPri = 0, upIn = 0, upApp = 0;
            for (int k = 0; k < poItemDetailsArray.length(); k++) {
                JSONObject jsonObject1 = poItemDetailsArray.optJSONObject(k);
                quanOpenTotal += jsonObject1.optInt("openQty");
                quanPo += jsonObject1.optInt("poQty");
                quanBalanceTotal += jsonObject1.optInt("balanceQty");
                quanInQuant += jsonObject1.optInt("inQty");
                quanApp += jsonObject1.optInt("apQty");
                double up = jsonObject1.getDouble("unitPrice");

                upIn += jsonObject1.optInt("inQty") * up;
                upApp += jsonObject1.optInt("apQty") * up;
            }

            int totalPoQty = quanPo + (quanInQuant + quanApp);


            jsonObject.put("poQty", totalPoQty);
            jsonObject.put("value", upIn + upApp);
            jsonObject.put("balanceQty", quanBalanceTotal);
            jsonObject.put("poItemDetails", poItemDetailsArray);
            jsonObject.put("poAttachments", arrayPoAttachment);
            jsonObject.put("poIncoTerms", arrayPoIncco);
            jsonObject.put("poPaymentTerms", arrayPayTerms);
            jsonObject.put("poTermsAndConditions", arrayTermsAndCondition);


            Log.e(TAG, "Data::" + jsonObject2.toString());

            Log.e("Data1111", new Gson().toJson(jsonObject2));

            new RealmController().saveTransferInDetails(jsonObject.toString());

            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //----------------------------------------------------show end

    }

    private void saveDataToLocal(int position) {
        Realm realm = Realm.getDefaultInstance();
        RealmTransferDetail realmTransferDetail = realm.where(RealmTransferDetail.class).findFirst();
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(realm.copyFromRealm(realmTransferDetail));
        try {

            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray poItemDetailsArray = new JSONArray(jsonObject.optString("poItemDetails"));
                JSONArray arrayPoAttachment = new JSONArray(jsonObject.optString("poAttachments"));
                JSONArray arrayPoIncco = new JSONArray(jsonObject.optString("poIncoTerms"));
                JSONArray arrayPayTerms = new JSONArray(jsonObject.optString("poPaymentTerms"));
                JSONArray arrayTermsAndCondition = new JSONArray(jsonObject.optString("poTermsAndConditions"));


                JSONObject jsonObject2 = poItemDetailsArray.getJSONObject(position);
                JSONObject jsonObject3 = jsonObject2.getJSONObject("gRNItemInfoDetails");


                //  JSONArray jsonArrayDefects = new JSONArray();
                JSONArray jsonArray1 = new JSONArray();

                int quantity = 0, quantityDefect = 0, qantityOthers = 0;
                for (RealmTransferTabData tabDatum : realmInventoryTabData) {

                    JSONArray jsonArray = new JSONArray();

                    for (TransferInBatchDetailModel transferInBatchDetailModel : tabDatum.modelList) {
                        JSONObject jsonObject1 = new JSONObject();
                        jsonObject1.put("number", transferInBatchDetailModel.getNumber());
                        jsonObject1.put("actionTitle", transferInBatchDetailModel.getActionTitle());
                        jsonObject1.put("actionID", transferInBatchDetailModel.getActionID());
                        jsonObject1.put("qty", transferInBatchDetailModel.getQty());


                        if (tabDatum.getTabId() == 1) {
                            quantity += transferInBatchDetailModel.getQty();
                        }
                        if (tabDatum.getTabId() == 2) {
                            quantityDefect += transferInBatchDetailModel.getQty();
                        }
                        if (tabDatum.getTabId() > 2) {
                            qantityOthers += transferInBatchDetailModel.getQty();
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

                poItemDetailsArray.put(position, jsonObject2);

                int quanOpenTotal = 0, quanBalanceTotal = 0, quanInQuant = 0, quanApp = 0;
                for (int k = 0; k < poItemDetailsArray.length(); k++) {
                    JSONObject jsonObject1 = poItemDetailsArray.optJSONObject(k);
                    quanOpenTotal += jsonObject1.optInt("openQty");
                    quanBalanceTotal += jsonObject1.optInt("balanceQty");
                    quanInQuant += jsonObject1.optInt("inQty");
                    quanApp += jsonObject1.optInt("apQty");

                }

//            jsonObject.put("openQty", poQty - (quanInQuant + quanApp));
                jsonObject.put("balanceQty", quanBalanceTotal);
                jsonObject.put("poItemDetails", poItemDetailsArray);
                jsonObject.put("poAttachments", arrayPoAttachment);
                jsonObject.put("poIncoTerms", arrayPoIncco);
                jsonObject.put("poPaymentTerms", arrayPayTerms);
                jsonObject.put("poTermsAndConditions", arrayTermsAndCondition);


                Log.e(TAG, "Data::" + jsonObject2.toString());

                Log.e("Data1111", new Gson().toJson(jsonObject2));

                new RealmController().saveTransferInDetails(jsonObject.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }

    }

    @Override
    public void updateValue(int position, String value, String title) {

    }

    @Override
    public void notifyQty() {
        notifyCount();
    }

    @Override
    public void onRowClicked(int position) {

    }

    @Override
    public void onRowClicked(int position, int value) {

    }

    @Override
    public void tabClick(int position) {
        Log.i(TAG, "position" + position);
        batchEditText.setText("");
        RealmTransferTabData tabData = realmInventoryTabData.get(position);
        setRealmData(tabData.getTabId(), tabData);
    }

    @Override
    public void onTabClick(int position) {

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

    private void setChangeAction(int key, boolean isMove, boolean isCopy, boolean isDelete, String remark, int actionId) {
        filterModelList = new ArrayList<>();
        for (TransferInBatchDetailModel transferInBatchDetailModel : selectedtabData.modelList) {
            if (transferInBatchDetailModel.isSelected) {
                TransferInBatchDetailModel newBatch = new TransferInBatchDetailModel();
                if (isCopy) {
                    newBatch.actionTitle = remark;
                    newBatch.setActionID(actionId);
                    newBatch.setActionTitle(remark);
                    newBatch.setQty(0);
                    newBatch.setNumber(transferInBatchDetailModel.getNumber());

                } else {
                    newBatch.actionTitle = remark;
                    newBatch.setActionID(actionId);
                    newBatch.setActionTitle(remark);
                    newBatch.setQty(transferInBatchDetailModel.getQty());
                    newBatch.setNumber(transferInBatchDetailModel.getNumber());

                }

                if (actionId == 12 || actionId == 13 || actionId == 14 || actionId == 15)
                    transferInBatchDetailModel.actionTitle = remark;
                transferInBatchDetailModel.setSelected(false);


                transferInBatchDetailModel.setActionID(actionId);
//                grnProductDetailModel.setActionTitle("Defect");

                // filterModelList.add(grnProductDetailModel);
                if (isMove || isDelete) {
                    filterModelList.add(transferInBatchDetailModel);
                } else {
                    filterModelList.add(newBatch);
                }
            }
        }
        if (!isDelete) {
            for (RealmTransferTabData model : realmInventoryTabData) {
                if (model.getTabId() == key) {
                    model.modelList.addAll(filterModelList);
                }
            }
        }
        if (isMove || isDelete) {
            selectedtabData.modelList.removeAll(filterModelList);
        }
        transferInBatchListDetailAdapter = new TransferInBatchListDetailAdapter(mContext, selectedtabData.modelList, this, this);
        recyclerviewBatch.setAdapter(transferInBatchListDetailAdapter);

        notifyCount();
    }

}
