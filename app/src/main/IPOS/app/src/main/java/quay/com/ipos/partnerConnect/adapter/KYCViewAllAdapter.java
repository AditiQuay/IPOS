package quay.com.ipos.partnerConnect.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.listeners.MyListener;
import quay.com.ipos.partnerConnect.model.KycModel;

/**
 * Created by niraj.kumar on 6/12/2018.
 */

public class KYCViewAllAdapter extends RecyclerView.Adapter<KYCViewAllAdapter.ItemViewHolder> {
    private Context mContext;
    private ArrayList<KycModel> kycModels;
    private MyListener myListener;

    public KYCViewAllAdapter(Context mContext, ArrayList<KycModel> kycModels,MyListener myListener) {
        this.mContext = mContext;
        this.kycModels = kycModels;
        this.myListener = myListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.documents_view_all, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        KycModel kycModel = kycModels.get(position);
        holder.textViewPhotoStatus.setText(kycModel.documentName);
        String kycStatus = kycModel.approvalStatus;
        if (kycStatus.equalsIgnoreCase("Approved")) {
            holder.imageViewPhotoStatus.setImageResource(R.drawable.green_signal);
        } else if (kycStatus.equalsIgnoreCase("Pending")) {
            holder.imageViewPhotoStatus.setImageResource(R.drawable.yellow);
        } else if (kycStatus.equalsIgnoreCase("Declined")) {
            holder.imageViewPhotoStatus.setImageResource(R.drawable.green_signal);
        }
        holder.btnViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myListener.onRowClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kycModels.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewPhotoStatus;
        private ImageView imageViewPhotoStatus;
        private Button btnViewAll;
        public ItemViewHolder(View itemView) {
            super(itemView);
            textViewPhotoStatus = itemView.findViewById(R.id.textViewPhotoStatus);
            imageViewPhotoStatus = itemView.findViewById(R.id.imageViewPhotoStatus);
            btnViewAll = itemView.findViewById(R.id.btnViewAll);
        }
    }
}
