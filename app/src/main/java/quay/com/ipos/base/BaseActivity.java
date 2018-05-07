package quay.com.ipos.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;


import quay.com.ipos.R;

import static quay.com.ipos.utility.Util.getStringRes;


public class BaseActivity extends AppCompatActivity {
    //Save the path as a string value

    protected Context mContext;
    private ProgressDialog mProgressDialog;
    public boolean isReplaced = false;

    public static Activity mActivity;
    protected Fragment fragmentCurrent;
    public FragmentManager fragmentManager;

    private String mImageUrl;
    private boolean isUpdateImage = false;

    private SparseIntArray mErrorString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;

    }


    public int setLayoutId() {
        return R.layout.activity_main;
    }

    public void addFragment(Fragment fragment, int containerId) {
        fragmentCurrent = fragment;
        fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment).addToBackStack("tag")
                .commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment, int containerId) {
        isReplaced = true;
        fragmentCurrent = fragment;
        fragmentManager = getSupportFragmentManager();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
<<<<<<< HEAD
                .addToBackStack("tag")
=======
                .addToBackStack("fragment")
>>>>>>> 1ea0ce6001fe680ca9355027564dbefd55b8001f
                .commitAllowingStateLoss();
    }

    // get visible fragment
    public Fragment getVisibleFragment() {
        Fragment mFrag = ((Fragment) fragmentManager.findFragmentById(R.id.fragment_container));
        return mFrag;
    }

    public void showProgress() {
        showProgress(getStringRes(R.string.msg_load_default));
    }

    public void showProgress(String msg) {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.setMessage(msg);
        } else {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(msg);
            mProgressDialog.show();
        }
    }

    public void showProgress(int msgId) {
        String message = getStringRes(msgId);
        showProgress(message);
    }

    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }




    protected boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }






}
