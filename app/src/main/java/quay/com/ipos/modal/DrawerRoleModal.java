package quay.com.ipos.modal;

/**
 * Created by niraj.kumar on 5/15/2018.
 */

public class DrawerRoleModal {
    public String name;
    private boolean selected;
    // Constructor.
    public DrawerRoleModal(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
