package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.GrnInccoTermsModel;

/**
 * Created by niraj.kumar on 7/2/2018.
 */

public class InventoryGrnInccoViewAdapter extends RecyclerView.Adapter<InventoryGrnInccoViewAdapter.ItemView> {
    private static final String TAG = InventoryGrnInccoViewAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<GrnInccoTermsModel> grnInccoTermsModels;

    public InventoryGrnInccoViewAdapter(Context mContext, ArrayList<GrnInccoTermsModel> grnInccoTermsModels) {
        this.mContext = mContext;
        this.grnInccoTermsModels = grnInccoTermsModels;
    }

    @NonNull
    @Override
    public InventoryGrnInccoViewAdapter.ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grn_inco_terms_item, parent, false);
        return new InventoryGrnInccoViewAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InventoryGrnInccoViewAdapter.ItemView holder, int position) {

        GrnInccoTermsModel grnInccoTermsModel = grnInccoTermsModels.get(position);
        holder.tvDetailName.setText(grnInccoTermsModel.grnIncoDetail);


        holder.tvPayAmount.setText(grnInccoTermsModels.get(holder.getAdapterPosition()).grnPayAmount + "");


        holder.sender.setChecked(grnInccoTermsModel.grnPayBySender);
        holder.sender.setClickable(false);
        holder.reciver.setChecked(grnInccoTermsModel.grnPayByReceiver);
        holder.reciver.setClickable(false);
    }

    @Override
    public int getItemCount() {
        return grnInccoTermsModels.size();
    }

    public class ItemView extends RecyclerView.ViewHolder {
        private TextView tvDetailName;
        private EditText tvPayAmount;
        RadioButton sender, reciver;

        public ItemView(View itemView) {
            super(itemView);
            tvDetailName = itemView.findViewById(R.id.tvDetailName);
            tvPayAmount = itemView.findViewById(R.id.tvPayAmount);
            sender = itemView.findViewById(R.id.sender);
            reciver = itemView.findViewById(R.id.reciver);

        }
    }


}
