package quay.com.ipos.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import quay.com.ipos.R;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.listeners.InitInterface;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.FontUtil;

public class LoginActivity extends AppCompatActivity implements InitInterface, View.OnClickListener {
    private TextView textViewWelcome;
    private EditText editTextEmail, editTextPassword;
    private Button btnLogin;
    private TextView textViewForgotPassword, textViewMainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewById();
        applyInitValues();
        applyTypeFace();
        applyLocalValidation();

    }

    private void getDeviceToken() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Constants.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);
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
    }

    @Override
    public void applyInitValues() {



    }

    @Override
    public void applyTypeFace() {
        ///
        FontUtil.applyTypeface(textViewForgotPassword, FontUtil.getTypceFaceRobotoRegular(LoginActivity.this));
        FontUtil.applyTypeface(textViewWelcome, FontUtil.getTypceFaceRobotoMedium_0(LoginActivity.this));
        FontUtil.applyTypeface(textViewMainTitle, FontUtil.getTypceFaceRobotoMedium_0(LoginActivity.this));
        FontUtil.applyTypeface(editTextEmail, FontUtil.getTypceFaceRobotoRegular(LoginActivity.this));
        FontUtil.applyTypeface(editTextPassword, FontUtil.getTypceFaceRobotoRegular(LoginActivity.this));
        FontUtil.applyTypeface(btnLogin, FontUtil.getTypceFaceRobotoBold(LoginActivity.this));
    }

    @Override
    public boolean applyLocalValidation() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            getDeviceToken();
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
