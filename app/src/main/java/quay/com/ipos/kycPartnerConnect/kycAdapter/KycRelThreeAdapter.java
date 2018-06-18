package quay.com.ipos.kycPartnerConnect.kycAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.partnerConnect.model.PssPrincipleBankpaymentTo;

public class KycRelThreeAdapter extends RecyclerView.Adapter<KycRelThreeAdapter.VH> {
    List<PssPrincipleBankpaymentTo> list;

    public KycRelThreeAdapter(List<PssPrincipleBankpaymentTo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_pc_rel_three, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        PssPrincipleBankpaymentTo bankpaymentTo = list.get(position);
        if (bankpaymentTo != null) {

            holder.mtxt1.setText(bankpaymentTo.EntityPaytoName);
            holder.mtxt2.setText(bankpaymentTo.principleAccountNo);
            holder.mtxt3.setText(bankpaymentTo.principleBankName);
            holder.mtxt4.setText(bankpaymentTo.principleBankBranchName);
            holder.mtxt5.setText(bankpaymentTo.principleBankIFSCode);
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView mtxt1;
        TextView mtxt2;
        TextView mtxt3;
        TextView mtxt4;
        TextView mtxt5;

        public VH(View itemView) {
            super(itemView);
            mtxt1 = itemView.findViewById(R.id.mtxt1);
            mtxt2 = itemView.findViewById(R.id.mtxt2);
            mtxt3 = itemView.findViewById(R.id.mtxt3);
            mtxt4 = itemView.findViewById(R.id.mtxt4);
            mtxt5 = itemView.findViewById(R.id.mtxt5);
        }
    }
}
