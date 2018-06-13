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
import quay.com.ipos.inventory.modal.InventoryModel;


public class InventoryListAdapter extends RecyclerView.Adapter<InventoryListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<InventoryModel> stringArrayList;
    private OnItemSelecteListener mListener;

    public InventoryListAdapter(Context mContext, ArrayList<InventoryModel> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inventory_list_childs, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {



        holder.tvPoNumber.setText(stringArrayList.get(position).getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(mContext, ExpandablePODetailsActivity.class);
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

        private TextView tvPoNumber;

        private RadioButton radio;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvPoNumber = itemView.findViewById(R.id.tvPoNumber);


        }
    }
}
