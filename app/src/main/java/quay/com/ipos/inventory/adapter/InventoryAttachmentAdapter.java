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
import quay.com.ipos.listeners.AttachmentListener;
import quay.com.ipos.listeners.MyListener;

/**
 * Created by niraj.kumar on 6/20/2018.
 */

public class InventoryAttachmentAdapter extends RecyclerView.Adapter<InventoryAttachmentAdapter.ItemView> {
    private Context mContext;
    private ArrayList<GrnAttachment> grnAttachments;
    AttachmentListener listener;
    public InventoryAttachmentAdapter(Context mContext,ArrayList<GrnAttachment> grnAttachments,AttachmentListener listener){
        this.mContext = mContext;
        this.grnAttachments = grnAttachments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grn_attachments_items, parent, false);
        return new InventoryAttachmentAdapter.ItemView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemView holder, final int position) {
        GrnAttachment grnAttachment = grnAttachments.get(position);
        holder.tvtitle.setText(grnAttachment.getGrnAttachmentName());
        holder.tvtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAttachmentClicked(position);
            }
        });

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
