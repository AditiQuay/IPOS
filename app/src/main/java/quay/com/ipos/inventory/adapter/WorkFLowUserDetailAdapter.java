package quay.com.ipos.inventory.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.inventory.modal.UserModal;
import quay.com.ipos.utility.Util;


public class WorkFLowUserDetailAdapter extends RecyclerView.Adapter<WorkFLowUserDetailAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<UserModal> stringArrayList;
    private OnItemSelecteListener mListener;
    private int lastCheckedPosition = -1;
    SparseBooleanArray mSelectedItems = new SparseBooleanArray();



    public WorkFLowUserDetailAdapter(Context mContext, ArrayList<UserModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;


    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_flow_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SurveyViewHolder holder, final int position) {


        if (Util.validateString(stringArrayList.get(position).getUserName()))
        holder.textViewCD.setText(stringArrayList.get(position).getUserName());

        if ("Submitted".equalsIgnoreCase(stringArrayList.get(position).getUserStatus())) {
            holder.imgCD.setBackgroundResource(R.drawable.green_circle);
        }else if ("Initiated".equalsIgnoreCase(stringArrayList.get(position).getUserStatus())){
            holder.imgCD.setBackgroundResource(R.drawable.yellow_circle);
        }else  if ("Approved".equalsIgnoreCase(stringArrayList.get(position).getUserStatus())){
            holder.imgCD.setBackgroundResource(R.drawable.green_circle);
        }else if ("Rejected".equalsIgnoreCase(stringArrayList.get(position).getUserStatus())){
            holder.imgCD.setBackgroundResource(R.drawable.red_circle);
        }else if ("".equalsIgnoreCase(stringArrayList.get(position).getUserStatus())){
           // holder.imgCD.setBackgroundResource(R.drawable.red_circle);
        }else {

        }

      //  holder.textViewStatus.setText(stringArrayList.get(position).getUserDateStatus());
        holder.textViewStatus.setVisibility(View.GONE);
        if(position == lastCheckedPosition) {
            holder.imgArrow.setImageResource(R.drawable.arrow_down);
        } else {
            holder.imgArrow.setImageResource(R.drawable.icon_right_arrow);
        }
        holder.llUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.animateView(v);
              //  holder.imgArrow.setImageResource(R.drawable.arrow_down);


                if(position == lastCheckedPosition) {
                    holder.imgArrow.setImageResource(R.drawable.icon_right_arrow);
                    mSelectedItems.put(position, false);

                } else {
                    holder.imgArrow.setImageResource(R.drawable.arrow_down);
                    mSelectedItems.put(position, true);

                }
                lastCheckedPosition = holder.getAdapterPosition();

                notifyDataSetChanged();




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
        private TextView imgCD, textViewCD, textViewStatus;
        private ImageView imgArrow;
        private LinearLayout llUser;
        public SurveyViewHolder(View itemView) {
            super(itemView);
            imgCD = itemView.findViewById(R.id.imgCD);
            textViewCD = itemView.findViewById(R.id.textViewCD);
            textViewStatus=itemView.findViewById(R.id.textViewStatus);
            imgArrow=itemView.findViewById(R.id.imgArrow);
            llUser=itemView.findViewById(R.id.llUser);

           /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onItemSelected(view, getAdapterPosition());

                }
            });*/

        }
    }
}
