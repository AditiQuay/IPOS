package quay.com.ipos.compliance.interfaces;

import android.view.View;

/**
 * Created by deepak.kumar1 on 04-04-2018.
 */

public interface SubTaskHandler {
    void onClickCloseTask(View view);

    void onClickAddSubtask(View view);

    void onClickAddStartDate(View view);

    void onClickAddStartTime(View view);

    void onClickAddEndDate(View view);

    void onClickAddEndTime(View view);

    void onClickAlert(View view);

    void onClickRepeat(View view);

    void onClickRemRepeat(View view);

    void onClickDescription(View view);

    void onClickProgressState(View view);

    void onClickAssignTo(View view);

}
