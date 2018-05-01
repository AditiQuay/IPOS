package quay.com.ipos.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import quay.com.ipos.R;

public class CustomProgressDialog extends AlertDialog
{
    private TextView _tvLoadingText;
	String _progressMessage;
	public CustomProgressDialog(Context context, String progressMessage)
	   {
			super(context);
			_progressMessage = progressMessage;
	   }
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.custom_progress_dialog);
		_tvLoadingText = (TextView)findViewById(R.id.tvLoadingText);
		_tvLoadingText.setText(_progressMessage);
		setCanceledOnTouchOutside(false);
	}

	
	@Override
	public void setMessage(CharSequence message) 
	{
		super.setMessage(message);
	}
}