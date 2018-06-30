package quay.com.ipos.ddrsales.model.response;

public class Address {
    public static int ADDRESS_TYPE_BILLING = 1;
    public static int ADDRESS_TYPE_SHIPPING = 2;

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
