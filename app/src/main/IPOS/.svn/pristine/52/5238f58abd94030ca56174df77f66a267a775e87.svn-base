package com.quayintech.tasklib.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepak.kumar1 on 03-05-2018.
 */

public class AlertUtils {
    public static List<Alert> getAlertList() {
        List<Alert> alertList = new ArrayList<>();
        alertList.add(new Alert(0,-1, "None", false));
        alertList.add(new Alert(101,0, "At time of event", false));
        alertList.add(new Alert(102,5, "5 minutes before", false));
      //  alertList.add(new Alert(103,600000, "10 minutes before", false));
        alertList.add(new Alert(104,15, "15 minutes before", false));
        alertList.add(new Alert(105,30, "30 minutes before", false));
        alertList.add(new Alert(106,60, "1 hour before", false));
       // alertList.add(new Alert(107,7200000, "2 hours before", false));
        alertList.add(new Alert(108,1440, "1 day before", false));
        alertList.add(new Alert(109,10080, "1 week before", false));
        alertList.add(new Alert(110,43200, "1 month before", false));
        return alertList;
    }

    public static Alert getDefaultAlert() {
       return getAlertList().get(0);
    }

}
