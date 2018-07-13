package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
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
import quay.com.ipos.utility.Util;

/**
 * Created by niraj.kumar on 7/2/2018.
 */

public class InventoryGrnItemDetailsView extends RecyclerView.Adapter<InventoryGrnItemDetailsView.ItmeViewHolder> {
    private Context mContext;
    private ArrayList<GrnItemQtyModel> grnItemQtyModels;

    public InventoryGrnItemDetailsView(Context mContext, ArrayList<GrnItemQtyModel> grnItemQtyModels) {
        this.mContext = mContext;
        this.grnItemQtyModels = grnItemQtyModels;
    }

    @NonNull
    @Override
    public InventoryGrnItemDetailsView.ItmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_grn_item_details, parent, false);
        return new InventoryGrnItemDetailsView.ItmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final InventoryGrnItemDetailsView.ItmeViewHolder holder, int position) {

        GrnItemQtyModel grnItemQtyModel = grnItemQtyModels.get(position);

        SpannableString content = new SpannableString(grnItemQtyModel.getMaterialName());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        holder.tvMaterialName.setText(content);

        try{
            holder.tvOpenQty.setText((int) grnItemQtyModel.getOpenQty() + "");
            holder.tvInQty.setText((int) grnItemQtyModel.getInQty() + "");
            holder.tvApQty.setText((int) grnItemQtyModel.getApQty() + "");
            holder.tvBalanceQty.setText((int) grnItemQtyModel.getBalanceQty() + "");

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return grnItemQtyModels.size();
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
