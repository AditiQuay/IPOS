package quay.com.ipos.ddrsales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import quay.com.ipos.utility.NumberFormatEditText;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.ddrsales.model.DDR;

public class DDRAdapter extends RecyclerView.Adapter<DDRAdapter.ViewHolder> {

    private List<DDR> mDDRList;
    private LayoutInflater layoutInflater;
    private OnDDRSelectListener listener;
    private String rs;

    public DDRAdapter(Context context, List<DDR> mDDRList, OnDDRSelectListener listener) {
        this.listener = listener;
        this.layoutInflater = LayoutInflater.from(context);
        this.mDDRList = mDDRList;
        this.rs = context.getString(R.string.Rs) + " ";

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.adapter_ddr_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final DDR ddr = mDDRList.get(position);
        holder.textDDRCode.setText(ddr.mDDRCode);
        holder.textDDRName.setText(ddr.mDDRName);
        holder.textDDRAddress.setText(ddr.mDDRAddress1 + " | " + ddr.mDDRAddress2);
        holder.textDDRCreditLimit.setText(rs + NumberFormatEditText.getText(ddr.mDDRCreditLimit + ""));
        holder.textDDRNonDue.setText(rs +NumberFormatEditText.getText( ddr.mDDRNonDue+""));
        holder.textDDROverDue.setText(rs + NumberFormatEditText.getText(ddr.mDDROverDue + ""));
        if (ddr.mDDROverDue > 0) {
            holder.mDDROverDueIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.mDDROverDueIndicator.setVisibility(View.GONE);

        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onSelect(ddr);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDDRList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textDDRCode, textDDRName, textDDRAddress, textDDRCreditLimit, textDDROverDue, textDDRNonDue;
        private View mDDROverDueIndicator;

        public ViewHolder(View itemView) {
            super(itemView);
            textDDRCode = itemView.findViewById(R.id.textDDRCode);
            textDDRName = itemView.findViewById(R.id.textDDRName);
            textDDRAddress = itemView.findViewById(R.id.textDDRAddress);
            textDDRCreditLimit = itemView.findViewById(R.id.textDDRCreditLimit);
            textDDRNonDue = itemView.findViewById(R.id.textDDRNonDue);
            textDDROverDue = itemView.findViewById(R.id.textDDROverDue);
            mDDROverDueIndicator = itemView.findViewById(R.id.mDDROverDueIndicator);
        }
    }

    public interface OnDDRSelectListener {
        void onSelect(DDR ddr);
    }
}
