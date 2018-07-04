package quay.com.ipos.compliance.viewModel;

import android.app.Activity;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;


import java.util.Calendar;
import java.util.List;

import quay.com.ipos.compliance.constants.AnnotationTaskState;
import quay.com.ipos.compliance.constants.KeyConstants;
import quay.com.ipos.compliance.data.local.entity.BusinessPlaceEntity;
import quay.com.ipos.compliance.data.local.entity.Task;
import quay.com.ipos.utility.DateAndTimeUtil;

/**
 * Created by deepak.kumar1 on 29-03-2018.
 */

public class StoreViewModel extends ViewModel {
    private static final String TAG = StoreViewModel.class.getSimpleName();
    public int progressDone = 0;
    public int progressPending = 0;
    public int progressUpcoming = 0;
    public int progressImmediate = 0;
    public int maxSize = 0;

    public String progressDoneText = "0/0";
    public String progressPendingText = "0/0";
    public String progressUpcomingText = "0";
    public String progressImmText = "0";

    public StoreViewModel() {
    }

    public static  StoreViewModel getInstance(FragmentActivity fragmentActivity) {
        return ViewModelProviders.of(fragmentActivity).get(StoreViewModel.class);
    }

    public  static  StoreViewModel getInstance(Fragment fragment) {
        return ViewModelProviders.of(fragment).get(StoreViewModel.class);
    }

    public void updateUI(List<BusinessPlaceEntity> stores, int position) {

        int total_task_size = 0;
        int task_done_size = 0;
        int task_pending_size = 0;
        int com_upcoming_size = 0;
        int com_immediate_size = 0;

        String complianceType = "all";

        if (position == 1) {
            complianceType = KeyConstants.KEY_TYPE_BUSINESS;
        }
        if (position == 2) {
            complianceType = KeyConstants.KEY_TYPE_STATUTORY;
        }

        for (BusinessPlaceEntity store : stores) {
            if (store.getComplianceList() == null || store.getComplianceList().size() == 0) {
                Log.e("TAG", "No any Compliance Record!");
                return;
            }
            for (Task taskData : store.getComplianceList()) {

                if (complianceType.contentEquals("all") || taskData.task_category.contentEquals(complianceType)) {


                    total_task_size++;
                    if (taskData.progress_state == AnnotationTaskState.DONE) {
                        task_done_size++;
                    } else if (taskData.progress_state == AnnotationTaskState.PENDING) {
                        task_pending_size++;
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


        }
        //int com_immediate_size = DatabaseHelper.getInstance(context).getImmediateTaskList().size();
        progressDoneText = task_done_size + "/" + total_task_size + "";
        progressPendingText = task_pending_size + "/" + total_task_size + "";
        progressUpcomingText = com_upcoming_size + "";
        progressImmText = com_immediate_size + "";
        progressDone = task_done_size;
        progressPending = task_pending_size;
        maxSize = total_task_size;
        progressUpcoming = com_upcoming_size;
        progressImmediate = com_immediate_size;
        //    notifyChange();


    }

    public void updateUI(List<Task> compliances, int position, String storeId) {
        /*for (BusinessPlaceEntity store : stores) {
            Log.i(TAG, "store_detail" + store.toString());
            for (Compliance taskData : store.getComplianceList()) {
                Log.i(TAG, "compliance_detail" + taskData.toString());

            }
        }*/
        int total_task_size = 0;
        int task_done_size = 0;
        int task_pending_size = 0;
        int com_upcoming_size = 0;
        int com_immediate_size = 0;

        String complianceType = "all";

        if (position == 1) {
            complianceType = KeyConstants.KEY_TYPE_BUSINESS;
        }
        if (position == 2) {
            complianceType = KeyConstants.KEY_TYPE_STATUTORY;
        }


        for (Task taskData : compliances) {

            if (taskData.task_category == null) {
                taskData.task_category = "";
            }
            if (complianceType.contentEquals("all") || taskData.task_category.contentEquals(complianceType)) {


                total_task_size++;
                if (taskData.progress_state == AnnotationTaskState.DONE) {
                    task_done_size++;
                } else if (taskData.progress_state == AnnotationTaskState.PENDING) {
                    task_pending_size++;
                }
               /* if (DateAndTimeUtil.parseDateAndTime(taskData.task_due_date).after(DashboardActivity.CURRENT_DATE_LONG)) {
                    com_upcoming_size++;
                }*/
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


        // int com_immediate_size = DatabaseHelper.getInstance(context).getImmediateTaskList().size();

        progressDoneText = task_done_size + "/" + total_task_size + "";
        progressPendingText = task_pending_size + "/" + total_task_size + "";
        progressUpcomingText = com_upcoming_size + "";
        progressImmText = com_immediate_size + "";
        progressDone = task_done_size;
        progressPending = task_pending_size;
        maxSize = total_task_size;

        progressUpcoming = com_upcoming_size;

        progressImmediate = com_immediate_size;
        // notifyChange();


    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}