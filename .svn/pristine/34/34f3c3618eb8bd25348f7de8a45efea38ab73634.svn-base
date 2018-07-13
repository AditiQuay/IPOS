package quay.com.ipos.compliance.fragment;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

import quay.com.ipos.R;
import quay.com.ipos.application.IPOSApplication;
import quay.com.ipos.compliance.adapter.UserSelectionAdapter;
import quay.com.ipos.compliance.data.local.entity.Employee;
import quay.com.ipos.compliance.interfaces.UserSelectionListener;

/**
 * Created by deepak.kumar1 on 03-04-2018.
 */

public class AssignTaskDialogFragment extends BottomSheetDialogFragment {


    private UserSelectionListener mListener;

    private RecyclerView  recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_assigntask, container, false);
        //binding = DataBindingUtil.bind(view);
        recyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // dismissAllowingStateLoss();
        /*        List<UserProfileModel> userProfileModelList = new RushSearch().find(UserProfileModel.class);*/
        IPOSApplication.getDatabase().employeeDao().fetchAllData().observe(getActivity(), new Observer<List<Employee>>() {
            @Override
            public void onChanged(@Nullable List<Employee> employees) {
                recyclerView.setAdapter(new UserSelectionAdapter(employees, new UserSelectionListener() {
                    @Override
                    public void onUserSlected(Employee employee) {
                        if (mListener != null) {
                            mListener.onUserSlected(employee);
                            dismiss();
                        }
                    }
                }));

            }
        });
    }

    public static AssignTaskDialogFragment showDialog(FragmentManager fm) {
        AssignTaskDialogFragment myDialog = new AssignTaskDialogFragment();
        myDialog.show(fm, "MyBottomSheetDialogFragment");
        return myDialog;
    }

    public void setListener(UserSelectionListener listener) {
        this.mListener = listener;
    }


}
