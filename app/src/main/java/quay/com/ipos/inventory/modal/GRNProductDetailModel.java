package quay.com.ipos.inventory.modal;

import java.io.Serializable;

/**
 * Created by niraj.kumar on 6/21/2018.
 */

public class GRNProductDetailModel implements Serializable {
    private String number;
    private String actionTitle;
    private int actionID;
    private int qty;

    public boolean isSelected;
    public String remark = "";

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = isSelected;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getActionTitle() {
        return actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public int getActionID() {
        return actionID;
    }

    public void setActionID(int actionID) {
        this.actionID = actionID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
