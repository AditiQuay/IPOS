package quay.com.ipos.retailsales.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseFragment;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.dashboard.adapter.SpinnerDropDownAdapter;
import quay.com.ipos.modal.LoginResult;
import quay.com.ipos.modal.RetailOrderCenterListResult;
import quay.com.ipos.modal.RetailOrderCentreRequest;
import quay.com.ipos.retailsales.activity.OutboxActivity;
import quay.com.ipos.retailsales.activity.PrintReceiptActivity;
import quay.com.ipos.retailsales.adapter.RetailOrderCenterAdapter;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.WrapContentLinearLayoutManager;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.SharedPrefUtil;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 03-07-2018.
 */

public class RetailOrderCentreFragment extends BaseFragment implements ServiceTask.ServiceResultListener,View.OnClickListener{

    private TextView tvNoItemAvailable,btnCancel,btnAccept;
    private ImageView imvClear;
    private RecyclerView rvOutBox;
    private ImageView imageViewCancel;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeToRefresh;
    RetailOrderCenterAdapter mRetailOrderCenterAdapter;
    TextView tvDateFrom,tvDateTo;
    EditText searchView;
    private ArrayList<RetailOrderCenterListResult.ListOrderCenter> listOrderCenters = new ArrayList<>();
//    private ArrayList<RetailOrderCenterListResult.ListOrderCenter> allListOrderCenters = new ArrayList<>();
    /**
     * The Root view.
     */
    View rootView;
    LoginResult loginResult;
    long lfromDateInMillis;
    String fromDate="",toDate="",paymentMode="",searchParam="";
    private Dialog dialogOTC;
    private LinearLayout llDateFrom,llDateTo,llCancel,llAccept;
    private Spinner spPayment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = getView() != null ? getView() : inflater.inflate(R.layout.order_centre_retail, container, false);
        initializeComponent(rootView);
        setHasOptionsMenu(true);
        dialogOTCTask();
        return rootView;
    }

    /**
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Util.hideSoftKeyboard(getActivity());
        switch (item.getItemId()) {

            case R.id.action_filter:
                // Do onClick on menu action here
                dialogOTC.show();
                return true;
            case R.id.action_outbox:
                // Do onClick on menu action here
                onOutBoxButton();
                break;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((MainActivity) IPOSApplication.getContext()).setToolbarTitle(getString(R.string.retail_sales_order_centre));
    }
    public void onOutBoxButton() {
        Intent mIntent = new Intent(getActivity(), OutboxActivity.class);
        startActivity(mIntent);
    }
    private void initializeComponent(View rootView) {
        loginResult = Util.getCustomGson().fromJson(SharedPrefUtil.getString(Constants.Login_result,"",getActivity()),LoginResult.class);
        swipeToRefresh = rootView.findViewById(R.id.swipeToRefresh);
        searchView = rootView.findViewById(R.id.searchView);
        tvNoItemAvailable = rootView.findViewById(R.id.tvNoItemAvailable);
        imvClear = rootView.findViewById(R.id.imvClear);
        rvOutBox = rootView.findViewById(R.id.rvOutBox);

        rvOutBox.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rvOutBox.setLayoutManager(new WrapContentLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvOutBox.addItemDecoration(
                new ItemDecorationAlbumColumns(getActivity().getResources().getDimensionPixelSize(R.dimen.dim_5),
                        getActivity().getResources().getInteger(R.integer.photo_list_preview_columns)));
        setAdapter();
        callServiceRetailOrderCenter(fromDate,toDate,paymentMode,searchParam);
        imvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setText("");
//                listOrderCenters.clear();
//                listOrderCenters.addAll(allListOrderCenters);
                searchParam="";
                callServiceRetailOrderCenter(fromDate,toDate,paymentMode,searchParam);
                mRetailOrderCenterAdapter.notifyDataSetChanged();
            }
        });
        swipeToRefresh.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        listOrderCenters.clear();
                        callServiceRetailOrderCenter(fromDate,toDate,paymentMode,searchParam);
                        mRetailOrderCenterAdapter.notifyDataSetChanged();
                    }
                }
        );
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length()>0)
                {
//                    if(listOrderCenters.size()>0) {
                        imvClear.setVisibility(View.VISIBLE);
//                        filter(charSequence.toString(), allListOrderCenters);
                        searchParam=charSequence.toString();
                        listOrderCenters.clear();
                        callServiceRetailOrderCenter(fromDate,toDate,paymentMode,searchParam);
//                    }else {
//                        imvClear.setVisibility(View.GONE);
//                    }
                    mRetailOrderCenterAdapter.notifyDataSetChanged();
                }
                else {
//                    arrSearchlist.clear();
                    mRetailOrderCenterAdapter.notifyDataSetChanged();
//                    tvItemSize.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

//    private void filter(String charText, ArrayList<RetailOrderCenterListResult.ListOrderCenter> responseList) {
//        if (listOrderCenters != null && responseList != null) {
//            charText = charText.toLowerCase(Locale.getDefault());
//            listOrderCenters.clear();
//            if (charText.length() == 0) {
//                listOrderCenters.addAll(responseList);
//            } else {
//                for (RetailOrderCenterListResult.ListOrderCenter wp : responseList) {
//                    if (wp != null) {
//
//                        if (wp.getMobile().toLowerCase(Locale.getDefault()).contains(charText) || wp.getCustomerName().toLowerCase(Locale.getDefault()).contains(charText) || wp.getOrderDate().toLowerCase(Locale.getDefault()).contains(charText) || wp.getOrderNo().toLowerCase(Locale.getDefault()).contains(charText) ) {
//                            listOrderCenters.add(wp);
//                        }
//                    }
//                }
//            }
//        }
//    }


    private void dialogOTCTask() {

        dialogOTC = new Dialog(getActivity());
        dialogOTC.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOTC.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        // Include dialog.xml file
        dialogOTC.setContentView(R.layout.retail_order_filter_layout);
//        dialogOTC.show();
        dialogOTC.setCancelable(false);
        llDateFrom = dialogOTC.findViewById(R.id.llDateFrom);
        spPayment = dialogOTC.findViewById(R.id.spPayment);
        llDateTo = dialogOTC.findViewById(R.id.llDateTo);
        llDateTo = dialogOTC.findViewById(R.id.llDateTo);
        llAccept = dialogOTC.findViewById(R.id.llAccept);
        btnCancel = dialogOTC.findViewById(R.id.btnCancel);
        llCancel = dialogOTC.findViewById(R.id.llCancel);
        btnAccept = dialogOTC.findViewById(R.id.btnAccept);
        tvDateFrom = dialogOTC.findViewById(R.id.tvDateFrom);
        tvDateTo = dialogOTC.findViewById(R.id.tvDateTo);
        imageViewCancel = dialogOTC.findViewById(R.id.imageViewCancel);
        llCancel.setBackgroundResource(R.drawable.button_drawable);
        btnCancel.setTextColor(getActivity().getResources().getColor(R.color.white));
        btnCancel.setText("Reset");
        btnAccept.setText("Apply");
        llDateFrom.setOnClickListener(this);
        llDateTo.setOnClickListener(this);
        llAccept.setOnClickListener(this);
        llCancel.setOnClickListener(this);
        imageViewCancel.setOnClickListener(this);
        SpinnerDropDownAdapter mSpinnerDropDownAdapter = new SpinnerDropDownAdapter(getActivity(),getResources().getStringArray(R.array.payment_type));
        mSpinnerDropDownAdapter.setColor(true);
        mSpinnerDropDownAdapter.setColorBG(R.color.colorAccent,"grey");
        // attaching data adapter to spinner
        spPayment.setAdapter(mSpinnerDropDownAdapter);
        spPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if(spPayment.getSelectedItem().toString().equalsIgnoreCase("Select Payment Mode")){
                    paymentMode = "";
                }else {
                    paymentMode = spPayment.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.llAccept:
                if (paymentMode.equalsIgnoreCase("")){
                    if(fromDate.equalsIgnoreCase("") ){
                        Util.showToast("Please select Date from also ");
                    }else if(toDate.equalsIgnoreCase("") ){
                        Util.showToast("Please select Date to also");
                    } else {
                        dialogOTC.dismiss();
                        listOrderCenters.clear();
                        callServiceRetailOrderCenter(fromDate,toDate,paymentMode,searchParam);
                    }
                }else {
                    dialogOTC.dismiss();
                    listOrderCenters.clear();
                    callServiceRetailOrderCenter(fromDate,toDate,paymentMode,searchParam);
                }



//                if(fromDate.equalsIgnoreCase("")){
//                    tvDateFrom.setText(getResources().getString(R.string.order_date_from));
//                }else if(toDate.equalsIgnoreCase("")){
//                    tvDateTo.setText(getResources().getString(R.string.order_date_to));
//                }
                break;
            case R.id.llCancel:
                fromDate="";
                toDate="";
                paymentMode="";
                spPayment.setSelection(0);
                tvDateFrom.setText(getResources().getString(R.string.order_date_from));
                tvDateTo.setText(getResources().getString(R.string.order_date_to));
                dialogOTC.dismiss();
                listOrderCenters.clear();
                callServiceRetailOrderCenter(fromDate,toDate,paymentMode,searchParam);
                if(fromDate.equalsIgnoreCase("")){
                    tvDateFrom.setText(getResources().getString(R.string.order_date_from));
                }else if(toDate.equalsIgnoreCase("")){
                    tvDateTo.setText(getResources().getString(R.string.order_date_to));
                }
                break;
            case R.id.llDateFrom:
                dateDialogfrom();
//                tvDateFrom.setText(Util.getFormattedDates(fromDate,Constants.format6,Constants.format2));
                if(fromDate.equalsIgnoreCase("")){
                    tvDateFrom.setText(getResources().getString(R.string.order_date_from));
                }else if(toDate.equalsIgnoreCase("")){
                    tvDateTo.setText(getResources().getString(R.string.order_date_to));
                }
                break;
            case R.id.llDateTo:
                if(fromDate.equalsIgnoreCase("")){
                    Util.showToast("Please select Date from also ");
                }else {

                    dateDialogTo();
                }

                if(fromDate.equalsIgnoreCase("")){
                    tvDateFrom.setText(getResources().getString(R.string.order_date_from));
                }else if(toDate.equalsIgnoreCase("")){
                    tvDateTo.setText(getResources().getString(R.string.order_date_to));
                }
                break;
            case R.id.imageViewCancel:
                dialogOTC.dismiss();
                if(fromDate.equalsIgnoreCase("")){
                    tvDateFrom.setText(getResources().getString(R.string.order_date_from));
                }else if(toDate.equalsIgnoreCase("")){
                    tvDateTo.setText(getResources().getString(R.string.order_date_to));
                }
                break;
        }
    }
    public  void dateDialogfrom() {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        try {
                            String erg = year+"";
                            erg += "-" + String.valueOf(monthOfYear + 1);
                            erg += "-" + String.valueOf(dayOfMonth);
                            fromDate=erg;
                            Calendar c1 = Calendar.getInstance();

                             c1.set(year,monthOfYear,dayOfMonth);
//                            int m = c1.get(monthOfYear);
//                            int d = c1.get(dayOfMonth);
                            lfromDateInMillis =c1.getTimeInMillis();

//                            jsonObjectSubmitJson.put("deliveryBy",erg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        tvDateFrom.setText(Util.getFormattedDates(fromDate,Constants.format6,Constants.format2));
                    }

                }, y, m, d);
        dp.setTitle("Date From");
        dp.show();

        dp.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);


    }

    public  void dateDialogTo() {
        final Calendar c = Calendar.getInstance();

        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dp = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {


                        try {
                            String erg = year+"";
                            erg += "-" + String.valueOf(monthOfYear + 1);
                            erg += "-" + String.valueOf(dayOfMonth);

//                            lfromDateInMillis =c.getTimeInMillis();
                            toDate=erg;
//                            jsonObjectSubmitJson.put("deliveryBy",erg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        tvDateTo.setText(Util.getFormattedDates(toDate,Constants.format6,Constants.format2));
//                        deliverDate.setText(Util.getFormattedDates(erg,Constants.format6,Constants.format2));

                    }

                }, y, m, d);
        dp.setTitle("Date To");
        dp.show();


        dp.getDatePicker().setMaxDate(System.currentTimeMillis()-1000);
        if(lfromDateInMillis>0)
            dp.getDatePicker().setMinDate(lfromDateInMillis-1000);
//        dp.getDatePicker().setMinDate(System.currentTimeMillis()-1000);

    }
    private void setAdapter() {
        mRetailOrderCenterAdapter = new RetailOrderCenterAdapter(getActivity(), listOrderCenters);
        rvOutBox.setAdapter(mRetailOrderCenterAdapter);
        final GestureDetector mGestureDetector = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        Util.hideSoftKeyboard(getActivity());
                        return true;
                    }

                });

        rvOutBox.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            @Override
            public void onTouchEvent(RecyclerView arg0, MotionEvent arg1) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean arg0) {

            }

            @SuppressWarnings("deprecation")
            @Override
            public boolean onInterceptTouchEvent(RecyclerView arg0, MotionEvent motionEvent) {
                View child = arg0.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (child != null && mGestureDetector.onTouchEvent(motionEvent)) {

//                    Util.hideSoftKeyboard(getActivity());
                    callPrintViewActivity(listOrderCenters.get(arg0.getChildAdapterPosition(child)).getOrderNo()+"");
                    return true;

                }

                return false;
            }

        });
    }

    public void callPrintViewActivity(String mPrintViewResult){
        Intent mIntent = new Intent(getActivity(), PrintReceiptActivity.class);
        mIntent.putExtra(Constants.RECEIPT,"");
        mIntent.putExtra(Constants.KEY_ORDER_ID,mPrintViewResult);
        mIntent.putExtra(Constants.RECEIPT_FROM,Constants.OrderCenterMode);
        startActivity(mIntent);
    }
    private void callServiceRetailOrderCenter(String fromDate,String toDate,String paymentMode,String searchParam) {

        RetailOrderCentreRequest orderCentreRequest = new RetailOrderCentreRequest();
        orderCentreRequest.setBusinessPlaceCode(loginResult.getUserAccess().getWorklocationID()+"");
        orderCentreRequest.setBusinessStateCode(loginResult.getUserAccess().getWorklocations().get(0).getLocationStateCode());
        orderCentreRequest.setEmployeeCode(loginResult.getUserAccess().getEmpCode());
        orderCentreRequest.setEmployeeRole(loginResult.getUserAccess().getUserRole());
        orderCentreRequest.setStoreId(loginResult.getUserAccess().getWorklocationID()+"");
        orderCentreRequest.setFromDate(fromDate);
        orderCentreRequest.setToDate(toDate);
        orderCentreRequest.setPaymentType(paymentMode);
        orderCentreRequest.setSearchParam(searchParam);
        orderCentreRequest.setType("NA");

        showProgressDialog(R.string.please_wait);
        ServiceTask mServiceTask = new ServiceTask();
        mServiceTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_ORDER_CENTER);
        mServiceTask.setParamObj(orderCentreRequest);
        mServiceTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mServiceTask.setListener(this);
        mServiceTask.setResultType(RetailOrderCenterListResult.class);
        mServiceTask.execute();
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        hideProgressDialog();
        swipeToRefresh.invalidate();
        swipeToRefresh.setRefreshing(false);
        if(httpStatusCode == Constants.SUCCESS){
            if (serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_RETAIL_ORDER_CENTER)) {
                if (resultObj != null) {
                    RetailOrderCenterListResult mRetailOrderCenterListResult = (RetailOrderCenterListResult) resultObj;
                    if(mRetailOrderCenterListResult != null && mRetailOrderCenterListResult.getListOrderCenter() != null){

                        listOrderCenters.addAll(mRetailOrderCenterListResult.getListOrderCenter());
//                        allListOrderCenters.addAll(mRetailOrderCenterListResult.getListOrderCenter());

                    }

                }
            }
        }else if (httpStatusCode == Constants.BAD_REQUEST) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_bad_request), Toast.LENGTH_SHORT).show();

        } else if (httpStatusCode == Constants.INTERNAL_SERVER_ERROR) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_internal_server_error), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.URL_NOT_FOUND) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_url_not_found), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.UNAUTHORIZE_ACCESS) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_unautorize_access), Toast.LENGTH_SHORT).show();
        } else if (httpStatusCode == Constants.CONNECTION_OUT) {
            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_connection_timed_out), Toast.LENGTH_SHORT).show();

        }
        mRetailOrderCenterAdapter.notifyDataSetChanged();
    }


}
