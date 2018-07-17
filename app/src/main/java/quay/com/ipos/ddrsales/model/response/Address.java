package quay.com.ipos.ddrsales.model.response;

import java.io.Serializable;

public class Address implements Serializable{


    public int id;
    public String name;
    public boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
