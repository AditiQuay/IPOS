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

/**
 * Created by aditi.bhuranda on 25-04-2018.
 */

public class DiscountDeleteFragment extends DialogFragment
{
    TextView txtclose,tvItemName,tvItemWeight,tvItemQty,tvItemPrice,tvDiscount,tvDiscountPrice;
    Button btnNo,btnYes;
    int points;
    View.OnClickListener mOnClickListener;
    static DiscountDeleteFragment f;

    public DiscountDeleteFragment() {
        super();
    }

    public static DiscountDeleteFragment newInstance() {

        f= new DiscountDeleteFragment();
        return f;
    }

    public void setDialogInfo(View.OnClickListener mOnClickListener)
    {
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.delete_discount_popup, new LinearLayout(getActivity()), false);
        txtclose = view.findViewById(R.id.txtclose);
        tvItemWeight = view.findViewById(R.id.tvItemWeight);
        tvItemQty = view.findViewById(R.id.tvItemQty);
        tvItemPrice = view.findViewById(R.id.tvItemPrice);
        tvDiscount = view.findViewById(R.id.tvDiscount);
        btnNo = view.findViewById(R.id.btnNo);
        btnYes = view.findViewById(R.id.btnYes);
        Typeface iconFont = FontManager.getTypeface(getActivity(), FontManager.FONTAWESOME);
        FontManager.markAsIconContainer(txtclose, iconFont);

        txtclose.setOnClickListener(mOnClickListener);
        btnNo.setOnClickListener(mOnClickListener);
        btnYes.setOnClickListener(mOnClickListener);
        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }
}