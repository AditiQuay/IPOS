package quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInListDataModel;
import quay.com.ipos.listeners.LayoutClickListener;

/**
 * Created by niraj.kumar on 7/10/2018.
 */

public class TransferInStepsAdapter extends RecyclerView.Adapter<TransferInStepsAdapter.ItemView> {
    private Context mContext;
    private ArrayList<TransferInListDataModel> transferInListDataModels;
    private LayoutClickListener listener;

    public TransferInStepsAdapter(Context mContext, ArrayList<TransferInListDataModel> transferInListDataModels, LayoutClickListener listener) {
        this.mContext = mContext;
        this.transferInListDataModels = transferInListDataModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TransferInStepsAdapter.ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inventory_grn_list_childs, parent, false);
        return new TransferInStepsAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransferInStepsAdapter.ItemView holder, final int position) {
        TransferInListDataModel transferInListDataModel = transferInListDataModels.get(position);
        holder.tvGrnNumber.setText(transferInListDataModel.getGrnNumber());
        holder.grnDate.setText(transferInListDataModel.getGrnDate());
        holder.tvOpen.setText(transferInListDataModel.getGrnStatus());
        holder.grnCount.setText(transferInListDataModel.getGrnQty() + "");
        holder.appCount.setText(transferInListDataModel.getGrnAPQty() + "");
        holder.valueCount.setText(transferInListDataModel.getGrnValue() + "");

        if (transferInListDataModel.isAttachment()) {
            holder.attachement.setVisibility(View.VISIBLE);
        } else {
            holder.attachement.setVisibility(View.INVISIBLE);
        }

        if (transferInListDataModel.isAction()) {
            holder.action.setVisibility(View.VISIBLE);
        } else {
            holder.action.setVisibility(View.INVISIBLE);
        }

        holder.rlTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCardClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transferInListDataModels.size();
    }

    public class ItemView extends RecyclerView.ViewHolder {
        private TextView tvGrnNumber, grnDate, tvOpen;
        private TextView grnCount, appCount, valueCount;
        private ImageView attachement, action;
        private RelativeLayout rlTab;

        public ItemView(View itemView) {
            super(itemView);
            rlTab = itemView.findViewById(R.id.rlTab);

            tvGrnNumber = itemView.findViewById(R.id.tvGrnNumber);
            grnDate = itemView.findViewById(R.id.grnDate);
            tvOpen = itemView.findViewById(R.id.tvOpen);

            grnCount = itemView.findViewById(R.id.grnCount);
            appCount = itemView.findViewById(R.id.appCount);
            valueCount = itemView.findViewById(R.id.valueCount);


            attachement = itemView.findViewById(R.id.attachement);
            action = itemView.findViewById(R.id.action);

        }
    }
}
