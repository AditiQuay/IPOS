package quay.com.ipos.compliance.interfaces;

import android.view.View;

/**
 * Created by deepak.kumar1 on 04-04-2018.
 */

public interface ComplianceDetailHandler {
    void onClickUpdate(View view);

    void onClickAddSubTask(View view);

    void onClickClose(View view);

    void onClickAddStartDate(View view);

    void onClickAddStartTime(View view);


    void onClickAddDate(View view);

    void onClickAddTime(View view);

    void onClickAlert(View view);

    void onClickRepeat(View view);
    void onClickDescription(View view);

    void onClickProgressState(View view);

    void onClickAssignTo(View view);

}
