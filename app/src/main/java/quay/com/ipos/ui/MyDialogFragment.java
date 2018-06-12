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

import java.lang.reflect.Type;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.base.BaseDialogFragment;
import quay.com.ipos.modal.CustomerPointsRedeemRequest;
import quay.com.ipos.modal.OrderSubmitResult;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;

public class MyDialogFragment extends BaseDialogFragment implements View.OnClickListener,ServiceTask.ServiceResultListener
{
    TextView tvRedeemPoints,tvResendOTP;
    ImageView imageViewCancel;
    EditText etPointToRedeem,etRedeemValue,etOTP;
    Button buttonSendOtp,buttonVerify,buttonRedeem;
    LinearLayout llVerifyRedeem;
    int points=0,pointsPer=0, points1;
    String mCustomerEmail,mCustomerID;
    View.OnClickListener mOnClickListener;
    static MyDialogFragment f;
    double redeemValue;
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

    public void setDialogInfo(View.OnClickListener mOnClickListener, int points, int pointsPer, String mCustomerEmail, String mCustomerID)
    {
        this.mOnClickListener = mOnClickListener;
        this.points = points;
        this.pointsPer = pointsPer;
        this.mCustomerID = mCustomerID;
        this.mCustomerEmail = mCustomerEmail;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.retail_sales_loyalty_points, new LinearLayout(getActivity()), false);
//        points = getArguments().getInt("points",0);
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
                if(!charSequence.toString().trim().equals("")) {
                    points1 = Integer.parseInt(charSequence.toString());
                    if(points1<=points) {
                        redeemValue = points1 * pointsPer;
                        etRedeemValue.setText(redeemValue + "");
                    }
                }else {
                    etRedeemValue.setText(0 + "");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        redeemValue = points*pointsPer;
        etRedeemValue.setText(redeemValue+"");


//        Bundle mBundle = new Bundle();
////        mBundle.putInt(Constants.KEY_CUSTOMER_POINTS_EMAIL,points);
//        mBundle.putInt(Constants.KEY_CUSTOMER_POINTS,points1);
//        buttonSendOtp.setTag(mBundle);
//        if(redeemValue>0){
        buttonSendOtp.setOnClickListener(this);
//        }else {
//            Util.showToast("No points to redeem", getActivity() );
//        }

        buttonRedeem.setOnClickListener(this);
        buttonVerify.setOnClickListener(this);
        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }
    private void sendOTPtoServer() {
        CustomerPointsRedeemRequest customerPointsRedeemRequest = new CustomerPointsRedeemRequest();
        customerPointsRedeemRequest.setCustomerId(mCustomerID);
        customerPointsRedeemRequest.setEmailId("aditi.bhuranda@quayintech.com");
        customerPointsRedeemRequest.setEmployeeCode(Prefs.getStringPrefs(Constants.employeeCode.trim()));
        customerPointsRedeemRequest.setPointsRedeemValue((int)redeemValue);
        customerPointsRedeemRequest.setPointsToRedeem(points1);
        showProgressDialog(R.string.please_wait);
        ServiceTask mServiceTask = new ServiceTask();
        mServiceTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_ORDER_SUBMIT);
        mServiceTask.setParamObj(customerPointsRedeemRequest);
        mServiceTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mServiceTask.setListener(this);
        mServiceTask.setResultType(null);
        mServiceTask.execute();
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.buttonSendOtp:
                if(redeemValue>0){
//                    sendOTPtoServer();
                }else {
                    Util.showToast("No points to redeem", getActivity() );
                }

                break;
        }
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {

    }
}