package quay.com.ipos.partnerConnect.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.partnerConnect.model.PssLOBS;

public class RelOneAdapter extends RecyclerView.Adapter<RelOneAdapter.VH> {
    private List<PssLOBS> pssLOBS = new ArrayList<>();

    public  RelOneAdapter(List<PssLOBS> pssLOBS){
        this.pssLOBS = pssLOBS;
    }
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_pc_rel_partner, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        PssLOBS lobs = pssLOBS.get(position);
        holder.mtxt1.setText(lobs.pssType);
        holder.mtxt2.setText(lobs.pssLobName);

    }

    @Override
    public int getItemCount() {
        return pssLOBS.size();
    }

    public void setData(List<PssLOBS> pssLOBS) {
        this.pssLOBS = pssLOBS;
        notifyDataSetChanged();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView mtxt1, mtxt2;

        public VH(View itemView) {
            super(itemView);
            mtxt1 = itemView.findViewById(R.id.mtxt1);
            mtxt2 = itemView.findViewById(R.id.mtxt2);
        }
    }
}
