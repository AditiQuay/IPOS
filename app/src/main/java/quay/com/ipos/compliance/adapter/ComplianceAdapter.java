package quay.com.ipos.compliance.adapter;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.Collections;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.utility.DateAndTimeUtil;

/**
 * Created by deepak.kumar1 on 19-03-2018.
 */

public class ComplianceAdapter extends RecyclerView.Adapter<ComplianceAdapter.ComplianceWiseVH> {
    private final Context context;
    private List<Task> list = Collections.emptyList();
    private int pagerPosition = 0;
    private View view;
    private int backGroundColor;

    public ComplianceAdapter(Context context, int backGroundColor) {
        this.context = context;
        this.backGroundColor = backGroundColor;
    }


    @Override
    public ComplianceWiseVH onCreateViewHolder(ViewGroup parent, int viewType) {
      /*  View view = LayoutInflater.from(context).inflate(R.layout.adapter_compliance, parent, false);
        return new ComplianceWiseVH(view);*/

        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_compliance, parent, false);
        return new ComplianceWiseVH(view);


    }

    @Override
    public void onBindViewHolder(ComplianceWiseVH holder, int position) {
        final Task compliance = list.get(position);
        holder.mainLayout.setBackgroundColor(backGroundColor);
        if (compliance.task_sub_category != null && compliance.task_sub_category.length() > 0) {
            holder.txtLogo.setText((compliance.task_sub_category.charAt(0) + "").toUpperCase());
        }
        holder.iposBlockTxt1.setText(compliance.task_sub_category + "");
        holder.mTxtdueDate.setText(DateAndTimeUtil.getCustomDateAndTime(compliance.getDateAndTime()));// + " "+taskData.task_due_date);
        holder.txt1.setText(compliance.task_name + "");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent = new Intent(view.getContext(), TaskDetailActivity.class);
                intent.putExtra("task_id", compliance.task_id);
                view.getContext().startActivity(intent);*/
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Task> compliances, int pagerPosition) {
        this.list = compliances;
        this.pagerPosition = pagerPosition;
        notifyDataSetChanged();
    }

    public class ComplianceWiseVH extends RecyclerView.ViewHolder {

        View mainLayout;
        public TextView txtLogo;
        public TextView mTxtdueDate;
        public TextView txt1;
        public TextView iposBlockTxt1;

        public ComplianceWiseVH(View view) {
            super(view);
            mainLayout = view.findViewById(R.id.mainLayout);
            txtLogo = view.findViewById(R.id.txtLogo);
            mTxtdueDate = view.findViewById(R.id.mTxtdueDate);
            txt1 = view.findViewById(R.id.txt1);
            iposBlockTxt1 = view.findViewById(R.id.ipos_block_txt1);
        }

    }

}
