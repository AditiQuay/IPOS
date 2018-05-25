package quay.com.ipos.enums;

/**
 * Created by niraj.kumar on 5/25/2018.
 */

public enum ProductCatalogueEnum {
    info("info"), section("section"),sectionProduct("sectionProduct"),
    sectionItems("sectionItems"), productId("productId"),
    productName("productName"), productUrl("productUrl"), count("count");

    private final String name;

    ProductCatalogueEnum(String s) {
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
