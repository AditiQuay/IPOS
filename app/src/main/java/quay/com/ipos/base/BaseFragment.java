package quay.com.ipos.base;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;

import quay.com.ipos.ui.CustomProgressDialog;


/**
 * The Class BaseFragment.
 */

public class BaseFragment extends Fragment
{
	/** The _progress dialog. */
	protected CustomProgressDialog _progressDialog = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
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
		_progressDialog = new CustomProgressDialog(getActivity(), getResources().getString(message));
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


}
