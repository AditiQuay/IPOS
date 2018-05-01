package quay.com.ipos.retailsales.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.retailsales.activity.CustomerDetailActivity;


/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<RecentOrderModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public CustomerListAdapter(Context mContext, ArrayList<RecentOrderModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.customer_list_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {


        holder.textViewName.setText(stringArrayList.get(position).getTitle());


        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, CustomerDetailActivity.class);
                mContext.startActivity(intent);
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
        private TextView textViewName;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            item=itemView;
            textViewName = itemView.findViewById(R.id.tvName);

        }
    }
}
