package com.quayintech.tasklib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import com.quayintech.tasklib.R;
import com.quayintech.tasklib.utils.TypefaceManager;

public class SquareDayView extends AppCompatButton implements Checkable {
    private boolean mChecked;
    private static final int[] CHECKED_STATE_SET = new int[]{android.R.attr.state_checked};
    private Drawable drawable;
    private int color;
    private int dayOfMonth;

    public SquareDayView(Context context) {
        super(context);
        setTypeface(TypefaceManager.getTypeface(context, TypefaceManager.Roboto.Regular));
        setTextColor(ContextCompat.getColor(context, (int) R.color.calendar_view_week_day_text_color));
        setTextSize(0, (float) context.getResources().getDimensionPixelSize(R.dimen.calendar_view_week_day_font_size));
        setGravity(17);
        setIncludeFontPadding(false);
        setAllCaps(false);
        setSelectionDrawable(new CalendarDaySelectionDrawable(context, CalendarDaySelectionDrawable.Mode.SINGLE));
        setPadding(0, 0, 0, 0);
        setBackgroundDrawable(null);
    }

    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
        setText(Integer.toString(dayOfMonth));
        m16460a();
    }

    public void setSelectionDrawable(Drawable drawable) {
        if (this.drawable != drawable) {
            if (this.drawable != null) {
                this.drawable.setCallback(null);
                unscheduleDrawable(this.drawable);
            }
            this.drawable = drawable;
            if (this.drawable != null) {
                this.drawable.setCallback(this);
                if (this.drawable.isStateful()) {
                    this.drawable.setState(getDrawableState());
                }

                this.drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
                this.drawable.setColorFilter(this.color, PorterDuff.Mode.SRC_ATOP);
            }
            invalidate();
            //ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void setAccentColor(int accentColor) {
        if (this.color != accentColor) {
            this.color = accentColor;
            if (this.drawable != null) {
                this.drawable.setColorFilter(this.color, PorterDuff.Mode.SRC_ATOP);
                invalidate();
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
    }

    public void setChecked(boolean checked) {
        if (this.mChecked != checked) {
            this.mChecked = checked;
            refreshDrawableState();
            setActivated(this.mChecked);
          invalidate();
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public void toggle() {
        setChecked(!isChecked());
    }

    public void setActivated(boolean isActivated) {
        boolean wasActivated = isActivated();
        super.setActivated(isActivated);
        if (wasActivated != isActivated) {
            invalidate();
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        if (this.drawable != null) {
            this.drawable.setBounds(0, 0, getMeasuredWidth(), getMeasuredHeight());
            //this.drawable.setBounds(0, 0, 100, 100);
        }
    }

    public void onDraw(Canvas canvas) {
        if (isChecked() && this.drawable != null) {
            Log.e("onDrawCalled", "true");
            this.drawable.draw(canvas);
        }
        super.onDraw(canvas);
    }

    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        return drawableState;
    }

    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.drawable;
    }

    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (this.drawable != null && this.drawable.isStateful()) {
            this.drawable.jumpToCurrentState();
        }
    }

    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.drawable != null && this.drawable.isStateful()) {
            this.drawable.setState(getDrawableState());
            invalidate();
        }
    }

    @TargetApi(21)
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (this.drawable != null) {
            this.drawable.setHotspot(x, y);
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        m16460a();
        super.onInitializeAccessibilityNodeInfo(info);
    }

    private void m16460a() {
       /* Resources resources = getResources();
        StringBuilder tmp = new StringBuilder(resources.getString(R.string.accessibility_on_day_number, new Object[]{Integer.valueOf(this.dayOfMonth)}));
        if (isChecked()) {
            tmp.append(", ");
            tmp.append(resources.getString(R.string.accessibility_selected));
        }
        setContentDescription(tmp.toString());*/
    }
}
