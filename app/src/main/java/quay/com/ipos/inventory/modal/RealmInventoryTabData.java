package quay.com.ipos.inventory.modal;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by niraj.kumar on 6/21/2018.
 */

public class RealmInventoryTabData extends RealmObject {

    /**
     * tabTitle : Normal
     * tabId : 1
     * count : 1
     * model : [{"number":"123456","actionTitle":"Normal","actionID":1,"qty":2}]
     */
    @PrimaryKey
    private String tabTitle;
    private int tabId;
    private int count;
    private String model;
    @Ignore
    public boolean flag;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected;

    @Ignore
     public List<GRNProductDetailModel> modelList;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public int getTabId() {
        return tabId;
    }

    public void setTabId(int tabId) {
        this.tabId = tabId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


}
