package quay.com.ipos.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.dashboard.modal.LowInventoryModal;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class LowInventoryListAdapter extends RecyclerView.Adapter<LowInventoryListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<LowInventoryModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public LowInventoryListAdapter(Context mContext, ArrayList<LowInventoryModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.low_inventory_list_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {


        holder.tvTitle.setText(stringArrayList.get(position).getTitle());



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

        private TextView tvTitle;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);

        }
    }
}
