package quay.com.ipos.dashboard.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import quay.com.ipos.R;
import quay.com.ipos.dashboard.modal.BarGraphModal;
import quay.com.ipos.dashboard.modal.LowInventoryModal;
import quay.com.ipos.utility.Util;

/**
 * Created by gaurav.pandey on 24-01-2018.
 */

public class BarGraphAdapter extends RecyclerView.Adapter<BarGraphAdapter.SurveyViewHolder> {
    private Context mContext;
    private ArrayList<BarGraphModal> stringArrayList;
    private OnItemSelecteListener mListener;

    public BarGraphAdapter(Context mContext, ArrayList<BarGraphModal> stringArrayList) {
        this.mContext = mContext;
        this.stringArrayList = stringArrayList;

    }

    @Override
    public SurveyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.bar_graph_item, parent, false);
        return new SurveyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(SurveyViewHolder holder, int position) {

        if (position==0){
            holder.tvTitle.setTextColor(ContextCompat.getColor(mContext,R.color.green));
        }

        holder.tvTitle.setText(stringArrayList.get(position).getTitle());
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) holder.seekbar.getLayoutParams();
        int progress=stringArrayList.get(position).getProgress();

       // int prog=(Integer.parseInt(stringArrayList.get(position).getMoney())*100)/(50000000);

        double progDouble=Double.parseDouble(stringArrayList.get(position).getMoney())*100/50000000;
        if (progDouble<=0){
            lp.width = 5*3;
        }else {
            lp.width = (int) (progDouble*3);
        }
        Log.e("TOTALVALUE",progDouble+" width=="+ lp.width);
        holder.seekbar.setLayoutParams(lp);
      /*  holder.seekbar.setMax(100);
        holder.seekbar.setProgress(stringArrayList.get(position).getProgress());
        holder.seekbar.setSecondaryProgress(100);*/
        holder.tvValueMonth.setText("₹ "+stringArrayList.get(position).getMoney());



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

        private TextView tvTitle;
        private TextView seekbar;
        private TextView tvValueMonth;

        public SurveyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            seekbar=(TextView) itemView.findViewById(R.id.seekbar);
            tvValueMonth=itemView.findViewById(R.id.tvValueMonth);

        }
    }
}
