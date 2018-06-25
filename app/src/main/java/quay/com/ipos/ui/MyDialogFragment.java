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
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.base.BaseDialogFragment;
import quay.com.ipos.modal.CustomerPointsRedeemRequest;
import quay.com.ipos.modal.CustomerPointsRedeemResult;
import quay.com.ipos.modal.OrderSubmitResult;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.Util;

public class MyDialogFragment extends BaseDialogFragment implements View.OnClickListener,ServiceTask.ServiceResultListener
{

    public interface RedeemListener{
        void redeem(double pointsToRedeem,double pointsToRedeemValue);
    }

    RedeemListener mRedeemListener=null;
    TextView tvRedeemPoints,tvResendOTP;
    ImageView imageViewCancel;
    EditText etPointToRedeem,etRedeemValue,etOTP;
    Button buttonSendOtp,buttonVerify,buttonRedeem;
    LinearLayout llVerifyRedeem;
    double points=0,pointsPer=0, points1=0;
    String mCustomerEmail="",mCustomerID="";
    View.OnClickListener mOnClickListener;
    static MyDialogFragment f;
    double redeemValue=0;
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

    public void setDialogInfo(View.OnClickListener mOnClickListener, double points, double pointsPer, String mCustomerEmail, String mCustomerID, RedeemListener mRedeemListener)
    {
        this.mOnClickListener = mOnClickListener;
        this.points = points;
        this.pointsPer = pointsPer;
        this.mCustomerID = mCustomerID;
        this.mCustomerEmail = mCustomerEmail;
        this.mRedeemListener = mRedeemListener;

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
                IPOSApplication.totalpointsToRedeem = 0;
                IPOSApplication.totalpointsToRedeemValue = 0;
                f.dismiss();
            }
        });
        tvRedeemPoints.setText(points + " Pts");
        etPointToRedeem.setText("");
        etPointToRedeem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().equals("")) {
                    points1 = Double.parseDouble(charSequence.toString());
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
        points1 = points;
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
        tvResendOTP.setOnClickListener(this);
        buttonRedeem.setOnClickListener(this);
        buttonVerify.setOnClickListener(this);
        // Build dialog
        Dialog builder = new Dialog(getActivity());
        builder.setCancelable(false);
        builder.setCanceledOnTouchOutside(false);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(view);
        return builder;
    }
    private void sendOTPtoServer() {
        CustomerPointsRedeemRequest customerPointsRedeemRequest = new CustomerPointsRedeemRequest();
        customerPointsRedeemRequest.setCustomerId(mCustomerID);
        customerPointsRedeemRequest.setEmailId(mCustomerEmail);
        customerPointsRedeemRequest.setEmployeeCode(Prefs.getStringPrefs(Constants.employeeCode.trim()));
        customerPointsRedeemRequest.setPointsRedeemValue(redeemValue);
        customerPointsRedeemRequest.setPointsToRedeem(points1);
        if(sendVerify)
            customerPointsRedeemRequest.setRequestOTP(etOTP.getText().toString());
        else
            customerPointsRedeemRequest.setRequestOTP("");
        showProgressDialog(R.string.please_wait);
        ServiceTask mServiceTask = new ServiceTask();
        if(sendVerify)
            mServiceTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_ValidateCustomerPointsRedeemRequest);
        else
            mServiceTask.setApiMethod(IPOSAPI.WEB_SERVICE_RETAIL_CustomerPointsRedeemRequest);
        mServiceTask.setParamObj(customerPointsRedeemRequest);
        mServiceTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mServiceTask.setListener(this);
        mServiceTask.setResultType(CustomerPointsRedeemResult.class);
        mServiceTask.execute();
    }

    boolean sendOTP=false,sendVerify = false,sendRedeem=false;
    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.buttonSendOtp:
                if(!etPointToRedeem.getText().toString().equalsIgnoreCase("")) {
                    if (!sendOTP)
                        if (points1 > 0) {
                            if (redeemValue > 0) {
                                sendOTP = true;
                                etPointToRedeem.setEnabled(false);
                                etRedeemValue.setEnabled(false);
                                sendOTPtoServer();
                            } else
                                Util.showToast("No points to redeem", getActivity());
                        } else {
                            Util.showToast("No points to redeem", getActivity());
                        }
                }else {
                    Util.showToast("Enter points to redeem", getActivity());
                }
                break;
            case R.id.tvResendOTP:
                if(!sendVerify){
                    if(redeemValue>0) {
                        sendOTP = true;
                        etPointToRedeem.setEnabled(false);
                        etRedeemValue.setEnabled(false);
                        sendOTPtoServer();
                    }else
                        Util.showToast("No points to redeem", getActivity() );
                }else {
                    Util.showToast("ID already verified!", getActivity() );
                }
                break;
            case R.id.buttonVerify:
                if(!sendVerify)
                    if(redeemValue>0)
                    {
                        sendVerify = true;
                        if(!etOTP.getText().toString().trim().equalsIgnoreCase(""))
                            if(etOTP.getText().toString().trim().length()>=4)
                            {
                                sendOTPtoServer();
                                sendVerify = true;
                            }
                            else
                                Util.showToast("Please enter valid otp", getActivity() );
                        else
                            Util.showToast("Please enter otp", getActivity() );
                    }else
                    {
                        Util.showToast("No points to redeem", getActivity() );
                    }
                break;
            case R.id.buttonRedeem:
                if(!sendVerify)
                    if(redeemValue>0){
                        sendRedeem=true;
                        if(sendRedeem){
                            sendRedeem =false;
                            Util.showToast("Redeem points successfully", getActivity());
                            llVerifyRedeem.setVisibility(View.VISIBLE);
                            IPOSApplication.totalpointsToRedeem = points1;
                            IPOSApplication.totalpointsToRedeemValue = redeemValue;
                            mRedeemListener.redeem(points1,redeemValue);
                            f.dismiss();
                        }else {
                            sendRedeem =false;
                        }
                    }else {

                    }
                break;
        }
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        hideProgressDialog();
        if(serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_RETAIL_CustomerPointsRedeemRequest)){
            if(resultObj!=null) {
                CustomerPointsRedeemResult customerPointsRedeemResult = (CustomerPointsRedeemResult) resultObj;
                if(customerPointsRedeemResult!=null){
                    if(customerPointsRedeemResult.getError()==200){
                        Util.showToast(customerPointsRedeemResult.getMessage(), getActivity());
                        if(sendOTP) {
                            sendOTP=false;
                            Util.showToast(customerPointsRedeemResult.getMessage(), getActivity());
                            llVerifyRedeem.setVisibility(View.VISIBLE);

                            buttonVerify.setVisibility(View.VISIBLE);
                            buttonVerify.setBackgroundResource(R.drawable.button_drawable);
                            buttonVerify.setEnabled(true);

                            buttonRedeem.setVisibility(View.INVISIBLE);
                            buttonRedeem.setEnabled(false);
                            buttonRedeem.setBackgroundResource(R.drawable.button_rectangle_light_gray);

                            buttonSendOtp.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                            buttonSendOtp.setEnabled(false);
                        }else {
                            sendOTP=false;
                        }
                    }else {
                        Util.showToast(customerPointsRedeemResult.getErrorDescription(), getActivity());
                    }

                }
            }
        }else  if(serviceMethod.equalsIgnoreCase(IPOSAPI.WEB_SERVICE_RETAIL_ValidateCustomerPointsRedeemRequest)){
            if(resultObj!=null) {
                CustomerPointsRedeemResult customerPointsRedeemResult = (CustomerPointsRedeemResult) resultObj;
                if (customerPointsRedeemResult != null) {
                    if (customerPointsRedeemResult.getError() == 200) {
                        if (sendVerify) {
                            sendVerify = false;
                            Util.showToast(customerPointsRedeemResult.getMessage(), getActivity());
                            llVerifyRedeem.setVisibility(View.VISIBLE);

                            buttonVerify.setVisibility(View.VISIBLE);
                            buttonVerify.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                            buttonVerify.setEnabled(false);

                            buttonRedeem.setVisibility(View.VISIBLE);
                            buttonRedeem.setEnabled(true);
                            buttonRedeem.setBackgroundResource(R.drawable.button_drawable);

                            buttonSendOtp.setBackgroundResource(R.drawable.button_rectangle_light_gray);
                            buttonSendOtp.setEnabled(false);
                        } else {
                            sendVerify = false;
                        }
                    }
                }
            }

        }

    }
}