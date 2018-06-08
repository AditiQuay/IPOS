package quay.com.ipos.partnerConnect.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.partnerConnect.model.PssPrincipleBankpaymentTo;

public class RelThreeAdapter extends RecyclerView.Adapter<RelThreeAdapter.VH> {
    public RelThreeAdapter(List<PssPrincipleBankpaymentTo> pssPrincipleBankpaymentTo) {
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

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(View itemView) {
            super(itemView);
        }
    }
}
