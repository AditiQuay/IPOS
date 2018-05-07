package quay.com.ipos.ddr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.OrderList;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class ItemsDetailListAdapter extends RecyclerView.Adapter<ItemsDetailListAdapter.SurveyViewHolder> {
    public ArrayList<OrderList.Datum> mOrderList = new ArrayList<>();
    private Context mContext;
    //    private ArrayList<RecentOrderModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public ItemsDetailListAdapter(Context mContext, ArrayList<OrderList.Datum> stringArrayList) {
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

        OrderList.Datum datum = mOrderList.get(position);
        holder.tvTitle.setText(datum.getSProductName());
        holder.tvItemQty.setText(datum.getQty());
        holder.tvValue.setText(mContext.getResources().getString(R.string.Rs) + " " + datum.getSProductPrice());
        double totalPrice = datum.getQty() * Double.parseDouble(datum.getSProductPrice());
        holder.tvValueMonth.setText(mContext.getResources().getString(R.string.Rs) + " " + totalPrice);
        if(datum.getIsDiscount()) {
            Double discount = (Double.parseDouble(datum.getSDiscountPrice())*totalPrice)/100;
            holder.tvValueDisc.setText(mContext.getResources().getString(R.string.Rs) +" "+discount+"");
            holder.tvValueDisc.setVisibility(View.VISIBLE);
        }else {
//                str.setDiscItemSelected(false);
//                str.setTotalPrice(totalPrice);
//                str.setDiscount(0.0);
            holder.tvValueDisc.setVisibility(View.GONE);
        }
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
