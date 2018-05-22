package quay.com.ipos.modal;

/**
 * Created by niraj.kumar on 5/15/2018.
 */

public class DrawerRoleModal {
    public String name;
    private boolean selected;
    // C
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
