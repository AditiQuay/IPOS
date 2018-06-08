package quay.com.ipos.enums;

/**
 * Created by ankush.bansal on 04-06-2018.
 */

public enum NoGetEntityEnums {
    poNumber("poNumber"),parentProductId("parentProductId"),buisnessPlaces("buisnessPlaces"),buisnessPlaceId("buisnessPlaceId"),buisnessPlaceName("buisnessPlaceName"),
    buisnessLocationStateCode("buisnessLocationStateCode"),data("data"),OrderId("OrderId"),productCode("productCode"),iProductModalId("iProductModalId");

    private final String name;

    NoGetEntityEnums(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equalsIgnoreCase(otherName);
    }

    public String toString() {
        return this.name;
    }
}

