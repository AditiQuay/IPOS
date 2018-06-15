package quay.com.ipos.realmbean;

import io.realm.RealmObject;

/**
 * Created by ankush.bansal on 15-06-2018.
 */

public class RealmPOInventory extends RealmObject {

    private String poNumber;
    private String id;
    private String date;
    private double value;
    private String company;

    public String getPoNumber() {
        return poNumber;
    }

    public void setPoNumber(String poNumber) {
        this.poNumber = poNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
