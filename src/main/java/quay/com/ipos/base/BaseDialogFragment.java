package quay.com.ipos.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import quay.com.ipos.ui.CustomProgressDialog;

import static quay.com.ipos.utility.Util.getStringRes;

/**
 * Created by aditi.bhuranda on 11-06-2018.
 */

public class BaseDialogFragment  extends DialogFragment {
    protected Fragment fragmentCurrent;
    protected Activity mActivity;
    public boolean isReplaced = false;
    /**
     * The _progress dialog.
     */
    protected CustomProgressDialog _progressDialog = null;

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    /**
     * Show progress dialog.
     *
     * @param title   the title
     * @param message the message
     */
    protected void showProgressDialog(int title, int message) {
        if (null != _progressDialog) {
            _progressDialog.setTitle(title);
            _progressDialog.setMessage(getResources().getString(message));
            _progressDialog.show();
        }
    }

    public void addFragment(Fragment fragment, int containerId) {
        fragmentCurrent = fragment;
        getChildFragmentManager()
                .beginTransaction()
                .add(containerId, fragment)
                .commitAllowingStateLoss();
    }

    public void replaceFragment(Fragment fragment, int containerId) {
        isReplaced = true;
        fragmentCurrent = fragment;
        getChildFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commitAllowingStateLoss();
    }

    /**
     * Show progress dialog.
     *
     * @param message the message
     */
    protected void showProgressDialog(Context context, int message) {
        _progressDialog = new CustomProgressDialog(context, getResources().getString(message));
        _progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        _progressDialog.setCancelable(true);
        if (null != _progressDialog) {
            //  _progressDialog.setMessage(getResources().getString(message));
            if (!_progressDialog.isShowing())
                _progressDialog.show();
        }
    }

    /**
     * Show progress dialog.
     *
     * @param messageText the message text
     */
    protected void showProgressDialog(String messageText) {
        if (null != _progressDialog) {
            _progressDialog.setMessage(messageText);
            _progressDialog.show();
        }
    }

    public void showProgressDialog(int msgId) {
        String message = getStringRes(msgId);
        showProgressDialog(message);
    }

    /**
     * Hide progress dialog.
     */
    protected void hideProgressDialog() {
        if (null != _progressDialog && _progressDialog.isShowing()) {
            _progressDialog.dismiss();
        }
    }


}
