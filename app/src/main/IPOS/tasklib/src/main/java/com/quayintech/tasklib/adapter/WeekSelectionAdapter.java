package com.quayintech.tasklib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.quayintech.tasklib.R;
import com.quayintech.tasklib.interfaces.WeeksSelectionListener;
import com.quayintech.tasklib.model.Weeks;
import com.quayintech.tasklib.model.Weeks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepak.kumar1 on 01-05-2018.
 */

public class WeekSelectionAdapter extends RecyclerView.Adapter<WeekSelectionAdapter.UserSelectionVH> {
    private List<Weeks> data;
    private WeeksSelectionListener listener;

    public WeekSelectionAdapter(List<Weeks> WeeksList, WeeksSelectionListener listener) {
        this.data = WeeksList;
        this.listener = listener;
    }

    @Override
    public UserSelectionVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_weeks_item, parent, false);
        return new UserSelectionVH(view);
    }

    @Override
    public void onBindViewHolder(final UserSelectionVH holder, int position) {
        final Weeks alert = data.get(position);
        holder.checkBox.setText(alert.getLabel());
       /* holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                alert.setSelected(!isChecked);
                notifyDataSetChanged();
              *//*  if (listener != null) {
                    listener.onWeeksSelected(data);
                }
*//*
            }
        });*/
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.setSelected(!alert.isSelected());
                holder.checkBox.setChecked(alert.isSelected());
                if (listener != null) {
                    listener.onWeeksSelected(data);
                }
            }
        });
        holder.checkBox.setChecked(alert.isSelected());


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class UserSelectionVH extends RecyclerView.ViewHolder {
        private CheckBox checkBox;

        public UserSelectionVH(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkUser);
        }
    }
}
