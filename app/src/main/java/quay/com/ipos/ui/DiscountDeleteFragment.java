package quay.com.ipos.ui;

import android.app.Dialog;
import android.graphics.Typeface;
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

import quay.com.ipos.R;
import quay.com.ipos.ddr.fragment.NewOrderFragment;
import quay.com.ipos.modal.OrderList;
import quay.com.ipos.modal.ProductList;

/**
 * Created by aditi.bhuranda on 25-04-2018.
 */

public class DiscountDeleteFragment extends DialogFragment
{
    TextView tvItemName,tvItemWeight,tvItemQty,tvItemPrice,tvDiscount,tvDiscountPrice;
    Button btnNo,btnYes;
    ImageView ImvClose;
    int points;
    View.OnClickListener mOnClickListener;
    static DiscountDeleteFragment f;
    ProductList.Datum datum;
    OrderList.Datum datum1;
    public DiscountDeleteFragment() {
        super();
    }

    public static DiscountDeleteFragment newInstance() {

        f= new DiscountDeleteFragment();
        return f;
    }

    public void setDialogInfo(View.OnClickListener mOnClickListener, ProductList.Datum datum)
    {
        this.mOnClickListener = mOnClickListener;
        this.datum = datum;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.delete_discount_popup, new LinearLayout(getActivity()), false);
        ImvClose = view.findViewById(R.id.ImvClose);
        tvItemName = view.findViewById(R.id.tvItemName);
        tvItemWeight = view.findViewById(R.id.tvItemWeight);
        tvItemQty = view.findViewById(R.id.tvItemQty);
        tvItemPrice = view.findViewById(R.id.tvItemPrice);
        tvDiscount = view.findViewById(R.id.tvDiscount);
        tvDiscountPrice = view.findViewById(R.id.tvDiscountPrice);
        btnNo = view.findViewById(R.id.btnNo);
        btnYes = view.findViewById(R.id.btnYes);

        btnNo.setOnClickListener(mOnClickListener);
        ImvClose.setOnClickListener(mOnClickListener);
        btnYes.setOnClickListener(mOnClickListener);
        if(datum!=null) {
            tvItemName.setText(datum.getSProductName());
            tvItemWeight.setText(datum.getSProductWeight() + " gm");
            tvItemQty.setText("Qty " + datum.getQty());
            tvItemPrice.setText(getResources().getString(R.string.Rs) + " " + datum.getSProductPrice());
            tvDiscount.setText(datum.getSDiscountName());
            Double discount = (Double.parseDouble(datum.getSDiscountPrice()) * Double.parseDouble(datum.getSProductPrice())) / 100;
            tvDiscountPrice.setText(getResources().getString(R.string.Rs) + discount);
        }else {
            tvItemName.setText(datum1.getSProductName());
            tvItemWeight.setText(datum1.getSProductWeight() + " gm");
            tvItemQty.setText("Qty " + datum1.getQty());
            tvItemPrice.setText(getResources().getString(R.string.Rs) + " " + datum1.getSProductPrice());
            tvDiscount.setText(datum1.getSDiscountName());
            Double discount = (Double.parseDouble(datum1.getSDiscountPrice()) * Double.parseDouble(datum1.getSProductPrice())) / 100;
            tvDiscountPrice.setText(getResources().getString(R.string.Rs) + discount);
        }
        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }

    public void setDialogInfoOrder(View.OnClickListener mOnClickListener, OrderList.Datum datum) {
        this.mOnClickListener = mOnClickListener;
        this.datum1 = datum;
    }
}