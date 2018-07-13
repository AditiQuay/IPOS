package quay.com.ipos.base;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import quay.com.ipos.ui.CustomProgressDialog;

/**
 * Created by aditi.bhuranda on 16-04-2018.
 */

public class BaseFragmentActivity extends FragmentActivity {
    /** The Constant TAG. */
//	private static final String TAG = MKHBaseFragmentActivity.class.getSimpleName();

    /** The _progress dialog. */
//	protected ProgressDialog _progressDialog = null;
    protected CustomProgressDialog _progressDialog = null;

    /** The receiver. */
    private BroadcastReceiver _receiver = null;

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onPause()
     */
    @Override
    protected void onPause() {
        super.onPause();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onResume()
     */
    @Override
    protected void onResume()
    {
        super.onResume();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//		_progressDialog = new CustomProgressDialog(this);
//		_progressDialog.setCancelable(false);
    }


    /**
     * Show progress dialog.
     *
     * @param title
     *            the title
     * @param message
     *            the message
     */
    protected void showProgressDialog(int title, int message) {
        if (null != _progressDialog) {
            _progressDialog.setTitle(title);
            _progressDialog.setMessage(getResources().getString(message));
            _progressDialog.show();
        }
    }

    /**
     * Show progress dialog.
     *
     * @param message
     *            the message
     */
    protected void showProgressDialog(int message) {
        _progressDialog = new CustomProgressDialog(this, getResources().getString(message));
        _progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        _progressDialog.setCancelable(false);
        if (null != _progressDialog) {
            _progressDialog.setMessage(getResources().getString(message));
            _progressDialog.show();
        }
    }

    /**
     * Show progress dialog.
     *
     * @param messageText
     *            the message text
     */
    protected void showProgressDialog(String messageText) {
        if (null != _progressDialog) {
            _progressDialog.setMessage(messageText);
            _progressDialog.show();
        }
    }

    /**
     * Hide progress dialog.
     *
     */
    protected void hideProgressDialog() {
        if (null != _progressDialog && _progressDialog.isShowing()) {
            _progressDialog.dismiss();
        }
    }
    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onDestroy()
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (null != _receiver)
        {
            getApplicationContext().unregisterReceiver(_receiver);
        }
    }



    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onStart()
     */
    @Override
    protected void onStart()
    {
        super.onStart();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onStop()
     */
    @Override
    protected void onStop() {
        super.onStop();
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Activity#onActivityResult(int, int,
     * android.content.Intent)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.slidingmenu.lib.app.SlidingFragmentActivity#onSaveInstanceState(android
     * .os.Bundle)
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
