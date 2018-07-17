package com.quayintech.tasklib.utils;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class DividerItemDecoration extends ItemDecoration {
    private Drawable mDividerDrawable;
    private int mDividerSize;
    private int mOrientation;

    public DividerItemDecoration(int size) {
        this(null, size, 1);
    }

    public DividerItemDecoration(Drawable divider) {
        this(divider, divider.getIntrinsicHeight(), 1);
    }

    public DividerItemDecoration(int size, int orientation) {
        this(null, size, orientation);
    }

    public DividerItemDecoration(Drawable divider, int size, int orientation) {
        this.mDividerDrawable = divider;
        this.mDividerSize = size;
        this.mOrientation = orientation;
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        if (shouldDrawDividerForItemView(view, parent)) {
            switch (this.mOrientation) {
                case 0:
                    outRect.set(this.mDividerSize, 0, 0, 0);
                    return;
                case 1:
                    outRect.set(0, this.mDividerSize, 0, 0);
                    return;
                default:
                    return;
            }
        }
    }

    public void onDraw(Canvas c, RecyclerView parent, State state) {
        boolean isVertical = true;
        int left = parent.getPaddingLeft();
        int top = parent.getPaddingTop();
        if (this.mOrientation != 1) {
            isVertical = false;
        }
        if (this.mDividerDrawable != null) {
            if (isVertical) {
                this.mDividerDrawable.setBounds(0, 0, (parent.getWidth() - parent.getPaddingLeft()) - parent.getPaddingRight(), this.mDividerSize);
            } else {
                this.mDividerDrawable.setBounds(0, 0, this.mDividerSize, (parent.getHeight() - parent.getPaddingTop()) - parent.getPaddingBottom());
            }
            int size = parent.getChildCount();
            for (int i = 0; i < size; i++) {
                int count;
                View itemView = parent.getChildAt(i);
                if (shouldDrawDividerForItemView(itemView, parent)) {
                    count = c.save();
                    if (isVertical) {
                        c.translate((float) left, (((float) (itemView.getTop() + top)) + itemView.getTranslationY()) - ((float) this.mDividerSize));
                    } else {
                        c.translate((((float) (itemView.getLeft() + left)) + itemView.getTranslationX()) - ((float) this.mDividerSize), (float) top);
                    }
                    this.mDividerDrawable.draw(c);
                    c.restoreToCount(count);
                }
                if (shouldDrawDividerAtEnd() && isLastItemView(itemView, parent)) {
                    count = c.save();
                    if (isVertical) {
                        c.translate((float) left, (((float) (itemView.getBottom() + top)) + itemView.getTranslationY()) - ((float) this.mDividerSize));
                    } else {
                        c.translate((((float) (itemView.getRight() + left)) + itemView.getTranslationX()) - ((float) this.mDividerSize), (float) top);
                    }
                    this.mDividerDrawable.draw(c);
                    c.restoreToCount(count);
                }
            }
        }
    }

    protected boolean isFirstItemView(View view, RecyclerView recyclerView) {
        return recyclerView.getChildAdapterPosition(view) == 0;
    }

    protected boolean isLastItemView(View view, RecyclerView recyclerView) {
        Adapter adapter = recyclerView.getAdapter();
        return adapter != null && recyclerView.getChildAdapterPosition(view) == adapter.getItemCount() - 1;
    }

    protected boolean shouldDrawDividerForItemView(View view, RecyclerView recyclerView) {
        return view.getVisibility() == View.VISIBLE && !isFirstItemView(view, recyclerView);
    }

    protected boolean shouldDrawDividerAtEnd() {
        return false;
    }
}
