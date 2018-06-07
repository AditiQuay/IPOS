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
import quay.com.ipos.partnerConnect.model.PssPrincipleContact;

public class RelTwoAdapter extends RecyclerView.Adapter<RelTwoAdapter.VH> {
    List<PssPrincipleContact> contactList = new ArrayList<>();

    public RelTwoAdapter(List<PssPrincipleContact> pssPrincipleContact) {
        this.contactList = pssPrincipleContact;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_pc_rel_customerdetails, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        PssPrincipleContact contact = contactList.get(position);
        holder.mtxt1.setText(contact.pssContactPersonName);
        holder.mtxt2.setText(contact.pssDesignation);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

   /* public void setData(List<PssPrincipleContact> contactList) {
        this.contactList = contactList;
        notifyDataSetChanged();
    }*/

    public class VH extends RecyclerView.ViewHolder {
        TextView mtxt1, mtxt2;

        public VH(View itemView) {
            super(itemView);
            mtxt1 = itemView.findViewById(R.id.mTxt1);
            mtxt2 = itemView.findViewById(R.id.mTxt2);
        }
    }
}
