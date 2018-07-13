package com.quayintech.tasklib.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.quayintech.tasklib.R;
import com.quayintech.tasklib.adapter.AlertSelectionAdapter;
import com.quayintech.tasklib.databinding.FragmentDialogAlertpickerBinding;
import com.quayintech.tasklib.interfaces.AlertSelectionListener;
import com.quayintech.tasklib.model.Alert;
import com.quayintech.tasklib.model.AlertUtils;

import java.util.List;

/**
 * Created by deepak.kumar1 on 03-04-2018.
 */

public class AlertPickerDialogFragment extends BottomSheetDialogFragment implements AlertSelectionListener {
    private AlertSelectionListener mListener;
    FragmentDialogAlertpickerBinding binding;
    private static String mAlertLabel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_alertpicker, container, false);
        binding = DataBindingUtil.bind(view);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // dismissAllowingStateLoss();
        List<Alert> alertList = AlertUtils.getAlertList();
        if (mAlertLabel != null) {
            for (Alert alert : alertList) {
                if (alert.getLabel().contentEquals(mAlertLabel)) {
                    alert.setSelected(true);

                } else {
                    alert.setSelected(false);

                }
            }
        }

        binding.recyclerView.setAdapter(new AlertSelectionAdapter(alertList, this));
    }

    public static AlertPickerDialogFragment showDialog(FragmentManager fm, String alertLabel) {
        mAlertLabel = alertLabel;
        AlertPickerDialogFragment myDialog = new AlertPickerDialogFragment();

        myDialog.show(fm, "MyBottomSheetDialogFragment");

        return myDialog;
    }

    public void setListener(AlertSelectionListener listener) {
        this.mListener = listener;
    }


    @Override
    public void onAlertSelected(Alert alert) {
        if (mListener != null) {
            mListener.onAlertSelected(alert);
            dismiss();
        }
    }
}
