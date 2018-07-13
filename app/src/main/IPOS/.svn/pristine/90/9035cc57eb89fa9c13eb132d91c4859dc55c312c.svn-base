package com.quayintech.tasklib.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.quayintech.tasklib.R;
import com.quayintech.tasklib.utils.DividerItemDecoration;
import com.quayintech.tasklib.view.adapter.DayOfMonthPickerAdapter;

/**
 * Created by deepak.kumar1 on 04-05-2018.
 */

public class DayOfMonthPickerView extends RecyclerView {
    private boolean isFirstTime;
    private DayOfMonthPickerAdapter adapter;
    private OnDayOfMonthPickerListener listener;

    public void setListener(OnDayOfMonthPickerListener listener) {
        this.adapter.setListener(listener);
    }

    public interface OnDayOfMonthPickerListener {
        void onDayOfMonthSelected(int dayOfMonth);
    }

    public DayOfMonthPickerView(Context context) {
        super(context);
        init();
    }

    public DayOfMonthPickerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public DayOfMonthPickerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        if (!this.isFirstTime) {
            this.isFirstTime = true;
            this.adapter = new DayOfMonthPickerAdapter();
            setOverScrollMode(2);
            setHasFixedSize(true);
            setLayoutManager(new GridLayoutManager(getContext(), 7, 1, false));
            setAdapter(this.adapter);
            addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), (int) R.drawable.horizontal_divider)));
        }
    }

    public void setSelected(int dayIndex) {
        this.adapter.selectDay(dayIndex);
    }
    public int getSelected() {
        return this.adapter.getSelected();
    }
//    public void setAccentColor(int accentColor) {
//        this.adapter.m16482a(accentColor);
//    }

}
