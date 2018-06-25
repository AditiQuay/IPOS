package quay.com.ipos.pss_order.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.pss_order.modal.UserModal;


public class WorkFLowAdapter extends RecyclerView.Adapter<WorkFLowAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<UserModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public WorkFLowAdapter(Context mContext, ArrayList<UserModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.workflow_row, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {

        if (stringArrayList.size()==1){
            holder.textDot.setVisibility(View.GONE);

        }else {
            holder.textDot.setVisibility(View.VISIBLE);
        }

        if (position==0){
            holder.view1.setVisibility(View.GONE);
        }else{
            holder.view1.setVisibility(View.VISIBLE);
        }
        if (position==stringArrayList.size()-1){
            holder.view2.setVisibility(View.GONE);
        }else{
            holder.view2.setVisibility(View.VISIBLE);
        }

        holder.textViewDate.setText(stringArrayList.get(position).getDate());

        holder.textViewRetailersName.setText(stringArrayList.get(position).getUserName());
        holder.textViewStatus.setText(stringArrayList.get(position).getUserStatus());




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
        private TextView textViewRetailersName, textViewStatus,textDot,textViewDate;

        private View view1,view2;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            textViewRetailersName = itemView.findViewById(R.id.textViewRetailersName);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textDot=itemView.findViewById(R.id.textDot);
            view1=itemView.findViewById(R.id.view1);
            view2=itemView.findViewById(R.id.view2);
            textViewDate=itemView.findViewById(R.id.textViewDate);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());

                }
            });*/

        }
    }
}
