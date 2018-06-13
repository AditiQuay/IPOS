package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.realmbean.RealmBusinessPlaces;


public class INCOTermsPOListAdapter extends RecyclerView.Adapter<INCOTermsPOListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<RealmBusinessPlaces> stringArrayList;
    private OnItemSelecteListener mListener;

    public INCOTermsPOListAdapter(Context mContext, ArrayList<RealmBusinessPlaces> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.inco_terms_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {



        holder.tvQty.setText(stringArrayList.get(position).getHeader());




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

        private TextView tvQty,textViewName;

        private RadioButton radio;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvQty = itemView.findViewById(R.id.tvQty);


        }
    }
}
