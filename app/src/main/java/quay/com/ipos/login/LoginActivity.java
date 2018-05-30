package quay.com.ipos.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quay.com.ipos.IPOSAPI;
import quay.com.ipos.R;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.base.RunTimePermissionActivity;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.modal.LoginResult;
import quay.com.ipos.realmbean.RealmController;
import quay.com.ipos.service.ServiceTask;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;
import quay.com.ipos.utility.NetUtil;
import quay.com.ipos.utility.Prefs;
import quay.com.ipos.utility.SharedPrefUtil;

public class LoginActivity extends RunTimePermissionActivity implements InitInterface, View.OnClickListener, View.OnFocusChangeListener, ServiceTask.ServiceResultListener {

    private TextView textViewWelcome;
    private EditText editTextEmail, editTextPassword;
    private Button btnLogin;
    private TextView textViewForgotPassword, textViewMainTitle;
    private String sAppVersion, sDeviceType, sDeviceModel, sDeviceVersion, sDeviceIMEI;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS = 20;
    private static final String[] ALL_PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;

        findViewById();
        applyInitValues();
        applyTypeFace();


    }

    /*
*Getting device information like deviceId,AppVersion,DeviceModel and IMEI number.
*
*/
    private void getDeviceInformation() {
        //App version
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            sAppVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //Device type
        sDeviceType = "android";
        sDeviceModel = Build.MODEL;
        sDeviceVersion = Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();

        //IMEI number;
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI;
        try {
            assert telephonyManager != null;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            IMEI = telephonyManager.getDeviceId();
        } catch (Exception e) {
            IMEI = "Error!!";
        }
        sDeviceIMEI = IMEI;
        if (applyLocalValidation()) {
            if (NetUtil.isNetworkAvailable(mContext)) {
                validateWithServer(sAppVersion, sDeviceType, sDeviceModel, sDeviceVersion, IMEI);
            } else {
                Toast.makeText(mContext, getResources().getString(R.string.no_internet_connection_warning_server_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void validateWithServer(String sAppVersion, String sDeviceType, String sDeviceModel, String sDeviceVersion, String sDeviceIMEI) {
        showProgress("Authenticating please wait....");

        String sFcmToken = Prefs.getStringPrefs(Constants.FCM_KEY);
        final String sEmail = editTextEmail.getText().toString().trim();
        final String sPassword = editTextPassword.getText().toString().trim();

        LoginParams loginParams = new LoginParams();
        loginParams.setUserEmailId(sEmail.trim());
        loginParams.setUserPwd(sPassword.trim());
        loginParams.setUserDeviceActive(1);
        loginParams.setDeviceIMEI(sDeviceIMEI);
        loginParams.setDeviceVersion(sDeviceVersion);
        loginParams.setDeviceModel(sDeviceModel);
        loginParams.setDeviceType(sDeviceType);
        loginParams.setDeviceToken(sFcmToken);
        loginParams.setAppVersion(sAppVersion);

        ServiceTask mTask = new ServiceTask();
        mTask.setApiUrl(IPOSAPI.WEB_SERVICE_BASE_URL);
        mTask.setApiMethod(IPOSAPI.WEB_SERVICE_LOGIN);
        mTask.setApiCallType(Constants.API_METHOD_POST);
        mTask.setParamObj(loginParams);
        mTask.setListener(this);
        mTask.setResultType(LoginResult.class);
        mTask.execute();
    }

    @Override
    public void findViewById() {
        textViewWelcome = findViewById(R.id.textViewWelcome);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewMainTitle = findViewById(R.id.textViewMainTitle);

        btnLogin.setOnClickListener(this);
        editTextEmail.setOnFocusChangeListener(this);
        editTextPassword.setOnFocusChangeListener(this);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        if (requestCode == REQUEST_PERMISSIONS) {
            getDeviceInformation();
            hideKeyboard();
        }

    }

    private boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            assert getSystemService(Context.INPUT_METHOD_SERVICE) != null;
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void applyInitValues() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            textViewWelcome.setText(mContext.getResources().getString(R.string.morning_text));
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            textViewWelcome.setText(mContext.getResources().getString(R.string.afternoon_text));
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            textViewWelcome.setText(mContext.getResources().getString(R.string.evening_text));
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            textViewWelcome.setText(mContext.getResources().getString(R.string.night_text));
        }

    }

    @Override
    public void applyTypeFace() {
        FontUtil.applyTypeface(textViewForgotPassword, FontUtil.getTypceFaceRobotoRegular(LoginActivity.this));
        FontUtil.applyTypeface(textViewWelcome, FontUtil.getTypceFaceRobotoMedium_0(LoginActivity.this));
        FontUtil.applyTypeface(textViewMainTitle, FontUtil.getTypceFaceRobotoMedium_0(LoginActivity.this));
        FontUtil.applyTypeface(editTextEmail, FontUtil.getTypceFaceRobotoRegular(LoginActivity.this));
        FontUtil.applyTypeface(editTextPassword, FontUtil.getTypceFaceRobotoRegular(LoginActivity.this));
        FontUtil.applyTypeface(btnLogin, FontUtil.getTypceFaceRobotoBold(LoginActivity.this));
    }

    @Override
    public boolean applyLocalValidation() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        if (!validateEmail(email)) {
            editTextEmail.setError(getResources().getString(R.string.edittext_email_error_activity_login));
        } else if (TextUtils.isEmpty(password)) {
            editTextPassword.setError(getResources().getString(R.string.edittext_password_error_activity_login));
        } else {
            return true;
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                LoginActivity.super.requestAppPermissions(ALL_PERMISSIONS, R.string.runtime_permissions_txt, REQUEST_PERMISSIONS, 0);
            } else {
                getDeviceInformation();
                hideKeyboard();
            }

        }
    }

    @Override
    public void onResult(String serviceUrl, String serviceMethod, int httpStatusCode, Type resultType, Object resultObj, String serverResponse) {
        dismissProgress();
        if (httpStatusCode == Constants.SUCCESS) {
            if (resultObj != null) {


                LoginResult loginResult = (LoginResult) resultObj;
                Gson gson = new GsonBuilder().create();
                gson.fromJson(serverResponse, LoginResult.class);
                SharedPrefUtil.putBoolean(Constants.ISLOGGEDIN.trim(), true, mContext);
                SharedPrefUtil.setAccessToken(Constants.ACCESS_TOKEN.trim(), loginResult.getUserAccess().getAccessToken(), mContext);
                SharedPrefUtil.setStoreID(Constants.STORE_ID.trim(), loginResult.getUserAccess().getWorklocationID(), mContext);


                new RealmController().saveUserDetail(serverResponse);
                //new  RealmController().saveUserDetail(userdata);
                Intent i = new Intent(mContext, MainActivity.class);
                startActivity(i);

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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        switch (v.getId()) {
            case R.id.editTextEmail:
                if (!hasFocus) {
                    if (!validateEmail(email.trim())) {
                        editTextEmail.setError(getResources().getString(R.string.edittext_email_error_activity_login));
                    } else {
                        editTextEmail.setError(null);
                    }
                }
                break;
            case R.id.editTextPassword:
                if (hasFocus) {
                    {
                        if (TextUtils.isEmpty(password)) {
                            editTextPassword.setError(getResources().getString(R.string.edittext_password_error_activity_login));
                        }
                    }
                }
            default:
                break;

        }
    }
}
