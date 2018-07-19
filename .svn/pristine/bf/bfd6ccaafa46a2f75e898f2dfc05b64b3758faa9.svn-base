package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInActionList;
import quay.com.ipos.listeners.ActionListClick;

/**
 * Created by niraj.kumar on 7/11/2018.
 */

public class TransferInActionListAdapter extends RecyclerView.Adapter<TransferInActionListAdapter.MyView> {
    public Context mContext;
    public List<TransferInActionList> transferInActionLists;
    ActionListClick actionListClick;

    public TransferInActionListAdapter(Context mContext, List<TransferInActionList> transferInActionLists, ActionListClick actionListClick) {
        this.mContext = mContext;
        this.transferInActionLists = transferInActionLists;
        this.actionListClick = actionListClick;
    }

    @NonNull
    @Override
    public TransferInActionListAdapter.MyView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.action_list_item, parent, false);
        return new TransferInActionListAdapter.MyView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransferInActionListAdapter.MyView holder, int position) {
        TransferInActionList transferInActionList = transferInActionLists.get(position);
        holder.text1.setText(transferInActionList.actionTitle);
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListClick.actionListClicked(holder.getAdapterPosition());

            }
        });
        if (position == 2) {
            holder.borderLine.setVisibility(View.VISIBLE);
        } else {
            holder.borderLine.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return transferInActionLists.size();
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
