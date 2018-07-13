/**
 * 
 */
package quay.com.ipos.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatEditText;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.EditText;

import quay.com.ipos.R;
import quay.com.ipos.utility.Util;

/**
 * @author Lata
 * 
 */
public final class CustomEditText extends AppCompatEditText {

	/**
	 * @param context
	 */
	public CustomEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) {
			Util.setTypeface(attrs, this);
		}
		setHintText();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (!isInEditMode()) {
			Util.setTypeface(attrs, this);
		}
		setHintText();
		
	}
	private void setHintText(){
		if(this.getTag() !=(null)){
		if(!this.getHint().equals("")&& this.getTag().equals(getResources().getString(R.string.mandatory)) ){
			String _hint = this.getHint().toString()+getResources().getString(R.string.superscript);
			this.setHintTextColor(Color.WHITE);
			this.setHint(Html.fromHtml(_hint));
		}
		}
	}

}
