package quay.com.ipos.modal;

import java.util.ArrayList;

import quay.com.ipos.inventory.modal.InventoryPOModal;

/**
 * Created by niraj.kumar on 5/16/2018.
 */

public class MenuModal {
    private String groupTitle;
    private String groupIcon;
    private ArrayList<String> arrayList;
    private ArrayList<InventoryPOModal> inventoryPOModals;

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

    public ArrayList<InventoryPOModal> getInventoryPOModals() {
        return inventoryPOModals;
    }

    public void setInventoryPOModals(ArrayList<InventoryPOModal> inventoryPOModals) {
        this.inventoryPOModals = inventoryPOModals;
    }
}
