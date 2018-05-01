package quay.com.ipos.ui;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.modal.ProductList;
import quay.com.ipos.utility.Util;

/**
 * Created by aditi.bhuranda on 01-05-2018.
 */

public class OTCDiscountDelete extends DialogFragment
{
    TextView tvClearOTC,tvApplyOTC;
    private ToggleButton tbPerc,tbRs;
    EditText etDiscountAmt;
    View.OnClickListener mOnClickListener;
    static OTCDiscountDelete f;
    private boolean isOTC;
    private double otcDiscount=0.0;

    //private View pic;
//    public MyDialogFragment()
//    {
//    }
//    public MyDialogFragment(int points)
//    {
//        this.points = points;
//    }
    public OTCDiscountDelete() {
        super();
    }
    public static OTCDiscountDelete newInstance() {

        f= new OTCDiscountDelete();
        return f;
    }

    public void setDialogInfo(View.OnClickListener mOnClickListener)
    {
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.otc_discount, new LinearLayout(getActivity()), false);
//        tvClearOTC = view.findViewById(R.id.tvClearOTC);
//        etDiscountAmt = view.findViewById(R.id.etDiscountAmt);
//        tbRs = view.findViewById(R.id.tbRs);
//        tbPerc = view.findViewById(R.id.tbPerc);
//        tvClearOTC = view.findViewById(R.id.tvClearOTC);
//        tvApplyOTC = view.findViewById(R.id.tvApplyOTC);
//        tvClearOTC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                f.dismiss();
//            }
//        });
//        etDiscountAmt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//        tvApplyOTC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ApplyOTC();
//            }
//        });
////        tbRs.setOnClickListener(mOnClickListener);
////        tbPerc.setOnClickListener(mOnClickListener);
//        tbPerc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tbRs.setChecked(false);
//                tbPerc.setChecked(true);
//                tbRs.setTextColor(getActivity().getResources().getColor(R.color.accent_color));
//                tbPerc.setTextColor(getActivity().getResources().getColor(R.color.white));
//            }
//        });
//
//        tbRs.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                tbPerc.setChecked(false);
//                tbRs.setChecked(true);
//                tbRs.setTextColor(getActivity().getResources().getColor(R.color.white));
//                tbPerc.setTextColor(getActivity().getResources().getColor(R.color.accent_color));
//            }
//        });
//        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }

    private void ApplyOTC() {
        for(int i = 0; i < IPOSApplication.mProductList.size(); i++){
            ProductList.Datum datum = IPOSApplication.mProductList.get(i);
            isOTC = datum.isOTCselected();
            if(isOTC) {
                datum.setDiscSelected(true);
                datum.setItemSelected(false);
                datum.setOTCselected(false);
                if (tbPerc.isChecked())
                    otcDiscount = Double.parseDouble(etDiscountAmt.getText().toString()) * Double.parseDouble(datum.getSProductPrice()) / 100;
                else
                    otcDiscount = Double.parseDouble(datum.getSProductPrice()) - Double.parseDouble(etDiscountAmt.getText().toString());
                datum.setOTCDiscount(otcDiscount);
                IPOSApplication.mProductList.set(i, datum);

            }
        }
        if(isOTC)
            Util.showToast("OTC Discount Applied!",getActivity());
//        mRetailSalesAdapter.notifyDataSetChanged();
//        setUpdateValues(IPOSApplication.mProductList);
    }
}