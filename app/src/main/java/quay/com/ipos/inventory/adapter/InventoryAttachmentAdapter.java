package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.GrnAttachment;

/**
 * Created by niraj.kumar on 6/20/2018.
 */

public class InventoryAttachmentAdapter extends RecyclerView.Adapter<InventoryAttachmentAdapter.ItemView> {
    private Context mContext;
    private ArrayList<GrnAttachment> grnAttachments;

    public InventoryAttachmentAdapter(Context mContext,ArrayList<GrnAttachment> grnAttachments){
        this.mContext = mContext;
        this.grnAttachments = grnAttachments;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grn_attachments_items, parent, false);
        return new InventoryAttachmentAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, int position) {
        GrnAttachment grnAttachment = new GrnAttachment();
        holder.tvtitle.setText(grnAttachment.getGrnAttachmentName());

    }

    @Override
    public int getItemCount() {
        return grnAttachments.size();
    }

    public class ItemView extends RecyclerView.ViewHolder {
        private ImageView imageattach,imDelete;
        private TextView tvtitle;

        public ItemView(View itemView) {
            super(itemView);
            imageattach = itemView.findViewById(R.id.imageattach);
            imDelete = itemView.findViewById(R.id.imDelete);
            tvtitle = itemView.findViewById(R.id.tvtitle);

        }
    }
}
