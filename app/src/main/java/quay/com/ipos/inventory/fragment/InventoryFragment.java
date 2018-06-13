package quay.com.ipos.inventory.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.enums.NoGetEntityEnums;
import quay.com.ipos.inventory.activity.InventoryGRNStepsActivity;
import quay.com.ipos.inventory.activity.InventoryStepsActivity;
import quay.com.ipos.inventory.adapter.CustomAdapter;
import quay.com.ipos.inventory.modal.NOGetEntityBuisnessPlacesModal;
import quay.com.ipos.inventory.modal.NoGetEntityResultModal;
import quay.com.ipos.modal.NewOrderPinnedResults;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.realmbean.RealmNewOrderCart;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 03-05-2018.
 */

public class InventoryFragment extends BaseFragment implements ServiceTask.ServiceResultListener{
    private TextView tvMoreDetails, tvItemNo, tvItemQty, tvTotalItemPrice,
            tvTotalGST, tvTotalItemGSTPrice, tvTotalDiscountDetail, tvTotalDiscountPrice, tvCGSTPrice, tvSGSTPrice,
            tvLessDetails, tvRoundingOffPrice, tvPay, tvPinCount;

    private FrameLayout flScanner;
    private Fragment scanner_fragment;
    private LinearLayout llTotalDiscountDetail, ll_item_pay, llTotalGST;
    private ImageView imvPin, imvRight;

    private LinearLayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private String TAG = InventoryFragment.class.getSimpleName();

    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private OrderList mOrderListResult;
    Dialog myDialog;
    double otcDiscount = 0.0;
    View rootView;
    private double totalAmount = 0;
    private boolean isFragmentDisplayed = true;
    private ArrayList<RealmNewOrderCart> mList = new ArrayList<>();

    Double afterDiscountPrice;
    ArrayList<NewOrderPinnedResults.Info> mOrderInfoArrayList = new ArrayList<>();
    private String json;
    private RelativeLayout llBelowPaymentDetail;
    private TextView tvMessage;
    private AppCompatSpinner spnAddress;
    private Context mContext;
    private ArrayList<NoGetEntityResultModal.BuisnessPlacesBean> noGetEntityBuisnessPlacesModals = new ArrayList<>();
    private String entityStateCode = "";
    private int businessPlaceCode;
    private boolean isSync;
    private String strPlace;
    private LinearLayout btnNext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.inventory_fragment, container, false);
        initializeComponent(rootView);
        mContext = getActivity();
        myDialog = new Dialog(getActivity());
        btnNext=rootView.findViewById(R.id.btnNext);
        setHasOptionsMenu(true);
        Util.hideSoftKeyboard(getActivity());
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(), InventoryGRNStepsActivity.class);
                startActivity(i);
            }
        });
        return rootView;
    }

 /*   RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.TOUCH_SLOP_PAGING || newState != RecyclerView.SCROLL_STATE_IDLE) {
                hideViews();
            } else {
                llBelowPaymentDetail.animate().alpha(1.0f).translationY(0).setInterpolator(new DecelerateInterpolator(1)).start();
                showViews();
            }
        }
    };
*/

    private void initializeComponent(View rootView) {

        spnAddress=rootView.findViewById(R.id.spnAddress);

        setSpinnerData();

    }

    private void setSpinnerData() {

        spnAddress.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strPlace=noGetEntityBuisnessPlacesModals.get(i).getBuisnessPlaceName();
                entityStateCode = noGetEntityBuisnessPlacesModals.get(i).getBuisnessLocationStateCode();
                businessPlaceCode = noGetEntityBuisnessPlacesModals.get(i).getBuisnessPlaceId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        showProgressDialog(mContext,R.string.msg_load_default);
        NOGetEntityBuisnessPlacesModal noGetEntityBuisnessPlacesModal = new NOGetEntityBuisnessPlacesModal();
        noGetEntityBuisnessPlacesModal.setEntityCode(Prefs.getIntegerPrefs(Constants.entityCode)+"");
        noGetEntityBuisnessPlacesModal.setEntityRole(Prefs.getStringPrefs(Constants.entityRole));
        noGetEntityBuisnessPlacesModal.setEntityType(Prefs.getStringPrefs(Constants.entityRole));


        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_NOGetEntityBuisnessPlaces);
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(noGetEntityBuisnessPlacesModal);
        mTask.setListener(this);
        mTask.setResultType(NoGetEntityResultModal.class);
        mTask.execute();

    }



    boolean isBack = false;

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_search:
                // Do onlick on menu action here

                return true;
        }
        return false;
    }













    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
     //   hideProgressDialog();
        if (httpStatusCode == Constants.SUCCESS) {

            if (Util.validateString(serverResponse)){

                try {
                    JSONObject jsonObject=new JSONObject(serverResponse);
                    JSONArray array=jsonObject.optJSONArray(NoGetEntityEnums.buisnessPlaces.toString());
                    new RealmController().saveBusinessPlaces(array.toString());
                    for (int i=0;i<array.length();i++){
                        NoGetEntityResultModal.BuisnessPlacesBean noGetEntityBuisnessPlacesModal=new NoGetEntityResultModal.BuisnessPlacesBean();
                        JSONObject jsonObject1=array.optJSONObject(i);
                        noGetEntityBuisnessPlacesModal.setBuisnessPlaceId(jsonObject1.optInt(NoGetEntityEnums.buisnessPlaceId.toString()));
                        noGetEntityBuisnessPlacesModal.setBuisnessPlaceName(jsonObject1.optString(NoGetEntityEnums.buisnessPlaceName.toString()));
                        noGetEntityBuisnessPlacesModal.setBuisnessLocationStateCode(jsonObject1.optString(NoGetEntityEnums.buisnessLocationStateCode.toString()));
                        noGetEntityBuisnessPlacesModals.add(noGetEntityBuisnessPlacesModal);



                    }

                    CustomAdapter adapter = new CustomAdapter(mContext, R.layout.spinner_item_pss,R.id.text1,noGetEntityBuisnessPlacesModals);
                    adapter.setDropDownViewResource(R.layout.spinner_item_pss);
                    spnAddress.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        } else if (httpStatusCode == Constants.BAD_REQUEST) {
            Toast.makeText(mContext, getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            Toast.makeText(mContext, getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            Toast.makeText(mContext, getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            Toast.makeText(mContext, getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            Toast.makeText(mContext, getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();
        }

    }
}
