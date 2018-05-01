package quay.com.ipos.ui;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.widget.Button;
import quay.com.ipos.utility.Util;

// TODO: Auto-generated Javadoc
/**
 * The Class Button
 */
public class CustomButton extends AppCompatButton {

	/**
	 * Instantiates a new custom text view.
	 * 
	 * @param context
	 *            the context
	 */
	public CustomButton(Context context) {
		super(context);
	}

	/**
	 * Instantiates a new custom text view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public CustomButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		if (!isInEditMode()) {
			Util.setTypeface(attrs, this);
		}
	}

	/**
	 * Instantiates a new custom text view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public CustomButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		if (!isInEditMode()) {
			Util.setTypeface(attrs, this);
		}
	}
}
