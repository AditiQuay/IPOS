package com.quayintech.tasklib.interfaces;

import android.view.View;

/**
 * Created by deepak.kumar1 on 04-04-2018.
 */

public interface RepeatPageHandler {

    void onClickCloseTask(View view);
    void onClickAddSubtask(View view);


    void onClickFrequency(View view);

    void onClickInterval(View view);

    void onClickOnDays(View view);

    void onClickUntil(View view);

}
