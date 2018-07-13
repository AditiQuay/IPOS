package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.OthersTabList;
import quay.com.ipos.listeners.OthersTabListner;

/**
 * Created by niraj.kumar on 6/22/2018.
 */

public class OthersListAdapter extends RecyclerView.Adapter<OthersListAdapter.ItemView> {
    private Context mContext;
    private List<OthersTabList> othersTabLists;
    OthersTabListner othersTabListner;
    public OthersListAdapter(Context mContext, List<OthersTabList> othersTabList,OthersTabListner othersTabListner){
        this.mContext = mContext;
        this.othersTabLists = othersTabList;
        this.othersTabListner = othersTabListner;
    }
    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, parent, false);
        return new OthersListAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, final int position) {
        OthersTabList othersTabList = othersTabLists.get(position);
        holder.text1.setText(othersTabList.tabTitle);
        holder.text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                othersTabListner.otherTabListner(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return othersTabLists.size();
    }

    public class ItemView extends RecyclerView.ViewHolder{
        private TextView text1;
        public ItemView(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);
        }
    }
}
