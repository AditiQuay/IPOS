package com.quayintech.tasklib.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.quayintech.tasklib.R;
import com.quayintech.tasklib.view.DayOfMonthPickerView;
import com.quayintech.tasklib.view.SquareDayView;
import com.quayintech.tasklib.view.adapter.vh.DayOfMonthViewHolder;

/**
 * Created by deepak.kumar1 on 04-05-2018.
 */

public class DayOfMonthPickerAdapter extends RecyclerView.Adapter<DayOfMonthViewHolder> implements View.OnClickListener {
    private DayOfMonthPickerView.OnDayOfMonthPickerListener listener;
    private int accentColor = -1;
    private int mCurrentIndex = -1;

    public void setListener(DayOfMonthPickerView.OnDayOfMonthPickerListener listener) {
        this.listener = listener;

    }

    @Override
    public void onBindViewHolder(@NonNull DayOfMonthViewHolder holder, int position) {
        onBind(holder, position);

    }

    @NonNull
    @Override
    public DayOfMonthViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //mCurrentIndex = Integer.parseInt(DateTimeUtils.getDayOfMonth());
        accentColor = viewGroup.getContext().getResources().getColor(R.color.colorAccent);
        return init(viewGroup, viewType);
    }

    @Override
    public int getItemCount() {
        return 31;
    }

    @Override
    public void onClick(View view) {
        int dayIndex = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition() + 1;
        selectDay(dayIndex);
        if (this.listener != null) {
            this.listener.onDayOfMonthSelected(dayIndex);
          //  Toast.makeText(view.getContext(), ""+dayIndex, Toast.LENGTH_SHORT).show();
        }
    }
    public void selectDay(int dayIndex) {
        if (this.mCurrentIndex != dayIndex) {
            int prevDayIndex = this.mCurrentIndex;
            this.mCurrentIndex = dayIndex;
            if (prevDayIndex != -1) {
                notifyItemChanged(prevDayIndex - 1);
            }
            if (this.mCurrentIndex != -1) {
                notifyItemChanged(this.mCurrentIndex - 1);
            }
        }
    }

    public void onBind(DayOfMonthViewHolder holder, int position) {
        int dayIndex = position + 1;
        holder.setData(dayIndex, this.mCurrentIndex == dayIndex);
    }

    public DayOfMonthViewHolder init(ViewGroup parent, int viewType) {
        SquareDayView dayOfMonthView = new SquareDayView(parent.getContext());
        dayOfMonthView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        dayOfMonthView.setAccentColor(this.accentColor);
        dayOfMonthView.setOnClickListener(this);
        return new DayOfMonthViewHolder(dayOfMonthView);
    }


    public int getSelected() {
        return mCurrentIndex;
    }
}
