package com.quayintech.tasklib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.quayintech.tasklib.R;

public class CalendarDaySelectionDrawable extends Drawable {
    private final Paint paint = new Paint(1);
    private final Mode mode;
    private final int mSize;

    public enum Mode {
        SINGLE,
        START,
        END,
        MIDDLE
    }

    public CalendarDaySelectionDrawable(Context context, Mode mode) {
        this.paint.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        this.mode = mode;
        this.mSize = context.getResources().getDimensionPixelSize(R.dimen.calendar_view_day_selection_size);
    }

    public void draw(Canvas canvas) {
        int width = getIntrinsicWidth();
        int centerX = width / 2;
        int centerY = getIntrinsicHeight() / 2;
        int radius = this.mSize / 2;
        switch (this.mode) {
            case SINGLE:
                canvas.drawCircle((float) centerX, (float) centerY, (float) radius, this.paint);
                return;
            case START:
                canvas.drawCircle((float) centerX, (float) centerY, (float) radius, this.paint);
                canvas.drawRect((float) centerX, (float) (centerY - radius), (float) width, (float) (centerY + radius), this.paint);
                return;
            case MIDDLE:
                canvas.drawRect(0.0f, (float) (centerY - radius), (float) width, (float) (centerY + radius), this.paint);
                return;
            case END:
                canvas.drawCircle((float) centerX, (float) centerY, (float) radius, this.paint);
                canvas.drawRect(0.0f, (float) (centerY - radius), (float) centerX, (float) (centerY + radius), this.paint);
                return;
            default:
                return;
        }
    }

    public int getIntrinsicHeight() {
        return getBounds().height();
    }

    public int getIntrinsicWidth() {
        return getBounds().width();
    }

    public void setAlpha(int alpha) {
        this.paint.setAlpha(alpha);
        invalidateSelf();
    }

    public void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
