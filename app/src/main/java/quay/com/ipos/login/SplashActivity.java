package quay.com.ipos.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import quay.com.ipos.R;

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

//        if (NetUtil.isNetworkAvailable(SplashActivity.this)) {
//            if (checkPlayServices()) {
        redirectToActivity();
//            } else {
//                NetworkErrorDialog.playServiceErrorDialog(this, "");
//            }
//        } else {
//            NetworkErrorDialog.networkErrorDialogShow(this, "");
//        }
    }

    //Redirect to activity
    private void redirectToActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (!SharedPrefUtil.getBoolean(KeyConstants.ISLOGGEDIN, false, SplashActivity.this)){
//                    Intent i = new Intent(SplashActivity.this,LoginActivity.class);
//                    startActivity(i);
//                    finish();
//                }else {
//                    Intent i = new Intent(SplashActivity.this,LandingActivity.class);
//                    startActivity(i);
//                    finish();
//                }
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

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

//    //Check for google play service
//    private boolean checkPlayServices() {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
//                        .show();
//            } else {
//                Log.i(TAG, "This device is not supported.");
//                finish();
//            }
//            return false;
//        }
//        return true;
//    }

}
