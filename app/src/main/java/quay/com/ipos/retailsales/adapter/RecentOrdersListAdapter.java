package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.CustomerResult;
import quay.com.ipos.modal.RecentOrderModal;


/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class RecentOrdersListAdapter extends RecyclerView.Adapter<RecentOrdersListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<CustomerResult.RecentOrder> stringArrayList;
    private OnItemSelecteListener mListener;

    public RecentOrdersListAdapter(Context mContext, ArrayList<CustomerResult.RecentOrder> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recent_orders_list_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {


        holder.tvTitle.setText(stringArrayList.get(position).getFromStoreName());
        holder.tvStoreState.setText(stringArrayList.get(position).getStoreState() + ", "+stringArrayList.get(position).getStoreCity());
        holder.tvBillingAmount.setText(stringArrayList.get(position).getBillPrice());
        holder.tvBillingDate.setText(stringArrayList.get(position).getBillDate());


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

        private TextView tvTitle,tvStoreState,tvBillingDate,tvBillingAmount;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvStoreState = itemView.findViewById(R.id.tvStoreState);
            tvBillingDate = itemView.findViewById(R.id.tvBillingDate);
            tvBillingAmount = itemView.findViewById(R.id.tvBillingAmount);


        }
    }
}
