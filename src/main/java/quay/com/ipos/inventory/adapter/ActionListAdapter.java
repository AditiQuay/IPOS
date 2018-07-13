package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.ActionListModel;
import quay.com.ipos.listeners.ActionListClick;

/**
 * Created by niraj.kumar on 6/23/2018.
 */

public class ActionListAdapter extends RecyclerView.Adapter<ActionListAdapter.MyView> {
    public Context mContext;
    public List<ActionListModel> actionListModels;
    ActionListClick actionListClick;

    public ActionListAdapter(Context mContext, List<ActionListModel> actionListModels, ActionListClick actionListClick) {
        this.mContext = mContext;
        this.actionListModels = actionListModels;
        this.actionListClick = actionListClick;
    }

    @NonNull
    @Override
    public MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.action_list_item, parent, false);
        return new ActionListAdapter.MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView holder, int position) {
        ActionListModel actionListModel = actionListModels.get(position);
        holder.text1.setText(actionListModel.actionTitle);
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListClick.actionListClicked(holder.getAdapterPosition());

            }
        });
        if (position==2){
            holder.borderLine.setVisibility(View.VISIBLE);
        }else {
            holder.borderLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return actionListModels.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        private TextView text1;
        private View borderLine;
        public MyView(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
            borderLine = itemView.findViewById(R.id.borderLine);
        }
    }
}
