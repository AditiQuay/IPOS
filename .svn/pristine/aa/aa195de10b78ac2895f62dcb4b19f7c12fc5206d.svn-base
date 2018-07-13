package quay.com.ipos.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import quay.com.ipos.R;

public class CustomProgressDialog extends Dialog
{
    private TextView _tvLoadingText;
	//String _progressMessage;
	public CustomProgressDialog(Context context, String progressMessage)
	   {
			super(context);
		   dismiss();
		   ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, R.style.DialogThemeNoTitle);
		   LayoutInflater inflater = LayoutInflater.from(context);
		   View dialogView = inflater.inflate(R.layout.custom_progress_dialog, null);
		   _tvLoadingText = (TextView)dialogView.findViewById(R.id.tvLoadingText);
		   _tvLoadingText.setText(progressMessage);
		   requestWindowFeature(Window.FEATURE_NO_TITLE);
		   setContentView(dialogView);
		   AlertDialog.Builder alertDialog = new AlertDialog.Builder(contextThemeWrapper);
		   alertDialog.setView(dialogView);

		   alertDialog.create();
		   setCanceledOnTouchOutside(false);


		   if (isShowing()) {
			   dismiss();
		   } else {
			   show();
		   }
			//_progressMessage = progressMessage;

	   }
	/*@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		LayoutInflater inflater = (LayoutInflater) getLayoutInflater();
		View view = inflater.inflate(R.layout.custom_progress_dialog, null);
		_tvLoadingText = (TextView)view.findViewById(R.id.tvLoadingText);
		_tvLoadingText.setText(_progressMessage);
		setCanceledOnTouchOutside(true);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}*/

	
	public void setMessage(CharSequence message)
	{
		_tvLoadingText.setText(message);
	}
}