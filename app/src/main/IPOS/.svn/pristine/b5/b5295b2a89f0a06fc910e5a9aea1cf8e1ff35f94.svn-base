package com.quayintech.tasklib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import com.quayintech.tasklib.R;
import com.quayintech.tasklib.interfaces.AlertSelectionListener;
import com.quayintech.tasklib.model.Alert;

import java.util.List;

/**
 * Created by deepak.kumar1 on 01-05-2018.
 */

public class AlertSelectionAdapter extends RecyclerView.Adapter<AlertSelectionAdapter.UserSelectionVH> {
    private List<Alert> data;
    private AlertSelectionListener listener;

    public AlertSelectionAdapter(List<Alert> AlertList, AlertSelectionListener listener) {
        this.data = AlertList;
        this.listener = listener;
    }

    @Override
    public UserSelectionVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_alert_item, parent, false);
        return new UserSelectionVH(view);
    }

    @Override
    public void onBindViewHolder(UserSelectionVH holder, int position) {
        final Alert alert = data.get(position);
        holder.checkBox.setText(alert.getLabel());
        holder.checkBox.setChecked(alert.isSelected());
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAlertSelected(alert);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class UserSelectionVH extends RecyclerView.ViewHolder {
        private RadioButton checkBox;

        public UserSelectionVH(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkUser);
        }
    }
}
