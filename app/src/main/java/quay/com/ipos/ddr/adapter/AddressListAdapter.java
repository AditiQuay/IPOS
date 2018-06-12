package quay.com.ipos.ddr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.modal.RecentOrderModal;
import quay.com.ipos.realmbean.RealmBusinessPlaces;


public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<RealmBusinessPlaces> stringArrayList;
    private OnItemSelecteListener mListener;

    public AddressListAdapter(Context mContext, ArrayList<RealmBusinessPlaces> stringArrayList) {
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



        holder.textViewHouseNo.setText(stringArrayList.get(position).getBuisnessPlaceName());
        holder.textViewName.setText(stringArrayList.get(position).getHeader());
        if (stringArrayList.get(position).isSelected())
        holder.radio.setChecked(true);
        else {
            holder.radio.setChecked(false);
        }



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

        private TextView textViewHouseNo,textViewName;

        private RadioButton radio;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            textViewHouseNo = itemView.findViewById(R.id.textViewHouseNo);
            textViewName=itemView.findViewById(R.id.textViewName);
            radio=itemView.findViewById(R.id.radio);

        }
    }
}
