package quay.com.ipos.compliance.adapter;

import android.content.Context;
import android.content.Intent;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.compliance.StorewiseComplianceActivity;
import quay.com.ipos.compliance.constants.AnnotationTaskState;
import quay.com.ipos.compliance.constants.KeyConstants;
import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.utility.DateAndTimeUtil;

/**
 * Created by deepak.kumar1 on 19-03-2018.
 */

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreWiseVH> {
    private static final String TAG = StoreAdapter.class.getSimpleName();
    private final Context context;
    private List<BusinessPlaceEntity> list = Collections.emptyList();
    private int pagerPosition = 0;
    private LayoutInflater layoutInflater;

    public StoreAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public StoreWiseVH onCreateViewHolder(ViewGroup parent, int viewType) {

       View view = layoutInflater.inflate(R.layout.c_adapter_store, parent, false);
        return new StoreWiseVH(view);
    }

    @Override
    public void onBindViewHolder(final StoreWiseVH holder, int position) {
        final BusinessPlaceEntity store = list.get(position);
        holder.iposBlockTxt1.setText(store.name + " | " + store.city+", "+store.state);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StorewiseComplianceActivity.class);
                intent.putExtra("title", "" + store.name);
                intent.putExtra("storeid", "" + store.id);
                intent.putExtra("curr_pos",pagerPosition);

                Log.i(TAG, "title" + store.name + ", storeid" + store.id);

                view.getContext().startActivity(intent);
            }
        });

        updateUI(store, holder);
    }

    @Override
    public int getItemCount() {
        // return list.size();
        // note this is hc value could be change
        return this.list.size();
    }

    public void setData(List<BusinessPlaceEntity> stores, int pagerPosition) {
        this.list = stores;
        this.pagerPosition = pagerPosition;
        this.notifyDataSetChanged();
    }


    @Override
    public void onBindViewHolder(StoreWiseVH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public void updateUI(BusinessPlaceEntity store, StoreWiseVH holder) {

        int total_comp_size = 0;
        int comp_done_size = 0;
        int comp_pending_size = 0;
        int com_upcoming_size = 0;
        int com_immediate_size = 0;

        String complianceType = "all";

        if (pagerPosition == 1) {
            complianceType = KeyConstants.KEY_TYPE_BUSINESS;
        }
        if (pagerPosition == 2) {
            complianceType = KeyConstants.KEY_TYPE_STATUTORY;
        }
        if (store.getComplianceList() == null || store.getComplianceList().size() == 0) {
            Log.e(TAG, "No any Compliance Record!");
            return;
        }
        for (Task taskData : store.getComplianceList()) {
           if(taskData.task_category==null){
               taskData.task_category = "";
           }
            if (complianceType.contentEquals("all") || taskData.task_category.contentEquals(complianceType)) {

                total_comp_size++;
                if (taskData.progress_state == AnnotationTaskState.DONE) {
                    comp_done_size++;
                } else if (taskData.progress_state == AnnotationTaskState.PENDING) {
                    comp_pending_size++;
                }


                Calendar dueDate = DateAndTimeUtil.parseDateAndTime(taskData.getDateAndTime());
                Calendar calendardayAfterTomorrow = Calendar.getInstance();
                calendardayAfterTomorrow.add(Calendar.DAY_OF_YEAR, 2);
                calendardayAfterTomorrow.add(Calendar.HOUR_OF_DAY, 0);
                calendardayAfterTomorrow.add(Calendar.MINUTE, 0);
                calendardayAfterTomorrow.add(Calendar.SECOND, 0);

                if (dueDate.after(calendardayAfterTomorrow)) {
                    com_upcoming_size++;
                }


                Calendar calendarImm = Calendar.getInstance();
                calendarImm.add(Calendar.DAY_OF_YEAR, 3);
                calendarImm.set(Calendar.HOUR_OF_DAY, 0);
                calendarImm.set(Calendar.MINUTE, 0);
                calendarImm.set(Calendar.SECOND, 0);
                if (dueDate.before(calendarImm) && taskData.progress_state == AnnotationTaskState.PENDING) {
                    com_immediate_size++;
                }

            }


        }

        holder.mCTCompliant.setText(comp_done_size + "/" + total_comp_size + "");
        holder.mCTNonCompliant.setText(comp_pending_size + "/" + total_comp_size + "");

        //  int com_immediate_size = DatabaseHelper.getInstance(context).getImmediateTaskList(store.id).size();
        holder.mCTImmediateAttention.setText(com_immediate_size + "");
        holder.mCTUpcomingEvents.setText(com_upcoming_size + "");
    }


    public class StoreWiseVH extends RecyclerView.ViewHolder {
        public TextView mCTCompliant;
        public TextView iposBlockTxt1;
        public TextView mCTNonCompliant;
        public TextView mCTImmediateAttention;
        public TextView mCTUpcomingEvents;

        public StoreWiseVH(View view) {
            super(view);
            iposBlockTxt1 = view.findViewById(R.id.ipos_block_txt1);
            mCTCompliant = view.findViewById(R.id.mCT_Compliant);
            mCTNonCompliant = view.findViewById(R.id.mCT_NonCompliant);
            mCTImmediateAttention = view.findViewById(R.id.mCT_ImmediateAttention);
            mCTUpcomingEvents = view.findViewById(R.id.mCT_UpcomingEvents);
        }

    }
}
