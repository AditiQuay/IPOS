package quay.com.ipos.compliance.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.compliance.SubTaskActivity;
import quay.com.ipos.compliance.constants.AnnotationTaskState;
import quay.com.ipos.compliance.data.local.entity.SubTask;
import quay.com.ipos.utility.DateAndTimeUtil;

/**
 * Created by deepak.kumar1 on 19-03-2018.
 */

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.SubTaskVH> {
    private static final String TAG = SubTaskAdapter.class.getSimpleName();
    private final Context context;
    private List<SubTask> list = Collections.emptyList();


    public SubTaskAdapter(Context context) {
        this.context = context;
    }


    @Override
    public SubTaskVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater =
                LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_subtask, parent, false);
        return new SubTaskVH(view);
    }

    @Override
    public void onBindViewHolder(final SubTaskVH holder, int position) {
        try {
            final SubTask subTask = list.get(position);
            holder.textName.setText("subTaskID:"+subTask.id+", Name"+subTask.task_name);
            Calendar calendar = DateAndTimeUtil.parseDateAndTime(subTask.getDateAndTime());

            String strDueDate = DateAndTimeUtil.toStringReadableDate(calendar);
            String strDueTime = DateAndTimeUtil.toStringReadableTime(calendar, context);
            holder.txtDueDate.setText(strDueDate + " " + strDueTime);
            String strProgressState;

            if (subTask.progress_state == AnnotationTaskState.PENDING) {
                strProgressState = context.getString(R.string.task_pending);
            } else {
                strProgressState = context.getString(R.string.task_done);

            }
            holder.textProgressState.setText(strProgressState);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), SubTaskActivity.class);
                    intent.putExtra("id", subTask.id);
                    intent.putExtra("task_id", subTask.getTask_scheduler_id());
                    view.getContext().startActivity(intent);

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }


    @Override
    public void onBindViewHolder(SubTaskVH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }


    public void setData(List<SubTask> subTasks) {
        this.list = subTasks;
        notifyDataSetChanged();
    }


    class SubTaskVH extends RecyclerView.ViewHolder {
        public TextView textName;
        public TextView txtDueDate;
        public TextView textProgressState;

        public SubTaskVH(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            txtDueDate = itemView.findViewById(R.id.txtDueDate);
            textProgressState = itemView.findViewById(R.id.textProgressState);
        }
    }
}
