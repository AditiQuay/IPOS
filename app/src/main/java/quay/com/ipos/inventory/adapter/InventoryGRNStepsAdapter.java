package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.InventoryGRNModel;

/**
 * Created by niraj.kumar on 6/13/2018.
 */

public class InventoryGRNStepsAdapter extends RecyclerView.Adapter<InventoryGRNStepsAdapter.ItemView> {
    private Context mContext;
    private ArrayList<InventoryGRNModel> inventoryGRNModels;
    public InventoryGRNStepsAdapter(Context mContext,ArrayList<InventoryGRNModel> inventoryGRNModels) {
        this.mContext = mContext;
        this.inventoryGRNModels = inventoryGRNModels;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inventory_grn_list_childs, parent, false);
        return new InventoryGRNStepsAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, int position) {
        InventoryGRNModel inventoryGRNModel = inventoryGRNModels.get(position);
        holder.grnQtyCount.setText(inventoryGRNModel.grnQty);
        holder.apQtyCount.setText(inventoryGRNModel.apQTY);
        holder.balanceQtyCount.setText(inventoryGRNModel.value);
    }

    @Override
    public int getItemCount() {
        return inventoryGRNModels.size();
    }

    public class ItemView extends RecyclerView.ViewHolder {
        private TextView grnQtyCount,apQtyCount,balanceQtyCount;
        public ItemView(View itemView) {
            super(itemView);
            grnQtyCount = itemView.findViewById(R.id.grnQtyCount);
            apQtyCount = itemView.findViewById(R.id.apQtyCount);
            balanceQtyCount = itemView.findViewById(R.id.balanceQtyCount);

        }
    }
}
