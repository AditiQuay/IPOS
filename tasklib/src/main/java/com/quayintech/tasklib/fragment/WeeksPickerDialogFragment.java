package com.quayintech.tasklib.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quayintech.tasklib.R;
import com.quayintech.tasklib.adapter.AlertSelectionAdapter;
import com.quayintech.tasklib.adapter.WeekSelectionAdapter;
import com.quayintech.tasklib.databinding.FragmentDialogAlertpickerBinding;
import com.quayintech.tasklib.databinding.FragmentDialogPickerWeeksBinding;
import com.quayintech.tasklib.interfaces.AlertSelectionListener;
import com.quayintech.tasklib.interfaces.WeeksSelectionListener;
import com.quayintech.tasklib.model.Alert;
import com.quayintech.tasklib.model.AlertUtils;
import com.quayintech.tasklib.model.WeekUtils;
import com.quayintech.tasklib.model.Weeks;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepak.kumar1 on 03-04-2018.
 */

public class WeeksPickerDialogFragment extends DialogFragment implements WeeksSelectionListener {
    private WeeksSelectionListener mListener;
    FragmentDialogPickerWeeksBinding binding;
    private static String mAlertLabel;
    private List<Weeks> selectedData = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_picker_weeks, container, false);
        binding = DataBindingUtil.bind(view);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // dismissAllowingStateLoss();
        List<Weeks> alertList = WeekUtils.getWeeksList();
        if (mAlertLabel != null) {
            for (Weeks alert : alertList) {
                if (mAlertLabel.contains(alert.getLabel().substring(0, 3))) {
                    alert.setSelected(true);

                } else {
                    alert.setSelected(false);

                }
            }
        }

        binding.recyclerView.setAdapter(new WeekSelectionAdapter(alertList, this));
        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null && selectedData.size() > 0) {
                    mListener.onWeeksSelected(selectedData);
                    dismiss();
                }
            }
        });
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public static WeeksPickerDialogFragment showDialog(FragmentManager fm, String alertLabel) {
        mAlertLabel = alertLabel;
        WeeksPickerDialogFragment myDialog = new WeeksPickerDialogFragment();

        myDialog.show(fm, "MyBottomSheetDialogFragment");

        return myDialog;
    }

    public void setListener(WeeksSelectionListener listener) {
        this.mListener = listener;
    }


    @Override
    public void onWeeksSelected(List<Weeks> list) {
        selectedData = new ArrayList<>();
        for (Weeks weeks : list) {
            if (weeks.isSelected()) {
                selectedData.add(weeks);
            }

        }

        if (selectedData.size() > 0) {
            binding.btnOk.setEnabled(true);
        } else {
            binding.btnOk.setEnabled(false);
        }

    }
}
