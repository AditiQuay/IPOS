package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.activity.ExpandablePODetailsActivity;
import quay.com.ipos.inventory.activity.InventoryWorkFlowActivity;
import quay.com.ipos.inventory.modal.InventoryModel;
import quay.com.ipos.realmbean.RealmPOInventory;


public class InventoryListAdapter extends RecyclerView.Adapter<InventoryListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<RealmPOInventory> stringArrayList;
    private OnItemSelecteListener mListener;

    public InventoryListAdapter(Context mContext, ArrayList<RealmPOInventory> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inventory_list_childs, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SurveyViewHolder holder, int position) {



        holder.tvPoNumber.setText(stringArrayList.get(position).getPoNumber());
        holder.tvTitle.setText(stringArrayList.get(position).getCompany());
        holder.tvDate.setText(stringArrayList.get(position).getDate());
        holder.tvOrderNumber.setText(stringArrayList.get(position).getId());
        holder.tvValue.setText(stringArrayList.get(position).getValue()+"");


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext, ExpandablePODetailsActivity.class);
                i.putExtra("poNumber",stringArrayList.get(holder.getAdapterPosition()).getPoNumber());
                mContext.startActivity(i);
            }
        });

        holder.tvOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext, ExpandablePODetailsActivity.class);
                i.putExtra("poNumber",stringArrayList.get(holder.getAdapterPosition()).getPoNumber());
                mContext.startActivity(i);
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

        private TextView tvPoNumber,tvOpen,tvOrderNumber,tvTitle,tvValue,tvDate;

        private RadioButton radio;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvPoNumber = itemView.findViewById(R.id.tvPoNumber);
            tvOpen=itemView.findViewById(R.id.tvOpen);
            tvOrderNumber=itemView.findViewById(R.id.tvOrderNumber);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvValue=itemView.findViewById(R.id.tvValue);
            tvDate=itemView.findViewById(R.id.tvDate);


        }
    }
}
