package quay.com.ipos.ddrsales.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.model.response.DDRIncoTerms;

/**
 * Created by deepa.kumar on 6/25/2018.
 */

public class DDRIncoTermsAdapter extends RecyclerView.Adapter<DDRIncoTermsAdapter.ItemView> {
    private Context mContext;
    private List<DDRIncoTerms> list;

    public DDRIncoTermsAdapter(Context mContext, List<DDRIncoTerms> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ddr_adapter_incoterms, parent, false);
        return new DDRIncoTermsAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, int position) {
        DDRIncoTerms incoTerms = list.get(position);
        holder.tvDetailName.setText(incoTerms.grnIncoDetail);
        holder.tvPayAmount.setText(incoTerms.grnPayAmount + "");
        if (incoTerms.grnPayBySender) {
            holder.sender.setChecked(true);
        }
        if (incoTerms.grnPayByReceiver) {
            holder.reciver.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
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
