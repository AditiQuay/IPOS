package quay.com.ipos.compliance.viewModel;

import android.view.View;



/**
 * Created by deepak.kumar1 on 26-03-2018.
 */

public class ProgressStateViewModel {
    public boolean viewDone = false;
    public boolean viewPending = false;
    public boolean viewUpcoming = false;
    public boolean viewImmediate = false;
    private ComplianceViewFilterSelectionListner listner;

    public ProgressStateViewModel(ComplianceViewFilterSelectionListner listner) {
        this.listner = listner;
    }




    public void setAllTrue() {
        viewDone = true;
        viewPending = true;
        viewUpcoming = true;
        viewImmediate = true;
        this.listner.onViewAllSelected();
        //notifyChange();
    }

    public void setViewDone(View view, boolean viewDone) {
        //  Toast.makeText(view.getContext(), "setViewDone", Toast.LENGTH_SHORT).show();
        setAllFalse();
        this.viewDone = true;
        listner.onDoneSelected();
     //   notifyChange();
    }

    public void setViewImmediate(boolean viewImmediate) {
        setAllFalse();
        this.viewImmediate = viewImmediate;
        this.listner.onImmediateSelected();
    //    notifyChange();

    }

    public void setViewPending(boolean viewPending) {
        setAllFalse();
        this.viewPending = viewPending;
        this.listner.onPendingSelected();
        //notifyChange();

    }

    public void setViewUpcoming(boolean viewUpcoming) {
        setAllFalse();
        this.viewUpcoming = viewUpcoming;
        this.listner.onUpcomingSelected();
       // notifyChange();

    }

    private void setAllFalse() {
        viewDone = false;
        viewImmediate = false;
        viewUpcoming = false;
        viewPending = false;
    }

}
