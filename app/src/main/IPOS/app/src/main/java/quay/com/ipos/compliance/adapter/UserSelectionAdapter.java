package quay.com.ipos.compliance.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;


import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.interfaces.UserSelectionListener;

/**
 * Created by deepak.kumar1 on 01-05-2018.
 */

public class UserSelectionAdapter extends RecyclerView.Adapter<UserSelectionAdapter.UserSelectionVH> {
    private List<Employee> data;
    private UserSelectionListener listener;

    public UserSelectionAdapter(List<Employee> userProfileModelList, UserSelectionListener listener) {
        this.data = userProfileModelList;
        this.listener = listener;
    }

    @Override
    public UserSelectionVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_assign_item, parent, false);
        return new UserSelectionVH(view);
    }

    @Override
    public void onBindViewHolder(UserSelectionVH holder, int position) {
        final Employee employee = data.get(position);
        holder.checkBox.setText(employee.empName);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onUserSlected(employee);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class UserSelectionVH extends RecyclerView.ViewHolder {
        private RadioButton checkBox;

        public UserSelectionVH(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkUser);
        }
    }
}
