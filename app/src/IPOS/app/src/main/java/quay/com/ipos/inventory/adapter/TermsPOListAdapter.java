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
import quay.com.ipos.inventory.modal.POTermsCondition;
import quay.com.ipos.realmbean.RealmBusinessPlaces;


public class TermsPOListAdapter extends RecyclerView.Adapter<TermsPOListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<POTermsCondition> stringArrayList;
    private OnItemSelecteListener mListener;

    public TermsPOListAdapter(Context mContext, ArrayList<POTermsCondition> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {



        holder.text1.setText((position+1)+". "+stringArrayList.get(position).getpOTermsAndConditionDetail());




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

        private TextView text1,textViewName;

        private RadioButton radio;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(R.id.text1);


        }
    }
}
