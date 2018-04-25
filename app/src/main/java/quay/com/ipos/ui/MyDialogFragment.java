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

import quay.com.ipos.R;

public class MyDialogFragment extends DialogFragment
{
    TextView tvRedeemPoints,tvResendOTP;
    ImageView imageViewCancel;
    EditText etPointToRedeem,etRedeemValue,etOTP;
    Button buttonSendOtp,buttonVerify,buttonRedeem;
    LinearLayout llVerifyRedeem;
    int points;
    View.OnClickListener mOnClickListener;
    static MyDialogFragment f;
    //private View pic;
//    public MyDialogFragment()
//    {
//    }
//    public MyDialogFragment(int points)
//    {
//        this.points = points;
//    }
    public MyDialogFragment() {
        super();
    }
    public static MyDialogFragment newInstance() {

        f= new MyDialogFragment();
        return f;
    }

    public void setDialogInfo(View.OnClickListener mOnClickListener)
    {
       this.mOnClickListener = mOnClickListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.retail_sales_loyalty_points, new LinearLayout(getActivity()), false);
        points = getArguments().getInt("points",0);
        imageViewCancel = view.findViewById(R.id.imageViewCancel);
        tvRedeemPoints = view.findViewById(R.id.tvRedeemPoints);
        etPointToRedeem = view.findViewById(R.id.etPointToRedeem);
        etRedeemValue = view.findViewById(R.id.etRedeemValue);
        buttonSendOtp = view.findViewById(R.id.buttonSendOtp);
        tvResendOTP = view.findViewById(R.id.tvResendOTP);
        llVerifyRedeem = view.findViewById(R.id.llVerifyRedeem);
        etOTP = view.findViewById(R.id.etOTP);
        buttonVerify = view.findViewById(R.id.buttonVerify);
        buttonRedeem = view.findViewById(R.id.buttonRedeem);
        imageViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                f.dismiss();
            }
        });
        tvRedeemPoints.setText(points + " Pts");
        etPointToRedeem.setText(points+"");
        etPointToRedeem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                points = Integer.parseInt(charSequence.toString());
                double redeemValue = points*0.1;
                etRedeemValue.setText(redeemValue+"");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        double redeemValue = points*0.1;
        etRedeemValue.setText(redeemValue+"");
        buttonSendOtp.setOnClickListener(mOnClickListener);
        buttonRedeem.setOnClickListener(mOnClickListener);
        buttonVerify.setOnClickListener(mOnClickListener);
        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }
}