package quay.com.ipos.ddr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.RecentOrderModal;



public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<RecentOrderModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public AddressListAdapter(Context mContext, ArrayList<RecentOrderModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.address_list_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {


        holder.textViewHouseNo.setText(stringArrayList.get(position).getTitle());



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

        private TextView textViewHouseNo;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            textViewHouseNo = itemView.findViewById(R.id.textViewHouseNo);

        }
    }
}
