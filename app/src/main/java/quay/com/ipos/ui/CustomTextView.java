package quay.com.ipos.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.TextView;

import quay.com.ipos.utility.Util;

/**
 * The Class CustomTextView.
 */
@SuppressLint("NewApi")
public class CustomTextView extends AppCompatTextView
{
	/**
	 * Instantiates a new custom text view.
	 * 
	 * @param context
	 *            the context
	 */
	public CustomTextView(Context context) {
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
	public CustomTextView(Context context, AttributeSet attrs) {
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
	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		if (!isInEditMode()) {
			Util.setTypeface(attrs, this);
		}
	}
	
	/**
	 * Sets the text and max lines.
	 *
	 * @param text the new text and max lines
	 */
	public void setTextAndMaxLines(CharSequence text){
		
		ViewTreeObserver vto = getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				ViewTreeObserver vto = getViewTreeObserver();
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
					vto.removeGlobalOnLayoutListener(this);
				}
				else {
					vto.removeOnGlobalLayoutListener(this);
				}

				int height = getHeight();
				int lineHeight = getLineHeight();

				int maxlines = height / lineHeight;
				setMaxLines(maxlines);
			}
		});		
	}
}
