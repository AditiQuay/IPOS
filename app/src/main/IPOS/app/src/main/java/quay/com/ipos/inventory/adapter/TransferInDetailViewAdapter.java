package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.GrnItemQtyModel;
import quay.com.ipos.inventoryTrasfer.inventoryTransferIn.transferInModel.TransferInItemQtyModel;

/**
 * Created by niraj.kumar on 7/11/2018.
 */

public class TransferInDetailViewAdapter extends RecyclerView.Adapter<TransferInDetailViewAdapter.ItmeViewHolder> {
    private Context mContext;
    private ArrayList<TransferInItemQtyModel> transferInItemQtyModels;

    public TransferInDetailViewAdapter(Context mContext, ArrayList<TransferInItemQtyModel> transferInItemQtyModels) {
        this.mContext = mContext;
        this.transferInItemQtyModels = transferInItemQtyModels;
    }

    @NonNull
    @Override
    public TransferInDetailViewAdapter.ItmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_grn_item_details, parent, false);
        return new TransferInDetailViewAdapter.ItmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TransferInDetailViewAdapter.ItmeViewHolder holder, int position) {

        TransferInItemQtyModel transferInItemQtyModel = transferInItemQtyModels.get(position);

        SpannableString content = new SpannableString(transferInItemQtyModel.getMaterialName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.tvMaterialName.setText(content);

        try{
            holder.tvOpenQty.setText((int) transferInItemQtyModel.getOpenQty() + "");
            holder.tvInQty.setText((int) transferInItemQtyModel.getInQty() + "");
            holder.tvApQty.setText((int) transferInItemQtyModel.getApQty() + "");
            holder.tvBalanceQty.setText((int) transferInItemQtyModel.getBalanceQty() + "");

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return transferInItemQtyModels.size();
    }

    public class ItmeViewHolder extends RecyclerView.ViewHolder {
        private final View item;
        private TextView tvMaterialName, tvOpenQty, tvBalanceQty;
        private EditText tvInQty, tvApQty;
        private ImageView imageViewStatus;

        public ItmeViewHolder(View itemView) {
            super(itemView);
            item = itemView;
            tvMaterialName = itemView.findViewById(R.id.tvMaterialName);
            tvInQty = itemView.findViewById(R.id.tvInQty);
            tvOpenQty = itemView.findViewById(R.id.tvOpenQty);
            tvApQty = itemView.findViewById(R.id.tvApQty);
            tvBalanceQty = itemView.findViewById(R.id.tvBalanceQty);
            imageViewStatus = itemView.findViewById(R.id.imageViewStatus);




        }
    }

}

