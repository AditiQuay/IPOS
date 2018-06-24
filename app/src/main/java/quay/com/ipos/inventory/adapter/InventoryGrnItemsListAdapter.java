package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.GrnItemQtyModel;
import quay.com.ipos.listeners.MyListener;

/**
 * Created by niraj.kumar on 6/20/2018.
 */

public class InventoryGrnItemsListAdapter extends RecyclerView.Adapter<InventoryGrnItemsListAdapter.ItmeViewHolder> {
    private Context mContext;
    private ArrayList<GrnItemQtyModel> grnItemQtyModels;
    MyListener myListener;

    public InventoryGrnItemsListAdapter(Context mContext, ArrayList<GrnItemQtyModel> grnItemQtyModels, MyListener myListener) {
        this.mContext = mContext;
        this.grnItemQtyModels = grnItemQtyModels;
        this.myListener = myListener;
    }

    @NonNull
    @Override
    public ItmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grn_item_details, parent, false);
        return new InventoryGrnItemsListAdapter.ItmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItmeViewHolder holder, int position) {
        GrnItemQtyModel grnItemQtyModel = grnItemQtyModels.get(position);

        SpannableString content = new SpannableString(grnItemQtyModel.getMaterialName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.tvMaterialName.setText(content);

        holder.tvOpenQty.setText(grnItemQtyModel.getOpenQty() + "");
        holder.tvInQty.setText(grnItemQtyModel.getInQty() + "");
        holder.tvApQty.setText(grnItemQtyModel.getApQty() + "");
        holder.tvBalanceQty.setText(grnItemQtyModel.getBalanceQty() + "");

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListener.onRowClicked(holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return grnItemQtyModels.size();
    }

    public class ItmeViewHolder extends RecyclerView.ViewHolder {
        private final View item;
        private TextView tvMaterialName, tvOpenQty, tvInQty, tvApQty, tvBalanceQty;

        public ItmeViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            tvMaterialName = itemView.findViewById(R.id.tvMaterialName);
            tvOpenQty = itemView.findViewById(R.id.tvOpenQty);
            tvInQty = itemView.findViewById(R.id.tvInQty);
            tvApQty = itemView.findViewById(R.id.tvApQty);
            tvBalanceQty = itemView.findViewById(R.id.tvBalanceQty);

        }
    }
}
