package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemView extends RecyclerView.ViewHolder {
        public ItemView(View itemView) {
            super(itemView);
        }
    }
}
