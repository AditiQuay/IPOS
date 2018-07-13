package quay.com.ipos.pss_order.adapter;

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


public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.SurveyViewHolder> {
    private Context mContext;
    private static RadioButton lastChecked = null;
    private static int lastCheckedPos = 0;
    private ArrayList<RealmBusinessPlaces> stringArrayList;
    private OnItemSelecteListener mListener;
    View.OnClickListener mOnClickListener;

    public AddressListAdapter(Context mContext, ArrayList<RealmBusinessPlaces> stringArrayList,View.OnClickListener mClickListener) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;
        this.mOnClickListener = mClickListener;

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
       // if (stringArrayList.get(position).isSelected())
        holder.radio.setChecked(stringArrayList.get(position).isSelected());
        holder.radio.setTag(position);
        if (stringArrayList.size()==1){
            holder.radio.setEnabled(false);
            holder.radio.setClickable(false);
        }
        if (stringArrayList.get(position).isSelected()){
            lastChecked = holder.radio;
            lastCheckedPos = 0;
        }
        holder.radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton cb = (RadioButton)view;
                int clickedPos = (Integer) cb.getTag();
                if(cb.isChecked())
                {
                    if(lastChecked != null)
                    {
                        lastChecked.setChecked(false);
                        stringArrayList.get(lastCheckedPos).setSelected(false);
                    }

                    lastChecked = cb;
                    lastCheckedPos = clickedPos;
                }
                else
                    lastChecked = null;

                stringArrayList.get(clickedPos).setSelected(cb.isChecked());
                mOnClickListener.onClick(view);
            }
        });




      //  else {
       //     holder.radio.setChecked(false);
      //  }



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
