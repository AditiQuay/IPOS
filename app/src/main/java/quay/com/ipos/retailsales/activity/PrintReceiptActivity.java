package quay.com.ipos.retailsales.activity;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.base.BaseActivity;
import quay.com.ipos.modal.PrintViewResult;
import quay.com.ipos.retailsales.adapter.GSTSummaryAdapter;
import quay.com.ipos.retailsales.adapter.ItemDetailAdapter;
import quay.com.ipos.retailsales.adapter.PaymentAdapter;
import quay.com.ipos.ui.ItemDecorationAlbumColumns;
import quay.com.ipos.ui.LinearLayoutManagerDummy;
import quay.com.ipos.ui.WrapContentLinearLayoutManager;
import quay.com.ipos.utility.AppLog;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 29-06-2018.
 */

public class PrintReceiptActivity extends BaseActivity {

    String json;
    String jsonFrom;
    private LinearLayout llRedeem;
    private RecyclerView rvItemDetails,rvGST,rvPayment;
    private TextView tvStoreName,tvStoreAddress,tvStorePhone,tvStoreEmail,tvStoreCIN,tvStoreGSTIN,tvTaxVoice,tvBillNumber,
            tvDateTime,tvCustomerDetails,tvItemDetails,tvItemSize,tvTotalQty,tvTotalAmount,tvTotalDiscountPrice,
            tvTotalPoints,tvTotalRedeemValue,tvNetTotal,tvCGSTPrice,tvSGSTPrice,tvRoundingOffPrice,tvSaleValue,
            tvPaymentDetails,tvPaidByCash,tvTenderChanges,tvPaidByCard,tvCardType,tvCardNumber,tvExpiryDate,tvGSTSummary,tvTotalGSTValue;
    private LinearLayoutManagerDummy mLayoutManager;
    private ItemDetailAdapter mItemDetailAdapter;
    private GSTSummaryAdapter mGSTSummaryAdapter;
    private PaymentAdapter paymentAdapter;
    ArrayList<PrintViewResult.ItemDetail> itemDetails = new ArrayList<>();
    ArrayList<PrintViewResult.PaymentsDetail> paymentsDetails = new ArrayList<>();
    ArrayList<PrintViewResult.GstSummary> gstSummaries = new ArrayList<>();
    PrintViewResult mPrintViewResult;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_print_view);
        initializeComponent();
        try {
            if (getIntent() != null) {
                json = getIntent().getStringExtra(Constants.RECEIPT);
                jsonFrom = getIntent().getStringExtra(Constants.RECEIPT_FROM);
                if (jsonFrom.equalsIgnoreCase(Constants.paymentMode)) {
                    if (!json.equalsIgnoreCase("")) {
                        mPrintViewResult = Util.getCustomGson().fromJson(json, PrintViewResult.class);
                        setValues();
                    }
                }
            }
        }catch (Exception e){
            AppLog.e(PrintReceiptActivity.class.getSimpleName(),e.getMessage());
        }

    }

    private void setValues() {
        itemDetails.addAll(mPrintViewResult.getItemDetails());
        mItemDetailAdapter.notifyDataSetChanged();
        paymentsDetails.addAll(mPrintViewResult.getPaymentsDetails());
        paymentAdapter.notifyDataSetChanged();
        gstSummaries.addAll(mPrintViewResult.getGstSummary());
        mGSTSummaryAdapter.notifyDataSetChanged();
        tvStoreName.setText(mPrintViewResult.getLocationName()+"");
        tvStoreAddress.setText(mPrintViewResult.getBusinessPlaceName()+"");
        tvStorePhone.setText("Phone: "+mPrintViewResult.getLocationPhone1()+"");
        tvStoreEmail.setText("Email: "+mPrintViewResult.getLocationEmail1()+"");
        tvStoreGSTIN.setText("GSTIN: "+mPrintViewResult.getGstin()+"");
        tvStoreCIN.setText("CIN: "+mPrintViewResult.getCin()+"");
        tvBillNumber.setText(mPrintViewResult.getOrderNo()+"");
        tvDateTime.setText(mPrintViewResult.getOrderDate()+"");
        if(mPrintViewResult.getCustomerName()!=null && mPrintViewResult.getCustomerName().equalsIgnoreCase(""))
            tvCustomerDetails.setText(mPrintViewResult.getMobile()+" - "+mPrintViewResult.getCustomerName());
        else {
            tvCustomerDetails.setText("NA");
        }
        tvItemSize.setText(itemDetails.size()+" items");
        tvTotalQty.setText("Qty "+ mPrintViewResult.getTotalQty());
        tvTotalAmount.setText(Util.getIndianNumberFormat( mPrintViewResult.getTotalAmount()));
        tvTotalDiscountPrice.setText(Util.getIndianNumberFormat(mPrintViewResult.getTotalDiscountAmount()));
        tvNetTotal.setText(Util.getIndianNumberFormat(mPrintViewResult.getNetTotalAmount()));
        tvCGSTPrice.setText(Util.getIndianNumberFormat(mPrintViewResult.getTotalCgst()));
        tvSGSTPrice.setText(Util.getIndianNumberFormat(mPrintViewResult.getTotalSgst()));
        tvRoundingOffPrice.setText(Util.getIndianNumberFormat(mPrintViewResult.getRoundingOff()));
        tvSaleValue.setText(Util.getIndianNumberFormat( mPrintViewResult.getTotalSaleAmount()));
        tvTotalGSTValue.setText(Util.getIndianNumberFormat( mPrintViewResult.getTotalIgst()));
//        if(mPrintViewResult.get)
    }

    private void initializeComponent() {
        tvItemDetails = findViewById(R.id.tvItemDetails);
        tvTaxVoice = findViewById(R.id.tvTaxVoice);
        tvPaymentDetails = findViewById(R.id.tvPaymentDetails);
        tvGSTSummary = findViewById(R.id.tvGSTSummary);
        tvStoreName = findViewById(R.id.tvStoreName);
        tvStoreAddress = findViewById(R.id.tvStoreAddress);
        tvStorePhone = findViewById(R.id.tvStorePhone);
        tvStoreEmail = findViewById(R.id.tvStoreEmail);
        tvStoreCIN = findViewById(R.id.tvStoreCIN);
        tvStoreGSTIN = findViewById(R.id.tvStoreGSTIN);
        tvBillNumber = findViewById(R.id.tvBillNumber);
        tvDateTime = findViewById(R.id.tvDateTime);
        tvCustomerDetails = findViewById(R.id.tvCustomerDetails);
        tvItemSize = findViewById(R.id.tvItemSize);
        tvTotalQty = findViewById(R.id.tvTotalQty);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTotalDiscountPrice = findViewById(R.id.tvTotalDiscountPrice);
        tvTotalPoints = findViewById(R.id.tvTotalPoints);
        tvTotalRedeemValue = findViewById(R.id.tvTotalRedeemValue);
        tvNetTotal = findViewById(R.id.tvNetTotal);
        tvCGSTPrice = findViewById(R.id.tvCGSTPrice);
        tvSGSTPrice = findViewById(R.id.tvSGSTPrice);
        tvRoundingOffPrice = findViewById(R.id.tvRoundingOffPrice);
        tvSaleValue = findViewById(R.id.tvSaleValue);
        tvPaidByCash = findViewById(R.id.tvPaidByCash);
        tvTenderChanges = findViewById(R.id.tvTenderChanges);
        tvPaidByCard = findViewById(R.id.tvPaidByCard);
        tvCardType = findViewById(R.id.tvCardType);
        tvCardNumber = findViewById(R.id.tvCardNumber);
        tvExpiryDate = findViewById(R.id.tvExpiryDate);
        tvTotalGSTValue = findViewById(R.id.tvTotalGSTValue);
        rvItemDetails = findViewById(R.id.rvItemDetails);
        rvGST = findViewById(R.id.rvGST);
        rvPayment = findViewById(R.id.rvPayment);

        //        >>>>>>>>>>>>>>>>>>>>>>>>>> rvItemDetails <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        rvItemDetails.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManagerDummy(mContext, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        rvItemDetails.setLayoutManager(mLayoutManager);

        rvItemDetails.addItemDecoration(
                new ItemDecorationAlbumColumns(mContext.getResources().getDimensionPixelSize(R.dimen.dim_5),
                        mContext.getResources().getInteger(R.integer.photo_list_preview_columns)));


        //        >>>>>>>>>>>>>>>>>>>>>>>>>> rvGST <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        rvGST.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManagerDummy(mContext, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        rvGST.setLayoutManager(mLayoutManager);
        rvGST.addItemDecoration(
                new ItemDecorationAlbumColumns(mContext.getResources().getDimensionPixelSize(R.dimen.dim_5),
                        mContext.getResources().getInteger(R.integer.photo_list_preview_columns)));



        //        >>>>>>>>>>>>>>>>>>>>>>>>>> rvPayment <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        rvPayment.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManagerDummy(mContext, LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setAutoMeasureEnabled(true);
        rvPayment.setLayoutManager(mLayoutManager);
        rvPayment.addItemDecoration(
                new ItemDecorationAlbumColumns(mContext.getResources().getDimensionPixelSize(R.dimen.dim_5),
                        mContext.getResources().getInteger(R.integer.photo_list_preview_columns)));


        tvItemDetails.setPaintFlags(tvItemDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvTaxVoice.setPaintFlags(tvTaxVoice.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvPaymentDetails.setPaintFlags(tvPaymentDetails.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvGSTSummary.setPaintFlags(tvGSTSummary.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        setAdapterItemDetails();
    }

    private void setAdapterItemDetails() {
        mItemDetailAdapter = new ItemDetailAdapter(this, itemDetails);
        rvItemDetails.setAdapter(mItemDetailAdapter);
        mGSTSummaryAdapter = new GSTSummaryAdapter(this,gstSummaries);
        rvGST.setAdapter(mGSTSummaryAdapter);
        paymentAdapter = new PaymentAdapter(this,paymentsDetails);
        rvPayment.setAdapter(paymentAdapter);
    }
}
