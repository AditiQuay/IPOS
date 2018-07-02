package quay.com.ipos.inventory.modal;

/**
 * Created by ankush.bansal on 29-06-2018.
 */

public class CommonModal {

    String id;
    String address;
    private String supplierGSTIN;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSupplierGSTIN() {
        return supplierGSTIN;
    }

    public void setSupplierGSTIN(String supplierGSTIN) {
        this.supplierGSTIN = supplierGSTIN;
    }
}


