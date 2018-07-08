package quay.com.ipos.inventory.adapter;

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
import quay.com.ipos.inventory.modal.GRNListModel;
import quay.com.ipos.listeners.AdapterListener;
import quay.com.ipos.listeners.LayoutClickListener;
import quay.com.ipos.listeners.MyListener;

/**
 * Created by niraj.kumar on 6/13/2018.
 */

public class InventoryGRNStepsAdapter extends RecyclerView.Adapter<InventoryGRNStepsAdapter.ItemView> {
    private Context mContext;
    private ArrayList<GRNListModel> grnListModels;
    private LayoutClickListener listener;

    public InventoryGRNStepsAdapter(Context mContext, ArrayList<GRNListModel> grnListModels, LayoutClickListener listener) {
        this.mContext = mContext;
        this.grnListModels = grnListModels;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inventory_grn_list_childs, parent, false);
        return new InventoryGRNStepsAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, final int position) {
        GRNListModel grnListModel = grnListModels.get(position);
        holder.tvGrnNumber.setText(grnListModel.getGrnNumber());
        holder.grnDate.setText(grnListModel.getGrnDate());
        holder.tvOpen.setText(grnListModel.getGrnStatus());
        holder.grnCount.setText(grnListModel.getGrnQty()+"");
        holder.appCount.setText(grnListModel.getGrnAPQty()+"");
        holder.valueCount.setText(grnListModel.getGrnValue()+"");

        if (grnListModel.isAttachment()) {
            holder.attachement.setVisibility(View.VISIBLE);
        } else {
            holder.attachement.setVisibility(View.INVISIBLE);
        }

        if (grnListModel.isAction()) {
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
        return grnListModels.size();
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
