package quay.com.ipos.loyalty.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.RecentOrderModal;

/**
 * Created by aditi.bhuranda on 16-06-2018.
 */

public class RecentOrdersAdapter extends RecyclerView.Adapter<RecentOrdersAdapter.SurveyViewHolder> {
        public ArrayList<RecentOrderModal> mOrderList = new ArrayList<>();
        private Context mContext;
        //    private ArrayList<RecentOrderModal> stringArrayList;
        private RecentOrdersAdapter.OnItemSelecteListener mListener;

        public RecentOrdersAdapter(Context mContext, ArrayList<RecentOrderModal> stringArrayList) {
            this.mContext = mContext;
            this.mOrderList = stringArrayList;
        }

        @Override
        public RecentOrdersAdapter.SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.recent_order_list, parent, false);
            return new RecentOrdersAdapter.SurveyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecentOrdersAdapter.SurveyViewHolder holder, int position) {

            RecentOrderModal datum = mOrderList.get(position);
            holder.tvTitle.setText(datum.getTitle());
            holder.tvQty.setText(datum.getQty()+"");
//            holder.tvBillingDate.setText(mContext.getResources().getString(R.string.Rs) + " " + datum.getDiscountValue());
//            //double totalPrice = datum.getQty() * Double.parseDouble(datum.getSProductPrice());
//            holder.tvValueMonth.setText(mContext.getResources().getString(R.string.Rs) + " " + datum.getValue());
//            holder.tvValueDisc.setText("- "+mContext.getResources().getString(R.string.Rs) +" "+datum.getDiscountValue()+"");
        }

        public void setOnItemClickLister(RecentOrdersAdapter.OnItemSelecteListener mListener) {
            this.mListener = mListener;
        }

        @Override
        public int getItemCount() {
            return mOrderList.size();
        }

        public interface OnItemSelecteListener {
            public void onItemSelected(View v, int position);
        }

        public class SurveyViewHolder extends RecyclerView.ViewHolder {

            private TextView tvTitle,tvBillingDate,tvQty, tvPOnumber, tvPoints;

            public SurveyViewHolder(View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvBillingDate = itemView.findViewById(R.id.tvBillingDate);
                tvQty = itemView.findViewById(R.id.tvQty);
                tvPOnumber = itemView.findViewById(R.id.tvPOnumber);
                tvPoints = itemView.findViewById(R.id.tvPoints);
            }
        }

}
