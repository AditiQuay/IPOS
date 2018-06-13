package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.RecentOrderModal;


public class NewOrderItemsDetailListAdapter extends RecyclerView.Adapter<NewOrderItemsDetailListAdapter.SurveyViewHolder> {
    public ArrayList<RecentOrderModal> mOrderList = new ArrayList<>();
    private Context mContext;
    //    private ArrayList<RecentOrderModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public NewOrderItemsDetailListAdapter(Context mContext, ArrayList<RecentOrderModal> stringArrayList) {
        this.mContext = mContext;
        this.mOrderList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_details_list_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {

        RecentOrderModal datum = mOrderList.get(position);
        holder.tvTitle.setText(datum.getTitle());
        holder.tvItemQty.setText(datum.getQty()+"");
        holder.tvValue.setText(mContext.getResources().getString(R.string.Rs) + " " + datum.getDiscountValue());
        //double totalPrice = datum.getQty() * Double.parseDouble(datum.getSProductPrice());
        holder.tvValueMonth.setText(mContext.getResources().getString(R.string.Rs) + " " + datum.getValue());
        holder.tvValueDisc.setText("- "+mContext.getResources().getString(R.string.Rs) +" "+datum.getDiscountValue()+"");
    }

    public void setOnItemClickLister(OnItemSelecteListener mListener) {
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

        private TextView tvTitle, tvValue, tvItemQty, tvValueMonth, tvValueDisc;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvValue = itemView.findViewById(R.id.tvValue);
            tvItemQty = itemView.findViewById(R.id.tvItemQty);
            tvValueMonth = itemView.findViewById(R.id.tvValueMonth);
            tvValueDisc = itemView.findViewById(R.id.tvValueDisc);
        }
    }
}
