package quay.com.ipos.compliance.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import quay.com.ipos.R;
import quay.com.ipos.compliance.constants.AnnotationTaskState;

/**
 * Created by deepak.kumar1 on 03-04-2018.
 */

public class TaskStateSelectorDialogFragment extends BottomSheetDialogFragment {
    private @AnnotationTaskState.TaskState
    int mTaskType;
    private   ProgressStateListener mListener;
   private RadioButton checkboxPending, checkboxDone;
    private RadioGroup radioGroup;
  
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_state_selector_dilaog, container, false);
        radioGroup = view.findViewById(R.id.radioGroup);
        checkboxPending = view.findViewById(R.id.checkboxPending);
        checkboxDone = view.findViewById(R.id.checkboxDone);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUI();
    }

    private void updateUI() {
        switch (mTaskType) {
            case AnnotationTaskState.DONE:
                checkboxDone.setChecked(true);
                break;
            case AnnotationTaskState.PENDING:
                checkboxPending.setChecked(true);
                break;
        }

        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                String string = null;
                @AnnotationTaskState.TaskState
                int taskState = 0;
                if (checkboxPending.isChecked()) {
                    string = "Pending";
                    taskState = AnnotationTaskState.PENDING;
                }
                if (checkboxDone.isChecked()) {
                    string = "Done";
                    taskState = AnnotationTaskState.DONE;
                }
                Toast.makeText(getActivity(), string+" is Selected!", Toast.LENGTH_SHORT).show();

                if (mListener != null) {
                    mListener.onStateSlected(taskState);

                }

                dismiss();
            }
        });

       /* checkboxPending.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dismiss();
            }
        });
        checkboxDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dismiss();
            }
        });*/


    }

    public static TaskStateSelectorDialogFragment showDialog(FragmentManager fm) {
        TaskStateSelectorDialogFragment myDialog = new TaskStateSelectorDialogFragment();
        myDialog.show(fm, "MyBottomSheetDialogFragment");
        return myDialog;
    }

    public void setListener(ProgressStateListener listener) {
        this.mListener = listener;
    }

    public interface ProgressStateListener {
        void onStateSlected(@AnnotationTaskState.TaskState int mTaskType);


    }

}
