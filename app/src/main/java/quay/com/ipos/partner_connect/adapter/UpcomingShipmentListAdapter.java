package quay.com.ipos.partner_connect.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.partner_connect.modal.LowInventoryModal;


public class UpcomingShipmentListAdapter extends RecyclerView.Adapter<UpcomingShipmentListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<LowInventoryModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public UpcomingShipmentListAdapter(Context mContext, ArrayList<LowInventoryModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.upcoming_shipment_list_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {


        holder.tvTitle.setText(stringArrayList.get(position).getTitle());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    public void setOnItemClickLister(OnItemSelecteListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public interface OnItemSelecteListener {
        public void onItemSelected(View v, int position);
    }

    public class SurveyViewHolder extends RecyclerView.ViewHolder {

        private final View item;
        private TextView tvTitle;


        public SurveyViewHolder(View itemView) {
            super(itemView);
            item=itemView;
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }
}
