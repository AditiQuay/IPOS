package quay.com.ipos.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;

import quay.com.ipos.R;
import quay.com.ipos.base.MainActivity;
import quay.com.ipos.utility.Constants;
import quay.com.ipos.utility.NetUtil;
import quay.com.ipos.utility.NetworkErrorDialog;
import quay.com.ipos.utility.SharedPrefUtil;

/**
 * Created by niraj.kumar on 4/16/2018.
 */

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final int FILE_SELECT_CODE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (NetUtil.isNetworkAvailable(SplashActivity.this)) {
            redirectToActivity();
        } else {
            NetworkErrorDialog.networkErrorDialogShow(this, "");
        }
    }

    //Redirect to activity
    private void redirectToActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!SharedPrefUtil.getBoolean(Constants.ISLOGGEDIN, false, SplashActivity.this)) {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        }, 1000);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


}
