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
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, parent, false);
        return new ActionListAdapter.MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyView holder, int position) {
        ActionListModel actionListModel = actionListModels.get(position);
        holder.text1.setText(actionListModel.actionTitle);
        applyClickEvent(position);
    }

    private void applyClickEvent(int position) {
        actionListClick.actionListClicked(position);
    }

    @Override
    public int getItemCount() {
        return actionListModels.size();
    }

    public class MyView extends RecyclerView.ViewHolder {
        private TextView text1;

        public MyView(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
        }
    }
}
