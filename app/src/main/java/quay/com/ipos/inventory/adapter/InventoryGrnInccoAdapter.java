package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.GrnInccoTermsModel;

/**
 * Created by niraj.kumar on 6/20/2018.
 */

public class InventoryGrnInccoAdapter extends RecyclerView.Adapter<InventoryGrnInccoAdapter.ItemView> {
   private Context mContext;
   private ArrayList<GrnInccoTermsModel> grnInccoTermsModels;
   public InventoryGrnInccoAdapter(Context mContext,ArrayList<GrnInccoTermsModel>grnInccoTermsModels){
       this.mContext = mContext;
       this.grnInccoTermsModels = grnInccoTermsModels;
   }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grn_inco_terms_item, parent, false);
        return new InventoryGrnInccoAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, int position) {
        GrnInccoTermsModel grnInccoTermsModel = grnInccoTermsModels.get(position);
        holder.tvDetailName.setText(grnInccoTermsModel.getGrnIncoDetail());
        holder.tvPayAmount.setText(grnInccoTermsModel.getGrnPayAmount()+"");
        if (grnInccoTermsModel.isGrnPayBySender()){
            holder.sender.setChecked(true);
        }
        if (grnInccoTermsModel.isGrnPayByReceiver()){
            holder.reciver.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return grnInccoTermsModels.size();
    }

    public class ItemView extends RecyclerView.ViewHolder{
        private TextView tvDetailName,tvPayAmount;
        RadioButton sender,reciver;
        public ItemView(View itemView) {
            super(itemView);
            tvDetailName = itemView.findViewById(R.id.tvDetailName);
            tvPayAmount = itemView.findViewById(R.id.tvPayAmount);
            sender = itemView.findViewById(R.id.sender);
            reciver = itemView.findViewById(R.id.reciver);
        }
    }
}
