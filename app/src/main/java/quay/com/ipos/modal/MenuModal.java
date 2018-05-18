package quay.com.ipos.modal;

import java.util.ArrayList;

/**
 * Created by niraj.kumar on 5/16/2018.
 */

public class MenuModal {
    private String groupTitle;
    private String groupIcon;
    private ArrayList<String> arrayList;

    public String getGroupTitle() {
        return groupTitle;
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }
}
