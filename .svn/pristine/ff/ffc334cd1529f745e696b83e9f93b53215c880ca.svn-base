package quay.com.ipos.dashboard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.dashboard.modal.SchemePerformanceModal;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class SchemeListAdapter extends RecyclerView.Adapter<SchemeListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<SchemePerformanceModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public SchemeListAdapter(Context mContext, ArrayList<SchemePerformanceModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.schemeperformance_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {


        holder.schemeName.setText(stringArrayList.get(position).getName());
        holder.tvSales.setText(stringArrayList.get(position).getSales());
        holder.tvTarget.setText(stringArrayList.get(position).getTarget());
        holder.tvAcheviment.setText(stringArrayList.get(position).getAchievement());
        





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

        private TextView schemeName,tvSales,tvTarget,tvAcheviment;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            schemeName = itemView.findViewById(R.id.schemeName);
            tvSales = itemView.findViewById(R.id.tvSales);
            tvTarget = itemView.findViewById(R.id.tvTarget);
            tvAcheviment = itemView.findViewById(R.id.tvAcheviment);

        }
    }
}
