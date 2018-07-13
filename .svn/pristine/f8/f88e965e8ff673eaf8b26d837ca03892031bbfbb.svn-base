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
import quay.com.ipos.inventory.adapter.OthersListAdapter;
import quay.com.ipos.inventory.modal.OthersTabList;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInOtherTabList;
import quay.com.ipos.listeners.OthersTabListner;

/**
 * Created by niraj.kumar on 7/11/2018.
 */

public class TransferOtherTabListAdapter extends RecyclerView.Adapter<TransferOtherTabListAdapter.ItemView> {
    private Context mContext;
    private List<TransferInOtherTabList> transferInOtherTabLists;
    OthersTabListner othersTabListner;
    public TransferOtherTabListAdapter(Context mContext, List<TransferInOtherTabList> transferInOtherTabLists,OthersTabListner othersTabListner){
        this.mContext = mContext;
        this.transferInOtherTabLists = transferInOtherTabLists;
        this.othersTabListner = othersTabListner;
    }
    @NonNull
    @Override
    public TransferOtherTabListAdapter.ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, parent, false);
        return new TransferOtherTabListAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransferOtherTabListAdapter.ItemView holder, final int position) {
        TransferInOtherTabList transferInOtherTabList = transferInOtherTabLists.get(position);
        holder.text1.setText(transferInOtherTabList.tabTitle);
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othersTabListner.otherTabListner(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transferInOtherTabLists.size();
    }

    public class ItemView extends RecyclerView.ViewHolder{
        private TextView text1;
        public ItemView(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
        }
    }
}

