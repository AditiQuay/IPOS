package quay.com.ipos.dayClosure.dayClosureModel;

/**
 * Created by niraj.kumar on 7/11/2018.
 */

public class BusinessPlace {
    private String businessPlace;
    private int id;
    public BusinessPlace(String businessPlace, int id) {
        this.businessPlace = businessPlace;
        this.id = id;
    }
    public String getName() {
        return businessPlace;
    }

    public void setName(String name) {
        this.businessPlace = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
